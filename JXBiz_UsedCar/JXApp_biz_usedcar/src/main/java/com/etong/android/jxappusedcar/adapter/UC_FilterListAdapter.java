package com.etong.android.jxappusedcar.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.etong.android.frame.utils.L;
import com.etong.android.frame.widget.EtongNoScrollGridView;
import com.etong.android.jxappusedcar.R;
import com.etong.android.jxappusedcar.bean.UC_FilterDataDictionaryBean;
import com.etong.android.jxappusedcar.bean.UC_FilterRequestBean;
import com.etong.android.jxappusedcar.utils.UC_AdvancedFilter_RangeSeekBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @desc (二手车筛选适配器)
 * @createtime 2016/10/9 0009--15:06
 * @Created by wukefan.
 */
public class UC_FilterListAdapter extends BaseAdapter implements UC_FilterDefaultGridAdapter.UC_ICallBack {
    private Context mContext;
    private List<UC_FilterDataDictionaryBean> mData;     // 传送过来的数据
    final int VIEW_TYPE = 2;
    final int TYPE_1 = 0;//选择类型
    final int TYPE_2 = 1;//范围类型
    private UC_FilterDefaultGridAdapter defaultGridAdapter;
    private Map<String, String> mRange = new HashMap<String, String>();

    private Map<String, UC_FilterRequestBean> selectMap = new HashMap<String, UC_FilterRequestBean>();

    /**
     * 构造方法
     *
     * @param context
     */
    public UC_FilterListAdapter(Context context) {
        this.mContext = context;
        mData = new ArrayList<UC_FilterDataDictionaryBean>();
    }

    /**
     * @param
     * @return
     * @desc (对外公布的更新ListView数据的方法)
     */
    public void updateCarList(List<UC_FilterDataDictionaryBean> data) {
        this.mData = data;

        //记录上次选择的seekbar位置
        for (int i = 0; i < mData.size(); i++) {
            if (mData.get(i).getType().equals("2")) {
                if (!mData.get(i).isRangeIsSelect()) {
                    mRange.put(i + "MaxValue", mData.get(i).getMaxValue());
                    mRange.put(i + "MinValue", mData.get(i).getMinValue());
                } else {
                    if (null != mData.get(i).getSelectMin() && null != mData.get(i).getSelectMax()) {
                        mRange.put(i + "MinValue", mData.get(i).getSelectMin());
                        mRange.put(i + "MaxValue", mData.get(i).getSelectMax());
                    } else if (null != mData.get(i).getSelectMin()) {
                        mRange.put(i + "MinValue", mData.get(i).getSelectMin());
                        mRange.put(i + "MaxValue", mData.get(i).getMaxValue());
                    } else if (null != mData.get(i).getSelectMax()) {
                        mRange.put(i + "MaxValue", mData.get(i).getSelectMax());
                        mRange.put(i + "MinValue", mData.get(i).getMinValue());
                    }
                }
            }
        }

        notifyDataSetChanged();
    }


    // 每个convert view都会调用此方法，获得当前所需要的view样式
    @Override
    public int getItemViewType(int position) {
        int p = position;
        if (mData.get(p).getType().equals("1")){
            return TYPE_1;
        } else{
            return TYPE_2;
        }
    }

    @Override
    public int getViewTypeCount() {
        return VIEW_TYPE;
    }

