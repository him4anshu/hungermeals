/*
 * Global Variables
 * */
	var URL_gbl = "http://104.238.96.134:8080/hungermeals/services/userservices/";

$(function(){
	var credData = {
		  "userStatus": true,
		  "uName": "Example",
		  "logTime": "2016-01-03 10:10:59",
		  "uCode": "AB2D-K45S-CD22-DA01"
		};

	$("#myLogin").click(function(e){
		var user_details = {};
		user_details.user = {
			email: $("input[name='username'").val(),
			password1: $("input[name='password'").val()
		};
		console.log(JSON.stringify(user_details));
		$.ajax({
				url:URL_gbl+"userLogin.json",
				type:"POST",
				contentType: "application/json",
			  	data: JSON.stringify(user_details)
			}).success(function(data) {
				console.log(data);
				if(data.user.userStatus == true){
					credData = data.user;
					setCreds(credData);
				}else{
					alert("Username or Password wrong,Please re-enter!!!");
				}
			}).error(function(err){
				console.log(JSON.stringify(err));
			});
	});
	
	
	$("#myRegister").click(function(e){
		if($("input[name='uName'").val() == undefined || $("input[name='uName'").val() == null || $("input[name='uName'").val() == ""){
			$('#pass-info').removeClass().addClass('weakpass').html("Please provide name.");
		}else if($("input[name='mobile'").val() == null || $("input[name='mobile'").val() == undefined || $("input[name='mobile'").val() == ""){
			$('#pass-info').removeClass().addClass('weakpass').html("Please provide mobile number");
		}else if($("input[name='email'").val() == null || $("input[name='email'").val() == undefined || $("input[name='email'").val() == ""){
			$('#pass-info').removeClass().addClass('weakpass').html("Please provide email");
		}else if($("input[name='password1'").val() == null || $("input[name='password1'").val() == undefined || $("input[name='password1'").val() == ""){
			$('#pass-info').removeClass().addClass('weakpass').html("Please provide password");
		}else{
			var user_details = {};
			user_details.user = {
				uName: $("input[name='uName'").val(),
				lastName: "null",
				mobile: $("input[name='mobile'").val(),
				email: $("input[name='email'").val(),
				password1: $("input[name='password1'").val(),
	            userType: "HM"
			};
			call_register(user_details);
			
		}

	});

});

function call_register(userObj){
   // alert("userReg-"+JSON.stringify(userObj));
    console.log(JSON.stringify(userObj));
	$.ajax({
			url:URL_gbl+"userRegistration.json",
			type:"POST",
			contentType: "application/json",
			data: JSON.stringify(userObj),
			success:function(data){
				console.log(data);
				if(data.user.userStatus == true){
					credData = data.user;
					setCreds(credData);
				}
 			},
			error:function(XHR, textStatus, errorThrown){
				console.log("Error Occured: "+errorThrown);
			//					setCreds(credData);
			}
		});
}

function setCreds(data){
/*	console.log(data.logTime);
	var exdays = 1; //login expires on 1 day
	var mydate = new Date("January 23, 2016 11:30:22");
	mydate.setTime(mydate.getTime() + (exdays*24*60*60*1000));
    var expires = "expires="+mydate.toUTCString();
    var uCode = data.uCode;
    
    // USE THIS FOLLOWING USER INFORMATION EVERYWHERE
    document.cookie = "uCode="+uCode+"; "+expires+"; path=/";
    document.cookie = "uName="+data.userName+"; "+expires+"; path=/";
	sessionStorage.setItem("user", JSON.stringify(data));
	*/
	console.log(data);
	var hmObj = {};
	hmObj.hm1 = data.userStatus;
	hmObj.hm2 = data.uName;
	hmObj.hm3 = data.logTime;
	hmObj.hm4 = data.uCode;

	Cookies.set('uCode', hmObj, { expires: 7, path: '/' });
	setLoggedInfo(); // set the changes
}

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
		$("#head_menu").html("<li><a href=\"about.html\">About us</a></li><li><a href=\"faq.html\">Faq</a></li><li id=\"myOrder_link\"><a href=\"#\" onclick=\"pastOrders();\">My Orders</a></li><li><a href=\"#\" onclick=\"logout();\">Logout</a></li>");
//		$("#head_menu li:nth-child(3)").html("<a href=\"#\" onclick=\"logout();\">Logout</a>");
		$(".modal").modal('hide');
	}else{
		$("#myOrder_link").remove();
		//Login is added by default in html
//		$("#head_menu li:nth-child(3)").html("<a href=\"#0\" data-toggle=\"modal\" data-target=\"#login_2\">Login</a>");		

	}
}

/*
 * CUSTOM get COOKIE
 function getCookie(name) {
	  var regexp = new RegExp("(?:^" + name + "|;\s*"+ name + ")=(.*?)(?:;|$)", "g");
	  var result = regexp.exec(document.cookie);
	  return (result === null) ? null : result[1];
}
*/

function pastOrders(){
	redir('my_orders.html');
}

function logout(){
	removeCreds();
	location.reload();
}

function removeCreds(){
/*	document.cookie = "uCode=; expires=; path=/";
    document.cookie = "uName=; expires=; path=/";
    sessionStorage.removeItem('user');	
*/

	Cookies.remove('uCode', { path: '/' });
}
