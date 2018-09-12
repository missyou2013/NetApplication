package net.com.mvp.ac.model;

/**
 * Created by Administrator on 2018-05-17.
 */

public class UpLineModel {
    private String Line,Para;

    public String getLine() {
        return Line;
    }

    public void setLine(String line) {
        Line = line;
    }

    public String getPara() {
        return Para;
    }

    public void setPara(String para) {
        Para = para;
    }

    @Override
    public String toString() {
        return "UpLineModel{" +
                "Line='" + Line + '\'' +
                ", Para='" + Para + '\'' +
                '}';
    }
}
