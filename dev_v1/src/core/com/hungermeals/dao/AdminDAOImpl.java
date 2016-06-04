package com.hungermeals.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.mail.javamail.JavaMailSender;

import com.hungermeals.common.ConfigReader;
import com.hungermeals.handler.UserDetailsRowMapper;
import com.hungermeals.persist.Address;
import com.hungermeals.persist.ComboDetails;
import com.hungermeals.persist.CouponTxn;
import com.hungermeals.persist.Item;
import com.hungermeals.persist.Menu;
import com.hungermeals.persist.Order;
import com.hungermeals.persist.OrderDetails;
import com.hungermeals.persist.OrderStatus;
import com.hungermeals.persist.PlanSubscription;
import com.hungermeals.persist.ResponseStatus;
import com.hungermeals.persist.User;
import com.hungermeals.persist.UserBean;

public class AdminDAOImpl implements AdminDAO {
	@Autowired
	private JavaMailSender mailSender;
	@Autowired
	private ConfigReader configReader;

	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	private JdbcTemplate jdbcTemplate;
	private SimpleJdbcInsert insertMenu;
	private SimpleJdbcInsert insertItem;
	private SimpleJdbcInsert insertCoupon;
	private SimpleJdbcInsert insertCombo;

	public void setDataSource(DataSource ds) {
		namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(ds);
		jdbcTemplate = new JdbcTemplate(ds);

		insertMenu = new SimpleJdbcInsert(ds).withTableName("menu")
				.usingGeneratedKeyColumns("menu_id");

		insertItem = new SimpleJdbcInsert(ds).withTableName("item")
				.usingGeneratedKeyColumns("item_id");
		
		insertCoupon = new SimpleJdbcInsert(ds).withTableName("coupon_details")
				.usingGeneratedKeyColumns("id");
		
		insertCombo = new SimpleJdbcInsert(ds).withTableName("combo_details")
				.usingGeneratedKeyColumns("combo_id");
	}

