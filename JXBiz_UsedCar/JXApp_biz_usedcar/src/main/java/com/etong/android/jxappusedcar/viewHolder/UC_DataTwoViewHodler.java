package com.etong.android.jxappusedcar.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.etong.android.jxappusedcar.R;

public class UC_DataTwoViewHodler extends RecyclerView.ViewHolder {

    public ImageView two_car_picture;
    public TextView two_car_title;
    public TextView two_used_time;
    public TextView two_car_price;
    public TextView two_car_isAttestation;


    public UC_DataTwoViewHodler(View itemView) {
        super(itemView);
        two_car_picture =(ImageView)itemView.findViewById(R.id.used_car_two_iv_car_picture);
        two_car_title =(TextView)itemView.findViewById(R.id.used_car_two_tv_title);
        two_used_time =(TextView)itemView.findViewById(R.id.used_car_two_tv_time);
        two_car_price =(TextView)itemView.findViewById(R.id.used_car_two_tv_price);
        two_car_isAttestation =(TextView)itemView.findViewById(R.id.used_car_two_tv_isAttestation);

    }

}