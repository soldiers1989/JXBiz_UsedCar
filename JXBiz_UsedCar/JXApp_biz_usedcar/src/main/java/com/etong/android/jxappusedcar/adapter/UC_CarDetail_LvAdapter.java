package com.etong.android.jxappusedcar.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.etong.android.frame.common.ImageProvider;
import com.etong.android.frame.utils.L;
import com.etong.android.frame.widget.pinnedlistview.PinnedSectionListView;
import com.etong.android.jxappusedcar.R;
import com.etong.android.jxappusedcar.bean.UC_CarDetailBean;
import com.etong.android.jxappusedcar.bean.UC_CarDetail_CarConfig;
import com.etong.android.jxappusedcar.bean.UC_CarDetail_History;
import com.etong.android.jxappusedcar.bean.UC_CarDetail_InstallPlan;
import com.etong.android.jxappusedcar.bean.UC_CarDetail_LightConfig;
import com.etong.android.jxappusedcar.content.UC_CarDetail_Activity;
import com.etong.android.jxappusedcar.utils.UC_ArabToCUpper;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : by sunyao
 * @ClassName : UC_CarDetail_LvAdapter
 * @Description : (这里用一句话描述这个类的作用)
 * @date : 2016/10/20 - 18:15
 */

public class UC_CarDetail_LvAdapter extends ArrayAdapter<UC_CarDetailBean> implements PinnedSectionListView.PinnedSectionListAdapter {

    private final int mWinWidth;
    private Context mContext;

    private List mListDatas;
    private List<UC_CarDetailBean> allList = new ArrayList<UC_CarDetailBean>();         // 保存所有项
    private int sectionPosition;
    private int listPosition;

    private String [] titleSection = {
            "分期方案",
            "亮点配置",
            "车辆历史",
            "车辆配置",
            "车辆图片"
    };
    private String [] carHistoryStr = {
        "年检到期：",//f_inspectiondate
        "维修保养：",//f_repairtype
        "保险到期：",//f_insurancedate
        "过户次数：",//f_transfercount
        "质保到期：",//f_assurancedate
        "用途：",    //f_useproperty
        "原厂延保：",//warranty
        "环保标准"  //envstandard
    };
    private String [] carHistoryLetterStr = {
      "f_inspectiondate",
      "f_repairtype",
      "f_insurancedate",
      "f_transfercount",
      "f_assurancedate",
      "f_useproperty",
      "warranty",
      "envstandard",
    };
    private String [] carConfigStr = {
        "发动机 ： ",    // engine
        "变速器 ： ",    // gearmode
        "车辆等级 ： ",   // carlever
        "颜色 ： ",         //plateColor
        "最高时速 ： ",   //maxSpeed
        "驱动方式 ： ",   //drivesystem
        "燃油编号 ： ",   //fuelLabel
    };
    private String [] carConfigLetterStr = {
        "engine",
        "gearmode",
        "carlever",
        "plateColor",
        "maxSpeed",
        "drivesystem",
        "fuelLabel",
    };

    public UC_CarDetail_LvAdapter(Context context) {
        super(context, android.R.layout.simple_list_item_1);
        this.mContext = context;
        mListDatas = new ArrayList();

        // 获取到屏幕的宽度，动态的设置图的宽高
        WindowManager mWm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        mWinWidth = mWm.getDefaultDisplay().getWidth();
    }

    public void updateListDatas(List listDatas) {
        this.mListDatas = listDatas;
        generateListData();
        notifyDataSetChanged();
    }

