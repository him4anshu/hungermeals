package com.hungermeals.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import javax.mail.Multipart;
import javax.mail.internet.MimeMessage;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;

import com.hungermeals.common.ConfigReader;
import com.hungermeals.common.StringEncrypterService;
import com.hungermeals.common.TemplateProcessing;
import com.hungermeals.handler.TemplateDetailsRowMapper;
import com.hungermeals.handler.UserDetailsRowMapper;
import com.hungermeals.persist.Address;
import com.hungermeals.persist.ComboDetails;
import com.hungermeals.persist.CouponTxn;
import com.hungermeals.persist.Item;
import com.hungermeals.persist.MailerBean;
import com.hungermeals.persist.MailingDetails;
import com.hungermeals.persist.Menu;
import com.hungermeals.persist.Order;
import com.hungermeals.persist.OrderDetails;
import com.hungermeals.persist.OrderStatus;
import com.hungermeals.persist.PlanSubscription;
import com.hungermeals.persist.ResponseStatus;
import com.hungermeals.persist.TemplateBean;
import com.hungermeals.persist.User;
import com.hungermeals.persist.User1;
import com.hungermeals.persist.UserBean;




public class UserDAOImpl implements UserDAO{
	@Autowired
	private JavaMailSender mailSender;
	@Autowired
	private ConfigReader configReader;
	
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	private JdbcTemplate jdbcTemplate;
	private SimpleJdbcInsert insertIntoUserTable;
	private SimpleJdbcInsert insertIntoAddressTable;
	private SimpleJdbcInsert insertIntoOrderTable;
	private SimpleJdbcInsert insertIntoOrderItemTable;
	private SimpleJdbcInsert insertIntoUserCouponMapping;
	private SimpleJdbcInsert insertPlanSubscription;



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
		
		insertIntoUserCouponMapping=new SimpleJdbcInsert(ds)
		.withTableName("user_coupon_mapping")									  
		.usingGeneratedKeyColumns("id");
		
