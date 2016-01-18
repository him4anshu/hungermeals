package com.hungermeals.common.exception;

public class AuthenticationErrorCode extends GPNErrorCode {

	protected AuthenticationErrorCode(String errorCode) {
		super(errorCode);
	}
	
	public static final GPNErrorCode AUTHENTICATION_FAILED = new AuthenticationErrorCode("GPN30001000");
	
	public static final GPNErrorCode ENCRYPTION_EXCEPTION = new AuthenticationErrorCode("GPN30002000");

}

