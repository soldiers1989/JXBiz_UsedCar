package com.etong.android.jxbizusedcar.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.etong.android.frame.common.ImageProvider;
import com.etong.android.frame.permissions.PermissionsManager;
import com.etong.android.frame.publisher.HttpPublisher;
import com.etong.android.frame.user.UC_CollectOrScannedBean;
import com.etong.android.frame.user.UC_FrameEtongApplication;
import com.etong.android.frame.user.UC_LoginActivity;
import com.etong.android.frame.utils.ActivitySkipUtil;
import com.etong.android.frame.widget.TitleBar;
import com.etong.android.frame.widget.dialog.Default_ShowDialog;
import com.etong.android.jxbizusedcar.R;
import com.etong.android.jxbizusedcar.subscriber.UC_SubscriberActivity;
import com.pgyersdk.javabean.AppBean;
import com.pgyersdk.update.PgyUpdateManager;
import com.pgyersdk.update.UpdateManagerListener;

import java.io.File;

/**
 * 设置
 * Created by Administrator on 2016/10/14.
 */

public class UC_SettingActivity extends UC_SubscriberActivity {
    private TextView mAboutUs;          //关于我们
    private TextView mUpdate;           //检测更新
    private TextView mClearCache;          //清空缓存
    private Button logOffBtn;               //退出登录
    private TitleBar mTitleBar;
    private TextView mOpenLocation;
    private RelativeLayout mOpenLocationView;
    public String[] permissions = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE};
    public boolean isFirst = true;

    @Override
    protected void myInit(Bundle bundle) {
        setContentView(R.layout.uc_activity_setting_content);
        initView();
    }

    private void initView() {

        mTitleBar = new TitleBar(this);
        mTitleBar.setTitle("设置");

        //关于我们
        mAboutUs = findViewById(R.id.uc_ll_about_us, TextView.class);
        addClickListener(mAboutUs);
        //检测更新
        mUpdate = findViewById(R.id.uc_ll_detect_updates, TextView.class);
        addClickListener(mUpdate);
        //清空缓存
        mClearCache = findViewById(R.id.uc_ll_clear_cache, TextView.class);
        addClickListener(mClearCache);
        //退出登录
        logOffBtn = findViewById(R.id.uc_login_off, Button.class);
        addClickListener(logOffBtn);

        mOpenLocation = findViewById(R.id.me_txt_is_open_location, TextView.class);
        mOpenLocationView = findViewById(R.id.me_rl_open_location, RelativeLayout.class);
        addClickListener(mOpenLocationView);

        //如果用户未登录退出登录按钮不可见
        if (UC_FrameEtongApplication.getApplication().isLogin()) {
            logOffBtn.setVisibility(View.VISIBLE);
        } else {
            logOffBtn.setVisibility(View.GONE);
        }

        if (Build.VERSION.SDK_INT >= 23) {
            mOpenLocationView.setVisibility(View.VISIBLE);
            if (UC_FrameEtongApplication.getApplication().getIsLocation()) {
                mOpenLocation.setText("已开启");
                mOpenLocationView.setClickable(false);
            } else {
                mOpenLocation.setText("未开启");
                mOpenLocationView.setClickable(true);
            }
        }

    }

    @Override
    protected void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            //关于我们
            case R.id.uc_ll_about_us:
                ActivitySkipUtil.skipActivity(this, UC_AboutUsActivity.class);
                break;
            case R.id.uc_ll_detect_updates:
                // 检测更新
                onCheckUpdate();
                break;
            case R.id.uc_ll_clear_cache:
                // 清除缓存
                ImageProvider.getInstance().getImageLoader().getInstance().clearMemoryCache();//清除内存
                ImageProvider.getInstance().getImageLoader().getInstance().clearDiskCache();//清除sd卡
                clearWebViewCache();
                boolean isClear = HttpPublisher.clearCache();
                String msg = "";
                if (isClear) {
                    msg = "清除成功！";
                } else {
                    msg = "清除失败！";
                }
                toastMsg(msg);
                break;
            case R.id.me_rl_open_location:
                // 显示定位
                //跳到本app的应用管理中
                Intent intent = new Intent(
                        Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                intent.setData(Uri.parse("package:" + getPackageName()));
                startActivity(intent);
                break;
            // 退出登录
            case R.id.uc_login_off:
                UC_CollectOrScannedBean temp = new UC_CollectOrScannedBean();
                temp.setChanged(true);
                UC_FrameEtongApplication.getApplication().setCollectCache(temp);
                UC_FrameEtongApplication.getApplication().setHistoryCache(temp);
                UC_FrameEtongApplication.getApplication().setUserInfo(null);
                ActivitySkipUtil.skipActivity(this, UC_LoginActivity.class);
                toastMsg("退出登录成功!");
                // 在退出登录之后设置别名
                UC_FrameEtongApplication.getApplication().setJPushAlias();

                finish();

                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isFirst) {
            isFirst = false;
        } else {
            if (!UC_FrameEtongApplication.getApplication().getIsLocation()) {
                if (PermissionsManager.getInstance().hasAllPermissions(this, permissions)) {
                    UC_FrameEtongApplication.getApplication().setIsLocation(true);
                }
            } else {
                if (!PermissionsManager.getInstance().hasAllPermissions(this, permissions)) {
                    UC_FrameEtongApplication.getApplication().setIsLocation(false);
                }
            }

            if (UC_FrameEtongApplication.getApplication().getIsLocation()) {
                mOpenLocation.setText("已开启");
                mOpenLocationView.setClickable(false);
            } else {
                mOpenLocation.setText("未开启");
                mOpenLocationView.setClickable(true);
            }
        }
    }

    /**
     * @desc (检测更新)
     * @createtime 2016/11/1 0001-16:33
     * @author wukefan
     */
    private void onCheckUpdate() {
        PgyUpdateManager.register(UC_SettingActivity.this,
                new UpdateManagerListener() {
                    @Override
                    public void onUpdateAvailable(final String result) {
                        // 将新版本信息封装到AppBean中
                        final AppBean appBean = getAppBeanFromString(result);
                        Default_ShowDialog.Builder builder = new Default_ShowDialog.Builder(UC_SettingActivity.this);
                        builder.setMessage("检测到新版本，我们建议您更新版本享受更好的服务！");
                        builder.setTitle("更新信息");
                        builder.setPositiveButton("立即升级", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                //设置你的操作事项
                                startDownloadTask(UC_SettingActivity.this, appBean.getDownloadURL());
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
    }


    /**
     * 清除WebView缓存
     */
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

    /**
     * 递归删除 文件/文件夹 * * @param file
     */
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
