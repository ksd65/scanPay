package com.epay.scanpay.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.epay.scanpay.common.constant.DataDicConstant;
import com.epay.scanpay.common.constant.SysConfig;
import com.epay.scanpay.common.utils.CommonUtil;
import com.epay.scanpay.common.utils.DateUtil;
import com.epay.scanpay.common.utils.EpaySignUtil;
import com.epay.scanpay.common.utils.HttpClient4Util;
import com.epay.scanpay.common.utils.HttpUtil;
import com.epay.scanpay.common.utils.IpUtils;
import com.epay.scanpay.common.utils.StringUtil;

@Controller
public class CashierDeskController {
	private static Logger logger = LoggerFactory.getLogger(CashierDeskController.class);
	
	
	@RequestMapping("/cashierDesk/qrCodeIndex")
	public String qrCodeIndex(Model model,HttpServletRequest request){
		String orderNum = "C"+new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		String ip = "";
		try {
			ip = IpUtils.getIpAddress(request);
			System.out.println("ip===="+ip);
		} catch (IOException e) {
			e.printStackTrace();
		}
		model.addAttribute("orderNum", orderNum);
		model.addAttribute("ip", ip);
		return "cashierDesk/qrCodePay";
	}

	@RequestMapping("/cashierDesk/qrCodeConfirm")
	public String qrCodeConfirm(Model model,HttpServletRequest request){
		String memberCode = request.getParameter("memberCode");//商家编码
		String orderNum = request.getParameter("orderNum");//商家订单号
		String payMoney = request.getParameter("payMoney");//支付金额
		String payType = request.getParameter("payType");
		String callbackUrl = request.getParameter("callbackUrl");//回调地址
		String ip = request.getParameter("ip");
		//待签名字符串
		Map<String,String> params = new HashMap<String,String>();
		
		String privateKey = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCIxkLM+QBKTqw9BsCSjiLw5pO186HwgMOZIXUFDVnRsYyPeiFsnPgaNQmT7L5Ur0jHtKc0yUdk5jd8BG06N40474MiBzNNNJJa4mkPQfUlsD0T0NMLesjFthKqyQxS1cMqpz8b372pwsWb16BVM/9i5ak9JgqguAtEkxR2lSNd0sn/GEqH9hdw6RvMH/Y/ePztdA3bANJL64g0hrKKBW0YcJvXJpKF8vIwuNuamOoKOzlEIPZC05331j4c8xw/EUiBbS9sELAT+MLNlYXHIWtpDDbJTNZGKXDskJpfae7ifW4TU6ZlraiW4JGabl1d5DH2t4k5NnFqiquPZWPhZ8u9AgMBAAECggEAdGdHsuq4NIWAUO/ONOyDEEMss04GJIlx7oFq9kHGj5Br8DAhAi8VeDhrTlnOIoSLjGtTYrlq6ZSE5CdgTou4xRwSnoNCRhLX/EF06GdaHBlB4ft3oe19scajXHZ+5oDG+SYdr7tbz37Uby20Zs86KxEKV+BoayA3dsU2RTXoQ5BCtMeXaIUif3JjHGy1xHkR1MPN2vXvwMqU7Femk/Qr9s8W+9IgVp5ah28Kx5qkR63TkEONyYTbI+U4El/bVnZmcaHL+/KJ9wo8+/7hHrl+mWV0T3iKa4tJWed9V+IUKrLeUpn9DOIv9WNlDFjJqBckC/GBhujG7uRKWfminGRAgQKBgQDJnQv9HAgUnGsYTayY1iW2CwgFZJn1RgaTCjo1Oc6tDTFXdP4uIFfxEfnLhGuRuItTKHW1hf1Hp4n+wiZO5q1HjjC3bLBUy1Lw+HvaJLNHRkCye58cg2Jlu81girzciazLiHqfEaZxFsUq8WsqzZIBRr0gPDDJMrSOBfkf+hWckwKBgQCtq5WSq/t5Cr1N5AOoYoBDvJOHqQ4/6Ue0JF270/u1H/qdMQ9w/OQRixhHNP7g5oiPBWKPQTs3uU9/SHpX5CHsyqlQDvOkrSnESp4pz0BUOT971er/8o79NrUE9ZQMRDhu0WHXtbPOq+7siUw9SA5HcEh8Hd/Y3xLOFhvw90x4bwKBgGBGFRZ9j0JAW0eUt8mX4RQn+mGQ44/jK3qFlLwb6ZxrQ1eO712ZZkUgn1bW2gMQy78e/+55mDPiRhwYG/DraG1V8d91EFK9cNLO5V2Kzu1HF9fi/lzARHluD6l9NqhdOd1LQ7q30/IGvIpAFDuxRHpFjERbWbSJ+Pwk0Ay8ABvvAoGAah4XFfkqfqqWQ3rY1VHiyAD5MIKXJ2w2mRdDgxqjiegRbW1l3wdXoHSakCAMwYV72dBTie807Pa5Ya/6uau3IwYucLHCJFR+2ecyP5/Y0d3tMZDjuCMRRh3gfDhGjzw8M1KTc4geZ2Fda4D1adiWiQZN9DEY715XEkAmMJYbTtcCgYEAn9BcQcpAsbV2fpmO0VU8CMFoTnbfYpmHR9N6A45r72j1nQmJxfZbvL7JiIK7jhRUuQERC+jK08FPKbFDoE5a7UxNiKDE/ZtuEwPehrf8yVenxmAWMh4llc1HoWQfjCY8BJuAm3K4jHtswB0+PQfTMG7PVisoMfdVEJ4gq8BLAv0=";
		if("9010000002".equals(memberCode)){
			privateKey = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCfKi2ZOWOPYsDPj6RT9kV2s5RCOezBoEnAMfVoEbe5eQB5rJhTwRaN2rGHPMlv1baB6dB4yJ49Eidvq9IX4D7lhnbPwvvCi7EjszJgyrID8miVu9wBpPRUdjS+YsrWyGrVK0vMxl79TlQWK22io/yZp9aWTCTgSM3qEyQaMyk6bEANYIUaSlpbiDJ2mvaU6JpSDZJLHKpNvPVNDLk52fpuArOuvHo2Nn0NOrnv8bjAYk8CMxNBuE6xicHl3ycDU8PQ3MKEhWkyNAm/vVH8v37kYcDpx6ftUM7ddAq+SOE9BPvjoxsBNYEKztvZxBdaZHYZUEGtjyBeulzr0L1KZJDtAgMBAAECggEACDG1s0O/GmytHIJ6pU/yd4/7PAWbaMSFx31K8xambMgL/Dekh/tS2+68YQgCHt3TzZBqCS3a5639lcQ0xsHmuw5XI48YQwXKEtpw54bH72gVdk/7naIOaiLDyGFxq+kZhuv5tQspbMURkyqdNFhY8tgvNgGpjFpzL2/Y1fh4UOeYzGFTKMCzpxfHwCdpfFoW4QNGbVvC+IKpuLyKf58hBvSaR8AaFCQ0XTBVl6QbB/8ryWL2xbdg+/+cB4TXwD/Ct3xcLIEF5ow7/1IVyqlSzUelh9SaKq2113T1HGFTr7rELlXplU5J1icV6JEdoq+RplW3SQ9dMesV9l8dhAA3QQKBgQDpKSQPq7nAVCEcEzohiBvZMibW/EBxWBBIXgAc1G/DasfDuTxyW//4gPUeozW4ZMzwnlc31CMkusd806Iecio0+LrKB3V9yFW4fbzcLYdY4tN3oeQ9MoX9nkFM/ikKl2B1HcLmBWZqgg3kdf1hIa/E2YdK/x+NofaSz4lQMjjveQKBgQCuwXnzrk9RqQ7bg8Ol38NAqV+3xQ7hrvzH/Zjifd/sMbGC0y0JgM++3Y5jALsxD7GcQt9V1m31qGlXzW4aitNxdlMpvcSfHdUE9kXgbp9g1rqJDg/hJe+nH7nBI6NmRdrb+Ns0lnNYXBHYAQ/W3M521U46TWtODLcNOXwXbGlMFQKBgQCSkBXm899zkm6tozhrU4+N3ASmJzKrDNxPYSdY+AC5KiogUhQ5HrOslgN/GsDuBA7/Qck5gtQEhpRXVwEVelYlriRcUov8YS3hJsjM7qGhshOTo+RAw72OSyhpKWrLCZTMicS1qrdSRCZPcguwPuiqKMLu1agT87d3WZXLH4bCoQKBgB8lHDbxufEz0BIPSa8mUgYUKZr249AU/7gk2jqDdIUD1j8ao8wtyNibY+UBHFuCEIVo5aTGspI1kZC0bAsO8uAl1mx6BbDWAEECIzH8hSsdGeGTQAFAYZXHcbOaRmTTzk2l7GtS5Pu6bPOyPMBuWd2T5n09jwI6AeW5eQQzrhCBAoGBAKR9CR4SYsH+lcONvAfRC7fQMrS4FwwUbHYUuDEDmDn07oelaJYh7UNbCbNG6x0cHYkJleQlDHyl7hSwHl5pBXzJooSKWCj9Hn3DAfqUouFNZf1kZruoOWBosGt/pKjDzNwVhAp+gCeD9veGZxrvfuVdUgS5yv0fgqQg2qsGdeN3";
		}
		params.put("orderNum", orderNum); 
		params.put("memberCode", memberCode);  
		params.put("callbackUrl",  callbackUrl);
		params.put("payMoney", payMoney );
		params.put("payType", payType );
		params.put("ip", ip );
		
		String srcStr = orderedKey(params);
 		System.out.println("srcStr==="+srcStr);
		String signstr = EpaySignUtil.sign(privateKey, srcStr);
		
		model.addAttribute("memberCode", memberCode);
		model.addAttribute("orderNum", orderNum);
		model.addAttribute("payMoney", payMoney);
		model.addAttribute("payType", payType);
		model.addAttribute("callbackUrl", callbackUrl);
		model.addAttribute("signStr", signstr);
		model.addAttribute("ip", ip);
		
		return "cashierDesk/qrCodeConfirm";
		
	}
	
