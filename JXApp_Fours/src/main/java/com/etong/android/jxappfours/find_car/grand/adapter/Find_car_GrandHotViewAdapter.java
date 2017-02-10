package com.etong.android.jxappfours.find_car.grand.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.etong.android.jxappfours.R;
import com.etong.android.jxappfours.find_car.grand.hotkey.Find_car_HotKeyActivity;

import java.util.List;

/**
 *
 * GridView适配器 热门品牌
 * Created by Administrator on 2016/8/2.
 */
public class Find_car_GrandHotViewAdapter extends BaseAdapter {
    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private List<String> mDatas;
    private DrawerLayout mDrawerLayout;

    public Find_car_GrandHotViewAdapter(Context context, List<String> datas, DrawerLayout drawerLayout) {
        this.mContext = context;
        this.mDatas = datas;
        mLayoutInflater = LayoutInflater.from(context);
        this.mDrawerLayout= drawerLayout;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder vh = null;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.grand_hottag_item, parent, false);
            vh = new ViewHolder();
            vh.tv = (TextView) convertView.findViewById(R.id.tv_hot_tag);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        vh.tv.setText(mDatas.get(position));

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(mContext,mDatas.get(position),Toast.LENGTH_SHORT).show();

//                mDrawerLayout.openDrawer(Gravity.RIGHT);
                Intent i = new Intent(mContext, Find_car_HotKeyActivity.class);
                i.putExtra("hotTitleName", mDatas.get(position));
                mContext.startActivity(i);

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
    }
}