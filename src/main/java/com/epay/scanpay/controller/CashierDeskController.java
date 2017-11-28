package com.epay.scanpay.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.epay.scanpay.common.constant.SysConfig;
import com.epay.scanpay.common.utils.CommonUtil;
import com.epay.scanpay.common.utils.DateUtil;
import com.epay.scanpay.common.utils.HttpUtil;
import com.epay.scanpay.common.utils.StringUtil;

@Controller
public class CashierDeskController {
	private static Logger logger = LoggerFactory.getLogger(CashierDeskController.class);

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
		reqData.put("tranCode", "001");//扫码支付
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
		String isMoneyFilled = request.getParameter("isMoneyFilled");
		String callbackUrl = request.getParameter("callbackUrl");
		String platformType = request.getParameter("platformType");
		String signStr = request.getParameter("signStr");

		JSONObject reqData = new JSONObject();
		reqData.put("payMoney", payMoney);
		reqData.put("payType", payType);
		reqData.put("orderNum", orderNum);
		reqData.put("memberCode", memberCode);
		reqData.put("isMoneyFilled", isMoneyFilled);
		reqData.put("callbackUrl", callbackUrl);
		reqData.put("platformType", platformType);
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
	
	
}
