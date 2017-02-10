package com.etong.android.jxappusedcar.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.etong.android.frame.widget.pinnedlistview.PinnedSectionListView;
import com.etong.android.jxappusedcar.R;
import com.etong.android.jxappusedcar.javabean.UC_CarConfigListItem;
import com.etong.android.jxappusedcar.utils.UC_ConfigItemInfoUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ellison.Sun on 2016/8/12.
 * <p>
 * 车辆配置的适配器
 */
public class UC_CarConfigAdapter extends ArrayAdapter<UC_CarConfigListItem> implements PinnedSectionListView.PinnedSectionListAdapter {

    private Context mContext;
    private List mList;
    private List allList = new ArrayList();         // 保存所有项

    public UC_CarConfigAdapter(Context context) {
        super(context, android.R.layout.simple_list_item_1);
        this.mContext = context;
        this.mList = new ArrayList();
    }

    public void updateListData(List<Object> listData) {
        this.mList = listData;
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

        final int sectionsNumber = 1;
        prepareSections(sectionsNumber);

        String[] strTitle = UC_ConfigItemInfoUtil.getStrTitle();
        String[][] strItems = UC_ConfigItemInfoUtil.getStrItems();
        String[][] strLetterItem = UC_ConfigItemInfoUtil.getStrLetterItem();

        int sectionPosition = 0, listPosition = 0;

        for (int i = 0; i < strTitle.length; i++) {
            UC_CarConfigListItem section = new UC_CarConfigListItem(UC_CarConfigListItem.SECTION, strTitle[i]);
            section.sectionPosition = sectionPosition;
            section.listPosition = listPosition++;
            onSectionAdded(section, sectionPosition);

            this.allList.add(section);      // 添加头部悬浮

            for (int j = 0; j < strItems[i].length; j++) {
                Object object;
                try {
                    Field field = mList.get(i).getClass().getDeclaredField(strLetterItem[i][j]);
                    field.setAccessible(true);
                    object = field.get(mList.get(i));
                } catch (Exception e) {
                    object = "";
                }
                UC_CarConfigListItem item;
                try {
                    item = new UC_CarConfigListItem(UC_CarConfigListItem.ITEM, strItems[i][j], object.toString());
                    item.sectionPosition = sectionPosition;
                    item.listPosition = listPosition++;
                } catch (Exception e) {
                    item = new UC_CarConfigListItem(UC_CarConfigListItem.ITEM, strItems[i][j], "");
                    item.sectionPosition = sectionPosition;
                    item.listPosition = listPosition++;
                }
                // 将所有的item添加到List中
                this.allList.add(item);
            }
            sectionPosition++;
        }
        // 将所有的Item一次性添加到Adapter中
        addAll(allList);
    }

    protected void prepareSections(int sectionsNumber) {
    }

    protected void onSectionAdded(UC_CarConfigListItem section, int sectionPosition) {
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // 获取到每一项
        UC_CarConfigListItem item = getItem(position);
        SectionViewHolder sectionViewHolder = null;
        ItemViewHolder itemViewHolder = null;
        int type = getItemViewType(position);

        // 根据Type值来加载布局文件
        if (convertView == null) {
            switch (type) {
                case UC_CarConfigListItem.SECTION:
                    convertView =  LayoutInflater.from(mContext).inflate(R.layout.used_car_detail_one_list_title, parent, false);
                    sectionViewHolder = new SectionViewHolder();
                    sectionViewHolder.tText = (TextView)convertView.findViewById(R.id.used_car_detail_config_title_bar);
                    convertView.setTag(sectionViewHolder);
                    break;
                case UC_CarConfigListItem.ITEM:
                    convertView =  LayoutInflater.from(mContext).inflate(R.layout.used_car_detail_one_list_item, parent, false);
                    itemViewHolder = new ItemViewHolder();
                    itemViewHolder.lText = (TextView) convertView.findViewById(R.id.used_car_car_config_detail_title);
                    itemViewHolder.rText = (TextView) convertView.findViewById(R.id.used_car_car_config_detail_desc);
                    convertView.setTag(itemViewHolder);
                    break;
            }
        } else {
            switch (type) {
                case UC_CarConfigListItem.SECTION:
                    sectionViewHolder = (SectionViewHolder) convertView.getTag();
                    break;
                case UC_CarConfigListItem.ITEM:
                    itemViewHolder = (ItemViewHolder) convertView.getTag();
                    break;
            }
        }

        // 根据Type值来设置布局上的文字
        switch (type) {
            case UC_CarConfigListItem.SECTION:
                sectionViewHolder.tText.setText(item.tText);
                break;
            case UC_CarConfigListItem.ITEM:
                itemViewHolder.lText.setText(item.lText);
                if (!TextUtils.isEmpty(item.rText)) {   // 如果获取到的参数不为空
                    itemViewHolder.rText.setText(item.rText);
                } else {                                // 如果获取到的参数为空则设置为   -
                    itemViewHolder.rText.setText("-");
                }
                break;
        }

        return convertView;

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
        return viewType == UC_CarConfigListItem.SECTION;
    }

    /**
     * @desc (头部Holder)
     * @user sunyao
     * @createtime 2016/11/24 - 18:30
     * @param
     * @return
     */
    class SectionViewHolder{
        TextView tText;
    }

    /**
     * @desc (子item中的Holder)
     * @user sunyao
     * @createtime 2016/11/24 - 20:02
     * @param
     * @return
     */
    class ItemViewHolder {
        TextView lText;
        TextView rText;
    }
}
