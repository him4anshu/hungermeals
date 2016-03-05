/*
 * Global Variables
 * */

	var URL_gbl = "../services/userservices/";
	var userInfo_glb = Cookies.getJSON('uCode'); // use this instead of cookie calls repeatedly
	
$(document).ready(function(){
	setLoggedInfo();
	//cart_load();	
	mealList();		

});

function  mymeal_sel(comboType){

	if(userInfo_glb != undefined && userInfo_glb.hm1 == true){
		var combObj = {};
		combObj.user = {
				uName: userInfo_glb.hm2,
				uCode: userInfo_glb.hm4,
				uPhone: userInfo_glb.hm5
			}	
		combObj.comboDetails = {
			cType: parseInt(comboType)
		}	

		sessionStorage.setItem("cart_combo", JSON.stringify(combObj));
		redir('mymeal_orders.jsp');
	}else{
   			$(".modal#login_2").modal('show');
   			jmp_nxt = "mymeal.jsp";
	}
}


	function mealList(){
/*		var getuPincode = Cookies.getJSON('uPincode');
		console.log(getuPincode);
		if(getuPincode == undefined){
			$("#position1").show();
		}else{
			$("#position1").hide();
		}*/


		var mdata = {};
	   	$.ajax({
			url:URL_gbl+"getComboDetails.json",
			type:"GET",
			contentType: "application/json",
			crossDomain: true,
		    success:function(data){
		    	// alert(JSON.stringify(data));
		    	mdata = data;
		    	set_mealList(mdata);
		    },
		    error:function(XHR, textStatus, errorThrown){
		    	console.log("Error Occured: "+errorThrown);
		    }

	   });

	}


	function set_mealList(mdata){
		 // console.log(JSON.stringify(mdata));

       		/*--------------re-factor MEAL JSON-------------------*/
    		var menu_lists = [],final_menuObj = {};

    		jQuery.each(mdata.comboDetails,function(i,val){
	    			var itemObj = {};
	    			if(val.comboName != 'null'){
	    				itemObj.id = parseInt(val.comboId);
		    			itemObj.name = val.comboName;
		    			itemObj.desc = "Find the suitable plan for your subscription.Find the suitable plan for your subscription. Find the suitable plan for your subscription. Find the suitable plan for your subscription. Find the suitable plan for your subscription. ";
		    			itemObj.cost = parseInt(val.cost);	    				

		    			menu_lists.push(itemObj);
	    			}
    		})
			
			final_menuObj.items = menu_lists
    		console.log(JSON.stringify(final_menuObj));

    		var menu_items = final_menuObj;

    		/*--------------re-factor MEAL JSON ends-------------------*/

    		var meal_lists = "";
		   	$.each(menu_items.items, function(index,values){

	   			var imgStr = values.name.toLowerCase();
		   		if((index%2) == 0){
		   			/*
						Even element - 
						Add  <div class="col-md-2"></div>

		   			*/
		   			meal_lists += "<div class=\"col-md-5\">"+
					                "<div>"+
					                    "<span class=\"mymeal_items_name\"><strong>"+ values.name +"</strong></span>"+
					                    "<span class=\"mymeal_items_price\"><strong>&#8377; "+ values.cost +"</strong></span>"+
					                "</div>"+
					                "<a href=\"#0\" class=\"strip_list_meal mymeal_items_div\">"+
					                    "<div class=\"desc_meal1\">"+
					                        "<div class=\"thumb_strip_meal no_border_mymeal  mymeal_overlay\">"+
					                            "<img src=\"pics/"+imgStr.replace(/\s/g,"-")+".jpg\" alt=\"\">"+
					                        "</div>"+
					                    "</div>"+ 
					                "<span class=\"mymeal_overlay_text\">"+
					                  	values.desc +
					                    "<div onclick='mymeal_sel(\""+values.id+"\");'><span class=\"btn_1 mymeal_overlay_btn\"><i class=\"icon-plus\"></i> Add</span></div>"+
					                "</span>"+
					                "</a>"+
					            "</div>";
								
					meal_lists +=	"<div class=\"col-md-2\"></div>";
		   		}else if((index%2) == 1){
		   			/*
						ODD element - 

		   			*/
		   			meal_lists += "<div class=\"col-md-5\">"+
					                "<div>"+
					                    "<span class=\"mymeal_items_name\"><strong>"+ values.name +"</strong></span>"+
					                    "<span class=\"mymeal_items_price\"><strong>&#8377; "+ values.cost +"</strong></span>"+
					                "</div>"+
					                "<a href=\"#0\" class=\"strip_list_meal mymeal_items_div\">"+
					                    "<div class=\"desc_meal1\">"+
					                        "<div class=\"thumb_strip_meal no_border_mymeal  mymeal_overlay\">"+
					                            "<img src=\"pics/"+imgStr.replace(/\s/g,"-")+".jpg\" alt=\"\">"+
					                        "</div>"+
					                    "</div>"+ 
					                "<span class=\"mymeal_overlay_text\">"+
					                  	values.desc +
					                    "<div onclick='mymeal_sel(\""+values.id+"\");'><span class=\"btn_1 mymeal_overlay_btn\"><i class=\"icon-plus\"></i> Add</span></div>"+
					                "</span>"+
					                "</a>"+
					            "</div>";
		   		}

    		});

    		$("#combo_list").html(meal_lists);
	}