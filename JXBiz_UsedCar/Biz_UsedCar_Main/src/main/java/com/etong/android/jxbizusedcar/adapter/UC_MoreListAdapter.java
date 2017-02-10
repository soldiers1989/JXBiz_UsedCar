package com.etong.android.jxbizusedcar.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.etong.android.frame.widget.EtongNoScrollGridView;
import com.etong.android.jxbizusedcar.R;
import com.etong.android.jxbizusedcar.bean.UC_MoreTitleBeam;

import java.util.List;


public class UC_MoreListAdapter extends BaseAdapter {
    private Context context;
    private List<UC_MoreTitleBeam> mList;
    private LayoutInflater inflater;
    private ItemHolder childHolder;


    public UC_MoreListAdapter(Context context, List<UC_MoreTitleBeam> mList) {
        this.context = context;
        this.mList = mList;
        inflater = LayoutInflater.from(context);

    }


    // 当列表数据发生变化时,用此方法来更新列表
    public void updateListView(List<UC_MoreTitleBeam> mList) {
        this.mList = mList;
        notifyDataSetChanged();
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
    public View getView(final int position, View convertView, ViewGroup arg2) {

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.uc_more_items_lv, null);
            childHolder = new ItemHolder();
            childHolder.titleName = (TextView) convertView.findViewById(R.id.uc_more_txt_listtitle);
            childHolder.mGridView = (EtongNoScrollGridView) convertView.findViewById(R.id.uc_more_gv_content);

            convertView.setTag(childHolder);

        } else {
            childHolder = (ItemHolder) convertView.getTag();
        }

        childHolder.titleName.setText(mList.get(position).getTitleName());
        UC_MoreGridViewAdapter moreGridViewAdapter = new UC_MoreGridViewAdapter(context, mList.get(position).getItemBeamList());
        childHolder.mGridView.setAdapter(moreGridViewAdapter);
        moreGridViewAdapter.notifyDataSetChanged();

        return convertView;
    }


    class ItemHolder {
        TextView titleName;
        EtongNoScrollGridView mGridView;
    }
}