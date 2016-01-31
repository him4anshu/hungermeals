package com.hungermeals.persist;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="cpInfo")
public class MailerBean {
	
	private String emailid;
	private String recipientsName;
	private int cat_id;
	private String category_name;
	private int contact_id;
	private String templateName;
	private String templateLocation;
	private int templateId;
	private String columnName;
	private List<MailerBean> categoryList;
	private String groupcount;
	private int insertGroupC;
	private int group_id;
	private String groupName;
	private String groupDescription;
	private List<MailerBean> MailingGroupsList;
	private String deliveryStatus;
	private String redirectLink;
	private int campaignId;
	private String campaignName;
	private int CampaignMailingId;
	private String CampaignMailingName;
	private String templateBody;
	private int link_id;
	private String linkParameter;
	private String linkParameterValue;
	private List<MailerBean> templateDetailsList;
	private List<MailerBean> templateLinksList;
	private int templateLinkId;
	private String conditions;
	private int f_id;
	private String trackingLink;
	private int trackingCount;
	private String creation_status;
	private String updation_status;
	private int gr_id;
	private String TEXTBody;
	private List<MailerBean> templateLinkList;
	private int noOfLeads;
	private String filterConditions;
	private String CampaignCreationDate;
	private String CampaignDeliveryDate;
	private String mailingStatus;
	private String CampaignScheduleDate;
	/**List all email leads required details parameters**/
	
	
	private String subscriberCreationDate;
	private String subscriberUpdateDATE;
	private String ASSOCIATION_NAME;
	private String company_industry;
	private String company_name;
	private String encrypted_key;
	private String unsubscribe_status;
	private String company_or_association;
	
	private String companyUrl;
	private String bouncedStatus;
	private String bouncedMessage;
	private List<String> folderName;
    private String subscribe_status;
    private String eventUrl;
    private String urlStatus;
    private String parameterType;
    private String templateDescription;
    private int flag;
    private List errorList;
	
