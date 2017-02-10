package com.etong.android.jxappusedcar.adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.etong.android.jxappusedcar.R;
import com.etong.android.jxappusedcar.bean.UC_BrandBean;
import com.etong.android.jxappusedcar.bean.UC_FilterBean;
import com.etong.android.jxappusedcar.bean.UC_PriceBean;
import com.etong.android.jxappusedcar.bean.UC_TagListBean;
import com.etong.android.jxappusedcar.content.UC_BuyCarFragment;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
/**
 * @desc 买车标签的适配器
 * @createtime 2016/10/25 - 11:24
 * @Created by xiaoxue.
 */
public class UC_BuyCarTagAdapter extends BaseAdapter{
    private Context mContext;
    private List<UC_TagListBean> mListDatas;
    private UC_tagCallBack iCallBack;
    private UC_CarsetCallBack carsetCallBack;

    public UC_BuyCarTagAdapter(Context mContext,UC_tagCallBack iCallBack,UC_CarsetCallBack carsetCallBack){
        this.mContext=mContext;
        this.iCallBack=iCallBack;
        this.carsetCallBack=carsetCallBack;
        this.mListDatas= new ArrayList<UC_TagListBean>();
    }
    //处理数据的方法
    public void upListData(List<Object> mList){
        mListDatas.clear();
        //品牌 车系
        UC_BrandBean brandBean = (UC_BrandBean) mList.get(0);
        Map<String,String> brandMap = brandBean.getBrand();
        Map<String,String> carsetMap = brandBean.getCarset();
        if(null!=brandMap&&brandMap.size()!=0){//品牌
            for (String key : brandMap.keySet()) {
                UC_TagListBean brandTag = new UC_TagListBean(key, UC_BuyCarFragment.TAG_BRAND,true);
                mListDatas.add(brandTag);
            }
        }
        if(null!=carsetMap&&carsetMap.size()!=0){//车系
            for (String key : carsetMap.keySet()) {
                UC_TagListBean carsetTag = new UC_TagListBean(key,UC_BuyCarFragment.TAG_CARSET,false);
                mListDatas.add(carsetTag);
                for(int i=0;i<mListDatas.size();i++){
                    if(mListDatas.get(i).getTag().equals(UC_BuyCarFragment.TAG_BRAND)){
                        UC_TagListBean brandTag = mListDatas.get(i);
                        brandTag.setBrand(false);
                        mListDatas.set(i,brandTag);
                    }
                }
            }
        }
        //价格
        UC_PriceBean priceBean = (UC_PriceBean)mList.get(1);
        Map<String,List<String>> priceMap = priceBean.getPrice();
        if(null!=priceMap&&priceMap.size()!=0) {
            for (String key : priceMap.keySet()) {
                UC_TagListBean priceTag = new UC_TagListBean(key,UC_BuyCarFragment.TAG_PRICE, false);
                mListDatas.add(priceTag);
            }
        }
        //筛选
        UC_FilterBean filterBean = (UC_FilterBean)mList.get(2);
        Map<String, String> vehicleTypeMap = filterBean.getVehicleType();
        Map<String, List<String>> carAgeMap = filterBean.getCarAge();
        Map<String, List<String>> mileAgeMap = filterBean.getMileAge();
        Map<String, String> gearBoxMap = filterBean.getGearBox();
        Map<String, String> countryMap = filterBean.getCountry();
        Map<String, String> isauthenticateMap = filterBean.getIsauthenticate();
        Map<String, String> colorMap = filterBean.getColor();
        if (null!=vehicleTypeMap&&vehicleTypeMap.size() != 0) {//车辆类型
            for (String key : vehicleTypeMap.keySet()) {
                UC_TagListBean vehicleTypeTag = new UC_TagListBean(key,UC_BuyCarFragment.TAG_VEHICLE_TYPE,false);
                mListDatas.add(vehicleTypeTag);
            }
        }
        if (null!=carAgeMap&&carAgeMap.size() != 0) {//车龄
            for (String key : carAgeMap.keySet()) {
                UC_TagListBean carAgeTag = new UC_TagListBean(key,UC_BuyCarFragment.TAG_CARAGE,false);
                mListDatas.add(carAgeTag);
            }
        }
        if (null!=mileAgeMap&&mileAgeMap.size() != 0) {//里程
            for (String key : mileAgeMap.keySet()) {
                UC_TagListBean mileAgTag = new UC_TagListBean(key,UC_BuyCarFragment.TAG_MILEAGE,false);
                mListDatas.add(mileAgTag);
            }
        }
        if (null!=gearBoxMap&&gearBoxMap.size() != 0) {//变速箱
            for (String key : gearBoxMap.keySet()) {
                UC_TagListBean gearBoxTag = new UC_TagListBean(key,UC_BuyCarFragment.TAG_GEARBOX,false);
                mListDatas.add(gearBoxTag);
            }
        }
        if (null!=countryMap&&countryMap.size() != 0) {//国别
            for (String key : countryMap.keySet()) {
                UC_TagListBean countryTag = new UC_TagListBean(key,UC_BuyCarFragment.TAG_COUNTRY,false);
                mListDatas.add(countryTag);
            }
        }
        if (null!=isauthenticateMap&&isauthenticateMap.size() != 0) {//是否认证
            for (String key : isauthenticateMap.keySet()) {
                UC_TagListBean isauthenticateTag = new UC_TagListBean(key,UC_BuyCarFragment.TAG_ISAUTHENTICATE,false);
                mListDatas.add(isauthenticateTag);
            }
        }
        if (null!=colorMap&&colorMap.size() != 0) {//颜色
            for (String key : colorMap.keySet()) {
                UC_TagListBean colorTag = new UC_TagListBean(key,UC_BuyCarFragment.TAG_COLOR,false);
                mListDatas.add(colorTag);
            }
        }
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return mListDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mListDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        UC_ViewHolder viewHolder = null;
        if (null == convertView) {
            viewHolder = new UC_ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.used_car_tag_item, parent, false);
            viewHolder.used_car_ll_tag_item=(LinearLayout)convertView.findViewById(R.id.used_car_ll_tag_item);
            viewHolder.used_car_tv_reset=(TextView)convertView.findViewById(R.id.used_car_tv_reset);
            viewHolder.used_car_iv_cancel=(ImageView)convertView.findViewById(R.id.used_car_iv_cancel);
            viewHolder.used_car_tv_select_cars=(TextView)convertView.findViewById(R.id.used_car_tv_select_cars);
            //显示车系的布局
            viewHolder.used_car_ll_tag_item_carset=(LinearLayout)convertView.findViewById(R.id.used_car_ll_tag_item_carset);
            viewHolder.used_car_tv_reset_carset=(TextView)convertView.findViewById(R.id.used_car_tv_reset_carset);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (UC_ViewHolder) convertView.getTag();
        }
        if(mListDatas.get(position).isBrand()){
            viewHolder.used_car_tv_select_cars.setVisibility(View.VISIBLE);
        }
        viewHolder.used_car_tv_reset.setText(mListDatas.get(position).getTagName());
        viewHolder.used_car_ll_tag_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iCallBack.answerTag(mListDatas.get(position).getTag());
            }
        });
        viewHolder.used_car_tv_select_cars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                carsetCallBack.answerIntent(v);
            }
        });
        return convertView;
    }

    //tag回调接口
    public interface UC_tagCallBack {
        public void answerTag(String tagName);  //类B的内部接口
    }

    //点击列表item 回调接口
    public interface UC_CarsetCallBack {
        public void answerIntent(View view);
    }

    private static class UC_ViewHolder {
        public LinearLayout used_car_ll_tag_item;
        public TextView used_car_tv_reset;
        public ImageView used_car_iv_cancel;
        public TextView used_car_tv_select_cars;
        public LinearLayout used_car_ll_tag_item_carset;
        public TextView used_car_tv_reset_carset;
    }
}
