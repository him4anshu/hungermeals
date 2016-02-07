package com.hungermeals.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.mail.javamail.JavaMailSender;

import com.hungermeals.common.ConfigReader;
import com.hungermeals.persist.Address;
import com.hungermeals.persist.Item;
import com.hungermeals.persist.Order;
import com.hungermeals.persist.OrderDetails;
import com.hungermeals.persist.OrderStatus;
import com.hungermeals.persist.ResponseStatus;
import com.hungermeals.persist.User;

public class AdminDAOImpl implements AdminDAO{
	@Autowired
	private JavaMailSender mailSender;
	@Autowired
	private ConfigReader configReader;
	
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	private JdbcTemplate jdbcTemplate;


	public void setDataSource(DataSource ds) {
		namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(ds);
		jdbcTemplate=new JdbcTemplate(ds);
	}


	@Override
	public List<OrderDetails> orderList(String orderStatus) {

		char orderStatusIdArray[]=orderStatus.toCharArray();
		String requiredOrderStatus="";
		for (char c : orderStatusIdArray) {
			requiredOrderStatus=requiredOrderStatus+c+",";
		}
		requiredOrderStatus=requiredOrderStatus.substring(0,requiredOrderStatus.length()-1);
		MapSqlParameterSource mapSqlParameterSourceAddress = new MapSqlParameterSource();
		String orderDetailsQuery ="SELECT OD.USER_ID,OD.ORDER_ID,OD.ADDRESS_ID,OD.TOTAL_AMOUNT,OD.DELIVERY_SLOT,"
				+	" OD.DELIVERY_CHARGES,OD.PAYMENT_MODE,OD.COUPON_CODE,OD.DELIVERY_TIME,"
				+ 	" U.FIRST_NAME,U.EMAIL,U.PHONE,U.USER_CODE,U.USER_TYPE,"
				+	" UA.NAME,UA.PHONE,UA.LINE_1_BUILDING_NO,UA.LINE_2_STREET_NO,UA.CITY,"
				+	" UA.STATE,UA.PINCODE,OS.ORDER_STATUS_ID,OS.EXECUTIVE_NAME,OS.EXECUTIVE_PHONE "
				+	" FROM order_details OD"
				+	" JOIN user U ON OD.USER_ID=U.USER_ID"
				+	" JOIN user_address UA ON OD.ADDRESS_ID=UA.USER_ADDRESS_ID"
				+	" JOIN order_status OS ON OD.ORDER_ID=OS.ORDER_ID"
				+	" WHERE OS.ORDER_STATUS_ID IN ("+requiredOrderStatus+")  ORDER BY OD.ORDER_ID DESC";
		System.out.println(orderDetailsQuery);
		List<OrderDetails> orderDetailsList=namedParameterJdbcTemplate.query(orderDetailsQuery,mapSqlParameterSourceAddress, new RowMapper() {
			@Override
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				OrderDetails orderDetails=new OrderDetails();
			
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
				order.setOrderId(rs.getInt("ORDER_ID"));
				order.setCouponCode(rs.getString("COUPON_CODE"));
				order.setDeliveryCharges(rs.getDouble("DELIVERY_CHARGES"));
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
				
				return orderDetails;
			}
		});
		return orderDetailsList;
	}


	@Override
	public String updateOrderStatus(String orderStatus) {
		String orders[]=orderStatus.split("@");
		String insertFilter="UPDATE order_status SET ORDER_STATUS_ID="+ orders[0]+" WHERE ORDER_ID="+orders[1];
		int insertResult=namedParameterJdbcTemplate.update(insertFilter, new MapSqlParameterSource());
		if(insertResult==0)
			return "FAIL";
		else
			return "SUCCESS";
	}


	@Override
	public List<Item> itemListByOrder(String orderId) {
		MapSqlParameterSource mapSqlParameterSourceAddress = new MapSqlParameterSource();
		String orderDetailsQuery ="SELECT * FROM order_item_details OID"
				+	" JOIN item I ON OID.ITEM_ID=I.ITEM_ID"
				+	" WHERE OID.ORDER_ID="+orderId;
		System.out.println(orderDetailsQuery);
		
		List<Item> itemList=namedParameterJdbcTemplate.query(orderDetailsQuery,mapSqlParameterSourceAddress, new RowMapper() {
			@Override
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
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
	public  OrderStatus getUserByOrderId(String orderId){
		OrderStatus orderStatus=new OrderStatus();
		ResponseStatus response=new ResponseStatus();
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue("ORDER_ID", orderId);
		int userId=0;
		String userIdQuery = "SELECT EMAIL,PHONE,TOTAL_AMOUNT,OS.EXECUTIVE_NAME,OS.EXECUTIVE_PHONE FROM user U "
						+"	JOIN order_details OD ON U.USER_ID=OD.USER_ID AND U.STATUS='A' "
						+"	JOIN order_status OS ON OD.ORDER_ID=OS.ORDER_ID"
						+"	WHERE OD.ORDER_ID=:ORDER_ID";
		try {
			orderStatus= (OrderStatus)namedParameterJdbcTemplate.queryForObject(userIdQuery,mapSqlParameterSource, new RowMapper(){
				@Override
				public Object mapRow(ResultSet rs, int arg1)
						throws SQLException {
					OrderStatus order=new OrderStatus();
					order.setUserPhone(rs.getString("PHONE"));
					order.setTotalAmount(rs.getDouble("TOTAL_AMOUNT")+"");
					order.setExecutiveName(rs.getString("EXECUTIVE_NAME"));
					order.setExecutivePhone(rs.getString("EXECUTIVE_PHONE"));
					return order;
				}
			});
		}catch(Exception e){
			response.setResponseCode("HM103");
			response.setResponseMessage(configReader.getValue("HM103"));
			response.setErrorDetails(e.getMessage());
		}
		return orderStatus;
	}
}
