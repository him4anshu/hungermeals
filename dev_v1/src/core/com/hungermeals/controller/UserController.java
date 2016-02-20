package com.hungermeals.controller;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;

import com.hungermeals.api.UserAPI;
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
	public OrderStatus orderConfirm(OrderDetails orderDetails){
		return userAPI.orderConfirm(orderDetails);
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
		
	
}
