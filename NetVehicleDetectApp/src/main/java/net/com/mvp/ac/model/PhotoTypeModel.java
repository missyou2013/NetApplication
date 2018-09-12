package net.com.mvp.ac.model;

/**
 * Created by Administrator on 2017/7/26.
 */

public class PhotoTypeModel {

    /**
     * ID : 59
     * Code : 59
     * Name : 二轴加载制动工位照片
     * PlatformCode : 0357
     * Type : 0
     */

    private String ID;
    private String Code;
    private String Name;
    private String PlatformCode;
    private String Type;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String Code) {
        this.Code = Code;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getPlatformCode() {
        return PlatformCode;
    }

    public void setPlatformCode(String PlatformCode) {
        this.PlatformCode = PlatformCode;
    }

    public String getType() {
        return Type;
    }

    public void setType(String Type) {
        this.Type = Type;
    }

    @Override
    public String toString() {
        return "PhotoTypeModel{" +
                "ID='" + ID + '\'' +
                ", Code='" + Code + '\'' +
                ", Name='" + Name + '\'' +
                ", PlatformCode='" + PlatformCode + '\'' +
                ", Type='" + Type + '\'' +
                '}';
    }
}
