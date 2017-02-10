package com.etong.android.jxappcarfinancial.Interface;

/**
 * @desc (订单类型枚举接口)
 * @createtime 2016/11/23 0010--08:50
 * @Created by wukefan.
 * <p>
 * 3-车贷申请
 * 4-融资租赁申请
 * 5-畅通钱包申请
 * 6-车辆撤押申请
 */
public interface CF_InterfaceOrderType {

    int VEHICLES_LOAN = 3;                                   //车贷申请
    String VEHICLES_LOAN_NAME = "车贷申请";

    int FINANCE_LEASE = 4;                                   //融资租赁申请
    String FINANCE_LEASE_NAME = "融资租赁申请";

    int HAPPY_WALLET = 5;                                    //畅通钱包申请
    String HAPPY_WALLET_NAME = "畅通钱包申请";

    int VEHICLES_DRAW = 6;                                   //车辆撤押申请
    String VEHICLES_DRAW_NAME = "车辆撤押申请";

}
