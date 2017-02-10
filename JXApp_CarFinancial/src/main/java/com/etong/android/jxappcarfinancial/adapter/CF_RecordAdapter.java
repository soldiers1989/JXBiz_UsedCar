package com.etong.android.jxappcarfinancial.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.etong.android.frame.utils.AddCommaToMoney;
import com.etong.android.jxappcarfinancial.R;
import com.etong.android.jxappcarfinancial.activity.CF_LoanDocumentationDetailsActivity;
import com.etong.android.jxappcarfinancial.activity.CF_RecordDetailsActivity;
import com.etong.android.jxappcarfinancial.bean.CF_LoanListBean;
import com.etong.android.jxappcarfinancial.bean.CF_OverdueBean;
import com.etong.android.jxappcarfinancial.bean.CF_RecordDetailsBean;
import java.util.ArrayList;
import java.util.List;

/**
 * @desc 记录adapter
 * @createtime 2016/11/17 - 16:38
 * @Created by xiaoxue.
 */

public class CF_RecordAdapter extends BaseAdapter {
    private Context mContext;
    private int flag = -1;
    private List<CF_LoanListBean> mLoanDatas;
    private List<CF_RecordDetailsBean> mRepayDatas;
    private List<CF_OverdueBean> mOverdueDatas;
    private String fRepayTime;


    public CF_RecordAdapter(Context mContext) {
        this.mContext = mContext;

        // 初始化List，让程序不奔溃
        mLoanDatas = new ArrayList<>();
        mRepayDatas = new ArrayList<>();
    }

    /**
     * @desc (贷款)
     * @createtime 2016/11/28 - 19:03
     * @author xiaoxue
     */
    public void updateListDatasLoan(List<CF_LoanListBean> datas, int flag) {
        this.mLoanDatas = datas;
        this.flag = flag;
        notifyDataSetChanged();
    }

    /**
     * @desc (还款)
     * @createtime 2016/11/28 - 19:03
     * @author xiaoxue
     */
    public void updateListDatasRepay(List<CF_RecordDetailsBean> datas, int flag) {
        this.mRepayDatas = datas;
        this.flag = flag;
        notifyDataSetChanged();
    }

    /**
     * @desc (逾期)
     * @createtime 2016/11/29 - 18:43
     * @author xiaoxue
     */
    public void updateListDatasOverdue(List<CF_OverdueBean> datas, int flag) {
        this.mOverdueDatas = datas;
        this.flag = flag;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        int size = 0;
        switch (flag) {
            case 0:
                size = mLoanDatas.size();
                break;
            case 1:
                size = mRepayDatas.size();
                break;
            case 2:
                size = mOverdueDatas.size();
                break;
            default:
                size = 0;
                break;
        }
        return size;
    }

