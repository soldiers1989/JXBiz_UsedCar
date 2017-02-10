package com.etong.android.jxbizusedcar.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.etong.android.frame.publisher.HttpPublisher;
import com.etong.android.frame.user.UC_UserProvider;
import com.etong.android.frame.utils.ActivitySkipUtil;
import com.etong.android.frame.utils.DisplayUtil;
import com.etong.android.frame.utils.EtongCommonUtils;
import com.etong.android.frame.utils.L;
import com.etong.android.jxbizusedcar.Interface.UC_InterfaceStatus;
import com.etong.android.jxbizusedcar.R;
import com.etong.android.jxbizusedcar.activity.UC_AdditionalInformationActivity;
import com.etong.android.jxbizusedcar.activity.UC_CancelOrderActivity;
import com.etong.android.jxbizusedcar.activity.UC_CarIdentifyReportActivity;
import com.etong.android.jxbizusedcar.activity.UC_PaywayPopuwindow;
import com.etong.android.jxbizusedcar.bean.UC_Identify_OrderListBean;

import java.util.ArrayList;
import java.util.List;


/**
 * @desc (车鉴定订单适配器)
 * @createtime 2016/11/9 0009--14:56
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
public class UC_Identify_OrderAdapter extends BaseAdapter implements UC_InterfaceStatus {

    private Context mContext;
    private List<UC_Identify_OrderListBean> mData;
    //    private static final String BLACK = "black";
    private static final String RED = "red";
    private static final String GREY = "grey";
    private UC_UserProvider mUsedCarProvider;
    private int tag;//判断四个fragment的tag

    /**
     * 构造方法
     *
     * @param context
     */
    public UC_Identify_OrderAdapter(Context context, int tag) {
        this.mContext = context;
        this.tag = tag;
        mData = new ArrayList<UC_Identify_OrderListBean>();
        mUsedCarProvider = UC_UserProvider.getInstance();
        mUsedCarProvider.initalize(HttpPublisher.getInstance());
    }

    /**
     * @param
     * @return
     * @desc (对外公布的更新ListView数据的方法)
     */
    public void updateOrderList(List<UC_Identify_OrderListBean> data) {
        this.mData = data;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        UC_Identify_OrderHolder viewHolder = null;
        if (null == convertView) {
            viewHolder = new UC_Identify_OrderHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.uc_identify_order_list_items, null);
            viewHolder.orderNumber = (TextView) convertView.findViewById(R.id.uc_identify_order_number);                    //订单编号
            viewHolder.orderState = (TextView) convertView.findViewById(R.id.uc_identify_order_state);                      //订单状态
            viewHolder.orderContent = (TextView) convertView.findViewById(R.id.uc_identify_order_txt_identify_content);     //订单内容（VIN码、时间）
            viewHolder.orderMoney = (TextView) convertView.findViewById(R.id.uc_identify_order_money);                      //订单金额
            viewHolder.orderLeftBtn = (Button) convertView.findViewById(R.id.uc_identify_order_btn_left);                   //左边按钮
            viewHolder.orderRightBtn = (Button) convertView.findViewById(R.id.uc_identify_order_btn_right);                 //右边按钮
            viewHolder.orderAddBtn = (Button) convertView.findViewById(R.id.uc_identify_order_btn_add);                     //补录信息按钮
            viewHolder.orderAnnotation = (TextView) convertView.findViewById(R.id.uc_identify_order_txt_annotation);        //注释信息

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (UC_Identify_OrderHolder) convertView.getTag();
        }

        /**订单内容设置**/
        String vinCode = mData.get(position).getF_vin();
