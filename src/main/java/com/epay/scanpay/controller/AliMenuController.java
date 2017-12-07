package com.epay.scanpay.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipaySystemOauthTokenRequest;
import com.alipay.api.response.AlipaySystemOauthTokenResponse;
import com.epay.scanpay.common.constant.AlipayServiceEnvConstants;
import com.epay.scanpay.common.constant.SysConfig;
import com.epay.scanpay.common.utils.AlipayAPIClientFactory;
import com.epay.scanpay.common.utils.Base64Utils;
import com.epay.scanpay.common.utils.CommonUtil;
import com.epay.scanpay.common.utils.HttpUtil;
import com.epay.scanpay.common.utils.SessionUtils;
import com.epay.scanpay.entity.MemberInfo;

@Controller
public class AliMenuController {
	private static Logger logger = LoggerFactory.getLogger(AliMenuController.class);
	
	@RequestMapping("/ali/menuRedirect")
	public String menuRedirect(Model model, HttpServletRequest request) {
		try {
			String urlReturn = request.getParameter("refer");
			if(null == urlReturn || "".equals(urlReturn)){
				return null;
			}
			MemberInfo memberInfo = SessionUtils.getMemberInfoSession(request);
			if(memberInfo != null){
				String urlReturnDecode = Base64Utils.getFromBASE64NoWrap(urlReturn);
				return "redirect:" + urlReturnDecode;
			}else{
				String aliCallbackUrl = SysConfig.payService + "/ali/menuCallback?refer=" + urlReturn;
				aliCallbackUrl = URLEncoder.encode(aliCallbackUrl, "utf-8");
				String aliCodeUrl = "redirect:https://openauth.alipay.com/oauth2/publicAppAuthorize.htm?app_id="
						+ AlipayServiceEnvConstants.APP_ID + "&state=STATE&scope=auth_base&redirect_uri="
						+ aliCallbackUrl;
				return  aliCodeUrl;
			}
		
		} catch (UnsupportedEncodingException e) {
			logger.info(e.getMessage());
			e.printStackTrace();
			return null;
		}	
	}

	@RequestMapping("/ali/menuCallback")
	public String wxDebitNoteAuthCallBack(Model model, HttpServletRequest request) {
		
		String authCode = request.getParameter("auth_code");
		System.out.println("authCode===="+authCode);
		String referUrl = request.getParameter("refer");
		
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
				String openid = oauthTokenResponse.getUserId();
				System.out.println("userid========"+openid);
				JSONObject reqData=new JSONObject();
				reqData.put("openid", openid);
				JSONObject result = JSONObject.fromObject(HttpUtil.sendPostRequest(SysConfig.pospService+"/api/common/checkAliAutoLogin", CommonUtil.createSecurityRequstData(reqData)));
				if("0000".equals(result.getString("returnCode"))){
					JSONObject resDataObject = result.getJSONObject("resData");
					if("1".equals(resDataObject.getString("loginFlag")) && null != resDataObject.getJSONObject("memberInfo")){
						SessionUtils.addMemberInfoSession(request, resDataObject.getJSONObject("memberInfo"));
						return "redirect:" + Base64Utils.getFromBASE64NoWrap(referUrl);
					}else{
						request.getSession().setAttribute("openid", openid);// 将openId保存到session
						model.addAttribute("openid", openid);
						return "login";
					}
				}

			} else {
				return null;
			}
		} catch (AlipayApiException alipayApiException) {
			// 自行处理异常
			alipayApiException.printStackTrace();
			model.addAttribute("resultMessage", "亲，不好意思，出错啦。");
			// return "debitNote/scanResult";
		}
		return null;
	}
	
}
