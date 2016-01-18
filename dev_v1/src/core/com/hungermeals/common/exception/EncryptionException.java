package com.hungermeals.common.exception;

public class EncryptionException extends GPNBaseException {

	public EncryptionException(GPNErrorCode errorCode, Throwable t) {
		super(errorCode, t);		
	}
	
	public EncryptionException(GPNErrorCode errorCode) {
		super(errorCode);
	}

}
