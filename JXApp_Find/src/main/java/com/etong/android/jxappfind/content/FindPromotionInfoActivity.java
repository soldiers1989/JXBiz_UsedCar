package com.etong.android.jxappfind.content;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.etong.android.frame.common.ImageProvider;
import com.etong.android.frame.permissions.PermissionsManager;
import com.etong.android.frame.permissions.PermissionsResultAction;
import com.etong.android.frame.publisher.HttpPublisher;
import com.etong.android.frame.subscriber.BaseSubscriberActivity;
import com.etong.android.frame.utils.ListAdapter;
import com.etong.android.frame.utils.MarkUtils;
import com.etong.android.frame.widget.TitleBar;
import com.etong.android.jxappfind.R;
import com.etong.android.jxappfind.adapter.Find_Promotion_InfoAdapter;
import com.etong.android.jxappfind.javabean.FindAllCarBean;
import com.etong.android.jxappfind.javabean.FindPromotionBean;
import com.etong.android.jxappfind.view.FindJsonAllCarData;
import com.etong.android.jxappfind.view.FindJsonPromotionCarData;
import com.etong.android.jxappfours.order.FO_OrderActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 促销车详情页
 * Created by Administrator on 2016/9/2.
 */
public class FindPromotionInfoActivity extends BaseSubscriberActivity {

    private TitleBar mTitleBar;
    private ListView allcar_list;
    private RadioButton rb_promotion;
    private RadioButton all_car;
    private ListView promotion_list;
    private RelativeLayout rl_promotion;
    private TextView service_phone;
    private ListAdapter<FindAllCarBean> mListAdapter;
    private ListAdapter<FindPromotionBean> mPromotionListAdapter;
    private HttpPublisher mHttpPublisher;
    private ImageProvider mImageProvider;
    private ViewGroup allContentLv;
    private List<FindPromotionBean> mPromotionList;
    private List<FindAllCarBean> mAllCarList;
    private Find_Promotion_InfoAdapter findInfoSalesAdapter;

    @Override
    protected void onInit(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.find_promotion_info_activity);
        initView();
        Intent intent=getIntent();
        int carseriesId=intent.getIntExtra("carseriesId",-1);
        int cartypeId= intent.getIntExtra("cartypeId",-1);

