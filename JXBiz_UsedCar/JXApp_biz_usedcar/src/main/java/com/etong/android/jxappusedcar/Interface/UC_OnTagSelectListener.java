package com.etong.android.jxappusedcar.Interface;



import com.etong.android.jxappusedcar.utils.UC_GrandFlowTagLayout;

import java.util.List;

/**
 * 选择事件
 */
public interface UC_OnTagSelectListener {
    void onItemSelect(UC_GrandFlowTagLayout parent, List<Integer> selectedList);
}
