/**
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package wayos.car.updateEngine;

import android.os.IBinder;
import android.os.IUpdateEngine;
import android.os.IUpdateEngineCallback;
import wayos.car.updateEngine.ICarUpdateEngineCallback;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.storage.StorageManager;
import android.os.storage.VolumeInfo;
import android.content.Context;
import android.util.Log;
import android.os.AsyncTask;
import java.util.ArrayList;
import java.util.List;
import com.android.internal.util.Preconditions;
import java.io.IOException;
import wayos.car.updateEngine.UpdateParser.ParsedUpdate;
import android.os.UpdateEngine;
import android.os.UpdateEngineCallback;
import android.car.CarNotConnectedException;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.File;


/**
 * UpdateEngine handles calls to the update engine which takes care of A/B OTA
 * updates. It wraps up the update engine Binder APIs and exposes them as
 * SystemApis, which will be called by the system app responsible for OTAs.
 * On a Google device, this will be GmsCore.
 *
 * The minimal flow is:
 * <ol>
 * <li>Create a new UpdateEngine instance.
 * <li>Call {@link #bind}, optionally providing callbacks.
 * <li>Call {@link #applyPayload}.
 * </ol>
 *
 * In addition, methods are provided to {@link #cancel} or
 * {@link #suspend}/{@link #resume} application of an update.
 *
 * The APIs defined in this class and UpdateEngineCallback class must be in
 * sync with the ones in
 * system/update_engine/binder_bindings/android/os/IUpdateEngine.aidl and
 * system/update_engine/binder_bindings/android/os/IUpdateEngineCallback.aidl.
 *
 *
 */
public class CarUpdateEngine{
    private static final String TAG = "CarUpdateService";
    private static final String UPDATE_ENGINE_SERVICE = "android.os.UpdateEngineService";
    private static final String UPDATE_FILE_NAME = "/ota_update.zip";
    private static final String CORE_AMP_PATH = "/data/ota_package/care_map.txt";
    private File mUpdateFile;;
    private StorageManager mStorageManager;
    private IUpdateEngine mUpdateEngine = null;
    private IUpdateEngineCallback mUpdateEngineCallback = null;
    private ICarUpdateEngineCallback mCarUpdateCallback = null;
    private UpdateParser.ParsedUpdate mUnZipResult = null;
    private UpdateParser mUpdateParser = new UpdateParser();
    private final Object mUpdateEngineCallbackLock = new Object();
    public static final class ErrorCodeConstants {
        public static final int SUCCESS = 0;
        public static final int ERROR = 1;
        public static final int FILESYSTEM_COPIER_ERROR = 4;
        public static final int POST_INSTALL_RUNNER_ERROR = 5;
        public static final int PAYLOAD_MISMATCHED_TYPE_ERROR = 6;
        public static final int INSTALL_DEVICE_OPEN_ERROR = 7;
        public static final int KERNEL_DEVICE_OPEN_ERROR = 8;
        public static final int DOWNLOAD_TRANSFER_ERROR = 9;
        public static final int PAYLOAD_HASH_MISMATCH_ERROR = 10;
        public static final int PAYLOAD_SIZE_MISMATCH_ERROR = 11;
        public static final int DOWNLOAD_PAYLOAD_VERIFICATION_ERROR = 12;
        public static final int UPDATED_BUT_NOT_ACTIVE = 52;
    }

    /**
     * Update status code from the update engine. Values must agree with the
     * ones in system/update_engine/client_library/include/update_engine/update_status.h.
     */
    public static final class UpdateStatusConstants {
        public static final int IDLE = 0;
        public static final int CHECKING_FOR_UPDATE = 1;
        public static final int UPDATE_AVAILABLE = 2;
        public static final int DOWNLOADING = 3;
        public static final int VERIFYING = 4;
        public static final int FINALIZING = 5;
        public static final int UPDATED_NEED_REBOOT = 6;
        public static final int REPORTING_ERROR_EVENT = 7;
        public static final int ATTEMPTING_ROLLBACK = 8;
        public static final int DISABLED = 9;
    }

    public CarUpdateEngine(Context context) {
        mUpdateEngine  = IUpdateEngine.Stub.asInterface(ServiceManager.getService(UPDATE_ENGINE_SERVICE));
        mStorageManager = (StorageManager) context.getSystemService(Context.STORAGE_SERVICE);
    }

    public void init() {

    }

    public void release() {

    }

    public void dump(PrintWriter writer) {

    }

    public void registerUpdateCallback(ICarUpdateEngineCallback callback) {
            mCarUpdateCallback = callback;
            bind();
    }


    public void unRegisterUpdateCallback(ICarUpdateEngineCallback callback) {
        if (null != mCarUpdateCallback) {
            mCarUpdateCallback = null;
        }
        unbind();
    }

