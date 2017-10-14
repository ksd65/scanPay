package com.epay.scanpay.common.utils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

	public static String null2Empty(String oldStr, String newStr) {
		if (null == oldStr) {
			return newStr;
		}
		return oldStr;
	}

	public static String null2Empty(String s) {
		return null2Empty(s, "");
	}

	public static String jsonMsg(boolean flag, String returnStr) {
		return "{\"success\":" + flag + ",\"msg\":\"" + returnStr + "\"}";
	}

	public static List<Long> strToList(String str, String rex) {
		List<Long> result = new ArrayList<Long>();
		String[] items = str.split(",");
		for (int i = 0; i < items.length; i++) {
			if (!"".equals(items[i]))
				result.add(Long.parseLong(items[i]));
		}
		return result;
	}

	/**
	 * 生成省略词工具类
	 * 
	 * @param str
	 * @param limit
	 * @param isAllLength
	 * @return
	 */
	public static String getEllipsisString(String str, int limit,
			boolean isAllLength) {
		if (str.length() <= limit) {
			return str;
		} else {
			StringBuilder stb = new StringBuilder(str);
			if (isAllLength) {
				stb.delete(limit - 3, str.length());
			} else {
				stb.delete(limit, str.length());
			}
			stb.append("...");
			return stb.toString();
		}
	}
	
	  /**
     * 匹配是否包含数字包含2位小数
     * @param str 可能为中文，也可能是-19162431.1254，不使用BigDecimal的话，变成-1.91624311254E7,
     * @return
     * @author 
     * @date 
     */
    public static boolean isNumeric(String str) {
        // 该正则表达式可以匹配所有的数字 包括负数
        Pattern pattern = Pattern.compile("^[1-9][0-9]+\\.[0-9]{2}?$");
        String bigStr;
        try {
            bigStr = new BigDecimal(str).toString();
        } catch (Exception e) {
            return false;//异常 说明包含非数字。
        }

        Matcher isNum = pattern.matcher(bigStr); // matcher是全匹配
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }
    public static void main(String[] args) {
    	System.out.println(isNumeric("111111.11"));
    	System.out.println(isNumeric("11.11"));
    	System.out.println(isNumeric("01.11"));
    	System.out.println(isNumeric("11.1a"));
    	System.out.println(isNumeric("11.aa"));
    	System.out.println(isNumeric("11.111"));
    	System.out.println(isNumeric("1111.a1"));
    }
}
