package com.etong.android.jxappfours.models_contrast.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.etong.android.frame.widget.pinnedlistview.PinnedSectionListView;
import com.etong.android.jxappfours.R;
import com.etong.android.jxappfours.models_contrast.javabean.Models_Contrast_VechileSeries;
import com.etong.android.jxappfours.models_contrast.javabean.Models_Contrast_VechileType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Ellison.Sun on 2016/8/12.
 * <p/>
 * 车辆对比界面中选择车型侧滑界面
 */
public class MC_SelectCarTypeAdapter extends ArrayAdapter<Models_Contrast_VechileType> implements PinnedSectionListView.PinnedSectionListAdapter {

    private Context mContext;
    private List<Models_Contrast_VechileType> mListDatas;

    public MC_SelectCarTypeAdapter(Context context) {
        super(context, android.R.layout.simple_list_item_1);
        this.mContext = context;
    }

    public MC_SelectCarTypeAdapter(Context context, List<Models_Contrast_VechileType> listDatas) {
        super(context, android.R.layout.simple_list_item_1);
        this.mContext = context;
        this.mListDatas = listDatas;
        generateDataset(true);
    }

    public void updateListView(List<Models_Contrast_VechileType> listDatas) {
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
        if(null!=sectionList){
            for (int i = 0; i < mListDatas.size(); i++) {

                // 判断mListData数据中是否添加该头部
                Models_Contrast_VechileType temBean = mListDatas.get(i);
                double tempOutputVol = temBean.getOutputVol() / 1000;
                String result = String .format("%.1f", tempOutputVol);
                if (!sectionList.contains(result)) {       // 判断是否有添加过FullName标题名字
                    sectionList.add(result);
                    Models_Contrast_VechileType sectionBean =
                            new Models_Contrast_VechileType(Models_Contrast_VechileType.SECTION, result);
                    sectionBean.sectionPosition = sectionPosition;
                    sectionBean.listPosition = listPosition++;
                    onSectionAdded(sectionBean, sectionPosition);
                    add(sectionBean);
                }
//            // 获取到所有的Item 添加item列表
//            Models_Contrast_VechileType itemBean = mListDatas.get(j);

                // 获取到每一个item，得到相同的排量
                temBean.type = Models_Contrast_VechileType.ITEM;
                temBean.sectionPosition = sectionPosition;
                temBean.listPosition = listPosition++;
                add(temBean);
            }
            sectionPosition++;
        }

    }

    protected void prepareSections(int sectionsNumber) {
    }

    protected void onSectionAdded(Models_Contrast_VechileType section, int sectionPosition) {
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // 悬浮头的布局
        Models_Contrast_VechileType item = getItem(position);
        View view = null;
        if (item.type == Models_Contrast_VechileType.SECTION) {
            view = LayoutInflater.from(mContext).inflate(R.layout.fc_select_car_series_list_title_section, null);
            TextView tTitle = (TextView) view.findViewById(R.id.models_contrast_txt_title);
            tTitle.setText("排量 "+item.charTv +" L");
        } else if (item.type == Models_Contrast_VechileType.ITEM) {
            view = LayoutInflater.from(mContext).inflate(R.layout.fc_select_car_series_list_item, null);
            TextView tvRight = (TextView) view.findViewById(R.id.models_contrast_txt_car_name);

            tvRight.setText(item.getTitle());
        }

        ViewGroup parentView = (ViewGroup) view.getParent();
        if (parentView != null) {
            parent.removeView(view);
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
        return viewType == Models_Contrast_VechileType.SECTION;
    }

    public int getCharForPosition(String c) {
        if (c.matches("[A-Z]")) {
            for (int i = 0; i < getCount(); i++) {
                if (getItem(i).type == Models_Contrast_VechileType.SECTION) {

                    if (c.equals(getItem(i).charTv)) {
                        return getItem(i).listPosition;
                    }
                }
            }
        }
        return -1;
    }



}
