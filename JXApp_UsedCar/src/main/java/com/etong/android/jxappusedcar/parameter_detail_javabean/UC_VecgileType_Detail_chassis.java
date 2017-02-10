package com.etong.android.jxappusedcar.parameter_detail_javabean;

/**
 * 底盘转向
 * Created by Administrator on 2016/8/24.
 */
public class UC_VecgileType_Detail_chassis {


    /**
     *  "powertype"         : "无助力",
     *  "drivesystem"       : "中置后驱",           // 驱动方式
     *  "suspensionType"    : "钢板弹簧非独立悬架",
     *  "driveSystem"       : "11",
     *  "frontSuspensionType": "麦弗逊式独立悬挂",
     *  "structure"         : "1",
     *  "powerType"         : "4"                   // 助力类型
     */

    private String powertype;
    private String drivesystem;
    private String suspensionType;
    private String driveSystem;
    private String frontSuspensionType;
    private String structure;
    private String powerType;

    public String getPowertype() {
        return powertype;
    }

    public void setPowertype(String powertype) {
        this.powertype = powertype;
    }

    public String getDrivesystem() {
        return drivesystem;
    }

    public void setDrivesystem(String drivesystem) {
        this.drivesystem = drivesystem;
    }

    public String getSuspensionType() {
        return suspensionType;
    }

    public void setSuspensionType(String suspensionType) {
        this.suspensionType = suspensionType;
    }

    public String getDriveSystem() {
        return driveSystem;
    }

    public void setDriveSystem(String driveSystem) {
        this.driveSystem = driveSystem;
    }

    public String getFrontSuspensionType() {
        return frontSuspensionType;
    }

    public void setFrontSuspensionType(String frontSuspensionType) {
        this.frontSuspensionType = frontSuspensionType;
    }

    public String getStructure() {
        return structure;
    }

    public void setStructure(String structure) {
        this.structure = structure;
    }

    public String getPowerType() {
        return powerType;
    }

    public void setPowerType(String powerType) {
        this.powerType = powerType;
    }
}
