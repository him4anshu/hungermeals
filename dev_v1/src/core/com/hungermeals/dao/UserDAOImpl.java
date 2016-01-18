package com.hungermeals.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import com.hungermeals.persist.Address;
import com.hungermeals.persist.Item;
import com.hungermeals.persist.Menu;
import com.hungermeals.persist.OrderDetails;
import com.hungermeals.persist.OrderStatus;
import com.hungermeals.persist.User;
import com.hungermeals.persist.User1;


public class UserDAOImpl implements UserDAO{
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	private JdbcTemplate jdbcTemplate;
	private SimpleJdbcInsert insertIntoUserTable;
	private SimpleJdbcInsert insertIntoAddressTable;
	private SimpleJdbcInsert insertIntoOrderTable;
	private SimpleJdbcInsert insertIntoOrderItemTable;



	public void setDataSource(DataSource ds) {
		namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(ds);
		new JdbcTemplate(ds); 
		jdbcTemplate=new JdbcTemplate(ds);
		
		insertIntoUserTable=new SimpleJdbcInsert(ds)
		.withTableName("user")									  
		.usingGeneratedKeyColumns("user_id");
		
		insertIntoAddressTable=new SimpleJdbcInsert(ds)
		.withTableName("user_address")									  
		.usingGeneratedKeyColumns("user_address_id");
		
		insertIntoOrderTable=new SimpleJdbcInsert(ds)
		.withTableName("order_details")									  
		.usingGeneratedKeyColumns("order_id");
		
		insertIntoOrderItemTable=new SimpleJdbcInsert(ds)
		.withTableName("order_item_details")									  
		.usingGeneratedKeyColumns("oidid");
	}

	@Override
	public User userRegistration(User user) {
			//Checking already registered email
			MapSqlParameterSource paramMap=new MapSqlParameterSource();
			paramMap.addValue("EMAIL",user.getEmail());	
			String trackCountSql="SELECT count(*) FROM user WHERE EMAIL=:EMAIL";
			int tCount = 0;
			try {
				tCount=namedParameterJdbcTemplate.queryForInt(trackCountSql, paramMap);
			} catch (DataAccessException e) {
				e.printStackTrace();
				user.setUserStatus(false);
			}
		
			//If user count is zero with given email ,start registration
			if(tCount==0){
				MapSqlParameterSource newUserInsert=new MapSqlParameterSource();
				newUserInsert.addValue("FIRST_NAME", user.getuName());
				newUserInsert.addValue("LAST_NAME", user.getLastName());
				newUserInsert.addValue("EMAIL", user.getEmail());
				newUserInsert.addValue("ENC_EMAIL", user.getEncEmail());
				newUserInsert.addValue("ENC_PASSWORD", user.getPassword1());
				newUserInsert.addValue("STATUS", "A");
				String ucode=UUID.randomUUID().toString();
				newUserInsert.addValue("USER_CODE", ucode);			
				try {
					int userId=insertIntoUserTable.executeAndReturnKey(newUserInsert).intValue();
					user.setUserId(userId);
					user.setLogTime(new Date());
					user.setuCode(ucode);
					user.setuName(user.getEmail());
					user.setUserStatus(true);
					}catch (Exception e) {
						user.setUserStatus(false);
						e.printStackTrace();
					}			
			}else{
				user.setUserStatus(false);
			}
	  return user;
	}

	@Override
	public User1 alreadyRegisteredCheck(String email) {
		User1 user = new User1();
		user.setUserEmail(email);
		MapSqlParameterSource paramMap=new MapSqlParameterSource();
		paramMap.addValue("EMAIL",email);
		
		String trackCountSql="SELECT count(*) FROM user WHERE EMAIL=:EMAIL";
		int tCount = 0;
		try {
			tCount=namedParameterJdbcTemplate.queryForInt(trackCountSql, paramMap);
		} catch (DataAccessException e) {
			e.printStackTrace();
			return new User1();
		}
		if(tCount==0){
			user.setAlreadyRegistered(false);
		}else{
			user.setAlreadyRegistered(true);
		}
		return user;
	}
	
	@Override
	public User logedInUserProfile(User user) {
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue("EMAIL", user.getEmail());
		mapSqlParameterSource.addValue("E_PASSWORD", user.getPassword1());
		String userIdQuery = "SELECT USER_ID,FIRST_NAME,USER_CODE,EMAIL FROM user WHERE EMAIL=:EMAIL AND ENC_PASSWORD=:E_PASSWORD AND STATUS='A'";
		try {
			user=(User) namedParameterJdbcTemplate.queryForObject(userIdQuery,mapSqlParameterSource, new RowMapper(){
				@Override
				public Object mapRow(ResultSet rs, int arg1)
						throws SQLException {
					User user = new User();
					user.setUserId(rs.getInt("USER_ID"));
					user.setUserStatus(true);
					user.setLogTime(new Date());
					user.setuCode(rs.getString("USER_CODE"));
					user.setuName(rs.getString("EMAIL"));
					return user;
				}
			});
		} catch (DataAccessException e) {
			e.printStackTrace();
			user.setUserStatus(false);
		}
		return user;
	
	}

