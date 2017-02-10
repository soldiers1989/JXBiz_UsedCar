package com.etong.android.jxappfours.find_car.grand.find_car_shopcar_calc;

import com.etong.android.frame.utils.AddCommaToMoney;
import com.etong.android.frame.utils.L;

/**
 * @author : yuanjie
 * @ClassName : CarcalculatorUtils
 * @Description : TODO(计算贷款中花费的总金额、首付、月供)
 * @date : 2016年7月20日 上午10:36:34
 */
public class Find_car_CarcalculatorUtils {
    private double mInterestRate;    // 利率
    private double interest;        // 利息
    private double otherCost;       // 其他费用 = 基本费用 + 保险

    /**
     * @param carPrice
     * @param firstPayRatio
     * @param basicPrice
     * @param commercialInsurance
     * @param interestRate
     * @return double    返回类型
     * @throws
     * @Title : getTotalMoney
     * @Description : TODO(获取到总价)
     * @params
     */
    public static String getTotalMoney(    // 总价 = （裸车价 * 首付比例） + 基本费用 + 保险 + 利息
                                           long carPrice,            // 裸车价
                                           double firstPayRatio,        // 首付比例
                                           double basicPrice,            // 基本费用
                                           double commercialInsurance, // 保险
                                           double interestRate            // 利率
    ) {

        double price = carPrice
                + basicPrice
                + commercialInsurance
                + getInterest(carPrice, firstPayRatio, interestRate);    // 获取到利息
//        L.d("获取到总价为:", price + "");
        String strPrice = String.format("%.2f", price);
        String s = strPrice.substring(0, strPrice.indexOf("."));

        return AddCommaToMoney.AddCommaToMoney(s);
    }

    /**
     * @param carPrice
     * @param firstPayRatio
     * @param interestRate
     * @param modle
     * @param basicPrice
     * @param commercialInsurance
     * @return double    返回类型
     * @throws
     * @Title : getDownPayment
     * @Description : TODO(获取到首付)
     * @params
     */
    public static String getDownPayment(
            long carPrice,        // 裸车价
            double firstPayRatio,    // 首付比例
            double interestRate,    // 利率
            int modle,                // 融资方案   等额本金=1   等额本息=2
            double basicPrice,            // 基本费用
            double commercialInsurance // 保险
    ) {
        double price = 0.0;

        if (1 == modle) {        // 等额本金
            price = (carPrice * firstPayRatio)
                    + (carPrice * (1 - firstPayRatio) * interestRate)
                    + getOtherCost(basicPrice, commercialInsurance);
        } else if (2 == modle) {    // 等额本息
            price = (carPrice * firstPayRatio)
                    + getOtherCost(basicPrice, commercialInsurance);
        }
//        L.d("获取到的首付为：", price + "");
        String strPrice = String.format("%.2f", price);
        String s = strPrice.substring(0, strPrice.indexOf("."));    // 将小数点后面的位强制去掉

        // 三位一逗号
        return AddCommaToMoney.AddCommaToMoney(s);
    }

    public static String getMonthlyPayment(
            long carPrice,        // 裸车价
            double firstPayRatio,    // 首付比例
            double interestRate,    // 利率
            int year,                // 贷款年限，需要转换成月份
            int modle                // 融资方案
    ) {
        double price = 0.0;

        if (1 == modle) {        // 等额本金
            double a = 1 - firstPayRatio;
            double b = carPrice * a;
            int mothly = year * 12;
            price = b / mothly;
        } else if (2 == modle) {    // 等额本息
            price = (carPrice * (1 - firstPayRatio) + getInterest(carPrice, firstPayRatio, interestRate)) / (year * 12);
            double a = 1 - firstPayRatio;
            double b = carPrice * a;
            double c = getInterest(carPrice, firstPayRatio, interestRate);
            double d = b + c;
            int mothly = year * 12;
            price = d / mothly;
        }
//        L.d("获取到的月供为：", price + "");

        String strPrice = String.format("%.2f", price);
        String s = strPrice.substring(0, strPrice.indexOf("."));

        return AddCommaToMoney.AddCommaToMoney(s);
    }

    /**
     * @param carPrice
     * @param firstPayRatio
     * @param interestRate
     * @return double    返回类型
     * @throws
     * @Title : getInterest
     * @Description : TODO(根据裸车价、首付比例、利率获取到利息的值)
     * @params
     */
    private static double getInterest(long carPrice, double firstPayRatio, double interestRate) {

//        L.d("传送过来的值为（利息）：", carPrice + "--" + firstPayRatio + "--" + interestRate);
//		double p = (1-firstPayRatio) * carPrice * interestRate;

        double a = 1 - firstPayRatio;
        double b = carPrice * a;
        double p = b * interestRate;

        String f = String.format("%.2f", p);
//        L.d("获取到的利息为：", f);
        return Double.valueOf(f);
    }

    /**
     * @param basicCost
     * @param commercialInsurance
     * @return double    返回类型
     * @throws
     * @Title : getOtherCost
     * @Description : TODO(获取到其他费用)
     * @params
     */
    private static double getOtherCost(double basicCost, double commercialInsurance) {

        double p = basicCost + commercialInsurance;
        String str = String.format("%.2f", p);

        return Double.valueOf(str);
    }


}
