package com.etong.android.jxappcarfinancial.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.etong.android.jxappcarfinancial.R;
import java.util.ArrayList;
import java.util.List;

/**
 * @desc 贷款记录详情adapter
 * @createtime 2016/11/18 - 13:53
 * @Created by xiaoxue.
 */

public class CF_LoanDocumentationDatailsAdapter extends BaseAdapter{
    private Context mContext;
    private List<String> mData;     // 传送过来的数据
    LayoutInflater mInflater;

    String [] strTitle = {
            "申请时间:",
            "放款时间:",
            "办理进度:",
            "贷款金额:",
            "月供:",
            "还款卡号尾号:"
    };

    /**
     * 构造方法
     * @param context
     * @param
     */
    public CF_LoanDocumentationDatailsAdapter(Context context) {
        this.mContext = context;
        mData=new ArrayList<>();
        mInflater = LayoutInflater.from(context);
    }

    public void update(List<String> data){
        this.mData = data;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.cf_activity_loan_records_details_item, null);
            holder = new ViewHolder();
            holder.cf_txt_details_left = (TextView) convertView.findViewById(R.id.cf_txt_details_left);
            holder.cf_txt_details_right = (TextView) convertView.findViewById(R.id.cf_txt_details_right);
            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder)convertView.getTag();
        }

        holder.cf_txt_details_left.setText(strTitle[position]);
        if("".equals(mData.get(position)) && TextUtils.isEmpty(mData.get(position))){
            holder.cf_txt_details_right.setText("——");
        }else{
            holder.cf_txt_details_right.setText(mData.get(position));
        }
        if(!TextUtils.isEmpty(mData.get(position)) && mData.get(position).equals("--")){
            holder.cf_txt_details_right.setText("——");
        }
        return convertView;
    }
    static class ViewHolder {
        TextView cf_txt_details_left;
        TextView cf_txt_details_right;
    }
}
