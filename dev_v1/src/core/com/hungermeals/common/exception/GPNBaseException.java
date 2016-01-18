package com.hungermeals.common.exception;

public class GPNBaseException extends RuntimeException {
	
	private GPNErrorCode errorCode;

	public GPNBaseException(String message, Throwable t) {
		super(message, t);
	}

	public GPNBaseException(Throwable r) {
		super(r);
	}
	
	public GPNBaseException(GPNErrorCode errorCode, Throwable t) {
		super(t);
		this.errorCode = errorCode;
	}
	
	public GPNBaseException(GPNErrorCode errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorCode() {
		return (errorCode.toString());
	}
	
	public GPNErrorCode getGPNErrorCode() {
		return errorCode;
	}

}