package com.etong.android.jxappusedcar.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import com.etong.android.jxappusedcar.R;
import com.etong.android.jxappusedcar.bean.UC_Sort;
import java.util.ArrayList;
import java.util.List;

/**
 * @desc 买车排序适配器
 * @createtime 2016/11/7 - 12:53
 * @Created by xiaoxue.
 */

public class UC_OrderAdapter extends BaseAdapter {
    private Context mContext;
    private List<UC_Sort> sortList;
    private int mOrderChoicePos=0;
    private UC_OrderCallBack iCallBack;

    public UC_OrderAdapter(Context mContext,UC_OrderCallBack orderCallBack) {
        this.mContext = mContext;
        sortList = new ArrayList<>();
        getSortData();
        iCallBack=orderCallBack;
    }

    public void update(int position){
        mOrderChoicePos=position;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return sortList.size();
    }

    @Override
    public Object getItem(int position) {
        return sortList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(mContext).inflate(R.layout.used_car_item_buy_car_popwindow, parent, false);
        final ImageView sureDraw = (ImageView) convertView.findViewById(R.id.uc_iv_sure_draw);      //√图片
        RadioButton choice = (RadioButton) convertView.findViewById(R.id.uc_tv_list_item_pop);  //radiobutton
        choice.setText(sortList.get(position).getTitle());
        initOrderState(sureDraw, choice, position);
        choice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOrderChoicePos = position;//记住选中的位置
                sureDraw.setVisibility(View.VISIBLE);    //类似价格选中
                iCallBack.OrderIntent(mOrderChoicePos,sortList.get(position).getSortType()+"");
            }
        });
        return convertView;
    }

    /**
     * 排序按钮各个选项的初始化
     */
    private void initOrderState(ImageView view, RadioButton choice, int position) {
        view.setVisibility(View.GONE);
        choice.setChecked(false);
        if (position == mOrderChoicePos) {
            view.setVisibility(View.VISIBLE);
            choice.setChecked(true);
        }
    }

    //点击列表item 回调接口
    public interface UC_OrderCallBack {
        public void OrderIntent(int position,String str);
    }

    /**
     * @desc 排序的数据
     * @createtime 2016/11/4 - 14:11
     * @author xiaoxue
     */
    public void getSortData() {
        UC_Sort mUC_Sort = new UC_Sort();
        mUC_Sort.setTitle("智能推荐");
        mUC_Sort.setSortType(5);
        sortList.add(mUC_Sort);
        UC_Sort mUC_Sort1 = new UC_Sort();
        mUC_Sort1.setTitle("最新上架");
        mUC_Sort1.setSortType(0);
        sortList.add(mUC_Sort1);
        UC_Sort mUC_Sort2 = new UC_Sort();
        mUC_Sort2.setTitle("价格最低");
        mUC_Sort2.setSortType(1);
        sortList.add(mUC_Sort2);
        UC_Sort mUC_Sort3 = new UC_Sort();
        mUC_Sort3.setTitle("价格最高");
        mUC_Sort3.setSortType(2);
        sortList.add(mUC_Sort3);
        UC_Sort mUC_Sort4 = new UC_Sort();
        mUC_Sort4.setTitle("车龄最短");
        mUC_Sort4.setSortType(3);
        sortList.add(mUC_Sort4);
        UC_Sort mUC_Sort5 = new UC_Sort();
        mUC_Sort5.setTitle("里程最少");
        mUC_Sort5.setSortType(4);
        sortList.add(mUC_Sort5);
    }
}

