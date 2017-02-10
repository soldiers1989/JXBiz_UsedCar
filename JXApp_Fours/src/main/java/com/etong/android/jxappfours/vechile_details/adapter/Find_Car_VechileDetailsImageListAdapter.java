package com.etong.android.jxappfours.vechile_details.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.etong.android.frame.common.ImageProvider;
import com.etong.android.frame.utils.ListAdapter;
import com.etong.android.frame.utils.SerializableObject;
import com.etong.android.frame.widget.EtongNoScrollGridView;
import com.etong.android.jxappfours.R;
import com.etong.android.jxappfours.vechile_details.Find_Car_VechileDetailsImageSeeAllActivity;
import com.etong.android.jxappfours.vechile_details.Find_Car_VechileDetailsSelectImageActivity;
import com.etong.android.jxappfours.vechile_details.javabeam.Find_Car_VechileDetailsImageBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 加载显示车辆图片的ListView适配器
 * Created by Administrator on 2016/8/15 0015.
 */
public class Find_Car_VechileDetailsImageListAdapter<T> extends BaseAdapter {

    private Context context;
    private List<Find_Car_VechileDetailsImageBean> itemList;
    private ListAdapter<Find_Car_VechileDetailsImageBean.PhotoListBean> gridAdapter;
    private ImageProvider mImageProvider;
    private String brand;

    public Find_Car_VechileDetailsImageListAdapter(String brand,Context context, List<Find_Car_VechileDetailsImageBean> itemList) {
        this.context = context;
        this.itemList = itemList;
        this.brand=brand;
        mImageProvider = ImageProvider.getInstance();
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

        convertView = LayoutInflater.from(context).inflate(R.layout.find_car_vechile_details_parameter_lv, null);

        TextView tvTitleName = (TextView) convertView.findViewById(R.id.find_car_txt_vechile_details_parameter_title);
        TextView tvImageNum = (TextView) convertView.findViewById(R.id.find_car_txt_vechile_details_parameter_imagenum);
        TextView BtnLookAll = (TextView) convertView.findViewById(R.id.find_car_txt_vechile_details_parameter_findall);

        EtongNoScrollGridView mGridView = (EtongNoScrollGridView) convertView.findViewById(R.id.find_car_gv_vechile_details_parameter);

        //ListView里面嵌套的GridView的适配器
        gridAdapter = new ListAdapter<Find_Car_VechileDetailsImageBean.PhotoListBean>(context, R.layout.find_car_vechile_details_image_items_gv) {
            @Override
            protected void onPaint(View view, Find_Car_VechileDetailsImageBean.PhotoListBean data, final int pos) {

                ImageView imageView = (ImageView) view.findViewById(R.id.find_car_img_vechile_details_image);
                // 获取屏幕像素相关信息
                DisplayMetrics dm = new DisplayMetrics();
                WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
                wm.getDefaultDisplay().getMetrics(dm);
                int mWidth = dm.widthPixels;
                ViewGroup.LayoutParams lp = (ViewGroup.LayoutParams) imageView.getLayoutParams();
                //将宽度设置为屏幕的1/3
                lp.width = (mWidth - (int) (24 * dm.density + 0.5f)) / 3;
                //将高度设置为宽度的3/4(7/10)
                lp.height = ((mWidth - (int) (24 * dm.density + 0.5f)) / 3)*7/10;
                //根据屏幕大小按比例动态设置图片大小
                imageView.setLayoutParams(lp);
//                imageView.setLayoutParams(new LinearLayout.LayoutParams((mWidth - (int) (24 * dm.density + 0.5f)) / 3,((mWidth - (int) (24 * dm.density + 0.5f)) / 3)*3/4));

                mImageProvider.loadImage(imageView, data.getThumbnail(),R.mipmap.fours_default_img);

                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context,Find_Car_VechileDetailsSelectImageActivity.class);

                        Map map = new HashMap<>();
                        map.put("titleName", itemList.get(position).getType());
                        map.put("imagePos",pos);
                        map.put("allData",itemList);
                        map.put("brand", brand);
                        // 传递数据
                        final SerializableObject myMap = new SerializableObject();
                        myMap.setObject(map);// 将map数据添加到封装的myMap中
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("dataMap", myMap);
                        intent.putExtras(bundle);

//                        intent.putExtra("position",position);
//                        intent.putExtra("imagePos",pos);
                        context.startActivity(intent);
                    }
                });
            }
        };

        mGridView.setAdapter(gridAdapter);
        List<Find_Car_VechileDetailsImageBean.PhotoListBean> imageList = new ArrayList<Find_Car_VechileDetailsImageBean.PhotoListBean>();
        //如果图片张数大于6个就只显示6个
        if (itemList.get(position).getPhotoList().size() > 6) {
            for (int i = 0; i < 6; i++) {
                imageList.add(itemList.get(position).getPhotoList().get(i));
            }
            gridAdapter.addAll(imageList);
        } else {
            gridAdapter.addAll(itemList.get(position).getPhotoList());
        }
        gridAdapter.notifyDataSetChanged();
        tvImageNum.setText(itemList.get(position).getPhotoList().size() + "张");
        tvTitleName.setText(itemList.get(position).getType());

        //查看所有按钮点击事件
        BtnLookAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context,Find_Car_VechileDetailsImageSeeAllActivity.class);
                Map map = new HashMap<>();
                map.put("titleName", itemList.get(position).getType());
                map.put("data",itemList.get(position).getPhotoList());
                map.put("position",position);
                map.put("allData",itemList);
                map.put("brand",brand);
                // 传递数据
                final SerializableObject myMap = new SerializableObject();
                myMap.setObject(map);// 将map数据添加到封装的myMap中
                Bundle bundle = new Bundle();
                bundle.putSerializable("dataMap", myMap);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });

        return convertView;
    }


}

