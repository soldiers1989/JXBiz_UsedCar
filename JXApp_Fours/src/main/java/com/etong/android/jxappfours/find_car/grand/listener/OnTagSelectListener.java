package com.etong.android.jxappfours.find_car.grand.listener;


import com.etong.android.jxappfours.find_car.grand.view.GrandFlowTagLayout;

import java.util.List;

/**
 * 选择事件
 */
public interface OnTagSelectListener {
    void onItemSelect(GrandFlowTagLayout parent, List<Integer> selectedList);
}
