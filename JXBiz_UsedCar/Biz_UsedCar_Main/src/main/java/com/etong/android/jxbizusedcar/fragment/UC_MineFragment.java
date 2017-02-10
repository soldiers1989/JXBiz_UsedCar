package com.etong.android.jxbizusedcar.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.etong.android.frame.common.ImageProvider;
import com.etong.android.frame.publisher.HttpMethod;
import com.etong.android.frame.publisher.HttpPublisher;
import com.etong.android.frame.request_init.UC_HttpTag;
import com.etong.android.frame.user.UC_CollectOrScannedBean;
import com.etong.android.frame.user.UC_FrameEtongApplication;
import com.etong.android.frame.user.UC_LoginActivity;
import com.etong.android.frame.user.UC_User;
import com.etong.android.frame.user.UC_UserProvider;
import com.etong.android.frame.utils.ActivitySkipUtil;
import com.etong.android.frame.utils.PhotoHeadUtils;
import com.etong.android.frame.utils.UploadFileProvider;
import com.etong.android.frame.widget.CircleImageView;
import com.etong.android.jxbizusedcar.R;
import com.etong.android.jxbizusedcar.activity.UC_CollectCarActivity;
import com.etong.android.jxbizusedcar.activity.UC_ScannedRecordsActivity;
import com.etong.android.jxbizusedcar.adapter.UC_Mine_ExpandableListAdapter;
import com.etong.android.jxbizusedcar.bean.UC_MineAllDataBean;
import com.etong.android.jxbizusedcar.subscriber.UC_SubscriberFragment;

import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的
 * Created by Administrator on 2016/10/11.
 */

public class UC_MineFragment extends UC_SubscriberFragment {

    private static final String PERSON_PIC_HEAD_UPLOAD = "person pic head upload";

    private CircleImageView mUserHeader;//用户头像
    private TextView mClickLogin;//点击登录
    private LinearLayout mKeepCars;//收藏车辆
    private LinearLayout mScanned;//浏览记录

    private ExpandableListView mExpandableListMine;//我的列表
    private UC_Mine_ExpandableListAdapter mExpandableAdapter;//我的列表适配器
    private View mListHead;//我的列表的头部
    private String mHeadPic;//头像
    private TextView mTxtCollectNum;//收藏车辆数量
    private TextView mTxtScannedNum;//浏览记录数量
    private UC_UserProvider mUC_Provider;
    private boolean isInit = false;
    private UC_FrameEtongApplication uc_frameEtongApplication = UC_FrameEtongApplication.getApplication();

    private List<UC_MineAllDataBean.CarlistBean> carlist;
    private boolean isHidde = true;
    private boolean isOnResume = false;

