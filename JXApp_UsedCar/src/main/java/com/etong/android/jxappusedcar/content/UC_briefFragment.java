package com.etong.android.jxappusedcar.content;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.etong.android.frame.subscriber.BaseSubscriberActivity;
import com.etong.android.frame.subscriber.BaseSubscriberFragment;
import com.etong.android.jxappusedcar.R;

import org.simple.eventbus.Subscriber;

/**
 * @desc 简要结论fragment
 * @createtime 2016/10/10 - 15:38
 * @Created by xiaoxue.
 */

public class UC_briefFragment extends BaseSubscriberFragment {

    private ImageView brief_photo;
    private TextView title_brief;

    @Override
    protected View onInit(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.used_car_details_brief_fragment,
                container, false);
        initView(view);
        return view;
    }

    /**
     * @desc 初始化控件
     * @createtime 2016/10/10 - 16:09
     * @author xiaoxue
     */

    protected void initView(View view){
        brief_photo =(ImageView)view.findViewById(R.id.used_car_iv_brief_photo);
        title_brief =(TextView)view.findViewById(R.id.used_car_tv_brief);
    }

    @Subscriber(tag="brief")
    public void getData(String title){
        title_brief.setText(title);
    }

    public void setShowInfo(String dataInfo) {
        if(!TextUtils.isEmpty(dataInfo))
            title_brief.setText(dataInfo);
    }
}
