package com.epay.scanpay.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.util.HashMap;
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
import com.epay.scanpay.common.utils.CommonUtil;
import com.epay.scanpay.common.utils.HttpUtil;
import com.epay.scanpay.common.utils.PageInitUtil;
import com.epay.scanpay.common.utils.SessionUtils;
import com.epay.scanpay.entity.MemberInfo;

@Controller
public class MemberInfoController {
	private static Logger logger = LoggerFactory.getLogger(MemberInfoController.class);
	
	@RequestMapping("/memberInfo/memberInfo")
	public ModelAndView index(Model model, HttpServletRequest request,HttpServletResponse response) {
		JSONObject result=new JSONObject();
		try {
			JSONObject reqData=new JSONObject();
			MemberInfo memberInfo = SessionUtils.getMemberInfoSession(request);
			if(memberInfo == null){
				result.put("returnCode", "4004");
				result.put("returnMsg", "请先登陆");
				response.sendRedirect(request.getContextPath()+"/login");
				return null;
			}
			reqData.put("memberId", memberInfo.getId());
			System.out.println(JSONObject.fromObject(memberInfo).toString());
			result=JSONObject.fromObject(HttpUtil.sendPostRequest(SysConfig.pospService+"/api/memberInfo/memberInfo", CommonUtil.createSecurityRequstData(reqData)));
			return new ModelAndView("memberInfo/memberInfo", (Map<String,Object>)result);
		} catch (IOException e) {
			e.printStackTrace();
			logger.info("查询会员信息出错");
			return null;
		}

	}
	
	@RequestMapping("/memberInfo/feeRate")
	public ModelAndView feeRate(Model model, HttpServletRequest request,HttpServletResponse response) {
		JSONObject result=new JSONObject();
		try {
			JSONObject reqData=new JSONObject();
			MemberInfo memberInfo = SessionUtils.getMemberInfoSession(request);
			if(memberInfo == null){
				result.put("returnCode", "4004");
				result.put("returnMsg", "请先登陆");
				response.sendRedirect(request.getContextPath()+"/login");
				return null;
			}
			reqData.put("memberId", memberInfo.getId());
			System.out.println(JSONObject.fromObject(memberInfo).toString());
			result=JSONObject.fromObject(HttpUtil.sendPostRequest(SysConfig.pospService+"/api/memberInfo/feeRate", CommonUtil.createSecurityRequstData(reqData)));
			return new ModelAndView("memberInfo/feeRate", (Map<String,Object>)result);
		} catch (IOException e) {
			e.printStackTrace();
			logger.info("查询会员信息出错");
			return null;
		}
		
	}
	
	@RequestMapping("/memberInfo/showChannel")
	public ModelAndView showChannel(Model model, HttpServletRequest request,HttpServletResponse response) {
		JSONObject result=new JSONObject();
		try {
			JSONObject reqData=new JSONObject();
			MemberInfo memberInfo = SessionUtils.getMemberInfoSession(request);
			if(memberInfo == null){
				result.put("returnCode", "4004");
				result.put("returnMsg", "请先登陆");
				response.sendRedirect(request.getContextPath()+"/login");
				return null;
			}
			reqData.put("memberId", memberInfo.getId());
			System.out.println(JSONObject.fromObject(memberInfo).toString());
			result=JSONObject.fromObject(HttpUtil.sendPostRequest(SysConfig.pospService+"/api/memberInfo/showChannel", CommonUtil.createSecurityRequstData(reqData)));
			return new ModelAndView("memberInfo/showChannel", (Map<String,Object>)result);
		} catch (IOException e) {
			e.printStackTrace();
			logger.info("查询会员信息出错");
			return null;
		}
		
	}
	
