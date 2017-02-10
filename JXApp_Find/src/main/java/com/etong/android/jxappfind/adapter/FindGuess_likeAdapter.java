package com.etong.android.jxappfind.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.etong.android.frame.common.ImageProvider;
import com.etong.android.jxappfind.R;
import com.etong.android.jxappfind.javabean.FindGuessBean;
import com.etong.android.jxappfours.order.FO_OrderActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * @desc (猜你喜欢adapter)
 * @createtime 2017/1/5 - 16:37
 * @Created by xiaoxue.
 */

public class FindGuess_likeAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private Context mContext;
    private List<FindGuessBean> mData;


    public FindGuess_likeAdapter(Context mContext){
        this.mContext=mContext;
        this.mData=new ArrayList<>();
        mInflater = LayoutInflater.from(mContext);
    }

    public void clear() {
        mData.clear();
        notifyDataSetChanged();
    }

    public void updateCarList(List<FindGuessBean> list) {
        this.mData = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.find_guess_like_item_lv, null);
            holder = new ViewHolder();
            holder.img = (ImageView) convertView.findViewById(R.id.find_img_image);
            holder.title = (TextView) convertView.findViewById(R.id.find_txt_title);
            holder.price = (TextView) convertView.findViewById(R.id.find_txt_price);
            holder.costprice = (TextView) convertView.findViewById(R.id.find_txt_costprice);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        ImageProvider.getInstance().loadImage(holder.img, mData.get(position).getPicturesList().get(0).getUrl(), R.mipmap.find_activity_ic);
        holder.title.setText(mData.get(position).getName());
        holder.price.setText(String.valueOf(mData.get(position).getPrice())+"万");
        holder.costprice.setText(String.valueOf(mData.get(position).getOriginal_price())+"万");//原价
        holder.costprice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG); //删除线

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(mContext, FO_OrderActivity.class);
                intent.putExtra("flag", 4);
                intent.putExtra("isSelectCar", true);
                intent.putExtra("vTitleName", mData.get(position).getName());
                intent.putExtra("carsetId", mData.get(position).getCarset_id());
                if (null == mData.get(position).getVid()) {
                    intent.putExtra("vid", "-1");
                } else {
                    intent.putExtra("vid", mData.get(position).getVid() + "");
                }
                intent.putExtra("carImage", mData.get(position).getPicturesList().get(0).getUrl());
                mContext.startActivity(intent);
            }
        });
        return convertView;
    }

    static class ViewHolder {
        ImageView img;
        TextView title;
        TextView price;
        TextView costprice;
    }
}
