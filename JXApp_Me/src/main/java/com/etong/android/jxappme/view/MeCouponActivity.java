package com.etong.android.jxappme.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.etong.android.frame.subscriber.BaseSubscriberActivity;
import com.etong.android.frame.utils.ListAdapter;
import com.etong.android.frame.widget.TitleBar;
import com.etong.android.jxappme.JavaBeam.MeCouponItem;
import com.etong.android.jxappme.R;

/**
 * 我的优惠券页面
 * Created by Administrator on 2016/8/30.
 */
public class MeCouponActivity extends BaseSubscriberActivity {

    private TitleBar mTitleBar;
    private RadioButton mRadioButtons[] = new RadioButton[3];
    private ListView listview_coupon;
    private ListAdapter<MeCouponItem> mListAdapter;
    @Override
    protected void onInit(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_me_coupon);

        initView();
    }

    protected void initView(){
        mTitleBar =new TitleBar(this);
        mTitleBar.showNextButton(false);
        mTitleBar.setTitle("我的优惠券");

        Intent intent =getIntent();
        int mCouponNum =Integer.parseInt(intent.getStringExtra("CouponNum"));


        mRadioButtons[0] = findViewById(R.id.me_rb_unused,
                RadioButton.class);
//        mRadioButtons[0].setText("未使用("+mCouponNum+")");
        mRadioButtons[1] = findViewById(R.id.me_rb_used,
                RadioButton.class);
//        mRadioButtons[1].setText("已使用("+mCouponNum+")");
        mRadioButtons[2] = findViewById(R.id.me_rb_out_of_date,
                RadioButton.class);
//        mRadioButtons[2].setText("已过期("+mCouponNum+")");

        addClickListener(mRadioButtons);

        listview_coupon =findViewById(R.id.me_lv_coupon, ListView.class);

        // 设置数据为空的ListView显示
        View emptyListView = LayoutInflater.from(this).inflate(R.layout.default_empty_listview, null);
        ViewGroup default_empty_content = (ViewGroup) emptyListView.findViewById(R.id.default_empty_content);
        TextView default_empty_lv_textview = (TextView) emptyListView.findViewById(R.id.default_empty_lv_textview);
        default_empty_lv_textview.setText("暂无优惠券");
       /* default_empty_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //刷新数据

            }
        });*/
        ((ViewGroup)listview_coupon.getParent()).addView(emptyListView);
        listview_coupon.setEmptyView(emptyListView);






        mListAdapter = new ListAdapter<MeCouponItem>(this,
                R.layout.me_coupon_lv_item) {

            @Override
            protected void onPaint(View view, MeCouponItem data, int position) {
                ImageView coupon_image= (ImageView) view.findViewById(R.id.me_iv_coupon_image);
                TextView coupon_title= (TextView) view.findViewById(R.id.me_txt_coupon_title);
                TextView coupon_discount= (TextView) view.findViewById(R.id.me_txt_coupon_discount);
                TextView coupon_explain= (TextView) view.findViewById(R.id.me_txt_coupon_explain);
                TextView coupon_time= (TextView) view.findViewById(R.id.me_txt_coupon_time);






            }
        };




        listview_coupon.setAdapter(mListAdapter);

    }

    @Override
    protected void onClick(View view) {
        super.onClick(view);
        if(view.getId()==R.id.me_rb_unused){

        }else if(view.getId()==R.id.me_rb_used){

        }else if(view.getId()==R.id.me_rb_out_of_date){

        }
    }
}
