

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
    public static final String APP_ID            = "2017032306367271";

    //开发者请使用openssl生成的密钥替换此处  请看文档：https://fuwu.alipay.com/platform/doc.htm#2-1接入指南
    //TODO !!!! 注：该私钥为测试账号私钥  开发者必须设置自己的私钥 , 否则会存在安全隐患 
    public static final String PRIVATE_KEY       = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAKlROWWIW2wk1+c9Hx53b4Bd/VR0Q9PJYm+rhIMcHI+hwoCBD2nDZTSatb+qqwW74ihDqXfU9tIz6Bc2Tx++miR9h3pWUXRiuzU8yT/W1pnyF69inc7N3I8MKx/Y706E6bweWJkNCOoA8f0xT1sYI7P8lr3C9I5ue4qlsQX8ZphlAgMBAAECgYAlfgbnpLoZyE7GtZIefPQDyMw/1z7ttrpFYAWHwsHQTFF1gx6WnN3cOCXmuzQDoQcDQ3hTQKUnBrdt7Gi47svSzIOK4Q6VHhJTDefcpvXwWY+B1CeL86miltAicBL7Z0QYAQG/c+CNHI9b7JlqvqCCkDCNkrRXEwyn+gR5XfYmQQJBANKICGVcPPxGMhoAvSvERdMSLLFi6A1/+Aqu4RViJCH1I0hLvQO/JUUy0kXVOvqOPWnDrOq99y8sFuLjexJ4IBMCQQDN4oZ22/zAXQBPL6CbgI8eveaLPklXRDTGcnxKXvTcVNtbYc70HpEh+7OZh9N78FOP8m9XMQ454b64tFzdPSSnAkEAttcnm4gI+vunxYeAw0L5dT9ii0gfyqGp9PU+TAjo4oj4dHA6nsdiAgAKOFXgm/vugC1NvqzuwQwkcDYqC7dB+wJAWeghBgidADfcqbfHsABc/3S4F3hUuKoNTdey1RtQXGMmA2rb4Bj1Ed0DmfkJbmcNZp/c5TuD1dzx12DT6L0JowJBALL/eeAWRBWj1WkJV08O1jvDUGvTBa4aM34sSd2AAysHMuDHPFOTrOGZ6d5wBEMecxjTe4s4QZRiuvETof2gGY0=";
    //TODO !!!! 注：该公钥为测试账号公钥  开发者必须设置自己的公钥 ,否则会存在安全隐患
    public static final String PUBLIC_KEY        = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCpUTlliFtsJNfnPR8ed2+AXf1UdEPTyWJvq4SDHByPocKAgQ9pw2U0mrW/qqsFu+IoQ6l31PbSM+gXNk8fvpokfYd6VlF0Yrs1PMk/1taZ8hevYp3OzdyPDCsf2O9OhOm8HliZDQjqAPH9MU9bGCOz/Ja9wvSObnuKpbEF/GaYZQIDAQAB";

    /**支付宝网关*/
    public static final String ALIPAY_GATEWAY    = "https://openapi.alipay.com/gateway.do";

    /**授权访问令牌的授权类型*/
    public static final String GRANT_TYPE        = "authorization_code";
}