package com.etong.android.jxappfours.find_car.collect_search.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.etong.android.frame.common.ImageProvider;
import com.etong.android.frame.user.FrameEtongApplication;
import com.etong.android.frame.utils.Etong_DateToStringUtil;
import com.etong.android.jxappfours.R;
import com.etong.android.jxappfours.find_car.collect_search.javabean.Find_Car_Search_Result;
import com.etong.android.jxappfours.find_car.collect_search.main_content.Find_Car_SearchActivity;
import com.etong.android.jxappfours.find_car.grand.bean.FC_InsalesListItem;
import com.etong.android.jxappfours.find_car.grand.carset.FC_CarsetActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * @desc (搜索列表adapter)
 * @createtime 2016/12/15 - 15:30
 * @Created by xiaoxue.
 */

public class FC_SearchListAdapter extends BaseAdapter{

    private Context mContext;
    private List<Find_Car_Search_Result> mlist;

    public FC_SearchListAdapter(Context mContext) {
        this.mContext = mContext;
        this.mlist = new ArrayList<>();
    }
    public void clear() {
        mlist.clear();
        notifyDataSetChanged();
    }
    //更新资讯列表的数据
    public void update(List<Find_Car_Search_Result> mlist) {
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
        FC_SearchViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.find_car_search_list_item, null);
            holder = new FC_SearchViewHolder();
            holder.vechilePic = (ImageView) convertView.findViewById(R.id.find_car_img_pic);
            holder.vechileTitle = (TextView) convertView.findViewById(R.id.find_car_txt_title);
            holder.vechileMinPrice = (TextView) convertView.findViewById(R.id.find_car_txt_minprice);
            holder.vechileMaxPrice = (TextView) convertView.findViewById(R.id.find_car_txt_maxprice);
            convertView.setTag(holder);
        } else {
            holder = (FC_SearchViewHolder) convertView.getTag();
        }
        ImageProvider.getInstance().loadImage(holder.vechilePic,mlist.get(position).getImage(),R.mipmap.find_search_ic);
        holder.vechileTitle.setText(mlist.get(position).getFullName());
        holder.vechileMinPrice.setText(String.valueOf(mlist.get(position).getMinguide())+"-");
        holder.vechileMaxPrice.setText(String.valueOf(mlist.get(position).getMaxguide())+"万");
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(mContext, FC_CarsetActivity.class);
                FC_InsalesListItem mFC_InsalesListItem=new FC_InsalesListItem();
                mFC_InsalesListItem.setId(mlist.get(position).getId());
                mFC_InsalesListItem.setImage(mlist.get(position).getImage());
                mFC_InsalesListItem.setPid(mlist.get(position).getPid());
                mFC_InsalesListItem.setpTitle(mlist.get(position).getPTitle());
                mFC_InsalesListItem.setFullName(mlist.get(position).getFullName());
                mFC_InsalesListItem.setMaxguide(mlist.get(position).getMaxguide());
                mFC_InsalesListItem.setMinguide(mlist.get(position).getMinguide());
                mFC_InsalesListItem.setLevel(mlist.get(position).getLevel()+"");
                mFC_InsalesListItem.setCarlevel(mlist.get(position).getCarlevel());
                mFC_InsalesListItem.setOutVol(mlist.get(position).getOutVol());
                mFC_InsalesListItem.setMinOut(mlist.get(position).getMinOut());
                mFC_InsalesListItem.setMaxOut(mlist.get(position).getMaxOut());
                intent.putExtra("carset_name",mFC_InsalesListItem);
                FrameEtongApplication.getApplication().addSearchHistory(mlist.get(position).getPTitle());
                mContext.startActivity(intent);
            }
        });
        return convertView;
    }

    class FC_SearchViewHolder {
        ImageView vechilePic;
        TextView vechileTitle;
        TextView vechileMinPrice;
        TextView vechileMaxPrice;
    }
}
