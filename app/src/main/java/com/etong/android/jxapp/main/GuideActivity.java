package com.etong.android.jxapp.main;

import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.etong.android.frame.common.ImageProvider;
import com.etong.android.frame.subscriber.BaseSubscriberActivity;
import com.etong.android.frame.user.FrameEtongApplication;
import com.etong.android.frame.utils.ActivitySkipUtil;
import com.etong.android.jxapp.R;
import com.etong.android.jxapp.main.adapter.GuideViewAdapter;
import com.etong.android.jxapp.main.utils.MainUtils;

import java.util.ArrayList;
import java.util.List;

public class GuideActivity extends BaseSubscriberActivity {
/*
  ##################################################################################################
  ##                                        类中的变量                                            ##
  ##################################################################################################
*/

    public static String IS_GUIDE = "96512";

    private ViewPager viewPage;
    private List<View> list;
    // 底部小点的图片
    private LinearLayout llPoint;
    //立即进入按钮
    private TextView textView;
    // 图片
    private String[] imageView = {"main_buy_car", "main_safe", "main_quick_loan"};
    private ImageProvider provider;
    private ViewGroup content;

    @Override
    protected void onInit(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_main_guid);

        provider = ImageProvider.getInstance();

        initView();
        initData();
//        addPoint();
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
        content = (ViewGroup) findViewById(R.id.main_guide_content);
        viewPage = (ViewPager) findViewById(R.id.main_guide_viewpage);
        llPoint = (LinearLayout) findViewById(R.id.main_guide_llPoint);
        textView = (TextView) findViewById(R.id.main_guide_Tv);

        initoper();
    }

    /**
     * @desc (这里用一句话描述这个方法的作用)
     * @user sunyao
     * @createtime 2016/12/5 - 14:45
     * @param 
     * @return 
     */
    private void initoper() {
        // 进入按钮
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                ActivitySkipUtil.skipActivity(GuideActivity.this, MainContentActivity.class);
                FrameEtongApplication.getApplication().setIsGuided(GuideActivity.this, GuideActivity.IS_GUIDE);
                finish();
            }
        });

        // 2.监听当前显示的页面，将对应的小圆点设置为选中状态，其它设置为未选中状态
        viewPage.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                monitorPoint(position);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });

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
        list = new ArrayList<View>();
        // 将imageview添加到view
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        for (int i = 0; i < imageView.length; i++) {
            ImageView iv = new ImageView(this);
            iv.setLayoutParams(params);
            iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
            // 加载本地的图片
            String url = MainUtils.getImageUrl(imageView[i]);
            provider.loadImage(iv, url);

            list.add(iv);
        }
        // 加入适配器
        viewPage.setAdapter(new GuideViewAdapter(list));
    }

    /**
     * @desc (添加小圆点)
     * @user sunyao
     * @createtime 2016/12/5 - 14:46
     * @param
     * @return
     */
    private void addPoint() {
        // 1.根据图片多少，添加多少小圆点
        for (int i = 0; i < imageView.length; i++) {
            LinearLayout.LayoutParams pointParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            if (i < 1) {
                pointParams.setMargins(0, 0, 0, 0);
            } else {
                pointParams.setMargins(10, 0, 0, 0);
            }
            ImageView iv = new ImageView(this);
            iv.setLayoutParams(pointParams);
            iv.setBackgroundResource(R.drawable.main_guide_point_normal);
            llPoint.addView(iv);
        }
        llPoint.getChildAt(0).setBackgroundResource(R.drawable.main_guide_point_selector);
    }

    /**
     * @desc (判断小圆点)
     * @user sunyao
     * @createtime 2016/12/5 - 14:46
     * @param
     * @return
     */
    private void monitorPoint(int position) {
//        for (int i = 0; i < imageView.length; i++) {
//            if (i == position) {
//                llPoint.getChildAt(position).setBackgroundResource(
//                        R.drawable.main_guide_point_selector);
//            } else {
//                llPoint.getChildAt(i).setBackgroundResource(
//                        R.drawable.main_guide_point_normal);
//            }
//        }
        // 3.当滑动到最后一个添加按钮点击进入，
        if (position == imageView.length - 1) {
            textView.setVisibility(View.VISIBLE);
        } else {
            textView.setVisibility(View.GONE);
        }
    }

/*
  ##################################################################################################
  ##                                     点击事件的处理                                           ##
  ##################################################################################################
*/

    /**
     * 控件的点击事件
     */
    @Override
    protected void onClick(View view) {

    }


/*
  ##################################################################################################
  ##                              使用的逻辑方法，以及对外公开的方法                              ##
  ##                                      请求数据、获取数据                                      ##
  ##################################################################################################
*/





/*
  ##################################################################################################
  ##                            类中根据成员设置的getter和setter方法                              ##
  ##################################################################################################
*/

}
