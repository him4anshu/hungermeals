/*
 * Global Variables
 * */
	var	cart_selectedItmes = [];
    // var URL_gbl = "http://www.hungermeals.com:8080/hungermeals/services/userservices/";
	var URL_gbl = "../services/userservices/";
    var userInfo_glb = Cookies.getJSON('uCode'); // use this instead of cookie calls repeatedly

	function flying_cart(){
		//Flying Cart
	    $("#cart_box").pin({padding: {top: 80, bottom: 25},minWidth: 1100, containerSelector: "#container_pin"})
	}
  
	function cartfiller(itemId,itemName,itemCost,itemCategory){
		var temp_cart_selectedItmes = sessionStorage.getItem("cart_items");
        var ses_cart_selectedItmes = $.parseJSON(temp_cart_selectedItmes);

		cart_selectedItmes = (ses_cart_selectedItmes != null)? ses_cart_selectedItmes : [];

		//		alert(itemId);
		for(var i in cart_selectedItmes){
			if(cart_selectedItmes[i].item === itemId){
				cart_selectedItmes[i].count ++;
				cart_selectedItmes[i].cost = itemCost;
				console.log(cart_selectedItmes);
				sessionStorage.setItem("cart_items", JSON.stringify(cart_selectedItmes));
				setCartItmes(cart_selectedItmes);
				return;
			}
		}

		cart_selectedItmes.push({
					"item": itemId,
					"name": itemName,
					"count": 1,
					"cost": itemCost,
					"category":itemCategory
				});
		console.log(cart_selectedItmes);
		sessionStorage.setItem("cart_items", JSON.stringify(cart_selectedItmes));
		setCartItmes(cart_selectedItmes);
		
	}

	function update_coupon_cartfiller(couponDetails){
		var temp_cart_selectedItmes = sessionStorage.getItem("cart_items");
        var ses_cart_selectedItmes = $.parseJSON(temp_cart_selectedItmes);

		jQuery.each(ses_cart_selectedItmes, function(i,val){
			val.cCode = couponDetails.cCode;
			val.cValue = couponDetails.cValue;
		});	
		sessionStorage.setItem("uCart_items", JSON.stringify(ses_cart_selectedItmes));

	}
	
	function removeCartItems(item,itemsObj){
		var temp_cart_selectedItmes = sessionStorage.getItem("cart_items");
        var ses_cart_selectedItmes = $.parseJSON(temp_cart_selectedItmes);
		console.log(ses_cart_selectedItmes);
		for(var i in ses_cart_selectedItmes){
			if(ses_cart_selectedItmes[i].item == item){
				if(ses_cart_selectedItmes[i].count > 1){
					ses_cart_selectedItmes[i].count --;
				}else{
					ses_cart_selectedItmes.splice(i,1);
				}
			}
		}
		sessionStorage.setItem("cart_items", JSON.stringify(ses_cart_selectedItmes));
		setCartItmes(ses_cart_selectedItmes);
	}
	
	function setCartItmes(itemsObj,couponObj){
		
/*
 *  Cart items
 		<tr>
			<td>
				<a href="#0" class="remove_item"><i class="icon_minus_alt"></i></a> <strong>1x</strong> Enchiladas
			</td>
			<td>
				<strong class="pull-right">$11</strong>
			</td>
		</tr>
*/
		var cart_items = "";
		var total_price = 0,subtotal_price = 0;
		jQuery.each(itemsObj,function(i,values){
//			console.log(itemsObj);
			total_cost = values.cost * values.count;
			total_price = total_price + total_cost;
			subtotal_price = total_price; // subtotal will be before any discount/tax/coupon/delivery
			cart_items += "<tr>"+
							"<td>"+							
								"<a href=\"#0\" class=\"remove_item\" onclick='cartfiller("+values.item+",\""+values.name+"\","+values.cost+")'><i class=\"icon_plus_alt\"></i></a><strong class=\"cart_count_box\"> "+ values.count +" </strong><a href=\"#0\" class=\"remove_item\" onclick=\"removeCartItems("+values.item+",cart_selectedItmes);\"><i class=\"icon_minus_alt\"></i></a> "+ values.name +""+
							"</td>"+
							"<td>"+
								"<strong class=\"pull-right\">&#8377; "+total_cost+"</strong>"+
							"</td>"+
						"</tr>";
		});
		
/*
 * Subtotal - delivery charges - Total
 	<tr>
		<td>
			 Subtotal <span class="pull-right">$56</span>
		</td>
	</tr>
	<tr>
		<td>
			 Delivery fee <span class="pull-right">Free</span>
		</td>
	</tr>
	<tr>
		<td class="total">
			 TOTAL <span class="pull-right">$66</span>
		</td>
	</tr>*/
		var couponTxt = "";
		var couponAmt = 0,cCode = "";
		var update_couponDetails = {};
		// alert(JSON.stringify(couponObj));
		/*-------------------coupon calculation-------------------*/
		if(couponObj != undefined){
			cCode = couponObj.couponDetails.cCode;

			if(couponObj.couponDetails.cType == 'fixed'){
				total_price = total_price - couponObj.couponDetails.cValue;
				couponAmt = couponObj.couponDetails.cValue;
			}else if(couponObj.couponDetails.cType == 'percentage'){
				couponAmt = (total_price*(couponObj.couponDetails.cValue/100));
				couponAmt = Math.round(couponAmt);
				total_price = total_price - couponAmt;
				total_price = Math.round(total_price);
			}
			couponTxt = "<td>"+
							 "Coupon Applied <span class=\"pull-right\">&#8377; "+ couponAmt +"</span>"+
						"</td>";
		}

		/*-------------general add/edit/delete of items with/without coupon-------------*/
		var price_items = "<tr>"+
								"<td>"+
									 "Subtotal <span class=\"pull-right\">&#8377; "+subtotal_price+"</span>"+
								"</td>"+
							"</tr>"+
							"<tr>"+
								"<td>"+
									 "Delivery fee <span class=\"pull-right\">free</span>"+
								"</td>"+
							"</tr>"+
							"<tr id=\"cCode_list\"></tr>"+
							"<tr>"+
								"<td class=\"total\">"+
									 "TOTAL <span class=\"pull-right\">&#8377; "+total_price+"</span>"+
								"</td>"+
							"</tr>";	

		jQuery("#HMcart tbody").html(cart_items);
		jQuery("#cart_break_1, #options_2, #cart_break_2").show();
		jQuery("#cart_total_price tbody").html(price_items);
		jQuery("#cCode_list").html(couponTxt);

		// Update the NEW 'uCart_items' cart after discount
		update_couponDetails.cValue = couponAmt;				
		update_couponDetails.cCode = cCode;	
		update_coupon_cartfiller(update_couponDetails); // for payment page display & confirm order: use this


	}

	function cart_load(){
		var temp_cart_selectedItmes = sessionStorage.getItem("cart_items");
		if(temp_cart_selectedItmes != null){
	        var ses_cart_selectedItmes = $.parseJSON(temp_cart_selectedItmes);
			setCartItmes(ses_cart_selectedItmes);
		}

	}

	function cart_clear(){
		if(userInfo_glb != undefined){
			sessionStorage.removeItem("cart_items");
		}
	}
	
	function redir(link){
		window.location.replace(link);
	}

	function getCookieVal(cookieName){
		var cookieInfo = Cookies.getJSON(cookieName);
		return cookieInfo;
	}

	function currentDate(){
	    var currentdate = new Date(); 
		var datetime = 	currentdate.getDate() + "/"
		                + (currentdate.getMonth()+1)  + "/" 
		                + currentdate.getFullYear() + " @ "  
		                + currentdate.getHours() + ":"  
		                + currentdate.getMinutes() + ":" 
		                + currentdate.getSeconds();

		var date = 	currentdate.getDate() + "-"
	                + (currentdate.getMonth()+1)  + "-" 
	                + currentdate.getFullYear();

		var time = 	currentdate.getHours() + ":"  
	                + currentdate.getMinutes() + ":" 
	                + currentdate.getSeconds();
	    var dateTimeObj = {};
	    dateTimeObj.datetime = datetime;
		dateTimeObj.date = date;
	    dateTimeObj.time = time;

		return dateTimeObj;

	}

