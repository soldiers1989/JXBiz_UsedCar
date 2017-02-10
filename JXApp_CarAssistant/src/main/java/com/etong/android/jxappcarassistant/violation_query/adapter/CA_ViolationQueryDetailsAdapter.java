package com.etong.android.jxappcarassistant.violation_query.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.etong.android.jxappcarassistant.R;
import com.etong.android.jxappcarassistant.violation_query.bean.CA_ViolationQueryBean;
import com.etong.android.jxappcarassistant.violation_query.bean.CA_ViolationQueryDetailsBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @desc (违章结果详情界面)
 * @createtime 2016/11/23 0023--17:37
 * @Created by wukefan.
 */
public class CA_ViolationQueryDetailsAdapter extends BaseAdapter {

    private Context mContext;
    private CA_ViolationQueryBean.ListBean mAllDatas;
    private List<CA_ViolationQueryDetailsBean> mListDatas;

    public CA_ViolationQueryDetailsAdapter(Context context) {
        this.mContext = context;
        this.mListDatas = new ArrayList();
    }

    /**
     * @desc (更新设置数据)
     */
    public void updateListDatas(CA_ViolationQueryBean.ListBean datas) {
        this.mAllDatas = datas;
        generateListData();
        notifyDataSetChanged();
    }

    /**
     * @desc (设置数据到适配器的list中)
     */
    private void generateListData() {
        CA_ViolationQueryDetailsBean oneLine = new CA_ViolationQueryDetailsBean("车辆所有人:", "未知", true, "违法代码:", setNullData(mAllDatas.getLegalnum()));
        mListDatas.add(oneLine);
        CA_ViolationQueryDetailsBean towLine = new CA_ViolationQueryDetailsBean("违 法 时 间:", mAllDatas.getTime(), false, null, null);
        mListDatas.add(towLine);
        CA_ViolationQueryDetailsBean threeLine = new CA_ViolationQueryDetailsBean("违 法 记 分:", mAllDatas.getScore() + "分", true, "罚款金额:", mAllDatas.getPrice());
        mListDatas.add(threeLine);
        CA_ViolationQueryDetailsBean fourLine = new CA_ViolationQueryDetailsBean("发现机关名称:", setNullData(mAllDatas.getAgency()), false, null, null);
        mListDatas.add(fourLine);
        CA_ViolationQueryDetailsBean fiveLine = new CA_ViolationQueryDetailsBean("违 法 地 址:", mAllDatas.getAddress(), false, null, null);
        mListDatas.add(fiveLine);
        CA_ViolationQueryDetailsBean sixLine = new CA_ViolationQueryDetailsBean("违法行为描述:", mAllDatas.getContent(), false, null, null);
        mListDatas.add(sixLine);
    }

    @Override
    public int getCount() {
        return mListDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mListDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(mContext).inflate(R.layout.ca_violation_query_details_items_list, null);

        ViewGroup rightView = (ViewGroup) convertView.findViewById(R.id.ca_vq_details_ll_right);

        TextView leftTitleName = (TextView) convertView.findViewById(R.id.ca_vq_details_txt_left_title);
        TextView leftContent = (TextView) convertView.findViewById(R.id.ca_vq_details_txt_left_content);
        TextView rightTitleName = (TextView) convertView.findViewById(R.id.ca_vq_details_txt_right_title);
        TextView rightContent = (TextView) convertView.findViewById(R.id.ca_vq_details_txt_right_content);

        leftTitleName.setText(mListDatas.get(position).getLeftTitle());        //左边的title
        leftContent.setText(mListDatas.get(position).getLeftContent());        //左边的内容

        //如果有右边显示右边布局否则隐藏右边布局
        if (mListDatas.get(position).isHasRight()) {
            rightView.setVisibility(View.VISIBLE);
            rightTitleName.setText(mListDatas.get(position).getRightTitle());  //右边的title
            rightContent.setText(mListDatas.get(position).getRightContent());  //右边的内容
        } else {
            rightView.setVisibility(View.GONE);
        }

        if (position == 2) {//如果是第三行设置内容颜色为橘色
            leftContent.setTextColor(Color.parseColor("#ff6600"));
            rightContent.setTextColor(Color.parseColor("#ff6600"));
        }

        return convertView;
    }

    /**
     * @desc (设置为空的字段显示)
     */
    private String setNullData(String data) {
        if (null == data || "".equals(data) || TextUtils.isEmpty(data)) {
            data = "— —";
        }
        return data;
    }
}
