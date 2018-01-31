package com.epay.scanpay.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.epay.scanpay.common.constant.DataDicConstant;
import com.epay.scanpay.common.constant.SysConfig;
import com.epay.scanpay.common.utils.CommonUtil;
import com.epay.scanpay.common.utils.HttpUtil;
import com.epay.scanpay.common.utils.Verify;
import com.epay.scanpay.entity.SinoPayRequestForm;





@Controller
public class BankPayController {
	private static Logger logger = LoggerFactory.getLogger(DebitNoteController.class);
	
	@RequestMapping("/payment/index")
	public String index(Model model,HttpServletRequest request){
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String memberCode = request.getParameter("memberCode");//商家编码
		String orderNum = request.getParameter("orderNum");//商家订单号
		String payMoney = request.getParameter("payMoney");//支付金额
		String goodsName = request.getParameter("goodsName");//商品名称
		try {
			System.out.println(goodsName);
			goodsName = URLDecoder.decode(goodsName, "utf-8");
			System.out.println(goodsName);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String gateWayType = request.getParameter("gateWayType");//支付方式 01借记卡 02信用卡
		String callbackUrl = request.getParameter("callbackUrl");//回调地址
		String signStr = request.getParameter("signStr"); 
		model.addAttribute("memberCode", memberCode);
		model.addAttribute("orderNum", orderNum);
		model.addAttribute("payMoney", payMoney);
		model.addAttribute("goodsName", goodsName);
		model.addAttribute("gateWayType", gateWayType);
		model.addAttribute("callbackUrl", callbackUrl);
		model.addAttribute("signStr", signStr);
		
		return "payment/sinopay";
		
	}
	
	
	@RequestMapping("/payment/toPayment")
	public String toPayment(Model model,HttpServletRequest request){
		String page = "payment/fail";
		
		try { // 获取页面请求信息
			SinoPayRequestForm merchantForm = createMerForm(request);
			
			String memberCode = request.getParameter("memberCode");
			String callbackUrl = request.getParameter("callbackUrl");
			String signStr = request.getParameter("signStr");
			String bankCode = request.getParameter("bankCode");

			JSONObject reqData = new JSONObject();
			reqData.put("memberCode", memberCode);
			reqData.put("orderNum", merchantForm.getMerBillNo());
			reqData.put("payMoney", merchantForm.getAmount());
			//reqData.put("goodsName", merchantForm.getGoodsName());
			reqData.put("bankCode", bankCode);
			reqData.put("callbackUrl", callbackUrl);
			reqData.put("signStr", signStr);
			
			logger.info("进入请求toPayment");
			logger.info("memberCode="+memberCode);
			logger.info("callbackUrl="+callbackUrl);
			logger.info("signStr="+signStr);
			logger.info("orderNum="+merchantForm.getMerBillNo());
			logger.info("payMoney="+merchantForm.getAmount());
			logger.info("bankCode="+bankCode);
			
			JSONObject responseJson = JSONObject.fromObject(
					HttpUtil.sendPostRequest(SysConfig.pospService + "/api/bankPay/toPay",
							CommonUtil.createSecurityRequstData(reqData)));
			if ("0000".equals(responseJson.getString("returnCode"))) {
				String routeCode = responseJson.getString("routeCode");
				if(DataDicConstant.HX_ROUTE_CODE.equals(routeCode)){
					String action = SysConfig.ipsFormAction;
					// body部分
					String bodyXml = "<body>" +
							"<MerBillNo>" + merchantForm.getMerBillNo() + "</MerBillNo>" +
							"<Lang>GB</Lang>" +//语言  中文
							"<Amount>" + merchantForm.getAmount() + "</Amount>" +
							"<Date>" + new SimpleDateFormat("yyyyMMdd").format(new Date()) + "</Date>" +
							"<CurrencyType>156</CurrencyType>" +  //币种 人民币
							"<GatewayType>" + merchantForm.getGatewayType() + "</GatewayType>" +
							"<Merchanturl>" + SysConfig.ipsMerchantUrl + "</Merchanturl>" +
							"<FailUrl><![CDATA[" + merchantForm.getFailUrl() + "]]></FailUrl>" +
							"<Attach><![CDATA[" + merchantForm.getAttach() + "]]></Attach>" +
							"<OrderEncodeType>5</OrderEncodeType>" +//订单支付接口加密方式  5:MD5
							"<RetEncodeType>17</RetEncodeType>" +//交易返回接口加密方式 17:md5摘要
							"<RetType>1</RetType>" +//Server to Server返回
							"<ServerUrl><![CDATA[" + SysConfig.ipsServerUrl + "]]></ServerUrl>" +
							"<BillEXP>" + merchantForm.getBillExp() + "</BillEXP>" +
							"<GoodsName>" + merchantForm.getGoodsName() + "</GoodsName>" +
							"<IsCredit></IsCredit>" +//决定商户是否参用直连方式 1:直连  如不用直连方式，此参数不用传值
							"<BankCode></BankCode>" +
							"<ProductType></ProductType>" +
							"</body>";
					// MD5签名
					String directStr = SysConfig.ipsDirectStr;
					String sign = DigestUtils
							.md5Hex(Verify.getBytes(bodyXml + SysConfig.ipsMerCode + directStr,
									"UTF-8"));
					// xml
					String date = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
					String xml = "<Ips>" +
							"<GateWayReq>" +
							"<head>" +
							"<Version>v1.0.0</Version>" +
							"<MerCode>" + SysConfig.ipsMerCode + "</MerCode>" +
							"<MerName>" + SysConfig.ipsMerName + "</MerName>" +
							"<Account>" + SysConfig.ipsMerAccount + "</Account>" +
							"<MsgId>" + "msg" + date + "</MsgId>" +
							"<ReqDate>" + date + "</ReqDate>" +
							"<Signature>" + sign + "</Signature>" +
							"</head>" +
							bodyXml +
							"</GateWayReq>" +
							"</Ips>";
					System.out.println(">>>>> 订单支付 请求信息: " + xml);
					request.setAttribute("action", action);
					request.setAttribute("pGateWayReq", xml);
					model.addAttribute("action", action);
					model.addAttribute("pGateWayReq", xml);
					page = "payment/sinopayPaymentForm";
				}else if(DataDicConstant.SLF_ROUTE_CODE.equals(routeCode)){
					String msg = responseJson.getString("msg");
					String payUrl = responseJson.getString("payUrl");
					System.out.println(">>>>> 订单支付 请求信息: " + msg);
					request.setAttribute("action", payUrl);
					request.setAttribute("msg", msg);
					model.addAttribute("action", payUrl);
					model.addAttribute("msg", msg);
					page = "payment/paySubmit";
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
	
	
	@RequestMapping("/payment/payResult")
	public String payResult(Model model,HttpServletRequest request){
		InputStream is = null;
		try {
			StringBuffer notifyResultStr = new StringBuffer("");
			is = request.getInputStream();
			byte[] b = new byte[1024];
			int len = -1;
			while((len = is.read(b)) != -1){
				notifyResultStr.append(new String(b,0,len,"utf-8"));
			}
			logger.info("payResult前台通知返回报文[{}]",  notifyResultStr );
			
			
			
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		return "payment/payResult";
		
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
	

}
