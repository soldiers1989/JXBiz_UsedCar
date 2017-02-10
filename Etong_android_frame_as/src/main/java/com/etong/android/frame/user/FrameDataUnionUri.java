package com.etong.android.frame.user;

import com.etong.android.frame.request_init.FrameConstant;

public class FrameDataUnionUri {
	
//	public  final static String HTTP_SERVER_UNIFIED = "http://192.168.20.76:8080/JXApp";//内网测试环境
//	public  final static String HTTP_SERVER_UNIFIED = "http://192.168.10.169:8080/JXApp";//内网测试环境
//	public  final static String HTTP_SERVER_UNIFIED = "http://192.168.10.169:8080/jxapi";//内网测试环境
//	public  final static String HTTP_SERVER_UNIFIED = "http://120.25.98.100:8085/JXApp";//正式环境
	
	public  final static String SALE_NUMBER= FrameConstant.CURRENT_SERVICE+"carDealer/sum/saleCount.do";//销售台数统计接口
	public  final static String SALE_AMOUNT=FrameConstant.CURRENT_SERVICE+"carDealer/sum/saleAmount.do";//销售金额统计接口
	
	public  final static String MAINTENANCE_NUMBER=FrameConstant.CURRENT_SERVICE+"carDealer/sum/maintenanceCount.do";//维修保养台数统计接口
	public  final static String MAINTENANCE_AMOUNT=FrameConstant.CURRENT_SERVICE+"carDealer/sum/maintenanceAmount.do";//维修保养金额统计接口
	
	public  final static String BUY_TYPE=FrameConstant.CURRENT_SERVICE+"carDealer/sum/buyType.do";//购车方式统计接口
	public  final static String BUY_BRAND=FrameConstant.CURRENT_SERVICE+"carDealer/sum/buyBrand.do";//购车品牌统计接口
	
	public  final static String INCOME_DATE=FrameConstant.CURRENT_SERVICE+"carDealer/sum/incomeDate.do";//销售收入增长统计接口
	public  final static String SALENUM_DATE=FrameConstant.CURRENT_SERVICE+"carDealer/sum/saleNumDate.do";//销售数增长统计接口
	
	public  final static String FOURS_STRUCTURE=FrameConstant.CURRENT_SERVICE+"carDealer/sum/get4sStructure.do";//4S店组织架构接口
	
	public  final static String FINANCED_AMOUNT=FrameConstant.CURRENT_SERVICE+"hgDS/sum/amountData.do";//融资额数据统计接口
	public  final static String FINANCED_NUMBER = FrameConstant.CURRENT_SERVICE+"hgDS/sum/countData.do";//融资台数统计接口
	
	public  final static String OVERDUE_DATA = FrameConstant.CURRENT_SERVICE+"hgDS/sum/overdueData.do";//逾期数据统计接口
	
	public  final static String RETURNED_MONEY = FrameConstant.CURRENT_SERVICE+"hgDS/sum/rTAmountData.do";//回款数据统计接口
	
//	public  final static String PERSON_ANALYZE = HTTP_SERVER_UNIFIED+"/hgDS/sum/analyzeThrong.do";//人群分析接口
	public  final static String PERSON_SEX = FrameConstant.CURRENT_SERVICE+"hgDS/sum/sexData.do";	//人群性别分析
	public  final static String PERSON_AGE = FrameConstant.CURRENT_SERVICE+"hgDS/sum/ageData.do";	//人群年龄分析
	public  final static String PERSON_MARRIAGE = FrameConstant.CURRENT_SERVICE+"hgDS/sum/maritalStatusData.do";	//人群婚姻状况分析
	
	public  final static String SEND_VERIFY=FrameConstant.CURRENT_SERVICE+"user/verify.do";//发送验证码接口
	public  final static String LOGIN=FrameConstant.CURRENT_SERVICE+"user/plogon.do";//登录接口(验证码登录)
	public  final static String USER_INFO=FrameConstant.CURRENT_SERVICE+"superApp/appUser/info.do";//用户信息接口
	
	public  final static String DATA_INFO=FrameConstant.CURRENT_SERVICE+"carDealer/sum/getSaleAmt.do";//集团数据
	public  final static String USER_DATA_INFO=FrameConstant.CURRENT_SERVICE+"carDealer/sum/userSaleAmt.do";//

	public  final static String UPLOAD_IMAGE=FrameConstant.CURRENT_SERVICE+"upload/file.do";//上传头像接口(没用了)
	public  final static String UPLOAD_IMAGE_SERVICER="http://222.247.51.114:10002/upload";// 用户上传图片到服务器

//	public  final static String UPLOAD_IMAGE=FrameConstant.CURRENT_SERVICE+"upload";//上传头像接口


	
	
}
