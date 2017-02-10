package com.etong.android.jxappfours.find_car.collect_search.javabean;

/**
 * 搜索到结果的javabean
 * Created by Administrator on 2016/8/11.
 */
public class Find_Car_Search_Result {


    /**
     * image : http://113.247.237.98:10002/auto5.1/images/common/qichetupian/aodi_a6l.jpg##
     * country : null
     * website :
     * maxOut : 2995
     * level : 2
     * minOut : 1798
     * pTitle : 一汽奥迪
     * fullName : 一汽奥迪A6L
     * pid : 146
     * carlevel : 中大型车
     * title : A6L
     * trueorder : 2
     * minguide : 27.28
     * brandDesc :
     * outVol : 1798	1984	2393	2498
     * letter : A
     * id : 149
     * maxguide : 74.26
     * modelsLevel : 369
     */

    private Integer minOut;             // 最低排量
    private Integer maxOut;             // 最高排量
    private String image;
    private String pTitle;
    private String fullName;
    private Integer pid;
    private String carlevel;
    private String title;
    private Double minguide;
    private String outVol;
    private String letter;
    private Integer id;
    private Double maxguide;
    private int level;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPTitle() {
        return pTitle;
    }

    public void setPTitle(String pTitle) {
        this.pTitle = pTitle;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getCarlevel() {
        return carlevel;
    }

    public void setCarlevel(String carlevel) {
        this.carlevel = carlevel;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getMinguide() {
        return minguide;
    }

    public void setMinguide(Double minguide) {
        this.minguide = minguide;
    }

    public String getOutVol() {
        return outVol;
    }

    public void setOutVol(String outVol) {
        this.outVol = outVol;
    }

    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getMaxguide() {
        return maxguide;
    }

    public void setMaxguide(Double maxguide) {
        this.maxguide = maxguide;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Integer getMinOut() {
        return minOut;
    }

    public void setMinOut(Integer minOut) {
        this.minOut = minOut;
    }

    public Integer getMaxOut() {
        return maxOut;
    }

    public void setMaxOut(Integer maxOut) {
        this.maxOut = maxOut;
    }
}
