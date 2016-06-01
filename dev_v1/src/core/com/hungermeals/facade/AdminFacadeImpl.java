package com.hungermeals.facade;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.hungermeals.common.ConfigReader;
import com.hungermeals.common.SMSThirdPartyService;
import com.hungermeals.dao.AdminDAO;
import com.hungermeals.persist.CouponTxn;
import com.hungermeals.persist.Item;
import com.hungermeals.persist.Menu;
import com.hungermeals.persist.OrderDetails;
import com.hungermeals.persist.OrderStatus;
import com.hungermeals.persist.PlanSubscription;
import com.hungermeals.persist.User;

public class AdminFacadeImpl implements AdminFacade{

	@Autowired
	private AdminDAO adminDAO;
	
	@Autowired
	private SMSThirdPartyService smsThirdPartyService;
	
	@Autowired
	private ConfigReader configReader;
	
	@Override
	public List<OrderDetails> orderList(String orderStatus) {
		return adminDAO.orderList(orderStatus);
	}
	@Override
	public String updateOrderStatus(String orderStatus) {
		final String os[]=orderStatus.split("@");
		new Thread(){
			@Override
			public void run() {
				Map<String,String> orderDependentParameters=new HashMap<String, String>();
				OrderStatus orderDetails=adminDAO.getUserByOrderId(os[1]);
				String templateId="";
				if(os[0].equals("2")){
					templateId=configReader.getValue("sms.orderconfirmed");
					orderDependentParameters.put("F1", os[1]);
					orderDependentParameters.put("F2", configReader.getValue("admin.phone1"));
				}else if(os[0].equals("3")){

				}else if(os[0].equals("4")){
					templateId=configReader.getValue("sms.outfordelivery");
					orderDependentParameters.put("F1", os[1]);
					if(orderDetails.getExecutiveName()==null || orderDetails.getExecutiveName().equals("null")){
						orderDependentParameters.put("F2", "");
					}else{
						orderDependentParameters.put("F2", orderDetails.getExecutiveName());
					}
					orderDependentParameters.put("F3", orderDetails.getTotalAmount());
				}else if(os[0].equals("5")){
					templateId=configReader.getValue("sms.delivered");
					orderDependentParameters.put("F1", os[1]);
				}
				SMSThirdPartyService sms=new SMSThirdPartyService();
				String phone=orderDetails.getUserPhone();
				try {
					sms.sendOrderConfermation(new Long(phone), new Long(templateId), orderDependentParameters);
				} catch (NumberFormatException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			
			}
		}.start();
		return adminDAO.updateOrderStatus(orderStatus);

	}
	@Override
	public List<Item> itemListByOrder(String orderId) {
		return adminDAO.itemListByOrder(orderId);

	}
	@Override
	public List<PlanSubscription> comboDetailsByUser(User user) {
		return adminDAO.comboDetailsByUser(user);

	}
	@Override
	public Menu addMenuDetails(Menu menu) {
		return adminDAO.addMenuDetails(menu);

	}
	@Override
	public Menu addItemDetails(Menu menu) {
		return adminDAO.addItemDetails(menu);

	}
	@Override
	public Menu updateMenuDetails(Menu menu) {
		return adminDAO.updateMenuDetails(menu);

	}
	@Override
	public Menu updateItemDetails(Menu menu) {
		return adminDAO.updateItemDetails(menu);

	}
	@Override
	public Menu deleteItem(String itemId) {
		return adminDAO.deleteItem(itemId);

	}
	@Override
	public Menu deleteMenu(String menuId) {
		return adminDAO.deleteMenu(menuId);

	}
	@Override
	public Menu alterMenu(Menu menu) {
		return adminDAO.alterMenu(menu);

	}
	@Override
	public List<OrderDetails> subscriptionList(String orderStatus) {
		return adminDAO.subscriptionList(orderStatus);

	}
	@Override
	public CouponTxn alterCoupon(CouponTxn couponDetails) {
		return adminDAO.alterCoupon(couponDetails);

	}
	@Override
	public List<User> getUserListForNotification() {
		return adminDAO.getUserListForNotification();

	}
	@Override
	public List<String> getRegistrationIdForNotification() {
		return adminDAO.getRegistrationIdForNotification();

	}

}
