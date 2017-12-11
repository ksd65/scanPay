package com.epay.scanpay.common.constant;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.PropertiesConfiguration;

import com.epay.scanpay.common.utils.EnvironmentUtil;

public class SysConfig {
 public static String payService;
 public static String pospService;
 public static String baseUploadFilePath;
 public static String epayCodePath;
 public static String channel;
 public static String ipsFormAction;
 public static String ipsDirectStr;
 public static String ipsMerCode;
 public static String ipsMerName;
 public static String ipsMerAccount;
 public static String ipsMerchantUrl;
 public static String ipsServerUrl;
	/**
	 * 初始化系统配置
	 */
	static  {
		try {
			Configuration configuration = new PropertiesConfiguration(EnvironmentUtil.propertyPath + "config.properties");
			payService = configuration.getString("payService");
			pospService = configuration.getString("pospService");
			baseUploadFilePath = configuration.getString("baseUploadFilePath");
			epayCodePath = configuration.getString("epayCodePath");
			channel = configuration.getString("channel");
			ipsFormAction = configuration.getString("ipsFormAction");
			ipsDirectStr = configuration.getString("ipsDirectStr");
			ipsMerCode = configuration.getString("ipsMerCode");
			ipsMerName = configuration.getString("ipsMerName");
			ipsMerAccount = configuration.getString("ipsMerAccount");
			ipsMerchantUrl = configuration.getString("ipsMerchantUrl");
			ipsServerUrl = configuration.getString("ipsServerUrl");
		} catch (Exception e) {
			throw new RuntimeException("[加载配置文件失败]", e);
		}
	}
}
