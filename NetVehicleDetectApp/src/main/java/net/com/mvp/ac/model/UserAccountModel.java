package net.com.mvp.ac.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/6/27.
 */

public class UserAccountModel implements Serializable {

    /**
     * ID : 7
     * UserID : 6
     * UserName : a二
     * LoginPass : 6
     * InsurePass : null
     * Face : null
     * EPRight : 0
     * UserRight : 0,
     * Sex : 1
     * IDNumber :
     * Birthday : /Date(631123200000)/
     * Status : 0
     * EntryDate : /Date(631123200000)/
     * Education :
     * Phone :
     * Driver : 7
     * Engineer : 0
     * Email :
     * Address :
     * ErrorCount : 0
     */
//UserRight:代表用户权限 0:管理员 1操作员 2引车员 3外检员 4底盘动态检测员  5底盘静态检测员 6路试检测员
    private int ID;
    private String UserID;
    private String UserName;
    private String LoginPass;
    private Object InsurePass;
    private Object Face;
    private int EPRight;
    private String UserRight;
    private String Sex;
    private String IDNumber;
    private String Birthday;
    private int Status;
    private String EntryDate;
    private String Education;
    private String Phone;
    private String Driver;
    private String Engineer;
    private String Email;
    private String Address;
    private int ErrorCount;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String UserID) {
        this.UserID = UserID;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String UserName) {
        this.UserName = UserName;
    }

    public String getLoginPass() {
        return LoginPass;
    }

    public void setLoginPass(String LoginPass) {
        this.LoginPass = LoginPass;
    }

    public Object getInsurePass() {
        return InsurePass;
    }

    public void setInsurePass(Object InsurePass) {
        this.InsurePass = InsurePass;
    }

    public Object getFace() {
        return Face;
    }

    public void setFace(Object Face) {
        this.Face = Face;
    }

    public int getEPRight() {
        return EPRight;
    }

    public void setEPRight(int EPRight) {
        this.EPRight = EPRight;
    }

    public String getUserRight() {
        return UserRight;
    }

    public void setUserRight(String UserRight) {
        this.UserRight = UserRight;
    }

    public String getSex() {
        return Sex;
    }

    public void setSex(String Sex) {
        this.Sex = Sex;
    }

    public String getIDNumber() {
        return IDNumber;
    }

    public void setIDNumber(String IDNumber) {
        this.IDNumber = IDNumber;
    }

    public String getBirthday() {
        return Birthday;
    }

    public void setBirthday(String Birthday) {
        this.Birthday = Birthday;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int Status) {
        this.Status = Status;
    }

    public String getEntryDate() {
        return EntryDate;
    }

    public void setEntryDate(String EntryDate) {
        this.EntryDate = EntryDate;
    }

    public String getEducation() {
        return Education;
    }

    public void setEducation(String Education) {
        this.Education = Education;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String Phone) {
        this.Phone = Phone;
    }

    public String getDriver() {
        return Driver;
    }

    public void setDriver(String Driver) {
        this.Driver = Driver;
    }

    public String getEngineer() {
        return Engineer;
    }

    public void setEngineer(String Engineer) {
        this.Engineer = Engineer;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String Address) {
        this.Address = Address;
    }

    public int getErrorCount() {
        return ErrorCount;
    }

    public void setErrorCount(int ErrorCount) {
        this.ErrorCount = ErrorCount;
    }

    @Override
    public String toString() {
        return "UserAccountModel{" +
                "ID=" + ID +
                ", UserID='" + UserID + '\'' +
                ", UserName='" + UserName + '\'' +
                ", LoginPass='" + LoginPass + '\'' +
                ", InsurePass=" + InsurePass +
                ", Face=" + Face +
                ", EPRight=" + EPRight +
                ", UserRight='" + UserRight + '\'' +
                ", Sex='" + Sex + '\'' +
                ", IDNumber='" + IDNumber + '\'' +
                ", Birthday='" + Birthday + '\'' +
                ", Status=" + Status +
                ", EntryDate='" + EntryDate + '\'' +
                ", Education='" + Education + '\'' +
                ", Phone='" + Phone + '\'' +
                ", Driver='" + Driver + '\'' +
                ", Engineer='" + Engineer + '\'' +
                ", Email='" + Email + '\'' +
                ", Address='" + Address + '\'' +
                ", ErrorCount=" + ErrorCount +
                '}';
    }
}
