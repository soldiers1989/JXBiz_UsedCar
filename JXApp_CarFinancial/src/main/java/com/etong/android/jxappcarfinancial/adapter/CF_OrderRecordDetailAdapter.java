package com.etong.android.jxappcarfinancial.adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.etong.android.jxappcarfinancial.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wukefan
 * @desc (订单记录详情适配器)
 * @createtime 2016/11/18 0018-16:25
 */
public class CF_OrderRecordDetailAdapter extends BaseAdapter {

    private Context mContext;
    private List<String> mData;     // 传送过来的数据
    LayoutInflater mInflater;

    String[] strTitle = {
            "订单编号：",
            "创建时间：",
            "订单分类：",
            "姓名：",
            "手机号码：",
            "身份证号码：",
            "订单状态：",
            "备注："
    };

    /**
     * 构造方法
     *
     * @param context
     */
    public CF_OrderRecordDetailAdapter(Context context) {
        this.mContext = context;
        this.mData = new ArrayList<>();
        mInflater = LayoutInflater.from(context);
    }

    public void updateDatas(List<String> datas) {
        this.mData = datas;
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
            convertView = mInflater.inflate(R.layout.cf_content_order_record_detail_item, null);
            holder = new ViewHolder();
            holder.mTitle = (TextView) convertView.findViewById(R.id.cf_order_record_detail_title);
            holder.mDesc = (TextView) convertView.findViewById(R.id.cf_order_record_detail_title_desc);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.mTitle.setText(strTitle[position]);          //详情项标题
        holder.mDesc.setText(mData.get(position));          //详情项内容

        ViewTreeObserver observer = holder.mDesc.getViewTreeObserver(); // textAbstract为TextView控件
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {
                ViewTreeObserver obs = holder.mDesc.getViewTreeObserver();
                obs.removeGlobalOnLayoutListener(this);
                if (holder.mDesc.getLineCount() >= 2) {//大于一行居左显示
                    holder.mDesc.setGravity(Gravity.LEFT);
                }
            }
        });

        return convertView;
    }

    static class ViewHolder {
        TextView mTitle;        //详情项标题
        TextView mDesc;         //详情项内容
    }
}
