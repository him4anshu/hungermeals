function edit(TemplateLinkId,tempalateId,linkvalue){
	//alert("edit TemplateLinkId--->"+TemplateLinkId);
	//alert("edit tempalateId--->"+tempalateId);
	//alert("edit linkvalue--->"+linkvalue);
	document.getElementById("editButton"+linkvalue).style.display="none";
	document.getElementById("saveButton"+linkvalue).style.display="block";
	document.getElementById("delButton"+linkvalue).style.display="block";
	document.getElementById("linkvalue"+linkvalue).disabled=false;
	
}
function save(TemplateLinkId,tempalateId,linkvalue){
	//alert("TemplateLinkId--->"+TemplateLinkId);
	//alert("tempalateId--->"+tempalateId);
	//alert("linkvalue--->"+linkvalue);
	var linkvalue1 = $("#linkvalue"+linkvalue).val();
	document.getElementById("saveButton"+linkvalue).style.display="none";
	var serverUrl = "../services/json/editLinks.json";
	var jsonText ="{\"cpInfo\":{\"templateLinkId\":"+TemplateLinkId+",\"templateBody\":\""+linkvalue1+"\",\"templateId\":"+tempalateId+"}}";
	//alert("jsonText:::"+jsonText);

	$.ajax({
		url:serverUrl,
		type:"POST",
		contentType: "application/json",
	    data: jsonText,
		//data: JSON.stringify({ "TemplateLinkId": TemplateLinkId, "templateBody" : linkvalue1 , "templateId" : tempalateId })
		success:save_success(linkvalue),
		error:function(XHR, textStatus, errorThrown){
		alert("Error Occured: "+errorThrown);
	}
	});
	
}
function save_success(linkvalue){
	document.getElementById("editButton"+linkvalue).style.display="block";
	document.getElementById("delButton"+linkvalue).style.display="block";
}

function deleteRow1(TemplateLinkId,tempalateId,rowId){
	//alert("TemplateLinkId--->"+TemplateLinkId);
	//alert("tempalateId--->"+tempalateId);
	//alert("rowId--->"+rowId);
	var serverUrl = "../services/json/deleteLinks.json/"+TemplateLinkId+"/"+tempalateId;
	$.ajax({
		url:serverUrl,
		type:"GET",
		success:delete_success(rowId),
		error:function(XHR, textStatus, errorThrown){
		alert("Error Occured: "+errorThrown);
	}
	});
	
}

function delete_success(rowId){
	//alert("in delete success");
	document.getElementById("rowId"+rowId).style.display="none";
	
}

function show(Contact_id){
	//alert("Contact_id---->"+Contact_id);
	var serverUrl = "../services/json/getEmailLeadDetails.json/"+Contact_id;
	$.ajax({
		url:serverUrl,
		type:"GET",
		success:show_success,
		error:function(XHR, textStatus, errorThrown){
		alert("Error Occured: "+errorThrown);
	}
	});
}

/*function show_success(data){
	var contents = "<table  border='1' >"
	contents+= "<tr><td><h1 class='font_size_4 font_normal medium_gray'>Name </h1></td><td><h1 class='font_size_4 font_normal medium_gray'>SubscriberCreationDate</h1></td><td><h1 class='font_size_4 font_normal medium_gray'>SubscriberUpdateDATE</h1></td><td><h1 class='font_size_4 font_normal medium_gray'>ASSOCIATION_NAME</h1></td>";
	contents+= "<td><h1 class='font_size_4 font_normal medium_gray'>Company_industry</h1></td><td><h1 class='font_size_4 font_normal medium_gray'>Company_name</h1></td><td><h1 class='font_size_4 font_normal medium_gray'>Encrypted_key</h1></td><td><h1 class='font_size_4 font_normal medium_gray'>Unsubscribe_status</h1></td>";
	contents+= "<td><h1 class='font_size_4 font_normal medium_gray'>Company_or_association</h1></td><td><h1 class='font_size_4 font_normal medium_gray'>Email Id</h1></td></tr>";
		$.each(data.cpInfo,function(i,item){
			var recipientsName = item.recipientsName;
			var subscriberCreationDate= item.subscriberCreationDate;
			var subscriberUpdateDATE= item.subscriberUpdateDATE;
			var ASSOCIATION_NAME= item.ASSOCIATION_NAME;
			var company_industry= item.company_industry;
			var company_name= item.company_name;
			var encrypted_key= item.encrypted_key;
			var unsubscribe_status= item.unsubscribe_status;
			var company_or_association= item.company_or_association;
			var emailid= item.emailid;
			contents+= "<tr><td class='border_bottom medium_gray'><font size='3'>"+recipientsName+"</td><td class='border_bottom medium_gray'><font size='3'>"+subscriberCreationDate+"</td><td class='border_bottom medium_gray'><font size='3'>"+subscriberUpdateDATE+"</td><td class='border_bottom medium_gray'><font size='3'>"+ASSOCIATION_NAME+"</td>";
			contents+= "<td class='border_bottom medium_gray'><font size='3'>"+company_industry+"</td><td class='border_bottom medium_gray'><font size='3'>"+company_name+"</td><td class='border_bottom medium_gray'><font size='3'>"+encrypted_key+"</td><td class='border_bottom medium_gray'><font size='3'>"+unsubscribe_status+"</td>";
			contents+= "<td class='border_bottom medium_gray'><font size='3'>"+company_or_association+"</td><td class='border_bottom medium_gray'><font size='3'>"+emailid+"</td></tr>";

		});
	contents+= "</table>";
	//$("#dialog").dialog({
	//	height: 300,
	//	width: 900,
	//	title:"Lead Information",
	//	open: function(event, ui) {
	//	     $('#Message').html(contents);
	//	   }
	//	
	//});
	$("#Message").html(contents);
	//alert("content----------->"+contents)
}*/

