package com.hungermeals.common.exception;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.hungermeals.common.exception.GpnAPIException;

public class GpnAPIMethodInterceptor implements MethodInterceptor {
	
	private static final Log log = LogFactory.getLog(GpnAPIMethodInterceptor.class);

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		log.debug("Before API GpnAPIMethodInterceptor called");
		try {
			Object returnVal = invocation.proceed();
			log.debug("Invocation returned without problems");
			return returnVal;
		} catch (GPNBaseException e) {
			log.error("Exception occured in API", e);
			throw new GpnAPIException(e.getGPNErrorCode(), e);
		} catch (Exception e) {
			log.error("System error occured", e);
			throw new GpnAPIException(GPNErrorCode.GPN_SYSTEM_ERROR, e);
		}
	}

}
