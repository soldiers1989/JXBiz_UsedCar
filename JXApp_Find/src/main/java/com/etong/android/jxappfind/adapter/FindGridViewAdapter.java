package com.etong.android.jxappfind.adapter;

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
import com.etong.android.jxappcarfinancial.Interface.CF_InterfaceOrderType;
import com.etong.android.jxappcarfinancial.activity.CF_ApplyForActivity;
import com.etong.android.jxappfind.R;
import com.etong.android.jxappfind.javabean.FindImageBean;
import com.etong.android.jxappfours.find_car.collect_search.main_content.Find_Car_MainActivity;
import com.etong.android.jxappfours.find_car.grand.maintain_progress.Find_car_MaintainProgressActivity;
import com.etong.android.jxappfours.models_contrast.main_content.MC_MainActivity;
import com.etong.android.jxappfours.order.FO_OrderActivity;

import java.util.List;

/**
 * GridView适配器
 * Created by Administrator on 2016/8/2.
 */
public class FindGridViewAdapter extends BaseAdapter {
    private List<FindImageBean> mDatas;
    private LayoutInflater mLayoutInflater;
    private Context context;
    private int columnWidth;
    /**
     * 页数下标,从0开始
     */
    private int mIndex;
    /**
     * 每页显示最大条目个数 ,默认是dimes.xml里 HomePageHeaderColumn 属性值的两倍
     */
    private int mPageSize;

    /**
     * @param context     上下文
     * @param mDatas      传递的数据
     * @param columnWidth
     * @param mIndex      页码
     */
    public FindGridViewAdapter(Context context, List<FindImageBean> mDatas, int columnWidth, int mIndex) {
        this.context = context;
        this.mDatas = mDatas;
        mLayoutInflater = LayoutInflater.from(context);
        this.mIndex = mIndex;
        mPageSize = context.getResources().getInteger(R.integer.HomePageHeaderColumn) * 2;
        this.columnWidth = columnWidth;
    }

    public FindGridViewAdapter(Context context, List<FindImageBean> mDatas, int index) {
        this.context = context;
        this.mDatas = mDatas;
        mLayoutInflater = LayoutInflater.from(context);
        this.mIndex = index;
        mPageSize = context.getResources().getInteger(R.integer.HomePageHeaderColumn) * 2;
    }

    /**
     * 先判断数据集的大小是否足够显示满本页？mDatas.size() > (mIndex+1)*mPageSize,
     * 如果够，则直接返回每一页显示的最大条目个数mPageSize,
     * 如果不够，则有几项返回几,(mDatas.size() - mIndex * mPageSize);
     */
    @Override
    public int getCount() {
        return mDatas.size() > (mIndex + 1) * mPageSize ? mPageSize : (mDatas.size() - mIndex * mPageSize);
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position + mIndex * mPageSize);
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
            convertView = mLayoutInflater.inflate(R.layout.find_gridview_item_gv, parent, false);
            vh = new ViewHolder();
            vh.tv = (TextView) convertView.findViewById(R.id.find_txt_title);
            vh.iv = (ImageView) convertView.findViewById(R.id.find_img_icon);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        /**
         * 在给View绑定显示的数据时，计算正确的position = position + mIndex * mPageSize，
         */
        final int pos = position + mIndex * mPageSize;
        vh.tv.setText(mDatas.get(pos).getItemName());
        vh.iv.setImageResource(getImageIdByName(mDatas.get(pos).getItemIconName()));

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(context,mDatas.get(pos).getItemName(),Toast.LENGTH_SHORT).show();

                Intent i = new Intent();
                if ("找车".equals(mDatas.get(pos).getItemName())) {
                    i.setClass(context, Find_Car_MainActivity.class);
                } else if ("车型对比".equals(mDatas.get(pos).getItemName())) {
//                    i.setClass(context, Models_Contrast_MainActivity.class);
                    i.setClass(context, MC_MainActivity.class);
                } else if ("试驾预约".equals(mDatas.get(pos).getItemName())) {
//                    i.setClass(context, Fours_Order_OrderActivity.class);
                    i.setClass(context, FO_OrderActivity.class);
                    i.putExtra("flag", 1);
                } else if ("维保预约".equals(mDatas.get(pos).getItemName())) {
//                    i.setClass(context, Fours_Order_OrderActivity.class);
                    i.setClass(context, FO_OrderActivity.class);
                    i.putExtra("flag", 2);
                } else if ("订购预约".equals(mDatas.get(pos).getItemName())) {
//                    i.setClass(context, Fours_Order_OrderActivity.class);
                    i.setClass(context, FO_OrderActivity.class);
                    i.putExtra("flag", 3);
                } else if ("底价购车".equals(mDatas.get(pos).getItemName())) {
//                    i.setClass(context, Fours_Order_OrderActivity.class);
                    i.setClass(context, FO_OrderActivity.class);
                    i.putExtra("flag", 4);
                } else if ("维保进度".equals(mDatas.get(pos).getItemName())) {
                    if (FrameEtongApplication.getApplication().isLogin()) {
                        i.setClass(context, Find_car_MaintainProgressActivity.class);
                    } else {
//                        CToast.toastMessage("您还未登录，请登录", 0);
                        DefinedToast.showToast(context, "您还未登录，请登录", 0);
                        i.setClass(context, FramePersonalLoginActivity.class);
                    }
                } else if ("车贷申请".equals(mDatas.get(pos).getItemName())) {
                    i.setClass(context, CF_ApplyForActivity.class);
                    i.putExtra("flag_tag", CF_InterfaceOrderType.VEHICLES_LOAN);
                    i.putExtra("title", mDatas.get(pos).getItemName());
                } else if ("畅通钱包申请".equals(mDatas.get(pos).getItemName())) {
                    i.setClass(context, CF_ApplyForActivity.class);
                    i.putExtra("flag_tag", CF_InterfaceOrderType.HAPPY_WALLET);
                    i.putExtra("title", mDatas.get(pos).getItemName());
                } else {
                    i.setClass(context, DevelopingActivity.class);
                    i.putExtra(DevelopingActivity.TITLE_NAME, mDatas.get(pos).getItemName());
                }
                context.startActivity(i);

            }
        });
        return convertView;
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
            Class<R.mipmap> cls = R.mipmap.class;
            try {
                value = cls.getDeclaredField(name).getInt(null);
            } catch (Exception e) {

            }
        }
        return value;
    }

    class ViewHolder {
        public TextView tv;
        public ImageView iv;
    }
}