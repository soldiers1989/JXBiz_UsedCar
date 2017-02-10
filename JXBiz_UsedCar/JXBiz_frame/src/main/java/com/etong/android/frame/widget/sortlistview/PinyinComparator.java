package com.etong.android.frame.widget.sortlistview;

import java.util.Comparator;

/**
 * 
 * @author sunyao
 *
 */
public class PinyinComparator implements Comparator<EtongSortModel> {

	public int compare(EtongSortModel o1, EtongSortModel o2) {
		if (o1.getSortLetters().equals("@")
				|| o2.getSortLetters().equals("#")) {
			return -1;
		} else if (o1.getSortLetters().equals("#")
				|| o2.getSortLetters().equals("@")) {
			return 1;
		} else {
			return o1.getSortLetters().compareTo(o2.getSortLetters());
		}
	}

}
