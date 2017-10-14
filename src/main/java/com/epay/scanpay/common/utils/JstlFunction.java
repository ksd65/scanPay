package com.epay.scanpay.common.utils;

import java.util.Collection;

/**
 * 自定义jstl函数
 * @author ghq
 *
 */
public class JstlFunction {

	public static boolean contains(Collection<?> coll, Object o) {
		if(coll !=null && !coll.isEmpty()){
			return coll.contains(o);
		}else{
			return false;
		}
	}
	
	/**
     * 把银行卡号转成1234 **** **** 3456格式
     * @param card
     * @return
     */
    public static String getEncryptCard(String card){
    	String ret = card.replaceAll("\\d{4}(?!$)", "$0 ");
    	int begin = ret.indexOf(" ");
    	int end = ret.lastIndexOf(" ");
    	String head = ret.substring(0,begin);
    	String body = ret.substring(begin,end);
    	String foot = ret.substring(end);
    	body = body.replaceAll("\\d", "*");
    	return head+body+foot;
    }
}
