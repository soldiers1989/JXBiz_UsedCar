package com.etong.android.jxappcarfinancial.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.etong.android.jxappcarfinancial.R;
import com.etong.android.jxappcarfinancial.activity.CF_OrderRecordDetailActivity;
import com.etong.android.jxappcarfinancial.bean.CF_OrderRecordBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @desc (某类订单列表Adapter)
 * @createtime 2016/11/17 0017--18:52
 * @Created by wukefan.
 */
public class CF_OrderRecordListAdapter extends BaseAdapter {

    private List<CF_OrderRecordBean> mDatas;
    private Context mContext;

    public CF_OrderRecordListAdapter(Context context) {
        this.mContext = context;
        this.mDatas = new ArrayList<>();
    }

    public void updateDatas(List<CF_OrderRecordBean> datas) {
        this.mDatas = datas;
        notifyDataSetChanged();
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

        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.cf_list_order_record_item, null);
            viewHolder = new ViewHolder();
            viewHolder.numberTxt = (TextView) convertView.findViewById(R.id.cf_txt_or_number);
            viewHolder.orderNumberTxt = (TextView) convertView.findViewById(R.id.cf_txt_or_order_number);
            viewHolder.createTimeTxt = (TextView) convertView.findViewById(R.id.cf_txt_or_createtime);
            viewHolder.orderCategoryTxt = (TextView) convertView.findViewById(R.id.cf_txt_or_order_category);
            viewHolder.statusTxt = (TextView) convertView.findViewById(R.id.cf_txt_or_status);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        //序号
        if (position < 9) {
            viewHolder.numberTxt.setText("0" + (position + 1));
        } else {
            viewHolder.numberTxt.setText((position + 1) + "");
        }

        viewHolder.orderNumberTxt.setText(mDatas.get(position).getF_orderid1());                //订单编号
        viewHolder.createTimeTxt.setText(mDatas.get(position).getF_createtime1());              //创建时间
        viewHolder.statusTxt.setText(mDatas.get(position).getF_orderstatusname());              //状态
        viewHolder.orderCategoryTxt.setText(mDatas.get(position).getF_ordertypename());         //订单分类


        //底部加横线
        viewHolder.statusTxt.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //跳转至详情
                Intent intent = new Intent(mContext, CF_OrderRecordDetailActivity.class);
                intent.putExtra("f_id", mDatas.get(position).getF_id1());
                intent.putExtra("f_ordertype", mDatas.get(position).getF_ordertype1());
                mContext.startActivity(intent);
            }
        });

        return convertView;
    }

    class ViewHolder {
        public TextView numberTxt;                  //序号
        public TextView orderNumberTxt;             //订单编号
        public TextView createTimeTxt;              //创建时间
        public TextView orderCategoryTxt;           //订单分类
        public TextView statusTxt;                  //状态
    }
}
