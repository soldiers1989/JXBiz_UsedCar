package com.etong.android.jxappme.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.etong.android.frame.common.ImageProvider;
import com.etong.android.frame.developing.DevelopingActivity;
import com.etong.android.frame.developing.DevelopingEmptyActivity;
import com.etong.android.frame.publisher.HttpMethod;
import com.etong.android.frame.publisher.HttpPublisher;
import com.etong.android.frame.request_init.FrameHttpTag;
import com.etong.android.frame.subscriber.BaseSubscriberFragment;
import com.etong.android.frame.user.FrameEtongApplication;
import com.etong.android.frame.user.FramePersonalLoginActivity;
import com.etong.android.frame.user.FrameUserInfo;
import com.etong.android.frame.user.UsedAndNewCollectCar;
import com.etong.android.frame.utils.ActivitySkipUtil;
import com.etong.android.frame.utils.L;
import com.etong.android.frame.utils.ListAdapter;
import com.etong.android.frame.widget.CircleImageView;
import com.etong.android.frame.widget.TitleBar;
import com.etong.android.jxappcarfinancial.activity.CF_OrderRecordActivity;
import com.etong.android.jxappcarfinancial.activity.CF_RecordActivity;
import com.etong.android.jxappcarfinancial.activity.CF_RepaymentRemindActivity;
import com.etong.android.jxappfours.find_car.grand.maintain_progress.Find_car_MaintainProgressActivity;
import com.etong.android.jxappfours.order.Fours_MyCarActivity;
import com.etong.android.jxappme.JavaBeam.MeItemBeam;
import com.etong.android.jxappme.R;
import com.etong.android.jxappme.adapter.MeMainListAdapter;
import com.etong.android.jxappme.common.MeProvider;

import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.List;


/**
 * "我的"子模块的Fragment主界面
 * Created by Administrator on 2016/8/1 0001.
 */
public class MeMainFragment extends BaseSubscriberFragment {


    private CircleImageView personMyHead;
    private TextView personMyHeadName;
    private ImageView ivCoupon;
    private TextView tvCoupon;
    private TextView tvCouponNum;
    private ImageView ivCollect;
    private TextView tvCollect;
    private TextView tvCollectNum;
    private ListView personListview;
    private ScrollView mScrollView;

    private TitleBar mTitleBar;
    private MeMainListAdapter adapter;
    private Button loginBtnHead;
    private FrameEtongApplication mFrameEtongApplication;
    boolean isFirst = true;
    private boolean isOnResume = false;
    private HttpPublisher mHttpPublisher;
    private MeProvider mProvider;

    @Override
    protected View onInit(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.me_content_frg,
                container, false);
        //初始化请求
        mHttpPublisher = HttpPublisher.getInstance();
        mHttpPublisher.initialize(getActivity());
        mProvider = MeProvider.getInstance();
        mProvider.initialize(mHttpPublisher);

