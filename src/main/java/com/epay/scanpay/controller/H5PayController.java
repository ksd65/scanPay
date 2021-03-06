package com.epay.scanpay.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.epay.scanpay.common.constant.DataDicConstant;
import com.epay.scanpay.common.constant.SysConfig;
import com.epay.scanpay.common.utils.CommonUtil;
import com.epay.scanpay.common.utils.EpaySignUtil;
import com.epay.scanpay.common.utils.HttpUtil;
import com.epay.scanpay.common.utils.IpUtils;





@Controller
public class H5PayController {
	private static Logger logger = LoggerFactory.getLogger(DebitNoteController.class);
	
	@RequestMapping("/payment/h5index")
	public String index(Model model,HttpServletRequest request){
		String orderNum = "H"+new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		String ip = "";
		String page = "";
		try {
			ip = IpUtils.getIpAddress(request);
			String type = request.getParameter("type");
			if(type== null ||"".equals(type)){
				type = "";
			}
			page = "payment/wxH5Pay"+type;
			System.out.println("ip===="+ip);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.addAttribute("orderNum", orderNum);
		model.addAttribute("ip", ip);
		
		return page;
		
	}
	
	@RequestMapping("/payment/h5indexqq")
	public String indexQq(Model model,HttpServletRequest request){
		String orderNum = "H"+new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		String ip = "";
		String page = "";
		try {
			ip = IpUtils.getIpAddress(request);
			String type = request.getParameter("type");
			if(type== null ||"".equals(type)){
				type = "";
			}
			page = "payment/qqH5Pay"+type;
			System.out.println("ip===="+ip);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.addAttribute("orderNum", orderNum);
		model.addAttribute("ip", ip);
		
		return page;
		
	}
	
	@RequestMapping("/payment/h5indexjd")
	public String indexJd(Model model,HttpServletRequest request){
		String orderNum = "H"+new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		String ip = "";
		String page = "";
		try {
			ip = IpUtils.getIpAddress(request);
			String type = request.getParameter("type");
			if(type== null ||"".equals(type)){
				type = "";
			}
			page = "payment/jdH5Pay"+type;
			System.out.println("ip===="+ip);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.addAttribute("orderNum", orderNum);
		model.addAttribute("ip", ip);
		
		return page;
		
	}
	
	@RequestMapping("/payment/h5indexzfb")
	public String indexZfb(Model model,HttpServletRequest request){
		String orderNum = "H"+new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		String ip = "";
		String page = "";
		try {
			ip = IpUtils.getIpAddress(request);
			String type = request.getParameter("type");
			if(type== null ||"".equals(type)){
				type = "";
			}
			page = "payment/zfbH5Pay"+type;
			System.out.println("ip===="+ip);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.addAttribute("orderNum", orderNum);
		model.addAttribute("ip", ip);
		
		return page;
		
	}
	
	
	@RequestMapping("/payment/h5Confirm")
	public String h5Confirm(Model model,HttpServletRequest request){
		
		
		String memberCode = request.getParameter("memberCode");
		String callbackUrl = request.getParameter("callbackUrl");
		String orderNum = request.getParameter("orderNum");
		String payMoney = request.getParameter("payMoney");
		String sceneInfo = request.getParameter("sceneInfo");
		String ip = request.getParameter("ip");
		
		
		String privateKey = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCIxkLM+QBKTqw9BsCSjiLw5pO186HwgMOZIXUFDVnRsYyPeiFsnPgaNQmT7L5Ur0jHtKc0yUdk5jd8BG06N40474MiBzNNNJJa4mkPQfUlsD0T0NMLesjFthKqyQxS1cMqpz8b372pwsWb16BVM/9i5ak9JgqguAtEkxR2lSNd0sn/GEqH9hdw6RvMH/Y/ePztdA3bANJL64g0hrKKBW0YcJvXJpKF8vIwuNuamOoKOzlEIPZC05331j4c8xw/EUiBbS9sELAT+MLNlYXHIWtpDDbJTNZGKXDskJpfae7ifW4TU6ZlraiW4JGabl1d5DH2t4k5NnFqiquPZWPhZ8u9AgMBAAECggEAdGdHsuq4NIWAUO/ONOyDEEMss04GJIlx7oFq9kHGj5Br8DAhAi8VeDhrTlnOIoSLjGtTYrlq6ZSE5CdgTou4xRwSnoNCRhLX/EF06GdaHBlB4ft3oe19scajXHZ+5oDG+SYdr7tbz37Uby20Zs86KxEKV+BoayA3dsU2RTXoQ5BCtMeXaIUif3JjHGy1xHkR1MPN2vXvwMqU7Femk/Qr9s8W+9IgVp5ah28Kx5qkR63TkEONyYTbI+U4El/bVnZmcaHL+/KJ9wo8+/7hHrl+mWV0T3iKa4tJWed9V+IUKrLeUpn9DOIv9WNlDFjJqBckC/GBhujG7uRKWfminGRAgQKBgQDJnQv9HAgUnGsYTayY1iW2CwgFZJn1RgaTCjo1Oc6tDTFXdP4uIFfxEfnLhGuRuItTKHW1hf1Hp4n+wiZO5q1HjjC3bLBUy1Lw+HvaJLNHRkCye58cg2Jlu81girzciazLiHqfEaZxFsUq8WsqzZIBRr0gPDDJMrSOBfkf+hWckwKBgQCtq5WSq/t5Cr1N5AOoYoBDvJOHqQ4/6Ue0JF270/u1H/qdMQ9w/OQRixhHNP7g5oiPBWKPQTs3uU9/SHpX5CHsyqlQDvOkrSnESp4pz0BUOT971er/8o79NrUE9ZQMRDhu0WHXtbPOq+7siUw9SA5HcEh8Hd/Y3xLOFhvw90x4bwKBgGBGFRZ9j0JAW0eUt8mX4RQn+mGQ44/jK3qFlLwb6ZxrQ1eO712ZZkUgn1bW2gMQy78e/+55mDPiRhwYG/DraG1V8d91EFK9cNLO5V2Kzu1HF9fi/lzARHluD6l9NqhdOd1LQ7q30/IGvIpAFDuxRHpFjERbWbSJ+Pwk0Ay8ABvvAoGAah4XFfkqfqqWQ3rY1VHiyAD5MIKXJ2w2mRdDgxqjiegRbW1l3wdXoHSakCAMwYV72dBTie807Pa5Ya/6uau3IwYucLHCJFR+2ecyP5/Y0d3tMZDjuCMRRh3gfDhGjzw8M1KTc4geZ2Fda4D1adiWiQZN9DEY715XEkAmMJYbTtcCgYEAn9BcQcpAsbV2fpmO0VU8CMFoTnbfYpmHR9N6A45r72j1nQmJxfZbvL7JiIK7jhRUuQERC+jK08FPKbFDoE5a7UxNiKDE/ZtuEwPehrf8yVenxmAWMh4llc1HoWQfjCY8BJuAm3K4jHtswB0+PQfTMG7PVisoMfdVEJ4gq8BLAv0=";
		if("9010000002".equals(memberCode)){
			privateKey = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCfKi2ZOWOPYsDPj6RT9kV2s5RCOezBoEnAMfVoEbe5eQB5rJhTwRaN2rGHPMlv1baB6dB4yJ49Eidvq9IX4D7lhnbPwvvCi7EjszJgyrID8miVu9wBpPRUdjS+YsrWyGrVK0vMxl79TlQWK22io/yZp9aWTCTgSM3qEyQaMyk6bEANYIUaSlpbiDJ2mvaU6JpSDZJLHKpNvPVNDLk52fpuArOuvHo2Nn0NOrnv8bjAYk8CMxNBuE6xicHl3ycDU8PQ3MKEhWkyNAm/vVH8v37kYcDpx6ftUM7ddAq+SOE9BPvjoxsBNYEKztvZxBdaZHYZUEGtjyBeulzr0L1KZJDtAgMBAAECggEACDG1s0O/GmytHIJ6pU/yd4/7PAWbaMSFx31K8xambMgL/Dekh/tS2+68YQgCHt3TzZBqCS3a5639lcQ0xsHmuw5XI48YQwXKEtpw54bH72gVdk/7naIOaiLDyGFxq+kZhuv5tQspbMURkyqdNFhY8tgvNgGpjFpzL2/Y1fh4UOeYzGFTKMCzpxfHwCdpfFoW4QNGbVvC+IKpuLyKf58hBvSaR8AaFCQ0XTBVl6QbB/8ryWL2xbdg+/+cB4TXwD/Ct3xcLIEF5ow7/1IVyqlSzUelh9SaKq2113T1HGFTr7rELlXplU5J1icV6JEdoq+RplW3SQ9dMesV9l8dhAA3QQKBgQDpKSQPq7nAVCEcEzohiBvZMibW/EBxWBBIXgAc1G/DasfDuTxyW//4gPUeozW4ZMzwnlc31CMkusd806Iecio0+LrKB3V9yFW4fbzcLYdY4tN3oeQ9MoX9nkFM/ikKl2B1HcLmBWZqgg3kdf1hIa/E2YdK/x+NofaSz4lQMjjveQKBgQCuwXnzrk9RqQ7bg8Ol38NAqV+3xQ7hrvzH/Zjifd/sMbGC0y0JgM++3Y5jALsxD7GcQt9V1m31qGlXzW4aitNxdlMpvcSfHdUE9kXgbp9g1rqJDg/hJe+nH7nBI6NmRdrb+Ns0lnNYXBHYAQ/W3M521U46TWtODLcNOXwXbGlMFQKBgQCSkBXm899zkm6tozhrU4+N3ASmJzKrDNxPYSdY+AC5KiogUhQ5HrOslgN/GsDuBA7/Qck5gtQEhpRXVwEVelYlriRcUov8YS3hJsjM7qGhshOTo+RAw72OSyhpKWrLCZTMicS1qrdSRCZPcguwPuiqKMLu1agT87d3WZXLH4bCoQKBgB8lHDbxufEz0BIPSa8mUgYUKZr249AU/7gk2jqDdIUD1j8ao8wtyNibY+UBHFuCEIVo5aTGspI1kZC0bAsO8uAl1mx6BbDWAEECIzH8hSsdGeGTQAFAYZXHcbOaRmTTzk2l7GtS5Pu6bPOyPMBuWd2T5n09jwI6AeW5eQQzrhCBAoGBAKR9CR4SYsH+lcONvAfRC7fQMrS4FwwUbHYUuDEDmDn07oelaJYh7UNbCbNG6x0cHYkJleQlDHyl7hSwHl5pBXzJooSKWCj9Hn3DAfqUouFNZf1kZruoOWBosGt/pKjDzNwVhAp+gCeD9veGZxrvfuVdUgS5yv0fgqQg2qsGdeN3";
		}
		
		//待签名字符串
		Map<String,String> params = new HashMap<String,String>();
		params.put("orderNum", orderNum); 
		params.put("memberCode", memberCode);  
		params.put("callbackUrl",  callbackUrl);
		params.put("payMoney", payMoney );
		params.put("sceneInfo", sceneInfo );
		params.put("ip",  ip);
		
		String payType = request.getParameter("payType");
		if(payType !=null &&!"".equals(payType)){
			params.put("payType",  payType);
		}else{
			payType = "1";
		}
		
 		String srcStr = orderedKey(params);
 		System.out.println("srcStr==="+srcStr);
		String signstr = EpaySignUtil.sign(privateKey, srcStr);
		
		model.addAttribute("orderNum", orderNum);
		model.addAttribute("memberCode", memberCode);
		model.addAttribute("callbackUrl", callbackUrl);
		model.addAttribute("payMoney", payMoney);
		model.addAttribute("payType", payType);
		model.addAttribute("sceneInfo", sceneInfo);
		model.addAttribute("ip", ip);
		model.addAttribute("signStr", signstr);
		
		if("1".equals(payType)){
			return "payment/wxH5Confirm";
		}else if("3".equals(payType)||"5".equals(payType)||"2".equals(payType)){
			return "payment/qqH5Confirm";
		}else{
			return  "payment/fail";
		}
		
		
	}
	
	
	private String orderedKey(Map<String, String> dataMap){
		ArrayList<String> list = new ArrayList<String>();
		for(String key:dataMap.keySet()){
			if(dataMap.get(key)!=null&&!"".equals(dataMap.get(key))){
				list.add(key + "=" + dataMap.get(key));
			}
		}
		int size = list.size();
		String [] arrayToSort = list.toArray(new String[size]);
		Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < size; i ++) {
			sb.append(arrayToSort[i]);
			sb.append("&");
		}
		String result = sb.toString();
		if(StringUtils.isNotBlank(result)){//去掉最后的&
			result = result.substring(0, result.length()-1);
		}
		return result;
	}
	
	@RequestMapping("/payment/toWxH5")
	public String toWxH5(Model model,HttpServletRequest request){
		String page = "payment/fail";
		
		try { // 获取页面请求信息
			
			String memberCode = request.getParameter("memberCode");
			String callbackUrl = request.getParameter("callbackUrl");
			String signStr = request.getParameter("signStr");
			String orderNum = request.getParameter("orderNum");
			String payMoney = request.getParameter("payMoney");
			String sceneInfo = request.getParameter("sceneInfo");
			String ip = request.getParameter("ip");
			String agent = request.getHeader("user-agent");
			logger.info("进入请求toWxH5");
			logger.info("memberCode="+memberCode);
			logger.info("callbackUrl="+callbackUrl);
			logger.info("signStr="+signStr);
			logger.info("orderNum="+orderNum);
			logger.info("payMoney="+payMoney);
			logger.info("sceneInfo="+sceneInfo);
			logger.info("ip="+ip);
			logger.info("agent="+agent);
		   
			if(payMoney==null || "".equals(payMoney)){
				model.addAttribute("errorFlag", "1");
				model.addAttribute("errorMsg", "支付金额payMoney为空");
				return page;
			}
			if(memberCode==null || "".equals(memberCode)){
				model.addAttribute("errorFlag", "1");
				model.addAttribute("errorMsg", "商户号memberCode为空");
				return page;
			}
			if(callbackUrl==null || "".equals(callbackUrl)){
				model.addAttribute("errorFlag", "1");
				model.addAttribute("errorMsg", "回调地址callbackUrl为空");
				return page;
			}
			if(orderNum==null || "".equals(orderNum)){
				model.addAttribute("errorFlag", "1");
				model.addAttribute("errorMsg", "商户订单号orderNum为空");
				return page;
			}
			
			if(sceneInfo==null || "".equals(sceneInfo)){
				model.addAttribute("errorFlag", "1");
				model.addAttribute("errorMsg", "场景信息sceneInfo为空");
				return page;
			}
			if(ip==null || "".equals(ip)){
				model.addAttribute("errorFlag", "1");
				model.addAttribute("errorMsg", "ip为空");
				return page;
			}
			if(signStr==null || "".equals(signStr)){
				model.addAttribute("errorFlag", "1");
				model.addAttribute("errorMsg", "签名signStr为空");
				return page;
			}
			JSONObject reqData = new JSONObject();
			reqData.put("memberCode", memberCode);
			reqData.put("orderNum", orderNum);
			reqData.put("payMoney", payMoney);
			reqData.put("sceneInfo", sceneInfo);
			reqData.put("ip", ip);
			reqData.put("callbackUrl", callbackUrl);
			reqData.put("signStr", signStr);
			reqData.put("userAgent", agent);
			
			JSONObject responseJson = JSONObject.fromObject(
					HttpUtil.sendPostRequest(SysConfig.pospService + "/api/debitNote/wxH5Pay",
							CommonUtil.createSecurityRequstData(reqData)));
			if ("0000".equals(responseJson.getString("returnCode"))) {
				
				String payUrl = responseJson.getString("payUrl");
				String routeCode = responseJson.getString("routeCode");
				if(routeCode.equals(DataDicConstant.WW_ROUTE_CODE)){
					model.addAttribute("memberCode", responseJson.getString("memberCode"));
					model.addAttribute("orderNum", responseJson.getString("orderNum"));
					model.addAttribute("payMoney", responseJson.getString("payMoney"));
					model.addAttribute("payType", responseJson.getString("payType"));
					model.addAttribute("sceneInfo", responseJson.getString("sceneInfo"));
					model.addAttribute("ip", responseJson.getString("ip"));
					model.addAttribute("callbackUrl", responseJson.getString("callbackUrl"));
					model.addAttribute("signStr", responseJson.getString("signStr"));
					request.setAttribute("memberCode", responseJson.getString("memberCode"));
					request.setAttribute("orderNum", responseJson.getString("orderNum"));
					request.setAttribute("payMoney", responseJson.getString("payMoney"));
					request.setAttribute("payType", responseJson.getString("payType"));
					request.setAttribute("sceneInfo", responseJson.getString("sceneInfo"));
					request.setAttribute("ip", responseJson.getString("ip"));
					request.setAttribute("callbackUrl", responseJson.getString("callbackUrl"));
					request.setAttribute("signStr", responseJson.getString("signStr"));
					page = "payment/wwH5PaySubmit";
				}else{
					page = "payment/wxH5Submit";
				}
				request.setAttribute("payUrl", payUrl);
				model.addAttribute("payUrl", payUrl);
				logger.info("调服务返回payUrl="+payUrl);
			}else{
			    request.setAttribute("errorMsg", responseJson.getString("returnMsg"));
				model.addAttribute("errorMsg", responseJson.getString("returnMsg"));
			}
		}catch (Exception ex) {
			ex.printStackTrace();
		}
		return page;
		
	}
	
	
	@RequestMapping("/payment/toH5")
	public String toH5(Model model,HttpServletRequest request){
		String page = "payment/fail";
		
		try { // 获取页面请求信息
			
			String memberCode = request.getParameter("memberCode");
			String callbackUrl = request.getParameter("callbackUrl");
			String frontUrl = request.getParameter("frontUrl");
			String signStr = request.getParameter("signStr");
			String orderNum = request.getParameter("orderNum");
			String payMoney = request.getParameter("payMoney");
			String sceneInfo = request.getParameter("sceneInfo");
			String ip = request.getParameter("ip");
			String payType = request.getParameter("payType");
			String agent = request.getHeader("user-agent");
			String ipReal = IpUtils.getIpAddress(request);
			logger.info("进入请求toQqH5");
			logger.info("memberCode="+memberCode);
			logger.info("callbackUrl="+callbackUrl);
			logger.info("frontUrl="+frontUrl);
			logger.info("signStr="+signStr);
			logger.info("orderNum="+orderNum);
			logger.info("payMoney="+payMoney);
			logger.info("payType="+payType);
			logger.info("sceneInfo="+sceneInfo);
			logger.info("ip="+ip);
			logger.info("ipReal="+ipReal);
			logger.info("agent="+agent);
			
			if(payMoney==null || "".equals(payMoney)){
				model.addAttribute("errorFlag", "1");
				model.addAttribute("errorMsg", "支付金额payMoney为空");
				return page;
			}
			if(memberCode==null || "".equals(memberCode)){
				model.addAttribute("errorFlag", "1");
				model.addAttribute("errorMsg", "商户号memberCode为空");
				return page;
			}
			if(callbackUrl==null || "".equals(callbackUrl)){
				model.addAttribute("errorFlag", "1");
				model.addAttribute("errorMsg", "回调地址callbackUrl为空");
				return page;
			}
			if(orderNum==null || "".equals(orderNum)){
				model.addAttribute("errorFlag", "1");
				model.addAttribute("errorMsg", "商户订单号orderNum为空");
				return page;
			}
			
			if(sceneInfo==null || "".equals(sceneInfo)){
				model.addAttribute("errorFlag", "1");
				model.addAttribute("errorMsg", "场景信息sceneInfo为空");
				return page;
			}
			if(ip==null || "".equals(ip)){
				model.addAttribute("errorFlag", "1");
				model.addAttribute("errorMsg", "ip为空");
				return page;
			}
			if(signStr==null || "".equals(signStr)){
				model.addAttribute("errorFlag", "1");
				model.addAttribute("errorMsg", "签名signStr为空");
				return page;
			}
			if(payType==null || "".equals(payType)){
				payType = "";
			}
			if(frontUrl==null || "".equals(frontUrl)){
				frontUrl = "";
			}
			JSONObject reqData = new JSONObject();
			reqData.put("memberCode", memberCode);
			reqData.put("orderNum", orderNum);
			reqData.put("payMoney", payMoney);
			reqData.put("sceneInfo", sceneInfo);
			reqData.put("ip", ip);
			reqData.put("callbackUrl", callbackUrl);
			reqData.put("signStr", signStr);
			reqData.put("payType", payType);
			reqData.put("userAgent", agent);
			reqData.put("ipReal", ipReal);
			reqData.put("frontUrl", frontUrl);
			
			JSONObject responseJson = JSONObject.fromObject(
					HttpUtil.sendPostRequest(SysConfig.pospService + "/api/debitNote/wxH5Pay",
							CommonUtil.createSecurityRequstData(reqData)));
			if ("0000".equals(responseJson.getString("returnCode"))) {
				String payUrl = responseJson.getString("payUrl");
				String routeCode = responseJson.getString("routeCode");
				if(routeCode.equals(DataDicConstant.WW_ROUTE_CODE)){
					model.addAttribute("memberCode", responseJson.getString("memberCode"));
					model.addAttribute("orderNum", responseJson.getString("orderNum"));
					model.addAttribute("payMoney", responseJson.getString("payMoney"));
					model.addAttribute("payType", responseJson.getString("payType"));
					model.addAttribute("sceneInfo", responseJson.getString("sceneInfo"));
					model.addAttribute("ip", responseJson.getString("ip"));
					model.addAttribute("callbackUrl", responseJson.getString("callbackUrl"));
					model.addAttribute("signStr", responseJson.getString("signStr"));
					request.setAttribute("memberCode", responseJson.getString("memberCode"));
					request.setAttribute("orderNum", responseJson.getString("orderNum"));
					request.setAttribute("payMoney", responseJson.getString("payMoney"));
					request.setAttribute("payType", responseJson.getString("payType"));
					request.setAttribute("sceneInfo", responseJson.getString("sceneInfo"));
					request.setAttribute("ip", responseJson.getString("ip"));
					request.setAttribute("callbackUrl", responseJson.getString("callbackUrl"));
					request.setAttribute("signStr", responseJson.getString("signStr"));
					page = "payment/wwH5PaySubmit";
				}else{
					page = "payment/wxH5Submit";
				}
				request.setAttribute("payUrl", payUrl);
				model.addAttribute("payUrl", payUrl);
				
				logger.info("调服务返回payUrl="+payUrl);
			}else{
			    request.setAttribute("errorMsg", responseJson.getString("returnMsg"));
				model.addAttribute("errorMsg", responseJson.getString("returnMsg"));
			}
		}catch (Exception ex) {
			ex.printStackTrace();
		}
		return page;
		
	}
	
	
	@RequestMapping("/payment/h5Result")
	public String h5Result(Model model,HttpServletRequest request){
		
		return "payment/h5Result";
		
	}
	
	@RequestMapping("/payment/h5indextest")
	public String h5indextest(Model model,HttpServletRequest request){
		String orderNum = "H"+new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		String ip = "";
		String page = "";
		try {
			ip = IpUtils.getIpAddress(request);
			page = "payment/h5PayTest";
			System.out.println("ip===="+ip);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.addAttribute("orderNum", orderNum);
		model.addAttribute("ip", ip);
		
		return page;
		
	}
	
	@RequestMapping("/payment/h5ConfirmTest")
	public String h5ConfirmTest(Model model,HttpServletRequest request){
		
		
		String memberCode = request.getParameter("memberCode");
		String callbackUrl = request.getParameter("callbackUrl");
		String orderNum = request.getParameter("orderNum");
		String payMoney = request.getParameter("payMoney");
		String sceneInfo = request.getParameter("sceneInfo");
		String ip = request.getParameter("ip");
		String payType = request.getParameter("payType");
		String routeCode = request.getParameter("routeCode");
		String merchantCode = request.getParameter("merchantCode").trim();
		
		String privateKey = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCIxkLM+QBKTqw9BsCSjiLw5pO186HwgMOZIXUFDVnRsYyPeiFsnPgaNQmT7L5Ur0jHtKc0yUdk5jd8BG06N40474MiBzNNNJJa4mkPQfUlsD0T0NMLesjFthKqyQxS1cMqpz8b372pwsWb16BVM/9i5ak9JgqguAtEkxR2lSNd0sn/GEqH9hdw6RvMH/Y/ePztdA3bANJL64g0hrKKBW0YcJvXJpKF8vIwuNuamOoKOzlEIPZC05331j4c8xw/EUiBbS9sELAT+MLNlYXHIWtpDDbJTNZGKXDskJpfae7ifW4TU6ZlraiW4JGabl1d5DH2t4k5NnFqiquPZWPhZ8u9AgMBAAECggEAdGdHsuq4NIWAUO/ONOyDEEMss04GJIlx7oFq9kHGj5Br8DAhAi8VeDhrTlnOIoSLjGtTYrlq6ZSE5CdgTou4xRwSnoNCRhLX/EF06GdaHBlB4ft3oe19scajXHZ+5oDG+SYdr7tbz37Uby20Zs86KxEKV+BoayA3dsU2RTXoQ5BCtMeXaIUif3JjHGy1xHkR1MPN2vXvwMqU7Femk/Qr9s8W+9IgVp5ah28Kx5qkR63TkEONyYTbI+U4El/bVnZmcaHL+/KJ9wo8+/7hHrl+mWV0T3iKa4tJWed9V+IUKrLeUpn9DOIv9WNlDFjJqBckC/GBhujG7uRKWfminGRAgQKBgQDJnQv9HAgUnGsYTayY1iW2CwgFZJn1RgaTCjo1Oc6tDTFXdP4uIFfxEfnLhGuRuItTKHW1hf1Hp4n+wiZO5q1HjjC3bLBUy1Lw+HvaJLNHRkCye58cg2Jlu81girzciazLiHqfEaZxFsUq8WsqzZIBRr0gPDDJMrSOBfkf+hWckwKBgQCtq5WSq/t5Cr1N5AOoYoBDvJOHqQ4/6Ue0JF270/u1H/qdMQ9w/OQRixhHNP7g5oiPBWKPQTs3uU9/SHpX5CHsyqlQDvOkrSnESp4pz0BUOT971er/8o79NrUE9ZQMRDhu0WHXtbPOq+7siUw9SA5HcEh8Hd/Y3xLOFhvw90x4bwKBgGBGFRZ9j0JAW0eUt8mX4RQn+mGQ44/jK3qFlLwb6ZxrQ1eO712ZZkUgn1bW2gMQy78e/+55mDPiRhwYG/DraG1V8d91EFK9cNLO5V2Kzu1HF9fi/lzARHluD6l9NqhdOd1LQ7q30/IGvIpAFDuxRHpFjERbWbSJ+Pwk0Ay8ABvvAoGAah4XFfkqfqqWQ3rY1VHiyAD5MIKXJ2w2mRdDgxqjiegRbW1l3wdXoHSakCAMwYV72dBTie807Pa5Ya/6uau3IwYucLHCJFR+2ecyP5/Y0d3tMZDjuCMRRh3gfDhGjzw8M1KTc4geZ2Fda4D1adiWiQZN9DEY715XEkAmMJYbTtcCgYEAn9BcQcpAsbV2fpmO0VU8CMFoTnbfYpmHR9N6A45r72j1nQmJxfZbvL7JiIK7jhRUuQERC+jK08FPKbFDoE5a7UxNiKDE/ZtuEwPehrf8yVenxmAWMh4llc1HoWQfjCY8BJuAm3K4jHtswB0+PQfTMG7PVisoMfdVEJ4gq8BLAv0=";
		//待签名字符串
		Map<String,String> params = new HashMap<String,String>();
		params.put("orderNum", orderNum); 
		params.put("memberCode", memberCode);  
		params.put("callbackUrl",  callbackUrl);
		params.put("payMoney", payMoney );
		params.put("sceneInfo", sceneInfo );
		params.put("ip",  ip);
		params.put("payType",  payType);
		params.put("routeCode",  routeCode);
		params.put("merchantCode",  merchantCode);
		
		
 		String srcStr = orderedKey(params);
 		System.out.println("srcStr==="+srcStr);
		String signstr = EpaySignUtil.sign(privateKey, srcStr);
		
		model.addAttribute("orderNum", orderNum);
		model.addAttribute("memberCode", memberCode);
		model.addAttribute("callbackUrl", callbackUrl);
		model.addAttribute("payMoney", payMoney);
		model.addAttribute("sceneInfo", sceneInfo);
		model.addAttribute("ip", ip);
		model.addAttribute("signStr", signstr);
		model.addAttribute("payType", payType);
		model.addAttribute("routeCode", routeCode);
		model.addAttribute("merchantCode", merchantCode);
		
		return "payment/h5ConfirmTest";
	}
	
	@RequestMapping("/payment/toH5Test")
	public String toH5Test(Model model,HttpServletRequest request){
		String page = "payment/fail";
		
		try { // 获取页面请求信息
			
			String memberCode = request.getParameter("memberCode");
			String callbackUrl = request.getParameter("callbackUrl");
			String signStr = request.getParameter("signStr");
			String orderNum = request.getParameter("orderNum");
			String payMoney = request.getParameter("payMoney");
			String sceneInfo = request.getParameter("sceneInfo");
			String ip = request.getParameter("ip");
			String payType = request.getParameter("payType");
			String routeCode = request.getParameter("routeCode");
			String merchantCode = request.getParameter("merchantCode");
			String agent = request.getHeader("user-agent");
			logger.info("进入请求toQqH5");
			logger.info("memberCode="+memberCode);
			logger.info("callbackUrl="+callbackUrl);
			logger.info("signStr="+signStr);
			logger.info("orderNum="+orderNum);
			logger.info("payMoney="+payMoney);
			logger.info("payType="+payType);
			logger.info("sceneInfo="+sceneInfo);
			logger.info("ip="+ip);
			logger.info("routeCode="+routeCode);
			logger.info("merchantCode="+merchantCode);
			logger.info("agent="+agent);
			if(payMoney==null || "".equals(payMoney)){
				model.addAttribute("errorFlag", "1");
				model.addAttribute("errorMsg", "支付金额payMoney为空");
				return page;
			}
			if(memberCode==null || "".equals(memberCode)){
				model.addAttribute("errorFlag", "1");
				model.addAttribute("errorMsg", "商户号memberCode为空");
				return page;
			}
			if(callbackUrl==null || "".equals(callbackUrl)){
				model.addAttribute("errorFlag", "1");
				model.addAttribute("errorMsg", "回调地址callbackUrl为空");
				return page;
			}
			if(orderNum==null || "".equals(orderNum)){
				model.addAttribute("errorFlag", "1");
				model.addAttribute("errorMsg", "商户订单号orderNum为空");
				return page;
			}
			
			if(sceneInfo==null || "".equals(sceneInfo)){
				model.addAttribute("errorFlag", "1");
				model.addAttribute("errorMsg", "场景信息sceneInfo为空");
				return page;
			}
			if(ip==null || "".equals(ip)){
				model.addAttribute("errorFlag", "1");
				model.addAttribute("errorMsg", "ip为空");
				return page;
			}
			if(signStr==null || "".equals(signStr)){
				model.addAttribute("errorFlag", "1");
				model.addAttribute("errorMsg", "签名signStr为空");
				return page;
			}
			if(payType==null || "".equals(payType)){
				model.addAttribute("errorFlag", "1");
				model.addAttribute("errorMsg", "支付方式payType为空");
				return page;
			}
			if(routeCode==null || "".equals(routeCode)){
				model.addAttribute("errorFlag", "1");
				model.addAttribute("errorMsg", "通道routeCode为空");
				return page;
			}
			if(merchantCode==null || "".equals(merchantCode)){
				model.addAttribute("errorFlag", "1");
				model.addAttribute("errorMsg", "子商户编号merchantCode为空");
				return page;
			}
			JSONObject reqData = new JSONObject();
			reqData.put("memberCode", memberCode);
			reqData.put("orderNum", orderNum);
			reqData.put("payMoney", payMoney);
			reqData.put("sceneInfo", sceneInfo);
			reqData.put("ip", ip);
			reqData.put("callbackUrl", callbackUrl);
			reqData.put("signStr", signStr);
			reqData.put("payType", payType);
			reqData.put("routeCode", routeCode);
			reqData.put("merchantCode", merchantCode);
			reqData.put("userAgent", agent);
			
			JSONObject responseJson = JSONObject.fromObject(
					HttpUtil.sendPostRequest(SysConfig.pospService + "/api/debitNote/testWxH5Pay",
							CommonUtil.createSecurityRequstData(reqData)));
			if ("0000".equals(responseJson.getString("returnCode"))) {
				String payUrl = responseJson.getString("payUrl");
				request.setAttribute("payUrl", payUrl);
				model.addAttribute("payUrl", payUrl);
				page = "payment/wxH5Submit";
				logger.info("调服务返回payUrl="+payUrl);
			}else{
			    request.setAttribute("errorMsg", responseJson.getString("returnMsg"));
				model.addAttribute("errorMsg", responseJson.getString("returnMsg"));
			}
		}catch (Exception ex) {
			ex.printStackTrace();
		}
		return page;
		
	}
	
	@RequestMapping("/payment/toUrl")
	public String toUrl(Model model,HttpServletRequest request){
		String page = "payment/fail";
		try { // 获取页面请求信息
			String ticket = request.getParameter("ticket");
			logger.info("进入请求toUrl,ticket="+ticket);
			
			if(ticket==null || "".equals(ticket)){
				model.addAttribute("errorMsg", "ticket为空");
				return page;
			}
			
			JSONObject reqData = new JSONObject();
			reqData.put("ticket", ticket);
			
			JSONObject responseJson = JSONObject.fromObject(
					HttpUtil.sendPostRequest(SysConfig.pospService + "/api/cashierDesk/getPayUrl",
							CommonUtil.createSecurityRequstData(reqData)));
			if ("0000".equals(responseJson.getString("returnCode"))) {
				String payUrl = responseJson.getString("payUrl");
				page = "payment/wxH5Submit";
				
				model.addAttribute("payUrl", payUrl);
				
				logger.info("调服务返回payUrl="+payUrl);
			}else{
			    model.addAttribute("errorMsg", responseJson.getString("returnMsg"));
			}
		}catch (Exception ex) {
			ex.printStackTrace();
		}
		return page;
		
	}
	

}
