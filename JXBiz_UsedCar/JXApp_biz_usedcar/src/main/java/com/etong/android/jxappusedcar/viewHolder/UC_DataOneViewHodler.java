package com.etong.android.jxappusedcar.viewHolder;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.etong.android.jxappusedcar.R;

public class UC_DataOneViewHodler extends RecyclerView.ViewHolder {


    public LinearLayout ll_item_layout;
    public TextView car_isAttestation;
    public TextView car_price;
    public TextView used_time;
    public TextView car_title;
    public ImageView car_picture;


    public UC_DataOneViewHodler(View itemView) {
        super(itemView);
        ll_item_layout =(LinearLayout)itemView.findViewById(R.id.ll_item_layout);
        car_picture =(ImageView)itemView.findViewById(R.id.used_car_iv_car_picture);
        car_title =(TextView)itemView.findViewById(R.id.used_car_tv_car_title);
        used_time =(TextView)itemView.findViewById(R.id.used_car_tv_used_car_time);
        car_price =(TextView)itemView.findViewById(R.id.used_car_tv_price);
        car_isAttestation =(TextView)itemView.findViewById(R.id.used_car_tv_isAttestation);

    }
}