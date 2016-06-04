package com.hungermeals.facade;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import com.hungermeals.common.ConfigReader;
import com.hungermeals.common.MailSender;
import com.hungermeals.common.PaytmConstants;
import com.hungermeals.common.PaytmService;
import com.hungermeals.common.PayuService;
import com.hungermeals.common.SMSThirdPartyService;
import com.hungermeals.common.StringEncrypterService;
import com.hungermeals.dao.UserDAO;
import com.hungermeals.persist.Address;
import com.hungermeals.persist.ComboDetails;
import com.hungermeals.persist.CouponTxn;
import com.hungermeals.persist.MailerDTO;
import com.hungermeals.persist.MailingDetails;
import com.hungermeals.persist.Menu;
import com.hungermeals.persist.Order;
import com.hungermeals.persist.OrderDetails;
import com.hungermeals.persist.OrderStatus;
import com.hungermeals.persist.PlanSubscription;
import com.hungermeals.persist.ResponseStatus;
import com.hungermeals.persist.User;
import com.hungermeals.persist.User1;
import com.paytm.merchant.CheckSumServiceHelper;

public class UserFacadeImpl implements UserFacade{

	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private SMSThirdPartyService smsThirdPartyService;
	
	@Autowired
	private ConfigReader configReader;
	
	@Autowired
	private MailSender hungermealsMailSender;
	
