package com.hungermeals.api;

import java.util.List;

import com.hungermeals.persist.Item;
import com.hungermeals.persist.OrderDetails;
import com.hungermeals.persist.PlanSubscription;
import com.hungermeals.persist.User;

public interface AdminAPI {

	List<OrderDetails> orderList(String orderStatus);

	String updateOrderStatus(String orderStatus);

	List<Item> itemListByOrder(String orderId);

	List<PlanSubscription> comboDetailsByUser(User user);

}
