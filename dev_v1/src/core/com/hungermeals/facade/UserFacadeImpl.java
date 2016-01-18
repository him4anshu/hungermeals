package com.hungermeals.facade;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.hungermeals.common.ConfigReader;
import com.hungermeals.common.SMSThirdPartyService;
import com.hungermeals.common.StringEncrypterService;
import com.hungermeals.dao.UserDAO;
import com.hungermeals.persist.Address;
import com.hungermeals.persist.Menu;
import com.hungermeals.persist.OrderDetails;
import com.hungermeals.persist.OrderStatus;
import com.hungermeals.persist.User;
import com.hungermeals.persist.User1;

public class UserFacadeImpl implements UserFacade{

	@Autowired
	private UserDAO userDAO;
	@Override
	public User logedInUserProfile(User user) {
		// TODO Auto-generated method stub
		String encPassword= StringEncrypterService.encryptString(user.getPassword1());
		user.setPassword1(encPassword);
		return userDAO.logedInUserProfile(user);
	}
	@Override
	public User userRegistration(User user) {
		String encPassword= StringEncrypterService.encryptString(user.getPassword1());
		user.setPassword1(encPassword);
		
		String encEmail= StringEncrypterService.encryptString(user.getEmail());
		user.setEncEmail(encEmail);
		return userDAO.userRegistration(user);
	}
	@Override
	public User1 alreadyRegisteredCheck(String email) {
		// TODO Auto-generated method stub
		return userDAO.alreadyRegisteredCheck(email);
	}
	@Override
	public List<Address> getUserAddress(User user) {
		return userDAO.getUserAddress(user);
	}
	@Override
	public User addUserAddress(User user) {
		return userDAO.addUserAddress(user);
	}
	@Override
	public OrderStatus orderConfirm(OrderDetails orderDetails) {
		OrderStatus orderStatus = userDAO.orderConfirm(orderDetails);
		if("Order placed".equals(orderStatus.getOrderStatus())){
			ConfigReader cfg=new ConfigReader();
			String templateId=cfg.getValue("sms.orderconfirmtemplateid");
			SMSThirdPartyService sms=new SMSThirdPartyService();
			String phone=orderDetails.getUser().getAddress().getPhone();
			Map<String,String> orderDependentParameters=new HashMap<String, String>();
			orderDependentParameters.put("F1", orderDetails.getOrderInfo().getOrderId()+"");
			orderDependentParameters.put("F2", orderDetails.getOrderInfo().getTotalAmount()+"");
			orderDependentParameters.put("F3", orderStatus.getExecutiveName()+" (+91 "+orderStatus.getExecutivePhone()+" )");		
			try {
				sms.sendOrderConfermation(new Long(phone), new Long(templateId), orderDependentParameters);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return orderStatus;
	}
	@Override
	public List<Menu> menuDetail() {
		return userDAO.menuDetail();
	}
	
		
}
