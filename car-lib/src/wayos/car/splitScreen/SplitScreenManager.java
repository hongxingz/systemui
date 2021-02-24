package wayos.car.splitScreen;

import android.view.GestureDetector;
import android.app.ActivityManager;
import android.app.ActivityOptions;
import android.app.IActivityManager;
import android.app.WindowConfiguration;
import android.app.ActivityOptions;
import android.content.ComponentName;
import android.content.Context;
import android.view.WindowManager;
import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.os.RemoteException;
import android.app.TaskStackListener;
import android.content.Intent;
import android.os.UserHandle;
import java.util.Iterator;
import java.util.List;
import android.view.View.OnTouchListener;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import static android.app.ActivityManager.SPLIT_SCREEN_CREATE_MODE_TOP_OR_LEFT;
import android.graphics.PixelFormat;
import wayos.car.view.AiwaysGestureManager;
import android.view.ScaleGestureDetector;
import wayos.car.view.AiwaysGestureManager.GestureEvent;
import java.util.Arrays;
import android.os.ServiceManager;
import android.util.Singleton;
import android.os.IBinder;


public class SplitScreenManager{
    private final String TAG                                  = "SplitScreenManager";
    private final String LEFT_SCREEN_PKG_NAME                 = "com.android.car.mapsplaceholder";
    private final String LEFT_SCREEN_ACT_NAME                 = "com.android.car.mapsplaceholder.MapsPlaceholderActivity";
    private final String HOME_PKG_NAME                        = "com.aiways.launcher";
    private final String HOME_ACT_NAME                        = "com.aiways.carlauncher.ui.CarLauncherActivity";
    private final int RECENT_TOTAL_TASK_NUM                   = 30;
    private final int CFG_RIGHT_WIN_WIDTH                     = 640;
    private Context mContext                                  = null;
    private IActivityManager mAm                              = null;
    private ActivityManager.RunningTaskInfo mCurrentTask      = null;
    private Rect mSplitLeftRect                               = null;
    private WindowManager mWinMgr                             = null;
    private AiwaysGestureManager mGd                          = null;
    private boolean isSplitScreen                             = false;
    private LinearLayout mTouchLayout                         = null;
    private static SplitScreenManager mInstance               = null;
    // private BottomButton mBottomButton                        = null;
    private int primaryStackId                                = -1;
    private int primaryTaskId                                 = -1;
    private SplitScreenManager() {
        Log.d(TAG, "SplitScreenManager getInstance()");
    }

    public static SplitScreenManager getInstance() {
        if (null != mInstance) {
            return mInstance;
        }
        return mInstance = new SplitScreenManager();
    }

    public void init(Context context) {
        mAm = ActivityManager.getService();
        mWinMgr = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Point point = new Point();
        mWinMgr.getDefaultDisplay().getSize(point);
        mSplitLeftRect = new Rect(0, 0, CFG_RIGHT_WIN_WIDTH, point.y);

        mGd = new AiwaysGestureManager(context, mGestureListener);
        mContext = context;

        try {
            mAm.registerTaskStackListener(mTaskStackListener);    
        } catch (Exception exception) {
            Log.d(TAG, "ActivityManager register failed");
            exception.printStackTrace();
        }
        // createBottomButton();
    }

    // private void createBottomButton() {
    //     mBottomButton = new BottomButton(mContext);
    //     int flags =  WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
    //                         | WindowManager.LayoutParams.FLAG_TOUCHABLE_WHEN_WAKING
    //                         | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH
    //                         | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
    //                         | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS;

    //     WindowManager.LayoutParams wmParams = new WindowManager.LayoutParams(400, 50, 0, 20,
    //             WindowManager.LayoutParams.TYPE_STATUS_BAR_PANEL,
    //             flags, PixelFormat.TRANSLUCENT);

    //     wmParams.gravity = Gravity.BOTTOM;
    //     mWinMgr.addView(mBottomButton, wmParams);
    // }

