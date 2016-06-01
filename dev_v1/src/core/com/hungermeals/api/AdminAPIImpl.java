package com.hungermeals.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.hungermeals.facade.AdminFacade;
import com.hungermeals.persist.CouponTxn;
import com.hungermeals.persist.Item;
import com.hungermeals.persist.Menu;
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

	@Override
	public Menu addMenuDetails(Menu menu) {
		return adminFacade.addMenuDetails(menu);

	}

	@Override
	public Menu addItemDetails(Menu menu) {
		return adminFacade.addItemDetails(menu);

	}

	@Override
	public Menu updateMenuDetails(Menu menu) {
		return adminFacade.updateMenuDetails(menu);

	}

	@Override
	public Menu updateItemDetails(Menu menu) {
		return adminFacade.updateItemDetails(menu);

	}

	@Override
	public Menu deleteItem(String itemId) {
		return adminFacade.deleteItem(itemId);

	}

	@Override
	public Menu deleteMenu(String menuId) {
		return adminFacade.deleteMenu(menuId);

	}

	@Override
	public Menu alterMenu(Menu menu) {
		return adminFacade.alterMenu(menu);

	}

	@Override
	public List<OrderDetails> subscriptionList(String orderStatus) {
		return adminFacade.subscriptionList(orderStatus);

	}

	@Override
	public CouponTxn alterCoupon(CouponTxn couponDetails) {
		return adminFacade.alterCoupon(couponDetails);

	}

	@Override
	public List<User> getUserListForNotification() {
		return adminFacade.getUserListForNotification();

	}

	@Override
	public List<String> getRegistrationIdForNotification() {
		return adminFacade.getRegistrationIdForNotification();

	}

}
