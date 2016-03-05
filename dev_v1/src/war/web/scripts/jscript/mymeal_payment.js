/*
 * Global Variables
 * */

	var URL_gbl = "../services/userservices/";
    var userInfo_glb = Cookies.getJSON('uCode'); // use this instead of cookie calls repeatedly
    var userCombo = sessionStorage.getItem("cart_combo"); 
    var userCombo_glb = $.parseJSON(userCombo);
    var sel_dates_glb = [];


$(document).ready(function(){
	if(Cookies.getJSON('uCode') != undefined){
        mymeal_cartfiller(userCombo_glb);
  		setLoggedInfo();
        flying_cart();

		$("#order_confirm_btn").click(function(e){
			e.preventDefault();
			$("#myLoginSpin").show();
			$("#order_confirm_btn").addClass('not-active');

			var finalObj = {},tmpfinalObj = {};

			//Plan subscription
			tmpfinalObj.uCode = userCombo_glb.user.uCode;
			tmpfinalObj.mobile = userCombo_glb.user.uPhone;
			tmpfinalObj.uPhone = userCombo_glb.user.uPhone;
			tmpfinalObj.uName = userCombo_glb.user.uName;
			tmpfinalObj.selectedDate = userCombo_glb.selDates.myDates;
			tmpfinalObj.startDate = userCombo_glb.selDates.myStartDate;
			tmpfinalObj.endDate = userCombo_glb.selDates.myEndDate;
			tmpfinalObj.planCost = userCombo_glb.subscibe.price;
			tmpfinalObj.addressId = parseInt(userCombo_glb.address.delAddr);
			tmpfinalObj.city = userCombo_glb.address.city;
			tmpfinalObj.pCode = userCombo_glb.address.pCode;
			tmpfinalObj.timeSlot = userCombo_glb.selDates.timeSlot;
			tmpfinalObj.planType = userCombo_glb.subscibe.subPlan;
			tmpfinalObj.comboId = parseInt(userCombo_glb.comboDetails.cType);
			tmpfinalObj.paymentMode = "COD";

			finalObj.planSubscription = tmpfinalObj;    			

			console.log(JSON.stringify(finalObj));

	/*
	* Template
			finalObj = {
						  "planSubscription": {
						    "uCode": "8b68d709-ab9c-46c3-8e31-233a4697d4d8",
						    "mobile": 8880588801,
						    "uPhone": 8880588801,
						    "uName": "varunsk23@gmail.com",
						    "selectedDate": "02/16/2016, 02/19/2016, 02/20/2016, 02/22/2016, 02/23/2016, 02/25/2016, 02/26/2016, 02/27/2016, 02/29/2016, 03/01/2016, 03/02/2016, 03/03/2016, 03/04/2016, 03/05/2016, 03/06/2016, 03/07/2016, 03/08/2016, 03/09/2016, 03/10/2016, 03/11/2016, 03/12/2016, 03/13/2016, 03/14/2016, 03/15/2016",
						    "startDate": "02/16/2016",
						    "endDate": " 03/15/2016",
						    "planCost": 2000,
						    "addressId": 82,
						    "city": "Bengaluru",
						    "pCode": "560102",
						    "timeSlot": "03",
						    "planType": "AdHoPlan",
						    "comboId": 1,
						    "paymentMode": "COD"
						  }
						}*/

			order_confirm_call(finalObj);
		   
		});
	}else{
		redir('index.jsp');
	}
});

function order_confirm_call(finalObj){
	// console.log(finalObj);
	$.ajax({
			url:URL_gbl+"planSubscription.json",
			type:"POST",
			contentType: "application/json",
			crossDomain: true,
		  	data: JSON.stringify(finalObj),
		    success:function(data){
				$("#myLoginSpin").hide();
				$("#order_confirm_btn").removeClass('not-active');
		    	console.log(JSON.stringify(data));
		    	localStorage.setItem("mymeal_c_order", JSON.stringify(data));
		    	
				redir('mymeal_o_confirm.jsp');
		    },
		    error:function(XHR, textStatus, errorThrown){
		    	console.log("Error Occured: "+errorThrown);
		    	redir('error.jsp');
		    }

	   });
}
