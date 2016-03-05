/*
 * Global Variables
 * */

    var URL_gbl = "../services/userservices/";
    var userInfo_glb = Cookies.getJSON('uCode'); // use this instead of cookie calls repeatedly

$(document).ready(function(){
    mVerifySucc(userInfo_glb);
	
	$("#updatePass").click(function(){
        $("#updatePass").prop("disabled",true);            
		var op = $("#oldPss").val();
		var np = $("#newPss").val();
		var rp = $("#repeatPss").val();
		if(!op || !np || !rp){
			$("#errTxt").html("Please enter all the fields.");
			 $("#updatePass").prop("disabled",false);    
		}else if(rp === np){
			$("#errTxt").html("Look's good,checking...!!");
			//verify old password and then change the new password
        var pssObj = {};
        pssObj.user = {
            uCode:userInfo_glb.hm4,
            password1: rp,
            o_password: op
        };
        alert(JSON.stringify(pssObj));
        $.ajax({
                url:URL_gbl+"changePassword.json",
                type:"POST",
                contentType: "application/json",
                crossDomain: true,
                data: JSON.stringify(pssObj),
                success:function(data){
                    alert(JSON.stringify(data));
                    $("#updatePass").prop("disabled",false);            
					$("#errTxt").html("Updated Successfully!!!");
                },
                error:function(XHR, textStatus, errorThrown){
                    $("#updatePass").prop("disabled",false);            
                    console.log("Error Occured: "+errorThrown);
                    $(".modal#hm_alert").modal('hide');
                }
            });


		}else{
			$("#errTxt").html("Password doesn't match!!");
			 $("#updatePass").prop("disabled",false);    
		}
	});

    $("#getVcode").click(function(){
        var verObj = {};
        verObj.user = {
            uCode:userInfo_glb.hm4,
            mobile: userInfo_glb.hm5
        };
            $.ajax({
            url:URL_gbl+"mobileVerification.json",
            type:"POST",
            contentType: "application/json",
            crossDomain: true,
            data: JSON.stringify(verObj),
            success:function(data){
                //console.log(JSON.stringify(data));
            },
            error:function(XHR, textStatus, errorThrown){

            }
        });
    });

    $("#verifyme").click(function(){
        var verifyTxt = $("#verifyTxt").val();
        if(verifyTxt){
            $(this).prop("disabled",true);
            var verfmeObj = {};
            verfmeObj.user = {
                uCode:userInfo_glb.hm4,
                mobileVerificationCode: verifyTxt
            };

            $.ajax({
                url:URL_gbl+"updateMobileVerificationStatus.json",
                type:"POST",
                contentType: "application/json",
                crossDomain: true,
                data: JSON.stringify(verfmeObj),
                success:function(data){
                    if(data.user.responseStatus.responseCode != "HM200"){
                        alert("Wrong code, Please try again!!!");
                    }else{
                        userInfo_glb.hm6 = true;
                        Cookies.set('uCode', userInfo_glb, { path: '/' });
                        mVerifySucc(userInfo_glb);                        
                    }
                },
                error:function(XHR, textStatus, errorThrown){

                }
            });
        }else{
            alert("Please enter verfication code sent to your mobile!!");
        }

    });
});

function mVerifySucc(data){
    if(data.hm6 == true){
        $(".verifyMobile").html("<a href=\"#0\">Mobile Verified Successfully.</a>");
    }
}