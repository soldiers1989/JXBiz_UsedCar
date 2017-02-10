package com.etong.android.jxappfours.find_car.collect_search.main_content;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RadioButton;

import com.etong.android.frame.subscriber.BaseSubscriberActivity;
import com.etong.android.frame.subscriber.BaseSubscriberFragment;
import com.etong.android.frame.user.FramePersonalLoginActivity;
import com.etong.android.frame.utils.ActivitySkipUtil;
import com.etong.android.jxappfours.R;
import com.etong.android.jxappfours.find_car.collect_search.javabean.Find_Car_VechileCollect;
import com.etong.android.jxappfours.find_car.filtrate.Find_Car_FiltrateFragment;
import com.etong.android.jxappfours.find_car.grand.grand_frag.Find_car_GrandFragment;

import java.util.ArrayList;
import java.util.List;

/***
 * 找车页面 --》大的fragment
 */
public class Find_Car_MainActivity extends BaseSubscriberActivity {
    private BaseSubscriberFragment mFragments[] = new BaseSubscriberFragment[3];
    private RadioButton mRadioButtons[] = new RadioButton[3];
    private List<Find_Car_VechileCollect> mList=new ArrayList<Find_Car_VechileCollect>();

    private Fragment mContent;  // 默认显示当前布局，只在第一次加载时初始化

    @Override
    protected void onInit(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.find_car_activity_vechile_main);

        // 设置该Activity为不可滑动
        setSwipeBackEnable(false);

        initView();
    }


    protected void initView() {

        ImageButton img_back=findViewById(R.id.find_car_img_back,ImageButton.class);
        ImageButton img_search=findViewById(R.id.find_car_img_search,ImageButton.class);
        mRadioButtons[0] = findViewById(R.id.find_car_rb_vechile_brand,
                RadioButton.class);
        mRadioButtons[1] = findViewById(R.id.find_car_rb_vechile_collect,
                RadioButton.class);
        mRadioButtons[2] = findViewById(R.id.find_car_rb_vechile_condition,
                RadioButton.class);
        addClickListener(mRadioButtons);
        addClickListener(R.id.find_car_img_back);
        addClickListener(R.id.find_car_img_search);
        initFragment();
    }

    protected void initFragment() {
        mFragments[0] = new Find_car_GrandFragment();           // 品牌界面
        mFragments[1] = new Find_Car_VechileCollectPriceFragment();//收藏
        mFragments[2] = new Find_Car_FiltrateFragment();
//        switchFragment(0);

        setDefaultFragment(mFragments[0]);
    }

    public void startVechileSearchActivity() {
        Intent intent = new Intent(Find_Car_MainActivity.this,
                Find_Car_SearchActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onClick(View view) {
//        switch (view.getId()) {
//
//            case R.id.find_car_rb_vechile_brand:
//                switchFragment(0);
//                break;
//            case R.id.find_car_rb_vechile_collect:
//                switchFragment(1);
//                break;
//            case R.id.find_car_rb_vechile_condition:
//                switchFragment(2);
//                break;
//            case R.id.find_car_img_search:
//                startVechileSearchActivity();
//                break;
//        }

        if (view.getId() == R.id.find_car_rb_vechile_brand) {
            switchFragment(0);
        } else if (view.getId() ==R.id.find_car_rb_vechile_collect) {
            switchFragment(1);
        }else if (view.getId() ==R.id.find_car_rb_vechile_condition) {
            switchFragment(2);
        }else if (view.getId() ==R.id.find_car_img_search) {
            startVechileSearchActivity();
        }else if(view.getId() ==R.id.find_car_img_back){
//            Intent intent =new Intent(this,Models_Contrast_MainActivity.class);
//            startActivity(intent);
            this.finish();
        }
    }

    //3个fragment切换
    public void switchFragment(int index) {
        // toastMsg(index, "SwitchFragment");

//        if (index < mFragments.length) {
//            FragmentManager manager = getSupportFragmentManager();
//
//            FragmentTransaction transaction = manager.beginTransaction();
//            transaction.replace(R.id.find_car_flg_vechile_fragment_container,
//                    mFragments[index], "");
//
//            transaction.commit();
//
//
//
//            mRadioButtons[index].setChecked(true);
//        }

        // 不要被替换，不然会重新加载页面
        if (mContent != mFragments[index]) {
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            if (!mFragments[index].isAdded()) { // 先判断是否被add过
                transaction.hide(mContent).add(R.id.find_car_flg_vechile_fragment_container,
                        mFragments[index]).commitAllowingStateLoss(); // 隐藏当前的fragment，add下一个到Activity中
            } else {
                transaction.hide(mContent).show(mFragments[index]).commitAllowingStateLoss(); // 隐藏当前的fragment，显示下一个
            }
            mContent = mFragments[index];
        }
    }

    /**
     * 设置当前布局为默认布局
     * @param defaultFragment
     */
    public void setDefaultFragment(Fragment defaultFragment) {
        FragmentManager mFm = getSupportFragmentManager();
        FragmentTransaction mFragmentTrans = mFm.beginTransaction();

        mFragmentTrans.add(R.id.find_car_flg_vechile_fragment_container, defaultFragment).commitAllowingStateLoss();

        mContent = defaultFragment;
    }

}
