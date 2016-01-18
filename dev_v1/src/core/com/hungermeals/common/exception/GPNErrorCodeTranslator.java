package com.hungermeals.common.exception;

import java.util.ResourceBundle;

public class GPNErrorCodeTranslator {
	
	private ResourceBundle errorBundle;

	public GPNErrorCodeTranslator() {
		errorBundle = ResourceBundle.getBundle("com.infiniti.gpn.errorcode.ErrorCode");
	}
	
	public String getValue(String key) {
		return (errorBundle.getString(key));
	}

}
