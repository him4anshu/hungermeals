/*
 * Global Variables
 * */
	var URL_gbl = "../services/userservices/";
    var userInfo_glb = Cookies.getJSON('uCode'); // use this instead of cookie calls repeatedly
    var getmymeal_corder_glb = localStorage.getItem("mymeal_c_order");

$(document).ready(function(){
       //check login
        if(userInfo_glb != undefined){
            setLoggedInfo();
            order_confirm_load('thank_you_p','ordered_tab');
        }else{
            redir('index.jsp');
        }
});

	
	function order_confirm_load(pId,tableId){
			// get the just saved order id from session
			// pass it here and set the content
		if(getmymeal_corder_glb == null || getmymeal_corder_glb == undefined || getmymeal_corder_glb == ""){
			redir("index.jsp");
		}else{
			var	getOrderList = JSON.parse(getmymeal_corder_glb);
			console.log(JSON.stringify(getOrderList));

			var ordered_items = {};
			ordered_items.order_by = "";
			ordered_items.order_ref_id = getOrderList.planSubscription.planSubscribeId;
			ordered_items.planType = getOrderList.planSubscription.planType;
			ordered_items.comboId = getOrderList.planSubscription.comboId;
			ordered_items.endDate = getOrderList.planSubscription.endDate;
			ordered_items.startDate = getOrderList.planSubscription.startDate; 
			ordered_items.timeSlot = getOrderList.planSubscription.timeSlot;
			ordered_items.order_dates = getOrderList.planSubscription.selectedDate;
			ordered_items.order_pay = getOrderList.planSubscription.paymentMode;
			ordered_items.price = getOrderList.planSubscription.planCost;
			ordered_items.order_status_txt = getOrderList.planSubscription.responseStatus.responseCode;

			if(ordered_items.order_status_txt == "HM200"){
					var thank_you_note = "Eat Hearty!! Thank you for giving us the opportunity to serve you. Your HungerMeals Subscription #:<strong> "+ordered_items.order_ref_id+" </strong>will be packed and delivery with special care.";
					
					var ordered_list = "",item_price = 0,total_cost = 0;

			/*Ordered Delivery status
				<tr>
					<td>
						 Delivery schedule <a href="#" class="tooltip-1" data-placement="top" title="" data-original-title="Please consider 30 minutes of margin for the delivery!"><i class="icon_question_alt"></i></a>
					</td>
					<td>
						<strong class="pull-right">Today 07.30 pm</strong>
					</td>
				</tr>*/

					ordered_list += "<tr>"+
										"<td>"+
										 "Combo"+
										"</td>"+
										"<td>"+
											"<strong class=\"pull-right\">"+ordered_items.comboId +"</strong>"+
										"</td>"+
									"</tr>";
						
					ordered_list += "<tr>"+
										"<td>"+
										 "Subscription"+
										"</td>"+
										"<td>"+
											"<strong class=\"pull-right\">"+ordered_items.planType +"</strong>"+
										"</td>"+
									"</tr>";

					ordered_list += "<tr>"+
										"<td>"+
										 "Dates"+
										"</td>"+
										"<td>"+
											"<strong class=\"pull-right\">"+ ordered_items.startDate + " - " +ordered_items.endDate + "</strong>"+
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
											"<span class=\"pull-right\">&#8377; "+ordered_items.price +"</span>"+
										"</td>"+
									"</tr>";
					
					$("#"+pId).html(thank_you_note);
					$("#"+tableId).find("tbody").html(ordered_list);

				//REMOVE order-id after first call
				localStorage.removeItem("mymeal_c_order");
				sessionStorage.removeItem("mymeal_c_order");

			}else{
				redir('error.jsp');
			}

		}


	}