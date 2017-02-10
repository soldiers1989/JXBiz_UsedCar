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

import com.etong.android.frame.common.ImageProvider;
import com.etong.android.frame.utils.SerializableObject;
import com.etong.android.jxappfours.R;
import com.etong.android.jxappfours.vechile_details.Find_Car_VechileDetailsSelectImageActivity;
import com.etong.android.jxappfours.vechile_details.javabeam.Find_Car_VechileDetailsImageBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 查看所有的显示图片的GridView的适配器
 * Created by Administrator on 2016/8/17 0017.
 */
public class Find_Car_VechileDetailsImageSeeAllGridViewAdapter extends BaseAdapter {

    private List<Find_Car_VechileDetailsImageBean.PhotoListBean> imageList;
    private List<Find_Car_VechileDetailsImageBean> allList;
    private Context context;
    private ImageProvider mImageProvider;
    private ItemHolder childHolder;
    private int pos;
    private String brand;


    public Find_Car_VechileDetailsImageSeeAllGridViewAdapter(String brand,int pos,Context context, List<Find_Car_VechileDetailsImageBean.PhotoListBean> imageList,List<Find_Car_VechileDetailsImageBean> allList) {
        this.context = context;
        this.imageList = imageList;
        this.pos = pos;
        this.allList =allList;
        this.brand=brand;
        mImageProvider = ImageProvider.getInstance();
    }

    public void setPos(int i){
        pos=i;
    }

    @Override
    public int getCount() {
        return imageList.size();
    }

    @Override
    public Object getItem(int position) {
        return imageList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.find_car_vechile_details_image_items_gv, null);
            childHolder = new ItemHolder();
            childHolder.iv_image = (ImageView) convertView.findViewById(R.id.find_car_img_vechile_details_image);
            // 获取屏幕像素相关信息
            DisplayMetrics dm = new DisplayMetrics();
            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            wm.getDefaultDisplay().getMetrics(dm);
            int mWidth = dm.widthPixels;

            ViewGroup.LayoutParams lp = (ViewGroup.LayoutParams) childHolder.iv_image.getLayoutParams();
            //将宽度设置为屏幕的1/3
            lp.width = (mWidth - (int) (24 * dm.density + 0.5f)) / 3;
            //将高度设置为宽度的3/4(7/10)
            lp.height = ((mWidth - (int) (24 * dm.density + 0.5f)) / 3)*7/10;
            //根据屏幕大小按比例动态设置图片大小
            childHolder.iv_image.setLayoutParams(lp);
            convertView.setTag(childHolder);
        } else {
            childHolder = (ItemHolder) convertView.getTag();
        }
        mImageProvider.loadImage(childHolder.iv_image,imageList.get(position).getThumbnail(),R.mipmap.fours_default_img);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,Find_Car_VechileDetailsSelectImageActivity.class);

                Map map = new HashMap<>();
                map.put("titleName", allList.get(pos).getType());
                map.put("imagePos", position);
                map.put("allData", allList);
                map.put("brand", brand);
                 //传递数据
                final SerializableObject myMap = new SerializableObject();
                myMap.setObject(map);// 将map数据添加到封装的myMap中
                Bundle bundle = new Bundle();
                bundle.putSerializable("dataMap", myMap);
                intent.putExtras(bundle);

//                intent.putExtra("position",pos);
//                intent.putExtra("imagePos",position);
                context.startActivity(intent);
            }
        });

        return convertView;
    }


    class ItemHolder {
        ImageView iv_image;
    }
}
