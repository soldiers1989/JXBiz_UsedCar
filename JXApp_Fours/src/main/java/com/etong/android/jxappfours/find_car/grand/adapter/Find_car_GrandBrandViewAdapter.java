package com.etong.android.jxappfours.find_car.grand.adapter;

import android.content.Context;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.etong.android.frame.common.ImageProvider;
import com.etong.android.frame.request_init.FrameHttpTag;
import com.etong.android.jxappfours.R;
import com.etong.android.jxappfours.find_car.grand.bean.Find_car_GrandBrandItemBean;

import org.simple.eventbus.EventBus;

import java.util.List;

/**
 *
 * GridView适配器  品牌
 * Created by Administrator on 2016/8/2.
 */
public class Find_car_GrandBrandViewAdapter extends BaseAdapter {
    private LayoutInflater mLayoutInflater;
    private Context context;
    private List<Find_car_GrandBrandItemBean> mDatas;
    private DrawerLayout mDrawerLayout;
    private EventBus mEventBus;     // EventBus传入

    public Find_car_GrandBrandViewAdapter(Context context, List<Find_car_GrandBrandItemBean> datas
            , DrawerLayout drawerLayout, EventBus eventBus) {
        this.context = context;
        this.mDatas = datas;
        mLayoutInflater = LayoutInflater.from(context);
        this.mDrawerLayout= drawerLayout;
        this.mEventBus = eventBus;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder vh = null;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.grand_gridview_item, parent, false);
            vh = new ViewHolder();
            vh.tv = (TextView) convertView.findViewById(R.id.grand_txt_title);
            vh.iv = (ImageView) convertView.findViewById(R.id.grand_img_icon);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        vh.tv.setText(mDatas.get(position).getTitle());
        vh.iv.setImageResource(mDatas.get(position).getId());
        ImageProvider.getInstance().loadImage(vh.iv,mDatas.get(position).getImage(),R.mipmap.fours_grand_loading);
//        vh.iv.setImageDrawable(context.getResources().getDrawable(mDatas.get(position).getId()));

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // 传入ID，调用详细车系
                mEventBus.post(mDatas.get(position).getBrand_id(), FrameHttpTag.GET_CARSET_DETAIL_BY_GRAND_ID);
                mDrawerLayout.openDrawer(Gravity.RIGHT);
            }
        });

        return convertView; // 返回ImageView
    }

    /*
     * 功能：获得当前选项的ID
     *
     * @see android.widget.Adapter#getItemId(int)
     */
    @Override
    public long getItemId(int position) {
        //System.out.println("getItemId = " + position);
        return position;
    }

    /*
     * 功能：获得当前选项
     *
     * @see android.widget.Adapter#getItem(int)
     */
    @Override
    public Object getItem(int position) {
        return position;
    }

    /*
     * 获得数量
     *
     * @see android.widget.Adapter#getCount()
     */
    @Override
    public int getCount() {
        return mDatas.size();
    }

    class ViewHolder {
        public TextView tv;
        public ImageView iv;
    }
}