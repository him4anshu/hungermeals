package com.hungermeals.common;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.TreeMap;

import javax.net.ssl.HttpsURLConnection;

import com.hungermeals.persist.OrderDetails;
import com.hungermeals.persist.OrderStatus;
import com.paytm.merchant.CheckSumServiceHelper;

	public class PaytmService {
		
	public TreeMap<String,String> paytmWalletRequestParameter(OrderDetails orderDetails,
			OrderStatus orderStatus) {
		TreeMap<String,String> parameters = generatePostParameter(orderDetails,orderStatus);
		//String checksumhash=getGenratedCheckSum(parameters);
		//parameters.put("CHECKSUMHASH", checksumhash);
		//String postParameters="";
		/*for(Map.Entry<String,String> entry : parameters.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();
			postParameters+=key+"="+value+"&";
		}	  
		postParameters+=getGenratedCheckSum(parameters);	
		return postParameters;*/
		return parameters;
	}
	public void redirectToPaytm(OrderDetails orderDetails,
			OrderStatus orderStatus) {
		TreeMap<String,String> parameters = generatePostParameter(orderDetails,orderStatus);
		String postParameters="";
		for(Map.Entry<String,String> entry : parameters.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();
			postParameters+=key+"="+value+"&";
		}	  
		postParameters+=getGenratedCheckSum(parameters);	
		String url = PaytmConstants.PAYTM_URL;
		try {
			URL obj = new URL(url);
			HttpsURLConnection con= (HttpsURLConnection) obj.openConnection();
			//add reuqest header
			con.setRequestMethod("POST");
			con.setRequestProperty("User-Agent", "Mozilla/5.0");
			con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");		
			// Send post request
			con.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(postParameters);
			wr.flush();
			wr.close();

			int responseCode = con.getResponseCode();
			System.out.println("\nSending 'POST' request to URL : " + url);
			System.out.println("Post parameters : " + postParameters);
			System.out.println("Response Code : " + responseCode);

			BufferedReader in = new BufferedReader(
			        new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();			
			System.out.println(response.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public TreeMap<String,String> generatePostParameter(OrderDetails orderDetails,
			OrderStatus orderStatus){
		TreeMap<String,String> parameters = new TreeMap<String,String>();
		parameters.put("ORDER_ID",orderStatus.getOrderId()+"");
		parameters.put("CUST_ID", orderDetails.getUser().getuCode());
		parameters.put("TXN_AMOUNT", orderDetails.getOrderInfo().getTotalAmount()+"");
		//parameters.put("REQUEST_TYPE",PaytmConstants.REQUEST_TYPE);
		parameters.put("MID",PaytmConstants.MID);
		parameters.put("CHANNEL_ID",PaytmConstants.CHANNEL_ID);
		parameters.put("INDUSTRY_TYPE_ID",PaytmConstants.INDUSTRY_TYPE_ID);
		parameters.put("WEBSITE",PaytmConstants.WEBSITE);
		parameters.put("MOBILE_NO", orderDetails.getUser().getMobile());
		parameters.put("EMAIL", orderDetails.getUser().getEmail());
		//parameters.put("ORDER_DETAILS", "Some messages");
		//parameters.put("VERIFIED_BY", "MOBILE_NO"); //EMAIL or MOBILE_NO
		//parameters.put("IS_USER_VERIFIED", "YES"); //YES /NO
		parameters.put("CALLBACK_URL", PaytmConstants.CALLBACK_URL);
		return parameters;
	}
	
	public String getGenratedCheckSum(TreeMap<String,String> parameters){
		String checkSum="";
		try {
			checkSum = CheckSumServiceHelper.getCheckSumServiceHelper().genrateCheckSum(PaytmConstants.MERCHANT_KEY, parameters);
		} catch (Exception e1) {
			e1.printStackTrace();		
		}
		return checkSum;
	}
}
