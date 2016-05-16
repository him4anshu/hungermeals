<!DOCTYPE html>
<!--[if IE 9]><html class="ie ie9"> <![endif]-->
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="keywords" content="Home cooked, Delivery, Fresh, Pure, Take Away, Meals, Delicious, Healthy, Nutritious, Ingredients, Comfortable, Timely">
    <meta name="description" content="">
    <meta name="author" content="Varunsk23">
    <title>HungerMeals</title>



</head>

<body>
	<h2>Please wait while loading...Do not refresh</h2>
	<div id="aa"></div>
	
	<form action="https://pguat.paytm.com/oltp-web/processTransaction" method="POST" id="myform" name="myform">
<!-- 		<input type="text" name="REQUEST_TYPE" value="1234">
		<input type="text" name="MID" value="1234">
		<input type="text" name="ORDER_ID" value="1234">
		<input type="text" name="CUST_ID" value="1234">
		<input type="text" name="TXN_AMOUNT" value="1234">
		<input type="text" name="CHANNEL_ID" value="1234">
		<input type="text" name="INDUSTRY_TYPE_ID" value="1234">
		<input type="text" name="WEBSITE" value="1234">
		<input type="text" name="CHECKSUMHASH" value="1234">
		<input type="text" name="MOBILE_NO" value="1234">
		<input type="text" name="EMAIL" value="1234">
		<input type="text" name="ORDER_DETAILS" value="1234">
		<input type="text" name="VERIFIED_BY" value="1234">
		<input type="text" name="IS_USER_VERIFIED" value="1234">
 -->
		<input type="submit" name="myform_submit" value="submit">
	</form>

	<script src="js/jquery-1.11.2.min.js"></script>
	<script type="text/javascript">


	var	getOrderList = JSON.parse(localStorage.getItem("orderPay"));
 	/*var getOrderList = {
					  "orderStatus": {
					    "orderId": 153,
					    "orderStatusCode": 1,
					    "orderStatusDesc": "Order placed",
					    "responseStatus": {
					      "responseCode": "HM200",
					      "responseMessage": "Operation Executed Successfully."
					    },
					    "walletRequest": {
					      "entry": [
					        {
					          "key": "CALLBACK_URL",
					          "value": "http://104.238.96.134:8080/hungermeals/services/userservices/paytmWalletResponse.json"
					        },
					        {
					          "key": "CHANNEL_ID",
					          "value": "WEB"
					        },
					        {
					          "key": "CHECKSUMHASH",
					          "value": "bqtZtswY2DJgT5wiIjxR6lAIkS8o3KaOn+t20uQNKXlNi0y9obWulP3bIyh4q47zV12LtL9an0R+uTbf8vUrYhM4b7hhF4tlluedIbmxyVE="
					        },
					        {
					          "key": "CUST_ID",
					          "value": "011ec63d-a1ef-42f6-93d3-e1d343536a9b"
					        },
					        {
					          "key": "EMAIL"
					        },
					        {
					          "key": "INDUSTRY_TYPE_ID",
					          "value": "Retail"
					        },
					        {
					          "key": "IS_USER_VERIFIED",
					          "value": "YES"
					        },
					        {
					          "key": "MID",
					          "value": "WorldP64425807474247"
					        },
					        {
					          "key": "MOBILE_NO",
					          "value": 8123719594
					        },
					        {
					          "key": "ORDER_DETAILS",
					          "value": "Some messages"
					        },
					        {
					          "key": "ORDER_ID",
					          "value": 153
					        },
					        {
					          "key": "REQUEST_TYPE",
					          "value": "DEFAULT"
					        },
					        {
					          "key": "TXN_AMOUNT",
					          "value": 99
					        },
					        {
					          "key": "VERIFIED_BY",
					          "value": "MOBILE_NO"
					        },
					        {
					          "key": "WEBSITE",
					          "value": "worldpressplg"
					        }
					      ]
					    }
					  }
					}; */

/*		var getOrderList = {
						  "orderStatus": {
						    "orderId": 150,
						    "orderStatusCode": 1,
						    "orderStatusDesc": "Order placed",
						    "responseStatus": {
						      "responseCode": "HM200",
						      "responseMessage": "Operation Executed Successfully."
						    },
						    "walletRequest": {
						      "CALLBACK_URL": "http://104.238.96.134:8080/hungermeals/services/userservices/paytmWalletResponse.json",
						      "CHANNEL_ID": "WEB",
						      "CUST_ID": "011ec63d-a1ef-42f6-93d3-e1d343536a9b",
						      "INDUSTRY_TYPE_ID": "Retail",
						      "IS_USER_VERIFIED": "YES",
						      "CHECKSUMHASH":"cXknqz6vwcCcoX1lSJ1Vn6M4MzvG2fPAbltPPqZtnfoWFZplVp8EfRn0YGYcgtuKEmdiTz9lPW2GjIh3FZVLRaEzdXdwjbxPYKjJk5Woc",
						      "MID": "WorldP64425807474247",
						      "MOBILE_NO": "8123719594",
						      "ORDER_DETAILS": "Some messages",
						      "ORDER_ID": "150",
						      "REQUEST_TYPE": "DEFAULT",
						      "TXN_AMOUNT": "99.0",
						      "VERIFIED_BY": "MOBILE_NO",
						      "WEBSITE": "worldpressplg"
						    }
						  }
						};*/

		console.log(getOrderList);
		document.getElementById('aa').innerHTML = JSON.stringify(getOrderList);

		$(function(){
/*				for(var i in getOrderList.orderStatus.walletRequest){
				var key_value = (getOrderList.orderStatus.walletRequest[i])? getOrderList.orderStatus.walletRequest[i] : "none"; 
				$("#myform").append("<input type='text' name='"+i+"' value='"+key_value+"'>");

			}

*/

		jQuery.each(getOrderList.orderStatus.walletRequest.entry, function(i,val){
				// alert(JSON.stringify(val.key));
				var key_value = (val.value)? val.value : ""; 
				// $("input[name='"+val.key+"']").val(key_value);
				$("#myform").append("<input type='text' name='"+val.key+"' value='"+key_value+"'>");
			});



		//REMOVE orderPay after call
		// localStorage.removeItem("orderPay");
		// document.forms['myform'].submit();

		});

	</script>
</body>
</html>