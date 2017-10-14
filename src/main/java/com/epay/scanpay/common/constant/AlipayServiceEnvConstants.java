

/**

 * Alipay.com Inc.

 * Copyright (c) 2004-2014 All Rights Reserved.

 */

package com.epay.scanpay.common.constant;


/**
 * 支付宝服务窗环境常量（demo中常量只是参考，需要修改成自己的常量值）
 * 
 * @author taixu.zqq
 * @version $Id: AlipayServiceConstants.java, v 0.1 2014年7月24日 下午4:33:49 taixu.zqq Exp $
 */
public class AlipayServiceEnvConstants {

    /**支付宝公钥-从支付宝服务窗获取*/
    public static final String ALIPAY_PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDDI6d306Q8fIfCOaTXyiUeJHkrIvYISRcc73s3vF1ZT7XN8RNPwJxo8pWaJMmvyTn9N4HQ632qJBVHf8sxHi/fEsraprwCtzvzQETrNRwVxLO5jVmRGi60j8Ue1efIlzPXV9je9mkjzOmdssymZkh2QhUrCmZYI/FCEa3/cNMW0QIDAQAB";

    /**签名编码-视支付宝服务窗要求*/
    public static final String SIGN_CHARSET      = "GBK";

    /**字符编码-传递给支付宝的数据编码*/
    public static final String CHARSET           = "GBK";

    /**签名类型-视支付宝服务窗要求*/
    public static final String SIGN_TYPE         = "RSA";
    
    
    public static final String PARTNER           = "";

    /** 服务窗appId  */
    //TODO !!!! 注：该appId必须设为开发者自己的服务窗id  这里只是个测试id
    public static final String APP_ID            = "2015050400056178";

    //开发者请使用openssl生成的密钥替换此处  请看文档：https://fuwu.alipay.com/platform/doc.htm#2-1接入指南
    //TODO !!!! 注：该私钥为测试账号私钥  开发者必须设置自己的私钥 , 否则会存在安全隐患 
    public static final String PRIVATE_KEY       = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAMxIn1GEp+/Qz/1NgQzWZWlrkUZ8luw5rQXsH7uI8cLTefJvVZ4PeHR1NQFiS52e5k9K8fX4lNJ3lRyXoCcFTgKWnHe1y8H4u1TrNBKBmTz+aSwxy7sA1b9iDuS/O1N9D2xzjtqpfNWsP6/9eN8GE1Js5qBtgQh39BilJkjbVKUjAgMBAAECgYEArUsx86ov+E1QE1YrEmPRlZNNotjQAsaOk4aSCLmMNTTGrzVpEPOYwFopQ+pJsQV55Gv0RC/Ct6P/10h88H0chasfFlra0e5KICo+Co8yhf2PXQmBTBYwStIQFwCmHK/xRGLQlKg5TN1p7n5iGXhDzWQ02KA55XBGU362i7t8wfkCQQDwKzEp3R908KU5NnoiXwiSIp7nkMDIPH028vWiFC6pJ8XkS/97JN2zZKqSEkecEWM0tMf1h2IobfxHSl23o/3tAkEA2b/gSiTBVXHi954nY6Nof1ny6r7B6rQLTSgrI0NKgN9iNOS6jFcWtu9m8j9fp1rpyl2+RaJFlmV4C/DSap1NTwJANjX5pfJ0rEe5c72ZOfLrk1jfN3PUIB6PQPz8dvZJRgNlRs/kvh4nMNwTKF+Z7F98XiREUmaZwHh7Dnh5yP7qTQJAfVOKbCIrhFpAH/TPBvIOOv4yahnQ0p09w/LSoUEhqHrpszfShuO5FyFDRzpie5g/51F9Kebz1lQge/7nnKVc0QJALmiyxc28xeugNEOVUalhBjGRe1EIgtdYfA60igjx0HqY5SBLnx3D6czvWOFTzjmm2KJ6Ve/r3U3gwJLd0xYTsw==";
    //TODO !!!! 注：该公钥为测试账号公钥  开发者必须设置自己的公钥 ,否则会存在安全隐患
    public static final String PUBLIC_KEY        = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDMSJ9RhKfv0M/9TYEM1mVpa5FGfJbsOa0F7B+7iPHC03nyb1WeD3h0dTUBYkudnuZPSvH1+JTSd5Ucl6AnBU4Clpx3tcvB+LtU6zQSgZk8/mksMcu7ANW/Yg7kvztTfQ9sc47aqXzVrD+v/XjfBhNSbOagbYEId/QYpSZI21SlIwIDAQAB";

    /**支付宝网关*/
    public static final String ALIPAY_GATEWAY    = "https://openapi.alipay.com/gateway.do";

    /**授权访问令牌的授权类型*/
    public static final String GRANT_TYPE        = "authorization_code";
}