package com.etong.android.jxappfours.find_car.grand.adapter;

import android.content.Context;
import android.widget.SectionIndexer;

import com.etong.android.jxappfours.find_car.grand.bean.FC_InsalesListItem;

/**
 * Created by Ellison.Sun on 2016/8/12.
 *
 * 悬浮快滑
 */
public class FC_GrandAllFastAdapter extends FC_GrandInsalesAdapter implements SectionIndexer {

    private FC_InsalesListItem[] sections;

    public FC_GrandAllFastAdapter(Context context, int resource, int textViewResourceId) {
        super(context);
//        super(context, resource, textViewResourceId);
    }

    @Override protected void prepareSections(int sectionsNumber) {
        sections = new FC_InsalesListItem[sectionsNumber];
    }

    @Override protected void onSectionAdded(FC_InsalesListItem section, int sectionPosition) {
        sections[sectionPosition] = section;
    }

    @Override public FC_InsalesListItem[] getSections() {
        return sections;
    }

    @Override public int getPositionForSection(int section) {
        if (section >= sections.length) {
            section = sections.length - 1;
        }
        return sections[section].listPosition;
    }

    @Override public int getSectionForPosition(int position) {
        if (position >= getCount()) {
            position = getCount() - 1;
        }
        return getItem(position).sectionPosition;
    }

}
