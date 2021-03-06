package com.etong.android.frame.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.Media;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.etong.android.frame.R;
import com.etong.android.frame.permissions.PermissionsManager;
import com.etong.android.frame.permissions.PermissionsResultAction;
import com.etong.android.frame.subscriber.BaseSubscriberActivity;


/**
 * @author : zhouxiqing
 * @ClassName : PhotoPicUtils
 * @Description : TODO(照片工具类,返回图片路径,在裁剪模式下会返回裁剪后得到的BitMap对象)
 * @date : 2015-11-17 上午10:36:00
 */
public class PhotoPicUtils extends BaseSubscriberActivity {
    public static final String TAG = "PhotoPicUtils";

    private static final int REQUEST_CODE_TAKE_PHOTO = 0x0001;
    private static final int REQUEST_CODE_SELECT_PHOTO = 0x0002;
    private static final int REQUEST_CODE_SHEAR_PICTURES = 0x0003;

    private Uri imageUri;
    private final int outputX = 750;
    private final int outputY = 500;
    private String tag = TAG;
    private Boolean SharePicture = true;

    /**
     * @param context
     * @param tag     EventBus TAG
     * @param share   是否对图片进行裁剪
     * @return void 返回类型
     * @Title : startPhotoUtils
     * @Description : TODO(启动拍照选择框,设置TAG及图片是否裁剪)
     * @params
     */
    public static void startPhotoUtils(Context context, String tag,
                                       Boolean share) {
        Intent intent = new Intent(context, PhotoPicUtils.class);
        intent.putExtra("tag", tag);
        intent.putExtra("share", share);
        context.startActivity(intent);
    }

    @Override
    protected void onInit(@Nullable Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        // setContentView(R.layout.popdialog_photo_utils);
        View selectView = getLayoutInflater().inflate(
                R.layout.popdialog_photo_utils, null);

        setFinishOnTouchOutside(false);        // 设置在空白区域点击不消失

        setContentView(selectView, new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT));
        this.getWindow().getDecorView().setBackgroundColor(Color.TRANSPARENT);
        // 可以在此设置显示动画
        WindowManager.LayoutParams wl = getWindow().getAttributes();
        wl.x = 0;
        wl.y = getWindowManager().getDefaultDisplay().getHeight();
        // 以下这两句是为了保证按钮可以水平满屏
        wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
        wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        // 设置显示位置
        onWindowAttributesChanged(wl);

