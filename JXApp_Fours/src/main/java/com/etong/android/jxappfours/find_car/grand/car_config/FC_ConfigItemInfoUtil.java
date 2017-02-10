package com.etong.android.jxappfours.find_car.grand.car_config;

/**
 * @author : by sunyao
 * @ClassName : FC_ConfigItemInfoUtil
 * @Description : (这里用一句话描述这个类的作用)
 * @date : 2016/10/10 - 14:25
 */

public class FC_ConfigItemInfoUtil {

    static String[] strTitle = new String[]{
            "基本参数", "车身", "发动机", "空调/冰箱", "内部配置", "安全装备", "变速箱",
            "底盘转向", "车轮制动",
            "操控配置", "多媒体配置",
            "灯光配置", "外部配置", "玻璃/后视镜", "高科技配置", "座椅配置"

    };

    static String[][] strItems = new String[][]{
            // 基本参数 15
            {"厂商指导价(万元)", "车身结构类型", "官方0-100加速(s)", "实测0-100加速(s)", "实测100-0制动(m)", "最高车速(Km/h)", "工信部测定油耗",
                    "整车质保", "实测油耗", "上市时间", "平均油耗", "费用人数", "平均养车费用", "描述", "车款全名"},

//            "车身结构类型",
            // 车身  13
            {"长", "宽", "高", "轴距(mm)", "前轮距(mm)", "后轮距(mm)", "最小离地间隙(mm)", "整备质量(Kg)",  "车门数",
                    "座位数(个)", "油箱容积(L)", "行李厢容积(L)"},

            // 发动机  18
            {"发动机型号", "每缸气门数(个)", "压缩比", "缸体材料", "环保标准", "缸径(mm)", "冲程(mm)", "最大马力(Ps)", "供油方式", "缸盖材料", "燃料类型",
                    "燃油标号", "最大功率(kW)", "最大功率转速(rpm)", "最大扭矩(N·m)", "最大扭矩转速(rpm)", "发动机排列", "配气机构"},

            // 空调/冰箱  7
            {"手动空调", "自动空调", "后排独立空调", "后排出风口", "温度分区控制", "空气调节/花粉过滤", "车载冰箱"},

            // 内部配置  11
            {"真皮方向盘", "方向盘上下调节", "方向盘前后调节", "方向盘电动调节", "多功能方向盘", "方向盘换档", "定速巡航系统", "倒车视频影像",
                    "HUD抬头数字显示", "行车电脑", "泊车辅助"},

            // 安全装备  16
            {"驾驶座安全气囊", "副驾驶位安全气囊", "前排侧安全气囊", "后排侧安全气囊", "前排头部气囊(气帘)", "后排头部气囊(气帘)", "膝部气囊",
                    "胎压监测装置", "零胎压继续行驶",
                    "安全带未系提示", "ISO FIX儿童座椅接口", "LATCH儿童座椅接口", "发动机电子防盗", "车内中控锁", "遥控钥匙", "无钥匙启动系统"},

            // 变速箱  2
            {"简称", "挡位个数"},

            // 底盘转向   5
            {"驱动方式", "前悬挂类型", "后悬挂类型", "助力类型", "车体结构"},

            // 车轮制动  6
            {"前制动类型", "后制动类型", "驻车制动器", "前轮胎规格", "后轮胎规格", "备胎规格"},

            // 操控配置  10
            {"ABS防抱死", "制动力分配(EBD/CBC等)", "刹车辅助(EBA/BAS/BA等)", "牵引力控制(ASR/TCS/TRC等)", "车身稳定控制(ESP/DSC/VSC等)", "自动驻车/上坡辅助", "陡坡缓降", "可变悬挂", "空气悬挂", "可变转向比"},

            // 多媒体配置  19
            {"GPS导航系统", "蓝牙/车载电话", "车载电视", "中控台彩色大屏", "后排液晶显示器", "定位互动服务", "内置硬盘",
                    "外接音源接口(AUX/USB/iPod等)",
                    "CD", "单碟CD", "虚拟多碟CD", "多碟CD系统", "多碟DVD系统", "DVD", "2-3喇叭扬声器系统", "4-5喇叭扬声器系统",
                    "6-7喇叭扬声器系统", "≥8喇叭扬声器系统", "人机交互系统"},

            // 灯光配置  7
            {"前雾灯", "大灯清洗装置", "氙气大灯", "日间行车灯", "自动头灯", "转向头灯转向头灯(辅助灯)", "车内氛围灯"},

            // 外部配置  7
            {"电动天窗", "全景天窗", "运动外观套件", "铝合金轮毂", "电动吸合门", "电动后备厢", "大灯高度可调",},

            // 玻璃/后视镜  14
            {"雨刷传感器", "后视镜电动调节", "后视镜电动折叠", "后视镜加热", "后视镜记忆", "后风挡遮阳帘", "后雨刷器", "前电动车窗", "后电动车窗", "后视镜自动防眩目",
                    "后排侧遮阳帘", "遮阳板化妆镜", "车窗防夹手功能", "防紫外线/隔热玻璃"},

            // 高科技配置  6
            {"主动安全系统", "主动转向系统", "夜视系统", "中控液晶屏分屏显示", "自适应巡航", "全景摄像头"},

            // 座椅配置   20
            {"真皮/仿皮座椅", "座椅高低调节", "肩部支撑调节", "前排座椅电动调节", "第二排靠背角度调节", "第二排座椅移动",
                    "后排座椅电动调节", "电动座椅记忆",
                    "前排座椅加热", "后排座椅加热", "座椅通风", "后排座椅整体放倒", "后排座椅比例放倒", "第三排座椅",
                    "前座中央扶手", "后座中央扶手", "运动座椅",
                    "座椅按摩功能", "驾驶座腰部支撑调节", "后排杯架"}
    };
    static String[][] strLetterItem = new String[][]{
            {// 基本参数 15
                    "prices",
                    "category",
                    "officialSpeedup",
                    "measuredSpeedup",
                    "measuredRetardation",
                    "maxSpeed",
                    "miitOilwear",
                    "warranty",
                    "measuredOilwear",
                    "launchTime",
                    "avgOilwear",
                    "chargeNum",
                    "avgCharge",
                    "desc",
                    "fullname",
            },
            {// 车身  13
                    "length",
                    "width",
                    "height",
                    "wheelbase",
                    "frontGauge",
                    "rearTrack",
                    "groundClearance",
                    "curbWeight",
//                    "category",
                    "doorNum",
                    "seatNum",
                    "tankVol",
                    "trunkVol",
            },
            {// 发动机  18
                    "engine",
                    "valveNum",
                    "ratio",
                    "cylinderMaterial",
                    "envStandards",
                    "bore",
                    "stroke",
                    "maxHorsepower",
                    "oilDrive",
                    "headMaterial",
                    "fuelType ",
                    "fuelGrade : ",
                    "maxPower",
                    "maxPowerRev",
                    "maxTorque",
                    "maxTorqueRev",
                    "zylinderreihe",
                    "admissionGear",
            },
            {// 空调/冰箱  7
                    "manualAcc",
                    "automaticAcc",
                    "rearAcc",
                    "rearOutlet",
                    "zoneControl",
                    "pollenFilter",
                    "cooler",
            },
            {// 内部配置  11
                    "leatherSteering",
                    "downAdjustment",
                    "afterAdjustment",
                    "electricAdjustment",
                    "mfl",
                    "steeringShift",
                    "gra",
                    "rvc",
                    "hud",
                    "ecu",
                    "parkingAssist",
            },
            {// 安全装备  16
                    "driversAirbag",
                    "assistantAirbag",
                    "frontSideairbag",
                    "rearSideairbag",
                    "frontHeadairbag",
                    "rearHeadairbag",
                    "kneeAirbag",
                    "pressureMonitoring",
                    "zeroPressure",
                    "lifebeltHint",
                    "isoFix",
                    "latch",
                    "bcm",
                    "centralLocking",
                    "keyless",
                    "comfortKeyless",
            },
            {// 变速箱  2
                    "summary",
                    "gearNum",
            },
            {// 底盘转向   5
                    "drivesystem",
                    "frontSuspensionType",
                    "suspensionType",
                    "powertype",
                    "structure",
            },
            {// 车轮制动  6
                    "frontBrakeType",
                    "rearBrakeType",
                    "parkingBrake",
                    "frontTire",
                    "rearTire",
                    "spareTire",
            },
            {// 操控配置  10
                    "abs",
                    "ebd",
                    "ebaBas",
                    "tcs",
                    "espDsc",
                    "autoHold",
                    "hdc",
                    "variableSuspension",
                    "airSuspension",
                    "variableRatioSteering",
            },
            {// 多媒体配置  19
                    "gps",
                    "bluetooth",
                    "cartv",
                    "centerScreen",
                    "rearScreen",
                    "positioningInteractive",
                    "internalharddisk",
                    "aux",
                    "cd",
                    "singleDisc",
                    "virtualDisc",
                    "multiDisc",
                    "multiDiscDvd",
                    "dvd",
                    "horn23",
                    "horn45",
                    "horn67",
                    "horn8",
                    "interactiveServices",
            },
            {// 灯光配置  7
                    "frontFoglight",
                    "headlightClean",
                    "xenonHeadlight",
                    "ledDrl",
                    "autoLight",
                    "afs",
                    "atmosphereLight",
            },
            {// 外部配置  7
                    "powerSunroof",
                    "panoramaSunroof",
                    "sportAppearancePackage",
                    "aluminiumWheels",
                    "electricSuctionDoor",
                    "electricTrunk",
                    "headlightAdjust",
            },
            {// 玻璃/后视镜  14
                    "wiperSensor",
                    "rearviewElectric",
                    "rearviewFold",
                    "rearviewHeat",
                    "rearviewRemember",
                    "abatVent",
                    "rearWipe",
                    "frontPowerWindows",
                    "rearPowerWindows",
                    "glarefree",
                    "rearSunshade",
                    "cosmeticMirror",
                    "windowAntitrapping",
                    "antiUv",
            },
            {// 高科技配置  6
                    "activeSafety",
                    "activeSteering",
                    "nightView",
                    "splitview",
                    "adaptiveCruise",
                    "panoramicCamera",
//                        "auxiliary",
//                        "automaticParking",
//                        "measuredOilwear",
            },
            {// 座椅配置   20
                    "leatherSeats",
                    "updownSeats",
                    "shoulderSupport",
                    "frontSeatElectric",
                    "rowBackElectric",
                    "secondSeatMovement",
                    "rearSeatRemember",
                    "electricSeatRemember",
                    "frontSeatHeat",
                    "rearSeatHeat",
                    "seatVentilated",
                    "seatOverall",
                    "seatRatio",
                    "thirdSats",
                    "frontArmrest",
                    "rearArmrest",
                    "sportSeat",
                    "massageSeat",
                    "lumbarSupport",
                    "rearCuphold",
            },
    };

    // 获取到标题
    public static String[] getStrTitle() {
        return strTitle;
    }

    // 获取到英文的item
    public static String[][] getStrLetterItem() {
        return strLetterItem;
    }

    // 获取到中文的item
    public static String[][] getStrItems() {
        return strItems;
    }
}
