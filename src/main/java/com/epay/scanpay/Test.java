package com.epay.scanpay;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import jxl.Sheet;
import jxl.Workbook;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
/*
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
*/
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.epay.scanpay.common.constant.SysConfig;
import com.epay.scanpay.common.utils.Base64Utils;
import com.epay.scanpay.common.utils.CommonUtil;
import com.epay.scanpay.common.utils.HttpUtil;
import com.epay.scanpay.entity.MemberInfo;
import com.epay.scanpay.entity.RegisterTmp;

public class Test {

	/**
	 * 修改商户信息
	 * @return
	 */
	public static JSONObject modifyMem() {
		
		JSONObject result = new JSONObject();
		try {
			JSONObject reqData=new JSONObject();
			String memberID = "2374";
			
			//String shortName = "小若奶茶";
			//String merchantAddress="群升国际";
			//String servicePhone="17785387545";
			//String idCard = "421023198307070418";
			String accNo = "6230200730839151";
			String accName = "刘铭峰";
			//String bankId="310";
			//String subId="310290098551";
			
			//String t0tradeRate = "0.008";//费率
			//String t0drawFee = "2";//提现手续费
			//String t1tradeRate = "0.008";//费率
			//String t1drawFee = "2";//提现手续费
			
			//String contactType="01";//联系人类型
			//String provinceCode = "440000";//省代码
			//String cityCode = "442000";//市代码
			//String districtCode = "442000002";//区代码
			
			reqData.put("memberID", memberID);
			
			//reqData.put("shortName", shortName);
			//reqData.put("merchantAddress", merchantAddress);
			//reqData.put("idCard", idCard);
			//reqData.put("servicePhone", servicePhone);
			reqData.put("accNo", accNo);
			reqData.put("accName", accName);
			//reqData.put("bankId", bankId);
			//reqData.put("subId", subId);
			//reqData.put("t0drawFee", t0drawFee);
			//reqData.put("t0tradeRate", t0tradeRate);
			//reqData.put("t1drawFee", t1drawFee);
			//reqData.put("t1tradeRate", t1tradeRate);
			
			//reqData.put("contactType", contactType);
			//reqData.put("provinceCode", provinceCode);
			//reqData.put("cityCode", cityCode);
			//reqData.put("districtCode", districtCode);
			
			Object obj = HttpUtil.sendPostRequest(SysConfig.pospService+"/api/merchantManager/modify", CommonUtil.createSecurityRequstData(reqData));
			result=JSONObject.fromObject(obj);
			
		} catch (Exception e) {
			e.printStackTrace();
			result.put("returnCode", "4004");
			result.put("returnMsg", "请求失败");
			return result;
		}
		return result;
	}
	
	/**
	 * 查询商户信息
	 * @return
	 */
	public static JSONObject queryMem() {
		
		JSONObject result = new JSONObject();
		try {
			JSONObject reqData=new JSONObject();
			String memberID = "2315";
			reqData.put("memberID", memberID);
			
			Object obj = HttpUtil.sendPostRequest(SysConfig.pospService+"/api/merchantManager/merchantInfo", CommonUtil.createSecurityRequstData(reqData));
			result=JSONObject.fromObject(obj);
			
		} catch (Exception e) {
			e.printStackTrace();
			result.put("returnCode", "4004");
			result.put("returnMsg", "请求失败");
			return result;
		}
		return result;
	}
	
	/**
	 * 提现
	 * @return
	 */
	public static JSONObject handleDraw() {
		
		JSONObject result = new JSONObject();
		try {
			JSONObject reqData=new JSONObject();
			int memberId = 1046;
			
			reqData.put("memberId", memberId);//商户id
			
			Object obj = HttpUtil.sendPostRequest(SysConfig.pospService+"/api/debitNote/handleDraw", CommonUtil.createSecurityRequstData(reqData));
			System.out.println(obj);;
			
		} catch (Exception e) {
			e.printStackTrace();
			result.put("returnCode", "4004");
			result.put("returnMsg", "请求失败");
			return result;
		}
		return result;
	}
	
