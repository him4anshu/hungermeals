<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>Mailing Server Test</title>
 </head>

<body>
<%
String msg="";
if(request.getAttribute("MSG")!=null)
msg=request.getAttribute("MSG").toString(); 

%>
<div id="wrapper">
		<div id="main_content">								
			<div id="upload_content_area">		
			<form id="contentupload" name="contentupload" action="../jsp/serverTest1.jsp" method="get"	enctype="multipart/form-data">
				
				 <div class="converterMid" align="center">
				 <h1 >Server Test</h1>
						</br></br></br>
		         <table>
		         <tr><td><b>Host Name:</b></td><td><input type="text" name="host" id="host" value="smtp.zoho.com"/></td></tr>
		         <tr><td><b>Port:</b></td><td><input type="text" name="port" id="port" value="465"/></td></tr>
		         <tr><td><b>From Name:</b></td><td><input type="text" name="fromName" id="from" value="Hungermeals Team"/></td></tr>
		         <tr><td><b>From Email:</b></td><td><input type="text" name="fromEmail" id="from" value="rejesh@hungermeals.com"/></td></tr>
		         <tr><td><b>Password:</b></td><td><input type="password" name="password" id="password"/></td></tr>
		         <tr><td colspan="2"></td></tr>
		         <tr><td><b>To:</b></td><td><input type="text" name="to" id="to" value="himanshuonemail@gmail.com"/></td></tr>
		         <tr><td><b>Subject:</b></td><td><input type="text" name="subject" id="subject" value="Mailer Server Test"/></td></tr>
		         <tr><td><b>Message:</b></td><td><input type="text" name="message" id="messgage" value="This is test mail"/></td></tr>
		         <tr><td colspan="2"><div align="center" ><input class="global_button" type="submit" value="Send" name="Submit" /></div></td></tr>
		         <tr><td colspan="2"><div align="center" ><b style="color: red"><%=msg%></b></div></td></tr>
		         </table>
			 </form>
		</div>
	  </div>
	 </div>>
</body>

</html>