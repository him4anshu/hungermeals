
package com.hungermeals.persist;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="WUserBean")
public class UserBean {
	
	
	/*For user details*/
	private int userId;
	private String userTitle;
	private String userFirstName;
	private String userLastName;
	private String userEmail;
	private String userStatus;
	private String userpassword;
	private String password;
	private String userEEmail;
	private String userNewEmail;
	private String userOldEmail;
	private String grId;
	
	
	/*For organization details*/
	private String organizationName;
	private String organizationUrl;
	private String organizationCountry;
	private String organizationId;
	private String organizationIndustry;
	private String organizationDescription;
 
	/*For event details*/
	private String eventName;
	private String eventUrl;
	private String eventCountry;
	private String eventId;
	private String eventIndustry;
	private String eventVenue;
	private String eventStartDate;
	private String eventEndDate;
	private String eventDescription;
	
	
	/*For forum details*/
	private String forumQuestion;
	private String forumTopicName;
	private String forumCreatedBy;
	private String forumcommentedUserName;
	private String answerId;

	/*For adds on websites*/
	private String adTypeName;
	private String adPixel;
	private String adCreationDate;
	private String adValidityDate;
	private String profilePercentage;
	
	/*For dynamic subject*/
	private String dynamicSubjectV1;
	private String dynamicSubjectV2;
	private String dynamicSubjectV3;
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	public String getUserTitle() {
		return userTitle;
	}
	public void setUserTitle(String userTitle) {
		this.userTitle = userTitle;
	}
	public String getUserpassword() {
		return userpassword;
	}
	public void setUserpassword(String userpassword) {
		this.userpassword = userpassword;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUserFirstName() {
		return userFirstName;
	}
	public void setUserFirstName(String userFirstName) {
		this.userFirstName = userFirstName;
	}
	public String getUserLastName() {
		return userLastName;
	}
	public void setUserLastName(String userLastName) {
		this.userLastName = userLastName;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getUserStatus() {
		return userStatus;
	}
	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}
	public String getUserEEmail() {
		return userEEmail;
	}
	public void setUserEEmail(String userEEmail) {
		this.userEEmail = userEEmail;
	}
	public String getOrganizationName() {
		return organizationName;
	}
	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}
	public String getOrganizationUrl() {
		return organizationUrl;
	}
	public void setOrganizationUrl(String organizationUrl) {
		this.organizationUrl = organizationUrl;
	}
	public String getOrganizationCountry() {
		return organizationCountry;
	}
	public void setOrganizationCountry(String organizationCountry) {
		this.organizationCountry = organizationCountry;
	}
	public String getOrganizationId() {
		return organizationId;
	}
	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}
	public String getOrganizationIndustry() {
		return organizationIndustry;
	}
	public void setOrganizationIndustry(String organizationIndustry) {
		this.organizationIndustry = organizationIndustry;
	}
	public String getEventName() {
		return eventName;
	}
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
	public String getEventUrl() {
		return eventUrl;
	}
	public void setEventUrl(String eventUrl) {
		this.eventUrl = eventUrl;
	}
	public String getEventCountry() {
		return eventCountry;
	}
	public void setEventCountry(String eventCountry) {
		this.eventCountry = eventCountry;
	}
	public String getEventId() {
		return eventId;
	}
	public void setEventId(String eventId) {
		this.eventId = eventId;
	}
	public String getEventIndustry() {
		return eventIndustry;
	}
	public void setEventIndustry(String eventIndustry) {
		this.eventIndustry = eventIndustry;
	}
	public String getEventVenue() {
		return eventVenue;
	}
	public void setEventVenue(String eventVenue) {
		this.eventVenue = eventVenue;
	}
	public String getForumQuestion() {
		return forumQuestion;
	}
	public void setForumQuestion(String forumQuestion) {
		this.forumQuestion = forumQuestion;
	}
	public String getForumTopicName() {
		return forumTopicName;
	}
	public void setForumTopicName(String forumTopicName) {
		this.forumTopicName = forumTopicName;
	}
	public String getForumCreatedBy() {
		return forumCreatedBy;
	}
	public void setForumCreatedBy(String forumCreatedBy) {
		this.forumCreatedBy = forumCreatedBy;

	}
	public String getUserNewEmail() {
		return userNewEmail;
	}
	public void setUserNewEmail(String userNewEmail) {
		this.userNewEmail = userNewEmail;
	}
	public String getUserOldEmail() {
		return userOldEmail;
	}
	public void setUserOldEmail(String userOldEmail) {
		this.userOldEmail = userOldEmail;
	}
	public String getAdTypeName() {
		return adTypeName;
	}
	public void setAdTypeName(String adTypeName) {
		this.adTypeName = adTypeName;
	}
	public String getAdPixel() {
		return adPixel;
	}
	public void setAdPixel(String adPixel) {
		this.adPixel = adPixel;
	}
	public String getAdCreationDate() {
		return adCreationDate;
	}
	public void setAdCreationDate(String adCreationDate) {
		this.adCreationDate = adCreationDate;
	}
	public String getAdValidityDate() {
		return adValidityDate;
	}
	public void setAdValidityDate(String adValidityDate) {
		this.adValidityDate = adValidityDate;
	}
	public String getProfilePercentage() {
		return profilePercentage;
	}
	public void setProfilePercentage(String profilePercentage) {
		this.profilePercentage = profilePercentage;
	}
	public String getForumcommentedUserName() {
		return forumcommentedUserName;
	}
	public void setForumcommentedUserName(String forumcommentedUserName) {
		this.forumcommentedUserName = forumcommentedUserName;
	}
	public String getAnswerId() {
		return answerId;
	}
	public void setAnswerId(String answerId) {
		this.answerId = answerId;
	}

	private String s1;
	private String s2;
	private String s3;
	private String s4;
	private String s5;
	private String s6;
	private String s7;
	private String s8;
	private String s9;
	private String s10;
	private String s11;
	private String s12;
	private String s13;
	private String s14;
	private String s15;
	private double d1;
	private double d2;
	private double d3;
	private int i1,i2,i3;
	private String dynamicSenderV1;
	private String dynamicSenderV2;
	private String dynamicSenderV3;
	
	

	public String getS1() {
		return s1;
	}
	public void setS1(String s1) {
		this.s1 = s1;
	}
	public String getS2() {
		return s2;
	}
	public void setS2(String s2) {
		this.s2 = s2;
	}
	public String getS3() {
		return s3;
	}
	public void setS3(String s3) {
		this.s3 = s3;
	}
	public String getS4() {
		return s4;
	}
	public void setS4(String s4) {
		this.s4 = s4;
	}
	public String getS5() {
		return s5;
	}
	public void setS5(String s5) {
		this.s5 = s5;
	}
	public String getS6() {
		return s6;
	}
	public void setS6(String s6) {
		this.s6 = s6;
	}
	public String getS7() {
		return s7;
	}
	public void setS7(String s7) {
		this.s7 = s7;
	}
	public String getS8() {
		return s8;
	}
	public void setS8(String s8) {
		this.s8 = s8;
	}
	public String getS9() {
		return s9;
	}
	public void setS9(String s9) {
		this.s9 = s9;
	}
	public String getS10() {
		return s10;
	}
	public void setS10(String s10) {
		this.s10 = s10;
	}
	public String getS11() {
		return s11;
	}
	public void setS11(String s11) {
		this.s11 = s11;
	}
	public String getS12() {
		return s12;
	}
	public void setS12(String s12) {
		this.s12 = s12;
	}
	public String getS13() {
		return s13;
	}
	public void setS13(String s13) {
		this.s13 = s13;
	}
	public String getS14() {
		return s14;
	}
	public void setS14(String s14) {
		this.s14 = s14;
	}
	public String getS15() {
		return s15;
	}
	public void setS15(String s15) {
		this.s15 = s15;
	}
	public String getDynamicSubjectV1() {
		return dynamicSubjectV1;
	}
	public void setDynamicSubjectV1(String dynamicSubjectV1) {
		this.dynamicSubjectV1 = dynamicSubjectV1;
	}
	public String getDynamicSubjectV2() {
		return dynamicSubjectV2;
	}
	public void setDynamicSubjectV2(String dynamicSubjectV2) {
		this.dynamicSubjectV2 = dynamicSubjectV2;
	}
	public String getDynamicSubjectV3() {
		return dynamicSubjectV3;
	}
	public void setDynamicSubjectV3(String dynamicSubjectV3) {
		this.dynamicSubjectV3 = dynamicSubjectV3;
	}
	public String getEventStartDate() {
		return eventStartDate;
	}
	public void setEventStartDate(String eventStartDate) {
		this.eventStartDate = eventStartDate;
	}
	public String getEventEndDate() {
		return eventEndDate;
	}
	public void setEventEndDate(String eventEndDate) {
		this.eventEndDate = eventEndDate;
	}
	public String getOrganizationDescription() {
		return organizationDescription;
	}
	public void setOrganizationDescription(String organizationDescription) {
		this.organizationDescription = organizationDescription;
	}
	public String getEventDescription() {
		return eventDescription;
	}
	public void setEventDescription(String eventDescription) {
		this.eventDescription = eventDescription;
	}
	public double getD1() {
		return d1;
	}
	public void setD1(double d1) {
		this.d1 = d1;
	}
	public double getD2() {
		return d2;
	}
	public void setD2(double d2) {
		this.d2 = d2;
	}
	public double getD3() {
		return d3;
	}
	public void setD3(double d3) {
		this.d3 = d3;
	}
	public int getI1() {
		return i1;
	}
	public void setI1(int i1) {
		this.i1 = i1;
	}
	public int getI2() {
		return i2;
	}
	public void setI2(int i2) {
		this.i2 = i2;
	}
	public int getI3() {
		return i3;
	}
	public void setI3(int i3) {
		this.i3 = i3;
	}
	public String getGrId() {
		return grId;
	}
	public void setGrId(String grId) {
		this.grId = grId;
	}
	public String getDynamicSenderV1() {
		return dynamicSenderV1;
	}
	public void setDynamicSenderV1(String dynamicSenderV1) {
		this.dynamicSenderV1 = dynamicSenderV1;
	}
	public String getDynamicSenderV2() {
		return dynamicSenderV2;
	}
	public void setDynamicSenderV2(String dynamicSenderV2) {
		this.dynamicSenderV2 = dynamicSenderV2;
	}
	public String getDynamicSenderV3() {
		return dynamicSenderV3;
	}
	public void setDynamicSenderV3(String dynamicSenderV3) {
		this.dynamicSenderV3 = dynamicSenderV3;
	}
	
	
	
	
}