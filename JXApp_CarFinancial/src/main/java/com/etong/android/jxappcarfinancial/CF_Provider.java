package com.etong.android.jxappcarfinancial;

import android.text.TextUtils;

import com.etong.android.frame.publisher.HttpMethod;
import com.etong.android.frame.publisher.HttpPublisher;
import com.etong.android.frame.publisher.SharedPublisher;
import com.etong.android.frame.request_init.FrameHttpTag;
import com.etong.android.frame.request_init.FrameHttpUri;
import com.etong.android.frame.user.FrameEtongApplication;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/8/16 0016.
 */
public class CF_Provider {

    private HttpPublisher mHttpPublisher;

    private static String TAG = "CF_Provider";
    private static CF_Provider instance;

    public static final String USER_SUCCEED = "PT_ERROR_SUCCESS";
    private static SharedPublisher mSharedPublisher = SharedPublisher.getInstance();

    private CF_Provider() {

    }

    public void initialize(HttpPublisher httpPublisher) {
        this.mHttpPublisher = httpPublisher;
    }

    public static CF_Provider getInstance() {
        if (null == instance) {
            instance = new CF_Provider();
        }
        return instance;
    }

    /**
     * @param f_ordertype 3 车贷申请，4 融资租赁申请，5 畅通钱包申请
     * @desc (车贷申请 融资申请 畅通钱包申请)
     * @createtime 2016/11/22 0022-10:57
     * @author wukefan
     */
    public void addCarPayOrderForApp(String f_phone, String f_orderman, String f_cardid, int f_ordertype, String f_remark) {

        Map<String, String> map = new HashMap<String, String>();
        map.put("f_phone", f_phone);
        map.put("f_orderman", f_orderman);
        map.put("f_ordertype", f_ordertype + "");

        if (null != f_cardid && (!TextUtils.isEmpty(f_cardid))) {
            map.put("f_cardid", f_cardid);
        }
        if (null != f_remark && f_remark != "" && !(TextUtils.isEmpty(f_remark))) {
            map.put("f_remark", f_remark);
        }

        HttpMethod method = new HttpMethod(FrameHttpUri.AddCarPayOrderForApp, map);
        mHttpPublisher.sendRequest(method, FrameHttpTag.ADD_CAR_ORDER_FOR_APP);
    }


    /**
     * @desc 查询金融机构接口
     * @createtime 2016/11/22 - 15:10
     * @author xiaoxue
     */
    public void QueryTheFinancial() {
        Map map = new HashMap<>();
        HttpMethod method = new HttpMethod(FrameHttpUri.QueryTheFinancial, map);
        mHttpPublisher.sendRequest(method, FrameHttpTag.QUERY_THE_FINANCIAL);
    }

    /**
     * @param f_ordertype 6 车辆撤销撤押
     * @desc (车辆撤销申请)
     * @createtime 2016/11/22 0022-15:27
     * @author wukefan
     */
    public void addCarDrawOrderForApp(String f_phone, String f_orderman, String f_cardid, int f_ordertype, String f_remark) {

        Map<String, String> map = new HashMap<String, String>();
        map.put("f_phone", f_phone);
        map.put("f_orderman", f_orderman);
        map.put("f_ordertype", f_ordertype + "");

        if (null != f_cardid && (!TextUtils.isEmpty(f_cardid))) {
            map.put("f_cardid", f_cardid);
        }
        if (null != f_remark && f_remark != "" && !(TextUtils.isEmpty(f_remark))) {
            map.put("f_remark", f_remark);
        }

        HttpMethod method = new HttpMethod(FrameHttpUri.AddCarDrawOrderForApp, map);
        mHttpPublisher.sendRequest(method, FrameHttpTag.ADD_CAR_ORDER_FOR_APP);
    }

    /**
     * @param f_ordertype 3 车贷申请，4 融资租赁申请，5 畅通钱包申请
     * @param f_type bxTotal   新车类订单
     *               newTotal  汽车金融订单
     *               financialTotal(默认)  ETC类订单
     *               etcTotal  保险类订单
     *               onlineTotal  在线购车订单
     *               secordTotal  二手车订单
     *               kyTotal   客运类订单
     *               sqTotal  社区服务类订单
     * @desc (申请进度)
     * @createtime 2016/11/22 0022-15:32
     * @author wukefan
     */
    public void addTotalOrderForApp(String f_type,int f_ordertype, int pageCurrent, int pageSize) {

        Map<String, String> map = new HashMap<String, String>();
        map.put("f_type", f_type);
        map.put("f_ordertype", f_ordertype + "");
        map.put("pageCurrent", pageCurrent + "");
        map.put("pageSize", pageSize + "");

        if (FrameEtongApplication.getApplication().isLogin()) {
            map.put("f_phone", FrameEtongApplication.getApplication().getUserInfo().getUserPhone());
            map.put("platformID", FrameEtongApplication.getApplication().getUserInfo().getPlatformID()+"");
        }

        HttpMethod method = new HttpMethod(FrameHttpUri.AddTotalOrderForApp, map);
        mHttpPublisher.sendRequest(method, FrameHttpTag.ADD_TOTAL_ORDER_FOR_APP);
    }

