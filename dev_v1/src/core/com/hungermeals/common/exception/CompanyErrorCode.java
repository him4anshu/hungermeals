package com.hungermeals.common.exception;

public class CompanyErrorCode extends GPNErrorCode {
	protected CompanyErrorCode(String errorCode) {
		super(errorCode);
	}
	
	public static final GPNErrorCode INVALID_COMPANY = new AuthenticationErrorCode("40001000");
	
	}
