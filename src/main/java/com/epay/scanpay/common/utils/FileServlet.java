package com.epay.scanpay.common.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epay.scanpay.common.constant.SysConfig;

public class FileServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private String urlPattern ;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String basePath = null;
		if("/file_epay_qrcode".equals(urlPattern)){
			basePath = SysConfig.epayCodePath;
		}else{
			basePath = SysConfig.baseUploadFilePath;
		}
		String requestUri = req.getRequestURI();
		String fileName = requestUri.substring(requestUri.indexOf(urlPattern)+urlPattern.length());
		String filePath = basePath + fileName;
		resp.setContentType("image/jpeg");      //设置返回内容格式
	    File file = new File(filePath);       //括号里参数为文件图片路径
	    if(file.exists()){   //如果文件存在
	     InputStream in = new FileInputStream(filePath);   //用该文件创建一个输入流
	     OutputStream os = resp.getOutputStream();  //创建输出流
	     byte[] b = new byte[1024];  
	     while( in.read(b)!= -1){  
	    	 os.write(b);     
	     }
	     in.close(); 
	     os.flush();
	     os.close();
	    }
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doPost(req, resp);
	}

	@Override
	public void init() throws ServletException {
		urlPattern = this.getInitParameter("url-pattern");
	}
	
}
