package com.epay.scanpay.common.utils;

import javax.servlet.http.HttpServletRequest;

public class PageInitUtil {
	
	private static Integer currentPage = 1;
	private static Integer pageSize = 10;
	
	public static void init(HttpServletRequest request){
		if(request.getParameter("currentPage") != null){
			currentPage = Integer.parseInt(request.getParameter("currentPage"));
		}else{
			currentPage = 1;
		}
		if(request.getParameter("pageSize") != null){
			pageSize = Integer.parseInt(request.getParameter("pageSize"));
		}else{
			pageSize = 10;
		}
	}
	
	public static Integer getLimitStart(){
		return (currentPage-1)*pageSize;
	}
	
	public static Integer getLimitSize(){
		return currentPage*pageSize;
	}
}
