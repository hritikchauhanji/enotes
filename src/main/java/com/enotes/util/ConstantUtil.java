package com.enotes.util;

public class ConstantUtil {

	public final static String Email_Regex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
	
	public final static String Mobile_No_Regex = "^[7-9][0-9]{9}$";
	
	public final static String Password_Regex = "^.*(?=.{8,})(?=..*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).*$";
}
