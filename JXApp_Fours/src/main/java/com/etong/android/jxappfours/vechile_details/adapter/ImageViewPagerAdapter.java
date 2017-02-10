package com.etong.android.jxappfours.vechile_details.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.etong.android.frame.common.ImageProvider;
import com.etong.android.frame.widget.photoview.Frame_DefaultPhotoView;
import com.etong.android.jxappfours.R;
import com.etong.android.jxappfours.vechile_details.javabeam.Find_Car_VechileDetailsImageBean;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.List;

/**
 * 自定义viewpager适配器
 * Created by Administrator on 2016/8/18.
 */
public class ImageViewPagerAdapter extends PagerAdapter {

    private List<Find_Car_VechileDetailsImageBean.PhotoListBean> imageUrl;
    private Context mContext;

    public ImageViewPagerAdapter(Context context, List<Find_Car_VechileDetailsImageBean.PhotoListBean> url) {
        this.mContext = context;
        this.imageUrl = url;

    }

    public ImageViewPagerAdapter(List<Find_Car_VechileDetailsImageBean.PhotoListBean> url) {
        this.imageUrl = url;
    }

    /**
     * 获取ViewPager的个数
     */
    @Override
    public int getCount() {
//        //设置成最大，使用户看不到边界
//        return Integer.MAX_VALUE;
        return imageUrl.size();
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
        Frame_DefaultPhotoView view = new Frame_DefaultPhotoView(mContext);
        view.enable();
        view.setScaleType(ImageView.ScaleType.FIT_CENTER);
        DisplayImageOptions displayImageOptions  = new DisplayImageOptions.Builder()
                .cacheInMemory(false)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .imageScaleType(ImageScaleType.EXACTLY)
                .showImageForEmptyUri(R.mipmap.fours_carserice_loading)
                .showImageOnLoading(R.mipmap.fours_carserice_loading)
                .showImageOnFail(R.mipmap.fours_carserice_loading).build();
        ImageLoader.getInstance().displayImage(imageUrl.get(position).getUrl(), view, displayImageOptions);

//        ImageProvider.getInstance().loadImage(view, imageUrl.get(position).getUrl(), R.mipmap.fours_carserice_loading);

        ((ViewPager) container).addView(view);
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
