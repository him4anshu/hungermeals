package com.hungermeals.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import com.hungermeals.persist.Address;
import com.hungermeals.persist.Item;
import com.hungermeals.persist.Menu;
import com.hungermeals.persist.Order;
import com.hungermeals.persist.OrderDetails;
import com.hungermeals.persist.OrderStatus;
import com.hungermeals.persist.PlanSubscription;
import com.hungermeals.persist.ResponseStatus;
import com.hungermeals.persist.User;

public class AdminDAOImpl implements AdminDAO {
	@Autowired
	private JavaMailSender mailSender;
	@Autowired
	private ConfigReader configReader;

	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	private JdbcTemplate jdbcTemplate;
	private SimpleJdbcInsert insertMenu;
	private SimpleJdbcInsert insertItem;

	public void setDataSource(DataSource ds) {
		namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(ds);
		jdbcTemplate = new JdbcTemplate(ds);

		insertMenu = new SimpleJdbcInsert(ds).withTableName("menu")
				.usingGeneratedKeyColumns("menu_id");

		insertItem = new SimpleJdbcInsert(ds).withTableName("item")
				.usingGeneratedKeyColumns("item_id");
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
		String orderDetailsQuery = "SELECT OD.USER_ID,OD.ORDER_ID,OD.ADDRESS_ID,OD.TOTAL_AMOUNT,OD.DELIVERY_SLOT,"
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
}
