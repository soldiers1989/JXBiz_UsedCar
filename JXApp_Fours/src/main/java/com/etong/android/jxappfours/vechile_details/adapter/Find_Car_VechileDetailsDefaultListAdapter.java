package com.etong.android.jxappfours.vechile_details.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.etong.android.jxappfours.R;

import java.util.List;

/**
 * 选择类型的ListAdapter
 * Created by Administrator on 2016/8/15 0015.
 */
public class Find_Car_VechileDetailsDefaultListAdapter extends BaseAdapter{

    private Context context;
    private List<String> itemList;
    private int mPosition;
    private String mSelectItem;
    private ListView mListView;
    private  int itemPositon;
    private ICallBack mICallBack;
    private TextView tvItemName;
    private ImageView ivItemImage;

    public Find_Car_VechileDetailsDefaultListAdapter(int itemPositon,ListView mListView, Context context,ICallBack mICallBack,List<String> itemList) {
        this.context = context;
        this.itemList = itemList;
        this.mListView = mListView;
        this.itemPositon = itemPositon;
        this.mICallBack =mICallBack;
        mPosition=0;
        mSelectItem= "全部类型";
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
    public View getView(final int position, View convertView, ViewGroup viewGroup) {

        convertView = LayoutInflater.from(context).inflate(R.layout.find_car_vechile_details_parameter_defualt_items_lv, null);

        RelativeLayout rlItemZone = (RelativeLayout) convertView.findViewById(R.id.find_car_rl_vd_parameter);
        tvItemName = (TextView) convertView.findViewById(R.id.find_car_txt_vd_parameter_item);
        ivItemImage = (ImageView) convertView.findViewById(R.id.find_car_img_vd_parameter_checked);

        tvItemName.setTextColor(Color.parseColor("#222222"));

        if (position == 0) {
            rlItemZone.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        }
        tvItemName.setText(itemList.get(position));

        if (itemList.get(position).equals(mSelectItem)) {
            tvItemName.setPressed(true);
            tvItemName.setTextColor(context.getResources().getColor(R.color.select_blue));
            ivItemImage.setVisibility(View.VISIBLE);
            mPosition = position;
        }

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(itemList.get(position));
                mSelectItem = itemList.get(position);
                mPosition = position;
//                tvItemName.setPressed(true);
//                tvItemName.setTextColor(context.getResources().getColor(R.color.select_blue));
//                ivItemImage.setVisibility(View.VISIBLE);
                //设置选中状态
                mListView.getChildAt(position).findViewById(R.id.find_car_txt_vd_parameter_item).setPressed(true);
                TextView tempTv = (TextView) mListView.getChildAt(position).findViewById(R.id.find_car_txt_vd_parameter_color_item);
                tempTv.setTextColor(context.getResources().getColor(R.color.select_blue));
                mListView.getChildAt(position).findViewById(R.id.find_car_img_vd_parameter_checked).setVisibility(View.VISIBLE);
                mICallBack.answer(itemPositon,itemList.get(position),position);
//                //把上次选中项设置为未选中
//                if (mPosition != position) {
//                    mListView.getChildAt(mPosition).findViewById(R.id.find_car_txt_vd_parameter_item).setPressed(false);
//                    mListView.getChildAt(mPosition).findViewById(R.id.find_car_img_vd_parameter_checked).setVisibility(View.GONE);
//                }
            }
        });

//        convertView.setOnTouchListener(new View.OnTouchListener() {
//
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                // TODO Auto-generated method stub
//                    //设置选中时出现选中的小图标
//                    mListView.getChildAt(position).findViewById(R.id.find_car_img_vd_parameter_checked).setVisibility(View.VISIBLE);
//                return false;
//            }
//        });

        return convertView;
    }

    //回调接口
    public interface ICallBack {
        public void answer(int postion, String str,int pos);  //类B的内部接口
    }
}

