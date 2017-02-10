package com.etong.android.jxappfind.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.etong.android.jxappfind.R;


/**
 * @author : zhouxiqing
 * @ClassName : CountDownView
 * @Description : 自定义倒计时View,带背景
 * @date : 2015-11-27 下午4:08:42
 */
@SuppressLint("CutPasteId")
public class FindCountDownViews extends FrameLayout {
    public CountDownTimer timer = null;
    private TextView[] mViews = new TextView[8];
    public static final int DAY = 86400;
    public static final int HOUR = 3600;
    public static final int MIN = 60;
    public TimeCallback mTimeCallback;

    public FindCountDownViews(Context context) {
        super(context);

    }

    public FindCountDownViews(Context context, AttributeSet attrs) {
        super(context, attrs);
        // 在构造函数中将Xml中定义的布局解析出来。
        LayoutInflater.from(context).inflate(R.layout.find_downtime,
                this, true);
        mViews[0] = (TextView) findViewById(R.id.find_txt_time_day1);
        mViews[1] = (TextView) findViewById(R.id.find_txt_time_hour1);
        mViews[2] = (TextView) findViewById(R.id.find_txt_time_min1);
    }

    /**
     * @param millisInFuture    倒计时时长总毫秒数
     * @param countDownInterval 时间间隔毫秒数
     * @return void 返回类型
     * @Title : setCountDown
     * @Description : 设置倒计时数据并开始计时
     * @params
     */
    public void startCountDown(long millisInFuture, long countDownInterval) {
        start(millisInFuture, countDownInterval);
    }

    private void start(long millisInFuture, long countDownInterval) {
        if (timer != null) {
            timer.cancel();
        }
        timer = new CountDownTimer(millisInFuture, countDownInterval) {

            // millisUntilFinished 倒计时剩余时间
            @Override
            public void onTick(long millisUntilFinished) {
                setView(millisUntilFinished);
            }

            @Override
            public void onFinish() {
                setView(0);
                mTimeCallback.getTimeStop();
            }
        };
        timer.start();
    }

    public void setmTimeCallback(TimeCallback callback) {
        this.mTimeCallback = callback;
    }

    public interface TimeCallback {
        public void getTimeStop();
    }
    protected void setView(long millisUntilFinished) {
        int secons = Integer.valueOf("" + millisUntilFinished / 1000);
        int day = Integer.valueOf(secons / DAY + "");
        int hour = Integer.valueOf(secons % DAY / HOUR + "");
        int min = Integer.valueOf(secons % HOUR / MIN + "");
        int second = Integer.valueOf(secons % HOUR % MIN + "");

        if(day>0){
            mViews[0].setText(day + "天");
            mViews[1].setText(hour / 10 + ""+hour % 10+"时");
            mViews[2].setText(min / 10 + ""+min % 10+"分");
        }else{
            mViews[0].setText(hour / 10 + ""+hour % 10+"时");
            mViews[1].setText(min / 10 + ""+min % 10+"分");
            mViews[2].setText(second / 10 + ""+second % 10+"秒");
        }



    }
}
