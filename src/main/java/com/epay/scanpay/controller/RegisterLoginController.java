package com.epay.scanpay.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.epay.scanpay.common.constant.SysConfig;
import com.epay.scanpay.common.constant.WxConfig;
import com.epay.scanpay.common.excep.ArgException;
import com.epay.scanpay.common.utils.CommonUtil;
import com.epay.scanpay.common.utils.HttpUtil;
import com.epay.scanpay.common.utils.SessionUtils;
import com.epay.scanpay.entity.MemberInfo;
import com.epay.scanpay.entity.RegisterTmp;

@Controller
public class RegisterLoginController {
private static Logger logger = LoggerFactory.getLogger(DebitNoteController.class);
	@RequestMapping("/")
	public ModelAndView index(Model model, HttpServletRequest request) {
		return new ModelAndView("index");
	}
	
	
	@RequestMapping("/register")
	public ModelAndView register(Model model, HttpServletRequest request) {
		String epayCode = request.getParameter("epayCode");
		String oemName = request.getParameter("oemName");
		String register="";
		Map<String,Object> modelMap = new HashMap<String,Object>();
		modelMap.put("epayCode", epayCode);
		modelMap.put("oemName", oemName);
		if (oemName==null || oemName==""){
			if("ESK".equals(SysConfig.channel)){
				register="registerEsk";
			}else{
				register="register";
			}
			
		}else{
			register=oemName+"/register";
		}
		return new ModelAndView(register,modelMap);
	
	}
	
	@RequestMapping("/agentRegister")
	public ModelAndView agentRegister(Model model, HttpServletRequest request) {
		String type = request.getParameter("type");
		String agentCode = request.getParameter("agentCode");
		String oemName = request.getParameter("oemName");
		String register="";
		Map<String,Object> modelMap = new HashMap<String,Object>();
		modelMap.put("agentType", type);//	1--瑞卡通，2--其他oem厂商
		modelMap.put("agentCode", agentCode);
		modelMap.put("oemName", oemName);
		if (oemName==null || oemName==""){
			register="register";
		}else{
			register=oemName+"/register";
		}
		return new ModelAndView(register,modelMap);
	
	}
	
	@RequestMapping("/regForT1")
	public ModelAndView regForT1(Model model, HttpServletRequest request) {
		String epayCode = request.getParameter("epayCode");
		Map<String,Object> modelMap = new HashMap<String,Object>();
		modelMap.put("epayCode", epayCode);
		return new ModelAndView("regForT1",modelMap);
		
	}
	
	@ResponseBody
	@RequestMapping("/registerSuccess")
	public ModelAndView registerSuccess(Model model, HttpServletRequest request) {
		String oemName = request.getParameter("oemName");
		String registerSuccess="";
		if (oemName==null || oemName==""){
			registerSuccess="registerSuccess";
		}else{
			registerSuccess=oemName+"/registerSuccess";
		}
		Map<String,Object> modelMap = new HashMap<String,Object>();
		modelMap.put("oemName", oemName);
		return new ModelAndView(registerSuccess,modelMap);
	}

