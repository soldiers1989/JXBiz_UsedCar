package com.etong.android.jxbizusedcar.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.etong.android.frame.utils.L;
import com.etong.android.frame.widget.dialog.Default_ShowDialog;

import com.etong.android.jxbizusedcar.R;
import com.pgyersdk.javabean.AppBean;
import com.pgyersdk.update.PgyUpdateManager;
import com.pgyersdk.update.UpdateManagerListener;

import org.simple.eventbus.Subscriber;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UC_WelcomeActivity extends FragmentActivity {

    /*
  ##################################################################################################
  ##                                        类中的变量                                            ##
  ##################################################################################################
*/
    private String ALL_DATA = "all_car";  //所有车标记
    private String HIS_DATA = "his_car";  //历史记录标记
    private boolean canStart = false;
    private boolean isLoaded = false;
    private boolean isTimeOut = true;

    private ImageView mImageView;
    private RelativeLayout rootView;
    private final static long ANIMATION_DURATION = 3000;
    private final static long TIME_OUT_GET_UPDATE_DATA = 5000;
//    private String appVersion;
//    private int appVersionNo = -1;

//    private String currentAppVersion;
//    private int currentAppVersionNo=-1;
//    private boolean netWorkEnable;
    private boolean isSrart;        // 是否跳转到下一页




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_uc__welcome);


        initView();
        initData();
        // 更新信息
        initUpdateInfo();
    }
/*
  ##################################################################################################
  ##                                     初始化View的方法                                         ##
  ##################################################################################################
*/

    /**
     * 请使用快捷模板生成注释
     */
    private void initView() {
        mImageView = (ImageView) findViewById(R.id.uc_main_welcome_img);
        rootView = (RelativeLayout) this.findViewById(R.id.start_root);
        rootView.startAnimation(setAnimation());
    }

/*
  ##################################################################################################
  ##                                     初始化数据的方法                                         ##
  ##################################################################################################
*/

    /**
     * 请使用快捷模板生成注释
     */
    private void initData() {


    }

/*
  ##################################################################################################
  ##                                     点击事件的处理                                           ##
  ##################################################################################################
*/

    /**
     * 控件的点击事件
     */
    protected void onClick(View view) {

    }


/*
  ##################################################################################################
  ##                              使用的逻辑方法，以及对外公开的方法                              ##
  ##                                      请求数据、获取数据                                      ##
  ##################################################################################################
*/



    private void initUpdateInfo() {

        PgyUpdateManager.register(UC_WelcomeActivity.this,
                new UpdateManagerListener() {
                    @Override
                    public void onUpdateAvailable(final String result) {
                        isTimeOut = false;
                        // 将新版本信息封装到AppBean中
                        final AppBean appBean = getAppBeanFromString(result);
                        Default_ShowDialog.Builder builder = new Default_ShowDialog.Builder(UC_WelcomeActivity.this);
                        builder.setMessage("检测到新版本，我们建议您更新版本享受更好的服务！");
                        builder.setTitle("更新信息");
                        builder.setCancelableOnTouchOutSide(false);
                        builder.setPositiveButton("立即升级", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                isLoaded = true;
                                startDownloadTask(UC_WelcomeActivity.this, appBean.getDownloadURL());
                            }
                        });

                        builder.setNegativeButton("下次提醒",
                                new android.content.DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        canStart = true;
                                        srartActivity();
                                    }
                                });
                        builder.create().show();
                    }

                    @Override
                    public void onNoUpdateAvailable() {
                        isTimeOut = false;
                        L.d("MainStartActivity", "已是最新版本!");
                        canStart = true;
                    }
                });
    }

    /**
     * 设置动画
     *
     * @return
     */
    private AnimationSet setAnimation() {
        // 透明度的动画
        AlphaAnimation alphaAnimation = new AlphaAnimation(0f, 1f);
        alphaAnimation.setDuration(ANIMATION_DURATION);// 设置动画的时长
        alphaAnimation.setFillAfter(true);

        // 动画集合
        AnimationSet set = new AnimationSet(false);
        set.addAnimation(alphaAnimation);
        // 监听动画执行
        set.setAnimationListener(new StartAnimationListener());

        return set;
    }

    class StartAnimationListener implements Animation.AnimationListener {
        protected final long ANIMATION_DELAY = System.currentTimeMillis();

        @Override
        public void onAnimationStart(Animation animation) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            // TODO Auto-generated method stub
            // 动画结束后，等待一会，再进行页面跳转
            new Thread(new Runnable() {

                @Override
                public void run() {
                    while (true) {
                        if((System.currentTimeMillis() - ANIMATION_DELAY) > TIME_OUT_GET_UPDATE_DATA) {
                            // 如果超过了5秒，并且isTimeOut为true
                            if(isTimeOut && !canStart) {
                                srartActivity();
                            }
                            return;
                        }
                        if ((System.currentTimeMillis() - ANIMATION_DELAY) > ANIMATION_DURATION) {
                            // 关闭了自动更新，后面打开自动更新需要更改
                            if (canStart) {
                                srartActivity();
                                return;
                            }
                        }
                    }
                }
            }).start();
        }

        @Override
        public void onAnimationRepeat(Animation animation) {
            // TODO Auto-generated method stub

        }

    }

    public void srartActivity() {
        if (!isSrart) {
            isSrart = true;
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
//        ActivitySkipUtil.skipActivity(this, MainContentActivity.class);
            Intent i = new Intent(this, UsedCar_MainActivity.class);
            startActivity(i);
            //设置切换动画，从右边进入，左边退出
            overridePendingTransition(R.anim.main_enter_with_right, R.anim.main_out_to_left);
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isLoaded) {
            srartActivity();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PgyUpdateManager.unregister();
    }

    /*
  ##################################################################################################
  ##                            类中根据成员设置的getter和setter方法                              ##
  ##################################################################################################
*/

}
