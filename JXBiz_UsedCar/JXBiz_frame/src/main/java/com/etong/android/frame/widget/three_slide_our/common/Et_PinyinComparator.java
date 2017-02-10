package com.etong.android.frame.widget.three_slide_our.common;

import com.etong.android.frame.widget.three_slide_our.javabean.Et_SelectBrand;

import java.util.Comparator;

/**
 * 拼音比较器
 * @author sunyao
 *
 */
public class Et_PinyinComparator implements Comparator<Et_SelectBrand> {
	@Override
	public int compare(Et_SelectBrand t1, Et_SelectBrand t2) {
		if (t1.getInitial().equals("@")
				|| t2.getInitial().equals("#")) {
			return -1;
		} else if (t1.getInitial().equals("#")
				|| t2.getInitial().equals("@")) {
			return 1;
		} else {
			return t1.getInitial().compareTo(t2.getInitial());
		}
	}
}