	@ResponseBody
	@RequestMapping("/registerMem")
	public JSONObject registerMem(Model model, HttpServletRequest request) {
//		String baseFilePath = request.getSession().getServletContext().getRealPath("/")+"/upload";
		String baseFilePath = SysConfig.baseUploadFilePath;   //linux下路径地址，从配置文件中读取
		String memberName = null;
		String certNbr = null;
		String addr = null;
		String cardNbr = null;
		String busLicenceNbr = null;
		String province = null;
		String city = null;
		String bankId = null;
		String subId = null;
		String mobilePhone = null;
		String verifyCode = null;
		String payCode = null;
		String certFilePath = null;
		String cardFilePath = null;
		String certOppoFilePath = null;
		String cardOppoFilePath = null;
		String blFilePath = null;
		String authFilePath = null;
		String merchantName = null;
		String shortName = null;
		String settleType = null;
		String county=null;
		String contactType=null;
		String agentType=null;
		String agentCode=null;
		String bankArea=null;
		JSONObject result = new JSONObject();
		JSONObject reqData=new JSONObject();
		try {
			memberName = request.getParameter("memberName").toString();
			certNbr = request.getParameter("certNbr").toString();
			addr = request.getParameter("addr").toString();
			cardNbr = request.getParameter("cardNbr").toString();
			//busLicenceNbr = request.getParameter("busLicenceNbr").toString();
			contactType=request.getParameter("select_c").toString();
			province = request.getParameter("addr_province").toString();
			city = request.getParameter("addr_city").toString();
			county=request.getParameter("addr_county").toString();
			bankId = request.getParameter("bankId").toString();
//			subId = request.getParameter("subId").toString();
			mobilePhone = request.getParameter("mobilePhone").toString();
			verifyCode = request.getParameter("verifyCode").toString();
			payCode = request.getParameter("epayCode");
			merchantName = request.getParameter("merchantName");
			shortName = request.getParameter("shortName");
			settleType = request.getParameter("settleType");
			agentType = request.getParameter("agentType");
			bankArea = request.getParameter("bankArea");
			
			String fileParentPath = "/member/" ;
			MultipartHttpServletRequest requestFiles = (MultipartHttpServletRequest) request;//request强制转换注意
			MultipartFile certImg = requestFiles.getFile("certImg");
			if(null != certImg && !certImg.isEmpty()){
				certFilePath = saveImgFile(certImg, fileParentPath,baseFilePath);
			}
			
			MultipartFile cardImg = requestFiles.getFile("cardImg");
			if(null != cardImg && !cardImg.isEmpty()){
				cardFilePath = saveImgFile(cardImg, fileParentPath,baseFilePath);
			}
			
			MultipartFile certOppoImg = requestFiles.getFile("certOppoImg");
			if(null != certOppoImg && !certOppoImg.isEmpty()){
				certOppoFilePath = saveImgFile(certOppoImg, fileParentPath,baseFilePath);
			}
			
			MultipartFile cardOppoImg = requestFiles.getFile("cardOppoImg");
			if(null != cardOppoImg && !cardOppoImg.isEmpty()){
				cardOppoFilePath = saveImgFile(cardOppoImg, fileParentPath,baseFilePath);
			}
			
			MultipartFile blImg = requestFiles.getFile("blImg");
			if(null != blImg && !blImg.isEmpty()){
				blFilePath = saveImgFile(blImg, fileParentPath,baseFilePath);
			}
			
			MultipartFile authImg = requestFiles.getFile("authImg");
			if(null != authImg && !authImg.isEmpty()){
				authFilePath = saveImgFile(authImg, fileParentPath,baseFilePath);
			}
			
			if("".equals(memberName)){
				throw new ArgException("姓名不能为空");
			}
			
			if("".equals(merchantName)){
				throw new ArgException("商铺名称不能为空");
			}
			if("".equals(contactType)){
				throw new ArgException("联系人类型不能为空");
			}
			
			if("".equals(shortName)){
				shortName = merchantName;
			}
			
			if("".equals(certNbr)){
				throw new ArgException("身份证号码不能为空");
			}
			
			//暂时屏蔽营业执照号码
//			if("".equals(busLicenceNbr)){
//				throw new ArgException("营业执照号码不能为空");
//			}
//			
//			if(busLicenceNbr.length() != 15 && busLicenceNbr.length() != 18){
//				throw new ArgException("营业执照号码不正确");
//			}
			
			if("".equals(addr)){
				throw new ArgException("地址不能为空");
			}
			
			if("".equals(cardNbr)){
				throw new ArgException("收款卡号不能为空");
			}
			
			if("".equals(province) || "".equals(city)){
				throw new ArgException("所在省市不能为空");
			}
			if("".equals(county)){
				throw new ArgException("所在市县不能为空");
			}
			if("".equals(bankId)){
				throw new ArgException("开户银行不能为空");
			}
			
			if("ESK".equals(SysConfig.channel)){
				if(certFilePath==null){
					throw new ArgException("身份证正面照不能为空");
				}
				if(certOppoFilePath==null){
					throw new ArgException("身份证背面照不能为空");
				}
				if(cardFilePath==null){
					throw new ArgException("银行卡正面照不能为空");
				}
				if(cardOppoFilePath==null){
					throw new ArgException("银行卡背面照不能为空");
				}
				if(blFilePath==null){
					throw new ArgException("营业执照不能为空");
				}
				if(!"01".equals(contactType)&&authFilePath==null){
					throw new ArgException("授权书照片不能为空");
				}
			}
//			if("".equals(subId)){
//				throw new ArgException("开户支行不能为空");
//			}
			
			if("".equals(settleType)){
				throw new ArgException("结算方式不能为空");
			}
			
			if("".equals(mobilePhone)){
				throw new ArgException("手机号不能为空");
			}
			if("".equals(verifyCode)){
				throw new ArgException("验证码不能为空");
			}
			if(null == agentType || "".equals(agentType)){
				if(null == payCode || "".equals(payCode.trim())){
					throw new ArgException("对不起,请联系管理员获取收款码后再注册");
				}
			}else if("1".equals(agentType) || "2".equals(agentType)){
				reqData.put("agentType", agentType);
				agentCode = request.getParameter("agentCode");
				if(null == agentCode || "".equals(agentCode)){
					throw new ArgException("无效的机构编码,请联系管理员解决");
				}
			}else{
				throw new ArgException("无效的请求");
			}
			
			
			RegisterTmp registerTmp = new RegisterTmp();
			registerTmp.setType("1");
			registerTmp.setLoginCode(mobilePhone);//手机号座位账号
			registerTmp.setContact(memberName);
			registerTmp.setAddr(addr);
			registerTmp.setCertNbr(certNbr);
			registerTmp.setProvince(province);
			registerTmp.setCity(city);
			registerTmp.setCounty(county);
			registerTmp.setContactType(contactType);
			registerTmp.setBusLicenceNbr(busLicenceNbr);//增加营业执照项
			
			registerTmp.setBankOpen(bankArea);
			registerTmp.setBankId(bankId);
			registerTmp.setSubId(subId);
			registerTmp.setMobilePhone(mobilePhone);
			registerTmp.setCertPic1(certFilePath);
			registerTmp.setCertPic2(certOppoFilePath);
			registerTmp.setCardPic1(cardFilePath);
			registerTmp.setCardPic2(cardOppoFilePath);
			registerTmp.setMemcertPic(blFilePath);
			registerTmp.setAuthPic(authFilePath);
			registerTmp.setAccountName(memberName);
			registerTmp.setAccountNumber(cardNbr);
			
			
			registerTmp.setName(merchantName);
			registerTmp.setShortName(shortName);
			registerTmp.setSettleType(settleType);
			//收款码 必填
			registerTmp.setPayCode(payCode);
			registerTmp.setDelFlag("0");
			registerTmp.setStatus("0");
			
			reqData.put("registerTmp", registerTmp);
			reqData.put("verifyCode", verifyCode);
			reqData.put("agentCode", agentCode);
//			reqData.put("accountName", accountName);
//			reqData.put("certNbr", certNbr);
//			reqData.put("addr", addr);
//			reqData.put("province", province);
//			reqData.put("city", city);
//			reqData.put("bankID", bankID);
//			reqData.put("subID", subID);
//			reqData.put("loginCode", loginCode);
//			reqData.put("verifyCode", verifyCode);
			result=JSONObject.fromObject(HttpUtil.sendPostRequest(SysConfig.pospService+"/api/registerLogin/toRegister", CommonUtil.createSecurityRequstData(reqData)));
			
		}catch (ArgException e){
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
	@ResponseBody
	@RequestMapping("/registerMemT1")
	public JSONObject registerMemT1(Model model, HttpServletRequest request) {
//		String baseFilePath = request.getSession().getServletContext().getRealPath("/")+"/upload";
		String baseFilePath = SysConfig.baseUploadFilePath;   //linux下路径地址，从配置文件中读取
		String memberName = null;
		String certNbr = null;
		String addr = null;
		String cardNbr = null;
		String province = null;
		String city = null;
		String bankId = null;
		String subId = null;
		String mobilePhone = null;
		String verifyCode = null;
		String payCode = null;
		String certFilePath = null;
		String cardFilePath = null;
		String merchantName = null;
		String shortName = null;
		String settleType = null;
		
		JSONObject result = new JSONObject();
		try {
			memberName = request.getParameter("memberName").toString();
			certNbr = request.getParameter("certNbr").toString();
			addr = request.getParameter("addr").toString();
			cardNbr = request.getParameter("cardNbr").toString();
			province = request.getParameter("province").toString();
			city = request.getParameter("city").toString();
			bankId = request.getParameter("bankId").toString();
			subId = request.getParameter("subId").toString();
			mobilePhone = request.getParameter("mobilePhone").toString();
			payCode = request.getParameter("epayCode");
			merchantName = request.getParameter("merchantName");
			shortName = request.getParameter("shortName");
			settleType = request.getParameter("settleType");
			
			
			String fileParentPath = "/member/" ;
			MultipartHttpServletRequest requestFiles = (MultipartHttpServletRequest) request;//request强制转换注意
			MultipartFile certImg = requestFiles.getFile("certImg");
			if(null != certImg && !certImg.isEmpty()){
				certFilePath = saveImgFile(certImg, fileParentPath,baseFilePath);
			}
			
			MultipartFile cardImg = requestFiles.getFile("cardImg");
			if(null != cardImg && !cardImg.isEmpty()){
				cardFilePath = saveImgFile(cardImg, fileParentPath,baseFilePath);
			}
			
			if("".equals(memberName)){
				throw new ArgException("姓名不能为空");
			}
			
			if("".equals(merchantName)){
				throw new ArgException("商铺名称不能为空");
			}
			
			if("".equals(shortName)){
				shortName = merchantName;
			}
			
			if("".equals(certNbr)){
				throw new ArgException("身份证号码不能为空");
			}
			
			if("".equals(addr)){
				throw new ArgException("地址不能为空");
			}
			
			if("".equals(cardNbr)){
				throw new ArgException("收款卡号不能为空");
			}
			
			if("".equals(province) || "".equals(city)){
				throw new ArgException("开户省市不能为空");
			}
			
			if("".equals(bankId)){
				throw new ArgException("开户银行不能为空");
			}
			
			if("".equals(subId)){
				throw new ArgException("开户支行不能为空");
			}
			
			if("".equals(settleType)){
				throw new ArgException("结算方式不能为空");
			}
			
			if("".equals(mobilePhone)){
				throw new ArgException("手机号不能为空");
			}
			if("".equals(verifyCode)){
				throw new ArgException("验证码不能为空");
			}
			RegisterTmp registerTmp = new RegisterTmp();
			registerTmp.setType("1");
			registerTmp.setLoginCode(mobilePhone);//手机号座位账号
			registerTmp.setContact(memberName);
			registerTmp.setAddr(addr);
			registerTmp.setCertNbr(certNbr);
			registerTmp.setProvince(province);
			registerTmp.setCity(city);
			registerTmp.setBankId(bankId);
			registerTmp.setSubId(subId);
			registerTmp.setMobilePhone(mobilePhone);
			registerTmp.setCertPic1(certFilePath);
			registerTmp.setCardPic1(cardFilePath);
			registerTmp.setAccountName(memberName);
			registerTmp.setAccountNumber(cardNbr);
			
			registerTmp.setName(merchantName);
			registerTmp.setShortName(shortName);
			registerTmp.setSettleType(settleType);
			if(null != payCode && !"".equals(payCode)){
				registerTmp.setPayCode(payCode);
			}
			registerTmp.setDelFlag("0");
			registerTmp.setStatus("0");
			
			JSONObject reqData=new JSONObject();
			reqData.put("registerTmp", registerTmp);
			reqData.put("verifyCode", verifyCode);
			result=JSONObject.fromObject(HttpUtil.sendPostRequest(SysConfig.pospService+"/api/registerLogin/toRegisterT1", CommonUtil.createSecurityRequstData(reqData)));
			
		}catch (ArgException e){
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
	
	
	@RequestMapping("/login")
	public String login(Model model, HttpServletRequest request) {
		MemberInfo memberInfo = SessionUtils.getMemberInfoSession(request);
		if(memberInfo != null){
			return "redirect:/memberInfo/memberInfo";
		}
//		String ua = request.getHeader("user-agent").toLowerCase();
//		if (ua.indexOf("micromessenger") > 0) {
//			String redirectUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/weixinLoginCallback";
//			try {
//				redirectUrl = URLEncoder.encode(redirectUrl, "utf-8");
//			} catch (UnsupportedEncodingException e) {
//				e.printStackTrace();
//			}
//			String weixinCodeUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + WxConfig.appid + "&redirect_uri=" + redirectUrl 
//					+ "&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect";
//			return "redirect:"+weixinCodeUrl;
//		}
		return "login";
	}
	
	@RequestMapping("/weixinLoginCallback")
	public String wxDebitNoteAuthCallBack(Model model, HttpServletRequest request) {
		String code = request.getParameter("code");
		if (code != null && !"".equals(code)) {
			String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + WxConfig.appid + "&secret=" + WxConfig.appSecret + "&code=" + code + "&grant_type=authorization_code";
			String jsonStr = HttpUtil.sendGetRequest(url);// 根据code获取用户openId
			JSONObject resJson = JSONObject.fromObject(jsonStr);
			if (resJson.has("errcode")) {// 请求出错跳到首页
				return null;
			}
			String openid = resJson.getString("openid");
			request.getSession().setAttribute("openid", openid);// 将openId保存到session
			model.addAttribute("openid", openid);
			return "login";
		}
		return null;
	}
	
	@ResponseBody
	@RequestMapping("/loginIn")
	public String loginIn(Model model, HttpServletRequest request) {
		
		String loginCode = request.getParameter("loginCode").toString();
		String password = request.getParameter("password").toString();
		String userOpenID = request.getParameter("userOpenID");
		JSONObject result = new JSONObject();
		try {
			if("".equals(loginCode)){
				throw new ArgException("账号格式不正确");
			}
			
			if("".equals(password)){
				throw new ArgException("密码不能为空");
			}
			
			JSONObject reqData=new JSONObject();
			reqData.put("loginCode", loginCode);
			reqData.put("password", password);
			reqData.put("userOpenID", userOpenID);
			result=JSONObject.fromObject(HttpUtil.sendPostRequest(SysConfig.pospService+"/api/registerLogin/toLogin", CommonUtil.createSecurityRequstData(reqData)));
			if("0000".equals(result.getString("returnCode"))){
				SessionUtils.addMemberInfoSession(request, result.getJSONObject("resData").getJSONObject("memberInfo"));
				String epayCode = result.getJSONObject("resData").getString("epayCode");
				JSONObject resData=new JSONObject();
				result.clear();
				result.put("returnCode", "0000");
				result.put("returnMsg", "成功");
				resData.put("epayCode", epayCode);
				result.put("resData", resData);
				return result.toString();
			}
		
		}catch (ArgException e){
			result.put("returnCode", "4004");
			result.put("returnMsg", e.getMessage());
			logger.info(e.getMessage());
			return result.toString();
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			result.put("returnCode", "4004");
			result.put("returnMsg", "请求失败");
			return result.toString();
		}
		return result.toString();
	}
	
	@RequestMapping("/loginOut")
	public String loginOut(Model model, HttpServletRequest request) {
		
		MemberInfo memberInfo = SessionUtils.getMemberInfoSession(request);
		if(null == memberInfo){
			return "login";
		}
		Integer memberID = memberInfo.getId();
		SessionUtils.removeMemberInfoSession(request);
		JSONObject result = new JSONObject();
		
		JSONObject reqData=new JSONObject();
		reqData.put("memberID", memberID);
		result=JSONObject.fromObject(HttpUtil.sendPostRequest(SysConfig.pospService+"/api/registerLogin/toLoginOut", CommonUtil.createSecurityRequstData(reqData)));
		return "login";
	}
	@RequestMapping("/registerqq")
	public String registerqq(Model model, HttpServletRequest request) {
		
		JSONObject result = new JSONObject();
		
		JSONObject reqData=new JSONObject();
		result=JSONObject.fromObject(HttpUtil.sendPostRequest(SysConfig.pospService+"/api/registerLogin/registerQQAccount", CommonUtil.createSecurityRequstData(reqData)));
		return "login";
	}
	
	@RequestMapping("/forget")
	public String forget(Model model, HttpServletRequest request) {
		
		return "forget";
	}
	
	@RequestMapping("/resetPassword")
	@ResponseBody
	public String resetPassword(Model model, HttpServletRequest request) {
		JSONObject result = new JSONObject();
		String contactName;
		String mobilePhone;
		String certNbr;
		String password;
		String confirmPassword;
		try {
			MemberInfo memberInfo = SessionUtils.getMemberInfoSession(request);
			if (null != memberInfo) {
				SessionUtils.removeMemberInfoSession(request);
			}
			contactName = request.getParameter("contactName");
			mobilePhone = request.getParameter("mobilePhone");
			certNbr = request.getParameter("certNbr");
			password = request.getParameter("password");
			confirmPassword = request.getParameter("confirmPassword");
			if (StringUtils.isEmpty(contactName)) {
				throw new ArgException("真实姓名不能为空");
			}
			if (StringUtils.isEmpty(mobilePhone)) {
				throw new ArgException("手机号不能为空");
			}
			if (StringUtils.isEmpty(certNbr)) {
				throw new ArgException("身份证号码不能为空");
			}
			if (StringUtils.isEmpty(password)) {
				throw new ArgException("密码不能为空");
			}
			if (StringUtils.isEmpty(confirmPassword)) {
				throw new ArgException("确认密码不能为空");
			}
			if(!password.equals(confirmPassword)){
				throw new ArgException("两次密码输入不一致");
			}
		} catch (Exception e) {
			result.put("returnCode", "4004");
			result.put("returnMsg", e.getMessage());
			return result.toString();
		}
		
		JSONObject reqData=new JSONObject();
		reqData.put("contactName", contactName);
		reqData.put("mobilePhone", mobilePhone);
		reqData.put("certNbr", certNbr);
		reqData.put("password", password);
		reqData.put("confirmPassword", confirmPassword);
		result=JSONObject.fromObject(HttpUtil.sendPostRequest(SysConfig.pospService+"/api/registerLogin/resetPassword", CommonUtil.createSecurityRequstData(reqData)));
		return result.toString();
	}
	
	public String saveImgFile (MultipartFile imgFile,String saveFilePath,String basePath) throws IOException{
		String fileName = imgFile.getOriginalFilename();
		String filePath = null;
		if (StringUtils.isNotBlank(fileName)) {
			String fileNameNew = generateFileName(3) + fileName.substring(fileName.lastIndexOf("."));
			filePath = saveFilePath + fileNameNew;
			File uploadfile = new File(basePath + filePath);// 上传地址
			if(!uploadfile.exists()){
				if(!uploadfile.getParentFile().exists()){
					uploadfile.getParentFile().mkdirs();
				}
				uploadfile.createNewFile();
			}
			imgFile.transferTo(uploadfile);// 开始上传
		}
		return filePath;
	}
	
	public String generateFileName(int randomLength){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String randomStr = "";
		for(int i=0;i<randomLength;i++){
			randomStr += (new Random()).nextInt(10);
		}
		return sdf.format(new Date()) + randomStr;
	}
	@RequestMapping("/production")
	public ModelAndView production(Model model, HttpServletRequest request) {
		return new ModelAndView("production");
	}
	
	//@RequestMapping("/loginOem")   测试用  oem登陆
	public String loginOem(Model model, HttpServletRequest request) {
		MemberInfo memberInfo = SessionUtils.getMemberInfoSession(request);
		if(memberInfo != null){
			return "redirect:/memberInfo/memberInfo";
		}
		String oemName = request.getParameter("oemName");
		if(StringUtils.isNotEmpty(oemName)){
			model.addAttribute("oemName",oemName);
			return oemName + "/login"; 
		}
		return "login";
	}
	
	//@RequestMapping("/forgetOem")      测试用  oem忘记密码
	public String forgetOem(Model model, HttpServletRequest request) {
		String oemName = request.getParameter("oemName");
		if(StringUtils.isNotEmpty(oemName)){
			model.addAttribute("oemName",oemName);
			return oemName + "/forget"; 
		}
		return "forget";
	}
}
