$(document).ready(function(){
    var getLogInfo = Cookies.getJSON('uCode');
    //check login
    if(getLogInfo != undefined){
        addonList();		
        cart_load();
        setLoggedInfo();
    }else{
        redir('index.jsp');
    }
	
	// Add-on cart click
	$("#add_on_btn").click(function(e){
		e.preventDefault();
			var getLogInfo = Cookies.getJSON('uCode');
			// console.log(getLogInfo);
			if(getLogInfo != undefined && getLogInfo.hm1 == true &&  getLogInfo.hm6 == true){
				var temp_cart_selectedItmes = sessionStorage.getItem("cart_items");
        		var ses_cart_selectedItmes = $.parseJSON(temp_cart_selectedItmes);
        		if(ses_cart_selectedItmes == null || ses_cart_selectedItmes == undefined || ses_cart_selectedItmes == ""){
					hm_alert("Please add meals to check-out!!!");
					redir('menu.jsp');
        		}else{
					redir('addr.jsp');
        		}
			}else if(getLogInfo.hm6 == false){
				hm_alert("Please verify your mobile!!");
				redir('settings.jsp');
			}else{
		   			$(".modal#login_2").modal('show');
			}
	});


});


	function addonList(){
		var mdata = {};
	   	$.ajax({
			url:URL_gbl+"menuDetail.json",
			type:"GET",
			crossDomain: true,
			contentType: "application/json",
		    success:function(data){
		    	// alert(JSON.stringify(data));
		    	mdata = data;
		    	set_addonList(mdata);		    	
		    },
		    error:function(XHR, textStatus, errorThrown){
		    	console.log("Error Occured: "+errorThrown);
		    }

	   });
/*	   var mdata = {
  "menu": [
    {
      "name": "Addons",
      "description": "Traditiona sweets",
      "itemList": [
        {
          "itemId": "7",
          "itemName": "Pepper Chicken",
          "perItemCost": "100",
          "description": "Roasted babycorns with fresh "
        },
        {
          "itemId": "21",
          "itemName": "Gulam Jamun",
          "perItemCost": "70",
          "description": "Roasted babycorns with fresh guntur chilly."
        }
      ]
    }
  ]
};
			set_addonList(mdata);

*/	}



	function set_addonList(mdata){
       		/*--------------re-factor MENU JSON-------------------*/
    		var menu_lists = [],final_menuObj = {};
    		jQuery.each(mdata.menu,function(i,menulist){
    			jQuery.each(menulist.itemList,function(index,val){
	    			var itemObj = {};
    				// alert(menulist.name);
	    			itemObj.id = parseInt(val.itemId);
	    			itemObj.category = menulist.name;
	    			itemObj.name = val.itemName;
	    			itemObj.desc = val.description;
	    			itemObj.cost = parseInt(val.perItemCost);	    				

	    			menu_lists.push(itemObj);
    			});
    		})
			
			final_menuObj.items = menu_lists
    		// console.log(JSON.stringify(final_menuObj));

    		var menu_items = final_menuObj;

	   		$(".strtr_desc").html(mdata.menu[0].description);


    		/*--------------re-factor MENU JSON ends-------------------*/

    		var addon_list = "";
		   	$.each(menu_items.items, function(index,values){
		   		var imgStr = values.name.toLowerCase();
    			if(values.category == 'Add On'){
					addon_list += "<div class=\"col-md-6 menu_items_div\">"+
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
    			}
    		});

    		$("#addon_list").html(addon_list);

    		//Flying Cart
    		flying_cart();
	}