	@Override
	public List<OrderDetails> orderList(String orderStatus) {

		char orderStatusIdArray[] = orderStatus.toCharArray();
		String requiredOrderStatus = "";
		for (char c : orderStatusIdArray) {
			requiredOrderStatus = requiredOrderStatus + c + ",";
		}
		requiredOrderStatus = requiredOrderStatus.substring(0,
				requiredOrderStatus.length() - 1);
		MapSqlParameterSource mapSqlParameterSourceAddress = new MapSqlParameterSource();
		String orderDetailsQuery = "SELECT U.FIRST_NAME,OD.USER_ID,OD.ORDER_ID,OD.ADDRESS_ID,OD.TOTAL_AMOUNT,OD.DELIVERY_SLOT,"
				+ " OD.DELIVERY_CHARGES,OD.PAYMENT_MODE,OD.COUPON_CODE,OD.DELIVERY_TIME,"
				+ " U.FIRST_NAME,U.EMAIL,U.PHONE,U.USER_CODE,U.USER_TYPE,"
				+ " UA.NAME,UA.PHONE,UA.LINE_1_BUILDING_NO,UA.LINE_2_STREET_NO,UA.CITY,"
				+ " UA.STATE,UA.PINCODE,OS.ORDER_STATUS_ID,OS.EXECUTIVE_NAME,OS.EXECUTIVE_PHONE "
				+ " FROM order_details OD"
				+ " JOIN user U ON OD.USER_ID=U.USER_ID"
				+ " JOIN user_address UA ON OD.ADDRESS_ID=UA.USER_ADDRESS_ID"
				+ " JOIN order_status OS ON OD.ORDER_ID=OS.ORDER_ID"
				+ " WHERE OS.ORDER_STATUS_ID IN ("
				+ requiredOrderStatus
				+ ")  ORDER BY OD.ORDER_ID DESC";
		System.out.println(orderDetailsQuery);
		List<OrderDetails> orderDetailsList = namedParameterJdbcTemplate.query(
				orderDetailsQuery, mapSqlParameterSourceAddress,
				new RowMapper() {
					@Override
					public Object mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						OrderDetails orderDetails = new OrderDetails();

						/* Getting address data */
						Address address = new Address();
						try {
							address.setAddressId(rs.getInt("ADDRESS_ID"));
							address.setCity(rs.getString("CITY"));
							address.setLine1BuildingNo(rs
									.getString("LINE_1_BUILDING_NO"));
							address.setLine2StreetNo(rs
									.getString("LINE_2_STREET_NO"));
							address.setpCode(rs.getString("PINCODE"));
							address.setPhone(rs.getString("PHONE"));
							address.setState(rs.getString("STATE"));
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

						/* Getting user details */
						User user = new User();
						try {
							user.setFirstName(rs.getString("FIRST_NAME"));
							user.setuName(rs.getString("EMAIL"));
							user.setuCode(rs.getString("USER_CODE"));
							user.setUserId(rs.getInt("USER_ID"));
							user.setAddress(address);
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

						/* Getting Order details */
						Order order = new Order();
						order.setOrderId(rs.getInt("ORDER_ID"));
						order.setCouponCode(rs.getString("COUPON_CODE"));
						order.setDeliveryCharges(rs
								.getDouble("DELIVERY_CHARGES"));
						try {
							order.setDeliveryTime(rs.getDate("DELIVERY_TIME"));
						} catch (Exception e) {
							order.setDeliveryTime(null);
						}
						try {
							order.setDeliverySlot(rs.getString("DELIVERY_SLOT"));
						} catch (Exception e) {
							order.setDeliverySlot(null);
						}
						order.setTotalAmount(rs.getDouble("TOTAL_AMOUNT"));
						order.setPaymentMode(rs.getString("PAYMENT_MODE"));

						/* Getting Order status */
						OrderStatus orderStatus = new OrderStatus();
						try {
							orderStatus.setOrderId(rs.getInt("ORDER_ID"));
							orderStatus.setOrderStatusCode(rs
									.getInt("ORDER_STATUS_ID"));
							orderStatus.setExecutiveName(rs
									.getString("EXECUTIVE_NAME"));
							orderStatus.setExecutivePhone(rs
									.getString("EXECUTIVE_PHONE"));

							orderDetails.setOrderInfo(order);
							orderDetails.setOrderStatus(orderStatus);
							orderDetails.setUser(user);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						return orderDetails;
					}
				});
		return orderDetailsList;
	}

	@Override
	public String updateOrderStatus(String orderStatus) {
		String orders[] = orderStatus.split("@");
		String insertFilter = "UPDATE order_status SET ORDER_STATUS_ID="
				+ orders[0] + " WHERE ORDER_ID=" + orders[1];
		int insertResult = namedParameterJdbcTemplate.update(insertFilter,
				new MapSqlParameterSource());
		if (insertResult == 0)
			return "FAIL";
		else
			return "SUCCESS";
	}

	@Override
	public List<Item> itemListByOrder(String orderId) {
		MapSqlParameterSource mapSqlParameterSourceAddress = new MapSqlParameterSource();
		String orderDetailsQuery = "SELECT * FROM order_item_details OID"
				+ " JOIN item I ON OID.ITEM_ID=I.ITEM_ID"
				+ " WHERE OID.ORDER_ID=" + orderId;
		System.out.println(orderDetailsQuery);

		List<Item> itemList = namedParameterJdbcTemplate.query(
				orderDetailsQuery, mapSqlParameterSourceAddress,
				new RowMapper() {
					@Override
					public Object mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						Item item = new Item();
						item.setDescription(rs.getString("DESCRIPTION"));
						item.setItemName(rs.getString("ITEM_NAME"));
						item.setItemId(rs.getInt("ITEM_ID"));
						item.setNumberOfItems(rs.getInt("NO_OF_ITEM"));
						item.setPerItemCost(rs.getFloat("ITEM_PRICE"));
						return item;
					}
				});
		return itemList;
	}

	@Override
	public OrderStatus getUserByOrderId(String orderId) {
		OrderStatus orderStatus = new OrderStatus();
		ResponseStatus response = new ResponseStatus();
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue("ORDER_ID", orderId);
		int userId = 0;
		String userIdQuery = "SELECT EMAIL,PHONE,TOTAL_AMOUNT,OS.EXECUTIVE_NAME,OS.EXECUTIVE_PHONE FROM user U "
				+ "	JOIN order_details OD ON U.USER_ID=OD.USER_ID AND U.STATUS='A' "
				+ "	JOIN order_status OS ON OD.ORDER_ID=OS.ORDER_ID"
				+ "	WHERE OD.ORDER_ID=:ORDER_ID";
		try {
			orderStatus = (OrderStatus) namedParameterJdbcTemplate
					.queryForObject(userIdQuery, mapSqlParameterSource,
							new RowMapper() {
								@Override
								public Object mapRow(ResultSet rs, int arg1)
										throws SQLException {
									OrderStatus order = new OrderStatus();
									order.setUserPhone(rs.getString("PHONE"));
									order.setTotalAmount(rs
											.getDouble("TOTAL_AMOUNT") + "");
									order.setExecutiveName(rs
											.getString("EXECUTIVE_NAME"));
									order.setExecutivePhone(rs
											.getString("EXECUTIVE_PHONE"));
									return order;
								}
							});
		} catch (Exception e) {
			response.setResponseCode("HM103");
			response.setResponseMessage(configReader.getValue("HM103"));
			response.setErrorDetails(e.getMessage());
		}
		return orderStatus;
	}

	@Override
	public List<PlanSubscription> comboDetailsByUser(User user) {

		List<PlanSubscription> planSubscription = new ArrayList<PlanSubscription>();
		MapSqlParameterSource mapSqlParameterSourceAddress = new MapSqlParameterSource();
		String orderStatusQuery = "SELECT PHONE,LINE_1_BUILDING_NO,LINE_2_STREET_NO,CITY,PINCODE,ADDRESS_ID,"
				+ "COMBO_ID,END_DATE,PAYMENT_MODE,PLAN_COST,PLAN_TYPE,SELECTED_DATE,"
				+ "START_DATE,TIME_SLOT,ID from plan_subscription PS JOIN user_address UA ON PS.ADDRESS_ID=UA.USER_ADDRESS_ID";

		planSubscription = namedParameterJdbcTemplate.query(orderStatusQuery,
				mapSqlParameterSourceAddress, new RowMapper() {
					@Override
					public Object mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						PlanSubscription planSubscription = new PlanSubscription();
						planSubscription.setAddressId(rs.getInt("ADDRESS_ID"));
						planSubscription.setComboId(rs.getInt("COMBO_ID") + "");
						planSubscription.setEndDate(rs.getString("END_DATE"));
						planSubscription.setPaymentMode(rs
								.getString("PAYMENT_MODE"));
						planSubscription.setPlanCost(rs.getInt("PLAN_COST"));
						planSubscription.setPlanSubscribeId(rs.getInt("ID"));
						planSubscription.setPlanType(rs.getString("PLAN_TYPE"));
						planSubscription.setSelectedDate(rs
								.getString("SELECTED_DATE"));
						planSubscription.setStartDate(rs
								.getString("START_DATE"));
						planSubscription.setTimeSlot(rs.getString("TIME_SLOT"));
						Address address = new Address();
						try {
							address.setAddressId(rs.getInt("ADDRESS_ID"));
							address.setCity(rs.getString("CITY"));
							address.setLine1BuildingNo(rs
									.getString("LINE_1_BUILDING_NO"));
							address.setLine2StreetNo(rs
									.getString("LINE_2_STREET_NO"));
							address.setpCode(rs.getString("PINCODE"));
							address.setPhone(rs.getString("PHONE"));
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						planSubscription.setAddress(address);
						ResponseStatus response = new ResponseStatus();
						response.setResponseCode("HM200");
						response.setResponseMessage(configReader
								.getValue("HM200"));
						planSubscription.setResponseStatus(response);
						return planSubscription;
					}
				});
		if (planSubscription.size() == 0) {
			ResponseStatus response = new ResponseStatus();
			response.setResponseCode("HM204");
			response.setResponseMessage(configReader.getValue("HM204"));
			PlanSubscription pp = new PlanSubscription();
			pp.setResponseStatus(response);
		}
		return planSubscription;

	}

	@Override
	public Menu addMenuDetails(Menu menu) {
		ResponseStatus response = new ResponseStatus();
		MapSqlParameterSource newMenuInsert = new MapSqlParameterSource();
		newMenuInsert.addValue("NAME", menu.getName());
		newMenuInsert.addValue("DESCRIPTION", menu.getDescription());
		newMenuInsert.addValue("STATUS", "A");
		newMenuInsert.addValue("DISPLAY_ORDER", menu.getDisplayOrder());
		try {
			int menuId = insertMenu.executeAndReturnKey(newMenuInsert)
					.intValue();
			menu.setMenuId(menuId);
			response.setResponseCode("HM200");
			response.setResponseMessage(configReader.getValue("HM200"));
		} catch (Exception e) {
			e.printStackTrace();
			response.setResponseCode("HM103");
			response.setResponseMessage(configReader.getValue("HM103"));
			response.setErrorDetails(e.getMessage());
		}
		menu.setResponseStatus(response);
		return menu;
	}

	@Override
	public Menu addItemDetails(Menu menu) {
		ResponseStatus response = new ResponseStatus();
		List<Item> itemList = menu.getItemList();
		for (Iterator iterator = itemList.iterator(); iterator.hasNext();) {
			Item item = (Item) iterator.next();
			MapSqlParameterSource newItemInsert = new MapSqlParameterSource();
			newItemInsert.addValue("MENU_ID", menu.getMenuId());
			newItemInsert.addValue("ITEM_NAME", item.getItemName());
			newItemInsert.addValue("ITEM_PRICE", item.getPerItemCost());
			newItemInsert.addValue("DESCRIPTION", item.getDescription());
			newItemInsert.addValue("STATUS", "A");
			newItemInsert.addValue("IMAGE_FILE_NAME", item.getImagePath());
			newItemInsert.addValue("DISPLAY_ORDER", item.getDisplayOrder());

			try {
				int itemId = insertItem.executeAndReturnKey(newItemInsert)
						.intValue();
				item.setItemId(itemId);
				response.setResponseCode("HM200");
				response.setResponseMessage(configReader.getValue("HM200"));
			} catch (Exception e) {
				e.printStackTrace();
				response.setResponseCode("HM103");
				response.setResponseMessage(configReader.getValue("HM103"));
				response.setErrorDetails(e.getMessage());
			}
		}
		menu.setResponseStatus(response);
		return menu;
	}

	@Override
	public Menu updateMenuDetails(Menu menu) {
		ResponseStatus response = new ResponseStatus();
		menu.setResponseStatus(response);
		MapSqlParameterSource updateMenu = new MapSqlParameterSource();
		updateMenu.addValue("MENU_ID", menu.getMenuId());
		updateMenu.addValue("NAME", menu.getName());
		updateMenu.addValue("DESCRIPTION", menu.getDescription());
		updateMenu.addValue("DISPLAY_ORDER", menu.getDisplayOrder());
		//updateMenu.addValue("MODIFIED_DATE", new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()));
		String updateMenuQuery = "UPDATE menu SET NAME=:NAME,DESCRIPTION=:DESCRIPTION,DISPLAY_ORDER=:DISPLAY_ORDER,MODIFIED_DATE=now() WHERE MENU_ID=:MENU_ID";
		int updateResult = 0;
		try {
			updateResult = namedParameterJdbcTemplate.update(updateMenuQuery,
					updateMenu);
			if (updateResult != 0) {
				response.setResponseCode("HM200");
				response.setResponseMessage(configReader.getValue("HM200"));
			} else {
				response.setResponseCode("HM203");
				response.setResponseMessage(configReader.getValue("HM203"));
			}
		} catch (DataAccessException e) {
			e.printStackTrace();
			response.setResponseCode("HM103");
			response.setResponseMessage(configReader.getValue("HM103"));
			response.setErrorDetails(e.getMessage());

		}
		return menu;
	}

	@Override
	public Menu updateItemDetails(Menu menu) {
		ResponseStatus response=new ResponseStatus();
		menu.setResponseStatus(response);
		List<Item> itemList=menu.getItemList();
	for (Iterator iterator = itemList.iterator(); iterator.hasNext();) {
		Item item = (Item) iterator.next();	
		MapSqlParameterSource updateItem=new MapSqlParameterSource();
		updateItem.addValue("MENU_ID", menu.getMenuId());
		updateItem.addValue("ITEM_ID", item.getItemId());
		updateItem.addValue("ITEM_NAME", item.getItemName());
		updateItem.addValue("ITEM_PRICE", item.getPerItemCost());
		updateItem.addValue("DESCRIPTION", item.getDescription());	
		updateItem.addValue("STATUS","A");	
		updateItem.addValue("IMAGE_FILE_NAME", item.getImagePath());
		updateItem.addValue("DISPLAY_ORDER", item.getDisplayOrder());
		//updateItem.addValue("MODIFIED_DATE", new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()));
		String updateItemQuery="";
		if(item.getImagePath()!=null && item.getImagePath().equals("") ){
			updateItemQuery="UPDATE item SET MENU_ID=:MENU_ID,ITEM_NAME=:ITEM_NAME,ITEM_PRICE=:ITEM_PRICE,IMAGE_FILE_NAME=:IMAGE_FILE_NAME,DESCRIPTION=:DESCRIPTION,STATUS=:STATUS,DISPLAY_ORDER=:DISPLAY_ORDER,MODIFIED_DATE=now() WHERE ITEM_ID=:ITEM_ID";
		}else{
			updateItemQuery="UPDATE item SET MENU_ID=:MENU_ID,ITEM_NAME=:ITEM_NAME,ITEM_PRICE=:ITEM_PRICE,DESCRIPTION=:DESCRIPTION,STATUS=:STATUS,DISPLAY_ORDER=:DISPLAY_ORDER,MODIFIED_DATE=now() WHERE ITEM_ID=:ITEM_ID";
		}
		int updateResult=0;
		try {
			updateResult = namedParameterJdbcTemplate.update(updateItemQuery, updateItem);
			if(updateResult!=0){
				response.setResponseCode("HM200");
				response.setResponseMessage(configReader.getValue("HM200"));
			}else{
				response.setResponseCode("HM203");
				response.setResponseMessage(configReader.getValue("HM203"));
			}
		} catch (DataAccessException e1) {
			e1.printStackTrace();
			response.setResponseCode("HM103");
			response.setResponseMessage(configReader.getValue("HM103"));
			response.setErrorDetails(e1.getMessage());

		}
	}
		return menu;

	}

	@Override
	public Menu deleteItem(String itemId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Menu deleteMenu(String menuId) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Menu deleteItems(Menu menu) {
		ResponseStatus response=new ResponseStatus();
		menu.setResponseStatus(response);
		List<Item> itemList=menu.getItemList();
	for (Iterator iterator = itemList.iterator(); iterator.hasNext();) {
		Item item = (Item) iterator.next();	
		MapSqlParameterSource deleteItem=new MapSqlParameterSource();
		deleteItem.addValue("ITEM_ID", item.getItemId());
		String deleteItemQuery="DELETE FROM item WHERE ITEM_ID=:ITEM_ID";	
		int updateResult=0;
		try {
			updateResult = namedParameterJdbcTemplate.update(deleteItemQuery, deleteItem);
			if(updateResult!=0){
				response.setResponseCode("HM200");
				response.setResponseMessage(configReader.getValue("HM200"));
			}else{
				response.setResponseCode("HM203");
				response.setResponseMessage(configReader.getValue("HM203"));
			}
		} catch (DataAccessException e1) {
			e1.printStackTrace();
			response.setResponseCode("HM103");
			response.setResponseMessage(configReader.getValue("HM103"));
			response.setErrorDetails(e1.getMessage());

		}
	}
		return menu;
	}

	public Menu hideItems(Menu menu) {
		ResponseStatus response=new ResponseStatus();
		menu.setResponseStatus(response);
		List<Item> itemList=menu.getItemList();
	for (Iterator iterator = itemList.iterator(); iterator.hasNext();) {
		Item item = (Item) iterator.next();	
		MapSqlParameterSource updateItem=new MapSqlParameterSource();
		updateItem.addValue("ITEM_ID", item.getItemId());	
		updateItem.addValue("STATUS","D");	
		String updateItemQuery="UPDATE item SET STATUS=:STATUS WHERE ITEM_ID=:ITEM_ID";		
		int updateResult=0;
		try {
			updateResult = namedParameterJdbcTemplate.update(updateItemQuery, updateItem);
			if(updateResult!=0){
				response.setResponseCode("HM200");
				response.setResponseMessage(configReader.getValue("HM200"));
			}else{
				response.setResponseCode("HM203");
				response.setResponseMessage(configReader.getValue("HM203"));
			}
		} catch (DataAccessException e1) {
			e1.printStackTrace();
			response.setResponseCode("HM103");
			response.setResponseMessage(configReader.getValue("HM103"));
			response.setErrorDetails(e1.getMessage());

		}
	}
		return menu;
	}
	public Menu hideMenu(Menu menu) {
		ResponseStatus response = new ResponseStatus();
		menu.setResponseStatus(response);
		MapSqlParameterSource updateMenu = new MapSqlParameterSource();
		updateMenu.addValue("MENU_ID", menu.getMenuId());
		String updateMenuQuery = "UPDATE menu SET STATUS='D' WHERE MENU_ID=:MENU_ID";
		int updateResult = 0;
		try {
			updateResult = namedParameterJdbcTemplate.update(updateMenuQuery,
					updateMenu);
			if (updateResult != 0) {
				response.setResponseCode("HM200");
				response.setResponseMessage(configReader.getValue("HM200"));
			} else {
				response.setResponseCode("HM203");
				response.setResponseMessage(configReader.getValue("HM203"));
			}
		} catch (DataAccessException e1) {
			e1.printStackTrace();
			response.setResponseCode("HM103");
			response.setResponseMessage(configReader.getValue("HM103"));
			response.setErrorDetails(e1.getMessage());

		}
		return menu;
	}
	
	@Override
	public Menu alterMenu(Menu menu) {
		if(menu.getOperationType().equalsIgnoreCase("ADDMENU")){
			return addMenuDetails(menu);
		}else if(menu.getOperationType().equalsIgnoreCase("UPDATEMENU")){
			return updateMenuDetails(menu);
		}else if(menu.getOperationType().equalsIgnoreCase("HIDEMENU")){
			return hideMenu(menu);
		}else if(menu.getOperationType().equalsIgnoreCase("ADDITEM")){
			return addItemDetails(menu);
		}else if(menu.getOperationType().equalsIgnoreCase("UPDATEITEM")){
			return updateItemDetails(menu);
		}else if(menu.getOperationType().equalsIgnoreCase("DELETEITEM")){
			return deleteItems(menu);
		}else if(menu.getOperationType().equalsIgnoreCase("HIDEITEM")){
			return hideItems(menu);
		}else{
			return menu;
		}
	
	}

	@Override
	public List<OrderDetails> subscriptionList(String orderStatus) {
		MapSqlParameterSource mapSqlParameterSourceAddress = new MapSqlParameterSource();
		String orderDetailsQuery = "SELECT U.USER_ID,OD.PLAN_TYPE,OD.SELECTED_DATE,OD.PLAN_STATUS,OD.STATUS,OD.CREATION_DATE,"
				+ " OD.MODIFIED_DATE,OD.START_DATE,OD.END_DATE,OD.PLAN_COST,OD.ADDRESS_ID,OD.COMBO_ID,OD.TIME_SLOT,OD.PAYMENT_MODE,"
				+ " U.FIRST_NAME,U.EMAIL,U.PHONE,U.USER_CODE,U.USER_TYPE,"
				+ " UA.NAME,UA.PHONE,UA.LINE_1_BUILDING_NO,UA.LINE_2_STREET_NO,UA.CITY,"
				+ " UA.STATE,UA.PINCODE,CD.COMBO_NAME,CD.COMBO_PRICE "
				+ " FROM plan_subscription OD"
				+ " JOIN user U ON OD.USER_ID=U.USER_ID"
				+ " JOIN user_address UA ON OD.ADDRESS_ID=UA.USER_ADDRESS_ID"
				+ " JOIN combo_details CD on CD.combo_id=OD.combo_id"
				+ " ORDER BY OD.ID DESC";
		System.out.println(orderDetailsQuery);
		List<OrderDetails> orderDetailsList = namedParameterJdbcTemplate.query(
				orderDetailsQuery, mapSqlParameterSourceAddress,
				new RowMapper() {
					@Override
					public Object mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						OrderDetails orderDetails = new OrderDetails();

						/* Getting address data */
						Address address = new Address();
						try {
							address.setAddressId(rs.getInt("ADDRESS_ID"));
							address.setCity(rs.getString("CITY"));
							address.setLine1BuildingNo(rs
									.getString("LINE_1_BUILDING_NO"));
							address.setLine2StreetNo(rs
									.getString("LINE_2_STREET_NO"));
							address.setpCode(rs.getString("PINCODE"));
							address.setPhone(rs.getString("PHONE"));
							address.setState(rs.getString("STATE"));
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

						/* Getting user details */
						User user = new User();
						try {
							user.setFirstName(rs.getString("FIRST_NAME"));
							user.setuName(rs.getString("EMAIL"));
							user.setuCode(rs.getString("USER_CODE"));
							user.setUserId(rs.getInt("USER_ID"));
							user.setAddress(address);
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

						/* Getting Order details */
						PlanSubscription planSubscription=new PlanSubscription();
						planSubscription.setPlanType(rs.getString("PLAN_TYPE"));
						planSubscription.setSelectedDate(rs.getString("SELECTED_DATE"));
						planSubscription.setPlanStatus(rs.getString("PLAN_STATUS"));
						planSubscription.setStartDate(rs.getString("START_DATE"));
						planSubscription.setEndDate(rs.getString("END_DATE"));
						planSubscription.setPlanCost(rs.getInt("PLAN_COST"));
						planSubscription.setAddressId(rs.getInt("ADDRESS_ID"));
						planSubscription.setComboId(rs.getString("COMBO_ID"));
						planSubscription.setTimeSlot(rs.getString("TIME_SLOT"));
						planSubscription.setPaymentMode(rs.getString("PAYMENT_MODE"));
						
						ComboDetails comboDetails=new ComboDetails();
						comboDetails.setComboName(rs.getString("COMBO_NAME"));
						comboDetails.setCost(rs.getInt("COMBO_PRICE"));
						comboDetails.setPlanSubscriptionDetails(planSubscription);
						orderDetails.setComboDetails(comboDetails);
						orderDetails.setUser(user);			
						return orderDetails;
					}
				});
		return orderDetailsList;
	
	}

	@Override
	public CouponTxn alterCoupon(CouponTxn couponDetails) {
		if(couponDetails.getOperationType().equalsIgnoreCase("ADDCOUPON")){
			return addCoupon(couponDetails);
		}else if(couponDetails.getOperationType().equalsIgnoreCase("UPDATECOUPON")){
			return updateCoupon(couponDetails);
		}else if(couponDetails.getOperationType().equalsIgnoreCase("DELETECOUPON")){
			return deleteCoupon(couponDetails);
		}else if(couponDetails.getOperationType().equalsIgnoreCase("GETCOUPON")){
			return getCoupon(couponDetails);
		}else if(couponDetails.getOperationType().equalsIgnoreCase("GETALLCOUPON")){
			return getAllCoupon(couponDetails);
		}else{
			return couponDetails;
		}
	
		}

	private CouponTxn getAllCoupon(CouponTxn couponDetails) {
		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("ID", couponDetails.getCouponId());
		String couponDetailsQuery ="SELECT * FROM coupon_details";
		System.out.println("Coupon validation check query "+couponDetailsQuery);
		CouponTxn cp=new CouponTxn();
		List<CouponTxn> couponTxnList=new ArrayList<CouponTxn>();
		try {
			couponTxnList =namedParameterJdbcTemplate.query(couponDetailsQuery,map, new RowMapper(){
			@Override
			public Object mapRow(ResultSet rs, int arg1)
					throws SQLException {
				CouponTxn couponDetails=new CouponTxn();
				couponDetails.setCouponId(rs.getInt("id")+"");
				couponDetails.setCouponCode(rs.getString("coupon_code"));
				couponDetails.setCouponValue(rs.getString("coupon_value"));
				couponDetails.setCouponType(rs.getString("coupon_type"));
				couponDetails.setCouponValueType(rs.getString("coupon_value_type"));
				couponDetails.setResue_attempt(rs.getInt("reuse_attempt"));
				couponDetails.setCouponType(rs.getString("coupon_type"));
				couponDetails.setMinimumPurchaseAmount(rs.getInt("minimum_purchase_amount"));
				couponDetails.setStatus(rs.getString("status"));
				return couponDetails;
			}
		 });
		} catch (DataAccessException e) {
			cp.setCouponAppliedStatus("Invalid");
			e.printStackTrace();
		}
		cp.setCouponTxnList(couponTxnList);
		return cp;
	}

	private CouponTxn getCoupon(CouponTxn couponDetails) {

		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("ID", couponDetails.getCouponId());
		String couponDetailsQuery ="SELECT * FROM coupon_details WHERE ID=:ID ";
		System.out.println("Coupon validation check query "+couponDetailsQuery);
		CouponTxn cp=new CouponTxn();
		try {
			cp = (CouponTxn) namedParameterJdbcTemplate.queryForObject(couponDetailsQuery,map, new RowMapper(){
			@Override
			public Object mapRow(ResultSet rs, int arg1)
					throws SQLException {
				CouponTxn couponDetails=new CouponTxn();
				couponDetails.setCouponId(rs.getInt("id")+"");
				couponDetails.setCouponCode(rs.getString("coupon_code"));
				couponDetails.setCouponValue(rs.getString("coupon_value"));
				couponDetails.setCouponType(rs.getString("coupon_type"));
				couponDetails.setCouponValueType(rs.getString("coupon_value_type"));
				couponDetails.setResue_attempt(rs.getInt("reuse_attempt"));
				couponDetails.setCouponType(rs.getString("coupon_type"));
				couponDetails.setMinimumPurchaseAmount(rs.getInt("minimum_purchase_amount"));
				couponDetails.setStatus(rs.getString("status"));
				return couponDetails;
			}
		 });
		} catch (DataAccessException e) {
			cp.setCouponAppliedStatus("Invalid");
			e.printStackTrace();
		}	
		return cp;
	
	}

	private CouponTxn deleteCoupon(CouponTxn couponDetails) {
		ResponseStatus response = new ResponseStatus();
		couponDetails.setResponseStatus(response);
		MapSqlParameterSource updateCoupon = new MapSqlParameterSource();
		updateCoupon.addValue("ID", couponDetails.getCouponId());
		String updateCouponQuery = "UPDATE coupon_details SET STATUS='D' WHERE ID=:ID";
		int updateResult = 0;
		try {
			updateResult = namedParameterJdbcTemplate.update(updateCouponQuery,
					updateCoupon);
			if (updateResult != 0) {
				response.setResponseCode("HM200");
				response.setResponseMessage(configReader.getValue("HM200"));
			} else {
				response.setResponseCode("HM203");
				response.setResponseMessage(configReader.getValue("HM203"));
			}
		} catch (DataAccessException e1) {
			e1.printStackTrace();
			response.setResponseCode("HM103");
			response.setResponseMessage(configReader.getValue("HM103"));
			response.setErrorDetails(e1.getMessage());

		}
		return couponDetails;
	
	}

	private CouponTxn updateCoupon(CouponTxn couponDetails) {

		ResponseStatus response = new ResponseStatus();
		couponDetails.setResponseStatus(response);
		MapSqlParameterSource updateCoupon = new MapSqlParameterSource();
		updateCoupon.addValue("coupon_code", couponDetails.getCouponCode());
		updateCoupon.addValue("coupon_value",couponDetails.getCouponValue());
		updateCoupon.addValue("coupon_type", couponDetails.getCouponType());
		updateCoupon.addValue("modified_date", new Date());
		updateCoupon.addValue("status", "A");
		updateCoupon.addValue("reuse_attempt", couponDetails.getResue_attempt());
		updateCoupon.addValue("coupon_value_type", couponDetails.getCouponValueType());
		updateCoupon.addValue("coupon_type", couponDetails.getCouponType());
		updateCoupon.addValue("ID", couponDetails.getCouponId());
		//updateMenu.addValue("MODIFIED_DATE", new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()));
		String updateCouponQuery = "UPDATE coupon_details SET status=:status,coupon_code=:coupon_code,coupon_value=:coupon_value,coupon_type=:coupon_type,modified_date=now(),reuse_attempt=:reuse_attempt,coupon_value_type=:coupon_value_type,coupon_type=:coupon_type WHERE ID=:ID";
		int updateResult = 0;
		try {
			updateResult = namedParameterJdbcTemplate.update(updateCouponQuery,
					updateCoupon);
			if (updateResult != 0) {
				response.setResponseCode("HM200");
				response.setResponseMessage(configReader.getValue("HM200"));
			} else {
				response.setResponseCode("HM203");
				response.setResponseMessage(configReader.getValue("HM203"));
			}
		} catch (DataAccessException e) {
			e.printStackTrace();
			response.setResponseCode("HM103");
			response.setResponseMessage(configReader.getValue("HM103"));
			response.setErrorDetails(e.getMessage());

		}
		return couponDetails;
	
	}

	private CouponTxn addCoupon(CouponTxn couponDetails) {
		ResponseStatus response = new ResponseStatus();
		MapSqlParameterSource newMenuInsert = new MapSqlParameterSource();
		newMenuInsert.addValue("coupon_code", couponDetails.getCouponCode());
		newMenuInsert.addValue("coupon_value",couponDetails.getCouponValue());
		newMenuInsert.addValue("coupon_type", couponDetails.getCouponType());
		newMenuInsert.addValue("creation_date", new Date());
		newMenuInsert.addValue("modified_date", new Date());
		newMenuInsert.addValue("status", "A");
		newMenuInsert.addValue("reuse_attempt", couponDetails.getResue_attempt());
		newMenuInsert.addValue("coupon_value_type", couponDetails.getCouponValueType());
		newMenuInsert.addValue("coupon_type", couponDetails.getCouponType());
		newMenuInsert.addValue("minimum_purchase_amount", couponDetails.getMinimumPurchaseAmount());
	try {
			int couponId = insertCoupon.executeAndReturnKey(newMenuInsert)
					.intValue();
			couponDetails.setCouponId(couponId+"");
			response.setResponseCode("HM200");
			response.setResponseMessage(configReader.getValue("HM200"));
		} catch (Exception e) {
			e.printStackTrace();
			response.setResponseCode("HM103");
			response.setResponseMessage(configReader.getValue("HM103"));
			response.setErrorDetails(e.getMessage());
		}
	couponDetails.setResponseStatus(response);
		return couponDetails;
	
	}

	@Override
	public List<User> getUserListForNotification() {

		List<User> userListDetail=new ArrayList<User>();
		MapSqlParameterSource paramMap=new MapSqlParameterSource();
		String userDetailSql="select * from mobile_user where status='A'";
		try {
			userListDetail=namedParameterJdbcTemplate.query(userDetailSql, new HashMap(), new RowMapper() {

				@Override
				public Object mapRow(ResultSet rs, int arg1)
						throws SQLException {
					User user=new User();
					user.setAppRegistrationId(rs.getString("app_registration_id"));
					user.setDeviceId(rs.getString("device_id"));

					return user;
				}
				
			});
		} catch (DataAccessException e) {
			System.out.println("No results found");
			e.printStackTrace();
		}
		return userListDetail;
	
	}

	@Override
	public List<String> getRegistrationIdForNotification() {

		String userDetailSql="select app_registration_id from mobile_user where status='A'";		
		List<String> regIdList=new ArrayList<String>();
		try {
			regIdList = jdbcTemplate.query(userDetailSql, new RowMapper<String>(){
			    public String mapRow(ResultSet rs, int rowNum) throws SQLException {
			            return rs.getString("app_registration_id");
			    }
			});
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return regIdList;
	}

	@Override
	public ComboDetails alterComboDetails(ComboDetails comboDetails) {

		if(comboDetails.getOperationType().equalsIgnoreCase("ADDCOMBO")){
			return addCombo(comboDetails);
		}else if(comboDetails.getOperationType().equalsIgnoreCase("UPDATECOMBO")){
			return updateCombo(comboDetails);
		}else if(comboDetails.getOperationType().equalsIgnoreCase("DELETECOMBO")){
			return deleteCombo(comboDetails);
		}else if(comboDetails.getOperationType().equalsIgnoreCase("GETCOMBO")){
			return getCombo(comboDetails);
		}else if(comboDetails.getOperationType().equalsIgnoreCase("GETALLCOMBO")){
			return getAllCombo(comboDetails);
		}else{
			return comboDetails;
		}

	}

	private ComboDetails getAllCombo(ComboDetails comboDetails) {
		MapSqlParameterSource map = new MapSqlParameterSource();
		String couponDetailsQuery ="SELECT * FROM combo_details";
		List<ComboDetails> comboListDetails=new ArrayList<ComboDetails>();
		try {
			comboListDetails =namedParameterJdbcTemplate.query(couponDetailsQuery,map, new RowMapper(){
			@Override
			public Object mapRow(ResultSet rs, int arg1)
					throws SQLException {
				ComboDetails comboDetails=new ComboDetails();
				comboDetails.setComboId(rs.getInt("combo_Id"));
				comboDetails.setComboName(rs.getString("combo_name"));
				comboDetails.setCost(rs.getInt("combo_price"));
				comboDetails.setStatus(rs.getString("status"));
				return comboDetails;
			}
		 });
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		comboDetails.setComboList(comboListDetails);
		return comboDetails;
	
	}

	private ComboDetails getCombo(ComboDetails comboDetails) {
		
		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("combo_Id", comboDetails.getComboId());
		String couponDetailsQuery ="SELECT * FROM combo_details WHERE combo_Id=:combo_Id ";
		ComboDetails cp=new ComboDetails();
		try {
			cp = (ComboDetails) namedParameterJdbcTemplate.queryForObject(couponDetailsQuery,map, new RowMapper(){
			@Override
			public Object mapRow(ResultSet rs, int arg1)
					throws SQLException {
				ComboDetails comboDetails=new ComboDetails();
				comboDetails.setComboId(rs.getInt("combo_Id"));
				comboDetails.setComboName(rs.getString("combo_name"));
				comboDetails.setCost(rs.getInt("combo_price"));
				comboDetails.setStatus(rs.getString("status"));
				return comboDetails;
			}
		 });
		} catch (DataAccessException e) {
			e.printStackTrace();
		}	
		return cp;
	
	
	}

	private ComboDetails deleteCombo(ComboDetails comboDetails) {
		ResponseStatus response = new ResponseStatus();
		comboDetails.setResponseStatus(response);
		MapSqlParameterSource updateCoupon = new MapSqlParameterSource();
		updateCoupon.addValue("combo_Id", comboDetails.getComboId());
		String updateCouponQuery = "UPDATE combo_details SET STATUS='D' WHERE combo_Id=:combo_Id";
		int updateResult = 0;
		try {
			updateResult = namedParameterJdbcTemplate.update(updateCouponQuery,
					updateCoupon);
			if (updateResult != 0) {
				response.setResponseCode("HM200");
				response.setResponseMessage(configReader.getValue("HM200"));
			} else {
				response.setResponseCode("HM203");
				response.setResponseMessage(configReader.getValue("HM203"));
			}
		} catch (DataAccessException e1) {
			e1.printStackTrace();
			response.setResponseCode("HM103");
			response.setResponseMessage(configReader.getValue("HM103"));
			response.setErrorDetails(e1.getMessage());
		}
		return comboDetails;
	}

	private ComboDetails updateCombo(ComboDetails comboDetails) {
		ResponseStatus response = new ResponseStatus();
		comboDetails.setResponseStatus(response);
		MapSqlParameterSource updateCoupon = new MapSqlParameterSource();
		updateCoupon.addValue("combo_name", comboDetails.getComboName());
		updateCoupon.addValue("combo_price",comboDetails.getCost());
		updateCoupon.addValue("modified_date", new Date());
		updateCoupon.addValue("status", "A");
		updateCoupon.addValue("combo_Id", comboDetails.getComboId());
		//updateMenu.addValue("MODIFIED_DATE", new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()));
		String updateComboQuery = "UPDATE combo_details SET status=:status,combo_name=:combo_name,combo_price=:combo_price,modified_date=now() WHERE combo_Id=:combo_Id";
		int updateResult = 0;
		try {
			updateResult = namedParameterJdbcTemplate.update(updateComboQuery,
					updateCoupon);
			if (updateResult != 0) {
				response.setResponseCode("HM200");
				response.setResponseMessage(configReader.getValue("HM200"));
			} else {
				response.setResponseCode("HM203");
				response.setResponseMessage(configReader.getValue("HM203"));
			}
		} catch (DataAccessException e) {
			e.printStackTrace();
			response.setResponseCode("HM103");
			response.setResponseMessage(configReader.getValue("HM103"));
			response.setErrorDetails(e.getMessage());

		}
		return comboDetails;
	}

	private ComboDetails addCombo(ComboDetails comboDetails) {

		ResponseStatus response = new ResponseStatus();
		MapSqlParameterSource newComboInsert = new MapSqlParameterSource();
		newComboInsert.addValue("combo_name", comboDetails.getComboName());
		newComboInsert.addValue("combo_price",comboDetails.getCost());
		newComboInsert.addValue("status","A");

	try {
			int comboId = insertCombo.executeAndReturnKey(newComboInsert)
					.intValue();
			comboDetails.setComboId(comboId);
			response.setResponseCode("HM200");
			response.setResponseMessage(configReader.getValue("HM200"));
		} catch (Exception e) {
			e.printStackTrace();
			response.setResponseCode("HM103");
			response.setResponseMessage(configReader.getValue("HM103"));
			response.setErrorDetails(e.getMessage());
		}
	comboDetails.setResponseStatus(response);
		return comboDetails;
	
	
	}
}
