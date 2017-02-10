package com.etong.android.jxappfours.find_car.grand.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;


import com.etong.android.frame.publisher.HttpMethod;
import com.etong.android.frame.request_init.FrameHttpUri;
import com.etong.android.jxappfours.R;
import com.etong.android.jxappfours.find_car.grand.hotkey.Find_car_HotKeyActivity;
import com.etong.android.jxappfours.find_car.grand.listener.OnTagClickListener;
import com.etong.android.jxappfours.find_car.grand.view.GrandFlowTagLayout;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by HanHailong on 15/10/19.
 */
public class Find_car_GrandTagAdapter<T> extends BaseAdapter implements OnTagClickListener {

    private final Context mContext;
    private final List<T> mDataList;
    private Random random = new Random();

    public Find_car_GrandTagAdapter(Context context) {
        this.mContext = context;
        mDataList = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return mDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return mDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.grand_hottag_item, null);

        TextView tvHotTag = (TextView) view.findViewById(R.id.tv_hot_tag);
        T t = mDataList.get(position);

        if (t instanceof String) {
            tvHotTag.setText((String) t);

            if (random.nextInt(10)+1 > 5) {
                tvHotTag.setBackgroundResource(R.drawable.find_car_grand_tag_rectangle_bg_blue);
            } else {
                tvHotTag.setBackgroundResource(R.drawable.find_car_grand_tag_rectangle_bg_orange);
            }
        }

        tvHotTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(mContext,mDataList.get(position)+"",Toast.LENGTH_SHORT).show();

//                mDrawerLayout.openDrawer(Gravity.RIGHT);
                Intent i = new Intent(mContext, Find_car_HotKeyActivity.class);
                i.putExtra("hotTitleName", mDataList.get(position)+"");
                mContext.startActivity(i);
            }
        });

        return view;
    }

    public void onlyAddAll(List<T> datas) {
        mDataList.addAll(datas);
        notifyDataSetChanged();
    }

    public void clearAndAddAll(List<T> datas) {
        mDataList.clear();
        onlyAddAll(datas);
    }

    @Override
    public void onItemClick(GrandFlowTagLayout parent, View view, int position) {



    }
}
