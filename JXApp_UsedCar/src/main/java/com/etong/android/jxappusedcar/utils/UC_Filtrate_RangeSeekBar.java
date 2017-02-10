package com.etong.android.jxappusedcar.utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.ViewGroup;

import com.etong.android.frame.utils.L;
import com.etong.android.jxappusedcar.R;


/**
 * 有范围刻度的SeekBar控件
 */
public class UC_Filtrate_RangeSeekBar extends ViewGroup {
    private Drawable mThumbDrawable;
    private Drawable mThumbPlaceDrawable;

    public UC_Filtrate_ThumbView mThumbLeft;   //左游标
    public UC_Filtrate_ThumbView mThumbRight;  //右游标
    private int mProgressBarHeight;     //进度条的高度
    private int mThumbPlaceHeight;      //游标的高度

    private int mMaxValue = 100;   //分成100份，每一小格占2份

    private int mLeftValue;     //左游标  数值    (100分之多少)   例如：1就是 1/100
    private int mRightValue = mMaxValue;  //右游标  数值    (100分之多少)

    public int mLeftLimit;     //游标左边的限制坐标
    public int mRightLimit;        //游标右边的限制坐标
    private int proPaddingLeftAndRight;     //进度条左右的padding 等于游标图标宽度
    private int mProBaseline;       //进度条top  坐标

    private static final int PART_ITEM = 5;//半小 占的分数
    private float mPartWidth;   //每一小份的宽度

    public static final int SHORTLINE_HEIGHT = 6; //短线的高度 （画刻度时会有长短线）
    public static final int LONGLINE_HEIGHT = 12; //长线的高度

    public static final int RULE_HEIGHT_DP = 25;  //尺子的高度  dp
    public static int RULE_HEIGHT_PX;

    private int degs[] = {0, 10, 20, 30, 40, 50, 60, 70, 80, 90, 100};      //尺子上标记刻度值
    private String unitStr = "";     //尺子标记单位

    private OnRangeChangeListener mOnRangeChangeListener;       //当左右任意一个游标改变时，回调接口

    private Context context;

    private OnThumbMoveListener mOnThumbMoveListener;

    public static int LEFT_RANGE_RIGHT = 50;

    private int CURRENT_LEFT = 0;                   // 供外部方法使用，设置当前的左右两边的值
    private int CURRENT_RIGHT = mMaxValue;

    public interface OnRangeChangeListener {
        public void onRangeChange(int leftValue, int rightValue);
    }

    public interface OnThumbMoveListener {
        public void onThumbMoveChange(int leftValue, int rightValue);
    }

    public UC_Filtrate_RangeSeekBar(Context context) {
        this(context, null);
        this.context = context;
    }

    public UC_Filtrate_RangeSeekBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        this.context = context;
    }

    public UC_Filtrate_RangeSeekBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;


        setBackgroundDrawable(new BitmapDrawable());
        //换算px
        RULE_HEIGHT_PX = UC_DensityUtil.dip2px(context, RULE_HEIGHT_DP);
        mProgressBarHeight = UC_DensityUtil.dip2px(context, 4);

        mThumbDrawable = getResources().getDrawable(R.drawable.used_car_filtrate_rod_handshank_butten);
//        mThumbPlaceDrawable=getResources().getDrawable(R.drawable.rod_place_icon);

        mThumbPlaceHeight = mThumbDrawable.getIntrinsicHeight();
        mProBaseline = RULE_HEIGHT_PX;

        mThumbLeft = new UC_Filtrate_ThumbView(getContext());
        mThumbLeft.setRangeSeekBar(this);
        mThumbLeft.setImageDrawable(mThumbDrawable);
        mThumbRight = new UC_Filtrate_ThumbView(getContext());
        mThumbRight.setRangeSeekBar(this);
        mThumbRight.setImageDrawable(mThumbDrawable);

        //measureView(mThumbLeft);

        addView(mThumbLeft);
        addView(mThumbRight);
        mThumbLeft.setOnThumbListener(new UC_Filtrate_ThumbView.OnThumbListener() {
            @Override
            public void onThumbChange(int i) {
                mLeftValue = i;
                if (mOnRangeChangeListener != null) {
                    mOnRangeChangeListener.onRangeChange(mLeftValue, mRightValue);
                }
            }
        });
        mThumbRight.setOnThumbListener(new UC_Filtrate_ThumbView.OnThumbListener() {
            @Override
            public void onThumbChange(int i) {
                mLeftValue = i;
                if (mOnRangeChangeListener != null) {
                    mOnRangeChangeListener.onRangeChange(mLeftValue, mRightValue);
                }
            }
        });


        mThumbLeft.setOnThumbUpListener(new UC_Filtrate_ThumbView.OnThumbUpListener() {
            @Override
            public void onThumbUpChange(int i) {
                if (mOnThumbMoveListener != null) {
                    mOnThumbMoveListener.onThumbMoveChange(geneareThumbValue(mThumbLeft), geneareThumbValue(mThumbRight));
                }
            }
        });

        mThumbRight.setOnThumbUpListener(new UC_Filtrate_ThumbView.OnThumbUpListener() {
            @Override
            public void onThumbUpChange(int i) {
                if (mOnThumbMoveListener != null) {
                    mOnThumbMoveListener.onThumbMoveChange(geneareThumbValue(mThumbLeft), geneareThumbValue(mThumbRight));
                }
            }
        });

    }

    public void setOnThumbMoveListener(OnThumbMoveListener mOnThumbMoveListener) {
        this.mOnThumbMoveListener = mOnThumbMoveListener;
    }

    public void setOnRangeChangeListener(OnRangeChangeListener mOnRangeChangeListener) {
        this.mOnRangeChangeListener = mOnRangeChangeListener;
    }

