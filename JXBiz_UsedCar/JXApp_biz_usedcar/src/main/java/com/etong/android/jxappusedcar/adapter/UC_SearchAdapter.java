package com.etong.android.jxappusedcar.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.etong.android.frame.widget.UC_BrandCarset;
import com.etong.android.jxappusedcar.R;
import com.etong.android.jxappusedcar.bean.UC_BrandBean;
import com.etong.android.jxappusedcar.content.UC_BuyCarFragment;
import org.simple.eventbus.EventBus;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @desc 所有车型 车系的adapter
 * @createtime 2016/11/4 - 16:07
 * @Created by xiaoxue.
 */

public class UC_SearchAdapter extends BaseAdapter{
    private Context mContext;
    private List<UC_BrandCarset> list;
    private UC_SearchCallBack iCallBack;

    public UC_SearchAdapter() {
    }
    public UC_SearchAdapter(Context context,UC_SearchCallBack searchCallBack) {
        mContext=context;
        list=new ArrayList<>();
        iCallBack=searchCallBack;
    }

    public void update(List<UC_BrandCarset> data){
        list=data;
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        convertView=LayoutInflater.from(mContext).inflate(R.layout.used_car_item_popupwindow,parent,false);
        TextView carName = (TextView) convertView.findViewById(R.id.uc_tv_list_item_pop);
        carName.setText(list.get(position).getName());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* mIsChoiceCar = true;  //选择了车
                mCarShowView.setVisibility(View.GONE);   //隐藏所有车系列表
                if (!mHisstoryList.isEmpty()) {   //判断历史列表是否有数据
                    mHisCarLayout.setVisibility(View.VISIBLE);
                }
                mSqliteDao.save(brandCarset, HIS_DATA); //保存历史数据*/

                iCallBack.SearchIntent(list.get(position));

                Map<String, String> brandMap = new HashMap<String, String>();
                Map<String, String> carsetMap = new HashMap<String, String>();
                if (("brand").equals(list.get(position).getType())) {
                    brandMap.put(list.get(position).getName(), list.get(position).getId());
                } else if (("carset").equals(list.get(position).getType())) {
                    carsetMap.put(list.get(position).getName(), list.get(position).getId());
                }
                UC_BrandBean brandBean = new UC_BrandBean(brandMap, carsetMap);
                EventBus.getDefault().post(brandBean, UC_BuyCarFragment.GET_DATABEAN_FROME_HOMEPAGE);//传给主页
                EventBus.getDefault().post(brandBean, "get type search data");//传给买车页
                ((Activity)mContext).finish();
            }
        });
        return convertView;
    }


    //点击列表item 回调接口
    public interface UC_SearchCallBack {
        public void SearchIntent(UC_BrandCarset brandCarset);
    }


}
