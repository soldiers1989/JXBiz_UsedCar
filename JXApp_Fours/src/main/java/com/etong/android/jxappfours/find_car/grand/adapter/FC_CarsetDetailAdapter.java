package com.etong.android.jxappfours.find_car.grand.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.etong.android.frame.publisher.HttpMethod;
import com.etong.android.frame.publisher.HttpPublisher;
import com.etong.android.frame.request_init.FrameHttpTag;
import com.etong.android.frame.user.FrameEtongApplication;
import com.etong.android.frame.user.FramePersonalLoginActivity;
import com.etong.android.frame.utils.CToast;
import com.etong.android.frame.utils.DefinedToast;
import com.etong.android.frame.utils.L;
import com.etong.android.frame.utils.SerializableObject;
import com.etong.android.frame.widget.pinnedlistview.PinnedSectionListView;
import com.etong.android.jxappfours.R;
import com.etong.android.jxappfours.find_car.collect_search.utils.Find_Car_VechileCollect_Method;
import com.etong.android.jxappfours.find_car.grand.bean.FC_CalcuCarPriceBean;
import com.etong.android.jxappfours.find_car.grand.bean.FC_CollectBean;
import com.etong.android.jxappfours.find_car.grand.bean.FC_InsalesListItem;
import com.etong.android.jxappfours.find_car.grand.car_config.Find_car_CarConfigActivity;
import com.etong.android.jxappfours.find_car.grand.carset.FC_CarsetActivity;
import com.etong.android.jxappfours.find_car.grand.find_car_shopcar_calc.Find_car_CalcuTotalActivity;
import com.etong.android.jxappfours.find_car.grand.provider.FC_GetInfo_Provider;
import com.etong.android.jxappfours.models_contrast.common.Models_Contrast_AddVechileStyle_Method;
import com.etong.android.jxappfours.models_contrast.javabean.Models_Contrast_Add_VechileStyle;
import com.etong.android.jxappfours.models_contrast.javabean.Models_Contrast_VechileType;
import com.etong.android.jxappfours.order.FO_OrderActivity;

import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Ellison.Sun on 2016/8/12.
 * <p>
 * Menu中所有车型的适配器
 */
public class FC_CarsetDetailAdapter extends ArrayAdapter<Models_Contrast_VechileType> implements PinnedSectionListView.PinnedSectionListAdapter {

    private final HttpPublisher mHttpPublisher;
    private Context mContext;

    private List<Models_Contrast_VechileType> mListDatas;            // 传递过来的数据

    private FC_GetInfo_Provider mFC_GetInfo_Provider;
    private Button fc_detail_list_btn_collect;


    public FC_CarsetDetailAdapter(Context context) {
        super(context, android.R.layout.simple_list_item_1);
        this.mContext = context;

        mHttpPublisher = HttpPublisher.getInstance();
        mHttpPublisher.initialize(context);
        mFC_GetInfo_Provider = FC_GetInfo_Provider.getInstance();
        mFC_GetInfo_Provider.initialize(mHttpPublisher);
    }

