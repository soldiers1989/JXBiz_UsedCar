package com.etong.android.jxappfours.find_car.filtrate.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.etong.android.frame.common.ImageProvider;
import com.etong.android.jxappfours.R;
import com.etong.android.jxappfours.find_car.filtrate.Find_Car_FiltrateFragment;
import com.etong.android.jxappfours.find_car.filtrate.javabeam.Find_Car_VechileSeries;
import com.etong.android.jxappfours.find_car.grand.bean.FC_InsalesListItem;
import com.etong.android.jxappfours.find_car.grand.carset.FC_CarsetActivity;

import org.simple.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * @desc (这里用一句话描述这个类的作用)
 * @createtime 2016/10/14 0014--14:52
 * @Created by wukefan.
 */
public class Find_Car_FiltrateResultListAdapter extends BaseAdapter {

    private Context mContext;
    private List<Find_Car_VechileSeries> mData;     // 传送过来的数据
    LayoutInflater mInflater;

    /**
     * 构造方法
     *
     * @param context
     */
    public Find_Car_FiltrateResultListAdapter(Context context) {
        this.mContext = context;
        mData = new ArrayList<Find_Car_VechileSeries>();
        mInflater = LayoutInflater.from(context);
    }

    /**
     * @param
     * @return
     * @desc (对外公布的更新ListView数据的方法)
     * @user sunyao
     * @createtime 2016/10/6 - 15:57
     */
    public void updateCarList(List<Find_Car_VechileSeries> data) {
        this.mData = data;
        notifyDataSetChanged();
    }

    public void cleanList() {
        this.mData.clear();
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
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.find_car_filtrate_result_items_lv, null);
            holder = new ViewHolder();
            holder.titlezone = (RelativeLayout) convertView.findViewById(R.id.find_car_filtrate_rl_title);
            holder.zero = (TextView) convertView.findViewById(R.id.find_car_filtrate_txt_vechile_series_zero);
            holder.title = (TextView) convertView.findViewById(R.id.find_car_filtrate_txt_vechile_series_title);
            holder.price = (TextView) convertView.findViewById(R.id.find_car_filtrate_txt_vechile_series_price);
            holder.logo = (ImageView) convertView.findViewById(R.id.find_car_filtrate_img_vechile_series_logo);
            holder.item = (ViewGroup) convertView.findViewById(R.id.find_car_filtrate_rl_vechile_series_item);
            holder.mResetBt = (TextView) convertView.findViewById(R.id.find_car_filtrate_txt_reset);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (mData.get(position).isRoot()) {
            holder.titlezone.setVisibility(View.VISIBLE);
            holder.item.setVisibility(View.GONE);
            holder.zero.setText(mData.get(position).getTitle());
        } else {
            holder.titlezone.setVisibility(View.GONE);
            holder.item.setVisibility(View.VISIBLE);
        }

        if (position == 0) {
            holder.mResetBt.setVisibility(View.VISIBLE);
        } else {
            holder.mResetBt.setVisibility(View.GONE);
        }

        holder.title.setText(mData.get(position).getPTitle() + mData.get(position).getTitle());

        if (null != mData.get(position).getMinguide() && null != mData.get(position).getMaxguide() && mData.get(position).getMinguide() != 0.0 && mData.get(position).getMaxguide() != 0.0) {
            holder.price.setText(mData.get(position).getMinguide() + "-" + mData.get(position).getMaxguide() + "万");
        } else {
            holder.price.setText("未记录");
        }

        ImageProvider.getInstance().loadImage(holder.logo, mData.get(position).getImage(), R.mipmap.fours_default_img);

        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(mContext, FC_CarsetActivity.class);//跳转车系列表页面
                FC_InsalesListItem insalesListItem = new FC_InsalesListItem();
                insalesListItem.setId(mData.get(position).getId());
                insalesListItem.setImage(mData.get(position).getImage());
                insalesListItem.setFullName(mData.get(position).getPTitle() + mData.get(position).getTitle());
                insalesListItem.setpTitle(mData.get(position).getPTitle());
                insalesListItem.setMaxguide(mData.get(position).getMaxguide());
                insalesListItem.setMinguide(mData.get(position).getMinguide());
                insalesListItem.setMaxOut(mData.get(position).getMaxOut());
                insalesListItem.setMinOut(mData.get(position).getMinOut());
                insalesListItem.setCarlevel("");
                intent.putExtra("carset_name", insalesListItem);
                mContext.startActivity(intent);
            }

        });

        holder.mResetBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post("", "reset");
                Find_Car_FiltrateFragment.closeDrawerLayout();
            }
        });

        return convertView;
    }

    static class ViewHolder {
        RelativeLayout titlezone;
        TextView zero;
        TextView title;
        TextView price;
        ImageView logo;
        ViewGroup item;
        TextView mResetBt;

    }
}
