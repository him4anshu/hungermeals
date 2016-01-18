package com.hungermeals.common.exception;

public class GPNSystemException extends GPNBaseException {

	public GPNSystemException(GPNErrorCode errorCode, Throwable t) {
		super(errorCode, t);
	}

	public GPNSystemException(GPNErrorCode errorCode) {
		super(errorCode);
	}

}
