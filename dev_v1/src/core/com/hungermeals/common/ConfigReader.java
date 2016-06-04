package com.hungermeals.common;

import java.util.ResourceBundle;



public class ConfigReader {

	//private Properties properties;
	private ResourceBundle bundle;

	public ConfigReader(){
		/*String gpnConfigHome = System.getProperty("GPN_CONFIG_HOME");
		properties = new Properties();
		properties.load(new FileInputStream(gpnConfigHome + File.separator +  "gpn" + File.separator + "gpn.properties"));*/
		try {
			bundle = ResourceBundle.getBundle("com.fidloo.fidloo");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getValue(String key) {
		
		//System.out.println("ingetValues");
		
		return bundle.getString(key);
	}

}
