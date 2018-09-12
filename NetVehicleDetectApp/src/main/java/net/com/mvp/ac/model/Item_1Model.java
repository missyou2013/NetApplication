package net.com.mvp.ac.model;

/**
 * Created by Administrator on 2017/9/27.
 */

public class Item_1Model {

    private String ID;
    private String Code1;
    private String Name1;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getCode1() {
        return Code1;
    }

    public void setCode1(String code1) {
        Code1 = code1;
    }

    public String getName1() {
        return Name1;
    }

    public void setName1(String name1) {
        Name1 = name1;
    }

    @Override
    public String toString() {
        return "Item_1Model{" +
                "ID='" + ID + '\'' +
                ", Code1='" + Code1 + '\'' +
                ", Name1='" + Name1 + '\'' +
                '}';
    }
}
