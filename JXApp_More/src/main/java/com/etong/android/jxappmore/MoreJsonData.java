package com.etong.android.jxappmore;

import android.content.Context;

import com.etong.android.frame.user.FrameEtongApplication;
import com.etong.android.frame.user.FrameUserInfo;

/**
 * 创造的JSON假数据
 * Created by Administrator on 2016/8/4 0004.
 */
public class MoreJsonData {

    private static FrameUserInfo mFrameUserInfo;


    static String unLoginJsonStr = "[{\"titleName\":\"新车购买\","
            + "\"itemBeamList\":[{\"itemName\":\"找车\","
            + "\"itemIconName\":\"find_car\"},"
            + "{\"itemName\":\"车型对比\","
            + "\"itemIconName\":\"models_comparison\"},"
            + "{\"itemName\":\"试驾预约\","
            + "\"itemIconName\":\"test_drive_appointment\"},"
            + "{\"itemName\":\"订购预约\","
            + "\"itemIconName\":\"order_reservation\"},"
            + "{\"itemName\":\"维保预约\","
            + "\"itemIconName\":\"maintenance_appointment\"},"
            + "{\"itemName\":\"维保进度\","
            + "\"itemIconName\":\"maintenance_progress\"},"
            + "{\"itemName\":\"道路救援\","
            + "\"itemIconName\":\"road_rescue\"}]},"
            + "{\"titleName\":\"汽车金融\","
            + "\"itemBeamList\":[{\"itemName\":\"车贷申请\","
            + "\"itemIconName\":\"car_loan_application\"},"
            + "{\"itemName\":\"融资租赁申请\","
            + "\"itemIconName\":\"car_rental_application\"},"
            + "{\"itemName\":\"畅通钱包申请\","
            + "\"itemIconName\":\"happy_card_application\"},"
            + "{\"itemName\":\"车辆撤押申请\","
            + "\"itemIconName\":\"vehicles_draw\"},"
            + "{\"itemName\":\"申请进度\","
            + "\"itemIconName\":\"application_progress\"},"
            + "{\"itemName\":\"贷款记录\","
            + "\"itemIconName\":\"loan_record\"},"
            + "{\"itemName\":\"还款记录\","
            + "\"itemIconName\":\"repayment_history\"},"
            + "{\"itemName\":\"还款提醒\","
            + "\"itemIconName\":\"repayment_reminder\"},"
            + "{\"itemName\":\"融资计算\","
            + "\"itemIconName\":\"financing_calculation\"},"
            + "{\"itemName\":\"逾期记录\","
            + "\"itemIconName\":\"more_overdue\"}]},"
            + "{\"titleName\":\"ETC\","
            + "\"itemBeamList\":[{\"itemName\":\"ETC办卡预约\","
            + "\"itemIconName\":\"etc_card_appointment\"},"
            + "{\"itemName\":\"ETC充值\","
            + "\"itemIconName\":\"etc_recharge\"},"
            + "{\"itemName\":\"余额查询\","
            + "\"itemIconName\":\"balance_query\"},"
            + "{\"itemName\":\"流水记录\","
            + "\"itemIconName\":\"running_account\"},"
            + "{\"itemName\":\"扣款记录\","
            + "\"itemIconName\":\"chargebacks_record\"},"
            + "{\"itemName\":\"发票打印\","
            + "\"itemIconName\":\"invoice_printing\"}]},"
            + "{\"titleName\":\"保险\","
            + "\"itemBeamList\":[{\"itemName\":\"续保预约\","
            + "\"itemIconName\":\"more_renewal_order\"},"
            + "{\"itemName\":\"一键理赔\","
            + "\"itemIconName\":\"onekey_claim\"},"
            + "{\"itemName\":\"续保提醒\","
            + "\"itemIconName\":\"more_renewal_remind\"}]},"
            + "{\"titleName\":\"在线购车\","
            + "\"itemBeamList\":[{\"itemName\":\"底价购车\","
            + "\"itemIconName\":\"car_buying\"},"
            + "{\"itemName\":\"限时购\","
            + "\"itemIconName\":\"more_limited_time\"},"
            + "{\"itemName\":\"促销车\","
            + "\"itemIconName\":\"more_promotion_car\"},"
            + "{\"itemName\":\"汽车服务\","
            + "\"itemIconName\":\"more_car_service\"}]},"
            + "{\"titleName\":\"二手车\","
            + "\"itemBeamList\":[{\"itemName\":\"预约卖车\","
            + "\"itemIconName\":\"more_order_buycars\"},"
            + "{\"itemName\":\"二手车世界\","
            + "\"itemIconName\":\"more_usedcar_world\"}]},"
            + "{\"titleName\":\"客运\","
            + "\"itemBeamList\":[{\"itemName\":\"行程发布\","
            + "\"itemIconName\":\"travel_release\"},"
            + "{\"itemName\":\"行程订单接单\","
            + "\"itemIconName\":\"travel_order\"},"
            + "{\"itemName\":\"行程记录\","
            + "\"itemIconName\":\"travel_record\"},"
            + "{\"itemName\":\"投诉维权\","
            + "\"itemIconName\":\"complaints_rights\"}]},"
            + "{\"titleName\":\"社区服务\","
            + "\"itemBeamList\":[{\"itemName\":\"推荐服务\","
            + "\"itemIconName\":\"recommendation_service\"},"
            + "{\"itemName\":\"社区服务记录\","
            + "\"itemIconName\":\"community_service_record\"}]},"
            + "{\"titleName\":\"用车助手\","
            + "\"itemBeamList\":[{\"itemName\":\"违章查询\","
            + "\"itemIconName\":\"violation_query\"},"
            + "{\"itemName\":\"附近加油站\","
            + "\"itemIconName\":\"near_gas_station\"},"
            + "{\"itemName\":\"周边社区服务\","
            + "\"itemIconName\":\"peripheral_service\"},"
            + "{\"itemName\":\"附近停车场\","
            + "\"itemIconName\":\"near_park\"}]}]";

