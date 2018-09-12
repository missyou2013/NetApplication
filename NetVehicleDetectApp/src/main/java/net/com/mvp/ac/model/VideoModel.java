package net.com.mvp.ac.model;

/**
 * C视频接口model
 */

public class VideoModel {
    //    10:02:29
//    张栋 2017/10/18 10:02:29
//<jylsh>#PlatformSN#<lsh>
//    <jyjgbh>#SecurityNO#<jgbh>
//    <jcxdh>#Line#</jcxdh>
//    <jyxm>#Itemcode#<xm>
//    <jycs>#Test_times#<cs>
//    <hpzl>#PlateType#<zl>
//    <hphm>#PlateNO#<hm>
//    开始时间DetectionItemStartDate，结束时间DetectionItemEndDate（Video）
    private String PlatformSN, Line, Itemcode, Test_times, PlateType, PlateNO, DetectionItemStartDate,
            DetectionItemEndDate, DetectionID;

    public String getPlatformSN() {
        return PlatformSN;
    }

    public void setPlatformSN(String platformSN) {
        PlatformSN = platformSN;
    }

    public String getDetectionID() {
        return DetectionID;
    }

    public void setDetectionID(String detectionID) {
        DetectionID = detectionID;
    }

    public String getLine() {
        return Line;
    }

    public void setLine(String line) {
        Line = line;
    }

    public String getItemcode() {
        return Itemcode;
    }

    public void setItemcode(String itemcode) {
        Itemcode = itemcode;
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

    public String getDetectionItemStartDate() {
        return DetectionItemStartDate;
    }

    public void setDetectionItemStartDate(String detectionItemStartDate) {
        DetectionItemStartDate = detectionItemStartDate;
    }

    public String getDetectionItemEndDate() {
        return DetectionItemEndDate;
    }

    public void setDetectionItemEndDate(String detectionItemEndDate) {
        DetectionItemEndDate = detectionItemEndDate;
    }

    @Override
    public String toString() {
        return "VideoModel{" +
                "PlatformSN='" + PlatformSN + '\'' +
                ", Line='" + Line + '\'' +
                ", Itemcode='" + Itemcode + '\'' +
                ", Test_times='" + Test_times + '\'' +
                ", PlateType='" + PlateType + '\'' +
                ", PlateNO='" + PlateNO + '\'' +
                ", DetectionItemStartDate='" + DetectionItemStartDate + '\'' +
                ", DetectionItemEndDate='" + DetectionItemEndDate + '\'' +
                '}';
    }
}
