package com.etong.android.jxappcarfinancial.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.etong.android.frame.user.FrameRepayRemindInfo;
import com.etong.android.jxappcarfinancial.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @desc 还款提醒adapter
 * @createtime 2016/11/18 - 16:08
 * @Created by xiaoxue.
 */

public class CF_RepaymentRemindAdapter extends BaseAdapter {

    private Context mContext;
    private List<FrameRepayRemindInfo> list;

    public CF_RepaymentRemindAdapter(Context mContext) {
        this.mContext = mContext;
        list = new ArrayList<>();
    }

    public void update(List<FrameRepayRemindInfo> list){
        this.list=list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CF_RepaymentRemindHolder viewHolder=null;
        if (null == convertView) {
            viewHolder = new CF_RepaymentRemindHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.cf_activity_remind_item, null);
            viewHolder.cf_txt_number = (TextView) convertView.findViewById(R.id.cf_txt_number);
            viewHolder.cf_txt_sendtime = (TextView) convertView.findViewById(R.id.cf_txt_sendtime);
            viewHolder.cf_txt_message = (TextView) convertView.findViewById(R.id.cf_txt_message);
//            viewHolder.cf_txt_real_amount_also = (TextView) convertView.findViewById(R.id.cf_txt_real_amount_also);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (CF_RepaymentRemindHolder) convertView.getTag();
        }
        if(position<10){
            viewHolder.cf_txt_number.setText("0"+(position+1));
        }else {
            viewHolder.cf_txt_number.setText(position+1+"");
        }
        viewHolder.cf_txt_sendtime.setText(list.get(position).getTime());
        /*if(list.get(position).getMessage().contains("未读")){
            viewHolder.cf_txt_should_also_amount.setTextColor(mContext.getResources().getColor(R.color.a1_blue_color));
        }
        if(list.get(position).getStatus().contains("未读")){
            viewHolder.cf_txt_real_amount_also.setTextColor(mContext.getResources().getColor(R.color.a1_blue_color));
        }*/
        viewHolder.cf_txt_message.setText(list.get(position).getFPeriod()+list.get(position).getFPerRev()+"\n" + "最后还款日："+list.get(position).getFDate());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        return convertView;
    }

    private static class CF_RepaymentRemindHolder {
        public TextView cf_txt_number;
        public TextView cf_txt_sendtime;
        public TextView cf_txt_message;

    }
}
