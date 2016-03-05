/*
 * Global Variables
 * */

    var URL_gbl = "../services/userservices/";
    var userInfo_glb = Cookies.getJSON('uCode'); // use this instead of cookie calls repeatedly
    var getMeal_details = localStorage.getItem("mymeal_order");
    var getMeal_details_glb = $.parseJSON(getMeal_details);
    var maxCountDiff = 0;
    
$(function() {

var cookObj = getCookieVal('uCode');
	if(cookObj == undefined){
		redir('index.jsp');
	}else{
		if(cookObj.hm1 == true){
            // alert(JSON.stringify(getMeal_details_glb));
            set_mymeal(getMeal_details_glb);

		}else{
			// if logged in with false state
			redir('index.jsp');
		}

	}

    function set_mymeal(data){
        console.log(JSON.stringify(data));          

            var planList =    "<div class=\"row\">"+
                                "<div class=\"col-md-6 col-sm-6\">"+
                                    "<div class=\"desc_mealType\">"+
                                        "<h3 class=\"\">Combo 2 + Addict Home Plan</h3>"+
                                        "<div class=\"location\">"+
                                            "Start Date: 12-02-1201"+
                                        "</div>"+
                                        "<div class=\"location\">"+
                                            "End Date: 12-02-1201"+
                                        "</div>"+
                                        "<div class=\"location\">"+
                                            "Cost: <i class=\"icon-rupee\"></i> 2000"+
                                        "</div>"+
                                        "<div class=\"location\">"+
                                            "Delivery slot: 04:00 PM"+
                                        "</div>"+
                                        "<div class=\"location\">"+
                                            "Delivery Address: avcs"+
                                        "</div>"+
                                    "</div>"+
                                "</div>"+
                                "<div class=\"col-md-6 col-sm-6\">"+
                                    "<div class=\"\" id=\"calView\"></div>"+
                                "</div>"+
                            "</div>"+
                            "<div class=\"row\">"+
                                "<div class=\"col-md-12 col-sm-12\">"+
                                    "<div class=\"go_to\">"+
                                        "<div>"+
                                            "<a href=\"#0\" data-toggle=\"modal\" data-target=\"#edit_mymeal\" class=\"btn_1 mySubscribe\" onclick=\"editmymeal();\">Re-shedule Plan</a>"+
                                            "<a href=\"#0\" class=\"btn_1 mySubscribe\" id=\"cancel_mymeal\">Cancel Plan</a>"+
                                        "</div>"+
                                    "</div>"+
                                "</div>"+
                            "</div>";


        $('#planDetails').html(planList);

        var cal_date = new Date(data.endDate);
        var sel_dates = data.selectedDate.split(",");

        var pre_cal_dates = [];

        $.each(sel_dates, function(i,val){
            pre_cal_dates.push(val.trim());
        });

        console.log(pre_cal_dates);
        
        var calObj = {
            addDates: pre_cal_dates,
            addDisabledDates: pre_cal_dates
        };

        set_cal(calObj);

    }

    function set_cal(dataObj){
        $("#calView").multiDatesPicker(dataObj);
    }


    $("#update_mymeal").click(function(){
        var uptDates = $("#alt_sel_dates").val();
        var uptDatesArr = uptDates.split(",");

        var updateObj = {};
        updateObj.planSubscription = {
            planSubscribeId:getMeal_details_glb.planSubscribeId,
            uCode:userInfo_glb.hm4,
            selectedDate:uptDates,
            startDate:uptDatesArr[0].trim(),
            endDate:uptDatesArr[uptDatesArr.length - 1].trim()
        }

        console.log(JSON.stringify(updateObj));
        $.ajax({
                url:URL_gbl+"updatePlanSubscription.json",
                type:"POST",
                contentType: "application/json",
                crossDomain: true,
                data: JSON.stringify(updateObj),
                success:function(data){
                    alert(JSON.stringify(data));

                },
                error:function(XHR, textStatus, errorThrown){
                    // $("#myRegister").prop("disabled",false);            
                    alert('err');
                    console.log("Error Occured: "+errorThrown);
                    getMeal_details_glb.selectedDate = uptDates;
                    getMeal_details_glb.startDate = uptDatesArr[0].trim();
                    getMeal_details_glb.endDate = uptDatesArr[uptDatesArr.length - 1].trim();
                    localStorage.setItem("mymeal_order", JSON.stringify(getMeal_details_glb));
                    $("#resMsg").html("Updated successfully!!!");

                    set_mymeal(getMeal_details_glb);
                    $(".modal#edit_mymeal").modal('hide');
                }
            });

    });

    $("#cancel_mymeal").click(function(){
        hm_del_confirm("Are you sure you want to cancel Meal-Plan");
    })

});


    function editmymeal(){
        maxCountDiff = 0; // global variable

        var cal_date = new Date(getMeal_details_glb.endDate);
        var sel_dates = getMeal_details_glb.selectedDate.split(",");

        var pre_cal_dates = [];

        var maxCount = 0;
        // user selected dates
        $.each(sel_dates, function(i,val){
            val = val.trim();
            pre_cal_dates.push(val);
            maxCount = calculateDeliveredMeals(val,maxCount,'forMaxCount');
        });

        //calculate already delivery meals till current date
        maxDates = calculateDeliveredMeals(getMeal_details_glb.endDate,maxCount,'forMaxDate');

        console.log(pre_cal_dates);
        
        var calObj = {
            minDate: 1,
            maxDate: (maxDates + 10),
            maxPicks: (maxCountDiff + maxCount),
            addDates: pre_cal_dates,
            altField: '#alt_sel_dates',
            onSelect: function(dateText){
                var mydates = $("#alt_sel_dates").val();
                update_sel_data(maxCountDiff,maxCount,maxDates,mydates);
            }
        };
        // console.log(JSON.stringify(calObj));
        $("#calEditView").multiDatesPicker(calObj);
    }

    function calculateDeliveredMeals(mySelDates,maxCount,calType){
        var date1 = new Date();
        var date2 = new Date(mySelDates);

        if(calType == 'forMaxCount'){
            var timeDiff = Math.abs(date2.getTime() - date1.getTime());
            var diffDays = Math.ceil(timeDiff / (1000 * 3600 * 24)); 

            if(Math.ceil((date2 - date1)/ (1000*60*60*24)) > 0){
                maxCount++;
            }else{
                maxCountDiff++;
            }
            return maxCount;           
        }else if(calType == 'forMaxDate'){
            return Math.ceil((date2 - date1)/ (1000*60*60*24));
        }
    }

    function update_sel_data(maxCountDiff,maxCount,maxDates,mydates){
        var resultant = "<li><a>Already Delivered: "+maxCountDiff+"</a></li>"+
                        "<li><a>Remaining Meals: "+maxCount+"</a></li>"+
                        "<li><a>Remaining Days: "+maxDates+"</a></li>";

        var uptDatesArr = mydates.split(",");

        if((maxCountDiff + maxCount) != uptDatesArr.length){
           $("#resMsg").html((24 - uptDatesArr.length) +" remaining date selction required!!!");
           $("#update_mymeal").hide();
        }else{
           $("#resMsg").html("Good to update");
           $("#update_mymeal").show();
        }

        $("#remData").html(resultant);

    }

