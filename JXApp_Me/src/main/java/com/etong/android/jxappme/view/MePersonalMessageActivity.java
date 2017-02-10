package com.etong.android.jxappme.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.etong.android.frame.subscriber.BaseSubscriberActivity;
import com.etong.android.frame.widget.TitleBar;
import com.etong.android.jxappme.R;

/**
 * Created by Administrator on 2016/9/8 0008.
 */
public class MePersonalMessageActivity extends BaseSubscriberActivity {


    private TitleBar mTitleBar;
    private TextView mEmptyTxt;
    private ImageView mEmptyImg;

    @Override
    protected void onInit(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_me_message);

        mTitleBar = new TitleBar(this);
        mTitleBar.setTitle("个人消息");
        mTitleBar.showBottomLin(false);

        initView();
    }

    private void initView() {

        mEmptyTxt = findViewById(R.id.default_empty_lv_textview, TextView.class);
        mEmptyImg = findViewById(R.id.default_empty_img, ImageView.class);
//        mEmptyTxt.setText("暂无消息");
        mEmptyTxt.setText("");
        mEmptyImg.setBackgroundResource(R.drawable.ic_no_data);
    }
}