    public void updateListView(List<Models_Contrast_VechileType> listDatas) {
        this.mListDatas = listDatas;
        generateDataset(true);
        notifyDataSetChanged();
    }
    /**
     * 从本地获取到车辆对比缓存信息
     *
     * @return
     */
    private List<Integer> getComparisonVidList() {
        //得到车型对比缓存的信息的vid
        List<Integer> mVidList = new ArrayList<Integer>();
        List<Models_Contrast_Add_VechileStyle> mList = new ArrayList<Models_Contrast_Add_VechileStyle>();
        Map map = Models_Contrast_AddVechileStyle_Method.getAdd_VechileStyle();
        Models_Contrast_VechileType info = null;
        if (map != null) {
            Iterator it = map.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry entry = (Map.Entry) it.next();
                Object key = entry.getKey();
                String data = String.valueOf(map.get(Integer.valueOf(String.valueOf(key))));
                info = JSON.parseObject(data, Models_Contrast_VechileType.class);
                if (null != info) {
                    mVidList.add(info.getVid());
                }
            }
        }
        return mVidList;
    }

    /**
     * 生成数据的方法
     *
     * @param clear
     */
    public void generateDataset(boolean clear) {

        if (clear) clear();
        final int sectionsNumber = 'Z' - 'A' + 1;
        prepareSections(sectionsNumber);

        int sectionPosition = 0, listPosition = 0;
        List<String> sectionList = new ArrayList<String>();
        for (int i = 0; i < mListDatas.size(); i++) {

            // 判断mListData数据中是否添加该头部
            Models_Contrast_VechileType vechileType = mListDatas.get(i);
            double tempOutputVol = vechileType.getOutputVol();
            tempOutputVol = tempOutputVol / 1000;
            String strTempOutputVol = String.format("%.2f", tempOutputVol);
            if (!sectionList.contains(strTempOutputVol)) {
                sectionList.add(strTempOutputVol);
                Models_Contrast_VechileType listTitleItem = new Models_Contrast_VechileType(Models_Contrast_VechileType.SECTION, strTempOutputVol);
                listTitleItem.sectionPosition = sectionPosition;
                listTitleItem.listPosition = listPosition++;
                onSectionAdded(listTitleItem, sectionPosition);
                add(listTitleItem);
            }

            Models_Contrast_VechileType listItem = new Models_Contrast_VechileType();
            // 获取到每一个item，得到它的字母
            listItem.type = FC_InsalesListItem.ITEM;
            listItem.setImage(vechileType.getImage());
            listItem.setYear(vechileType.getYear());
            listItem.setSubject(vechileType.getImage());
            listItem.setImageNum(vechileType.getImageNum());
            listItem.setManu(vechileType.getManu());
            listItem.setFullName(vechileType.getFullName());
            listItem.setTitle(vechileType.getTitle());
            listItem.setVid(vechileType.getVid());
            listItem.setCarset(vechileType.getCarset());
            listItem.setSalestatus(vechileType.getSalestatus());
            listItem.setCarsetTitle(vechileType.getCarsetTitle());
            listItem.setPrices(vechileType.getPrices());
            listItem.setOutputVol(vechileType.getOutputVol());
            listItem.setBrand(vechileType.getBrand());
            listItem.setSalestatusid(vechileType.getSalestatusid());
            listItem.setF_collectstatus(vechileType.getF_collectstatus());
            listItem.sectionPosition = sectionPosition;
            listItem.listPosition = listPosition++;
            add(listItem);
            sectionPosition++;
        }
    }

    protected void prepareSections(int sectionsNumber) {
    }

    protected void onSectionAdded(Models_Contrast_VechileType section, int sectionPosition) {
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // 悬浮头的布局
        final Models_Contrast_VechileType item = getItem(position);
        View view = null;
        if (item.type == Models_Contrast_VechileType.SECTION) {
            view = LayoutInflater.from(mContext).inflate(R.layout.fc_carset_section_item_title, null);
            TextView tTitle = (TextView) view.findViewById(R.id.fc_insales_item_title_name);
            tTitle.setText("排量 " + item.charTv + "L");
        } else if (item.type == Models_Contrast_VechileType.ITEM) {
            view = LayoutInflater.from(mContext).inflate(R.layout.find_car_detail_list_item, null);
            ViewGroup find_car_carset_detail_item_title = (ViewGroup) view.findViewById(R.id.find_car_carset_detail_item_title);
            TextView find_car_carset_item_title = (TextView) view.findViewById(R.id.find_car_carset_item_title);
            TextView find_car_carset_tv_guid_price = (TextView) view.findViewById(R.id.find_car_carset_tv_guid_price);
            TextView find_car_carset_tv_reference_price = (TextView) view.findViewById(R.id.find_car_carset_tv_reference_price);

            fc_detail_list_btn_collect = (Button) view.findViewById(R.id.fc_detail_list_btn_collect);
            final Button fc_detail_list_btn_calculate = (Button) view.findViewById(R.id.fc_detail_list_btn_calculate);
            final Button fc_detail_list_btn_comparison = (Button) view.findViewById(R.id.fc_detail_list_btn_comparison);
            final Button fc_detail_list_btn_ask = (Button) view.findViewById(R.id.fc_detail_list_btn_ask);


            // 设置每个Item的文字
            find_car_carset_item_title.setText(item.getTitle());

            find_car_carset_tv_guid_price.setText(item.getPrices() + "万");
            find_car_carset_tv_reference_price.setText(item.getPrices() + "万");

            if (item.getF_collectstatus().equals("1")) {
                fc_detail_list_btn_collect.setSelected(true);
                fc_detail_list_btn_collect.setText("已收藏");
            } else {
                fc_detail_list_btn_collect.setSelected(false);
                fc_detail_list_btn_collect.setText("收藏");
            }
            if (getComparisonVidList().contains(item.getVid())) {
                fc_detail_list_btn_comparison.setSelected(true);
                fc_detail_list_btn_comparison.setText("已加入");
            } else {
                fc_detail_list_btn_comparison.setSelected(false);
                fc_detail_list_btn_comparison.setText("加入对比");
            }

            find_car_carset_detail_item_title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

//                    Toast.makeText(mContext, "进入车型详情界面", Toast.LENGTH_SHORT).show();

                    Intent toCarsetActivity = new Intent(mContext, Find_car_CarConfigActivity.class);

                    Map map = new HashMap<>();
                    map.put("dataType", item);
                    // 传递数据
                    final SerializableObject myMap = new SerializableObject();
                    myMap.setObject(map);// 将map数据添加到封装的myMap中
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("dataTypeMap", myMap);
                    toCarsetActivity.putExtras(bundle);

                    mContext.startActivity(toCarsetActivity);
                }
            });

            fc_detail_list_btn_ask.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    Intent intent = new Intent(mContext, Fours_Order_OrderActivity.class);
                    Intent intent = new Intent(mContext, FO_OrderActivity.class);
                    intent.putExtra("vid", item.getVid() + "");
                    intent.putExtra("flag", 4);
                    intent.putExtra("carsetId", item.getCarset());
                    intent.putExtra("carImage", item.getImage());
                    intent.putExtra("vTitleName", item.getTitle());
                    intent.putExtra("brand", item.getBrand());
                    intent.putExtra("isSelectCar", true);
                    mContext.startActivity(intent);
                }
            });

            fc_detail_list_btn_collect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    L.d("-------------->", fc_detail_list_btn_collect.isSelected() + "");
                    mItemOnClickListener.itemOnClickListener(position);
                    //请求点击收藏
                    if (FrameEtongApplication.getApplication().isLogin()) {
                        mFC_GetInfo_Provider.clickCollection(String.valueOf(item.getVid()), item.getImage(), String.valueOf(item.getImageNum()), item.getManu(),
                                item.getFullName(), item.getTitle(), String.valueOf(item.getCarset()), item.getCarsetTitle(), String.valueOf(item.getPrices()),
                                item.getBrand(), FC_CarsetActivity.TAG);//点击收藏
                        return;
                    } else {
                        CToast.toastMessage("您还未登录，请登录", 0);
                        Intent intent = new Intent();
                        intent.setClass(mContext, FramePersonalLoginActivity.class);
                        mContext.startActivity(intent);
                    }
                }
            });

            fc_detail_list_btn_comparison.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!fc_detail_list_btn_comparison.isSelected()) {
                        if (getComparisonVidList().size() >= 12) {
//                            CToast.toastMessage("对比的个数已满",0);
                            DefinedToast.showToast(mContext, "对比的个数已满", 0);

                            return;
                        }
//                        Models_Contrast_Add_VechileStyle add = new Models_Contrast_Add_VechileStyle();
//                        add.setTitle(item.getFullName());
//                        add.setId(item.getVid());
//                        Models_Contrast_AddVechileStyle_Method.cartAdd(add);
                        Models_Contrast_AddVechileStyle_Method.cartAdd(item);
                        fc_detail_list_btn_comparison.setSelected(true);
                        fc_detail_list_btn_comparison.setText("已加入");
                    } else {
                        Models_Contrast_AddVechileStyle_Method.remove(item.getVid());
                        fc_detail_list_btn_comparison.setSelected(false);
                        fc_detail_list_btn_comparison.setText("加入对比");
                    }
                }
            });

            // 点击购车计算跳转到购车计算页面中
            fc_detail_list_btn_calculate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FC_CalcuCarPriceBean bean = new FC_CalcuCarPriceBean();
                    bean.setCarName(item.getFullName());
                    bean.setCarPrice((int) ((item.getPrices()) * 10000));

                    Intent i = new Intent(mContext, Find_car_CalcuTotalActivity.class);
                    i.putExtra(FrameHttpTag.TRANSFER_CAR_PRICE_INFO, bean);
                    mContext.startActivity(i);
                }
            });
