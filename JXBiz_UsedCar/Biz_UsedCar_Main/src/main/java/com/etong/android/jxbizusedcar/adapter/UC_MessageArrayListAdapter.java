package com.etong.android.jxbizusedcar.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.etong.android.frame.common.ImageProvider;
import com.etong.android.jxbizusedcar.R;
import com.etong.android.jxbizusedcar.activity.UC_MessageWebViewActivity;
import com.etong.android.jxbizusedcar.bean.UC_MessageWebViewBean;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @desc (这里用一句话描述这个类的作用)
 * @createtime 2016/11/17 0017--10:53
 * @Created by wukefan.
 */
public class UC_MessageArrayListAdapter extends ArrayAdapter<UC_MessageWebViewBean> {

    //    private List<UC_MessageWebViewBean> mListDatas;
    private Context mContext;
    private int mResource;

    public UC_MessageArrayListAdapter(Context context) {
        super(context, R.layout.uc_message_list_item);
        this.mContext = context;
        this.mResource = R.layout.uc_message_list_item;
//        mListDatas = new ArrayList();
    }

    public void addAllData(List<UC_MessageWebViewBean> list) {
        addAll(list);
//        mListDatas.addAll(list);
        notifyDataSetChanged();
    }

    public void addFirstData(List<UC_MessageWebViewBean> list) {
        clear();
        addAll(list);
//        mListDatas.clear();
//        mListDatas.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(mResource, null);
            viewHolder = new ViewHolder();

            viewHolder.image = (ImageView) convertView.findViewById(R.id.uc_message_img_pic);
            viewHolder.title = (TextView) convertView.findViewById(R.id.uc_message_txt_title);
            viewHolder.time = (TextView) convertView.findViewById(R.id.uc_message_txt_time);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final UC_MessageWebViewBean item = getItem(position);

//        ImageProvider.getInstance().loadImage(viewHolder.image, item.getImg(), R.mipmap.uc_image_loading);
        // 加载图片的选项
        DisplayImageOptions displayImageOptions  = new DisplayImageOptions.Builder()
                .cacheInMemory(false)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .imageScaleType(ImageScaleType.EXACTLY)
                .showImageForEmptyUri(com.etong.android.jxappusedcar.R.mipmap.uc_image_loading)
                .showImageOnLoading(com.etong.android.jxappusedcar.R.mipmap.uc_image_loading)
                .showImageOnFail(com.etong.android.jxappusedcar.R.mipmap.uc_image_loading).build();
        ImageLoader.getInstance().displayImage(item.getImg(), viewHolder.image, displayImageOptions);

        viewHolder.title.setText(item.getTitle());

        viewHolder.time.setText(getDateToString(item.getCreate_time()));
            convertView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, UC_MessageWebViewActivity.class);//跳转到资讯详情
                intent.putExtra("id", item.getId());//资讯id
//                        intent.putExtra("title", data.getTitle());
                mContext.startActivity(intent);
            }
        });

        return convertView;
    }

    class ViewHolder {

        public TextView title;
        public TextView time;
        public ImageView image;
    }

    /*时间戳转换成字符串*/
    public static String getDateToString(long time) {
        Date d = new Date(time);
        SimpleDateFormat sf = new SimpleDateFormat("yyyy年MM月dd日");
        return sf.format(d);
    }
}