    private void bind() {
        synchronized (mUpdateEngineCallbackLock) {
            mUpdateEngineCallback = new IUpdateEngineCallback.Stub() {
                @Override
                public void onStatusUpdate(final int status, final float percent) {
                    Log.d(TAG, "mUpdateEngine status " + status + " percent= " + percent);
                    mCarUpdateCallback.onStatusUpdate(status, percent);
                }

                @Override
                public void onPayloadApplicationComplete(final int errorCode) {
                    Log.d(TAG, "mUpdateEngine errorCode " + errorCode );
                    mCarUpdateCallback.onPayloadApplicationComplete(errorCode);
                }
            };
            try {
                mUpdateEngine.bind(mUpdateEngineCallback);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

    }


    private void applyPayload(String url, long offset, long size, String[] headerKeyValuePairs) {
        try {
            mUpdateEngine.applyPayload(url, offset, size, headerKeyValuePairs);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void writeCareMapToDevice(String careMapText) {
        try {
            File file = new File(CORE_AMP_PATH);
            File fileParent = file.getParentFile();
            if(!fileParent.exists()){
                fileParent.mkdirs();
            }
            file.createNewFile();
            FileWriter writer;
            writer = new FileWriter(CORE_AMP_PATH);
            if (careMapText == null) {
                writer.write(mUpdateParser.getCareMapText());
            } else {
                writer.write(careMapText);
            }
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isOtaFileExite(){
        boolean ret = false;
        final List<VolumeInfo> vols = mStorageManager.getVolumes();
        ArrayList<File> volumes = new ArrayList<>(vols.size());
        for (VolumeInfo vol : vols) {
            File path = vol.getPath();
            if (vol.getState() == VolumeInfo.STATE_MOUNTED
                    && vol.getType() == VolumeInfo.TYPE_PUBLIC
                    && path != null) {
                String  wayOsUpdateFilePath = path.getPath()+UPDATE_FILE_NAME;
                Log.d(TAG, "isOtaFileExite() -> wayOsUpdateFilePath = " + wayOsUpdateFilePath);
                if(isFileExists(wayOsUpdateFilePath)) {
                    mUpdateFile = new File( wayOsUpdateFilePath);
                    if (null != mUpdateFile) {
                        ret = true;
                    }

                }
            }
        }
        return ret;
    }

    public void usbUpdate() {
        try {
            mUnZipResult = mUpdateParser.parse(mUpdateFile);
            Log.d(TAG, " mUnZipResult.mOffset = " + mUnZipResult.mOffset + " mUnZipResult.mSize" + mUnZipResult.mSize);
            if (mUnZipResult.mOffset != 0 && mUnZipResult.mSize != 0 && mUnZipResult.mProps != null && mUnZipResult.mUrl != null) {
                applyPayload(mUnZipResult.mUrl, mUnZipResult.mOffset, mUnZipResult.mSize, mUnZipResult.mProps);
            }
        } catch (IOException e) {

        }
    }

    private boolean isFileExists(String strFile)  {
        boolean ret = false;
        try {
            File f = new File(strFile);
            if(f.exists()) {
                ret = true;
            }
        } catch (Exception e) {

        }
        return ret;
    }


    public void remoteUpdate(ParsedUpdate parseUpdate) {
        applyPayload(parseUpdate.mUrl, parseUpdate.mOffset, parseUpdate.mSize, parseUpdate.mProps);
    }

    /**
     * Permanently cancels an in-progress update.
     *
     * <p>See {@link #resetStatus} to undo a finshed update (only available
     * before the updated system has been rebooted).
     *
     * <p>See {@link #suspend} for a way to temporarily stop an in-progress
     * update with the ability to resume it later.
     */
    public void cancel() {
        try {
            mUpdateEngine.cancel();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    /**
     * Suspends an in-progress update. This can be undone by calling
     * {@link #resume}.
     */
    public void suspend() {
        try {
            mUpdateEngine.suspend();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    /**
     * Resumes a suspended update.
     */
    public void resume() {
        try {
            mUpdateEngine.resume();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    /**
     * Resets the bootable flag on the non-current partition and all internal
     * update_engine state. This can be used after an unwanted payload has been
     * successfully applied and the device has not yet been rebooted to signal
     * that we no longer want to boot into that updated system. After this call
     * completes, update_engine will no longer report
     * {@code UPDATED_NEED_REBOOT}, so your callback can remove any outstanding
     * notification that rebooting into the new system is possible.
     */
    public void resetStatus() {
        try {
            mUpdateEngine.resetStatus();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    /**
     * Unbinds the last bound callback function.
     */
    private boolean unbind() {
        synchronized (mUpdateEngineCallbackLock) {
            if (mUpdateEngineCallback == null) {
                return true;
            }
            try {
                boolean result = mUpdateEngine.unbind(mUpdateEngineCallback);
                mUpdateEngineCallback = null;
                return result;
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    /**
     * Verifies that a payload associated with the given payload metadata
     * {@code payloadMetadataFilename} can be safely applied to ths device.
     * Returns {@code true} if the update can successfully be applied and
     * returns {@code false} otherwise.
     *
     * @param payloadMetadataFilename the location of the metadata without the
     * {@code file://} prefix.
     */
    public boolean verifyPayloadMetadata(String payloadMetadataFilename) {
        try {
            return mUpdateEngine.verifyPayloadApplicable(payloadMetadataFilename);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }
}
