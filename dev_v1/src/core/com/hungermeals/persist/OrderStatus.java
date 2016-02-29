package com.hungermeals.persist;

import java.util.TreeMap;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.databind.ObjectMapper;

@XmlRootElement(name="orderStatus")
public class OrderStatus {
	private long orderId;
	private String executiveName;
	private String executivePhone;
	private String orderStatusDesc;
	private int orderStatusCode;
	private String totalAmount;
	private String userPhone;
	private ResponseStatus responseStatus;
	private String walletResponse;
	private TreeMap<String,String> walletRequest;
	private String walletRequestParameter;
	
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
	public ResponseStatus getResponseStatus() {
		return responseStatus;
	}
	public void setResponseStatus(ResponseStatus responseStatus) {
		this.responseStatus = responseStatus;
	}
	public String getWalletResponse() {
		return walletResponse;
	}
	public void setWalletResponse(String walletResponse) {
		this.walletResponse = walletResponse;
	}
	public TreeMap<String, String> getWalletRequest() {
		return walletRequest;
	}
	public void setWalletRequest(TreeMap<String, String> walletRequest) {
		this.walletRequest = walletRequest;
	}
	public String getWalletRequestParameter() {
		return walletRequestParameter;
	}
	public void setWalletRequestParameter(String walletRequestParameter) {
		this.walletRequestParameter = walletRequestParameter;
	}
		
}
