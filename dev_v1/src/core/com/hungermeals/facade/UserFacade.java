package com.hungermeals.facade;

import java.util.List;

import com.hungermeals.persist.Address;
import com.hungermeals.persist.Menu;
import com.hungermeals.persist.OrderDetails;
import com.hungermeals.persist.OrderStatus;
import com.hungermeals.persist.User;
import com.hungermeals.persist.User1;

public interface UserFacade {

	User logedInUserProfile(User user);

	User userRegistration(User user);

	User1 alreadyRegisteredCheck(String email);

	List<Address> getUserAddress(User user);

	User addUserAddress(User user);

	OrderStatus orderConfirm(OrderDetails orderDetails);

	List<Menu> menuDetail();

}
