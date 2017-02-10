package com.etong.android.jxbizusedcar.Interface;

/**
 * @desc (订单状态枚举接口)
 * @createtime 2016/11/10 0010--19:46
 * @Created by wukefan.
 * <p>
 * 0-待支付
 * 1-已支付，等待报告
 * 1008-支付成功，补录信息（车牌号）
 * 1009-支付成功，补录信息（发动机号）；
 * 1010-支付成功，补录信息（车牌号&发动机号）；
 * 1011-支付成功，不要重复购买
 * 2-购买报告成功，等待回调
 * 3-报告无维修记录，等待退款；（退款中）
 * 4001-订单完成(成功)；
 * 4002-订单取消；
 * 5-退款成功；
 * 6-退款失败
 */
public interface UC_InterfaceStatus {

    String WAITING_FOR_PAY = "0";                   //待支付
    String PAY_SUCCESS_FOR_WAIT = "1";               //已支付，等待报告
    String PAY_SUCCESS_NUMBER_PLATE = "1008";       //支付成功，补录信息（车牌号）
    String PAY_SUCCESS_ENGINE_NUMBER = "1009";    //支付成功，补录信息（发动机号）
    String PAY_SUCCESS_ENGINE_PLATE = "1010";    //支付成功，补录信息（车牌号&发动机号）
    String PAY_SUCCESS_NO_SAME = "1011";        //支付成功，不要重复购买
    String WAITING_FOR_REPORT = "2";            //购买报告成功，等待回调
    String REPORT_ERROR = "3";                  //报告无维修记录，等待退款（退款中）
    String ORDER_COMPLETE = "4001";            //订单完成(成功)
    String ORDER_CANCEL = "4002";               //订单取消
    String REFUND_SUCCESS = "5";                //退款成功
    String REFUND_FAILED = "6";                 //退款失败

}
