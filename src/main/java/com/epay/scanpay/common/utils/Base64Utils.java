package com.epay.scanpay.common.utils;

import java.io.UnsupportedEncodingException;

import org.apache.commons.codec.binary.Base64;
public class Base64Utils {
	 //base64编码  编码后不换行 
    public static String getBASE64NoWrap(String s)
    {
        if (s == null)
            return null;
        try
        {
            return Base64.encodeBase64String(s.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
    
    //base64编码  编码后不换行   解码
    public static String getFromBASE64NoWrap(String s)
    {
        if (s == null)
            return null;
        try
        {
        	byte[] b = Base64.decodeBase64(s);
            return new String(b, "UTF-8");
        } catch (UnsupportedEncodingException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
}
