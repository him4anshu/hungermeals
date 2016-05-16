package com.hungermeals.common;

public class UserDataValidator {

	public static boolean validateName(String name){
		String regex = "[a-zA-Z ]+";
		return name.matches(regex);
	}
	public static boolean validatePhone(String phone){
		if(phone.length()==10){
			String regex = "[0-9]+";
			return phone.matches(regex);
		}else{
			return false;
		}
	}
	public static boolean validateEmail(String email){
		String regex="^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
		+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		return email.matches(regex);

	}
}
