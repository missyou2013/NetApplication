package net.com.mvp.ac.model;

/**
 * Created by Administrator on 2017/10/30.
 */

public class OutPhotoItem {

    private String  PlateType,CarType,Usage,PlatformSN;

    public String getPlateType() {
        return PlateType;
    }

    public void setPlateType(String plateType) {
        PlateType = plateType;
    }

    public String getCarType() {
        return CarType;
    }

    public void setCarType(String carType) {
        CarType = carType;
    }

    public String getUsage() {
        return Usage;
    }

    public void setUsage(String usage) {
        Usage = usage;
    }

    public String getPlatformSN() {
        return PlatformSN;
    }

    public void setPlatformSN(String platformSN) {
        PlatformSN = platformSN;
    }

    @Override
    public String toString() {
        return "OutPhotoItem{" +
                "PlateType='" + PlateType + '\'' +
                ", CarType='" + CarType + '\'' +
                ", Usage='" + Usage + '\'' +
                ", PlatformSN='" + PlatformSN + '\'' +
                '}';
    }
}
