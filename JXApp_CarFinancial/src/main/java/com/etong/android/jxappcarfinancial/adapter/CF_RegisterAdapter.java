package com.etong.android.jxappcarfinancial.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.etong.android.frame.common.ImageProvider;
import com.etong.android.jxappcarfinancial.R;
import com.etong.android.jxappcarfinancial.activity.CF_FirstBindingInfoActivity;
import com.etong.android.jxappcarfinancial.bean.CF_RegisterBean;

import java.util.List;

/**
 * @desc 查询金融机构adapter
 * @createtime 2016/11/22 - 15:38
 * @Created by xiaoxue.
 */

public class CF_RegisterAdapter extends BaseAdapter {

    private Context mContext;
    private List<CF_RegisterBean> mList;
    private int status;

    public CF_RegisterAdapter(Context mContext, List<CF_RegisterBean> mList,int status) {
        this.mContext = mContext;
        this.mList = mList;
        this.status=status;
    }


    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        UC_RegisterViewHolder viewHolder = null;
        if (null == convertView) {
            viewHolder = new UC_RegisterViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.cf_activity_no_register_item, null);
            viewHolder.cf_ll_make_sure = (LinearLayout) convertView.findViewById(R.id.cf_ll_make_sure);
            viewHolder.cf_iv_icon = (ImageView) convertView.findViewById(R.id.cf_iv_icon);
            viewHolder.cf_txt_name = (TextView) convertView.findViewById(R.id.cf_txt_name);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (UC_RegisterViewHolder) convertView.getTag();
        }
//        if(mList.get(position).getF_institutId().equals("11")){
//            viewHolder.cf_iv_icon.setImageResource(mList.get(position).getF_institutLogs());
//        }
        ImageProvider.getInstance().loadImage(viewHolder.cf_iv_icon,mList.get(position).getF_institutLogs());
        viewHolder.cf_txt_name.setText(mList.get(position).getF_institutName());

        viewHolder.cf_ll_make_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, CF_FirstBindingInfoActivity.class);
                intent.putExtra("f_institutId",mList.get(position).getF_institutId());
                intent.putExtra("f_ordertype",status);
                mContext.startActivity(intent);
            }
        });
        return convertView;
    }

    private static class UC_RegisterViewHolder {
        public LinearLayout cf_ll_make_sure;
        public ImageView cf_iv_icon;
        public TextView cf_txt_name;
    }
}

