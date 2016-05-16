<%@ page import="java.util.*" %>
<%@ page import="java.security.*" %>
<%@ page import="com.hungermeals.common.*" %>

<%!
public boolean empty(String s)
	{
		if(s== null || s.trim().equals(""))
			return true;
		else
			return false;
	}
%>
<%!
	public String hashCal(String type,String str){
		byte[] hashseq=str.getBytes();
		StringBuffer hexString = new StringBuffer();
		try{
		MessageDigest algorithm = MessageDigest.getInstance(type);
		algorithm.reset();
		algorithm.update(hashseq);
		byte messageDigest[] = algorithm.digest();
            
		

		for (int i=0;i<messageDigest.length;i++) {
			String hex=Integer.toHexString(0xFF & messageDigest[i]);
			if(hex.length()==1) hexString.append("0");
			hexString.append(hex);
		}
			
		}catch(NoSuchAlgorithmException nsae){ }
		
		return hexString.toString();


	}
%>
<% 	
	String merchant_key=PayuConstant.MERCHANT_KEY;
	String salt=PayuConstant.SALT;
	String action1 ="";
	String base_url=PayuConstant.PAYU_URL;
	int error=0;
	String hashString="";
	
	Enumeration paramNames = request.getParameterNames();
	Map<String,String> params= new HashMap<String,String>();
    	while(paramNames.hasMoreElements()){
      		String paramName = (String)paramNames.nextElement();
      		String paramValue = request.getParameter(paramName);
			params.put(paramName,paramValue);
		}
	String txnid ="";
	String udf2 = "";
	if(empty(params.get("txnid"))){
		Random rand = new Random();
		String rndm = Integer.toString(rand.nextInt())+(System.currentTimeMillis() / 1000L);
		txnid=hashCal("SHA-256",rndm).substring(0,20);
	}
	else
		txnid=params.get("txnid");
        udf2 = txnid;
	String txn="abcd";
	String hash="";
	String hashSequence = "key|txnid|amount|productinfo|firstname|email|udf1|udf2|udf3|udf4|udf5|udf6|udf7|udf8|udf9|udf10";
	if(empty(params.get("hash")) && params.size()>0)
	{
		if( empty(params.get("key"))
			|| empty(params.get("txnid"))
			|| empty(params.get("amount"))
			|| empty(params.get("firstname"))
			|| empty(params.get("email"))
			|| empty(params.get("phone"))
			|| empty(params.get("productinfo"))
			|| empty(params.get("surl"))
			|| empty(params.get("furl"))
			|| empty(params.get("service_provider"))
	)
			
			error=1;
		else{
			String[] hashVarSeq=hashSequence.split("\\|");
			
			for(String part : hashVarSeq)
			{
				hashString= (empty(params.get(part)))?hashString.concat(""):hashString.concat(params.get(part));
				hashString=hashString.concat("|");
			}
			hashString=hashString.concat(salt);
			

			 hash=hashCal("SHA-512",hashString);
			action1=base_url.concat("/_payment");
		}
	}
	else if(!empty(params.get("hash")))
	{
		hash=params.get("hash");
		action1=base_url.concat("/_payment");
	}
	StringBuilder outputHtml = new StringBuilder();
	outputHtml.append("<!DOCTYPE html PUBLIC '-//W3C//DTD HTML 4.01 Transitional//EN' 'http://www.w3.org/TR/html4/loose.dtd'>");
	outputHtml.append("<html>");
	outputHtml.append("<head>");
	outputHtml.append("<title>Merchant Check Out Page</title>");
	outputHtml.append("</head>");
	outputHtml.append("<body>");
	outputHtml.append("<center><h1>Please do not refresh this page...</h1></center>");
	outputHtml.append("<form method='post' action='"+ action1 +"' name='f1'>");
	outputHtml.append("<table border='1'>");
	outputHtml.append("<tbody>");

	for(Map.Entry<String,String> entry : params.entrySet()) {
		String key = entry.getKey();
		String value = entry.getValue();
		outputHtml.append("<input type='hidden' name='"+key+"' value='" +value+"'>");	
		System.out.println("<input type='hidden' name='"+key+"' value='" +value+"'>");
	}	  
	outputHtml.append("<input type='hidden' name='hash' value='"+hash+"'>");
	System.out.println("<input type='hidden' name='hash' value='"+hash+"'>");
	outputHtml.append("</tbody>");
	outputHtml.append("</table>");
	outputHtml.append("<script type='text/javascript'>");
	outputHtml.append("document.f1.submit();");
	outputHtml.append("</script>");
	outputHtml.append("</form>");
	outputHtml.append("</body>");
	outputHtml.append("</html>");
	out.println(outputHtml);		
%>
