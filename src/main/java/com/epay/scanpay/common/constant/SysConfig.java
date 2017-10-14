package com.epay.scanpay.common.constant;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.PropertiesConfiguration;

import com.epay.scanpay.common.utils.EnvironmentUtil;

public class SysConfig {
 public static String payService;
 public static String pospService;
 public static String baseUploadFilePath;
 public static String epayCodePath;
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
		} catch (Exception e) {
			throw new RuntimeException("[加载配置文件失败]", e);
		}
	}
}
