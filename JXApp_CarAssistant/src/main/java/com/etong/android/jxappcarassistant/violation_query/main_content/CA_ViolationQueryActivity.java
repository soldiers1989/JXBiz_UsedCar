package com.etong.android.jxappcarassistant.violation_query.main_content;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.ReplacementTransformationMethod;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.etong.android.frame.publisher.HttpMethod;
import com.etong.android.frame.publisher.HttpPublisher;
import com.etong.android.frame.request_init.FrameHttpTag;
import com.etong.android.frame.subscriber.BaseSubscriberActivity;
import com.etong.android.frame.user.FrameUserInfo;
import com.etong.android.frame.utils.ListAdapter;
import com.etong.android.frame.widget.TitleBar;
import com.etong.android.jxappcarassistant.R;
import com.etong.android.jxappcarassistant.gas_tation.utils.Oil_PriceProvider;
import com.etong.android.jxappcarassistant.violation_query.bean.CA_ViolationQueryBean;
import com.etong.android.jxappfours.order.javabeam.Fours_MyCarItemBean;

import org.simple.eventbus.Subscriber;

import java.util.Arrays;

/**
 * @desc 违章查询界面
 * @createtime 2016/9/28 0028--20:07
 * @Created by wukefan.
 */
public class CA_ViolationQueryActivity extends BaseSubscriberActivity {

    private TextView mTxtDetails;
    private TextView mTxtCity;
    private Button mBtnPlate;
    private EditText mEdtPlate;
    private EditText mEdtChassis;
    private EditText mEdtEngine;
    private PopupWindow mPopup;
    private RadioButton[] mCarLevelButtons = new RadioButton[2];
    private String[] plateDatas = {"京", "沪", "浙", "苏", "粤", "鲁", "晋", "冀", "豫", "川", "渝", "辽", "吉", "黑", "皖", "鄂", "湘", "赣", "闽", "陕", "甘", "宁", "蒙", "津", "贵", "云", "桂", "琼", "青", "新", "藏"};
    private LinearLayout mViolationQueryView;
    private TitleBar mTitleBar;
    private String mPlate;
    private boolean isHasCar;

    private Oil_PriceProvider mPriceProvider;
    private HttpPublisher mHttpPublisher;
    private Button mCommit;

    @Override
    protected void onInit(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.ca_activity_violation_query);
        mHttpPublisher = HttpPublisher.getInstance();
        mHttpPublisher.initialize(this);
        mPriceProvider = Oil_PriceProvider.getInstance();
        mPriceProvider.initialize(mHttpPublisher);

        Intent i = getIntent();
        isHasCar = i.getBooleanExtra("isHasCar", false);

        initView();