	/**
	 * 商户入驻t0
	 * @return
	 */
	public static JSONObject registerT0() {
		
		JSONObject result = new JSONObject();
		try {
			JSONObject reqData=new JSONObject();
			String memberID = "2575";//1055
			
			reqData.put("memberID", memberID);
			
			Object obj = HttpUtil.sendPostRequest(SysConfig.pospService+"/api/registerLogin/registerT0", CommonUtil.createSecurityRequstData(reqData));
			result=JSONObject.fromObject(obj);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("returnCode", "4004");
			result.put("returnMsg", "请求失败");
			return result;
		}
		return result;
	}
	
	private static void getMsT1FundSettle() {
		JSONObject result = new JSONObject();
		try {
			JSONObject reqData=new JSONObject();
			String memberID = "1139";//1055
			
			reqData.put("memberID", memberID);
			reqData.put("settleDate", "20170327");
			
			Object obj = HttpUtil.sendPostRequest(SysConfig.pospService+"/api/debitNote/getMsT1FundSettle", CommonUtil.createSecurityRequstData(reqData));
			result=JSONObject.fromObject(obj);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("returnCode", "4004");
			result.put("returnMsg", "请求失败");
		}
	}
	
	private static void queryUnExam() {
		JSONObject result = new JSONObject();
		try {
			JSONObject reqData=new JSONObject();
			
			HttpUtil.sendPostRequest(SysConfig.pospService+"/api/registerLogin/queryUnExam", CommonUtil.createSecurityRequstData(reqData));
		} catch (Exception e) {
			e.printStackTrace();
			result.put("returnCode", "4004");
			result.put("returnMsg", "请求失败");
		}
	}
	
	/**
	 * 加密公众号菜单url
	 */
	public static void encodeUrl(){
//		String head = "http://rkt.ruikafinance.com:18892/scanPay/weixin/menuRedirect?refer=";
		String head = "http://rkt.topepay.com/scanPay/weixin/menuRedirect?refer=";
//		String s = "http://rkt.topepay.com/scanPay/memberInfo/QRCode";
		String s = "http://rkt.topepay.com/scanPay/memberInfo/memberCenter";
		System.out.println(head+Base64Utils.getBASE64NoWrap(s));
	}
	
	/**
	 * 解码二维码地址
	 */
	public static void qrcodeDecod(){
		String qrcode = "aHR0cHM6Ly9xci5hbGlwYXkuY29tL2JheDAwNDk0aXdobGxkYnhubmg4NDBmNw==";
		System.out.println(Base64Utils.getFromBASE64NoWrap(qrcode));
	}
	
