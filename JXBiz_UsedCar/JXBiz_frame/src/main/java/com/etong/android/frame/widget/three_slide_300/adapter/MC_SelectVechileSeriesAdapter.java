package com.etong.android.frame.widget.three_slide_300.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.etong.android.frame.R;
import com.etong.android.frame.widget.pinnedlistview.PinnedSectionListView;
import com.etong.android.frame.widget.three_slide_300.javabean.Models_Contrast_VechileSeries;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ellison.Sun on 2016/8/12.
 * <p/>
 * 车辆对比界面中选择车型侧滑界面
 */
public class MC_SelectVechileSeriesAdapter extends ArrayAdapter<Models_Contrast_VechileSeries> implements PinnedSectionListView.PinnedSectionListAdapter {

    private Context mContext;
    private List<Models_Contrast_VechileSeries> mListDatas;

    public MC_SelectVechileSeriesAdapter(Context context) {
        super(context, android.R.layout.simple_list_item_1);
        this.mContext = context;
//        this.mListDatas = listDatas;
//        L.d("获取到所有的品牌为：", mListDatas.toString());
    }

    public void updateListView(List<Models_Contrast_VechileSeries> listDatas) {
        this.mListDatas = listDatas;
        generateDataset(true);
        notifyDataSetChanged();
    }


    /**
     * 初始化数据
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
            Models_Contrast_VechileSeries temBean = mListDatas.get(i);
            if (!sectionList.contains(temBean.getSeries_group_name())) {       // 判断是否有添加过FullName标题名字
                sectionList.add(temBean.getSeries_group_name());
                Models_Contrast_VechileSeries sectionBean =
                        new Models_Contrast_VechileSeries(Models_Contrast_VechileSeries.SECTION, temBean.getSeries_group_name());
                sectionBean.sectionPosition = sectionPosition;
                sectionBean.listPosition = listPosition++;
                onSectionAdded(sectionBean, sectionPosition);
                add(sectionBean);
            }
//            // 获取到所有的Item 添加item列表
//            Models_Contrast_VechileSeries itemBean = mListDatas.get(j);

            // 获取到每一个item，得到它的字母
            temBean.type = Models_Contrast_VechileSeries.ITEM;
            temBean.sectionPosition = sectionPosition;
            temBean.listPosition = listPosition++;
            add(temBean);
        }
        sectionPosition++;
    }

    protected void prepareSections(int sectionsNumber) {
    }

    protected void onSectionAdded(Models_Contrast_VechileSeries section, int sectionPosition) {
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // 悬浮头的布局
        Models_Contrast_VechileSeries item = getItem(position);
        View view = null;
        if (item.type == Models_Contrast_VechileSeries.SECTION) {
            view = LayoutInflater.from(mContext).inflate(R.layout.fc_select_car_series_list_title_section, null);
            TextView tTitle = (TextView) view.findViewById(R.id.models_contrast_txt_title);
            tTitle.setText(item.charTv);
        } else if (item.type == Models_Contrast_VechileSeries.ITEM) {
            view = LayoutInflater.from(mContext).inflate(R.layout.fc_select_car_series_list_item, null);
            TextView tvRight = (TextView) view.findViewById(R.id.models_contrast_txt_car_name);

            tvRight.setText(item.getSeries_name());
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
        return viewType == Models_Contrast_VechileSeries.SECTION;
    }

    public int getCharForPosition(String c) {
        if (c.matches("[A-Z]")) {
            for (int i = 0; i < getCount(); i++) {
                if (getItem(i).type == Models_Contrast_VechileSeries.SECTION) {

                    if (c.equals(getItem(i).charTv)) {
                        return getItem(i).listPosition;
                    }
                }
            }
        }
        return -1;
    }
}
