package com.etong.android.jxappusedcar.utils;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ImageView;

import com.etong.android.frame.utils.L;

/**
 * 有范围刻度的SeekBar控件中的游标视图
 */
public class UC_Filtrate_ThumbView extends ImageView implements GestureDetector.OnGestureListener{

    private UC_Filtrate_RangeSeekBar rangeSeekBar;

    private int temp;

    private int mDownX=0;
    private int mWidth;

    private int mLeftLimit=0;
    private int mRightLimit=Integer.MAX_VALUE;

    private Rect rect;

    private int mCenterX;   //游标的中心位置

    private boolean mIsMoving;      //游标是否正在移动

    private OnThumbListener listener;
    private OnThumbUpListener upListener;

    // 添加手势判断
    private Context mContext;
    private GestureDetector gesture = new GestureDetector(mContext, this);

    public UC_Filtrate_ThumbView(Context context) {
        this(context, null);
        this.mContext = context;
    }

    public UC_Filtrate_ThumbView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        this.mContext = context;
    }

    public UC_Filtrate_ThumbView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
    }

    public void setRangeSeekBar(UC_Filtrate_RangeSeekBar rangeSeekBar) {
        this.rangeSeekBar = rangeSeekBar;
    }

    public void setLimit(int mLeftLimit,int mRightLimit) {
        this.mLeftLimit = mLeftLimit;
        this.mRightLimit=mRightLimit;
    }

    public int getCenterX() {
        return mCenterX;
    }

    /**
     * 设置中心位置，不超过左右的limit，就刷新整个控件，并且回调onThumbChange()
     * @param centerX
     */
    public void setCenterX(int centerX) {
        int left=centerX-mWidth/2,  right=centerX+mWidth/2;
        if(centerX<mLeftLimit) {
            left=mLeftLimit-mWidth/2;
            right=mLeftLimit+mWidth/2;
        }

        if(centerX>mRightLimit){
            left=mRightLimit-mWidth/2;
            right=mRightLimit+mWidth/2;
        }

        this.mCenterX = (left+right)/2;

        if(left!=rect.left||right!=rect.right){
            rect.union(left,rect.top,right,rect.bottom);
            layout(left, rect.top, right, rect.bottom);
            //invalidate(rect);
            rangeSeekBar.invalidate();

            if(listener!=null){
                listener.onThumbChange(100*((left+right)/2-mLeftLimit)/(mRightLimit-mLeftLimit));
                temp=100*((left+right)/2-mLeftLimit)/(mRightLimit-mLeftLimit);
            }
        }
    }


    public boolean isMoving() {
        return mIsMoving;
    }


    public void setOnThumbListener(OnThumbListener listener) {
        this.listener = listener;
    }

    public void setOnThumbUpListener(OnThumbUpListener upListener) {
        this.upListener = upListener;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        mWidth=getMeasuredWidth();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        rect=new Rect(left,top,right,bottom);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {



        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
//                setFocusable(true);
////                upListener.onThumbUpChange(temp);
//                mDownX = (int) event.getX();
//                L.d("mDownX = ", mDownX+"");
//                mIsMoving=false;
//                // 如果按下的位置在垂直方向没有与图片接触，则不会滑动滑块
//                float yPos = event.getY();
//                if (Math.abs(yPos - lineHeight) > bmpHeight / 2) {
//                    return false;
//                }
                break;
            case MotionEvent.ACTION_MOVE:

                int nowX = (int) event.getX();
                int left = rect.left + nowX - mDownX;
                int right = rect.right + nowX - mDownX;
//L.d("left ","mDownX = "+mDownX +  "   nowX = "+nowX+"  rect.left="+rect.left+"   left="+left );
//L.d("right ","mDownX = "+mDownX +  "   nowX = "+nowX+"  rect.right="+rect.right+"   right="+right );
                mIsMoving=true;
                setCenterX((left+right)/2);

                return true;
            case MotionEvent.ACTION_UP:
                setFocusable(true);
                listener.onThumbChange(temp);
                upListener.onThumbUpChange(temp);
                L.d("------------------UP>",temp+"");
                mIsMoving=false;
                rangeSeekBar.invalidate();
                return true;
        }

        gesture.onTouchEvent(event);
        return true;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if(gesture.onTouchEvent(event)){
            event.setAction(MotionEvent.ACTION_CANCEL);
        }
        return super.dispatchTouchEvent(event);
    }

    public interface OnThumbListener{
        public void onThumbChange(int i);
    }

    public interface OnThumbUpListener{
        public void onThumbUpChange(int i);
    }


    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        L.d("onFling-->", e1.toString()+"    "+e2.toString()+"    "+velocityX+"    "+velocityY);
        setFocusable(true);
        listener.onThumbChange(temp);
        upListener.onThumbUpChange(temp);
        L.d("------------------UP>",temp+"");
        mIsMoving=false;
        rangeSeekBar.invalidate();
        return true;
    }


    @Override
    public boolean onDown(MotionEvent e) {
        L.d("onDown-->", e.toString());
        setFocusable(true);
//                upListener.onThumbUpChange(temp);
        mDownX = (int) e.getX();
        L.d("mDownX = ", mDownX+"");
        mIsMoving=false;
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {
        L.d("onShowPress-->", e.toString());
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        L.d("onSingleTapUp-->", e.toString());
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
//        L.d("onScroll-->", e1.toString()+"    "+e2.toString()+"    "+distanceX+"    "+distanceY);
//        int nowX = (int) e2.getX();
//        int left = rect.left + nowX - mDownX;
//        int right = rect.right + nowX - mDownX;
////L.d("left ","mDownX = "+mDownX +  "   nowX = "+nowX+"  rect.left="+rect.left+"   left="+left );
////L.d("right ","mDownX = "+mDownX +  "   nowX = "+nowX+"  rect.right="+rect.right+"   right="+right );
//        mIsMoving=true;
//        setCenterX((left+right)/2);

        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        L.d("onLongPress-->", e.toString());
    }

}
