package net.com.mvp.ac.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/7/12.
 */

public class BuHeGeModel implements Serializable{

    String Name1,Name2,Name3,code1,code2,code3,code_123;

    public String getName1() {
        return Name1;
    }

    public void setName1(String name1) {
        Name1 = name1;
    }

    public String getName2() {
        return Name2;
    }

    public void setName2(String name2) {
        Name2 = name2;
    }

    public String getName3() {
        return Name3;
    }

    public void setName3(String name3) {
        Name3 = name3;
    }

    public String getCode1() {
        return code1;
    }

    public void setCode1(String code1) {
        this.code1 = code1;
    }

    public String getCode2() {
        return code2;
    }

    public void setCode2(String code2) {
        this.code2 = code2;
    }

    public String getCode3() {
        return code3;
    }

    public void setCode3(String code3) {
        this.code3 = code3;
    }

    public String getCode_123() {
        return code_123;
    }

    public void setCode_123(String code_123) {
        this.code_123 = code_123;
    }
}
