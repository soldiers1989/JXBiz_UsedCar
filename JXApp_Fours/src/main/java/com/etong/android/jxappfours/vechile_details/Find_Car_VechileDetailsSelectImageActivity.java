package com.etong.android.jxappfours.vechile_details;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;

import com.etong.android.frame.subscriber.BaseSubscriberActivity;
import com.etong.android.frame.utils.SerializableObject;
import com.etong.android.jxappfours.R;
import com.etong.android.jxappfours.order.FO_OrderActivity;
import com.etong.android.jxappfours.vechile_details.adapter.ImageViewPagerAdapter;
import com.etong.android.jxappfours.vechile_details.javabeam.Find_Car_VechileDetailsImageBean;
import com.etong.android.jxappfours.vechile_details.widget.Find_car_ViewPager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 查看图片的页面
 * Created by Administrator on 2016/8/17.
 */
public class Find_Car_VechileDetailsSelectImageActivity extends BaseSubscriberActivity {

    private ImageButton back_button;
    private TextView image_count;
    private TextView image_count_sum;
    private ViewPager image_viewpager;
    private TextView title;
    private RadioButton facade;
    private RadioButton center_control;
    private RadioButton seat;
    private RadioButton detail;
    private RadioButton feature;
    private Button enquiry;
    private RadioButton[] mBtns;
    private ImageViewPagerAdapter mImageViewPagerAdapter;
    private List<Integer> position = new ArrayList<>();
    private List<Find_Car_VechileDetailsImageBean.PhotoListBean> imagList;
    private String selectImage;
    private int selectVid;
    private int selectCarsetId;
    private List<Find_Car_VechileDetailsImageBean> list;
    private int bottomPosition;
    private String brand;

    @Override
    protected void onInit(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.find_car_vechile_details_select_image_activity);
        initView();
//        initData();
        //得到传过来的配置
        Bundle bundle = getIntent().getExtras();
        SerializableObject serializableMap = (SerializableObject) bundle
                .get("dataMap");
        Map map = (Map) serializableMap.getObject();

        String titleName = (String) map.get("titleName");
        int pos = (Integer) map.get("imagePos");
        list = (List<Find_Car_VechileDetailsImageBean>) map.get("allData");
        brand = (String) map.get("brand");

        bottomPosition = setPosition(titleName);
        //list对应的位置
        for (int i = 0; i < list.size(); i++) {
            position.add(setPosition(list.get(i).getType()));
        }

        imagList = initSelectImage(bottomPosition);

        title.setText(imagList.get(pos).getName() + "");
        image_viewpager.setCurrentItem(pos);
        selectImage = imagList.get(pos).getUrl();//选中的图片
        selectCarsetId = imagList.get(pos).getCarsetId();
        if (null != imagList.get(pos).getVid()) {
            selectVid = imagList.get(pos).getVid();
        }
        mBtns[bottomPosition].setChecked(true);

