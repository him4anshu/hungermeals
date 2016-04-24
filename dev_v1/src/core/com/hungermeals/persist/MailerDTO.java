package com.hungermeals.persist;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.hungermeals.common.ConfigReader;

public class MailerDTO {

	ConfigReader configReader=new ConfigReader();
	private String[] to;
	private String subject;
	private Map<String, Object> dynamicData;
	private String fileName;
	private String[] cc=configReader.getValue("mail.ccEmail").split(",");
	private String[] bcc=configReader.getValue("mail.bccEmail").split(",");
	private String senderName=configReader.getValue("mail.senderName");
	private String senderEmail=configReader.getValue("mail.senderEmail");
	private String replyToName=configReader.getValue("mail.replyToName");
	private String replyToEmail=configReader.getValue("mail.replyToEmail");
	private String fileLocation=configReader.getValue("mail.tempateLocation");
	public String[] getTo() {
		return to;
	}
	public void setTo(String[] to) {
		this.to = to;
	}
	public String[] getCc() {
		return cc;
	}
	public void setCc(String[] cc) {
		this.cc = cc;
	}
	public String[] getBcc() {
		return bcc;
	}
	public void setBcc(String[] bcc) {
		this.bcc = bcc;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getSenderName() {
		return senderName;
	}
	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}
	public String getSenderEmail() {
		return senderEmail;
	}
	public void setSenderEmail(String senderEmail) {
		this.senderEmail = senderEmail;
	}
	public String getReplyToName() {
		return replyToName;
	}
	public void setReplyToName(String replyToName) {
		this.replyToName = replyToName;
	}
	public String getReplyToEmail() {
		return replyToEmail;
	}
	public void setReplyToEmail(String replyToEmail) {
		this.replyToEmail = replyToEmail;
	}
	public Map<String, Object> getDynamicData() {
		return dynamicData;
	}
	public void setDynamicData(Map<String, Object> dynamicData) {
		this.dynamicData = dynamicData;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileLocation() {
		return fileLocation;
	}
	public void setFileLocation(String fileLocation) {
		this.fileLocation = fileLocation;
	}
	
	
	
	

}
