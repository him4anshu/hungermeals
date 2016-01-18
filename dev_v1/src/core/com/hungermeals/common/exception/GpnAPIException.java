package com.hungermeals.common.exception;

import com.hungermeals.common.exception.GPNBaseException;
import com.hungermeals.common.exception.GPNErrorCode;

public class GpnAPIException extends GPNBaseException {

	public GpnAPIException(GPNErrorCode errorCode, Throwable t) {
		super(errorCode, t);
	}

	public GpnAPIException(GPNErrorCode errorCode) {
		super(errorCode);
	}

	public GpnAPIException(Throwable r) {
		super(r);
	}

}
