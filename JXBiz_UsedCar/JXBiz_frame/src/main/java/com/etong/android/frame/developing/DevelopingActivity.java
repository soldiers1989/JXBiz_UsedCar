package com.etong.android.frame.developing;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.TextView;

import com.etong.android.frame.R;
import com.etong.android.frame.subscriber.BaseSubscriberActivity;
import com.etong.android.frame.widget.TitleBar;

/**
 * Created by Ellison.Sun on 2016/9/2.
 */
public class DevelopingActivity extends BaseSubscriberActivity{
    public static String TITLE_NAME;
    public static String EMPTY_CHAR;
    private TextView developing_page_describe_text;

    @Override
    protected void onInit(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_developing_page_content);

        initView();
    }

    /**
     * 初始化操作
     */
    private void initView() {

        developing_page_describe_text = (TextView) findViewById(R.id.developing_page_describe_text);

        Intent intent = getIntent();
//        boolean emptyChar = intent.getBooleanExtra(DevelopingActivity.EMPTY_CHAR, false);
        String stringExtra = intent.getStringExtra(DevelopingActivity.TITLE_NAME);

        TitleBar mTitleBar = new TitleBar(this);
        mTitleBar.setmTitleBarBackground("#FFFFFF");
        mTitleBar.setTitleTextColor("#000000");
        mTitleBar.showNextButton(false);
        mTitleBar.showBackButton(true);

        if (!TextUtils.isEmpty(stringExtra)) {
            mTitleBar.setTitle(stringExtra);
        } else {
            mTitleBar.setTitle("接入中");
        }

//        if (emptyChar) {
//            developing_page_describe_text.setText("暂无");
//        } else {
////            developing_page_describe_text.setText("接入中");
//        }


    }
}
