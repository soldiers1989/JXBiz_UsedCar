package com.etong.android.jxappcarfinancial.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.etong.android.frame.utils.AddCommaToMoney;
import com.etong.android.jxappcarfinancial.R;
import com.etong.android.jxappcarfinancial.bean.CF_OverdueBean;
import com.etong.android.jxappcarfinancial.bean.CF_RecordDetailsBean;
import java.util.ArrayList;
import java.util.List;

/**
 * @desc 明细adapter （记录item点击   进来页面的 adapter ）
 * @createtime 2016/11/17 - 16:38
 * @Created by xiaoxue.
 */

public class CF_RecordDetailsAdapter extends BaseAdapter{
    private Context mContext;
    private List<CF_RecordDetailsBean.RepayListBean> repayList; //还款list
    private List<CF_OverdueBean.OverdueListBean> overdueList;   //逾期list
    private int flag=-1;
    public CF_RecordDetailsAdapter(Context mContext) {
        this.mContext = mContext;
        repayList = new ArrayList<>();
        overdueList=new ArrayList<>();
    }

    /**
     * @desc (还款)
     * @createtime 2016/11/29 - 14:19
     * @author xiaoxue
     */
    public void updateRepayList(List<CF_RecordDetailsBean.RepayListBean> datas, int flag) {
        repayList = datas;
        this.flag = flag;
        notifyDataSetChanged();
    }

    /**
     * @desc (逾期)
     * @createtime 2016/11/29 - 14:20
     * @author xiaoxue
     */
    public void updateOverdueList(List<CF_OverdueBean.OverdueListBean> datas, int flag) {
        overdueList = datas;
        this.flag = flag;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        int size = 0;
        switch (flag) {
            case 1:
                size = repayList.size();
                break;
            case 2:
                size = overdueList.size();
                break;
            default:
                size = 0;
                break;
        }
        return size;
    }

    @Override
    public Object getItem(int position) {
        Object o = null;
        switch (flag) {
            case 1:
                o = repayList.get(position);
                break;
            case 2:
                o = overdueList.get(position);
                break;
        }
        return o;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CF_RecordViewHolder viewHolder=null;
        if (null == convertView) {
            viewHolder = new CF_RecordViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.cf_activity_record_details_item, null);
            viewHolder.cf_txt_periods = (TextView) convertView.findViewById(R.id.cf_txt_periods);
            viewHolder.cf_txt_should_also_time = (TextView) convertView.findViewById(R.id.cf_txt_should_also_time);
            viewHolder.cf_txt_should_also_amount = (TextView) convertView.findViewById(R.id.cf_txt_should_also_amount);
            viewHolder.cf_txt_real_amount_also = (TextView) convertView.findViewById(R.id.cf_txt_real_amount_also);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (CF_RecordViewHolder) convertView.getTag();
        }

        switch (flag) {
            case 1:
                if(repayList.get(position).getFPeriod()<10){//期数
                    viewHolder.cf_txt_periods.setText("0"+repayList.get(position).getFPeriod()+"");
                }else {
                    viewHolder.cf_txt_periods.setText(repayList.get(position).getFPeriod()+"");
                }
                viewHolder.cf_txt_should_also_time.setText(repayList.get(position).getFDate());//应还时间
                viewHolder.cf_txt_should_also_amount.setText("¥"+ AddCommaToMoney.AddCommaToMoney(String.valueOf(repayList.get(position).getFPerRev())));
                if("".equals(String.valueOf(repayList.get(position).getFActRev2())) &&
                        TextUtils.isEmpty(String.valueOf(repayList.get(position).getFActRev2()))){
                    viewHolder.cf_txt_real_amount_also.setText("——");
                }else{
                    viewHolder.cf_txt_real_amount_also.setText("¥"+AddCommaToMoney.AddCommaToMoney(String.valueOf(repayList.get(position).getFActRev2())));
                }
                break;
            case 2:
                if(overdueList.get(position).getFPeriod()<10){//期数
                    viewHolder.cf_txt_periods.setText("0"+overdueList.get(position).getFPeriod()+"");
                }else {
                    viewHolder.cf_txt_periods.setText(overdueList.get(position).getFPeriod()+"");
                }
                viewHolder.cf_txt_should_also_time.setText(overdueList.get(position).getFDate());//应还时间
                viewHolder.cf_txt_should_also_amount.setText("¥"+ AddCommaToMoney.AddCommaToMoney(String.valueOf(overdueList.get(position).getFPerRev())));
                if("".equals(String.valueOf(overdueList.get(position).getFActRev2())) &&
                        TextUtils.isEmpty(String.valueOf(overdueList.get(position).getFActRev2()))){
                    viewHolder.cf_txt_real_amount_also.setText("——");
                }else{
                    viewHolder.cf_txt_real_amount_also.setText("¥"+AddCommaToMoney.AddCommaToMoney(String.valueOf(overdueList.get(position).getFActRev2())));
                }
                break;
        }
        return convertView;
    }


    private static class CF_RecordViewHolder {
        public TextView cf_txt_periods;
        public TextView cf_txt_should_also_time;
        public TextView cf_txt_should_also_amount;
        public TextView cf_txt_real_amount_also;
    }
}