	@RequestMapping("/memberInfo/memberCenter")
	public ModelAndView memberCenter(Model model, HttpServletRequest request,HttpServletResponse response) {
		JSONObject result=new JSONObject();
		try {
			JSONObject reqData=new JSONObject();
			MemberInfo memberInfo = SessionUtils.getMemberInfoSession(request);
			if(memberInfo == null){
				result.put("returnCode", "4004");
				result.put("returnMsg", "请先登陆");
				response.sendRedirect(request.getContextPath()+"/login");
				return null;
			}
			reqData.put("memberId", memberInfo.getId());
			System.out.println(JSONObject.fromObject(memberInfo).toString());
			result=JSONObject.fromObject(HttpUtil.sendPostRequest(SysConfig.pospService+"/api/memberInfo/memberCenter", CommonUtil.createSecurityRequstData(reqData)));
			return new ModelAndView("memberInfo/memberCenter", (Map<String,Object>)result);
		} catch (IOException e) {
			e.printStackTrace();
			logger.info("查询会员信息出错");
			return null;
		}
		
	}
	
	@RequestMapping("/memberInfo/transactionLog")
	public ModelAndView transactionLog(Model model, HttpServletRequest request,HttpServletResponse response) {
		JSONObject result=new JSONObject();
		try {
			JSONObject reqData=new JSONObject();
			MemberInfo memberInfo = SessionUtils.getMemberInfoSession(request);
			if(memberInfo == null){
				result.put("returnCode", "4004");
				result.put("returnMsg", "请先登陆");
				response.sendRedirect(request.getContextPath()+"/login");
				return null;
			}
			PageInitUtil.init(request);
			reqData.put("memberId", memberInfo.getId());
			reqData.put("limitStart", PageInitUtil.getLimitStart());
			reqData.put("limitSize", PageInitUtil.getLimitSize());
			String dataType = "1";
			if(request.getParameter("dataType") != null){
				dataType = request.getParameter("dataType");
			}
			reqData.put("dataType", dataType);
			
			System.out.println(JSONObject.fromObject(memberInfo).toString());
			result=JSONObject.fromObject(HttpUtil.sendPostRequest(SysConfig.pospService+"/api/memberInfo/transactionLog", CommonUtil.createSecurityRequstData(reqData)));
			return new ModelAndView("memberInfo/transactionLog", (Map<String,Object>)result);
		} catch (IOException e) {
			e.printStackTrace();
			logger.info("查询交易信息出错");
			return null;
		}

	}
	
	@ResponseBody
	@RequestMapping("/memberInfo/transactionLogData")
	public String transactionLogData(Model model, HttpServletRequest request,HttpServletResponse response) {
		JSONObject result=new JSONObject();
		try {
			JSONObject reqData=new JSONObject();
			MemberInfo memberInfo = SessionUtils.getMemberInfoSession(request);
			if(memberInfo == null){
				result.put("returnCode", "4004");
				result.put("returnMsg", "请先登陆");
				return result.toString();
			}
			PageInitUtil.init(request);
			reqData.put("memberId", memberInfo.getId());
			reqData.put("limitStart", PageInitUtil.getLimitStart());
			reqData.put("limitSize", PageInitUtil.getLimitSize());
			String dataType = "1";
			if(request.getParameter("dataType") != null){
				dataType = request.getParameter("dataType");
			}
			reqData.put("dataType", dataType);
			
			System.out.println(JSONObject.fromObject(memberInfo).toString());
			result=JSONObject.fromObject(HttpUtil.sendPostRequest(SysConfig.pospService+"/api/memberInfo/transactionLog", CommonUtil.createSecurityRequstData(reqData)));
			return result.toString();
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("查询交易信息出错");
			result.put("returnCode", "4004");
			result.put("returnMsg", "查询交易信息出错，请重试");
			return result.toString();
		}

	}
	
	@RequestMapping("/memberInfo/QRCode")
	public ModelAndView QRCode(Model model, HttpServletRequest request,HttpServletResponse response) {
		JSONObject result=new JSONObject();
		try {
			JSONObject reqData=new JSONObject();
			MemberInfo memberInfo = SessionUtils.getMemberInfoSession(request);
			if(memberInfo == null){
				result.put("returnCode", "4004");
				result.put("returnMsg", "请先登陆");
				response.sendRedirect(request.getContextPath()+"/login");
				return null;
			}
			reqData.put("memberId", memberInfo.getId());
			result=JSONObject.fromObject(HttpUtil.sendPostRequest(SysConfig.pospService+"/api/memberInfo/getMemberQRCodeInfo", CommonUtil.createSecurityRequstData(reqData)));
			return new ModelAndView("memberInfo/QRCode",(Map<String,Object>) result);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("查询交易信息出错");
			result.put("returnCode", "4004");
			result.put("returnMsg", "获取二维码信息出错，请重试");
			return null;
		}

	}
	
