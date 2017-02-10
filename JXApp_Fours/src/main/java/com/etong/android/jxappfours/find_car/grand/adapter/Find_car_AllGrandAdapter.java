package com.etong.android.jxappfours.find_car.grand.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.etong.android.frame.common.ImageProvider;
import com.etong.android.frame.widget.pinnedlistview.PinnedSectionListView;
import com.etong.android.jxappfours.R;
import com.etong.android.jxappfours.find_car.grand.bean.Find_car_GrandBrandItemBean;

import java.util.List;

/**
 * Created by Ellison.Sun on 2016/8/12.
 *
 * 车辆配置的适配器
 */
public class Find_car_AllGrandAdapter extends ArrayAdapter<Find_car_GrandBrandItemBean> implements PinnedSectionListView.PinnedSectionListAdapter {

    private Context mContext;
    private List<Find_car_GrandBrandItemBean> mListDatas;

    public Find_car_AllGrandAdapter(Context context,List<Find_car_GrandBrandItemBean> listDatas) {
        super(context, android.R.layout.simple_list_item_1);
        this.mContext = context;
        this.mListDatas = listDatas;
        generateDataset(true);
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
                Find_car_GrandBrandItemBean temBean = mListDatas.get(a);
                if (String.valueOf((char)('A' + i)).equals(temBean.getLetter())) {
                    Find_car_GrandBrandItemBean sectionBean =
                            new Find_car_GrandBrandItemBean(Find_car_GrandBrandItemBean.SECTION, String.valueOf((char) ('A' + i)));
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
                Find_car_GrandBrandItemBean itemBean = mListDatas.get(j);
                if((String.valueOf((char)('A' + i)).equals(itemBean.getLetter()))) {
                    // 获取到每一个item，得到它的字母
                    itemBean.type = Find_car_GrandBrandItemBean.ITEM;
                    itemBean.sectionPosition = sectionPosition;
                    itemBean.listPosition = listPosition++;
                    add(itemBean);
                }
            }
            sectionPosition++;
        }
    }

    protected void prepareSections(int sectionsNumber) { }
    protected void onSectionAdded(Find_car_GrandBrandItemBean section, int sectionPosition) { }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
//        // 悬浮头的布局
//        Find_car_GrandBrandItemBean item = getItem(position);
//        View view = null;
//        if (item.type == Find_car_GrandBrandItemBean.SECTION) {
//            view = LayoutInflater.from(mContext).inflate(R.layout.fc_grand_title_section, null);
//            TextView tTitle = (TextView) view.findViewById(R.id.fc_grand_title_tv);
//            tTitle.setText(item.charTv);
//        } else if (item.type == Find_car_GrandBrandItemBean.ITEM) {
//            view = LayoutInflater.from(mContext).inflate(R.layout.fc_grand_item_detail, null);
//            ImageView ivLeft = (ImageView) view.findViewById(R.id.fc_grand_item_detail_iv);
//            TextView tvRight = (TextView) view.findViewById(R.id.fc_grand_item_detail_tv);
//
//            try {
//                ImageProvider.getInstance().loadImage(ivLeft, item.getImage(),R.mipmap.fours_grand_loading);
//            } catch (Exception e) {
//            }
//            tvRight.setText(item.getTitle());
//        }
//        view.setTag("" + position);
//        return view;

        // 悬浮头的布局
        Find_car_GrandBrandItemBean item = getItem(position);
        SectionViewHolder sectionViewHolder = null;
        ItemViewHolder itemViewHolder = null;
        int type = getItemViewType(position);

        // 根据Type值来加载布局文件
        if (convertView == null) {
            switch (type) {
                case Find_car_GrandBrandItemBean.SECTION:
                    convertView =  LayoutInflater.from(mContext).inflate(R.layout.fc_grand_title_section, parent, false);
                    sectionViewHolder = new SectionViewHolder();
                    sectionViewHolder.tText = (TextView)convertView.findViewById(R.id.fc_grand_title_tv);
                    convertView.setTag(sectionViewHolder);
                    break;
                case Find_car_GrandBrandItemBean.ITEM:
                    convertView =  LayoutInflater.from(mContext).inflate(R.layout.fc_grand_item_detail, parent, false);
                    itemViewHolder = new ItemViewHolder();
                    itemViewHolder.itemIv = (ImageView) convertView.findViewById(R.id.fc_grand_item_detail_iv);
                    itemViewHolder.itemTv = (TextView) convertView.findViewById(R.id.fc_grand_item_detail_tv);
                    convertView.setTag(itemViewHolder);
                    break;
            }
        } else {
            switch (type) {
                case Find_car_GrandBrandItemBean.SECTION:
                    sectionViewHolder = (SectionViewHolder) convertView.getTag();
                    break;
                case Find_car_GrandBrandItemBean.ITEM:
                    itemViewHolder = (ItemViewHolder) convertView.getTag();
                    break;
            }
        }

        // 根据Type值来设置布局上的文字
        switch (type) {
            case Find_car_GrandBrandItemBean.SECTION:
                sectionViewHolder.tText.setText(item.charTv);
                break;
            case Find_car_GrandBrandItemBean.ITEM:
                itemViewHolder.itemTv.setText(item.getTitle());
                try {
                    ImageProvider.getInstance().loadImage( itemViewHolder.itemIv, item.getImage(), R.mipmap.fours_grand_loading);
                } catch (Exception e) {
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
        return viewType == Find_car_GrandBrandItemBean.SECTION;
    }

    public int getCharForPosition(String c) {
        if (c.matches("[A-Z]")) {
            for (int i = 0; i < getCount(); i++) {
                if (getItem(i).type == Find_car_GrandBrandItemBean.SECTION) {

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
        ImageView itemIv;
        TextView itemTv;
    }
}