	private static void getAD() {
		String totalAmount = "12";
		HttpUtil.getAD("350526198105077512", totalAmount,"");
	}
	/*
	private static void importMemByExcel(){
//		String path="C:\\Users\\WYW\\Desktop\\注册商户模板1.xls";
		String path="C:\\Users\\WYW\\Desktop\\注册商户模板.xlsx";
		path = path.trim();
		String settleType="0";//结算类型t0=0,t1=1
		FileInputStream is = null;
		Workbook wwb = null;
		String memberName = null;
		String contact = null;
		String certNbr = null;
		String addr = null;
		String cardNbr = null;
		String province = null;
		String city = null;
		String bankId = null;
		String subId = null;
		String mobilePhone = null;
		String merchantName = null;
		String shortName = null;
		String t0TradeFee = null;
		String t0DrawRate = null;
		String t1TradeFee = null;
		String t1DrawRate = null;
		JSONObject result = new JSONObject();
		try {
			File file = new File(path);
			is = new FileInputStream(file);
			if(path.endsWith("xls")){
				wwb = Workbook.getWorkbook(is);
				Sheet sheet = wwb.getSheet(0);
				int rows = sheet.getRows();
				System.out.println("商户个数："+rows);
				for(int i=1; i<rows; i++){
					merchantName = sheet.getCell(0, i).getContents().toString();
					shortName = sheet.getCell(1, i).getContents().toString();
					contact = sheet.getCell(2, i).getContents().toString();
					mobilePhone = sheet.getCell(3, i).getContents().toString();
					addr = sheet.getCell(4, i).getContents().toString();
					certNbr = sheet.getCell(5, i).getContents().toString();
					province = sheet.getCell(6, i).getContents().toString();
					city = sheet.getCell(7, i).getContents().toString();
					memberName = sheet.getCell(8, i).getContents().toString();
					cardNbr = sheet.getCell(9, i).getContents().toString();
					subId = sheet.getCell(10, i).getContents().toString();
//					subId = sheet.getCell(11, i).getContents().toString();
					t0TradeFee = sheet.getCell(12, i).getContents().toString();
					t0DrawRate = sheet.getCell(13, i).getContents().toString();
					t1TradeFee = sheet.getCell(14, i).getContents().toString();
					t1DrawRate = sheet.getCell(15, i).getContents().toString();
					
					JSONObject reqData=new JSONObject();
					reqData.put("settleType", settleType);
					reqData.put("merchantName", merchantName);
					reqData.put("shortName", shortName);
					reqData.put("contact", contact);
					reqData.put("mobilePhone", mobilePhone);
					reqData.put("addr", addr);
					reqData.put("certNbr", certNbr);
					reqData.put("province", province);
					reqData.put("city", city);
					reqData.put("memberName", memberName);
					reqData.put("cardNbr", cardNbr);
					reqData.put("bankId", bankId);
					reqData.put("subId", subId);
					reqData.put("t0TradeFee", t0TradeFee);
					reqData.put("t0DrawRate", t0DrawRate);
					reqData.put("t1TradeFee", t1TradeFee);
					reqData.put("t1DrawRate", t1DrawRate);
					String obj = HttpUtil.sendPostRequest(SysConfig.pospService+"/api/registerLogin/registerBatch", CommonUtil.createSecurityRequstData(reqData));
					result = JSONObject.fromObject(obj);
				
				}
			}else if(path.endsWith("xlsx")){
				XSSFWorkbook wb = new XSSFWorkbook(is);
				XSSFSheet sheet = wb.getSheetAt(0);
				int num = sheet.getLastRowNum();
				System.out.println(num);
				for(int i=1; i<=num; i++){
					XSSFRow row = sheet.getRow(i);
					merchantName = row.getCell(0).getStringCellValue();
					shortName = row.getCell(1).getStringCellValue();
					contact = row.getCell(2).getStringCellValue();
					mobilePhone = row.getCell(3).getRawValue();
					addr = row.getCell(4).getStringCellValue();
					certNbr = row.getCell(5).getStringCellValue();
					province = row.getCell(6).getStringCellValue();
					city = row.getCell(7).getStringCellValue();
					memberName = row.getCell(8).getStringCellValue();
					cardNbr = row.getCell(9).getStringCellValue();
					subId = row.getCell(10).getStringCellValue();
//					subId = row.getCell(11).getRawValue();
					t0TradeFee = row.getCell(12).getRawValue();
					t0DrawRate = row.getCell(13).getRawValue();
					t1TradeFee = row.getCell(14).getRawValue();
					t1DrawRate = row.getCell(15).getRawValue();
					
					JSONObject reqData=new JSONObject();
					reqData.put("settleType", settleType);
					reqData.put("merchantName", merchantName);
					reqData.put("shortName", shortName);
					reqData.put("contact", contact);
					reqData.put("mobilePhone", mobilePhone);
					reqData.put("addr", addr);
					reqData.put("certNbr", certNbr);
					reqData.put("province", province);
					reqData.put("city", city);
					reqData.put("memberName", memberName);
					reqData.put("cardNbr", cardNbr);
					reqData.put("bankId", bankId);
					reqData.put("subId", subId);
					reqData.put("t0TradeFee", t0TradeFee);
					reqData.put("t0DrawRate", t0DrawRate);
					reqData.put("t1TradeFee", t1TradeFee);
					reqData.put("t1DrawRate", t1DrawRate);
					String obj = HttpUtil.sendPostRequest(SysConfig.pospService+"/api/registerLogin/registerBatch", CommonUtil.createSecurityRequstData(reqData));
					result = JSONObject.fromObject(obj);
					System.out.println(result);
				}
			}else{
				System.out.println("请检查文件格式");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			try {
				if(wwb!=null)wwb.close();
				is.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
*/
	