//        String time = null;
//        if (null != mData.get(position).getF_createtime()) {
//            time = DateUtils.getDateStringFromSecondsNor(mData.get(position).getF_createtime() / 1000);
//        } else {
//            time = mData.get(position).getF_updatetime();
//        }
        String time = mData.get(position).getF_createtime();
        String textContent = String.format("车鉴定\nVIN码：%1$s\n%2$s", vinCode, time);
        int change = textContent.lastIndexOf("\n");
        SpannableStringBuilder style = new SpannableStringBuilder(textContent);
        style.setSpan(new AbsoluteSizeSpan(DisplayUtil.dip2px(mContext, 10)), change + 1, textContent.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);//字号
        style.setSpan(new ForegroundColorSpan(Color.parseColor("#c9caca")), change + 1, textContent.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);//颜色
        viewHolder.orderContent.setText(style);

        /**订单编号设置**/
        viewHolder.orderNumber.setText("订单编号：" + mData.get(position).getF_cid());
        /**初始状态：补录信息按钮设置隐藏，左右按钮设置显示**/
        viewHolder.orderAddBtn.setVisibility(View.GONE);
        viewHolder.orderLeftBtn.setVisibility(View.VISIBLE);
        viewHolder.orderRightBtn.setVisibility(View.VISIBLE);
        viewHolder.orderAnnotation.setVisibility(View.GONE);
        Double payamt = Double.valueOf(mData.get(position).getF_payamt());

        switch (mData.get(position).getF_status()) {
            /**待支付**/
            case WAITING_FOR_PAY:
                setBtn(viewHolder.orderLeftBtn, RED, "取消订单");
                setBtn(viewHolder.orderRightBtn, RED, "付款");
                //订单金额
                viewHolder.orderMoney.setText(payamt + "元");
                viewHolder.orderMoney.setTextColor(mContext.getResources().getColor(R.color.used_car_cf1c36));
                //订单状态
                viewHolder.orderState.setText("待付款");
                viewHolder.orderState.setTextColor(mContext.getResources().getColor(R.color.uc_ffa13f));
                break;
            /**支付成功，补录信息（车牌号）**/
            case PAY_SUCCESS_NUMBER_PLATE:
                viewHolder.orderAddBtn.setVisibility(View.VISIBLE);
                setBtn(viewHolder.orderLeftBtn, GREY, "退款");
                setBtn(viewHolder.orderRightBtn, GREY, "查看报告");
                //订单金额
                viewHolder.orderMoney.setText(payamt + "元");
                viewHolder.orderMoney.setTextColor(mContext.getResources().getColor(R.color.used_car_cf1c36));
                //订单状态
                viewHolder.orderState.setText("补录信息");
                viewHolder.orderState.setTextColor(mContext.getResources().getColor(R.color.uc_ffa13f));
                break;
            /**支付成功，补录信息（发动机号）**/
            case PAY_SUCCESS_ENGINE_NUMBER:
                viewHolder.orderAddBtn.setVisibility(View.VISIBLE);
                setBtn(viewHolder.orderLeftBtn, GREY, "退款");
                setBtn(viewHolder.orderRightBtn, GREY, "查看报告");
                //订单金额
                viewHolder.orderMoney.setText(payamt + "元");
                viewHolder.orderMoney.setTextColor(mContext.getResources().getColor(R.color.used_car_cf1c36));
                //订单状态
                viewHolder.orderState.setText("补录信息");
                viewHolder.orderState.setTextColor(mContext.getResources().getColor(R.color.uc_ffa13f));
                break;
            /**支付成功，补录信息（车牌号&发动机号）**/
            case PAY_SUCCESS_ENGINE_PLATE:
                viewHolder.orderAddBtn.setVisibility(View.VISIBLE);
                setBtn(viewHolder.orderLeftBtn, GREY, "退款");
                setBtn(viewHolder.orderRightBtn, GREY, "查看报告");
                //订单金额
                viewHolder.orderMoney.setText(payamt + "元");
                viewHolder.orderMoney.setTextColor(mContext.getResources().getColor(R.color.used_car_cf1c36));
                //订单状态
                viewHolder.orderState.setText("补录信息");
                viewHolder.orderState.setTextColor(mContext.getResources().getColor(R.color.uc_ffa13f));
                break;
            /**购买报告成功，等待回调**/
            case WAITING_FOR_REPORT:
                viewHolder.orderLeftBtn.setVisibility(View.GONE);
                setBtn(viewHolder.orderRightBtn, GREY, "查看报告");
                //订单金额
                viewHolder.orderMoney.setText(payamt + "元");
                viewHolder.orderMoney.setTextColor(mContext.getResources().getColor(R.color.used_car_cf1c36));
                //订单状态
                viewHolder.orderState.setText("等待报告");
                viewHolder.orderState.setTextColor(mContext.getResources().getColor(R.color.uc_ffa13f));
                break;
            /**报告无维修记录，等待退款（退款中）**/
            case REPORT_ERROR:
                viewHolder.orderLeftBtn.setVisibility(View.GONE);
                setBtn(viewHolder.orderRightBtn, GREY, "退款");
                //订单金额
                viewHolder.orderMoney.setText(payamt + "元");
                viewHolder.orderMoney.setTextColor(mContext.getResources().getColor(R.color.uc_33cc73));
                //订单状态
                viewHolder.orderState.setText("退款中");
                viewHolder.orderState.setTextColor(mContext.getResources().getColor(R.color.uc_ffa13f));
                //注释信息
                viewHolder.orderAnnotation.setVisibility(View.VISIBLE);
                viewHolder.orderAnnotation.setText("因某某原因退款。");
                break;
            /**订单完成**/
            case ORDER_COMPLETE:
                viewHolder.orderLeftBtn.setVisibility(View.GONE);
                setBtn(viewHolder.orderRightBtn, RED, "查看报告");
                //订单金额
                viewHolder.orderMoney.setText(payamt + "元");
                viewHolder.orderMoney.setTextColor(mContext.getResources().getColor(R.color.used_car_cf1c36));
                //订单状态
                viewHolder.orderState.setText("已完成");
                viewHolder.orderState.setTextColor(mContext.getResources().getColor(R.color.uc_c9caca));
                break;
            /**订单取消**/
            case ORDER_CANCEL:
                viewHolder.orderLeftBtn.setVisibility(View.GONE);
                viewHolder.orderRightBtn.setVisibility(View.GONE);
                //订单金额
                viewHolder.orderMoney.setText(payamt + "元");
                viewHolder.orderMoney.setTextColor(mContext.getResources().getColor(R.color.used_car_cf1c36));
                //订单状态
                viewHolder.orderState.setText("已取消");
                viewHolder.orderState.setTextColor(mContext.getResources().getColor(R.color.uc_c9caca));
                break;
            /**退款成功**/
            case REFUND_SUCCESS:
                viewHolder.orderLeftBtn.setVisibility(View.GONE);
                viewHolder.orderRightBtn.setVisibility(View.GONE);
                //订单金额
                viewHolder.orderMoney.setText(payamt + "元");
                viewHolder.orderMoney.setTextColor(mContext.getResources().getColor(R.color.uc_33cc73));
                //订单状态
                viewHolder.orderState.setText("退款成功");
                viewHolder.orderState.setTextColor(mContext.getResources().getColor(R.color.uc_3e3a39));
                //注释信息
                viewHolder.orderAnnotation.setVisibility(View.VISIBLE);
                viewHolder.orderAnnotation.setText("车辆信息暂时查询不到，已退款！");
                break;
            /**退款失败**/
            case REFUND_FAILED:
                viewHolder.orderLeftBtn.setVisibility(View.GONE);
                setBtn(viewHolder.orderRightBtn, RED, "再次退款");
                //订单金额
                viewHolder.orderMoney.setText(payamt + "元");
                viewHolder.orderMoney.setTextColor(mContext.getResources().getColor(R.color.uc_33cc73));
                //订单状态
                viewHolder.orderState.setText("退款失败");
                viewHolder.orderState.setTextColor(mContext.getResources().getColor(R.color.uc_ffa13f));
                //注释信息
                viewHolder.orderAnnotation.setVisibility(View.VISIBLE);
                viewHolder.orderAnnotation.setText("退款失败，请再次退款！");
                break;
            /**已支付，等待报告**/
            case PAY_SUCCESS_FOR_WAIT:
                viewHolder.orderLeftBtn.setVisibility(View.GONE);
                setBtn(viewHolder.orderRightBtn, GREY, "查看报告");
                //订单金额
                viewHolder.orderMoney.setText(payamt + "元");
                viewHolder.orderMoney.setTextColor(mContext.getResources().getColor(R.color.used_car_cf1c36));
                //订单状态
                viewHolder.orderState.setText("等待报告");
                viewHolder.orderState.setTextColor(mContext.getResources().getColor(R.color.uc_ffa13f));
                break;
            /**支付成功，vin码相同不要重新支付**/
            case PAY_SUCCESS_NO_SAME:
                viewHolder.orderLeftBtn.setVisibility(View.GONE);
                setBtn(viewHolder.orderRightBtn, GREY, "查看报告");
                //订单金额
                viewHolder.orderMoney.setText(payamt + "元");
                viewHolder.orderMoney.setTextColor(mContext.getResources().getColor(R.color.used_car_cf1c36));
                //订单状态
                viewHolder.orderState.setText("等待报告");
                viewHolder.orderState.setTextColor(mContext.getResources().getColor(R.color.uc_ffa13f));
                break;
            default:
                viewHolder.orderLeftBtn.setVisibility(View.GONE);
                viewHolder.orderRightBtn.setVisibility(View.GONE);
                break;
        }

        viewHolder.orderLeftBtn.setOnClickListener(new View.OnClickListener() {
                                                       @Override
                                                       public void onClick(View view) {
                                                           if (!EtongCommonUtils.isFastDoubleClick()) {
                                                               switch (mData.get(position).getF_status()) {
                                                                   /**待支付 - 取消订单**/
                                                                   case WAITING_FOR_PAY:
//                        DefinedToast.showToast(mContext, "待支付 - 取消订单", 0);
                                                                       //跳转到取消订单的activity里
                                                                       Intent intent = new Intent(mContext, UC_CancelOrderActivity.class);
                                                                       intent.putExtra("f_cid", mData.get(position).getF_cid());
                                                                       intent.putExtra("tag", tag);
                                                                       mContext.startActivity(intent);
                                                                       break;
                                                               }
                                                           }
                                                       }
                                                   }
        );

        viewHolder.orderRightBtn.setOnClickListener(new View.OnClickListener()

                                                    {
                                                        @Override
                                                        public void onClick(View view) {
                                                            if (!EtongCommonUtils.isFastDoubleClick()) {
                                                                switch (mData.get(position).getF_status()) {
                                                                    /**待支付 - 付款**/
                                                                    case WAITING_FOR_PAY:
//                        DefinedToast.showToast(mContext, "待支付 - 付款", 0);
                                                                        //跳转到订单详情的activity里
                                                                        Intent intent = new Intent(mContext, UC_PaywayPopuwindow.class);
                                                                        intent.putExtra("UC_Identify_OrderListBean", mData.get(position));
                                                                        mContext.startActivity(intent);
                                                                        break;
                                                                    /**订单完成 - 查看报告**/
                                                                    case ORDER_COMPLETE:
                                                                        // 将获取到的 cid 订单号传送到显示报告的界面
                                                                        Bundle bundle = new Bundle();
                                                                        bundle.putString(UC_CarIdentifyReportActivity.CAR_IDENTIFY_SHOW_CONTENT,
                                                                                (mData.get(position)).getF_reporturl());
                                                                        // 跳转到获取报告的界面
                                                                        ActivitySkipUtil.skipActivity((Activity) mContext, UC_CarIdentifyReportActivity.class, bundle);
                                                                        break;
                                                                    /**退款失败 - 再次退款**/
                                                                    case REFUND_FAILED:
//                        DefinedToast.showToast(mContext, "退款失败 - 再次退款", 0);
                                                                        //请求退款方法
                                                                        mUsedCarProvider.refundAmt(mData.get(position).getF_cid());
                                                                        break;
                                                                }
                                                            }
                                                        }
                                                    }

        );

        viewHolder.orderAddBtn.setOnClickListener(new View.OnClickListener()

                                                  {
                                                      @Override
                                                      public void onClick(View view) {
                                                          if (!EtongCommonUtils.isFastDoubleClick()) {
                                                              Intent intent = new Intent(mContext, UC_AdditionalInformationActivity.class);
                                                              intent.putExtra("UC_Identify_OrderListBean", mData.get(position));
                                                              intent.putExtra("tag", tag);
                                                              mContext.startActivity(intent);
                                                          }
                                                      }
                                                  }

        );

        convertView.setOnClickListener(new View.OnClickListener()

                                       {
                                           @Override
                                           public void onClick(View view) {
                                               L.d("跳到详情页~");
                                           }
                                       }

        );
        return convertView;
    }


    private static class UC_Identify_OrderHolder {

        public TextView orderNumber;
        public TextView orderState;
        public TextView orderContent;
        public TextView orderMoney;
        public Button orderLeftBtn;
        public Button orderRightBtn;
        public Button orderAddBtn;
        public TextView orderAnnotation;
    }

    /**
     * @param btn     按钮
     * @param color   颜色
     * @param content 文字
     * @desc (设置按钮的样式、颜色和文字)
     */

    private void setBtn(Button btn, String color, String content) {
        if (null != content) {
            btn.setText(content);
        }
        if (color.equals(RED)) {
            btn.setBackgroundResource(R.drawable.uc_identify_order_hollow_button_red);
            btn.setTextColor(mContext.getResources().getColor(R.color.used_car_cf1c36));
            return;
        }
        if (color.equals(GREY)) {
            btn.setBackgroundResource(R.drawable.uc_identify_order_hollow_button_grey);
            btn.setTextColor(mContext.getResources().getColor(R.color.uc_c9caca));
            return;
        }
//        if (color.equals(BLACK)) {
//            btn.setBackgroundResource(R.drawable.uc_identify_order_hollow_button_black);
//            btn.setTextColor(mContext.getResources().getColor(R.color.uc_3e3a39));
//            return;
//        }
    }


}