        mFrameEtongApplication = FrameEtongApplication.getApplication();
        initView(view);
        isFirst = false;
        return view;
    }

    private void initView(View view) {
        // 获取屏幕像素相关信息
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);

        mTitleBar = new TitleBar(view);
        mTitleBar.getBackButton().setBackgroundResource(R.mipmap.ic_me_person);
        mTitleBar.getBackButton().setClickable(false);
        // 设置左边的按钮的边距
        RelativeLayout.LayoutParams para = new RelativeLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        para.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        para.addRule(RelativeLayout.CENTER_VERTICAL);
        para.leftMargin = (int) (20 * dm.density + 0.5f);
        mTitleBar.getBackButton().setLayoutParams(para);

        mTitleBar.showNextButton(true);
        mTitleBar.setNextButton("");
        mTitleBar.getNextButton().setBackgroundResource(R.mipmap.ic_me_message);
        // 设置右边的按钮的边距
        RelativeLayout.LayoutParams para2 = new RelativeLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        para2.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        para2.addRule(RelativeLayout.CENTER_VERTICAL);
        para2.rightMargin = (int) (15 * dm.density + 0.5f);
        mTitleBar.getNextButton().setLayoutParams(para2);
        mTitleBar.setNextOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mFrameEtongApplication.isLogin()) {
                    ActivitySkipUtil.skipActivity(getActivity(), MePersonalMessageActivity.class);
                } else {
                    toastMsg("您还没有登录，请先登录！");
                    ActivitySkipUtil.skipActivity(getActivity(), FramePersonalLoginActivity.class);
                }
            }
        });


        mTitleBar.setTitle("个人中心");
        // 设置title的位置和边距
        RelativeLayout.LayoutParams para3 = new RelativeLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        para3.addRule(RelativeLayout.RIGHT_OF, R.id.titlebar_back_button);
        para3.addRule(RelativeLayout.CENTER_VERTICAL);
        para3.leftMargin = (int) (5 * dm.density + 0.5f);
        mTitleBar.getTitleName().setLayoutParams(para3);
        mTitleBar.showBottomLin(false);

        loginBtnHead = findViewById(view, R.id.me_btn_head_login, Button.class);
        personMyHead = findViewById(view, R.id.me_img_head, CircleImageView.class);
        personMyHeadName = findViewById(view, R.id.me_txt_headname, TextView.class);
        tvCouponNum = findViewById(view, R.id.me_txt_coupon_num, TextView.class);
        tvCollectNum = findViewById(view, R.id.me_txt_collect_num, TextView.class);
        personListview = findViewById(view, R.id.me_lv_info, ListView.class);

        mScrollView = findViewById(view, R.id.me_sv_scroll, ScrollView.class);

        personMyHead.setBorderColor(getActivity().getResources().getColor(R.color.white));
        personMyHead.setBorderWidth(6);

        tvCouponNum.setText("0");

        initListView();
//        mScrollView.smoothScrollTo(0, 0);//listView不上滑的方法
        addClickListener(view, R.id.me_ll_coupon);
        addClickListener(view, R.id.me_ll_collect);
        addClickListener(personMyHead);
        addClickListener(loginBtnHead);