    public AiwaysGestureManager getGestureDetector() {
        return mGd;
    }



    private AiwaysGestureManager.AiwaysGestureListener mGestureListener = new GestureListener();
    private class GestureListener implements AiwaysGestureManager.AiwaysGestureListener {
        @Override
        public boolean onDown(MotionEvent motionEvent) {
            return true;
        }

        public boolean onSingleTapUp(MotionEvent motionEvent){
            return false;
        }

        public boolean onDoubleTap(MotionEvent motionEvent){
            return false;
        }

        public void onLongPress(MotionEvent e){
            return;
        }


        public boolean onUp(MotionEvent motionEvent){
            return false;
        }

        public boolean onScroll(MotionEvent e1, MotionEvent e2, float dx, float dy){
            return false;
        }

        public boolean onScale(ScaleGestureDetector detector){
            return false;

        }

        public boolean onScaleBegin(ScaleGestureDetector detector){
            return false;

        }

        public void onScaleEnd(ScaleGestureDetector detector){
            return;

        }
        public boolean singleFingeronSlipProcess(MotionEvent e1, MotionEvent e2, float dx, float dy) {
            return false;
        }
        public boolean singleFingerSlipAction(GestureEvent gestureEvent, MotionEvent startEvent, MotionEvent endEvent, float velocity) {
            if (mCurrentTask.resizeMode == ActivityInfo.RESIZE_MODE_UNRESIZEABLE) {
                startHomeActivity();
                return true;
            }
            return false;
        }
        public boolean mutiFingerSlipProcess(GestureEvent gestureEvent, float startX, float startY, float endX, float endY, float moveX, float moveY) {
            return false;
        }
        public boolean mutiFingerSlipAction(GestureEvent gestureEvent, float startX, float startY, float endX, float endY, float velocityX,float velocityY) {
            return false;
        }

    };

    private TaskStackListener mTaskStackListener =
            new TaskStackListener() {
        @Override
        public void onTaskStackChanged() {
            ActivityManager.StackInfo topStack = getTopStackInfo();
            Log.d(TAG, "onTaskStackChanged topStack name: " + topStack.topActivity.toString());
            if (topStack != null) {
                mCurrentTask = getTaskInfoByActivityName(topStack.topActivity);
                if (mCurrentTask != null) {
                    if (mCurrentTask.resizeMode != ActivityInfo.RESIZE_MODE_UNRESIZEABLE) {
                            showSplitScreen();
                    }
                }
            }
        }

        @Override
        public void onTaskCreated(int taskId, ComponentName componentName) {
            Log.d(TAG, "onTaskCreated taskId: " + taskId);
            Log.d(TAG, "onTaskCreated componentName: " + componentName.toString());
            mCurrentTask = getTaskInfoByTaskId(taskId);
            if (mCurrentTask != null) {
                if (mCurrentTask.resizeMode != ActivityInfo.RESIZE_MODE_UNRESIZEABLE) {
                        showSplitScreen();
                }
            }
        }

        @Override
        public void onTaskMovedToFront(int taskId) {
            Log.d(TAG, "onTaskMovedToFront");
        }

        public void onActivityForcedResizable(String packageName, int taskId, int reason) {
            Log.d(TAG, "onActivityForcedResizable -> packageName:" + packageName);
        }

        @Override
        public void onActivityDismissingDockedStack() {
            hideSplitScreen();
        }

        @Override
        public void onActivityLaunchOnSecondaryDisplayFailed() {
        }
    };

    public void startPrimaryScreenActivty() {
        ActivityOptions options = ActivityOptions.makeBasic();
        Intent intent = new Intent();
        intent.setClassName(LEFT_SCREEN_PKG_NAME, LEFT_SCREEN_ACT_NAME);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivityAsUser(intent, options.toBundle(), UserHandle.CURRENT);
    }