	private static void registerWxOnly() {
		JSONObject result = new JSONObject();
		try {
			JSONObject reqData=new JSONObject();
			String memberId = "1045";//1055
			
//			reqData.put("memberId", memberId);//有传id就只住一个， 没传入驻所有
			
			Object obj = HttpUtil.sendPostRequest(SysConfig.pospService+"/api/registerLogin/registerWxAccount", CommonUtil.createSecurityRequstData(reqData));
			result=JSONObject.fromObject(obj);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("returnCode", "4004");
			result.put("returnMsg", "请求失败");
		}
		System.out.println(result);
	}
	
	private static void registerBdOnly() {
		JSONObject result = new JSONObject();
		try {
			JSONObject reqData=new JSONObject();
			String memberId = "1044";//1055
			
			reqData.put("memberId", memberId);//有传id就只住一个， 没传入驻所有
			
			Object obj = HttpUtil.sendPostRequest(SysConfig.pospService+"/api/registerLogin/registerBdAccount", CommonUtil.createSecurityRequstData(reqData));
			result=JSONObject.fromObject(obj);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("returnCode", "4004");
			result.put("returnMsg", "请求失败");
		}
		System.out.println(result);
	}
	
	/**
	 * 停用商户
	 * @return
	 */
	public static JSONObject stopMember() {
		
		JSONObject result = new JSONObject();
		try {
			JSONObject reqData=new JSONObject();
			String memberCode = "1010000202";
			
			reqData.put("memberCode", memberCode);
			
			Object obj = HttpUtil.sendPostRequest(SysConfig.pospService+"/api/merchantManager/stopMerchant", CommonUtil.createSecurityRequstData(reqData));
			result=JSONObject.fromObject(obj);
			System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("returnCode", "4004");
			result.put("returnMsg", "请求失败");
			return result;
		}
		return result;
	}
	
	private static void childMerchant() {
		JSONObject result = new JSONObject();
		try {
			JSONObject reqData=new JSONObject();
			String memberCode = "1010000971";
			reqData.put("memberCode", memberCode);//有传id就只住一个， 没传代表所有
			Object obj = HttpUtil.sendPostRequest(SysConfig.pospService+"/api/merchantManager/childMerchant", CommonUtil.createSecurityRequstData(reqData));
			result=JSONObject.fromObject(obj);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("returnCode", "4004");
			result.put("returnMsg", "请求失败");
		}
		System.out.println(result);
	}
	
	private static void queryChild() {
		JSONObject result = new JSONObject();
		try {
			JSONObject reqData=new JSONObject();
			String memberCode = "1010000356";
			String oriReqMsgId = "20170614111220675223";//对应发起新增配置的请求流水
			reqData.put("memberCode", memberCode);//
			reqData.put("oriReqMsgId", oriReqMsgId);
			Object obj = HttpUtil.sendPostRequest(SysConfig.pospService+"/api/merchantManager/queryChild", CommonUtil.createSecurityRequstData(reqData));
			result=JSONObject.fromObject(obj);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("returnCode", "4004");
			result.put("returnMsg", "请求失败");
		}
		System.out.println(result);
	}
	
