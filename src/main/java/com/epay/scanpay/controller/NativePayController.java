package com.epay.scanpay.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
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
import org.springframework.web.bind.annotation.ResponseBody;

import com.epay.scanpay.common.constant.DataDicConstant;
import com.epay.scanpay.common.constant.SysConfig;
import com.epay.scanpay.common.constant.WxConfig;
import com.epay.scanpay.common.excep.ArgException;
import com.epay.scanpay.common.utils.CommonUtil;
import com.epay.scanpay.common.utils.EpaySignUtil;
import com.epay.scanpay.common.utils.HttpUtil;
import com.epay.scanpay.common.utils.IpUtils;
import com.epay.scanpay.common.utils.Md5Utils;





@Controller
public class NativePayController {
	private static Logger logger = LoggerFactory.getLogger(DebitNoteController.class);
	
	@RequestMapping("/payment/nativeIndex")
	public String index(Model model,HttpServletRequest request){
		String orderNum = "N"+new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		String ip = "";
		String page = "";
		try {
			ip = IpUtils.getIpAddress(request);
			page = "payment/nativePay";
			System.out.println("ip===="+ip);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.addAttribute("orderNum", orderNum);
		model.addAttribute("ip", ip);
		
		return page;
		
	}
	
	@RequestMapping("/payment/nativeConfirm")
	public String h5Confirm(Model model,HttpServletRequest request){
		
		String payType = request.getParameter("payType");
		String memberCode = request.getParameter("memberCode");
		String callbackUrl = request.getParameter("callbackUrl");
		String orderNum = request.getParameter("orderNum");
		String payMoney = request.getParameter("payMoney");
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
		params.put("payType", payType );
		params.put("ip",  ip);
		
		String srcStr = orderedKey(params);
 		System.out.println("srcStr==="+srcStr);
		String signstr = EpaySignUtil.sign(privateKey, srcStr);
		
		model.addAttribute("orderNum", orderNum);
		model.addAttribute("memberCode", memberCode);
		model.addAttribute("callbackUrl", callbackUrl);
		model.addAttribute("payMoney", payMoney);
		model.addAttribute("payType", payType);
		model.addAttribute("ip", ip);
		model.addAttribute("signStr", signstr);
		
		return "payment/nativeConfirm";
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
	
	@RequestMapping("/payment/toNative")
	public String toWxH5(Model model,HttpServletRequest request){
		String page = "payment/fail";
		
		try { // 获取页面请求信息
			
			String memberCode = request.getParameter("memberCode");
			String callbackUrl = request.getParameter("callbackUrl");
			String signStr = request.getParameter("signStr");
			String orderNum = request.getParameter("orderNum");
			String payMoney = request.getParameter("payMoney");
			String payType = request.getParameter("payType");
			String ip = request.getParameter("ip");
			String agent = request.getHeader("user-agent");
			logger.info("进入请求toNative");
			logger.info("memberCode="+memberCode);
			logger.info("callbackUrl="+callbackUrl);
			logger.info("signStr="+signStr);
			logger.info("orderNum="+orderNum);
			logger.info("payMoney="+payMoney);
			logger.info("payType="+payType);
			logger.info("ip="+ip);
			logger.info("agent="+agent);
		   
			if(payMoney==null || "".equals(payMoney)){
				model.addAttribute("errorMsg", "支付金额payMoney为空");
				return page;
			}
			if(payType==null || "".equals(payType)){
				model.addAttribute("errorMsg", "支付方式payType为空");
				return page;
			}
			if(memberCode==null || "".equals(memberCode)){
				model.addAttribute("errorMsg", "商户号memberCode为空");
				return page;
			}
			if(callbackUrl==null || "".equals(callbackUrl)){
				model.addAttribute("errorMsg", "回调地址callbackUrl为空");
				return page;
			}
			if(orderNum==null || "".equals(orderNum)){
				model.addAttribute("errorMsg", "商户订单号orderNum为空");
				return page;
			}
			
			if(ip==null || "".equals(ip)){
				model.addAttribute("errorMsg", "ip为空");
				return page;
			}
			if(signStr==null || "".equals(signStr)){
				model.addAttribute("errorMsg", "签名signStr为空");
				return page;
			}
			String ua = agent.toLowerCase();
			if (!(ua.indexOf("micromessenger") > 0 )) {
				model.addAttribute("errorMsg", "请在微信浏览器中进行支付");
				return page; 
			}
			
			String redirectUrl = SysConfig.payService + "/weixinPay/debitNoteAuthCallBack";
			try {
				redirectUrl = URLEncoder.encode(redirectUrl, "utf-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			
			request.getSession().setAttribute("memberCode", memberCode);
			request.getSession().setAttribute("orderNum", orderNum);
			request.getSession().setAttribute("payMoney", payMoney);
			request.getSession().setAttribute("payType", payType);
			request.getSession().setAttribute("ip", ip);
			request.getSession().setAttribute("callbackUrl", callbackUrl);
			request.getSession().setAttribute("signStr", signStr);
			request.getSession().setAttribute("userAgent", agent);
			
			String routeCode = DataDicConstant.ESK_ROUTE_CODE;
			String state = "";
			if(!DataDicConstant.ESK_ROUTE_CODE.equals(routeCode)){
				return "redirect:https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + WxConfig.appid
						+ "&redirect_uri=" + redirectUrl + "&response_type=code&scope=snsapi_base&state=" + state
						+ "#wechat_redirect";
			}else{
				redirectUrl = SysConfig.payService + "/weixinPay/eskOpenIdNew";
				return "redirect:http://www.tianlepay.com/aggregate/wx/openid.kb?agentId=2017101714241&uri="+redirectUrl+"&data=123456789";
			}
		}catch (Exception ex) {
			ex.printStackTrace();
		}
		return page;
		
	}
	
	
	@RequestMapping("/weixinPay/eskOpenIdNew")
	public String eskOpenId(Model model, HttpServletRequest request) {
		String page = "payment/fail";
		String openid = request.getParameter("openid");
		if (openid == null || "".equals(openid)) {
			model.addAttribute("errorMsg", "获取openid失败");
			return page;
		}
		request.getSession().setAttribute("openid", openid);// 将openId保存到session
		
		String memberCode = (String)request.getSession().getAttribute("memberCode");
		String callbackUrl = (String)request.getSession().getAttribute("callbackUrl");
		String signStr = (String)request.getSession().getAttribute("signStr");
		String orderNum = (String)request.getSession().getAttribute("orderNum");
		String payMoney = (String)request.getSession().getAttribute("payMoney");
		String payType = (String)request.getSession().getAttribute("payType");
		String ip = (String)request.getSession().getAttribute("ip");
		String agent = (String)request.getSession().getAttribute("user-agent");
	   
		if(payMoney==null || "".equals(payMoney)){
			model.addAttribute("errorMsg", "支付金额为空");
			return page;
		}
		if(payType==null || "".equals(payType)){
			model.addAttribute("errorMsg", "支付方式为空");
			return page;
		}
		if(memberCode==null || "".equals(memberCode)){
			model.addAttribute("errorMsg", "商户号为空");
			return page;
		}
		if(callbackUrl==null || "".equals(callbackUrl)){
			model.addAttribute("errorMsg", "回调地址为空");
			return page;
		}
		if(orderNum==null || "".equals(orderNum)){
			model.addAttribute("errorMsg", "商户订单号为空");
			return page;
		}
		
		if(ip==null || "".equals(ip)){
			model.addAttribute("errorMsg", "ip为空");
			return page;
		}
		if(signStr==null || "".equals(signStr)){
			model.addAttribute("errorMsg", "签名为空");
			return page;
		}
		
		System.out.println("openid======"+openid);
		model.addAttribute("orderNum", orderNum);
		model.addAttribute("payMoney", payMoney);
		model.addAttribute("userAgentType", "micromessenger");	
		return "payment/nativeSubmit";

	}
	
	@ResponseBody
	@RequestMapping("/debitNote/nativePay")
	public JSONObject person(Model model, HttpServletRequest request) {
		
		
		String memberCode = (String)request.getSession().getAttribute("memberCode");
		String callbackUrl = (String)request.getSession().getAttribute("callbackUrl");
		String signStr = (String)request.getSession().getAttribute("signStr");
		String orderNum = (String)request.getSession().getAttribute("orderNum");
		String payMoney = (String)request.getSession().getAttribute("payMoney");
		String payType = (String)request.getSession().getAttribute("payType");
		String ip = (String)request.getSession().getAttribute("ip");
		String agent = (String)request.getSession().getAttribute("user-agent");
		String userId = (String)request.getSession().getAttribute("openid");
		
		JSONObject result = new JSONObject();
		try {
			if ("".equals(payMoney)) {
				throw new ArgException("请输入正确的金额");
			}

			
			JSONObject reqData = new JSONObject();
			reqData.put("userId", userId);
			reqData.put("memberCode", memberCode);
			reqData.put("orderNum", orderNum);
			reqData.put("payMoney", payMoney);
			reqData.put("payType", payType);
			reqData.put("ip", ip);
			reqData.put("callbackUrl", callbackUrl);
			reqData.put("signStr", signStr);
			reqData.put("userAgent", agent);
			
			
			result = JSONObject.fromObject(HttpUtil.sendPostRequest(SysConfig.pospService + "/api/pay/nativePay",
					CommonUtil.createSecurityRequstData(reqData)));
		} catch (ArgException e) {
			result.put("returnCode", "4004");
			result.put("returnMsg", e.getMessage());
			logger.info(e.getMessage());
			return result;
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			result.put("returnCode", "4004");
			result.put("returnMsg", "请求失败");
			return result;
		}
		return result;
	}

}
