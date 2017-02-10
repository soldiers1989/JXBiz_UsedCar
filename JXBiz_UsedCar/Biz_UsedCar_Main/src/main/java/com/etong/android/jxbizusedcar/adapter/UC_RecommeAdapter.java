package com.etong.android.jxbizusedcar.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.etong.android.frame.common.ImageProvider;
import com.etong.android.jxappusedcar.content.UC_CarDetail_Activity;
import com.etong.android.jxbizusedcar.R;
import com.etong.android.jxbizusedcar.bean.UC_MineAllDataBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @desc (为你推荐适配器)
 * @createtime 2016/11/3 0003--16:44
 * @Created by wukefan.
 */
public class UC_RecommeAdapter extends BaseAdapter {

    private List<UC_MineAllDataBean.CarlistBean> mData;
    private Context mContext;

    public UC_RecommeAdapter(Context context) {
        this.mContext = context;
        mData = new ArrayList<UC_MineAllDataBean.CarlistBean>();
    }

    /**
     * @param
     * @return
     * @desc (对外公布的更新ListView数据的方法)
     */
    public void updateCarList(List<UC_MineAllDataBean.CarlistBean> data) {
        this.mData = data;
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
        return 0;
    }

    @Override
   public View getView(final int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(mContext).inflate(R.layout.uc_item_recomme_for_you, null);

        ImageView carImg = (ImageView) convertView.findViewById(R.id.uc_recomme_iv_car_picture);
        TextView titleTxt = (TextView) convertView.findViewById(R.id.uc_recomme_txt_car_title);
        TextView dateMileageTxt = (TextView) convertView.findViewById(R.id.uc_recomme_txt_car_date_mileage);
        TextView priceTxt = (TextView) convertView.findViewById(R.id.uc_recomme_txt_car_price);

        titleTxt.setText(mData.get(position).getF_cartitle());
        ImageProvider.getInstance().loadImage(carImg, mData.get(position).getImage(), R.mipmap.uc_image_loading);
//                if (0.01 <= mData.get(position).getF_mileage() / 10000) {//里程公里数大于等于100的
//                    String mileage = String.format("%.2f", mData.get(position).getF_mileage() / 10000);
//                    dateMileageTxt.setText(mData.get(position).getF_registerdate() + "   " + mileage.substring(0, mileage.indexOf(".") + 2 + 1) + "万公里");
//                } else {//里程公里数小于100的
//                    dateMileageTxt.setText(mData.get(position).getF_registerdate() + "   " + String.format("%.2f", mData.get(position).getF_mileage()) + "公里");
//                }
        dateMileageTxt.setText(mData.get(position).getF_registerdate() + "   " + mData.get(position).getF_mileage() + "万公里");
        priceTxt.setText(mData.get(position).getF_price() + "万");

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 点击车辆跳转到车辆详情页面
                Intent i = new Intent(mContext, UC_CarDetail_Activity.class);
                i.putExtra("f_dvid", mData.get(position).getF_dvid());
                mContext.startActivity(i);
            }
        });

        return convertView;
    }
}