    /**
     * @desc (申请进度详情)
     * @createtime 2016/11/22 0022-15:38
     * @author wukefan
     */
    public void queryCarPayOrder(String f_id, int f_ordertype) {

        Map<String, String> map = new HashMap<String, String>();
        map.put("f_id", f_id);
        map.put("f_ordertype", f_ordertype + "");

        HttpMethod method = new HttpMethod(FrameHttpUri.QueryCarPayOrder, map);
        mHttpPublisher.sendRequest(method, FrameHttpTag.QUERY_CAR_PAY_ORDER);
    }

    /**
     * @param f_ordertype 0车贷申请，1融资租赁申请，2畅通钱包申请，3车辆撤销撤押申请
     * @desc (绑定金融账号)
     * @createtime 2016/11/22 0022-15:52
     * @author wukefan
     */
    public void bindFinancialAgent(String f_phone, String f_name, String f_cardId, int f_institutId, int f_ordertype) {


        Map<String, String> map = new HashMap<String, String>();
        map.put("platformID", FrameEtongApplication.getApplication().getUserInfo().getPlatformID() + "");
        map.put("f_phone", f_phone);
        map.put("f_name", f_name);
        map.put("f_cardId", f_cardId);
        map.put("f_institutId", f_institutId + "");
        map.put("f_ordertype", f_ordertype + "");

        HttpMethod method = new HttpMethod(FrameHttpUri.BindFinancialAgent, map);
        mHttpPublisher.sendRequest(method, FrameHttpTag.BIND_FINANCIAL_AGENT);
    }

    /**
     * @desc (贷款列表查询接口)
     * @createtime 2016/11/24 - 17:06
     * @author xiaoxue
     */
    public void loanList() {
        Map<String, String> map = new HashMap<String, String>();
        if (FrameEtongApplication.getApplication().isLogin()) {
            if (null != FrameEtongApplication.getApplication().getUserInfo().getFcustid() &&
                    !TextUtils.isEmpty(FrameEtongApplication.getApplication().getUserInfo().getFcustid())) {
                map.put("fcustid",FrameEtongApplication.getApplication().getUserInfo().getFcustid());
            }
        }
        HttpMethod method = new HttpMethod(FrameHttpUri.LOANLIST, map);
        mHttpPublisher.sendRequest(method, FrameHttpTag.LOANLIST);
    }

    /**
     * @desc (还款列表查询)
     * @createtime 2016/11/24 - 20:03
     * @author xiaoxue
     */
    public void repaymentList() {
        Map<String, String> map = new HashMap<String, String>();
        if (FrameEtongApplication.getApplication().isLogin()) {
            if (null != FrameEtongApplication.getApplication().getUserInfo().getFcustid() &&
                    !TextUtils.isEmpty(FrameEtongApplication.getApplication().getUserInfo().getFcustid())) {
                map.put("fcustid",FrameEtongApplication.getApplication().getUserInfo().getFcustid());
            }
        }
        HttpMethod method = new HttpMethod(FrameHttpUri.REPAYMENTLIST, map);
        mHttpPublisher.sendRequest(method, FrameHttpTag.REPAYMENTLIST);
    }

    /**
     * @desc (逾期列表查询)
     * @createtime 2016/11/28 - 18:40
     * @author xiaoxue
     */
    public void overdueList() {
        Map<String, String> map = new HashMap<String, String>();
        if (FrameEtongApplication.getApplication().isLogin()) {
            if (null != FrameEtongApplication.getApplication().getUserInfo().getFcustid() &&
                    !TextUtils.isEmpty(FrameEtongApplication.getApplication().getUserInfo().getFcustid())) {
                map.put("fcustid",FrameEtongApplication.getApplication().getUserInfo().getFcustid());
            }
        }
        HttpMethod method = new HttpMethod(FrameHttpUri.OVERDUELIST, map);
        mHttpPublisher.sendRequest(method, FrameHttpTag.OVERDUELIST);
    }

    /**
     * @desc (订单记录)
     * @createtime 2016/12/2 0002-18:17
     * @author wukefan
     */
    public void queryMyOrder() {
        Map<String, String> map = new HashMap<String, String>();
        if (FrameEtongApplication.getApplication().isLogin()) {
            map.put("f_phone", FrameEtongApplication.getApplication().getUserInfo().getUserPhone());
            map.put("platformID", FrameEtongApplication.getApplication().getUserInfo().getPlatformID()+"");
        }
        HttpMethod method = new HttpMethod(FrameHttpUri.QueryMyOrder, map);
        mHttpPublisher.sendRequest(method, FrameHttpTag.QUERY_MY_ORDER);
    }

}