	@Override
	public User addUserAddress(User user) {
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue("EMAIL", user.getuName());
		mapSqlParameterSource.addValue("USER_CODE", user.getuCode());
		String userIdQuery = "SELECT USER_ID FROM user WHERE EMAIL=:EMAIL AND USER_CODE=:USER_CODE AND STATUS='A'";
		try {
			int userId= namedParameterJdbcTemplate.queryForInt(userIdQuery,mapSqlParameterSource);
			if(userId!=0){
				Address address=user.getAddress();
				MapSqlParameterSource newAddressInsert=new MapSqlParameterSource();
				newAddressInsert.addValue("USER_ID", userId);
				newAddressInsert.addValue("NAME", address.getName());
				newAddressInsert.addValue("LINE_1_BUILDING_NO", address.getLine1BuildingNo());
				newAddressInsert.addValue("LINE_2_STREET_NO", address.getLine2StreetNo());
				newAddressInsert.addValue("PHONE", address.getPhone());
				newAddressInsert.addValue("CITY", address.getCity());
				newAddressInsert.addValue("PINCODE", address.getpCode());	
				newAddressInsert.addValue("STATUS", "A");
				try {
					int addId=insertIntoAddressTable.executeAndReturnKey(newAddressInsert).intValue();
					address.setAddressId(addId);
					user.setAddress(address);
					user.setUserStatus(true);
					}catch (Exception e) {
						user.setUserStatus(false);
						e.printStackTrace();
					}
			}
		} catch (DataAccessException e) {
			e.printStackTrace();
			user.setUserStatus(false);
		}
		return user;
	}

	@Override
	public List<Address> getUserAddress(User user) {
		List<Address> addressList=new ArrayList<Address>();
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue("EMAIL", user.getuName());
		mapSqlParameterSource.addValue("USER_CODE", user.getuCode());
		String userIdQuery = "SELECT USER_ID FROM user WHERE EMAIL=:EMAIL AND USER_CODE=:USER_CODE AND STATUS='A' ";
		try {
			int userId= namedParameterJdbcTemplate.queryForInt(userIdQuery,mapSqlParameterSource);
			MapSqlParameterSource mapSqlParameterSourceAddress = new MapSqlParameterSource();
			mapSqlParameterSourceAddress.addValue("USER_ID", userId);
			String addressListQuery = "SELECT * FROM user_address WHERE USER_ID=:USER_ID AND STATUS='A' ORDER BY USER_ADDRESS_ID DESC LIMIT 2";
			addressList=namedParameterJdbcTemplate.query(addressListQuery,mapSqlParameterSourceAddress, new RowMapper() {
						@Override
						public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
							Address address =new Address();
							address.setAddressId(rs.getInt("USER_ADDRESS_ID"));
							address.setName(rs.getString("NAME"));
							address.setPhone(rs.getString("PHONE"));
							address.setpCode(rs.getString("PINCODE"));
							address.setLine1BuildingNo(rs.getString("LINE_1_BUILDING_NO"));
							address.setLine2StreetNo(rs.getString("LINE_2_STREET_NO"));
							address.setCity(rs.getString("CITY"));
							return address;
				}
			});
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return addressList;
	}