//            ImageView iv = (ImageView) view.findViewById(R.id.grand_insales_img_pic);
//            TextView rTText = (TextView) view.findViewById(R.id.grand_insales_item_rTText);
//            TextView rBText = (TextView) view.findViewById(R.id.grand_insales_item_rBText);
//
//            ImageProvider.getInstance().loadImage(iv, item.getImage());
//            rTText.setText(item.getFullName());
//            rBText.setText(item.getMinguide() +" - " +item.getMaxguide() +"万");
        }
        view.setTag("" + position);
        return view;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        return getItem(position).type;
    }

    @Override
    public boolean isItemViewTypePinned(int viewType) {
        return viewType == Models_Contrast_VechileType.SECTION;
    }


    /**
     * @desc (设置收藏状态)
     * @createtime 2016/12/10 0010-15:56
     * @author wukefan
     */
    public void setCollectState(int position,String staue) {
        getItem(position).setF_collectstatus(staue);
        notifyDataSetChanged();
    }

    private ItemOnClickListener mItemOnClickListener;

    public void setmItemOnClickListener(ItemOnClickListener listener) {
        this.mItemOnClickListener = listener;
    }

    public interface ItemOnClickListener {
        /**
         * 传递点击的view
         *
         * @param
         */
        public void itemOnClickListener(int position);
    }


}
