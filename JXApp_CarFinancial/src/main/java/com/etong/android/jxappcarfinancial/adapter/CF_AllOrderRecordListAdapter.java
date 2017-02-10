package com.etong.android.jxappcarfinancial.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.etong.android.frame.utils.DefinedToast;
import com.etong.android.jxappcarfinancial.R;
import com.etong.android.jxappcarfinancial.activity.CF_OrderRecordActivity;
import com.etong.android.jxappcarfinancial.bean.CF_AllOrderRecordBean;
import com.etong.android.jxappcarfinancial.bean.CF_AllOrderRecordCountBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @desc (所有订单类列表Adapter)
 * @createtime 2016/11/17 0017--18:52
 * @Created by wukefan.
 */
public class CF_AllOrderRecordListAdapter extends BaseAdapter {

    private List<CF_AllOrderRecordBean> mDatas;
    private Context mContext;

    public CF_AllOrderRecordListAdapter(Context context) {
        this.mContext = context;
        this.mDatas = new ArrayList<>();
    }

    public void updateDatas(CF_AllOrderRecordCountBean datas) {
        settleDatas(datas);
        notifyDataSetChanged();
    }

    public void settleDatas(CF_AllOrderRecordCountBean bean) {

//        CF_AllOrderRecordBean temp0 = new CF_AllOrderRecordBean("newTotal", bean.getNewTotal(), "新车类订单");
        CF_AllOrderRecordBean temp0 = new CF_AllOrderRecordBean("newTotal", 0, "新车类订单");
        this.mDatas.add(temp0);
        CF_AllOrderRecordBean temp1 = new CF_AllOrderRecordBean("financialTotal", bean.getFinancialTotal(), "汽车金融订单");
        this.mDatas.add(temp1);
//        CF_AllOrderRecordBean temp2 = new CF_AllOrderRecordBean("etcTotal", bean.getEtcTotal(), "ETC类订单");
        CF_AllOrderRecordBean temp2 = new CF_AllOrderRecordBean("etcTotal", 0, "ETC类订单");
        this.mDatas.add(temp2);
//        CF_AllOrderRecordBean temp3 = new CF_AllOrderRecordBean("bxTotal", bean.getBxTotal(), "保险类订单");
        CF_AllOrderRecordBean temp3 = new CF_AllOrderRecordBean("bxTotal", 0, "保险类订单");
        this.mDatas.add(temp3);
//        CF_AllOrderRecordBean temp4 = new CF_AllOrderRecordBean("onlineTotal", bean.getOnlineTotal(), "在线购车订单");
        CF_AllOrderRecordBean temp4 = new CF_AllOrderRecordBean("onlineTotal", 0, "在线购车订单");
        this.mDatas.add(temp4);
//        CF_AllOrderRecordBean temp5 = new CF_AllOrderRecordBean("secordTotal", bean.getSecordTotal(), "二手车订单");
        CF_AllOrderRecordBean temp5 = new CF_AllOrderRecordBean("secordTotal", 0, "二手车订单");
        this.mDatas.add(temp5);
//        CF_AllOrderRecordBean temp6 = new CF_AllOrderRecordBean("kyTotal", bean.getKyTotal(), "客运类订单");
        CF_AllOrderRecordBean temp6 = new CF_AllOrderRecordBean("kyTotal", 0, "客运类订单");
        this.mDatas.add(temp6);
//        CF_AllOrderRecordBean temp7 = new CF_AllOrderRecordBean("sqTotal", bean.getSqTotal(), "社区服务类订单");
        CF_AllOrderRecordBean temp7 = new CF_AllOrderRecordBean("sqTotal", 0, "社区服务类订单");
        this.mDatas.add(temp7);

    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        convertView = LayoutInflater.from(mContext).inflate(R.layout.cf_list_all_order_record_item, null);

        TextView titleTxt = (TextView) convertView.findViewById(R.id.cf_all_or_item_title);
        TextView numberTxt = (TextView) convertView.findViewById(R.id.cf_all_or_item_number);

        titleTxt.setText(mDatas.get(position).getItemName());                //订单类别
        numberTxt.setText(mDatas.get(position).getCount() + "条");           //订单个数

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mDatas.get(position).getCount() == 0) {
                    DefinedToast.showToast(mContext, "暂时没有" + mDatas.get(position).getItemName(), 0);
                } else {
                    Intent intent = new Intent(mContext, CF_OrderRecordActivity.class);
                    intent.putExtra("flag", mDatas.get(position).getItemTag());
                    mContext.startActivity(intent);
                }
            }
        });

        return convertView;
    }
}
