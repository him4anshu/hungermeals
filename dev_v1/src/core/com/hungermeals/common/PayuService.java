package com.hungermeals.common;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

import javax.net.ssl.HttpsURLConnection;

import com.hungermeals.persist.OrderDetails;
import com.hungermeals.persist.OrderStatus;
import com.paytm.merchant.CheckSumServiceHelper;

public class PayuService {

	public static boolean empty(String s){
		if(s== null || s.trim().equals(""))
			return true;
		else
			return false;
	}
	
	public static String hashCal(String type,String str){
		byte[] hashseq=str.getBytes();
		StringBuffer hexString = new StringBuffer();
		try{
			MessageDigest algorithm = MessageDigest.getInstance(type);
			algorithm.reset();
			algorithm.update(hashseq);
			byte messageDigest[] = algorithm.digest();
			for (int i=0;i<messageDigest.length;i++) {
			String hex=Integer.toHexString(0xFF & messageDigest[i]);
			if(hex.length()==1) hexString.append("0");
			hexString.append(hex);
			}			
		}catch(NoSuchAlgorithmException nsae){ 
			nsae.printStackTrace();
		}
		return hexString.toString();
	}

	public TreeMap<String,String> payuWalletRequestParameter(OrderDetails orderDetails,
			OrderStatus orderStatus) {
		TreeMap<String,String> parameters = generatePostParameter(orderDetails,orderStatus);
		/*String checksumhash=getGenratedCheckSum(parameters);
		parameters.put("hash",checksumhash);*/
		return parameters;
	}
	
	public TreeMap<String,String> generatePostParameter(OrderDetails orderDetails,
			OrderStatus orderStatus){
		TreeMap<String,String> parameters = new TreeMap<String,String>();
		parameters.put("key",PayuConstant.MERCHANT_KEY);
		parameters.put("txnid", orderStatus.getOrderId()+"");
		parameters.put("udf2", orderStatus.getOrderId()+"");
		parameters.put("amount", orderDetails.getOrderInfo().getTotalAmount()+"");
		parameters.put("productinfo","prppppppppp");
		parameters.put("firstname",orderDetails.getUser().getFirstName());
		parameters.put("email",orderDetails.getUser().getEmail());
		parameters.put("phone",orderDetails.getUser().getMobile());
		parameters.put("surl",PayuConstant.CALLBACK_SUCCESS_URL);
		parameters.put("furl", PayuConstant.CALLBACK_FAILURE_URL);
		parameters.put("curl", PayuConstant.CALLBACK_CANCEL_URL);
		parameters.put("service_provider", PayuConstant.SERVICE_PROVIDER);
		return parameters;
	}
	
	public static String getGenratedCheckSum(TreeMap<String,String> params){
		String txnid ="";
		String hashString="";
		String hash="";
		if(empty(params.get("txnid"))){
			Random rand = new Random();
			String rndm = Integer.toString(rand.nextInt())+(System.currentTimeMillis() / 1000L);
			txnid=hashCal("SHA-256",rndm).substring(0,20);
		}else{
			txnid=params.get("txnid");
		}
		String hashSequence = "key|txnid|amount|productinfo|firstname|email";
		if( empty(params.get("key"))|| empty(params.get("txnid"))|| empty(params.get("amount"))|| empty(params.get("firstname"))
				|| empty(params.get("email"))|| empty(params.get("phone"))|| empty(params.get("productinfo"))|| empty(params.get("surl"))
				|| empty(params.get("furl"))|| empty(params.get("service_provider"))){
			return "Missing mandetory field";
		}else{
			String[] hashVarSeq=hashSequence.split("\\|");			
			for(String part : hashVarSeq){
					hashString= (empty(params.get(part)))?hashString.concat(""):hashString.concat(params.get(part));
					hashString=hashString.concat("|");
			}
			hashString=hashString.concat(PayuConstant.SALT);
			hash=hashCal("SHA-512",hashString);
		}
		return hash;	
	}
	
	public static boolean verifyCheckSumHash(Map<String,String> parameter ,String payUchecksum){
		return false;
		
	}

}
