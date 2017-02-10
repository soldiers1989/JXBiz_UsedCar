package com.etong.android.jxappfours.models_contrast.javabean;

import java.io.Serializable;
import java.util.Comparator;

/**
 * 选择车型
 * Created by Administrator on 2016/8/15.
 */
public class Models_Contrast_VechileType implements Serializable {
    public static final int ITEM = 0;       // list中的item
    public static final int SECTION = 1;    // list中的title
    public int sectionPosition;     // 悬浮标题的位置
    public int listPosition;        // item的显示位置

    public int type;
    public String charTv;

    private String image;               // 图片的地址 如："http://113.247.237.98:10002/auto5.1/userfiles/car/2015112608/2015112608562839.jpg"
    private String year;                // 年份       如： “2015”
    private String subject;            //  汽车的名字 如： "ACS35 35i"
    private Integer imageNum;           // 图片数量    如： 109
    private String manu;               //              如： "AC Schnitzer"
    private String fullName;           //              如： "AC SchnitzerAC Schnitzer X52015款ACS35 35i"
    private String title;              //              如：  "2015款ACS35 35i"
    private int vid;                //  车型id      如：  769596
    private int carset;             //  车系id      如： 4922
    private String salestatus;        //   车辆销售状态  如："在售"
    private String carsetTitle;       //   车系名字      如： "AC Schnitzer X5"
    private double prices;             //  车辆价格 （万）如： 110
    private Double outputVol;         // 汽车的排量    如：   2979
    private String brand;              // 品牌名称      如：  "AC Schnitzer"
    private int salestatusid;       //              如：  1325

    private boolean isChecked = false;//是否选中

    private String f_collectstatus;     //是否收藏

    public String getF_collectstatus() {
        if (f_collectstatus == null) {
            f_collectstatus = "0";
        }
        return f_collectstatus;
    }

    public void setF_collectstatus(String f_collectstatus) {
        this.f_collectstatus = f_collectstatus;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public Models_Contrast_VechileType() {

    }

    public Models_Contrast_VechileType(int type, String outputVol) {
        this.type = type;
        this.charTv = outputVol;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getVid() {
        return vid;
    }

    public void setVid(Integer vid) {
        this.vid = vid;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Double getPrices() {
        return prices;
    }

    public void setPrices(Double prices) {
        this.prices = prices;
    }

    public int getCarset() {
        return carset;
    }

    public void setCarset(int carset) {
        this.carset = carset;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getSalestatus() {
        return salestatus;
    }

    public void setSalestatus(String salestatus) {
        this.salestatus = salestatus;
    }

    public String getCarsetTitle() {
        return carsetTitle;
    }

    public void setCarsetTitle(String carsetTitle) {
        this.carsetTitle = carsetTitle;
    }

    public Integer getImageNum() {
        if(null==imageNum){
            imageNum=0;//imageNum为空就设置默认为0
        }
        return imageNum;
    }

    public void setImageNum(Integer imageNum) {
        this.imageNum = imageNum;
    }

    public Double getOutputVol() {
        if (outputVol!=null) {
            return outputVol;
        } else {
            return 0.0;
        }
    }

    public void setOutputVol(Double outputVol) {
        this.outputVol = outputVol;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }


    public String getManu() {
        return manu;
    }

    public void setManu(String manu) {
        this.manu = manu;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setVid(int vid) {
        this.vid = vid;
    }

    public void setPrices(double prices) {
        this.prices = prices;
    }

    public int getSalestatusid() {
        return salestatusid;
    }

    public void setSalestatusid(int salestatusid) {
        this.salestatusid = salestatusid;
    }

}

