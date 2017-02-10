package com.etong.android.jxbizusedcar.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.etong.android.frame.common.ImageProvider;
import com.etong.android.jxappusedcar.bean.UC_BrandBean;
import com.etong.android.jxappusedcar.bean.UC_PriceBean;
import com.etong.android.jxappusedcar.content.UC_BuyCarFragment;
import com.etong.android.jxbizusedcar.R;
import com.etong.android.jxbizusedcar.bean.UC_PriceAndBrandBean;

import org.simple.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @desc (品牌价格适配器)
 * @createtime 2016/11/3 0003--16:44
 * @Created by wukefan.
 */
public class UC_PriceAndBrandAdapter extends BaseAdapter {

    private List<UC_PriceAndBrandBean> mData;
    private Context mContext;

    public UC_PriceAndBrandAdapter(Context context) {
        this.mContext = context;
        mData = new ArrayList<UC_PriceAndBrandBean>();
    }

    /**
     * @param
     * @return
     * @desc (对外公布的更新ListView数据的方法)
     */
    public void updateCarList(List<UC_PriceAndBrandBean> data) {
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

        convertView = LayoutInflater.from(mContext).inflate(R.layout.uc_item_gv_price_brand, null);

        ImageView mLogo = (ImageView) convertView.findViewById(R.id.uc_price_brand_iv_logo);
        ImageView mRightAllImg = (ImageView) convertView.findViewById(R.id.uc_price_brand_iv_btn);
        TextView mTitle = (TextView) convertView.findViewById(R.id.uc_price_brand_txt_title);
        View mDivider = (View) convertView.findViewById(R.id.uc_price_brand_divider);

        mTitle.setTextColor(mContext.getResources().getColor(R.color.used_car_2a2a2a));
        mRightAllImg.setVisibility(View.GONE);
        mDivider.setVisibility(View.VISIBLE);

        if ((position + 1) % 4 == 0) {
            mDivider.setVisibility(View.GONE);
        }

        if (position == getCount() - 1) {//全部
            mRightAllImg.setVisibility(View.VISIBLE);
            mLogo.setVisibility(View.GONE);
            mTitle.setTextColor(mContext.getResources().getColor(R.color.used_car_cf1c36));
            mTitle.setText(mData.get(position).getAll());
        } else if (mData.get(position).isIsBrand()) {//品牌
            mLogo.setVisibility(View.VISIBLE);
            mTitle.setText(mData.get(position).getF_brand());
            ImageProvider.getInstance().loadImage(mLogo, mData.get(position).getImage());
        } else {//价格
            mLogo.setVisibility(View.GONE);
            mTitle.setText(mData.get(position).getF_price());
        }

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (position == getCount() - 1) {//全部
                    EventBus.getDefault().post(1, "switch page");
                    EventBus.getDefault().post("", "list data is null");
                } else if (mData.get(position).isIsBrand()) {//品牌
                    Map<String, String> brandMap = new HashMap<String, String>();
                    Map<String, String> carsetMap = new HashMap<String, String>();
                    brandMap.put(mData.get(position).getF_brand(), mData.get(position).getF_carbrandid());

                    UC_BrandBean brandBean = new UC_BrandBean(brandMap, carsetMap);
                    EventBus.getDefault().post(brandBean, UC_BuyCarFragment.GET_DATABEAN_FROME_HOMEPAGE);
                } else {//价格
                    Map<String, List<String>> map = new HashMap();
                    List<String> priceList = new ArrayList<String>();
                    priceList.add(null);
                    priceList.add(null);
                    priceList.add(mData.get(position).getF_priceid());
                    map.put(mData.get(position).getF_price(), priceList);
                    UC_PriceBean mUC_PriceBean = new UC_PriceBean(map);
                    EventBus.getDefault().post(mUC_PriceBean, UC_BuyCarFragment.GET_DATABEAN_FROME_HOMEPAGE);
                }
            }
        });
        return convertView;
    }
}