    @Override
    protected View myInit(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {

        View view = layoutInflater.inflate(R.layout.uc_fragment_mine_content, viewGroup, false);

        initView(view);
        return view;
    }


    private void initView(View view) {

        //listview头部布局
        mListHead = LayoutInflater.from(getContext()).inflate(R.layout.uc_view_mine_list_header, null);
        //头像
        mUserHeader = findViewById(mListHead, R.id.uc_civ_user_header, CircleImageView.class);
        addClickListener(mUserHeader);
        //手机号、点击登录
        mClickLogin = findViewById(mListHead, R.id.uc_tv_click_login, TextView.class);
        addClickListener(mClickLogin);
        //收藏
        mKeepCars = findViewById(mListHead, R.id.uc_ll_keep_car_numbers, LinearLayout.class);
        addClickListener(mKeepCars);
        mTxtCollectNum = findViewById(mListHead, R.id.uc_txt_collect_numbers, TextView.class);
        //浏览记录
        mScanned = findViewById(mListHead, R.id.uc_ll_scanned_records, LinearLayout.class);
        addClickListener(mScanned);
        mTxtScannedNum = findViewById(mListHead, R.id.uc_txt_scanned_records, TextView.class);

        //ExpandableListView
        mExpandableListMine = findViewById(view, R.id.uc_lv_recomme, ExpandableListView.class);
        mExpandableListMine.addHeaderView(mListHead);//添加头部
        mExpandableListMine.setGroupIndicator(null);//去掉父级的箭头
        //不能点击收缩
        mExpandableListMine.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return true;
            }
        });

        mExpandableAdapter = new UC_Mine_ExpandableListAdapter(getActivity(), mExpandableListMine);
        mExpandableListMine.setAdapter(mExpandableAdapter);

        //设置可请求数据
        setCanRequest();
    }

    @Override
    protected void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            //收藏车辆
            case R.id.uc_ll_keep_car_numbers:
                if (uc_frameEtongApplication.isLogin()) {
                    ActivitySkipUtil.skipActivity(UC_MineFragment.this, UC_CollectCarActivity.class);
                } else {
                    Intent i = new Intent(getActivity(), UC_LoginActivity.class);
                    i.putExtra("isShowToast", true);
                    startActivity(i);
                }
                break;
            //浏览记录
            case R.id.uc_ll_scanned_records:
                ActivitySkipUtil.skipActivity(UC_MineFragment.this, UC_ScannedRecordsActivity.class);
                break;
            //点击登录
            case R.id.uc_tv_click_login:
                ActivitySkipUtil.skipActivity(UC_MineFragment.this, UC_LoginActivity.class);
                break;
            //用户头像
            case R.id.uc_civ_user_header:
                if (uc_frameEtongApplication.isLogin()) {
                    PhotoHeadUtils.startPhotoUtils(getActivity(), PERSON_PIC_HEAD_UPLOAD, true);
                } else {
                    ActivitySkipUtil.skipActivity(UC_MineFragment.this, UC_LoginActivity.class);
                }
                break;
        }
    }


    //我的页面综合接口回调
    @Subscriber(tag = UC_HttpTag.MINE_INFO)
    public void mineInfoResult(HttpMethod method) {
        carlist = new ArrayList<UC_MineAllDataBean.CarlistBean>();
        String status = method.data().getString("status");
        String msg = method.data().getString("msg");

        if (status.equals("true")) {
            JSONObject jsonData = method.data().getJSONObject("data");
            UC_MineAllDataBean mMineAllDataBean = JSON.toJavaObject(jsonData, UC_MineAllDataBean.class);

            //设置头像
            if (null != mMineAllDataBean.getF_userimage() && !TextUtils.isEmpty(mMineAllDataBean.getF_userimage())) {
                if (mMineAllDataBean.getF_userimage().contains("113.247.237.98")) {
                    mHeadPic = mMineAllDataBean.getF_userimage().replace("113.247.237.98", "222.247.51.114");
                } else {
                    mHeadPic = mMineAllDataBean.getF_userimage();
                }
                ImageProvider.getInstance().loadImage(mUserHeader, mHeadPic);
                if (uc_frameEtongApplication.isLogin()) {
                    UC_User mUser = uc_frameEtongApplication.getUserInfo();
                    mUser.setUserHead(mHeadPic);
                    uc_frameEtongApplication.setUserInfo(mUser);
                }
            } else {
                if (uc_frameEtongApplication.isLogin()) {
                    ImageProvider.getInstance().loadImage(mUserHeader, uc_frameEtongApplication.getUserInfo().getUserHead());
                } else {
                    ImageProvider.getInstance().loadImage(mUserHeader, "mUserHeader", R.mipmap.uc_ic_head_boy);
                }
            }

            //设置收藏、浏览记录个数
            mTxtScannedNum.setText(mMineAllDataBean.getF_historycount() + "");
            mTxtCollectNum.setText(mMineAllDataBean.getF_collectcount() + "");

            //设置为你推荐数据
            carlist = mMineAllDataBean.getCarlist();
            mExpandableAdapter.updateCarList(carlist);
        } else {
            //设置浏览记录个数
            if (null != uc_frameEtongApplication.getHistoryCache().getCarList()) {
                mTxtScannedNum.setText(uc_frameEtongApplication.getHistoryCache().getCarList().size() + "");
            }
            //设置收藏个数
            if (uc_frameEtongApplication.isLogin()) {
                if (null != uc_frameEtongApplication.getCollectCache().getCarList()) {
                    mTxtCollectNum.setText(uc_frameEtongApplication.getCollectCache().getCarList().size() + "");
                }
            } else {
                mTxtCollectNum.setText("0");
            }

//            if (null != mRecommeAdapter && mRecommeAdapter.isEmpty()) {
            if (null != mExpandableAdapter && mExpandableAdapter.getChildrenCount(mExpandableAdapter.getGroupCount() - 1) == 0) {
                setCanRequest();
            }
//            toastMsg(msg);
        }
    }

    //上传头像图片到图片服务器
    @Subscriber(tag = PERSON_PIC_HEAD_UPLOAD)
    private void startUserIdPicFinish(Bitmap bitmap) {
        loadStart("正在上传图片", 0);
        UploadFileProvider.uploadFile(getActivity(), bitmap, PERSON_PIC_HEAD_UPLOAD, 100);
    }

    //上传头像图片到图片服务器接口回调
    @Subscriber(tag = PERSON_PIC_HEAD_UPLOAD)
    private void uploadUserIdfinish(JSONObject data) {
        System.out.println("uploadPicfinish:" + data);
        int code = data.getIntValue("errCode");
        if (code == 0) {
            mHeadPic = data.getString("url");
            mUC_Provider.uploadUserHead(mHeadPic);
        } else if (code == -1) {
            toastMsg("上传失败,重试!");
            loadFinish();
        } else if (code == -2) {
            toastMsg("请重新拍照!");
            loadFinish();
        }
    }

    //上传头像接口回调
    @Subscriber(tag = UC_HttpTag.UPLOAD_HEAD_IMAGE)
    public void uploadHeadResult(HttpMethod method) {
        String msg = method.data().getString("msg");
        loadFinish();

        String status = method.data().getString("status");
        if (status.equals("true")) {
            toastMsg("上传成功！");
            UC_User mUser = uc_frameEtongApplication.getUserInfo();
            mUser.setUserHead(mHeadPic);
            uc_frameEtongApplication.setUserInfo(mUser);
            ImageProvider.getInstance().loadImage(mUserHeader, mHeadPic);
            mUC_Provider.mineInfo();
        } else if (status.equals(HttpPublisher.NETWORK_ERROR)) {
            toastMsg(msg);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        isOnResume = true;
        if (!isHidde) {
            setMineInfo();
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {

        isHidde = hidden;

        if (!isInit) {
            //初始化请求
            mUC_Provider = UC_UserProvider.getInstance();
            mUC_Provider.initalize(HttpPublisher.getInstance());
            isInit = true;
        }

        if (!hidden) {
            if (!isOnResume) {
                if (null != mExpandableListMine) {
                    mExpandableListMine.setSelection(0);//滑动到顶部
                }
            }
            isOnResume = false;
            setMineInfo();
        }
    }

    /**
     * @desc (onResume和onHiddenChanged时设置我的页面相关信息)
     * @createtime 2016/11/8 0008-8:45
     * @author wukefan
     */
    private void setMineInfo() {
        //设置手机号、头像
        if (null != mClickLogin) {
            if (uc_frameEtongApplication.isLogin()) {
                UC_User mUC_User = uc_frameEtongApplication.getUserInfo();
                mClickLogin.setText(mUC_User.getF_phone());
                mClickLogin.setEnabled(false);
            } else {
                mClickLogin.setText("点击登录");
                mClickLogin.setEnabled(true);
                ImageProvider.getInstance().loadImage(mUserHeader, "mUserHeader", R.mipmap.uc_ic_head_boy);
            }
        }

        //通过判断收藏浏览记录是否改变来设置是否请求接口
        if (isInit && (uc_frameEtongApplication.getCollectCache().isChanged() || uc_frameEtongApplication.getHistoryCache().isChanged())) {
            mUC_Provider.mineInfo();
            UC_CollectOrScannedBean tempCollect = UC_FrameEtongApplication.getApplication().getCollectCache();
            tempCollect.setChanged(false);
            UC_FrameEtongApplication.getApplication().setCollectCache(tempCollect);
            UC_CollectOrScannedBean tempHistory = UC_FrameEtongApplication.getApplication().getHistoryCache();
            tempHistory.setChanged(false);
            UC_FrameEtongApplication.getApplication().setHistoryCache(tempHistory);
        }
    }

    /**
     * @desc (设置可请求数据的缓存)
     * @createtime 2016/11/8 0008-10:28
     * @author wukefan
     */
    private void setCanRequest() {
        UC_CollectOrScannedBean temp = UC_FrameEtongApplication.getApplication().getCollectCache();
        temp.setChanged(true);
        UC_FrameEtongApplication.getApplication().setCollectCache(temp);
    }

}