	@RequestMapping("/cashierDesk/index")
	public String index(Model model,HttpServletRequest request){
		String memberCode = request.getParameter("memberCode");
		String orderNum = request.getParameter("orderNum");
		String payMoney = request.getParameter("payMoney");
		String callbackUrl = request.getParameter("callbackUrl");
		String isMoneyFilled = request.getParameter("isMoneyFilled");
		String platformType = request.getParameter("platformType");
		String signStr = request.getParameter("signStr"); 
		model.addAttribute("memberCode", memberCode);
		model.addAttribute("orderNum", orderNum);
		model.addAttribute("callbackUrl", callbackUrl);
		model.addAttribute("isMoneyFilled", isMoneyFilled);
		model.addAttribute("platformType", platformType);
		model.addAttribute("signStr", signStr);
		if(!"1".equals(isMoneyFilled)){ //isMoneyFilled: 1--订单金额由接口传送 0--在收银台填写
			payMoney = "";
		}
		model.addAttribute("payMoney", payMoney);
		JSONObject reqData = new JSONObject();
		reqData.put("memberCode", memberCode);
		reqData.put("payMethod", DataDicConstant.PAY_METHOD_SMZF);//扫码支付
		JSONObject responseJson = JSONObject.fromObject(
				HttpUtil.sendPostRequest(SysConfig.pospService + "/api/memberInfo/getMemberPayTypeByCode",
						CommonUtil.createSecurityRequstData(reqData)));
		JSONObject memberInfoData ;
		List<HashMap<String, String>> payList = new ArrayList<HashMap<String,String>>();
		if ("0000".equals(responseJson.getString("returnCode"))) {
			memberInfoData = responseJson.getJSONObject("resData");
			String payTypeStr = memberInfoData.getString("payTypeList");
			if(payTypeStr!=null &&!"".equals(payTypeStr)){
				String[] arr = payTypeStr.split("#");
				for(int i=0;i<arr.length;i++){
					HashMap<String, String> map = new HashMap<String, String>();
					map.put("payType", arr[i]);
					payList.add(map);
				}
			}
		}
		model.addAttribute("payList", payList);
		if("1".equals(platformType)){
			return "cashierDesk/indexMobile";
		}else{
			return "cashierDesk/indexPC";
		}
	}
	