function hm_del_confirm(txt,deleteObj){
    var hm_content = "<div class=\"modal-dialog\">"+
                    "<div class=\"modal-content modal-popup\">"+
                            "<a href=\"#\" class=\"close-link\"><i class=\"icon_close_alt2\"></i></a>"+
                            "<form class=\"popup-form\">"+
                                "<a href=\"#0\" class=\"alert_txt_a\" id=\"hm_alert_txt\"></a>"+
                                    "<div class=\"row\">"+
                                        "<div class=\"col-md-6 col-lg-6\">"+
                                            "<button type=\"button\" class=\"btn btn-submit\" id=\"can_btn\">OK</button>"+
                                        "</div>"+                                
                                        "<div class=\"col-md-6 col-lg-6\">"+
                                            "<button type=\"button\" class=\"btn btn-submit modal-close-btn\" id=\"can_btn\">Cancel</button>"+
                                        "</div>"+                                                                                                        
                                    "</div>"+                                
                            "</form>"+
                    "</div>"+
                "</div>";
    $("#hm_alert").html(hm_content);
    $("#hm_alert_txt").html(txt);
    $("#hm_alert").modal('show');

    $('.modal-popup .close-link, .modal-close-btn').click(function(event){
        event.preventDefault();
        $('.modal#hm_alert').modal('hide');
    });

    $("#can_btn").click(function(){
        cancel_mealplan();
    });
}

function cancel_mealplan(){
        var deleteObj = {};
        deleteObj.planSubscription = {
            planSubscribeId:getMeal_details_glb.planSubscribeId,
            uCode:userInfo_glb.hm4,
            planStatus: "cancelled"
        };
        alert(JSON.stringify(deleteObj));
        $.ajax({
                url:URL_gbl+"updatePlanSubscription.json",
                type:"POST",
                contentType: "application/json",
                crossDomain: true,
                data: JSON.stringify(deleteObj),
                success:function(data){
                    alert(JSON.stringify(data));

                },
                error:function(XHR, textStatus, errorThrown){
                    // $("#myRegister").prop("disabled",false);            
                    alert('err');
                    console.log("Error Occured: "+errorThrown);
                    $(".modal#hm_alert").modal('hide');
                }
            });


}