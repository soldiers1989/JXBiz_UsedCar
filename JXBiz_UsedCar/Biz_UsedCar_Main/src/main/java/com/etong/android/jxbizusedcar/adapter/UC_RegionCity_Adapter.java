package com.etong.android.jxbizusedcar.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.etong.android.frame.common.ImageProvider;
import com.etong.android.frame.widget.pinnedlistview.PinnedSectionListView;
import com.etong.android.jxbizusedcar.R;
import com.etong.android.jxbizusedcar.bean.UC_RegionSel_ItemBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ellison.Sun on 2016/8/12.
 *
 * 车辆配置的适配器
 */
public class UC_RegionCity_Adapter extends ArrayAdapter<UC_RegionSel_ItemBean> implements PinnedSectionListView.PinnedSectionListAdapter {

    private Context mContext;
    private List<UC_RegionSel_ItemBean> mListDatas;

    public UC_RegionCity_Adapter(Context context) {
        super(context, android.R.layout.simple_list_item_1);
        this.mContext = context;
        this.mListDatas = new ArrayList<UC_RegionSel_ItemBean>();
        generateDataset();
    }

    public void updateDatas(List<UC_RegionSel_ItemBean> listDatas) {
        this.mListDatas = listDatas;
        notifyDataSetChanged();
        generateDataset();
    }

    /**
     * 初始化数据
     */
    public void generateDataset() {

        clear();

        final int sectionsNumber = 'Z'-'A' + 1;
        prepareSections(sectionsNumber);

        int sectionPosition = 0, listPosition = 0;
        for (int i=0; i<sectionsNumber; i++) {

            // 判断mListData数据中是否包含有该字母
            for (int a=0; a<mListDatas.size(); a++) {
                UC_RegionSel_ItemBean temBean = mListDatas.get(a);
                if (String.valueOf((char)('A' + i)).equals(temBean.getLetter())) {
                    UC_RegionSel_ItemBean sectionBean =
                            new UC_RegionSel_ItemBean(UC_RegionSel_ItemBean.SECTION, String.valueOf((char) ('A' + i)));
                    sectionBean.sectionPosition = sectionPosition;
                    sectionBean.listPosition = listPosition++;
                    onSectionAdded(sectionBean, sectionPosition);
                    add(sectionBean);
                    break;
                }
            }
            // 添加item列表
            for (int j=0; j<mListDatas.size(); j++) {
                // 获取到所有的Item
                UC_RegionSel_ItemBean itemBean = mListDatas.get(j);
                if((String.valueOf((char)('A' + i)).equals(itemBean.getLetter()))) {
                    // 获取到每一个item，得到它的字母
                    itemBean.type = UC_RegionSel_ItemBean.ITEM;
                    itemBean.sectionPosition = sectionPosition;
                    itemBean.listPosition = listPosition++;
                    add(itemBean);
                }
            }
            sectionPosition++;
        }
    }

    protected void prepareSections(int sectionsNumber) { }
    protected void onSectionAdded(UC_RegionSel_ItemBean section, int sectionPosition) { }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // 悬浮头的布局
        UC_RegionSel_ItemBean item = getItem(position);
        View view = null;
        if (item.type == UC_RegionSel_ItemBean.SECTION) {
            view = LayoutInflater.from(mContext).inflate(R.layout.uc_lv_item_letter_select_city, null);
            TextView tTitle = (TextView) view.findViewById(R.id.uc_txt_item_letter);
            tTitle.setText(item.charTv);
        } else if (item.type == UC_RegionSel_ItemBean.ITEM) {
            view = LayoutInflater.from(mContext).inflate(R.layout.uc_lv_item_city_select_city, null);
            TextView tvRight = (TextView) view.findViewById(R.id.uc_txt_item_city);

            tvRight.setText(item.getTitle());
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
        return viewType == UC_RegionSel_ItemBean.SECTION;
    }

    public int getCharForPosition(String c) {
        if (c.matches("[A-Z]")) {
            for (int i = 0; i < getCount(); i++) {
                if (getItem(i).type == UC_RegionSel_ItemBean.SECTION) {

                    if (c.equals(getItem(i).charTv)) {
                        return getItem(i).listPosition;
                    }
                }
            }
        }
        return -1;
    }

    /**
     * 根据ListView的当前位置获取分类的首字母的Char ascii值
     */
    public int getSectionForPosition(int position) {
        return mListDatas.get(position).getLetter().charAt(0);
    }

    /**
     * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
     */
    public int getPositionForSection(int section) {
        for (int i = 0; i < getCount(); i++) {
            String sortStr = mListDatas.get(i).getLetter();
            char firstChar = sortStr.toUpperCase().charAt(0);
            if (firstChar == section) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 提取英文的首字母，非英文字母用#代替。
     *
     * @param str
     * @return
     */
    public String getAlpha(String str) {
        String  sortStr = str.trim().substring(0, 1).toUpperCase();
        // 正则表达式，判断首字母是否是英文字母
        if (sortStr.matches("[A-Z]")) {
            return sortStr;
        } else {
            return "#";
        }
    }
}