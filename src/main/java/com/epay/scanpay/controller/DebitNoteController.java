package com.epay.scanpay.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Map;

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

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipaySystemOauthTokenRequest;
import com.alipay.api.response.AlipaySystemOauthTokenResponse;
import com.epay.scanpay.common.constant.AlipayServiceEnvConstants;
import com.epay.scanpay.common.constant.DataDicConstant;
import com.epay.scanpay.common.constant.SysConfig;
import com.epay.scanpay.common.constant.WxConfig;
import com.epay.scanpay.common.excep.ArgException;
import com.epay.scanpay.common.utils.AlipayAPIClientFactory;
import com.epay.scanpay.common.utils.CommonUtil;
import com.epay.scanpay.common.utils.HttpUtil;
import com.epay.scanpay.common.utils.SessionUtils;
import com.epay.scanpay.entity.MemberInfo;

@Controller
public class DebitNoteController {
	private static Logger logger = LoggerFactory.getLogger(DebitNoteController.class);

	@RequestMapping("/debitNote/index")
	public String index(Model model, HttpServletRequest request) {
		String requestUrl = request.getRequestURL().toString();

		String replaceStr = "rkt.ruikafinance.com:18892";
		if (requestUrl.indexOf(replaceStr) != -1) {
			requestUrl = requestUrl.replace(replaceStr, "rkt.topepay.com");
			return "redirect:" + requestUrl + "?" + request.getQueryString();
		}
		String epayCode = request.getParameter("epayCode");
		// 接入oem对应页面提示信息
		String oemName = request.getParameter("oemName");
		request.getSession().setAttribute("epayCode", epayCode);
		request.getSession().setAttribute("oemName", oemName);
		JSONObject reqData = new JSONObject();
		reqData.put("epayCode", epayCode);
		String register = "";
		String scanResult = "";
		String index = "";
		String state = "";
		String auditPage = "";
		if (oemName == null || oemName == "") {
			register = "redirect:/register?epayCode=" + epayCode;
			scanResult = "debitNote/scanResult";
			index = "/debitNote/index";
			state = "";
			auditPage = "debitNote/auditing";
		} else {
			register = "redirect:/register?oemName=" + oemName + "&epayCode=" + epayCode;
			scanResult = oemName + "/" + "debitNote/scanResult";
			index = oemName + "/debitNote/index";
			state = oemName;
			auditPage = oemName + "/debitNote/auditing";
		}
		String ua = request.getHeader("user-agent").toLowerCase();
		if (!(ua.indexOf("micromessenger") > 0 || ua.indexOf("alipay") > 0
				|| (ua.indexOf("applewebkit") != -1 && ua.indexOf("qq") != -1) 
				|| ua.indexOf("baiduwallet") > 0 || ua.indexOf("application=jdjr-app") > 0)) {
			model.addAttribute("resultMessage", "请选择支付宝、微信、手机QQ、百度钱包或京东金融进行扫描");
			return scanResult; // by linxf 先屏蔽
		}

		JSONObject responseJson = JSONObject.fromObject(
				HttpUtil.sendPostRequest(SysConfig.pospService + "/api/memberInfo/getMemberInfoByEpayCode",
						CommonUtil.createSecurityRequstData(reqData)));

		JSONObject memberInfoData ;
		if ("0000".equals(responseJson.getString("returnCode"))) {
			memberInfoData = responseJson.getJSONObject("resData");
			String epayCodeStatus = memberInfoData.getString("epayCodeStatus");
			if ("2".equals(epayCodeStatus) || "3".equals(epayCodeStatus) || "6".equals(epayCodeStatus)) {

				// return "redirect:/register?epayCode=" + epayCode;
				return register;
			}
			if ("1".equals(epayCodeStatus)) {
				model.addAttribute("resultMessage", "亲，此收款码暂不可用。");
				// return "debitNote/scanResult";
				return scanResult;
			}
			if ("4".equals(epayCodeStatus)) {
				model.addAttribute("resultMessage", "亲，您的信息还在审核中，暂不能收款，通过之后我们将会短信提醒您。。。。。");
				// return "debitNote/scanResult";
				return scanResult;
			}

			// model.addAttribute("memberId",
			// responseJson.getJSONObject("resData").getString("memberId"));
			// model.addAttribute("epayCode",epayCode);
			// model.addAttribute("memberName",
			// responseJson.getJSONObject("resData").getString("memberName"));
		} else {
			model.addAttribute("resultMessage", responseJson.getString("returnMsg"));
			// return "debitNote/scanResult";
			return scanResult;
		}
		Integer memberId = memberInfoData.getInt("memberId");
		String routeCode = "";
		if (ua.indexOf("micromessenger") > 0) {
			routeCode = getRouteCode(memberId,DataDicConstant.PAY_METHOD_GZHZF,DataDicConstant.PAY_TYPE_WX);
			
			//若微信商户号还未审核通过，则提示正在审核中
			if(!memberInfoData.containsKey("wxStatus") || !"1".equals(memberInfoData.get("wxStatus"))){
				return auditPage;
			}
			model.addAttribute("userAgentType", "micromessenger");
			// String redirectUrl = "http://" + request.getServerName() + ":" +
			// request.getServerPort() + request.getContextPath() +
			// "/weixin/debitNoteAuthCallBack";
			String redirectUrl = SysConfig.payService + "/weixinPay/debitNoteAuthCallBack";
			try {
				redirectUrl = URLEncoder.encode(redirectUrl, "utf-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			if(!DataDicConstant.ESK_ROUTE_CODE.equals(routeCode)){
				return "redirect:https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + WxConfig.appid
						+ "&redirect_uri=" + redirectUrl + "&response_type=code&scope=snsapi_base&state=" + state
						+ "#wechat_redirect";
			}else{
				redirectUrl = SysConfig.payService + "/weixinPay/eskOpenId";
				return "redirect:http://www.tianlepay.com/aggregate/wx/openid.kb?agentId=2017101714241&uri="+redirectUrl+"&data=123456789";
			}
			
			/*
			 * model.addAttribute("memberId",
			 * responseJson.getJSONObject("resData").getString("memberId"));
			 * model.addAttribute("epayCode", epayCode);
			 * model.addAttribute("memberName",
			 * responseJson.getJSONObject("resData").getString("memberName"));
			 * model.addAttribute("userId", "");
			 */

		} else if (ua.indexOf("alipay") > 0) {
			routeCode = getRouteCode(memberId,DataDicConstant.PAY_METHOD_GZHZF,DataDicConstant.PAY_TYPE_ZFB);
			
			//若支付宝商户号还未审核通过，则提示正在审核中
			if(!memberInfoData.containsKey("zfbStatus") || !"1".equals(memberInfoData.get("zfbStatus"))){
				return auditPage;
			}
			model.addAttribute("userAgentType", "alipay");
			String redirectUrl = SysConfig.payService + "/alipay/debitNoteAuthCallBack";
			try {
				redirectUrl = URLEncoder.encode(redirectUrl, "utf-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			//if(!DataDicConstant.ESK_ROUTE_CODE.equals(routeCode)){
				return "redirect:https://openauth.alipay.com/oauth2/publicAppAuthorize.htm?app_id="
					+ AlipayServiceEnvConstants.APP_ID + "&state=" + state + "&scope=auth_base&redirect_uri="
					+ redirectUrl;
			//}
		} else if (ua.indexOf("application=jdjr-app") > 0) {//放在qq前面，因为ua里面有和qq相同的字符串
			model.addAttribute("memberId", responseJson.getJSONObject("resData").getString("memberId"));
			model.addAttribute("memberName", responseJson.getJSONObject("resData").getString("memberName"));
			model.addAttribute("userAgentType", "jdjrpay");
		} else if (ua.indexOf("applewebkit") != -1 && ua.indexOf("qq") != -1) {
			// huangkz 20170415 Add,在支付页面不显示问题
			model.addAttribute("memberId", responseJson.getJSONObject("resData").getString("memberId"));
			model.addAttribute("memberName", responseJson.getJSONObject("resData").getString("memberName"));
			model.addAttribute("userAgentType", "mqqbrowser");
		} else if (ua.indexOf("baiduwallet") > 0) {
			model.addAttribute("memberId", responseJson.getJSONObject("resData").getString("memberId"));
			model.addAttribute("memberName", responseJson.getJSONObject("resData").getString("memberName"));
			model.addAttribute("userAgentType", "baiduwallet");
		}
		model.addAttribute("epayCode", epayCode);
		model.addAttribute("oemName", oemName);
		
		if(DataDicConstant.ESK_ROUTE_CODE.equals(routeCode)){
			JSONObject resJson = JSONObject.fromObject(HttpUtil.sendPostRequest(SysConfig.pospService + "/api/debitNote/oneCodePay",
							CommonUtil.createSecurityRequstData(reqData)));
			if ("0000".equals(resJson.getString("returnCode"))) {
				String codePath = resJson.getString("qrCode");
				return "redirect:" + codePath;
			}else{
				model.addAttribute("resultMessage", responseJson.getString("returnMsg"));
				return scanResult;
			}
		}
		
		
		// return "/debitNote/index";
		return index;

	}
	
	@RequestMapping("/weixinPay/eskOpenId")
	public String eskOpenId(Model model, HttpServletRequest request) {
		model.addAttribute("userAgentType", "micromessenger");
		String oemName = request.getParameter("state");
		model.addAttribute("oemName", oemName);
		String register = "";
		String scanResult = "";
		String index = "";
		String state = "";
		if (oemName == null || oemName == "") {
			register = "redirect:/register?a=1";
			scanResult = "debitNote/scanResult";
			index = "/debitNote/index";
			state = "";
		} else {
			register = "redirect:/register?oemName=" + oemName;
			scanResult = oemName + "/" + "debitNote/scanResult";
			index = oemName + "/debitNote/index";
			state = oemName;
		}
		String openid = request.getParameter("openid");
		if (openid == null || "".equals(openid)) {
			model.addAttribute("resultMessage", "亲，不好意思，出错啦。");
			// return "debitNote/scanResult";
			return scanResult;
		}
		request.getSession().setAttribute("openid", openid);// 将openId保存到session

		String epayCode = request.getSession().getAttribute("epayCode").toString();
		JSONObject reqData = new JSONObject();
		reqData.put("epayCode", epayCode);
		JSONObject responseJson = JSONObject.fromObject(
					HttpUtil.sendPostRequest(SysConfig.pospService + "/api/memberInfo/getMemberInfoByEpayCode",
							CommonUtil.createSecurityRequstData(reqData)));
		if ("0000".equals(responseJson.getString("returnCode"))) {
			String epayCodeStatus = responseJson.getJSONObject("resData").getString("epayCodeStatus");
			if ("3".equals(epayCodeStatus) || "6".equals(epayCodeStatus)) {
				// return "redirect:/register?epayCode=" + epayCode;
				return register + "&epayCode=" + epayCode;
			}
			if ("4".equals(epayCodeStatus)) {
				model.addAttribute("resultMessage", "亲，您的信息还在审核中，暂不能收款，通过之后我们将会短信提醒您。。。。。");
				// return "debitNote/scanResult";
				return scanResult;
			}
			System.out.println("openid======"+openid);
			model.addAttribute("memberId", responseJson.getJSONObject("resData").getString("memberId"));
			model.addAttribute("epayCode", epayCode);
			model.addAttribute("memberName", responseJson.getJSONObject("resData").getString("memberName"));
			model.addAttribute("userId", openid);
		} else {
			model.addAttribute("resultMessage", responseJson.getString("returnMsg"));
			// return "debitNote/scanResult";
			return scanResult;
		}

		

		// return "/debitNote/index";
		return index;

	}
	
	
	
	

	@RequestMapping("/weixinPay/debitNoteAuthCallBack")
	public String wxDebitNoteAuthCallBack(Model model, HttpServletRequest request) {
		model.addAttribute("userAgentType", "micromessenger");
		String code = request.getParameter("code");
		String oemName = request.getParameter("state");
		model.addAttribute("oemName", oemName);
		String register = "";
		String scanResult = "";
		String index = "";
		String state = "";
		if (oemName == null || oemName == "") {
			register = "redirect:/register?a=1";
			scanResult = "debitNote/scanResult";
			index = "/debitNote/index";
			state = "";
		} else {
			register = "redirect:/register?oemName=" + oemName;
			scanResult = oemName + "/" + "debitNote/scanResult";
			index = oemName + "/debitNote/index";
			state = oemName;
		}
		if (code != null && !"".equals(code)) {
			String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + WxConfig.appid + "&secret="
					+ WxConfig.appSecret + "&code=" + code + "&grant_type=authorization_code";
			String jsonStr = HttpUtil.sendGetRequest(url);// 根据code获取用户openId
			JSONObject resJson = JSONObject.fromObject(jsonStr);
			if (resJson.has("errcode") && !"0".equals(resJson.getString("errcode"))) {// 请求出错跳到首页
				model.addAttribute("resultMessage", "亲，不好意思，出错啦。");
				// return "debitNote/scanResult";
				return scanResult;
			}
			String openid = resJson.getString("openid");
			request.getSession().setAttribute("openid", openid);// 将openId保存到session

			String epayCode = request.getSession().getAttribute("epayCode").toString();
			JSONObject reqData = new JSONObject();
			reqData.put("epayCode", epayCode);
			JSONObject responseJson = JSONObject.fromObject(
					HttpUtil.sendPostRequest(SysConfig.pospService + "/api/memberInfo/getMemberInfoByEpayCode",
							CommonUtil.createSecurityRequstData(reqData)));
			if ("0000".equals(responseJson.getString("returnCode"))) {
				String epayCodeStatus = responseJson.getJSONObject("resData").getString("epayCodeStatus");
				if ("3".equals(epayCodeStatus) || "6".equals(epayCodeStatus)) {
					// return "redirect:/register?epayCode=" + epayCode;
					return register + "&epayCode=" + epayCode;
				}
				if ("4".equals(epayCodeStatus)) {
					model.addAttribute("resultMessage", "亲，您的信息还在审核中，暂不能收款，通过之后我们将会短信提醒您。。。。。");
					// return "debitNote/scanResult";
					return scanResult;
				}
				System.out.println("openid======"+openid);
				model.addAttribute("memberId", responseJson.getJSONObject("resData").getString("memberId"));
				model.addAttribute("epayCode", epayCode);
				model.addAttribute("memberName", responseJson.getJSONObject("resData").getString("memberName"));
				model.addAttribute("userId", openid);
			} else {
				model.addAttribute("resultMessage", responseJson.getString("returnMsg"));
				// return "debitNote/scanResult";
				return scanResult;
			}

		} else {
			model.addAttribute("resultMessage", "亲，不好意思，出错啦。");
			// return "debitNote/scanResult";
			return scanResult;
		}

		// return "/debitNote/index";
		return index;

	}

	@RequestMapping("/alipay/debitNoteAuthCallBack")
	public String aliPayDebitNoteAuthCallBack(Model model, HttpServletRequest request) {
		model.addAttribute("userAgentType", "alipay");

		String authCode = request.getParameter("auth_code");
		System.out.println("authCode===="+authCode);
		String oemName = request.getParameter("state");
		model.addAttribute("oemName", oemName);
		String register = "";
		String scanResult = "";
		String index = "";
		String state = "";

		try {
			// 3. 利用authCode获得authToken
			AlipaySystemOauthTokenRequest oauthTokenRequest = new AlipaySystemOauthTokenRequest();
			oauthTokenRequest.setCode(authCode);
			oauthTokenRequest.setGrantType(AlipayServiceEnvConstants.GRANT_TYPE);
			AlipayClient alipayClient = AlipayAPIClientFactory.getAlipayClient();
			AlipaySystemOauthTokenResponse oauthTokenResponse = alipayClient.execute(oauthTokenRequest);
			// 成功获得authToken
			if (null != oauthTokenResponse && oauthTokenResponse.isSuccess()) {
				// request.getSession().setAttribute("alipayUserId",
				// oauthTokenResponse.getUserId());
				System.out.println("userid========"+oauthTokenResponse.getUserId());
				String epayCode = request.getSession().getAttribute("epayCode").toString();
				JSONObject reqData = new JSONObject();
				reqData.put("epayCode", epayCode);

				if (oemName == null || oemName == "") {
					register = "redirect:/register?epayCode=" + epayCode;
					;
					scanResult = "debitNote/scanResult";
					index = "/debitNote/index";
					state = "";
				} else {
					register = "redirect:/register?oemName=" + oemName + "&epayCode=" + epayCode;
					;
					scanResult = oemName + "/" + "debitNote/scanResult";
					index = oemName + "/debitNote/index";
					state = oemName;
				}
				JSONObject responseJson = JSONObject.fromObject(
						HttpUtil.sendPostRequest(SysConfig.pospService + "/api/memberInfo/getMemberInfoByEpayCode",
								CommonUtil.createSecurityRequstData(reqData)));
				if ("0000".equals(responseJson.getString("returnCode"))) {
					String epayCodeStatus = responseJson.getJSONObject("resData").getString("epayCodeStatus");
					if ("3".equals(epayCodeStatus) || "6".equals(epayCodeStatus)) {
						// return "redirect:/register?epayCode=" + epayCode;
						return register;
					}
					if ("4".equals(epayCodeStatus)) {
						model.addAttribute("resultMessage", "亲，您的信息还在审核中，暂不能收款，通过之后我们将会短信提醒您。。。。。");
						// return "debitNote/scanResult";
						return scanResult;
					}

					model.addAttribute("memberId", responseJson.getJSONObject("resData").getString("memberId"));
					model.addAttribute("epayCode", epayCode);
					model.addAttribute("memberName", responseJson.getJSONObject("resData").getString("memberName"));
					model.addAttribute("userId", oauthTokenResponse.getUserId());
				} else {
					model.addAttribute("resultMessage", responseJson.getString("returnMsg"));
					// return "debitNote/scanResult";
					return scanResult;
				}

			} else {
				model.addAttribute("resultMessage", "亲，不好意思，出错啦。");
				// return "debitNote/scanResult";
				return scanResult;
			}
		} catch (AlipayApiException alipayApiException) {
			// 自行处理异常
			alipayApiException.printStackTrace();
			model.addAttribute("resultMessage", "亲，不好意思，出错啦。");
			// return "debitNote/scanResult";
			return scanResult;
		}

		// return "/debitNote/index";
		return index;
	}

	@ResponseBody
	@RequestMapping("/debitNote/pay")
	public JSONObject person(Model model, HttpServletRequest request) {

		String money = request.getParameter("money").toString();
		String epayCode = request.getParameter("epayCode").toString();
		String userId = request.getParameter("userId");
		String remark = request.getParameter("remark");
		JSONObject result = new JSONObject();
		try {
			if ("".equals(money)) {
				throw new ArgException("请输入正确的金额");
			}

			if ("".equals(epayCode)) {
				throw new ArgException("付款码出错");
			}
			
			if(StringUtils.isNotEmpty(remark)){
				if(remark.length() > 20){
					throw new ArgException("备注信息超出长度限制");
				}
			}

			JSONObject reqData = new JSONObject();
			reqData.put("money", money);
			reqData.put("epayCode", epayCode);
			reqData.put("userId", userId);
			reqData.put("remark", remark);

			String ua = request.getHeader("user-agent").toLowerCase();
			reqData.put("appClientType", "other");
			if (ua.indexOf("micromessenger") > 0) {
				reqData.put("appClientType", "micromessenger");
			} else if (ua.indexOf("alipay") > 0) {
				reqData.put("appClientType", "alipay");
			} else if (ua.indexOf("application=jdjr-app") > 0){//放在qq前面，因为ua里面有和qq相同的字符串
				reqData.put("appClientType", "jdjrpay");
			} else if (ua.indexOf("applewebkit") != -1 && ua.indexOf("qq") != -1) {
				reqData.put("appClientType", "mqqbrowser");
			} else if (ua.indexOf("baiduwallet") > 0) {// 百度钱包
				reqData.put("appClientType", "baiduwallet");
			}
			result = JSONObject.fromObject(HttpUtil.sendPostRequest(SysConfig.pospService + "/api/debitNote/pay",
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

	@RequestMapping("/debitNote/payCallBack")
	public String payCallBack(Model model, HttpServletRequest request) {

		JSONObject reqData = new JSONObject();
		String oemName = request.getParameter("oemName");
		model.addAttribute("oemName", oemName);
		String scanResultSuccess = "";
		String scanResult = "";

		if (oemName == null || oemName == "") {
			scanResultSuccess = "debitNote/scanResultSuccess";
			scanResult = "debitNote/scanResult";
		} else {
			scanResultSuccess = oemName + "/"+"debitNote/scanResultSuccess";
			scanResult = oemName + "/" + "debitNote/scanResult";
		}
		reqData.put("orderCode", request.getParameter("orderCode"));
		JSONObject result = JSONObject
				.fromObject(HttpUtil.sendPostRequest(SysConfig.pospService + "/api/debitNote/msTransactionQuery",
						CommonUtil.createSecurityRequstData(reqData)));
		// JSONObject result =
		// JSONObject.fromObject("{'returnCode':'0000','certNbr':'350526198105077512','resData':{'oriRespType':'S','oriRespCode':'000000','totalAmount':'123'}}");
		String resultMessage = "";
		String totalAmount = "0";
		boolean flag = false;
		if ("0000".equals(result.getString("returnCode"))) {
			JSONObject resData = result.getJSONObject("resData");
			if ("S".equals(resData.getString("oriRespType")) && "000000".equals(resData.getString("oriRespCode"))) {
				resultMessage = "恭喜您！支付成功！";
				totalAmount = resData.getString("totalAmount");
//				if (oemName == null || oemName == "") {
					Map<String, String> map = HttpUtil.getAD(result.getString("certNbr"), totalAmount,oemName);
					model.addAttribute("ImageUrl", map.get("ImageUrl"));
					model.addAttribute("Href", map.get("Href"));
//				}
				flag = true;
			} else {
				if ("R".equals(resData.getString("oriRespType"))) {
					resultMessage = "您的支付正处理中.......";
					model.addAttribute("oriRespType", "R");
					model.addAttribute("refresh", request.getParameter("refresh"));
				} else {
					resultMessage = "亲，支付失败啦，请重新支付!";
				}
			}
		} else {
			resultMessage = "交易不确定!";
		}

		// model.addAttribute("shortName", memberInfo.getShortName());
		model.addAttribute("totalAmount", totalAmount);
		model.addAttribute("resultMessage", resultMessage);
		model.addAttribute("orderCode", request.getParameter("orderCode"));
		if (flag) {
			// return "debitNote/scanResultSuccess";
			return scanResultSuccess;
		} else {
			// return "debitNote/scanResult";
			return scanResult;
		}

	}

	@RequestMapping("/debitNote/msNotify")
	public String msNotify(HttpServletRequest request) {
		String notifyStr = HttpUtil.getPostString(request);
		return "debitNote/scanResult";

	}

	@RequestMapping("/debitNote/getQrCode")
	public String getQrCode(Model model, HttpServletRequest request) {
		String payType = request.getParameter("payType");
		String oemName = request.getParameter("oemName");
		model.addAttribute("oemName", oemName);
		model.addAttribute("qrCode", request.getParameter("qrCode"));
		String qrPayCode = "debitNote/qrCode";
		if ("3".equals(payType)) {
			qrPayCode = "debitNote/qqPayCode";
		} else if ("4".equals(payType)) {
			qrPayCode = "debitNote/bdPayCode";
		} else if("5".equals(payType)){
			qrPayCode = "debitNote/jdPayCode";
		}
		if (null != oemName && !"".equals(oemName)) {
			qrPayCode = oemName + "/" + qrPayCode;
		}
		return qrPayCode;

		// String oemName=request.getParameter("oemName");
		// model.addAttribute("oemName", oemName);
		// String qqPayCode="";
		// String qrCode="";
		//
		// if (oemName==null || oemName==""){
		// qqPayCode="debitNote/qqPayCode";
		// qrCode="debitNote/qrCode";
		// }else{
		// qqPayCode=oemName+"/debitNote/qqPayCode";
		// qrCode=oemName+"/"+"debitNote/qrCode";
		// }
		// model.addAttribute("qrCode",request.getParameter("qrCode"));
		// String payType = request.getParameter("payType");
		//
		// if("3".equals(payType)){
		// //return "debitNote/qqPayCode";
		// return qqPayCode;
		// }
		// //return "debitNote/qrCode";
		// return qrCode;

	}

	/**
	 * 选择卡页面
	 */
	@RequestMapping("/debitNote/chooseCard")
	public String chooseCard(Model model, HttpServletRequest request, HttpServletResponse response) {

		MemberInfo memberInfo = SessionUtils.getMemberInfoSession(request);
		try {
			if (memberInfo == null) {
				response.sendRedirect(request.getContextPath() + "/login");
				return null;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		JSONObject reqData = new JSONObject();
		String scanResultSuccess = "";
		String scanResult = "";

		scanResultSuccess = "debitNote/chooseCard";
		scanResult = "debitNote/bindCard";
		reqData.put("memberId", memberInfo.getId());

		JSONObject result = JSONObject
				.fromObject(HttpUtil.sendPostRequest(SysConfig.pospService + "/api/memberInfo/getMemberBindAccList",
						CommonUtil.createSecurityRequstData(reqData)));
		// JSONObject result =
		// JSONObject.fromObject("{'returnCode':'0000','resData':{'binAccCnt':1,'memberBindAccList':[{'memberId':7900,'acc':'1234567890123456','bankName':'中国建设银行'}]}}");
		System.out.println(result);
		String resultMessage = "";
		String totalAmount = "0";
		boolean flag = false;
		if ("0000".equals(result.getString("returnCode"))) {
			JSONObject resData = result.getJSONObject("resData");
			if (resData.getInt("binAccCnt") > 0) {// 存在绑卡记录，跳转到选择卡界面，否则跳转到绑定卡界面
				flag = true;
				model.addAttribute("resData", resData);
			} else {
				flag = false;
			}
		} else {
			resultMessage = "请求出错";
		}
		model.addAttribute("totalAmount", totalAmount);
		model.addAttribute("resultMessage", resultMessage);
		if (flag) {
			return scanResultSuccess;
		} else {
			return scanResult;
		}
	}

	/**
	 * 绑定卡页面
	 */
	@RequestMapping("/debitNote/bindCard")
	public String bindCard(Model model, HttpServletRequest request) {
		return "debitNote/bindCard";
	}

	/**
	 * 绑定卡操作
	 */
	@ResponseBody
	@RequestMapping("/debitNote/toBindCard")
	public String toBindCard(Model model, HttpServletRequest request) {

		JSONObject reqData = new JSONObject();
		JSONObject memberBindAcc = new JSONObject();

		MemberInfo memberInfo = SessionUtils.getMemberInfoSession(request);
		com.epay.scanpay.common.utils.JsonBeanReleaseUtil.beanToJson(memberInfo, "yyyy-MM-dd HH:mm:ss");
		memberBindAcc.put("memberId", memberInfo.getId());
		memberBindAcc.put("acc", request.getParameter("cardNbr"));
		memberBindAcc.put("bankId", request.getParameter("bankId"));
		memberBindAcc.put("name", request.getParameter("name"));
		memberBindAcc.put("mobilePhone", request.getParameter("phone"));
		reqData.put("memberBindAcc", memberBindAcc);
		JSONObject result = JSONObject
				.fromObject(HttpUtil.sendPostRequest(SysConfig.pospService + "/api/memberInfo/memberBindAcc",
						CommonUtil.createSecurityRequstData(reqData)));
		// JSONObject result = JSONObject.fromObject("{'returnCode':'0000'}");
		System.out.println(result);
		return result.toString();
	}

	/**
	 * 快捷支付页面
	 */
	@RequestMapping("/debitNote/miPayPage")
	public String miPayPage(Model model, HttpServletRequest request) {
		MemberInfo memberInfo = SessionUtils.getMemberInfoSession(request);
		if (memberInfo == null) {
			return "redirect:/login";
		}
		String acc = request.getParameter("zffs");
		model.addAttribute("acc", acc);
		model.addAttribute("memberName", memberInfo.getName());

		return "debitNote/indexQuick";
	}

	/**
	 * 快捷支付操作
	 */
	@RequestMapping("/debitNote/miPay")
	public String miPay(Model model, HttpServletRequest request) {

		JSONObject reqData = new JSONObject();
		JSONObject mlTradeDetail = new JSONObject();

		MemberInfo memberInfo = SessionUtils.getMemberInfoSession(request);
		
		System.out.println("memberInfo==="+com.epay.scanpay.common.utils.JsonBeanReleaseUtil.beanToJson(memberInfo, "yyyy-MM-dd HH:mm:ss"));
		System.out.println("memberInfo==="+memberInfo.getId());
		mlTradeDetail.put("memberId", memberInfo.getId());
		mlTradeDetail.put("isJf", request.getParameter("isJf"));
		mlTradeDetail.put("acc", request.getParameter("acc"));
		mlTradeDetail.put("money", request.getParameter("money"));
		mlTradeDetail.put("remarks", request.getParameter("remark"));
		reqData.put("mlTradeDetail", mlTradeDetail);
		JSONObject result = JSONObject
				.fromObject(HttpUtil.sendPostRequest(SysConfig.pospService + "/api/memberInfo/memberQuickPay",
						CommonUtil.createSecurityRequstData(reqData)));
		// JSONObject result =
		// JSONObject.fromObject("{'returnCode':'0000','resData':{'memberQuickPay':{'ORDER_ID':'Test20170508162658068','ORDER_AMT':'0.01','ORDER_TIME':'20170508162658','PAY_TYPE':'13','USER_TYPE':'02','BG_URL':'http://123.182.255.83:8090/NMobPay/yzf002.xml','PAGE_URL':'http://123.182.255.83:8090/NMobPay/yzf002.xml','USER_ID':'990581000011021','SIGN':'BB655DAE598ED853','SIGN_TYPE':'03','BUS_CODE':'3101','CCT':'CNY','GOODS_NAME':'测试商品','GOODS_DESC':'测试商品'}}}");
		System.out.println("result.toString()=="+result.toString());
		model.addAttribute("resData", result.get("resData"));
		  if(!result.opt("returnCode").equals("0000"))  {
			  model.addAttribute("returnCode", result.opt("returnCode"));
			  model.addAttribute("returnMsg", result.opt("returnMsg"));
			  return "debitNote/scanFaile";
		  }else{		
			  return "debitNote/quickPay";
		  }
	}

	/**
	 * 快捷支付回调前台页面
	 */
	@RequestMapping("/debitNote/miPayBack")
	public String miPayBack(Model model, HttpServletRequest request, HttpServletResponse response) {

		String resultMessage = "";
		String totalAmount = "0";
		JSONObject reqData = new JSONObject();
		boolean flag = false;
		String retXml = "";
		String RESP_DESC = null;
		JSONObject req = new JSONObject();
		JSONObject result = new JSONObject();
		String oemName = request.getParameter("oemName");
		String res="";
		try {

			String ORDER_ID = request.getParameter("ORDER_ID");
			String ORDER_AMT = request.getParameter("ORDER_AMT");
			String REF_NO = request.getParameter("REF_NO");
			String PAYCH_TIME = request.getParameter("PAYCH_TIME");
			String ORDER_TIME = request.getParameter("ORDER_TIME");// 订单提交时间
			String PAY_AMOUNT = request.getParameter("PAY_AMOUNT");
			String RESP_CODE = request.getParameter("RESP_CODE");
			RESP_DESC = request.getParameter("RESP_DESC");
			if (!(RESP_DESC == null)) {
				RESP_DESC = URLDecoder.decode(RESP_DESC, "utf-8");
			}
			String USER_ID = request.getParameter("USER_ID");
			String SIGN = request.getParameter("SIGN");
			String SIGN_TYPE = request.getParameter("SIGN_TYPE");
			String BUS_CODE = request.getParameter("BUS_CODE");
			String CNY = request.getParameter("CCT");
			String ADD5 = request.getParameter("ADD5");
			String ADD1 = request.getParameter("ADD1");
			String ADD2 = request.getParameter("ADD2");
			String ADD3 = request.getParameter("ADD3");
			String ADD4 = request.getParameter("ADD4");

			/*
			 * System.out.println("ORDER_ID="+ORDER_ID);
			 * System.out.println("ORDER_AMT="+ORDER_AMT);
			 * System.out.println("REF_NO="+REF_NO);
			 * System.out.println("PAYCH_TIME="+PAYCH_TIME);
			 * System.out.println("ORDER_TIME="+ORDER_TIME);
			 * System.out.println("RESP_CODE="+RESP_CODE);
			 * System.out.println("PAY_AMOUNT="+PAY_AMOUNT);
			 * System.out.println("RESP_DESC="+RESP_DESC);
			 * System.out.println("USER_ID="+USER_ID);
			 * System.out.println("SIGN="+SIGN);
			 * System.out.println("SIGN_TYPE="+SIGN_TYPE);
			 * System.out.println("BUS_CODE="+BUS_CODE);
			 * System.out.println("CNY="+CNY); System.out.println("ADD1="+ADD1);
			 * System.out.println("ADD2="+ADD2);
			 * System.out.println("ADD3="+ADD3);
			 * System.out.println("ADD4="+ADD4);
			 * System.out.println("ADD5="+ADD5);
			 */
			reqData.put("orderId", ORDER_ID);
			/******判断该订单是否第三方接入******************/
			result = JSONObject.fromObject(HttpUtil.sendPostRequest(SysConfig.pospService + "/api/memberInfo/st/miPayBack", CommonUtil.createSecurityRequstData(reqData)));
			if("0000".equals(result.opt("returnCode"))){
				req=result.getJSONObject("resData").getJSONObject("stTradeDetail");
				res=req.optString("pageUrl").toString()
						+  "?orderNum="+req.optString("orderNum").toString()
						+  "&orderAmt="+req.optString("orderAmt").toString() 
						+  "&payType="+req.optString("payType").toString()
						+  "&bgUrl="+req.optString("bgUrl").toString()
						+  "&pageUrl="+req.optString("pageUrl").toString()
						+  "&memberCode="+req.optString("memberCode").toString()
						+  "&goodsName="+req.optString("goodsName").toString()
						+  "&goodsDesc="+req.optString("goodsDesc").toString()
						+  "&accNo="+req.optString("accNo").toString()
						+  "&add1="+req.optString("add1").toString()
						+  "&add2="+req.optString("add2").toString()
						+  "&add3="+req.optString("add3").toString()
						+  "&add4="+req.optString("add4").toString()
						+  "&returnCode="+RESP_CODE
						+  "&returnMsg="+RESP_DESC;
				return "redirect:"+res;
			}
			if ("0000".equals(RESP_CODE)) {
				resultMessage = "恭喜您！支付成功！";
				// 根据memberId获取商户信息
				
				 result = JSONObject.fromObject(HttpUtil.sendPostRequest(SysConfig.pospService + "/api/memberInfo/miPayBack",
								CommonUtil.createSecurityRequstData(reqData)));
				if ("0000".equals(result.getString("returnCode"))) {
					JSONObject resData = result.getJSONObject("resData");
					resData = resData.getJSONObject("memberInfoMore");
					Map<String, String> map = HttpUtil.getAD(resData.getString("certNbr"), totalAmount,oemName);
					model.addAttribute("ImageUrl", map.get("ImageUrl"));
					model.addAttribute("Href", map.get("Href"));
				}
				flag = true;
			} else {
				flag = false;
				resultMessage = RESP_DESC;
			}
			model.addAttribute("totalAmount", ORDER_AMT);
			model.addAttribute("resultMessage", resultMessage);
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			model.addAttribute("resultMessage", "支付失败");
		}
		if (flag) {
			return "debitNote/scanResultSuccess";
		} else {
			return "debitNote/scanResult";
		}
	}

	/**
	 * 提供第三快捷支付回调页面
	 */
	@RequestMapping("/debitNote/st/miPayBack")
	public String stmiPayBack(Model model, HttpServletRequest request,HttpServletResponse response) {
		
		String resultMessage="";
		String totalAmount = "0";
		JSONObject reqData = new JSONObject();
		JSONObject req = new JSONObject();
		boolean flag = false; 
		String RESP_DESC = null;
		String res="";
		try {
			
			
			String ORDER_ID	  =request.getParameter("ORDER_ID"); 
			String ORDER_AMT =	request.getParameter("ORDER_AMT");  
			String REF_NO	    = request.getParameter("REF_NO");
			String PAYCH_TIME=request.getParameter("PAYCH_TIME");
			String ORDER_TIME =request.getParameter("ORDER_TIME");//订单提交时间
			String PAY_AMOUNT =request.getParameter("PAY_AMOUNT");	 
			String RESP_CODE  =request.getParameter("RESP_CODE");
			RESP_DESC=request.getParameter("RESP_DESC");
			if(!(RESP_DESC==null)){
			  RESP_DESC =  URLDecoder.decode(RESP_DESC,"utf-8");
			}
			String USER_ID	  = request.getParameter("USER_ID");			
			String SIGN	     =request.getParameter("SIGN");
			String SIGN_TYPE	 =request.getParameter("SIGN_TYPE"); 
			String BUS_CODE =	request.getParameter("BUS_CODE");
			String CNY	     =  request.getParameter("CCT");
			String ADD5	    = request.getParameter("ADD5");
			String ADD1	   =  request.getParameter("ADD1");                       
			String ADD2	   =  request.getParameter("ADD2");                       
			String ADD3	   =  request.getParameter("ADD3");                       
			String ADD4	   =  request.getParameter("ADD4");  			

			reqData.put("orderId", ORDER_ID);
			JSONObject result = JSONObject.fromObject(HttpUtil.sendPostRequest(SysConfig.pospService + "/api/memberInfo/st/miPayBack", CommonUtil.createSecurityRequstData(reqData)));
			if("0000".equals(result.opt("returnCode"))){
				req=result.getJSONObject("stTradeDetail");
				res=req.optString("pageUrl").toString()
						+  "?orderNum="+req.optString("orderNum").toString()
						+  "&orderAmt="+req.optString("orderAmt").toString() 
						+  "&payType="+req.optString("payType").toString()
						+  "&bgUrl="+req.optString("bgUrl").toString()
						+  "&pageUrl="+req.optString("pageUrl").toString()
						+  "&memberCode="+req.optString("memberCode").toString()
						+  "&goodsName="+req.optString("goodsName").toString()
						+  "&goodsDesc="+req.optString("goodsDesc").toString()
						+  "&accNo="+req.optString("accNo").toString()
						+  "&add1="+req.optString("add1").toString()
						+  "&add2="+req.optString("add2").toString()
						+  "&add3="+req.optString("add3").toString()
						+  "&add4="+req.optString("add4").toString()
						+  "&returnCode="+RESP_CODE
						+  "&returnMsg="+RESP_DESC;
			}
		
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace(); 
		}
		return "redirect:"+res;
	 
	}
	
	private String getRouteCode(Integer memberId,String payMethod,String payType){
		String routeCode = SysConfig.channel;
		try{
			JSONObject reqData = new JSONObject();
			reqData.put("memberId", memberId);
			reqData.put("payMethod", payMethod);
			reqData.put("payType", payType);
			JSONObject result = JSONObject.fromObject(HttpUtil.sendPostRequest(SysConfig.pospService + "/api/common/getRouteCode", CommonUtil.createSecurityRequstData(reqData)));
			if("0000".equals(result.getString("returnCode"))){
				routeCode = result.getString("resData");
			}
		}catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace(); 
		}
		return routeCode;
	}

}
