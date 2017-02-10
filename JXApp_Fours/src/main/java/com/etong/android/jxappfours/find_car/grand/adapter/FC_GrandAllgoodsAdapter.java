package com.etong.android.jxappfours.find_car.grand.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.etong.android.frame.common.ImageProvider;
import com.etong.android.frame.widget.pinnedlistview.PinnedSectionListView;
import com.etong.android.jxappfours.R;
import com.etong.android.jxappfours.find_car.grand.bean.FC_AllSalesListItem;
import com.etong.android.jxappfours.find_car.grand.bean.FC_AllinsalesTotalItem;
import com.etong.android.jxappfours.find_car.grand.bean.FC_InsalesTotalItem;
import com.etong.android.jxappfours.find_car.grand.bean.Find_car_CarConfigListItem;
import com.etong.android.jxappfours.find_car.grand.bean.FC_InsalesListItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ellison.Sun on 2016/8/12.
 *
 * Menu中所有车型的适配器
 */
public class FC_GrandAllgoodsAdapter extends ArrayAdapter<FC_InsalesListItem> implements PinnedSectionListView.PinnedSectionListAdapter {

    private Context mContext;

    private List<FC_InsalesTotalItem> mListDatas;            // 传递过来的数据

    public FC_GrandAllgoodsAdapter(Context context) {
        super(context, android.R.layout.simple_list_item_1);
        this.mContext = context;
    }
    public void updateListView(List<FC_InsalesTotalItem> listDatas) {
        this.mListDatas = listDatas;
        generateDataset(true);
        notifyDataSetChanged();
    }

    /**
     * 生成数据的方法
     *
     * @param clear
     */
    public void generateDataset(boolean clear) {

        if (clear) clear();
        final int sectionsNumber = 'Z' - 'A' + 1;
        prepareSections(sectionsNumber);

        int sectionPosition = 0, listPosition = 0;
        List<String> sectionList = new ArrayList<String>();
        for (int i = 0; i < mListDatas.size(); i++) {

            // 判断mListData数据中是否添加该头部
            FC_InsalesTotalItem totalItem = mListDatas.get(i);
            List<FC_InsalesListItem> carsetList = totalItem.getCarsetList();

            FC_InsalesListItem listTitleItem = new FC_InsalesListItem(FC_InsalesListItem.SECTION, totalItem.getTitle());
            listTitleItem.sectionPosition = sectionPosition;
            listTitleItem.listPosition = listPosition++;
            onSectionAdded(listTitleItem, sectionPosition);
            add(listTitleItem);

            for (int k = 0; k < carsetList.size(); k++) {
                FC_InsalesListItem tempItem = carsetList.get(k);
                // 获取到每一个item，得到它的字母
                tempItem.type = FC_InsalesListItem.ITEM;
                tempItem.sectionPosition = sectionPosition;
                tempItem.listPosition = listPosition++;
                add(tempItem);
            }
            sectionPosition++;
        }
    }

    protected void prepareSections(int sectionsNumber) { }
    protected void onSectionAdded(FC_InsalesListItem section, int sectionPosition) { }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // 悬浮头的布局
        FC_InsalesListItem item = getItem(position);
        View view = null;
        if (item.type == FC_InsalesListItem.SECTION) {
            view = LayoutInflater.from(mContext).inflate(R.layout.fc_lv_item_title_insales, null);
            TextView tTitle = (TextView) view.findViewById(R.id.fc_insales_item_title_name);
            tTitle.setText(item.charTv);
        } else if (item.type == FC_InsalesListItem.ITEM) {
            view = LayoutInflater.from(mContext).inflate(R.layout.fc_lv_item_insales, null);
            ImageView iv = (ImageView) view.findViewById(R.id.grand_insales_img_pic);
            TextView rTText = (TextView) view.findViewById(R.id.grand_insales_item_rTText);
            TextView rBText = (TextView) view.findViewById(R.id.grand_insales_item_rBText);

            ImageProvider.getInstance().loadImage(iv, item.getImage(),R.mipmap.fours_grand_loading);
            rTText.setText(item.getFullName());
            rBText.setText(item.getMinguide() +" - " +item.getMaxguide() +"万");
        }
        view.setTag("" + position);
        return view;
    }
    @Override
    public int getViewTypeCount() {
        return 2;
    }
    @Override
    public int getItemViewType(int position) {
        return getItem(position).type;
    }
    @Override
    public boolean isItemViewTypePinned(int viewType) {
        return viewType == FC_InsalesListItem.SECTION;
    }
}
