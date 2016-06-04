package com.hungermeals.facade;

import java.util.List;

import com.hungermeals.persist.ComboDetails;
import com.hungermeals.persist.CouponTxn;
import com.hungermeals.persist.Item;
import com.hungermeals.persist.Menu;
import com.hungermeals.persist.OrderDetails;
import com.hungermeals.persist.PlanSubscription;
import com.hungermeals.persist.User;

public interface AdminFacade {

	List<OrderDetails> orderList(String orderStatus);

	String updateOrderStatus(String orderStatus);

	List<Item> itemListByOrder(String orderId);

	List<PlanSubscription> comboDetailsByUser(User user);

	Menu addMenuDetails(Menu menu);

	Menu addItemDetails(Menu menu);

	Menu updateMenuDetails(Menu menu);

	Menu updateItemDetails(Menu menu);

	Menu deleteItem(String itemId);

	Menu deleteMenu(String menuId);

	Menu alterMenu(Menu menu);

	List<OrderDetails> subscriptionList(String orderStatus);

	CouponTxn alterCoupon(CouponTxn couponDetails);

	List<User> getUserListForNotification();

	List<String> getRegistrationIdForNotification();

	ComboDetails alterComboDetails(ComboDetails comboDetails);

}
