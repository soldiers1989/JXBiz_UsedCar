package com.etong.android.jxappfours.find_car.filtrate.adapter;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.etong.android.jxappfours.R;
import com.etong.android.jxappfours.find_car.filtrate.javabeam.Find_Car_LevelItemBeam;

import java.util.List;

/**
 * 找车的筛选主界面的级别的GridView适配器
 * Created by Administrator on 2016/8/9 0009.
 */
public class Find_Car_LevelGridAdapter extends BaseAdapter {
    private Context context;
    private List<Find_Car_LevelItemBeam> itemList;
    private int clickTemp = -1;
    private boolean[] isChecked;
    LCallBack lCallBack;
    private TextView tvItemName;
    private ImageView ivItemImage;
    private LinearLayout llView;
    private GridView mGridView;


    public Find_Car_LevelGridAdapter(GridView mGridView,Context context, LCallBack lCallBack, List<Find_Car_LevelItemBeam> itemList) {
        this.context = context;
        this.itemList = itemList;
        this.lCallBack = lCallBack;
        this.mGridView=mGridView;

        isChecked = new boolean[itemList.size()];
        for (int i = 0; i < itemList.size(); i++) {
            isChecked[i] = false;
        }

    }

//    //标识选择的Item
//    public void setSeclection(int position) {
//        clickTemp = position;
//    }

    //重置每个item的选中状态为false
    public void setCleanSelect(){
        for (int i = 0; i < itemList.size(); i++) {
            isChecked[i] = false;
        }
    }

    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public Object getItem(int position) {
        return itemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {


        convertView = LayoutInflater.from(context).inflate(R.layout.find_car_filtrate_level_items_gv, null);

        llView = (LinearLayout) convertView.findViewById(R.id.find_car_ll_filtrate);
        tvItemName = (TextView) convertView.findViewById(R.id.find_car_txt_level_name);
        ivItemImage = (ImageView) convertView.findViewById(R.id.find_car_img_level_icon);
        tvItemName.setText(itemList.get(position).getItemName());
        ivItemImage.setBackgroundResource(getImageIdByName(itemList.get(position).getItemIconName() + "_grey"));

        // 获取屏幕像素相关信息
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(dm);
        int mWidth = dm.widthPixels;

        ViewGroup.LayoutParams lp = (ViewGroup.LayoutParams) llView.getLayoutParams();
        //将宽度设置为屏幕的1/3
        lp.width = mWidth / 3;
        lp.height = (mWidth / 3) * 7 / 10;
        llView.setLayoutParams(lp);

//        // 点击改变选中的item的图片和文字颜色
//        if (clickTemp == position) {
//            if (!isChecked[position]) {
//                //设置选中状态
//                ivItemImage.setBackgroundResource(getImageIdByName(itemList.get
//                        (position).getItemIconName()));
////                tvItemName.setTextColor(context.getResources().getColor
////                        (R.color.select_blue));
//                lCallBack.answer(true,itemList.get(position).getId());
//                setCleanSelect();
//                isChecked[position] = true;
//                tvItemName.setSelected(true);
//            } else {
//                ivItemImage.setBackgroundResource(getImageIdByName(itemList.get
//                        (position).getItemIconName() + "_grey"));
//                lCallBack.answer(false,itemList.get(position).getId());
//                setCleanSelect();
//                tvItemName.setSelected(false);
//            }
//        }
        // 点击改变选中的item的图片和文字颜色
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (Find_Car_FiltrateFragment.canClick) {
                    if (!isChecked[position]) {
                        //设置选中状态
                        mGridView.getChildAt(position).findViewById(R.id.find_car_img_level_icon).setBackgroundResource(getImageIdByName(itemList.get(position).getItemIconName()));
                        mGridView.getChildAt(position).findViewById(R.id.find_car_txt_level_name).setSelected(true);
//                ivItemImage.setBackgroundResource(getImageIdByName(itemList.get
//                        (position).getItemIconName()));
//                tvItemName.setSelected(true);
                        lCallBack.answer(true, itemList.get(position).getId());
                        setCleanSelect();
                        isChecked[position] = true;
                        //设置上一选中项为未选中
                        if (clickTemp != -1 && clickTemp != position) {
                            mGridView.getChildAt(clickTemp).findViewById(R.id.find_car_img_level_icon).setBackgroundResource(getImageIdByName(itemList.get(clickTemp).getItemIconName() + "_grey"));
                            mGridView.getChildAt(clickTemp).findViewById(R.id.find_car_txt_level_name).setSelected(false);
                        }
                        clickTemp = position;//记录选中项
                    } else {
//                ivItemImage.setBackgroundResource(getImageIdByName(itemList.get
//                        (position).getItemIconName() + "_grey"));
//                tvItemName.setSelected(false);
                        mGridView.getChildAt(position).findViewById(R.id.find_car_img_level_icon).setBackgroundResource(getImageIdByName(itemList.get(position).getItemIconName() + "_grey"));
                        mGridView.getChildAt(position).findViewById(R.id.find_car_txt_level_name).setSelected(false);
                        lCallBack.answer(false, itemList.get(position).getId());
                        setCleanSelect();
                    }
//                }
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
            Class<com.etong.android.jxappfours.R.mipmap> cls = R.mipmap.class;
            try {
                value = cls.getDeclaredField(name).getInt(null);
            } catch (Exception e) {

            }
        }
        return value;
    }

    //回调接口
    public interface LCallBack {
        public void answer(boolean isSelect,Integer id);  //类B的内部接口
    }

}
