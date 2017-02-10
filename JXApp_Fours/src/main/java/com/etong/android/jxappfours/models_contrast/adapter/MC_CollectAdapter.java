package com.etong.android.jxappfours.models_contrast.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.etong.android.jxappfours.R;
import com.etong.android.jxappfours.find_car.collect_search.adapter.FC_CollectAdapter;
import com.etong.android.jxappfours.find_car.collect_search.javabean.FC_NewCarCollectBean;
import com.etong.android.jxappfours.find_car.grand.adapter.FC_CarsetDetailAdapter;
import com.etong.android.jxappfours.models_contrast.javabean.Models_Contrast_VechileType;

import java.util.ArrayList;
import java.util.List;

/**
 * @desc (车型对比中的收藏列表)
 * @createtime 2016/12/13 - 10:14
 * @Created by xiaoxue.
 */

public class MC_CollectAdapter extends BaseAdapter {
    private Context mContext;
    private List<FC_NewCarCollectBean> mlist;
    private ItemOnClickListener mItemOnClickListener;
    public MC_CollectAdapter(Context mContext,ItemOnClickListener mItemOnClickListener) {
        this.mContext = mContext;
        this.mlist = new ArrayList<>();
        this.mItemOnClickListener=mItemOnClickListener;
    }

    public void update(List<FC_NewCarCollectBean> mlist) {
        this.mlist = mlist;
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return mlist.size();
    }

    @Override
    public Object getItem(int position) {
        return mlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final MC_ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.models_contrast_collect_list_item, null);
            holder = new MC_ViewHolder();
            holder.mc_title = (TextView) convertView.findViewById(R.id.models_contrast_txt_collect_title);
            convertView.setTag(holder);
        } else {
            holder = (MC_ViewHolder) convertView.getTag();
        }
        holder.mc_title.setText(mlist.get(position).getF_fullname());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mItemOnClickListener.itemOnClickListener(mlist.get(position));
            }
        });
        return convertView;
    }


    static class MC_ViewHolder {
        TextView mc_title;
    }




    public interface ItemOnClickListener{
        /**
         * 传递点击的javabean
         * @param
         */
        public void itemOnClickListener(FC_NewCarCollectBean data);
    }
}