	@ResponseBody
	@RequestMapping("/memberInfo/modifyPassword")
	public ModelAndView modifyPassword(Model model, HttpServletRequest request,HttpServletResponse response) {
		MemberInfo memberInfo = SessionUtils.getMemberInfoSession(request);
		if(memberInfo==null){
			return new ModelAndView("login");
		}
		return new ModelAndView("memberInfo/modifyPassword");
	}
	
	@ResponseBody
	@RequestMapping("/memberInfo/modifyPwd")
	public String modifyPwd(Model model, HttpServletRequest request,HttpServletResponse response) {
		MemberInfo memberInfo = SessionUtils.getMemberInfoSession(request);
		if(memberInfo==null){
			return "login";
		}
		String oldPassword = request.getParameter("oldPassword");
		String newPassword = request.getParameter("newPassword");
		String confirmPassword = request.getParameter("confirmPassword");
		JSONObject result = new JSONObject();
		JSONObject reqData=new JSONObject();
		reqData.put("memberId", memberInfo.getId());
		reqData.put("oldPassword", oldPassword);
		reqData.put("newPassword", newPassword);
		reqData.put("confirmPassword", confirmPassword);
		result=JSONObject.fromObject(HttpUtil.sendPostRequest(SysConfig.pospService+"/api/memberInfo/modifyPwd", CommonUtil.createSecurityRequstData(reqData)));
		
		return result.toString();
	}
	
	
	@ResponseBody
	@RequestMapping("/memberInfo/draw")
	public ModelAndView draw(Model model, HttpServletRequest request,HttpServletResponse response) {
		MemberInfo memberInfo = SessionUtils.getMemberInfoSession(request);
		if(memberInfo==null){
			return new ModelAndView("login");
		}
		PageInitUtil.init(request);
		JSONObject result = new JSONObject();
		JSONObject reqData=new JSONObject();
		reqData.put("memberId", memberInfo.getId());
		result=JSONObject.fromObject(HttpUtil.sendPostRequest(SysConfig.pospService+"/api/memberInfo/draw", CommonUtil.createSecurityRequstData(reqData)));
		
		return new ModelAndView("memberInfo/draw",result);
	}
	
	@ResponseBody
	@RequestMapping("/memberInfo/checkToDraw")
	public String checkToDraw(Model model, HttpServletRequest request,HttpServletResponse response) {
		MemberInfo memberInfo = SessionUtils.getMemberInfoSession(request);
		if(memberInfo==null){
			return "login";
		}
		JSONObject result = new JSONObject();
		JSONObject reqData=new JSONObject();
		reqData.put("memberId", memberInfo.getId());
		result=JSONObject.fromObject(HttpUtil.sendPostRequest(SysConfig.pospService+"/api/memberInfo/checkToDraw", CommonUtil.createSecurityRequstData(reqData)));
//		result.put("returnCode", "0000");
		
		return result.toString();
	}
	
	@RequestMapping("/memberInfo/toDraw")
	public ModelAndView toDraw(Model model, HttpServletRequest request,HttpServletResponse response) {
		MemberInfo memberInfo = SessionUtils.getMemberInfoSession(request);
		if(memberInfo==null){
			return new ModelAndView("login");
		}
		JSONObject result = new JSONObject();
		JSONObject reqData=new JSONObject();
		reqData.put("memberId", memberInfo.getId());
		result=JSONObject.fromObject(HttpUtil.sendPostRequest(SysConfig.pospService+"/api/memberInfo/toDraw", CommonUtil.createSecurityRequstData(reqData)));
//		result.put("returnCode", "0000");
		
		return new ModelAndView("memberInfo/toDraw",result);
	}
	
