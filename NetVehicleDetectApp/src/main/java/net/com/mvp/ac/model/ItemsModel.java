package net.com.mvp.ac.model;

/**
 * Created by Administrator on 2017/9/27.
 */

public class ItemsModel {

//    .params("PlateType", carsInforModel.getPlateType())
//                .params("CarType", carsInforModel.getType())
//                .params("DetectCategroy", carsInforModel.getDetectionCategory())
//                .params("PassengerNb", carsInforModel.getApprovedLoad())
//                .params("ProductionDate", carsInforModel.getProductionDate())

    private String PlateType, CarType, DetectCategroy, PassengerNb, RegisteDate;

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

    public String getDetectCategroy() {
        return DetectCategroy;
    }

    public void setDetectCategroy(String detectCategroy) {
        DetectCategroy = detectCategroy;
    }

    public String getPassengerNb() {
        return PassengerNb;
    }

    public void setPassengerNb(String passengerNb) {
        PassengerNb = passengerNb;
    }

    public String getRegisteDate() {
        return RegisteDate;
    }

    public void setRegisteDate(String registeDate) {
        RegisteDate = registeDate;
    }
}
