package com.hungermeals.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.MulticastResult;
import com.google.android.gcm.server.Sender;
import com.hungermeals.api.AdminAPI;
import com.hungermeals.common.POST2GCM;
import com.hungermeals.persist.Content;
import com.hungermeals.persist.CouponTxn;
import com.hungermeals.persist.Item;
import com.hungermeals.persist.Menu;
import com.hungermeals.persist.MyMessage;
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
	
	@POST
	@Path("/addMenuDetails.json")
    @Produces("application/json")
	public Menu addMenuDetails(Menu menu){
		return adminAPI.addMenuDetails(menu);
	}
	
	@POST
	@Path("/addItemDetails.json")
    @Produces("application/json")
	public Menu addItemDetails(Menu menu){
		return adminAPI.addItemDetails(menu);
	}
	
	@POST
	@Path("/updateMenuDetails.json")
    @Produces("application/json")
	public Menu updateMenuDetails(Menu menu){
		return adminAPI.updateMenuDetails(menu);
	}
	
	@POST
	@Path("/updateItemDetails.json")
    @Produces("application/json")
	public Menu updateItemDetails(Menu menu){
		return adminAPI.updateItemDetails(menu);
	}
	
	@GET
	@Path("/deleteItem.json/{itemId}")
    @Produces("application/json")
	public Menu deleteItem(@PathParam("itemId") String itemId){
		return adminAPI.deleteItem(itemId);
	}
	
	@GET
	@Path("/deleteMenu.json/{menuId}")
    @Produces("application/json")
	public Menu deleteMenu(@PathParam("menuId") String menuId){
		return adminAPI.deleteMenu(menuId);
	}
	
	@POST
	@Path("/alterMenu.json")
    @Produces("application/json")
	public Menu alterMenu(Menu menu){
		return adminAPI.alterMenu(menu);
	}
	
	@GET
	@Path("/subscriptionList.json/{orderStatus}")
    @Produces("application/json")
	public List<OrderDetails> subscriptionList(@PathParam("orderStatus") String orderStatus){
		return adminAPI.subscriptionList(orderStatus);
	}
	
	@POST
	@Path("/alterCoupon.json")
    @Produces("application/json")
	public CouponTxn alterCoupon(CouponTxn couponDetails){
		return adminAPI.alterCoupon(couponDetails);
	}
	
	@POST
	@Path("/GCMBroadcastold.json")
    @Produces("application/json")
	public void GCMBroadcastOld(MyMessage message){
        System.out.println( "Sending POST to GCM" );

        String apiKey = "AIzaSyBcnGBVGBFJBWn0XM_ZEN-dtVzJRKOViL8";
        List<User> userlist=adminAPI.getUserListForNotification();
        for (Iterator iterator = userlist.iterator(); iterator.hasNext();) {
			User user = (User) iterator.next();
	        Content content = createContent(user,message);
	        POST2GCM.post(apiKey, content);

		}
    }

    public Content createContent(User user,MyMessage message){
        Content c = new Content();
        
        //c.addRegId("APA91bFqnQzp0z5IpXWdth1lagGQZw1PTbdBAD13c-UQ0T76BBYVsFrY96MA4SFduBW9RzDguLaad-7l4QWluQcP6zSoX1HSUaAzQYSmI93....");
        //c.createData("Test Title", "Test Message");
        c.addRegId(user.getAppRegistrationId());
        c.createData(message.getMessage(), message.getTitle());

        return c;
    }
    @POST
	@Path("/GCMBroadcast.json")
    @Produces("application/json")
	public MulticastResult GCMBroadcast(MyMessage mssg){
		System.out.println("Inside pushDataToDevice method ###############");
		Sender gcmSender = new Sender("AIzaSyBcnGBVGBFJBWn0XM_ZEN-dtVzJRKOViL8");
		Message message = new Message.Builder().priority(Message.Priority.NORMAL)
				.addData("title",mssg.getTitle())
				.addData("message",mssg.getMessage()).build();
        List<String> regIdList=adminAPI.getRegistrationIdForNotification();
		MulticastResult mRes=null;
		try {
			mRes = gcmSender.sendNoRetry(message, regIdList);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mRes;
    }
}
