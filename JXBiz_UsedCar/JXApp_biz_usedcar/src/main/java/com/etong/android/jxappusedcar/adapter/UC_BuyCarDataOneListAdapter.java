package com.etong.android.jxappusedcar.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.etong.android.frame.common.ImageProvider;
import com.etong.android.jxappusedcar.R;
import com.etong.android.jxappusedcar.bean.UC_CarListBean;
import com.etong.android.jxappusedcar.content.UC_CarDetail_Activity;
import com.etong.android.jxappusedcar.content.UC_SelectImageActivity;
import com.etong.android.jxappusedcar.viewHolder.UC_DataOneViewHodler;
import com.etong.android.jxappusedcar.viewHolder.UC_DataTwoViewHodler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 买车列表适配器
 * Created by Dasheng on 2016/10/16.
 */

public class UC_BuyCarDataOneListAdapter extends BaseAdapter {

    private Context mContext;
    private List<UC_CarListBean> mDatas;
    private boolean click;//是否点击了按钮

    public UC_BuyCarDataOneListAdapter(Context context) {
        this.mContext = context;
        this.mDatas = new ArrayList<>();
    }
    //更新数据的方法
    public void upDate(List<UC_CarListBean> datas,boolean isClick) {
        mDatas = datas;
       /*  //遍历其中的javabean，将javabean中type设置为传递过来的type值
        for(int i=0; i<datas.size(); i++) {
            UC_CarListBean u = datas.get(i);
            u.setmType(isClick);
        }*/
        click=isClick;
        notifyDataSetChanged();
    }
    //清空
    public void clear() {
        mDatas.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        UC_DataOneViewHodler viewHolder = null;
        if (null == convertView) {
            viewHolder = new UC_DataOneViewHodler();
            //第一个布局
            convertView = LayoutInflater.from(mContext).inflate(R.layout.used_car_item_buy_car_data_one, null);
            viewHolder.ll_item_layout = (LinearLayout) convertView.findViewById(R.id.ll_item_layout);
            viewHolder.car_picture = (ImageView) convertView.findViewById(R.id.used_car_iv_car_picture);
            viewHolder.car_title = (TextView) convertView.findViewById(R.id.used_car_tv_car_title);
            viewHolder.used_time = (TextView) convertView.findViewById(R.id.used_car_tv_used_car_time);
            viewHolder.car_price = (TextView) convertView.findViewById(R.id.used_car_tv_price);
            viewHolder.car_isAttestation = (TextView) convertView.findViewById(R.id.used_car_tv_isAttestation);
            //第二个布局
            viewHolder.ll_item_two_layout = (LinearLayout) convertView.findViewById(R.id.ll_item_two_layout);
            viewHolder.car_two_picture = (ImageView) convertView.findViewById(R.id.used_car_two_iv_car_picture);
            viewHolder.car_two_title = (TextView) convertView.findViewById(R.id.used_car_two_tv_title);
            viewHolder.used_two_time = (TextView) convertView.findViewById(R.id.used_car_two_tv_time);
            viewHolder.car_two_price = (TextView) convertView.findViewById(R.id.used_car_two_tv_price);
            viewHolder.car_two_isAttestation = (TextView) convertView.findViewById(R.id.used_car_two_tv_isAttestation);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (UC_DataOneViewHodler) convertView.getTag();
        }
        try {
            viewHolder.used_time.setText(mTimeConvert(mDatas.get(position).getF_registerdate()) + "   " + mDatas.get(position).getF_mileage() + "万公里");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(click){//第二种布局
            viewHolder.ll_item_two_layout.setVisibility(View.VISIBLE);
            viewHolder.ll_item_layout.setVisibility(View.GONE);
            ImageProvider.getInstance().loadImage(viewHolder.car_two_picture, mDatas.get(position).getImage(), R.mipmap.uc_select_image_loading);
            viewHolder.car_two_title.setText(mDatas.get(position).getF_cartitle());
            viewHolder.car_two_price.setText(mDatas.get(position).getF_price() + "万");
            if (mDatas.get(position).getF_isauthenticate() == 1) {
                viewHolder.car_two_isAttestation.setText("已认证");
            } else if (mDatas.get(position).getF_isauthenticate() == 0) {
                viewHolder.car_two_isAttestation.setText("未认证");
            }
            viewHolder.ll_item_two_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mDatas.size()!=0){
                        Intent intent=new Intent(mContext, UC_CarDetail_Activity.class);
                        intent.putExtra(UC_CarDetail_Activity.CAR_DETAIL_DVID,mDatas.get(position).getF_dvid());
                        mContext.startActivity(intent);
                    }
                }
            });
        }else{//第一种布局
            viewHolder.ll_item_two_layout.setVisibility(View.GONE);
            viewHolder.ll_item_layout.setVisibility(View.VISIBLE);
            ImageProvider.getInstance().loadImage(viewHolder.car_picture, mDatas.get(position).getImage(), R.mipmap.uc_image_loading);
            viewHolder.car_title.setText(mDatas.get(position).getF_cartitle());
            viewHolder.car_price.setText(mDatas.get(position).getF_price() + "万");
            if (mDatas.get(position).getF_isauthenticate() == 1) {
                viewHolder.car_isAttestation.setText("已认证");
            } else if (mDatas.get(position).getF_isauthenticate() == 0) {
                viewHolder.car_isAttestation.setText("未认证");
            }
            viewHolder.ll_item_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mDatas.size() != 0) {
                        Intent intent = new Intent(mContext, UC_CarDetail_Activity.class);
                        intent.putExtra(UC_CarDetail_Activity.CAR_DETAIL_DVID, mDatas.get(position).getF_dvid());
                        mContext.startActivity(intent);
                    }
                }
            });
        }

        return convertView;
    }

        private static class UC_DataOneViewHodler {
            public LinearLayout ll_item_layout;
            public TextView car_isAttestation;
            public TextView car_price;
            public TextView used_time;
            public TextView car_title;
            public ImageView car_picture;

            public LinearLayout ll_item_two_layout;
            public TextView car_two_isAttestation;
            public TextView car_two_price;
            public TextView used_two_time;
            public TextView car_two_title;
            public ImageView car_two_picture;

        }
        /**
         * @desc 将2016-11-01转换成2016年11月1日
         * @createtime 2016/11/1 - 13:48
         * @author xiaoxue
         */

    public String mTimeConvert(String time) throws ParseException {
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy年MM月d日");
        return sdf2.format(sdf1.parse(time));
    }
}
