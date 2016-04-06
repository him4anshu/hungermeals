<!DOCTYPE html>
<!--[if IE 9]><html class="ie ie9"> <![endif]-->
<html>

<body>
<%
String paytmResponseCode=request.getParameter("RESPCODE");
String orderId=request.getParameter("ORDERID");
if(paytmResponseCode!=null && paytmResponseCode.equals("01")){%>
	<h1>Do what ever you want to do on success for order number <%=orderId %></h1>
<%}else{%>
		<h1>Do what ever you want to do on failure for order number <%=orderId %></h1>
<%}%>
</body>
</html>