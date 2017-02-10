package com.etong.android.jxappfours.find_car.filtrate.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.etong.android.frame.publisher.HttpPublisher;
import com.etong.android.frame.utils.L;
import com.etong.android.frame.widget.EtongNoScrollGridView;
import com.etong.android.jxappfours.R;
import com.etong.android.jxappfours.find_car.filtrate.Find_Car_FiltrateFragment;
import com.etong.android.jxappfours.find_car.filtrate.javabeam.Find_Car_OtherItemBeam;
import com.etong.android.jxappfours.order.Fours_Order_Provider;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 找车的筛选主界面的级别一下的ListView适配器
 * Created by Administrator on 2016/8/9 0009.
 */
public class Find_Car_OtherListAdapter extends BaseAdapter implements Find_Car_ItemGridAdapter.ICallBack {

    private final Fours_Order_Provider mFours_Order_Provider;
    private Context context;
    private List<Find_Car_OtherItemBeam> itemList;
    private Find_Car_ItemGridAdapter itemGridAdapter;
    private Map<Integer, Find_Car_OtherItemBeam.ItemNamesBean> selectMap = new HashMap<Integer, Find_Car_OtherItemBeam.ItemNamesBean>();
//private Map<Integer, String> selectMap = new HashMap<Integer, String>();
    private String country;
    private String category;
    private String summary;
    private String outVolStar;
    private String outVolEnd;


    public Find_Car_OtherListAdapter(Context context, List<Find_Car_OtherItemBeam> itemList) {
        this.context = context;
        this.itemList = itemList;

        mFours_Order_Provider = Fours_Order_Provider.getInstance();
        mFours_Order_Provider.initialize(HttpPublisher.getInstance());

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

    //清空保存选中的items的Map
    public void setCleanSeclect() {
        selectMap.clear();
        itemGridAdapter.notifyDataSetChanged();
    }

    //得到保存选中的items的Map
    public Map<Integer, Find_Car_OtherItemBeam.ItemNamesBean> getSelectItemMap() {
        return selectMap;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {

        convertView = LayoutInflater.from(context).inflate(R.layout.find_car_filtrate_othergv_lv, null);

        TextView tvTitleName = (TextView) convertView.findViewById(R.id.find_car_txt_item_title);
        EtongNoScrollGridView mGridView = (EtongNoScrollGridView) convertView.findViewById(R.id.find_car_gv_item_content);

        itemGridAdapter = new Find_Car_ItemGridAdapter(mGridView, position, context, Find_Car_OtherListAdapter.this, itemList.get(position).getItemNames());

        tvTitleName.setText(itemList.get(position).getTitleName());

        mGridView.setAdapter(itemGridAdapter);
        itemGridAdapter.notifyDataSetChanged();


        return convertView;
    }


    //ListView里面的GridView的回调接口，得到选中的item
    @Override
    public void answer(boolean isSelect, int selectPostion, Find_Car_OtherItemBeam.ItemNamesBean itemBean) {
        L.d("------------------->>>>" + selectPostion, itemBean + " " + isSelect);
        if (isSelect) {
            selectMap.put(selectPostion, itemBean);
        } else {
            selectMap.remove(selectPostion);
        }

        if (selectPostion == 0) {
            if (isSelect) {
                country=itemBean.getId()+"";
            }else{
                country=null;
            }
        }else if(selectPostion==1){
            if (isSelect) {
                category=itemBean.getId()+"";
            }else{
                category=null;
            }
        }else if(selectPostion==2){
            if (isSelect) {
                summary=itemBean.getTitle();
            }else{
                summary=null;
            }
        }else if(selectPostion==3){
            if (isSelect) {
                outVolStar=itemBean.getOutVolStar();
                outVolEnd=itemBean.getOutVolEnd();
            }else{
                outVolStar=null;
                outVolEnd=null;
            }
        }

        L.d("------------------->>>>" , category+"");
        mFours_Order_Provider.coutSearchCarsetCount(Find_Car_FiltrateFragment.priceStart,Find_Car_FiltrateFragment.priceEnd, Find_Car_FiltrateFragment.selectLevel,country,category,summary,outVolStar,outVolEnd);
        Find_Car_FiltrateFragment.canClickOther=false;
    }
}
