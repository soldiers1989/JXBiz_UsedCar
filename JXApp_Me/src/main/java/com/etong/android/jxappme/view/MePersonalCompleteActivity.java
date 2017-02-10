package com.etong.android.jxappme.view;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.text.method.ReplacementTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.etong.android.frame.common.ImageProvider;
import com.etong.android.frame.publisher.HttpMethod;
import com.etong.android.frame.publisher.HttpPublisher;
import com.etong.android.frame.request_init.FrameHttpTag;
import com.etong.android.frame.subscriber.BaseSubscriberActivity;
import com.etong.android.frame.user.FrameEtongApplication;
import com.etong.android.frame.user.FrameUserInfo;
import com.etong.android.frame.utils.PhotoHeadUtils;
import com.etong.android.frame.utils.UploadFileProvider;
import com.etong.android.frame.widget.CircleImageView;
import com.etong.android.frame.widget.TitleBar;
import com.etong.android.jxappme.R;
import com.etong.android.jxappme.common.MeProvider;

import org.simple.eventbus.Subscriber;

import java.util.HashMap;
import java.util.Map;

/**
 * 完善个人信息界面
 * Created by Administrator on 2016/8/30 0030.
 */
public class MePersonalCompleteActivity extends BaseSubscriberActivity {

    private TitleBar mTitleBar;
    private CircleImageView compileHead;
    private TextView compileHeadName;
    private EditText compilePhone;
    private EditText compileName;
    private EditText compileIdCard;
    private RadioButton[] mSexRadioButtons = new RadioButton[2];
    private RadioButton[] mMarryRadioButtons = new RadioButton[2];
    private ScrollView mScrollView;

    private ImageProvider mImageProvider;

    private static final String PERSON_PIC_HEAD_COMPILE = "person pic head compile";
    private String mHeadPic;
    private HttpPublisher mHttpPublisher;
    private MeProvider mProvider;
    private FrameUserInfo mFrameUserInfo;
    private Button saveBtn;

    @Override
    protected void onInit(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.me_activity_complete_personal);

        mTitleBar = new TitleBar(this);
        mTitleBar.setTitle("个人信息");
        mTitleBar.showBottomLin(false);

        mImageProvider = ImageProvider.getInstance();
        mImageProvider.initialize(this);

        //初始化请求
        mHttpPublisher = HttpPublisher.getInstance();
        mHttpPublisher.initialize(this);
        mProvider = MeProvider.getInstance();
        mProvider.initialize(mHttpPublisher);

        mFrameUserInfo = FrameEtongApplication.getApplication().getUserInfo();

