package com.all.management.user.web;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class Utils {
	public static final String SERVER_REST_URI = "http://localhost:8080/user";
	public static final boolean ACTIVE = true;
    public static final boolean INACTIVE = false;
    
    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String ROLE_USER = "ROLE_USER";
    public static final String ROLE_XILINADMIN = "ROLE_XILINADMIN";
    public static final String ROLE_XILINFAMILY = "ROLE_XILINFAMILY";
    public static final String ROLE_XILINTEACHER = "ROLE_XILINTEACHER";
    
	public static String bytesToHex(byte[] hash) {
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < hash.length; i++) {
        String hex = Integer.toHexString(0xff & hash[i]);
        if(hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }
	
	public static String retrieveLoginUsername() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    if (auth != null){    
	    	return auth.getName();
	    }
	    else {
	    	return("");
	    }
	}
}
