package net.com.mvp.ac.model;

/**
 * Created by Administrator on 2017/9/26.
 */

public class CarItemStartModel {
//       .params("PlatformSN",carsInforModel.getPlatformSN())
//            .params("PlateType",carsInforModel.getPlateType())
//            .params("PlateNO",carsInforModel.getPlateNO())
//            .params("VIN",carsInforModel.getVIN())
//            .params("Line",Line)
//                .params("Test_times",carsInforModel.getTimes())
//            .params("DetectionDevID","1")
//                .params("ItemCode",Item_Code)
//                .params("DetectionItemStartDate", DateUtil.currentTime2())

    private String PlatformSN, PlateType, PlateNO, VIN, Line, Test_times, DetectionDevID, ItemCode,
            DetectionItemStartDate, DetectionID;


    public String getPlatformSN() {
        return PlatformSN;
    }

    public void setPlatformSN(String platformSN) {
        PlatformSN = platformSN;
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

    public String getDetectionDevID() {
        return DetectionDevID;
    }

    public void setDetectionDevID(String detectionDevID) {
        DetectionDevID = detectionDevID;
    }

    public String getItemCode() {
        return ItemCode;
    }

    public void setItemCode(String itemCode) {
        ItemCode = itemCode;
    }

    public String getDetectionItemStartDate() {
        return DetectionItemStartDate;
    }

    public void setDetectionItemStartDate(String detectionItemStartDate) {
        DetectionItemStartDate = detectionItemStartDate;
    }

    public String getDetectionID() {
        return DetectionID;
    }

    public void setDetectionID(String detectionID) {
        DetectionID = detectionID;
    }
}
