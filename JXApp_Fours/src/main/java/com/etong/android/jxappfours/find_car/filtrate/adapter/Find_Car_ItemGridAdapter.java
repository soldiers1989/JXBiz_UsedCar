package com.etong.android.jxappfours.find_car.filtrate.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.GridView;

import com.etong.android.frame.utils.L;
import com.etong.android.jxappfours.R;
import com.etong.android.jxappfours.find_car.filtrate.javabeam.Find_Car_OtherItemBeam;

import java.util.HashMap;
import java.util.List;

/**
 * 找车的筛选主界面的ListView里面的GridView适配器
 * Created by Administrator on 2016/8/9 0009.
 */
public class Find_Car_ItemGridAdapter extends BaseAdapter {
    private Context context;
    private List<Find_Car_OtherItemBeam.ItemNamesBean> items;
    ICallBack iCallBack;
    private Integer bPosition;
    private boolean isAlreadyChecked = false;
    private int alreadyChecked = -1;
    private HashMap<Integer, Boolean> isSelected = new HashMap<Integer, Boolean>();
    private GridView mGridView;

    public Find_Car_ItemGridAdapter(GridView mGridView, Integer bPosition, Context context, ICallBack iCallBack, List<Find_Car_OtherItemBeam.ItemNamesBean> items) {
        this.iCallBack = iCallBack;
        this.context = context;
        this.items = items;
        this.bPosition = bPosition;
        this.mGridView = mGridView;

        //初始化每个item的选中状态为false
        for (int i = 0; i < items.size(); i++) {
            isSelected.put(i, false);
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

//        Holder holder;
//        if (convertView == null) {
//            holder = new Holder();
//
//            convertView = View.inflate((Activity) this.context,R.layout.find_car_filtrate_items_gv, null);
//            holder.cbItemName = (CheckBox) convertView.findViewById(R.id.find_car_cb_item);
//            holder.item = (TextView) convertView.findViewById(R.id.find_car_item);
//
//            convertView.setTag(holder);
//            // 绑定listener监听器，检测convertview的height
//            holder.update();
//        } else {
//            holder = (Holder) convertView.getTag();
//        }
//
//        holder.cbItemName = (CheckBox) convertView.findViewById(R.id.find_car_cb_item);
//        // 绑定tag
//        holder.cbItemName.setTag(position);
//        //  绑定当前的item，也就是convertview
//        holder.item.setTag(convertView);


        convertView = LayoutInflater.from(context).inflate(R.layout.find_car_filtrate_items_gv, null);
        final CheckBox cbItemName = (CheckBox) convertView.findViewById(R.id.find_car_cb_item);


        cbItemName.setText(items.get(position).getTitle());


        cbItemName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (Find_Car_FiltrateFragment.canClickOther) {

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
                            iCallBack.answer(false, bPosition, items.get(position));  //回调
                        } else {
                            isSelected.put(position, true);
                            L.d("++++++++++++++++>" + position, isSelected.get(position) + "");
//                        Toast.makeText(context, items.get(position).getTitle(), Toast.LENGTH_SHORT).show();
                            iCallBack.answer(true, bPosition, items.get(position));  //回调
                        }
                    } else {
//                    Toast.makeText(context, items.get(position).getTitle(), Toast.LENGTH_SHORT).show();
                        CheckBox cbTemp = (CheckBox) mGridView.getChildAt(alreadyChecked).findViewById(R.id.find_car_cb_item);
                        cbTemp.setChecked(false);
                        isSelected.put(alreadyChecked, false);
                        isSelected.put(position, true);
                        iCallBack.answer(true, bPosition, items.get(position));  //回调
                    }
//                }
            }
        });

        return convertView;
    }

//
//    public class Holder {
//
//        CheckBox cbItemName;
//        TextView item;
//
//        public void update() {
//
//            // 精确计算GridView的item高度
//
//            cbItemName.getViewTreeObserver().addOnGlobalLayoutListener(
//                    new ViewTreeObserver.OnGlobalLayoutListener() {
//                        public void onGlobalLayout() {
//                            int position = (Integer) cbItemName.getTag();
//
//                            // 这里是保证同一行的item高度是相同的！！也就是同一行是齐整的 height相等
//
//                            if (position > 0 && position % 2 == 1) {
//                                View v = (View) item.getTag();
//                                int height = v.getHeight();
//
//                                View view = mGridView.getChildAt(position - 1);
//                                int lastheight = view.getHeight();
//
//                                // 得到同一行的最后一个item和前一个item想比较，把谁的height大，就把两者中                                                                // height小的item的高度设定为height较大的item的高度一致，也就是保证同一                                                                 // 行高度相等即可
//
//                                if (height > lastheight) {
//                                    view.setLayoutParams(new GridView.LayoutParams(
//                                            GridView.LayoutParams.FILL_PARENT,
//                                            height));
//                                } else if (height < lastheight) {
//                                    v.setLayoutParams(new GridView.LayoutParams(
//                                            GridView.LayoutParams.FILL_PARENT,
//                                            lastheight));
//                                }
//                            }
//                        }
//                    });
//        }
//    }


    //回调接口
    public interface ICallBack {
        public void answer(boolean isSelect, int selectPostion, Find_Car_OtherItemBeam.ItemNamesBean itemBean);  //类B的内部接口
    }
}
