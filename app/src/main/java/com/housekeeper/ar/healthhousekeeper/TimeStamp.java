package com.housekeeper.ar.healthhousekeeper;

import java.text.DecimalFormat;

public class TimeStamp {
	String TAG = "TimeStamp";
	public static String TimeStamp2Date(String timestampString, String formats){
		Long timestamp = Long.parseLong(timestampString)*1000;
		String date = new java.text.SimpleDateFormat(formats).format(new java.util.Date(timestamp));
		return date;
	}
	public static String TimeStamp2Date(Long timestamp, String formats){
		//Long timestamp = Long.parseLong(timestampString)*1000;
		String date = new java.text.SimpleDateFormat(formats).format(new java.util.Date(timestamp));
		return date;
	}
	public static int compareTime(String time1, String time2){
		long long1 = Long.parseLong(dateToInt(time1));
		long long2 = Long.parseLong(dateToInt(time2));
		int result = (int)(long1-long2);

		return  result;
	}
	public static String dateToInt(String str){
		str=str.replace(":", "");
		str=str.replace("-", "");
		str=str.replace(" ", "");
		return str;

	}
	public static double get2Double(double a){
		DecimalFormat df=new DecimalFormat("0.00");
		return new Double(df.format(a).toString());
	}

	public static boolean isChinese(String str){
		if(str.length() < str.getBytes().length){
			return true;
		}else{
			return false;
		}
	}

}
