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
import com.etong.android.jxappusedcar.bean.UC_CollectOrScannedCarInfo;
import com.etong.android.jxappusedcar.content.UC_CarDetail_Activity;
import com.etong.android.jxbizusedcar.R;
import com.etong.android.jxbizusedcar.widget.UC_SlideDragView;

import java.util.ArrayList;
import java.util.List;

/**
 * @desc (收藏、浏览记录适配器)
 * @createtime 2016/10/31 0031--19:38
 * @Created by wukefan.
 */
public class UC_CollectOrScannedListAdapter extends BaseAdapter {

    private List<UC_SlideDragView> mDragviews = new ArrayList<UC_SlideDragView>();
    private List<UC_CollectOrScannedCarInfo> mData;
    private Context mContext;
    private UC_CarCallBack mCarCallBack;

    /**
     * 构造方法
     *
     * @param context
     */
    public UC_CollectOrScannedListAdapter(Context context, UC_CarCallBack mCarCallBack) {
        this.mContext = context;
        this.mCarCallBack = mCarCallBack;
        mData = new ArrayList<UC_CollectOrScannedCarInfo>();
    }

    /**
     * @param
     * @return
     * @desc (对外公布的更新ListView数据的方法)
     */
    public void updateCarList(List<UC_CollectOrScannedCarInfo> data) {
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
        ViewHolder holder = new ViewHolder();
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.uc_item_scanned_car, null);
            convertView.setTag(holder);
            UC_SlideDragView view = (UC_SlideDragView) convertView.findViewById(R.id.uc_drag_view);
            mDragviews.add(view);
            view.setOnDragStateListener(new UC_SlideDragView.DragStateListener() {
                @Override
                public void onOpened(UC_SlideDragView dragView) {
                    int pos = (int) dragView.getTag();
                    closeAnother(pos);
                }

                @Override
                public void onClosed(UC_SlideDragView dragView) {
                }

                @Override
                public void onForegroundViewClick(UC_SlideDragView dragView, View v) {
                    int pos = (int) dragView.getTag();
                    // 点击车辆跳转到车辆详情页面
                    Intent i = new Intent(mContext, UC_CarDetail_Activity.class);
                    i.putExtra("f_dvid", mData.get(pos).getF_dvid());
                    mContext.startActivity(i);
                }

                @Override
                public void onBackgroundViewClick(UC_SlideDragView dragView, View v) {
                    int pos = (int) dragView.getTag();
                    mCarCallBack.answerCarDelete(pos);
                }
            });

        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.dv = (UC_SlideDragView) convertView.findViewById(R.id.uc_drag_view);
        holder.dv.setTag(position);
        holder.dv.close();
        holder.carImg = (ImageView) convertView.findViewById(R.id.uc_iv_car_picture);
        holder.titleTxt = (TextView) convertView.findViewById(R.id.uc_txt_ca_car_title);
        holder.yearTxt = (TextView) convertView.findViewById(R.id.uc_txt_years_mileage);
        holder.priceTxt = (TextView) convertView.findViewById(R.id.uc_txt_cs_car_price);
        holder.authenticateTxt = (TextView) convertView.findViewById(R.id.uc_txt_ca_isauthenticate);

        holder.titleTxt.setText(mData.get(position).getF_cartitle());
        ImageProvider.getInstance().loadImage(holder.carImg, mData.get(position).getImage(), R.mipmap.uc_image_loading);
//        if (0.01 <= mData.get(position).getF_mileage() / 10000) {//里程公里数大于等于100的
//            String mileage = String.format("%.2f", mData.get(position).getF_mileage() / 10000);
//            holder.yearTxt.setText(mData.get(position).getF_registerdate() + "   " + mileage.substring(0, mileage.indexOf(".") + 2 + 1) + "万公里");
//        } else {//里程公里数小于100的
//            holder.yearTxt.setText(mData.get(position).getF_registerdate() + "   " + String.format("%.2f", mData.get(position).getF_mileage()) + "公里");
//        }
        holder.yearTxt.setText(mData.get(position).getF_registerdate() + "   " + mData.get(position).getF_mileage() + "万公里");
        holder.priceTxt.setText(mData.get(position).getF_price() + "万");

        if (1 == mData.get(position).getF_isauthenticate()) {
            holder.authenticateTxt.setText("已认证");
        } else if (0 == mData.get(position).getF_isauthenticate()) {
            holder.authenticateTxt.setText("未认证");
        }
        return convertView;
    }

    class ViewHolder {
        UC_SlideDragView dv;
        ImageView carImg;
        TextView titleTxt;
        TextView yearTxt;
        TextView priceTxt;
        TextView authenticateTxt;
    }

    /**
     * @desc (关闭所有item侧滑)
     */
    public void close() {
        for (int i = 0; i < mDragviews.size(); i++) {
            if (mDragviews.get(i).isOpen())
                mDragviews.get(i).closeAnim();
        }
    }

    /**
     * @desc (关闭其他item侧滑，除pos以外的)
     */
    public void closeAnother(int pos) {
        for (int i = 0; i < mDragviews.size(); i++) {
            if (i != pos) {
                if (mDragviews.get(i).isOpen())
                    mDragviews.get(i).closeAnim();
            }
        }
    }

    //回调接口
    public interface UC_CarCallBack {
        public void answerCarDelete(int position);  //类B的内部接口
    }
}
