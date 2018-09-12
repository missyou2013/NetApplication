package net.com.mvp.ac.model;

/**
 * 不合格项目上报的model
 */

public class ReportedModel {
    private String DataType,Detection_ID,GUID,
            Test_times,
            Unqualified_Code1,Unqualified_Code2,Unqualified_Code3;

    public String getDataType() {
        return DataType;
    }

    public void setDataType(String dataType) {
        DataType = dataType;
    }

    public String getDetection_ID() {
        return Detection_ID;
    }

    public void setDetection_ID(String detection_ID) {
        Detection_ID = detection_ID;
    }

    public String getGUID() {
        return GUID;
    }

    public void setGUID(String GUID) {
        this.GUID = GUID;
    }

    public String getTest_times() {
        return Test_times;
    }

    public void setTest_times(String test_times) {
        Test_times = test_times;
    }

    public String getUnqualified_Code1() {
        return Unqualified_Code1;
    }

    public void setUnqualified_Code1(String unqualified_Code1) {
        Unqualified_Code1 = unqualified_Code1;
    }

    public String getUnqualified_Code2() {
        return Unqualified_Code2;
    }

    public void setUnqualified_Code2(String unqualified_Code2) {
        Unqualified_Code2 = unqualified_Code2;
    }

    public String getUnqualified_Code3() {
        return Unqualified_Code3;
    }

    public void setUnqualified_Code3(String unqualified_Code3) {
        Unqualified_Code3 = unqualified_Code3;
    }
}
