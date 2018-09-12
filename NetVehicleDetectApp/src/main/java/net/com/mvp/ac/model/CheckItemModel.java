package net.com.mvp.ac.model;

/**
 * Created by Administrator on 2017/9/27.
 */

public class CheckItemModel {


    /**
     * ID : 1
     * Code1 : 1
     * Name1 : 车辆唯一性检查
     * Code2 : 1
     * Name2 : 号牌号码/车辆类型
     * Code3 : 1
     * Name3 : 号牌号码与行驶证不一致
     */

    private String ID;
    private String Code1;
    private String Name1;
    private String Code2;
    private String Name2;
    private String Code3;
    private String Name3;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getCode1() {
        return Code1;
    }

    public void setCode1(String Code1) {
        this.Code1 = Code1;
    }

    public String getName1() {
        return Name1;
    }

    public void setName1(String Name1) {
        this.Name1 = Name1;
    }

    public String getCode2() {
        return Code2;
    }

    public void setCode2(String Code2) {
        this.Code2 = Code2;
    }

    public String getName2() {
        return Name2;
    }

    public void setName2(String Name2) {
        this.Name2 = Name2;
    }

    public String getCode3() {
        return Code3;
    }

    public void setCode3(String Code3) {
        this.Code3 = Code3;
    }

    public String getName3() {
        return Name3;
    }

    public void setName3(String Name3) {
        this.Name3 = Name3;
    }

    @Override
    public String toString() {
        return "CheckItemModel{" +
                "ID='" + ID + '\'' +
                ", Code1='" + Code1 + '\'' +
                ", Name1='" + Name1 + '\'' +
                ", Code2='" + Code2 + '\'' +
                ", Name2='" + Name2 + '\'' +
                ", Code3='" + Code3 + '\'' +
                ", Name3='" + Name3 + '\'' +
                '}';
    }
}