	@ResponseBody
	@RequestMapping("/memberInfo/doDraw")
	public String doDraw(Model model, HttpServletRequest request,HttpServletResponse response) {
		MemberInfo memberInfo = SessionUtils.getMemberInfoSession(request);
		if(memberInfo==null){
			return "login";
		}
		String txnType = request.getParameter("txnType");
		JSONObject result = new JSONObject();
		JSONObject reqData=new JSONObject();
		reqData.put("memberCode", memberInfo.getCode());
		reqData.put("txnType", txnType);
		result=JSONObject.fromObject(HttpUtil.sendPostRequest(SysConfig.pospService+"/api/debitNote/autoDraw", CommonUtil.createSecurityRequstData(reqData)));
//		result.put("returnCode", "0000");
		
		return result.toString();
	}
	
	
	@ResponseBody
	@RequestMapping("/memberInfo/drawCommit")
	public String drawCommit(Model model, HttpServletRequest request,HttpServletResponse response) {
		MemberInfo memberInfo = SessionUtils.getMemberInfoSession(request);
		if(memberInfo==null){
			return "login";
		}
		String drawMoney = request.getParameter("drawMoney");
		JSONObject result = new JSONObject();
		JSONObject reqData=new JSONObject();
		reqData.put("memberId", memberInfo.getId());
		reqData.put("drawMoney", drawMoney);
		result=JSONObject.fromObject(HttpUtil.sendPostRequest(SysConfig.pospService+"/api/memberInfo/drawCommit", CommonUtil.createSecurityRequstData(reqData)));
//		result.put("returnCode", "0000");
		
		return result.toString();
	}
	
	
	
	
	
	@ResponseBody
	@RequestMapping("/memberInfo/drawList")
	public ModelAndView drawList(Model model, HttpServletRequest request,HttpServletResponse response) {
		MemberInfo memberInfo = SessionUtils.getMemberInfoSession(request);
		if(memberInfo==null){
			return new ModelAndView("login");
		}
		PageInitUtil.init(request);
		JSONObject result = new JSONObject();
		JSONObject reqData=new JSONObject();
		reqData.put("memberId", memberInfo.getId());
		reqData.put("limitStart", PageInitUtil.getLimitStart());
		reqData.put("limitSize", PageInitUtil.getLimitSize());
		result=JSONObject.fromObject(HttpUtil.sendPostRequest(SysConfig.pospService+"/api/memberInfo/drawList", CommonUtil.createSecurityRequstData(reqData)));
		
		return new ModelAndView("memberInfo/drawList",result);
	}
	
	@ResponseBody
	@RequestMapping("/memberInfo/drawListData")
	public String drawListData(Model model, HttpServletRequest request,HttpServletResponse response) {
		JSONObject result = new JSONObject();
		JSONObject reqData=new JSONObject();
		MemberInfo memberInfo = SessionUtils.getMemberInfoSession(request);
		if(memberInfo==null){
			result.put("returnCode", "4004");
			result.put("returnMsg", "请先登陆");
			return result.toString();
		}
		PageInitUtil.init(request);
		reqData.put("memberId", memberInfo.getId());
		reqData.put("limitStart", PageInitUtil.getLimitStart());
		reqData.put("limitSize", PageInitUtil.getLimitSize());
		result=JSONObject.fromObject(HttpUtil.sendPostRequest(SysConfig.pospService+"/api/memberInfo/drawList", CommonUtil.createSecurityRequstData(reqData)));
		
		return result.toString();
	}
	
	@ResponseBody
	@RequestMapping("/memberInfo/drawDetail")
	public ModelAndView drawDetail(Model model, HttpServletRequest request,HttpServletResponse response) {
		MemberInfo memberInfo = SessionUtils.getMemberInfoSession(request);
		if(memberInfo==null){
			return new ModelAndView("login");
		}
		String drawId = request.getParameter("drawId");
		JSONObject result = new JSONObject();
		JSONObject reqData=new JSONObject();
		reqData.put("drawId", Integer.parseInt(drawId));
		reqData.put("memberId", memberInfo.getId());
		result=JSONObject.fromObject(HttpUtil.sendPostRequest(SysConfig.pospService+"/api/memberInfo/drawDetail", CommonUtil.createSecurityRequstData(reqData)));
		
		return new ModelAndView("memberInfo/drawDetail",result);
	}

