package com.etong.android.jxappusedcar.content;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.etong.android.frame.subscriber.BaseSubscriberFragment;
import com.etong.android.frame.widget.TitleBar;

import java.util.ArrayList;
import java.util.List;
import com.etong.android.jxappusedcar.R;

/**
 * Created by Ellison.Sun on 2016/8/27.
 */
public class UC_CarconfigMenu extends BaseSubscriberFragment {

    private Context mContext;
    private DrawerLayout mDrawerLayout;                 // 侧滑菜单
//    private PinnedSectionListView mListView;            // 悬浮头ListView
    private OnCarconfigItemSelectListener mListener;    // 选择Item的Listener
    private ListView menuListView;                      // Menu中的ListView
    private View view;
    private int selectedPosition = -1;// 选中的位置

    private List<String> menuCarconfigListItem = new ArrayList<String>();
    private ListView fc_car_config_menu_listview;

    final String[] strTitle = new String[]{
            "基本参数", "车身", "发动机", "空调/冰箱", "内部配置", "安全装备", "变速箱",
            "底盘转向", "车轮制动",
            "操控配置", "多媒体配置",
            "灯光配置", "外部配置", "玻璃/后视镜", "高科技配置", "座椅配置"

    };
    private FC_carconfigMenuListAdapter adapter;

    public UC_CarconfigMenu() {

    }

    public UC_CarconfigMenu(Context context, DrawerLayout drawerLayout) {
        this.mContext = context;
        this.mDrawerLayout = drawerLayout;
    }

    /**
     * 更新ListView的选中位置
     *
     * @param strPosition
     */
    public void updateSelectPosition(String strPosition) {

        int tempPos = 0;
        for (int i=0; i<strTitle.length; i++) {
            if (strTitle[i].equals(strPosition)) {
                tempPos = i;
                break;
            }
        }
        // 设置选中的ListView的位置
        if (adapter!=null) {
            adapter.setSelectedPosition(tempPos);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    protected View onInit(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.uc_car_config_menu_content, container, false);

        initView(); // 初始化操作

        return view;
    }

    /**
     * 初始化操作
     */
    private void initView() {

        TitleBar titleBar = new TitleBar(view);
        titleBar.showBackButton(false);

        titleBar.setNextButton("关闭");
        titleBar.setTitle("选择分类");
        titleBar.setTitleTextColor("#000000");
        titleBar.setmTitleBarBackground("#FFFFFF");

        // 点击关闭按钮，关闭Drawerlayout
        titleBar.setNextOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.closeDrawer(Gravity.RIGHT);
            }
        });

        // 给ListView添加左边文字数据
        for (int i = 0; i < strTitle.length; i++) {
            menuCarconfigListItem.add(strTitle[i]);
        }
        fc_car_config_menu_listview = (ListView) view.findViewById(R.id.uc_car_config_menu_listview);
        // 设置ListView的单选模式
        // 设置ListView的单选模式
        fc_car_config_menu_listview.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        adapter = new FC_carconfigMenuListAdapter(mContext);
        fc_car_config_menu_listview.setAdapter(adapter);
        fc_car_config_menu_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapter.setSelectedPosition(position);      // 设置选中的item
                mListener.onCarconfigItemSelect(strTitle[position]);
                mDrawerLayout.closeDrawer(Gravity.RIGHT);
                adapter.notifyDataSetInvalidated();
            }
        });
    }

    public void setOnItemSelectListener(OnCarconfigItemSelectListener listener) {
        this.mListener = listener;
    }

    public interface OnCarconfigItemSelectListener {
        void onCarconfigItemSelect(String positionName);
    }

    class FC_carconfigMenuListAdapter extends BaseAdapter {
        private Context context;

        public FC_carconfigMenuListAdapter(Context context) {
            this.context = context;
        }

        @Override
        public int getCount() {
            return strTitle.length;
        }

        @Override
        public Object getItem(int position) {
            return strTitle[position];
        }

        public void setSelectedPosition(int position) {
            selectedPosition = position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = LayoutInflater.from(context).inflate(R.layout.uc_car_config_menu_listview_item, null);
                holder.tv = (TextView) convertView.findViewById(R.id.uc_car_config_menu_listview_text);
                holder.iv = (ImageView) convertView.findViewById(R.id.uc_car_config_menu_listview_iv);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            String string = strTitle[position];
            holder.tv.setText(string);
            if (position % 2 == 0) {
                if (selectedPosition == position) {
                    convertView.setSelected(true);
                    convertView.setPressed(true);
//                  convertView.setBackgroundColor(Color.parseColor("#0097e0"));
                    holder.tv.setTextColor(Color.parseColor("#578CD5"));
                    holder.iv.setImageResource(R.mipmap.uc_car_config_menu_listview_right_yes);
//                    holder.iv.setImageResource(R.mipmap.find_car_select_checked);
                } else {
                    convertView.setSelected(false);
                    convertView.setPressed(false);
//                  convertView.setBackgroundColor(Color.parseColor("#e4ebf1"));
                    holder.tv.setTextColor(Color.parseColor("#7A7A7A"));
                    holder.iv.setImageResource(R.color.transparent);
                }
            } else {
                if (selectedPosition == position) {
                    convertView.setSelected(true);
                    convertView.setPressed(true);
//                  convertView.setBackgroundColor(Color.parseColor("#0097e0"));
                    holder.tv.setTextColor(Color.parseColor("#578CD5"));
                    holder.iv.setImageResource(R.mipmap.uc_car_config_menu_listview_right_yes);
//                    holder.iv.setImageResource(R.mipmap.find_car_select_checked);
                } else {
                    convertView.setSelected(false);
                    convertView.setPressed(false);
//                  convertView.setBackgroundColor(Color.parseColor("#ced7de"));
                    holder.tv.setTextColor(Color.parseColor("#7A7A7A"));
                    holder.iv.setImageResource(R.color.transparent);
                }
            }
            return convertView;
        }

        class ViewHolder {
            TextView tv;
            ImageView iv;
        }
    }
}
