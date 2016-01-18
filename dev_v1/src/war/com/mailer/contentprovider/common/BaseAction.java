package com.mailer.contentprovider.common;

import java.util.Map;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public abstract class BaseAction extends ActionSupport {
	


	/**
	 * 
	 */
	private static final long serialVersionUID = 3176441903038910508L;

	protected String VALIDATION_ERROR = "validationerror";
	private String errorCode;

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	protected Map getSession() {
		return (ActionContext.getContext().getSession());
	}
	
	protected Map getAllRequestParams() {
		return (ActionContext.getContext().getParameters());
	}
	
	

}
