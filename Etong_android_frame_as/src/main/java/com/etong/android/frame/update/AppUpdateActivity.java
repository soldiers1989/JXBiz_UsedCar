package com.etong.android.frame.update;

import android.Manifest;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.etong.android.frame.R;
import com.etong.android.frame.permissions.PermissionsManager;
import com.etong.android.frame.permissions.PermissionsResultAction;
import com.etong.android.frame.subscriber.BaseSubscriberActivity;
import com.etong.android.frame.utils.ActivityStackManager;
import com.etong.android.frame.utils.ApkUtils;
import com.etong.android.frame.utils.SignUtils;
import com.etong.android.frame.utils.StoragePathUtils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.simple.eventbus.Subscriber;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @ClassName : AppUpdateActivity
 * @Description : 自动更新页面
 * @author : zhouxiqing
 * @date : 2015-11-3 下午2:42:39
 * 
 */
@SuppressWarnings("deprecation")
public class AppUpdateActivity extends BaseSubscriberActivity {
	private RelativeLayout mPromptedUpdate;
	private RelativeLayout mProgressUpdate;
	private TextView mUpdateTip;
	private ProgressBar mDownloadProgress;
	private TextView mDownloadProgressValue;
	private Button mBtnUpdataCancle;
	private Button mBtnCancle;

	private AppUpdate info = null;// 更新信息
	private File load_file = null;// 下载的文件
	private String FileName;// 下载的文件名
	private String FilePath = null;// 下载的文件路径
	private boolean iscancle = true;// 是否可以跳过更新
	private DownLoadTask download_task = null;// 下载文件线程
	private AppUpdateResultAction action;

	@Override
	protected void onInit(@Nullable Bundle savedInstanceState) {
		setFinishOnTouchOutside(false);// 设置外部点击不消失
		this.setContentView(R.layout.activity_app_update);
		registerSticky();
		initViews();
		this.action = AppUpdateProvider.action;
	}

	public void initViews() {
		mPromptedUpdate = findViewById(R.id.prompted_update,
				RelativeLayout.class);
		mProgressUpdate = findViewById(R.id.progress_update,
				RelativeLayout.class);
		mUpdateTip = findViewById(R.id.update_tip, TextView.class);
		mDownloadProgress = findViewById(R.id.download_progress,
				ProgressBar.class);
		mDownloadProgressValue = findViewById(R.id.download_progress_value,
				TextView.class);
		mBtnUpdataCancle = findViewById(R.id.update_cancel, Button.class);
		mBtnCancle = findViewById(R.id.download_cancle, Button.class);

		addClickListener(R.id.update_cancel);
		addClickListener(R.id.update_ok);
		addClickListener(R.id.download_cancle);

		// mPromptedUpdate.setVisibility(View.VISIBLE);
		mProgressUpdate.setVisibility(View.GONE);
	}

	/**
	 * @Title : initData
	 * @Description : 初始化数据
	 * @params
	 * @param data
	 *            更新信息
	 * @return void 返回类型
	 */
	@Subscriber(tag = AppUpdateProvider.TAG)
	public void initData(AppUpdate data) {
		// 初始化文件路径
		FilePath = StoragePathUtils.getStoragePaths(this)[0] + File.separator
				+ "Download" + File.separator;
		info = data;
		mEventBus.removeStickyEvent(AppUpdate.class);

		// 没有升级信息时默认进入app
		if (info == null) {
			action.fail(AppUpdateProvider.ERR_NULL, "更新内容为空");
			this.finish();
			return;
		}

		// 本地apk包无法使用或差分合成so无法加载时设为完全更新
		if (TextUtils.isEmpty(info.getOldApkSource()) || !PatchUtils.canPatch()) {
			info.setIspatch(false);
		}

		// 初始化文件名
		FileName = info.getAppName();
		String tip = info.getTitle();

		// 下载文件大小在于零且为差分更新时显示下载文件大小
		if (info.getSize() > 0 && info.isIspatch()) {
			tip += "     " + info.getSize() + "MB\n";
		}
		tip += "更新内容\n";
		tip += info.getComments();
		if (!TextUtils.isEmpty(info.getOldMd5())) {// 旧MD5不为空
			if (!SignUtils.checkMd5(info.getOldApkSource(), info.getOldMd5())) {// 本地安装的apkMD5错误
				tip = "注意！注意！注意！\n您安装的是盗版应用，经过非法修改的应用可能会损害您的权益，请到各应用市场下载正版！";
				info.setInstall(true);// 设置更新过程不可跳过
				mUpdateTip.setTextColor(Color.RED);
				mBtnCancle.setClickable(false);
			}
		}
		// 显示提示信息
		mUpdateTip.setText(tip);

		if (info.isInstall()) {
			mBtnUpdataCancle.setText("退出程序");
			mBtnCancle.setText("取消下载并退出");

		}
	}

