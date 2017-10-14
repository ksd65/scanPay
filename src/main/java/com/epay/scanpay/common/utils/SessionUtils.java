package com.epay.scanpay.common.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import com.epay.scanpay.entity.MemberInfo;

public class SessionUtils {
	private final static String MEMBERINFOSESSION = "userInfoSession";
	
	public static void addMemberInfoSession(HttpServletRequest request,Object object){
		HttpSession session = request.getSession();
		session.setAttribute(MEMBERINFOSESSION,object);
	}
	
	public static MemberInfo getMemberInfoSession(HttpServletRequest request){
		HttpSession session = request.getSession();
		JSONObject object = (JSONObject) session.getAttribute(MEMBERINFOSESSION);
		if(object == null){
			return null;
		}
		return (MemberInfo) JsonBeanReleaseUtil.jsonToBean(object.toString(), MemberInfo.class);
	}
	
	public static void removeMemberInfoSession(HttpServletRequest request){
		HttpSession session = request.getSession(false);
		session.removeAttribute(MEMBERINFOSESSION);
	}
	
	
}