	@ResponseBody
	@RequestMapping("/cashierDesk/pay")
	public JSONObject qrCodePay(Model model, HttpServletRequest request) {
		String payMoney = request.getParameter("payMoney");
		String payType = request.getParameter("payType");
		String orderNum = request.getParameter("orderNum");
		String memberCode = request.getParameter("memberCode");
		String callbackUrl = request.getParameter("callbackUrl");
		String signStr = request.getParameter("signStr");
		String ip = request.getParameter("ip");

		JSONObject reqData = new JSONObject();
		reqData.put("payMoney", payMoney);
		reqData.put("payType", payType);
		reqData.put("orderNum", orderNum);
		reqData.put("memberCode", memberCode);
		reqData.put("ip", ip);
		reqData.put("callbackUrl", callbackUrl);
		reqData.put("platformType", "1");
		reqData.put("signStr", signStr);
		JSONObject result = JSONObject.fromObject(HttpUtil.sendPostRequest(SysConfig.pospService + "/api/cashierDesk/getQrcodePay", CommonUtil.createSecurityRequstData(reqData)));

		return result;

	}
	//移动端付款码页面
	@RequestMapping("/cashierDesk/getQrCodeM")
	public String getQrCodeM(Model model,HttpServletRequest request) {
		model.addAttribute("qrCode",request.getParameter("qrCodeDetail"));
		model.addAttribute("payMoney",request.getParameter("payMoneyDetail"));
		String payType = request.getParameter("payTypeDetail");
		if("2".equals(payType)){
			return "cashierDesk/qrCodeMzfb";
		}else if("3".equals(payType)){
			return "cashierDesk/qrCodeMQq";
		}else if("5".equals(payType)){
			return "cashierDesk/qrCodeMJd";
		}
		return "cashierDesk/qrCodeMwx";
		
	}
	//pc端付款码页面
	@RequestMapping("/cashierDesk/getQrCodeP")
	public String getQrCodeP(Model model,HttpServletRequest request) {
		model.addAttribute("qrCode",request.getParameter("qrCodeDetail"));
		model.addAttribute("payMoney",request.getParameter("payMoneyDetail"));
		String payType = request.getParameter("payTypeDetail");
		if("2".equals(payType)){
			return "cashierDesk/qrCodeZFB";
		}
		return "cashierDesk/qrCodeWX";
	}
	
