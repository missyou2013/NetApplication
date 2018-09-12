package net.com.mvp.ac.model;

import java.io.Serializable;

/**
 * @Title: UserModel.java
 * @Package com.herry.shequ.model
 * @Description: 用户实体类
 * @author lxj
 * @date 2016-1-14 下午4:17:36
 * @version V1.0
 */
public class UserModel implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	// {
	// "result": "true",
	// "resident": {
	// "communityId": 1,
	// "status": 0,
	// "id": 3,
	// "passportid": 21,
	// "pid": 0,
	// "truename": "李四",
	// "address": "翠湖新苑-三号楼一单元101室",
	// "linktel": "15092264855",
	// "idtype": "1",
	// "idno": "372928199105305678",
	// "birthday": "1990-05-30",
	// "photo":
	// "http://222.173.68.166:8888/SmartCommunity/upload/images/20160114/1452752197000.jpg",
	// "type": "1",
	// "workstatus": "1",
	// "sex": "1",
	// "remark": " ",
	// "grade": "1"
	// }
	// }
	private String communityId;
	private String status;
	private String id;
	private String passportid;
	private String pid;
	private String truename;
	private String address;
	private String linktel;
	private String idtype;
	private String idno;
	private String birthday;
	private String photo;
	private String type;
	private String workstatus;
	private String sex;
	private String remark;
	private String grade;
	//

	private String fromdate;
	private String content;
	private String result;
	//
	private String title;
	private String dates;
	private String money;
	//
	private String phone;
	//
	private String name;
	//
	private String date;

	//
	private String buyNum;

	public String getBuyNum() {
		return buyNum;
	}

	public void setBuyNum(String buyNum) {
		this.buyNum = buyNum;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDates() {
		return dates;
	}

	public void setDates(String dates) {
		this.dates = dates;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getFromdate() {
		return fromdate;
	}

	public void setFromdate(String fromdate) {
		this.fromdate = fromdate;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCommunityId() {
		return communityId;
	}

	public void setCommunityId(String communityId) {
		this.communityId = communityId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPassportid() {
		return passportid;
	}

	public void setPassportid(String passportid) {
		this.passportid = passportid;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getTruename() {
		return truename;
	}

	public void setTruename(String truename) {
		this.truename = truename;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getLinktel() {
		return linktel;
	}

	public void setLinktel(String linktel) {
		this.linktel = linktel;
	}

	public String getIdtype() {
		return idtype;
	}

	public void setIdtype(String idtype) {
		this.idtype = idtype;
	}

	public String getIdno() {
		return idno;
	}

	public void setIdno(String idno) {
		this.idno = idno;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getWorkstatus() {
		return workstatus;
	}

	public void setWorkstatus(String workstatus) {
		this.workstatus = workstatus;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	@Override
	public String toString() {
		return "UserModel{" +
				"communityId='" + communityId + '\'' +
				", status='" + status + '\'' +
				", id='" + id + '\'' +
				", passportid='" + passportid + '\'' +
				", pid='" + pid + '\'' +
				", truename='" + truename + '\'' +
				", address='" + address + '\'' +
				", linktel='" + linktel + '\'' +
				", idtype='" + idtype + '\'' +
				", idno='" + idno + '\'' +
				", birthday='" + birthday + '\'' +
				", photo='" + photo + '\'' +
				", type='" + type + '\'' +
				", workstatus='" + workstatus + '\'' +
				", sex='" + sex + '\'' +
				", remark='" + remark + '\'' +
				", grade='" + grade + '\'' +
				", fromdate='" + fromdate + '\'' +
				", content='" + content + '\'' +
				", result='" + result + '\'' +
				", title='" + title + '\'' +
				", dates='" + dates + '\'' +
				", money='" + money + '\'' +
				", phone='" + phone + '\'' +
				", name='" + name + '\'' +
				", date='" + date + '\'' +
				", buyNum='" + buyNum + '\'' +
				'}';
	}
}
