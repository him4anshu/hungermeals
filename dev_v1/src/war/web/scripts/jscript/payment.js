/*
 * Global Variables
 * */

	var URL_gbl = "../services/userservices/";
    var userInfo_glb = Cookies.getJSON('uCode'); // use this instead of cookie calls repeatedly


$(document).ready(function(){
	if(Cookies.getJSON('uCode') != undefined){
  		cart_load();
  		setLoggedInfo();
        flying_cart();

        /*-----------------coupon---------------*/
	    $('#c_code').keyup(function() {
	    	$(this).val($(this).val().toUpperCase());
	        $('#couponclear').toggle(Boolean($(this).val()));
	    });
		$('#couponclear').toggle(Boolean($("#c_code").val()));
			$('#couponclear').click(function() {
			$('#c_code').val('').focus();
			$(this).hide();
			console.log('coupon clear here');
		});

        $("#cCode").click(function(){
			var temp_cart_selectedItmes = sessionStorage.getItem("cart_items");
	        var ses_cart_selectedItmes = $.parseJSON(temp_cart_selectedItmes);
	        var isCartValid = false;
	        $.each(ses_cart_selectedItmes, function(i,val){
	        	if(val.category != 'Add On'){
	        		isCartValid = true;
	        	}
	        });
        	// alert(JSON.stringify(ses_cart_selectedItmes));
        	if(isCartValid == true){
				var couponObj = {}, tempCouponTxn = {};
				tempCouponTxn.uCode = userInfo_glb.hm4;
				tempCouponTxn.couponCode = $('#c_code').val();
				couponObj.couponTxn = tempCouponTxn;

			// var response = '{"couponTxn":{"couponAppliedStatus":"Verified","couponId":1,"couponValue":150,"couponValueType":"fixed","responseStatus":{"responseCode":"HM105","responseMessage":"Valid Coupon"},"resue_attempt":1,"use_attempt":0}}';

				$.ajax({
						url:URL_gbl+"applyCouponCode.json",
						type:"POST",
						contentType: "application/json",
						crossDomain: true,
					  	data: JSON.stringify(couponObj),
					    success:function(response){
					    	// console.log(response);
					    	set_couponStatus(response);
					    },
					    error:function(XHR, textStatus, errorThrown){
					    	alert("Error Occured: "+errorThrown);
					    }

				   });
				

        	}else{
        		alert("please add menu items to checkout!!!");
        		redir('index.jsp');
        	}
        });


        /*-----------------coupon ends---------------*/


		$("#order_confirm_btn").click(function(e){
			e.preventDefault();
			$("#myLoginSpin").show();
			$("#order_confirm_btn").addClass('not-active');

			//from the update_coupon_cartfiller() along with the coupon discount if any. instead of cart_items use uCart_items due to coupon adding
			var temp_cart_selectedItmes = sessionStorage.getItem("uCart_items");
	        var ses_cart_selectedItmes = $.parseJSON(temp_cart_selectedItmes);

			var getLogInfo = Cookies.getJSON('uCode');

			var finalObj = {},tmpfinalObj = {},userObj = {},addrObj = {},menuArr = [],orderObj = {};
			//Delivery Address
			addrObj.addressId = getLogInfo.delAddr;
			addrObj.deliverySlot = getLogInfo.delSlot;
			addrObj.deliveryTime = getLogInfo.delSlot;
			addrObj.phone = getLogInfo.hm5;
			addrObj.city = getLogInfo.city;
			addrObj.pCode = getLogInfo.pCode;

			//User
			userObj.uName = getLogInfo.hm2;
			userObj.uCode = getLogInfo.hm4;
			userObj.address = addrObj;
			userObj.mobile = getLogInfo.hm5;
			userObj.email = getLogInfo.hm2;
			userObj.firstName = getLogInfo.hm8;

			//menu
			var subtotal = 0,tot_amt = 0;
			var couponPC = 0; // set this in int(percentage) like 10/25/50 to get the coupon discount
			var cCode = "", cValue = 0;  //from the update_coupon_cartfiller() //WORKING with session and calucation
			jQuery.each(ses_cart_selectedItmes, function(i,val){
				// alert(JSON.stringify(val));
				var tempObj =  {};
				tempObj.itemId = val.item;
				tempObj.numberOfItems = val.count;
				tempObj.itemName = val.name;
				tempObj.itemCost = val.cost;

				menuArr.push(tempObj);
				subtotal = subtotal + (val.count * val.cost);
				if(val.cCode){
				// if coupon is valid
					tot_amt = subtotal - val.cValue;
					cCode = val.cCode;
				}else{
					tot_amt = subtotal;
					cCode = null;
				}

			});

			//Order
			orderObj.paymentMode = $("input[type='radio'][name='payment_method']:checked").val();
			orderObj.deliveryTime = getLogInfo.delSlot;
			orderObj.deliverySlot = getLogInfo.delSlot;
			orderObj.couponCode = cCode;
			orderObj.subtotal = subtotal.toString();
			orderObj.deliveryCharges = "0";
			orderObj.totalAmount = tot_amt.toString();

			tmpfinalObj.user = userObj;
			tmpfinalObj.itemList = menuArr;
			tmpfinalObj.orderInfo = orderObj;

			finalObj.orderDetails = tmpfinalObj;    			
			console.log(JSON.stringify(finalObj));

	/*
	* Template
			finalObj = {
						  "orderDetails": {
						    "user": {
						      "uName": "varunsk23@gmail.com",
						      "uCode": "011ec63d-a1ef-42f6-93d3-e1d343536a9b",
						      "address": {
						        "addressId": "2",
						        "phone": "8880588801"
						      }
						    },
						    "itemList": [
						      {
						        "itemId": "123",
						        "numberOfItems": "2"
						      },
						      {
						        "itemId": "123",
						        "numberOfItems": "2"
						      }
						    ],
						    "orderInfo": {
						      "paymentMode": "COD",
						      "deliveryCharges": "30",
						      "totalAmount": "230",
						      "deliveryTime": "2016-01-12 05:10:08",
						      "couponCode": "ABCQ"
						    }
						  }
						};*/
					// console.log(JSON.stringify(finalObj));
			    	// localStorage.setItem("orderPay", JSON.stringify(finalObj));

			
			order_confirm_call(finalObj);
		   
		});
	}else{
		redir('index.jsp');
	}
});

