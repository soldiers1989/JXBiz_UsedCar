package com.etong.android.jxappusedcar.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import com.etong.android.jxappusedcar.Interface.UC_OnPriceRangeListener;
import com.etong.android.jxappusedcar.bean.UC_FilterDataDictionaryBean;
import com.etong.android.jxappusedcar.bean.UC_PriceBean;
import com.etong.android.jxappusedcar.viewHolder.UC_PriceRangeViewHodler;
import com.etong.android.jxappusedcar.R;

import org.simple.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 买车价格适配器
 * Created by Dasheng on 2016/10/16.
 */

public class UC_PriceRangeRecycleViewAdapter extends RecyclerView.Adapter<UC_PriceRangeViewHodler> {

    private Context mContext;
    private List<UC_FilterDataDictionaryBean.MapBean> mDatas;
    //    private List<String> mDatas;
    private UC_OnPriceRangeListener mRangeListener;
    private int mPriceChoicePos = 0;
    private UC_PriceCallBack iCallBack;

    public UC_PriceRangeRecycleViewAdapter(Context context, List<UC_FilterDataDictionaryBean.MapBean> datas, UC_PriceCallBack priceCallBack) {
        mContext = context;
        mDatas = datas;
        iCallBack = priceCallBack;

    }
  /*  public void setOnPriceRangeListener(UC_OnPriceRangeListener listener){

        mRangeListener = listener;

    }*/

    public void upDate(int position) {
        mPriceChoicePos = position;
        notifyDataSetChanged();
    }

    @Override
    public UC_PriceRangeViewHodler onCreateViewHolder(ViewGroup parent, int viewType) {

        UC_PriceRangeViewHodler hodler = new UC_PriceRangeViewHodler(LayoutInflater.from(mContext).inflate(R.layout.used_car_item_price_range, parent, false));

        return hodler;
    }

    @Override
    public void onBindViewHolder(final UC_PriceRangeViewHodler holder, final int position) {
        holder.mPriceView.setText(mDatas.get(position).getValue());//设置请求到的价格（文字）
        initPriceState(holder.mPriceView, position);
        holder.mPriceView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPriceChoicePos = position; //记录位置
                Map<String, List<String>> map = new HashMap();
                if (!holder.mPriceView.getText().toString().equals("不限")) {
                    List<String> priceList = new ArrayList<String>();
                    priceList.add("");
                    priceList.add("");
                    priceList.add(mDatas.get(position).getKey());
                    map.put(holder.mPriceView.getText().toString(), priceList);
                }
                UC_PriceBean mUC_PriceBean = new UC_PriceBean(map);
                EventBus.getDefault().post(mUC_PriceBean, "get type price data");//传一个价格javabean给处理价格的方法
                iCallBack.PriceIntent(mPriceChoicePos, mDatas.get(position).getKey());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    /**
     * 价格按钮各个选项状态的初始化
     */
    private void initPriceState(RadioButton button, int position) {
        button.setChecked(false);
        if (position == mPriceChoicePos) {
            button.setChecked(true);
        }
    }

    //点击列表item 回调接口
    public interface UC_PriceCallBack {
        public void PriceIntent(int position, String key);
    }

}