	//测试快捷支付页面
	@RequestMapping("/cashierDesk/testQuickPay")
	public String testQuickPay(Model model,HttpServletRequest request) {
		return "cashierDesk/testQuickPay";
	}
	
	/**
	 * 测试快捷支付回调前台页面
	 */
	@RequestMapping("/cashierDesk/testQuickPageCall")
	public String miPayBack(Model model, HttpServletRequest request, HttpServletResponse response) {

		String resultMessage = "";
		String totalAmount = "0";
		JSONObject reqData = new JSONObject();
		boolean flag = false;
		String retXml = "";
		String RESP_DESC = null;
		JSONObject req = new JSONObject();
		JSONObject result = new JSONObject();
		String res="";
		try {
			String str = getParamString(request);
			System.out.println("testQuickPageCall====="+str);
			
			String orderNum = request.getParameter("orderNum");
			String orderAmt = request.getParameter("orderAmt");
			String payType = request.getParameter("payType");
			String bgUrl = request.getParameter("bgUrl");
			String pageUrl = request.getParameter("pageUrl");// 订单提交时间
			String memberCode = request.getParameter("memberCode");
			String goodsName = request.getParameter("goodsName");
			
			String goodsDesc = request.getParameter("goodsDesc");
			String accNo = request.getParameter("accNo");
			String add1 = request.getParameter("add1");
			String add2 = request.getParameter("add2");
			String add3 = request.getParameter("add3");
			String add4 = request.getParameter("add4");
			String returnCode = request.getParameter("returnCode");
			String returnMsg = request.getParameter("returnMsg");
			/*
			if (!(RESP_DESC == null)) {
				RESP_DESC = URLDecoder.decode(RESP_DESC, "utf-8");
			}
			*/

			
			  System.out.println("orderNum="+orderNum);
			  System.out.println("orderAmt="+orderAmt);
			  System.out.println("payType="+payType);
			  System.out.println("bgUrl="+bgUrl);
			  System.out.println("pageUrl="+pageUrl);
			  System.out.println("memberCode="+memberCode);
			  System.out.println("goodsName="+goodsName);
			  System.out.println("goodsDesc="+goodsDesc);
			  System.out.println("accNo="+accNo);
			  System.out.println("add1="+add1);
			  System.out.println("add2="+add2);
			  System.out.println("add3="+add3);
			  System.out.println("add4="+add4); 
			  System.out.println("returnCode="+returnCode);
			  System.out.println("returnMsg="+returnMsg);
			
			if ("0000".equals(returnCode)) {
				resultMessage = "测试！支付成功！";
				flag = true;
			} else {
				flag = false;
				resultMessage = RESP_DESC;
			}
			model.addAttribute("totalAmount", orderAmt);
			model.addAttribute("resultMessage", resultMessage);
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			model.addAttribute("resultMessage", "支付失败");
		}
		if (flag) {
			return "debitNote/testResultSuccess";
		} else {
			return "debitNote/testResult";
		}
	}
	
