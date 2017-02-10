package com.etong.android.frame.widget.three_slide_300.common;

import com.etong.android.frame.widget.three_slide_300.javabean.Models_Contrast_SelectBrand;

import java.util.Comparator;

/**
 * 拼音比较器
 * @author sunyao
 *
 */
public class Models_Contrast_PinyinComparator implements Comparator<Models_Contrast_SelectBrand> {
	@Override
	public int compare(Models_Contrast_SelectBrand models_contrast_selectBrand, Models_Contrast_SelectBrand t1) {
		if (models_contrast_selectBrand.getInitial().equals("@")
				|| t1.getInitial().equals("#")) {
			return -1;
		} else if (models_contrast_selectBrand.getInitial().equals("#")
				|| t1.getInitial().equals("@")) {
			return 1;
		} else {
			return models_contrast_selectBrand.getInitial().compareTo(t1.getInitial());
		}
	}
}
