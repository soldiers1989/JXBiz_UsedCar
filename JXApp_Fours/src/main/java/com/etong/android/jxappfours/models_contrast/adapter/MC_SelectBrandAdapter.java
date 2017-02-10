package com.etong.android.jxappfours.models_contrast.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.etong.android.frame.utils.L;
import com.etong.android.frame.widget.pinnedlistview.PinnedSectionListView;
import com.etong.android.jxappfours.R;
import com.etong.android.jxappfours.models_contrast.javabean.Models_Contrast_SelectBrand;

import java.util.List;

/**
 * Created by Ellison.Sun on 2016/8/12.
 *
 * 车辆对比界面中选择车型侧滑界面
 */
public class MC_SelectBrandAdapter extends ArrayAdapter<Models_Contrast_SelectBrand> implements PinnedSectionListView.PinnedSectionListAdapter {

    private Context mContext;
    private List<Models_Contrast_SelectBrand> mListDatas;

    public MC_SelectBrandAdapter(Context context, List<Models_Contrast_SelectBrand> listDatas) {
        super(context, android.R.layout.simple_list_item_1);
        this.mContext = context;
        this.mListDatas = listDatas;
        generateDataset(true);
        L.d("获取到所有的品牌为：", mListDatas.toString());
    }

    public void updateListView(List<Models_Contrast_SelectBrand> listDatas) {
        this.mListDatas = listDatas;
        notifyDataSetChanged();
    }


    /**
     * 初始化数据
     * @param clear
     */
    public void generateDataset(boolean clear) {

        if (clear) clear();

        final int sectionsNumber = 'Z'-'A' + 1;
        prepareSections(sectionsNumber);

        int sectionPosition = 0, listPosition = 0;
        for (int i=0; i<sectionsNumber; i++) {

            // 判断mListData数据中是否包含有该字母
            for (int a=0; a<mListDatas.size(); a++) {
                Models_Contrast_SelectBrand temBean = mListDatas.get(a);
                if (String.valueOf((char)('A' + i)).equals(temBean.getLetter())) {
                    Models_Contrast_SelectBrand sectionBean =
                            new Models_Contrast_SelectBrand(Models_Contrast_SelectBrand.SECTION, String.valueOf((char) ('A' + i)));
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
                Models_Contrast_SelectBrand itemBean = mListDatas.get(j);
                if((String.valueOf((char)('A' + i)).equals(itemBean.getLetter()))) {
                    // 获取到每一个item，得到它的字母
                    itemBean.type = Models_Contrast_SelectBrand.ITEM;
                    itemBean.sectionPosition = sectionPosition;
                    itemBean.listPosition = listPosition++;
                    add(itemBean);
                }
            }
            sectionPosition++;
        }
    }

    protected void prepareSections(int sectionsNumber) { }
    protected void onSectionAdded(Models_Contrast_SelectBrand section, int sectionPosition) { }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // 悬浮头的布局
        Models_Contrast_SelectBrand item = getItem(position);
        View view = null;
        if (item.type == Models_Contrast_SelectBrand.SECTION) {
            view = LayoutInflater.from(mContext).inflate(R.layout.fc_select_brand_list_title_section, null);
            TextView tTitle = (TextView) view.findViewById(R.id.fc_select_brand_list_title_char);
            tTitle.setText(item.charTv);
        } else if (item.type == Models_Contrast_SelectBrand.ITEM) {
            view = LayoutInflater.from(mContext).inflate(R.layout.fc_select_brand_list_item, null);
            TextView tvRight = (TextView) view.findViewById(R.id.fc_grand_item_detail_tv);

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
        return viewType == Models_Contrast_SelectBrand.SECTION;
    }

    public int getCharForPosition(String c) {
        if (c.matches("[A-Z]")) {
            for (int i = 0; i < getCount(); i++) {
                if (getItem(i).type == Models_Contrast_SelectBrand.SECTION) {

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