    //清空保存选中的items的Map
    public void setCleanSeclect() {
        selectMap.clear();
        for (int i = 0; i < mData.size(); i++) {
            if (mData.get(i).getType().equals("2")) {
                mRange.put(i + "MinValue", mData.get(i).getMinValue());
                mRange.put(i + "MaxValue", mData.get(i).getMaxValue());
            } else {
                for (int j = 0; j < mData.get(i).getMap().size(); j++) {
                    UC_FilterDataDictionaryBean.MapBean tempMap = mData.get(i).getMap().get(j);
                    tempMap.setSelect(false);
                    mData.get(i).getMap().set(j, tempMap);
                }
            }
        }

        notifyDataSetChanged();
        defaultGridAdapter.notifyDataSetChanged();
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
    public UC_FilterDataDictionaryBean getItem(int i) {
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
//        int type = Integer.valueOf(mData.get(position).getType());

        if (convertView == null) {
            // 按当前所需的样式，确定new的布局
            // 按当前所需的样式，确定new的布局
            switch (type) {
                //选择项
                case TYPE_1:
//        if (type.equals("1")) {//选择项
                    convertView = LayoutInflater.from(mContext).inflate(R.layout.uc_filter_list_default, null);
                    holder1 = new ViewHolder1();
                    holder1.typeTitle = (TextView) convertView.findViewById(R.id.uc_filter_item_title);
                    holder1.defaultGridView = (EtongNoScrollGridView) convertView.findViewById(R.id.uc_filter_gv_item_content);

                    convertView.setTag(holder1);
                    break;
                //选择范围
                case TYPE_2:
//        } else if (type.equals("2")) {//选择范围
//                try {
                    convertView = LayoutInflater.from(mContext).inflate(R.layout.uc_filter_list_rangeseekbar, null);
                    holder2 = new ViewHolder2();
                    holder2.mRangeSbTxt = (TextView) convertView.findViewById(R.id.uc_filter_txt_rangesb);
                    holder2.mRangeSbTitleTxt = (TextView) convertView.findViewById(R.id.uc_filter_txt_rangesb_title);
                    holder2.mRangeSbUnitTxt = (TextView) convertView.findViewById(R.id.uc_filter_txt_rangesb_unit);
                    holder2.mRangeSeekBar = (UC_AdvancedFilter_RangeSeekBar) convertView.findViewById(R.id.uc_filter_seekbar_rangesb);

//                } catch (NumberFormatException e) {
//                    e.printStackTrace();
//                }
                    convertView.setTag(holder2);
                    break;
                default:
                    break;
            }

        } else {
            switch (type) {
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

                defaultGridAdapter = new UC_FilterDefaultGridAdapter(holder1.defaultGridView,
                        position, mData.get(position).getParaName(),
                        mContext,
                        UC_FilterListAdapter.this,
                        mData.get(position).getMap());

                holder1.typeTitle.setText(mData.get(position).getParaName());

                holder1.defaultGridView.setAdapter(defaultGridAdapter);
                defaultGridAdapter.notifyDataSetChanged();
                break;
            case TYPE_2:
                try {
                    holder2.mRangeSbTitleTxt.setText(mData.get(position).getParaName());//名字
                    holder2.mRangeSbUnitTxt.setText("(" + mData.get(position).getUnit() + ")");//单位

                    //设置范围
                    List<String> marks = new ArrayList<String>();
                    if (null != mData.get(position).getMaxValue()) {
                        for (int i = 0; i <= Integer.valueOf(mData.get(position).getMaxValue()); i++) {
                            marks.add(i + "");
                        }
                    }
                    CharSequence[] charSequenceItems = marks.toArray(new CharSequence[marks.size()]);
                    holder2.mRangeSeekBar.setTextMarks(charSequenceItems);//设置RangeSeekBar的数值标记数组
                    //设置RangeSeekBar数值的间距
                    holder2.mRangeSeekBar.setIntervalValue(Integer.valueOf(mData.get(position).getIntervalValue()));

                    //设置RangeSeekBar左右游标选中位置
                    holder2.mRangeSeekBar.setLeftSelection(Integer.valueOf(mRange.get(position + "MinValue")));
                    holder2.mRangeSeekBar.setRightSelection(Integer.valueOf(mRange.get(position + "MaxValue")));

                    //设置seekbar选择范围的记录
                    if (mRange.get(position + "MinValue").equals("0")) {
                        holder2.mRangeSbTxt.setText(mRange.get(position + "MaxValue") + mData.get(position).getRemark());
                    } else {
                        holder2.mRangeSbTxt.setText(mRange.get(position + "MinValue") + " - " + mRange.get(position + "MaxValue") + mData.get(position).getRemark().replace("以下", ""));
                    }

                    //当选择范围不为默认范围时将选择的范围设置进选中的map中
                    if (!mRange.get(position + "MaxValue").equals(mData.get(position).getMaxValue()) || !mRange.get(position + "MinValue").equals(mData.get(position).getMinValue())) {
                        UC_FilterRequestBean tempBean = new UC_FilterRequestBean();
                        tempBean.setType("2");
                        tempBean.setMinValue(mRange.get(position + "MinValue"));
                        tempBean.setMaxValue(mRange.get(position + "MaxValue"));
                        tempBean.setName(holder2.mRangeSbTxt.getText().toString());
                        selectMap.put(mData.get(position).getParaName(), tempBean);
                    }

                    final ViewHolder2 finalHolder = holder2;
                    holder2.mRangeSeekBar.setOnCursorChangeListener(new UC_AdvancedFilter_RangeSeekBar.OnCursorChangeListener() {
                        @Override
                        public void onLeftCursorChanged(int location, String textMark) {
                            mRange.put(position + "MinValue", textMark);
                            setChangedRangeSeekBar(position, finalHolder.mRangeSbTxt);
                        }

                        @Override
                        public void onRightCursorChanged(int location, String textMark) {
                            mRange.put(position + "MaxValue", textMark);
                            setChangedRangeSeekBar(position, finalHolder.mRangeSbTxt);
                        }
                    });
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                break;
        }

        return convertView;
    }

    /**
     * @desc (设置RangeSeekBar左右游标滑动后，记录选择的范围的显示)
     * @createtime 2016/11/4 0004-13:58
     * @author wukefan
     */
    private void setChangedRangeSeekBar(int position, TextView mRangeSbTxt) {

        if (mRange.get(position + "MinValue").equals("0")) {
            mRangeSbTxt.setText(mRange.get(position + "MaxValue") + mData.get(position).getRemark());
        } else {
            mRangeSbTxt.setText(mRange.get(position + "MinValue") + " - " + mRange.get(position + "MaxValue") + mData.get(position).getRemark().replace("以下", ""));
        }

        //将选择的范围设置进选中的map中
        UC_FilterRequestBean tempBean = new UC_FilterRequestBean();
        tempBean.setType("2");
        tempBean.setMinValue(mRange.get(position + "MinValue"));
        tempBean.setMaxValue(mRange.get(position + "MaxValue"));
        tempBean.setName(mRangeSbTxt.getText().toString());
        selectMap.put(mData.get(position).getParaName(), tempBean);
    }

    /**
     * @param isSelect      点击项是否选中
     * @param position      点击项父类所在的位置
     * @param selectPostion 点击项的位置
     * @param paraName      点击项父类的名字
     * @param itemName      点击项的名字
     * @param flag          点击项的参数
     * @desc (选择项的回调)
     * @createtime 2016/11/4 0004-14:00
     * @author wukefan
     */
    @Override
    public void answerDefault(boolean isSelect, int position, int selectPostion, String paraName, String itemName, String flag) {
        L.d("------------------->>>>" + paraName, flag + " " + isSelect);
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
                    UC_FilterDataDictionaryBean.MapBean tempMap = mData.get(position).getMap().get(i);
                    tempMap.setSelect(true);
                    mData.get(position).getMap().set(i, tempMap);
                } else {
                    UC_FilterDataDictionaryBean.MapBean tempMap = mData.get(position).getMap().get(i);
                    tempMap.setSelect(false);
                    mData.get(position).getMap().set(i, tempMap);
                }
            }
        } else {//选择项点击项未选中
            selectMap.remove(paraName);//从选中的map中移除点击项
            //相应的数据也设置未选中
            for (int i = 0; i < mData.get(position).getMap().size(); i++) {
                UC_FilterDataDictionaryBean.MapBean tempMap = mData.get(position).getMap().get(i);
                tempMap.setSelect(false);
                mData.get(position).getMap().set(i, tempMap);
            }
        }
    }

    public class ViewHolder1 {
        TextView typeTitle;//标题
        EtongNoScrollGridView defaultGridView;
    }

    public class ViewHolder2 {
        TextView mRangeSbTxt;//范围记录
        TextView mRangeSbTitleTxt;//标题
        TextView mRangeSbUnitTxt;//单位
        UC_AdvancedFilter_RangeSeekBar mRangeSeekBar;
    }

}
