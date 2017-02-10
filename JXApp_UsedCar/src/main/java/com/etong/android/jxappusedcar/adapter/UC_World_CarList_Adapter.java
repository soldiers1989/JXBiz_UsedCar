package com.etong.android.jxappusedcar.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.etong.android.frame.common.ImageProvider;
import com.etong.android.frame.user.FrameEtongApplication;
import com.etong.android.frame.utils.L;
import com.etong.android.frame.utils.ValueFormatUtils;
import com.etong.android.jxappusedcar.R;
import com.etong.android.jxappusedcar.content.UC_CarDetailActivity;
import com.etong.android.jxappusedcar.javabean.UC_World_CarListJavaBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @desc (二手车列表适配器)
 * @createtime 2016/10/9 0009--15:06
 * @Created by wukefan.
 */
public class UC_World_CarList_Adapter extends BaseAdapter {
    private Context mContext;
    private List<UC_World_CarListJavaBean> mData;     // 传送过来的数据
    LayoutInflater mInflater;
    private boolean isSearch = false;//是否是搜索界面

    /**
     * 构造方法
     *
     * @param context
     */
    public UC_World_CarList_Adapter(Context context) {
        this.mContext = context;
        mData = new ArrayList<UC_World_CarListJavaBean>();
        mInflater = LayoutInflater.from(context);
    }

    public void clear() {
        mData.clear();
        notifyDataSetChanged();
    }

    /**
     * @param
     * @return
     * @desc (对外公布的更新ListView数据的方法)
     * @user sunyao
     * @createtime 2016/10/6 - 15:57
     */
    public void updateCarList(List<UC_World_CarListJavaBean> data) {
        this.mData = data;
        notifyDataSetChanged();
    }

    /**
     * @desc (更新ListView数据, 搜索界面的)
     * @createtime 2016/11/28 0028-19:22
     * @author wukefan
     */
    public void updateSearchCarList(List<UC_World_CarListJavaBean> data, boolean isSearch) {
        this.mData = data;
        this.isSearch = isSearch;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.used_car_world_lv_items, null);
            holder = new ViewHolder();
            holder.uc_img = (ImageView) convertView.findViewById(R.id.used_car_img_pic);
            holder.uc_title = (TextView) convertView.findViewById(R.id.used_car_txt_title);
            holder.uc_year = (TextView) convertView.findViewById(R.id.used_car_txt_year);
            holder.uc_mileage = (TextView) convertView.findViewById(R.id.used_car_txt_mileage);
            holder.uc_time = (TextView) convertView.findViewById(R.id.used_car_txt_time);
            holder.uc_price = (TextView) convertView.findViewById(R.id.used_car_txt_price);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ImageProvider.getInstance().loadImage(holder.uc_img, mData.get(position).getImage(), R.mipmap.used_car_default_img);
        holder.uc_title.setText(mData.get(position).getF_cartitle() + "");
//        String time=mData.get(position).getF_registerdate().substring(0,4);
        holder.uc_year.setText(mData.get(position).getF_registerdate() + "");
        holder.uc_time.setText(mData.get(position).getF_createtime() + "发布");

        holder.uc_price.setText(ValueFormatUtils.setIntOrPointValue(mData.get(position).getF_price()) + "万");
        holder.uc_mileage.setText(ValueFormatUtils.setIntOrPointValue(mData.get(position).getF_mileage()) + "万公里");

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(mContext, UC_CarDetailActivity.class);
                i.putExtra(UC_CarDetailActivity.UC_CARDETAIL_ID, mData.get(position).getF_dvid());
                i.putExtra("UC_World_CarListJavaBean", mData.get(position));
                mContext.startActivity(i);
                if (isSearch) {
                    FrameEtongApplication.getApplication().addSearchUsedCarHistory(mData.get(position).getF_cartitle());
                }
            }
        });


        return convertView;
    }

    static class ViewHolder {
        ImageView uc_img;
        TextView uc_title;
        TextView uc_year;
        TextView uc_mileage;
        TextView uc_time;
        TextView uc_price;
    }

}
