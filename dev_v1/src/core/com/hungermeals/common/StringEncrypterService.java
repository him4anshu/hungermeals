package com.hungermeals.common;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.hungermeals.common.exception.AuthenticationErrorCode;
import com.hungermeals.common.exception.EncryptionException;

import sun.misc.BASE64Encoder;


public class StringEncrypterService {

	public StringEncrypterService (){
	}

	public static final String encryptString(String unencryptedString)
	throws EncryptionException {

		MessageDigest digest = null;
		try {
			digest = MessageDigest.getInstance("SHA");
		} catch (NoSuchAlgorithmException ex) {
			throw new EncryptionException(AuthenticationErrorCode.ENCRYPTION_EXCEPTION);
		}

		try {
			digest.update(unencryptedString.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException ex) {
			throw new EncryptionException(AuthenticationErrorCode.ENCRYPTION_EXCEPTION);
		}

		byte[] b = digest.digest();
		String encPass = (new BASE64Encoder()).encode(b);

		return removeSpecialCharacter(encPass);
	}

	public static final boolean matchEncryptedString(String str1,String str2) {
		return (MessageDigest.isEqual(str1.getBytes(),str2.getBytes()));
	}
	
	public static String removeSpecialCharacter(String str){
		
		return str.replaceAll("[^A-Za-z0-9 ]", "");
	}

}