package com.usermgmt.constants;

public class PortalConstants {

	public static final String VALID_EMAIL_ADDRESS_REGEX = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";
	public static final String VALID_PHONE_NUMBER_REGEX = "\\+?\\d+$";
	public static final String VALID_PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
	public static final String EMPTY_STRING = "";
	public static final String BASIC_AUTH_HEADER_PREFIX = "Basic ";
}