	/**
	 * 米联平台支付回调结果
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@ResponseBody
	@RequestMapping("ml/memberQuickPayCallBack")
	public String memberQuickPayCallBack(Model model, HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException {
		Map<String, String> map = new HashMap<String, String>();
		JSONObject result=new JSONObject();
		JSONObject reqData=new JSONObject();
		try {	
		String ORDER_ID	  =request.getParameter("ORDER_ID"); 
		String ORDER_AMT =	request.getParameter("ORDER_AMT");  
		String REF_NO	    = request.getParameter("REF_NO");
		String PAYCH_TIME=request.getParameter("PAYCH_TIME");
		String ORDER_TIME =request.getParameter("ORDER_TIME");//订单提交时间
		String PAY_AMOUNT =request.getParameter("PAY_AMOUNT");	 
		String RESP_CODE  =request.getParameter("RESP_CODE");
		String RESP_DESC =  new String(request.getParameter("RESP_DESC").getBytes("iso-8859-1"), "utf-8");
		String USER_ID	  = request.getParameter("USER_ID");
		
		String temp =request.getParameter("RESP_DESC");
		//temp=URLDecoder.decode(RESP_DESC,"utf-8"); 
		//System.out.println("RESP_DESC="+RESP_DESC);
		RESP_DESC=temp;
		
		String SIGN	     =request.getParameter("SIGN");
		String SIGN_TYPE	 =request.getParameter("SIGN_TYPE"); 
		String BUS_CODE =	request.getParameter("BUS_CODE");
		String CNY	     =  request.getParameter("CCT");
		String ADD5	    = request.getParameter("ADD5");
		String ADD1	   =  request.getParameter("ADD1");                       
		String ADD2	   =  request.getParameter("ADD2");                       
		String ADD3	   =  request.getParameter("ADD3");                       
		String ADD4	   =  request.getParameter("ADD4");  
		String[] arrs=ADD4.split("\\|");
		map.put("ORDER_ID", ORDER_ID);
		map.put("TRANS_AMT", ORDER_AMT);
		map.put("REF_NO", REF_NO);
		map.put("PAYCH_TIME", PAYCH_TIME);
		map.put("TRANS_STIME", ORDER_TIME);
		map.put("PAYAMOUNT", arrs[1]);
		map.put("RESP_CODE", RESP_CODE);
		map.put("RESP_DESCR", RESP_DESC);
		map.put("USER_ID", USER_ID);
		map.put("SIGN", SIGN);
		map.put("SIGN_TYPE", SIGN_TYPE);
		map.put("MSG_TYPE", BUS_CODE);
		map.put("CCT", CNY);
		map.put("ADD5", ADD5);
		map.put("ADD1", ADD1);
		map.put("ADD2", ADD2);
		map.put("ADD3", ADD3);
		map.put("ADD4", arrs[0]);
		map.put("TXNTYPE", arrs[2]);
		map.put("orderNum", arrs[3]);
		map.put("MESSAGE", ADD4);
		reqData.put("requestMsg", map); 
		
		logger.info("米联回调参数字符串："+reqData.toString());
				
		 result=JSONObject.fromObject(HttpUtil.sendPostRequest(SysConfig.pospService+"/api/memberInfo/memberQuickPayCallBack", CommonUtil.createSecurityRequstData(reqData)));
		 if("0000".equals(result.getString("returnCode"))){
			 return "SUCCESS";
		 }
		 return "FAIL";
		
		} catch (Exception e) {
			e.printStackTrace();
			return "FAIL";
		}
		
	}
	
	//绑卡页面 用于升级T0权限
	@RequestMapping("/memberInfo/bindCard")
	public ModelAndView toBindCard(Model model, HttpServletRequest request,HttpServletResponse response) {
		return new ModelAndView("memberInfo/upgradeBindCard");
	}
	
}
