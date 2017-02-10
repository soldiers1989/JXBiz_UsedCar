package com.etong.android.jxappfours.find_car.collect_search.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.etong.android.frame.common.ImageProvider;
import com.etong.android.frame.user.FrameEtongApplication;
import com.etong.android.frame.utils.CToast;
import com.etong.android.frame.utils.SerializableObject;
import com.etong.android.jxappfours.R;
import com.etong.android.jxappfours.find_car.collect_search.javabean.FC_NewCarCollectBean;
import com.etong.android.jxappfours.find_car.grand.car_config.Find_car_CarConfigActivity;
import com.etong.android.jxappfours.models_contrast.common.Models_Contrast_AddVechileStyle_Method;
import com.etong.android.jxappfours.models_contrast.javabean.Models_Contrast_Add_VechileStyle;
import com.etong.android.jxappfours.models_contrast.javabean.Models_Contrast_VechileType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @desc (新车收藏列表的adapter)
 * @createtime 2016/12/12 - 19:29
 * @Created by xiaoxue.
 */

public class FC_CollectAdapter extends BaseAdapter {

    private Context mContext;
    private List<FC_NewCarCollectBean> mlist;

    public FC_CollectAdapter(Context mContext) {
        this.mContext = mContext;
        this.mlist = new ArrayList<>();
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
        final DataViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.find_car_collect_list_item, null);
            holder = new DataViewHolder();
            holder.fc_img = (ImageView) convertView.findViewById(R.id.find_car_img_pic);
            holder.fc_title = (TextView) convertView.findViewById(R.id.find_car_txt_title);
            holder.fc_price = (TextView) convertView.findViewById(R.id.find_car_txt_price);
            holder.fc_mAddContrast = (TextView) convertView.findViewById(R.id.find_car_txt_contrast);

            convertView.setTag(holder);
        } else {
            holder = (DataViewHolder) convertView.getTag();
        }
        ImageProvider.getInstance().loadImage(holder.fc_img,mlist.get(position).getF_image(),R.mipmap.find_search_ic);
        holder.fc_title.setText(mlist.get(position).getF_fullname());
        holder.fc_price.setText((mlist.get(position).getF_price())+"万起");
        if (getComparisonVidList().contains(mlist.get(position).getF_vid())) {
            holder.fc_mAddContrast .setSelected(true);
            holder.fc_mAddContrast .setText("已加入");
        }else{
            holder.fc_mAddContrast .setSelected(false);
            holder.fc_mAddContrast .setText("加入对比");
        }
        holder.fc_mAddContrast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (! holder.fc_mAddContrast .isSelected()) {
                    if(getComparisonVidList().size()>=12){
                        CToast.toastMessage("对比的个数已满",0);
                        return;
                    }
                    Models_Contrast_AddVechileStyle_Method.cartAdd(changeBean(position));
                    holder.fc_mAddContrast.setSelected(true);
                    holder.fc_mAddContrast.setText("已加入");
                } else {
                    Models_Contrast_AddVechileStyle_Method.remove(mlist.get(position).getF_vid());
                    holder.fc_mAddContrast.setSelected(false);
                    holder.fc_mAddContrast.setText("加入对比");
                }
            }
        });

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(mContext, Find_car_CarConfigActivity.class);
                Map map = new HashMap<>();
                map.put("dataType", changeBean(position));
                // 传递数据
                final SerializableObject myMap = new SerializableObject();
                myMap.setObject(map);// 将map数据添加到封装的myMap中
                Bundle bundle = new Bundle();
                bundle.putSerializable("dataTypeMap", myMap);
                intent.putExtras(bundle);

                mContext.startActivity(intent);

            }
        });

        return convertView;
    }

    static class DataViewHolder {
        ImageView fc_img;
        TextView fc_title;
        TextView fc_price;
        TextView fc_mAddContrast;

    }

    /**
     * 获取到对比的Item
     * @return
     */
    private List<Integer> getComparisonVidList() {
        //得到车型对比缓存的信息的vid
        List<Integer> mVidList = new ArrayList<Integer>();
        List<Models_Contrast_Add_VechileStyle> mList = new ArrayList<Models_Contrast_Add_VechileStyle>();
        Map map = Models_Contrast_AddVechileStyle_Method.getAdd_VechileStyle();
        Models_Contrast_VechileType info = null;
        if (map != null) {
            Iterator it = map.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry entry = (Map.Entry) it.next();
                Object key = entry.getKey();
                String data = String.valueOf(map.get(Integer.valueOf(String.valueOf(key))));
                info = JSON.parseObject(data, Models_Contrast_VechileType.class);
                if (null != info) {
                    mVidList.add(info.getVid());
                }
            }
        }
        return mVidList;
    }

    /**
     * @desc (将javabean转换成下一页面需要的javabean)
     * @createtime 2016/12/13 - 19:29
     * @author xiaoxue
     */
    public Models_Contrast_VechileType changeBean(int position){
        Models_Contrast_VechileType mModels_Contrast_VechileType=new Models_Contrast_VechileType();
        mModels_Contrast_VechileType.setFullName(mlist.get(position).getF_fullname());
        mModels_Contrast_VechileType.setVid(mlist.get(position).getF_vid());
        mModels_Contrast_VechileType.setTitle(mlist.get(position).getF_title());
        mModels_Contrast_VechileType.setBrand(mlist.get(position).getF_brand());
        mModels_Contrast_VechileType.setCarset(mlist.get(position).getF_carset());
        mModels_Contrast_VechileType.setCarsetTitle(mlist.get(position).getF_carsettitle());
        mModels_Contrast_VechileType.setImage(mlist.get(position).getF_image());
        mModels_Contrast_VechileType.setImageNum(mlist.get(position).getF_imagenum());
        mModels_Contrast_VechileType.setPrices(mlist.get(position).getF_price());
        mModels_Contrast_VechileType.setManu(mlist.get(position).getF_manu());
        mModels_Contrast_VechileType.setF_collectstatus(mlist.get(position).getF_collectstatus());
        return mModels_Contrast_VechileType;
    }

}