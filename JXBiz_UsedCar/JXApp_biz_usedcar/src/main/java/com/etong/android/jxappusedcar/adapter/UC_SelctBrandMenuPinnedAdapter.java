package com.etong.android.jxappusedcar.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.etong.android.frame.widget.pinnedlistview.PinnedSectionListView;
import com.etong.android.jxappusedcar.R;
import com.etong.android.jxappusedcar.bean.UC_SelectBrandMenuPinnedBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ellison.Sun on 2016/8/12.
 * <p>
 * Menu中在售车型的适配器
 */
public class UC_SelctBrandMenuPinnedAdapter extends ArrayAdapter<UC_SelectBrandMenuPinnedBean> implements PinnedSectionListView.PinnedSectionListAdapter {

    private Context mContext;

    private List<UC_SelectBrandMenuPinnedBean> mListDatas;            // 传递过来的数据

    public UC_SelctBrandMenuPinnedAdapter(Context context) {
        super(context, android.R.layout.simple_list_item_1);
        this.mContext = context;
        mListDatas = new ArrayList<UC_SelectBrandMenuPinnedBean>();
    }

    public void updateDatas(List<UC_SelectBrandMenuPinnedBean> listDatas) {
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
        if (mListDatas == null) {
            return;
        }
        if (clear) clear();
        final int sectionsNumber = 'Z' - 'A' + 1;
        prepareSections(sectionsNumber);

        int sectionPosition = 0, listPosition = 0;
        for (int i = 0; i < mListDatas.size(); i++) {
            UC_SelectBrandMenuPinnedBean pinnedBean = mListDatas.get(i);
            // 表示品牌的item列
            if (pinnedBean.getId() == 0) {

                pinnedBean.type = UC_SelectBrandMenuPinnedBean.SECTION;
                pinnedBean.sectionPosition = sectionPosition;
                pinnedBean.listPosition = listPosition++;
                onSectionAdded(pinnedBean, sectionPosition);
                add(pinnedBean);
            } else {
                // 表示车系的item列
                pinnedBean.type = UC_SelectBrandMenuPinnedBean.ITEM;
                pinnedBean.sectionPosition = sectionPosition;
                pinnedBean.listPosition = listPosition++;
                add(pinnedBean);
            }
            sectionPosition++;
        }
    }

    protected void prepareSections(int sectionsNumber) {
    }

    protected void onSectionAdded(UC_SelectBrandMenuPinnedBean section, int sectionPosition) {
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // 悬浮头的布局
        UC_SelectBrandMenuPinnedBean item = getItem(position);
        View view = null;
        if (item.type == UC_SelectBrandMenuPinnedBean.SECTION) {
            view = LayoutInflater.from(mContext).inflate(R.layout.uc_content_menu_lv_select_brand_title_section, null);
            TextView tTitle = (TextView) view.findViewById(R.id.uc_content_menu_lv_select_brand_title_tv_section);
            tTitle.setText(item.getName());
        } else if (item.type == UC_SelectBrandMenuPinnedBean.ITEM) {
            view = LayoutInflater.from(mContext).inflate(R.layout.uc_content_menu_lv_select_brand_title_item, null);
            TextView rTText = (TextView) view.findViewById(R.id.uc_content_menu_lv_select_brand_title_tv_item);

            rTText.setText(item.getName());
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
        return viewType == UC_SelectBrandMenuPinnedBean.SECTION;
    }
}
