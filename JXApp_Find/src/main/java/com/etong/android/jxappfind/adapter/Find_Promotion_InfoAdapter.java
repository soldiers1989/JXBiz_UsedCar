package com.etong.android.jxappfind.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.etong.android.jxappfind.R;
import com.etong.android.jxappfind.javabean.FindPromotionBean;
import com.etong.android.jxappfind.utils.FindTimerTextView;
import com.etong.android.jxappfours.order.FO_OrderActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 促销车详情适配器
 * Created by Administrator on 2016/9/6.
 */
public class Find_Promotion_InfoAdapter extends BaseAdapter {

    private Context mContext;
    private List<FindPromotionBean> mListDatas;
//    private boolean flag = true;

    public Find_Promotion_InfoAdapter(Context context) {
        this.mContext = context;
        mListDatas = new ArrayList<FindPromotionBean>();
    }

    public void updateListDatas(List<FindPromotionBean> listDatas) {
        this.mListDatas = listDatas;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mListDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mListDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        FindPromotionBean info = mListDatas.get(position);
        final ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.find_promotion_list_item_activity, null);
            holder = new ViewHolder();

            holder.title = (TextView) convertView.findViewById(R.id.find_txt_promotion_title);
            holder.color = (TextView) convertView.findViewById(R.id.find_txt_promotion_color);
            holder.guide_price = (TextView) convertView.findViewById(R.id.find_txt_guideprice);
            holder.promotion_price = (TextView) convertView.findViewById(R.id.find_txt_promotion_price);
            holder.save_money = (TextView) convertView.findViewById(R.id.find_txt_save_money);
            holder.cuxiaotime = (FindTimerTextView) convertView.findViewById(R.id.find_txt_time);
            holder.comment1 = (TextView) convertView.findViewById(R.id.find_txt_comment1);
            holder.comment2 = (TextView) convertView.findViewById(R.id.find_txt_comment2);
            holder.btn = (Button) convertView.findViewById(R.id.find_btn_submit);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        holder.title.setText(mListDatas.get(position).getTitle());
        holder.color.setText(mListDatas.get(position).getColors());
        holder.guide_price.setText(mListDatas.get(position).getGuide_price());
        holder.promotion_price.setText(mListDatas.get(position).getCuxiaojia());
        holder.save_money.setText(mListDatas.get(position).getSave_money());
        holder.cuxiaotime.startCountDown((mListDatas.get(position).getEnd_seconds() - System.currentTimeMillis()), 1000);
        holder.comment1.setText(mListDatas.get(position).getComment1());
        holder.comment2.setText(mListDatas.get(position).getComment2());

        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(mContext, Fours_Order_OrderActivity.class);
                Intent intent = new Intent(mContext, FO_OrderActivity.class);
                intent.putExtra("flag", 4);
                intent.putExtra("isSelectCar", true);
                intent.putExtra("vTitleName", mListDatas.get(position).getTitle());
                intent.putExtra("carsetId", -1);
                intent.putExtra("vid", mListDatas.get(position).getCar_id());
                intent.putExtra("carImage", mListDatas.get(position).getImage());
                mContext.startActivity(intent);
            }
        });

        return convertView;
    }

    class ViewHolder {
        boolean isLoaded;
        TextView title;
        TextView color;
        TextView guide_price;
        TextView promotion_price;
        TextView save_money;
        FindTimerTextView cuxiaotime;
        TextView comment1;
        TextView comment2;
        Button btn;
    }
}
