/*
 * Global Variables
 * */
	var URL_gbl = "../services/userservices/";
	var jmp_nxt = "";
	
$(document).ready(function(){
	setLoggedInfo();
	cart_load();
	menuList();		


	// Menu cart click
	$("#menu_btn").click(function(e){
		e.preventDefault();
			var getLogInfo = Cookies.getJSON('uCode');
			// console.log(getLogInfo);
			if(getLogInfo != undefined && getLogInfo.hm1 == true){
				var temp_cart_selectedItmes = sessionStorage.getItem("cart_items");
        		var ses_cart_selectedItmes = $.parseJSON(temp_cart_selectedItmes);
        		if(ses_cart_selectedItmes == null || ses_cart_selectedItmes == undefined || ses_cart_selectedItmes == ""){
					hm_alert("Please add meals to check-out!!!");
        		}else{
					redir('addon.jsp');
        		}
			}else{
		   			$(".modal#login_2").modal('show');
		   			jmp_nxt = "addon.jsp";
			}
	});

});


	function menuList(){
/*		var getuPincode = Cookies.getJSON('uPincode');
		console.log(getuPincode);
		if(getuPincode == undefined){
			$("#position1").show();
		}else{
			$("#position1").hide();
		}*/


		var mdata = {};
	   	$.ajax({
			url:URL_gbl+"menuDetail.json",
			type:"GET",
			contentType: "application/json",
			crossDomain: true,
		    success:function(data){
		    	// alert(JSON.stringify(data));
		    	mdata = data;
		    	set_menuList(mdata);
		    },
		    error:function(XHR, textStatus, errorThrown){
		    	console.log("Error Occured: "+errorThrown);
		    }

	   });

/*	   var mdata = {
				  "menu": [
				    {
				      "name": "Starter",
				      "description": "Served as hot n spicy Andhra style.",
				      "itemList": [
				        {
				          "itemId": "1",
				          "itemName": "Baby Corn Chilly",
				          "perItemCost": "40",
				          "description": "Roasted babycorns with fresh "
				        }
				      ]
				    },
				    {
				      "name": "Main Course",
				      "description": "Rice and Cury",
				      "itemList": [
				        {
				          "itemId": "4",
				          "itemName": "North thali",
				          "perItemCost": "120",
				          "description": "Roasted babycorns with fresh "
				        }
				      ]
				    },
				    {
				      "name": "Desert",
				      "description": "Traditiona sweets",
				      "itemList": [
				        {
				          "itemId": "7",
				          "itemName": "Chocolate chips",
				          "perItemCost": "100",
				          "description": "Roasted babycorns with fresh "
				        }
				      ]
				    }
				  ]
				};
*/
	}


	function set_menuList(mdata){
       		/*--------------re-factor MENU JSON-------------------*/
    		var menu_lists = [],final_menuObj = {};

    		jQuery.each(mdata.menu,function(i,menulist){
    			jQuery.each(menulist.itemList,function(index,val){
	    			var itemObj = {};
	    			if(val.itemName != 'null'){
	    				itemObj.id = parseInt(val.itemId);
		    			itemObj.category = menulist.name;
		    			itemObj.name = val.itemName;
		    			itemObj.desc = val.description;
		    			itemObj.cost = parseInt(val.perItemCost);	    				

		    			menu_lists.push(itemObj);
	    			}
    			});
    		})
			
			final_menuObj.items = menu_lists
    		// console.log(JSON.stringify(final_menuObj));

    		var menu_items = final_menuObj;

	   		$(".strtr_desc").html(mdata.menu[0].description);
    		$(".maco_desc").html(mdata.menu[1].description);
    		$(".dess_desc").html(mdata.menu[2].description);


    		/*--------------re-factor MENU JSON ends-------------------*/

    		var starters_list = "",main_courses_list = "",biryani_list = "";
		   	$.each(menu_items.items, function(index,values){
	   			var imgStr = values.name.toLowerCase();

    			if(values.category == 'South Indian Meals'){
					starters_list += "<div class=\"col-md-6 menu_items_div\">"+
	                   		"<a href=\"../../images/"+imgStr.replace(/\s/g,"-")+".jpg\" title=\""+imgStr.replace(/\s/g,"-")+".jpg\"><img src=\"../images/"+imgStr.replace(/\s/g,"-")+".jpg\" alt=\"\" class=\"img-responsive\"></a>"+
							"<span class=\"menu_overlay\"></span>"+	                   		
							"<span class=\"menu_overlay_text\">"+ values.desc +"</span>"+
							"<div class=\"desc2\">"+
								"<h3>"+values.name+"</h3>"+
								// "<div class=\"type\">"+""+"</div>"+
								// "<div class=\"location\">"+ values.desc +"</div>"+
								"<ul>"+
									"<li class=\"\"><strong><i class=\"icon-rupee\"></i>"+ values.cost +"</strong></li>"+
								"</ul>"+
							"</div>"+
							"<div class=\"go_to pull-right\">"+
								"<div>"+
									// "<a href=\"#0\" onclick='cartfiller("+values.id+",\""+values.name+"\","+values.cost+")'><i class=\"icon_plus_alt2\"></i></a>"+
									"<button href=\"#0\" onclick='cartfiller("+values.id+",\""+values.name+"\","+values.cost+",\""+values.category+"\")' class=\"btn_1 fontsize_btn_1\"><i class=\"icon-plus\"></i> Add</button>"+
								"</div>"+
							"</div>"+
		               	"</div>";

    			}else if(values.category == 'North Indian Meals'){
					main_courses_list += "<div class=\"col-md-6 menu_items_div\">"+
	                   		"<a href=\"../../images/"+imgStr.replace(/\s/g,"-")+".jpg\" title=\""+imgStr.replace(/\s/g,"-")+".jpg\"><img src=\"../images/"+imgStr.replace(/\s/g,"-")+".jpg\" alt=\"\" class=\"img-responsive\"></a>"+
							"<span class=\"menu_overlay\"></span>"+	                   		
							"<span class=\"menu_overlay_text\">"+ values.desc +"</span>"+
							"<div class=\"desc2\">"+
								"<h3>"+values.name+"</h3>"+
								// "<div class=\"type\">"+""+"</div>"+
								// "<div class=\"location\">"+ values.desc +"</div>"+
								"<ul>"+
									"<li class=\"\"><strong><i class=\"icon-rupee\"></i>"+ values.cost +"</strong></li>"+
								"</ul>"+
							"</div>"+
							"<div class=\"go_to pull-right\">"+
								"<div>"+
									// "<a href=\"#0\" onclick='cartfiller("+values.id+",\""+values.name+"\","+values.cost+")'><i class=\"icon_plus_alt2\"></i></a>"+
									"<button href=\"#0\" onclick='cartfiller("+values.id+",\""+values.name+"\","+values.cost+",\""+values.category+"\")' class=\"btn_1 fontsize_btn_1\"><i class=\"icon-plus\"></i> Add</button>"+
								"</div>"+
							"</div>"+
		               	"</div>";

    			}else if(values.category == 'Biryani'){
					biryani_list += "<div class=\"col-md-6 menu_items_div\">"+
	                   		"<a href=\"../images/"+imgStr.replace(/\s/g,"-")+".jpg\" title=\""+imgStr.replace(/\s/g,"-")+".jpg\"><img src=\"../images/"+imgStr.replace(/\s/g,"-")+".jpg\" alt=\"\" class=\"img-responsive\"></a>"+
							"<span class=\"menu_overlay\"></span>"+	                   		
							"<span class=\"menu_overlay_text\">"+ values.desc +"</span>"+
							"<div class=\"desc2\">"+
								"<h3>"+values.name+"</h3>"+
								// "<div class=\"type\">"+""+"</div>"+
								// "<div class=\"location\">"+ values.desc +"</div>"+
								"<ul>"+
									"<li class=\"\"><strong><i class=\"icon-rupee\"></i>"+ values.cost +"</strong></li>"+
								"</ul>"+
							"</div>"+
							"<div class=\"go_to pull-right\">"+
								"<div>"+
									// "<a href=\"#0\" onclick='cartfiller("+values.id+",\""+values.name+"\","+values.cost+")'><i class=\"icon_plus_alt2\"></i></a>"+
									"<button href=\"#0\" onclick='cartfiller("+values.id+",\""+values.name+"\","+values.cost+",\""+values.category+"\")' class=\"btn_1 fontsize_btn_1\"><i class=\"icon-plus\"></i> Add</button>"+
								"</div>"+
							"</div>"+
		               	"</div>";
    			}
    		});

    		$("#starters_list").html(starters_list);
    		$("#main_courses_list").html(main_courses_list);
    		$("#biryani_list").html(biryani_list);    		
			flying_cart();    		

	}