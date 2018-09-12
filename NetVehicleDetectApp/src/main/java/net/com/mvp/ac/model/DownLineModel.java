package net.com.mvp.ac.model;

/**
 * Created by Administrator on 2018/1/12.
 */

public class DownLineModel {
    private String Status,Type,PlateNO;

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getPlateNO() {
        return PlateNO;
    }

    public void setPlateNO(String plateNO) {
        PlateNO = plateNO;
    }

    @Override
    public String toString() {
        return "DownLineModel{" +
                "Status='" + Status + '\'' +
                ", Type='" + Type + '\'' +
                ", PlateNO='" + PlateNO + '\'' +
                '}';
    }
}
