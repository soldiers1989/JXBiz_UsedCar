package com.etong.android.jxappfours.find_car.grand.parameter_detail_javabean;

/**
 * 灯光配置
 * Created by Administrator on 2016/8/24.
 */
public class FC_VecgileType_Detail_lamplight {


    /**
     *   "atmosphereLight"      : "-",
     *   "autoLight"            : "-",
     *   "frontFoglight"        : "-",
     *   "headlightClean"       : "-",
     *   "xenonHeadlight"       : "-",
     *   "ledDrl"               : "-",
     *   "afs"                  : "-"
     *
     */

    private String atmosphereLight;
    private String autoLight;
    private String frontFoglight;
    private String headlightClean;
//    private String headlightAdjust;           // 移动到外部配置中
    private String xenonHeadlight;
    private String ledDrl;
    private String afs;

    public String getAtmosphereLight() {
        return atmosphereLight;
    }

    public void setAtmosphereLight(String atmosphereLight) {
        this.atmosphereLight = atmosphereLight;
    }

    public String getAutoLight() {
        return autoLight;
    }

    public void setAutoLight(String autoLight) {
        this.autoLight = autoLight;
    }

    public String getFrontFoglight() {
        return frontFoglight;
    }

    public void setFrontFoglight(String frontFoglight) {
        this.frontFoglight = frontFoglight;
    }

    public String getHeadlightClean() {
        return headlightClean;
    }

    public void setHeadlightClean(String headlightClean) {
        this.headlightClean = headlightClean;
    }

    public String getXenonHeadlight() {
        return xenonHeadlight;
    }

    public void setXenonHeadlight(String xenonHeadlight) {
        this.xenonHeadlight = xenonHeadlight;
    }

    public String getLedDrl() {
        return ledDrl;
    }

    public void setLedDrl(String ledDrl) {
        this.ledDrl = ledDrl;
    }

    public String getAfs() {
        return afs;
    }

    public void setAfs(String afs) {
        this.afs = afs;
    }
}
