/*
 * Global Variables
 * */
	var URL_gbl = "../services/userservices/";
    var userInfo_glb = Cookies.getJSON('uCode'); // use this instead of cookie calls repeatedly

$(document).ready(function() {
	if(userInfo_glb != undefined){

		getLoggedInfo_addr(true);
		addr_delivery_page();
		cart_load();
  		setLoggedInfo();

		$("#addr_next_btn").click(function(e){
			e.preventDefault();
			// alert($("input[type='radio'][name='addr_option']:checked").parent().parent().next('.address_ul').find('li:nth-child(2)').attr('data-phone'));

			if($("#deliveryTime").val() == null || $("#deliveryTime").val() == "" || $("#deliveryTime").val() == "-1"){
				alert("Please select time slot!!");
			}else{
				var sel_date = convertDateFormat($("#deliveryDay").val()) +" "+ $("#deliveryTime").val();


				var add_id = $("input[type='radio'][name='addr_option']:checked").attr('data-addr-id');
				var delvTimeSlot = sel_date;
				var delvPhone = $("input[type='radio'][name='addr_option']:checked").parent().parent().next('.address_ul').find('li:nth-child(2)').attr('data-phone');
				var delvCity = $("input[type='radio'][name='addr_option']:checked").parent().parent().next('.address_ul').find('li:nth-child(5)').attr('data-city'); 
				var delvpCode = $("input[type='radio'][name='addr_option']:checked").parent().parent().next('.address_ul').find('li:nth-child(5)').attr('data-pcode');
				if(add_id == "" || add_id == undefined || add_id  == null || !add_id){
					hm_alert("Please add address to deliver!!!");
				}else if(delvTimeSlot == "" || delvTimeSlot == undefined || delvTimeSlot  == null || !delvTimeSlot){
					hm_alert("Please select delivery time slot!!!");
				}else{
					// update selected slot and address to uCode cookie.
					var getLogInfo = Cookies.getJSON('uCode');

					getLogInfo.delAddr = add_id;
					getLogInfo.delSlot = delvTimeSlot;
					getLogInfo.phone = delvPhone;
					getLogInfo.city = delvCity;
					getLogInfo.pCode = delvpCode;

					Cookies.set('uCode', getLogInfo, { expires: 7, path: '/' });
					redir('payment.jsp');
				}
			}

		});


		//change locality
		$("#address_order").change(function(){
			var sel_pcode = $("#address_order option:selected").parent().attr('data-pcode');
			$("#city_order").val("Bengaluru").prop("disabled",true);
			$("#pcode_oder").val(sel_pcode).prop("disabled",true);
		});
		
	}else{
		redir('index.jsp');
	}
});

	function getLoggedInfo_addr(popup_flag){
		//popup_flag - first time popup_flag will false to give user one chance of login-afterwards redirect to home
		if(userInfo_glb != undefined){
			// if log-in is not there,prompt login
			console.log(userInfo_glb);
			if(userInfo_glb.hm1 == true){
/*
*Address Template
			<label><input type="radio" value="" checked name="addr_option" class="icheck">Home:</label>
			<ul class="address_ul">
				<li class="addr_name">Varun.S.Kumar</li>
				<li class="addr_locality_phone"><i class="icon-phone-squared"></i> 8880588801</li>
				<li class="addr_locality"><i class="icon-location"></i>"ITTINA ANAI APARTMENTS", #18/4, D-103,</li>
				<li class="addr_locality">Kempapura Main road,yamlur post,bellandur,</li>
				<li class="addr_locality">bangalore 560037</li>
			</ul>*/

			var user_details = {};
			var getLogInfo = userInfo_glb;

			user_details.user = {
				uName: getLogInfo.hm2,
				uCode: getLogInfo.hm4
			};
/*
	    	var mdata = {
				  "address": [
				    {
				      "addressId": 2,
				      "city": "Bangalore",
				      "line1BuildingNo": "#18/4 \"ITTINA ANAI APARTMENTS\", D-103,yemlur",
				      "line2StreetNo": "yemlur-kempapura main road,yemlur post,bellandur, after bellandur lake",
				      "name": "Varun Kumar S",
				      "phone": 8880188801,
				      "pCode": 560037
				    },{
				      "addressId": 21,
				      "city": "Bangalore",
				      "line1BuildingNo": "#18/4 D-103,yemlur",
				      "line2StreetNo": "yemlur-kempapura main road,yemlur post,bellandur, after bellandur lake",
				      "name": "Varun Kumar S",
				      "phone": 8880122401,
				      "pCode": 560027
				    }
				  ]
				};

*/
			var addr_list = {};
	    	// addr_list = mdata; //TESTING
	    	// set_addr(addr_list);
		   	$.ajax({
				url:URL_gbl+"getUserAddress.json",
				type:"POST",
				contentType: "application/json",
				crossDomain: true,
				data: JSON.stringify(user_details),
			    success:function(data){
			    	addr_list = data; 
			    	set_addr(addr_list);
			    },
			    error:function(XHR, textStatus, errorThrown){
			    	console.log("Error Occured: "+errorThrown);
			    }
		   	});

			}else{
				// due to some wrong userinfo- call logout and fresh login required from index(keep the cart)
				redir('index.jsp');
			}
		}else{
			//prompt login once,still if user closes login popup without entry then redirect to index
			redir('index.jsp');
		}

	}

	function addr_delivery_page(){
		
		$("#add_new_addr").click(function(){
			jQuery(".new_addr_group").show();
			$('html, body').animate({scrollTop: $(".new_addr_group").offset().top - 100}, 2000);
			flying_cart();
		});
		
		$("#save_new_addr").click(function(){
			$("#save_new_addr").prop("disabled",true);
			$("#myAddrSave").show();

			//VALIDATION
			if($("input[name='HM_name']").val()){
				var HM_name = $("input[name='HM_name']").val();
				if($("#address_order option:selected").val() && $("#address_order option:selected").val() != "-1"){
					var HM_address1 = $("#address_order option:selected").parent().attr('label')+":"+$("#address_order option:selected").val();
					if($("textarea[name='HM_address2']").val()){
						var HM_address2 = $("textarea[name='HM_address2']").val();
						if($("input[name='HM_city']").val()){
							var HM_city = $("input[name='HM_city']").val();				
							if($("input[name='HM_pcode']").val()){
								var HM_pcode = $("input[name='HM_pcode']").val();
								var HM_mob = userInfo_glb.hm5; //username is the verified number
								var validateFlag = true;
							}else{
								validateFlagTxt = "Please choose your valid locality details!!";
								var validateFlag = false;
							}
						}else{
							validateFlagTxt = "Please choose your valid locality details!!";
							var validateFlag = false;
						}
					}else{
						validateFlagTxt = "Please provide full address for better delivery!!";
						var validateFlag = false;
					}					
				}else{
					validateFlagTxt = "Please choose your valid locality details!!";
					var validateFlag = false;
				}
			}else{
				validateFlagTxt = "Please provide your name!!";
				var validateFlag = false;
			}

			if(validateFlag == true){
    			$(".err_addr").html("");

    			var save_addrObj = {},final_saveObj = {}, userInfo_saveObj = {};
				save_addrObj.name = HM_name;
				save_addrObj.phone =  HM_mob;
				save_addrObj.line1BuildingNo = HM_address1;
				save_addrObj.line2StreetNo = HM_address2;
				save_addrObj.city = HM_city;
				save_addrObj.pCode =  parseInt(HM_pcode);

				var tempObj = getCookieVal('uCode');
				// console.log(tempObj);	
				userInfo_saveObj.uName = tempObj.hm2;
				userInfo_saveObj.uCode = tempObj.hm4;
				userInfo_saveObj.address = save_addrObj;

				final_saveObj.user = userInfo_saveObj;

				// console.log(final_saveObj);
				//SEND ajax call here
				//on success reload,default selected 2nd address
			   $.ajax({
					url:URL_gbl+"addUserAddress.json",
					type:"POST",
					contentType: "application/json",
					crossDomain: true,
				  	data: JSON.stringify(final_saveObj),
				    success:function(data){
				    	//alert(JSON.stringify(data));
						$("#save_new_addr").prop("disabled",false);
						$("#myAddrSave").hide();
						$(".new_addr_group").hide();
				    	getLoggedInfo_addr(true);
						reset_addrFields();
						$('html, body').animate({scrollTop: $(document).height() + 100}, 2000);
				    },
				    error:function(XHR, textStatus, errorThrown){
				    	console.log("Error Occured: "+errorThrown);
				    }

			   });
				
			}else{
				$("#save_new_addr").prop("disabled",false);
				$("#myAddrSave").hide();
    			$(".err_addr").html("<span class=\"error_text\">"+validateFlagTxt+"</span>");
			}
		});
		
		/*-----------------Delivery slot criteria as per HUNGERMEALS-----------------------------*/
	/*	var myDate = new Date();
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
		var logic = function ( $sel_deliveryDay ){
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
	  			onChangeDateTime: logic,
	  			onShow: logic,
			    minDate: past_dateTime,
			    format:'Y-m-d H:i'

		    }); 
    		/*-----------------End Delivery slot criteria as per HUNGERMEALS-----------------------------*/

    		/*-----------------Delivery slot criteria -2 as per HUNGERMEALS-----------------------------*/
	        $('#deliveryDay').multiDatesPicker({
	            minDate: 0,
	            maxDate: 30,
	            maxPicks: 1,
	            altField: '#alt_sel_dates',
	            onSelect: function(dateText){
	         		var mydates = $("#alt_sel_dates").val();
	         		set_timeslot(mydates);
	            }
	        });

    		/*-----------------End Delivery slot criteria -2 as per HUNGERMEALS-----------------------------*/
	}

	function set_timeslot(selDate){
		var date1 = new Date();
		var date2 = new Date(selDate.toString());
		if(date2.getTime() - date1.getTime() < 0){
			diffDays = 0;
		}else{
			var timeDiff = Math.abs(date2.getTime() - date1.getTime());
			var diffDays = Math.ceil(timeDiff / (1000 * 3600 * 24)); 
		}

		//SET the timeslot
		// alert(isNaN(diffDays));
		if(isNaN(diffDays) === false){
			$("#deliveryTime").prop("disabled",false);
			var timeslots = ['11:00','12:00','13:00','14:00','15:00','16:00','17:00','18:00'];
			var options = "<option value=\"-1\" disabled selected>Select time-slot</option>";
			if(diffDays == 0){
				// alert(date1.getHours());
				if(date1.getHours() < 11 ){
					$.each(timeslots, function(i,val){
						options += "<option value="+val+">"+val+"</option>"
					});
				}else if(date1.getHours() >= 19){
					alert("Sorry we have just closed!! Kindly choose another date.");

				}else{
					//clear the array and re-build remaining time
					timeslots = [];
					for(var i = date1.getHours();i<=19;i++){
						j = i + 2;
						timeslots.push(j+":00");
					}
					if(timeslots.length == 0){
						alert("Sorry we are closed for selected date!! Kindly choose another date.");
					}

					$.each(timeslots, function(i,val){
						options += "<option value="+val+">"+val+"</option>"
					});
				}
			}else if(diffDays > 0){
					$.each(timeslots, function(i,val){
						options += "<option value="+val+">"+val+"</option>"
					});
			}
		}else{
			$("#deliveryTime").prop("disabled",true);
		}

		$("#deliveryTime").empty().html(options);
		date1 = "";
		date2 = "";
		// set time based on diffDays if 0 get the current time and set until 4PM, if > 1, show all ['11:00','12:00','13:00','14:00','15:00','16:00','17:00','18:00']


	}
	
	function set_addr(addr_list){
		// alert(JSON.stringify(addr_list));
		if($.isEmptyObject(addr_list.address) == true){
			$("#address-group").html("<h4 class=\"no_address_banner\"><i class=\"icon-address\"></i>Please add address to deliver.</h4>");
		}else{
			
			var addr_result = "<label>Saved Address:</label>";

			jQuery.each(addr_list.address,function(i,key){
				var j = i + 1;
				// alert(JSON.stringify(key));
				addr_result += "<div class=\"form-group\">"+
									"<label id=\"addr"+j+"radioLabel\">"+
										"<input id=\"addr"+j+"radio\" type=\"radio\" value=\""+j+"\" name=\"addr_option\" class=\"icheck\" data-addr-id=\""+key.addressId+"\"><span>Address "+j+":</span>"+
									"</label>"+
									"<ul id=\"addr"+j+"\" class=\"address_ul\">"+
										"<li class=\"addr_name\" id=\"uAddr2\" data-name=\""+ key.name +"\">"+ key.name +"</li>"+
										"<li class=\"addr_locality_phone\" data-phone=\""+ key.phone +"\"><i class=\"icon-phone-squared\"></i> "+ key.phone +"</li>"+
										"<li class=\"addr_locality\"><i class=\"icon-location\"></i>"+ key.line1BuildingNo +"</li>"+
										"<li class=\"addr_locality\">"+ key.line2StreetNo +"</li>"+
										"<li class=\"addr_locality\" data-city=\""+ key.city +"\"  data-pcode=\""+ key.pCode +"\">"+ key.city +","+ key.pCode +"</li>"+
									"</ul>"+
								"</div>";

			});

			$("#address-group").html(addr_result);
			$("#address-group").find("input[name='addr_option']:first").prop("checked",true);
			$('input.icheck').iCheck({
			   checkboxClass: 'icheckbox_square-grey',
			   radioClass: 'iradio_square-grey'
			 });
			flying_cart();

		}

	}


