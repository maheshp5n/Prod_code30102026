package com.pratikshat.exicom.config;

import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class MiscellaneousUtils {
	public static String fillZero(String msg,int size) {
		StringBuilder builder=new StringBuilder();
		if(msg.length()>=size) {
			builder.append(msg);
			return builder.toString();
		}
		else {
			int restCount=size-msg.length();
			for(int i=0;i<restCount;i++) {
				builder.append("0");
			}
			builder.append(msg);
		}
		return builder.toString();
	}
	public static boolean isValid(com.pratikshat.exicom.model.request.AccountCreate account) {
		if(account.getAccountName()==null||account.getAccountName().isEmpty())
			return false;
		if(account.getSampAccountNumber()==null||account.getSampAccountNumber().isEmpty())
			return false;
		
		return true;
	}
	public static boolean isValid(com.pratikshat.exicom.entity.AddressInformation address) {
		if(address.getPincode()==null||address.getPincode().isEmpty()||address.getPincode().length()<6)
			return false;
		if(address.getState()==null)
			return false;
		if(address.getAddress()==null||address.getAddress().isEmpty())
			return false;
		return true;
	}
	public static boolean isValidEmailId(String emailId) {
		if(emailId==null||emailId.length()==0) {
			return false;
		}
		int indexOfAt=emailId.lastIndexOf("@");
		int indexOfDot=emailId.lastIndexOf(".");
		if((indexOfDot-indexOfAt)>1) {
			return true;
		}
		return false;
	}
	
	public static String slaCalculation(Date firstDate, Date secondDate) {
		String sla=null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		Date d1 = null;
		Date d2 = null;

		try {
			d1 = sdf.parse(sdf.format(firstDate));
			d2 = sdf.parse(sdf.format(secondDate));

			//in milliseconds
			long diff = d2.getTime() - d1.getTime();

			long diffSeconds = diff / 1000 % 60;
			long diffMinutes = diff / (60 * 1000) % 60;
			long diffHours = diff / (60 * 60 * 1000) % 24;
			long diffDays = diff / (24 * 60 * 60 * 1000);
			
			sla = diffDays+" days "+diffHours+" hours "+diffMinutes+" mins";
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return sla;
	}
	public static long daydiffrence(Date from,Date to){
		long timediffren=(to.getTime()-from.getTime());
		return ((timediffren / (1000*60*60*24)) % 365);
	}
	

	public static String sha256(String base) {
	    try{
	        MessageDigest digest = MessageDigest.getInstance("SHA-256");
	        byte[] hash = digest.digest(base.getBytes("UTF-8"));
	        StringBuffer hexString = new StringBuffer();

	        for (int i = 0; i < hash.length; i++) {
	            String hex = Integer.toHexString(0xff & hash[i]);
	            if(hex.length() == 1) hexString.append('0');
	            hexString.append(hex);
	        }

	        return hexString.toString();
	    } catch(Exception ex){
	       throw new RuntimeException(ex);
	    }
	}
//	public static char[] randomToken(int len) 
//    { 
//        String Capital_chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"; 
//        String Small_chars = "abcdefghijklmnopqrstuvwxyz"; 
//        String numbers = "0123456789"; 
//        String symbols = "!@#$%^&*_=+-/.?<>)"; 
//  
//  
//        String values = Capital_chars + Small_chars + 
//                        numbers + symbols; 
//  
//        // Using random method 
//        Random rndm_method = new Random(); 
//  
//        char[] password = new char[len]; 
//  
//        for (int i = 0; i < len; i++) 
//        { 
//            // Use of charAt() method : to get character value 
//            // Use of nextInt() as it is scanning the value as int 
//            password[i] = 
//              values.charAt(rndm_method.nextInt(values.length())); 
//  
//        } 
//        return password; 
//    }
	public static char[] randomToken(int len) {
	    if (len < 4) throw new IllegalArgumentException("Length must be at least 4");

	    String capitalChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	    String smallChars = "abcdefghijklmnopqrstuvwxyz";
	    String numbers = "0123456789";
	    String symbols = "!@#$%^&*_=+-/.?<>)";

	    String allChars = capitalChars + smallChars + numbers + symbols;

	    Random random = new Random();
	    char[] password = new char[len];

	    // Ensure at least one of each required character type
	    password[0] = capitalChars.charAt(random.nextInt(capitalChars.length()));
	    password[1] = smallChars.charAt(random.nextInt(smallChars.length()));
	    password[2] = numbers.charAt(random.nextInt(numbers.length()));
	    password[3] = symbols.charAt(random.nextInt(symbols.length()));

	    // Fill remaining characters randomly (start from index 4)
	    for (int i = 4; i < len; i++) {
	        password[i] = allChars.charAt(random.nextInt(allChars.length()));
	    }

	    // Shuffle the array to mix guaranteed characters randomly
	    for (int i = 0; i < password.length; i++) {
	        int randomIndex = random.nextInt(password.length);
	        char temp = password[i];
	        password[i] = password[randomIndex];
	        password[randomIndex] = temp;
	    }

	    // Print and return
	    System.out.println("Password ---> " + new String(password));
	    return password;
	}

	public static String genOtp(int len) {
		 
	        String numbers = "QWERTYUIOPLKJHGFDSAZXCVBNM0123456789";
	        String values = numbers; 
	  
	        // Using random method 
	        Random rndm_method = new Random(); 
	  
	        char[] password = new char[len]; 
	  
	        for (int i = 0; i < len; i++) 
	        { 
	            password[i] = 
	              values.charAt(rndm_method.nextInt(values.length())); 
	  
	        } 
	        return String.valueOf(password); 
	}

	public static String objToString(Object obj){
		return obj==null?"":obj.toString();

	}

	public static String likeStatement(Object obj){
		return "%"+obj+"%";
	}
}