        initDatas();
        initAllDatas();
    }

    //促销车假数据
    public void initDatas() {
        mPromotionList = new ArrayList<>();
        JSONArray array = JSONArray.parseArray(FindJsonPromotionCarData.getJsonArray());
        for (int i = 0; i < array.size(); i++) {
            FindPromotionBean mFindPromotionBean = JSON.toJavaObject(
                    array.getJSONObject(i), FindPromotionBean.class);
            mPromotionList.add(mFindPromotionBean);

        }
//        mPromotionListAdapter.addAll(mPromotionList);
        findInfoSalesAdapter.updateListDatas(mPromotionList);
    }

    //促销车（全部）假数据
    public void initAllDatas() {
        mAllCarList = new ArrayList<>();
        JSONArray array = JSONArray.parseArray(FindJsonAllCarData.getJsonArray());
        for (int i = 0; i < array.size(); i++) {
            FindAllCarBean mFindAllCarBean = JSON.toJavaObject(
                    array.getJSONObject(i), FindAllCarBean.class);
            mAllCarList.add(mFindAllCarBean);

        }
        mListAdapter.addAll(mAllCarList);
    }

    protected void initView() {
        mHttpPublisher = HttpPublisher.getInstance();
        mHttpPublisher.initialize(this);
//        mFindProvider= FindProvider.getInstance();
//        mFindProvider.initialize(mHttpPublisher);
        mImageProvider = ImageProvider.getInstance();
        mImageProvider.initialize(this);
        mTitleBar = new TitleBar(this);
        mTitleBar.setTitle("促销车");
        mTitleBar.showBackButton(true);
        mTitleBar.showNextButton(false);

        rb_promotion = findViewById(R.id.find_rb_promotion, RadioButton.class);//促销车

        all_car = findViewById(R.id.find_rb_all_car, RadioButton.class);//全部车
        allContentLv = findViewById(R.id.find_lv_allcar_content_lv, ViewGroup.class);
        allcar_list = findViewById(R.id.find_lv_allcar_list, ListView.class);//所有车款
        allContentLv.setVisibility(View.GONE);
        rl_promotion = findViewById(R.id.find_rl_promotion, RelativeLayout.class);
        rl_promotion.setVisibility(View.VISIBLE);
        promotion_list = findViewById(R.id.find_lv_promotion_list, ListView.class);
        promotion_list.setVisibility(View.VISIBLE);
        service_phone = findViewById(R.id.find_txt_service_phone, TextView.class);
        service_phone.setVisibility(View.VISIBLE);
        addClickListener(R.id.find_rb_promotion);
        addClickListener(R.id.find_rb_all_car);
        addClickListener(R.id.find_txt_service_phone);

        //添加listView数据为空显示
        View emptyListView = LayoutInflater.from(this).inflate(R.layout.default_empty_listview, null);
        ViewGroup default_empty_content = (ViewGroup) emptyListView.findViewById(R.id.default_empty_content);
        TextView default_empty_lv_textview = (TextView) emptyListView.findViewById(R.id.default_empty_lv_textview);
        ImageView default_empty_img = (ImageView) emptyListView.findViewById(R.id.default_empty_img);
        default_empty_lv_textview.setText("");
        default_empty_img.setBackgroundResource(R.drawable.ic_no_data);
      /*  default_empty_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //刷新数据
                mMessageProvider.MessageType(String.valueOf(1),true);
            }
        });*/
        ((ViewGroup) promotion_list.getParent()).addView(emptyListView);
        promotion_list.setEmptyView(emptyListView);


        //添加listView数据为空显示
        View emptyListView1 = LayoutInflater.from(this).inflate(R.layout.default_empty_listview, null);
        ViewGroup default_empty_content1 = (ViewGroup) emptyListView1.findViewById(R.id.default_empty_content);
        TextView default_empty_lv_textview1 = (TextView) emptyListView1.findViewById(R.id.default_empty_lv_textview);
        ImageView default_empty_img1 = (ImageView) emptyListView.findViewById(R.id.default_empty_img);
        default_empty_lv_textview1.setText("");
        default_empty_img1.setBackgroundResource(R.drawable.ic_no_data);
      /*  default_empty_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //刷新数据
                mMessageProvider.MessageType(String.valueOf(1),true);
            }
        });*/
        ((ViewGroup) allcar_list.getParent()).addView(emptyListView1);
        allcar_list.setEmptyView(emptyListView1);


        //全部车款适配器
        mListAdapter = new ListAdapter<FindAllCarBean>(this,
                R.layout.find_all_car_list_item_activity) {

            @Override
            protected void onPaint(View view, final FindAllCarBean data, int position) {
                TextView group = findViewById(view, R.id.find_txt_year,
                        TextView.class);
                ImageView image = (ImageView) view.findViewById(R.id.find_iv_image);
                TextView title = (TextView) view.findViewById(R.id.find_txt_title);
                TextView guideprice = (TextView) view.findViewById(R.id.find_txt_guideprice);


                if (data.isFirst()) {
                    group.setVisibility(View.VISIBLE);
                    group.setText(data.getYear() + "款");
                } else {
                    group.setVisibility(View.GONE);
                }

                mImageProvider.loadImage(image, data.getImage(), R.mipmap.find_guesslike_icon);
                title.setText(data.getTitle());
                guideprice.setText(data.getGuidPrice() + "万");

                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        Intent intent = new Intent(FindPromotionInfoActivity.this, Fours_Order_OrderActivity.class);
                        Intent intent = new Intent(FindPromotionInfoActivity.this, FO_OrderActivity.class);
                        intent.putExtra("flag", 4);
                        intent.putExtra("isSelectCar", true);
                        intent.putExtra("vTitleName", data.getTitle());
                        intent.putExtra("carsetId", -1);
                        intent.putExtra("vid", data.getId());
                        intent.putExtra("carImage", data.getImage());
                        startActivity(intent);
                    }
                });


            }
        };
        allcar_list.setAdapter(mListAdapter);

        //促销车款适配器
     /*   mPromotionListAdapter = new ListAdapter<FindPromotionBean>(this,
                R.layout.find_promotion_list_item_activity) {

            @Override
            protected void onPaint(View view, final FindPromotionBean data, int position) {
                TextView title = findViewById(view, R.id.find_txt_promotion_title,
                        TextView.class);
                TextView color = findViewById(view, R.id.find_txt_promotion_color,
                        TextView.class);
                TextView guide_price = findViewById(view,
                        R.id.find_txt_guideprice, TextView.class);
                TextView promotion_price = findViewById(view,
                        R.id.find_txt_promotion_price, TextView.class);
                TextView save_money = findViewById(view,
                        R.id.find_txt_save_money, TextView.class);
                FindTimerTextView cuxiaotime = findViewById(view,
                        R.id.find_txt_time, FindTimerTextView.class);
                TextView comment1 = findViewById(view,
                        R.id.find_txt_comment1, TextView.class);
                TextView comment2 = findViewById(view,
                        R.id.find_txt_comment2, TextView.class);
                Button btn = findViewById(view, R.id.find_btn_submit,
                        Button.class);
                title.setText(data.getTitle());
                color.setText(data.getColors());
                guide_price.setText(data.getGuide_price());
                promotion_price.setText(data.getCuxiaojia());
                save_money.setText(data.getSave_money());
                cuxiaotime.startCountDown(data.getEnd_seconds() * 1000, 1000);
                comment1.setText(data.getComment1());
                comment2.setText(data.getComment2());

                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(FindPromotionInfoActivity.this, Fours_Order_OrderActivity.class);
                        intent.putExtra("flag", 4);
                        intent.putExtra("isSelectCar", true);
                        intent.putExtra("vTitleName", data.getTitle());
                        intent.putExtra("carsetId", -1);
                        intent.putExtra("vid", data.getCar_id());
                        intent.putExtra("carImage", data.getImage());
                        startActivity(intent);
                    }
                });

            }
        };*/
        findInfoSalesAdapter = new Find_Promotion_InfoAdapter(this);
