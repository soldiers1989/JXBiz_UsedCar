package com.etong.android.jxappusedcar.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.etong.android.frame.common.ImageProvider;
import com.etong.android.frame.widget.photoview.Frame_DefaultPhotoView;
import com.etong.android.jxappusedcar.R;
import com.etong.android.jxappusedcar.bean.UC_CarDetail_TitleBean;
import com.etong.android.jxappusedcar.bean.UC_SelectImageJavabean;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;


import java.util.List;

/**
 * 自定义viewpager适配器
 * Created by Administrator on 2016/8/18.
 */
public class UC_ImageViewPagerAdapter extends PagerAdapter {
    private List<UC_CarDetail_TitleBean.ImgUrlsBean> mUC_SelectImageJavabeanList;
    private Context mContext;

    public UC_ImageViewPagerAdapter(Context context,List<UC_CarDetail_TitleBean.ImgUrlsBean> mUC_SelectImageJavabeanList) {
        this.mContext = context;
        this.mUC_SelectImageJavabeanList = mUC_SelectImageJavabeanList;
    }

    public UC_ImageViewPagerAdapter(List<UC_CarDetail_TitleBean.ImgUrlsBean> mUC_SelectImageJavabeanList) {
        this.mUC_SelectImageJavabeanList = mUC_SelectImageJavabeanList;
    }
    /**
     * 获取ViewPager的个数
     */
    @Override
    public int getCount() {
        return mUC_SelectImageJavabeanList.size();
    }
    /**
     * 是否将显示的ViewPager页面与instantiateItem返回的对象进行关联 这个方法是必须实现的
     */
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
    /**
     * 从View集合中获取对应索引的元素, 并添加到ViewPager中
     */
    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        Frame_DefaultPhotoView view = new Frame_DefaultPhotoView(mContext);
        view.enable();
        view.setScaleType(ImageView.ScaleType.FIT_CENTER);

        // 加载图片的选项
        DisplayImageOptions displayImageOptions  = new DisplayImageOptions.Builder()
                .cacheInMemory(false)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .imageScaleType(ImageScaleType.EXACTLY)
                .showImageForEmptyUri(R.mipmap.uc_select_image_loading)
                .showImageOnLoading(R.mipmap.uc_select_image_loading)
                .showImageOnFail(R.mipmap.uc_select_image_loading).build();
        ImageLoader.getInstance().displayImage(mUC_SelectImageJavabeanList.get(position).getImgUrl(), view, displayImageOptions);
        ((ViewPager) container).addView(view, params);
        return view;
    }
    /**
     * 从ViewPager中删除集合中对应索引的View对象
     */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((View) object);
    }
}