	@Override
	public OrderStatus orderConfirm(OrderDetails orderDetails) {
		OrderStatus orderStatus=new  OrderStatus();
		int orderId=0;
		int userId=getUserIdByUserCode(orderDetails.getUser());
		int addressId=orderDetails.getUser().getAddress().getAddressId();

		MapSqlParameterSource newOrderInsert=new MapSqlParameterSource();
		newOrderInsert.addValue("USER_ID", userId);
		newOrderInsert.addValue("ADDRESS_ID", addressId);
		newOrderInsert.addValue("DELIVERY_CHARGES", orderDetails.getOrderInfo().getDeliveryCharges());
		newOrderInsert.addValue("TOTAL_AMOUNT", orderDetails.getOrderInfo().getTotalAmount());
		//newOrderInsert.addValue("DELIVERY_TIME", orderDetails.getOrderInfo().getDeliveryTime());
		newOrderInsert.addValue("STATUS", "A");
		newOrderInsert.addValue("CREATION_DATE", new Date());//"2016-01-12 21:50:24");
		newOrderInsert.addValue("MODIFIED_DATE", new Date());	
		newOrderInsert.addValue("PAYMENT_MODE", orderDetails.getOrderInfo().getPaymentMode());
		newOrderInsert.addValue("COUPON_CODE", orderDetails.getOrderInfo().getCouponCode());

		try{
			orderId=insertIntoOrderTable.executeAndReturnKey(newOrderInsert).intValue();
			orderStatus.setOrderStatus("Order placed");
			}catch (Exception e) {
				orderStatus.setOrderStatus("Order couldn't able to place ");
				e.printStackTrace();
			}			
		List<Item> itemList=orderDetails.getItemList();
		for (Item item : itemList) {
			MapSqlParameterSource newItemInsert=new MapSqlParameterSource();
			newItemInsert.addValue("ITEM_ID", item.getItemId());
			newItemInsert.addValue("NO_OF_ITEM", item.getNumberOfItems());
			newItemInsert.addValue("MODIFIED_DATE", new Date());
			newItemInsert.addValue("ORDER_ID", orderId);
			newItemInsert.addValue("STATUS", "A");
			try{
				insertIntoOrderItemTable.executeAndReturnKey(newItemInsert).intValue();			
			}catch (Exception e) {
				orderStatus.setOrderStatus("Order couldn't able to place ");
				e.printStackTrace();
			}

		}	
		orderStatus.setOrderId(orderId);
		orderStatus.setExecutiveName("MK Jacob");
		orderStatus.setExecutivePhone("8123719594");
		return orderStatus;
	}

	@Override
	public List<Menu> menuDetail() {
		List<Menu> menuList=new ArrayList<Menu>();
		final List<Item> itemList=new ArrayList<Item>();
		final Map<Menu, List<Item>> menuMapList=new HashMap<Menu, List<Item>>();
		final Menu menu=new Menu();
		MapSqlParameterSource mapSqlParameterSourceAddress = new MapSqlParameterSource();
		String addressListQuery = "SELECT M.MENU_ID AS M_ID,NAME AS MENU_NAME,M.DESCRIPTION AS MENU_DESCRIPTION,ITEM_ID,ITEM_NAME,"
				+ " ITEM_PRICE,I.DESCRIPTION AS ITEM_DESCRIPTION FROM menu M"
				+"  JOIN item I ON M.MENU_ID=I.MENU_ID AND M.STATUS='A' AND I.STATUS='A';";
		namedParameterJdbcTemplate.query(addressListQuery,mapSqlParameterSourceAddress, new RowMapper() {
					@Override
					public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
						
						/*Menu Details*/
						Menu menu =new Menu();
						menu.setMenuId(rs.getLong("M_ID"));
						menu.setName(rs.getString("MENU_NAME"));
						menu.setDescription(rs.getString("MENU_DESCRIPTION"));
						
						/*Item Details*/
						Item item=new Item();
						item.setItemId(rs.getInt("ITEM_ID"));
						item.setItemName(rs.getString("ITEM_NAME"));
						item.setPerItemCost(rs.getInt("ITEM_PRICE"));
						item.setDescription(rs.getString("ITEM_DESCRIPTION"));
						
						if(menuMapList.containsKey(menu)){							
							List<Item> itemList=menuMapList.get(menu);
							itemList.add(item);
							menuMapList.put(menu, itemList);
						}else{							
							List<Item> itemList=new ArrayList<Item>();
							itemList.add(item);
							menuMapList.put(menu, itemList);
						}
						
						return menu;
			}
		});
		
		 Iterator it = menuMapList.entrySet().iterator();
		    while (it.hasNext()) {
		        Map.Entry pair = (Map.Entry)it.next();
		        System.out.println(pair.getKey() + " = " + pair.getValue());
		        Menu m=(Menu)pair.getKey();
		        m.setItemList((List<Item>)pair.getValue());
		        menuList.add(m);

		    }		
		return menuList;
	}

	private int getUserIdByUserCode(User user){
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue("EMAIL", user.getuName());
		mapSqlParameterSource.addValue("USER_CODE", user.getuCode());
		int userId=0;
		String userIdQuery = "SELECT USER_ID FROM user WHERE EMAIL=:EMAIL AND USER_CODE=:USER_CODE AND STATUS='A' ";
		try {
			userId= namedParameterJdbcTemplate.queryForInt(userIdQuery,mapSqlParameterSource);
		}catch(Exception e){
			e.printStackTrace();
		}
		return userId;
	}
	
}
