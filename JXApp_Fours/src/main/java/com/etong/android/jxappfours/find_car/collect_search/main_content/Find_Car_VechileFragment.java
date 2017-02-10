package com.etong.android.jxappfours.find_car.collect_search.main_content;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.etong.android.frame.common.ImageProvider;
import com.etong.android.frame.subscriber.BaseSubscriberFragment;
import com.etong.android.jxappfours.R;

import java.util.List;

/**
 * 三个fragment下对应的fragment  通用的fragement
 * <p/>
 * Created by Administrator on 2016/8/8.
 */
public abstract class Find_Car_VechileFragment extends BaseSubscriberFragment {

    private View view;
    private ImageProvider mImageProvider;
    private RadioButton mRadioButtons[];
    private BaseSubscriberFragment mFragments[];
    private BaseSubscriberFragment baseFragment;
    private RadioButton radiobutton;
    private FragmentManager fragment_manager;
    private FragmentTransaction transaction;

    private List<BaseSubscriberFragment> mFragmentList;

    public static int flag = 0;
    @Override
    protected View onInit(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mImageProvider = ImageProvider.getInstance();

        view = inflater.inflate(R.layout.find_car_base_fragment_frg,
                container, false);

        return view;
    }

    // 1.载入topbutton
    public void initTopButton(Context context, View view, String list[]) {
        RadioGroup group = (RadioGroup) view.findViewById(R.id.find_car_radiogroup);
        RadioButton button = (RadioButton) view.findViewById(R.id.find_car_left_radiobutton);
        RadioButton button1 = (RadioButton) view.findViewById(R.id.find_car_right_radiobutton);


        mRadioButtons = new RadioButton[list.length];
        mFragments = new BaseSubscriberFragment[list.length];
        mRadioButtons[0] = button;
        mRadioButtons[1] = button1;
        for (int i = 0; i < list.length; i++) {
            mRadioButtons[i].setText(list[i]);
            //设置默认选中第一个
            if (i == 0) {
                mRadioButtons[i].setChecked(true);
                fragment_manager = getChildFragmentManager();
                transaction = fragment_manager.beginTransaction();
                // 默认选中第一个fragment和第一个button
                switchFragments(transaction, 0);
            }
        }
        //切换监听
        group.setOnCheckedChangeListener(listener);
    }
    public void getFragment(List<BaseSubscriberFragment> fragmentList) {
        this.mFragmentList = fragmentList;
    }

    // radiobutton 切换
    RadioGroup.OnCheckedChangeListener listener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {

            for (int i = 0; i < mRadioButtons.length; i++) {
                if (mRadioButtons[i].getId() == checkedId) {
                    // Fragment中嵌套子Fragment，需要获取ChildFragmentManager
                    FragmentManager fragment_manager = getChildFragmentManager();
                    FragmentTransaction transaction = fragment_manager.beginTransaction();
                    switchFragments(transaction, i);

                    return;
                }
            }

        }
    };

    //暴露一个处理数据的方法
    protected abstract void processing_data();

    // 切换fragment
    private void switchFragments(FragmentTransaction mTransaction, int index) {
        processing_data();
        flag = index;
        for (int i = 0; i < mFragmentList.size(); i++) {

            if (index == i) {
                if (mFragments[i] == null) {
                    mFragments[i] = mFragmentList.get(i);
                 /*   mTransaction.add(R.id.find_car_frg_list,
                            mFragments[i]);*/
                }
                mTransaction.show(mFragments[i]);
                mTransaction.commitAllowingStateLoss();
            } else {
                if (mFragments[i] != null) {
                    mTransaction.hide(mFragments[i]);

                }
            }
        }
    }

}
