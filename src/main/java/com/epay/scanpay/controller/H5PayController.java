package com.epay.scanpay.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.epay.scanpay.common.constant.SysConfig;
import com.epay.scanpay.common.utils.CommonUtil;
import com.epay.scanpay.common.utils.HttpUtil;
import com.epay.scanpay.common.utils.IpUtils;




@Controller
public class H5PayController {
	private static Logger logger = LoggerFactory.getLogger(DebitNoteController.class);
	
	@RequestMapping("/payment/h5index")
	public String index(Model model,HttpServletRequest request){
		String orderNum = "H"+new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		String ip = "";
		try {
			ip = IpUtils.getIpAddress(request);
			System.out.println("ip===="+ip);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.addAttribute("orderNum", orderNum);
		model.addAttribute("ip", ip);
		model.addAttribute("signStr", "11111");
		
		return "payment/wxH5Pay";
		
	}
	
	
	@RequestMapping("/payment/toWxH5")
	public String toPayment(Model model,HttpServletRequest request){
		String page = "payment/fail";
		
		try { // 获取页面请求信息
			
			String memberCode = request.getParameter("memberCode");
			String callbackUrl = request.getParameter("callbackUrl");
			String signStr = request.getParameter("signStr");
			String orderNum = request.getParameter("orderNum");
			String payMoney = request.getParameter("payMoney");
			String sceneInfo = request.getParameter("sceneInfo");
			String ip = request.getParameter("ip");
			
			JSONObject reqData = new JSONObject();
			reqData.put("memberCode", memberCode);
			reqData.put("orderNum", orderNum);
			reqData.put("payMoney", payMoney);
			reqData.put("sceneInfo", sceneInfo);
			reqData.put("ip", ip);
			reqData.put("callbackUrl", callbackUrl);
			reqData.put("signStr", signStr);
			
			
			
			JSONObject responseJson = JSONObject.fromObject(
					HttpUtil.sendPostRequest(SysConfig.pospService + "/api/debitNote/wxH5Pay",
							CommonUtil.createSecurityRequstData(reqData)));
			if ("0000".equals(responseJson.getString("returnCode"))) {
				String payUrl = responseJson.getString("payUrl");
				request.setAttribute("payUrl", payUrl);
				model.addAttribute("payUrl", payUrl);
				page = "payment/wxH5Submit";
				
			}else{
			    request.setAttribute("errorMsg", responseJson.getString("returnMsg"));
				model.addAttribute("errorMsg", responseJson.getString("returnMsg"));
			}
		}catch (Exception ex) {
			ex.printStackTrace();
		}
		return page;
		
	}
	
	

}
