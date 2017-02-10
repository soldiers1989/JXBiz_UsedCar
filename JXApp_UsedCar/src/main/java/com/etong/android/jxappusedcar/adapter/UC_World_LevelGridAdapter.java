package com.etong.android.jxappusedcar.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.etong.android.frame.utils.L;
import com.etong.android.jxappusedcar.R;
import com.etong.android.jxappusedcar.javabean.UC_World_FiltrateListItemBeam;

import java.util.HashMap;
import java.util.List;

/**
 * 二手车的筛选主界面的ListView里面的车级别GridView适配器
 * Created by Administrator on 2016/8/9 0009.
 */
public class UC_World_LevelGridAdapter extends BaseAdapter {
    private Context context;
    private List<UC_World_FiltrateListItemBeam.MapBean> items;
    UC_LICallBack iCallBack;
    private Integer bPosition;
    private boolean isAlreadyChecked = false;
    private int alreadyChecked = -1;
    private HashMap<Integer, Boolean> isSelected = new HashMap<Integer, Boolean>();
    private GridView mGridView;
    private String paraName;

    public UC_World_LevelGridAdapter(GridView mGridView, Integer bPosition, String paraName, Context context, UC_LICallBack iCallBack, List<UC_World_FiltrateListItemBeam.MapBean> items) {
        this.iCallBack = iCallBack;
        this.context = context;
        this.items = items;
        this.bPosition = bPosition;
        this.mGridView = mGridView;
        this.paraName = paraName;

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

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        convertView = LayoutInflater.from(context).inflate(R.layout.used_car_world_filtrate_gv_item_level, null);
        final CheckBox cbItemName = (CheckBox) convertView.findViewById(R.id.uc_world_cb_level);

        LinearLayout llView = (LinearLayout) convertView.findViewById(R.id.uc_world_ll_level);

        // 获取屏幕像素相关信息
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(dm);
        int mWidth = dm.widthPixels;

        ViewGroup.LayoutParams lp = (ViewGroup.LayoutParams) cbItemName.getLayoutParams();
        //将宽度设置为屏幕的1/3
        lp.width = mWidth / 3;
        lp.height = (mWidth / 3) * 7 / 10;
        cbItemName.setLayoutParams(lp);

        if(0!=getImageIdByName("used_car_filtrate_"+items.get(position).getKey()+"_selector")){
            Drawable drawable=context.getResources().getDrawable(getImageIdByName("used_car_filtrate_"+items.get(position).getKey()+"_selector"));
            cbItemName.setCompoundDrawablesRelativeWithIntrinsicBounds(null,drawable,null,null);
        }

        cbItemName.setText(items.get(position).getValue());

        if (isSelected.get(position)) {
            cbItemName.setChecked(true);
            iCallBack.answerDefault(true, bPosition, position, paraName, items.get(position).getValue(), items.get(position).getKey());
        } else {
            cbItemName.setChecked(false);
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
                            iCallBack.answerDefault(false, bPosition, position, paraName, items.get(position).getValue(), items.get(position).getKey());  //回调
                        } else {
                            isSelected.put(position, true);
                            L.d("++++++++++++++++>" + position, isSelected.get(position) + "");
//                        Toast.makeText(context, items.get(position).getTitle(), Toast.LENGTH_SHORT).show();
                            iCallBack.answerDefault(true, bPosition, position, paraName, items.get(position).getValue(), items.get(position).getKey());  //回调
                        }
                    } else {
//                    Toast.makeText(context, items.get(position).getTitle(), Toast.LENGTH_SHORT).show();
                        CheckBox cbTemp = (CheckBox) mGridView.getChildAt(alreadyChecked).findViewById(R.id.uc_world_cb_level);
                        cbTemp.setChecked(false);
                        isSelected.put(alreadyChecked, false);
                        isSelected.put(position, true);
                        iCallBack.answerDefault(true, bPosition, position, paraName, items.get(position).getValue(), items.get(position).getKey());  //回调
                    }
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
            Class<R.drawable> cls = R.drawable.class;
            try {
                value = cls.getDeclaredField(name).getInt(null);
            } catch (Exception e) {

            }
        }
        return value;
    }

    //回调接口
    public interface UC_LICallBack {
        /**
         * @param isSelect      点击项是否选中
         * @param position      点击项父类所在的位置
         * @param selectPostion 点击项的位置
         * @param paraName      点击项父类的名字
         * @param itemName      点击项的名字
         * @param flag          点击项的参数
         */
//        public void answerLevel(boolean isSelect, int position, int selectPostion, String paraName, String itemName, String flag);  //类B的内部接口
        public void answerDefault(boolean isSelect, int position, int selectPostion, String paraName, String itemName, String flag);  //类B的内部接口
    }
}
