package com.etong.android.jxbizusedcar.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.etong.android.jxbizusedcar.R;
import com.etong.android.jxbizusedcar.bean.UC_MessageItemBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 二手车的筛选主界面的ListView里面的GridView适配器
 * Created by Administrator on 2016/10/24 0009.
 */
public class UC_MessageGridAdapter extends BaseAdapter {
    private Context context;
    private List<UC_MessageItemBean> items;
    private UC_ItemCallBack iCallBack;

    public UC_MessageGridAdapter(Context context, UC_ItemCallBack iCallBack) {
        this.iCallBack = iCallBack;
        this.context = context;

        //设置数据
        items = new ArrayList<>();
        UC_MessageItemBean recommendedBean = new UC_MessageItemBean("每日推荐资讯", "uc_recommended_daily", "#fd513c");
        items.add(recommendedBean);
        UC_MessageItemBean preferentialBean = new UC_MessageItemBean("活动优惠资讯", "uc_preferential_activities", "#e8950c");
        items.add(preferentialBean);
        UC_MessageItemBean noticeBean = new UC_MessageItemBean("园区拍卖公告", "uc_the_auction_notice", "#fd513c");
        items.add(noticeBean);
        UC_MessageItemBean communityBean = new UC_MessageItemBean("社区资讯", "uc_community_information", "#fd513c");
        items.add(communityBean);
        UC_MessageItemBean hospitalBean = new UC_MessageItemBean("汽车医院资讯", "uc_auto_hospital_information", "#4a90e2");
        items.add(hospitalBean);
        UC_MessageItemBean inancialBean = new UC_MessageItemBean("金融资讯", "uc_inancial_information", "#38b785");
        items.add(inancialBean);
        UC_MessageItemBean transferBean = new UC_MessageItemBean("过户政策资讯", "uc_transfer_policy_guild_regulations", "#e8950c");
        items.add(transferBean);
        UC_MessageItemBean qandaBean = new UC_MessageItemBean("二手车问答", "uc_questions_and_answers", "#fd513c");
        items.add(qandaBean);
        UC_MessageItemBean otherBean = new UC_MessageItemBean("其他资讯", "uc_other_information", "#fd513c");
        items.add(otherBean);
    }


    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        convertView = LayoutInflater.from(context).inflate(R.layout.uc_message_items_gv, null);

        ImageView titleImg = (ImageView) convertView.findViewById(R.id.uc_message_item_img);
        TextView titleName = (TextView) convertView.findViewById(R.id.uc_message_item_txt);

        titleImg.setBackgroundResource(getImageIdByName(items.get(position).getTitleImg()));
        titleName.setText(items.get(position).getTitleName());
        titleName.setTextColor(Color.parseColor(items.get(position).getColor()));

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iCallBack.answerItem(items.get(position).getTitleName());
            }
        });

        return convertView;
    }


    //回调接口
    public interface UC_ItemCallBack {
        public void answerItem(String titleName);  //类B的内部接口
    }

    /**
     * 根据图片名称获取R.java中对应的id
     *
     * @param name
     * @return
     */

    public static int getImageIdByName(String name) {
        int value = 0;
        if (null != name) {
            if (name.indexOf(".") != -1) {
                name = name.substring(0, name.indexOf("."));
            }
            Class<com.etong.android.jxbizusedcar.R.mipmap> cls = com.etong.android.jxbizusedcar.R.mipmap.class;
            try {
                value = cls.getDeclaredField(name).getInt(null);
            } catch (Exception e) {

            }
        }
        return value;
    }
}
