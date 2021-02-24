package wayos.car.media;

import android.os.Parcel;
import android.os.Parcelable;
import android.media.AudioAttributes;
import android.media.AudioFocusInfo;
import java.util.Arrays;
import android.annotation.SystemApi;
import java.util.Objects;
public class CarAudioFocusInfo implements Parcelable {

    private final AudioAttributes mAttributes;
    private final int mClientUid;
    private final String mClientId;
    private final String mPackageName;
    private final int mSdkTarget;
    private int mGainRequest;
    private int mLossReceived;
    private int mFlags;

    // generation count for the validity of a request/response async exchange between
    // external focus policy and MediaFocusControl
    private long mGenCount = -1;
    public CarAudioFocusInfo(AudioAttributes aa, int clientUid, String clientId, String packageName,
            int gainRequest, int lossReceived, int flags, int sdk) {
        mAttributes = aa == null ? new AudioAttributes.Builder().build() : aa;
        mClientUid = clientUid;
        mClientId = clientId == null ? "" : clientId;
        mPackageName = packageName == null ? "" : packageName;
        mGainRequest = gainRequest;
        mLossReceived = lossReceived;
        mFlags = flags;
        mSdkTarget = sdk;
    }

    @SystemApi
    public CarAudioFocusInfo (AudioFocusInfo info){
        mAttributes = info.getAttributes();
        mClientUid = info.getClientUid();
        mClientId =  info.getClientId();
        mPackageName = info.getPackageName();
        mGainRequest = info.getGainRequest();
        mLossReceived = info.getLossReceived();
        mFlags = info.getFlags();
        mSdkTarget = info.getSdkTarget();
    }

    /** @hide */
    public void setGen(long g) {
        mGenCount = g;
    }

    /** @hide */
    public long getGen() {
        return mGenCount;
    }


    /**
     * The audio attributes for the audio focus request.
     * @return non-null {@link AudioAttributes}.
     */
    public AudioAttributes getAttributes() { return mAttributes; }

    public int getClientUid() { return mClientUid; }

    public String getClientId() { return mClientId; }

    public String getPackageName() { return mPackageName; }

    /**
     * The type of audio focus gain request.
     * @return one of {@link AudioManager#AUDIOFOCUS_GAIN},
     *     {@link AudioManager#AUDIOFOCUS_GAIN_TRANSIENT},
     *     {@link AudioManager#AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK},
     *     {@link AudioManager#AUDIOFOCUS_GAIN_TRANSIENT_EXCLUSIVE}.
     */

    public int getGainRequest() { return mGainRequest; }

    /**
     * The type of audio focus loss that was received by the
     * {@link AudioManager.OnAudioFocusChangeListener} if one was set.
     * @return 0 if focus wasn't lost, or one of {@link AudioManager#AUDIOFOCUS_LOSS},
     *   {@link AudioManager#AUDIOFOCUS_LOSS_TRANSIENT} or
     *   {@link AudioManager#AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK}.
     */

    public int getLossReceived() { return mLossReceived; }


    /**
     * The flags set in the audio focus request.
     * @return 0 or a combination of {link AudioManager#AUDIOFOCUS_FLAG_DELAY_OK},
     *     {@link AudioManager#AUDIOFOCUS_FLAG_PAUSES_ON_DUCKABLE_LOSS}, and
     *     {@link AudioManager#AUDIOFOCUS_FLAG_LOCK}.
     */
    public int getFlags() { return mFlags; }

    @Override
    public int describeContents() {
        return 0;
    }



    @Override
    public void writeToParcel(Parcel dest, int flags) {
        mAttributes.writeToParcel(dest, flags);
        dest.writeInt(mClientUid);
        dest.writeString(mClientId);
        dest.writeString(mPackageName);
        dest.writeInt(mGainRequest);
        dest.writeInt(mLossReceived);
        dest.writeInt(mFlags);
        dest.writeInt(mSdkTarget);
        dest.writeLong(mGenCount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mAttributes, mClientUid, mClientId, mPackageName, mGainRequest, mFlags);
    }

    public static final Parcelable.Creator<CarAudioFocusInfo> CREATOR
            = new Parcelable.Creator<CarAudioFocusInfo>() {

        public CarAudioFocusInfo createFromParcel(Parcel in) {
            final CarAudioFocusInfo afi = new CarAudioFocusInfo(
                    AudioAttributes.CREATOR.createFromParcel(in), //AudioAttributes aa
                    in.readInt(), // int clientUid
                    in.readString(), //String clientId
                    in.readString(), //String packageName
                    in.readInt(), //int gainRequest
                    in.readInt(), //int lossReceived
                    in.readInt(), //int flags
                    in.readInt()  //int sdkTarget
                    );
            afi.setGen(in.readLong());
            return afi;
        }

        public CarAudioFocusInfo[] newArray(int size) {
            return new CarAudioFocusInfo[size];
        }
    };
}
