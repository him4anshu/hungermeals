package com.hungermeals.persist;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="orderDetails")
public class OrderDetails {

	private User user;
	private List<Item> itemList;
	private Order orderInfo;
	private OrderStatus orderStatus;
	private ComboDetails comboDetails;
	
	public ComboDetails getComboDetails() {
		return comboDetails;
	}
	public void setComboDetails(ComboDetails comboDetails) {
		this.comboDetails = comboDetails;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public List<Item> getItemList() {
		return itemList;
	}
	public void setItemList(List<Item> itemList) {
		this.itemList = itemList;
	}
	public Order getOrderInfo() {
		return orderInfo;
	}
	public void setOrderInfo(Order orderInfo) {
		this.orderInfo = orderInfo;
	}
	public OrderStatus getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}
	
}
