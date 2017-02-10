package com.etong.android.jxapp.main;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.etong.android.frame.publisher.HttpPublisher;
import com.etong.android.frame.user.FrameEtongApplication;
import com.etong.android.frame.utils.ActivitySkipUtil;
import com.etong.android.frame.utils.L;
import com.etong.android.frame.widget.dialog.Default_ShowDialog;
import com.etong.android.jxapp.R;
import com.etong.android.jxapp.main.provider.Main_GetInfo_Provider;
import com.pgyersdk.javabean.AppBean;
import com.pgyersdk.update.PgyUpdateManager;
import com.pgyersdk.update.UpdateManagerListener;

/**
 * 启动页
 * Created by Administrator on 2016/9/7.
 */
public class MainStartActivity extends FragmentActivity{

    private boolean canStart = false;
    private boolean isLoaded = false;
    private boolean visonCanLoaded = false;
    private ImageView mImageView;
    private RelativeLayout rootView;
    private final static long ANIMATION_DURATION = 3000;
    private HttpPublisher mHttpPublisher;
    private Main_GetInfo_Provider mMainProvider;

    private String appVersion;
    private int appVersionNo = -1;

    private String currentAppVersion;
    private int currentAppVersionNo=-1;
    private boolean netWorkEnable;


    private boolean isTimeOut = true;
    private final static long TIME_OUT_GET_UPDATE_DATA = 5000;
    private boolean isSrart;        // 是否跳转到下一页
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main_start);
                // 设置启动页Activity不能侧滑
//        setSwipeBackEnable(false);
        mHttpPublisher = HttpPublisher.getInstance();
        mHttpPublisher.initialize(this);
        mMainProvider = Main_GetInfo_Provider.getInstance();
        mMainProvider.initialize(mHttpPublisher);

        initView();

        // 更新信息
        initUpdateInfo();
    }

    private void initUpdateInfo() {

        PgyUpdateManager.register(MainStartActivity.this,
                new UpdateManagerListener() {
                    @Override
                    public void onUpdateAvailable(final String result) {
                        isTimeOut = false;
                        // 将新版本信息封装到AppBean中
                        final AppBean appBean = getAppBeanFromString(result);
                        Default_ShowDialog.Builder builder = new Default_ShowDialog.Builder(MainStartActivity.this);
                        builder.setMessage("检测到新版本，我们建议您更新版本享受更好的服务！");
                        builder.setTitle("更新信息");
                        builder.setPositiveButton("立即升级", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                isLoaded = true;
                                startDownloadTask(MainStartActivity.this, appBean.getDownloadURL());
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

    public void initView() {

        mImageView = (ImageView) findViewById(R.id.main_start_img);
        rootView = (RelativeLayout) this.findViewById(R.id.start_root);
        rootView.startAnimation(setAnimation());
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
                        if ((System.currentTimeMillis() - ANIMATION_DELAY) > 3000) {
                            // 关闭了自动更新，后面打开自动更新需要更改
                            if (canStart) {
                                srartActivity();
                                return;
                            }
//                            return;
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
        // 判断是否引导过
        boolean isGuided = FrameEtongApplication.getApplication().activityIsGuided(this, GuideActivity.IS_GUIDE);
        if (isGuided) {
            ActivitySkipUtil.skipActivity(this, MainContentActivity.class);
            overridePendingTransition(R.anim.main_enter_with_right, R.anim.main_out_to_left);
        } else {
            ActivitySkipUtil.skipActivity(this, GuideActivity.class);
        }
//            Intent i = new Intent(this, MainContentActivity.class);
//            startActivity(i);
            //设置切换动画，从右边进入，左边退出
        finish();
    }
    }

    @Override
    protected void onPause() {
        super.onPause();

//        Thread thread =new TestThread();
//        thread.start();
    }
    class TestThread extends Thread{
        @Override
        public void run(){
            try {
                Thread.currentThread().sleep(2000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            L.e("tag", "onPause");
//            Toast.makeText("退出程序",Toast.LENGTH_LONG).show();

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
}


