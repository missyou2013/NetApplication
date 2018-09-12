package net.com.mvp.ac.model;

/**
 * Created by Administrator on 2017/9/28.
 */

public class MyChoiceModel {
    private String Code2,code_key,code_values;
    String Name2;
    private boolean isChecked;

    public String getName2() {
        return Name2;
    }

    public void setName2(String name2) {
        Name2 = name2;
    }

    public String getCode_values() {
        return code_values;
    }

    public void setCode_values(String code_values) {
        this.code_values = code_values;
    }

    public String getCode2() {
        return Code2;
    }

    public void setCode2(String code2) {
        Code2 = code2;
    }

    public String getCode_key() {
        return code_key;
    }

    public void setCode_key(String code_key) {
        this.code_key = code_key;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    @Override
    public String toString() {
        return "MyChoiceModel{" +
                "Code2='" + Code2 + '\'' +
                ", code_key='" + code_key + '\'' +
                ", code_values='" + code_values + '\'' +
                ", Name2='" + Name2 + '\'' +
                ", isChecked=" + isChecked +
                '}';
    }
}