        //判断是否有图片为空的情况
        for (int i = 0; i < 6; i++) {
            setImage(i);
        }

    }

    public int setPosition(String name) {
        int pos = -1;
        if (name.contains("外观")) {
            pos = 0;
        }
        if (name.contains("中控")) {
            pos = 1;
        }
        if (name.contains("座椅")) {
            pos = 2;
        }
        if (name.contains("图解")) {
            pos = 3;
        }
        if (name.contains("活动")) {
            pos = 4;
        }
        if (name.contains("细节")) {
            pos = 5;
        }
        return pos;
    }

    //初始化控件
    protected void initView() {
        back_button = findViewById(R.id.find_car_select_image_ib_back_button, ImageButton.class);
        image_count = findViewById(R.id.find_car_select_image_txt_count, TextView.class);
        image_count_sum = findViewById(R.id.find_car_select_image_txt_count_sum, TextView.class);
//        image_viewpager = findViewById(R.id.find_car_select_image_viewpager, ViewPager.class);
        image_viewpager = findViewById(R.id.find_car_select_image_viewpager, Find_car_ViewPager.class);

        //viewpager页面滑动监听
        image_viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                //设置页面改变的页面数
                image_count.setText(position + 1 + "");
                //设置页面改变的title
                title.setText(imagList.get(position).getName() + "");
                selectImage = imagList.get(position).getUrl();
                selectCarsetId = imagList.get(position).getCarsetId();
                if (null != imagList.get(position).getVid()) {
                    selectVid = imagList.get(position).getVid();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mBtns = new RadioButton[6];
        ViewGroup titleBarBackGroup = findViewById(R.id.titlebar_default, ViewGroup.class);
        titleBarBackGroup.setBackgroundColor(Color.TRANSPARENT);
        title = findViewById(R.id.find_car_select_image_txt_title, TextView.class);
        mBtns[0] = facade = findViewById(R.id.find_car_select_image_rb_facade, RadioButton.class);//外观
        mBtns[1] = center_control = findViewById(R.id.find_car_select_image_rb_center_control, RadioButton.class);//中控
        mBtns[2] = seat = findViewById(R.id.find_car_select_image_rb_seat, RadioButton.class);//座椅
        mBtns[3] = seat = findViewById(R.id.find_car_select_image_rb_chart, RadioButton.class);//图解
        mBtns[4] = seat = findViewById(R.id.find_car_select_image_rb_activity, RadioButton.class);//活动
        mBtns[5] = detail = findViewById(R.id.find_car_select_image_rb_detail, RadioButton.class);//细节

        enquiry = findViewById(R.id.find_car_select_image_btn_enquiry, Button.class);//询价


        addClickListener(R.id.find_car_select_image_ib_back_button);
        addClickListener(R.id.find_car_select_image_rb_facade);
        addClickListener(R.id.find_car_select_image_rb_center_control);
        addClickListener(R.id.find_car_select_image_rb_seat);
        addClickListener(R.id.find_car_select_image_rb_chart);
        addClickListener(R.id.find_car_select_image_rb_activity);
        addClickListener(R.id.find_car_select_image_rb_detail);
        addClickListener(R.id.find_car_select_image_btn_enquiry);


    }


    //得到数据的方法
//    protected  void initData(){
//        JSONArray array = JSONArray.parseArray(Find_Car_VechileDatailsSelectImageJsonData.getJsonArray2());
//        for (int i = 0; i < array.size(); i++) {
//            Find_Car_VechileDatailsSelectImage mSelectImageJavaBean = JSON.toJavaObject(
//                    array.getJSONObject(i), Find_Car_VechileDatailsSelectImage.class);
//            mSelectImageList.add(mSelectImageJavaBean);
//        }
//        for(int j=0;j<mSelectImageList.size();j++){
//            position.add(mSelectImageList.get(j).getPosition());
//        }
//    }

    //判断是否有图片为空的情况
    protected void setImage(int pos) {
        int num = -1;
        for (int i = 0; i < position.size(); i++) {
            if (position.get(i) == pos) {
                num = list.get(i).getPhotoList().size();
                break;
            }
        }
        if (num == 0 || num == -1) {
//            mBtns[pos].setTextColor(Find_Car_VechileDetailsSelectImageActivity.this.getResources().getColorStateList(R.color.gray_bg));
            mBtns[pos].setTextColor(Color.parseColor("#787878"));
            mBtns[pos].setClickable(false);

        }
    }


    //点击查看图片的方法
    protected List<Find_Car_VechileDetailsImageBean.PhotoListBean> initSelectImage(int pos) {
        image_viewpager.setCurrentItem(0);//设置当前位置

        List<Find_Car_VechileDetailsImageBean.PhotoListBean> image = new ArrayList<>();
        for (int i = 0; i < position.size(); i++) {
            if (position.get(i) == pos) {
                image = list.get(i).getPhotoList();
//                image.addAll(mSelectImageList.get(i).getImagelist());
            }
        }

        if (image.size() > 0) {
            image_count.setText(1 + "");
            image_viewpager.setAdapter(new ImageViewPagerAdapter(this, image));
            //图片总数
            image_count_sum.setText(image.size() + "");
        } else {
            //没有图片就设置radiobutton不能点击
            mBtns[pos].setClickable(false);
        }
        return image;
    }

    @Override
    protected void onClick(View view) {
        super.onClick(view);
        if (view.getId() == R.id.find_car_select_image_ib_back_button) {
            back();
        } else if (view.getId() == R.id.find_car_select_image_rb_facade) {
            imagList = initSelectImage(0);
            if (0 != imagList.size()) {
                title.setText(imagList.get(0).getName() + "");
                selectImage = imagList.get(0).getUrl();
                selectCarsetId = imagList.get(0).getCarsetId();
                if (null != imagList.get(0).getVid()) {
                    selectVid = imagList.get(0).getVid();
                }
            }
        } else if (view.getId() == R.id.find_car_select_image_rb_center_control) {
            imagList = initSelectImage(1);
            if (0 != imagList.size()) {
                title.setText(imagList.get(0).getName() + "");
                selectImage = imagList.get(0).getUrl();
                selectCarsetId = imagList.get(0).getCarsetId();
                if (null != imagList.get(0).getVid()) {
                    selectVid = imagList.get(0).getVid();
                }
            }
        } else if (view.getId() == R.id.find_car_select_image_rb_seat) {
            imagList = initSelectImage(2);
            if (0 != imagList.size()) {
                title.setText(imagList.get(0).getName() + "");
                selectImage = imagList.get(0).getUrl();
                selectCarsetId = imagList.get(0).getCarsetId();
                if (null != imagList.get(0).getVid()) {
                    selectVid = imagList.get(0).getVid();
                }
            }
        } else if (view.getId() == R.id.find_car_select_image_rb_chart) {
            imagList = initSelectImage(3);
            if (0 != imagList.size()) {
                title.setText(imagList.get(0).getName() + "");
                selectImage = imagList.get(0).getUrl();
                selectCarsetId = imagList.get(0).getCarsetId();
                if (null != imagList.get(0).getVid()) {
                    selectVid = imagList.get(0).getVid();
                }
            }
        } else if (view.getId() == R.id.find_car_select_image_rb_activity) {
            imagList = initSelectImage(4);
            if (0 != imagList.size()) {
                title.setText(imagList.get(0).getName() + "");
                selectImage = imagList.get(0).getUrl();
                selectCarsetId = imagList.get(0).getCarsetId();
                if (null != imagList.get(0).getVid()) {
                    selectVid = imagList.get(0).getVid();
                }
            }
        } else if (view.getId() == R.id.find_car_select_image_rb_detail) {
            imagList = initSelectImage(5);
            if (0 != imagList.size()) {
                title.setText(imagList.get(0).getName() + "");
                selectImage = imagList.get(0).getUrl();
                selectCarsetId = imagList.get(0).getCarsetId();
                if (null != imagList.get(0).getVid()) {
                    selectVid = imagList.get(0).getVid();
                }
            }
        } else if (view.getId() == R.id.find_car_select_image_btn_enquiry) {
//            Intent intent = new Intent(this, Fours_Order_OrderActivity.class);
            Intent intent = new Intent(this, FO_OrderActivity.class);
            intent.putExtra("vid", selectVid + "");
            intent.putExtra("flag", 4);
            intent.putExtra("carsetId", selectCarsetId);
            intent.putExtra("carImage", selectImage);
            intent.putExtra("vTitleName", title.getText().toString());
            intent.putExtra("brand", brand);
            intent.putExtra("isSelectCar", true);
            startActivity(intent);
        }
    }
}