        initViews();
        tag = this.getIntent().getStringExtra("tag");
        SharePicture = this.getIntent().getBooleanExtra("share", true);
    }

    public void initViews() {
        addClickListener(R.id.btn_take_photo);
        addClickListener(R.id.btn_select_picture);
        addClickListener(R.id.btn_cancel);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_take_photo) {// 拍照
            // 因此处调用的是系统相机，摄像头权限不需要单独请求，仅请求存储读写权限
            PermissionsManager
                    .getInstance()
                    .requestPermissionsIfNecessaryForResult(
                            this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            new PermissionsResultAction() {

                                @Override
                                public void onGranted() {
                                    takePhoto();
                                }

                                @Override
                                public void onDenied(String permission) {
                                    toastMsg("授权失败，无法完成操作！");
                                    finish();
                                }
                            });
            return;
        }

        if (view.getId() == R.id.btn_select_picture) {// 选择图片
            selectPhoto();
            return;
        }
        if (view.getId() == R.id.btn_cancel) {// 取消
            this.finish();
            return;
        }
        this.finish();
    }

    public void takePhoto() {
        ContentValues contentValues = new ContentValues();
        long times = System.currentTimeMillis();
        contentValues.put(Media.DISPLAY_NAME, times);
        contentValues.put(Media.MIME_TYPE, "image/jpeg");
        if (Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState())) {
            imageUri = this.getContentResolver().insert(
                    Media.EXTERNAL_CONTENT_URI, contentValues);
        } else {
            toastMsg("SD卡未就绪!");
            return;
        }

        Intent intent = new Intent(
                MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        this.startActivityForResult(intent, REQUEST_CODE_TAKE_PHOTO);
    }

    public void selectPhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK,
                Media.EXTERNAL_CONTENT_URI);
        this.startActivityForResult(intent, REQUEST_CODE_SELECT_PHOTO);
    }

    public void shearPictures() {
        if (null == imageUri)
            return;
        Intent intent = new Intent();
        intent.setAction("com.android.camera.action.CROP");
        intent.setDataAndType(imageUri, "image/*");// mUri是已经选择的图片Uri
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 3);// 裁剪框比例
        intent.putExtra("aspectY", 2);
        intent.putExtra("outputX", outputX);// 输出图片大小
        intent.putExtra("outputY", outputY);
        intent.putExtra("return-data", true);
        this.startActivityForResult(intent, REQUEST_CODE_SHEAR_PICTURES);
        L.d("~~~~~~~~~~>",imageUri+"");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent intent) {

        switch (requestCode) {
            case REQUEST_CODE_SELECT_PHOTO:
                if (intent != null) {
                    imageUri = intent.getData();
                    if (SharePicture) {
                        shearPictures();
                    } else {
                        getEventBus().post(getImageAbsolutePath(this, imageUri),
                                tag);
                        this.finish();
                    }
                }
                L.d("++++++++>",imageUri+"");
                break;
            case REQUEST_CODE_TAKE_PHOTO:
                if (SharePicture) {
                    shearPictures();
                } else {
                    getEventBus().post(getImageAbsolutePath(this, imageUri), tag);
                    this.finish();
                }
                L.d("===============>",imageUri+"");
                break;
            case REQUEST_CODE_SHEAR_PICTURES:
                L.d("*************>",imageUri+"");
                if (intent != null) {
                    L.d("%%%%%%%%%%%%%%>",imageUri+"");
                    Bitmap bitmap = intent.getParcelableExtra("data");
                    if (resultCode == RESULT_OK && bitmap != null) {
                        getEventBus().post(getImageAbsolutePath(this, imageUri),
                                tag);
                        getEventBus().post(bitmap, tag);
                        L.d("----------->",bitmap+"");
                        this.finish();
                    }
                }break;
        }
    }

    /**
     * @param context
     * @param imageUri
     * @return String 图片绝对路径
     * @Title : getImageAbsolutePath
     * @Description : TODO(根据Uri获取图片绝对路径，解决Android4.4以上版本Uri转换)
     * @params
     */
    @SuppressLint("NewApi")
    public static String getImageAbsolutePath(Activity context, Uri imageUri) {
        if (context == null || imageUri == null)
            return null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT
                && DocumentsContract.isDocumentUri(context, imageUri)) {
            if (isExternalStorageDocument(imageUri)) {
                String docId = DocumentsContract.getDocumentId(imageUri);
                String[] split = docId.split(":");
                String type = split[0];
                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/"
                            + split[1];
                }
            } else if (isDownloadsDocument(imageUri)) {
                String id = DocumentsContract.getDocumentId(imageUri);
                Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"),
                        Long.valueOf(id));
                return getDataColumn(context, contentUri, null, null);
            } else if (isMediaDocument(imageUri)) {
                String docId = DocumentsContract.getDocumentId(imageUri);
                String[] split = docId.split(":");
                String type = split[0];
                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                String selection = Media._ID + "=?";
                String[] selectionArgs = new String[]{split[1]};
                return getDataColumn(context, contentUri, selection,
                        selectionArgs);
            }
        } // MediaStore (and general)
        else if ("content".equalsIgnoreCase(imageUri.getScheme())) {
            // Return the remote address
            if (isGooglePhotosUri(imageUri))
                return imageUri.getLastPathSegment();
            return getDataColumn(context, imageUri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(imageUri.getScheme())) {
            return imageUri.getPath();
        }
        return null;
    }

    public static String getDataColumn(Context context, Uri uri,
                                       String selection, String[] selectionArgs) {
        Cursor cursor = null;
        String column = Media.DATA;
        String[] projection = {column};
        try {
            cursor = context.getContentResolver().query(uri, projection,
                    selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri
                .getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri
                .getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri
                .getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri
                .getAuthority());
    }
}