function show_success(data){
	var contents = "<table  border='1' >"
	
		$.each(data.cpInfo,function(i,item){
			var recipientsName = item.recipientsName;
			var subscriberCreationDate= item.subscriberCreationDate;
			var subscriberUpdateDATE= item.subscriberUpdateDATE;
			var country_name= item.ASSOCIATION_NAME;
			var company_industry= item.company_industry;
			var company_name= item.company_name;
			var encrypted_key= item.encrypted_key;
			var unsubscribe_status= item.unsubscribe_status;
			var company_or_association= item.company_or_association;
			var emailid= item.emailid;
			contents+= "<tr><td><h1 class='font_size_4 font_normal medium_gray'>User Name </h1></td> <td class='border_bottom medium_gray'><font size='3'>"+recipientsName+"</td></tr> <tr><td><h1 class='font_size_4 font_normal medium_gray'>SubscriberCreationDate</h1></td> <td class='border_bottom medium_gray'><font size='3'>"+subscriberCreationDate+"</td></tr> <tr><td><h1 class='font_size_4 font_normal medium_gray'>SubscriberUpdateDATE</h1></td> <td class='border_bottom medium_gray'><font size='3'>"+subscriberUpdateDATE+"</td></tr><tr><td><h1 class='font_size_4 font_normal medium_gray'>Country Name</h1></td> <td class='border_bottom medium_gray'><font size='3'>"+country_name+"</td></tr>";
			
contents+= "<tr><td><h1 class='font_size_4 font_normal medium_gray'>Company_industry</h1></td> <td class='border_bottom medium_gray'><font size='3'>"+company_industry+"</td></tr> <tr><td><h1 class='font_size_4 font_normal medium_gray'>Company_name</h1></td> <td class='border_bottom medium_gray'><font size='3'>"+company_name+"</td></tr> <tr><td><h1 class='font_size_4 font_normal medium_gray'>Encrypted_key</h1></td> <td class='border_bottom medium_gray'><font size='3'>"+encrypted_key+"</td></tr> <tr> <td><h1 class='font_size_4 font_normal medium_gray'>Unsubscribe_status</h1></td><td class='border_bottom medium_gray'><font size='3'>"+unsubscribe_status+"</td></tr>";
			
contents+= "<tr> <td><h1 class='font_size_4 font_normal medium_gray'>Company_or_association</h1></td> <td class='border_bottom medium_gray'><font size='3'>"+company_or_association+"</td></tr> <tr><td><h1 class='font_size_4 font_normal medium_gray'>Email Id</h1></td> <td class='border_bottom medium_gray'><font size='3'>"+emailid+"</td></tr>";

		});
	contents+= "</table>";
	//$("#dialog").dialog({
	//	height: 300,
	//	width: 900,
	//	title:"Lead Information",
	//	open: function(event, ui) {
	//	     $('#Message').html(contents);
	//	   }
	//	
	//});
	$("#Message").html(contents);
	//alert("content----------->"+contents)
}
function displayTrackingList(startlimit,endlimit,CM_ID){
	//alert("startlimit--->"+startlimit);
	//alert("endlimit--->"+endlimit);
	//alert("CM_ID--->"+CM_ID);
	
	var serverUrl = "../services/json/getCampaignDeliveryListTrack.json/"+startlimit+"/"+endlimit+"/"+CM_ID;
	$.ajax({
		url:serverUrl,
		type:"GET",
		success:displayTrackingListSuccess,
		error:function(XHR, textStatus, errorThrown){
		alert("Error Occured: "+errorThrown);
	}
	});
	
}
function displayTrackingListSuccess(data){

	var contents = ""
		//alert("data---->"+data);
	//alert("contentswt---->"+contents);
		$.each(data.cpInfo,function(i,item){
			
			var emailid= item.emailid;
			var recipientsName = item.recipientsName;
			var deliveryStatus= item.deliveryStatus;
			var trackingCount= item.trackingCount;
			var deliveryResult="";
			if(jQuery.trim(deliveryStatus) == 'A'){
				deliveryResult="Delivered";
			}else if(jQuery.trim(deliveryStatus) == 'D'){
				deliveryResult="Failed";
			}else{
				deliveryResult="in progress";
			}
			contents+= "<tr ><td valign='top' class='padding_5 border_bottom medium_gray'>"+recipientsName+"</td>";
			contents+= "<td valign='top' class='padding_5 border_bottom medium_gray'>"+emailid+"</td>";
			contents+= "<td valign='top' class='padding_5 border_bottom medium_gray'>"+deliveryResult+"</td>";
			contents+= "<td valign='top' class='padding_5 border_bottom medium_gray'>"+trackingCount+"</td></tr>";
		});
	//alert("contents------------->"+contents);
	
	$("#contentHolder").html(contents);
	//alert("content----------->"+contents)

}


