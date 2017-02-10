package com.etong.android.jxbizusedcar.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.etong.android.frame.common.ImageProvider;
import com.etong.android.jxappusedcar.content.UC_CarDetail_Activity;
import com.etong.android.jxbizusedcar.R;
import com.etong.android.jxbizusedcar.bean.UC_HomePageCar;
import com.etong.android.jxbizusedcar.viewholder.UC_HomePagerViewHolder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dasheng on 2016/10/11.
 */

public class UC_HomePagerRecycleViewAdapter extends RecyclerView.Adapter<UC_HomePagerViewHolder> {

    private Context mContext;
    private List<UC_HomePageCar> mDatas;
    private boolean isChageTime = false;

    public UC_HomePagerRecycleViewAdapter(Context context) {

        mContext = context;
        mDatas = new ArrayList<UC_HomePageCar>();

    }

    public void updateData(List<UC_HomePageCar> datas, boolean isChageTime) {
        this.isChageTime = isChageTime;
        mDatas = datas;
        notifyDataSetChanged();

    }


    @Override
    public UC_HomePagerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        UC_HomePagerViewHolder holder = new UC_HomePagerViewHolder(LayoutInflater.from(mContext).inflate(R.layout.uc_item_home_pager, parent, false));

        return holder;
    }

    @Override
    public void onBindViewHolder(UC_HomePagerViewHolder holder, final int position) {

        UC_HomePageCar car = mDatas.get(position);

        if (car != null) {

            if (car.getF_cartype() == null) {
                holder.mCarProduceView.setText(car.getF_cartitle());
            }

            if (car.getF_cartitle() == null) {
                holder.mCarProduceView.setText(car.getF_cartype());
            }
            String registerdate = null;
            if (!isChageTime) {
                registerdate = car.getF_registerdate();
            } else {
                try {
                    registerdate = mTimeConvert(car.getF_registerdate());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

//            if (0.01 <= car.getF_mileage() / 10000) {//里程公里数大于等于100的
//                String mileage = String.format("%.2f", car.getF_mileage() / 10000);
//                holder.mUseTimeView.setText(registerdate + "  " + mileage.substring(0, mileage.indexOf(".") + 2 + 1) + "万公里");
//            } else {//里程公里数小于100的
//                holder.mUseTimeView.setText(registerdate + "  " + String.format("%.2f", car.getF_mileage()) + "公里");
//            }
            holder.mUseTimeView.setText(registerdate + "  " + car.getF_mileage() + "万公里");

            holder.mCarPriceView.setText(car.getF_price() + "万");
            try {
                ImageProvider.getInstance().loadImage(holder.mCarShowView, car.getImage().trim(), R.mipmap.uc_select_image_loading);

            } catch (Exception e) {
            }

            holder.mItemLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 点击车辆跳转到车辆详情页面
//                    Toast.makeText(mContext,position+"",Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(mContext, UC_CarDetail_Activity.class);
                    i.putExtra(UC_CarDetail_Activity.CAR_DETAIL_DVID, mDatas.get(position).getF_dvid());
                    mContext.startActivity(i);
                }
            });
        }
    }

    public String mTimeConvert(String time) throws ParseException {
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy年MM月d日");
        return sdf2.format(sdf1.parse(time));
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }
}
