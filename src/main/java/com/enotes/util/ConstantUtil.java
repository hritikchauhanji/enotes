package com.enotes.util;

public class ConstantUtil {

	public final static String Email_Regex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
	
	public final static String Mobile_No_Regex = "^[7-9][0-9]{9}$";
	
	public final static String Password_Regex = "^.*(?=.{8,})(?=..*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).*$";
	
	public final static String Role_Admin = "hasRole('ADMIN')";
	
	public final static String Role_Admin_User = "hasAnyRole('USER','ADMIN')";
	
	public final static String Role_User = "hasRole('USER')";
	
	public final static String Default_Page_No = "0";
	
	public final static String Default_Page_Size = "3";
}
