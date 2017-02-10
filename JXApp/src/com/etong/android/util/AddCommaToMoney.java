package com.etong.android.util;

public class AddCommaToMoney {
    /**
     * @Title: handMoney
     * @Description: TODO(钱的数目，每三位加个逗号)
     * @param @param money
     * @param @return 设定文件
     * @return String 返回类型
     * @throws
     */
    private static String reverseStr;
    private static String decimals;
    private static String number;
    private static String symbol;

    public static String AddCommaToMoney(String money) {
        symbol = null;
        if (money.contains("-")) {
            symbol = "-";
            money = money.replace("-", "");
        }

        // 将传进数字反转
        if (money.contains(".")) {
            String integers = money.substring(0, money.indexOf("."));
            decimals = money.substring(money.indexOf("."));
            reverseStr = new StringBuilder(integers).reverse().toString();
        } else {
            reverseStr = new StringBuilder(money).reverse().toString();

        }

        String strTemp = "";
        for (int i = 0; i < reverseStr.length(); i++) {
            if (i * 3 + 3 > reverseStr.length()) {
                strTemp += reverseStr.substring(i * 3, reverseStr.length());
                break;
            }
            strTemp += reverseStr.substring(i * 3, i * 3 + 3) + ",";
        }
        // 将[789,456,] 中最后一个[,]去除
        if (strTemp.endsWith(",")) {
            strTemp = strTemp.substring(0, strTemp.length() - 1);
        }
        // 将数字重新反转
        if (money.contains(".")) {
            String resultStr = new StringBuilder(strTemp).reverse().toString();
            number = resultStr + decimals;
        } else {

            number = new StringBuilder(strTemp).reverse().toString();
        }

        if (null != symbol) {
            number = symbol + number;
        }
        return number;
    }

}
