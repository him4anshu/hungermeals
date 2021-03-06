package com.hungermeals.persist;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="couponTxn")
public class CouponTxn {

	private String couponId;
	private String uCode;
	private String couponCode;
	private String couponValue;
	private String couponValueType;
	private String couponType;
	private String couponAppliedStatus; //Verified,Expired,Already Used,Invalid
	private int resue_attempt;
	private int use_attempt;
	private ResponseStatus responseStatus;
	private String operationType;
	private String status;
	private int minimumPurchaseAmount;
	private List<CouponTxn> couponTxnList;
	
	public String getuCode() {
		return uCode;
	}
	public void setuCode(String uCode) {
		this.uCode = uCode;
	}
	public String getCouponCode() {
		return couponCode;
	}
	public void setCouponCode(String couponCode) {
		this.couponCode = couponCode;
	}
	public String getCouponValue() {
		return couponValue;
	}
	public void setCouponValue(String couponValue) {
		this.couponValue = couponValue;
	}
	public String getCouponValueType() {
		return couponValueType;
	}
	public void setCouponValueType(String couponValueType) {
		this.couponValueType = couponValueType;
	}
	public String getCouponType() {
		return couponType;
	}
	public void setCouponType(String couponType) {
		this.couponType = couponType;
	}
	public String getCouponAppliedStatus() {
		return couponAppliedStatus;
	}
	public void setCouponAppliedStatus(String couponAppliedStatus) {
		this.couponAppliedStatus = couponAppliedStatus;
	}
	public String getCouponId() {
		return couponId;
	}
	public void setCouponId(String couponId) {
		this.couponId = couponId;
	}
	public int getResue_attempt() {
		return resue_attempt;
	}
	public void setResue_attempt(int resue_attempt) {
		this.resue_attempt = resue_attempt;
	}
	public int getUse_attempt() {
		return use_attempt;
	}
	public void setUse_attempt(int use_attempt) {
		this.use_attempt = use_attempt;
	}
	public ResponseStatus getResponseStatus() {
		return responseStatus;
	}
	public void setResponseStatus(ResponseStatus responseStatus) {
		this.responseStatus = responseStatus;
	}
	public String getOperationType() {
		return operationType;
	}
	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getMinimumPurchaseAmount() {
		return minimumPurchaseAmount;
	}
	public void setMinimumPurchaseAmount(int minimumPurchaseAmount) {
		this.minimumPurchaseAmount = minimumPurchaseAmount;
	}
	public List<CouponTxn> getCouponTxnList() {
		return couponTxnList;
	}
	public void setCouponTxnList(List<CouponTxn> couponTxnList) {
		this.couponTxnList = couponTxnList;
	}
	
	
}
