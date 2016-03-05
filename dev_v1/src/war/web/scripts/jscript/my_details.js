/*
 * Global Variables
 * */

    var URL_gbl = "../services/userservices/";
    var userInfo_glb = Cookies.getJSON('uCode'); // use this instead of cookie calls repeatedly

$(function() {

var cookObj = getCookieVal('uCode');
	if(cookObj == undefined){
		redir('index.jsp');
	}else{
		if(cookObj.hm1 == true){
			$("#cust_name").html(cookObj.hm2);
		// alert(JSON.stringify(cookObj));
			var user_details = {};
			user_details.user = {
            	userStatus: cookObj.hm1,
				uName: cookObj.hm2,
            	logTime: cookObj.hm3,
				uCode: cookObj.hm4
			};
			// alert(JSON.stringify(user_details));

		   $.ajax({
				url:URL_gbl+"orderHistory.json",
				type:"POST",
				contentType: "application/json",
                crossDomain: true,
			  	data: JSON.stringify(user_details),
			    success:function(data){
			    	//alert(JSON.stringify(data));
			    	getOrderList(data);
			    },
			    error:function(XHR, textStatus, errorThrown){
			    	console.log("Error Occured: "+errorThrown);
                    redir('error.jsp');
			    }
		   });

		}else{
			// if logged in with false state
			redir('index.jsp');
		}

	}

});

    function getOrderList(data){
    	console.log(data);

    	var o_list = "";
    	$.each(data.orderStatus,function(index,val){
    			//1.Placed,2.Confirm,3.Preparing,4.Payment,5.Delivery
    		if(val.orderStatusCode == 1){
    			//Placed
    			var tmp_ord_status =    "<li>Confirm<i class=\"icon-ok-circle order-li-icons no\"></i></li>"+
                                        "<li>Preparing<i class=\"icon-fast-food no order-li-icons\"></i></li>"+
                                        "<li>Payment<i class=\"icon-money-2 no order-li-icons\"></i></li>"+
                                        "<li>Delivery<i class=\"icon-truck no order-li-icons\"></i></li>";

    		}else if(val.orderStatusCode == 2){
    			//Confirm
    			var tmp_ord_status =    "<li>Confirm<i class=\"icon-ok-circle order-li-icons ok\"></i></li>"+
                                        "<li>Preparing<i class=\"icon-fast-food no order-li-icons\"></i></li>"+
                                        "<li>Payment<i class=\"icon-money-2 no order-li-icons\"></i></li>"+
                                        "<li>Delivery<i class=\"icon-truck no order-li-icons\"></i></li>";

    		}else if(val.orderStatusCode == 3){
    			//Preparing
    			var tmp_ord_status =    "<li>Confirm<i class=\"icon-ok-circle order-li-icons ok\"></i></li>"+
                                        "<li>Preparing<i class=\"icon-fast-food ok order-li-icons\"></i></li>"+
                                        "<li>Payment<i class=\"icon-money-2 no order-li-icons\"></i></li>"+
                                        "<li>Delivery<i class=\"icon-truck no order-li-icons\"></i></li>";

    		}else if(val.orderStatusCode == 4){
    			//Payment
    			var tmp_ord_status =    "<li>Confirm<i class=\"icon-ok-circle order-li-icons ok\"></i></li>"+
                                        "<li>Preparing<i class=\"icon-fast-food ok order-li-icons\"></i></li>"+
                                        "<li>Payment<i class=\"icon-money-2 ok order-li-icons\"></i></li>"+
                                        "<li>Delivery<i class=\"icon-truck no order-li-icons\"></i></li>";

    		}else if(val.orderStatusCode == 5){
    			//Delivery
    			var tmp_ord_status =    "<li>Confirm<i class=\"icon-ok-circle order-li-icons ok\"></i></li>"+
                                        "<li>Preparing<i class=\"icon-fast-food ok order-li-icons\"></i></li>"+
                                        "<li>Payment<i class=\"icon-money-2 ok order-li-icons\"></i></li>"+
                                        "<li>Delivery<i class=\"icon-truck ok order-li-icons\"></i></li>";

    		}

        o_list += "<div class=\"strip_list wow fadeIn\" data-wow-delay=\"0.1s\">"+
                            "<div class=\"row\">"+
                                "<div class=\"col-md-9 col-sm-9\">"+
                                    "<div class=\"desc\">"+
                                        "<div class=\"thumb_strip\">"+
                                            "<a href=\"#0\" onclick='order_details("+val.orderId+")'><img src=\"../images/hm_icon.png\" alt=\"\"></a>"+
                                        "</div>"+
                                        "<h3>Order #: "+ val.orderId +"</h3>"+
                                        "<div class=\"type\">"+
                                        "</div>"+
                                        "<div class=\"location\">"+
                                            "Total: <i class=\"icon-rupee\"></i>"+val.totalAmount+
                                        "</div>"+
                                        "<ul>"+tmp_ord_status+"</ul>"+
                                    "</div>"+
                                "</div>"+
                                "<div class=\"col-md-3 col-sm-3\">"+
                                    "<div class=\"go_to\">"+
                                        "<div>"+
                                            "<a href=\"#0\" onclick='order_details("+val.orderId+")' class=\"btn_1\">View Details</a>"+
                                        "</div>"+
                                    "</div>"+
                                "</div>"+
                            "</div>"+
                        "</div>";

    	});


                $("#my_ord_list").html(o_list);

    };

    function order_details(id){
        //Get Order Details for following orderid & user
    	 //alert(id);    	
        var cookObj = getCookieVal('uCode');

            var user_details = {}, tempOrderDetails = {};
            tempOrderDetails.user = {
                uName: cookObj.hm2,
                uCode: cookObj.hm4
            };
            tempOrderDetails.orderInfo = {
                orderId: id
            };
            user_details.orderDetails = tempOrderDetails;

            $.ajax({
                url:URL_gbl+"orderDetails.json",
                type:"POST",
                contentType: "application/json",
                crossDomain: true,
                data: JSON.stringify(user_details),
                success:function(data){
                    // alert(JSON.stringify(data));
                    localStorage.setItem("orderId", JSON.stringify(data));
                    redir('my_order_details.jsp');

                },
                error:function(XHR, textStatus, errorThrown){
                    console.log("Error Occured: "+errorThrown);
                }
           });

    	/*// in success localStore orderId
    	var data = {
				  "orderDetails": {
				    "user": {
				      "uName": "varunsk23@gmail.com",
				      "uCode": "011ec63d-a1ef-42f6-93d3-e1d343536a9b",
				      "address": {
				        "addressId": "13",
				        "deliverySlot": "2016-01-25 12:00",
				        "phone": "8880588801",
				        "city": "Bangalore",
				        "pCode": "560037"
				      }
				    },
				    "itemList": [
				      {
				        "itemId": 4,
				        "numberOfItems": 1,
				        "itemName": "Non Veg Andhra Meals",
				        "itemCost": 170
				      },
				      {
				        "itemId": 7,
				        "numberOfItems": 1,
				        "itemName": "Pepper Chicken",
				        "itemCost": 920
				      }
				    ],
				    "orderInfo": {
				      "paymentMode": "COD",
				      "deliveryTime": "2016-01-25 12:00",
				      "couponCode": "PAY10",
				      "subtotal": "260",
				      "deliveryCharges": "0",
				      "totalAmount": "260"
				    }
				  },
				  "orderStatus": {
				    "executiveName": "MK Jacob",
				    "executivePhone": 8123719594,
				    "orderId": 42,
				    "orderStatusCode": 0,
				    "orderStatusDesc": "Order placed"
				  }
				};
    	console.log(JSON.stringify(data));
    	localStorage.setItem("orderId", JSON.stringify(data));
    	redir('my_order_details.jsp');*/
    }