package com.hungermeals.handler;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.hungermeals.persist.UserBean;

public class UserDetailsRowMapper implements RowMapper<UserBean>{

	@Override
	public UserBean mapRow(ResultSet rs, int arg1) throws SQLException {

		UserBean userBean=new UserBean();
		
		try {
			userBean.setUserId(rs.getInt("USER_ID"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("USER_ID column not required");
		}
		try {
			userBean.setUserTitle(rs.getString("USER_TITLE"));
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			System.out.println("USER_TITLE column not required");
		}
		try {
			userBean.setUserEmail(rs.getString("USER_EMAIL"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("USER_EMAIL column not required");
		}
		try {
			userBean.setUserFirstName(rs.getString("USER_FIRST_NAME"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("USER_FIRST_NAME column not required");
		}
		try {
			userBean.setUserLastName(rs.getString("USER_LAST_NAME"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("USER_LAST_NAME column not required");
		}
		try {
			userBean.setUserEEmail(rs.getString("E_EMAIL"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("E_EMAIL column not required");
		}
		
		try {
			userBean.setOrganizationName(rs.getString("ORGANIZATION_NAME"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("ORGANIZATION_NAME column not required");
		}
		/*try {
			userBean.setOrganizationUrl("COMPANY_URL");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		try {
			userBean.setOrganizationUrl(rs.getString("URL"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("URL column not required");
		}
		try {
			userBean.setOrganizationId(rs.getString("ORGANIZATION_ID"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("ORGANIZATION_ID column not required");
		}
		try {
			userBean.setOrganizationCountry(rs.getString("COUNTRY_NAME"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("COUNTRY_NAME column not required");
		}
		try {
			userBean.setOrganizationIndustry(rs.getString("INDUSTRY_NAME"));
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("INDUSTRY_NAME column not required");
		}
		try {
			userBean.setUserNewEmail(rs.getString("USER_NEW_EMAIL"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("USER_NEW_EMAIL column not required");
		}
		try {
			userBean.setUserOldEmail(rs.getString("USER_OLD_EMAIL"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("USER_OLD_EMAIL column not required");
		}
		try {
			userBean.setProfilePercentage(rs.getString("PROFILE_PERCENTAGE"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("PROFILE_PERCENTAGE column not required");
		}
		try {
			userBean.setAnswerId(rs.getInt("ANSWER_ID")+"");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("ANSWER_ID column not required");
		}
		
		try {
			userBean.setAdCreationDate(rs.getString("CREATION_DATE"));
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			System.out.println("CREATION_DATE column not required");
		}
		try {
			userBean.setAdValidityDate(rs.getString("UPDATION_DATE"));
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			System.out.println("UPDATION_DATE column not required");
		}
		try {
			userBean.setAdPixel(rs.getString("PIXELS_SZ"));
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			System.out.println("PIXELS_SZ column not required");
		}
		try {
			userBean.setAdTypeName(rs.getString("AD_TYPE_NAME"));
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			System.out.println("AD_TYPE_NAME column not required");
		}
		try {
			userBean.setEventName(rs.getString("EVENT_NAME"));
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			System.out.println("EVENT_NAME column not required");
		}
		try {
			userBean.setEventCountry(rs.getString("EVENT_COUNTRY"));
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			System.out.println("EVENT_COUNTRY column not required");
		}
		try {
			userBean.setEventIndustry(rs.getString("EVENT_INDUSTRY"));
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			System.out.println("EVENT_INDUSTRY column not required");
		}
		try {
			userBean.setEventVenue(rs.getString("EVENT_VENUE"));
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			System.out.println("EVENT_VENUE column not required");
		}
		try {
			userBean.setEventUrl(rs.getString("EVENT_URL"));
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			System.out.println("EVENT_URL column not required");
		}
		try {
			userBean.setEventStartDate(rs.getString("EVENT_START_DATE"));
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			System.out.println("EVENT_START_DATE column not required");
		}
		try {
			userBean.setEventEndDate(rs.getString("EVENT_END_DATE"));
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			System.out.println("EVENT_END_DATE column not required");
		}
		try {
			userBean.setForumCreatedBy(rs.getString("FORUM_CREATED_BY"));
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			System.out.println("FORUM_CREATED_BY column not required");
		}
		try {
			userBean.setForumQuestion(rs.getString("FORUM_QUESTION"));
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			System.out.println("FORUM_QUESTION column not required");
		}
		try {
			userBean.setForumcommentedUserName(rs.getString("FORUM_COMMENETED_USER_NAME"));
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			System.out.println("FORUM_COMMENETED_USER_NAME column not required");
		}
		try {
			userBean.setForumTopicName(rs.getString("FORUM_TOPIC"));
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			System.out.println("FORUM_TOPIC column not required");
		}
		try {
			userBean.setS1(rs.getString("S1"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("S1 column value not required");
		}
		try {
			userBean.setS2(rs.getString("S2"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("S2 column value not required");
		}
		try {
			userBean.setS3(rs.getString("S3"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("S3 column value not required");
		}
		try {
			userBean.setS4(rs.getString("S4"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("S4 column value not required");
		}
		try {
			userBean.setS5(rs.getString("S5"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("S5 column value not required");
		}
		try {
			userBean.setS6(rs.getString("S6"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("S6 column value not required");
		}
		try {
			userBean.setS7(rs.getString("S7"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("S7 column value not required");
		}
		try {
			userBean.setS8(rs.getString("S8"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("S8 column value not required");
		}
		try {
			userBean.setS9(rs.getString("S9"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("S9 column value not required");
		}
		try {
			userBean.setS10(rs.getString("S10"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("S10 column value not required");
		}
		try {
			userBean.setS11(rs.getString("S11"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("S11 column value not required");
		}
		try {
			userBean.setS12(rs.getString("S12"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("S12 column value not required");
		}
		try {
			userBean.setS13(rs.getString("S13"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("S13 column value not required");
		}
		try {
			userBean.setS14(rs.getString("S14"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("S14 column value not required");
		}
		try {
			userBean.setS15(rs.getString("S15"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("S15 column value not required");
		}
		
		try {
			userBean.setDynamicSubjectV1(rs.getString("DSV1"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("DSV1 column value not required");
		}
		try {
			userBean.setDynamicSubjectV2(rs.getString("DSV2"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("DSV2 column value not required");
		}
		try {
			userBean.setDynamicSubjectV3(rs.getString("DSV3"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("DSV3 column value not required");
		}
		try {
			userBean.setEventDescription(rs.getString("EVENT_DESCRIPTION"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("EVENT_DESCRIPTION column value not required");
		}
		try {
			userBean.setOrganizationDescription(rs.getString("ORGANIZATION_DESCRIPTION"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("ORGANIZATION_DESCRIPTION column value not required");
		}
		try {
			userBean.setD1(rs.getDouble("D1"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("D1 column value not required");
		}
		try {
			userBean.setD2(rs.getDouble("D2"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("D2 column value not required");
		}
		try {
			userBean.setD3(rs.getDouble("D3"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("D3 column value not required");
		}
		try {
			userBean.setI1(rs.getInt("I1"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("I1 column value not required");
		}
		try {
			userBean.setI2(rs.getInt("I2"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("I2 column value not required");
		}
		try {
			userBean.setI3(rs.getInt("I3"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("I3 column value not required");
		}
		try {
			userBean.setDynamicSenderV1(rs.getString("DSNV1"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("DSNV1 column value not required");
		}
		try {
			userBean.setDynamicSenderV1(rs.getString("DSNV2"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("DSNV2 column value not required");
		}
		try {
			userBean.setDynamicSenderV1(rs.getString("DSNV3"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("DSNV3 column value not required");
		}
		return userBean;
	
	}

}
