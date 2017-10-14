package com.epay.scanpay.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.epay.scanpay.common.constant.SysConfig;
import com.epay.scanpay.common.excep.ArgException;
import com.epay.scanpay.common.utils.CommonUtil;
import com.epay.scanpay.common.utils.HttpUtil;
import com.epay.scanpay.common.utils.SessionUtils;
import com.epay.scanpay.entity.MemberInfo;

@Controller
public class FeeRateController {
	private static Logger logger = LoggerFactory.getLogger(FeeRateController.class);
	
	@RequestMapping("/feeRate/limitMoney")
	public ModelAndView limitMoney(Model model, HttpServletRequest request,HttpServletResponse response) {
		return new ModelAndView("feeRate/limitMoney");
	}
	
	@RequestMapping("/feeRate/index")
	public ModelAndView index(Model model, HttpServletRequest request,HttpServletResponse response) {
		JSONObject result=new JSONObject();
		JSONObject reqData=new JSONObject();
		MemberInfo memberInfo = SessionUtils.getMemberInfoSession(request);
		try{
			if(memberInfo == null){
				result.put("returnCode", "4004");
				result.put("returnMsg", "请先登陆");
				response.sendRedirect(request.getContextPath()+"/login");
				return null;
			}
			reqData.put("memberId", memberInfo.getId());
		}catch(Exception e){
			e.printStackTrace();
			logger.info("查询费率信息出错");
			return null;
		}
		result=JSONObject.fromObject(HttpUtil.sendPostRequest(SysConfig.pospService+"/api/feeRate/getRate", CommonUtil.createSecurityRequstData(reqData)));
		return new ModelAndView("feeRate/index",(Map<String,Object>)result);

	}
	
	
}
