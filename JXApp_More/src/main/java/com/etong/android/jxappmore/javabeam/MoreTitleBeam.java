package com.etong.android.jxappmore.javabeam;

import java.util.List;

/**
 * Created by Administrator on 2016/8/4 0004.
 */
public class MoreTitleBeam {

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


}
