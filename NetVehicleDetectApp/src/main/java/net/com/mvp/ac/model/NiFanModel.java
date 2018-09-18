package net.com.mvp.ac.model;

/**
 * Created by Administrator on 2018-09-18.
 */

public class NiFanModel {
    /**
     * Detection_ID : 123
     * GUID : CF75FD52-7E58-4EF1-A029-AEF1892E4D71
     * TestValue : 12
     * Standard :
     * Verdict :
     * Times : 1
     * TestName :
     * ReflectiveColor : 黄色
     */

    private int Detection_ID;
    private String GUID;
    private String TestValue;
    private String Standard;
    private String Verdict;
    private int Times;
    private String TestName;
    private String ReflectiveColor;

    public int getDetection_ID() {
        return Detection_ID;
    }

    public void setDetection_ID(int Detection_ID) {
        this.Detection_ID = Detection_ID;
    }

    public String getGUID() {
        return GUID;
    }

    public void setGUID(String GUID) {
        this.GUID = GUID;
    }

    public String getTestValue() {
        return TestValue;
    }

    public void setTestValue(String TestValue) {
        this.TestValue = TestValue;
    }

    public String getStandard() {
        return Standard;
    }

    public void setStandard(String Standard) {
        this.Standard = Standard;
    }

    public String getVerdict() {
        return Verdict;
    }

    public void setVerdict(String Verdict) {
        this.Verdict = Verdict;
    }

    public int getTimes() {
        return Times;
    }

    public void setTimes(int Times) {
        this.Times = Times;
    }

    public String getTestName() {
        return TestName;
    }

    public void setTestName(String TestName) {
        this.TestName = TestName;
    }

    public String getReflectiveColor() {
        return ReflectiveColor;
    }

    public void setReflectiveColor(String ReflectiveColor) {
        this.ReflectiveColor = ReflectiveColor;
    }
}
