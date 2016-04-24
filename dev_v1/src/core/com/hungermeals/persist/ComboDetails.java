package com.hungermeals.persist;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="comboDetails")
public class ComboDetails {
	private String comboName;
	private int comboId;
	private int cost;
	private PlanSubscription planSubscriptionDetails;

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
	
	
}
