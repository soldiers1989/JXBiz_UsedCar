package com.etong.android.jxappfours.find_car.grand.view;

import android.app.Dialog;

/**
 * @author : by sunyao
 * @ClassName : FC_RoadCallPhoneDialog
 * @Description : (这里用一句话描述这个类的作用)
 * @date : 2016/10/10 - 8:39
 */

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.etong.android.jxappfours.R;

/**
 * @desc (因为在library中识别不了系统自带的dialog，所以自定义一个加载布局文件)
 * @user sunyao
 * @createtime 2016/10/10 - 8:40
 */
public class FC_RoadCallPhoneDialog extends Dialog {

    private String titlePhoneNum;
    private Context mContext;

    public FC_RoadCallPhoneDialog(Context context,String phoneNum) {
        super(context);
        this.mContext = context;
        this.titlePhoneNum = phoneNum;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fc_road_assistance_dialog);

        // 设置固定的标题
        setTitle("服务热线");

        TextView dialog_phone = (TextView) findViewById(R.id.fc_road_assistance_dialog_phone);
        TextView dialog_cancle = (TextView) findViewById(R.id.fc_road_assistance_dialog_cancle);
        TextView dialog_confime = (TextView) findViewById(R.id.fc_road_assistance_dialog_confime);

        if(!TextUtils.isEmpty(titlePhoneNum)) {
            dialog_phone.setText(titlePhoneNum);
        }
        dialog_cancle.setOnClickListener(clickListenerCancle);
        dialog_confime.setOnClickListener(clickListenerConfime);
    }

    // 取消拨打电话
    private View.OnClickListener clickListenerCancle = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            FC_RoadCallPhoneDialog.this.dismiss();
        }
    };

    // 确定拨打电话按钮
    private View.OnClickListener clickListenerConfime = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            FC_RoadCallPhoneDialog.this.dismiss();
            if(TextUtils.isEmpty(titlePhoneNum)) {
                return;
            }
            final String convsePhone = titlePhoneNum.replace("-", "");
            try {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri
                        .parse("tel:"
                                + convsePhone));
                mContext.startActivity(intent);
            } catch (SecurityException e) {

            }
        }
    };

}