package net.com.mvp.ac.model;

import java.util.Observable;
import java.util.Observer;

public class City extends Observable implements Observer {
	private String ID;
	private String Code1;
	private String Name1;
	private String Code2;
	private String Name2;
	private String Code3;
	private String Name3;
	private String name;
	private boolean isChecked;

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

	public String getCode3() {
		return Code3;
	}

	public void setCode3(String code3) {
		Code3 = code3;
	}

	public String getName3() {
		return Name3;
	}

	public void setName3(String name3) {
		Name3 = name3;
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
	public void changeChecked(){
		isChecked = !isChecked;
		setChanged();
		notifyObservers();
	}
	
	@Override
	public void update(Observable observable, Object data) {
		if (data instanceof Boolean) {
			this.isChecked = (Boolean) data;
		}
	}

	@Override
	public String toString() {
		return "City{" +
				"ID='" + ID + '\'' +
				", Code1='" + Code1 + '\'' +
				", Name1='" + Name1 + '\'' +
				", Code2='" + Code2 + '\'' +
				", Name2='" + Name2 + '\'' +
				", Code3='" + Code3 + '\'' +
				", Name3='" + Name3 + '\'' +
				", name='" + name + '\'' +
				", isChecked=" + isChecked +
				'}';
	}
}