//    private void measureView(View view){
//        ViewGroup.LayoutParams params=view.getLayoutParams();
//
//        if(params==null){
//            params=new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        }
//
//        int widthSpec=ViewGroup.getChildMeasureSpec(0,0,params.width);
//
//        int heightSpec;
//        if(params.height>0){
//            heightSpec=MeasureSpec.makeMeasureSpec(params.height,MeasureSpec.EXACTLY);
//        }else{
//            heightSpec=MeasureSpec.makeMeasureSpec(params.height,MeasureSpec.UNSPECIFIED);
//        }
//
//        view.measure(widthSpec,heightSpec);
//    }

    /**
     * 画尺子
     *
     * @param canvas
     */
    protected void drawProgressBar(Canvas canvas) {
        //画背景
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(getResources().getColor(R.color.used_car_909AAB));
        Rect rect = new Rect(mLeftLimit, mProBaseline, mRightLimit, mProBaseline + mProgressBarHeight);
        canvas.drawRect(rect, paint);

        //画进度
        paint.setColor(getResources().getColor(R.color.used_car_10a6e2));
        rect = new Rect(mThumbLeft.getCenterX(), mProBaseline, mThumbRight.getCenterX(), mProBaseline + mProgressBarHeight);
        canvas.drawRect(rect, paint);
    }

    /**
     * 画刻度尺
     *
     * @param canvas
     */
    protected void drawRule(Canvas canvas) {
        Paint paint = new Paint();
        paint.setStrokeWidth(1);
        paint.setColor(getResources().getColor(R.color.used_car_909AAB));
        paint.setTextSize(20);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setAntiAlias(true);

        //一次遍历两份,绘制的位置都是在10的倍数位置
        for (int i = 0; i <= mMaxValue; i += 2) {

//            if(i<PART_ITEM||i>mMaxValue-PART_ITEM){
//                continue;
//            }

            float degX = mLeftLimit + i * mPartWidth;
            int degY;
            int endY;

            if (i % (PART_ITEM * 2) == 0) {
                degY = mProBaseline - ((UC_DensityUtil.dip2px(getContext(), LONGLINE_HEIGHT) + mProgressBarHeight) / 2);
                endY = mProBaseline + ((UC_DensityUtil.dip2px(getContext(), LONGLINE_HEIGHT) + mProgressBarHeight) / 2) + mProgressBarHeight;
                canvas.drawText(degs[i / 10] + unitStr, degX, degY, paint);
            } else {
                degY = mProBaseline - ((UC_DensityUtil.dip2px(getContext(), SHORTLINE_HEIGHT) + mProgressBarHeight) / 2);
                endY = mProBaseline + ((UC_DensityUtil.dip2px(getContext(), SHORTLINE_HEIGHT) + mProgressBarHeight) / 2) + mProgressBarHeight;
            }
            canvas.drawLine(degX, endY, degX, degY, paint);
        }
    }

    /**
     * 画 Thumb 位置的数值
     */
