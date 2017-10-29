package com.util.pojo;

import java.util.Date;

public class PanDetails {

	private String PanCardNumber;
	private String Name;
	private String FatherName;
	private String Dob;
	
	public String getPanCardNumber() {
		return PanCardNumber;
	}
	public void setPanCardNumber(String panCardNumber) {
		PanCardNumber = panCardNumber;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getFatherName() {
		return FatherName;
	}
	public void setFatherName(String fatherName) {
		FatherName = fatherName;
	}
	public String getDob() {
		return Dob;
	}
	public void setDob(String dob) {
		Dob = dob;
	}
}
