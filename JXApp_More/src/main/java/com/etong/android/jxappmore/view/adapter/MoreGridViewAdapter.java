package com.etong.android.jxappmore.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.etong.android.frame.developing.DevelopingActivity;
import com.etong.android.frame.user.FrameEtongApplication;
import com.etong.android.frame.user.FramePersonalLoginActivity;
import com.etong.android.frame.utils.DefinedToast;
import com.etong.android.frame.utils.L;
import com.etong.android.jxappcarassistant.gas_tation.main_content.CA_NearServiceActivity;
import com.etong.android.jxappcarassistant.violation_query.main_content.CA_ViolationQueryMyCarListActivity;
import com.etong.android.jxappcarfinancial.Interface.CF_InterfaceOrderType;
import com.etong.android.jxappcarfinancial.activity.CF_ApplyForActivity;
import com.etong.android.jxappcarfinancial.activity.CF_OrderRecordActivity;
import com.etong.android.jxappcarfinancial.activity.CF_RecordActivity;
import com.etong.android.jxappcarfinancial.activity.CF_RepaymentRemindActivity;
import com.etong.android.jxappdata.DataContentActivity;
import com.etong.android.jxappfours.find_car.collect_search.main_content.Find_Car_MainActivity;
import com.etong.android.jxappfours.find_car.grand.carfinance_caclu.FC_FinanceActivity;
import com.etong.android.jxappfours.find_car.grand.maintain_progress.Find_car_MaintainProgressActivity;
import com.etong.android.jxappfours.find_car.grand.road_assistance.Find_car_RoadAssistanceActivity;
import com.etong.android.jxappfours.models_contrast.main_content.MC_MainActivity;
import com.etong.android.jxappfours.order.FO_OrderActivity;
import com.etong.android.jxappmore.R;
import com.etong.android.jxappmore.javabeam.MoreItemBeam;
import com.etong.android.jxappusedcar.content.UC_SelectWay;
import com.etong.android.jxappusedcar.content.UC_World_ListActivity;

import java.util.List;

/**
 * Created by Administrator on 2016/8/2 0002.
 */
public class MoreGridViewAdapter extends BaseAdapter{
    private List<MoreItemBeam> mDatas;
    private LayoutInflater mLayoutInflater;
    private Context context;
    private int columnWidth;
    //list的虚拟数量
    public int sums;
    /**
     * 页数下标,从0开始
     */
    private int mIndex;
    /**
     * 每页显示最大条目个数 ,默认是dimes.xml里 MoreHomePageHeaderColumn 属性值的两倍
     */
    private int mPageSize;

    /**
     * @param context     上下文
     * @param mDatas      传递的数据
     * @param columnWidth
     * @param mIndex      页码
     */
    public MoreGridViewAdapter(Context context, List<MoreItemBeam> mDatas, int columnWidth, int mIndex) {
        this.context = context;
        this.mDatas = mDatas;
        mLayoutInflater = LayoutInflater.from(context);
        this.mIndex = mIndex;
        mPageSize = context.getResources().getInteger(R.integer.MoreHomePageHeaderColumn) * 2;
        this.columnWidth = columnWidth;

        setFullSums();
    }


    public MoreGridViewAdapter(Context context, List<MoreItemBeam> mDatas, int index) {
        this.context = context;
        this.mDatas = mDatas;
        mLayoutInflater = LayoutInflater.from(context);
        this.mIndex = index;
        mPageSize = context.getResources().getInteger(R.integer.MoreHomePageHeaderColumn) * 2;

        setFullSums();
    }

    //判断GrivdView的item的个数，如果除以6有余数就把items数扩充到6的倍数，使GrivdView的整个视图都有网格线
    public void setFullSums() {

        switch (mDatas.size() % 6) {
            case 0:
                sums = mDatas.size();
                break;
            case 1:
                if (mDatas.size() > 6) {
                    sums = mDatas.size() + 5;
                } else {
                    sums = mDatas.size();
                }
                break;
            case 2:
                if (mDatas.size() > 6) {
                    sums = mDatas.size() + 4;
                } else {
                    sums = mDatas.size();
                }
                break;
            case 3:
                if (mDatas.size() > 6) {
                    sums = mDatas.size() + 3;
                } else {
                    sums = mDatas.size();
                }
                break;
            case 4:
                sums = mDatas.size() + 2;
                break;
            case 5:
                sums = mDatas.size() + 1;
                break;
        }
    }

