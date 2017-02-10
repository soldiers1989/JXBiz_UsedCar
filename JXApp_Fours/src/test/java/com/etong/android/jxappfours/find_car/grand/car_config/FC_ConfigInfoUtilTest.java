package com.etong.android.jxappfours.find_car.grand.car_config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author : by sunyao
 * @ClassName : FC_ConfigInfoUtilTest
 * @Description : (这里用一句话描述这个类的作用)
 * @date : 2017/1/4 - 16:58
 */
public class FC_ConfigInfoUtilTest {

    private FC_ConfigInfoUtil utils;

    @Test
    public void getObjByNull() throws Exception {
        utils = new FC_ConfigInfoUtil(null);
        List compareCarInfoList = utils.getCompareCarInfoList();
        assertNull(compareCarInfoList);
    }

    @Test
    public void getObjNotNull() throws Exception {

        String str = "{\"发动机\":{\"envStandards\":\"国IV(国V)\",\"maxTorqueRev\":\"1500-3900\",\"maxHorsepower\":\"180\",\"engine\":\"2.0T 180马力 L4 EA888\",\"bore\":\"\",\"fuelGrade\":\"97号\",\"maxPower\":\"132\",\"headMaterial\":\"铝\",\"oilDrive\":\"直喷\",\"stroke\":\"\",\"zylinderreihe\":\"L型\",\"ratio\":\"\",\"fuelType\":\"汽油\",\"maxTorque\":\"320\",\"admissionGear\":\"DOHC\",\"valveNum\":\"4\",\"maxPowerRev\":\"4000-6000\",\"cylinderMaterial\":\"铸铁\"},\"多媒体配置\":{\"multiDiscDvd\":\"●\",\"interactiveServices\":\"-\",\"horn8\":\"●\",\"horn67\":\"-\",\"dvd\":\"●\",\"horn45\":\"-\",\"positioningInteractive\":\"-\",\"rearScreen\":\"○\",\"multiDisc\":\"-\",\"aux\":\"-\",\"singleDisc\":\"-\",\"horn23\":\"-\",\"bluetooth\":\"●\",\"internalharddisk\":\"●\",\"cartv\":\"○\",\"virtualDisc\":\"-\",\"centerScreen\":\"●\",\"gps\":\"●\",\"cd\":\"-\"},\"车轮制动\":{\"rearTire\":\"225/55 R17\",\"spareTire\":\"非全尺寸\",\"frontTire\":\"225/55 R17 \",\"parkingBrake\":\"电子驻车制动\",\"frontBrakeType\":\"通风盘式\",\"rearBrakeType\":\"通风盘式\"},\"内部配置\":{\"parkingAssist\":\"-\",\"gra\":\"●\",\"steeringShift\":\"●\",\"ecu\":\"●\",\"electricAdjustment\":\"○\",\"afterAdjustment\":\"●\",\"rvc\":\"●\",\"hud\":\"○\",\"downAdjustment\":\"●\",\"mfl\":\"●\",\"leatherSteering\":\"●\"},\"空调/冰箱\":{\"cooler\":\"-\",\"pollenFilter\":\"●\",\"zoneControl\":\"●\",\"manualAcc\":\"-\",\"rearAcc\":\"●\",\"rearOutlet\":\"●\",\"automaticAcc\":\"●\"},\"基本参数\":{\"avgCharge\":0.0,\"carlever\":\"中大型车\",\"desc\":\"\",\"subject\":\"TFSI 舒适型\",\"avgOilwear\":0.0,\"imageNum\":26,\"carset\":149,\"carsetTitle\":\"A6L\",\"year\":\"2014\",\"salestatus\":\"在售\",\"fullname\":\"一汽奥迪A6L2014款 TFSI 舒适型\",\"chargeNum\":0,\"officialSpeedup\":\"8.8\",\"measuredSpeedup\":\"\",\"image\":\"http://113.247.237.98:10002/data//car/20160324/d9be576a-7809-4c42-945f-f5956af1b9de.png\",\"measuredRetardation\":\"\",\"category\":\"三厢车\",\"launchTime\":\"\",\"manu\":\"一汽奥迪\",\"maxSpeed\":\"222\",\"color\":\"幻影黑,达科塔灰,石英灰,水晶银,月光蓝,朱鹭白\",\"brand\":\"奥迪\",\"prices\":44.66,\"outputVol\":1984.0,\"warranty\":\"三年或10万公里\",\"miitOilwear\":\"6.9\",\"measuredOilwear\":\"\"},\"灯光配置\":{\"atmosphereLight\":\"○\",\"xenonHeadlight\":\"●\",\"headlightClean\":\"●\",\"autoLight\":\"●\",\"afs\":\"○\",\"ledDrl\":\"●\",\"frontFoglight\":\"-\"},\"车身\":{\"category\":\"三厢车\",\"wheelbase\":\"3012\",\"height\":\"1455\",\"tankVol\":\"75\",\"width\":\"1874\",\"doorNum\":\"4\",\"trunkVol\":\"460\",\"curbWeight\":\"1740\",\"length\":\"5015\",\"groundClearance\":\"132\",\"seatNum\":\"5\",\"rearTrack\":\"1618\",\"frontGauge\":\"1627\"},\"座椅配置\":{\"frontSeatHeat\":\"●\",\"electricSeatRemember\":\"○\",\"rearSeatRemember\":\"-\",\"thirdSats\":\"-\",\"frontSeatElectric\":\"●\",\"rowBackElectric\":\"-\",\"rearCuphold\":\"●\",\"shoulderSupport\":\"-\",\"seatOverall\":\"-\",\"frontArmrest\":\"●\",\"seatVentilated\":\"○\",\"sportSeat\":\"-\",\"seatRatio\":\"○\",\"secondSeatMovement\":\"-\",\"massageSeat\":\"○\",\"rearArmrest\":\"●\",\"lumbarSupport\":\"●\",\"leatherSeats\":\"●\",\"updownSeats\":\"●\",\"rearSeatHeat\":\"●\"},\"高科技配置\":{\"nightView\":\"○\",\"adaptiveCruise\":\"○\",\"splitview\":\"-\",\"activeSteering\":\"-\",\"automaticParking\":\"○\",\"activeSafety\":\"-\",\"auxiliary\":\"○\",\"panoramicCamera\":\"-\",\"measuredOilwear\":\"-\"},\"安全装备\":{\"rearHeadairbag\":\"●\",\"latch\":\"-\",\"driversAirbag\":\"●\",\"kneeAirbag\":\"-\",\"frontSideairbag\":\"●\",\"bcm\":\"●\",\"pressureMonitoring\":\"●\",\"comfortKeyless\":\"●\",\"assistantAirbag\":\"●\",\"lifebeltHint\":\"●\",\"keyless\":\"●\",\"zeroPressure\":\"-\",\"centralLocking\":\"●\",\"rearSideairbag\":\"●\",\"frontHeadairbag\":\"●\",\"isoFix\":\"●\"},\"外部配置\":{\"headlightAdjust\":\"●\",\"electricSuctionDoor\":\"-\",\"powerSunroof\":\"●\",\"electricTrunk\":\"○\",\"sportAppearancePackage\":\"-\",\"aluminiumWheels\":\"●\",\"panoramaSunroof\":\"○\"},\"底盘转向\":{\"structure\":\"承载式\",\"frontSuspensionType\":\"五连杆式独立悬架\",\"driveSystem\":\"8\",\"suspensionType\":\"梯形连杆式独立悬架\",\"drivesystem\":\"前置前驱\",\"powertype\":\"电动助力\"},\"操控配置\":{\"ebaBas\":\"●\",\"abs\":\"●\",\"variableSuspension\":\"○\",\"hdc\":\"-\",\"autoHold\":\"●\",\"ebd\":\"●\",\"variableRatioSteering\":\"-\",\"airSuspension\":\"○\",\"espDsc\":\"●\",\"tcs\":\"●\"},\"玻璃/后视镜\":{\"rearviewHeat\":\"●\",\"rearviewRemember\":\"○\",\"rearviewFold\":\"●\",\"cosmeticMirror\":\"●\",\"abatVent\":\"●\",\"frontPowerWindows\":\"●\",\"rearPowerWindows\":\"●\",\"glarefree\":\"○\",\"antiUv\":\"●\",\"windowAntitrapping\":\"●\",\"rearviewElectric\":\"●\",\"rearWiper\":\"-\",\"rearSunshade\":\"●\",\"wiperSensor\":\"●\"},\"变速箱\":{\"summary\":\"CVT无级变速(模拟8挡)\",\"gearbox\":\"无级变速箱(CVT)\",\"gearNum\":\"8\"}}";
        JSONObject obj = JSON.parseObject(str);
        utils = new FC_ConfigInfoUtil(obj);
        // 判断获取到List
        List compareCarInfoList = utils.getCompareCarInfoList();
        assertNotNull(compareCarInfoList);
    }


}