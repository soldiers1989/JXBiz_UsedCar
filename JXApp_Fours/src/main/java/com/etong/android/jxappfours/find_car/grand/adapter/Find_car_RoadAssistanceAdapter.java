package com.etong.android.jxappfours.find_car.grand.adapter;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.etong.android.frame.permissions.PermissionsManager;
import com.etong.android.frame.permissions.PermissionsResultAction;
import com.etong.android.frame.utils.CToast;
import com.etong.android.frame.utils.DefinedToast;
import com.etong.android.frame.utils.MarkUtils;
import com.etong.android.frame.widget.dialog.Default_ShowDialog;
import com.etong.android.jxappfours.R;
import com.etong.android.jxappfours.find_car.grand.bean.FC_roadassiBean;
import com.etong.android.jxappfours.find_car.grand.view.FC_RoadCallPhoneDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ellison.Sun on 2016/8/13.
 *
 * 道路救援适配器
 */
public class Find_car_RoadAssistanceAdapter extends BaseAdapter {

    private Context mContext;
    LayoutInflater mInflater;

    private List<FC_roadassiBean> mListData;

    public Find_car_RoadAssistanceAdapter(Context context) {
        this.mContext = context;
        mListData = new ArrayList<FC_roadassiBean>();
        mInflater = LayoutInflater.from(context);
    }

    public void updateCompanyData(List<FC_roadassiBean> listData) {
        this.mListData = listData;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mListData.size();
    }

    @Override
    public Object getItem(int i) {
        return mListData.get(i);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.find_car_road_assistance_list_item, null);
            holder = new ViewHolder();
            holder.item_serial_number = (TextView) convertView.findViewById(R.id.item_serial_number);
            holder.item_assistance_title = (TextView) convertView.findViewById(R.id.item_assistance_title);
            holder.item_assistance_phonenumber = (TextView) convertView.findViewById(R.id.item_assistance_phonenumber);
            holder.item_assistance_phone = (ImageView) convertView.findViewById(R.id.item_assistance_phone);
            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder)convertView.getTag();
        }

        if (position <9) {
            position = position + 1;
            holder.item_serial_number.setText("0"+position);
        } else {
            holder.item_serial_number.setText(position + 1 + "");
        }
        holder.item_assistance_title.setText(mListData.get(position).getCompanyName());
        holder.item_assistance_phonenumber.setText(mListData.get(position).getPhone());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(mContext, "拨打电话 :" +holder.item_assistance_phonenumber.getText(), Toast.LENGTH_SHORT).show();
                final String phone = (String) holder.item_assistance_phonenumber.getText().toString();
                PermissionsManager.getInstance()
                        .requestPermissionsIfNecessaryForResult(
                                (Activity)mContext,
                                new String[]{Manifest.permission.CALL_PHONE},
                                new PermissionsResultAction() {

                                    @Override
                                    public void onGranted() {
                                        callPhoneDialog(phone);
                                    }

                                    @Override
                                    public void onDenied(String permission) {
//                                        Toast.makeText(mContext, "授权失败，无法完成操作！", Toast.LENGTH_SHORT).show();
//                                        CToast.toastMessage("授权失败，无法完成操作！",0);
                                        DefinedToast.showToast(mContext,"授权失败，无法完成操作！", 0);
                                        return;
                                    }
                                });
            }
        });

        return convertView;
    }
    static class ViewHolder {
        TextView item_serial_number;
        TextView item_assistance_title;
        TextView item_assistance_phonenumber;
        ImageView item_assistance_phone;
    }

    /**
     * 打电话
     */
    private void callPhoneDialog(final String phone) {
        Default_ShowDialog.Builder builder = new Default_ShowDialog.Builder(mContext);
        builder.setTitle("服务热线");
        builder.setMessage("您将要拨打服务热线  :  " + phone);
        builder.setPositiveButton("立即拨打", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                if(TextUtils.isEmpty(phone)) {
                    return;
                }
                final String convsePhone = phone.replace("-", "");
                try {
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri
                            .parse("tel:"
                                    + convsePhone));
                    mContext.startActivity(intent);
                } catch (SecurityException e) {
                }
            }
        });

        builder.setNegativeButton("不了，谢谢~",
                new android.content.DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builder.create().show();
    }

}
