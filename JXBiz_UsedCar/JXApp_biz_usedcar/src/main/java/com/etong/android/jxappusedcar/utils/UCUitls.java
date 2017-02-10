package com.etong.android.jxappusedcar.utils;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @desc (这里用一句话描述这个类的作用)
 * @createtime 2016/10/20 - 20:34
 * @Created by xiaoxue.
 */

public class UCUitls {

    public static  Map fastMap(List keyList,List valueList){
        Map temMap= new HashMap();
        if (keyList.size()!=valueList.size()){
            return temMap;
        }
        for(int i=0;i<keyList.size();i++){
            temMap.put(keyList.get(i),valueList.get(i));
        }
        return temMap;
    }

    public  static Map fastMapRemoveNullValue(List keyList,List valueList){
        Map temMap= new HashMap();
        if (keyList.size()!=valueList.size()){
            return temMap;
        }
        for(int i=0;i<keyList.size();i++){
            if (null==valueList.get(i) || "".equals(valueList.get(i))){

            }else{
                temMap.put(keyList.get(i),valueList.get(i));
            }
        }
        return temMap;
    }
}
