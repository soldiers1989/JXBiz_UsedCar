package com.etong.android.jxappme.view;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.etong.android.frame.common.ImageProvider;
import com.etong.android.frame.permissions.PermissionsManager;
import com.etong.android.frame.publisher.HttpPublisher;
import com.etong.android.frame.subscriber.BaseSubscriberActivity;
import com.etong.android.frame.user.FrameEtongApplication;
import com.etong.android.frame.user.FramePersonalLoginActivity;
import com.etong.android.frame.user.UsedAndNewCollectCar;
import com.etong.android.frame.utils.ActivitySkipUtil;
import com.etong.android.frame.utils.L;
import com.etong.android.frame.widget.TitleBar;
import com.etong.android.frame.widget.dialog.Default_ShowDialog;
import com.etong.android.jxappme.R;
import com.pgyersdk.javabean.AppBean;
import com.pgyersdk.update.PgyUpdateManager;
import com.pgyersdk.update.UpdateManagerListener;

import java.io.File;

/**
 * 设置
 * Created by Administrator on 2016/9/8.
 */
public class MePersonalSettingActivity extends BaseSubscriberActivity {

    private TitleBar mTitleBar;
    private TextView check_update;
    private TextView clear_cache;
    private Button log_off;
    private ToggleButton show_weather;
    private ToggleButton show_oil_price;
    private RelativeLayout ll_open_location;
    private TextView tv_open_location;
    public String[] permissions = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE};
    private boolean isFirst = true;

    @Override
    protected void onInit(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.me_activity_personal_setting);

        initView();

        if(FrameEtongApplication.getApplication().isLogin()){
            log_off.setVisibility(View.VISIBLE);
        }else {
            log_off.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
       /* if(FrameEtongApplication.getApplication().getIsLocation() && PermissionsManager.getInstance().hasAllPermissions(this, permissions)){
            tv_open_location.setText("已开启");
            ll_open_location.setClickable(false);
        }else{
            tv_open_location.setText("未开启");
            ll_open_location.setClickable(true);
        }*/
        if (isFirst) {
            isFirst = false;
        } else {
            if (!FrameEtongApplication.getApplication().getIsLocation()) {
                if (PermissionsManager.getInstance().hasAllPermissions(this, permissions)) {
                    FrameEtongApplication.getApplication().setIsLocation(true);
                }
            } else {
                if (!PermissionsManager.getInstance().hasAllPermissions(this, permissions)) {
                    FrameEtongApplication.getApplication().setIsLocation(false);
                }
            }

            if (FrameEtongApplication.getApplication().getIsLocation()) {
                tv_open_location.setText("已开启");
                ll_open_location.setClickable(false);
            } else {
                tv_open_location.setText("未开启");
                ll_open_location.setClickable(true);
            }
        }
    }

    protected void initView(){
        mTitleBar = new TitleBar(this);
        mTitleBar.setTitle("设置");
        mTitleBar.showBottomLin(false);
        mTitleBar.showNextButton(false);
        mTitleBar.showBackButton(true);

        // 初始化设置版本号
        PackageInfo packageInfo = FrameEtongApplication.getApplication().getPackageInfo();
        String versionName = packageInfo.versionName;
        TextView verTv = findViewById(R.id.me_setting_version_name_tv, TextView.class);
        verTv.setText(TextUtils.isEmpty(versionName)?"津湘汽车":"津湘汽车    "+versionName);

        check_update =findViewById(R.id.me_txt_check_update, TextView.class);
        clear_cache =findViewById(R.id.me_txt_clear_cache, TextView.class);
        log_off =findViewById(R.id.me_log_off, Button.class);
        show_weather =findViewById(R.id.me_tb_show_weather,ToggleButton.class);
        show_oil_price =findViewById(R.id.me_tb_show_oil_price,ToggleButton.class);
        ll_open_location =findViewById(R.id.me_rl_open_location,RelativeLayout.class);
        tv_open_location =findViewById(R.id.me_txt_is_open_location,TextView.class);
        addClickListener(R.id.me_rl_open_location);

//
        if (Build.VERSION.SDK_INT >= 23) {
            ll_open_location.setVisibility(View.VISIBLE);
            L.d(FrameEtongApplication.getApplication().getIsLocation()+"===============");
//            if(FrameEtongApplication.getApplication().getIsLocation() && PermissionsManager.getInstance().hasAllPermissions(this, permissions)){
            if (FrameEtongApplication.getApplication().getIsLocation()) {
                tv_open_location.setText("已开启");
                ll_open_location.setClickable(false);
            }else{
                tv_open_location.setText("未开启");
                ll_open_location.setClickable(true);
            }

//            if(!PermissionsManager.getInstance().hasAllPermissions(this, permissions)){
//                tv_open_location.setText("未开启");
//            }else{
//                tv_open_location.setText("已开启");
//            }
        }

        //判断缓存中有没有天气 油价
        if(FrameEtongApplication.getApplication().getIsShowWeather()){
            show_weather.setChecked(true);
        }
        if(FrameEtongApplication.getApplication().getIsShowOilPrice()){
            show_oil_price.setChecked(true);
        }

        //开关监听
        show_weather.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    FrameEtongApplication.getApplication().setIsShowWeather(true);
                }else{
                    FrameEtongApplication.getApplication().setIsShowWeather(false);
                }
            }
        });

        show_oil_price.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    FrameEtongApplication.getApplication().setIsShowOilPrice(true);
                }else{
                    FrameEtongApplication.getApplication().setIsShowOilPrice(false);
                }

            }
        });

        addClickListener(R.id.me_txt_check_update);
        addClickListener(R.id.me_txt_clear_cache);
        addClickListener(R.id.me_log_off);

    }




    @Override
    protected void onClick(View view) {
        super.onClick(view);
        if(view.getId()==R.id.me_txt_check_update){
            // 检测更新
            PgyUpdateManager.register(MePersonalSettingActivity.this,
                    new UpdateManagerListener() {
                        @Override
                        public void onUpdateAvailable(final String result) {
                            // 将新版本信息封装到AppBean中
                            final AppBean appBean = getAppBeanFromString(result);
                            Default_ShowDialog.Builder builder = new Default_ShowDialog.Builder(MePersonalSettingActivity.this);
                            builder.setMessage("检测到新版本，我们建议您更新版本享受更好的服务！");
                            builder.setTitle("更新信息");
                            builder.setPositiveButton("立即升级", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    //设置你的操作事项
                                    startDownloadTask(MePersonalSettingActivity.this, appBean.getDownloadURL());
                                }
                            });

                            builder.setNegativeButton("下次提醒",
                                    new android.content.DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });
                            builder.create().show();
                        }

                        @Override
                        public void onNoUpdateAvailable() {
                            toastMsg("已是最新版本！");
                        }
                    });

        }else if(view.getId()==R.id.me_txt_clear_cache){
            ImageProvider.getInstance().getImageLoader().getInstance().clearMemoryCache();//清除内存
            ImageProvider.getInstance().getImageLoader().getInstance().clearDiskCache();//清除sd卡
            clearWebViewCache();
            boolean isClear = HttpPublisher.clearCache(); // 清除网络请求缓存
            String msg = "";
            if (isClear) {
                msg = "清除缓存成功！";
            } else {
                msg = "已经没有缓存了喔～";
            }
            toastMsg(msg);


        }else if(view.getId()==R.id.me_log_off){
            toastMsg("退出登录成功!");
            UsedAndNewCollectCar temp = new UsedAndNewCollectCar();
            temp.setChanged(true);
            FrameEtongApplication.getApplication().setUsedCarCollectCache(temp);
            FrameEtongApplication.getApplication().setNewCarCollectCache(temp);
            FrameEtongApplication.getApplication().setUserInfo(null);
            ActivitySkipUtil.skipActivity(this, FramePersonalLoginActivity.class);
            // 设置极光推送的别名
            FrameEtongApplication.getApplication().setJPushAlias();
            // 清除App的本地还款提醒的缓存
            FrameEtongApplication.getApplication().clearRepaymentRemindInfo();
            finish();

        }else if(view.getId()==R.id.me_rl_open_location){
                //跳到本app的应用管理中
                Intent intent = new Intent(
                        Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                intent.setData(Uri.parse("package:" + getPackageName()));
                startActivity(intent);

        }
    }

    /** * 清除WebView缓存 */
    public void clearWebViewCache() {
        // 清理Webview缓存数据库
        try {
            deleteDatabase("webview.db");
            deleteDatabase("webviewCache.db");
        } catch (Exception e) {
            e.printStackTrace();
        }
        // WebView 缓存文件
        File appCacheDir = new File(getFilesDir().getAbsolutePath() + "/webcache");
        File webviewCacheDir = new File(getCacheDir().getAbsolutePath()
                + "/webviewCache");

        // 删除webview 缓存目录
        if (webviewCacheDir.exists()) {
            deleteFile(webviewCacheDir);
        }
        // 删除webview 缓存 缓存目录
        if (appCacheDir.exists()) {
            deleteFile(appCacheDir);
        }

    }

    /** * 递归删除 文件/文件夹 * * @param file */
    public void deleteFile(File file) {
        if (file.exists()) {
            if (file.isFile()) {
                file.delete();
            } else if (file.isDirectory()) {
                File files[] = file.listFiles();
                for (int i = 0; i < files.length; i++) {
                    deleteFile(files[i]);
                }
            }
            file.delete();
        } else {

        }
    }
}