    private void startHomeActivity(){
        ActivityOptions options = ActivityOptions.makeBasic();
        Intent intent = new Intent();
        intent.setClassName(HOME_PKG_NAME, HOME_ACT_NAME);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivityAsUser(intent, options.toBundle(), UserHandle.CURRENT);
    }

    private ActivityManager.RunningTaskInfo getTaskInfoByTaskId(int taskId) {
        try {
            List<ActivityManager.RunningTaskInfo> tasks =
                    mAm.getFilteredTasks(RECENT_TOTAL_TASK_NUM, WindowConfiguration.ACTIVITY_TYPE_RECENTS,
                            WindowConfiguration.WINDOWING_MODE_PINNED);

            if (tasks.isEmpty()) {
                return null;
            }

            for (int i = 0; i < tasks.size(); i++) {
                if (taskId == tasks.get(i).id) {
                    return tasks.get(i);
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return null;
    }

    private ActivityManager.RunningTaskInfo getTaskInfoByActivityName(ComponentName topActivity) {
        try {
            List<ActivityManager.RunningTaskInfo> tasks =
                    mAm.getFilteredTasks(RECENT_TOTAL_TASK_NUM, WindowConfiguration.ACTIVITY_TYPE_RECENTS,
                            WindowConfiguration.WINDOWING_MODE_PINNED);

            if (tasks.isEmpty()) {
                return null;
            }

            for (int i = 0; i < tasks.size(); i++) {
                if (topActivity.equals(tasks.get(i).topActivity)) {
                    return tasks.get(i);
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return null;
    }

    private ActivityManager.StackInfo getTopStackInfo() {
        try {
            List<ActivityManager.StackInfo> stacks = mAm.getAllStackInfos();
            if (stacks.size() > 0) {
                return stacks.get(0);
            }
        } catch (RemoteException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    private void getPrimaryScreenStackId() {
        try {
            List<ActivityManager.StackInfo> stacks = mAm.getAllStackInfos();
            for (int i = stacks.size() -1; i >= 0; i--) {
                for (int j = stacks.get(i).taskNames.length - 1; j >= 0; j--)
                if(Arrays.asList(stacks.get(i).taskNames).contains(LEFT_SCREEN_PKG_NAME + "/" + LEFT_SCREEN_ACT_NAME)) {
                    Log.d(TAG, "taskName:" + stacks.get(i).taskNames[j] + " taskId:" + stacks.get(i).taskIds[j]);
                    primaryStackId = stacks.get(i).stackId;
                    primaryTaskId = stacks.get(i).taskIds[j];
                }
            }
        } catch (RemoteException ex) {
            ex.printStackTrace();
        }
    }

    private boolean showSplitScreen() {
        Log.d(TAG, "Show the left Window, isSplitScreen: " + isSplitScreen);
        try {
            if (!isSplitScreen && mAm != null) {
                startPrimaryScreenActivty();
                getPrimaryScreenStackId();
                if (primaryStackId > -1 && primaryTaskId > -1) {
                    mAm.setTaskWindowingModeSplitScreenPrimary(primaryTaskId,
                        SPLIT_SCREEN_CREATE_MODE_TOP_OR_LEFT, true, true, mSplitLeftRect, false);

                    mAm.resizeStack(primaryStackId, mSplitLeftRect,
                        false, false, false, -1);

                    isSplitScreen = true;
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            return false;
        }
        return true;
    }


    private boolean hideSplitScreen() {
        Log.d(TAG, "Hide the left Window, isSplitScreen: " + isSplitScreen);
        try {
            if (isSplitScreen && mAm != null) {
                mAm.dismissSplitScreenMode(false);
                isSplitScreen = false;
                primaryStackId = -1;
                primaryTaskId = -1;
            }
        } catch (Exception exception) {
            Log.e(TAG, "dismiss SplitScreen Mode failed");
            exception.printStackTrace();
            return false;
        }
        return true;
    }
}