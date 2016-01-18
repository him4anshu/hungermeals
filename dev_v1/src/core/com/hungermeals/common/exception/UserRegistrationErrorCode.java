package com.hungermeals.common.exception;

public class UserRegistrationErrorCode extends GPNErrorCode {

	protected UserRegistrationErrorCode(String errorCode) {
		super(errorCode);
	}
	
	public static final GPNErrorCode USER_NAME_ALREADY_EXISTS = new UserRegistrationErrorCode("GPN20001000");
	
	public static final GPNErrorCode ORGANIZATION_NAME_ALREADY_EXISTS = new UserRegistrationErrorCode("GPN20002000");
	
	public static final GPNErrorCode ORGANIZATION_DOES_NOT_EXISTS = new UserRegistrationErrorCode("GPN20003000");
	
	public static final GPNErrorCode INVALID_DOMAIN_NAME = new UserRegistrationErrorCode("GPN20004000");
	
	public static final GPNErrorCode INVALID_HQ_PHONE = new UserRegistrationErrorCode("GPN20005000");

}
