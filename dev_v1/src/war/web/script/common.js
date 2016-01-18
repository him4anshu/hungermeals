/*
 * Global Variables
 * */
	var	cart_selectedItmes = [];


$(function() {
    var availablePins = [
        {
            "id": 1,
            "value": "yemlur, Bellandur, 560037",
            "area": "bellandur",
            "place": "bangalore",
            "data": 560037
          },
          {
            "id": 2,
            "value": "ecospace, Bellandur, 560037",
            "area": "bellandur",
            "place": "bangalore",
            "data": 560037
          }
    ];
/*    $( "#searchPin" ).autocomplete({
      source: availablePins,
      select: function(event, ui){
         	console.log(ui.item);
          //navigate to menu page
        }
    });*/
    
    
    
    
    
    var menu_items = {
    	  "items": [
    		    {
    		      "id": 1,
    		      "category": "starters",
    		      "name": "Baby Corn Chilly",
    		      "desc": "Roasted babycorns with fresh guntur chilly.",
    		      "cost": 40
    		    },
    		    {
    		      "id": 2,
    		      "category": "starters",
    		      "name": "Gobi Chilly",
    		      "desc": "Roasted gobi with fresh guntur chilly.",
    		      "cost": 40
    		    },
    		    {
    		      "id": 3,
    		      "category": "main_courses",
    		      "name": "North Thali",
    		      "desc": "2Roti + 2Curry + 1Curd + 1Sweet",
    		      "cost": 80
    		    },
    		    {
    		      "id": 4,
    		      "category": "desserts",
    		      "name": "Payassa",
    		      "desc": "Shavige payassa.",
    		      "cost": 40
    		    },
    		    {
    		      "id": 5,
    		      "category": "desserts",
    		      "name": "Kheer",
    		      "desc": "North Style",
    		      "cost": 40
    		    }
    		  ]
    		};
    		
//    		alert(JSON.stringify(menu_items));
    		/*
    		 *--------------TEMPLATE for menu------------- 
 					<tr>
						<td>
							<h5>1. Baby Corn Chilly</h5>
							<p>
								Roasted babycorns with fresh guntur chilly.
							</p>
						</td>
						<td>
							<strong>&#8377; 40</strong>
						</td>
						<td class="options">
							<a href="#0"><i class="icon_plus_alt2"></i></a>
						</td>
					</tr>
    		 *
    		 */
    		var starters_list = "",main_courses_list = "",desserts_list = "";
    		$.each(menu_items.items, function(index,values){
    			if(values.category == 'starters'){
        			starters_list += "<tr>"+
										"<td>"+
											"<h5>"+(index+1)+". "+values.name+"</h5>"+
											"<p>"+ values.desc +"</p>"+
										"</td>"+
										"<td>"+
											"<strong>&#8377; "+ values.cost +"</strong>"+
										"</td>"+
										"<td class=\"options\">"+
											"<a href=\"#0\" onclick='cartfiller("+values.id+",\""+values.name+"\","+values.cost+")'><i class=\"icon_plus_alt2\"></i></a>"+
										"</td>"+
									"</tr>";
    			}else if(values.category == 'main_courses'){
        			main_courses_list += "<tr>"+
										"<td>"+
											"<h5>"+(index+1)+". "+values.name+"</h5>"+
											"<p>"+ values.desc +"</p>"+
										"</td>"+
										"<td>"+
											"<strong>&#8377; "+ values.cost +"</strong>"+
										"</td>"+
										"<td class=\"options\">"+
											"<a href=\"#0\" onclick='cartfiller("+values.id+",\""+values.name+"\","+values.cost+")'><i class=\"icon_plus_alt2\"></i></a>"+
										"</td>"+
									"</tr>";
    			}else if(values.category == 'desserts'){
    				desserts_list += "<tr>"+
										"<td>"+
											"<h5>"+(index+1)+". "+values.name+"</h5>"+
											"<p>"+ values.desc +"</p>"+
										"</td>"+
										"<td>"+
											"<strong>&#8377; "+ values.cost +"</strong>"+
										"</td>"+
										"<td class=\"options\">"+
											"<a href=\"#0\" onclick='cartfiller("+values.id+",\""+values.name+"\","+values.cost+")'><i class=\"icon_plus_alt2\"></i></a>"+
										"</td>"+
									"</tr>";
    			}

    		});
    		
    		$("#starters_list tbody").html(starters_list);
    		$("#main_courses_list tbody").html(main_courses_list);
    		$("#desserts_list tbody").html(desserts_list);
    		
    		
}); /*document.ready end*/
  
	function cartfiller(itemId,itemName,itemCost){
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
					"cost": itemCost
				});
		console.log(cart_selectedItmes);
		sessionStorage.setItem("cart_items", JSON.stringify(cart_selectedItmes));
		setCartItmes(cart_selectedItmes);
		
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
	
	function setCartItmes(itemsObj){
		
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
		var total_price = 0;
		jQuery.each(itemsObj,function(i,values){
//			console.log(itemsObj);
			total_cost = values.cost * values.count;
			total_price = total_price + total_cost;
			cart_items += "<tr>"+
							"<td>"+
								"<a href=\"#0\" class=\"remove_item\" onclick=\"removeCartItems("+values.item+",cart_selectedItmes);\"><i class=\"icon_minus_alt\"></i></a> <strong>"+ values.count +" x</strong> "+ values.name +""+
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

		var price_items = "<tr>"+
								"<td>"+
									 "Subtotal <span class=\"pull-right\">&#8377; "+total_price+"</span>"+
								"</td>"+
							"</tr>"+
							"<tr>"+
								"<td>"+
									 "Delivery fee <span class=\"pull-right\">Free</span>"+
								"</td>"+
							"</tr>"+
							"<tr>"+
								"<td class=\"total\">"+
									 "TOTAL <span class=\"pull-right\">&#8377; "+total_price+"</span>"+
								"</td>"+
							"</tr>";		
		
		jQuery("#HMcart tbody").html(cart_items);
		jQuery("#cart_break_1, #options_2, #cart_break_2").show();
		jQuery("#cart_total_price tbody").html(price_items);


	}

	function cart_load(){
		var temp_cart_selectedItmes = sessionStorage.getItem("cart_items");
        var ses_cart_selectedItmes = $.parseJSON(temp_cart_selectedItmes);
		setCartItmes(ses_cart_selectedItmes);

	}
	
	function addr_delivery_page(){
		
		$("#add_new_addr").click(function(){
			jQuery(".new_addr_group").show();
			jQuery("#select_delivery_slot").hide();
		});
		
		$("#save_new_addr").click(function(){
			var nullFlag = 0;
			var HM_name = ($("input[name='HM_name']").val())? $("input[name='HM_name']").val() : nullFlag++;
			var HM_mob = ($("input[name='HM_mob']").val())? $("input[name='HM_mob']").val() : nullFlag++;
			var HM_address1 = ($("input[name='HM_address1']").val())? $("input[name='HM_address1']").val() : nullFlag++;
			var HM_address2 = ($("input[name='HM_address2']").val())? $("input[name='HM_address2']").val() : nullFlag++;
			var HM_city = ($("input[name='HM_city']").val())? $("input[name='HM_city']").val() : nullFlag++;
			var HM_pcode = ($("input[name='HM_pcode']").val())? $("input[name='HM_pcode']").val() : nullFlag++;

			if(nullFlag === 0){
    			$(".err_addr").html("");

    			var save_addrObj = {};
				save_addrObj.lCode = "BD8S9SA";
				save_addrObj.name = HM_name;
				save_addrObj.mobile =  parseInt(HM_mob);
				save_addrObj.addr1 = HM_address1;
				save_addrObj.addr2 = HM_address2;
				save_addrObj.city = HM_city;
				save_addrObj.pcode =  parseInt(HM_pcode);

				console.log(save_addrObj);
				//SEND ajax call here
				//on success reload,default selected 2nd address
				
			}else{
    			$(".err_addr").html("<span class=\"error_text\">Please fill remaining "+nullFlag+" details!!</span>");
			}
		});
		
		/*-----------------Delivery slot criteria as per HUNGERMEALS-----------------------------*/
		var myDate = new Date();
		var p_year    = myDate.getFullYear();
	    var p_month   = myDate.getMonth()+1; 
	    var p_day     = myDate.getDate();
	    if(p_month.toString().length == 1) {
	        var p_month = '0'+p_month;
	    }
	    if(p_day.toString().length == 1) {
	        var p_day = '0'+p_day;
	    } 
	    var past_dateTime = p_year+'/'+p_month+'/'+p_day;

		var currentDateTime = new Date();
		var logic = function ( currentDateTime, $sel_deliveryDay ){
  	    		var myDate = new Date();
				if(currentDateTime.getDate() != myDate.getDate()){
					//if selected date is not same as current Date show full list
    			    this.setOptions({
       			    	timepicker:true,
    			    	allowTimes:['11:00','12:00','13:00','14:00','15:00','16:00','17:00','18:00']
      			    });
				}else if(currentDateTime.getDate() == myDate.getDate()){
					//if selected date is same as current date, check the current time and set the slots till 18:00
					var iHour = currentDateTime.getHours();
					if(iHour < 11){
						allowArr = ['11:00','12:00','13:00','14:00','15:00','16:00','17:00','18:00'];
           			    this.setOptions({
           			    	timepicker:true,
        			    	allowTimes:allowArr
          			    });
					}else if(iHour = 11 || iHour < 18){
						var allowArr = [];
						for(var i=iHour;i<=9;i++){
							allowArr.push(i+":00");
						}
           			    this.setOptions({
           			    	timepicker:true,
        			    	allowTimes:allowArr
          			    });
					}else{
						var p_year    = currentDateTime.getFullYear();
					    var p_month   = currentDateTime.getMonth()+1; 
					    var p_day     = currentDateTime.getDate();
					    if(p_month.toString().length == 1) {
					        var p_month = '0'+p_month;
					    }
					    if(p_day.toString().length == 1) {
					        var p_day = '0'+p_day;
					    } 
					    var past_dateTime = p_year+'/'+p_month+'/'+p_day;
           			    this.setOptions({
           			    	timepicker:false,
           			    	disabledDates:[past_dateTime],
           			    	minDate: past_dateTime,
           			    	
          			    });
					}
					
				}
			};
			
			$sel_deliveryDay = jQuery('#deliveryDay');
	  		jQuery('#deliveryDay').datetimepicker({
    			onGenerate:function( ct ){
    				$(this).find('.xdsoft_date.xdsoft_weekend')
    					.addClass('xdsoft_disabled');
    			},
	  			onChangeDateTime: logic,
	  			onShow: logic,
			    	minDate: past_dateTime,

		    });
    		/*-----------------End Delivery slot criteria as per HUNGERMEALS-----------------------------*/
	}
	
	function order_confirm_load(){
			// get the just saved order id from session
			// pass it here and set the content
		
		
		var ordered_items = {
			  "order_by": "Varun.s.Kumar",
			  "order_ref_id": "BD8SV001_20160102",
			  "order_date": "2016/01/02",
			  "order_time": "07:20 PM",
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
			    },
			    {
			      "id": 2,
			      "name": "Gobi Chilly",
			      "count": 4,
			      "cost": 40
			    },
			    {
			      "id": 3,
			      "name": "North Thali",
			      "count": 2,
			      "cost": 80
			    },
			    {
			      "id": 4,
			      "name": "Payassa",
			      "count": 1,
			      "cost": 40
			    },
			    {
			      "id": 5,
			      "name": "Kheer",
			      "count": 2,
			      "cost": 40
			    }
			  ]
			};

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

			var thank_you_note = "Bon appetit!!! Mr. <strong> "+ ordered_items.order_by +"</strong>, Master Chef's Kitchen will ensure a tasty meal preapred, packed and safely delivery to you for the Order #:<strong> "+ordered_items.order_ref_id+" </strong>at <strong>"+ ordered_items.order_addr+ '-' + ordered_items.order_pCode +"</strong> with special care.";
			
			var ordered_list = "",item_price = 0,total_cost = 0;
			jQuery.each(ordered_items.order_list, function(ind,val){
//				console.log(val);
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

			ordered_list += "<tr>"+
								"<td>"+
								 "Order receivied"+
								"</td>"+
								"<td>"+
									"<strong class=\"pull-right\">"+ordered_items.order_date +" - "+ ordered_items.order_time +"</strong>"+
								"</td>"+
							"</tr>";
				
			ordered_list += "<tr>"+
								"<td>"+
								 "Delivery status"+
								"</td>"+
								"<td>"+
									"<strong class=\"pull-right\"><i class=\"icon-hourglass\"></i><i class=\"icon-truck\"></i><i class=\"icon-food\"></i><i class=\"icon-cancel-alt-filled\"></i><i class=\"icon-frown\"></i>"+ordered_items.order_status_txt +"</strong>"+
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
			
			$("#thank_you_p").html(thank_you_note);
			$("#ordered_tab tbody").html(ordered_list);
	}