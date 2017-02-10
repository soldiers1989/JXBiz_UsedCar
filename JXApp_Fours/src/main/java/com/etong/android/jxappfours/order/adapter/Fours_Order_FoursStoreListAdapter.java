package com.etong.android.jxappfours.order.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.etong.android.jxappfours.R;
import com.etong.android.jxappfours.order.javabeam.Fours_Order_FoursStoreBeam;

import java.util.List;

/**
 * Created by Administrator on 2016/8/18 0018.
 */
public class Fours_Order_FoursStoreListAdapter extends BaseAdapter {
    private Context context;
    private List<Fours_Order_FoursStoreBeam> itemList;
    private ListView mListView;
    private LCallBack mLCallBack;
    private TextView tvStoreTitle;
//    private TextView tvStoreAddress;
    private boolean[] isChecked;
    private int clickTemp = -1;

    public Fours_Order_FoursStoreListAdapter(Context context, LCallBack mLCallBack, List<Fours_Order_FoursStoreBeam> itemList) {
        this.context = context;
        this.itemList = itemList;
        this.mLCallBack = mLCallBack;

        isChecked = new boolean[itemList.size()];
        for (int i = 0; i < itemList.size(); i++) {
            isChecked[i] = false;
        }
    }

    //标识选择的Item
    public void setSeclection(int position) {
        clickTemp = position;
    }

    //重置每个item的选中状态为false
    public void setCleanSelect(){
        isChecked = new boolean[itemList.size()];
        for (int i = 0; i < itemList.size(); i++) {
            isChecked[i] = false;
        }
    }

    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public Object getItem(int position) {
        return itemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {

        convertView = LayoutInflater.from(context).inflate(R.layout.fours_order_fours_lv, null);

        tvStoreTitle = (TextView) convertView.findViewById(R.id.fours_order_txt_td_order_fours_name);
//        tvStoreAddress = (TextView) convertView.findViewById(R.id.fours_order_txt_td_order_fours_address);

        tvStoreTitle.setText("4S-"+itemList.get(position).getDept_name());
//        tvStoreAddress.setText(itemList.get(position).getStoreAddress());

        // 点击改变选中的item的图片和文字颜色
        if (clickTemp == position) {
            if (!isChecked[position]) {
                //设置选中状态
                tvStoreTitle.setSelected(true);
                isChecked[position] = true;
            } else {
                tvStoreTitle.setSelected(false);
                setCleanSelect();
            }
            mLCallBack.answerStore(isChecked[position],itemList.get(position).getDept_id()+"");
        }
        return convertView;
    }

    public void updateDataChanged(List<Fours_Order_FoursStoreBeam> data) {
        this.itemList = data;
        setCleanSelect();
        this.notifyDataSetChanged();
    }


    //回调接口
    public interface LCallBack {
        public void answerStore(boolean isSelect,String id);  //类B的内部接口
    }
}
