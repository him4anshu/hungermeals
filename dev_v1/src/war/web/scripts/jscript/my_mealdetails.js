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
				url:URL_gbl+"comboDetailsByUser.json",
				type:"POST",
				contentType: "application/json",
                crossDomain: true,
			  	data: JSON.stringify(user_details),
			    success:function(data){
			    	// alert(JSON.stringify(data));
			    	getMealsList(data);
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

    function getMealsList(data){
    	console.log(JSON.stringify(data));

    	var o_list = "";
    	$.each(data.planSubscription,function(index,val){
        var planObj = {};
        planObj = val;
        // alert(JSON.stringify(planObj));
        o_list += "<div class=\"strip_list wow fadeIn\" data-wow-delay=\"0.1s\">"+
                            "<div class=\"row\">"+
                                "<div class=\"col-md-9 col-sm-9\">"+
                                    "<div class=\"desc\">"+
                                        "<div class=\"thumb_strip\">"+
                                            "<a href=\"#0\" onclick='order_details("+JSON.stringify(planObj)+")'><img src=\"../images/hm_icon.png\" alt=\"\"></a>"+
                                        "</div>"+
                                        "<h3>Order #: "+ val.planSubscribeId +"</h3>"+
                                        "<div class=\"type\">"+
                                        "</div>"+
                                        "<div class=\"location\">"+
                                            "Total: <i class=\"icon-rupee\"></i>"+val.planCost+
                                        "</div>"+
                                        "<div class=\"location\">"+
                                            "Plan Type: "+val.planType+
                                        "</div>"+
                                    "</div>"+
                                "</div>"+
                                "<div class=\"col-md-3 col-sm-3\">"+
                                    "<div class=\"go_to\">"+
                                        "<div>"+
                                            "<a href=\"#0\" onclick='order_details("+JSON.stringify(planObj)+")' class=\"btn_1\">View Details</a>"+
                                        "</div>"+
                                    "</div>"+
                                "</div>"+
                            "</div>"+
                        "</div>";

    	});


                $("#my_ord_list").html(o_list);

    };

    function order_details(planObj){
        //Get Order Details for following orderid & user
    	 // alert(JSON.stringify(planObj));    	
         localStorage.setItem("mymeal_order", JSON.stringify(planObj));
         redir('mymeal_view.jsp');
    }