package com.etong.android.jxappfours.find_car.grand.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.etong.android.frame.widget.pinnedlistview.PinnedSectionListView;
import com.etong.android.jxappfours.R;
import com.etong.android.jxappfours.find_car.grand.bean.FC_CarconfigCompareItem;
import com.etong.android.jxappfours.find_car.grand.bean.Find_car_CarConfigListItem;
import com.etong.android.jxappfours.find_car.grand.car_config.FC_ConfigItemInfoUtil;
import com.etong.android.jxappfours.find_car.grand.view.HVScrollView;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * Created by Ellison.Sun on 2016/8/15.
 * <p/>
 * 车辆配置比较界面的适配器
 */
public class Find_car_CarconfigCompareAdapter extends ArrayAdapter<FC_CarconfigCompareItem> implements PinnedSectionListView.PinnedSectionListAdapter {
    private Activity mContext;
    private List<HVScrollView> mHScrollViews;
    private PinnedSectionListView mListView;
    private List<List> mData = new ArrayList<List>();
    private Map configMap;
    private List diffList = new ArrayList();        // 保存不同项
    private List allList = new ArrayList();         // 保存所有项
    private List difData;

    private int currentCount = 0;

    public Find_car_CarconfigCompareAdapter(Context context, List<HVScrollView> mHScrollViews, PinnedSectionListView listView) {
        super(context, android.R.layout.simple_list_item_1, android.R.id.text1);
        this.mContext = (Activity) context;
        this.mHScrollViews = mHScrollViews;
        this.mListView = listView;
    }

    /**
     * 更新视图
     *
     * @param data
     */
    public void updateListViewData(List data) {

        // 清空其中的数据
        if (diffList != null) {
            diffList.clear();
        }

        this.mData = (List<List>) data.get(1);
        this.currentCount = mData.size();
        this.diffList = data.get(0) == null ? new ArrayList() : new ArrayList((Set) data.get(0));
        generateDataset(true);
        notifyDataSetChanged();
    }

    /**
     * 隐藏相同的条目
     */
    public void hideSameListViewData() {
        clear();
        if(difData != null) {
            addAll(difData);
        }
        notifyDataSetChanged();
    }

    /**
     * 显示所有的条目
     */
    public void showAllListViewData() {
        clear();
        if (allList != null) {
            addAll(allList);
        }
        notifyDataSetChanged();
    }

    /**
     * 删除某一个条目
     * @param whichTeam
     * @param position
     */
    public void deleteItemByPosition(String whichTeam, int position) {
        this.currentCount = this.currentCount - 1;      // 删除一个将当前的个数减一
        // 判断Position是否合理
        if (position < 0 || position > difData.size()
                || position > allList.size() || TextUtils.isEmpty(whichTeam)) {
            return;
        }
        if ("隐藏相同项".equals(whichTeam)) {
            for (int i = 0; i < difData.size(); i++) {
                List<String> rText = ((FC_CarconfigCompareItem) difData.get(i)).rText;
                if (rText != null) {
                    for (int j = 0; j < rText.size(); j++) {
                        if (position == j) {
                            rText.remove(j);
                            break;
                        }
                    }
                }
            }
            addAll(difData);
            notifyDataSetChanged();
        }else if ("显示所有项".equals(whichTeam)) {
            for (int i = 0; i < allList.size(); i++) {
                List<String> rText = ((FC_CarconfigCompareItem) allList.get(i)).rText;
                if (rText != null) {
                    for (int j = 0; j < rText.size(); j++) {
                        if (position == j) {
                            rText.remove(j);
                        }
                    }
                }
            }
            addAll(allList);
            notifyDataSetChanged();
        }
    }
    /**
     * 生成数据的方法
     *
     * @param clear
     */
    public void generateDataset(boolean clear) {

        if (clear) clear();

        if (allList != null) {
            allList.clear();
        }

        final int sectionsNumber = 1;
        prepareSections(sectionsNumber);

        String[] strTitle = FC_ConfigItemInfoUtil.getStrTitle();        // 获取到悬浮标题
        String[][] strItems = FC_ConfigItemInfoUtil.getStrItems();      // 获取到中文item
        String[][] strLetterItem = FC_ConfigItemInfoUtil.getStrLetterItem();    // 获取到英文item

        //  将所有的item对应title存入Map中
        configMap = new HashMap();
        for (int i = 0; i < strLetterItem.length; i++) {
            for (int j = 0; j < strLetterItem[i].length; j++) {
                configMap.put(strLetterItem[i][j], strTitle[i]);
            }
        }

        this.difData = new ArrayList();
        int sectionPosition = 0, listPosition = 0;
        int difsectionPosition = 0, diflistPosition = 0;
        for (char i = 0; i < strTitle.length; i++) {

            int h = 0;
            // 添加所有的item
            FC_CarconfigCompareItem section = new FC_CarconfigCompareItem(FC_CarconfigCompareItem.SECTION, strTitle[i]);
            section.sectionPosition = sectionPosition;
            section.listPosition = listPosition++;
            onSectionAdded(section, sectionPosition);

            // 添加不同项
            FC_CarconfigCompareItem difsection = new FC_CarconfigCompareItem(FC_CarconfigCompareItem.SECTION, strTitle[i]);
            difsection.sectionPosition = difsectionPosition;
            difsection.listPosition = diflistPosition++;
            onSectionAdded(difsection, difsectionPosition);
            this.difData.add(difsection);

            this.allList.add(section);

            for (int j = 0; j < strItems[i].length; j++) {

                List<String> tempList = new ArrayList<String>();
                for (int a = 0; a < mData.size(); a++) {
                    List t = mData.get(a);
                    Object object;
                    try {
                        Field field = t.get(i).getClass().getDeclaredField(strLetterItem[i][j]);
                        field.setAccessible(true);
                        object = field.get(t.get(i));
                        tempList.add(object.toString());
                    } catch (IllegalAccessException e) {
                        tempList.add("");
                    } catch (NoSuchFieldException e) {
                        tempList.add("");
                    } catch (NullPointerException e) {
                        tempList.add("");
                    }
                }
                FC_CarconfigCompareItem item = new FC_CarconfigCompareItem(FC_CarconfigCompareItem.ITEM, strItems[i][j], tempList);
                item.sectionPosition = sectionPosition;
                item.listPosition = listPosition++;
                if (this.diffList.contains(strLetterItem[i][j])) {
                    FC_CarconfigCompareItem itemdif = new FC_CarconfigCompareItem(FC_CarconfigCompareItem.ITEM, strItems[i][j], tempList);
                    itemdif.sectionPosition = difsectionPosition;
                    itemdif.listPosition = diflistPosition++;
                    this.difData.add(itemdif);
                    h++;
                    item.isDiff = true;
                    itemdif.isDiff = true;
                }
                // 将所有的item添加到List中
                this.allList.add(item);
            }
            if (0 == h) {
                this.difData.remove(difsection);
            } else {
                difsectionPosition++;
            }
            sectionPosition++;
        }
        // 将所有的Item一次性添加到Adapter中
        addAll(allList);
    }

