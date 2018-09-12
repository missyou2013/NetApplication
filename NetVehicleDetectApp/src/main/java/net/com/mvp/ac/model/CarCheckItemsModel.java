package net.com.mvp.ac.model;

/**
 * 待检测车辆要检测的项目
 */

public class CarCheckItemsModel {
//[
//    {"ItemCode":"B1","ItemStatus":0,"Name":"一轴制动"},
//    {"ItemCode":"B2","ItemStatus":0,"Name":"二轴制动"},
//    {"ItemCode":"F1","ItemStatus":0,"Name":"外观检验"},
//    {"ItemCode":"H1","ItemStatus":0,"Name":"左外灯或二三轮车左灯"},
//    {"ItemCode":"H4","ItemStatus":0,"Name":"右外灯或二三轮车右灯"}
//]
    private String ItemCode,Name;
    private int ItemStatus;

    public String getItemCode() {
        return ItemCode;
    }

    public void setItemCode(String itemCode) {
        ItemCode = itemCode;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getItemStatus() {
        return ItemStatus;
    }

    public void setItemStatus(int itemStatus) {
        ItemStatus = itemStatus;
    }
}
