package com.etong.android.frame.widget.sortlistview;

/**
 * @author sunyao
 */
public class EtongSortModel {

	private String name;   //显示的数据
	private String sortLetters;  //显示数据拼音的首字母
	private String imageUrl;		// 显示的图片url

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSortLetters() {
		return sortLetters;
	}
	public void setSortLetters(String sortLetters) {
		this.sortLetters = sortLetters;
	}
}
