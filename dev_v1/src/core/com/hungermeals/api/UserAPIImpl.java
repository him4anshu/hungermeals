package com.hungermeals.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.hungermeals.facade.UserFacade;
import com.hungermeals.persist.Address;
import com.hungermeals.persist.Menu;
import com.hungermeals.persist.OrderDetails;
import com.hungermeals.persist.OrderStatus;
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

	

	

}
