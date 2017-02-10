package com.etong.android.jxappcarassistant.violation_query.bean;

/**
 * @desc (违章查询结果详情界面)
 * @createtime 2016/11/23 0023--17:58
 * @Created by wukefan.
 */
public class CA_ViolationQueryDetailsBean {

    private String leftTitle;
    private String leftContent;
    private boolean hasRight;
    private String rightTitle;
    private String rightContent;

    public CA_ViolationQueryDetailsBean(String leftTitle, String leftContent, boolean hasRight, String rightTitle, String rightContent) {
        this.leftTitle = leftTitle;
        this.leftContent = leftContent;
        this.hasRight = hasRight;
        this.rightTitle = rightTitle;
        this.rightContent = rightContent;
    }

    public String getLeftTitle() {
        return leftTitle;
    }

    public void setLeftTitle(String leftTitle) {
        this.leftTitle = leftTitle;
    }

    public String getLeftContent() {
        return leftContent;
    }

    public void setLeftContent(String leftContent) {
        this.leftContent = leftContent;
    }

    public boolean isHasRight() {
        return hasRight;
    }

    public void setHasRight(boolean hasRight) {
        this.hasRight = hasRight;
    }

    public String getRightTitle() {
        return rightTitle;
    }

    public void setRightTitle(String rightTitle) {
        this.rightTitle = rightTitle;
    }

    public String getRightContent() {
        return rightContent;
    }

    public void setRightContent(String rightContent) {
        this.rightContent = rightContent;
    }
}