    @Override
    public Object getItem(int position) {
        Object o = null;
        switch (flag) {
            case 0:
                o = mLoanDatas.get(position);
                break;
            case 1:
                o = mRepayDatas.get(position);
                break;
            case 2:
                o = mOverdueDatas.get(position);
                break;
        }
        return o;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        CF_RecordViewHolder viewHolder = null;
        if (null == convertView) {
            viewHolder = new CF_RecordViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.cf_activity_record_item, null);
            viewHolder.cf_txt_number = (TextView) convertView.findViewById(R.id.cf_txt_number);
            viewHolder.cf_txt_applytime = (TextView) convertView.findViewById(R.id.cf_txt_applytime);
            viewHolder.cf_txt_loantime = (TextView) convertView.findViewById(R.id.cf_txt_loantime);
            viewHolder.cf_txt_money = (TextView) convertView.findViewById(R.id.cf_txt_money);
            viewHolder.cf_txt_overdue_detail = (TextView) convertView.findViewById(R.id.cf_txt_overdue_detail);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (CF_RecordViewHolder) convertView.getTag();
        }
        //不是两位数的就加0
        if (position < 10) {
            viewHolder.cf_txt_number.setText("0" + (position + 1));
        } else {
            viewHolder.cf_txt_number.setText(position + 1 + "");
        }
        String fRepayTime = "";     // 申请时间
        String lRepayTime = "";     // 放款时间
        int loanAcount = 0;        // 金额
        String detail = "";         //记录的内容
        switch (flag) {
            case 0:
                fRepayTime = mLoanDatas.get(position).getSQdate();
                lRepayTime = mLoanDatas.get(position).getFKdate();
                loanAcount = mLoanDatas.get(position).getLoanAmount();
                detail = mLoanDatas.get(position).getJDStatus();
                viewHolder.cf_txt_applytime.setText(fRepayTime);
                //放款时间为空就--
                if ("".equals(lRepayTime) && TextUtils.isEmpty(lRepayTime)) {
                    viewHolder.cf_txt_loantime.setText("——");
                } else {
                    viewHolder.cf_txt_loantime.setText(lRepayTime);
                }
                if(!TextUtils.isEmpty(lRepayTime) && lRepayTime.equals("--")){
                    viewHolder.cf_txt_loantime.setText("——");
                }
                viewHolder.cf_txt_money.setText("¥" + AddCommaToMoney.AddCommaToMoney(String.valueOf(loanAcount)));//放款金额
                if ("".equals(detail) && TextUtils.isEmpty(detail)) {
                    viewHolder.cf_txt_overdue_detail.setText("——");
                } else {
                    viewHolder.cf_txt_overdue_detail.setText(detail);
                }
                break;
            case 1:
                fRepayTime = mRepayDatas.get(position).getSQdate();
                lRepayTime = mRepayDatas.get(position).getFKdate();
                loanAcount = Integer.valueOf(mRepayDatas.get(position).getFPerRevSum());

                viewHolder.cf_txt_applytime.setText(fRepayTime);
                //放款时间为空就--
                if ("".equals(lRepayTime) && TextUtils.isEmpty(lRepayTime)) {
                    viewHolder.cf_txt_loantime.setText("——");
                } else {
                    viewHolder.cf_txt_loantime.setText(lRepayTime);
                }
                if(!TextUtils.isEmpty(lRepayTime) && lRepayTime.equals("--")){
                    viewHolder.cf_txt_loantime.setText("——");
                }
                viewHolder.cf_txt_money.setText("¥" + AddCommaToMoney.AddCommaToMoney(String.valueOf(loanAcount)));//放款金额
                viewHolder.cf_txt_overdue_detail.setText("总" + mRepayDatas.get(position).getFPeriodTotal() + "期,已还" + mRepayDatas.get(position).getRepaySum() + "期");
                break;
            case 2:
                fRepayTime = mOverdueDatas.get(position).getSQdate();
                lRepayTime = mOverdueDatas.get(position).getFKdate();
                loanAcount = Integer.valueOf(mOverdueDatas.get(position).getFPerRevSum());
                viewHolder.cf_txt_applytime.setText(fRepayTime);
                if ("".equals(lRepayTime) && TextUtils.isEmpty(lRepayTime)) {
                    viewHolder.cf_txt_loantime.setText("——");
                } else {
                    viewHolder.cf_txt_loantime.setText(lRepayTime);
                }
                if(!TextUtils.isEmpty(lRepayTime) && lRepayTime.equals("--")){
                    viewHolder.cf_txt_loantime.setText("——");
                }
                viewHolder.cf_txt_money.setText("¥" + AddCommaToMoney.AddCommaToMoney(String.valueOf(loanAcount)));//放款金额
                viewHolder.cf_txt_overdue_detail.setText("总" + mOverdueDatas.get(position).getFPeriodTotal() + "期,历史逾期" + mOverdueDatas.get(position).getOverdueTotal() + "期");
                break;
        }

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                switch (flag) {
                    case 0:
                        intent.setClass(mContext, CF_LoanDocumentationDetailsActivity.class);//到贷款详情
                        intent.putExtra("CF_LoanListBean", mLoanDatas.get(position));
                        break;
                    case 1:
                        intent.setClass(mContext, CF_RecordDetailsActivity.class);//到明细
                        intent.putExtra("flag", flag);
                        intent.putExtra("CF_RecordDetailsBean", mRepayDatas.get(position));
                        break;
                    case 2:
                        intent.setClass(mContext, CF_RecordDetailsActivity.class);
                        intent.putExtra("CF_OverdueBean", mOverdueDatas.get(position));
                        intent.putExtra("flag", flag);
                        break;
                }
                mContext.startActivity(intent);
            }
        });

        return convertView;
    }

    private static class CF_RecordViewHolder {
        public TextView cf_txt_number;
        public TextView cf_txt_applytime;
        public TextView cf_txt_loantime;
        public TextView cf_txt_money;
        public TextView cf_txt_overdue_detail;
    }
}
