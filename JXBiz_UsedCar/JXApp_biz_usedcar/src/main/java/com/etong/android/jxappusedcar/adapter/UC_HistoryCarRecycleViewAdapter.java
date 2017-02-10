package com.etong.android.jxappusedcar.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.etong.android.frame.widget.UC_BrandCarset;
import com.etong.android.jxappusedcar.R;
import com.etong.android.jxappusedcar.bean.UC_BrandBean;
import com.etong.android.jxappusedcar.content.UC_BuyCarFragment;
import com.etong.android.jxappusedcar.viewHolder.UC_HistoryCarViewHodler;

import org.simple.eventbus.EventBus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Dasheng on 2016/10/11.
 */

public class UC_HistoryCarRecycleViewAdapter extends RecyclerView.Adapter<UC_HistoryCarViewHodler> {

    private Context mContenxt;
    private List<UC_BrandCarset> mDatas;

    public UC_HistoryCarRecycleViewAdapter(Context context, List<UC_BrandCarset> datas) {
        mContenxt = context;
        mDatas = datas;
    }
    @Override
    public UC_HistoryCarViewHodler onCreateViewHolder(ViewGroup parent, int viewType) {

        UC_HistoryCarViewHodler hodler = new UC_HistoryCarViewHodler(LayoutInflater.from(mContenxt).inflate(R.layout.used_car_item_history_car, parent, false));

        return hodler;
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    @Override
    public void onBindViewHolder(UC_HistoryCarViewHodler holder, final int position) {

        holder.mHisCarView.setText(mDatas.get(position).getName());
        holder.mHisCarLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String,String> brandMap=new HashMap<String,String>();
                Map<String,String> carsetMap=new HashMap<String,String>();
                if(("brand").equals(mDatas.get(position).getType())){
                    brandMap.put(mDatas.get(position).getName(),mDatas.get(position).getId());
                }else if(("carset").equals(mDatas.get(position).getType())){
                    carsetMap.put(mDatas.get(position).getName(),mDatas.get(position).getId());
                }
                UC_BrandBean brandBean = new UC_BrandBean(brandMap,carsetMap);
                EventBus.getDefault().post(brandBean, UC_BuyCarFragment.GET_DATABEAN_FROME_HOMEPAGE);//将品牌javabean传给主页
                EventBus.getDefault().post(brandBean,"get type search data");//将品牌javabean传给买车页

                ((Activity)mContenxt).finish();
            }
        });


    }

}



