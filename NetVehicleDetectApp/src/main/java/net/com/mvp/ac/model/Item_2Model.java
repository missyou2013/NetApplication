package net.com.mvp.ac.model;

/**
 * Created by Administrator on 2017/9/27.
 */

public class Item_2Model {
    private String ID;
    private String Code1;
    private String Code2;
    private String Name2;

    public String getCode1() {
        return Code1;
    }

    public void setCode1(String code1) {
        Code1 = code1;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getCode2() {
        return Code2;
    }

    public void setCode2(String code2) {
        Code2 = code2;
    }

    public String getName2() {
        return Name2;
    }

    public void setName2(String name2) {
        Name2 = name2;
    }

    @Override
    public String toString() {
        return "Item_2Model{" +
                "ID='" + ID + '\'' +
                ", Code1='" + Code1 + '\'' +
                ", Code2='" + Code2 + '\'' +
                ", Name2='" + Name2 + '\'' +
                '}';
    }
}
