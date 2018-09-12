package net.com.mvp.ac.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class Group extends Observable implements Observer{
	private String ID;
	private String Code1;
	private String Name1;
	private String name;
	private boolean isChecked;
	private List<City> cityList = new ArrayList<City>();

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

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isChecked() {
		return isChecked;
	}
	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}
	public List<City> getCityList() {
		return cityList;
	}
	public void setCityList(List<City> cityList) {
		this.cityList = cityList;
	}
	public void changeChecked(){
		isChecked = !isChecked;
		setChanged();
		notifyObservers(isChecked);
	}
	
	@Override
	public void update(Observable observable, Object data) {
		boolean flag = true;
		for (City city : cityList) {
			if (city.isChecked() == false) {
				flag = false;
			}
		}
		this.isChecked = flag;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Group group = (Group) o;

		if (isChecked != group.isChecked) return false;
		if (!ID.equals(group.ID)) return false;
		if (!Code1.equals(group.Code1)) return false;
		if (!Name1.equals(group.Name1)) return false;
		if (!name.equals(group.name)) return false;
		return cityList.equals(group.cityList);

	}

	@Override
	public int hashCode() {
		int result = ID.hashCode();
		result = 31 * result + Code1.hashCode();
		result = 31 * result + Name1.hashCode();
		result = 31 * result + name.hashCode();
		result = 31 * result + (isChecked ? 1 : 0);
		result = 31 * result + cityList.hashCode();
		return result;
	}

	@Override
	public String toString() {
		return "Group{" +
				"ID='" + ID + '\'' +
				", Code1='" + Code1 + '\'' +
				", Name1='" + Name1 + '\'' +
				", name='" + name + '\'' +
				", isChecked=" + isChecked +
				", cityList=" + cityList +
				'}';
	}
}
