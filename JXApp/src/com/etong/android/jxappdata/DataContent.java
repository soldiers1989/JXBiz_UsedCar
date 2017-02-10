package com.etong.android.jxappdata;

import java.util.List;

public class DataContent {
	private String url;
	private List list;
	private int index;
	private String title;
	
	public DataContent(){
		
	}
	
	
	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public int getIndex() {
		return index;
	}


	public void setIndex(int index) {
		this.index = index;
	}


	public DataContent(List list){
		this.list=list;
	}
	public DataContent(List list,String url){
		this.list=list;
		this.url=url;
	} 
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public List getList() {
		return list;
	}
	public void setList(List list) {
		this.list = list;
	}
	
	
}
