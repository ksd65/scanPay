package com.epay.scanpay.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.codec.digest.DigestUtils;
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
import com.epay.scanpay.common.utils.HxUtils;
import com.epay.scanpay.common.utils.IpUtils;
import com.epay.scanpay.common.utils.Verify;
import com.epay.scanpay.entity.SinoPayRequestForm;






@Controller
public class BankPayController {
	private static Logger logger = LoggerFactory.getLogger(DebitNoteController.class);
	
	@RequestMapping("/payment/bankIndex")
	public String index(Model model,HttpServletRequest request){
		String orderNum = "B"+new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		String ip = "";
		try {
			ip = IpUtils.getIpAddress(request);
			System.out.println("ip===="+ip);
		} catch (IOException e) {
			e.printStackTrace();
		}
		model.addAttribute("orderNum", orderNum);
		model.addAttribute("ip", ip);
		return "payment/sinopay";
	}
	
	@RequestMapping("/payment/bankConfirm")
	public String bankConfirm(Model model,HttpServletRequest request){
		String memberCode = request.getParameter("memberCode");//商家编码
		String orderNum = request.getParameter("orderNum");//商家订单号
		String payMoney = request.getParameter("payMoney");//支付金额
		String goodsName = request.getParameter("goodsName");//商品名称
		String callbackUrl = request.getParameter("callbackUrl");//回调地址
		String bankCode = request.getParameter("bankCode");
		//待签名字符串
		Map<String,String> params = new HashMap<String,String>();
		if(StringUtils.isBlank(bankCode)){
			bankCode = "";
		}else{
			params.put("bankCode",  bankCode);
		}
		if(StringUtils.isBlank(goodsName)){
			goodsName = "";
		}else{
			params.put("goodsName",  goodsName);
		}
		
		String privateKey = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCIxkLM+QBKTqw9BsCSjiLw5pO186HwgMOZIXUFDVnRsYyPeiFsnPgaNQmT7L5Ur0jHtKc0yUdk5jd8BG06N40474MiBzNNNJJa4mkPQfUlsD0T0NMLesjFthKqyQxS1cMqpz8b372pwsWb16BVM/9i5ak9JgqguAtEkxR2lSNd0sn/GEqH9hdw6RvMH/Y/ePztdA3bANJL64g0hrKKBW0YcJvXJpKF8vIwuNuamOoKOzlEIPZC05331j4c8xw/EUiBbS9sELAT+MLNlYXHIWtpDDbJTNZGKXDskJpfae7ifW4TU6ZlraiW4JGabl1d5DH2t4k5NnFqiquPZWPhZ8u9AgMBAAECggEAdGdHsuq4NIWAUO/ONOyDEEMss04GJIlx7oFq9kHGj5Br8DAhAi8VeDhrTlnOIoSLjGtTYrlq6ZSE5CdgTou4xRwSnoNCRhLX/EF06GdaHBlB4ft3oe19scajXHZ+5oDG+SYdr7tbz37Uby20Zs86KxEKV+BoayA3dsU2RTXoQ5BCtMeXaIUif3JjHGy1xHkR1MPN2vXvwMqU7Femk/Qr9s8W+9IgVp5ah28Kx5qkR63TkEONyYTbI+U4El/bVnZmcaHL+/KJ9wo8+/7hHrl+mWV0T3iKa4tJWed9V+IUKrLeUpn9DOIv9WNlDFjJqBckC/GBhujG7uRKWfminGRAgQKBgQDJnQv9HAgUnGsYTayY1iW2CwgFZJn1RgaTCjo1Oc6tDTFXdP4uIFfxEfnLhGuRuItTKHW1hf1Hp4n+wiZO5q1HjjC3bLBUy1Lw+HvaJLNHRkCye58cg2Jlu81girzciazLiHqfEaZxFsUq8WsqzZIBRr0gPDDJMrSOBfkf+hWckwKBgQCtq5WSq/t5Cr1N5AOoYoBDvJOHqQ4/6Ue0JF270/u1H/qdMQ9w/OQRixhHNP7g5oiPBWKPQTs3uU9/SHpX5CHsyqlQDvOkrSnESp4pz0BUOT971er/8o79NrUE9ZQMRDhu0WHXtbPOq+7siUw9SA5HcEh8Hd/Y3xLOFhvw90x4bwKBgGBGFRZ9j0JAW0eUt8mX4RQn+mGQ44/jK3qFlLwb6ZxrQ1eO712ZZkUgn1bW2gMQy78e/+55mDPiRhwYG/DraG1V8d91EFK9cNLO5V2Kzu1HF9fi/lzARHluD6l9NqhdOd1LQ7q30/IGvIpAFDuxRHpFjERbWbSJ+Pwk0Ay8ABvvAoGAah4XFfkqfqqWQ3rY1VHiyAD5MIKXJ2w2mRdDgxqjiegRbW1l3wdXoHSakCAMwYV72dBTie807Pa5Ya/6uau3IwYucLHCJFR+2ecyP5/Y0d3tMZDjuCMRRh3gfDhGjzw8M1KTc4geZ2Fda4D1adiWiQZN9DEY715XEkAmMJYbTtcCgYEAn9BcQcpAsbV2fpmO0VU8CMFoTnbfYpmHR9N6A45r72j1nQmJxfZbvL7JiIK7jhRUuQERC+jK08FPKbFDoE5a7UxNiKDE/ZtuEwPehrf8yVenxmAWMh4llc1HoWQfjCY8BJuAm3K4jHtswB0+PQfTMG7PVisoMfdVEJ4gq8BLAv0=";
		if("9010000002".equals(memberCode)){
			privateKey = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCfKi2ZOWOPYsDPj6RT9kV2s5RCOezBoEnAMfVoEbe5eQB5rJhTwRaN2rGHPMlv1baB6dB4yJ49Eidvq9IX4D7lhnbPwvvCi7EjszJgyrID8miVu9wBpPRUdjS+YsrWyGrVK0vMxl79TlQWK22io/yZp9aWTCTgSM3qEyQaMyk6bEANYIUaSlpbiDJ2mvaU6JpSDZJLHKpNvPVNDLk52fpuArOuvHo2Nn0NOrnv8bjAYk8CMxNBuE6xicHl3ycDU8PQ3MKEhWkyNAm/vVH8v37kYcDpx6ftUM7ddAq+SOE9BPvjoxsBNYEKztvZxBdaZHYZUEGtjyBeulzr0L1KZJDtAgMBAAECggEACDG1s0O/GmytHIJ6pU/yd4/7PAWbaMSFx31K8xambMgL/Dekh/tS2+68YQgCHt3TzZBqCS3a5639lcQ0xsHmuw5XI48YQwXKEtpw54bH72gVdk/7naIOaiLDyGFxq+kZhuv5tQspbMURkyqdNFhY8tgvNgGpjFpzL2/Y1fh4UOeYzGFTKMCzpxfHwCdpfFoW4QNGbVvC+IKpuLyKf58hBvSaR8AaFCQ0XTBVl6QbB/8ryWL2xbdg+/+cB4TXwD/Ct3xcLIEF5ow7/1IVyqlSzUelh9SaKq2113T1HGFTr7rELlXplU5J1icV6JEdoq+RplW3SQ9dMesV9l8dhAA3QQKBgQDpKSQPq7nAVCEcEzohiBvZMibW/EBxWBBIXgAc1G/DasfDuTxyW//4gPUeozW4ZMzwnlc31CMkusd806Iecio0+LrKB3V9yFW4fbzcLYdY4tN3oeQ9MoX9nkFM/ikKl2B1HcLmBWZqgg3kdf1hIa/E2YdK/x+NofaSz4lQMjjveQKBgQCuwXnzrk9RqQ7bg8Ol38NAqV+3xQ7hrvzH/Zjifd/sMbGC0y0JgM++3Y5jALsxD7GcQt9V1m31qGlXzW4aitNxdlMpvcSfHdUE9kXgbp9g1rqJDg/hJe+nH7nBI6NmRdrb+Ns0lnNYXBHYAQ/W3M521U46TWtODLcNOXwXbGlMFQKBgQCSkBXm899zkm6tozhrU4+N3ASmJzKrDNxPYSdY+AC5KiogUhQ5HrOslgN/GsDuBA7/Qck5gtQEhpRXVwEVelYlriRcUov8YS3hJsjM7qGhshOTo+RAw72OSyhpKWrLCZTMicS1qrdSRCZPcguwPuiqKMLu1agT87d3WZXLH4bCoQKBgB8lHDbxufEz0BIPSa8mUgYUKZr249AU/7gk2jqDdIUD1j8ao8wtyNibY+UBHFuCEIVo5aTGspI1kZC0bAsO8uAl1mx6BbDWAEECIzH8hSsdGeGTQAFAYZXHcbOaRmTTzk2l7GtS5Pu6bPOyPMBuWd2T5n09jwI6AeW5eQQzrhCBAoGBAKR9CR4SYsH+lcONvAfRC7fQMrS4FwwUbHYUuDEDmDn07oelaJYh7UNbCbNG6x0cHYkJleQlDHyl7hSwHl5pBXzJooSKWCj9Hn3DAfqUouFNZf1kZruoOWBosGt/pKjDzNwVhAp+gCeD9veGZxrvfuVdUgS5yv0fgqQg2qsGdeN3";
		}
		
		
		params.put("orderNum", orderNum); 
		params.put("memberCode", memberCode);  
		params.put("callbackUrl",  callbackUrl);
		params.put("payMoney", payMoney );
		
		String srcStr = orderedKey(params);
 		System.out.println("srcStr==="+srcStr);
		String signstr = EpaySignUtil.sign(privateKey, srcStr);
		
		model.addAttribute("memberCode", memberCode);
		model.addAttribute("orderNum", orderNum);
		model.addAttribute("payMoney", payMoney);
		model.addAttribute("goodsName", goodsName);
		model.addAttribute("callbackUrl", callbackUrl);
		model.addAttribute("signStr", signstr);
		model.addAttribute("bankCode", bankCode);
		
		return "payment/bankConfirm";
		
	}
	
	
	@RequestMapping("/payment/toPayment")
	public String toPayment(Model model,HttpServletRequest request){
		String page = "payment/fail";
		
		try { // 获取页面请求信息
			
			String memberCode = request.getParameter("memberCode");
			String callbackUrl = request.getParameter("callbackUrl");
			String signStr = request.getParameter("signStr");
			String bankCode = request.getParameter("bankCode");
			String orderNum = request.getParameter("orderNum");
			String payMoney = request.getParameter("payMoney");
			String ip = IpUtils.getIpAddress(request);
			if(StringUtils.isBlank(bankCode)){
				bankCode = "";
			}
			String goodsName = request.getParameter("goodsName");
			if(StringUtils.isBlank(goodsName)){
				goodsName = "";
			}
			
			JSONObject reqData = new JSONObject();
			reqData.put("memberCode", memberCode);
			reqData.put("orderNum", orderNum);
			reqData.put("payMoney", payMoney);
			reqData.put("goodsName", goodsName);
			reqData.put("bankCode", bankCode);
			reqData.put("callbackUrl", callbackUrl);
			reqData.put("signStr", signStr);
			reqData.put("ip", ip);
			
			logger.info("进入请求toPayment");
			logger.info("memberCode="+memberCode);
			logger.info("callbackUrl="+callbackUrl);
			logger.info("signStr="+signStr);
			logger.info("orderNum="+orderNum);
			logger.info("payMoney="+payMoney);
			logger.info("bankCode="+bankCode);
			logger.info("goodsName="+goodsName);
			
			JSONObject responseJson = JSONObject.fromObject(
					HttpUtil.sendPostRequest(SysConfig.pospService + "/api/bankPay/toPay",
							CommonUtil.createSecurityRequstData(reqData)));
			if ("0000".equals(responseJson.getString("returnCode"))) {
				String routeCode = responseJson.getString("routeCode");
				if(DataDicConstant.HX_ROUTE_CODE.equals(routeCode)){
					String action = responseJson.getString("payUrl");
					// body部分
					String bodyXml = "<body>" +
							"<MerBillNo>" + responseJson.getString("orderCode") + "</MerBillNo>" +
							"<Lang>GB</Lang>" +//语言  中文
							"<Amount>" + payMoney + "</Amount>" +
							"<Date>" + new SimpleDateFormat("yyyyMMdd").format(new Date()) + "</Date>" +
							"<CurrencyType>156</CurrencyType>" +  //币种 人民币
							"<GatewayType>02</GatewayType>" + //01#借记卡 02#信用卡 03#IPS账户支付 
							"<Merchanturl>" + responseJson.getString("frontUrl") + "</Merchanturl>" +
							"<FailUrl><![CDATA[" + "" + "]]></FailUrl>" +
							"<Attach><![CDATA[" + "" + "]]></Attach>" +
							"<OrderEncodeType>5</OrderEncodeType>" +//订单支付接口加密方式  5:MD5
							"<RetEncodeType>17</RetEncodeType>" +//交易返回接口加密方式 17:md5摘要
							"<RetType>1</RetType>" +//Server to Server返回
							"<ServerUrl><![CDATA[" + responseJson.getString("callBack") + "]]></ServerUrl>" +
							"<BillEXP>" + "24" + "</BillEXP>" +
							"<GoodsName>" + responseJson.getString("goodsName") + "</GoodsName>" ;
							if(responseJson.containsKey("bankCode")&&StringUtils.isNotBlank(responseJson.getString("bankCode"))){
								bodyXml = bodyXml + "<IsCredit>1</IsCredit>" +//决定商户是否参用直连方式 1:直连  如不用直连方式，此参数不用传值
										"<BankCode>"+responseJson.getString("bankCode")+"</BankCode>" +
										"<ProductType>1</ProductType>" +
										"</body>";
							}else{
								bodyXml = bodyXml+"<IsCredit></IsCredit>" +//决定商户是否参用直连方式 1:直连  如不用直连方式，此参数不用传值
								"<BankCode></BankCode>" +
								"<ProductType></ProductType>" +
								"</body>";
							}
							
					// MD5签名
					String directStr = responseJson.getString("privateKey");
					String sign = DigestUtils
							.md5Hex(Verify.getBytes(bodyXml + responseJson.getString("merchantCode") + directStr,
									"UTF-8"));
					// xml
					String date = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
					String xml = "<Ips>" +
							"<GateWayReq>" +
							"<head>" +
							"<Version>v1.0.0</Version>" +
							"<MerCode>" + responseJson.getString("merchantCode") + "</MerCode>" +
							"<MerName>" + responseJson.getString("merchantName") + "</MerName>" +
							"<Account>" + responseJson.getString("merchantAccount") + "</Account>" +
							"<MsgId>" + "msg" + date + "</MsgId>" +
							"<ReqDate>" + date + "</ReqDate>" +
							"<Signature>" + sign + "</Signature>" +
							"</head>" +
							bodyXml +
							"</GateWayReq>" +
							"</Ips>";
					System.out.println(">>>>> 订单支付 请求信息: " + xml);
					model.addAttribute("action", action);
					model.addAttribute("pGateWayReq", xml);
					page = "payment/sinopayPaymentForm";
				}else if(DataDicConstant.SLF_ROUTE_CODE.equals(routeCode)){
					String msg = responseJson.getString("msg");
					String payUrl = responseJson.getString("payUrl");
					System.out.println(">>>>> 订单支付 请求信息: " + msg);
					model.addAttribute("action", payUrl);
					model.addAttribute("msg", msg);
					page = "payment/paySubmit";
				}else if(DataDicConstant.RF_ROUTE_CODE.equals(routeCode)){
					String payUrl = responseJson.getString("payUrl");
					
					model.addAttribute("action", payUrl);
					model.addAttribute("AppKey", responseJson.getString("merchantCode"));
					model.addAttribute("OrderNum", responseJson.getString("orderCode"));
					model.addAttribute("PayMoney", payMoney);
					model.addAttribute("SuccessUrl", responseJson.getString("frontUrl"));
					model.addAttribute("SignStr", responseJson.getString("signStr"));
					page = "payment/rfBankSubmit";
				}else if(DataDicConstant.CJWG_ROUTE_CODE.equals(routeCode)){
					String payUrl = responseJson.getString("payUrl");
					
					model.addAttribute("action", payUrl);
					model.addAttribute("v_mid", responseJson.getString("merchantCode"));
					model.addAttribute("v_oid", responseJson.getString("orderCode"));
					model.addAttribute("v_time", responseJson.getString("v_time"));
					model.addAttribute("v_bankAddr", responseJson.getString("bankCode"));
					model.addAttribute("v_productName", responseJson.getString("goodsName"));
					model.addAttribute("v_productDesc", responseJson.getString("goodsName"));
					model.addAttribute("v_cardType", responseJson.getString("cardType"));
					model.addAttribute("v_notify_url", responseJson.getString("callBack"));
					model.addAttribute("v_url", responseJson.getString("frontUrl"));
					
					model.addAttribute("v_txnAmt", payMoney);
					model.addAttribute("v_sign", responseJson.getString("signStr"));
					page = "payment/cjBankSubmit";
				}else if(DataDicConstant.ESKWG_ROUTE_CODE.equals(routeCode)||DataDicConstant.ESKWGD0_ROUTE_CODE.equals(routeCode)){
					String payUrl = responseJson.getString("payUrl");
					
					model.addAttribute("action", payUrl);
					page = "payment/eskBankSubmit";
				}else{
					request.setAttribute("errorMsg", "当前商户不支持网银支付");
					model.addAttribute("errorMsg", "当前商户不支持网银支付");
				}
			}else{
			    request.setAttribute("errorMsg", responseJson.getString("returnMsg"));
				model.addAttribute("errorMsg", responseJson.getString("returnMsg"));
			}
		}catch (Exception ex) {
			ex.printStackTrace();
		}
		return page;
		
	}
	
	
	@RequestMapping("/payment/result")
	public String payResult(Model model,HttpServletRequest request){
		
		try {
			request.setCharacterEncoding("UTF-8");
			String resultXml = request.getParameter("paymentResult");
			logger.info("payResult前台通知返回报文[{}]",  resultXml );
			
			JSONObject reqData = new JSONObject();
			reqData.put("paymentResult", resultXml);
			
			JSONObject responseJson = JSONObject.fromObject(
					HttpUtil.sendPostRequest(SysConfig.pospService + "/api/bankPay/hxPayNotifyDeal",
							CommonUtil.createSecurityRequstData(reqData)));
			
			String merCode = SysConfig.ipsMerCode;
			String directStr = SysConfig.ipsDirectStr;
			String resultMessage = "";
			String totalAmount = "0";
			boolean flag = false;
			if(resultXml != null&&!"".equals(resultXml)){
				boolean checkSign = HxUtils.checkSign(resultXml, merCode, directStr, null);
				if(!checkSign){
					resultMessage =  "验证签名不通过";
				}else{
					String resultCode = HxUtils.getRspCode(resultXml);
					if("000000".equals(resultCode)){
						String status = HxUtils.getStatus(resultXml);
						if (status.equals("Y")) {
							totalAmount = HxUtils.getAmount(resultXml);
							resultMessage = "恭喜您！支付成功！";
							model.addAttribute("ImageUrl", SysConfig.payService+File.separator+"images/ggimg.png");
							model.addAttribute("Href", "");
							flag = true;
						} else if (status.equals("N")) {
							resultMessage = "亲，支付失败啦，请重新支付!";
						} else if (status.equals("P")) {
							resultMessage = "您的支付正处理中.......";
							model.addAttribute("oriRespType", "R");
						}
					}else{
						resultMessage = "交易不确定!";
					}
				}
			}else{
				resultMessage = "交易不确定!";
			}
			model.addAttribute("totalAmount", totalAmount);
			model.addAttribute("resultMessage", resultMessage);
			
			if (flag) {
			    return "debitNote/scanResultSuccess";
			} else {
			    return "debitNote/scanResult";
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return "debitNote/scanResult";
		
	}
	
	private SinoPayRequestForm createMerForm(HttpServletRequest request){
		String billExp = "24";//订单有效期(以小时计算，必须是整数)
		SinoPayRequestForm merchantForm = new SinoPayRequestForm();
		merchantForm.setMerBillNo(request.getParameter("orderNum"));
		merchantForm.setAmount(request.getParameter("payMoney"));
		//merchantForm.setCurrencyType(request.getParameter("Currency_Type"));
		//merchantForm.setDate(request.getParameter("Date"));
		//merchantForm.setOrderEncodeType(request.getParameter("OrderEncodeType"));
		merchantForm.setGatewayType(request.getParameter("gateWayType"));
		//merchantForm.setLang(request.getParameter("Lang"));
		//merchantForm.setMerchantUrl(request.getParameter("Merchanturl"));
		merchantForm.setFailUrl("");
		merchantForm.setAttach("");
		//merchantForm.setRetEncodeType(request.getParameter("RetEncodeType"));
		//merchantForm.setRettype(request.getParameter("Rettype"));
		//merchantForm.setServerUrl(request.getParameter("ServerUrl"));
		merchantForm.setBillExp(billExp);
		merchantForm.setGoodsName(request.getParameter("goodsName"));
		//merchantForm.setIsCredit(request.getParameter("DoCredit"));
		//merchantForm.setBankcode(request.getParameter("Bankco"));
		//merchantForm.setProductType(request.getParameter("PrdType"));
	/*	merchantForm.setSysCode(request.getParameter("SysCode"));
		merchantForm.setWhoFee(request.getParameter("WhoFee"));
		merchantForm.setFeeType(request.getParameter("FeeType"));
		merchantForm.setUserId(request.getParameter("UserId"));
		merchantForm.setUserRealName(request.getParameter("UserRealName"));
		merchantForm.setBizType(request.getParameter("BizType"));
		merchantForm.setMerCode(request.getParameter("Mer_code"));
		merchantForm.setMerName(request.getParameter("Mer_Name"));
		merchantForm.setMerAcccode(request.getParameter("Mer_acccode"));
		merchantForm.setVersion(request.getParameter("version"));*/
		return merchantForm;
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

}