function getGroupEmailList2(startlimit,endlimit,GroupID){
	//alert("startlimit--->"+startlimit);
	//alert("endlimit--->"+endlimit);
	//alert("GroupID--->"+GroupID);
	var serverUrl = "../services/json/getGroupEmailList2.json/"+startlimit+"/"+endlimit+"/"+GroupID;
	$.ajax({
		url:serverUrl,
		type:"GET",
		success:getGroupEmailList2Success,
		error:function(XHR, textStatus, errorThrown){
		alert("Error Occured: "+errorThrown);
	}
	});
	
}
function getGroupEmailList2Success(data){

	var contents = ""
		//alert("getGroupEmailList2Success data---->"+data);
	//alert("getGroupEmailList2Success contentswt---->"+contents);
		$.each(data.cpInfo,function(i,item){
			
			var emailid= item.emailid;
			var recipientsName = item.recipientsName;
			
			contents+= "<tr ><td valign='top' class='padding_5 border_bottom medium_gray'><font COLOR=DarkBlue><a href='../jsp/showGroupEmailList2.jsp?contact_id=<%=bean.getContact_id()%>' target='_blank'>"+recipientsName+"</a></td>";
			contents+= "<td valign='top' class='padding_5 border_bottom medium_gray'> <font COLOR=DarkBlue><a href='../jsp/showGroupEmailList2.jsp?contact_id=<%=bean.getContact_id()%>' target='_blank'>"+emailid+"</a></td>";
										
		});
	//alert("getGroupEmailList2Success contents------------->"+contents);
	
	$("#contentHolder").html(contents);
	//alert("content----------->"+contents)

}


function getCategoryEmailList2(startlimit,endlimit,GroupID){
	//alert("startlimit--->"+startlimit);
	//alert("endlimit--->"+endlimit);
	//alert("GroupID--->"+GroupID);
	var serverUrl = "../services/json/getCategoryEmailList2.json/"+startlimit+"/"+endlimit+"/"+GroupID;
	$.ajax({
		url:serverUrl,
		type:"GET",
		success:getCategoryEmailList2Success,
		error:function(XHR, textStatus, errorThrown){
		alert("Error Occured: "+errorThrown);
	}
	});
	
}
function getCategoryEmailList2Success(data){

	var contents = ""
	//alert("getCategoryEmailList2Success data---->"+data);
	//alert("getCategoryEmailList2Success contentswt---->"+contents);
		$.each(data.cpInfo,function(i,item){
			var contact_id=item.contact_id;
			var emailid= item.emailid;
			var recipientsName = item.recipientsName;
			
			contents+="<tr ><td valign='top' class='padding_5 border_bottom medium_gray' width='50px'><input type='checkbox' name='Id' id='Id' value='"+contact_id+"'/></td>";
			contents+= "<td valign='top' class='padding_5 border_bottom medium_gray'><font COLOR=DarkBlue><a href='../jsp/showGroupEmailList2.jsp?contact_id=<%=bean.getContact_id()%>' target='_blank'>"+recipientsName+"</a></td>";
			contents+= "<td valign='top' class='padding_5 border_bottom medium_gray'> <font COLOR=DarkBlue><a href='../jsp/showGroupEmailList2.jsp?contact_id=<%=bean.getContact_id()%>' target='_blank'>"+emailid+"</a></td>";
										
		});
	//alert("getCategoryEmailList2 success contents------------->"+contents);
	
	$("#contentHolder").html(contents);
	//alert("content----------->"+contents)

}


