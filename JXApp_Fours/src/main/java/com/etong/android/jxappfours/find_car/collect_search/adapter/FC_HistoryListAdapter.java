package com.etong.android.jxappfours.find_car.collect_search.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.etong.android.jxappfours.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @desc (搜索历史的adapter)
 * @createtime 2017/1/6 - 17:18
 * @Created by xiaoxue.
 */

public class FC_HistoryListAdapter extends BaseAdapter {
    private Context mContext;
    private List<String> mlist;
    private HistoryCallBack historyCallBack;

    public FC_HistoryListAdapter(Context mContext, HistoryCallBack historyCallBack) {
        this.mContext = mContext;
        this.mlist = new ArrayList<>();
        this.historyCallBack=historyCallBack;
    }

    public void clear() {
        mlist.clear();
        notifyDataSetChanged();
    }

    public void update(List<String> mlist) {
        this.mlist = mlist;
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
        HistoryViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.find_car_history_search_list_item, null);
            holder = new HistoryViewHolder();
            holder.m_title = (TextView) convertView.findViewById(R.id.find_car_txt_history);
            convertView.setTag(holder);
        }else {
            holder = (HistoryViewHolder) convertView.getTag();
        }
        holder.m_title.setText(mlist.get(position));
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                historyCallBack.answerIntent(mlist.get(position));
            }
        });
        return convertView;
    }

    class HistoryViewHolder {
        TextView m_title;
    }

    //点击列表item 回调接口
    public interface HistoryCallBack {
        public void answerIntent(String history_text);
    }
}