    /**
     * @desc (生成数据的方法)
     * @user sunyao
     * @createtime 2016/10/20 - 19:16
     * @param
     * @return
     */
    private void generateListData() {
        clear();
        final int sectionsNumber = 'Z'-'A' + 1;
        prepareSections(sectionsNumber);
        sectionPosition = 0;        // 悬浮头的位置
        listPosition = 0;           // List的位置
        if (mListDatas.size() == 5 ) {      // 如果从上个页面传送过来的值为5
            UC_CarDetail_InstallPlan carInstallPlan = (UC_CarDetail_InstallPlan)mListDatas.get(0);
            if (carInstallPlan.getFqListCount()>0) {
                addSectionToListView(0);
                List<UC_CarDetail_InstallPlan.FqListBean> fqList = carInstallPlan.getFqList();
                addItemToListView(UC_CarDetailBean.ITEM_CHAR_3
                        , "本车支持以下分期 ："
                        , ""
                        , false);
                for (int i=0; i<fqList.size(); i++) {
                    addItemToListView(UC_CarDetailBean.ITEM_CHAR_3
                            ,"方案"+ UC_ArabToCUpper.convertUpper(i+1) +"（首付 ："+ +fqList.get(i).getF_firstpay() +"万  月供： "
                                    + fqList.get(i).getMonthPay()+"元，共 "+ fqList.get(i).getF_month_rate()+" 期）"
                            , ""
                            , true);
                }
                sectionPosition++;
            }
            UC_CarDetail_LightConfig listConfig = (UC_CarDetail_LightConfig)mListDatas.get(1);  // 亮点配置
            if (listConfig.getLdListCount()>0) {
                addSectionToListView(1);
                List<UC_CarDetail_LightConfig.LdListBean> ldList = listConfig.getLdList();
                for (int i=0; i<ldList.size()/2; i++) {
                    addItemToListView(UC_CarDetailBean.ITEM_CHAR_2
                            , ldList.get(i).getValue()
                            , ldList.get(i+1).getValue()
                            , true);
                }
                sectionPosition++;
            }
            UC_CarDetail_History carHistory = (UC_CarDetail_History)mListDatas.get(2);      // 车辆历史
            if(carHistory != null) {
                addSectionToListView(2);
                for (int i=0; i<carHistoryStr.length; i++) {
                    Object object = null;
                    try {
                        Field field = carHistory.getClass().getDeclaredField(carHistoryLetterStr[i]);
                        field.setAccessible(true);
                        object = field.get(carHistory);
                        if (object != null) {
                            addItemToListView(UC_CarDetailBean.ITEM_CHAR_1
                                    , carHistoryStr[i]
                                    , object.toString()
                                    , true);
                        }
                    }  catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (NoSuchFieldException e) {
                    }
                }
                sectionPosition++;
            }
            UC_CarDetail_CarConfig carConfig = (UC_CarDetail_CarConfig)mListDatas.get(3);      // 车辆配置
            if(carConfig != null) {
                addSectionToListView(3);
                for (int i=0; i<carConfigStr.length; i++) {
                    Object object = null;
                    try {
                        Field field = carConfig.getClass().getDeclaredField(carConfigLetterStr[i]);
                        field.setAccessible(true);
                        object = field.get(carConfig);
                        if (object != null) {
                            addItemToListView(UC_CarDetailBean.ITEM_CHAR_1
                                    ,carConfigStr[i]
                                    ,object.toString()
                                    , true);
                        }
                    }  catch (IllegalAccessException e) {
                    } catch (NoSuchFieldException e) {
                    }
                }
                sectionPosition++;
            }
            List<String> imgLists = (List<String>) mListDatas.get(4);                   // 车辆图片
            if(imgLists!=null && imgLists.size()>0) {
                addSectionToListView(4);
                for (String imgStr : imgLists) {
                    addItemToListView(UC_CarDetailBean.ITEM_CHAR_4
                            , imgStr
                            , ""
                            , true);
                }
                sectionPosition++;
            }
            addAll(allList);
        }
    }

    /**
     * @desc (给ListView添加悬浮头部)
     * @user sunyao
     * @createtime 2016/10/29 - 15:46
     * @param
     * @return
     */
    private void addSectionToListView(int sectionPos) {
        UC_CarDetailBean sectionLv = new UC_CarDetailBean(UC_CarDetailBean.SECTION, titleSection[sectionPos]);
        sectionLv.sectionPosition = sectionPosition;
        sectionLv.listPosition = listPosition++;
        onSectionAdded(sectionLv, sectionPosition);
//        add(carImgSection);
        allList.add(sectionLv);
    }

