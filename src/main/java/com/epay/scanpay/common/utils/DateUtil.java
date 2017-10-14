package com.epay.scanpay.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期工具类
 * 
 * @author ghq
 *
 */
public class DateUtil {
	/**
	 * 获取日期
	 * 
	 * @return
	 */
	public static String getDate() {
		return getDate(new Date());
	}
	
	public static String getDateStr() {
		return getDateStr(new Date());
	}

	/**
	 * 获取时间
	 * 
	 * @return
	 */
	public static String getTime() {
		return getTime(new Date());
	}

	/**
	 * 获取日期
	 * 
	 * @return
	 */
	public static String getDate(Date date) {
		SimpleDateFormat dateSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return dateSimpleDateFormat.format(date);
	}
	
	/**
	 * 获取日期
	 * 
	 * @return
	 */
	public static String getDateStr(Date date) {
		SimpleDateFormat dateSimpleDateFormat = new SimpleDateFormat("yyyyMMdd");
		return dateSimpleDateFormat.format(date);
	}

	/**
	 * 获取时间
	 * 
	 * @return
	 */
	public static String getTime(Date date) {
		SimpleDateFormat dateSimpleDateFormat = new SimpleDateFormat("HH:mm:ss");
		return dateSimpleDateFormat.format(date);
	}

	public static void main(String[] args) {
		System.out.println(getDateStr());
		System.out.println(getTime());
	}
}
