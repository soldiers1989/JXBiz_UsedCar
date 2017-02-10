package com.etong.android.jxappcarassistant.violation_query.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.etong.android.jxappcarassistant.R;
import com.etong.android.jxappcarassistant.violation_query.bean.CA_ViolationQueryBean;
import com.etong.android.jxappcarassistant.violation_query.main_content.CA_ViolationQueryDetailsActivity;

import java.util.List;

/**
 * @desc (违章查询的adapter)
 * @createtime 2016/11/23 - 16:44
 * @Created by xiaoxue.
 */

public class CA_ViolationAdapter extends BaseAdapter {

    private Context mContext;
    private List<CA_ViolationQueryBean.ListBean> mList;

    public CA_ViolationAdapter(Context mContext, List<CA_ViolationQueryBean.ListBean> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
            CA_ViolationViewHolder viewHolder = null;
        if (null == convertView) {
            viewHolder = new CA_ViolationViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.ca_violation_query_item, null);
            viewHolder.ca_ll_violation_item = (LinearLayout) convertView.findViewById(R.id.ca_ll_violation_item);
            viewHolder.ca_txt_number = (TextView) convertView.findViewById(R.id.ca_txt_number);
            viewHolder.ca_txt_illegal_behavior = (TextView) convertView.findViewById(R.id.ca_txt_illegal_behavior);
            viewHolder.ca_txt_score = (TextView) convertView.findViewById(R.id.ca_txt_score);
            viewHolder.ca_txt_price = (TextView) convertView.findViewById(R.id.ca_txt_price);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (CA_ViolationViewHolder) convertView.getTag();
        }

        //不是两位数的就加0
        if(position<9){
            viewHolder.ca_txt_number.setText("0"+(position+1));
        }else {
            viewHolder.ca_txt_number.setText(position+1+"");
        }
        viewHolder.ca_txt_illegal_behavior.setText(mList.get(position).getContent());
        viewHolder.ca_txt_score.setText(mList.get(position).getScore());
        viewHolder.ca_txt_price.setText(mList.get(position).getPrice());

        viewHolder.ca_ll_violation_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, CA_ViolationQueryDetailsActivity.class);//跳到详情页
                intent.putExtra("CA_ViolationQueryBean",mList.get(position));
                mContext.startActivity(intent);
            }
        });
        return convertView;
    }

    private static class CA_ViolationViewHolder {
        public LinearLayout ca_ll_violation_item;
        public TextView ca_txt_number;
        public TextView ca_txt_illegal_behavior;
        public TextView ca_txt_score;
        public TextView ca_txt_price;

    }

}