//        showHeadView();

        setCanRequest();
        isLogin();
    }

    protected void isLogin() {

//        tvCollectNum.setText(getCollectVidList() + "");

        if (mFrameEtongApplication.isLogin()) {
            showHeadView();
            FrameUserInfo mFrameUserInfo = mFrameEtongApplication.getUserInfo();

            if (null != mFrameUserInfo.getAvatar()) {
                ImageProvider.getInstance().loadImage(personMyHead, mFrameUserInfo.getAvatar());
            } else {
                if (null != mFrameUserInfo.getUserSex()) {
                    if (mFrameUserInfo.getUserSex().equals("男")) {
                        ImageProvider.getInstance().loadImage(personMyHead, "personMyHead", R.mipmap.ic_me_head_boy);
                    } else {
                        ImageProvider.getInstance().loadImage(personMyHead, "personMyHead", R.mipmap.ic_me_head_girl);
                    }
                } else {
                    ImageProvider.getInstance().loadImage(personMyHead, "personMyHead", R.mipmap.ic_me_head_boy);
                }
            }

            if (null != mFrameUserInfo.getUserName() && !TextUtils.isEmpty(mFrameUserInfo.getUserName().trim())) {
                personMyHeadName.setText(mFrameUserInfo.getUserName());
            } else {
                personMyHeadName.setText("未设置");
            }
        } else {
            showLoginView();
        }
        personMyHead.setFocusable(false);
    }


    protected void initListView() {
        adapter = new MeMainListAdapter(getActivity());
        personListview.setAdapter(adapter);
        setListViewHeightBasedOnChildren(personListview);
    }


    @Override
    protected void onClick(View v) {
        if (v.getId() == R.id.me_ll_coupon) {//优惠券
            if (mFrameEtongApplication.isLogin()) {
//                Intent intent = new Intent(getActivity(), MeCouponActivity.class);
//                intent.putExtra("CouponNum", tvCouponNum.getText().toString());
//                startActivity(intent);
                toastMsg("暂无优惠券~");
            } else {
                toastMsg("您还没有登录，请先登录！");
                ActivitySkipUtil.skipActivity(getActivity(), FramePersonalLoginActivity.class);
            }
        } else if (v.getId() == R.id.me_ll_collect) {//收藏
            if (mFrameEtongApplication.isLogin()) {
                ActivitySkipUtil.skipActivity(getActivity(), MeCollectActivity.class);
//                ActivitySkipUtil.skipActivity(getActivity(), MeTestCollectActivity.class);
            } else {
                toastMsg("您还没有登录，请先登录！");
                ActivitySkipUtil.skipActivity(getActivity(), FramePersonalLoginActivity.class);
            }
        } else if (v.getId() == R.id.me_img_head) {//头像
            ActivitySkipUtil.skipActivity(getActivity(), MePersonalCompleteActivity.class);
        } else if (v.getId() == R.id.me_btn_head_login) {//登录
            ActivitySkipUtil.skipActivity(getActivity(), FramePersonalLoginActivity.class);
        }
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
            Class<com.etong.android.jxappme.R.mipmap> cls = R.mipmap.class;
            try {
                value = cls.getDeclaredField(name).getInt(null);
            } catch (Exception e) {

            }
        }
        return value;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!isFirst) {
            isLogin();
            setCollect();
        }
        isOnResume = true;
    }

    private void showLoginView() {
        loginBtnHead.setVisibility(View.VISIBLE);
        personMyHead.setVisibility(View.GONE);
        personMyHeadName.setText("立即登录体验更多");
        personMyHeadName.setTextSize(15);
    }

    private void showHeadView() {
        personMyHead.setVisibility(View.VISIBLE);
        loginBtnHead.setVisibility(View.GONE);
        personMyHeadName.setTextSize(18);
    }

    @Subscriber(tag = FrameHttpTag.GET_COLLECT_TOTAL)
    protected void getCollectTotalResult(HttpMethod method) {
        L.d("----------->", "请求了收藏总数");
        String errno = method.data().getString("errno");
        String flag = method.data().getString("flag");
        String msg = method.data().getString("msg");

        if (!TextUtils.isEmpty(flag) && flag.equals("0")) {

            String num = method.data().getString("data");
            tvCollectNum.setText(num);
        } else {
            //设置收藏个数
            int usedCarCollect = 0;
            int newCarCollect = 0;
            if (mFrameEtongApplication.isLogin()) {
                if (null != mFrameEtongApplication.getUsedCarCollectCache().getCarList()) {
                    usedCarCollect = mFrameEtongApplication.getUsedCarCollectCache().getCarList().size();
                }
                if (null != mFrameEtongApplication.getNewCarCollectCache().getCarList()) {
                    newCarCollect = mFrameEtongApplication.getNewCarCollectCache().getCarList().size();
                }
            }
            L.d("----------->", "设置了收藏个数 :" + (usedCarCollect + newCarCollect));
            tvCollectNum.setText((usedCarCollect + newCarCollect) + "");
        }
    }


    /**
     * @param listView 要设置高度的ListView
     * @return void 返回类型
     * @throws
     * @Title : setListViewHeightBasedOnChildren
     * @Description : TODO(重新设置ListView的高度以解决ScrollView中的ListView显示不正常的问题)
     * @params
     */
    public void setListViewHeightBasedOnChildren(ListView listView) {
        android.widget.ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if (!hidden) {
            if (!isOnResume) {
                if (null != mScrollView) {
                    mScrollView.scrollTo(0, 0);//listView不上滑的方法
                    personListview.setFocusable(false);

                    setCollect();
                }
            }
            isOnResume = false;
        }
    }

    protected void setCollect() {

        //通过判断收藏浏览记录是否改变来设置是否请求接口
        if ((mFrameEtongApplication.getUsedCarCollectCache().isChanged() || mFrameEtongApplication.getNewCarCollectCache().isChanged())) {
            mProvider.getCollectTotal();
            UsedAndNewCollectCar tempUsedCarCollect = mFrameEtongApplication.getUsedCarCollectCache();
            tempUsedCarCollect.setChanged(false);
            mFrameEtongApplication.setUsedCarCollectCache(tempUsedCarCollect);
            UsedAndNewCollectCar tempNewCarCollect = mFrameEtongApplication.getNewCarCollectCache();
            tempNewCarCollect.setChanged(false);
            mFrameEtongApplication.setNewCarCollectCache(tempNewCarCollect);
        }
    }

    /**
     * @desc (设置可请求数据的缓存)
     * @createtime 2016/11/8 0008-10:28
     * @author wukefan
     */
    private void setCanRequest() {
        UsedAndNewCollectCar temp = FrameEtongApplication.getApplication().getUsedCarCollectCache();
        temp.setChanged(true);
        FrameEtongApplication.getApplication().setUsedCarCollectCache(temp);
    }

}
