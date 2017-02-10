package com.etong.android.jxappusedcar.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.etong.android.frame.common.ImageProvider;
import com.etong.android.jxappusedcar.R;
import com.etong.android.jxappusedcar.bean.UC_CarDetail_TitleBean;
import com.etong.android.jxappusedcar.bean.UC_SelectImageJavabean;
import com.etong.android.jxappusedcar.content.UC_SelectImageActivity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import org.simple.eventbus.EventBus;

import java.util.List;

/**
 * @desc 全部图片的适配器
 * @createtime 2016/10/24 - 9:27
 * @Created by xiaoxue.
 */

public class UC_AllImageAdapter  extends BaseAdapter {
//    private List<String> mImageList;
    private List<UC_CarDetail_TitleBean.ImgUrlsBean> mImageList;

    private Context mContext;
    private ItemHolder childHolder;
    private ImageProvider mImageProvider;

    public UC_AllImageAdapter(Context context,List<UC_CarDetail_TitleBean.ImgUrlsBean> list){
        this.mImageList=list;
        this.mContext=context;
        mImageProvider = ImageProvider.getInstance();
        mImageProvider.initialize(mContext);
    }

    public UC_AllImageAdapter(){

    }

    @Override
    public int getCount() {
        return mImageList.size();
    }

    @Override
    public Object getItem(int position) {
        return mImageList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.used_car_all_photo_item, null);
            childHolder = new ItemHolder();
            childHolder.iv_image = (ImageView) convertView.findViewById(R.id.used_car_iv_image);
            // 获取屏幕像素相关信息
            DisplayMetrics dm = new DisplayMetrics();
            WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
            wm.getDefaultDisplay().getMetrics(dm);
            int mWidth = dm.widthPixels;

            ViewGroup.LayoutParams lp = (ViewGroup.LayoutParams) childHolder.iv_image.getLayoutParams();
            //将宽度设置为屏幕的1/3
            lp.width = (mWidth - (int) (24 * dm.density + 0.5f)) / 3;
            //将高度设置为宽度的3/4(7/10)
            lp.height = ((mWidth - (int) (24 * dm.density + 0.5f)) / 3)*7/10;
            //根据屏幕大小按比例动态设置图片大小
            childHolder.iv_image.setLayoutParams(lp);
            convertView.setTag(childHolder);
        } else {
            childHolder = (ItemHolder) convertView.getTag();
        }
        DisplayImageOptions displayImageOptions  = new DisplayImageOptions.Builder()
                .cacheInMemory(false)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .imageScaleType(ImageScaleType.EXACTLY)
                .showImageForEmptyUri(R.mipmap.uc_all_image_loading)
                .showImageOnLoading(R.mipmap.uc_all_image_loading)
                .showImageOnFail(R.mipmap.uc_all_image_loading).build();
        ImageLoader.getInstance().displayImage(mImageList.get(position).getImgUrl(), childHolder.iv_image, displayImageOptions);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //传给上一个查看图片的位置
                EventBus.getDefault().post(position,"pass a position");
                ((Activity)mContext).finish();
            }
        });
        return convertView;
    }

    class ItemHolder {
        ImageView iv_image;
    }
}
