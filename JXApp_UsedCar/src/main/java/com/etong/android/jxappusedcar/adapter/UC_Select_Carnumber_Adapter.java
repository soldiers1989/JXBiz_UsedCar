package com.etong.android.jxappusedcar.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.TextView;

import com.etong.android.frame.user.FrameUserInfo;
import com.etong.android.jxappusedcar.R;
import com.etong.android.jxappusedcar.content.UC_Submit_SalesCar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author : by sunyao
 * @ClassName : UC_Select_Carnumber_Adapter
 * @Description : (爱车列表适配器)
 * @date : 2016/10/6 - 15:52
 */

public class UC_Select_Carnumber_Adapter extends BaseAdapter {

    private Context mContext;
    private List<FrameUserInfo.Frame_MyCarItemBean> mData;     // 传送过来的数据
    LayoutInflater mInflater;
    // 用于记录每个RadioButton的状态，并保证只可选一个
    HashMap<String, Boolean> states = new HashMap<String, Boolean>();

    /**
     * 构造方法
     *
     * @param context
     */
    public UC_Select_Carnumber_Adapter(Context context) {
        this.mContext = context;
        mData = new ArrayList<FrameUserInfo.Frame_MyCarItemBean>();
        mInflater = LayoutInflater.from(context);
    }

    /**
     * @desc (对外公布的更新ListView数据的方法)
     * @user sunyao
     * @createtime 2016/10/6 - 15:57
     * @param
     * @return
     */
    public void updateCarNumber(List<FrameUserInfo.Frame_MyCarItemBean> data) {
        this.mData = data;
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
        final UC_Select_Carnumber_Adapter.ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.used_car_select_car_number_item, null);
            holder = new UC_Select_Carnumber_Adapter.ViewHolder();
            holder.used_car_car_number = (TextView) convertView.findViewById(R.id.used_car_select_carnumber_item_text);
            holder.used_car_car_content = (ViewGroup) convertView.findViewById(R.id.used_car_select_carnumber_item_content);
            convertView.setTag(holder);
        } else {
            holder = (UC_Select_Carnumber_Adapter.ViewHolder) convertView.getTag();
        }

        holder.used_car_car_number.setText(mData.get(position).getPlate_no());

        final RadioButton radio=(RadioButton) convertView.findViewById(R.id.used_car_select_carnumber_item_rb);
        holder.used_car_car_rb = radio;
        holder.used_car_car_content.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                radio.setChecked(true);
                // 重置，确保最多只有一项被选中
                for (String key : states.keySet()) {
                    states.put(key, false);
                }
                states.put(String.valueOf(position), radio.isChecked());
                UC_Select_Carnumber_Adapter.this.notifyDataSetChanged();

                // 设置Item的点击事件
                // 为ListView的Item设置点击事件
//                Toast.makeText(mContext, mData.get(position).getPlate_no()+"", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(mContext, UC_Submit_SalesCar.class);
                i.putExtra("isSelectMyCar",true);
                i.putExtra("MyCar",mData.get(position));
                mContext.startActivity(i);
            }
        });

        boolean res = false;
        if (states.get(String.valueOf(position)) == null
                || states.get(String.valueOf(position)) == false) {
            res = false;
            states.put(String.valueOf(position), false);
        } else {
            res = true;
        }
        holder.used_car_car_rb.setChecked(res);


        return convertView;
    }

    static class ViewHolder {
        ViewGroup used_car_car_content;
        TextView used_car_car_number;
        RadioButton used_car_car_rb;
    }
}