	private static void importKbin() {
		try {
			String driverClassName = "com.mysql.jdbc.Driver";
	    	String url = "jdbc:mysql://192.168.101.29:3306/epay?useUnicode=true&characterEncoding=UTF-8&useSSL=false";
	    	String username ="root";
	    	String password = "Epay@123";
	    	Class.forName(driverClassName);
	    	Connection conn = DriverManager.getConnection(url, username, password);
	    	Statement stmt = conn.createStatement();
	    	
			String path="C:\\Users\\Administrator\\Desktop\\kbin.xlsx";
			path = path.trim();
			File file = new File(path);
			FileInputStream is = null;
			Workbook wwb = null;
			is = new FileInputStream(file);
			XSSFWorkbook wb = new XSSFWorkbook(is);
			XSSFSheet sheet = wb.getSheetAt(0);
			int num = sheet.getLastRowNum();
			System.out.println(num);
			for(int i=1; i<=num; i++){
				XSSFRow row = sheet.getRow(i);
//				String bankCode = row.getCell(0).getStringCellValue();
//				String kbin = row.getCell(1).getStringCellValue();
//				String bankName = row.getCell(2).getStringCellValue();
//				String len = row.getCell(3).getRawValue();
//				String sql = "insert into bu_kbin (bank_code,bank_name,kbin,len) values ('"+bankCode+"','"+bankName+"','"+kbin+"','"+len+"');";
				String bankCode = row.getCell(0).getStringCellValue();
				String bankName = row.getCell(1).getStringCellValue();
				String sql = "insert into bu_bank_name (bank_code,bank_name) values ('"+bankCode+"','"+bankName+"');";
				stmt.executeUpdate(sql);
			}

		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	private static void msTransactionQuery() {
		JSONObject result = new JSONObject();
		try {
			JSONObject reqData=new JSONObject();
			String orderCode = "20170917192505958535";
			reqData.put("orderCode", orderCode);//
			Object obj = HttpUtil.sendPostRequest(SysConfig.pospService+"/api/debitNote/msTransactionQuery", CommonUtil.createSecurityRequstData(reqData));
			result=JSONObject.fromObject(obj);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("returnCode", "4004");
			result.put("returnMsg", "请求失败");
		}
		System.out.println(result);
	}
	
	private static void msDrawQuery() {
		JSONObject result = new JSONObject();
		try {
			JSONObject reqData=new JSONObject();
			String orderCode = "20170917192505958535";//"20170715133656013864";
			reqData.put("orderCode", orderCode);//
			Object obj = HttpUtil.sendPostRequest(SysConfig.pospService+"/api/debitNote/msDrawQuery", CommonUtil.createSecurityRequstData(reqData));
			result=JSONObject.fromObject(obj);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("returnCode", "4004");
			result.put("returnMsg", "请求失败");
		}
		System.out.println(result);
	}
	
	public static void handRegister() {
		String memberName = null;//联系人
		String accountName = null;//收款人
		String certNbr = null;//身份证号
		String addr = null;///地址
		String cardNbr = null;//收款卡号
		String busLicenceNbr = null;//营业执照项
		String province = null;//省代号
		String city = null;//市代号
		String county=null;//县代号
		String bankId = null;//卡bin的bankcode
		String subId = null;//
		String mobilePhone = null;//手机号码
		String payCode = null;//E码
		String certFilePath = null;
		String cardFilePath = null;
		String merchantName = null;//商户名称
		String shortName = null;//商户简称
		String settleType = null;//结算类型
		String contactType=null;//联系人类型
		JSONObject result = new JSONObject();
		JSONObject reqData=new JSONObject();
		try {
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
			
			registerTmp.setBankId(bankId);
			registerTmp.setSubId(subId);
			registerTmp.setMobilePhone(mobilePhone);
			registerTmp.setCertPic1(certFilePath);
			registerTmp.setCardPic1(cardFilePath);
			registerTmp.setAccountName(accountName);
			registerTmp.setAccountNumber(cardNbr);
			
			
			registerTmp.setName(merchantName);
			registerTmp.setShortName(shortName);
			registerTmp.setSettleType(settleType);
			//收款码 必填
			registerTmp.setPayCode(payCode);
			registerTmp.setDelFlag("0");
			registerTmp.setStatus("0");
			
			reqData.put("registerTmp", registerTmp);
			result=JSONObject.fromObject(HttpUtil.sendPostRequest(SysConfig.pospService+"/api/registerLogin/toHandleRegister", CommonUtil.createSecurityRequstData(reqData)));
			System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("returnCode", "4004");
			result.put("returnMsg", "请求失败");
		}
	}
	
	public static void main(String[] args) {
		modifyMem(); //修改商户信息
		//qrcodeDecod(); //解码
		//queryMem();    //查询商户信息
		//registerT0(); //T0商户入驻
		//handleDraw(); //手动提现
		//getMsT1FundSettle(); //商户T1资金清算查询
		//queryUnExam();
		//importMemByExcel();//通过excel批量入驻商户
		//stopMember();//停用商户
		//getAD();//获取广告地址
		//registerWxOnly();//单独入驻微信
		//registerBdOnly();//单独入驻百度
		//childMerchant();//子商户配置
		//queryChild();//子商户配置查询
		//importKbin();
		//encodeUrl();//加密url
		//msTransactionQuery();//查询交易结果
		//msDrawQuery();//查询提现交易信息
		//handRegister();//手动注册
	}
}