	@Override
	public void onClick(View view) {

		if (view.getId() == R.id.update_cancel) {// 稍后更新
			if (info.isInstall()) {
				ActivityStackManager.create().AppExit(this);
			}
			action.fail(AppUpdateProvider.ERR_LATER, "稍后更新");
			this.finish();
			return;
		}

		if (view.getId() == R.id.update_ok) {// 现在更新
			mPromptedUpdate.setVisibility(View.GONE);
			mProgressUpdate.setVisibility(View.VISIBLE);
			startDownload();
			return;
		}

		if (view.getId() == R.id.download_cancle) {// 取消下载
			if (info.isInstall()) {
				ActivityStackManager.create().AppExit(this);
				return;
			}
			if (iscancle) {// 可取消
				download_task.cancel(true);
				download_task = null;
				load_file.delete();
				action.fail(AppUpdateProvider.ERR_CANCLE, "更新取消");
				this.finish();
			} else {
				// 重试下载
				if (!download_task.isCancelled()) {
					download_task.cancel(true);
					download_task = null;
					startDownload();
					iscancle = true;
					mBtnCancle.setText("取消下载");
				}
			}
			return;
		}

	}

	/**
	 * @Title : startDownload
	 * @Description : 检查授权情况并开始下载
	 * @params 设定文件
	 * @return void 返回类型
	 */
	private void startDownload() {
		PermissionsManager
				.getInstance()
				.requestPermissionsIfNecessaryForResult(
						this,
						new String[] { Manifest.permission.WRITE_EXTERNAL_STORAGE },
						new PermissionsResultAction() {

							@Override
							public void onGranted() {
								if (download_task == null) {
									download_task = new DownLoadTask(
											AppUpdateActivity.this);
								}
								if (info.isIspatch()) {// 差分升级，下载差分包
									download_task.execute(info.getUrl());
								} else {// 完整升级，下载完整包
									download_task.execute(info.getCompleteUrl());
								}
							}

							@Override
							public void onDenied(String permission) {
								toastMsg("授权失败，更新无法完成！");
								onClick(findViewById(R.id.update_cancel));
							}
						});
	}

	private void downloadSuccess(File file) {
		if (file != null) {
			String apkPath = file.getAbsolutePath();
			// 差分时进行合成apk
			if (info.isIspatch()) {// 差分安装，合成apk
				apkPath = FilePath + FileName + ".apk";
				int code = PatchUtils.bspatch(info.getOldApkSource(), apkPath,
						file.getAbsolutePath());
				switch (code) {
				case PatchUtils.PATCH_SUCCESS:
					load_file.delete();
					ApkUtils.installApk(this, apkPath);
					ActivityStackManager.create().AppExit(this);
					break;
				case PatchUtils.PATCH_FAIL:
					toastMsg("更新失败，进行完整更新！");
					mBtnCancle.setText("完整更新");
					info.setIspatch(false);
					info.setInstall(false);
					iscancle = false;
					break;
				}
			}else{
				ApkUtils.installApk(this, apkPath);
				ActivityStackManager.create().AppExit(this);
			}
		}
	}

	private void downloadFail() {
		mBtnCancle.setText("点击重试");
		iscancle = false;
	}

	private class DownLoadTask extends AsyncTask<String, Integer, Boolean> {

		public DownLoadTask(Context context) {
		}

		// onPreExecute方法用于在执行后台任务前做一些UI操作
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		// doInBackground方法内部执行后台任务,不可在此方法内修改UI
		@Override
		protected Boolean doInBackground(String... params) {
			HttpClient httpClient = new DefaultHttpClient();
			HttpResponse response;
			try {
				HttpGet httpGet = new HttpGet(params[0]);
				String app_path = FilePath + FileName;
				if (info.isIspatch()) {
					app_path += ".patch";
				} else {
					app_path += ".apk";
				}

				response = httpClient.execute(httpGet);
				HttpEntity entity = response.getEntity();
				long length = entity.getContentLength();
				InputStream is = entity.getContent();
				load_file = new File(app_path);
				if (load_file.exists()) {
					load_file.delete();
				}
				FileOutputStream fileOutputStream = null;
				if (is != null) {
					fileOutputStream = new FileOutputStream(load_file);
					byte[] buf = new byte[1024];
					int ch = -1;
					int count = 0;
					while ((ch = is.read(buf)) != -1
							&& !download_task.isCancelled()) {
						fileOutputStream.write(buf, 0, ch);
						count += ch;
						publishProgress((int) ((count * 100) / length));
					}
					is.close();
					if (fileOutputStream != null) {
						fileOutputStream.flush();
						fileOutputStream.close();
					}
					return true;
				}

			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (httpClient != null) {
					httpClient.getConnectionManager().shutdown();
				}
			}
			return false;
		}

		// onProgressUpdate方法用于更新进度信息
		@Override
		protected void onProgressUpdate(Integer... values) {
			super.onProgressUpdate(values);
			mDownloadProgressValue.setText(values[0] + "%");
			mDownloadProgress.setProgress(values[0]);
		}

		// onPostExecute方法用于在执行完后台任务后更新UI,显示结果
		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			if (result) {// 下载成功
				System.out.println("下载成功");
				downloadSuccess(load_file);
				mBtnCancle.setText("下载完成");
			} else {// 下载失败
				System.out.println("下载失败");
				downloadFail();
			}
		}

		// onCancelled方法用于在取消执行中的任务时更改UI
		@Override
		protected void onCancelled() {
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
