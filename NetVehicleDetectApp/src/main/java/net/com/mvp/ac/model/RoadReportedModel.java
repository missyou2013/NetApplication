package net.com.mvp.ac.model;

/**
 * 路试不合格项目上报的model
 */

public class RoadReportedModel {
    private String DataType,Detection_ID,GUID,
            Test_times,
            TotalMass,Approvedload,BrakeForce,
    Type,Road_Name,Initial_Velocity,Road_LaneWidth
            ,MFDD,Stability,Braking_Distance,
    Coordination_Time,Parking_Gradient,Parking_Gradient_Verdict,
    Speed,Speed_Verdict,Itemcode,PlateNO;

    public String getPlateNO() {
        return PlateNO;
    }

    public void setPlateNO(String plateNO) {
        PlateNO = plateNO;
    }

    public String getDataType() {
        return DataType;
    }

    public void setDataType(String dataType) {
        DataType = dataType;
    }

    public String getDetection_ID() {
        return Detection_ID;
    }

    public void setDetection_ID(String detection_ID) {
        Detection_ID = detection_ID;
    }

    public String getGUID() {
        return GUID;
    }

    public void setGUID(String GUID) {
        this.GUID = GUID;
    }

    public String getTest_times() {
        return Test_times;
    }

    public void setTest_times(String test_times) {
        Test_times = test_times;
    }

    public String getTotalMass() {
        return TotalMass;
    }

    public void setTotalMass(String totalMass) {
        TotalMass = totalMass;
    }

    public String getApprovedload() {
        return Approvedload;
    }

    public void setApprovedload(String approvedload) {
        Approvedload = approvedload;
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

    public String getRoad_LaneWidth() {
        return Road_LaneWidth;
    }

    public void setRoad_LaneWidth(String road_LaneWidth) {
        Road_LaneWidth = road_LaneWidth;
    }

    public String getMFDD() {
        return MFDD;
    }

    public void setMFDD(String MFDD) {
        this.MFDD = MFDD;
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

    public String getCoordination_Time() {
        return Coordination_Time;
    }

    public void setCoordination_Time(String coordination_Time) {
        Coordination_Time = coordination_Time;
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

    public String getItemcode() {
        return Itemcode;
    }

    public void setItemcode(String itemcode) {
        Itemcode = itemcode;
    }
}