	private String getParamString(HttpServletRequest request) {
		StringBuffer buffer = new StringBuffer();
		InputStream inputStream = null;
		BufferedReader bufferedReader = null;
		InputStreamReader inputStreamReader = null;
		try {
			inputStream = request.getInputStream();
			inputStreamReader = new InputStreamReader(inputStream, "utf-8");
			bufferedReader = new BufferedReader(inputStreamReader);

			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}

			inputStream = null;
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			try {
				bufferedReader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				inputStreamReader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return buffer.toString();
	}
	
	/**
	 * 合作机构接入，快捷支付
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/cashierDesk/quickPay")
	public String quickPay(Model model, HttpServletRequest request) {
		String returnStr="";
		String orderNum=request.getParameter("orderNum");
		String orderAmt=request.getParameter("orderAmt");
		String payType=request.getParameter("payType");
		String bgUrl=request.getParameter("bgUrl");
		String pageUrl=request.getParameter("pageUrl");
		String memberCode=request.getParameter("memberCode");
		String goodsName=request.getParameter("goodsName");
		String goodsDesc=request.getParameter("goodsDesc");
		String accNo=request.getParameter("accNo");
		String add1=request.getParameter("add1");
		String add2=request.getParameter("add2");
		String add3=request.getParameter("add3");
		String add4=request.getParameter("add4");
		String signStr=request.getParameter("signStr");
		JSONObject result=new JSONObject();
		JSONObject reqData=new JSONObject(); 
		JSONObject reqData1=new JSONObject(); 
		String sysdt=DateUtil.getTime();
		model.addAttribute("orderNum",orderNum);
		model.addAttribute("orderAmt",orderAmt);
		model.addAttribute("payType",payType);
		model.addAttribute("bgUrl",bgUrl);
		model.addAttribute("pageUrl",pageUrl);
		model.addAttribute("memberCode",memberCode);
		model.addAttribute("goodsName",goodsName);
		model.addAttribute("goodsDesc",goodsDesc);
		model.addAttribute("accNo",accNo);
		model.addAttribute("add1",add1);
		model.addAttribute("add2",add2);
		model.addAttribute("add3",add3);
		model.addAttribute("add4",add4); 
		returnStr="debitNote/scanFaile";
		if(sysdt.compareTo("21:59:59")>0 || sysdt.compareTo("08:00:00")<0){
			model.addAttribute("returnCode","0007");
			model.addAttribute("returnMsg","当前非交易时间段，交易时间段为9:00—22:00"); 
			return returnStr;
		}
		if(StringUtils.isNotBlank(signStr)){
			reqData.put("signStr", signStr);
		}else{
			model.addAttribute("returnCode", "0007");
			model.addAttribute("returnMsg", "缺少signStr参数");
			return returnStr;
		} 
		if(StringUtils.isNotBlank(orderNum)){
			reqData.put("orderNum", orderNum);
		}else{
			model.addAttribute("returnCode", "0007");
			model.addAttribute("returnMsg", "缺少orderNum参数");
			return returnStr;
		} 
		if(StringUtils.isNotBlank(orderAmt)){
			reqData.put("orderAmt", orderAmt);
		}else{
			model.addAttribute("returnCode", "0007");
			model.addAttribute("returnMsg", "缺少orderAmt参数");
			return returnStr;
		} 
		
		if(StringUtils.isNotBlank(payType)){
			reqData.put("payType", payType);
		}else{
			model.addAttribute("returnCode", "0007");
			model.addAttribute("returnMsg", "缺少payType参数");
			return returnStr;
		} 
		if(StringUtils.isNotBlank(bgUrl)){
			reqData.put("bgUrl", bgUrl);
		}else{
			model.addAttribute("returnCode", "0007");
			model.addAttribute("returnMsg", "缺少bgUrl参数");
			return returnStr;
		} 
		
		
		if(StringUtils.isNotBlank(pageUrl)){
			reqData.put("pageUrl", pageUrl);
		}else{
			model.addAttribute("returnCode", "0007");
			model.addAttribute("returnMsg", "缺少pageUrl参数");
			return returnStr;
		} 
		
		
		if(StringUtils.isNotBlank(memberCode)){
			reqData.put("memberCode", memberCode);
		}else{
			model.addAttribute("returnCode", "0007");
			model.addAttribute("returnMsg", "缺少memberCode参数");
			return returnStr;
		} 
		
		if(StringUtils.isNotBlank(accNo)){
			reqData.put("accNo", accNo);
		}else{
			model.addAttribute("returnCode", "0007");
			model.addAttribute("returnMsg", "缺少accNo参数");
			return returnStr;
		} 
		reqData.put("add1", add1);
		reqData.put("add2", add2);
		reqData.put("add3", add3);
		reqData.put("add4", add4);
		reqData.put("goodsName", goodsName);
		reqData.put("goodsDesc", goodsDesc);
		if(!(payType.equals("1") || payType.equals("0"))){
			model.addAttribute("returnCode", "0007");
			model.addAttribute("returnMsg", "支付方式payType错误");
			return returnStr;
		}
		if(!StringUtil.isNumeric(orderAmt)){
			model.addAttribute("returnCode", "0007");
			model.addAttribute("returnMsg", "订单金额格式错误");
			return returnStr;
		}
		float d=Float.parseFloat(orderAmt);
		if(!((payType.equals("1") && d>=10)||(payType.equals("0") && d>=500))){
			model.addAttribute("returnCode", "0007");
			model.addAttribute("returnMsg", "订单金额低于最低限制");
			return returnStr;
		}
		reqData1.put("mlTradeDetail", reqData);
		  result = JSONObject.fromObject(HttpUtil.sendPostRequest(SysConfig.pospService + "/api/cashierDesk/quickPay", CommonUtil.createSecurityRequstData(reqData1)));
	   if(!result.opt("returnCode").equals("0000"))  {
		  model.addAttribute("returnCode", result.opt("returnCode"));
		  model.addAttribute("returnMsg", result.opt("returnMsg"));
		  return returnStr;
		}else{
			//result.get("resData")
			model.addAttribute("resData",result.get("resData"));
			return "debitNote/quickPay";
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
	
	@RequestMapping("/payment/qrCodeIndex")
	public String qrCodeIndexPC(Model model,HttpServletRequest request){
		String orderNum = "C"+new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		String ip = "";
		try {
			ip = IpUtils.getIpAddress(request);
			System.out.println("ip===="+ip);
		} catch (IOException e) {
			e.printStackTrace();
		}
		model.addAttribute("orderNum", orderNum);
		model.addAttribute("ip", ip);
		return "payment/qrCodePay";
	}
	
	@RequestMapping("/payment/qrCodeConfirm")
	public String qrCodeConfirmPC(Model model,HttpServletRequest request){
		String memberCode = request.getParameter("memberCode");//商家编码
		String orderNum = request.getParameter("orderNum");//商家订单号
		String payMoney = request.getParameter("payMoney");//支付金额
		String payType = request.getParameter("payType");
		String callbackUrl = request.getParameter("callbackUrl");//回调地址
		String ip = request.getParameter("ip");
		//待签名字符串
		Map<String,String> params = new HashMap<String,String>();
		
		String privateKey = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCIxkLM+QBKTqw9BsCSjiLw5pO186HwgMOZIXUFDVnRsYyPeiFsnPgaNQmT7L5Ur0jHtKc0yUdk5jd8BG06N40474MiBzNNNJJa4mkPQfUlsD0T0NMLesjFthKqyQxS1cMqpz8b372pwsWb16BVM/9i5ak9JgqguAtEkxR2lSNd0sn/GEqH9hdw6RvMH/Y/ePztdA3bANJL64g0hrKKBW0YcJvXJpKF8vIwuNuamOoKOzlEIPZC05331j4c8xw/EUiBbS9sELAT+MLNlYXHIWtpDDbJTNZGKXDskJpfae7ifW4TU6ZlraiW4JGabl1d5DH2t4k5NnFqiquPZWPhZ8u9AgMBAAECggEAdGdHsuq4NIWAUO/ONOyDEEMss04GJIlx7oFq9kHGj5Br8DAhAi8VeDhrTlnOIoSLjGtTYrlq6ZSE5CdgTou4xRwSnoNCRhLX/EF06GdaHBlB4ft3oe19scajXHZ+5oDG+SYdr7tbz37Uby20Zs86KxEKV+BoayA3dsU2RTXoQ5BCtMeXaIUif3JjHGy1xHkR1MPN2vXvwMqU7Femk/Qr9s8W+9IgVp5ah28Kx5qkR63TkEONyYTbI+U4El/bVnZmcaHL+/KJ9wo8+/7hHrl+mWV0T3iKa4tJWed9V+IUKrLeUpn9DOIv9WNlDFjJqBckC/GBhujG7uRKWfminGRAgQKBgQDJnQv9HAgUnGsYTayY1iW2CwgFZJn1RgaTCjo1Oc6tDTFXdP4uIFfxEfnLhGuRuItTKHW1hf1Hp4n+wiZO5q1HjjC3bLBUy1Lw+HvaJLNHRkCye58cg2Jlu81girzciazLiHqfEaZxFsUq8WsqzZIBRr0gPDDJMrSOBfkf+hWckwKBgQCtq5WSq/t5Cr1N5AOoYoBDvJOHqQ4/6Ue0JF270/u1H/qdMQ9w/OQRixhHNP7g5oiPBWKPQTs3uU9/SHpX5CHsyqlQDvOkrSnESp4pz0BUOT971er/8o79NrUE9ZQMRDhu0WHXtbPOq+7siUw9SA5HcEh8Hd/Y3xLOFhvw90x4bwKBgGBGFRZ9j0JAW0eUt8mX4RQn+mGQ44/jK3qFlLwb6ZxrQ1eO712ZZkUgn1bW2gMQy78e/+55mDPiRhwYG/DraG1V8d91EFK9cNLO5V2Kzu1HF9fi/lzARHluD6l9NqhdOd1LQ7q30/IGvIpAFDuxRHpFjERbWbSJ+Pwk0Ay8ABvvAoGAah4XFfkqfqqWQ3rY1VHiyAD5MIKXJ2w2mRdDgxqjiegRbW1l3wdXoHSakCAMwYV72dBTie807Pa5Ya/6uau3IwYucLHCJFR+2ecyP5/Y0d3tMZDjuCMRRh3gfDhGjzw8M1KTc4geZ2Fda4D1adiWiQZN9DEY715XEkAmMJYbTtcCgYEAn9BcQcpAsbV2fpmO0VU8CMFoTnbfYpmHR9N6A45r72j1nQmJxfZbvL7JiIK7jhRUuQERC+jK08FPKbFDoE5a7UxNiKDE/ZtuEwPehrf8yVenxmAWMh4llc1HoWQfjCY8BJuAm3K4jHtswB0+PQfTMG7PVisoMfdVEJ4gq8BLAv0=";
		if("9010000002".equals(memberCode)){
			privateKey = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCfKi2ZOWOPYsDPj6RT9kV2s5RCOezBoEnAMfVoEbe5eQB5rJhTwRaN2rGHPMlv1baB6dB4yJ49Eidvq9IX4D7lhnbPwvvCi7EjszJgyrID8miVu9wBpPRUdjS+YsrWyGrVK0vMxl79TlQWK22io/yZp9aWTCTgSM3qEyQaMyk6bEANYIUaSlpbiDJ2mvaU6JpSDZJLHKpNvPVNDLk52fpuArOuvHo2Nn0NOrnv8bjAYk8CMxNBuE6xicHl3ycDU8PQ3MKEhWkyNAm/vVH8v37kYcDpx6ftUM7ddAq+SOE9BPvjoxsBNYEKztvZxBdaZHYZUEGtjyBeulzr0L1KZJDtAgMBAAECggEACDG1s0O/GmytHIJ6pU/yd4/7PAWbaMSFx31K8xambMgL/Dekh/tS2+68YQgCHt3TzZBqCS3a5639lcQ0xsHmuw5XI48YQwXKEtpw54bH72gVdk/7naIOaiLDyGFxq+kZhuv5tQspbMURkyqdNFhY8tgvNgGpjFpzL2/Y1fh4UOeYzGFTKMCzpxfHwCdpfFoW4QNGbVvC+IKpuLyKf58hBvSaR8AaFCQ0XTBVl6QbB/8ryWL2xbdg+/+cB4TXwD/Ct3xcLIEF5ow7/1IVyqlSzUelh9SaKq2113T1HGFTr7rELlXplU5J1icV6JEdoq+RplW3SQ9dMesV9l8dhAA3QQKBgQDpKSQPq7nAVCEcEzohiBvZMibW/EBxWBBIXgAc1G/DasfDuTxyW//4gPUeozW4ZMzwnlc31CMkusd806Iecio0+LrKB3V9yFW4fbzcLYdY4tN3oeQ9MoX9nkFM/ikKl2B1HcLmBWZqgg3kdf1hIa/E2YdK/x+NofaSz4lQMjjveQKBgQCuwXnzrk9RqQ7bg8Ol38NAqV+3xQ7hrvzH/Zjifd/sMbGC0y0JgM++3Y5jALsxD7GcQt9V1m31qGlXzW4aitNxdlMpvcSfHdUE9kXgbp9g1rqJDg/hJe+nH7nBI6NmRdrb+Ns0lnNYXBHYAQ/W3M521U46TWtODLcNOXwXbGlMFQKBgQCSkBXm899zkm6tozhrU4+N3ASmJzKrDNxPYSdY+AC5KiogUhQ5HrOslgN/GsDuBA7/Qck5gtQEhpRXVwEVelYlriRcUov8YS3hJsjM7qGhshOTo+RAw72OSyhpKWrLCZTMicS1qrdSRCZPcguwPuiqKMLu1agT87d3WZXLH4bCoQKBgB8lHDbxufEz0BIPSa8mUgYUKZr249AU/7gk2jqDdIUD1j8ao8wtyNibY+UBHFuCEIVo5aTGspI1kZC0bAsO8uAl1mx6BbDWAEECIzH8hSsdGeGTQAFAYZXHcbOaRmTTzk2l7GtS5Pu6bPOyPMBuWd2T5n09jwI6AeW5eQQzrhCBAoGBAKR9CR4SYsH+lcONvAfRC7fQMrS4FwwUbHYUuDEDmDn07oelaJYh7UNbCbNG6x0cHYkJleQlDHyl7hSwHl5pBXzJooSKWCj9Hn3DAfqUouFNZf1kZruoOWBosGt/pKjDzNwVhAp+gCeD9veGZxrvfuVdUgS5yv0fgqQg2qsGdeN3";
		}
		params.put("orderNum", orderNum); 
		params.put("memberCode", memberCode);  
		params.put("callbackUrl",  callbackUrl);
		params.put("payMoney", payMoney );
		params.put("payType", payType );
		params.put("ip", ip );
		
		String srcStr = orderedKey(params);
 		System.out.println("srcStr==="+srcStr);
		String signstr = EpaySignUtil.sign(privateKey, srcStr);
		
		model.addAttribute("memberCode", memberCode);
		model.addAttribute("orderNum", orderNum);
		model.addAttribute("payMoney", payMoney);
		model.addAttribute("payType", payType);
		model.addAttribute("callbackUrl", callbackUrl);
		model.addAttribute("signStr", signstr);
		model.addAttribute("ip", ip);
		
		return "payment/qrCodePayConfirm";
		
	}
	
	
	
	
	@RequestMapping("/payment/toQrCodePay")
	public String quickPaySms(Model model,HttpServletRequest request){
		String page = "payment/fail";
		
		try { // 获取页面请求信息
			Map<String,String> param = new HashMap<String, String>();
			String memberCode = request.getParameter("memberCode");
			String callbackUrl = request.getParameter("callbackUrl");
			String orderNum = request.getParameter("orderNum");
			String payMoney = request.getParameter("payMoney");
			String payType = request.getParameter("payType");
			String ip = request.getParameter("ip");
			String signStr = request.getParameter("signStr");
			String ipReal = IpUtils.getIpAddress(request);
			String agent = request.getHeader("user-agent");
			param.put("memberCode", memberCode);
			param.put("payMoney", payMoney);
			param.put("payType", payType);
			param.put("ip", ip);
			param.put("callbackUrl", callbackUrl);
			param.put("orderNum", orderNum);
			param.put("signStr", signStr);
			param.put("ipReal", ipReal);
			param.put("userAgent", agent);
			List<NameValuePair> nvps = new LinkedList<NameValuePair>();
			List<String> keys = new ArrayList<String>(param.keySet());
			for (int i = 0; i < keys.size(); i++) {
				String name=(String) keys.get(i);
				String value=(String) param.get(name);
				if(value!=null && !"".equals(value)){
					nvps.add(new BasicNameValuePair(name, value));
				}
			}
			
			byte[] b = HttpClient4Util.getInstance().doPost(SysConfig.pospService + "/cashierDesk/qrcodePay", null, nvps);
			String respStr = new String(b, "utf-8");
			JSONObject responseJson = JSONObject.fromObject(respStr);
			if ("0000".equals(responseJson.getString("returnCode"))) {
				String routeCode = "";
				if(responseJson.containsKey("routeCode")){
					routeCode = responseJson.getString("routeCode");
				}
			/*	if(routeCode.equals(DataDicConstant.KQ_ROUTE_CODE)){
					page = "payment/toKqPay";
					model.addAttribute("action", responseJson.getString("payUrl"));
					model.addAttribute("requestTime", responseJson.getString("requestTime"));
					model.addAttribute("externalTraceNo", responseJson.getString("externalTraceNo"));
					model.addAttribute("merchantCode", responseJson.getString("merchantCode"));
					model.addAttribute("merchantId", responseJson.getString("merchantId"));
					model.addAttribute("terminalId", responseJson.getString("terminalId"));
					model.addAttribute("amt", responseJson.getString("amt"));
					model.addAttribute("productInfo", responseJson.getString("productInfo"));
					model.addAttribute("returnUrl", responseJson.getString("returnUrl"));
					model.addAttribute("sign", responseJson.getString("sign"));
				}else{*/
					page = "payment/qrCode";
					model.addAttribute("qrCode", responseJson.getString("qrCode"));
					model.addAttribute("orderNum", orderNum);
					model.addAttribute("payMoney", payMoney);
					model.addAttribute("payType", payType);
			//	}
				
				
			}else{
			    model.addAttribute("errorMsg", responseJson.getString("returnMsg"));
			}
		}catch (Exception ex) {
			ex.printStackTrace();
		}
		return page;
		
	}
}