        if (isHasCar) {
            FrameUserInfo.Frame_MyCarItemBean mFours_MyCarItemBean = (FrameUserInfo.Frame_MyCarItemBean) i.getSerializableExtra("mFours_MyCarItemBean");
            mBtnPlate.setText(mFours_MyCarItemBean.getPlate_no().substring(0, 1));
            mEdtPlate.setText(mFours_MyCarItemBean.getPlate_no().substring(1, mFours_MyCarItemBean.getPlate_no().length()));
            mEdtEngine.setText(mFours_MyCarItemBean.getEngine_no());
            mEdtChassis.setText(mFours_MyCarItemBean.getChassis_no());
            mEdtPlate.requestFocus();
            mBtnPlate.setEnabled(false);
            mEdtPlate.setEnabled(false);
            mEdtEngine.setEnabled(false);
            mEdtChassis.setEnabled(false);

        }
    }

    private void initView() {
        mTitleBar = new TitleBar(this);
        mTitleBar.setTitle("违章查询");
        mViolationQueryView = findViewById(R.id.ca_vq_ll, LinearLayout.class);
        mTxtDetails = findViewById(R.id.ca_vq_txt_detail_title, TextView.class);
        mTxtCity = findViewById(R.id.ca_vq_edt_city, TextView.class);

        mBtnPlate = findViewById(R.id.ca_vq_btn_type, Button.class);
        mEdtPlate = findViewById(R.id.ca_vq_edt_plate, EditText.class);
        mEdtChassis = findViewById(R.id.ca_vq_edt_chassis, EditText.class);
        mEdtEngine = findViewById(R.id.ca_vq_edt_engine, EditText.class);

        mCommit = findViewById(R.id.ca_vq_btn_commit, Button.class);

        mCarLevelButtons[0] = findViewById(R.id.ca_vq_rb_small_car, RadioButton.class);
        mCarLevelButtons[1] = findViewById(R.id.ca_vq_rb_big_car, RadioButton.class);

        addClickListener(mBtnPlate);
        addClickListener(R.id.ca_vq_btn_commit);

        mEdtPlate.setTransformationMethod(new AllCapTransformationMethod());
        mEdtChassis.setTransformationMethod(new AllCapTransformationMethod());

        mEdtPlate.addTextChangedListener(new TextWatcher() {
                                             @Override
                                             public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                             }

                                             @Override
                                             public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                                             }

                                             @Override
                                             public void afterTextChanged(Editable editable) {
                                                 String temp = editable.toString();
                                                 char[] temC = temp.toCharArray();
                                                 if (temp.length() == 1) {//第一个字符为大写字母
                                                     int mid = temC[0];
                                                     if ((mid >= 65 && mid <= 90) || (mid >= 97 && mid <= 122)) {//字母
                                                         return;
                                                     } else {
                                                         mEdtPlate.setText("");
                                                     }
                                                 }
                                             }
                                         }

        );
    }

    /**
     * @desc 设置选择车牌省份的popupwindow
     */
    private void setPopupWindow() {

        View view = LayoutInflater.from(this).inflate(com.etong.android.jxappfours.R.layout.fours_mycar_popupwindow_select, null);
        view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);

        GridView mGridView = (GridView) view.findViewById(com.etong.android.jxappfours.R.id.fours_mycar_gv_mycar_plate_select);

        ListAdapter<String> mGridListAdapter = new ListAdapter<String>(this, com.etong.android.jxappfours.R.layout.fours_mycarr_plate_items_gv) {
            @Override
            protected void onPaint(View view, final String data, int position) {
                TextView mPlateItem = findViewById(view, com.etong.android.jxappfours.R.id.fours_mycar_txt_mycar_plate_select_item, TextView.class);


                ViewGroup.LayoutParams lp = (ViewGroup.LayoutParams) mPlateItem.getLayoutParams();
                //将宽度设置为屏幕的1/9
                lp.width = (mWidth - (int) (50 * mDensity + 0.5f)) / 9;
                //将高度设置为宽度的21/18
                lp.height = ((mWidth - (int) (50 * mDensity + 0.5f)) / 9) * 21 / 18;
                //根据屏幕大小按比例动态设置图片大小
                mPlateItem.setLayoutParams(lp);

                mPlateItem.setText(data);

                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mBtnPlate.setText(data);
                        mPopup.dismiss();
                    }
                });
            }
        };

        mGridView.setAdapter(mGridListAdapter);
        mGridListAdapter.addAll(Arrays.asList(plateDatas));
        mGridListAdapter.notifyDataSetChanged();

        mPopup = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, false);
        // 允许点击外部消失
        mPopup.setBackgroundDrawable(new BitmapDrawable());//注意这里如果不设置，下面的setOutsideTouchable(true);允许点击外部消失会失效
        mPopup.setOutsideTouchable(true);   //设置外部点击关闭ppw窗口
        // 设置此参数获得焦点，否则无法点击
        mPopup.setFocusable(true);
    }

    /**
     * @desc 显示选择车牌省份的popupwindow
     */
    private void showPopupWindow() {
        //显示PopupWindow
        View rootview = LayoutInflater.from(CA_ViolationQueryActivity.this).inflate(R.layout.ca_activity_violation_query, null);
        mPopup.showAtLocation(rootview, Gravity.BOTTOM, 0, 0);
//        int popupWidth = view.getMeasuredWidth();    //  获取测量后的宽度
//        int popupHeight = view.getMeasuredHeight();  //获取测量后的高度
//        int[] location = new int[2];
//
//        //mPopup.showAsDropDown(mBtnPlate);
//        // 获得位置 这里的v是目标控件，就是你要放在这个v的上面还是下面
//        v.getLocationOnScreen(location);
//        //这里就可自定义在上方和下方了 ，这种方式是为了确定在某个位置，某个控件的左边，右边，上边，下边都可以
//        mPopup.showAtLocation(v, Gravity.NO_GRAVITY, location[0] / 2, location[1] / 3 * 2 - v.getHeight() / 2 - (int) (5 * mDensity + 0.5f));
    }

    @Override
    protected void onClick(View v) {
        if (v.getId() == R.id.ca_vq_btn_type) {//选择车牌号省份
            if (null == mPopup) {
                setPopupWindow();
            }
            showPopupWindow();
        } else if (v.getId() == R.id.ca_vq_btn_commit) {//提交
            onCommit();
        }
    }

    /**
     * @desc 提交判断条件
     */
    protected void onCommit() {
        String carRegex = "[\\u4e00-\\u9fa5]{1}[A-Z]{1}[A-Z_0-9]{5}";

        if (mEdtPlate.getText().toString().length() != 6) {
            toastMsg("请输入完整车牌号");
            return;
        }
        String tempPlate = mBtnPlate.getText().toString() + mEdtPlate.getText().toString();
        if (!(tempPlate.toUpperCase()).matches(carRegex)) {
            toastMsg("请输入正确车牌号");
            return;
        }
        if (mEdtEngine.getText().toString().length() != 6) {
            toastMsg("请输入完整发动机号");
            return;
        }
        if (mEdtChassis.getText().toString().length() != 17) {
            toastMsg("请输入完整车架号");
            return;
        }
//        mPlate = mBtnPlate.getText().toString() + mEdtPlate.getText().toString();
        mPlate = mEdtPlate.getText().toString();
        String mChassis = mEdtChassis.getText().toString();
        String mEngine = mEdtEngine.getText().toString();
        String lstype = null;
        if (mCarLevelButtons[0].isChecked()) {
            lstype = "02";
        } else if (mCarLevelButtons[1].isChecked()) {
            lstype = "01";
        }

        mCommit.setClickable(false);
        //请求违章查询接口
        mPriceProvider.getViolation_query(mBtnPlate.getText().toString(), mPlate.toUpperCase(), mChassis, mEngine);
//        toastMsg("您目前没有任何违章记录~");
//        finish();
    }

    /**
     * @desc 小写转大写
     */
    public class AllCapTransformationMethod extends ReplacementTransformationMethod {

        @Override
        protected char[] getOriginal() {
            char[] aa = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
            return aa;
        }

        @Override
        protected char[] getReplacement() {
            char[] cc = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
            return cc;
        }

    }

    /**
     * @desc 处理查询违章的数据
     * @createtime 2016/11/23 - 17:51
     * @author xiaoxue
     */
    @Subscriber(tag = FrameHttpTag.VIOLATION_QUERY)
    protected void getViolationQuery(HttpMethod method) {
        mCommit.setClickable(true);
        String flag = method.data().getString("flag");
        String msg = method.data().getString("msg");
        String errno = method.data().getString("errno");
        if (!TextUtils.isEmpty(flag) && flag.equals("0")) {
            String data = method.data().getString("data");
            if (!"".equals(data)) {
                JSONObject json = JSONObject.parseObject(data);
                CA_ViolationQueryBean mCA_ViolationQueryBean = (CA_ViolationQueryBean) JSON.toJavaObject(json, CA_ViolationQueryBean.class);
                Intent intent = new Intent(this, CA_ViolationOfTheQueryActivity.class);
                intent.putExtra("CA_ViolationQueryBean", mCA_ViolationQueryBean);
                startActivity(intent);
            }
        } else if (!TextUtils.isEmpty(flag) && flag.equals("1")) {
            if (null != msg && !"".equals(msg) && !TextUtils.isEmpty(msg)) {
                toastMsg(msg);
            }
        } else if (flag.equals(HttpPublisher.NETWORK_ERROR)) {
            if (null != msg && !"".equals(msg) && !TextUtils.isEmpty(msg)) {
                toastMsg(msg);
            } else {
                toastMsg("数据请求失败");
            }
        } else if (flag.equals(HttpPublisher.DATA_ERROR)) {
            if (null != msg && !"".equals(msg) && !TextUtils.isEmpty(msg)) {
                toastMsg(msg);
            } else {
                toastMsg("服务器无响应");
            }
        }
    }
}
