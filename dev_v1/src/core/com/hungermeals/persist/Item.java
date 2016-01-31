package com.hungermeals.persist;


import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="item")
public class Item/* implements Serializable*/{

	private int itemId;
	private String itemName;
	private int numberOfItems;
	private float perItemCost;
	private String description;

	public int getItemId() {
		return itemId;
	}
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public int getNumberOfItems() {
		return numberOfItems;
	}
	public void setNumberOfItems(int numberOfItems) {
		this.numberOfItems = numberOfItems;
	}
	public float getPerItemCost() {
		return perItemCost;
	}
	public void setPerItemCost(float perItemCost) {
		this.perItemCost = perItemCost;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
