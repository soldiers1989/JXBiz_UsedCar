package com.etong.android.jxappfours.find_car.grand.road_assistance;

import com.etong.android.jxappfours.find_car.grand.bean.FC_roadassiBean;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author : by sunyao
 * @ClassName : FC_roadassisDataTest
 * @Description : (这里用一句话描述这个类的作用)
 * @date : 2017/1/4 - 14:18
 */
public class FC_roadassisDataTest {

    @Test
    public void roadAssisDatas() {
        List<FC_roadassiBean> roadAssisData = FC_roadassisData.getRoadAssisData();
        FC_roadassiBean fc_roadassiBean = roadAssisData.get(1);
        assertEquals("4000730888", fc_roadassiBean.getPhone());
    }

}