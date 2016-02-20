package com.hungermeals.controller;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;

import com.hungermeals.api.AdminAPI;
import com.hungermeals.persist.Item;
import com.hungermeals.persist.OrderDetails;
import com.hungermeals.persist.PlanSubscription;
import com.hungermeals.persist.User;


public class AdminController {
	@Autowired
	private AdminAPI adminAPI;
	
	public AdminAPI getAdminAPI() {
		return adminAPI;
	}
	public void setAdminAPI(AdminAPI adminAPI) {
		this.adminAPI = adminAPI;
	}

	@GET
	@Path("/userInfo.json")
    @Produces("application/json")
	public User getUserInfo(){
		System.out.println("###@@@");
		User user = new User();
		user.setEmail("test.fidloo.com");
		user.setUserId(99999);
		return user;
	}
	
	@GET
	@Path("/orderList.json/{orderStatus}")
    @Produces("application/json")
	public List<OrderDetails> orderList(@PathParam("orderStatus") String orderStatus){
		return adminAPI.orderList(orderStatus);
	}
	
	@GET
	@Path("/updateOrderStatus.json/{orderId}")
    @Produces("application/json")
	public String updateOrderStatus(@PathParam("orderId") String orderId){
		return adminAPI.updateOrderStatus(orderId);
	}
	
	@GET
	@Path("/itemListByOrder.json/{orderId}")
    @Produces("application/json")
	public List<Item> itemListByOrder(@PathParam("orderId") String orderId){
		return adminAPI.itemListByOrder(orderId);
	}
	
	@POST
	@Path("/comboDetailsByAdmin.json")
    @Produces("application/json")
	public List<PlanSubscription> comboDetails(User user){
		return adminAPI.comboDetailsByUser(user);
	}
}
