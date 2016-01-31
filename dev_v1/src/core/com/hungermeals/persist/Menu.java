package com.hungermeals.persist;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="menu")
public class Menu/* implements Serializable*/{
	private long menuId;
	private String name;
	private String description;
	private List<Item> itemList;
	public long getMenuId() {
		return menuId;
	}
	public void setMenuId(long menuId) {
		this.menuId = menuId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public List<Item> getItemList() {
		return itemList;
	}
	public void setItemList(List<Item> itemList) {
		this.itemList = itemList;
	}
	@Override
	public int hashCode() {
		int hashcode = 0;
        hashcode =Integer.parseInt(menuId+"")*20;
        hashcode += name.hashCode();
        return hashcode;
	}
	
	@Override
	public boolean equals(Object obj) {
		 if (obj instanceof Menu) {
			 Menu menu = (Menu) obj;
	            return (menu.name.equals(this.name) && menu.menuId == this.menuId);
	        } else {
	            return false;
	        }
	}
}
