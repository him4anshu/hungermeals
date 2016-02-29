package com.hungermeals.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

import com.hungermeals.api.UserAPI;
import com.hungermeals.common.PaytmConstants;
import com.hungermeals.common.PayuConstant;
import com.hungermeals.common.PayuService;
import com.hungermeals.common.SMSThirdPartyService;
import com.hungermeals.dao.UserDAO;
import com.hungermeals.persist.Address;
import com.hungermeals.persist.ComboDetails;
import com.hungermeals.persist.CouponTxn;
import com.hungermeals.persist.MailingDetails;
import com.hungermeals.persist.Menu;
import com.hungermeals.persist.OrderDetails;
import com.hungermeals.persist.OrderStatus;
import com.hungermeals.persist.PlanSubscription;
import com.hungermeals.persist.ResponseStatus;
import com.hungermeals.persist.User;
import com.hungermeals.persist.User1;
import com.paytm.merchant.CheckSumServiceHelper;


public class UserController {

	@Autowired
	private UserAPI userAPI;
	
	public UserAPI getUserAPI() {
		return userAPI;
	}

	public void setUserAPI(UserAPI userAPI) {
		this.userAPI = userAPI;
	}

	@GET
	@Path("/userInfo.json")
    @Produces("application/json")
	public User getUserInfo(){
		System.out.println("###@@@");
		User user = new User();
		user.setEmail("test.fidloo.com");
		user.setUserId(99999);
		return user;
	}
	@POST
	@Path("/userRegistration.json")
    @Produces("application/json")
	@Consumes("application/json")
	public User userRegistration(User user){
		return userAPI.userRegistration(user);
	}
	@POST
	@Path("/alreadyRegisteredCheck.json")
    @Produces("application/json")
	@Consumes("application/json")
	public User1 alreadyRegisteredCheck(User1 user){
		System.out.println("###@@@###");
		return userAPI.alreadyRegisteredCheck(user.getUserEmail());
	}
	@POST
	@Path("/userLogin.json")
    @Produces("application/json")
	@Consumes("application/json")
	public User userLogin(User user){
		return userAPI.logedInUserProfile(user);
	}
	
	@POST
	@Path("/addUserAddress.json")
    @Produces("application/json")
	@Consumes("application/json")
	public User addUserAddress(User user){
		return userAPI.addUserAddress(user);
	}
	
	@POST
	@Path("/getUserAddress.json")
    @Produces("application/json")
	@Consumes("application/json")
	public List<Address> getUserAddress(User user){
		return userAPI.getUserAddress(user);
	}
	
	@POST
	@Path("/orderConfirm.json")
    @Produces("application/json")
	@Consumes("application/json")
	public OrderStatus orderConfirm(OrderDetails orderDetails ,@Context HttpServletRequest request,@Context HttpServletResponse response){
		OrderStatus orderStatus= userAPI.orderConfirm(orderDetails);
		return orderStatus;
	}
	
	
	@GET
	@Path("/menuDetail.json")
    @Produces("application/json")
	public List<Menu> menuDetail(){
		return userAPI.menuDetail();
	}
	
	@POST
	@Path("/orderHistory.json")
    @Produces("application/json")
	public List<OrderStatus> orderHistory(User user){
		return userAPI.orderHistory(user);
	}
	
	@POST
	@Path("/orderDetails.json")
    @Produces("application/json")
	public OrderDetails orderDetails(OrderDetails orderDetails){
		return userAPI.orderDetails(orderDetails);
	}

	@POST
	@Path("/sendmail.json")
    @Produces("application/json")
	public String sendMail(MailingDetails mailingDetails){
		return userAPI.sendMail(mailingDetails);
	}
	
	@GET
	@Path("/getotp.json/{phoneNo}")
    @Produces("application/json")
	public ResponseStatus getotp(@PathParam("phoneNo") String phoneNo){
		return userAPI.getotp(phoneNo);
	}
	
	@POST
	@Path("/applyCouponCode.json")
    @Produces("application/json")
	public CouponTxn applyCouponCode(CouponTxn couponTxn){
		return userAPI.applyCouponCode(couponTxn);
	}
	
	@POST
	@Path("/planSubscription.json")
    @Produces("application/json")
	public PlanSubscription planSubscription(PlanSubscription planSubscription){
		return userAPI.planSubscription(planSubscription);
	}
	
	@POST
	@Path("/updatePlanSubscription.json")
    @Produces("application/json")
	public PlanSubscription updatePlanSubscription(PlanSubscription planSubscription){
		return userAPI.updatePlanSubscription(planSubscription);
	}
	
