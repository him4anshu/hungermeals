package com.hungermeals.persist;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="orderStatus")
public class OrderStatus {
	private long orderId;
	private String executiveName;
	private String executivePhone;
	private String orderStatus;
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
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	
}
