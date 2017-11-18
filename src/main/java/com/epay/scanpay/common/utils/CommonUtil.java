package com.epay.scanpay.common.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.mysql.fabric.xmlrpc.base.Data;

import net.sf.json.JSONObject;

/**
 * 工具类
 * 
 *
 * 
 */
public class CommonUtil {
	/**
	 * 生成带签名的请求数据
	 * @param paramData
	 * @return
	 */
	public static String createSecurityRequstData(Object reqData){
		
		
		String token="04faecde-792e-4027-89c9-6910798887b6";
		String timeStamp=String.valueOf(new Date().getTime() / 1000);
		String nonceStr=getRandomStringByLength(32);
		
		Map<String, String> paramMap=new HashMap<String, String>();
		paramMap.put("token", token);
		paramMap.put("timeStamp", timeStamp);
		paramMap.put("nonceStr", nonceStr);
		paramMap.put("reqData", reqData.toString());
		String sign=SignatureUtil.createSignature(paramMap);
		
		JSONObject result=new JSONObject();
		result.put("token", token);
		result.put("timeStamp", timeStamp);
		result.put("nonceStr", nonceStr);
		result.put("signature", sign);
		result.put("reqData", reqData);
		
		return result.toString();
	}
	
	/**
	 * 获取随机数 	
	 * @param length
	 * @return
	 */
	    public static String getRandomStringByLength(int length) {
	        String base = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	        Random random = new Random();
	        StringBuffer sb = new StringBuffer();
	        for (int i = 0; i < length; i++) {
	            int number = random.nextInt(base.length());
	            sb.append(base.charAt(number));
	        }
	        return sb.toString();
	    }
	    
	    public static void main(String[] args) {
	    	String token="04faecde-792e-4027-89c9-6910798887b6";
			String timeStamp=String.valueOf(new Date().getTime() / 1000);
			String nonceStr=getRandomStringByLength(32);
			
			
			JSONObject reqData=new JSONObject();
			
			reqData.put("epayCode", "J000000011");
			reqData.put("money", "0.1");
			reqData.put("userId", "oSmj1slOoxkyqquGHsTWDaLqyLOY");
			reqData.put("remark", "测试");

			reqData.put("appClientType", "micromessenger");
			
			Map<String, String> paramMap=new HashMap<String, String>();
			paramMap.put("token", token);
			paramMap.put("timeStamp", timeStamp);
			paramMap.put("nonceStr", nonceStr);
			paramMap.put("reqData", reqData.toString());
			String sign=SignatureUtil.createSignature(paramMap);
			
			JSONObject result=new JSONObject();
			result.put("token", token);
			result.put("timeStamp", timeStamp);
			result.put("nonceStr", nonceStr);
			result.put("signature", sign);
			result.put("reqData", reqData);
			
			System.out.println(result.toString());
		}
}
