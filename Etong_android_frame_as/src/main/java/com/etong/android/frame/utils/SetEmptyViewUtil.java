package com.etong.android.frame.utils;

import android.graphics.Color;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.etong.android.frame.R;

/**
 * @desc (这里用一句话描述这个类的作用)
 * @createtime 2016/12/27 - 17:17
 * @Created by wukefan.
 */
public class SetEmptyViewUtil {

    private ViewGroup content;
    private ImageView img;
    private TextView txt;

    public final int OtherView = 0;
    public final int NetworkErrorView = 1;
    public final int NullView = 2;
    public final int NoServerView = 3;
    public final int NoCollectView = 4;

    public SetEmptyViewUtil(ViewGroup content, ImageView img, TextView txt) {
        this.content = content;
        this.img = img;
        this.txt = txt;
    }

    public void setView(int type, String context, Boolean canClick) {

        switch (type) {
            case OtherView:
                showOtherView(context, canClick);
                break;
            case NetworkErrorView:
                showNetworkErrorView();
                break;
            case NullView:
                showNullView();
                break;
            case NoServerView:
                showNoServerView();
                break;
            case NoCollectView:
                showNoCollectView();
                break;
        }

    }

    public void showNetworkErrorView() {
        txt.setText("点击屏幕重试");
        content.setClickable(true);
        txt.setTextColor(Color.parseColor("#80310B"));
        img.setBackgroundResource(R.drawable.ic_network_error);
    }

    public void showNullView() {
        img.setBackgroundResource(R.drawable.ic_no_data);
        txt.setText("");
        content.setClickable(false);
    }

    public void showNoServerView() {
        txt.setText("Sorry,您访问的页面找不到了......");
        content.setClickable(false);
        txt.setTextColor(Color.parseColor("#13497f"));
        img.setBackgroundResource(R.drawable.ic_no_address);
    }

    public void showOtherView(String context, boolean isCanClick) {
        txt.setText(context);
        content.setClickable(isCanClick);
        txt.setTextColor(Color.parseColor("#A0A0A0"));
        img.setBackgroundResource(R.drawable.default_empty_listview);
    }
    public void showNoCollectView() {
        img.setBackgroundResource(R.drawable.ic_no_collection);
        txt.setText("");
        content.setClickable(false);
    }
}
