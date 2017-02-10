package com.etong.android.jxappfours.find_car.grand.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.etong.android.frame.request_init.FrameHttpTag;
import com.etong.android.frame.utils.DefinedToast;
import com.etong.android.frame.utils.L;
import com.etong.android.frame.utils.SerializableObject;
import com.etong.android.jxappfours.R;
import com.etong.android.jxappfours.find_car.collect_search.utils.Find_Car_VechileCollect_Method;
import com.etong.android.jxappfours.find_car.grand.bean.FC_CalcuCarPriceBean;
import com.etong.android.jxappfours.find_car.grand.car_config.Find_car_CarConfigActivity;
import com.etong.android.jxappfours.find_car.grand.find_car_shopcar_calc.Find_car_CalcuTotalActivity;
import com.etong.android.jxappfours.find_car.grand.provider.FC_GetInfo_Provider;
import com.etong.android.jxappfours.models_contrast.common.Models_Contrast_AddVechileStyle_Method;
import com.etong.android.jxappfours.models_contrast.javabean.Models_Contrast_Add_VechileStyle;
import com.etong.android.jxappfours.models_contrast.javabean.Models_Contrast_VechileType;
import com.etong.android.jxappfours.order.FO_OrderActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Ellison.Sun on 2016/8/13.
 * <p/>
 * 车系中item的适配器
 */
public class FC_CarsetItemAdapter extends BaseAdapter {

    private Context mContext;
    private List<Models_Contrast_VechileType> mData;
    LayoutInflater mInflater;

    public FC_CarsetItemAdapter(Context context, List<Models_Contrast_VechileType> data) {
        this.mContext = context;
        this.mData = data;
        mInflater = LayoutInflater.from(context);
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

    private List<Integer> getCollectVidList() {
        //获取本地收藏数据的vid
        List<Integer> mCollectVidList = new ArrayList<Integer>();
        Map collectMap = Find_Car_VechileCollect_Method.getVechileCollectInfo();
        Models_Contrast_VechileType collectInfo = null;
        if (collectMap != null) {
            Iterator it = collectMap.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry entry = (Map.Entry) it.next();
                Object key = entry.getKey();
                String data = String.valueOf(collectMap.get(Integer.valueOf(String.valueOf(key))));
                collectInfo = JSON.parseObject(data, Models_Contrast_VechileType.class);
                if (null != collectInfo) {
                    mCollectVidList.add(collectInfo.getVid());
                }
            }
        }
        return mCollectVidList;
    }

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

    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {


        final ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.find_car_detail_list_item, null);
            holder = new ViewHolder();
            holder.find_car_carset_detail_item_title = (ViewGroup) convertView.findViewById(R.id.find_car_carset_detail_item_title);
            holder.find_car_carset_item_title = (TextView) convertView.findViewById(R.id.find_car_carset_item_title);
            holder.find_car_carset_tv_guid_price = (TextView) convertView.findViewById(R.id.find_car_carset_tv_guid_price);
            holder.find_car_carset_tv_reference_price = (TextView) convertView.findViewById(R.id.find_car_carset_tv_reference_price);

            holder.fc_detail_list_btn_collect = (Button) convertView.findViewById(R.id.fc_detail_list_btn_collect);
            holder.fc_detail_list_btn_calculate = (Button) convertView.findViewById(R.id.fc_detail_list_btn_calculate);
            holder.fc_detail_list_btn_comparison = (Button) convertView.findViewById(R.id.fc_detail_list_btn_comparison);
            holder.fc_detail_list_btn_ask = (Button) convertView.findViewById(R.id.fc_detail_list_btn_ask);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // 设置每个Item的文字
        holder.find_car_carset_item_title.setText(mData.get(position).getTitle());
        holder.find_car_carset_tv_guid_price.setText(mData.get(position).getPrices() + "万");
        holder.find_car_carset_tv_reference_price.setText(mData.get(position).getPrices() + "万");

        if (getCollectVidList().contains(mData.get(position).getVid())) {
            holder.fc_detail_list_btn_collect.setSelected(true);
            holder.fc_detail_list_btn_collect.setText("已收藏");
        }else{
            holder.fc_detail_list_btn_collect.setSelected(false);
            holder.fc_detail_list_btn_collect.setText("收藏");
        }
        if (getComparisonVidList().contains(mData.get(position).getVid())) {
            holder.fc_detail_list_btn_comparison.setSelected(true);
            holder.fc_detail_list_btn_comparison.setText("已加入");
        }else{
            holder.fc_detail_list_btn_comparison.setSelected(false);
            holder.fc_detail_list_btn_comparison.setText("加入对比");
        }

