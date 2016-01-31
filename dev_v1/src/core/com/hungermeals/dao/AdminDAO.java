package com.hungermeals.dao;

import java.util.List;

import com.hungermeals.persist.Item;
import com.hungermeals.persist.OrderDetails;
import com.hungermeals.persist.OrderStatus;
import com.hungermeals.persist.User;

public interface AdminDAO {

	List<OrderDetails> orderList(String orderStatus);

	String updateOrderStatus(String orderStatus);

	List<Item> itemListByOrder(String orderId);

	OrderStatus getUserByOrderId(String orderId);

}