	@Override
	public User logedInUserProfile(User user) {
		// TODO Auto-generated method stub
		String encPassword= StringEncrypterService.encryptString(user.getPassword1());
		user.setPassword1(encPassword);
		return userDAO.logedInUserProfile(user);
	}
	@Override
	public User userRegistration(User user) {
		if("HM".equals(user.getUserType())){
		String encPassword= StringEncrypterService.encryptString(user.getPassword1());
		user.setPassword1(encPassword);
		}else{
			user.setPassword1(null);
		}
		
		String encEmail= StringEncrypterService.encryptString(user.getEmail());
		user.setEncEmail(encEmail);
		User userR=userDAO.userRegistration(user);
		System.out.println("###Sending welcome mail to user###"+userR.getResponseStatus().getResponseCode());
		if(userR.getResponseStatus().getResponseCode().equals("HM200")){
			MailerDTO mailerData=new MailerDTO();
			mailerData.setTo(new String[]{user.getEmail()});
			mailerData.setSubject(configReader.getValue("mail.welcomeUser.subject"));
			mailerData.setFileName(configReader.getValue("mail.welcomeUser.templateName"));
			Map<String, Object> dynamicData=new HashMap<String, Object>();
			dynamicData.put("userName", userR.getFirstName());
			mailerData.setDynamicData(dynamicData);
			boolean sendStatus=hungermealsMailSender.sendMail(mailerData);
			if(sendStatus)
				userR.setMailsendStatus("SUCCESS");
			else
				userR.setMailsendStatus("FAILED");

		}
		return userR;
	}
	@Override
	public User1 alreadyRegisteredCheck(String email) {
		// TODO Auto-generated method stub
		return userDAO.alreadyRegisteredCheck(email);
	}
	@Override
	public List<Address> getUserAddress(User user) {
		return userDAO.getUserAddress(user);
	}
	@Override
	public User addUserAddress(User user) {
		return userDAO.addUserAddress(user);
	}
	@Override
	public OrderStatus orderConfirm(OrderDetails orderDetails) {
		OrderStatus orderStatus = userDAO.orderConfirm(orderDetails);
		if("Order placed".equals(orderStatus.getOrderStatusDesc() ) && (orderDetails.getOrderInfo().getPaymentMode().equalsIgnoreCase("COD") || orderDetails.getOrderInfo().getPaymentMode().equalsIgnoreCase("PAYUAPP") || orderDetails.getOrderInfo().getPaymentMode().equalsIgnoreCase("PAYTMAPP"))){
			String templateId=configReader.getValue("sms.orderplaced4customer");
			SMSThirdPartyService sms=new SMSThirdPartyService();
			String phone=orderDetails.getUser().getMobile();
			Map<String,String> orderDependentParameters=new HashMap<String, String>();
			orderDependentParameters.put("F1", orderStatus.getOrderId()+"");
			orderDependentParameters.put("F2", orderDetails.getOrderInfo().getTotalAmount()+"");
			//orderDependentParameters.put("F3", orderStatus.getExecutiveName()+" (+91 "+orderStatus.getExecutivePhone()+" )");	
			orderDependentParameters.put("F3", "");
			try {
				sms.sendOrderConfermation(new Long(phone), new Long(templateId), orderDependentParameters);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			/*For Admin*/
			String templateId1=configReader.getValue("sms.orderplaced4admin");
			Map<String,String> orderDependentParameters1=new HashMap<String, String>();
			orderDependentParameters1.put("F1", orderStatus.getOrderId()+"");
			orderDependentParameters1.put("F2", orderDetails.getOrderInfo().getTotalAmount()+"");
			orderDependentParameters1.put("F3", "");
			orderDependentParameters1.put("F4", "-"+phone);
			String adminPhone=configReader.getValue("admin.phone");
			try {
				//sms.sendOrderConfermation(new Long(adminPhone), new Long(templateId1), orderDependentParameters1);
				sms.sendOrderConfermation(adminPhone, new Long(templateId1), orderDependentParameters1);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			//Send Email
			MailerDTO mailerData=new MailerDTO();
			mailerData.setTo(new String[]{orderDetails.getUser().getEmail()});
			mailerData.setSubject(configReader.getValue("mail.orderconfirm.subject"));
			mailerData.setFileName(configReader.getValue("mail.orderconfirm.templateName"));
			Map<String, Object> dynamicData=new HashMap<String, Object>();
			dynamicData.put("orderNumber", orderStatus.getOrderId());
			dynamicData.put("userName", orderDetails.getUser().getFirstName());
			dynamicData.put("deliveryTime", orderDetails.getOrderInfo().getDeliverySlot());
			dynamicData.put("totalAmount", orderDetails.getOrderInfo().getTotalAmount());
			dynamicData.put("phone", orderDetails.getUser().getMobile());
			dynamicData.put("line1BuildingNo", orderDetails.getUser().getAddress().getLine1BuildingNo());
			dynamicData.put("line2StreetNo", orderDetails.getUser().getAddress().getLine2StreetNo());
			dynamicData.put("city", orderDetails.getUser().getAddress().getCity());
			dynamicData.put("pCode", orderDetails.getUser().getAddress().getpCode());
			dynamicData.put("email", orderDetails.getUser().getEmail());
			dynamicData.put("paymentMode", orderDetails.getOrderInfo().getPaymentMode());
			mailerData.setDynamicData(dynamicData);
			
			boolean sendStatus=hungermealsMailSender.sendMail(mailerData);
		}else if("Order placed".equals(orderStatus.getOrderStatusDesc() ) && orderDetails.getOrderInfo().getPaymentMode().equalsIgnoreCase("PAYTM")){
			PaytmService paytmService = new PaytmService();
			TreeMap<String,String> parameters=paytmService.paytmWalletRequestParameter(orderDetails,orderStatus);
			orderStatus.setWalletRequest(parameters);
			String checksum="";
			try {
				checksum = CheckSumServiceHelper.getCheckSumServiceHelper().genrateCheckSum(PaytmConstants.MERCHANT_KEY, parameters);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			orderStatus.setChecksum(checksum);
		}else if("Order placed".equals(orderStatus.getOrderStatusDesc() ) && orderDetails.getOrderInfo().getPaymentMode().equalsIgnoreCase("PAYU")){
			PayuService payuService = new PayuService();
			orderStatus.setWalletRequest(payuService.payuWalletRequestParameter(orderDetails,orderStatus));
		}
		
		return orderStatus;
	}
	@Override
	public List<Menu> menuDetail() {
		return userDAO.menuDetail();
	}
	@Override
	public List<OrderStatus> orderHistory(User user) {
		return userDAO.orderHistory(user);
	}
	@Override
	public OrderDetails orderDetails(OrderDetails orderDetails) {
		return userDAO.orderDetails(orderDetails);
	}
	@Override
	public String sendMail(MailingDetails mailingDetails) {
		return userDAO.sendMail(mailingDetails);
	}
	@Override
	public ResponseStatus getotp(String phoneNo) {
		String templateId=configReader.getValue("sms.otp");
		SMSThirdPartyService sms=new SMSThirdPartyService();
		String ucode=UUID.randomUUID().toString();
		String otp=ucode.substring(0,4);
		String encryptedOtp= StringEncrypterService.encryptString(otp.toUpperCase());
		ResponseStatus response=new ResponseStatus();
		String otpUpdateStatus="";
		try {
			otpUpdateStatus = userDAO.getotp(phoneNo,encryptedOtp);
		} catch (Exception e) {
			e.printStackTrace();
			response.setResponseCode("HM103");
			response.setResponseMessage(configReader.getValue("HM103"));
			response.setErrorDetails(e.getMessage());
		}
		if(otpUpdateStatus.equals("FAIL")){
			response.setResponseCode("HM101");
			response.setResponseMessage(configReader.getValue("HM101"));
		}else{
			Map<String,String> smsDependentParameters=new HashMap<String, String>();
			smsDependentParameters.put("F1", otp.toUpperCase());
			smsDependentParameters.put("F2", "password");
			try {
				String responseArray[]=sms.sendsms(new Long(phoneNo), new Long(templateId), smsDependentParameters);
				if(responseArray[0].equals("200")){
					response.setResponseCode("HM200");
					response.setResponseMessage(configReader.getValue("HM200"));
				}else{
					response.setResponseCode(responseArray[0]);
					response.setResponseMessage(responseArray[1]);
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				response.setResponseCode("HM103");
				response.setResponseMessage(configReader.getValue("HM103"));
				response.setErrorDetails(e.getMessage());
			} 
		}
		return response;
	}
	@Override
	public CouponTxn applyCouponCode(CouponTxn couponTxn) {
		return userDAO.applyCouponCode(couponTxn);
	}
	@Override
	public PlanSubscription planSubscription(PlanSubscription planSubscription) {
		PlanSubscription p= userDAO.planSubscription(planSubscription);
		if(p.getPlanSubscribeId()!=0 && ("COD".equalsIgnoreCase(planSubscription.getPaymentMode()) || planSubscription.getPaymentMode().equalsIgnoreCase("PAYUAPP") || planSubscription.getPaymentMode().equalsIgnoreCase("PAYTMAPP"))){
			String templateId=configReader.getValue("sms.subscribeplan");
			SMSThirdPartyService sms=new SMSThirdPartyService();
			String phone=planSubscription.getMobile();
			Map<String,String> orderDependentParameters=new HashMap<String, String>();
			//orderDependentParameters.put("F1", "");
			//orderDependentParameters.put("F2", "");
			//orderDependentParameters.put("F3", orderStatus.getExecutiveName()+" (+91 "+orderStatus.getExecutivePhone()+" )");	
			//orderDependentParameters.put("F3", "");
			try {
				sms.sendOrderConfermation(new Long(phone), new Long(templateId), orderDependentParameters);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(p.getPlanSubscribeId()!=0 && "PAYTM".equalsIgnoreCase(planSubscription.getPaymentMode())){
			OrderStatus orderStatus=new OrderStatus();
			OrderDetails orderDetails=new OrderDetails();
			orderStatus.setOrderId(p.getPlanSubscribeId());
			User user=new User();
			user.setuCode(planSubscription.getuCode());
			user.setMobile(planSubscription.getMobile());
			user.setEmail(planSubscription.getEmail());
			orderDetails.setUser(user);
			Order orderInfo=new Order();
			orderInfo.setTotalAmount(planSubscription.getPlanCost());
			orderDetails.setOrderInfo(orderInfo);
			PaytmService paytmService = new PaytmService();
			TreeMap<String,String> parameters=paytmService.paytmWalletRequestParameter(orderDetails,orderStatus);
			orderStatus.setWalletRequest(parameters);
			String checksum="";
			try {
				checksum = CheckSumServiceHelper.getCheckSumServiceHelper().genrateCheckSum(PaytmConstants.MERCHANT_KEY, parameters);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			orderStatus.setChecksum(checksum);
			p.setOrderStatus(orderStatus);

		}else if(p.getPlanSubscribeId()!=0 && "PAYU".equalsIgnoreCase(planSubscription.getPaymentMode())){
			OrderStatus orderStatus=new OrderStatus();
			OrderDetails orderDetails=new OrderDetails();
			orderStatus.setOrderId(p.getPlanSubscribeId());
			User user=new User();
			user.setuCode(planSubscription.getuCode());
			user.setMobile(planSubscription.getMobile());
			user.setEmail(planSubscription.getEmail());
			orderDetails.setUser(user);
			Order orderInfo=new Order();
			orderInfo.setTotalAmount(planSubscription.getPlanCost());
			orderDetails.setOrderInfo(orderInfo);
			PayuService payuService = new PayuService();
			orderStatus.setWalletRequest(payuService.payuWalletRequestParameter(orderDetails,orderStatus));
			p.setOrderStatus(orderStatus);

		}
		return p;

	}
	@Override
	public PlanSubscription updatePlanSubscription(
			PlanSubscription planSubscription) {
		PlanSubscription p =userDAO.updatePlanSubscription(planSubscription);
		if("HM200".equals(p.getResponseStatus().getResponseCode())){
			String templateId=configReader.getValue("sms.updateplan");
			SMSThirdPartyService sms=new SMSThirdPartyService();
			String phone=planSubscription.getMobile();
			Map<String,String> orderDependentParameters=new HashMap<String, String>();
			//orderDependentParameters.put("F1", "");
			//orderDependentParameters.put("F2", "");
			//orderDependentParameters.put("F3", orderStatus.getExecutiveName()+" (+91 "+orderStatus.getExecutivePhone()+" )");	
			//orderDependentParameters.put("F3", "");
			try {
				sms.sendOrderConfermation(new Long(phone), new Long(templateId), orderDependentParameters);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		
		}
		return p;
	}
	@Override
	public PlanSubscription cancelPlanSubscription(
			PlanSubscription planSubscription) {
		PlanSubscription p= userDAO.cancelPlanSubscription(planSubscription);
		if("HM200".equals(p.getResponseStatus().getResponseCode())){
			String templateId=configReader.getValue("sms.cancelplan");
			SMSThirdPartyService sms=new SMSThirdPartyService();
			String phone=planSubscription.getMobile();
			Map<String,String> orderDependentParameters=new HashMap<String, String>();
			//orderDependentParameters.put("F1", "");
			//orderDependentParameters.put("F2", "");
			//orderDependentParameters.put("F3", orderStatus.getExecutiveName()+" (+91 "+orderStatus.getExecutivePhone()+" )");	
			//orderDependentParameters.put("F3", "");
			try {
				sms.sendOrderConfermation(new Long(phone), new Long(templateId), orderDependentParameters);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		
		}
		return p;

	}
	@Override
	public List<ComboDetails> getComboDetails() {
		return userDAO.getComboDetails();
	}
	
	@Override
	public User changePassword(User user) {
		String encPassword= StringEncrypterService.encryptString(user.getPassword1());
		user.setPassword1(encPassword);
		return userDAO.changePassword(user);

	}
	@Override
	public List<PlanSubscription> comboDetailsByUser(User user) {
		return userDAO.comboDetailsByUser(user);

	}
	@Override
	public User mobileVerification(User usr) {
		 User user=userDAO.mobileVerification(usr);
		 ResponseStatus response=new ResponseStatus();
		 if("HM200".equals(user.getResponseStatus().getResponseCode())){
			 String code=userDAO.getMobileVerificationCode(user);
			 if(!code.equals("FAIL")){
					String templateId=configReader.getValue("sms.mobileverificationcode");
					SMSThirdPartyService sms=new SMSThirdPartyService();
					String phone=usr.getMobile();
					Map<String,String> smsDependentParameters=new HashMap<String, String>();
					smsDependentParameters.put("F1", code.toUpperCase());
					smsDependentParameters.put("F2", "Mobile Number");
					try {
						sms.sendOrderConfermation(new Long(phone), new Long(templateId), smsDependentParameters);
					}catch (Exception e) {
						e.printStackTrace();
						response.setResponseCode("HM103");
						response.setResponseMessage(configReader.getValue("HM103"));
						response.setErrorDetails(e.getMessage());
						user.setResponseStatus(response);
					}				
			 }else{
				 	response.setResponseCode("HM206");
				 	response.setResponseMessage(configReader.getValue("HM206"));
					user.setResponseStatus(response);
			 }
		 }
		 return user;
	}
	@Override
	public User emailVerification(User user) {
		return userDAO.emailVerification(user);
	}
	@Override
	public User updateMobileVerificationStatus(User user) {
		return userDAO.updateMobileVerificationStatus(user);

	}
	@Override
	public boolean cancelOrder(String orderId) {
		return userDAO.cancelOrder(orderId);

	}
	@Override
	public int paytmWalletResponse(TreeMap<String, String> parameters) {
		return userDAO.paytmWalletResponse(parameters);

	}
	public boolean sendMessageForPlanSubscription(String orderId){
		String templateId=configReader.getValue("sms.subscribeplan");
		PlanSubscription planSubscription=userDAO.getPlanSubscriptionDetails(orderId);
		SMSThirdPartyService sms=new SMSThirdPartyService();
		String phone=planSubscription.getMobile();
		Map<String,String> orderDependentParameters=new HashMap<String, String>();
		//orderDependentParameters.put("F1", "");
		//orderDependentParameters.put("F2", "");
		//orderDependentParameters.put("F3", orderStatus.getExecutiveName()+" (+91 "+orderStatus.getExecutivePhone()+" )");	
		//orderDependentParameters.put("F3", "");
		try {
			sms.sendOrderConfermation(new Long(phone), new Long(templateId), orderDependentParameters);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}
	public boolean sendMessageForDailyOrder(String orderId){
		String templateId=configReader.getValue("sms.orderplaced4customer");
		SMSThirdPartyService sms=new SMSThirdPartyService();
		User user=userDAO.getCustomerByOrder(orderId);
		Map<String,String> orderDependentParameters=new HashMap<String, String>();
		orderDependentParameters.put("F1", orderId);
		orderDependentParameters.put("F2", user.getTotalAmount()+"");
		//orderDependentParameters.put("F3", orderStatus.getExecutiveName()+" (+91 "+orderStatus.getExecutivePhone()+" )");	
		orderDependentParameters.put("F3", "");
		try {
			sms.sendOrderConfermation(new Long(user.getMobile()), new Long(templateId), orderDependentParameters);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		/*For Admin*/
		String templateId1=configReader.getValue("sms.orderplaced4admin");
		Map<String,String> orderDependentParameters1=new HashMap<String, String>();
		orderDependentParameters1.put("F1", orderId);
		orderDependentParameters1.put("F2", user.getTotalAmount()+"");
		orderDependentParameters1.put("F3", "");
		orderDependentParameters1.put("F4", "-"+user.getMobile());
		String adminPhone=configReader.getValue("admin.phone");
		try {
			//sms.sendOrderConfermation(new Long(adminPhone), new Long(templateId1), orderDependentParameters1);
			sms.sendOrderConfermation(adminPhone, new Long(templateId1), orderDependentParameters1);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	
			//Send Email
			try {
				MailerDTO mailerData=new MailerDTO();
				mailerData.setTo(new String[]{user.getEmail()});
				mailerData.setSubject(configReader.getValue("mail.orderconfirm.subject"));
				mailerData.setFileName(configReader.getValue("mail.orderconfirm.templateName"));
				Map<String, Object> dynamicData=new HashMap<String, Object>();
				dynamicData.put("orderNumber", orderId);
				dynamicData.put("userName", user.getFirstName());
				dynamicData.put("deliveryTime", user.getTemp2());
				dynamicData.put("totalAmount", user.getTotalAmount());
				dynamicData.put("phone", user.getMobile());
				dynamicData.put("paymentMode", user.getTemp1());
				dynamicData.put("line1BuildingNo", user.getAddress().getLine1BuildingNo());
				dynamicData.put("line2StreetNo", user.getAddress().getLine2StreetNo());
				dynamicData.put("city", user.getAddress().getCity());
				dynamicData.put("pCode", user.getAddress().getpCode());
				dynamicData.put("email", user.getEmail());
				mailerData.setDynamicData(dynamicData);

				boolean sendStatus=hungermealsMailSender.sendMail(mailerData);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return true;
	}
	@Override
	public boolean sendMessage(String orderId,String ordrType) {

		if(ordrType.equalsIgnoreCase("DAILY_ORDER"))
			return sendMessageForDailyOrder(orderId);
		else if(ordrType.equalsIgnoreCase("MONTHLY_ORDER")){
			return sendMessageForPlanSubscription(orderId);
		}else {
			return false;
		}

	}
	@Override
	public boolean sendEmail(String orderId) {
		return userDAO.sendEmail(orderId);

	}
	@Override
	public List<Menu> allMenuDetail() {
		return userDAO.allMenuDetail();

	}
	@Override
	public List<Menu> menu() {
		return userDAO.menu();

	}
	@Override
	public int payuWalletResponse(TreeMap<String, String> parameters) {
		return userDAO.payuWalletResponse(parameters);

	}
	@Override
	public String updateOrderStatus(String string) {
		return userDAO.updateOrderStatus(string);

	}
	@Override
	public User registerMobileDevice(User user) {
		return userDAO.registerMobileDevice(user);

	}
	
		
}
