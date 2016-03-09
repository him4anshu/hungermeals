package com.hungermeals.common;

public class PayuConstant {
	public final static String MERCHANT_KEY="UJQjU144";
	public final static String SALT="PUJgKf1ni9";
	public final static String PAYU_URL="https://secure.payu.in";
	//public final static String PAYU_URL="https://test.payu.in/_payment";
	public final static String CALLBACK_SUCCESS_URL="http://hungermeals.com/services/userservices/payumWalletSuccessResponse.json"; 
	public final static String CALLBACK_FAILURE_URL="http://hungermeals.com/services/userservices/payumWalletFailureResponse.json"; 
	public final static String CALLBACK_CANCEL_URL="http://hungermeals.com/services/userservices/payumWalletCancelResponse.json"; 
	public final static String SERVICE_PROVIDER="payu_paisa";


}
