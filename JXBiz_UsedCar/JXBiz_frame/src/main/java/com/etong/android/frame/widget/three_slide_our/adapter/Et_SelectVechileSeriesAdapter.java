package com.etong.android.frame.widget.three_slide_our.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.etong.android.frame.R;
import com.etong.android.frame.widget.pinnedlistview.PinnedSectionListView;
import com.etong.android.frame.widget.three_slide_our.javabean.Et_VechileSeries;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ellison.Sun on 2016/8/12.
 * <p/>
 * 车辆对比界面中选择车型侧滑界面
 */
public class Et_SelectVechileSeriesAdapter extends ArrayAdapter<Et_VechileSeries> implements PinnedSectionListView.PinnedSectionListAdapter {

    private Context mContext;
    private List<Et_VechileSeries> mListDatas;

    public Et_SelectVechileSeriesAdapter(Context context) {
        super(context, android.R.layout.simple_list_item_1);
        this.mContext = context;
//        this.mListDatas = listDatas;
//        L.d("获取到所有的品牌为：", mListDatas.toString());
    }

    public void updateListView(List<Et_VechileSeries> listDatas) {
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
            Et_VechileSeries temBean = mListDatas.get(i);
            // 表示品牌的item列
            if (temBean.getId() == 0) {

                temBean.type = Et_VechileSeries.SECTION;
                temBean.sectionPosition = sectionPosition;
                temBean.listPosition = listPosition++;
                onSectionAdded(temBean, sectionPosition);
                add(temBean);
            } else {
                // 表示车系的item列
                temBean.type = Et_VechileSeries.ITEM;
                temBean.sectionPosition = sectionPosition;
                temBean.listPosition = listPosition++;
                add(temBean);
            }
            sectionPosition++;
        }
    }

    protected void prepareSections(int sectionsNumber) {
    }

    protected void onSectionAdded(Et_VechileSeries section, int sectionPosition) {
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // 悬浮头的布局
        Et_VechileSeries item = getItem(position);
        View view = null;
        if (item.type == Et_VechileSeries.SECTION) {
            view = LayoutInflater.from(mContext).inflate(R.layout.fc_select_car_series_list_title_section, null);
            TextView tTitle = (TextView) view.findViewById(R.id.models_contrast_txt_title);
            tTitle.setText(item.getName());
        } else if (item.type == Et_VechileSeries.ITEM) {
            view = LayoutInflater.from(mContext).inflate(R.layout.fc_select_car_series_list_item, null);
            TextView tvRight = (TextView) view.findViewById(R.id.models_contrast_txt_car_name);

            tvRight.setText(item.getName());
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
        return viewType == Et_VechileSeries.SECTION;
    }

    public int getCharForPosition(String c) {
        if (c.matches("[A-Z]")) {
            for (int i = 0; i < getCount(); i++) {
                if (getItem(i).type == Et_VechileSeries.SECTION) {

                    if (c.equals(getItem(i).charTv)) {
                        return getItem(i).listPosition;
                    }
                }
            }
        }
        return -1;
    }
}
