package com.hungermeals.common.exception;

public class GPNErrorCode {
	
	private String errorCode;
	
	protected GPNErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public static final GPNErrorCode GPN_SYSTEM_ERROR = new GPNErrorCode("GPN100010000");
	
	public String toString() {
		return errorCode;
	}
}