/*	function xset_addr(addr_list){
		var addr1_html = "",addr2_html = "";
		if((addr_list.address.length) === 0){
			$("#address-group").html("<h4 class=\"no_address_banner\"><i class=\"icon-address\"></i>Please add address to deliver.</h4>");
		}else{
	    	jQuery.each(addr_list.address,function(i,key){
	    		if((addr_list.address.length-1) === i){
					//if only one address or last address
					if((addr_list.address.length-1) < 1){
						// only one address,hide first addr and check default this
						$("#addr1radioLabel, #addr1").remove();
						$("#addr2radioLabel span").html("Address 1:");
						addr2_html = "<li class=\"addr_name\" id=\"uAddr2\" data-name=\""+ addr_list.address[0].name +"\">"+ addr_list.address[0].name +"</li>"+
									"<li class=\"addr_locality_phone\" data-phone=\""+ addr_list.address[0].phone +"\"><i class=\"icon-phone-squared\"></i> "+ addr_list.address[0].phone +"</li>"+
									"<li class=\"addr_locality\"><i class=\"icon-location\"></i>"+ addr_list.address[0].line1BuildingNo +"</li>"+
									"<li class=\"addr_locality\">"+ addr_list.address[0].line2StreetNo +"</li>"+
									"<li class=\"addr_locality\" data-city=\""+ addr_list.address[0].city +"\"  data-pcode=\""+ addr_list.address[0].pCode +"\">"+ addr_list.address[0].city +","+ addr_list.address[0].pCode +"</li>";
						jQuery("#addr2radio").attr("data-addr-id", addr_list.address[0].addressId);
					}else{
						//add address and check default,dont hide first addr
						addr2_html = "<li class=\"addr_name\" id=\"uAddr2\" data-name=\""+ addr_list.address[1].name +"\">"+ addr_list.address[1].name +"</li>"+
									"<li class=\"addr_locality_phone\" data-phone=\""+ addr_list.address[1].phone +"\"><i class=\"icon-phone-squared\"></i> "+ addr_list.address[1].phone +"</li>"+
									"<li class=\"addr_locality\"><i class=\"icon-location\"></i>"+ addr_list.address[1].line1BuildingNo +"</li>"+
									"<li class=\"addr_locality\">"+ addr_list.address[1].line2StreetNo +"</li>"+
									"<li class=\"addr_locality\" data-city=\""+ addr_list.address[1].city +"\"  data-pcode=\""+ addr_list.address[1].pCode +"\">"+ addr_list.address[1].city +","+ addr_list.address[1].pCode +"</li>";
						jQuery("#addr2radio").attr("data-addr-id", addr_list.address[1].addressId);

					}			    			
	    		}else{
						addr1_html = "<li class=\"addr_name\" id=\"uAddr1\" data-name=\""+ addr_list.address[0].name +"\">"+ addr_list.address[0].name +"</li>"+
										"<li class=\"addr_locality_phone\" data-phone=\""+ addr_list.address[0].phone +"\"><i class=\"icon-phone-squared\"></i> "+ addr_list.address[0].phone +"</li>"+
										"<li class=\"addr_locality\"><i class=\"icon-location\"></i>"+ addr_list.address[0].line1BuildingNo +"</li>"+
										"<li class=\"addr_locality\">"+ addr_list.address[0].line2StreetNo +"</li>"+
										"<li class=\"addr_locality\" data-city=\""+ addr_list.address[0].city +"\"  data-pcode=\""+ addr_list.address[0].pCode +"\">"+ addr_list.address[0].city +","+ addr_list.address[0].pCode +"</li>";
						jQuery("#addr1radio").attr("data-addr-id", addr_list.address[0].addressId);
	    		}
	    	});
			jQuery("#addr1").html(addr1_html);
			jQuery("#addr2").html(addr2_html);
		}
		flying_cart();
	}
*/
	function reset_addrFields(){
		$(".new_addr_group div").each(function(){
			$(".new_addr_group div input").val("");
			$(".new_addr_group div textarea").val("");
			$(".new_addr_group div select option:first").attr('selected','selected');
		});
		flying_cart();
	}

	function icke(){
		$("#order_process_time").html("<input type=\"radio\" class=\"icheck\">hi</input>")
	}

	function convertDateFormat(date){
		var mydate = new Date(date); 
		var datetime = 	mydate.getDate() + "/"
		                + (mydate.getMonth()+1)  + "/" 
		                + mydate.getFullYear() + " @ "  
		                + mydate.getHours() + ":"  
		                + mydate.getMinutes() + ":" 
		                + mydate.getSeconds();

		var date = 	mydate.getFullYear() + "-"
	                + (mydate.getMonth()+1)  + "-" 
	                + mydate.getDate();

        return date;
	}