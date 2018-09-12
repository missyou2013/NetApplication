package net.com.mvp.ac.model;

/**
 * Created by Administrator on 2017/9/26.
 */

public class RoadTestModel {
    //     <jylsh>#PlatformSN#</jylsh>检验流水号
//    <jcxdh>#Line#</jcxdh>检测线代号
//    <jyxm>#Item_Code#</jyxm>检验项目
//    <jycs>#Test_times#</jycs>检验次数
//    <hpzl>#PlateType#</hpzl>号牌种类
//    <hphm>#PlateNO#</hphm>号牌号码
//    <clsbdh>#VIN#</clsbdh>车辆识别代号
//    <lsy>#Road_Name#</lsy>路试员姓名
//    <zdcsd>#Initial_Velocity#</zdcsd>行车制动初速度
//    <zdxtsj>#Coordination_Time#</zdxtsj>行车制动协调时间
//    <zdwdx>#Stability#</zdwdx>行车制动稳定性
//    <xckzzdjl>#Braking_Distance#</xckzzdjl>行车空载制动距离
//    <xckzmfdd>#MFDD#</xckzmfdd>行车空载MFDD
//    <lszdpd>#Brake_Verdict#</lszdpd>行车路试制动判定
//    <zcpd>#Parking_Gradient#</zcpd>驻车坡度
//    <lszczdpd>#Parking_Gradient_Verdict#</lszczdpd>路试驻车制动判定
//    <csdscz>#Speed#</csdscz>车速表实测值
//    <csbpd>#Speed_Verdict#</csbpd>车速表判定
//    <lsjg>#Road_Verdict#</lsjg>路试结果
//    Road_laneWidth 车道宽度
    private String PlatformSN, Line, Item_Code, Test_times, PlateType, PlateNO, VIN, Road_Name,
            Initial_Velocity,
            Coordination_Time, Stability, Braking_Distance, MFDD, Brake_Verdict, Parking_Gradient,
            Parking_Gradient_Verdict, Speed, Speed_Verdict, Road_Verdict, Road_LaneWidth, Detection_ID,
            TotalMass, ApprovedLoad, BrakeForce, Type,GUID,DataType;

    public String getDataType() {
        return DataType;
    }

    public void setDataType(String dataType) {
        DataType = dataType;
    }

    public String getTotalMass() {
        return TotalMass;
    }

    public void setTotalMass(String totalMass) {
        TotalMass = totalMass;
    }

    public String getApprovedLoad() {
        return ApprovedLoad;
    }

    public void setApprovedLoad(String approvedLoad) {
        ApprovedLoad = approvedLoad;
    }

    public String getBrakeForce() {
        return BrakeForce;
    }

    public void setBrakeForce(String brakeForce) {
        BrakeForce = brakeForce;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getGUID() {
        return GUID;
    }

    public void setGUID(String GUID) {
        this.GUID = GUID;
    }

    public String getDetection_ID() {
        return Detection_ID;
    }

    public void setDetection_ID(String detection_ID) {
        Detection_ID = detection_ID;
    }

    public String getRoad_LaneWidth() {
        return Road_LaneWidth;
    }

    public void setRoad_LaneWidth(String road_LaneWidth) {
        Road_LaneWidth = road_LaneWidth;
    }

    public String getItem_Code() {
        return Item_Code;
    }

    public void setItem_Code(String item_Code) {
        Item_Code = item_Code;
    }

    public String getPlatformSN() {
        return PlatformSN;
    }

    public void setPlatformSN(String platformSN) {
        PlatformSN = platformSN;
    }

    public String getLine() {
        return Line;
    }

    public void setLine(String line) {
        Line = line;
    }

    public String getTest_times() {
        return Test_times;
    }

    public void setTest_times(String test_times) {
        Test_times = test_times;
    }

    public String getPlateType() {
        return PlateType;
    }

    public void setPlateType(String plateType) {
        PlateType = plateType;
    }

    public String getPlateNO() {
        return PlateNO;
    }

    public void setPlateNO(String plateNO) {
        PlateNO = plateNO;
    }

    public String getVIN() {
        return VIN;
    }

    public void setVIN(String VIN) {
        this.VIN = VIN;
    }

    public String getRoad_Name() {
        return Road_Name;
    }

    public void setRoad_Name(String road_Name) {
        Road_Name = road_Name;
    }

    public String getInitial_Velocity() {
        return Initial_Velocity;
    }

    public void setInitial_Velocity(String initial_Velocity) {
        Initial_Velocity = initial_Velocity;
    }

    public String getCoordination_Time() {
        return Coordination_Time;
    }

    public void setCoordination_Time(String coordination_Time) {
        Coordination_Time = coordination_Time;
    }

    public String getStability() {
        return Stability;
    }

    public void setStability(String stability) {
        Stability = stability;
    }

    public String getBraking_Distance() {
        return Braking_Distance;
    }

    public void setBraking_Distance(String braking_Distance) {
        Braking_Distance = braking_Distance;
    }

    public String getMFDD() {
        return MFDD;
    }

    public void setMFDD(String MFDD) {
        this.MFDD = MFDD;
    }

    public String getBrake_Verdict() {
        return Brake_Verdict;
    }

    public void setBrake_Verdict(String brake_Verdict) {
        Brake_Verdict = brake_Verdict;
    }

    public String getParking_Gradient() {
        return Parking_Gradient;
    }

    public void setParking_Gradient(String parking_Gradient) {
        Parking_Gradient = parking_Gradient;
    }

    public String getParking_Gradient_Verdict() {
        return Parking_Gradient_Verdict;
    }

    public void setParking_Gradient_Verdict(String parking_Gradient_Verdict) {
        Parking_Gradient_Verdict = parking_Gradient_Verdict;
    }

    public String getSpeed() {
        return Speed;
    }

    public void setSpeed(String speed) {
        Speed = speed;
    }

    public String getSpeed_Verdict() {
        return Speed_Verdict;
    }

    public void setSpeed_Verdict(String speed_Verdict) {
        Speed_Verdict = speed_Verdict;
    }

    public String getRoad_Verdict() {
        return Road_Verdict;
    }

    public void setRoad_Verdict(String road_Verdict) {
        Road_Verdict = road_Verdict;
    }
}
