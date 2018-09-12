package net.com.mvp.ac.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/12/22.
 */

public class RecheckCarsModel implements Serializable{
    /**
     * QueueID : 189
     * DetectDate : 2017-12-2216: 24: 00
     * GUID : 3564C317-BE38-48B2-B3A5-0B692CE5E231
     * SN : 201712220003
     * PlatformSN : 0000
     * Times : 2
     * DetectionStatus : 0
     * WeightStatus : 9
     * OutLineStatus : 9
     * PrintStatus : 0
     * Flag : null
     * Line : 0
     * CarID : 110
     * PlateRegion : ³
     * PlateNO : A201
     * VIN : 55
     * PlateType : 02
     * Type : K31
     */

    private int QueueID;
    private String DetectDate,Usage;
    private String GUID;
    private String SN;
    private String PlatformSN;
    private int Times;
    private int DetectionStatus;
    private int WeightStatus;
    private int OutLineStatus;
    private int PrintStatus;
    private Object Flag;
    private int Line;
    private int CarID;
    private String PlateRegion;
    private String PlateNO;
    private String VIN;
    private String PlateType;
    private String Type;

    public int getQueueID() {
        return QueueID;
    }

    public void setQueueID(int QueueID) {
        this.QueueID = QueueID;
    }

    public String getDetectDate() {
        return DetectDate;
    }

    public void setDetectDate(String DetectDate) {
        this.DetectDate = DetectDate;
    }

    public String getGUID() {
        return GUID;
    }

    public void setGUID(String GUID) {
        this.GUID = GUID;
    }

    public String getSN() {
        return SN;
    }

    public void setSN(String SN) {
        this.SN = SN;
    }

    public String getPlatformSN() {
        return PlatformSN;
    }

    public void setPlatformSN(String PlatformSN) {
        this.PlatformSN = PlatformSN;
    }

    public int getTimes() {
        return Times;
    }

    public void setTimes(int Times) {
        this.Times = Times;
    }

    public int getDetectionStatus() {
        return DetectionStatus;
    }

    public void setDetectionStatus(int DetectionStatus) {
        this.DetectionStatus = DetectionStatus;
    }

    public int getWeightStatus() {
        return WeightStatus;
    }

    public void setWeightStatus(int WeightStatus) {
        this.WeightStatus = WeightStatus;
    }

    public int getOutLineStatus() {
        return OutLineStatus;
    }

    public void setOutLineStatus(int OutLineStatus) {
        this.OutLineStatus = OutLineStatus;
    }

    public int getPrintStatus() {
        return PrintStatus;
    }

    public void setPrintStatus(int PrintStatus) {
        this.PrintStatus = PrintStatus;
    }

    public Object getFlag() {
        return Flag;
    }

    public void setFlag(Object Flag) {
        this.Flag = Flag;
    }

    public int getLine() {
        return Line;
    }

    public void setLine(int Line) {
        this.Line = Line;
    }

    public int getCarID() {
        return CarID;
    }

    public void setCarID(int CarID) {
        this.CarID = CarID;
    }

    public String getPlateRegion() {
        return PlateRegion;
    }

    public void setPlateRegion(String PlateRegion) {
        this.PlateRegion = PlateRegion;
    }

    public String getPlateNO() {
        return PlateNO;
    }

    public void setPlateNO(String PlateNO) {
        this.PlateNO = PlateNO;
    }

    public String getVIN() {
        return VIN;
    }

    public void setVIN(String VIN) {
        this.VIN = VIN;
    }

    public String getPlateType() {
        return PlateType;
    }

    public void setPlateType(String PlateType) {
        this.PlateType = PlateType;
    }

    public String getType() {
        return Type;
    }

    public void setType(String Type) {
        this.Type = Type;
    }

    public String getUsage() {
        return Usage;
    }

    public void setUsage(String usage) {
        Usage = usage;
    }
}
