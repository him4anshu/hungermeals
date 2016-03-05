/*
 * Global Variables
 * */
	var URL_gbl = "../services/userservices/";
    var userInfo_glb = Cookies.getJSON('uCode'); // use this instead of cookie calls repeatedly
    var userCombo = sessionStorage.getItem("cart_combo"); 
    var userCombo_glb = $.parseJSON(userCombo);
    var sel_dates_glb = [];

    $(document).ready(function() {
	if(userInfo_glb != undefined){

		getLoggedInfo_addr(true);
		addr_delivery_page();
        mymeal_cartfiller(userCombo_glb);
		setLoggedInfo();

		$("#addr_next_btn").click(function(e){
			e.preventDefault();
			// alert($("input[type='radio'][name='addr_option']:checked").parent().parent().next('.address_ul').find('li:nth-child(2)').attr('data-phone'));

			var add_id = $("input[type='radio'][name='addr_option']:checked").attr('data-addr-id');
			var delvPhone = $("input[type='radio'][name='addr_option']:checked").parent().parent().next('.address_ul').find('li:nth-child(2)').attr('data-phone');
			var delvCity = $("input[type='radio'][name='addr_option']:checked").parent().parent().next('.address_ul').find('li:nth-child(5)').attr('data-city'); 
			var delvpCode = $("input[type='radio'][name='addr_option']:checked").parent().parent().next('.address_ul').find('li:nth-child(5)').attr('data-pcode');
			if(add_id == "" || add_id == undefined || add_id  == null || !add_id){
				hm_alert("Please add address to deliver!!!");
			}else{
				// update selected slot and address to uCode cookie.
				var getLogInfo = {};

				getLogInfo.delAddr = parseInt(add_id);
				getLogInfo.phone = delvPhone;
				getLogInfo.city = delvCity;
				getLogInfo.pCode = delvpCode;

				userCombo_glb.address = getLogInfo;
				// alert(JSON.stringify(userCombo_glb));
				// Cookies.set('uCode', getLogInfo, { expires: 7, path: '/' });
		        sessionStorage.setItem("cart_combo", JSON.stringify(userCombo_glb));
				redir('mymeal_payment.jsp');
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
				console.log(tempObj);	
				userInfo_saveObj.uName = tempObj.hm2;
				userInfo_saveObj.uCode = tempObj.hm4;
				userInfo_saveObj.address = save_addrObj;

				final_saveObj.user = userInfo_saveObj;

				console.log(final_saveObj);
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