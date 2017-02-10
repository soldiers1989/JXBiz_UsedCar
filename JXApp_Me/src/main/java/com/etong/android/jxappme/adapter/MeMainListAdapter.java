package com.etong.android.jxappme.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.etong.android.frame.developing.DevelopingActivity;
import com.etong.android.frame.developing.DevelopingEmptyActivity;
import com.etong.android.frame.user.FrameEtongApplication;
import com.etong.android.frame.user.FramePersonalLoginActivity;
import com.etong.android.frame.utils.ActivitySkipUtil;
import com.etong.android.frame.utils.DefinedToast;
import com.etong.android.jxappcarfinancial.activity.CF_OrderRecordActivity;
import com.etong.android.jxappcarfinancial.activity.CF_RecordActivity;
import com.etong.android.jxappcarfinancial.activity.CF_RepaymentRemindActivity;
import com.etong.android.jxappfours.find_car.grand.maintain_progress.Find_car_MaintainProgressActivity;
import com.etong.android.jxappfours.order.Fours_MyCarActivity;
import com.etong.android.jxappme.JavaBeam.MeItemBeam;
import com.etong.android.jxappme.R;
import com.etong.android.jxappme.view.MeMainFragment;
import com.etong.android.jxappme.view.MePersonalSettingActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * @desc (这里用一句话描述这个类的作用)
 * @createtime 2017/1/4 - 14:24
 * @Created by wukefan.
 */
public class MeMainListAdapter extends BaseAdapter {

    private Context mContext;
    private List<MeItemBeam> mData;
    private LayoutInflater mInflater;

    /**
     * 构造方法
     *
     * @param context
     */
    public MeMainListAdapter(Context context) {
        this.mContext = context;
        this.mData = new ArrayList<>();
        this.mInflater = LayoutInflater.from(context);
        setDatas();
    }

    public void setDatas() {
        MeItemBeam itemBean1 = new MeItemBeam("我的爱车", "bound_vehicle");
        mData.add(itemBean1);
        MeItemBeam itemBean2 = new MeItemBeam("维保进度", "mine_maintenance_progress");
        mData.add(itemBean2);
        MeItemBeam itemBean3 = new MeItemBeam("申请进度", "mine_application_progress");
        mData.add(itemBean3);
        MeItemBeam itemBean4 = new MeItemBeam("贷款记录", "mine_loan_record");
        mData.add(itemBean4);
        MeItemBeam itemBean5 = new MeItemBeam("还款提醒", "mine_repayment_reminder");
        mData.add(itemBean5);
        MeItemBeam itemBean6 = new MeItemBeam("设置", "setting");
        mData.add(itemBean6);
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
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.me_infoitem_lv, null);
            viewHolder = new ViewHolder();
            viewHolder.tvItemName = (TextView) convertView.findViewById(R.id.me_txt_title);
            viewHolder.ivItemImage = (ImageView) convertView.findViewById(R.id.me_img_icon);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tvItemName.setText(mData.get(position).getItemName());
        viewHolder.ivItemImage.setBackgroundResource(MeMainFragment.getImageIdByName(mData.get(position).getItemIconName()));

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                        Toast.makeText(getActivity(), data.getItemName(), Toast.LENGTH_SHORT).show();
                if (FrameEtongApplication.getApplication().isLogin()) {
                    Intent i = new Intent();
                    if ("我的爱车".equals(mData.get(position).getItemName())) {
                        i.setClass(mContext, Fours_MyCarActivity.class);
                    } else if ("维保进度".equals(mData.get(position).getItemName())) {
                        i.setClass(mContext, Find_car_MaintainProgressActivity.class);
                    } else if ("申请进度".equals(mData.get(position).getItemName())) {
                        i.setClass(mContext, CF_OrderRecordActivity.class);
                    } else if ("设置".equals(mData.get(position).getItemName())) {
//                                i.setClass(getActivity(), MeSettingActivity.class);
                        i.setClass(mContext, MePersonalSettingActivity.class);
                    } else if ("贷款记录".equals(mData.get(position).getItemName())) {
                        i.setClass(mContext, CF_RecordActivity.class);
                        i.putExtra("flag", 0);
                    } else if ("还款提醒".equals(mData.get(position).getItemName())) {
                        i.setClass(mContext, CF_RepaymentRemindActivity.class);
                    } else {
                        i.setClass(mContext, DevelopingEmptyActivity.class);
                        i.putExtra(DevelopingActivity.TITLE_NAME, mData.get(position).getItemName());
                    }
                    mContext.startActivity(i);
                } else {
                    if ("设置".equals(mData.get(position).getItemName())) {
                        ActivitySkipUtil.skipActivity((Activity) mContext, MePersonalSettingActivity.class);
                        return;
                    }
                    toastMsg("您还没有登录，请先登录！");
                    ActivitySkipUtil.skipActivity((Activity) mContext, FramePersonalLoginActivity.class);
                }
            }
        });

        return convertView;
    }

    protected void toastMsg(String msg) {
        DefinedToast.showToast(mContext, msg, 0);
    }

    public class ViewHolder {
        public TextView tvItemName;
        public ImageView ivItemImage;

    }
}
