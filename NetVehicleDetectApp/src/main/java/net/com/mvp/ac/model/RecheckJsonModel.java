package net.com.mvp.ac.model;

/**
 * Created by Administrator on 2017/12/22.
 */

public class RecheckJsonModel {
    private String PlateNO,StartDate,EndDate,Type;

    public String getPlateNO() {
        return PlateNO;
    }

    public void setPlateNO(String plateNO) {
        PlateNO = plateNO;
    }

    public String getStartDate() {
        return StartDate;
    }

    public void setStartDate(String startDate) {
        StartDate = startDate;
    }

    public String getEndDate() {
        return EndDate;
    }

    public void setEndDate(String endDate) {
        EndDate = endDate;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    @Override
    public String toString() {
        return "RecheckJsonModel{" +
                "PlateNO='" + PlateNO + '\'' +
                ", StartDate='" + StartDate + '\'' +
                ", EndDate='" + EndDate + '\'' +
                ", Type='" + Type + '\'' +
                '}';
    }
}
