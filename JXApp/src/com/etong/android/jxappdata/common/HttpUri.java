package com.etong.android.jxappdata.common;

public class HttpUri {
	
//	public  final static String HTTP_SERVER_UNIFIED = "http://192.168.20.76:8080/JXApp";//内网测试环境
	public  final static String HTTP_SERVER_UNIFIED = "http://192.168.10.169:8080/JXApp";//内网测试环境
//	public  final static String HTTP_SERVER_UNIFIED = "http://120.25.98.100:8085/JXApp";//正式环境
	
	public  final static String SALE_NUMBER=HTTP_SERVER_UNIFIED+"/carDealer/sum/saleCount.do";//销售台数统计接口
	public  final static String SALE_AMOUNT=HTTP_SERVER_UNIFIED+"/carDealer/sum/saleAmount.do";//销售金额统计接口
	
	public  final static String MAINTENANCE_NUMBER=HTTP_SERVER_UNIFIED+"/carDealer/sum/maintenanceCount.do";//维修保养台数统计接口
	public  final static String MAINTENANCE_AMOUNT=HTTP_SERVER_UNIFIED+"/carDealer/sum/maintenanceAmount.do";//维修保养金额统计接口
	
	public  final static String BUY_TYPE=HTTP_SERVER_UNIFIED+"/carDealer/sum/buyType.do";//购车方式统计接口
	public  final static String BUY_BRAND=HTTP_SERVER_UNIFIED+"/carDealer/sum/buyBrand.do";//购车品牌统计接口
	
	public  final static String INCOME_DATE=HTTP_SERVER_UNIFIED+"/carDealer/sum/incomeDate.do";//销售收入增长统计接口
	public  final static String SALENUM_DATE=HTTP_SERVER_UNIFIED+"/carDealer/sum/saleNumDate.do";//销售数增长统计接口
	
	public  final static String FOURS_STRUCTURE=HTTP_SERVER_UNIFIED+"/carDealer/sum/get4sStructure.do";//4S店组织架构接口
	
	public  final static String FINANCED_AMOUNT=HTTP_SERVER_UNIFIED+"/hgDS/sum/amountData.do";//融资额数据统计接口
	public  final static String FINANCED_NUMBER = HTTP_SERVER_UNIFIED+"/hgDS/sum/countData.do";//融资台数统计接口
	
	public  final static String OVERDUE_DATA = HTTP_SERVER_UNIFIED+"/hgDS/sum/overdueData.do";//逾期数据统计接口
	
	public  final static String RETURNED_MONEY = HTTP_SERVER_UNIFIED+"/hgDS/sum/rTAmountData.do";//回款数据统计接口
	
//	public  final static String PERSON_ANALYZE = HTTP_SERVER_UNIFIED+"/hgDS/sum/analyzeThrong.do";//人群分析接口
	public  final static String PERSON_SEX = HTTP_SERVER_UNIFIED+"/hgDS/sum/sexData.do";	//人群性别分析
	public  final static String PERSON_AGE = HTTP_SERVER_UNIFIED+"/hgDS/sum/ageData.do";	//人群年龄分析
	public  final static String PERSON_MARRIAGE = HTTP_SERVER_UNIFIED+"/hgDS/sum/maritalStatusData.do";	//人群婚姻状况分析
	
	public  final static String SEND_VERIFY=HTTP_SERVER_UNIFIED+"/user/verify.do";//发送验证码接口
	public  final static String LOGIN=HTTP_SERVER_UNIFIED+"/user/plogon.do";//登录接口(验证码登录)
	public  final static String USER_INFO=HTTP_SERVER_UNIFIED+"/superApp/appUser/info.do";//用户信息接口
	
	public  final static String DATA_INFO=HTTP_SERVER_UNIFIED+"/carDealer/sum/getSaleAmt.do";//集团数据
	public  final static String USER_DATA_INFO=HTTP_SERVER_UNIFIED+"/carDealer/sum/userSaleAmt.do";//
	
	
}
