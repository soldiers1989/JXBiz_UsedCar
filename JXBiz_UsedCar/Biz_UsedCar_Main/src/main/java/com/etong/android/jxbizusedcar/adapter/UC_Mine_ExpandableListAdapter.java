package com.etong.android.jxbizusedcar.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.etong.android.frame.common.ImageProvider;
import com.etong.android.frame.user.UC_FrameEtongApplication;
import com.etong.android.frame.user.UC_LoginActivity;
import com.etong.android.jxappusedcar.content.UC_CarDetail_Activity;
import com.etong.android.jxbizusedcar.R;
import com.etong.android.jxbizusedcar.activity.UC_CarAssessActivity;
import com.etong.android.jxbizusedcar.activity.UC_Car_IdentifyActivity;
import com.etong.android.jxbizusedcar.activity.UC_FeedbackActivity;
import com.etong.android.jxbizusedcar.activity.UC_MyWalletActivity;
import com.etong.android.jxbizusedcar.activity.UC_OrdercentreActivity;
import com.etong.android.jxbizusedcar.activity.UC_SettingActivity;
import com.etong.android.jxbizusedcar.bean.UC_MineAllDataBean;
import com.etong.android.jxbizusedcar.bean.UC_MineTitleBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @desc (我的界面适配器(包括title和为你推荐的数据))
 * @createtime 2016/11/8 0008--15:12
 * @Created by wukefan.
 */
public class UC_Mine_ExpandableListAdapter extends BaseExpandableListAdapter {


    private List<List<UC_MineAllDataBean.CarlistBean>> mData;//子布局数据
    private List<UC_MineTitleBean> mTitleData;//父布局数据
    private Context mContext;
    private ExpandableListView mListView;

    public UC_Mine_ExpandableListAdapter(Context context, ExpandableListView mListView) {
        this.mContext = context;
        this.mListView = mListView;

        //设置父布局数据
        mTitleData = new ArrayList<>();
        UC_MineTitleBean orderBean = new UC_MineTitleBean("订单中心", "uc_ic_order_center", false);
        mTitleData.add(orderBean);
        UC_MineTitleBean walletBean = new UC_MineTitleBean("我的钱包", "uc_ic_my_wallet", false);
        mTitleData.add(walletBean);
        UC_MineTitleBean identifyBean = new UC_MineTitleBean("车鉴定", "uc_ic_car_test", false);
        mTitleData.add(identifyBean);
        UC_MineTitleBean assessBean = new UC_MineTitleBean("车辆估值", "uc_ic_car_assess", false);
        mTitleData.add(assessBean);
        UC_MineTitleBean feedBackBean = new UC_MineTitleBean("意见反馈", "uc_ic_feebback", false);
        mTitleData.add(feedBackBean);
        UC_MineTitleBean settingBean = new UC_MineTitleBean("设置", "uc_ic_setting", false);
        mTitleData.add(settingBean);
        UC_MineTitleBean recommeBean = new UC_MineTitleBean("为你推荐", "uc_ic_recomme_for_you", true);//有子布局的放最后
        mTitleData.add(recommeBean);

        //初始化子布局数据
        mData = new ArrayList<>();
        for (int i = 0; i < mTitleData.size(); i++) {
            List<UC_MineAllDataBean.CarlistBean> tempList = new ArrayList<>();
            mData.add(tempList);
        }

    }

    /**
     * @param
     * @return
     * @desc (对外公布的更新ListView数据的方法)
     */
    public void updateCarList(List<UC_MineAllDataBean.CarlistBean> data) {
        this.mData.set(mTitleData.size() - 1, data);
        this.mListView.expandGroup(mTitleData.size() - 1);
//        notifyDataSetChanged();
    }


    @Override
    public int getGroupCount() {
        return mTitleData.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return mData.get(i).size();
    }

