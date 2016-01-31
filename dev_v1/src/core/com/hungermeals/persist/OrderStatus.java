package com.hungermeals.persist;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="orderStatus")
public class OrderStatus {
	private long orderId;
	private String executiveName;
	private String executivePhone;
	private String orderStatusDesc;
	private int orderStatusCode;
	private String totalAmount;
	private String userPhone;
	
	public long getOrderId() {
		return orderId;
	}
	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}
	public String getExecutiveName() {
		return executiveName;
	}
	public void setExecutiveName(String executiveName) {
		this.executiveName = executiveName;
	}
	public String getExecutivePhone() {
		return executivePhone;
	}
	public void setExecutivePhone(String executivePhone) {
		this.executivePhone = executivePhone;
	}
	
	public String getOrderStatusDesc() {
		return orderStatusDesc;
	}
	public void setOrderStatusDesc(String orderStatusDesc) {
		this.orderStatusDesc = orderStatusDesc;
	}
	public String getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}
	public int getOrderStatusCode() {
		return orderStatusCode;
	}
	public void setOrderStatusCode(int orderStatusCode) {
		this.orderStatusCode = orderStatusCode;
	}
	public String getUserPhone() {
		return userPhone;
	}
	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}
	
	
}
