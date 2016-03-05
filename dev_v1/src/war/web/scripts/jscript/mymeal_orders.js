/*
 * Global Variables
 * */
    var URL_gbl = "../services/userservices/";
    var userInfo_glb = Cookies.getJSON('uCode'); // use this instead of cookie calls repeatedly
    var userCombo = sessionStorage.getItem("cart_combo"); 
    var userCombo_glb = $.parseJSON(userCombo);
    var sel_dates_glb = [];

$(document).ready(function(){
    setLoggedInfo();

	var date = new Date();
        $('#sel_dates').multiDatesPicker({
            minDate: 1,
            maxDate: 30,
            maxPicks: 24,
            altField: '#alt_sel_dates',
            onSelect: function(dateText){
         		var mydates = $("#alt_sel_dates").val();
                sel_dates_glb = mydates.split(","); // use glb dates for any func
         		add_mealDates(mydates);
            }
        });
    $("#deliveryDay").val("").prop('disabled',true);

    $(".mySubscribe").click(function(){
        setMyOrder($(this).attr('data-id'),$(this).attr('data-text'));

        //scroll down to div and visible          
    //    $("#deliveryDay").val("").prop('disabled',true);
        $("#main_menu").show();
        $("html, body").animate({ scrollTop: $("#main_menu").height() + 300}, 1500);
    });

    $("#deliveryDay").change(function(){
        $("#cart_box").show();
    });

    function setMyOrder(subscibePlan,subscibePlanTxt){
        // var combObj = userCombo_glb;
        var tempCombObj = {};
        tempCombObj.subPlan = subscibePlan;
        tempCombObj.subPlanTxt = subscibePlanTxt;
        tempCombObj.price = 2000;

        userCombo_glb.subscibe = tempCombObj; 
        
        mymeal_cartfiller(userCombo_glb);
    }



    $("#combo_btn").click(function(){

        var tempCombObj = {};
            tempCombObj.myDates = sel_dates_glb.toString();
            tempCombObj.myStartDate = sel_dates_glb[0].toString();
            tempCombObj.myEndDate = sel_dates_glb[23].toString();
            tempCombObj.timeSlot = $("#deliveryDay").val();

        userCombo_glb.selDates = tempCombObj; 
        sessionStorage.setItem("cart_combo", JSON.stringify(userCombo_glb));
        redir('mymeal_addr.jsp');

    });
}); /*document ends*/


function add_mealDates(mydates){
	var datesArr = mydates.split(",");
	// alert(datesArr.length);
    if(datesArr.length == 24){
        $("#deliveryDay").prop('disabled',false);
        $("#ico_sel_dates").removeClass().addClass("icon_check_alt2 ok");
        $("#days_rem").html("Thank You, Choose delvivery slot now!!!");
    }else{
        $("#ico_sel_dates").removeClass().addClass("icon_close_alt2 no");
        $("#deliveryDay").val("").prop('disabled',true);
        $("#days_rem").html("Please choose <span>"+(24 - datesArr.length)+"</span> remaining days to complete.");
    }
}