    /**
     * @desc (给ListView添加Item)
     * @user sunyao
     * @createtime 2016/10/29 - 15:26
     * @param 
     * @return 
     */
    private void addItemToListView(int viewType, String l, String r, boolean isShowImg) {
        UC_CarDetailBean itemLv = new UC_CarDetailBean(viewType, l, r, isShowImg);
        itemLv.sectionPosition = sectionPosition;
        itemLv.listPosition = listPosition++;
//                    add(carImgItem);
        allList.add(itemLv);
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // 悬浮头的布局
        UC_CarDetailBean item = getItem(position);
        View view = null;
        if (item.mType == UC_CarDetailBean.SECTION) {
            // 悬浮头部的item
            view = LayoutInflater.from(mContext).inflate(R.layout.uc_lv_car_detail_header_item, null);
            TextView tTitle = (TextView) view.findViewById(R.id.uc_tv_cardetail_header_item);
            tTitle.setText(item.getmTxt_l());
        } else if (item.mType == UC_CarDetailBean.ITEM_CHAR_1) {
            // 包含1个文字，没有图片的item  ---（车辆历史、车辆配置）
            view = LayoutInflater.from(mContext).inflate(R.layout.uc_lv_car_detail_history_and_config, null);
            TextView lText = (TextView) view.findViewById(R.id.uc_lv_car_detail_hc_l);
            TextView rText = (TextView) view.findViewById(R.id.uc_lv_car_detail_hc_r);

            lText.setText(item.getmTxt_l());
            if (!TextUtils.isEmpty(item.getmTxt_r())) {   // 如果获取到的参数不为空
                rText.setText(item.getmTxt_r());
            } else {                                // 如果获取到的参数为空则设置为   -
                rText.setText("");
            }
        } else if(item.mType == UC_CarDetailBean.ITEM_CHAR_2) {
            // 包含两个文字，但是没有图片的item  ---（亮点配置）
            view = LayoutInflater.from(mContext).inflate(R.layout.uc_lv_car_detail_highlight_config, null);
            TextView lText = (TextView) view.findViewById(R.id.uc_lv_car_detail_highlight_con_l);
            TextView rText = (TextView) view.findViewById(R.id.uc_lv_car_detail_highlight_con_r);

            if (!TextUtils.isEmpty(item.getmTxt_l())) {   // 如果获取到的参数不为空
                lText.setText(item.getmTxt_l());
            } else {
                lText.setText("");
            }
            if (!TextUtils.isEmpty(item.getmTxt_r())) {   // 如果获取到的参数不为空
                rText.setText(item.getmTxt_r());
            } else {                                // 如果获取到的参数为空则设置为   -
                rText.setText("");
            }
        } else if(item.mType == UC_CarDetailBean.ITEM_CHAR_3) {
            // 包含一个文字、可能有图片的item    ---（分期方案）
            view = LayoutInflater.from(mContext).inflate(R.layout.uc_lv_car_detail_installment_plan, null);
            ImageView iv = (ImageView) view.findViewById(R.id.uc_iv_car_detail_instanllment_plan_l);        // 左边图片
            TextView lText = (TextView) view.findViewById(R.id.uc_lv_car_detail_instanllment_plan_l);       // 文字
            // 设置图片是否显示
            if(item.ismIsShowIm()) {
                iv.setVisibility(View.VISIBLE);
            } else {
                iv.setVisibility(View.GONE);
            }
            // 显示图片的item列
            if (!TextUtils.isEmpty(item.getmTxt_l())) {   // 如果获取到的参数不为空
                lText.setText(item.getmTxt_l());
            } else {                                // 如果获取到的参数为空则设置为   -
                lText.setText("");
            }
        } else if(item.mType == UC_CarDetailBean.ITEM_CHAR_4) {
            // 只包含图片的item                   -- （车辆图片）
            view = LayoutInflater.from(mContext).inflate(R.layout.uc_lv_car_detail_car_pic_item, null);
            final ImageView iv = (ImageView) view.findViewById(R.id.uc_lv_car_detail_car_pic_item_p);

            if(!TextUtils.isEmpty(item.getmTxt_l())) {
                ImageProvider.getInstance().getImageLoader().displayImage(item.getmTxt_l(), iv, UC_CarDetail_Activity.options,
                        new ImageLoadingListener() {
                            @Override
                            public void onLoadingStarted(String arg0, View arg1) {
                                L.d("onLoadingStarted", arg0);
                            }

                            @Override
                            public void onLoadingFailed(String arg0, View arg1,
                                                        FailReason arg2) {
                                L.d("onLoadingFailed", arg0);
                            }

                            @Override
                            public void onLoadingComplete(String arg0, View arg1,
                                                          Bitmap arg2) {
                                L.d("onLoadingComplete", arg0);
                                if (arg2 == null) {
                                    return;
                                }
                                int with = arg2.getWidth() - 2;
                                int height = arg2.getHeight();
                                iv.getLayoutParams().width = mWinWidth;
                                iv.getLayoutParams().height = mWinWidth * height
                                        / with;
                            }

                            @Override
                            public void onLoadingCancelled(String arg0, View arg1) {
                            }
                        });

            }
        }
        view.setTag("" + position);
        return view;
    }

    /**
     * @desc (当前ListView中item的类型个数)
     * @user sunyao
     * @createtime 2016/10/20 - 18:58
     * @param
     * @return
     */
    @Override
    public int getViewTypeCount() {
        return 5;
    }

    /**
     * @desc (返回当前item的类型)
     * @user sunyao
     * @createtime 2016/10/20 - 18:58
     * @param
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        return getItem(position).mType;
    }

    /**
     * @desc (当前item是否是悬浮的)
     * @user sunyao
     * @createtime 2016/10/20 - 18:57
     * @param
     * @return
     */
    @Override
    public boolean isItemViewTypePinned(int viewType) {
        return viewType == UC_CarDetailBean.SECTION;
    }


    protected void prepareSections(int sectionsNumber) { }
    protected void onSectionAdded(UC_CarDetailBean section, int sectionPosition) { }
}