    protected void prepareSections(int sectionsNumber) {
    }

    protected void onSectionAdded(FC_CarconfigCompareItem section, int sectionPosition) {
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // 悬浮头的布局
        FC_CarconfigCompareItem item = getItem(position);
        SectionViewHolder sectionViewHolder = null;
        ItemViewHolder itemViewHolder = null;
        int type = getItemViewType(position);

        // 根据Type值来加载布局文件
        if (convertView == null) {
            switch (type) {
                case FC_CarconfigCompareItem.SECTION:
                    convertView =  LayoutInflater.from(mContext).inflate(R.layout.find_car_detail_one_list_title, parent, false);
                    sectionViewHolder = new SectionViewHolder();
                    sectionViewHolder.tText = (TextView)convertView.findViewById(R.id.find_car_detail_config_title_bar);
                    convertView.setTag(sectionViewHolder);
                    break;
                case FC_CarconfigCompareItem.ITEM:
                    convertView =  LayoutInflater.from(mContext).inflate(R.layout.find_car_detail_config_compare_list_item, parent, false);
                    itemViewHolder = new ItemViewHolder();
                    itemViewHolder.lText = (TextView) convertView.findViewById(R.id.find_car_detail_config_compare_list_item_title);
                    itemViewHolder.hvsv = (HVScrollView) convertView.findViewById(R.id.find_car_detail_config_compare_list_item_sv);
                    itemViewHolder.vp = (ViewGroup) convertView.findViewById(R.id.find_car_detail_config_compare_list_content);
                    convertView.setTag(itemViewHolder);
                    break;
            }
        } else {
            switch (type) {
                case FC_CarconfigCompareItem.SECTION:
                    sectionViewHolder = (SectionViewHolder) convertView.getTag();
                    break;
                case FC_CarconfigCompareItem.ITEM:
                    itemViewHolder = (ItemViewHolder) convertView.getTag();
                    break;
            }
        }

        // 根据Type值来设置布局上的文字
        switch (type) {
            case FC_CarconfigCompareItem.SECTION:
                sectionViewHolder.tText.setText(item.tText);
                break;
            case FC_CarconfigCompareItem.ITEM:
                // 添加首个文字
                itemViewHolder.lText.setText(item.lText);
                // 清除ScrollView中的item
                itemViewHolder.vp.removeAllViews();
                // 可以再次进行优化，加载一次就不需要再次加载该布局
                for (int i = 0; i < this.currentCount; i++) {
                    View cell = LayoutInflater.from(mContext).inflate(R.layout.find_car_detail_config_compare_list_item_cell, null);
                    ViewGroup viewGroup = (ViewGroup) cell.findViewById(R.id.fc_detail_config_compare_list_item_content);
                    TextView cell_tv = (TextView) cell.findViewById(R.id.fc_detail_config_compare_list_item_cell_tv);
                    if (item.rText != null && item.rText.size() > 0) {
                        if (TextUtils.isEmpty(item.rText.get(i))) {
                            cell_tv.setText("-");
                        } else {
                            cell_tv.setText(item.rText.get(i));
                        }
                    }
                    if (item.isDiff) {
                        viewGroup.setBackgroundColor(Color.parseColor("#E5EDF9"));
                    }
                    itemViewHolder.vp.addView(cell);
                }
                addHViews(itemViewHolder.hvsv);
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
        return viewType == Find_car_CarConfigListItem.SECTION;
    }

    public void addHViews(final HVScrollView hScrollView) {
        if (!mHScrollViews.isEmpty()) {
            int size = mHScrollViews.size();
            HVScrollView scrollView = mHScrollViews.get(0);
            final int scrollX = scrollView.getScrollX();

            if (scrollX != 0) {
                mListView.post(new Runnable() {
                    @Override
                    public void run() {

                        hScrollView.scrollTo(scrollX, 0);
                    }
                });
            }
        }
        mHScrollViews.add(hScrollView);
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
        HVScrollView hvsv;
        ViewGroup vp;
    }
}
