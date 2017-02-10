package com.etong.android.jxappcarfinancial.bean;

/**
 * @desc (所有订单记录项的javabean)
 * @createtime 2016/11/17 0017--18:59
 * @Created by wukefan.
 */
public class CF_AllOrderRecordBean {

    private Integer itemId;
    private String itemName;
    private String itemTag;
    private Integer count;

    public CF_AllOrderRecordBean(Integer itemId, Integer count, String itemName) {
        this.itemId = itemId;
        this.count = count;
        this.itemName = itemName;
    }

    public CF_AllOrderRecordBean(String itemTag, Integer count, String itemName) {
        this.itemName = itemName;
        this.itemTag = itemTag;
        this.count = count;
    }

    public String getItemTag() {
        return itemTag;
    }

    public void setItemTag(String itemTag) {
        this.itemTag = itemTag;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
