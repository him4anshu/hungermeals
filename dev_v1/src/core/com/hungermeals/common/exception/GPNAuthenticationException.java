package com.hungermeals.common.exception;

public class GPNAuthenticationException extends GPNBaseException {

	public GPNAuthenticationException(GPNErrorCode errorCode, Throwable t) {
		super(errorCode, t);
	}

	public GPNAuthenticationException(GPNErrorCode errorCode) {
		super(errorCode);
	}
	
}
