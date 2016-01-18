$(function(){
/*	var credData = {
		  "userStatus": true,
		  "userName": "Varun Kumar",
		  "logTime": "2016-01-03 10:10:59",
		  "uCode": "AB2DK45S"
		};*/
	var credData={};
	$("#myLogin").submit(function(e){
		e.preventDefault();
		var user_details = $(this).serialize();
		$.ajax({
			  headers: { 
		        'Accept': '*/*',
		        'Content-Type': 'application/json' 
		      },
		      dataType: 'json',
			  url: "services/user/login",
			  method: "POST",
			  data: user_details
			}).success(function(data) {
				credData=data;
				setCreds(credData);
				setLoggedInfo();
			}).error(function(err){
				console.log(err);
			});
	});
	
	
	$("#myRegister").submit(function(e){
		e.preventDefault();
		if($("input[name='uName'").val() == undefined || $("input[name='uName'").val() == null || $("input[name='uName'").val() == ""){
			$('#pass-info').removeClass().addClass('weakpass').html("Please provide name.");
		}else if($("input[name='mobile'").val() == null || $("input[name='mobile'").val() == undefined || $("input[name='mobile'").val() == ""){
			$('#pass-info').removeClass().addClass('weakpass').html("Please provide mobile number");
		}else if($("input[name='email'").val() == null || $("input[name='email'").val() == undefined || $("input[name='email'").val() == ""){
			$('#pass-info').removeClass().addClass('weakpass').html("Please provide email");
		}else if($("input[name='password1'").val() == null || $("input[name='password1'").val() == undefined || $("input[name='password1'").val() == ""){
			$('#pass-info').removeClass().addClass('weakpass').html("Please provide password");
		}else{
			var user_details = {
				uName: $("input[name='uName'").val(),
				mobile: $("input[name='mobile'").val(),
				email: $("input[name='email'").val(),
				password1: $("input[name='password1'").val()
			};
			alert(user_details);
			/*$.ajax({
					
				  Content-Type : 'application/json' 
			      dataType : 'json',
				  url: "/services/json/userRegistration.json",
				  method: "POST",
				  data: user_details
				}).success(function(response) {
					alert('done--'+response);
				}).error(function(err){
					//console.log(err);
					setCreds(credData);
				});*/
			$.ajax({
			     contentType:'application/json;',
			     dataType : 'json',
			     url: "services/json/userRegistration.json",
			     method: "POST",
			     data: user_details,
			     success:function(data){
			      alert('done--'+response);
			    },
			    error:function(err){
			     //console.log(err);
			     setCreds(credData);
			    }
			   });
		}

	});

});

function setCreds(data){
	console.log(data.logTime);
	var exdays = 1; //login expires on 1 day
	var mydate = new Date("January 23, 2016 11:30:22");
	mydate.setTime(mydate.getTime() + (exdays*24*60*60*1000));
    var expires = "expires="+mydate.toUTCString();
    var uCode = data.uCode;
    
    // USE THIS FOLLOWING USER INFORMATION EVERYWHERE
    document.cookie = "uCode="+uCode+"; "+expires+"; path=/";
    document.cookie = "uName="+data.userName+"; "+expires+"; path=/";
	sessionStorage.setItem("user", JSON.stringify(data));
}

function setLoggedInfo(){
	var getLogInfo = getCookie('uName');
	if(getLogInfo != null || getLogInfo != undefined || getLogInfo != ""){
		$("#head_menu li:nth-child(3)").html("<a href=\"#\" onclick=\"logout();\">Logout</a>");
	}
}

function getCookie(name)
{
  var re = new RegExp(name + "=([^;]+)");
  var value = re.exec(document.cookie);
  return (value != null) ? unescape(value[1]) : null;
}

function logout(){
	removeCreds();
	location.reload();
}

function removeCreds(){
	document.cookie = "uCode=; expires=; path=/";
    document.cookie = "uName=; expires=; path=/";
    sessionStorage.removeItem('user');	
}

