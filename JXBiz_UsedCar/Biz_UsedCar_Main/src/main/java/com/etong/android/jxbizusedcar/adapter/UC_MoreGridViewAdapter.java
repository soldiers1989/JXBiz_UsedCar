package com.etong.android.jxbizusedcar.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.etong.android.frame.developing.DevelopingActivity;
import com.etong.android.frame.user.UC_FrameEtongApplication;
import com.etong.android.frame.user.UC_LoginActivity;
import com.etong.android.jxappcarassistant.gas_tation.main_content.CA_NearServiceActivity;
import com.etong.android.jxappusedcar.content.UC_BuyCarActivity;
import com.etong.android.jxappusedcar.content.UC_BuyCarFragment;
import com.etong.android.jxappusedcar.content.UC_SellCarFragment;
import com.etong.android.jxappusedcar.content.UC_SellCarTestActivity;
import com.etong.android.jxbizusedcar.R;
import com.etong.android.jxbizusedcar.activity.UC_BuyingGuideActivity;
import com.etong.android.jxbizusedcar.activity.UC_CarAssessActivity;
import com.etong.android.jxbizusedcar.activity.UC_Car_IdentifyActivity;
import com.etong.android.jxbizusedcar.bean.UC_MoreTitleBeam;

import org.simple.eventbus.EventBus;

import java.util.List;

/**
 * Created by Administrator on 2016/8/2 0002.
 */
public class UC_MoreGridViewAdapter extends BaseAdapter {
    private List<UC_MoreTitleBeam.MoreItemBeam> mDatas;
    private LayoutInflater mLayoutInflater;
    private Context mContext;

    /**
     * @param context 上下文
     * @param mDatas  传递的数据
     */
    public UC_MoreGridViewAdapter(Context context, List<UC_MoreTitleBeam.MoreItemBeam> mDatas) {
        this.mContext = context;
        this.mDatas = mDatas;
        mLayoutInflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Log.i("TAG", "position:" + position);
        ViewHolder vh = null;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.uc_more_items_gv, parent, false);
            vh = new ViewHolder();
            vh.tv = (TextView) convertView.findViewById(R.id.uc_more_txt_item_name);
            vh.iv = (ImageView) convertView.findViewById(R.id.uc_more_img_item_icon);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        vh.tv.setText(mDatas.get(position).getItemName());
        vh.iv.setImageResource(getImageIdByName(mDatas.get(position).getItemIconName()));

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (position < mDatas.size()) {
                    Log.d("被选中的item位置为：", position + "");
                    Intent i = new Intent();
                    if ("买车".equals(mDatas.get(position).getItemName())) {
                        i.setClass(mContext, UC_BuyCarActivity.class);
                        EventBus.getDefault().postSticky("1", UC_BuyCarFragment.BUY_CAR_IS_REQUEST);//传给买车页的标识
                    } else if ("卖车".equals(mDatas.get(position).getItemName())) {
                        i.setClass(mContext, UC_SellCarTestActivity.class);
                        EventBus.getDefault().postSticky("1", UC_SellCarFragment.SELL_CAR_BACK_TITLE);//传给卖车页的标识
                    } else if ("车辆估值".equals(mDatas.get(position).getItemName())) {
                        i.setClass(mContext, UC_CarAssessActivity.class);
                    } else if ("车辆鉴定".equals(mDatas.get(position).getItemName())) {
                        if (UC_FrameEtongApplication.getApplication().isLogin()) {
                            i.setClass(mContext, UC_Car_IdentifyActivity.class);
                        } else {
//                        EtongToast.makeText(mContext,"您还未登录，请登录", Toast.LENGTH_SHORT).show();
                            i.setClass(mContext, UC_LoginActivity.class);
                            i.putExtra("isShowToast", true);
                        }
//                    } else if ("汽车金融".equals(mDatas.get(position).getItemName())) {
//                    i.setClass(mContext, .class);
                    } else if ("买车攻略".equals(mDatas.get(position).getItemName())) {
                        i.setClass(mContext, UC_BuyingGuideActivity.class);
                    } else if("附近加油站".equals(mDatas.get(position).getItemName())){
                        i.setClass(mContext, CA_NearServiceActivity.class);
                        i.putExtra("title", mDatas.get(position).getItemName());
                    }  else if("周边社区服务".equals(mDatas.get(position).getItemName())){
                        i.setClass(mContext, CA_NearServiceActivity.class);
                        i.putExtra("title", mDatas.get(position).getItemName());
                    } else if("附近停车场".equals(mDatas.get(position).getItemName())){
                        i.setClass(mContext, CA_NearServiceActivity.class);
                        i.putExtra("title", mDatas.get(position).getItemName());
                    } else {
                        i.setClass(mContext, DevelopingActivity.class);
                        i.putExtra(DevelopingActivity.TITLE_NAME, mDatas.get(position).getItemName());
                    }

                    mContext.startActivity(i);
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
            Class<com.etong.android.jxbizusedcar.R.mipmap> cls = R.mipmap.class;
            try {
                value = cls.getDeclaredField(name).getInt(null);
            } catch (Exception e) {

            }
        }
        return value;
    }
}

