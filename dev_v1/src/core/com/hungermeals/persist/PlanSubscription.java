package com.hungermeals.persist;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="planSubscription")
public class PlanSubscription {
	private int planSubscribeId;
	private String planType;
	private String uCode;
	private String selectedDate;
	private String planStatus;
	private String startDate;
	private String endDate;
	private int planCost;
	private int addressId;
	private String timeSlot;
	private String comboId;
	private String paymentMode;
	private String mobile;
	private ResponseStatus responseStatus;
	private boolean updatedStatus;
	private Address address;
	public String getPlanType() {
		return planType;
	}
	public void setPlanType(String planType) {
		this.planType = planType;
	}
	public String getuCode() {
		return uCode;
	}
	public void setuCode(String uCode) {
		this.uCode = uCode;
	}
	public String getSelectedDate() {
		return selectedDate;
	}
	public void setSelectedDate(String selectedDate) {
		this.selectedDate = selectedDate;
	}
	public String getPlanStatus() {
		return planStatus;
	}
	public void setPlanStatus(String planStatus) {
		this.planStatus = planStatus;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public int getPlanSubscribeId() {
		return planSubscribeId;
	}
	public void setPlanSubscribeId(int planSubscribeId) {
		this.planSubscribeId = planSubscribeId;
	}
	public int getPlanCost() {
		return planCost;
	}
	public void setPlanCost(int planCost) {
		this.planCost = planCost;
	}
	public int getAddressId() {
		return addressId;
	}
	public void setAddressId(int addressId) {
		this.addressId = addressId;
	}
	public String getTimeSlot() {
		return timeSlot;
	}
	public void setTimeSlot(String timeSlot) {
		this.timeSlot = timeSlot;
	}
	public String getComboId() {
		return comboId;
	}
	public void setComboId(String comboId) {
		this.comboId = comboId;
	}
	public String getPaymentMode() {
		return paymentMode;
	}
	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public ResponseStatus getResponseStatus() {
		return responseStatus;
	}
	public void setResponseStatus(ResponseStatus responseStatus) {
		this.responseStatus = responseStatus;
	}
	public boolean isUpdatedStatus() {
		return updatedStatus;
	}
	public void setUpdatedStatus(boolean updatedStatus) {
		this.updatedStatus = updatedStatus;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	
	
}
