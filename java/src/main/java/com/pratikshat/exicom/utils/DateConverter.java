package com.pratikshat.exicom.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DateConverter {

	private static final SimpleDateFormat tzDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

	private static final SimpleDateFormat yymmddhhmmss = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	private static final SimpleDateFormat yymmdd = new SimpleDateFormat("yyyy-MM-dd");

	private static final SimpleDateFormat ddmmyyyy = new SimpleDateFormat("dd-MM-yyyy");
	
	private static final SimpleDateFormat yyyymmdd = new SimpleDateFormat("yyyy/MM/dd");
	
	private static final SimpleDateFormat mddyy = new SimpleDateFormat("M/dd/yy");

	public static Date convertToDate(String dateInString) {
		Date dt = null;
		try {
			dt = tzDateFormat.parse(dateInString);
			return dt;
		} catch (Exception ex) {
			try {
				dt = yymmddhhmmss.parse(dateInString);
			} catch (ParseException e) {
				try {
					dt = yymmdd.parse(dateInString);
				} catch (ParseException e1) {
					try {
						dt = ddmmyyyy.parse(dateInString);
					} catch (ParseException e2) {
//						try {
//							dt=mddyy.parse(dateInString);
//						}catch(ParseException e3) {
//						e2.printStackTrace();
//						}
						try {
						    dt=yyyymmdd.parse(dateInString);
						}catch(ParseException e3) {
						    try {
						        dt=mddyy.parse(dateInString);
						    }catch(ParseException e4) {
						        e4.printStackTrace();
						    }
						}
					}
				}
			}
		}
		return dt;
	}

	public static String changeDateToISOString(Date date) {
		TimeZone tz = TimeZone.getTimeZone("UTC");
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'"); // Quoted "Z" to indicate UTC, no timezone offset
		df.setTimeZone(tz);
		return df.format(date);
	}
}