        initView();
        if (FrameEtongApplication.getApplication().isLogin()) {
            getInfo();
        }

    }

    /**
     * @desc (从缓存中获取已有的用户信息)
     * @createtime 2016/12/2 0002-15:10
     * @author wukefan
     */
    protected void getInfo() {
//        FrameUserInfo mFrameUserInfo = FrameEtongApplication.getApplication().getUserInfo();

        compilePhone.setText(mFrameUserInfo.getUserPhone());
        compilePhone.setEnabled(false);

        if (null != mFrameUserInfo.getUserName() && !TextUtils.isEmpty(mFrameUserInfo.getUserName().trim())) {
            compileName.setText(mFrameUserInfo.getUserName());
        }

        if (null != mFrameUserInfo.getAvatar()) {
            ImageProvider.getInstance().loadImage(compileHead, mFrameUserInfo.getAvatar());
        } else {
            if (null != mFrameUserInfo.getUserSex()) {
                if (mFrameUserInfo.getUserSex().equals("男")) {
                    compileHead.setImageResource(R.mipmap.ic_me_head_boy);
                } else {
                    compileHead.setImageResource(R.mipmap.ic_me_head_girl);
                }
            } else {
                compileHead.setImageResource(R.mipmap.ic_me_head_boy);
            }
        }
        if (null != mFrameUserInfo.getUserSex()) {
            if (mFrameUserInfo.getUserSex().equals("男")) {
                mSexRadioButtons[0].setChecked(true);
            } else {
                mSexRadioButtons[1].setChecked(true);
            }
        }

        if (null != mFrameUserInfo.getUserMarry()) {
            if (mFrameUserInfo.getUserMarry().equals("未婚")) {
                mMarryRadioButtons[0].setChecked(true);
            } else {
                mMarryRadioButtons[1].setChecked(true);
            }
        }

        if (null != mFrameUserInfo.getUserIdCard() && !TextUtils.isEmpty(mFrameUserInfo.getUserIdCard().trim())) {
            compileIdCard.setText(mFrameUserInfo.getUserIdCard());
        }
    }

    private void initView() {
        mScrollView = findViewById(R.id.me_sv_scroll, ScrollView.class);
        compileHead = findViewById(R.id.me_img_compile_head, CircleImageView.class);         //头像
        compileHeadName = findViewById(R.id.me_txt_compile_headname, TextView.class);
        compilePhone = findViewById(R.id.me_edt_compile_tel, EditText.class);                //手机号码
        compileName = findViewById(R.id.me_edt_compile_name, EditText.class);                //姓名
        compileIdCard = findViewById(R.id.me_edt_compile_idcard, EditText.class);            //身份证号码
        saveBtn = findViewById(R.id.me_btn_put_save, Button.class);                          //保存按钮


        mSexRadioButtons[0] = findViewById(R.id.me_compile_sex_man, RadioButton.class);
        mSexRadioButtons[1] = findViewById(R.id.me_compile_sex_girl, RadioButton.class);

        mMarryRadioButtons[0] = findViewById(R.id.me_compile_single, RadioButton.class);
        mMarryRadioButtons[1] = findViewById(R.id.me_compile_married, RadioButton.class);

        compileHead.setBorderColor(this.getResources().getColor(R.color.white));
        compileHead.setBorderWidth(6);

        addClickListener(compileHead);
        addClickListener(R.id.me_btn_put_save);
//        addClickListener(R.id.me_btn_close);

        compileIdCard.setTransformationMethod(new AllCapTransformationMethod());

    }


    @Override
    protected void onClick(View v) {
        if (v.getId() == R.id.me_img_compile_head) {//上传头像
            PhotoHeadUtils.startPhotoUtils(this, PERSON_PIC_HEAD_COMPILE, true);
        } else if (v.getId() == R.id.me_btn_put_save) {//保存
            setUserInfo();
//        } else if (v.getId() == R.id.me_btn_close) {//关闭
//            finish();
        }
    }

    /**
     * @desc (判断并设置保存用户信息)
     * @createtime 2016/12/2 0002-15:12
     * @author wukefan
     */
    protected void setUserInfo() {

        String idCard = compileIdCard.getText().toString();
        if (!TextUtils.isEmpty(idCard.trim())) {
            if (idCard.length() < 18) {
                toastMsg("请输入完整身份证号");
                return;
            } else {
                String regex = "^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$|^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|X)$";

                if (!idCard.toUpperCase().matches(regex)) {
                    toastMsg("请输入正确的身份证号");
                    return;
                }
            }
        }

        Map<String, String> userInfoMap = new HashMap<>();

        if (!TextUtils.isEmpty(compileName.getText().toString().trim())) {
            mFrameUserInfo.setUserName(compileName.getText().toString().trim());
            userInfoMap.put("userName", compileName.getText().toString().trim());
        } else {
            mFrameUserInfo.setUserName("");
            userInfoMap.put("userName", "");
        }
        if (mSexRadioButtons[0].isChecked()) {
            mFrameUserInfo.setUserSex("男");
            userInfoMap.put("sex", "1");
        } else if (mSexRadioButtons[1].isChecked()) {
            mFrameUserInfo.setUserSex("女");
            userInfoMap.put("sex", "2");
        }

        if (mMarryRadioButtons[0].isChecked()) {
            mFrameUserInfo.setUserMarry("未婚");
            userInfoMap.put("maritalStatus", "1");
        } else if (mMarryRadioButtons[1].isChecked()) {
            mFrameUserInfo.setUserMarry("已婚");
            userInfoMap.put("maritalStatus", "2");
        }
        if (!TextUtils.isEmpty(idCard.trim())) {
            mFrameUserInfo.setUserIdCard(idCard.toUpperCase());
            userInfoMap.put("cardID", idCard.toUpperCase());
        } else {
            mFrameUserInfo.setUserIdCard("");
            userInfoMap.put("cardID", "");
        }

        if (null != mHeadPic) {
            mFrameUserInfo.setAvatar(mHeadPic);
            userInfoMap.put("avatar", mHeadPic);
        }

        saveBtn.setClickable(false);
        mProvider.updateUserInfo(userInfoMap);
    }

    /**
     * @desc (更新用户信息接口的回调)
     * @createtime 2016/12/2 0002-15:13
     * @author wukefan
     */
    @Subscriber(tag = FrameHttpTag.UPDATE_USER_INFO)
    protected void updateUserInfoResult(HttpMethod method) {
        saveBtn.setClickable(true);
        String errno = method.data().getString("errno");
        String flag = method.data().getString("flag");
        String msg = method.data().getString("msg");

        if (!TextUtils.isEmpty(flag) && flag.equals("0")) {
            FrameEtongApplication.getApplication().setUserInfo(mFrameUserInfo);
            toastMsg("保存成功");
            finish();
        } else if (flag.equals(HttpPublisher.NETWORK_ERROR)) {
            toastMsg("保存失败，请检查网络！");
        } else {
            toastMsg(msg);
        }
    }

    @Subscriber(tag = PERSON_PIC_HEAD_COMPILE)
    private void startUserIdPicFinish(Bitmap bitmap) {
        loadStart("正在上传图片", 0);
        UploadFileProvider.uploadFile(this, bitmap, PERSON_PIC_HEAD_COMPILE, 100);
    }

    @Subscriber(tag = PERSON_PIC_HEAD_COMPILE)
    private void uploadUserIdfinish(JSONObject data) {
        System.out.println("uploadPicfinish:" + data);
        int code = data.getIntValue("code");
        if (code == 0) {
            mHeadPic = data.getString("url");
            mImageProvider.loadImage(compileHead, mHeadPic);
            loadFinish();
        } else if (code == -1) {
            toastMsg("上传失败,重试!");
            loadFinish();
        } else if (code == -2) {
            toastMsg("请重新拍照!");
            loadFinish();
        }
    }

    //小写转大写
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
}
