package com.hungermeals.persist;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="address")
public class Address {
	private int addressId;
	private String line1BuildingNo;
	private String line2StreetNo;
	private String state;
	private String city;
	private String pCode;
	private String name;
	private String phone;
	public String getLine1BuildingNo() {
		return line1BuildingNo;
	}
	public void setLine1BuildingNo(String line1BuildingNo) {
		this.line1BuildingNo = line1BuildingNo;
	}
	public String getLine2StreetNo() {
		return line2StreetNo;
	}
	public void setLine2StreetNo(String line2StreetNo) {
		this.line2StreetNo = line2StreetNo;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getpCode() {
		return pCode;
	}
	public void setpCode(String pCode) {
		this.pCode = pCode;
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
	public int getAddressId() {
		return addressId;
	}
	public void setAddressId(int addressId) {
		this.addressId = addressId;
	}
	
	
	
}
