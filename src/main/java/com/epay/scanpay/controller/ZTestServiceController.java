package com.epay.scanpay.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.epay.scanpay.common.constant.SysConfig;
import com.epay.scanpay.common.excep.ArgException;
import com.epay.scanpay.common.utils.CommonUtil;
import com.epay.scanpay.common.utils.HttpUtil;
import com.fasterxml.jackson.core.io.CharTypes;

@Controller
public class ZTestServiceController {
	private static Logger logger = LoggerFactory.getLogger(ZTestServiceController.class);
	
	@ResponseBody
	@RequestMapping("/testService/modifyMerchant")
	public JSONObject modifyMerchant(Model model, HttpServletRequest request) {
		JSONObject result=new JSONObject();
		JSONObject reqData=new JSONObject();
		try {
			reqData.put("memberID", "1040");
			reqData.put("accNo", "6227003814170172882");
			result=JSONObject.fromObject(HttpUtil.sendPostRequest(SysConfig.pospService+"/api/merchantManager/modify", CommonUtil.createSecurityRequstData(reqData)));
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			result.put("returnCode", "4004");
			result.put("returnMsg", "请求失败");
			return result;
		}
		return result;
	}

	@RequestMapping("/testService/msPayNotify")
	public JSONObject msPayNotify(Model model, HttpServletRequest request) {
		JSONObject result=new JSONObject();
		JSONObject reqData=new JSONObject();
		try {
			reqData.put("memberID", "1040");
			reqData.put("accNo", "6227003814170172882");
			result=JSONObject.fromObject(HttpUtil.sendPostRequest(SysConfig.pospService+"/api/cashierDesk/msPayNotify", CommonUtil.createSecurityRequstData(reqData)));
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
	@RequestMapping("/testService/testPayResult")
	public JSONObject testPayResult(Model model, HttpServletRequest request) {
		JSONObject result=new JSONObject();
		JSONObject reqData=new JSONObject();
		try {
			String str = getParamString(request);
			System.out.println(">>>>>"+str);
			/*
			 此处解析回调报文并进行业务处理
			 */
			result.put("resCode", "0000");//回调通知的响应
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			result.put("returnCode", "4004");
			result.put("returnMsg", "请求失败");
			return result;
		}
		return result;
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
	
	
	public static void main(String[] args) {
		JSONObject payNoticeJson = new JSONObject();
		payNoticeJson.put("test1", "1111");
		payNoticeJson.put("test2", "测试");
		payNoticeJson.put("test3", "数据啊");
		payNoticeJson.put("test4", "data");
		JSONObject resData = JSONObject.fromObject(HttpUtil.sendPostRequest("http://127.0.0.1:8080/scanPay/testService/testPayResult", payNoticeJson.toString()));
		System.out.println(resData);
	}
}