    /**
     * 先判断数据集的大小是否足够显示满本页？mDatas.size() > (mIndex+1)*mPageSize,
     * 如果够，则直接返回每一页显示的最大条目个数mPageSize,
     * 如果不够，则有几项返回几,(sums - mIndex * mPageSize);
     */
    @Override
    public int getCount() {
        return mDatas.size() > (mIndex + 1) * mPageSize ? mPageSize : (sums - mIndex * mPageSize);
    }

    @Override
    public Object getItem(int position) {
        if (position < mDatas.size()) {
            return mDatas.get(position + mIndex * mPageSize);
        } else {
            return sums;
        }
    }

    @Override
    public long getItemId(int position) {
        return position + mIndex * mPageSize;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        L.i("TAG", "position:" + position);
        ViewHolder vh = null;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.more_items_gv, parent, false);
            vh = new ViewHolder();
            vh.tv = (TextView) convertView.findViewById(R.id.more_txt_item_name);
            vh.iv = (ImageView) convertView.findViewById(R.id.more_img_item_icon);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        /**
         * 在给View绑定显示的数据时，计算正确的position = position + mIndex * mPageSize，
         */
        final int pos = position + mIndex * mPageSize;
        if (pos < mDatas.size()) {

            vh.tv.setText(mDatas.get(pos).getItemName());
            vh.iv.setImageResource(getImageIdByName(mDatas.get(pos).getItemIconName()));
        }

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pos < mDatas.size()) {
                    L.d("被选中的item位置为：", pos + "");
//                    Toast.makeText(context,mDatas.get(pos).getItemName(), Toast.LENGTH_SHORT).show();
                    Intent i = new Intent();
                    if ("找车".equals(mDatas.get(pos).getItemName())) {
                        i.setClass(context, Find_Car_MainActivity.class);
                    } else if ("车型对比".equals(mDatas.get(pos).getItemName())) {
                        i.setClass(context, MC_MainActivity.class);
                    } else if ("试驾预约".equals(mDatas.get(pos).getItemName())) {
//                        i.setClass(context, Fours_Order_OrderActivity.class);
                        i.setClass(context, FO_OrderActivity.class);
                        i.putExtra("flag", 1);
                    } else if ("维保预约".equals(mDatas.get(pos).getItemName())) {
//                        i.setClass(context, Fours_Order_OrderActivity.class);
                        i.setClass(context, FO_OrderActivity.class);
                        i.putExtra("flag", 2);
                    } else if ("订购预约".equals(mDatas.get(pos).getItemName())) {
//                        i.setClass(context, Fours_Order_OrderActivity.class);
                        i.setClass(context, FO_OrderActivity.class);
                        i.putExtra("flag", 3);
                    } else if ("维保进度".equals(mDatas.get(pos).getItemName())) {
                        if (FrameEtongApplication.getApplication().isLogin()) {
                            i.setClass(context, Find_car_MaintainProgressActivity.class);
                        } else {
//                            CToast.toastMessage("您还未登录，请登录", 0);
                            DefinedToast.showToast(context, "您还未登录，请登录", 0);
                            i.setClass(context, FramePersonalLoginActivity.class);
                        }
                    } else if ("道路救援".equals(mDatas.get(pos).getItemName())) {
                        i.setClass(context, Find_car_RoadAssistanceActivity.class);
                    } else if ("数据分析".equals(mDatas.get(pos).getItemName())) {
                        i.setClass(context, DataContentActivity.class);
                    } else if ("底价购车".equals(mDatas.get(pos).getItemName())) {
//                        i.setClass(context, Fours_Order_OrderActivity.class);
                        i.setClass(context, FO_OrderActivity.class);
                        i.putExtra("flag", 4);
                    } else if ("附近加油站".equals(mDatas.get(pos).getItemName())) {
                        i.setClass(context, CA_NearServiceActivity.class);
                        i.putExtra("title", mDatas.get(pos).getItemName());
                    } else if ("预约卖车".equals(mDatas.get(pos).getItemName())) {
                        i.setClass(context, UC_SelectWay.class);
                    } else if ("二手车世界".equals(mDatas.get(pos).getItemName())) {
                        i.setClass(context, UC_World_ListActivity.class);
                    } else if ("周边社区服务".equals(mDatas.get(pos).getItemName())) {
                        i.setClass(context, CA_NearServiceActivity.class);
                        i.putExtra("title", mDatas.get(pos).getItemName());
                    } else if ("附近停车场".equals(mDatas.get(pos).getItemName())) {
                        i.setClass(context, CA_NearServiceActivity.class);
                        i.putExtra("title", mDatas.get(pos).getItemName());
                    } else if ("违章查询".equals(mDatas.get(pos).getItemName())) {
                        i.setClass(context, CA_ViolationQueryMyCarListActivity.class);
                    }else if ("车贷申请".equals(mDatas.get(pos).getItemName())) {
                        i.setClass(context, CF_ApplyForActivity.class);
                        i.putExtra("flag_tag", CF_InterfaceOrderType.VEHICLES_LOAN);
                        i.putExtra("title", mDatas.get(pos).getItemName());
                    } else if ("融资租赁申请".equals(mDatas.get(pos).getItemName())) {
                        i.setClass(context, CF_ApplyForActivity.class);
                        i.putExtra("flag_tag", CF_InterfaceOrderType.FINANCE_LEASE);
                        i.putExtra("title", mDatas.get(pos).getItemName());
                    } else if ("畅通钱包申请".equals(mDatas.get(pos).getItemName())) {
                        i.setClass(context, CF_ApplyForActivity.class);
                        i.putExtra("flag_tag", CF_InterfaceOrderType.HAPPY_WALLET);
                        i.putExtra("title", mDatas.get(pos).getItemName());
                    } else if ("申请进度".equals(mDatas.get(pos).getItemName())) {
                        if (FrameEtongApplication.getApplication().isLogin()) {
                            i.setClass(context, CF_OrderRecordActivity.class);
                        } else {
                            DefinedToast.showToast(context, "您还未登录，请登录", 0);
                            i.setClass(context, FramePersonalLoginActivity.class);
                        }
                    } else if ("还款记录".equals(mDatas.get(pos).getItemName())) {// 贷款：0  还款：1  逾期：2
                        if (FrameEtongApplication.getApplication().isLogin()) {
                            i.setClass(context, CF_RecordActivity.class);
                            i.putExtra("flag",1);
                        } else {
//                            CToast.toastMessage("您还未登录，请登录", 0);
                            DefinedToast.showToast(context, "您还未登录，请登录", 0);
                            i.setClass(context, FramePersonalLoginActivity.class);
                        }
                    } else if ("贷款记录".equals(mDatas.get(pos).getItemName())) {
                        if (FrameEtongApplication.getApplication().isLogin()) {
                            i.setClass(context, CF_RecordActivity.class);
                            i.putExtra("flag",0);
                        } else {
//                            CToast.toastMessage("您还未登录，请登录", 0);
                            DefinedToast.showToast(context, "您还未登录，请登录", 0);
                            i.setClass(context, FramePersonalLoginActivity.class);
                        }
                    } else if ("还款提醒".equals(mDatas.get(pos).getItemName())) {
                        if (FrameEtongApplication.getApplication().isLogin()) {
                            i.setClass(context, CF_RepaymentRemindActivity.class);
                        } else {
//                            CToast.toastMessage("您还未登录，请登录", 0);
                            DefinedToast.showToast(context, "您还未登录，请登录", 0);
                            i.setClass(context, FramePersonalLoginActivity.class);
                        }
                    } else if ("融资计算".equals(mDatas.get(pos).getItemName())) {
                        i.setClass(context, FC_FinanceActivity.class);
                    } else if ("逾期记录".equals(mDatas.get(pos).getItemName())) {
                        if (FrameEtongApplication.getApplication().isLogin()) {
                            i.setClass(context, CF_RecordActivity.class);
                            i.putExtra("flag",2);
                        } else {
//                            CToast.toastMessage("您还未登录，请登录", 0);
                            DefinedToast.showToast(context, "您还未登录，请登录", 0);
                            i.setClass(context, FramePersonalLoginActivity.class);
                        }

                    }  else if ("车辆撤押申请".equals(mDatas.get(pos).getItemName())) {
                        i.setClass(context, CF_ApplyForActivity.class);
                        i.putExtra("flag_tag", CF_InterfaceOrderType.VEHICLES_DRAW);
                        i.putExtra("title", mDatas.get(pos).getItemName());
                    }  else {
                        i.setClass(context, DevelopingActivity.class);
                        i.putExtra(DevelopingActivity.TITLE_NAME, mDatas.get(pos).getItemName());
                    }
                    context.startActivity(i);
                }
            }
        });
        return convertView;
    }


    class ViewHolder {
        public TextView tv;
        public ImageView iv;
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
            Class<com.etong.android.jxappmore.R.mipmap> cls = R.mipmap.class;
            try {
                value = cls.getDeclaredField(name).getInt(null);
            } catch (Exception e) {

            }
        }
        return value;
    }
}

