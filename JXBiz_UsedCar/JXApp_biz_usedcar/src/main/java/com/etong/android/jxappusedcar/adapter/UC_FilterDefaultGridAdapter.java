package com.etong.android.jxappusedcar.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.GridView;

import com.etong.android.jxappusedcar.R;
import com.etong.android.jxappusedcar.bean.UC_FilterDataDictionaryBean;
import com.etong.android.jxappusedcar.utils.ColorImageUtils;
import com.etong.android.frame.utils.L;

import java.util.HashMap;
import java.util.List;

/**
 * 二手车的筛选主界面的ListView里面的GridView适配器
 * Created by Administrator on 2016/10/24 0009.
 */
public class UC_FilterDefaultGridAdapter<T> extends BaseAdapter {
    private Context context;
    private List<UC_FilterDataDictionaryBean.MapBean> items;
    UC_ICallBack iCallBack;
    private Integer bPosition;
    private String paraName;
    private boolean isAlreadyChecked = false;
    private int alreadyChecked = -1;
    private HashMap<Integer, Boolean> isSelected = new HashMap<Integer, Boolean>();
    private GridView mGridView;


    public UC_FilterDefaultGridAdapter(GridView mGridView, Integer bPosition,String paraName, Context context, UC_ICallBack iCallBack, List<UC_FilterDataDictionaryBean.MapBean> items) {
        this.iCallBack = iCallBack;
        this.context = context;
        this.items = items;
        this.bPosition = bPosition;
        this.paraName = paraName;
        this.mGridView = mGridView;

        //初始化每个item的选中状态为false
        for (int i = 0; i < items.size(); i++) {
            if(items.get(i).isSelect()){
                isSelected.put(i, true);
            }else {
                isSelected.put(i, false);
            }
        }
    }

    //获取每个item的选中状态
    public HashMap<Integer, Boolean> getSelectCondition() {
        return isSelected;
    }


    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        convertView = LayoutInflater.from(context).inflate(R.layout.uc_filter_gv_item_default, null);
        final CheckBox cbItemName = (CheckBox) convertView.findViewById(R.id.uc_world_cb_item);

        cbItemName.setText(items.get(position).getValue());

        if (isSelected.get(position)) {
            cbItemName.setChecked(true);
            iCallBack.answerDefault(true, bPosition,position,paraName,items.get(position).getValue(), items.get(position).getKey());
        } else {
            cbItemName.setChecked(false);
        }

        if (null != items.get(position).getRgb()) {
            Drawable drawable = ColorImageUtils.colorToImage(items.get(position).getRgb(), context);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {

                // 获取屏幕像素相关信息
                DisplayMetrics dm = new DisplayMetrics();
                WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
                wm.getDefaultDisplay().getMetrics(dm);
                int mWidth = (dm.widthPixels - ((int) (40 * dm.density + 0.5f))) / 3;
                int childWidth = items.get(position).getValue().length() * 26 + (dm.widthPixels / 11) + 12;

                int bound = mWidth - childWidth;
                cbItemName.setCompoundDrawablesRelativeWithIntrinsicBounds(drawable, null, null, null);
                cbItemName.setPadding(bound / 2 + 10, 0, bound / 2 - 10, 0);

            }
        }


        cbItemName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //循环判断checkbox是否有选中的
                for (int i = 0; i < isSelected.size(); i++) {
                    if (isSelected.get(i) == true) {
                        if (i == position) {//如果选中的checkbox是当前项则isAlreadyChecked设置为false然后break
                            isAlreadyChecked = false;
                            break;
                        }
                        isAlreadyChecked = true;//如果选中的checkbox不是当前项则isAlreadyChecked设置为true然后break
                        alreadyChecked = i;
                        break;
                    }
                }
                //如果选中的checkbox是当前项则进入下一判断
                if (!isAlreadyChecked) {
                    //判断点击的item是否已经是选中状态，如果是则取消选中，否则为选中，点击后并进行回调
                    if (isSelected.get(position)) {
                        isSelected.put(position, false);
                        L.d("++++++++++++++++>" + position, isSelected.get(position) + "");
                        iCallBack.answerDefault(false, bPosition,position,paraName,items.get(position).getValue(), items.get(position).getKey());  //回调
                    } else {
                        isSelected.put(position, true);
                        L.d("++++++++++++++++>" + position, isSelected.get(position) + "");
//                        Toast.makeText(context, items.get(position).getTitle(), Toast.LENGTH_SHORT).show();
                        iCallBack.answerDefault(true, bPosition,position,paraName,items.get(position).getValue(), items.get(position).getKey());  //回调
                    }
                } else {
//                    Toast.makeText(context, items.get(position).getTitle(), Toast.LENGTH_SHORT).show();
                    CheckBox cbTemp = (CheckBox) mGridView.getChildAt(alreadyChecked).findViewById(R.id.uc_world_cb_item);
                    cbTemp.setChecked(false);
                    isSelected.put(alreadyChecked, false);
                    isSelected.put(position, true);
                    iCallBack.answerDefault(true, bPosition,position,paraName,items.get(position).getValue(), items.get(position).getKey());  //回调
                }
//                }
            }
        });


        return convertView;
    }


    //回调接口
    public interface UC_ICallBack {
        /**
         * @param isSelect  点击项是否选中
         * @param position  点击项父类所在的位置
         * @param selectPostion  点击项的位置
         * @param paraName   点击项父类的名字
         * @param itemName  点击项的名字
         * @param flag   点击项的参数
         */
        public void answerDefault(boolean isSelect,int position,int selectPostion,String paraName, String itemName,String flag);  //类B的内部接口
    }
}