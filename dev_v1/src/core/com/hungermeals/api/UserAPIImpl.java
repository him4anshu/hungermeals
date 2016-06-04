package com.hungermeals.api;

import java.util.List;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;

import com.hungermeals.facade.UserFacade;
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

public class UserAPIImpl implements UserAPI{
	@Autowired
	private UserFacade userFacade;
	
	
	@Override
	public User1 userRegistration() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User logedInUserProfile(User user) {
		// TODO Auto-generated method stub
		return userFacade.logedInUserProfile(user);
	}

	@Override
	public User userRegistration(User user) {
		return userFacade.userRegistration(user);
	}

	@Override
	public User1 alreadyRegisteredCheck(String email) {
		return userFacade.alreadyRegisteredCheck(email);
	}

	@Override
	public User addUserAddress(User user) {
		return userFacade.addUserAddress(user);

	}

	@Override
	public List<Address> getUserAddress(User user) {
		return userFacade.getUserAddress(user);

	}

	@Override
	public OrderStatus orderConfirm(OrderDetails orderDetails) {
		return userFacade.orderConfirm(orderDetails);
	}

	@Override
	public List<Menu> menuDetail() {
		return userFacade.menuDetail();
	}

	@Override
	public List<OrderStatus> orderHistory(User user) {
		return userFacade.orderHistory(user);
	}

	@Override
	public OrderDetails orderDetails(OrderDetails orderDetails) {
		return userFacade.orderDetails(orderDetails);
	}

	@Override
	public String sendMail(MailingDetails mailingDetails) {
		return userFacade.sendMail(mailingDetails);
	}

	@Override
	public ResponseStatus getotp(String phoneNo) {
		return userFacade.getotp(phoneNo);
	}

	@Override
	public CouponTxn applyCouponCode(CouponTxn couponTxn) {
		return userFacade.applyCouponCode(couponTxn);
	}

	@Override
	public PlanSubscription planSubscription(PlanSubscription planSubscription) {
		return userFacade.planSubscription(planSubscription);
	}

	@Override
	public PlanSubscription updatePlanSubscription(PlanSubscription planSubscription) {
			return userFacade.updatePlanSubscription(planSubscription);
	}

	@Override
	public PlanSubscription cancelPlanSubscription(PlanSubscription planSubscription) {
		return userFacade.cancelPlanSubscription(planSubscription);

	}

	@Override
	public List<ComboDetails> getComboDetails() {
		return userFacade.getComboDetails();

	}

	@Override
	public User changePassword(User user) {
		return userFacade.changePassword(user);

	}

	@Override
	public List<PlanSubscription> comboDetailsByUser(User user) {
		return userFacade.comboDetailsByUser(user);

	}

	@Override
	public User mobileVerification(User user) {
		return userFacade.mobileVerification(user);

	}

	@Override
	public User emailVerification(User user) {
		return userFacade.emailVerification(user);

	}

	@Override
	public User updateMobileVerificationStatus(User user) {
		return userFacade.updateMobileVerificationStatus(user);

	}

	@Override
	public boolean cancelOrder(String orderId) {
		return userFacade.cancelOrder(orderId);

	}

	@Override
	public int paytmWalletResponse(TreeMap<String, String> parameters) {
		return userFacade.paytmWalletResponse(parameters);
		
	}

	@Override
	public boolean sendMessage(String orderId,String orderType) {
		return userFacade.sendMessage(orderId,orderType);

	}

	@Override
	public boolean sendEmail(String orderId) {
		return userFacade.sendEmail(orderId);

	}

	@Override
	public List<Menu> allMenuDetail() {
		return userFacade.allMenuDetail();

	}

	@Override
	public List<Menu> menu() {
		return userFacade.menu();
	}

	@Override
	public int payuWalletResponse(TreeMap<String, String> parameters) {
		return userFacade.payuWalletResponse(parameters);
	}

	@Override
	public String updateOrderStatus(String string) {
		return userFacade.updateOrderStatus(string);

	}

	@Override
	public User registerMobileDevice(User user) {
		return userFacade.registerMobileDevice(user);

	}


	

	

}
