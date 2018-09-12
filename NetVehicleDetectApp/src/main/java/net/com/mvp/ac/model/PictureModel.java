package net.com.mvp.ac.model;

/**
 * Created by Lukr on 2017/11/1.
 */

public class PictureModel {

    private String PlatformSN, PlateType, PlateNO, VIN, Line, Test_times,   ItemCode ,
    PhotoType,PhotoDate, Detection_ID
           ;

    public String getDetection_ID() {
        return Detection_ID;
    }

    public void setDetection_ID(String detection_ID) {
        Detection_ID = detection_ID;
    }

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

    public String getItemCode() {
        return ItemCode;
    }

    public void setItemCode(String itemCode) {
        ItemCode = itemCode;
    }

    public String getPhotoType() {
        return PhotoType;
    }

    public void setPhotoType(String photoType) {
        PhotoType = photoType;
    }

    public String getPhotoDate() {
        return PhotoDate;
    }

    public void setPhotoDate(String photoDate) {
        PhotoDate = photoDate;
    }

    @Override
    public String toString() {
        return "PictureModel{" +
                "PlatformSN='" + PlatformSN + '\'' +
                ", PlateType='" + PlateType + '\'' +
                ", PlateNO='" + PlateNO + '\'' +
                ", VIN='" + VIN + '\'' +
                ", Line='" + Line + '\'' +
                ", Test_times='" + Test_times + '\'' +
                ", ItemCode='" + ItemCode + '\'' +
                ", PhotoType='" + PhotoType + '\'' +
                ", PhotoDate='" + PhotoDate + '\'' +
                ", Detection_ID='" + Detection_ID + '\'' +
                '}';
    }
}
