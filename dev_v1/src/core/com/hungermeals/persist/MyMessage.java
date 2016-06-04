package com.hungermeals.persist;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="message")
public class MyMessage {
	private String title;
	private String message;
	private String sentStatus;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getSentStatus() {
		return sentStatus;
	}
	public void setSentStatus(String sentStatus) {
		this.sentStatus = sentStatus;
	}
	
}
