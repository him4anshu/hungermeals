package com.hungermeals.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.hungermeals.facade.AdminFacade;
import com.hungermeals.persist.Item;
import com.hungermeals.persist.OrderDetails;
import com.hungermeals.persist.PlanSubscription;
import com.hungermeals.persist.User;

public class AdminAPIImpl implements AdminAPI{

	@Autowired
	private AdminFacade adminFacade;
	
	@Override
	public List<OrderDetails> orderList(String orderStatus) {
		return adminFacade.orderList(orderStatus);
	}

	@Override
	public String updateOrderStatus(String orderStatus) {
		return adminFacade.updateOrderStatus(orderStatus);

	}

	@Override
	public List<Item> itemListByOrder(String orderId) {
		return adminFacade.itemListByOrder(orderId);

	}

	@Override
	public List<PlanSubscription> comboDetailsByUser(User user) {
		return adminFacade.comboDetailsByUser(user);

	}

}
