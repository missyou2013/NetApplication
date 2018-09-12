package com.kernal.smartvision.ocr;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.hardware.Camera;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.kernal.smartvision.inteface.FocusIndicator;
import com.kernal.smartvision.view.FocusIndicatorView;
import com.kernal.smartvisionocr.utils.Utills;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

@SuppressLint("NewApi") public class FocusManager {
    private static Camera mCamera = null;
    private Matrix mMatrix;
    private View mFocusIndicatorRotateLayout;
    public FocusIndicatorView mFocusIndicator;
    private int mState = STATE_IDLE;
    private static final int STATE_IDLE = 0; // Focus is not active.
    private static final int STATE_FOCUSING = 1; // Focus is in progress.
    // Focus is in progress and the camera should take a picture after focus finishes.
    private static final int STATE_FOCUSING_SNAP_ON_FINISH = 2;
    private static final int STATE_SUCCESS = 3; // Focus finishes and succeeds.
    private static final int STATE_FAIL = 4; // Focus finishes and fails.
    private List<Camera.Area> focusAreas;
    private View previewFrame;
    private List<Camera.Area> meteringAreas;
    private boolean isInit = true;
    private int focusBoxWidth=180;
    private Timer time = new Timer();
    private TimerTask timer;
    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mFocusIndicator.clear();
            if (timer != null) {
                timer.cancel();
                timer = null;
            }
        }
    };
    private Message msg;
    public FocusManager(View focusIndicatorRotate, View previewFrame, int srcWidth, int srcHeight) {
        focusBoxWidth=srcWidth/10;
        mMatrix = new Matrix();
        mFocusIndicatorRotateLayout = focusIndicatorRotate;

//        mFocusIndicator = (FocusIndicatorView) focusIndicatorRotate.findViewById(
//        		context.getResources().getIdentifier("focus_indicator",
//                        "id", context.getPackageName()));
        Matrix matrix = new Matrix();
        prepareMatrix(matrix, false, 0,
                previewFrame.getWidth(), previewFrame.getHeight());
        // In face detection, the matrix converts the driver coordinates to UI
        // coordinates. In tap focus, the inverted matrix converts the UI
        // coordinates to driver coordinates.
        matrix.invert(mMatrix);
        mState = 5;
        updateFocusUI(previewFrame);
        isInit = false;
        //绘出初始化对焦框
        RelativeLayout.LayoutParams params=new RelativeLayout.LayoutParams(focusBoxWidth
                ,focusBoxWidth);
        params.leftMargin=(srcWidth/2-focusBoxWidth/2);
        params.topMargin=(srcHeight/2-focusBoxWidth/2);
        mFocusIndicator.setLayoutParams(params);
        mFocusIndicator.showSuccess();
        if(timer==null){
            timer = new TimerTask() {
                public void run() {
                    msg = new Message();
                    handler.sendMessage(msg);
                }
            };
        }
        if( Utills.isCPUInfo64()){
            time.schedule(timer, 1000);
        }else{
            time.schedule(timer, 2000);
        }

    }

    public void onTouch(MotionEvent event, Camera.AutoFocusCallback mAutoFocusCallback, Camera camera) {
        mCamera = camera;
        mState = 0;
        mCamera.cancelAutoFocus();
        onFocus(mAutoFocusCallback, event);
    }

    /**
     * 手动聚焦
     */
    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH) @SuppressLint("NewApi") protected void onFocus(Camera.AutoFocusCallback callback, MotionEvent event) {
        mFocusIndicator.showStart();
        Camera.Parameters parameters = mCamera.getParameters();
        //不支持设置自定义聚焦，则使用自动聚焦，返回
        if (parameters.getMaxNumFocusAreas() <= 0) {
            mCamera.autoFocus(callback);
            return;
        }
        int focusWidth = mFocusIndicatorRotateLayout.getWidth();
        int focusHeight = mFocusIndicatorRotateLayout.getHeight();
        Rect focusRect = calculateTapArea(event.getRawX(), event.getRawY(), 1f);
        Rect meteringRect = calculateTapArea(event.getRawX(), event.getRawY(), 1.5f);
        parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);

        if (parameters.getMaxNumFocusAreas() > 0) {
            focusAreas = new ArrayList<Camera.Area>();
            focusAreas.add(new Camera.Area(focusRect, 1000));
            parameters.setFocusAreas(focusAreas);
        }

        if (parameters.getMaxNumMeteringAreas() > 0) {
            meteringAreas = new ArrayList<Camera.Area>();
            meteringAreas.add(new Camera.Area(meteringRect, 1000));

            parameters.setMeteringAreas(meteringAreas);
        }
        try {
            mCamera.setParameters(parameters);
        } catch (Exception e) {

        }
        mCamera.autoFocus(callback);
        //绘出对焦框
        RelativeLayout.LayoutParams params=new RelativeLayout.LayoutParams(focusBoxWidth
                ,focusBoxWidth);
        params.leftMargin=(int)(event.getRawX()-focusBoxWidth/2);
        params.topMargin=(int)(event.getRawY()-focusBoxWidth/2);
        mFocusIndicator.setLayoutParams(params);
    }

    /**
     * Convert touch position x:y to {@link Camera.Area} position -1000:-1000 to 1000:1000.
     */
    private Rect calculateTapArea(float x, float y, float coefficient) {
        float focusAreaSize = 300;
        int areaSize = Float.valueOf(focusAreaSize * coefficient).intValue();

        int centerX = (int) (x / getResolution().width * 2000 - 1000);
        int centerY = (int) (y / getResolution().height * 2000 - 1000);

        int left = clamp(centerX - areaSize / 2, -1000, 1000);
        int right = clamp(left + areaSize, -1000, 1000);
        int top = clamp(centerY - areaSize / 2, -1000, 1000);
        int bottom = clamp(top + areaSize, -1000, 1000);
       // System.out.println("left:" + left + "---top" + top + "---right:" + right + "---bottom:" + bottom);
        return new Rect(left, top, right, bottom);
    }

    private int clamp(int x, int min, int max) {
        if (x > max) {
            return max;
        }
        if (x < min) {
            return min;
        }
        return x;
    }

    public Camera.Size getResolution() {
        Camera.Parameters params = mCamera.getParameters();
        Camera.Size s = params.getPreviewSize();
        return s;
    }

    public static void rectFToRect(RectF rectF, Rect rect) {
        rect.left = Math.round(rectF.left);
        rect.top = Math.round(rectF.top);
        rect.right = Math.round(rectF.right);
        rect.bottom = Math.round(rectF.bottom);
    }

    public static void prepareMatrix(Matrix matrix, boolean mirror, int displayOrientation,
                                     int viewWidth, int viewHeight) {
        // Need mirror for front camera.
        matrix.setScale(mirror ? -1 : 1, 1);
        // This is the value for android.hardware.Camera.setDisplayOrientation.
        matrix.postRotate(displayOrientation);
        // Camera driver coordinates range from (-1000, -1000) to (1000, 1000).
        // UI coordinates range from (0, 0) to (width, height).
        matrix.postScale(viewWidth / 2000f, viewHeight / 2000f);
        matrix.postTranslate(viewWidth / 2f, viewHeight / 2f);
    }

    public void updateFocusUI(View mPreviewFrame) {

        // Set the length of focus indicator according to preview frame size.
        int len = Math.min(mPreviewFrame.getWidth(), mPreviewFrame.getHeight()) / 4;
        ViewGroup.LayoutParams layout = mFocusIndicator.getLayoutParams();
        layout.width = len;
        layout.height = len;
        FocusIndicator focusIndicator = mFocusIndicator;

        if (mState == STATE_IDLE) {
            if (focusAreas == null) {
                focusIndicator.clear();
            } else {
                // Users touch on the preview and the indicator represents the
                // metering area. Either focus area is not supported or
                // autoFocus call is not required.
                focusIndicator.showStart();
            }
        } else if (mState == STATE_FOCUSING || mState == STATE_FOCUSING_SNAP_ON_FINISH) {
            focusIndicator.showStart();
        } else {
            // In CAF, do not show success or failure because it only returns
            // the focus status. It does not do a full scan. So the result is
            // failure most of the time.
            focusIndicator.showStart();
            if (mState == STATE_SUCCESS) {
                focusIndicator.showSuccess();
            } else if (mState == STATE_FAIL) {
                focusIndicator.showFail();
            }
        }
    }

    public void onShutter() {
        resetTouchFocus();
        updateFocusUI(previewFrame);
    }

    public void resetTouchFocus() {
        // Put focus indicator to the center.
        RelativeLayout.LayoutParams p =
                (RelativeLayout.LayoutParams) mFocusIndicatorRotateLayout.getLayoutParams();
        int[] rules = p.getRules();
        rules[RelativeLayout.CENTER_IN_PARENT] = RelativeLayout.TRUE;
        p.setMargins(0, 0, 0, 0);
        focusAreas = null;
        meteringAreas = null;
    }
}