        holder.find_car_carset_detail_item_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Toast.makeText(mContext, "进入车型详情界面", Toast.LENGTH_SHORT).show();

                Intent toCarsetActivity = new Intent(mContext, Find_car_CarConfigActivity.class);

                Map map = new HashMap<>();
                map.put("dataType", mData.get(position));
                // 传递数据
                final SerializableObject myMap = new SerializableObject();
                myMap.setObject(map);// 将map数据添加到封装的myMap中
                Bundle bundle = new Bundle();
                bundle.putSerializable("dataTypeMap", myMap);
                toCarsetActivity.putExtras(bundle);

                mContext.startActivity(toCarsetActivity);
            }
        });

        holder.fc_detail_list_btn_ask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(mContext, Fours_Order_OrderActivity.class);
                Intent intent = new Intent(mContext, FO_OrderActivity.class);
                intent.putExtra("vid", mData.get(position).getVid() + "");
                intent.putExtra("flag", 4);
                intent.putExtra("carImage", mData.get(position).getImage());
                intent.putExtra("vTitleName", mData.get(position).getTitle());
                intent.putExtra("brand", mData.get(position).getBrand());
                intent.putExtra("isSelectCar", true);
                mContext.startActivity(intent);
            }
        });

        holder.fc_detail_list_btn_collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                L.d("-------------->", holder.fc_detail_list_btn_collect.isSelected() + "");
                if (!holder.fc_detail_list_btn_collect.isSelected()) {
                    Find_Car_VechileCollect_Method.cartAdd(mData.get(position));
                    holder.fc_detail_list_btn_collect.setSelected(true);
                    holder.fc_detail_list_btn_collect.setText("已收藏");
                } else {
                    Find_Car_VechileCollect_Method.remove(mData.get(position).getVid());
                    holder.fc_detail_list_btn_collect.setSelected(false);
                    holder.fc_detail_list_btn_collect.setText("收藏");
                }
            }
        });

        holder.fc_detail_list_btn_comparison.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!holder.fc_detail_list_btn_comparison.isSelected()) {
                    if(getComparisonVidList().size()>=12){
//                       CToast.toastMessage("对比的个数已满",0);
                        DefinedToast.showToast(mContext,"对比的个数已满", 0);
                        return;
                    }
//                    Models_Contrast_Add_VechileStyle add = new Models_Contrast_Add_VechileStyle();
//                    add.setTitle(mData.get(position).getTitle());
//                    add.setId(mData.get(position).getVid());
//                    Models_Contrast_AddVechileStyle_Method.cartAdd(add);
                    Models_Contrast_AddVechileStyle_Method.cartAdd(mData.get(position));
                    holder.fc_detail_list_btn_comparison.setSelected(true);
                    holder.fc_detail_list_btn_comparison.setText("已加入");
                } else {
                    Models_Contrast_AddVechileStyle_Method.remove(mData.get(position).getVid());
                    holder.fc_detail_list_btn_comparison.setSelected(false);
                    holder.fc_detail_list_btn_comparison.setText("加入对比");
                }
            }
        });

        // 点击购车计算跳转到购车计算页面中
        holder.fc_detail_list_btn_calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FC_CalcuCarPriceBean bean = new FC_CalcuCarPriceBean();
                bean.setCarName(mData.get(position).getTitle());
                bean.setCarPrice((int)((mData.get(position).getPrices()) * 10000));

                Intent i = new Intent(mContext, Find_car_CalcuTotalActivity.class);
                i.putExtra(FrameHttpTag.TRANSFER_CAR_PRICE_INFO, bean);
                mContext.startActivity(i);
            }
        });

        return convertView;
    }

    static class ViewHolder {
        ViewGroup find_car_carset_detail_item_title;
        TextView find_car_carset_item_title;
        TextView find_car_carset_tv_guid_price;
        TextView find_car_carset_tv_reference_price;
        Button fc_detail_list_btn_collect;//收藏
        Button fc_detail_list_btn_calculate;//购车计算
        Button fc_detail_list_btn_comparison;//加入对比
        Button fc_detail_list_btn_ask;//询底价
    }

    public void updateDataChanged(List<Models_Contrast_VechileType> data) {
        this.mData = data;
        this.notifyDataSetChanged();
    }
}
