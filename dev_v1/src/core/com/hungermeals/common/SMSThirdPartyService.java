package com.hungermeals.common;

import java.io.*;
import java.net.*;
import java.util.Map;


public class SMSThirdPartyService {

	private String thirdPartyUrl="";
	private String senderId="";
	private String templateId="";
	private String userName="";
	private String password="";
	private final String USER_AGENT = "Mozilla/5.0";
	ConfigReader cfg=new ConfigReader();
	public SMSThirdPartyService(){
		thirdPartyUrl=cfg.getValue("sms.url");
		senderId=cfg.getValue("sms.senderid");
		userName=cfg.getValue("sms.username");
		password=cfg.getValue("sms.password");
	}
	
	public StringBuffer sendOrderConfermation(long mobileNo ,long templateId ,Map<String,String> orderDependentParameters) throws Exception{

		StringBuffer dynamicParameter=new StringBuffer();
		for (Map.Entry<String, String> entry : orderDependentParameters.entrySet()){
			System.out.println(entry.getKey() +"=="+ entry.getValue());
			dynamicParameter.append("&"+entry.getKey()+"="+entry.getValue());
		}
		/*String url = thirdPartyUrl
				+"?username="+userName+"&pass="+password
				+"&senderid="+senderId+"&dest_mobileno="+mobileNo
				+"&tempid="+templateId
				+"&response=Y"
				+"&F1=HUNGER001&F2=380&F3=Himanshu[8123719594]&response=Y";*/
		String url = thirdPartyUrl
				+"?username="+userName+"&pass="+password
				+"&senderid="+senderId+"&dest_mobileno="+mobileNo
				+"&tempid="+templateId
				+"&response=Y"
				+dynamicParameter;
		
		System.out.println(url);
		url=url.replace(" ","%20");
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// optional default is GET
		con.setRequestMethod("GET");

		//add request header
		con.setRequestProperty("User-Agent", USER_AGENT);

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
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
		return response;
	}
	
	public String[] sendsms(long mobileNo ,long templateId ,Map<String,String> orderDependentParameters) throws Exception{

		String responseArray[]=new String[10];
		StringBuffer dynamicParameter=new StringBuffer();
		for (Map.Entry<String, String> entry : orderDependentParameters.entrySet()){
			System.out.println(entry.getKey() +"=="+ entry.getValue());
			dynamicParameter.append("&"+entry.getKey()+"="+entry.getValue());
		}
		/*String url = thirdPartyUrl
				+"?username="+userName+"&pass="+password
				+"&senderid="+senderId+"&dest_mobileno="+mobileNo
				+"&tempid="+templateId
				+"&response=Y"
				+"&F1=HUNGER001&F2=380&F3=Himanshu[8123719594]&response=Y";*/
		String url = thirdPartyUrl
				+"?username="+userName+"&pass="+password
				+"&senderid="+senderId+"&dest_mobileno="+mobileNo
				+"&tempid="+templateId
				+"&response=Y"
				+dynamicParameter;
		
		System.out.println(url);
		url=url.replace(" ","%20");
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// optional default is GET
		con.setRequestMethod("GET");

		//add request header
		con.setRequestProperty("User-Agent", USER_AGENT);

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);
		responseArray[0]=responseCode+"";
		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		System.out.println(response.toString());
		responseArray[1]=response.toString();
		return responseArray;
	
	}
}