	@POST
	@Path("/cancelPlanSubscription.json")
    @Produces("application/json")
	public PlanSubscription cancelPlanSubscription(PlanSubscription planSubscription){
		return userAPI.cancelPlanSubscription(planSubscription);
	}
	
	@GET
	@Path("/getComboDetails.json")
    @Produces("application/json")
	public List<ComboDetails> getComboDetails(){
		return userAPI.getComboDetails();
	}
	
	@POST
	@Path("/comboDetailsByUser.json")
    @Produces("application/json")
	public List<PlanSubscription> comboDetails(User user){
		return userAPI.comboDetailsByUser(user);
	}
	
	@POST
	@Path("/changePassword.json")
    @Produces("application/json")
	public User changePassword(User user){
		return userAPI.changePassword(user);
	}
	
	@POST
	@Path("/mobileVerification.json")
    @Produces("application/json")
	public User mobileVerification(User user){
		return userAPI.mobileVerification(user);
	}
	
	@POST
	@Path("/emailVerification.json")
    @Produces("application/json")
	public User emailVerification(User user){
		return userAPI.emailVerification(user);
	}
	
	@POST
	@Path("/updateMobileVerificationStatus.json")
    @Produces("application/json")
	public User updateMobileVerificationStatus(User user){
		return userAPI.updateMobileVerificationStatus(user);
	}
	
	@GET
	@Path("/paytmWalletResponse.json")
    @Produces("application/json")
	public String paytmWalletResponse(@Context HttpServletRequest request,@Context HttpServletResponse response){
		System.out.println("paytmWalletResponse.json called by PAYTM");
		Enumeration<String> paramNames = request.getParameterNames();
		Map<String, String[]> mapData = request.getParameterMap();
		TreeMap<String,String> parameters = new TreeMap<String,String>();
		String paytmChecksum =  "";
		while(paramNames.hasMoreElements()) {
			String paramName = (String)paramNames.nextElement();
			if(paramName.equals("CHECKSUMHASH")){
				paytmChecksum = mapData.get(paramName)[0];
			}else{
				parameters.put(paramName,mapData.get(paramName)[0]);
			}
		}
		//Inserting PAYTM response to database
		int x=userAPI.paytmWalletResponse(parameters);

		boolean isValideChecksum = false;
		String outputHTML="";
		try{
			isValideChecksum = CheckSumServiceHelper.getCheckSumServiceHelper().verifycheckSum(PaytmConstants.MERCHANT_KEY,parameters,paytmChecksum);
			if(isValideChecksum && parameters.containsKey("RESPCODE")){
				if(parameters.get("RESPCODE").equals("01")){
					outputHTML = parameters.toString();
				}else{
					outputHTML="<b>Payment Failed.</b>";
					cancelOrder(parameters.get("ORDERID"));
				}
			}else{
				outputHTML="<b>Checksum mismatched.</b>";
				cancelOrder(parameters.get("ORDERID"));
			}
		}catch(Exception e){
			outputHTML=e.toString();
		}
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(outputHTML);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return outputHTML;
	}
	
	@GET
	@Path("/payumWalletResponse.json")
    @Produces("application/json")
	public String payumWalletResponse(@Context HttpServletRequest request,@Context HttpServletResponse response){
		System.out.println("payumWalletResponse.json called by PAYTU");
		Enumeration<String> paramNames = request.getParameterNames();
		Map<String, String[]> mapData = request.getParameterMap();
		TreeMap<String,String> parameters = new TreeMap<String,String>();
		String payuChecksum =  "";
		while(paramNames.hasMoreElements()) {
			String paramName = (String)paramNames.nextElement();
			if(paramName.equals("hash")){
				payuChecksum = mapData.get(paramName)[0];
			}else{
				parameters.put(paramName,mapData.get(paramName)[0]);
			}
		}
		//Inserting PAYTM response to database
		//int x=userAPI.paytmWalletResponse(parameters);

		boolean isValideChecksum = false;
		String outputHTML="";
		try{
			isValideChecksum = PayuService.verifyCheckSumHash(parameters,payuChecksum);
			if(isValideChecksum && parameters.containsKey("RESPCODE")){
				if(parameters.get("RESPCODE").equals("01")){
					outputHTML = parameters.toString();
				}else{
					outputHTML="<b>Payment Failed.</b>";
					cancelOrder(parameters.get("ORDERID"));
				}
			}else{
				outputHTML="<b>Checksum mismatched.</b>";
				cancelOrder(parameters.get("ORDERID"));
			}
		}catch(Exception e){
			outputHTML=e.toString();
		}
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(outputHTML);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return outputHTML;
	}
	
	public boolean cancelOrder(String orderId){
		return userAPI.cancelOrder(orderId);
	}

}
