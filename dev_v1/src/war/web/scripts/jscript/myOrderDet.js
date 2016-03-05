/*
 * Global Variables
 * */

	var URL_gbl = "../services/userservices/";
    var userInfo_glb = Cookies.getJSON('uCode'); // use this instead of cookie calls repeatedly

$(document).ready(function(){
       //check login
        if(userInfo_glb != undefined){
       		order_confirm_load('thank_you_p','ordered_tab');
        }else{
            redir('index.jsp');
        }
});

	function order_confirm_load(pId,tableId){
			// get the just saved order id from session
			// pass it here and set the content
		if(localStorage.getItem("orderId") == null || localStorage.getItem("orderId") == undefined || localStorage.getItem("orderId") == ""){
			redir("my_orders.jsp");
		}else{
			var	getOrderList = JSON.parse(localStorage.getItem("orderId"));
			console.log(getOrderList);
			// alert(JSON.stringify(getOrderList));
			var ordered_items = {};
			ordered_items.order_by = "";
			ordered_items.order_ref_id = getOrderList.orderDetails.orderStatus.orderId;
			ordered_items.order_date_time = getOrderList.orderDetails.orderInfo.deliveryTime;
			ordered_items.order_pay = getOrderList.orderDetails.orderInfo.paymentMode;
			ordered_items.order_addr = getOrderList.orderDetails.user.address.line1BuildingNo + " "+getOrderList.orderDetails.user.address.line2StreetNo + " "+getOrderList.orderDetails.user.address.city + " "+getOrderList.orderDetails.user.address.pCode;
			ordered_items.order_pCode = getOrderList.orderDetails.user.address.pCode;
			ordered_items.order_status = getOrderList.orderDetails.orderStatus.orderStatusCode;
			ordered_items.order_status_txt = getOrderList.orderDetails.orderStatus.orderStatus;

			var itemArr = [];
			$.each(getOrderList.orderDetails.itemList, function(i,val){
				var tempItemObj = {};
				tempItemObj.id = val.itemId;
				tempItemObj.name = val.itemName;
				tempItemObj.count = val.numberOfItems;
				tempItemObj.cost = val.perItemCost;

				itemArr.push(tempItemObj);
			});
			// alert(JSON.stringify(itemArr));
			ordered_items.order_list = itemArr;
			console.log(ordered_items);

	/*		var ordered_items = {
				  "order_by": "Varun.s.Kumar",
				  "order_ref_id": "BD8SV001_20160102",
				  "order_date": "2016/01/02",
				  "order_pay": "By cash",
				  "order_addr": "addr1,addr2",
				  "order_pCode": 560037,
				  "order_status": 1,
				  "order_status_txt": "1.Preparing,2.Out for delivery,3.Delivered,4.Cancelled,5.Rejected",
				  "order_list": [
				    {
				      "id": 1,
				      "name": "Baby Corn Chilly",
				      "count": 2,
				      "cost": 40
				    }
				  ]
				};*/

				/*
				 * From AJAX response
				 * Ordered item list
				 	<tr>
						<td>
							<strong>2x</strong> Cheese Cake
						</td>
						<td>
							<strong class="pull-right">$12</strong>
						</td>
					</tr>*/

				var thank_you_note = "Eat Hearty!! Thank you for giving us the opportunity to serve you. Your HungerMeals order #:<strong> "+ordered_items.order_ref_id+" </strong>will be packed and delivery to <strong>"+ ordered_items.order_addr+"</strong> with special care.";
				
				var ordered_list = "",item_price = 0,total_cost = 0;

				jQuery.each(ordered_items.order_list, function(ind,val){
					var order_status_txt = "";
					item_price = val.count * val.cost;
					total_cost = total_cost + item_price;
					ordered_list += "<tr>"+
										"<td>"+
											"<strong>"+ val.count +" x</strong> "+ val.name +""+
										"</td>"+
										"<td>"+
											"<strong class=\"pull-right\">&#8377; "+ item_price +"</strong>"+
										"</td>"+
									"</tr>";
				});
				
		/*Ordered Delivery status
			<tr>
				<td>
					 Delivery schedule <a href="#" class="tooltip-1" data-placement="top" title="" data-original-title="Please consider 30 minutes of margin for the delivery!"><i class="icon_question_alt"></i></a>
				</td>
				<td>
					<strong class="pull-right">Today 07.30 pm</strong>
				</td>
			</tr>*/

			if(ordered_items.order_status == 1){
				var order_status_icon = "<i class=\"icon-hourglass\"></i>";	
				order_status_txt = "Placed";		
			}else if(ordered_items.order_status == 2){
				var order_status_icon = "<i class=\"icon-ok-circle\"></i>";
				order_status_txt = "Confirmed";		
			}else if(ordered_items.order_status == 3){
				var order_status_icon = "<i class=\"icon-food\"></i>";
				order_status_txt = "Preparing";		
			}else if(ordered_items.order_status == 4){
				var order_status_icon = "<i class=\"icon-truck\"></i>";
				order_status_txt = "Out for Delivery";		
				// var order_status_icon = "<i class=\"icon-cancel-alt-filled\"></i>";
				// order_status_txt = "Order Cancelled";		
			}else if(ordered_items.order_status == 5){				
				var order_status_icon = "<i class=\"icon-smile\"></i>";
				order_status_txt = "Order Delivered";		
//				var order_status_icon = "<i class=\"icon-frown\"></i>";
//				order_status_txt = "Order Returned";		
			}

				ordered_list += "<tr>"+
									"<td>"+
									 "Order receivied"+
									"</td>"+
									"<td>"+
										"<strong class=\"pull-right\">"+ordered_items.order_date_time +"</strong>"+
									"</td>"+
								"</tr>";
					
				ordered_list += "<tr>"+
									"<td>"+
									 "Delivery status"+
									"</td>"+
									"<td>"+
										"<strong class=\"pull-right\">"+order_status_icon+" "+ order_status_txt +"</strong>"+
									"</td>"+
								"</tr>";
				
				ordered_list += "<tr>"+
									"<td>"+
									 "Payment"+
									"</td>"+
									"<td>"+
										"<strong class=\"pull-right\">"+ordered_items.order_pay +"</strong>"+
									"</td>"+
								"</tr>";
				
				ordered_list += "<tr>"+
									"<td class=\"total_confirm\">"+
									 "TOTAL"+
									"</td>"+
									"<td class=\"total_confirm\">"+
										"<span class=\"pull-right\">&#8377; "+total_cost +"</span>"+
									"</td>"+
								"</tr>";
				
				$("#"+pId).html(thank_you_note);
				$("#"+tableId).find("tbody").html(ordered_list);

			//REMOVE order-id after first call
			localStorage.removeItem("orderId");
			sessionStorage.removeItem("cart_items");

		}


	}