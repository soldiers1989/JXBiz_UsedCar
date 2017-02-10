package com.etong.android.jxappfours.find_car.grand.view;

import android.app.Activity;
import android.view.View;

import com.etong.android.jxappfours.R;

/**
 * Created by Ellison.Sun on 2016/8/19.
 */
public class BottomAppoint {

    View mBottomGroup;

    Activity mContainer;            // 容器
    View default_appoint_drive;
    View default_appoint_order;
    View default_appoint_credit;

    public BottomAppoint(View container) {
        if (null == container) {
            return;
        }

        View bottomGroup = container.findViewById(R.id.default_appoint);
        initView(bottomGroup);

    }

    public BottomAppoint(Activity container) {
        mContainer = container;
        if (null == container) {
            return;
        }
        View titleBar = container.findViewById(R.id.default_appoint);
        initView(titleBar);
    }

    private void initView(View bottomGroup) {
        if (null == bottomGroup) {
            return;
        }
        mBottomGroup = bottomGroup;

        default_appoint_drive = bottomGroup.findViewById(R.id.default_appoint_drive);
        default_appoint_order = bottomGroup.findViewById(R.id.default_appoint_order);
        default_appoint_credit = bottomGroup.findViewById(R.id.default_appoint_credit);
    }

    /**
     * 为试驾预约添加监听事件
     * @param listener
     */
    public void setDriveOnclikListener(View.OnClickListener listener) {
        if (default_appoint_drive!=null) {
            default_appoint_drive.setOnClickListener(listener);
        }
    }
    /**
     * 为订购预约添加监听事件
     * @param listener
     */
    public void setOrderOnclikListener(View.OnClickListener listener) {
        if (default_appoint_order!=null) {
            default_appoint_order.setOnClickListener(listener);
        }
    }
    /**
     * 为车贷预约添加监听事件
     * @param listener
     */
    public void setCreditOnclikListener(View.OnClickListener listener) {
        if (default_appoint_credit!=null) {
            default_appoint_credit.setOnClickListener(listener);
        }
    }


}