    static String loginJsonStr = "[{\"titleName\":\"新车购买\","
            + "\"itemBeamList\":[{\"itemName\":\"找车\","
            + "\"itemIconName\":\"find_car\"},"
            + "{\"itemName\":\"车型对比\","
            + "\"itemIconName\":\"models_comparison\"},"
            + "{\"itemName\":\"试驾预约\","
            + "\"itemIconName\":\"test_drive_appointment\"},"
            + "{\"itemName\":\"订购预约\","
            + "\"itemIconName\":\"order_reservation\"},"
            + "{\"itemName\":\"维保预约\","
            + "\"itemIconName\":\"maintenance_appointment\"},"
            + "{\"itemName\":\"维保进度\","
            + "\"itemIconName\":\"maintenance_progress\"},"
            + "{\"itemName\":\"道路救援\","
            + "\"itemIconName\":\"road_rescue\"}]},"
            + "{\"titleName\":\"汽车金融\","
            + "\"itemBeamList\":[{\"itemName\":\"车贷申请\","
            + "\"itemIconName\":\"car_loan_application\"},"
            + "{\"itemName\":\"融资租赁申请\","
            + "\"itemIconName\":\"car_rental_application\"},"
            + "{\"itemName\":\"畅通钱包申请\","
            + "\"itemIconName\":\"happy_card_application\"},"
            + "{\"itemName\":\"车辆撤押申请\","
            + "\"itemIconName\":\"vehicles_draw\"},"
            + "{\"itemName\":\"申请进度\","
            + "\"itemIconName\":\"application_progress\"},"
            + "{\"itemName\":\"贷款记录\","
            + "\"itemIconName\":\"loan_record\"},"
            + "{\"itemName\":\"还款记录\","
            + "\"itemIconName\":\"repayment_history\"},"
            + "{\"itemName\":\"还款提醒\","
            + "\"itemIconName\":\"repayment_reminder\"},"
            + "{\"itemName\":\"融资计算\","
            + "\"itemIconName\":\"financing_calculation\"},"
            + "{\"itemName\":\"逾期记录\","
            + "\"itemIconName\":\"more_overdue\"}]},"
            + "{\"titleName\":\"ETC\","
            + "\"itemBeamList\":[{\"itemName\":\"ETC办卡预约\","
            + "\"itemIconName\":\"etc_card_appointment\"},"
            + "{\"itemName\":\"ETC充值\","
            + "\"itemIconName\":\"etc_recharge\"},"
            + "{\"itemName\":\"余额查询\","
            + "\"itemIconName\":\"balance_query\"},"
            + "{\"itemName\":\"流水记录\","
            + "\"itemIconName\":\"running_account\"},"
            + "{\"itemName\":\"扣款记录\","
            + "\"itemIconName\":\"chargebacks_record\"},"
            + "{\"itemName\":\"发票打印\","
            + "\"itemIconName\":\"invoice_printing\"}]},"
            + "{\"titleName\":\"保险\","
            + "\"itemBeamList\":[{\"itemName\":\"续保预约\","
            + "\"itemIconName\":\"more_renewal_order\"},"
            + "{\"itemName\":\"一键理赔\","
            + "\"itemIconName\":\"onekey_claim\"},"
            + "{\"itemName\":\"续保提醒\","
            + "\"itemIconName\":\"more_renewal_remind\"}]},"
            + "{\"titleName\":\"在线购车\","
            + "\"itemBeamList\":[{\"itemName\":\"底价购车\","
            + "\"itemIconName\":\"car_buying\"},"
            + "{\"itemName\":\"限时购\","
            + "\"itemIconName\":\"more_limited_time\"},"
            + "{\"itemName\":\"促销车\","
            + "\"itemIconName\":\"more_promotion_car\"},"
            + "{\"itemName\":\"汽车服务\","
            + "\"itemIconName\":\"more_car_service\"}]},"
            + "{\"titleName\":\"二手车\","
            + "\"itemBeamList\":[{\"itemName\":\"预约卖车\","
            + "\"itemIconName\":\"more_order_buycars\"},"
            + "{\"itemName\":\"二手车世界\","
            + "\"itemIconName\":\"more_usedcar_world\"}]},"
            + "{\"titleName\":\"客运\","
            + "\"itemBeamList\":[{\"itemName\":\"行程发布\","
            + "\"itemIconName\":\"travel_release\"},"
            + "{\"itemName\":\"行程订单接单\","
            + "\"itemIconName\":\"travel_order\"},"
            + "{\"itemName\":\"行程记录\","
            + "\"itemIconName\":\"travel_record\"},"
            + "{\"itemName\":\"投诉维权\","
            + "\"itemIconName\":\"complaints_rights\"}]},"
            + "{\"titleName\":\"社区服务\","
            + "\"itemBeamList\":[{\"itemName\":\"推荐服务\","
            + "\"itemIconName\":\"recommendation_service\"},"
            + "{\"itemName\":\"社区服务记录\","
            + "\"itemIconName\":\"community_service_record\"}]},"
            + "{\"titleName\":\"用车助手\","
            + "\"itemBeamList\":[{\"itemName\":\"违章查询\","
            + "\"itemIconName\":\"violation_query\"},"
            + "{\"itemName\":\"附近加油站\","
            + "\"itemIconName\":\"near_gas_station\"},"
            + "{\"itemName\":\"周边社区服务\","
            + "\"itemIconName\":\"peripheral_service\"},"
            + "{\"itemName\":\"附近停车场\","
            + "\"itemIconName\":\"near_park\"}]},"
            + "{\"titleName\":\"数据分析\","
            + "\"itemBeamList\":[{\"itemName\":\"数据分析\","
            + "\"itemIconName\":\"data_analysis\"}]}]";


    public static String getJsonData(Context context) {

        if (loginAuthority(context)) {
            return loginJsonStr;
        }
        return unLoginJsonStr;
    }

    private static boolean loginAuthority(Context context) {

        mFrameUserInfo = FrameEtongApplication.getApplication().getUserInfo();
        if (null != FrameEtongApplication.getApplication().getUserInfo()) {
            // 如果用户信息则从缓存中读取
            if (null != mFrameUserInfo) {
                if (null != mFrameUserInfo.getAuthority()) {
                    return true;
                }
            }
        }
        return false;
    }

//    private static FrameUserInfo login(Context context) {
//        mSharedPublisher = SharedPublisher.getInstance();
//        mSharedPublisher.initialize(context);
//
//        if (null == mFrameUserInfo) {
//            // 如果没有用户信息则从缓存中读取
//            String userShared = mSharedPublisher.getString(USER_SHARED);
//            if (null != userShared && !userShared.isEmpty())
//                mFrameUserInfo = JSON.parseObject(userShared, FrameUserInfo.class);
//        }
//        return mFrameUserInfo;
//    }
}
