package com.etong.android.jxappmessage.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.etong.android.frame.common.ImageProvider;
import com.etong.android.frame.user.FrameEtongApplication;
import com.etong.android.frame.utils.Etong_DateToStringUtil;
import com.etong.android.jxappmessage.R;
import com.etong.android.jxappmessage.content.MessageListActivity;
import com.etong.android.jxappmessage.content.MessageMainFragment;
import com.etong.android.jxappmessage.content.MessageSearchActivity;
import com.etong.android.jxappmessage.content.MessageWebViewActivity;
import com.etong.android.jxappmessage.javabean.MessageWebViewBean;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @desc (资讯列表adapter)
 * @createtime 2016/12/15 - 15:30
 * @Created by xiaoxue.
 */

public class MessageListAdapter extends BaseAdapter{

    private Context mContext;
    private List<MessageWebViewBean> mlist;
    private boolean isSearch=false;

    public MessageListAdapter(Context mContext) {
        this.mContext = mContext;
        this.mlist = new ArrayList<>();
    }
    public void clear() {
        mlist.clear();
        notifyDataSetChanged();
    }
    //更新资讯列表的数据
    public void update(List<MessageWebViewBean> mlist) {
        this.mlist = mlist;
        notifyDataSetChanged();
    }
    //更新搜索的数据
    public void updateSearch(List<MessageWebViewBean> mlist,boolean isSearch){
        this.mlist = mlist;
        this.isSearch=isSearch;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mlist.size();
    }

    @Override
    public Object getItem(int position) {
        return mlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        MessageViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.message_list_item, null);
            holder = new MessageViewHolder();
            holder.m_img = (ImageView) convertView.findViewById(R.id.message_img_pic);
            holder.m_title = (TextView) convertView.findViewById(R.id.message_txt_title);
            holder.m_time = (TextView) convertView.findViewById(R.id.message_txt_time);

            convertView.setTag(holder);
        } else {
            holder = (MessageViewHolder) convertView.getTag();
        }
        ImageProvider.getInstance().loadImage(holder.m_img,mlist.get(position).getImg(),R.mipmap.message_list_ic);
        holder.m_title.setText(mlist.get(position).getTitle());
        holder.m_time.setText(Etong_DateToStringUtil.getDateToString(mlist.get(position).getCreate_time()));
        convertView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, MessageWebViewActivity.class);//跳到详情页
                if(isSearch){//搜索的跳转
                    intent.putExtra("id", mlist.get(position).getId());
                    intent.putExtra("title", mlist.get(position).getTitle());
                    FrameEtongApplication.getApplication().addSearchMessageHistory(mlist.get(position).getTitle());
                }else {//列表的跳转
                    intent.putExtra("id", mlist.get(position).getId());
                }
                mContext.startActivity(intent);
            }
        });
        return convertView;
    }

    class MessageViewHolder {
        ImageView m_img;
        TextView m_title;
        TextView m_time;
    }
}
