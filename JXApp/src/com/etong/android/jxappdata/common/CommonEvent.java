package com.etong.android.jxappdata.common;

import java.util.UUID;

public class CommonEvent {
	
	//融资数据
	public static final String YEAR_CHART = "year chart";
	public static final String MONTH_CHART = "month chart";
	public static final String ALL_CHART = "all chart";
	
	//融资台数
	public static final String FINANCED_NUMBER_YEAR = "financing number year";
	public static final String FINANCED_NUMBER_MONTH = "financing number month";
	public static final String FINANCED_NUMBER_ALL = "financing number all";
	
	//逾期数据
	public static final String OVERDUE_DATA_YEAR = "overdue datas year";
	public static final String OVERDUE_DATA_ALL = "overdue datas all";
	
	//发送验证码
	public static final String SEND_VERIFY = "send verify";
	
	//登录
	public static final String LOGIN = "login";
	
	//回款数据
	public static final String RETURNED_MONEY = "returned money";
	public static final String RETURNED_MONEY_ALL = "returned money all";
	
	//人群性别
	public static final String PERSON_SEX = "person sex";
	public static final String PERSON_SEX_ALL = "person sex all";
	//人群年龄
	public static final String PERSON_AGE = "person age";
	public static final String PERSON_AGE_ALL = "person age all";
	//人群婚姻状况
	public static final String PERSON_MARRIAGE = "person marriage";
	public static final String PERSON_MARRIAGE_ALL = "person marriage all";
	
	public static final String CHART = "chart";
	public static final String ONE_CHART = "one chart";
	public static final String TWO_CHART = "two chart";
	public static final String THREE_CHART = "three chart";
	
	public static final String UUIDTag=  ""+UUID.randomUUID().toString()+(int)(1+Math.random()*(10000-1+1)); 
	//用户信息
	public static final String USER_INFO = "user info";
	//集团经营分析
	public static final String DATA_INFO = "data info";
	//具体的某个老总的集团经营分析
	public static final String DATA_CONTENT_INFO = "data  content info";
	
}
