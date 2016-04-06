package com.hungermeals.common;

import java.io.File;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
import com.hungermeals.persist.MailerBean;
import com.hungermeals.persist.TemplateBean;
import com.hungermeals.persist.UserBean;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class TemplateProcessing {
	String email_dns="";
	String login_dns="";
	public  Multipart processTemplate(List<MailerBean> templateDetails,TemplateBean templateBean,UserBean userBean){	

		
		/*Processing the link by removing unwanted characters*/
		Map<String, Object> params = new HashMap<String, Object>();			
		String encrypted_key=userBean.getUserEEmail();
		System.out.println("uSER_E_E_EMAIL="+encrypted_key);
		String output="";
		/*Temprory variable*/
		// String GR_ID=userID;CM_ID="999";
		System.out.println("userBean.getGrId()======"+userBean.getGrId());
		 String GR_ID=userBean.getGrId();String CM_ID="999";
		 String recipientName="XYZ";
		
		//final Multipart mp = new MimeMultipart();//This is applicable for html part only
	    final Multipart mp = new MimeMultipart("alternative");
		final MimeBodyPart textPart = new MimeBodyPart();
		final MimeBodyPart rtfPart = new MimeBodyPart();
		final MimeBodyPart htmlPart = new MimeBodyPart();
		
		/*Getting text part and link available in template*/
		List<MailerBean> templateLinkDetails=new ArrayList<MailerBean>();
		/*Text body means the text part which will be available to the user when html does not support*/
		String templateTextBody="";
		for(MailerBean bi:templateDetails){
			if(bi.getTEXTBody()!=null){
				templateTextBody=bi.getTEXTBody().trim();
			}
			if(bi.getTemplateLinkList()!=null){
				templateLinkDetails=bi.getTemplateLinkList();
			}
		}
		
		
		try {
					String linkPara=null;
					String trackValue=null;
					String parameterType=null;
					String tempalateId="";
					for(MailerBean tl:templateLinkDetails){
						linkPara=tl.getLinkParameter();
						trackValue=tl.getTrackingLink();
						parameterType=tl.getParameterType();
						
						/*Replacing $,{,} from link parameter so that it can fit for template processing
						We have added these three $,{,} to avoid the confusion in front end */
						  
						linkPara=tl.getLinkParameter().replace("$", "");
						linkPara=linkPara.replace("{", "");
						linkPara=linkPara.replace("}", "");
						
						if(parameterType.equalsIgnoreCase("LINK")){
							if(linkPara.equalsIgnoreCase("unsubscribeURL") || linkPara.equalsIgnoreCase("subscribeURL")){
								System.out.println("###############33"+trackValue);
								System.out.println("#####################email dns"+email_dns);
								trackValue=trackValue.replace("encrpKey=", "encrpKey="+encrypted_key.trim());
								System.out.println("###############33"+trackValue);
								System.out.println("#####################email dns"+email_dns);
								trackValue=email_dns+trackValue.trim();
								System.out.println("linkpara:"+linkPara+"  linkvalues"+trackValue);
							}else if(linkPara.equalsIgnoreCase("openMailLink")){
								System.out.println("GR_IDddddd"+GR_ID);
								trackValue=trackValue.replace("GR_ID=", "GR_ID="+GR_ID.trim());
								trackValue=login_dns+trackValue.trim();
							}else{
							trackValue=tl.getTrackingLink().replace("&CM_ID=", "&CM_ID="+CM_ID.trim());
							trackValue=trackValue.replace("LINK=", "LINK="+linkPara.trim());
							trackValue=trackValue.replace("&templateId=", "&templateId="+tempalateId.trim());
							trackValue=trackValue.replace("&GR_ID=", "&GR_ID="+GR_ID.trim());
							trackValue=email_dns+trackValue.trim();
							}						
							params.put(linkPara,trackValue.trim());	
							System.out.println("LINK PARAMETER KEY AND VALUE :"+linkPara+"="+trackValue.trim());
						}else{
							if(userBean.getClass().getMethod(trackValue).invoke(userBean)!=null)
								params.put(linkPara,userBean.getClass().getMethod(trackValue).invoke(userBean));
							else
								params.put(linkPara,trackValue);
							System.out.println("LINK PARAMETER KEY AND VALUE :"+linkPara+"="+userBean.getClass().getMethod(trackValue).invoke(userBean));
						}
						
					}
					//For evaluating properties file data

				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				try {
					System.out.println("Reading html file: FILE_LOCATION="+templateBean.getTemplateFileLocation()+" FILE NAME="+templateBean.getTemplateFileName());
					Configuration cfg = new Configuration();
					File fl=new File(templateBean.getTemplateFileLocation());
					try {
						cfg.setDirectoryForTemplateLoading(fl);
					} catch (Exception e) {
						e.printStackTrace();
					}
					Template template = cfg.getTemplate(templateBean.getTemplateFileName());
					/*params.put("recipientName", recipientName);
					params.put("O_ORG_CONTACT_NAME", O_OrgContactName);
					*/
					Writer out= new StringWriter();
					template.process(params, out);
		            htmlPart.setContent(out.toString(), "text/html");
		         		    		
				} catch (Exception e) {
					e.printStackTrace();
				}
				 try {
					textPart.setContent(templateTextBody, "text/plain");
				} catch (MessagingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} 
				String rtfBody=templateTextBody; 
				try {
					rtfPart.setContent(rtfBody, "text/rtf");
				} catch (MessagingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} 
				
					try {
						
						mp.addBodyPart(rtfPart);
						mp.addBodyPart(textPart);
						mp.addBodyPart(htmlPart);
					} catch (MessagingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return mp;
			}
	

}
