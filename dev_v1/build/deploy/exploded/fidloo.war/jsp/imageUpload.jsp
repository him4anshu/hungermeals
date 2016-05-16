<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page language="java" import="java.util.*" %>
	
	
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<body>
<%
String msg="";
if(request.getAttribute("MSG")!=null)
msg=request.getAttribute("MSG").toString(); 

%>
<div id="wrapper">
		<div id="main_content">
		     								
			<div id="upload_content_area">	
			<fieldset id="Image Upload">
        <legend style="font-style: italic ; font-weight: bold; color: red ;" >Browse Image</legend>	
			<form id="contentupload" name="contentupload" action="../jsp/imageUploadProcessing.jsp" method="post"	enctype="multipart/form-data">
				<h1 class="font_size_16 font_normal medium_gray border_bottom_dashed padding_5" >Template Image Upload</h1>
						</br></br></br>
				 <div class="converterMid" align="center">
		         <table>        
				<tr>
				<td colspan="2"><input type="file" name="file"/></td>		
				</tr>
				<tr>
				<td><input type="submit" value="Validate" style="width:150px;height:25px;"/></td>
				</tr>
				</table>
			 </form>
	    </fieldset>
		</div>
	  </div>
	 </div>
</body>

</html>