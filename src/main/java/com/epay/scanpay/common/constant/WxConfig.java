package com.epay.scanpay.common.constant;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.PropertiesConfiguration;

import com.epay.scanpay.common.utils.EnvironmentUtil;

public class WxConfig {
 public static String appid;
 public static String appSecret;
	/**
	 * 初始化系统配置
	 */
	static  {
		try {
			Configuration configuration = new PropertiesConfiguration(EnvironmentUtil.propertyPath + "wx_config.properties");
			appid = configuration.getString("appid");
			appSecret = configuration.getString("appSecret");
		} catch (Exception e) {
			throw new RuntimeException("[加载配置文件失败]", e);
		}
	}
}
