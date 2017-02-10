package com.etong.android.jxappusedcar.adapter;

import android.content.Context;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.etong.android.frame.common.ImageProvider;
import com.etong.android.frame.request_init.UC_HttpTag;
import com.etong.android.jxappusedcar.R;
import com.etong.android.jxappusedcar.bean.UC_GrandBrandItemBean;

import org.simple.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * GridView适配器  品牌
 * Created by Administrator on 2016/8/2.
 */
public class UC_SelectBrandHotAdapter extends BaseAdapter {
    private LayoutInflater mLayoutInflater;
    private Context context;
    private List<UC_GrandBrandItemBean> mDatas;
    private DrawerLayout mDrawerLayout;
    private EventBus mEventBus;     // EventBus传入

    public UC_SelectBrandHotAdapter(Context context, List<UC_GrandBrandItemBean> datas,
                                    DrawerLayout drawerLayout, EventBus eventBus) {
        this.context = context;
        this.mDatas = new ArrayList<UC_GrandBrandItemBean>();
        mLayoutInflater = LayoutInflater.from(context);
        this.mDrawerLayout= drawerLayout;
        this.mEventBus = eventBus;
    }

    // 更新方法
    public void updateListDatas(List<UC_GrandBrandItemBean> listDatas) {
        this.mDatas = listDatas;
        notifyDataSetChanged();
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder vh = null;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.uc_content_lv_header_hot_brand_item, parent, false);
            vh = new ViewHolder();
            vh.tv = (TextView) convertView.findViewById(R.id.uc_content_lv_header_hot_item_txt_title);
            vh.iv = (ImageView) convertView.findViewById(R.id.uc_content_lv_header_hot_item_img_icon);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        vh.tv.setText(mDatas.get(position).getF_brand());
        vh.iv.setImageResource(mDatas.get(position).getF_carbrandid());
        ImageProvider.getInstance().loadImage(vh.iv,mDatas.get(position).getImage(),R.mipmap.uc_select_brand_loading);
//        vh.iv.setImageDrawable(context.getResources().getDrawable(mDatas.get(position).getId()));

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // 传入javabean，到菜单页面获取值
                mEventBus.post(mDatas.get(position), UC_HttpTag.POST_BRAND_TO_MENU);
                mDrawerLayout.openDrawer(Gravity.RIGHT);
            }
        });

        return convertView; // 返回ImageView
    }

    /*
     * 功能：获得当前选项的ID
     *
     * @see android.widget.Adapter#getItemId(int)
     */
    @Override
    public long getItemId(int position) {
        //System.out.println("getItemId = " + position);
        return position;
    }

    /*
     * 功能：获得当前选项
     *
     * @see android.widget.Adapter#getItem(int)
     */
    @Override
    public Object getItem(int position) {
        return position;
    }

    /*
     * 获得数量
     *
     * @see android.widget.Adapter#getCount()
     */
    @Override
    public int getCount() {
        return mDatas.size();
    }

    class ViewHolder {
        public TextView tv;
        public ImageView iv;
    }
}