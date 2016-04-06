package com.mailer.contentprovider.filter;

import java.io.IOException;
import java.lang.management.*;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.sql.Timestamp;
import java.text.*; 
import java.util.Enumeration; 
import java.net.*;
import javax.servlet.*; 

import javax.servlet.ServletInputStream; 
import javax.servlet.http.*; 
import java.io.*;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.context.support.HttpRequestHandlerServlet;

public class NLCPCXFFilter implements Filter {
	
	private FilterConfig config;
	
	private static final Log log = LogFactory.getLog(NLCPCXFFilter.class);
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}
	 
	private ServletInputStream in; 

	private StringBuffer buffer = new StringBuffer() ; 

	@Override
	public void doFilter(ServletRequest req, ServletResponse res,
						 FilterChain chain) 
	throws IOException, ServletException ,UnsupportedEncodingException
	{
		
		
		HttpServletRequest request = (HttpServletRequest) req;
		
		
		//HttpRequestHandlerServlet reHandler=new HttpRequestHandlerServlet();
		
		//HttpRequestHandler reHandle=(HttpRequestHandler) req;
		
		//System.out.println(reHandle+ "Handler String +++++++++++++:"+reHandle.toString());
		//System.out.println("Handle Class"+reHandle.getClass());
		
		HttpServletResponse response = (HttpServletResponse) res;
		Locale clientLocale = request.getLocale();  
		Calendar calendar = Calendar.getInstance(clientLocale);  
		TimeZone clientTimeZone = calendar.getTimeZone();  
		Timestamp sT=new Timestamp(calendar.getTimeInMillis());
		
		System.out.println(sT+"-----"+calendar.getTimeInMillis()+"-------"+clientTimeZone+"@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@clientTimeZone :"+clientTimeZone.getID());
		System.out.println("clientTime getRawOffset  :"+clientTimeZone.getRawOffset());
		System.out.println("clientTime getRawOffset  :"+clientTimeZone.getDefault());
		    
		//reHandle.handleRequest(request,response);
		
		//System.out.println("++++++++CONTENT TYPE :: "+request.getContentType()+"   GPNCXFFILter ++ "+request.getCharacterEncoding());
		URLDecoder url=new URLDecoder(); 
		/*if(request.getContentType().equalsIgnoreCase("application/x-www-form-urlencoded"))
		{
			System.out.println("++++++++++POST@@@@@@@@@@@@@@@");
		}*/
	
		
		//System.out.println("++++++++AttributeNames : "+request.getAttributeNames());
		//System.out.println("++++++++Input Stream :: "+request.getInputStream().toString());
		//System.out.println("BODY ++++ :"+request.getParameter("lzpostbody"));
		//System.out.println("PARAMETER VALUES ++++ :"+request.getParameterValues("lzpostbody"));
		//System.out.println("HEADER ++++ "+request.getHeader("lzpostbody"));
		//System.out.println("HEADERSSS ++++ "+request.getHeaders("lzpostbody"));
		//URLEncoder urlEn=new URLEncoder();
		//Enumeration keys = request.getParameterNames();
		//System.out.println(request.getQueryString()+"++++++++REQUEST Before decoding .."+request.toString());
		//System.out.println(keys.hasMoreElements()+"REquest URL "+request.getRequestURL()+" ++++ URI : "+request.getRequestURI());
	//	String decodedString = url.decode(request.toString(), "utf-8");
		//System.out.println("++++++++String After decoding .."+decodedString);
		
		//StringRequestWrapper requestWrapper = new StringRequestWrapper ( request ) ; 
		/*in=request.getInputStream();*/
		System.out.println("INSIDE CXF FILTER");
		Cookie[] cookie = request.getCookies();
		if(cookie!=null)
		{
			System.out.println("COOKIE :::::: ++++ "+cookie.length);
			for(Cookie coo:cookie)
			{
				System.out.println("NAME :: "+coo.getName());
				System.out.println("Value :: "+coo.getValue());
			}
				
		}
		
		/*BufferedReader bufReader= request.getReader();
		System.out.println("BUFFERED READER+++ ");
		String line=null;
		System.out.println("LINE READER "+bufReader.readLine());*/
		
		/*in = request.getInputStream();
		  int chr = in.read (  ) ; 
	         if  ( chr!=-1 )   {  
	           buffer.append (  ( char ) chr ) ; 
	          }    
	        System.out.println("BODY ++++++++ "+buffer.toString());
		*/
		
		Enumeration keys = request.getParameterNames();
		   while (keys.hasMoreElements())
		   {
		      String key = (String)keys.nextElement();
		      System.out.println("KEY ++ : "+key);
		      //To retrieve a single value
		      String value = request.getParameter(key);
		      System.out.println("BODY +++ :: "+value);
		      // If the same key has multiple values (check boxes)
		     // String[] valueArray = request.getParameterValues();
		   }   
		   
		 


		/*while((line=bufReader.readLine())!=null)
		{
			System.out.println("LINE READER ::: "+line);
		}*/
		//System.out.println("BUFFER READER :: "+ bufReader);
		
		/*ServletRequestWrapper requestWrapper = new ServletRequestWrapper(request);
		System.out.println("REQUEST READER :: ++ "+requestWrapper.getReader());
		HttpServletRequestWrapper httpReader = new HttpServletRequestWrapper(request);
		System.out.println("HTTP  READER :: ++ "+httpReader.getReader());*/
		
		
		
		
		// in.read();
		 
		 
		
		
		/*String toDecode="%5F%5Flzbc%5F%5F=1211865919733&lzpostbody=%3CPage%3E%3CuserInfo%3E%3CuserId%3E200016%3C%2FuserId%3E%3CuserEmail%3Elskkishore%40gmail%2Ecom%3C%2FuserEmail%3E%3CorganizationInfo%3E%3CorganizationId%3E200007%3C%2ForganizationId%3E%3C%2ForganizationInfo%3E%3C%2FuserInfo%3E%3Cvalues%3E%20%3CfieldMap%3E%3CfieldMapId%3E1010%3C%2FfieldMapId%3E%3CfieldValues%3E0%3CfieldValue%3E50403508%3C%2FfieldValue%3E%3CfieldBinary%3Efalse%3C%2FfieldBinary%3E%3CparentMapId%3ETTBveou%3C%2FparentMapId%3E%3C%2FfieldValues%3E%3C%2FfieldMap%3E%3CfieldMap%3E%3CfieldMapId%3E1010%3C%2FfieldMapId%3E%3CfieldValues%3E1%3CfieldValue%3E50413408%3C%2FfieldValue%3E%3CfieldBinary%3Efalse%3C%2FfieldBinary%3E%3CparentMapId%3ETTBveou%3C%2FparentMapId%3E%3C%2FfieldValues%3E%3C%2FfieldMap%3E%3CfieldMap%3E%3CfieldMapId%3E1010%3C%2FfieldMapId%3E%3CfieldValues%3E2%3CfieldValue%3E50304201%3C%2FfieldValue%3E%3CfieldBinary%3Efalse%3C%2FfieldBinary%3E%3CparentMapId%3ETTBveou%3C%2FparentMapId%3E%3C%2FfieldValues%3E%3C%2FfieldMap%3E%3C%2Fvalues%3E%3C%2FPage%3E";
		
		
		System.out.println(" ********** Before Decoding : "+toDecode);
		String decoded=url.decode(toDecode,"UTF-8");
		//String encoded=urlEn.encode(toDecode, "UTF-8");
		System.out.println(" ********** After  Decoding : "+decoded);
	
		//System.out.println(" ********** After  Encoding "+encoded);
		
		if (request.getCharacterEncoding() == null) {
			System.out.println("Inside Ecoding....  ");
			  request.setCharacterEncoding("utf-8");
			}
		
		
		System.out.println("GPNCXFFilter::doFilter Called  REQ: " +request +" RESP : "+response );*/
		
		 

		/*try
	      {
	         String enc = request.getCharacterEncoding();
	         if (enc == null)
	         {
	            String encoding = "UTF-8";
	            request.setCharacterEncoding(encoding);
	           System.out.println("Info:setting encoding to " + encoding);
	         }
	      }
	      catch(java.io.UnsupportedEncodingException x)
	      {
	    	  System.out.println("Error: Encoding error while setting http request char encoding"+x);
	      }

		*/
		   ThreadMXBean TMB = ManagementFactory.getThreadMXBean();
		   long time = new Date().getTime() * 1000000;
		   long cput = 0;
		   double cpuperc = -1;

		   //Begin loop.

		   /*if( TMB.isThreadCpuTimeSupported() )
		   {
		       	if(new Date().getTime() * 1000000 - time > 1000000000) //Reset once per second
		       	{
		       		time = new Date().getTime() * 1000000;
		       		cput = TMB.getCurrentThreadCpuTime();
		       	}
		       				
		       	if(!TMB.isThreadCpuTimeEnabled())
		       	{
		       		TMB.setThreadCpuTimeEnabled(true);
		       	}
		       				
		       	if(new Date().getTime() * 1000000 - time != 0)
		       		cpuperc = (TMB.getCurrentThreadCpuTime() - cput) / (new Date().getTime() * 1000000.0 - time) * 100.0;  				
		   }
		   else
		   {
		       cpuperc = -2;
		   }
		   System.out.println("CPU Usage before executing request : "+cpuperc);*/
		   
		   

		   long startTime = System.currentTimeMillis();
		   cput = TMB.getCurrentThreadCpuTime();
		   System.out.println("maxMemory before : "+Runtime.getRuntime().maxMemory());
			System.out.println("totalMemory  before : "+Runtime.getRuntime().totalMemory());
			System.out.println("free memory before : "+Runtime.getRuntime().freeMemory());
			OperatingSystemMXBean operatingSystemMXBean = ManagementFactory.getOperatingSystemMXBean();
			   for (Method method : operatingSystemMXBean.getClass().getDeclaredMethods()) {
		            method.setAccessible(true);
		            if (method.getName().startsWith("get") && Modifier.isPublic(method.getModifiers())) {
		                Object value;
		                try {
		                    value = method.invoke(operatingSystemMXBean);
		                } catch (Exception e) {
		                    value = e;
		                }
		                System.out.println(" before requesting "+method.getName() + " = " + value);
		            }
		            
		        }
			   
		  
			   System.out.println("REQUEST DETAILS::::::::::\nGet Method:"+request.getMethod()+"\nRemote Addr"+request.getRemoteAddr() +"\nRemote Host"+request.getRemoteHost() +"\nremote Port"+request.getRemotePort() +"\nRemote User"+request.getRemoteUser() +"\nClass"+request.getClass() +"\n");
			chain.doFilter(request, response);
		   operatingSystemMXBean = ManagementFactory.getOperatingSystemMXBean();
		   for (Method method : operatingSystemMXBean.getClass().getDeclaredMethods()) {
	            method.setAccessible(true);
	            if (method.getName().startsWith("get") && Modifier.isPublic(method.getModifiers())) {
	                Object value;
	                try {
	                    value = method.invoke(operatingSystemMXBean);
	                } catch (Exception e) {
	                    value = e;
	                }
	                System.out.println("after requesting :  "+method.getName() + " = " + value);
	            }
	        }

		   long stopTime = System.currentTimeMillis();
		  if((stopTime - startTime)!=0){ cpuperc = (TMB.getCurrentThreadCpuTime() - cput) / (stopTime - startTime) * 100.0;
		    }else{
		    	cpuperc = -2;
		    }
			System.out.println("cpu usage percentage@********** : "+cpuperc);
			 /* time = new Date().getTime() * 1000000;
			    cput = 0;
			    cpuperc = -1;
			   if( TMB.isThreadCpuTimeSupported() )
			   {
			       	if(new Date().getTime() * 1000000 - time > 1000000000) //Reset once per second
			       	{
			       		time = new Date().getTime() * 1000000;
			       		cput = TMB.getCurrentThreadCpuTime();
			       	}
			       				
			       	if(!TMB.isThreadCpuTimeEnabled())
			       	{
			       		TMB.setThreadCpuTimeEnabled(true);
			       	}
			       				
			       	if(new Date().getTime() * 1000000 - time != 0)
			       		cpuperc = (TMB.getCurrentThreadCpuTime() - cput) / (new Date().getTime() * 1000000.0 - time) * 100.0;  				
			   }
			   else
			   {
			       cpuperc = -2;
			   }*/
			//System.out.println("CPU Usage After executing request : "+cpuperc);
			System.out.println("maxMemory after : "+Runtime.getRuntime().maxMemory());
			System.out.println("totalMemory after : "+Runtime.getRuntime().totalMemory());
			System.out.println("free memory after : "+Runtime.getRuntime().freeMemory());
			System.out.println("++Start::  "+startTime +" Stop::  "+stopTime );
	        System.out.println("++Time to execute request: " + (stopTime - startTime) + 
	            " milliseconds");
	     
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}	
	
}
