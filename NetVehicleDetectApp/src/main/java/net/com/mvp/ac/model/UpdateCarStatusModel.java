package net.com.mvp.ac.model;

/**
 * Created by Administrator on 2017/10/25.
 */

public class UpdateCarStatusModel {
//    参数：Type 0
//    QueueID队列id
//    //三个参数传一个对应的  0：待检测 1：上线了 2： 合格 3 不合格
//    DynamicStatus //底盘动态
//    AppearanceStatus//外检
//    RoadStatus//路试

    private int Type, DynamicStatus, AppearanceStatus, RoadStatus;
    private String QueueID;

    public int getType() {
        return Type;
    }

    public void setType(int type) {
        Type = type;
    }

    public int getDynamicStatus() {
        return DynamicStatus;
    }

    public void setDynamicStatus(int dynamicStatus) {
        DynamicStatus = dynamicStatus;
    }

    public int getAppearanceStatus() {
        return AppearanceStatus;
    }

    public void setAppearanceStatus(int appearanceStatus) {
        AppearanceStatus = appearanceStatus;
    }

    public int getRoadStatus() {
        return RoadStatus;
    }

    public void setRoadStatus(int roadStatus) {
        RoadStatus = roadStatus;
    }

    public String getQueueID() {
        return QueueID;
    }

    public void setQueueID(String queueID) {
        QueueID = queueID;
    }

    @Override
    public String toString() {
        return "UpdateCarStatusModel{" +
                "Type=" + Type +
                ", DynamicStatus=" + DynamicStatus +
                ", AppearanceStatus=" + AppearanceStatus +
                ", RoadStatus=" + RoadStatus +
                ", QueueID='" + QueueID + '\'' +
                '}';
    }
}
