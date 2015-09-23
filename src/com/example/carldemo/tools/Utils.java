package com.example.carldemo.tools;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.TimeZone;

/**
 * @author Peichen Xu
 *
 */
public class Utils {
	
	public static final int ONE_MINUTE = 60 * 1000;
	public static final int ONE_HOUR = 60 * ONE_MINUTE;

	public static <T> boolean isNullOrEmpty(List<T> list) {
		return list == null || list.size() <= 0;
	}
	
	public static <T> boolean isNullOrEmpty(T[] list) {
		return list == null || list.length <= 0;
	}
	
	public static <T> boolean isNullOrEmpty(Set<T> set) {
		return set == null || set.size() <= 0;
	}
	
	public static String getFormatPlayTime(int time) {
		String template = "mm:ss";
		if (time >= ONE_HOUR) {
			template = "HH:mm:ss";
		}
		SimpleDateFormat sdf = new SimpleDateFormat(template, Locale.CHINA);
		sdf.setTimeZone(TimeZone.getTimeZone("GMT+0:00"));
		return sdf.format(time);
	}
	
}
