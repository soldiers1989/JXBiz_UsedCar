package com.etong.android.jxappfind.javabean;


import java.io.Serializable;

/**
 * @desc (这里用一句话描述这个类的作用)
 * @createtime 2016/9/22 0022--8:57
 * @Created by wukefan.
 */
public class Find_WeatherDailyJavaBean implements Serializable {


    /**
     * brief : 不宜
     * details : 不宜洗车，未来24小时内有雨，如果在此期间洗车，雨水和路上的泥水可能会再次弄脏您的爱车。
     */

    private CarWashingBean car_washing;//洗车
    /**
     * brief : 舒适
     * details : 建议着长袖T恤、衬衫加单裤等服装。年老体弱者宜着针织长袖衬衫、马甲和长裤。
     */

    private DressingBean dressing;//穿衣
    /**
     * brief : 少发
     * details : 各项气象条件适宜，无明显降温过程，发生感冒机率较低。
     */

    private FluBean flu;//感冒
    /**
     * brief : 较不宜
     * details : 有降水，推荐您在室内进行健身休闲运动；若坚持户外运动，须注意保暖并携带雨具。
     */

    private SportBean sport;//运动
    /**
     * brief : 适宜
     * details : 有降水，温度适宜，在细雨中游玩别有一番情调，可不要错过机会呦！但记得出门要携带雨具。
     */

    private TravelBean travel;//旅游
    /**
     * brief : 弱
     * details : 紫外线强度较弱，建议出门前涂擦SPF在12-15之间、PA+的防晒护肤品。
     */

    private UvBean uv;//紫外线

    public CarWashingBean getCar_washing() {
        return car_washing;
    }

    public void setCar_washing(CarWashingBean car_washing) {
        this.car_washing = car_washing;
    }

    public DressingBean getDressing() {
        return dressing;
    }

    public void setDressing(DressingBean dressing) {
        this.dressing = dressing;
    }

    public FluBean getFlu() {
        return flu;
    }

    public void setFlu(FluBean flu) {
        this.flu = flu;
    }

    public SportBean getSport() {
        return sport;
    }

    public void setSport(SportBean sport) {
        this.sport = sport;
    }

    public TravelBean getTravel() {
        return travel;
    }

    public void setTravel(TravelBean travel) {
        this.travel = travel;
    }

    public UvBean getUv() {
        return uv;
    }

    public void setUv(UvBean uv) {
        this.uv = uv;
    }

    public static class CarWashingBean implements Serializable {
        public String brief;
        public String details;

        public String getBrief() {
            return brief;
        }

        public void setBrief(String brief) {
            this.brief = brief;
        }

        public String getDetails() {
            return details;
        }

        public void setDetails(String details) {
            this.details = details;
        }
    }

    public static class DressingBean implements Serializable{
        public String brief;
        public String details;

        public String getBrief() {
            return brief;
        }

        public void setBrief(String brief) {
            this.brief = brief;
        }

        public String getDetails() {
            return details;
        }

        public void setDetails(String details) {
            this.details = details;
        }
    }

    public static class FluBean implements Serializable{
        public String brief;
        public String details;

        public String getBrief() {
            return brief;
        }

        public void setBrief(String brief) {
            this.brief = brief;
        }

        public String getDetails() {
            return details;
        }

        public void setDetails(String details) {
            this.details = details;
        }
    }

    public static class SportBean implements Serializable {
        public String brief;
        public String details;

        public String getBrief() {
            return brief;
        }

        public void setBrief(String brief) {
            this.brief = brief;
        }

        public String getDetails() {
            return details;
        }

        public void setDetails(String details) {
            this.details = details;
        }
    }

    public static class TravelBean implements Serializable{
        public String brief;
        public String details;

        public String getBrief() {
            return brief;
        }

        public void setBrief(String brief) {
            this.brief = brief;
        }

        public String getDetails() {
            return details;
        }

        public void setDetails(String details) {
            this.details = details;
        }
    }

    public static class UvBean implements  Serializable{
        public String brief;
        public String details;

        public String getBrief() {
            return brief;
        }

        public void setBrief(String brief) {
            this.brief = brief;
        }

        public String getDetails() {
            return details;
        }

        public void setDetails(String details) {
            this.details = details;
        }
    }
}
