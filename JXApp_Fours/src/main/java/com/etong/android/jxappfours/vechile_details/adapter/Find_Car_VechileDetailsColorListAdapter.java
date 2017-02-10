package com.etong.android.jxappfours.vechile_details.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.etong.android.jxappfours.R;
import com.etong.android.jxappfours.vechile_details.javabeam.Find_Car_VechileDetails_ColorBeam;

import java.util.List;

/**
 * 选择颜色的ListAdapter
 * Created by Administrator on 2016/8/15 0015.
 */
public class Find_Car_VechileDetailsColorListAdapter extends BaseAdapter {

    private Context context;
    private List<Find_Car_VechileDetails_ColorBeam> itemList;
    private int mPosition;
    private String mSelectItem;
    private ListView mListView;
    private int itemPositon;
    private LCallBack mLCallBack;
    private TextView tvItemName;
    private ImageView ivItemImage;
    private ImageView ivSelectImage;
    private TextView tvItemTitle;

    public Find_Car_VechileDetailsColorListAdapter(int itemPositon, ListView mListView, Context context, LCallBack mLCallBack, List<Find_Car_VechileDetails_ColorBeam> itemList) {
        this.context = context;
        this.itemList = itemList;
        this.mListView = mListView;
        this.itemPositon = itemPositon;
        this.mLCallBack = mLCallBack;
        mPosition = 0;
        mSelectItem = "全部颜色";
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

        convertView = LayoutInflater.from(context).inflate(R.layout.find_car_vechile_details_parameter_color_items_lv, null);

        RelativeLayout rlItemZone = (RelativeLayout) convertView.findViewById(R.id.find_car_rl_vd_color_parameter);

        tvItemTitle = (TextView) convertView.findViewById(R.id.find_car_txt_vd_parameter_color_title);

        ivItemImage = (ImageView) convertView.findViewById(R.id.find_car_img_vd_parameter_color);
        tvItemName = (TextView) convertView.findViewById(R.id.find_car_txt_vd_parameter_color_item);
        ivSelectImage = (ImageView) convertView.findViewById(R.id.find_car_img_vd_parameter_color_checked);

        tvItemName.setTextColor(Color.parseColor("#222222"));

        if (itemList.get(position).isRoot()) {
            tvItemTitle.setVisibility(View.VISIBLE);
            rlItemZone.setVisibility(View.GONE);
            tvItemTitle.setText(itemList.get(position).getTitle());

        } else {
            tvItemTitle.setVisibility(View.GONE);
            rlItemZone.setVisibility(View.VISIBLE);
        }

        ivItemImage.setBackgroundResource(getImageIdByName(itemList.get(position).getImage()));

//        if (position == 0) {
//            ViewGroup.LayoutParams params=rlItemZone.getLayoutParams();
//            params.height =90;
//            rlItemZone.setLayoutParams(params);
//        }
        tvItemName.setText(itemList.get(position).getTitle());

        if (itemList.get(position).getTitle().equals(mSelectItem)) {
            tvItemName.setPressed(true);
            tvItemName.setTextColor(context.getResources().getColor(R.color.select_blue));
            ivSelectImage.setVisibility(View.VISIBLE);
            mPosition = position;
        }

        rlItemZone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(itemList.get(position));
                mSelectItem = itemList.get(position).getTitle();
                mPosition = position;
//                tvItemName.setPressed(true);
//                tvItemName.setTextColor(context.getResources().getColor(R.color.select_blue));
//                ivSelectImage.setVisibility(View.VISIBLE);
                //设置选中状态
                mListView.getChildAt(position).findViewById(R.id.find_car_txt_vd_parameter_color_item).setPressed(true);
                TextView tempTv = (TextView) mListView.getChildAt(position).findViewById(R.id.find_car_txt_vd_parameter_color_item);
                tempTv.setTextColor(context.getResources().getColor(R.color.select_blue));
                mListView.getChildAt(position).findViewById(R.id.find_car_img_vd_parameter_color_checked).setVisibility(View.VISIBLE);
                mLCallBack.answerColor(itemPositon, itemList.get(position).getTitle());
//                //把上次选中项设置为未选中
//                if (mPosition != position) {
//                    mListView.getChildAt(mPosition).findViewById(R.id.find_car_txt_vd_parameter_color_item).setPressed(false);
//                    mListView.getChildAt(mPosition).findViewById(R.id.find_car_img_vd_parameter_color_checked).setVisibility(View.GONE);
//                }
            }
        });

//        rlItemZone.setOnTouchListener(new View.OnTouchListener() {
//
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                // TODO Auto-generated method stub
//                //设置选中时出现选中的小图标
//                mListView.getChildAt(position).findViewById(R.id.find_car_img_vd_parameter_color_checked).setVisibility(View.VISIBLE);
//                return false;
//            }
//        });

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
        public void answerColor(int postion, String str);  //类B的内部接口
    }
}

