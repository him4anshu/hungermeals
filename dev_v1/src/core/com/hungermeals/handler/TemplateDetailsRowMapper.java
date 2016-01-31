package com.hungermeals.handler;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.hungermeals.persist.TemplateBean;


public class TemplateDetailsRowMapper implements RowMapper<TemplateBean>{

	@Override
	public TemplateBean mapRow(ResultSet rs, int arg1) throws SQLException {
        System.out.println("executing TemplateDetailsRowMapper");
		TemplateBean templateDetails=new TemplateBean();
		try {
			templateDetails.setReplyToEmail(rs.getString("REPLY_TO_EMAIL"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			templateDetails.setReplyToName(rs.getString("REPLY_TO_NAME"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			templateDetails.setSenderEmail(rs.getString("SENDER_EMAIL"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			templateDetails.setSenderName(rs.getString("SENDER_NAME"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			templateDetails.setSubject(rs.getString("SUBJECT"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			templateDetails.setTemplateFileName(rs.getString("TEMPLATE_NAME"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			templateDetails.setTemplateFileLocation(rs.getString("TEMPLATE_LOCATION"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			templateDetails.setCcEmailDetails(rs.getString("CC_EMAIL_DETAILS"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			templateDetails.setBccEmialDetails(rs.getString("BCC_EMAIL_DETAILS"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return templateDetails;
	
	}

}
