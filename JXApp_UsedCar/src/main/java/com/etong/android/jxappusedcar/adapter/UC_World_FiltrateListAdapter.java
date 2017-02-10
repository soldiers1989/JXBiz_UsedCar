package com.etong.android.jxappusedcar.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.etong.android.frame.utils.L;
import com.etong.android.frame.widget.EtongLineNoScrollGridView;
import com.etong.android.frame.widget.EtongNoScrollGridView;
import com.etong.android.jxappusedcar.R;
import com.etong.android.jxappusedcar.javabean.UC_FilterRequestBean;
import com.etong.android.jxappusedcar.javabean.UC_World_FiltrateListItemBeam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @desc (二手车筛选适配器)
 * @createtime 2016/10/9 0009--15:06
 * @Created by wukefan.
 */
public class UC_World_FiltrateListAdapter extends BaseAdapter implements UC_World_DefaultGridAdapter.UC_ICallBack, UC_World_LevelGridAdapter.UC_LICallBack {
    private Context mContext;
    private List<UC_World_FiltrateListItemBeam> mData;     // 传送过来的数据
    final int VIEW_TYPE = 2;
    final int TYPE_1 = 0;
    final int TYPE_2 = 1;
    private UC_World_DefaultGridAdapter defaultGridAdapter;
    private UC_World_LevelGridAdapter levelGridAdapter;

    private Map<String, UC_FilterRequestBean> selectMap = new HashMap<String, UC_FilterRequestBean>();

    /**
     * 构造方法
     *
     * @param context
     */
    public UC_World_FiltrateListAdapter(Context context) {
        this.mContext = context;
        this.mData = new ArrayList<>();
    }

    // 每个convert view都会调用此方法，获得当前所需要的view样式
    @Override
    public int getItemViewType(int position) {
        int p = position;
        if (p == 2)
            return TYPE_2;
        else
            return TYPE_1;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    /**
     * @param
     * @return
     * @desc (对外公布的更新ListView数据的方法)
     */
    public void updateCarList(List<UC_World_FiltrateListItemBeam> data) {
//        for (UC_World_FiltrateListItemBeam bean : data) {
//            if (bean.getType().equals("1")) {
//                this.mData.add(bean);
//            }
//        }
        this.mData = data;
        notifyDataSetChanged();
    }


    //清空保存选中的items的Map
    public void setCleanSeclect() {
        selectMap.clear();
        for (int i = 0; i < mData.size(); i++) {
            if (mData.get(i).getType().equals("1")) {
                for (int j = 0; j < mData.get(i).getMap().size(); j++) {
                    UC_World_FiltrateListItemBeam.MapBean tempMap = mData.get(i).getMap().get(j);
                    tempMap.setSelect(false);
                    mData.get(i).getMap().set(j, tempMap);
                }
            }
        }
        notifyDataSetChanged();
        defaultGridAdapter.notifyDataSetChanged();
        levelGridAdapter.notifyDataSetChanged();
    }

    //得到保存选中的items的Map
    public Map<String, UC_FilterRequestBean> getSelectItemMap() {
        return selectMap;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int i) {
        return mData.get(i);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder1 holder1 = null;
        ViewHolder2 holder2 = null;
        int type = getItemViewType(position);
        if (convertView == null) {
            // 按当前所需的样式，确定new的布局
            switch (type) {
                //选择项
                case TYPE_1:
                    convertView = LayoutInflater.from(mContext).inflate(R.layout.used_car_world_filtrate_list_default, null);
                    holder1 = new ViewHolder1();

                    holder1.typeTitle = (TextView) convertView.findViewById(R.id.uc_world_item_title);
                    holder1.defaultGridView = (EtongNoScrollGridView) convertView.findViewById(R.id.uc_world_gv_item_content);

                    convertView.setTag(holder1);
                    break;

                case TYPE_2:
                    convertView = LayoutInflater.from(mContext).inflate(R.layout.used_car_world_filtrate_list_level, null);

                    holder2 = new ViewHolder2();
                    holder2.levelGridView = (EtongLineNoScrollGridView) convertView.findViewById(R.id.uc_world_gv_level);

                    convertView.setTag(holder2);
                    break;
            }

        } else {

            switch (type) {
                //选择项
                case TYPE_1:
                    holder1 = (ViewHolder1) convertView.getTag();
                    break;

                case TYPE_2:
                    holder2 = (ViewHolder2) convertView.getTag();
                    break;
            }
        }

        // 设置资源
        switch (type) {
            case TYPE_1:
                defaultGridAdapter = new UC_World_DefaultGridAdapter(holder1.defaultGridView, position, mData.get(position).getParaName(), mContext, UC_World_FiltrateListAdapter.this, mData.get(position).getMap());

                holder1.typeTitle.setText(mData.get(position).getParaName());

                holder1.defaultGridView.setAdapter(defaultGridAdapter);
                defaultGridAdapter.notifyDataSetChanged();
                break;
            case TYPE_2:
                levelGridAdapter = new UC_World_LevelGridAdapter(holder2.levelGridView, position, mData.get(position).getParaName(), mContext, UC_World_FiltrateListAdapter.this, mData.get(position).getMap());

                holder2.levelGridView.setAdapter(levelGridAdapter);
                levelGridAdapter.notifyDataSetChanged();
                break;
        }

        return convertView;
    }

    @Override
    public void answerDefault(boolean isSelect, int position, int selectPostion, String
            paraName, String itemName, String flag) {
        L.d("------------------->>>>" + paraName, flag + " " + isSelect);
        setCallBackResult(isSelect, position, selectPostion, paraName, itemName, flag);
    }

//    @Override
//    public void answerLevel(boolean isSelect, int position, int selectPostion, String paraName, String itemName, String flag) {
//        setCallBackResult(isSelect, position, selectPostion, paraName, itemName, flag);
//    }

    public void setCallBackResult(boolean isSelect, int position, int selectPostion, String
            paraName, String itemName, String flag) {
        if (isSelect) {//选择项点击项选中
            // 放入选中的map中
            UC_FilterRequestBean tempBean = new UC_FilterRequestBean();
            tempBean.setType("1");
            tempBean.setFlag(flag);
            tempBean.setName(itemName);
            selectMap.put(paraName, tempBean);
            //相应的数据也设置选中
            for (int i = 0; i < mData.get(position).getMap().size(); i++) {
                if (i == selectPostion) {
                    UC_World_FiltrateListItemBeam.MapBean tempMap = mData.get(position).getMap().get(i);
                    tempMap.setSelect(true);
                    mData.get(position).getMap().set(i, tempMap);
                } else {
                    UC_World_FiltrateListItemBeam.MapBean tempMap = mData.get(position).getMap().get(i);
                    tempMap.setSelect(false);
                    mData.get(position).getMap().set(i, tempMap);
                }
            }
        } else {//选择项点击项未选中
            selectMap.remove(paraName);//从选中的map中移除点击项
            //相应的数据也设置未选中
            for (int i = 0; i < mData.get(position).getMap().size(); i++) {
                UC_World_FiltrateListItemBeam.MapBean tempMap = mData.get(position).getMap().get(i);
                tempMap.setSelect(false);
                mData.get(position).getMap().set(i, tempMap);
            }
        }
    }

    class ViewHolder1 {
        TextView typeTitle;
        EtongNoScrollGridView defaultGridView;

    }

    class ViewHolder2 {
        EtongLineNoScrollGridView levelGridView;
    }
}
