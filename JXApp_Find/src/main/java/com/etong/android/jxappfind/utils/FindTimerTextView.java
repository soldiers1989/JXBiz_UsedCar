package com.etong.android.jxappfind.utils;

import android.content.Context;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.widget.TextView;

import java.text.SimpleDateFormat;

/**
 * (自定义倒计时TextView)
 * Created by Administrator on 2016/9/6.
 */
public class FindTimerTextView  extends TextView {

    private static final String TAG = "TimerTextView";
    private static SimpleDateFormat format_dhms = new SimpleDateFormat(
            "dd天HH时mm分ss秒");
    private CountDownTimer timer = null;
    private String formatString = "dd天HH时mm分ss秒";
    private static String EndString = "00天00时00分00秒";
    public static final int DAY = 86400;
    public static final int HOUR = 3600;
    public static final int MIN = 60;

    private boolean isRun = false;
    private long mMillisInFuture;
    private long mCountDownInterval;

    public FindTimerTextView(Context context) {
        super(context);
    }

    public FindTimerTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FindTimerTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
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
        this.mMillisInFuture = millisInFuture;
        this.mCountDownInterval = countDownInterval;
        start();
    }

    public void start() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        // 倒计时是否开始
        isRun = true;
        timer = new CountDownTimer(mMillisInFuture, mCountDownInterval) {

            // millisUntilFinished 倒计时剩余时间
            @Override
            public void onTick(long millisUntilFinished) {
                setView(millisUntilFinished);
            }

            @Override
            public void onFinish() {
                setView(0);
            }
        };
        timer.start();
    }

    protected void setView(long millisUntilFinished) {
        int secons = Integer.valueOf("" + millisUntilFinished / 1000);
        int day = Integer.valueOf(secons / DAY + "");
        int hour = Integer.valueOf(secons % DAY / HOUR + "");
        int min = Integer.valueOf(secons % HOUR / MIN + "");
        int sec = Integer.valueOf(secons % HOUR % MIN + "");


        String text = format_dhms.toPattern();
        text = text.replaceAll("dd", "d");
        text = text.replaceAll("HH", "H");
        text = text.replaceAll("mm", "m");
        text = text.replaceAll("ss", "s");
        if (!formatString.contains("d")) {
            hour += day * 24;
        }
        text = text.replaceAll("d", day + "");
        text = text.replaceAll("H", (hour) + "");
        text = text.replaceAll("m", min + "");
        text = text.replaceAll("s", sec + "");
        this.setText(text);
    }



    public boolean isRun() {
        return isRun;
    }

    public void setRun(boolean run) {
        isRun = run;
    }
}
