package com.etong.android.jxbizusedcar.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.etong.android.frame.developing.DevelopingActivity;
import com.etong.android.frame.user.UC_FrameEtongApplication;
import com.etong.android.frame.user.UC_LoginActivity;
import com.etong.android.jxappusedcar.content.UC_BuyCarActivity;
import com.etong.android.jxappusedcar.content.UC_BuyCarFragment;
import com.etong.android.jxappusedcar.content.UC_SellCarFragment;
import com.etong.android.jxappusedcar.content.UC_SellCarTestActivity;
import com.etong.android.jxbizusedcar.R;
import com.etong.android.jxbizusedcar.activity.UC_BuyingGuideActivity;
import com.etong.android.jxbizusedcar.activity.UC_CarAssessActivity;
import com.etong.android.jxbizusedcar.activity.UC_Car_IdentifyActivity;
import com.etong.android.jxbizusedcar.activity.UsedCar_MainActivity;
import com.etong.android.jxbizusedcar.bean.UC_FindImageBean;
import com.etong.android.jxbizusedcar.fragment.UC_MoreMainFragment;

import org.simple.eventbus.EventBus;

import java.util.List;

/**
 * @desc (第一个fragment（二手车）里头部gridview适配器)
 * @createtime 2016/11/14 - 11:09
 * @Created by xiaoxue.
 */

public class UC_FindHeadAdapter extends BaseAdapter {

    private List<UC_FindImageBean> mDatas;
    private Context mContext;

    public UC_FindHeadAdapter(Context context,List<UC_FindImageBean> mDatas) {
        this.mDatas = mDatas;
        this.mContext = context;
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
        HeadViewHolder vh = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.uc_find_gridview_item_gv, parent, false);
            vh = new HeadViewHolder();
            vh.tv = (TextView) convertView.findViewById(R.id.uc_find_txt_title);
            vh.iv = (ImageView) convertView.findViewById(R.id.uc_find_img_icon);
            convertView.setTag(vh);
        } else {
            vh = (HeadViewHolder) convertView.getTag();
        }
        vh.tv.setText(mDatas.get(position).getItemName());
        vh.iv.setImageResource(getImageIdByName(mDatas.get(position).getItemIconName()));
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                if("买车".equals(mDatas.get(position).getItemName())) {
                    i.setClass(mContext, UC_BuyCarActivity.class);
                    EventBus.getDefault().postSticky("1", UC_BuyCarFragment.BUY_CAR_IS_REQUEST);//传给买车页的标识
                } else if ("卖车".equals(mDatas.get(position).getItemName())) {
                    i.setClass(mContext, UC_SellCarTestActivity.class);
                    EventBus.getDefault().postSticky("1", UC_SellCarFragment.SELL_CAR_BACK_TITLE);//传给卖车页的标识
                }else if ("车辆估值".equals(mDatas.get(position).getItemName())) {
                    i.setClass(mContext, UC_CarAssessActivity.class);
                }
                else if ("车辆鉴定".equals(mDatas.get(position).getItemName())) {
                    if(UC_FrameEtongApplication.getApplication().isLogin()){
                        i.setClass(mContext, UC_Car_IdentifyActivity.class);
                    }else{
//                        EtongToast.makeText(mContext,"您还未登录，请登录", Toast.LENGTH_SHORT).show();
                        i.setClass(mContext, UC_LoginActivity.class);
                    }
                } else if ("更多".equals(mDatas.get(position).getItemName())) {
                    EventBus.getDefault().post(2, UsedCar_MainActivity.SWITCH_PAGE);//跳到更多的fragment
                    return;
//                    i.setClass(mContext, UC_MoreMainFragment.class);
                } else if ("买车攻略".equals(mDatas.get(position).getItemName())) {
                    i.setClass(mContext, UC_BuyingGuideActivity.class);
                } else {
                    i.setClass(mContext, DevelopingActivity.class);
                    i.putExtra(DevelopingActivity.TITLE_NAME, mDatas.get(position).getItemName());
                }
               /*else if ("汽车金融".equals(mDatas.get(position).getItemName())) {
//                    i.setClass(mContext, .class);
                }*/
                mContext.startActivity(i);


            }
        });
        return convertView;
    }

    class HeadViewHolder {
        public TextView tv;
        public ImageView iv;
    }

    /**
     * 根据图片名称获取R.java中对应的id
     *
     * @param name
     * @return
     */
    public static int getImageIdByName (String name) {
        int value = 0;
        if (null != name) {
            if (name.indexOf(".") != -1) {
                name = name.substring(0, name.indexOf("."));
            }
            Class<R.mipmap> cls = R.mipmap.class;
            try {
                value = cls.getDeclaredField(name).getInt(null);
            } catch (Exception e) {

            }
        }
        return value;
    }

}