//    protected void drawRodPlaceValue(Canvas canvas,ThumbView thumbView){
//        int centerX=thumbView.getCenterX();
//        Paint paint=new Paint();
//        BitmapDrawable bd= (BitmapDrawable) mThumbPlaceDrawable;
//        canvas.drawBitmap(bd.getBitmap(),centerX-mThumbPlaceDrawable.getIntrinsicWidth()/2,0,paint);
//
//        paint.setColor(Color.WHITE);
//        paint.setTextAlign(Paint.Align.CENTER);
//        paint.setTextSize(30);
//        canvas.drawText(geneareThumbValue(thumbView)+"",centerX,mThumbDrawable.getIntrinsicHeight()/2,paint);
//    }
    public void reSet() {
        onLayoutPrepared();
    }

    public int[] getValue() {
        int[] value = new int[2];
        value[0] = geneareThumbValue(mThumbLeft);
        value[1] = geneareThumbValue(mThumbRight);
        return value;

    }

    //onLayout调用后执行的函数
    private void onLayoutPrepared() {
//        mThumbLeft.setCenterX(mLeftLimit);
//        mThumbRight.setCenterX(mRightLimit);

        int i = (CURRENT_LEFT * (mRightLimit - mLeftLimit)) / mMaxValue + mLeftLimit;
        int j = (CURRENT_RIGHT * (mRightLimit - mLeftLimit)) / mMaxValue + mLeftLimit;

        mThumbLeft.setCenterX(i);
        mThumbRight.setCenterX(j);
    }

    private int geneareThumbValue(UC_Filtrate_ThumbView view) {
        //todo 这里只是计算了100之多少的值，需要自行转换成刻度上的值
        int proValue = mMaxValue * (view.getCenterX() - mLeftLimit) / (mRightLimit - mLeftLimit);
        return proValue;
    }

    /**
     * @desc (设置最大值)
     * @createtime 2016/11/25 0025-9:53
     * @author wukefan
     */
    public void setIntervalValue(int mMaxValue) {
        if (mMaxValue < 0) {
            throw new IllegalArgumentException(
                    "Space between text mark and seekbar can not less than 0!");
        }
        this.mMaxValue = mMaxValue;

        requestLayout();
        invalidate();
    }

    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        measureChildren(widthMeasureSpec, heightMeasureSpec);    //测量子控件
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int mWidth = MeasureSpec.getSize(widthMeasureSpec);
        proPaddingLeftAndRight = mThumbLeft.getMeasuredWidth() / 2;
        mLeftLimit = proPaddingLeftAndRight;
        mRightLimit = mWidth - proPaddingLeftAndRight;

        int height = UC_DensityUtil.dip2px(context, 8);
        //尺子的刻度高度+尺子的高度+游标的高度
        setMeasuredDimension(mWidth, height + RULE_HEIGHT_PX + mProgressBarHeight + mThumbPlaceHeight);
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawProgressBar(canvas);
        drawRule(canvas);

//        if(mThumbLeft.isMoving()){
//            mOnThumbMoveListener.onThumbMoveChange(geneareThumbValue(mThumbLeft),geneareThumbValue(mThumbRight));
////            drawRodPlaceValue(canvas,mThumbLeft);
//        }else if(mThumbRight.isMoving()){
//            mOnThumbMoveListener.onThumbMoveChange(geneareThumbValue(mThumbLeft),geneareThumbValue(mThumbRight));
////            drawRodPlaceValue(canvas,mThumbRight);
//        }

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int heightSum = 0;

//        heightSum+=mThumbPlaceHeight;
//
//        heightSum+=RULE_HEIGHT_PX;

//        heightSum+=mProgressBarHeight;

        mPartWidth = (mRightLimit - mLeftLimit) / (float) mMaxValue;   //计算一份所占的宽度  一定要用float
        L.d("设置的范围为：", mLeftLimit + "--" + mRightLimit);
        mThumbLeft.setLimit(mLeftLimit, mRightLimit - UC_Filtrate_RangeSeekBar.LEFT_RANGE_RIGHT);    //设置可以移动的范围
//        mThumbLeft.setLimit(mLeftLimit, mThumbRight.getCenterX());
        mThumbLeft.layout(0, heightSum, mThumbLeft.getMeasuredWidth(), b - 10);      //设置在父布局的位置

        mThumbRight.setLimit(mLeftLimit + UC_Filtrate_RangeSeekBar.LEFT_RANGE_RIGHT, mRightLimit);
//        mThumbLeft.setLimit(mThumbLeft.getCenterX(), mRightLimit);
        mThumbRight.layout(0, heightSum, mThumbLeft.getMeasuredWidth(), b - 10);

        onLayoutPrepared();     //layout调用后调用的方法，比如设置thumb limit
    }

    /**
     * @desc (供外部类调用，设置当前左右两边的value)
     * @createtime 2016/11/25 0025-17:57
     * @author wukefan
     */
    public void setCurrentValue(int leftValue, int rightValue) {
        if ((leftValue<0 && leftValue>rightValue || leftValue>mMaxValue)
                || (rightValue<0 && rightValue>rightValue || rightValue<leftValue)) {
            throw new IllegalArgumentException(
                    "leftValue or rightValue is Illega!");
        }

        if (rightValue == 0) {
            rightValue = mMaxValue;
        }
        CURRENT_LEFT = leftValue;
        CURRENT_RIGHT = rightValue;

    }
}
