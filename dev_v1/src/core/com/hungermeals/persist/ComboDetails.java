package com.hungermeals.persist;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="comboDetails")
public class ComboDetails {
	private String comboName;
	private int comboId;
	private int cost;
	private PlanSubscription planSubscriptionDetails;
	private String operationType;
	private ResponseStatus responseStatus;
	private String status;
	private List<ComboDetails> comboList;
	public String getComboName() {
		return comboName;
	}

	public void setComboName(String comboName) {
		this.comboName = comboName;
	}

	public int getComboId() {
		return comboId;
	}

	public void setComboId(int comboId) {
		this.comboId = comboId;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public PlanSubscription getPlanSubscriptionDetails() {
		return planSubscriptionDetails;
	}

	public void setPlanSubscriptionDetails(PlanSubscription planSubscriptionDetails) {
		this.planSubscriptionDetails = planSubscriptionDetails;
	}

	public String getOperationType() {
		return operationType;
	}

	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}

	public ResponseStatus getResponseStatus() {
		return responseStatus;
	}

	public void setResponseStatus(ResponseStatus responseStatus) {
		this.responseStatus = responseStatus;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<ComboDetails> getComboList() {
		return comboList;
	}

	public void setComboList(List<ComboDetails> comboList) {
		this.comboList = comboList;
	}
	
	
}
