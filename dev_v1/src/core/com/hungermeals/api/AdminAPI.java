package com.hungermeals.api;

import java.util.List;

import com.hungermeals.persist.Item;
import com.hungermeals.persist.OrderDetails;

public interface AdminAPI {

	List<OrderDetails> orderList(String orderStatus);

	String updateOrderStatus(String orderStatus);

	List<Item> itemListByOrder(String orderId);

}