function forgotPassword(){
// CUSTOM MODAL

	$(".modal").modal('hide');

	var fPass = "<div class=\"modal-dialog\">"+
			        "<div class=\"modal-content modal-popup\">"+
			                "<a href=\"#\" class=\"close-link\"><i class=\"icon_close_alt2\"></i></a>"+
			                "<form class=\"popup-form\">"+
			                    "<div class=\"login_icon\"><i class=\"icon_lock_alt\"></i></div>"+
			                    "<input type=\"email\" class=\"form-control form-white\" placeholder=\"Email\" name=\"fpemail\">"+
			                    "<button type=\"button\" class=\"btn btn-submit\" id=\"myforgotPassword\">Reset Password</button>"+
			                "</form>"+
			        "</div>"+
			    "</div>";
    $("#forgotP").html(fPass);
	$("#forgotP").modal('show');

	$("#myforgotPassword").click(function(){
		var fpemail = $("input[name='fpemail']").val();
		console.log(fpemail);
		hm_alert("Mail server error!!!");
		setTimeout(function() { $('.modal').modal('hide'); }, 2000);
	});

	$('.modal-popup .close-link').click(function(event){
		event.preventDefault();
		$('.modal').modal('hide');
	});
}

function hm_alert(txt){
	var hm_content = "<div class=\"modal-dialog\">"+
			        "<div class=\"modal-content modal-popup\">"+
			                "<a href=\"#\" class=\"close-link\"><i class=\"icon_close_alt2\"></i></a>"+
			                "<form class=\"popup-form\">"+
								"<a href=\"#0\" class=\"alert_txt_a\" id=\"hm_alert_txt\"></a>"+			                	
			                    "<button type=\"button\" class=\"btn btn-submit modal-close-btn\">OK</button>"+
			                "</form>"+
			        "</div>"+
			    "</div>";
    $("#hm_alert").html(hm_content);
    $("#hm_alert_txt").html(txt);
	$("#hm_alert").modal('show');

	$('.modal-popup .close-link, .modal-close-btn').click(function(event){
		event.preventDefault();
		$('.modal#hm_alert').modal('hide');
	});

}

