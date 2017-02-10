package com.etong.android.jxappfours.find_car.grand.find_car_shopcar_calc;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.etong.android.frame.subscriber.BaseSubscriberActivity;
import com.etong.android.frame.widget.TitleBar;
import com.etong.android.jxappfours.R;

/**
 * 购车计算器的实现
 */
public class Find_car_Calcu_Activity extends BaseSubscriberActivity {

    private ViewGroup car_select_content;        // 选车的包含按钮
    private EditText car_price;                    // 输入车的价格
    private Button find_car_calcu_btn;        // 确定按钮
    private TitleBar mTitleBar;

    public static final String tag = "CarcalculatorActivity";

    public static final int FROM_INPUT_MONEY = 2;
    public static final int FROM_SELECT_CAT = 1;
    @Override
    protected void onInit(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.find_car_content_shoppingcar_calcu);

        initView();
    }

    /**
     * 初始化操作
     */
    private void initView() {


        mTitleBar = new TitleBar(this);
        mTitleBar.setTitle("购车计算器");
        mTitleBar.showNextButton(false);

        car_select_content = findViewById(R.id.find_car_calcu_select_cartype, ViewGroup.class);
        car_price = findViewById(R.id.find_car_calcu_price, EditText.class);
        find_car_calcu_btn = findViewById(R.id.find_car_calcu_btn, Button.class);

        addClickListener(R.id.find_car_calcu_select_cartype);
        addClickListener(R.id.find_car_calcu_btn);
    }

    @Override
    protected void onClick(View view) {
        if (view.getId() == R.id.find_car_calcu_select_cartype) {
            // 进入选车
//            Intent goSelect = new Intent(CarcalculatorActivity.this, VechileMainActivity.class);
//            startActivity(goSelect);

        } else if (view.getId() == R.id.find_car_calcu_btn) {
            // 进入计算页面
            goCalculatorResult();
        }
    }

    /**
     * @Title        : goCalculatorResult
     * @Description  : TODO(获取到输入的价格进入到计算的结果页面)
     * @params     设定文件
     * @return
     *     void    返回类型
     * @throws
     */
    private void goCalculatorResult() {
        if (TextUtils.isEmpty(car_price.getText().toString())) {
            toastMsg("请输入正确的金额");
            return;
        }

        String price = car_price.getText().toString();
        if (Double.valueOf(price) < 0) {
            toastMsg("请输入正确的金额");
            return;
        }

        Intent goResult = new Intent(Find_car_Calcu_Activity.this, Find_car_CalcuResultActivity.class);
        goResult.putExtra("nakedcarMoney", price);
        goResult.putExtra("fromWhere", Find_car_Calcu_Activity.FROM_INPUT_MONEY);
        startActivity(goResult);
    }
}