	public String getCompanyUrl() {
		return companyUrl;
	}
	public void setCompanyUrl(String companyUrl) {
		this.companyUrl = companyUrl;
	}
	public String getCampaignCreationDate() {
		return CampaignCreationDate;
	}
	public void setCampaignCreationDate(String campaignCreationDate) {
		CampaignCreationDate = campaignCreationDate;
	}
	public String getCampaignDeliveryDate() {
		return CampaignDeliveryDate;
	}
	public void setCampaignDeliveryDate(String campaignDeliveryDate) {
		CampaignDeliveryDate = campaignDeliveryDate;
	}
	public String getFilterConditions() {
		return filterConditions;
	}
	public void setFilterConditions(String filterConditions) {
		this.filterConditions = filterConditions;
	}
	public int getNoOfLeads() {
		return noOfLeads;
	}
	public void setNoOfLeads(int noOfLeads) {
		this.noOfLeads = noOfLeads;
	}
	public List<MailerBean> getTemplateLinkList() {
		return templateLinkList;
	}
	public void setTemplateLinkList(List<MailerBean> templateLinkList) {
		this.templateLinkList = templateLinkList;
	}
	public String getTEXTBody() {
		return TEXTBody;
	}
	public void setTEXTBody(String tEXTBody) {
		TEXTBody = tEXTBody;
	}
	public String getSubscriberCreationDate() {
		return subscriberCreationDate;
	}
	public void setSubscriberCreationDate(String subscriberCreationDate) {
		this.subscriberCreationDate = subscriberCreationDate;
	}
	public String getSubscriberUpdateDATE() {
		return subscriberUpdateDATE;
	}
	public void setSubscriberUpdateDATE(String subscriberUpdateDATE) {
		this.subscriberUpdateDATE = subscriberUpdateDATE;
	}
	public String getASSOCIATION_NAME() {
		return ASSOCIATION_NAME;
	}
	public void setASSOCIATION_NAME(String aSSOCIATION_NAME) {
		ASSOCIATION_NAME = aSSOCIATION_NAME;
	}
	public String getCompany_industry() {
		return company_industry;
	}
	public void setCompany_industry(String company_industry) {
		this.company_industry = company_industry;
	}
	public String getCompany_name() {
		return company_name;
	}
	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}
	public String getEncrypted_key() {
		return encrypted_key;
	}
	public void setEncrypted_key(String encrypted_key) {
		this.encrypted_key = encrypted_key;
	}
	public String getUnsubscribe_status() {
		return unsubscribe_status;
	}
	public void setUnsubscribe_status(String unsubscribe_status) {
		this.unsubscribe_status = unsubscribe_status;
	}
	public String getCompany_or_association() {
		return company_or_association;
	}
	public void setCompany_or_association(String company_or_association) {
		this.company_or_association = company_or_association;
	}
	public int getGr_id() {
		return gr_id;
	}
	public void setGr_id(int gr_id) {
		this.gr_id = gr_id;
	}
	public String getUpdation_status() {
		return updation_status;
	}
	public void setUpdation_status(String updation_status) {
		this.updation_status = updation_status;
	}
	public String getCreation_status() {
		return creation_status;
	}
	public void setCreation_status(String creation_status) {
		this.creation_status = creation_status;
	}
	public int getTrackingCount() {
		return trackingCount;
	}
	public void setTrackingCount(int trackingCount) {
		this.trackingCount = trackingCount;
	}
	public String getTrackingLink() {
		return trackingLink;
	}
	public void setTrackingLink(String trackingLink) {
		this.trackingLink = trackingLink;
	}
	public String getConditions() {
		return conditions;
	}
	public void setConditions(String conditions) {
		this.conditions = conditions;
	}
	public int getF_id() {
		return f_id;
	}
	public void setF_id(int f_id) {
		this.f_id = f_id;
	}
	public int getTemplateLinkId() {
		return templateLinkId;
	}
	public void setTemplateLinkId(int templateLinkId) {
		this.templateLinkId = templateLinkId;
	}
	public List<MailerBean> getTemplateLinksList() {
		return templateLinksList;
	}
	public void setTemplateLinksList(List<MailerBean> templateLinksList) {
		this.templateLinksList = templateLinksList;
	}
	public List<MailerBean> getTemplateDetailsList() {
		return templateDetailsList;
	}
	public void setTemplateDetailsList(List<MailerBean> templateDetailsList) {
		this.templateDetailsList = templateDetailsList;
	}
	public int getLink_id() {
		return link_id;
	}
	public void setLink_id(int link_id) {
		this.link_id = link_id;
	}
	public String getLinkParameter() {
		return linkParameter;
	}
	public void setLinkParameter(String linkParameter) {
		this.linkParameter = linkParameter;
	}
	public String getLinkParameterValue() {
		return linkParameterValue;
	}
	public void setLinkParameterValue(String linkParameterValue) {
		this.linkParameterValue = linkParameterValue;
	}
	public String getTemplateBody() {
		return templateBody;
	}
	public void setTemplateBody(String templateBody) {
		this.templateBody = templateBody;
	}
	public int getCampaignMailingId() {
		return CampaignMailingId;
	}
	public void setCampaignMailingId(int campaignMailingId) {
		CampaignMailingId = campaignMailingId;
	}
	public String getCampaignMailingName() {
		return CampaignMailingName;
	}
	public void setCampaignMailingName(String campaignMailingName) {
		CampaignMailingName = campaignMailingName;
	}
	public int getCampaignId() {
		return campaignId;
	}
	public void setCampaignId(int campaignId) {
		this.campaignId = campaignId;
	}
	public String getCampaignName() {
		return campaignName;
	}
	public void setCampaignName(String campaignName) {
		this.campaignName = campaignName;
	}
	public String getRedirectLink() {
		return redirectLink;
	}
	public void setRedirectLink(String redirectLink) {
		this.redirectLink = redirectLink;
	}
	public String getDeliveryStatus() {
		return deliveryStatus;
	}
	public void setDeliveryStatus(String deliveryStatus) {
		this.deliveryStatus = deliveryStatus;
	}
	public List<MailerBean> getMailingGroupsList() {
		return MailingGroupsList;
	}
	public void setMailingGroupsList(List<MailerBean> mailingGroupsList) {
		MailingGroupsList = mailingGroupsList;
	}
	public int getGroup_id() {
		return group_id;
	}
	public void setGroup_id(int group_id) {
		this.group_id = group_id;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getGroupDescription() {
		return groupDescription;
	}
	public void setGroupDescription(String groupDescription) {
		this.groupDescription = groupDescription;
	}
	public String getGroupcount() {
		return groupcount;
	}
	public void setGroupcount(String groupcount) {
		this.groupcount = groupcount;
	}
	public int getInsertGroupC() {
		return insertGroupC;
	}
	public void setInsertGroupC(int insertGroupC) {
		this.insertGroupC = insertGroupC;
	}
	public List<MailerBean> getCategoryList() {
		return categoryList;
	}
	public void setCategoryList(List<MailerBean> categoryList) {
		this.categoryList = categoryList;
	}
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public int getTemplateId() {
		return templateId;
	}
	public void setTemplateId(int templateId) {
		this.templateId = templateId;
	}
	public String getTemplateName() {
		return templateName;
	}
	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}
	public String getTemplateLocation() {
		return templateLocation;
	}
	public void setTemplateLocation(String templateLocation) {
		this.templateLocation = templateLocation;
	}
	public int getContact_id() {
		return contact_id;
	}
	public void setContact_id(int contact_id) {
		this.contact_id = contact_id;
	}
	public int getCat_id() {
		return cat_id;
	}
	public void setCat_id(int cat_id) {
		this.cat_id = cat_id;
	}
	public String getCategory_name() {
		return category_name;
	}
	public void setCategory_name(String category_name) {
		this.category_name = category_name;
	}
	public String getEmailid() {
		return emailid;
	}
	public void setEmailid(String emailid) {
		this.emailid = emailid;
	}
	public String getRecipientsName() {
		return recipientsName;
	}
	public void setRecipientsName(String recipientsName) {
		this.recipientsName = recipientsName;
	}
	private String countryNmae;


	public String getCountryNmae() {
		return countryNmae;
	}
	public void setCountryNmae(String countryNmae) {
		this.countryNmae = countryNmae;
	}
	public String getBouncedStatus() {
		return bouncedStatus;
	}
	public void setBouncedStatus(String bouncedStatus) {
		this.bouncedStatus = bouncedStatus;
	}
	public String getBouncedMessage() {
		return bouncedMessage;
	}
	public void setBouncedMessage(String bouncedMessage) {
		this.bouncedMessage = bouncedMessage;
	}
	public List<String> getFolderName() {
		return folderName;
	}
	public void setFolderName(List<String> folderName) {
		this.folderName = folderName;
	}
	public String getMailingStatus() {
		return mailingStatus;
	}
	public void setMailingStatus(String mailingStatus) {
		this.mailingStatus = mailingStatus;
	}
	public String getCampaignScheduleDate() {
		return CampaignScheduleDate;
	}
	public void setCampaignScheduleDate(String campaignScheduleDate) {
		CampaignScheduleDate = campaignScheduleDate;
	}
	public String getSubscribe_status() {
		return subscribe_status;
	}
	public void setSubscribe_status(String subscribe_status) {
		this.subscribe_status = subscribe_status;
	}
	public String getEventUrl() {
		return eventUrl;
	}
	public void setEventUrl(String eventUrl) {
		this.eventUrl = eventUrl;
	}
	public String getUrlStatus() {
		return urlStatus;
	}
	public void setUrlStatus(String urlStatus) {
		this.urlStatus = urlStatus;
	}
	public String getParameterType() {
		return parameterType;
	}
	public void setParameterType(String parameterType) {
		this.parameterType = parameterType;
	}
	public String getTemplateDescription() {
		return templateDescription;
	}
	public void setTemplateDescription(String templateDescription) {
		this.templateDescription = templateDescription;
	}

	public int getFlag() {
		return flag;
	}
	public void setFlag(int flag) {
		this.flag = flag;
	}
	public List getErrorList() {
		return errorList;
	}
	public void setErrorList(List errorList) {
		this.errorList = errorList;
	}
	
	

}
