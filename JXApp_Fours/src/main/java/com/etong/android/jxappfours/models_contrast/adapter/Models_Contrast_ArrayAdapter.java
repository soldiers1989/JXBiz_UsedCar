package com.etong.android.jxappfours.models_contrast.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.etong.android.frame.utils.ListAdapter;
import com.etong.android.jxappfours.R;
import com.etong.android.jxappfours.models_contrast.javabean.Models_Contrast_Add_VechileStyle;

import java.util.ArrayList;
import java.util.List;

/**
 *
 *自定义adapter实现车型对比添加车款的回调
 * Created by Administrator on 2016/8/16.
 */
public class Models_Contrast_ArrayAdapter extends BaseAdapter {
    private Context mContext;
    private List<Models_Contrast_Add_VechileStyle> data;
    private AdapterCallback adapterCallback;
    public Models_Contrast_ArrayAdapter(){

    }
    public  Models_Contrast_ArrayAdapter(Context context, List<Models_Contrast_Add_VechileStyle> data) {
        this.mContext = context;
        this.data = data;


    }
    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {


        view = View.inflate(mContext, R.layout.models_contrast_activity_list_item, null);
        final ImageButton image_click= (ImageButton) view.findViewById(R.id.models_contrast_img_click_state);
        final TextView click_name= (TextView) view.findViewById(R.id.models_contrast_txt_click_name);
        final ViewGroup vp = (ViewGroup) view.findViewById(R.id.models_contrast_root_content);

        image_click.setSelected(data.get(i).isChecked());
        click_name.setSelected(data.get(i).isChecked());
        data.get(i).setChecked(data.get(i).isChecked());
        vp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                image_click.setSelected(!image_click.isSelected());
                data.get(i).setChecked(!data.get(i).isChecked());
                click_name.setSelected(!click_name.isSelected());

                int isCheckedNumber = 0;

                for (int i=0; i<data.size(); i++) {
                    if (data.get(i).isChecked()) {
                        isCheckedNumber ++;
                    }

                }
                adapterCallback.callBack(isCheckedNumber,data.get(i));

            }
        });
        click_name.setText(data.get(i).getTitle());
        return view;
    }

    //回调接口
    public interface AdapterCallback {
        public void callBack(int isCheckedNum,Models_Contrast_Add_VechileStyle add);
    }

    public void setCallback(AdapterCallback adapterCallback) {
        this.adapterCallback = adapterCallback;
    }
}