/*----------------------------credentials copy-----------------------------*/

function setLoggedInfo(){
	/*
	 * FUNCTION to check if user is logged in
	 * If logged in, show logout and make orders
	 * Get cookie information of user and get address andd all other info's
	 * if not logged,popup login form or offer coupon and cannot go ahead to address page untill he logs in
	 * 
	 * */
	
	var getLogInfo = Cookies.getJSON('uCode');
	console.log(getLogInfo);
	if(getLogInfo != undefined && getLogInfo.hm1 == true){
		$("#login_link").remove();
		var myHeader = 	"<li><a href=\"about.jsp\">About</a></li>"+
		                "<li><a href=\"mymeal.jsp\">Make My Meal</a></li>"+
						"<li class=\"submenu\">"+
		                    "<a href=\"javascript:void(0);\" class=\"show-submenu\">"+getLogInfo.hm2+"<i class=\"icon-down-open-mini\"></i></a>"+
		                     "<ul>"+
		                        "<li><a href=\"settings.jsp\">My Account</a></li>"+
		                        "<li id=\"myOrder_link\"><a href=\"#\" onclick=\"pastOrders();\">My Orders</a></li>"+
		                        "<li id=\"mymeal_Order_link\"><a href=\"#\" onclick=\"myMeals();\">My Subscription</a></li>"+
								"<li><a href=\"#\" onclick=\"logout();\">Logout</a></li>";
						    "</ul>"+
		                "</li>";
		$("#head_menu").html(myHeader);
		
		// $("#head_menu").html("<li><a href=\"about.jsp\">About us</a></li><li><a href=\"faq.jsp\">Faq</a></li><li id=\"myOrder_link\"><a href=\"#\" onclick=\"pastOrders();\">My Orders</a></li><li><a href=\"#\" onclick=\"logout();\">Logout</a></li>");
//		$("#head_menu li:nth-child(3)").html("<a href=\"#\" onclick=\"logout();\">Logout</a>");
		$(".modal").modal('hide');
	}else{
		$("#myOrder_link").remove();
		//Login is added by default in html
//		$("#head_menu li:nth-child(3)").html("<a href=\"#0\" data-toggle=\"modal\" data-target=\"#login_2\">Login</a>");		

	}
}

function disEle(ele){
	//disable element
	$(ele).prop('disabled', true);
}

function enbEle(ele){
	//enable element
	$(ele).prop('disabled', false);
}


function pastOrders(){
	var getLogInfo = Cookies.getJSON('uCode');
	if(getLogInfo != undefined){
		redir('my_orders.jsp');
	}
}


function myMeals(){
	var getLogInfo = Cookies.getJSON('uCode');
	if(getLogInfo != undefined){
		redir('my_mealplans.jsp');
	}
}

function logout(){
	removeCreds();
	cart_clear();
	redir('index.jsp');
}

function removeCreds(){
/*	document.cookie = "uCode=; expires=; path=/";
    document.cookie = "uName=; expires=; path=/";
    sessionStorage.removeItem('user');	
*/
	Cookies.remove('uCode', { path: '/' });
}
/*----------------------------credentials copy ends-----------------------------*/



/*--------------------------------mymeal_subscription---------------------------------*/
    function mymeal_cartfiller(cartObj){

            var cartFill = "<tr>"+
                                "<td>"+
                                    "<a href=\"#0\" class=\"remove_item\"></a> <strong>" + cartObj.comboDetails.cType +" + "+ cartObj.subscibe.subPlanTxt + "</strong>"+
                                "</td>"+
                                "<td>"+
                                    "<strong class=\"pull-right\"></strong>"+
                                "</td>"+
                            "</tr>";
            $("#HMcart tbody").html(cartFill);

            var cartSubTotal = "<tr>"+
                            "<td>"+
                                "Subtotal <span class=\"pull-right\">&#8377; "+ cartObj.subscibe.price +"</span>"+
                            "</td>"+
                        "</tr>"+
                        "<tr>"+
                            "<td>"+
                                "Delivery fee <span class=\"pull-right\"> </span>"+
                            "</td>"+
                        "</tr>"+
                        "<tr>"+
                            "<td class=\"total\">"+
                                "TOTAL <span class=\"pull-right\">&#8377; "+ cartObj.subscibe.price +"</span>"+
                            "</td>"+
                        "</tr>";
            $("#cart_total_price tbody").html(cartSubTotal);

    }


/*--------------------------------mymeal_subscription ends---------------------------------*/
