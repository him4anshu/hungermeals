package com.hungermeals.dao;

import java.util.List;

import com.hungermeals.persist.Address;
import com.hungermeals.persist.MailingDetails;
import com.hungermeals.persist.Menu;
import com.hungermeals.persist.OrderDetails;
import com.hungermeals.persist.OrderStatus;
import com.hungermeals.persist.User;
import com.hungermeals.persist.User1;

public interface UserDAO {

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

	String getotp(String phoneNo,String encryptedOtp) throws Exception;

}
