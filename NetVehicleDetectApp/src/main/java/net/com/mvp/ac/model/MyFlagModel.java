package net.com.mvp.ac.model;

/**
 * Created by Administrator on 2017/10/30.
 */

public class MyFlagModel {
    int position,flag;
    String str;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    public MyFlagModel(int position, int flag) {
        this.position = position;
        this.flag = flag;
    }

    public MyFlagModel(int position, int flag, String str) {
        this.position = position;
        this.flag = flag;
        this.str = str;
    }
}