//        promotion_list.setAdapter(mPromotionListAdapter);
        promotion_list.setAdapter(findInfoSalesAdapter);

    }

    @Override
    protected void onClick(View view) {
        super.onClick(view);
        if (view.getId() == R.id.find_rb_promotion) {
            allContentLv.setVisibility(View.GONE);
            rl_promotion.setVisibility(View.VISIBLE);
//            promotion_list.setVisibility(View.VISIBLE);
        } else if (view.getId() == R.id.find_rb_all_car) {
            allContentLv.setVisibility(View.VISIBLE);
            rl_promotion.setVisibility(View.GONE);
//            promotion_list.setVisibility(View.GONE);
        } else if (view.getId() == R.id.find_txt_service_phone) {
            PermissionsManager.getInstance()
                    .requestPermissionsIfNecessaryForResult(this,
                            new String[]{Manifest.permission.CALL_PHONE},
                            new PermissionsResultAction() {

                                @Override
                                public void onGranted() {
                                    callPhoneDialog();
                                }

                                @Override
                                public void onDenied(String permission) {
                                    toastMsg("授权失败，无法完成操作！");
                                    return;
                                }
                            });

        }

    }

    /**
     * 打电话
     */
    private void callPhoneDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage("您将要拨打服务热线:" + MarkUtils.CUSTOMER_SERVICE_PHONE);
        builder.setTitle("服务热线");
        builder.setPositiveButton("拨打服务热线",
                new android.content.DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Intent intent = new Intent(Intent.ACTION_CALL, Uri
                                .parse("tel:"
                                        + MarkUtils.CUSTOMER_SERVICE_PHONE));
                        FindPromotionInfoActivity.this.startActivity(intent);
                    }
                });
        builder.setNegativeButton("不了,谢谢",
                new android.content.DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builder.create().show();
    }


}

