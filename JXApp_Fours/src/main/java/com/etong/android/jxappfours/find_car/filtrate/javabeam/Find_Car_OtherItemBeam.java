package com.etong.android.jxappfours.find_car.filtrate.javabeam;

import java.util.List;

/**
 * Created by Administrator on 2016/8/2 0002.
 */
public class Find_Car_OtherItemBeam {


    /**
     * titleName : 国别
     * itemNames : [{"title":"中国","id":5},{"title":"其他","id":6}]
     */

    private String titleName;
    /**
     * title : 中国
     * id : 5
     */

    private List<ItemNamesBean> itemNames;

    public String getTitleName() {
        return titleName;
    }

    public void setTitleName(String titleName) {
        this.titleName = titleName;
    }

    public List<ItemNamesBean> getItemNames() {
        return itemNames;
    }

    public void setItemNames(List<ItemNamesBean> itemNames) {
        this.itemNames = itemNames;
    }

    public static class ItemNamesBean {
        private String title;
        private int id;
        private String outVolStar;
        private String outVolEnd;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getOutVolStar() {
            return outVolStar;
        }

        public void setOutVolStar(String outVolStar) {
            this.outVolStar = outVolStar;
        }

        public String getOutVolEnd() {
            return outVolEnd;
        }

        public void setOutVolEnd(String outVolEnd) {
            this.outVolEnd = outVolEnd;
        }
    }

}
