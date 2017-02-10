package com.etong.android.jxappusedcar.adapter;

import android.app.ActionBar;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.annotation.IdRes;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.etong.android.frame.common.ImageProvider;

import com.etong.android.frame.widget.photoview.Frame_DefaultPhotoView;
import com.etong.android.jxappusedcar.R;
import com.etong.android.jxappusedcar.javabean.UC_SelectImageJavabean;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.List;

/**
 * 自定义viewpager适配器
 * Created by Administrator on 2016/8/18.
 */
public class UC_ImageViewPagerAdapter extends PagerAdapter {

    private List<UC_SelectImageJavabean> mUC_SelectImageJavabeanList;
    private Context mContext;

    public UC_ImageViewPagerAdapter(Context context,List<UC_SelectImageJavabean> mUC_SelectImageJavabeanList) {
        this.mContext = context;
        this.mUC_SelectImageJavabeanList = mUC_SelectImageJavabeanList;
    }

    public UC_ImageViewPagerAdapter(List<UC_SelectImageJavabean> mUC_SelectImageJavabeanList) {
        this.mUC_SelectImageJavabeanList = mUC_SelectImageJavabeanList;
    }


    /**
     * 获取ViewPager的个数
     */
    @Override
    public int getCount() {
//        //设置成最大，使用户看不到边界
//        return Integer.MAX_VALUE;
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

        View view = LayoutInflater.from(mContext).inflate(R.layout.used_car_image_text,null);
//        View view =View.inflate(mContext,R.layout.used_car_image_text,null);
        Frame_DefaultPhotoView imageView= (Frame_DefaultPhotoView) view.findViewById(R.id.used_car_image);
        imageView.enable();
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        TextView textView= (TextView) view.findViewById(R.id.used_car_title);
        // 加载图片的选项
        DisplayImageOptions displayImageOptions  = new DisplayImageOptions.Builder()
                .cacheInMemory(false)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .imageScaleType(ImageScaleType.EXACTLY)
                .showImageForEmptyUri(R.mipmap.used_car_carserice_loading)
                .showImageOnLoading(R.mipmap.used_car_carserice_loading)
                .showImageOnFail(R.mipmap.used_car_carserice_loading).build();
        ImageLoader.getInstance().displayImage(mUC_SelectImageJavabeanList.get(position).getUrl(), imageView,displayImageOptions);
        textView.setText(mUC_SelectImageJavabeanList.get(position).getTitle());

        container.addView(view);

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
