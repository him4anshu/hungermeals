package com.hungermeals.common;

import java.io.File;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Iterator;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;

import com.hungermeals.persist.MailerDTO;
import com.hungermeals.persist.MailingDetails;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class MailSender {
	@Autowired
	private JavaMailSender mailSender;
	public boolean sendMail(MailerDTO mailerData){
		boolean mailsendStatus=false;
		/*Preparing required data to send*/
		final String to[]=mailerData.getTo();
		final String cc[]=mailerData.getCc();
		final String bcc[]=mailerData.getBcc();
		final String fromEmail=mailerData.getSenderEmail();
		final String fromName=mailerData.getSenderName(); 
		final String subject=mailerData.getSubject();
		final String replyToName=mailerData.getReplyToName();
		final String replyToEmail=mailerData.getReplyToEmail();
		final Multipart mailContent=getMultipartData(mailerData);
		
		/*Sending mail to user*/
		MimeMessagePreparator preparator = new MimeMessagePreparator() {
			public void prepare(MimeMessage mimeMessage) throws Exception {
				try {
					MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
					message.setTo(to);
				  	message.setBcc(bcc);
					message.setFrom(fromEmail,fromName);
					message.setSubject(subject);
					message.setReplyTo(replyToEmail, replyToName);
					mimeMessage.setContent(mailContent);				
					System.out.println("Email message is set");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		System.out.println("MimeMessagePreparator is ready to send" +preparator);
		try {
			mailSender.send(preparator);
			System.out.println("Mail send sucessfully");
			mailsendStatus=true;
		} catch (Exception e1) {
			System.out.println("Error in sending mail");
			e1.printStackTrace();
		}
		return mailsendStatus;
	}
	private  Multipart getMultipartData(MailerDTO mailerData){
		Map<String, Object> map = mailerData.getDynamicData();
		for (Map.Entry<String, Object> entry : map.entrySet()){
			if(entry.getValue()==null)
				mailerData.getDynamicData().put(entry.getKey(), "NA");
		}
		final Multipart mp = new MimeMultipart("alternative");
		final MimeBodyPart textPart = new MimeBodyPart();
		final MimeBodyPart rtfPart = new MimeBodyPart();
		final MimeBodyPart htmlPart = new MimeBodyPart();
		try {
			System.out.println("FTL FILE_LOCATION="+mailerData.getFileLocation()+" FILE NAME="+mailerData.getFileName());
			Configuration cfg = new Configuration();
			File fl=new File(mailerData.getFileLocation());
			try {
				cfg.setDirectoryForTemplateLoading(fl);
			} catch (Exception e) {
				e.printStackTrace();
			}
			Template template = cfg.getTemplate(mailerData.getFileName());
			Writer out= new StringWriter();
			template.process(mailerData.getDynamicData(), out);
            htmlPart.setContent(out.toString(), "text/html");
            String templateTextBody="";
			textPart.setContent(templateTextBody, "text/plain");
			String rtfBody=""; 
			rtfPart.setContent(rtfBody, "text/rtf");
            mp.addBodyPart(rtfPart);
			mp.addBodyPart(textPart);	
			mp.addBodyPart(htmlPart);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mp;
	}
}
