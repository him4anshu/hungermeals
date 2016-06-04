package com.hungermeals.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
		HttpSession session=request.getSession();
		session.setAttribute("ORDER_TYPE", "DAILIY_ORDER");
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
	public PlanSubscription planSubscription(PlanSubscription planSubscription,@Context HttpServletRequest request){
		HttpSession session=request.getSession();
		session.setAttribute("ORDER_TYPE", "MONTHLY_ORDER");
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
	
	
	@POST
	@Path("/payumWalletResponse.json")
    @Produces("application/json")
	public String payumWalletResponse(@Context HttpServletRequest request,@Context HttpServletResponse response){
		System.out.println("payumWalletResponse.json called by PAYU");
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
					userAPI.updateOrderStatus(1+"@@"+parameters.get("ORDERID"));
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
	
	public boolean sendMessage(String orderId, String orderType){
		return userAPI.sendMessage(orderId,orderType);
	}
	
	public boolean sendEmail(String orderId){
		return userAPI.sendEmail(orderId);
	}
	
	@POST
	@Path("/paytmCheckSumGenrator.json")
	public String paytmCheckSumGenrator(OrderDetails orderDetails, @Context HttpServletRequest request,@Context HttpServletResponse response){
		System.out.println("paytmCheckSumGenrator");
		TreeMap<String,String> parameters = new TreeMap<String,String>();
		parameters.put("ORDER_ID",UUID.randomUUID().toString());
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
		
		String paytmChecksum =  "";
		
		String checkSum=null;
		try {
			checkSum = CheckSumServiceHelper.getCheckSumServiceHelper().genrateCheckSum(PaytmConstants.MERCHANT_KEY, parameters);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		parameters.put("CHECKSUMHASH",checkSum);
		parameters.put("payt_STATUS","1");
		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		return gson.toJson(parameters);
		//return null;
	}
	
	@POST
	@Path("/paytmCheckSumValidator.json")
	@Consumes("application/x-www-form-urlencoded")
	public String paytmCheckSumValidator(@Context HttpServletRequest request,@Context HttpServletResponse response){
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
		boolean isValideChecksum = false;
		try{
			isValideChecksum = CheckSumServiceHelper.getCheckSumServiceHelper().verifycheckSum(PaytmConstants.MERCHANT_KEY,parameters,paytmChecksum);
			parameters.put("IS_CHECKSUM_VALID",isValideChecksum==true?"Y":"N");
		}catch(Exception e){
			parameters.put("IS_CHECKSUM_VALID",isValideChecksum==true?"Y":"N");	
		}
		
		//
		Gson gson = new GsonBuilder().disableHtmlEscaping().create();	
		return gson.toJson(parameters);
	}

	@POST
	@Path("/paytmWalletResponse.json")
	@Consumes("application/x-www-form-urlencoded")
	public void paytmRedirect(@Context HttpServletRequest request,@Context HttpServletResponse response){
		System.out.println("PAYTM Response for Trnx");
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
			System.out.print("ParamName = "+paramName+" ParamValue = "+mapData.get(paramName)[0]);
		}
		//Inserting PAYTM response to database
		try {
			int x=userAPI.paytmWalletResponse(parameters);
			System.out.println("PAYTM Rsponse inserted in paytm_txn_details table successfully");
		} catch (Exception e1) {
			System.out.println("PAYTM Rsponse NOT inserted in paytm_txn_details table successfully");
			e1.printStackTrace();
		}

		boolean isValideChecksum = false;
		String outputHTML="";
		try{
			isValideChecksum = CheckSumServiceHelper.getCheckSumServiceHelper().verifycheckSum(PaytmConstants.MERCHANT_KEY,parameters,paytmChecksum);
			System.out.println("Validation of checksum is :"+isValideChecksum);
			 RequestDispatcher rd=null; 
			if(isValideChecksum && parameters.containsKey("RESPCODE")){
				if(parameters.get("RESPCODE").equals("01")){
					outputHTML = parameters.toString();
					try {
						System.out.println("Processed Order Id by PAYTM="+parameters.get("ORDERID"));
						sendMessage(parameters.get("ORDERID"),request.getSession().getAttribute("ORDER_TYPE")+"");
						userAPI.updateOrderStatus(1+"@@"+parameters.get("ORDERID"));
					} catch (Exception e) {
						e.printStackTrace();
					}
					rd=request.getRequestDispatcher("/jsp/o_confirm2.jsp");
				}else if(parameters.get("RESPCODE").equals("141") ||
						parameters.get("RESPCODE").equals("810") ||
						parameters.get("RESPCODE").equals("8102")||
						parameters.get("RESPCODE").equals("8103")){
					System.out.println("Processed Order Id by PAYTM="+parameters.get("ORDERID"));
					cancelOrder(parameters.get("ORDERID"));
					rd=request.getRequestDispatcher("/jsp/payment.jsp");
				}else if(parameters.get("RESPCODE").equals("227")){
					System.out.println("Processed Order Id by PAYTM="+parameters.get("ORDERID"));
					cancelOrder(parameters.get("ORDERID"));
					rd=request.getRequestDispatcher("/jsp/payment.jsp");
				}else{
					System.out.println("Processed Order Id by PAYTM="+parameters.get("ORDERID"));
					cancelOrder(parameters.get("ORDERID"));
					rd=request.getRequestDispatcher("/jsp/payment.jsp");
				}
			}else{
				System.out.println("Processed Order Id by PAYTM="+parameters.get("ORDERID"));
				cancelOrder(parameters.get("ORDERID"));
				rd=request.getRequestDispatcher("/jsp/error.jsp");
			}
			rd.forward(request, response);
		}catch(Exception e){
			outputHTML=e.toString();
		}
		 
	}
	
	@POST
	@Path("/payumWalletSuccessResponse.json")
	@Consumes("application/x-www-form-urlencoded")
	public void payumWalletSuccessResponse(@Context HttpServletRequest request,@Context HttpServletResponse response) throws Exception{
		System.out.println("payumWalletSuccessResponse.json called by PAYU");
		Enumeration<String> paramNames = request.getParameterNames();
		Map<String, String[]> mapData = request.getParameterMap();
		TreeMap<String,String> parameters = new TreeMap<String,String>();
		String hash =  "";
		while(paramNames.hasMoreElements()) {
			String paramName = (String)paramNames.nextElement();
			if(paramName.equals("hash")){
				hash = mapData.get(paramName)[0];
			}else{
				parameters.put(paramName,mapData.get(paramName)[0]);
			}
			System.out.println(paramName+"===>"+mapData.get(paramName)[0]);
		}
		 try {
			sendMessage(parameters.get("txnid"),request.getSession().getAttribute("ORDER_TYPE")+""); //txnid as orderId

		} catch (Exception e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		 try {
			userAPI.updateOrderStatus(1+"@@"+parameters.get("txnid"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//Inserting PAYTM response to database
			try {
				int x=userAPI.payuWalletResponse(parameters);
				System.out.println("PAYU Rsponse inserted in paytm_txn_details table successfully");
			} catch (Exception e1) {
				System.out.println("PAYU Rsponse NOT inserted in paytm_txn_details table successfully");
				e1.printStackTrace();
			}
		 
		 RequestDispatcher rd=null; 
		 rd=request.getRequestDispatcher("/jsp/o_confirm2.jsp");
		 rd.forward(request, response);
	}
	
	@POST
	@Path("/payumWalletFailureResponse.json")
	@Consumes("application/x-www-form-urlencoded")
	public void payumWalletFailureResponse(@Context HttpServletRequest request,@Context HttpServletResponse response){
		System.out.println("payumWalletFailureResponse.json called by PAYU");
		Enumeration<String> paramNames = request.getParameterNames();
		Map<String, String[]> mapData = request.getParameterMap();
		TreeMap<String,String> parameters = new TreeMap<String,String>();
		String hash =  "";
		while(paramNames.hasMoreElements()) {
			String paramName = (String)paramNames.nextElement();
			if(paramName.equals("hash")){
				hash = mapData.get(paramName)[0];
			}else{
				parameters.put(paramName,mapData.get(paramName)[0]);
			}
			System.out.println(paramName+"===>"+mapData.get(paramName)[0]);
		}
		try {
			RequestDispatcher rd=null;
			rd=request.getRequestDispatcher("/jsp/payment.jsp");
			System.out.println("OrderId=====>"+parameters.get("txnid"));
			cancelOrder(parameters.get("txnid")); //txnid as orderId
			try {
				int x=userAPI.payuWalletResponse(parameters);
				System.out.println("PAYU Rsponse inserted in paytm_txn_details table successfully");
			} catch (Exception e1) {
				System.out.println("PAYU Rsponse NOT inserted in paytm_txn_details table successfully");
				e1.printStackTrace();
			}
			rd.forward(request, response);		
			} catch (Exception e) {
			e.printStackTrace();
		}
		 
	}
	
	@POST
	@Path("/payumWalletCancelResponse.json")
	@Consumes("application/x-www-form-urlencoded")
	public void payumWalletCancelResponse(@Context HttpServletRequest request,@Context HttpServletResponse response){
		System.out.println("payumWalletCancelResponse.json called by PAYU");
		Enumeration<String> paramNames = request.getParameterNames();
		Map<String, String[]> mapData = request.getParameterMap();
		TreeMap<String,String> parameters = new TreeMap<String,String>();
		String hash =  "";
		while(paramNames.hasMoreElements()) {
			String paramName = (String)paramNames.nextElement();
			if(paramName.equals("hash")){
				hash = mapData.get(paramName)[0];
			}else{
				parameters.put(paramName,mapData.get(paramName)[0]);
			}
			System.out.println(paramName+"===>"+mapData.get(paramName)[0]);
		}
		try {
			RequestDispatcher rd=null;
			rd=request.getRequestDispatcher("/jsp/payment.jsp");
			System.out.println("OrderId=====>"+parameters.get("txnid"));
			cancelOrder(parameters.get("txnid")); //txnid as orderId
			try {
				int x=userAPI.payuWalletResponse(parameters);
				System.out.println("PAYU Rsponse inserted in paytm_txn_details table successfully");
			} catch (Exception e1) {
				System.out.println("PAYU Rsponse NOT inserted in paytm_txn_details table successfully");
				e1.printStackTrace();
			}
			rd.forward(request, response);		
			} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	@GET
	@Path("/allMenuDetail.json")
    @Produces("application/json")
	public List<Menu> allMenuDetail(){
		return userAPI.allMenuDetail();
	}
	
	@GET
	@Path("/menu.json")
    @Produces("application/json")
	public List<Menu> menu(){
		return userAPI.menu();
	}
	
	@POST
	@Path("/registerMobileDevice.json")
    @Produces("application/json")
	public User registerMobileDevice(User user){
		return userAPI.registerMobileDevice(user);
	}
}