		insertPlanSubscription=new SimpleJdbcInsert(ds)
		.withTableName("plan_subscription")									  
		.usingGeneratedKeyColumns("id");
		
	}

	@Override
	public User userRegistration(User user) {
			//Checking already registered email
			ResponseStatus response=new ResponseStatus();
			user.setResponseStatus(response);
			MapSqlParameterSource paramMap=new MapSqlParameterSource();
			paramMap.addValue("EMAIL",user.getEmail());	
			String trackCountSql="SELECT count(*) FROM user WHERE EMAIL=:EMAIL OR PHONE=:PHONE";
			int tCount = 0;
			try {
				tCount=namedParameterJdbcTemplate.queryForInt(trackCountSql, paramMap);
			} catch (DataAccessException e) {
				e.printStackTrace();
				response.setResponseCode("HM103");
				response.setResponseMessage(configReader.getValue("HM103"));
				response.setErrorDetails(e.getMessage());
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
				newUserInsert.addValue("PHONE", user.getMobile());
				newUserInsert.addValue("STATUS", "A");
				newUserInsert.addValue("USER_TYPE", user.getUserType());
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
						response.setResponseCode("HM103");
						response.setResponseMessage(configReader.getValue("HM103"));
						response.setErrorDetails(e.getMessage());
					}			
			}else if("HM".equals(user.getUserType())){
				response.setResponseCode("HM201");
				response.setResponseMessage(configReader.getValue("HM201"));
				user.setUserStatus(false); //Already registered
			}else{//i.e user already came from FB or GMAIL
				response.setResponseCode("HM202");
				response.setResponseMessage(configReader.getValue("HM202"));
				MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
				mapSqlParameterSource.addValue("EMAIL", user.getEmail());
				String userIdQuery = "SELECT USER_ID,FIRST_NAME,USER_CODE,EMAIL FROM user WHERE EMAIL=:EMAIL  AND STATUS='A'";
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
					response.setResponseCode("HM103");
					response.setResponseMessage(configReader.getValue("HM103"));
					response.setErrorDetails(e.getMessage());
					user.setUserStatus(false);
				}
				return user;
			
			
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
		ResponseStatus response=new ResponseStatus();
		user.setResponseStatus(response);
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue("EMAIL", user.getEmail());
		mapSqlParameterSource.addValue("E_PASSWORD", user.getPassword1());
		String userIdQuery = "SELECT USER_ID,FIRST_NAME,USER_CODE,EMAIL,PHONE FROM user WHERE (EMAIL=:EMAIL OR PHONE=:EMAIL) AND (ENC_PASSWORD=:E_PASSWORD OR OTP=:E_PASSWORD) AND STATUS='A'";
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
					user.setMobile(rs.getString("PHONE"));
					user.setMobileVerified(rs.getBoolean("MOBILE_VERIFICATION"));
					return user;
				}
			});
		} catch (DataAccessException e) {
			e.printStackTrace();
			response.setResponseCode("HM103");
			response.setResponseMessage(configReader.getValue("HM103"));
			response.setErrorDetails(e.getMessage());
			user.setUserStatus(false);
		}
		return user;
	
	}

	@Override
	public User addUserAddress(User user) {
		ResponseStatus response=new ResponseStatus();
		user.setResponseStatus(response);
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
						e.printStackTrace();
						response.setResponseCode("HM103");
						response.setResponseMessage(configReader.getValue("HM103"));
						response.setErrorDetails(e.getMessage());
						user.setUserStatus(false);
					}
			}
		} catch (DataAccessException e) {
			e.printStackTrace();
			response.setResponseCode("HM103");
			response.setResponseMessage(configReader.getValue("HM103"));
			response.setErrorDetails(e.getMessage());
			user.setUserStatus(false);
		}
		return user;
	}

	@Override
	public List<Address> getUserAddress(User user) {
		ResponseStatus response=new ResponseStatus();
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
			response.setResponseCode("HM103");
			response.setResponseMessage(configReader.getValue("HM103"));
			response.setErrorDetails(e.getMessage());
		}
		return addressList;
	}

	@Override
	public OrderStatus orderConfirm(OrderDetails orderDetails) {
		ResponseStatus response=new ResponseStatus();
		OrderStatus orderStatus=new  OrderStatus();
		int orderId=0;
		int userId=getUserIdByUserCode(orderDetails.getUser());
		int addressId=orderDetails.getUser().getAddress().getAddressId();

		MapSqlParameterSource newOrderInsert=new MapSqlParameterSource();
		newOrderInsert.addValue("USER_ID", userId);
		newOrderInsert.addValue("ADDRESS_ID", addressId);
		newOrderInsert.addValue("DELIVERY_CHARGES", orderDetails.getOrderInfo().getDeliveryCharges());
		newOrderInsert.addValue("TOTAL_AMOUNT", orderDetails.getOrderInfo().getTotalAmount());
		newOrderInsert.addValue("DELIVERY_TIME", orderDetails.getOrderInfo().getDeliveryTime());
		newOrderInsert.addValue("DELIVERY_SLOT", orderDetails.getOrderInfo().getDeliverySlot());
		newOrderInsert.addValue("STATUS", "A");
		newOrderInsert.addValue("CREATION_DATE", new Date());//"2016-01-12 21:50:24");
		newOrderInsert.addValue("MODIFIED_DATE", new Date());	
		newOrderInsert.addValue("PAYMENT_MODE", orderDetails.getOrderInfo().getPaymentMode());
		newOrderInsert.addValue("COUPON_CODE", orderDetails.getOrderInfo().getCouponCode());

		try{
			orderId=insertIntoOrderTable.executeAndReturnKey(newOrderInsert).intValue();
			if(orderDetails.getOrderInfo().getCouponCode()!=null && !("".equals(orderDetails.getOrderInfo().getCouponCode()))){
				int x=addCouponForUser(orderDetails.getOrderInfo().getCouponCode(),userId);
				if(x==0){
					orderStatus.setOrderStatusDesc("Coupon Couldn't applied successfully");
					response.setResponseCode("HM104");
					response.setResponseMessage(configReader.getValue("HM104"));
					orderStatus.setResponseStatus(response);
					return orderStatus;
				}
			}
			response.setResponseCode("HM200");
			response.setResponseMessage(configReader.getValue("HM200"));
			orderStatus.setResponseStatus(response);
			orderStatus.setOrderStatusDesc("Order placed");
			orderStatus.setOrderStatusCode(1);
			}catch (Exception e) {
				orderStatus.setOrderStatusDesc("Order couldn't able to place ");
				orderStatus.setOrderStatusCode(0);
				response.setResponseCode("HM103");
				response.setResponseMessage(configReader.getValue("HM103"));
				response.setResponseMessage(e.getMessage());
				orderStatus.setResponseStatus(response);
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
				orderStatus.setOrderStatusDesc("Order couldn't able to place ");
				orderStatus.setOrderStatusCode(0);
				response.setResponseCode("HM103");
				response.setResponseMessage(configReader.getValue("HM103"));
				response.setErrorDetails(e.getMessage());
				orderStatus.setResponseStatus(response);
				
			}

		}	
		orderStatus.setOrderId(orderId);
		//orderStatus.setExecutiveName("MK Jcob");
		//orderStatus.setExecutivePhone("7829777997");
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
		ResponseStatus response=new ResponseStatus();
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue("EMAIL", user.getuName());
		mapSqlParameterSource.addValue("USER_CODE", user.getuCode());
		int userId=0;
		String userIdQuery = "SELECT USER_ID FROM user WHERE EMAIL=:EMAIL AND USER_CODE=:USER_CODE AND STATUS='A' ";
		try {
			userId= namedParameterJdbcTemplate.queryForInt(userIdQuery,mapSqlParameterSource);
		}catch(Exception e){
			response.setResponseCode("HM103");
			response.setResponseMessage(configReader.getValue("HM103"));
			response.setErrorDetails(e.getMessage());
		}
		return userId;
	}
	
	private int getUserIdByUserCode(String userCode){
		ResponseStatus response=new ResponseStatus();
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue("USER_CODE", userCode);
		int userId=0;
		String userIdQuery = "SELECT USER_ID FROM user WHERE  USER_CODE=:USER_CODE AND STATUS='A' ";
		try {
			userId= namedParameterJdbcTemplate.queryForInt(userIdQuery,mapSqlParameterSource);
		}catch(Exception e){
			response.setResponseCode("HM103");
			response.setResponseMessage(configReader.getValue("HM103"));
			response.setErrorDetails(e.getMessage());
		}
		return userId;
	}

	@Override
	public List<OrderStatus> orderHistory(User user) {
		int userId=getUserIdByUserCode(user);
		List<OrderStatus> orderStatusList=new ArrayList<OrderStatus>();
		MapSqlParameterSource mapSqlParameterSourceAddress = new MapSqlParameterSource();
		mapSqlParameterSourceAddress.addValue("USER_ID", userId);
		String orderStatusQuery="SELECT O.ORDER_ID,O.TOTAL_AMOUNT,OSC.STATUS_CODE,OSC.DESCRIPTION,OS.EXECUTIVE_NAME,OS.EXECUTIVE_PHONE "
				+"  FROM order_details O"
				+"	JOIN order_status OS ON O.ORDER_ID=OS.ORDER_ID AND O.STATUS='A'"
				+"	JOIN order_status_category OSC ON OS.ORDER_STATUS_ID=OSC.STATUS_CODE"
				+"	WHERE O.USER_ID=:USER_ID ORDER BY O.ORDER_ID DESC";
		
		orderStatusList=namedParameterJdbcTemplate.query(orderStatusQuery,mapSqlParameterSourceAddress, new RowMapper() {
			@Override
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				OrderStatus orderStatus =new OrderStatus();
				orderStatus.setOrderId(rs.getLong("ORDER_ID"));
				orderStatus.setTotalAmount(rs.getString("TOTAL_AMOUNT"));
				orderStatus.setOrderStatusDesc(rs.getString("OSC.DESCRIPTION"));
				orderStatus.setOrderStatusCode(rs.getInt("OSC.STATUS_CODE"));
				orderStatus.setExecutiveName(rs.getString("EXECUTIVE_NAME"));
				orderStatus.setExecutivePhone(rs.getString("EXECUTIVE_PHONE"));
				return orderStatus;
			}
		});
		return orderStatusList;
	}

	@Override
	public OrderDetails orderDetails(final OrderDetails orderDetail) {
		int userId=getUserIdByUserCode(orderDetail.getUser());
		MapSqlParameterSource mapSqlParameterSourceAddress = new MapSqlParameterSource();
		mapSqlParameterSourceAddress.addValue("ORDER_ID", orderDetail.getOrderInfo().getOrderId());
		mapSqlParameterSourceAddress.addValue("USER_ID", userId);
		String orderDetailsQuery ="SELECT OD.USER_ID,OD.ORDER_ID,OD.ADDRESS_ID,OD.TOTAL_AMOUNT,"
				+	" OD.DELIVERY_CHARGES,OD.PAYMENT_MODE,OD.COUPON_CODE,OD.DELIVERY_TIME,"
				+ 	" U.FIRST_NAME,U.EMAIL,U.PHONE,U.USER_CODE,U.USER_TYPE,"
				+	" UA.NAME,UA.PHONE,UA.LINE_1_BUILDING_NO,UA.LINE_2_STREET_NO,UA.CITY,"
				+	" UA.STATE,UA.PINCODE,OS.ORDER_STATUS_ID,OS.EXECUTIVE_NAME,OS.EXECUTIVE_PHONE, "
				+	" I.DESCRIPTION,I.ITEM_NAME,I.ITEM_ID,OID.NO_OF_ITEM,I.ITEM_PRICE"
				+   " FROM order_details OD"
				+	" JOIN user U ON OD.USER_ID=U.USER_ID"
				+	" JOIN order_item_details OID ON OD.ORDER_ID=OID.ORDER_ID"
				+	" JOIN item I ON OID.ITEM_ID=I.ITEM_ID"
				+	" JOIN user_address UA on OD.ADDRESS_ID=UA.USER_ADDRESS_ID"
				+	" JOIN order_status OS ON OD.ORDER_ID=OS.ORDER_ID"
				+	" WHERE OD.ORDER_ID=:ORDER_ID AND OD.USER_ID=:USER_ID ";

		System.out.println(orderDetailsQuery);
		System.out.println("OrderId="+orderDetail.getOrderInfo().getOrderId() +"####### UserId="+userId);
		//OrderDetails orderDetails = new OrderDetails();
		final OrderDetails orderDetails=new OrderDetails();
		orderDetails.setItemList(new ArrayList<Item>());
		
		List<OrderDetails> orderDetailsList=namedParameterJdbcTemplate.query(orderDetailsQuery,mapSqlParameterSourceAddress, new RowMapper() {
			@Override
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				
				//User user =new User();
				OrderStatus orderStatus1 =new OrderStatus();
				System.out.println("Getting details For row number "+rowNum);
						if (rowNum == 0) {
							/*Getting address data*/
							Address address = new  Address();
							try {
								address.setAddressId(rs.getInt("ADDRESS_ID"));
								address.setCity(rs.getString("CITY"));
								address.setLine1BuildingNo(rs.getString("LINE_1_BUILDING_NO"));
								address.setLine2StreetNo(rs.getString("LINE_2_STREET_NO"));
								address.setpCode(rs.getString("PINCODE"));
								address.setPhone(rs.getString("PHONE"));
								address.setState(rs.getString("STATE"));
							} catch (Exception e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							
							/*Getting user details*/
							User user=new User();
							try {
								user.setuName(rs.getString("EMAIL"));
								user.setuCode(rs.getString("USER_CODE"));
								user.setUserId(rs.getInt("USER_ID"));
								user.setAddress(address);
							} catch (Exception e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							
							 
							/*Getting Order details*/
							Order order=new Order();
							try {
								order.setOrderId(rs.getInt("ORDER_ID"));
								order.setCouponCode(rs.getString("COUPON_CODE"));
								order.setDeliveryCharges(rs.getDouble("DELIVERY_CHARGES"));
								try {
									order.setDeliveryTime(rs.getDate("DELIVERY_TIME"));
								} catch (Exception e) {
									order.setDeliveryTime(null);
								}
								order.setTotalAmount(rs.getDouble("TOTAL_AMOUNT"));
								order.setPaymentMode(rs.getString("PAYMENT_MODE"));
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
						
							/*Getting Order status*/
							OrderStatus orderStatus=new OrderStatus();
							try {
								orderStatus.setOrderId(rs.getInt("ORDER_ID"));
								orderStatus.setOrderStatusCode(rs.getInt("ORDER_STATUS_ID"));
								orderStatus.setExecutiveName(rs.getString("EXECUTIVE_NAME"));
								orderStatus.setExecutivePhone(rs.getString("EXECUTIVE_PHONE"));

								orderDetails.setOrderInfo(order);
								orderDetails.setOrderStatus(orderStatus);
								orderDetails.setUser(user);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						
						Item item = new Item();
						item.setDescription(rs.getString("DESCRIPTION"));
						item.setItemName(rs.getString("ITEM_NAME"));
						item.setItemId(rs.getInt("ITEM_ID"));
						item.setNumberOfItems(rs.getInt("NO_OF_ITEM"));
						item.setPerItemCost(rs.getFloat("ITEM_PRICE"));	
						orderDetails.getItemList().add(item);
				
				return orderStatus1;
			}
		});
		return orderDetails;
	}

	@Override
	public String sendMail(MailingDetails mailingDetails) {
		/*Getting template dependent details*/
		//tempalateId="3";
		TemplateBean templateBean=templateDependentDeatils(mailingDetails.getTemplateId());
		
		/*Getting user dependent details*/
		//userID="123";flag="1";
		List<UserBean> userBean=userListDetails(mailingDetails.getUcode(),"12");
		
		/*Getting template links details*/
		List<MailerBean> templateDetails=getTemplateLinkDetails(mailingDetails.getTemplateId());
		
		/*Getting mail content from mail templates*/
		UserBean user=userBean.get(0);
		TemplateProcessing tmp=new TemplateProcessing();
		Multipart mailContent=tmp.processTemplate(templateDetails, templateBean, user);
		
		int flag=sendEmail(user,templateBean,mailContent);
		
		if(flag==1)
			return "SUCCESS";
		else 
			return "ERROR";
	}
	
	private List<MailerBean> getTemplateLinkDetails(int templateId) {

		List<MailerBean> templateLinkList=new ArrayList<MailerBean>();
		MapSqlParameterSource paramMap=new MapSqlParameterSource();
		paramMap.addValue("TEMPLATE_ID", templateId);
			try {			
				/************Get the text body from the db**************/
				String subSQL="SELECT textbody FROM template where template_id=:TEMPLATE_ID and status='A' ";
				MailerBean sub=(MailerBean) namedParameterJdbcTemplate.queryForObject(subSQL,paramMap, new RowMapper(){
					@Override
					public Object mapRow(ResultSet rs, int arg1)
							throws SQLException {
						MailerBean user = new MailerBean();
						user.setTEXTBody(rs.getString("textbody"));
						return user;
					}
				});
				System.out.println("text in dao while sending emails------>"+sub.getTEXTBody());

				/************************/
				String linkSql="SELECT lp.parameter_name,lp.tracking_link,lp.type FROM BV_TEMPLATE_LINKS tl " +
						"join BV_LINKS_PARAMETER lp on lp.l_id=tl.lp_id and lp.status='a' where " +
						"tl.T_ID=:TEMPLATE_ID and tl.STATUS='A'";
				List<MailerBean> templateLinkList1=namedParameterJdbcTemplate.query(linkSql, paramMap, new RowMapper() {
					@Override
					public Object mapRow(ResultSet rs, int arg1)
							throws SQLException {
						MailerBean services=new MailerBean();
						services.setLinkParameter(rs.getString("parameter_name"));
						services.setTrackingLink(rs.getString("tracking_link"));
						services.setParameterType(rs.getString("type"));
						
						return services;
					}
				});
				System.out.println("templateLinkList1 size----->"+templateLinkList1.size());
				sub.setTemplateLinkList(templateLinkList1);
				templateLinkList.add(sub);
				System.out.println("sub object templateLinkList size---->"+templateLinkList.size());
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			return templateLinkList;
	
	}

	private TemplateBean templateDependentDeatils(int templateId){
        TemplateBean bean=new TemplateBean();
		try {
			String sendingDetailsQuery="SELECT * FROM template_details WHERE STATUS='A' AND TEMPLATE_ID="+templateId;
			bean=namedParameterJdbcTemplate.queryForObject(sendingDetailsQuery, new HashMap(), new TemplateDetailsRowMapper());
			System.out.println("Sending Details Query"+sendingDetailsQuery);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bean;
	}
	
	private List<UserBean> userListDetails(String userID, String flag) {
		List<UserBean> userListDetail=new ArrayList<UserBean>();
		
		/*Fetching required query for getting dynamic details in template*/
		MailerBean filterObject=new MailerBean();
		String fetchingQuery="SELECT QUERY FROM mail_template.QUERY_DETAILS WHERE QUERY_ID="+flag;
		filterObject=(MailerBean) namedParameterJdbcTemplate.queryForObject(fetchingQuery, new HashMap(), new RowMapper() {
			@Override
			public Object mapRow(ResultSet rs, int arg1)
					throws SQLException {
				MailerBean services=new MailerBean();
				services.setFilterConditions(rs.getString("QUERY"));
				return services;
			}
		});			
		String requiredQuery=filterObject.getFilterConditions();
		String GPN_ID="GPN_ID";
		String IdList[]=userID.split("@");
		for (int i =1; i < IdList.length+1; i++) {
			GPN_ID=GPN_ID+"_"+i;
			String temp=GPN_ID;
			System.out.println("creating GPN_ID="+GPN_ID);
			GPN_ID=IdList[i-1];
			System.out.println("Final GPN_ID="+GPN_ID);
			if(requiredQuery.contains("${GPN_ID_"+i+"}")){
				requiredQuery=requiredQuery.replace("${GPN_ID_"+i+"}",GPN_ID);
			}
			/*if(requiredQuery.contains("${GPN_ID_1}") && i==1){
				requiredQuery=requiredQuery.replace("${GPN_ID_1}",GPN_ID);
			}else if (requiredQuery.contains("${GPN_ID_2}") && i==2) {
				requiredQuery=requiredQuery.replace("${GPN_ID_2}",GPN_ID);
			}else if (requiredQuery.contains("${GPN_ID_3}") && i==3) {
				requiredQuery=requiredQuery.replace("${GPN_ID_3}",GPN_ID);
			}*/
		}		
		System.out.println("User information select query-: "+requiredQuery);
		userListDetail=userListDetails(requiredQuery);
		
		List<UserBean> newUserListDetail=new ArrayList<UserBean>();
		
		/*Inserting user details into other table for tracking purpose*/
		for (UserBean userDetail : userListDetail) {}
		return newUserListDetail;
	
	}
	public List<UserBean> userListDetails(String query) {
		List<UserBean> userListDetail=new ArrayList<UserBean>();
		MapSqlParameterSource paramMap=new MapSqlParameterSource();
		String userDetailSql=null;
		userDetailSql=query;
		try {
			userListDetail=namedParameterJdbcTemplate.query(userDetailSql, new HashMap(), new UserDetailsRowMapper());
		} catch (DataAccessException e) {
			System.out.println("No results found");
			e.printStackTrace();
		}
		return userListDetail;
	}
	
	public int sendEmail(UserBean userBean,TemplateBean templateBean,Multipart mp){
		int flag=0;
		final Multipart mailContent=mp; 
		final String from=templateBean.getSenderEmail();
		System.out.println("FROM ="+from);
		final String fromName=templateBean.getSenderName(); 
		/*Making subject as dynamic*/
		String dynamicSubject="";
		if(templateBean.getSubject().contains("${")){
			//dynamicSubject="Dynamic subject under process";
			System.out.println("Subject from DB"+templateBean.getSubject());
			if(userBean.getDynamicSubjectV1()!=null)
			dynamicSubject=templateBean.getSubject().replace("${DSV1}",userBean.getDynamicSubjectV1());
			if(userBean.getDynamicSubjectV2()!=null)
			dynamicSubject=dynamicSubject.replace("${DSV2}",userBean.getDynamicSubjectV2());
			if(userBean.getDynamicSubjectV3()!=null)
			dynamicSubject=dynamicSubject.replace("${DSV3}",userBean.getDynamicSubjectV3());
			System.out.println("Final Subject"+dynamicSubject);
		}else{
			dynamicSubject=templateBean.getSubject();
		}
		final String subject=dynamicSubject;
		final String replyToName=templateBean.getReplyToName();
		final String replyToEmail=templateBean.getReplyToEmail();
		
		/*temp variable to avoid null pointer exception*/
		String t1[]=null;
		String t2[]=null;
		String t3[]=null;
		try {
			t1=templateBean.getCcEmailDetails().split(";");
			System.out.println("CC Emails"+t1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("CC Emails"+t1);
			//e.printStackTrace();
		}
		try {
			t2=templateBean.getBccEmialDetails().split(";");
			System.out.println("BCC Emails"+t2);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("BCC Emails"+t2);
			//e.printStackTrace();
		}
		try {
			t3=userBean.getUserEmail().split(";");
			System.out.println("To Emails"+t3);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("To Emails"+t3);
			//e.printStackTrace();
		} 
		
		final String cc[]=t1;
		final String bcc[]=t2;
		final String[] to=t3;

		System.out.println("in send  Email EmailUtils");
		MimeMessagePreparator preparator = new MimeMessagePreparator() {
			public void prepare(MimeMessage mimeMessage) throws Exception {
				try {
					MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
					final  String[] newTo=new String[1000];
					System.out.println("TO++    "+to[0]);
					int flag=0;
					String[] updateTo=new String[50];
					if(to[0].contains(";")){
						flag=1;
						updateTo=to[0].split(";");
					}
					if(flag==1){
						message.setTo(updateTo);
					}else{
						message.setTo(to);
					}
					System.out.println("To address is set");
					if(bcc!=null){
						int fg=0;
						String[] updTo=new String[50];
						if(bcc[0].contains(";")){
							fg=1;
							updTo=bcc[0].split(";");
					    }
						if(fg==1){
						System.out.println("BCC:"+updTo);
					  	message.setBcc(updTo);
						}else{
							System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!BCC:"+bcc);
							message.setBcc(bcc);
						}
					}
					message.setFrom(from,fromName);
					System.out.println("in preparator");
					message.setSubject(subject);
					
					//InternetAddress[] replyAdrr={new InternetAddress("infiniti4bangalore@gmail.com","Bvibes")};
					message.setReplyTo(replyToEmail, replyToName);
					

					//System.out.println("email message is: " + text);
					//message.setText(text, true);
					mimeMessage.setContent(mailContent);
					
					System.out.println("email message is set  ");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		try {
			mailSender.send(preparator);
			System.out.println("Mail send sucessfully");
			flag=1;
		} catch (Exception e1) {
			System.out.println("Error in sending mail");
			flag=0;
			e1.printStackTrace();
		}
		return flag;

	}

	@Override
	public String getotp(String phoneNo,String otp) throws Exception{
		String updateOtp="UPDATE user SET OTP='"+otp+"' WHERE phone='"+phoneNo+"'";
		int updateResult=namedParameterJdbcTemplate.update(updateOtp, new MapSqlParameterSource());
		if(updateResult==0)
			return "FAIL";
		else
			return otp+"";
	}

	@Override
	public CouponTxn applyCouponCode(CouponTxn couponTxn) {
		ResponseStatus responseStatus=new ResponseStatus();
		final CouponTxn cpt = getCouponDetails(couponTxn.getCouponCode());
		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("COUPON_ID", cpt.getCouponId());
		if(("Valid").equals(cpt.getCouponAppliedStatus())){
			int userId=getUserIdByUserCode(couponTxn.getuCode());
			System.out.println(cpt.getCouponCode()+"####"+userId+"###"+couponTxn.getuCode());
			map.addValue("USER_ID", userId);
			String couponDetailsQuery ="SELECT * FROM user_coupon_mapping UCM JOIN coupon_details CD ON UCM.COUPON_ID=CD.ID WHERE UCM.COUPON_ID=:COUPON_ID AND UCM.USER_ID=:USER_ID AND UCM.STATUS='A'";
			System.out.println("User and Coupon validation check query "+couponDetailsQuery);

			List<CouponTxn> cp=new ArrayList<CouponTxn>();
			try {
				cp = namedParameterJdbcTemplate.query(couponDetailsQuery,map, new RowMapper(){
				@Override
				public Object mapRow(ResultSet rs, int arg1)
						throws SQLException {
					CouponTxn cptx=new CouponTxn();
					cptx.setCouponAppliedStatus("Valid Coupon");
					cptx.setCouponValue(rs.getString("coupon_value"));
					cptx.setCouponValueType(rs.getString("coupon_value_type"));
					cptx.setResue_attempt(rs.getInt("reuse_attempt"));
					cptx.setUse_attempt(rs.getInt("use_attempt"));
					cptx.setCouponId(rs.getInt("id")+"");
					return cptx;
				}
			 });
			/*since one user will have one coupon only ,so getting zeroth index value*/	
			if(cp.size()==0){
				cpt.setCouponAppliedStatus("Verified");
				responseStatus.setResponseCode("HM105");
				responseStatus.setResponseMessage(configReader.getValue("HM105"));
			}else if(cp.size()>0 && (cp.get(0).getResue_attempt() > cp.get(0).getUse_attempt())){
				cpt.setCouponAppliedStatus("Verified");
				cpt.setUse_attempt(cp.get(0).getUse_attempt());
				responseStatus.setResponseCode("HM105");
				responseStatus.setResponseMessage(configReader.getValue("HM105"));
			}else if(cp.size()>0 && (cp.get(0).getResue_attempt()==cp.get(0).getUse_attempt())){
				cpt.setCouponAppliedStatus("Already Used");
				cpt.setUse_attempt(cp.get(0).getUse_attempt());
				responseStatus.setResponseCode("HM106");
				responseStatus.setResponseMessage(configReader.getValue("HM106"));
			}else{
				cpt.setCouponAppliedStatus("Not Authorize to use");
			}
				
			} catch (DataAccessException e) {
				cpt.setCouponAppliedStatus("Database Exception");
				responseStatus.setResponseCode("HM103");
				responseStatus.setResponseMessage(configReader.getValue("HM103"));
				responseStatus.setErrorDetails(e.getMessage());
				e.printStackTrace();
			}	

		}else{
			cpt.setCouponAppliedStatus("Invalid");
			responseStatus.setResponseCode("HM104");
			responseStatus.setResponseMessage(configReader.getValue("HM104"));
		}
		cpt.setResponseStatus(responseStatus);
		return cpt;
	}
	
	private CouponTxn getCouponDetails(String couponCodes){
		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("COUPON_CODE", couponCodes);
		String couponDetailsQuery ="SELECT * FROM coupon_details WHERE COUPON_CODE=:COUPON_CODE AND STATUS='A'";
		System.out.println("Coupon validation check query "+couponDetailsQuery);
		CouponTxn cp=new CouponTxn();
		try {
			cp = (CouponTxn) namedParameterJdbcTemplate.queryForObject(couponDetailsQuery,map, new RowMapper(){
			@Override
			public Object mapRow(ResultSet rs, int arg1)
					throws SQLException {
				CouponTxn cptx=new CouponTxn();
				cptx.setCouponAppliedStatus("Valid");
				cptx.setCouponValue(rs.getString("coupon_value"));
				cptx.setCouponValueType(rs.getString("coupon_value_type"));
				cptx.setResue_attempt(rs.getInt("reuse_attempt"));
				cptx.setCouponId(rs.getInt("id")+"");
				return cptx;
			}
		 });
		} catch (DataAccessException e) {
			cp.setCouponAppliedStatus("Invalid");
			e.printStackTrace();
		}	
		return cp;
	}
	
	private int addCouponForUser(String couponCode,int userId){
		MapSqlParameterSource newCouponInsert=new MapSqlParameterSource();
		int insertedRecord=0;
		CouponTxn cptx=getCouponDetails(couponCode);
		newCouponInsert.addValue("USER_ID", userId);
		newCouponInsert.addValue("COUPON_ID", cptx.getCouponId());
		newCouponInsert.addValue("USE_ATTEMPT", cptx.getUse_attempt()+1);
		newCouponInsert.addValue("STATUS", "A");
		try{
			insertedRecord=insertIntoUserCouponMapping.executeAndReturnKey(newCouponInsert).intValue();			
		}catch (Exception e) {
		e.printStackTrace();
	}
		return insertedRecord;
}

	@Override
	public PlanSubscription planSubscription(PlanSubscription planSubscription) {
		
		ResponseStatus res=new ResponseStatus();
		int userId=getUserIdByUserCode(planSubscription.getuCode());
		if(userId!=0){
		MapSqlParameterSource newInsertPlanSubscription=new MapSqlParameterSource();
		newInsertPlanSubscription.addValue("USER_ID", userId);
		newInsertPlanSubscription.addValue("PLAN_TYPE", planSubscription.getPlanType());
		newInsertPlanSubscription.addValue("SELECTED_DATE", planSubscription.getSelectedDate());
		newInsertPlanSubscription.addValue("START_DATE", planSubscription.getStartDate());
		newInsertPlanSubscription.addValue("END_DATE", planSubscription.getEndDate());
		newInsertPlanSubscription.addValue("PLAN_STATUS", "Active");
		newInsertPlanSubscription.addValue("PLAN_COST", planSubscription.getPlanCost());
		newInsertPlanSubscription.addValue("ADDRESS_ID", planSubscription.getAddressId());
		newInsertPlanSubscription.addValue("COMBO_ID", planSubscription.getComboId());
		newInsertPlanSubscription.addValue("PAYMENT_MODE", planSubscription.getPaymentMode());
		newInsertPlanSubscription.addValue("TIME_SLOT", planSubscription.getTimeSlot());
		newInsertPlanSubscription.addValue("STATUS", "A");
		
		try{
			int insertedRecord=insertPlanSubscription.executeAndReturnKey(newInsertPlanSubscription).intValue();
			res.setResponseCode("HM200");
			res.setResponseMessage(configReader.getValue("HM200"));
			planSubscription.setPlanSubscribeId(insertedRecord);
		}catch (Exception e) {
			e.printStackTrace();
			res.setResponseCode("HM103");
			res.setResponseMessage(configReader.getValue("HM103"));
			res.setErrorDetails(e.getMessage());
		}
		}
		planSubscription.setResponseStatus(res);
		return planSubscription;
	}

	@Override
	public PlanSubscription updatePlanSubscription(
			PlanSubscription planSubscription) {
		ResponseStatus res=new ResponseStatus();
		int userId=getUserIdByUserCode(planSubscription.getuCode());
		String updatePlanDate="UPDATE plan_subscription SET SELECTED_DATE='"+planSubscription.getSelectedDate()+"' ,START_DATE='"+planSubscription.getStartDate()+"',END_DATE='"+planSubscription.getEndDate()+"' WHERE USER_ID='"+userId+"' AND ID="+planSubscription.getPlanSubscribeId();
		try {
			int updateResult=namedParameterJdbcTemplate.update(updatePlanDate, new MapSqlParameterSource());	
			if(updateResult!=0){
				planSubscription.setUpdatedStatus(true);
				res.setResponseCode("HM200");
				res.setResponseMessage(configReader.getValue("HM200"));
			}else{
				planSubscription.setUpdatedStatus(false);
				res.setResponseCode("HM200");
				res.setResponseMessage(configReader.getValue("HM200"));
			}
		} catch (DataAccessException e) {
			e.printStackTrace();
			planSubscription.setUpdatedStatus(false);
			res.setResponseCode("HM203");
			res.setResponseMessage(configReader.getValue("HM203"));
			res.setErrorDetails(e.getMessage());
		}
		planSubscription.setResponseStatus(res);
		return  planSubscription;

	}

	@Override
	public PlanSubscription cancelPlanSubscription(
			PlanSubscription planSubscription) {
		ResponseStatus res=new ResponseStatus();
		int userId=getUserIdByUserCode(planSubscription.getuCode());
		String updatePlanDate="UPDATE plan_subscription SET PLAN_STATUS='cancelled' WHERE USER_ID='"+userId+"' AND ID="+planSubscription.getPlanSubscribeId();
		try {
			int updateResult=namedParameterJdbcTemplate.update(updatePlanDate, new MapSqlParameterSource());
			if(updateResult!=0){
				planSubscription.setUpdatedStatus(true);
				planSubscription.setPlanStatus("cancelled");
				res.setResponseCode("HM200");
				res.setResponseMessage(configReader.getValue("HM200"));
			}else{
				planSubscription.setUpdatedStatus(false);
				res.setResponseCode("HM203");
				res.setResponseMessage(configReader.getValue("HM203"));
			}
		} catch (DataAccessException e) {
			e.printStackTrace();
			planSubscription.setUpdatedStatus(false);
			res.setResponseCode("HM103");
			res.setResponseMessage(configReader.getValue("HM103"));
			res.setErrorDetails(e.getMessage());
		}
		planSubscription.setResponseStatus(res);
		return  planSubscription;

	}

	@Override
	public List<ComboDetails> getComboDetails() {
		String couponDetailsQuery ="SELECT * FROM combo_details";
		List<ComboDetails> cp=new ArrayList<ComboDetails>();
		try {
			cp =  namedParameterJdbcTemplate.query(couponDetailsQuery,new MapSqlParameterSource(), new RowMapper(){
			@Override
			public Object mapRow(ResultSet rs, int arg1)
					throws SQLException {
				ComboDetails cptx=new ComboDetails();
				cptx.setComboId(rs.getInt("combo_id"));
				cptx.setComboName(rs.getString("combo_name"));
				cptx.setCost(rs.getInt("combo_price"));
				return cptx;
			}
		 });
		} catch (DataAccessException e) {
			e.printStackTrace();
		}	
		return cp;
	
	}

	@Override
	public User changePassword(User user) {
			ResponseStatus response=new ResponseStatus();
			user.setResponseStatus(response);
			MapSqlParameterSource updatePassword=new MapSqlParameterSource();
			updatePassword.addValue("ENC_PASSWORD", user.getPassword1());
			updatePassword.addValue("USER_CODE", user.getuCode());	
			String updatePasswordQuery="UPDATE user SET ENC_PASSWORD=:ENC_PASSWORD WHERE USER_CODE=:USER_CODE";
			int updateResult=0;
			try {
				updateResult = namedParameterJdbcTemplate.update(updatePasswordQuery, updatePassword);
				if(updateResult!=0){
					response.setResponseCode("HM200");
					response.setResponseMessage(configReader.getValue("HM200"));
				}else{
					response.setResponseCode("HM203");
					response.setResponseMessage(configReader.getValue("HM203"));
				}
			} catch (DataAccessException e1) {
				e1.printStackTrace();
				response.setResponseCode("HM203");
				response.setResponseMessage(configReader.getValue("HM203"));
			}
			return user;
	}

	@Override
	public List<PlanSubscription> comboDetailsByUser(User user) {
		int userId=getUserIdByUserCode(user);
		List<PlanSubscription> planSubscription=new ArrayList<PlanSubscription>();
		MapSqlParameterSource mapSqlParameterSourceAddress = new MapSqlParameterSource();
		mapSqlParameterSourceAddress.addValue("USER_ID", userId);
		String orderStatusQuery="SELECT PHONE,LINE_1_BUILDING_NO,LINE_2_STREET_NO,CITY,PINCODE,ADDRESS_ID,"
				+ "COMBO_ID,END_DATE,PAYMENT_MODE,PLAN_COST,PLAN_TYPE,SELECTED_DATE,ID,"
				+ "START_DATE,TIME_SLOT from plan_subscription PS JOIN user_address UA ON PS.ADDRESS_ID=UA.USER_ADDRESS_ID WHERE PS.USER_ID=:USER_ID";
		System.out.println("comboDetailsByUser Query \n"+orderStatusQuery);
		
		planSubscription=namedParameterJdbcTemplate.query(orderStatusQuery,mapSqlParameterSourceAddress, new RowMapper() {
			@Override
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				PlanSubscription planSubscription =new PlanSubscription();
				planSubscription.setAddressId(rs.getInt("ADDRESS_ID"));
				planSubscription.setComboId(rs.getInt("COMBO_ID")+"");
				planSubscription.setEndDate(rs.getString("END_DATE"));
				planSubscription.setPaymentMode(rs.getString("PAYMENT_MODE"));
				planSubscription.setPlanCost(rs.getInt("PLAN_COST"));
				planSubscription.setPlanSubscribeId(rs.getInt("ID"));
				planSubscription.setPlanType(rs.getString("PLAN_TYPE"));
				planSubscription.setSelectedDate(rs.getString("SELECTED_DATE"));
				planSubscription.setStartDate(rs.getString("START_DATE"));
				planSubscription.setTimeSlot(rs.getString("TIME_SLOT"));
				Address address = new  Address();
				try {
					address.setAddressId(rs.getInt("ADDRESS_ID"));
					address.setCity(rs.getString("CITY"));
					address.setLine1BuildingNo(rs.getString("LINE_1_BUILDING_NO"));
					address.setLine2StreetNo(rs.getString("LINE_2_STREET_NO"));
					address.setpCode(rs.getString("PINCODE"));
					address.setPhone(rs.getString("PHONE"));
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				planSubscription.setAddress(address);
				ResponseStatus response=new ResponseStatus();
				response.setResponseCode("HM200");
				response.setResponseMessage(configReader.getValue("HM200"));
				planSubscription.setResponseStatus(response);
				return planSubscription;
			}
		});
		if(planSubscription.size()==0){
			ResponseStatus response=new ResponseStatus();
			response.setResponseCode("HM204");
			response.setResponseMessage(configReader.getValue("HM204"));
			PlanSubscription pp=new PlanSubscription();
			pp.setResponseStatus(response);
			planSubscription.add(pp);
		}
		return planSubscription;
	
	}

	@Override
	public User mobileVerification(User user) {
		ResponseStatus response=new ResponseStatus();
		user.setResponseStatus(response);
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue("USER_CODE", user.getuCode());
		mapSqlParameterSource.addValue("PHONE", user.getMobile());
		String userIdQuery = "SELECT count(*) FROM user WHERE USER_CODE=:USER_CODE AND PHONE=:PHONE AND STATUS='A'";
		try {
			int rowCount=namedParameterJdbcTemplate.queryForInt(userIdQuery,mapSqlParameterSource);
			if(rowCount==1){
				response.setResponseCode("HM200");
				response.setResponseMessage(configReader.getValue("HM200"));
			}else if(rowCount>1){
				response.setResponseCode("HM205");
				response.setResponseMessage(configReader.getValue("HM205"));
			}else{
				response.setResponseCode("HM204");
				response.setResponseMessage(configReader.getValue("HM204"));
			}
		} catch (DataAccessException e) {
			e.printStackTrace();
			response.setResponseCode("HM103");
			response.setResponseMessage(configReader.getValue("HM103"));
			response.setErrorDetails(e.getMessage());
		}
		return user;
	
	
	}

	@Override
	public User emailVerification(User user) {
		return null;
	}

	@Override
	public String getMobileVerificationCode(User user) {
		String ucode=UUID.randomUUID().toString();
		String otp=ucode.substring(0,6).toUpperCase();
		//String encryptedOtp= StringEncrypterService.encryptString(otp.toUpperCase());
		String updateOtp="UPDATE user SET MOBILE_VERIFICATION_CODE='"+otp+"' WHERE USER_CODE='"+user.getuCode()+"'";
		int updateResult=namedParameterJdbcTemplate.update(updateOtp, new MapSqlParameterSource());
		if(updateResult==0)
			return "FAIL";
		else
			return otp+"";
	}

	@Override
	public User updateMobileVerificationStatus(User user) {
		ResponseStatus response=new ResponseStatus();
		String updateOtp="UPDATE user SET MOBILE_VERIFICATION="+true+" WHERE USER_CODE='"+user.getuCode()+"' AND MOBILE_VERIFICATION_CODE='"+user.getMobileVerificationCode()+"'";
		int updateResult=namedParameterJdbcTemplate.update(updateOtp, new MapSqlParameterSource());
		if(updateResult==1){
			response.setResponseCode("HM200");
			response.setResponseMessage(configReader.getValue("HM200"));
			user.setMobileVerified(true);
			user.setUserStatus(true);
		}else{
			response.setResponseCode("HM203");
			response.setResponseMessage(configReader.getValue("HM203"));
		}
		user.setResponseStatus(response);
		return user;
	}
}