    @Override
    public Object getGroup(int i) {
        return mTitleData.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return mData.get(i).get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(final int i, boolean b, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(mContext).inflate(R.layout.uc_mine_parent_item, null);

        ViewGroup titleView = (ViewGroup) view.findViewById(R.id.uc_ll_title_view);
        View dividerView = (View) view.findViewById(R.id.uc_mine_bold_divider);
        ImageView titleImg = (ImageView) view.findViewById(R.id.uc_mine_img_item);
        TextView titleName = (TextView) view.findViewById(R.id.uc_mine_txt_title);
        TextView RightPic = (TextView) view.findViewById(R.id.uc_mine_right_pic);

        if (mTitleData.get(i).isHasChild()) {
            dividerView.setVisibility(View.VISIBLE);
            RightPic.setVisibility(View.GONE);
        } else {
            dividerView.setVisibility(View.GONE);
            RightPic.setVisibility(View.VISIBLE);
            if (mTitleData.get(i).getTitleName().equals("订单中心")) {
                RightPic.setText("查看全部订单");
            } else {
                RightPic.setText("");
            }
        }

        titleImg.setBackgroundResource(getImageIdByName(mTitleData.get(i).getTitleImg()));
        titleName.setText(mTitleData.get(i).getTitleName());

        titleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mTitleData.get(i).isHasChild()) {
                    Intent iternt = new Intent();
                    if ("车鉴定".equals(mTitleData.get(i).getTitleName())) {
                        if (UC_FrameEtongApplication.getApplication().isLogin()) {
                            iternt.setClass(mContext, UC_Car_IdentifyActivity.class);
                        } else {
                            iternt.setClass(mContext, UC_LoginActivity.class);
                            iternt.putExtra("isShowToast", true);
                        }
                    } else if ("车辆估值".equals(mTitleData.get(i).getTitleName())) {
                        iternt.setClass(mContext, UC_CarAssessActivity.class);
                    } else if ("意见反馈".equals(mTitleData.get(i).getTitleName())) {
                        iternt.setClass(mContext, UC_FeedbackActivity.class);
                    } else if ("设置".equals(mTitleData.get(i).getTitleName())) {
                        iternt.setClass(mContext, UC_SettingActivity.class);
                    } else if ("我的钱包".equals(mTitleData.get(i).getTitleName())) {
                        if (UC_FrameEtongApplication.getApplication().isLogin()) {
                            iternt.setClass(mContext, UC_MyWalletActivity.class);
                        } else {
                            iternt.setClass(mContext, UC_LoginActivity.class);
                            iternt.putExtra("isShowToast", true);
                        }
                    } else if ("订单中心".equals(mTitleData.get(i).getTitleName())) {
                        if (UC_FrameEtongApplication.getApplication().isLogin()) {
                            iternt.setClass(mContext, UC_OrdercentreActivity.class);
                        } else {
                            iternt.setClass(mContext, UC_LoginActivity.class);
                            iternt.putExtra("isShowToast", true);
                        }
                    }
                    mContext.startActivity(iternt);
                }
            }
        });

        return view;
    }

    @Override
    public View getChildView(int i, final int i1, boolean b, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(mContext).inflate(R.layout.uc_item_recomme_for_you, null);
        final int position = i;
        ImageView carImg = (ImageView) view.findViewById(R.id.uc_recomme_iv_car_picture);
        TextView titleTxt = (TextView) view.findViewById(R.id.uc_recomme_txt_car_title);
        TextView dateMileageTxt = (TextView) view.findViewById(R.id.uc_recomme_txt_car_date_mileage);
        TextView priceTxt = (TextView) view.findViewById(R.id.uc_recomme_txt_car_price);

        titleTxt.setText(mData.get(i).get(i1).getF_cartitle());
        ImageProvider.getInstance().loadImage(carImg, mData.get(i).get(i1).getImage(), R.mipmap.uc_image_loading);
//                if (0.01 <= mData.get(position).getF_mileage() / 10000) {//里程公里数大于等于100的
//                    String mileage = String.format("%.2f", mData.get(position).getF_mileage() / 10000);
//                    dateMileageTxt.setText(mData.get(position).getF_registerdate() + "   " + mileage.substring(0, mileage.indexOf(".") + 2 + 1) + "万公里");
//                } else {//里程公里数小于100的
//                    dateMileageTxt.setText(mData.get(position).getF_registerdate() + "   " + String.format("%.2f", mData.get(position).getF_mileage()) + "公里");
//                }
        dateMileageTxt.setText(mData.get(i).get(i1).getF_registerdate() + "   " + mData.get(i).get(i1).getF_mileage() + "万公里");
        priceTxt.setText(mData.get(i).get(i1).getF_price() + "万");

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 点击车辆跳转到车辆详情页面
//                UC_MineFragment.onResumeIsCanRequest = true;
                Intent i = new Intent(mContext, UC_CarDetail_Activity.class);
                i.putExtra("f_dvid", mData.get(position).get(i1).getF_dvid());
                mContext.startActivity(i);
            }
        });

        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
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
            Class<com.etong.android.jxbizusedcar.R.mipmap> cls = R.mipmap.class;
            try {
                value = cls.getDeclaredField(name).getInt(null);
            } catch (Exception e) {

            }
        }
        return value;
    }
}
