package com.etong.android.jxbizusedcar.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/8/4 0004.
 */
public class UC_MoreTitleBeam {

    private String titleName;
    private List<MoreItemBeam> itemBeamList;

    public void setTitleName(String titleName) {
        this.titleName = titleName;
    }

    public void setItemBeamList(List<MoreItemBeam> itemBeamList) {
        this.itemBeamList = itemBeamList;
    }


    public List<MoreItemBeam> getItemBeamList() {
        return itemBeamList;
    }


    public String getTitleName() {
        return titleName;
    }

    public static class MoreItemBeam {

        public String itemName;

        public String itemIconName;

        public void setItemName(String itemName) {
            this.itemName = itemName;
        }

        public void setItemIconName(String itemIconName) {
            this.itemIconName = itemIconName;
        }

        public String getItemName() {
            return itemName;
        }

        public String getItemIconName() {
            return itemIconName;
        }


    }


}
