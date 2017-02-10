package com.etong.android.frame.widget.photoview;

import android.view.MotionEvent;

/**
 * @author : by sunyao
 * @ClassName : Frame_RotateGestureDetector
 * @Description : (这里用一句话描述这个类的作用)
 * @date : 2016/10/11 - 18:34
 */

public class Frame_RotateGestureDetector {

    private static final int MAX_DEGREES_STEP = 120;

    private Frame_OnRotateListener mListener;

    private float mPrevSlope;
    private float mCurrSlope;

    private float x1;
    private float y1;
    private float x2;
    private float y2;

    public Frame_RotateGestureDetector(Frame_OnRotateListener l) {
        mListener = l;
    }

    public void onTouchEvent(MotionEvent event) {

        final int Action = event.getActionMasked();

        switch (Action) {
            case MotionEvent.ACTION_POINTER_DOWN:
            case MotionEvent.ACTION_POINTER_UP:
                if (event.getPointerCount() == 2) mPrevSlope = caculateSlope(event);
                break;
            case MotionEvent.ACTION_MOVE:
                if (event.getPointerCount() > 1) {
                    mCurrSlope = caculateSlope(event);

                    double currDegrees = Math.toDegrees(Math.atan(mCurrSlope));
                    double prevDegrees = Math.toDegrees(Math.atan(mPrevSlope));

                    double deltaSlope = currDegrees - prevDegrees;

                    if (Math.abs(deltaSlope) <= MAX_DEGREES_STEP) {
                        mListener.onRotate((float) deltaSlope, (x2 + x1) / 2, (y2 + y1) / 2);
                    }
                    mPrevSlope = mCurrSlope;
                }
                break;
            default:
                break;
        }
    }

    private float caculateSlope(MotionEvent event) {
        x1 = event.getX(0);
        y1 = event.getY(0);
        x2 = event.getX(1);
        y2 = event.getY(1);
        return (y2 - y1) / (x2 - x1);
    }
}
