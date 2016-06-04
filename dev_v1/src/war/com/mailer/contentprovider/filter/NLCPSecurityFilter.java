package com.mailer.contentprovider.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class NLCPSecurityFilter implements Filter {
	
	private FilterConfig config;
	private boolean isSecure =true;
	private static final Log log = LogFactory.getLog(NLCPSecurityFilter.class);
	private List<String> securedURLs = new ArrayList<String>();
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res,
						 FilterChain chain) 
	throws IOException, ServletException {
		//System.out.println("Mailer SecurityFilter::doFilter Called");
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		String uri = request.getRequestURI();
		//System.out.println("Orignal URL is: " + request.getRequestURL());
		//System.out.println("Orignal URI is: " + request.getRequestURI());
		for(String s: securedURLs){
			System.out.print("securedURLs--------------> "+s);
		}
		if (securedURLs.contains(uri)) {
		isSecure=true;
		}else{
			isSecure=false;
			//isSecure=true;
			
		}
		
		//System.out.println("is secured --------------->"+isSecure);
		
	
		if (!isSecure) {
			HttpSession session = request.getSession();
			
			//System.out.println("login user object reffernce : "+session.getAttribute("LoggedInUser"));
			if (session.getAttribute("LoggedInUser") == null) {
				//System.out.println("User not logged in to access this page");
				session.invalidate();
				
				// removed authentication for interfaces
				response.sendRedirect("../cp/SessionExp.action");
				
								
				
			} else {
				//System.out.println("User logged in. Proceed to the page");
				chain.doFilter(request, response);
			}
		} else {
			//System.out.println("URL is  secured. Allow user to access");
			chain.doFilter(request, response);
			//response.sendRedirect("../nlt/SessionExp.action");
		}
		
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		this.config = config;
		//String paramUnsecuredURL = config.getInitParameter("UNSECURED_URL");
		String paramUnsecuredURL = config.getInitParameter("SECURED_URL");
		String[] s = paramUnsecuredURL.split(";");
		for (int i = 0;i<s.length;i++) {
			securedURLs.add(s[i]);
			//System.out.println("secured URI "+s[i]);
		}

	}
	
	

}
