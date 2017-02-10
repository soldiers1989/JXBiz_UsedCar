package com.etong.android.jxbizusedcar.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.etong.android.frame.publisher.HttpMethod;
import com.etong.android.frame.publisher.HttpPublisher;
import com.etong.android.frame.request_init.UC_HttpTag;
import com.etong.android.frame.user.UC_FrameEtongApplication;
import com.etong.android.frame.widget.TitleBar;
import com.etong.android.jxbizusedcar.R;
import com.etong.android.jxbizusedcar.subscriber.UC_SubscriberActivity;

import org.simple.eventbus.Subscriber;

import java.util.HashMap;
import java.util.Map;

/**
 * 意见反馈
 * Created by Administrator on 2016/10/12.
 */

public class UC_FeedbackActivity extends UC_SubscriberActivity {
    private EditText mQuestionContent;//问题内容
    //    private EditText mTelPhone;//电话号码
    private Button mSubmit;//提交
    private TitleBar mTitleBar;

    @Override
    protected void myInit(Bundle bundle) {
        setContentView(R.layout.uc_activity_feedback_content);
        initView();
    }

    //初始化控件
    private void initView() {

        mTitleBar = new TitleBar(this);
        mTitleBar.setTitle("意见反馈");

        mQuestionContent = findViewById(R.id.uc_et_question_describe, EditText.class);
//        mTelPhone = findViewById(R.id.uc_et_telphone_input, EditText.class);
        mSubmit = findViewById(R.id.uc_btn_submit, Button.class);

        addClickListener(mSubmit);
    }

    @Override
    protected void onClick(View view) {
        switch (view.getId()) {
            case R.id.uc_btn_submit:
                feedbackRequest();
                break;

        }
    }

    //调用意见反馈接口
    private void feedbackRequest() {
        String content = mQuestionContent.getText().toString().trim();
        if (content.isEmpty() || content.equals("")) {
            toastMsg("问题描述或意见不能为空！");
            return;
        }
        if (content.length() > 300) {
            toastMsg("输入问题或意见过长！");
            return;
        }
        mSubmit.setClickable(false);
        Map<String, String> map = new HashMap<>();
        map.put("f_org_id", "001");
        map.put("f_feedbackdesc", content);
        if (UC_FrameEtongApplication.getApplication().isLogin()) {
            map.put("f_phone", UC_FrameEtongApplication.getApplication().getUserInfo().getF_phone());
            map.put("f_userid", UC_FrameEtongApplication.getApplication().getUserInfo().getF_userid());
        }
        mProvider.feedback(map);
        loadStart(null, 0);
    }

    //意见反馈接口回调
    @Subscriber(tag = UC_HttpTag.FEEDBACK)
    public void feedbackResult(HttpMethod method) {
        String status = method.data().getString("status");
        String msg = method.data().getString("msg");
        mSubmit.setClickable(true);
        loadFinish();
        if (status.equals("true")) {
            toastMsg("提交成功！");
            mQuestionContent.setText("");
        } else if (status.equals(HttpPublisher.NETWORK_ERROR)) {
            toastMsg("意见提交失败，请检查网络！");
        }
    }
}