function order_confirm_call(finalObj){
	var checkPayMode = $("input[type='radio'][name='payment_method']:checked").val();
	// console.log(finalObj);
	$.ajax({
			url:URL_gbl+"orderConfirm.json",
			type:"POST",
			contentType: "application/json",
			crossDomain: true,
		  	data: JSON.stringify(finalObj),
		    success:function(data){
				$("#myLoginSpin").hide();
				$("#order_confirm_btn").removeClass('not-active');
		    	console.log(JSON.stringify(data));
		    	if(data.orderStatus.responseStatus.responseCode == 'HM200'){
	    			console.log(JSON.stringify(finalObj));
			    	finalObj.orderStatus = data.orderStatus;

			    	if(checkPayMode == "COD"){
				    	localStorage.setItem("orderId", JSON.stringify(finalObj));
						redir('o_confirm.jsp');
			    	}else if(checkPayMode == "PAYTM"){
				    	// localStorage.setItem("orderPay", JSON.stringify(finalObj));
				    	setPaytm(finalObj);
				    	// redir('loadingPayment.jsp');
				    	//delete orderPay session
			    	}else if(checkPayMode == "PAYU"){
				    	// localStorage.setItem("orderPay", JSON.stringify(finalObj));
				    	setPayu(finalObj);
				    	// redir('loadingPayment.jsp');
				    	//delete orderPay session
			    	}
		    	}else{
		    		alert(data.orderStatus.responseStatus.responseMessage);
			    	// redir('error.jsp');

		    	}
		    },
		    error:function(XHR, textStatus, errorThrown){
		    	console.log("Error Occured: "+errorThrown);
		    	// redir('error.jsp');
		    }

	   });
}

function setPaytm(finalObj){
	//var url = "https://pguat.paytm.com/oltp-web/processTransaction";
	var url = "../jsp/paytmRedirect.jsp";
	var inputs = "";
	jQuery.each(finalObj.orderStatus.walletRequest.entry, function(i,val){
				var key_value = (val.value)? val.value : ""; 
				// $("input[name='"+val.key+"']").val(key_value);
				inputs += "<input type='hidden' name='"+val.key+"' value='"+key_value+"'>";				
	});
	var form = $('<form action="' + url + '" method="post">' +
					inputs+
				'</form>');
	$('body').append(form);
	form.submit();
}

function setPayu(finalObj){
	//var url = "https://secure.payu.in/_payment";
	var url = "../jsp/payuRedirect.jsp";
	var inputs = "";
	jQuery.each(finalObj.orderStatus.walletRequest.entry, function(i,val){
				var key_value = (val.value)? val.value : ""; 
				// $("input[name='"+val.key+"']").val(key_value);
				inputs += "<input type='hidden' name='"+val.key+"' value='"+key_value+"'>";				
	});
	var form = $('<form action="' + url + '" method="post">' +
					inputs+
				'</form>');
	$('body').append(form);
	form.submit();
}

function set_couponStatus(couponObj){
	console.log(couponObj);
	// couponObj = $.parseJSON(couponObj);  // for testing from variable


	var prepCouponObj = {},tempPrepCouponObj = {};
	if(couponObj.couponTxn.responseStatus.responseCode == 'HM105'){
		tempPrepCouponObj.couponId = couponObj.couponTxn.couponId;
		tempPrepCouponObj.cCode = $('#c_code').val();
		tempPrepCouponObj.cValue = couponObj.couponTxn.couponValue;
		tempPrepCouponObj.cType = couponObj.couponTxn.couponValueType;
		tempPrepCouponObj.statusTxt = "Coupon applied successfully";

		prepCouponObj.couponDetails = tempPrepCouponObj;

		var temp_cart_selectedItmes = sessionStorage.getItem("cart_items");
        var ses_cart_selectedItmes = $.parseJSON(temp_cart_selectedItmes);

        document.getElementById('cCode').innerHTML = "Coupon Applied";

        setCartItmes(ses_cart_selectedItmes,prepCouponObj);

	}else if(couponObj.couponTxn.responseStatus.responseCode == 'HM104'){
		$('#c_code').val("");
		$("#couponclear").hide();
		alert("Invalid coupon");
	}else if(couponObj.couponTxn.responseStatus.responseCode == 'HM106'){
		$('#c_code').val("");
		$("#couponclear").hide();
		alert("Coupon already used");
	}else if(couponObj.couponTxn.responseStatus.responseCode == 'HM107'){
		$('#c_code').val("");
		$("#couponclear").hide();
		alert("Coupon expired");
	}else{
		$('#c_code').val("");
		$("#couponclear").hide();
		alert("Something went wrong,please get in touch with us to resolve coupon issue!!");
	}
}
