/*
 * Global Variables
 * */
	var URL_gbl = "../services/userservices/";

$(document).ready(function(){
	var credData = {};
		$("#myLogin").click(function(e){
			e.preventDefault();
			$("#myLoginSpin").show();
			$("#myLogin").prop("disabled",true);			
			var chk_login = validate_login();
			if(chk_login == true){
				var user_details = {};
				user_details.user = {
				email: $("input[name='username'").val(),
				password1: $("input[name='password'").val()
				};
				set_login(user_details);
			}
		});

	$("#myRegister").click(function(e){
			e.preventDefault();
			$("#myRegisterSpin").show();			
			var chk_register = validate_register();
			if(chk_register == true){
				$("#myRegister").prop("disabled",true);			
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
			}else if(chk_register == false){
				$("#myRegisterSpin").hide();			
			}

	});

}); /*document.ready ends*/

function set_login(data){
	$.ajax({
		url:URL_gbl+"userLogin.json",
		type:"POST",
		contentType: "application/json",
		crossDomain: true,
	  	data: JSON.stringify(data)
	}).success(function(data) {
		$("#myLoginSpin").hide();
		$("#myLogin").prop("disabled",false);			
		console.log(data);
		if(data.user.userStatus == true){
			credData = data.user;
			setCreds(credData);
			if(jmp_nxt){
				redir(jmp_nxt);
			}
		}else{
			$("#myLoginSpin").hide();
			$("#myLogin").prop("disabled",false);
			hm_alert("Username or Password wrong,Please re-enter!!!");
		}
	}).error(function(err){
		console.log(JSON.stringify(err));
		$("#myLoginSpin").hide();
		redir('error.jsp');
	});
}


function call_register(userObj){
//    console.log(JSON.stringify(userObj));
	$.ajax({
			url:URL_gbl+"userRegistration.json",
			type:"POST",
			contentType: "application/json",
			crossDomain: true,
			data: JSON.stringify(userObj),
			success:function(data){
				$("#myRegister").prop("disabled",false);			
				// console.log(data);
				if(data.user.userStatus == true){
					credData = data.user;
					setCreds(credData);
					if(jmp_nxt){
						redir(jmp_nxt);
					}
				}else if(data.user.responseStatus.responseCode == "HM201"){
					credData = data.user;
					user_exist(credData);
				}else{
					hm_alert("Something went wrong!!Please try later");
				}
 			},
			error:function(XHR, textStatus, errorThrown){
				$("#myRegister").prop("disabled",false);			
				console.log("Error Occured: "+errorThrown);
			//					setCreds(credData);
			}
		});
}

function getotp(){
	document.getElementById("otpRequest").innerHTML = "Requesting OTP....";
	var uName = $("input[name='username'").val();
	if(uName != "" && uName.length == 10){
		$.ajax({
			url:URL_gbl+"getotp.json/"+uName,
			type:"GET",
			contentType: "application/json",
			crossDomain: true,
			success:function(data){
				if(data.responseStatus.responseCode == "HM200"){
					document.getElementById("otpRequest").innerHTML = "Instant OTP?";
					console.log("Please enter OTP received in password");
				}else if(data.responseStatus.responseCode == "HM101"){
					document.getElementById("otpRequest").innerHTML = "Instant OTP?";
					hm_alert("User doesn't exist!!!");
				}
 			},
			error:function(XHR, textStatus, errorThrown){
				console.log("Error Occured: "+errorThrown);
				document.getElementById("otpRequest").innerHTML = "Instant OTP?";
			}
		});
	}else{
		hm_alert("Please enter valid registered mobile number!!!");
	}


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
	hmObj.hm5 = data.mobile;
	hmObj.hm6 = data.mobileVerified;
	hmObj.hm7 = data.emailVerified;
	hmObj.hm8 = "Varun Kumar";

	Cookies.set('uCode', hmObj, { expires: 7, path: '/' });
	setLoggedInfo(); // set the changes
}

function user_exist(data){
	hm_alert("User already exist,Please login using registered mobile number!!!");
	$(".modal#register").modal('hide');
	$(".modal#login_2").modal('show');
}


/*----------------------------credentials copy(common.js)-----------------------------*/
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

/*----------------------------credentials copy(common.js) ends-----------------------------*/


function validate_login(){
	var username = $("input[name='username'").val();
	var password = $("input[name='password'").val();
	var pattern = /^[0-9]*$/;

	if(pattern.test(username) == true){
		if(username.length == 10){
			if(password){
				$("#error_statusTxt").hide();
				return true;
			}else{
				$("#myLoginSpin").hide();
				$("#myLogin").prop("disabled",false);
				$("#error_statusTxt").html("Password/OTP required!!!").show();
				return false;
			}
		}else{
			$("#myLoginSpin").hide();
			$("#myLogin").prop("disabled",false);
			$("#error_statusTxt").html("Invalid number!!!").show();
			return false;		
		}

	}else{
		$("#myLoginSpin").hide();
		$("#myLogin").prop("disabled",false);
		$("#error_statusTxt").html("Invalid characters!!!").show();
		return false;		
	}
}
    
function validate_register(){
		var pas_length = $("input[name='password1'").val();
		var mob_length = $("input[name='mobile'").val();
		if($("input[name='uName'").val() == undefined || $("input[name='uName'").val() == null || $("input[name='uName'").val() == ""){
			$('#pass-info').removeClass().addClass('weakpass').html("Please provide name.");
			return false;
		}else if($("input[name='mobile'").val() == null || $("input[name='mobile'").val() == undefined || $("input[name='mobile'").val() == ""){
			$('#pass-info').removeClass().addClass('weakpass').html("Please provide mobile number");
			return false;
		}else if($("input[name='email'").val() == null || $("input[name='email'").val() == undefined || $("input[name='email'").val() == ""){
			$('#pass-info').removeClass().addClass('weakpass').html("Please provide email");
			return false;
		}else if($("input[name='password1'").val() == null || $("input[name='password1'").val() == undefined || $("input[name='password1'").val() == ""){
			$('#pass-info').removeClass().addClass('weakpass').html("Please provide password");
			return false;
		}else if(pas_length.length < 5){
			$('#pass-info').removeClass().addClass('weakpass').html("Must be 5 or more letters");
			return false;
		}else if($("input[name='password1'").val() != $("input[name='password2'").val()){
			$('#pass-info').removeClass().addClass('weakpass').html("Passwords do not match!");
			return false;
		}else if(mob_length.length < 10){
			$('#pass-info').removeClass().addClass('weakpass').html("Invalid mobile number");
			return false;
		}else{
			//EMAIL validation
			var re = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
			if(re.test($("input[name='email'").val()) != true){
				$('#pass-info').removeClass().addClass('weakpass').html("Invalid email");
				return false;
			}else{
				return true;			
			}
		}

}