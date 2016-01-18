package com.hungermeals.persist;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="user1")
public class User1 {
	/*For user details*/
	private int userId;
	private String userTitle;
	private String userFirstName;
	private String userLastName;
	private String userEmail;
	private String userEEmail;
	private String userPassword;
	private String userType;//Indvidual OR Institution
	private String userSubType;//Individual[Student,Parents,Teachers,Other] OR Institution[Kindergarten,Scholl,College,Other]
	private String registrationStatus;
	private boolean alreadyRegistered;
	private Address userAddress;
	private String verifiedStatus;
	private String loginStatus;

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
	public String getUserEEmail() {
		return userEEmail;
	}
	public void setUserEEmail(String userEEmail) {
		this.userEEmail = userEEmail;
	}
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public String getUserSubType() {
		return userSubType;
	}
	public void setUserSubType(String userSubType) {
		this.userSubType = userSubType;
	}
	public boolean isAlreadyRegistered() {
		return alreadyRegistered;
	}
	public void setAlreadyRegistered(boolean alreadyRegistered) {
		this.alreadyRegistered = alreadyRegistered;
	}
	public Address getUserAddress() {
		return userAddress;
	}
	public void setUserAddress(Address userAddress) {
		this.userAddress = userAddress;
	}
	public String getRegistrationStatus() {
		return registrationStatus;
	}
	public void setRegistrationStatus(String registrationStatus) {
		this.registrationStatus = registrationStatus;
	}
	public String getVerifiedStatus() {
		return verifiedStatus;
	}
	public void setVerifiedStatus(String verifiedStatus) {
		this.verifiedStatus = verifiedStatus;
	}
	public String getLoginStatus() {
		return loginStatus;
	}
	public void setLoginStatus(String loginStatus) {
		this.loginStatus = loginStatus;
	}
	
}
