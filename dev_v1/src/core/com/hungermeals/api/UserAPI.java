package com.hungermeals.api;

import java.util.List;
import java.util.TreeMap;

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

public interface UserAPI {

	User1 userRegistration();
	User logedInUserProfile(User user);
	User userRegistration(User user);
	User1 alreadyRegisteredCheck(String email);
	User addUserAddress(User address);
	List<Address> getUserAddress(User user);
	OrderStatus orderConfirm(OrderDetails orderDetails);
	List<Menu> menuDetail();
	List<OrderStatus> orderHistory(User user);
	OrderDetails orderDetails(OrderDetails orderDetails);
	String sendMail(MailingDetails mailingDetails);
	ResponseStatus getotp(String phoneNo);
	CouponTxn applyCouponCode(CouponTxn couponTxn);
	PlanSubscription planSubscription(PlanSubscription planSubscription);
	PlanSubscription updatePlanSubscription(PlanSubscription planSubscription);
	PlanSubscription cancelPlanSubscription(PlanSubscription planSubscription);
	List<ComboDetails> getComboDetails();
	User changePassword(User user);
	List<PlanSubscription> comboDetailsByUser(User user);
	User mobileVerification(User user);
	User emailVerification(User user);
	User updateMobileVerificationStatus(User user);
	boolean cancelOrder(String orderId);
	int paytmWalletResponse(TreeMap<String, String> parameters);
	boolean sendMessage(String orderId);
	boolean sendEmail(String orderId);
	List<Menu> allMenuDetail();
	List<Menu> menu();
	int payuWalletResponse(TreeMap<String, String> parameters);

}
