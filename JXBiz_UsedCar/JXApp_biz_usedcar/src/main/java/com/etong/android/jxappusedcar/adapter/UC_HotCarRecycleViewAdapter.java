package com.etong.android.jxappusedcar.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.etong.android.jxappusedcar.R;
import com.etong.android.jxappusedcar.bean.UC_BrandBean;
import com.etong.android.jxappusedcar.bean.UC_HotCarType;
import com.etong.android.jxappusedcar.content.UC_BuyCarFragment;
import com.etong.android.jxappusedcar.viewHolder.UC_SearchViewHolder;

import org.simple.eventbus.EventBus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Dasheng on 2016/10/11.
 */

public class UC_HotCarRecycleViewAdapter extends RecyclerView.Adapter<UC_SearchViewHolder>{

    private Context mContenxt;
    private List<UC_HotCarType> mDatas;

    public UC_HotCarRecycleViewAdapter(Context context, List<UC_HotCarType> datas){
        mContenxt = context;
        mDatas = datas;
    }

    public void updata(List<UC_HotCarType> datas){
        mDatas = datas;
        notifyDataSetChanged();
    }

    @Override
    public UC_SearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        UC_SearchViewHolder holder = new UC_SearchViewHolder(LayoutInflater.from(mContenxt).inflate(R.layout.used_car_item_search,parent,false));
        return holder;
    }

    @Override
    public void onBindViewHolder(UC_SearchViewHolder holder, final int position) {

        holder.mHotCarNameView.setText(mDatas.get(position).getF_carset());
        holder.mHotCarNameView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String,String> brandMap=new HashMap<String,String>();
                Map<String,String> carsetMap=new HashMap<String,String>();
                carsetMap.put(mDatas.get(position).getF_carset(),mDatas.get(position).getF_carsetid()+"");

                UC_BrandBean brandBean = new UC_BrandBean(brandMap,carsetMap);
                // 切换页面  将品牌传给主页
                EventBus.getDefault().post(brandBean, UC_BuyCarFragment.GET_DATABEAN_FROME_HOMEPAGE);
                // 发送品牌到买车页面
                EventBus.getDefault().post(brandBean,"get type search data");
                ((Activity)mContenxt).finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }
}
