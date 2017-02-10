package com.etong.android.jxappfours.find_car.grand.find_car_shopcar_calc;


import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


/**
 * @desc (计算贷款中花费的总金额、首付、月供工具类测试用例)
 * @createtime 2016/12/24 - 11:14
 * @Created by wukefan.
 */

public class Find_car_CarcalculatorUtilsTest {

    private long carPrice;                      // 裸车价
    private double firstPayRatio;               // 首付比例
    private double basicPrice;                  // 基本费用
    private double commercialInsurance;         // 保险
    private double interestRate;                // 利率
    private int year;                           // 贷款年限，需要转换成月份
    private String mTotalMoney;                 // 总价
    private String mDownPayment1;               // 等额本金的首付
    private String mDownPayment2;               // 等额本息的首付
    private String mMonthlyPayment1;            // 等额本金的月付
    private String mMonthlyPayment2;            // 等额本息的月付

    @Before
    public void setUp() throws Exception {
        carPrice = 200000;
        firstPayRatio = 0.3;
        basicPrice = 5000;
        commercialInsurance = 0;
        interestRate = 0.23;
        year = 2;
        mTotalMoney = "237,200";
        mDownPayment1 = "97,200";
        mDownPayment2 = "65,000";
        mMonthlyPayment1 = "5,833";
        mMonthlyPayment2 = "7,175";
    }

    @Test
    public void getTotalMoney() throws Exception {
        // 总价 = （裸车价 * 首付比例） + 基本费用 + 保险 + 利息
        String total = Find_car_CarcalculatorUtils.getTotalMoney(carPrice, firstPayRatio, basicPrice, commercialInsurance, interestRate);
        assertEquals(mTotalMoney, total);
    }

    @Test
    public void getDownPayment() throws Exception {
        // 首付
        // 融资方案   等额本金=1   等额本息=2
        String downPayment = Find_car_CarcalculatorUtils.getDownPayment(carPrice, firstPayRatio, interestRate, 1, basicPrice, commercialInsurance);
        assertEquals(mDownPayment1, downPayment);
        String downPayment2 = Find_car_CarcalculatorUtils.getDownPayment(carPrice, firstPayRatio, interestRate, 2, basicPrice, commercialInsurance);
        assertEquals(mDownPayment2, downPayment2);
    }

    @Test
    public void getMonthlyPayment() throws Exception {
        // 月付
        // 融资方案   等额本金=1   等额本息=2
        String monthlyPayment = Find_car_CarcalculatorUtils.getMonthlyPayment(carPrice, firstPayRatio, interestRate, year, 1);
        assertEquals(mMonthlyPayment1, monthlyPayment);
        String monthlyPayment2 = Find_car_CarcalculatorUtils.getMonthlyPayment(carPrice, firstPayRatio, interestRate, year, 2);
        assertEquals(mMonthlyPayment2, monthlyPayment2);
    }

}