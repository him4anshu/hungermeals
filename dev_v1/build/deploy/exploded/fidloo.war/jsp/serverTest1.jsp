<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="java.util.Properties"%>
<%@page import="javax.mail.Session"%>
<%@page import="javax.mail.Message"%>
<%@page import="javax.mail.internet.MimeMessage"%>
<%@page import="javax.mail.internet.InternetAddress"%>
<%@page import="javax.mail.Transport"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
String host =request.getParameter("host");
String user = request.getParameter("fromEmail");
String fromName = request.getParameter("fromName");
String pass = request.getParameter("password");
String to = request.getParameter("to");
String subject = request.getParameter("subject");
String message= request.getParameter("message");
String port= request.getParameter("port");

boolean sessionDebug = false;
System.out.println("host"+host);
System.out.println("user"+user);
System.out.println("pass"+pass);
System.out.println("to"+to);
System.out.println("subject"+subject);
System.out.println("message"+message);


if(host!=null && user!=null && fromName!=null && pass!=null && to!=null && subject!=null && message!=null && pass.length()!=0){
	System.out.println("Pass Length="+pass.length());
	System.out.println("PassWORD="+pass);
Properties props = System.getProperties();
props.put("mail.host", host);
props.put("mail.smtp.port", port);
props.put("mail.smtp.auth", "true");
props.put("mail.transport.protocol", "smtp");
props.put("mail.smtp.user",user);
props.put("mail.smtp.password",pass);

props.setProperty("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
props.setProperty("mail.smtp.socketFactory.fallback","false");
props.setProperty("mail.smtp.socketFactory.port",port);
props.put("mail.smtp.startssl.enable", "true");



Session mailSession = Session.getInstance(props);
mailSession.setDebug(sessionDebug);

Message msg = new MimeMessage(mailSession);
msg.setFrom(new InternetAddress(user,fromName));
InternetAddress addr=new InternetAddress();
addr.setAddress(to);

msg.addRecipient(Message.RecipientType.TO, addr);
msg.setSubject(subject);
msg.setContent(message, "text/html");

try{
Transport transport = mailSession.getTransport("smtp");
transport.connect(host,user, pass);
transport.sendMessage(msg, msg.getAllRecipients());
transport.close();
request.setAttribute("MSG","Email Sent!!!");
}catch(Exception e){
	request.setAttribute("MSG","Problem in connection with mailer server");
	e.printStackTrace();
}
}else{
	request.setAttribute("MSG","Please Enter valid details");
}
RequestDispatcher rd=request.getRequestDispatcher("../jsp/serverTest.jsp");
rd.forward(request,response);
%>
</body>
</html>
