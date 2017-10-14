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

import com.epay.scanpay.common.constant.SysConfig;
import com.epay.scanpay.common.constant.WxConfig;
import com.epay.scanpay.common.utils.Base64Utils;
import com.epay.scanpay.common.utils.CommonUtil;
import com.epay.scanpay.common.utils.HttpUtil;
import com.epay.scanpay.common.utils.SessionUtils;
import com.epay.scanpay.entity.MemberInfo;

@Controller
public class WeixinMenuController {
	private static Logger logger = LoggerFactory.getLogger(WeixinMenuController.class);
	
	@RequestMapping("/weixin/menuRedirect")
	public String menuRedirect(Model model, HttpServletRequest request) {
		try {
			String urlReturn = request.getParameter("refer");
			if(null == urlReturn || "".equals(urlReturn)){
				return null;
			}
			String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() ;
			MemberInfo memberInfo = SessionUtils.getMemberInfoSession(request);
			if(memberInfo != null){
				String urlReturnDecode = Base64Utils.getFromBASE64NoWrap(urlReturn);
				return "redirect:" + urlReturnDecode;
			}else{
				String weixinCallbackUrl = SysConfig.payService + "/weixin/menuCallback?refer=" + urlReturn;
				//String weixinCallbackUrl = "http://rkt.topepay.com/scanPay/weixin/menuCallback";
				//String weixinCallbackUrl = baseUrl + "/weixin/menuCallback?refer=" + urlReturn;
				weixinCallbackUrl = URLEncoder.encode(weixinCallbackUrl, "utf-8");
				String weixinCodeUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + WxConfig.appid + "&redirect_uri=" + weixinCallbackUrl 
						+ "&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect";
				return "redirect:" + weixinCodeUrl;
			}
		
		} catch (UnsupportedEncodingException e) {
			logger.info(e.getMessage());
			e.printStackTrace();
			return null;
		}	
	}

	@RequestMapping("/weixin/menuCallback")
	public String wxDebitNoteAuthCallBack(Model model, HttpServletRequest request) {
		String code = request.getParameter("code");
		String referUrl = request.getParameter("refer");
		if (code != null && !"".equals(code)) {
			String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + WxConfig.appid + "&secret=" + WxConfig.appSecret + "&code=" + code + "&grant_type=authorization_code";
			String jsonStr = HttpUtil.sendGetRequest(url);// 根据code获取用户openId
			JSONObject resJson = JSONObject.fromObject(jsonStr);
			if (resJson.has("errcode")) {// 请求出错跳到首页
				return null;
			}
			String openid = resJson.getString("openid");
			JSONObject reqData=new JSONObject();
			reqData.put("openid", openid);
			JSONObject result = JSONObject.fromObject(HttpUtil.sendPostRequest(SysConfig.pospService+"/api/common/checkAutoLogin", CommonUtil.createSecurityRequstData(reqData)));
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
		}
		return null;
	}
	
}
