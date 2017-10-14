package com.epay.scanpay.controller;

import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.epay.scanpay.common.constant.SysConfig;
import com.epay.scanpay.common.excep.ArgException;
import com.epay.scanpay.common.utils.CommonUtil;
import com.epay.scanpay.common.utils.HttpUtil;
import com.epay.scanpay.common.utils.MatrixToImageWriter;
import com.epay.scanpay.common.utils.SecurityUtil;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

@Controller
public class CommomServiceController {
	private static Logger logger = LoggerFactory.getLogger(CommomServiceController.class);
	@ResponseBody
	@RequestMapping("/common/getAddrAreaList")
	public JSONObject getAddrAreaList(Model model, HttpServletRequest request) {
		JSONObject result = new JSONObject();
		JSONObject reqData = new JSONObject();
		try {
			String parentId = request.getParameter("areacode");
			if (parentId == null || "".equals(parentId)) {
				parentId = "0";
			}
			reqData.put("areacode", parentId);
			result = JSONObject.fromObject(HttpUtil.sendPostRequest(SysConfig.pospService + "/api/common/getAddrAreaList", CommonUtil.createSecurityRequstData(reqData)));
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			result.put("returnCode", "4004");
			result.put("returnMsg", "请求失败");
			return result;
		}
		return result;
	}
	@ResponseBody
	@RequestMapping("/common/getAreaList")
	public JSONObject getAreaList(Model model, HttpServletRequest request) {
		JSONObject result = new JSONObject();
		JSONObject reqData = new JSONObject();
		try {
			String parentId = request.getParameter("parentId");
			if (parentId == null || "".equals(parentId)) {
				parentId = "0";
			}
			reqData.put("parentId", parentId);
			result = JSONObject.fromObject(HttpUtil.sendPostRequest(SysConfig.pospService + "/api/common/getAreaList", CommonUtil.createSecurityRequstData(reqData)));
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			result.put("returnCode", "4004");
			result.put("returnMsg", "请求失败");
			return result;
		}
		return result;
	}

	@ResponseBody
	@RequestMapping("/common/getBankList")
	public JSONObject getBankList(Model model, HttpServletRequest request) {
		JSONObject result = new JSONObject();
		try {
			JSONObject reqData = new JSONObject();
			reqData.put("bankLevel", 1);
			result = JSONObject.fromObject(HttpUtil.sendPostRequest(SysConfig.pospService + "/api/common/getBankList", CommonUtil.createSecurityRequstData(reqData)));
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			result.put("returnCode", "4004");
			result.put("returnMsg", "请求失败");
			return result;
		}
		return result;
	}
	@ResponseBody
	@RequestMapping("/common/getKBankList")
	public JSONObject getKBankList(Model model, HttpServletRequest request) {
		JSONObject result = new JSONObject();
		try {
			JSONObject reqData = new JSONObject();
			result = JSONObject.fromObject(HttpUtil.sendPostRequest(SysConfig.pospService + "/api/common/getKBankList", CommonUtil.createSecurityRequstData(reqData)));
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			result.put("returnCode", "4004");
			result.put("returnMsg", "请求失败");
			return result;
		}
		return result;
	}

	@ResponseBody
	@RequestMapping("/common/getSubBankList")
	public JSONObject getSubBankList(Model model, HttpServletRequest request) {
		JSONObject result = new JSONObject();
		try {
			JSONObject reqData = new JSONObject();
			String bankId = request.getParameter("bankId");
			String cityId = request.getParameter("cityId");
			if (bankId == null || "".equals(bankId)) {
				throw new ArgException("父级元素参数缺失");
			}
			reqData.put("bankId", bankId);
			reqData.put("cityId", cityId);
			reqData.put("bankLevel", 2);
			result = JSONObject.fromObject(HttpUtil.sendPostRequest(SysConfig.pospService + "/api/common/getBankList", CommonUtil.createSecurityRequstData(reqData)));
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			result.put("returnCode", "4004");
			result.put("returnMsg", "请求失败");
			return result;
		}
		return result;
	}

	@ResponseBody
	@RequestMapping("/common/sendSms")
	public JSONObject sendSms(Model model, HttpServletRequest request) {
		JSONObject result = new JSONObject();
		try {
			JSONObject reqData = new JSONObject();
			String mobilePhone = request.getParameter("mobilePhone");
			String type = request.getParameter("type");
			if (mobilePhone == null || "".equals(mobilePhone)) {
				throw new ArgException("手机号码缺失");
			}
			reqData.put("mobilePhone", mobilePhone);
			reqData.put("type", type);
			result = JSONObject.fromObject(HttpUtil.sendPostRequest(SysConfig.pospService + "/api/sendSms/toSend", CommonUtil.createSecurityRequstData(reqData)));
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			result.put("returnCode", "4004");
			result.put("returnMsg", "请求失败");
			return result;
		}
		return result;
	}

	@ResponseBody
	@RequestMapping("/common/uploadFileImg")
	public JSONObject uploadFileImg(HttpServletRequest request, HttpServletResponse response, String fileDir) throws Exception {
		JSONObject result = new JSONObject();
		response.setContentType("text/html");
		File tempFilePath = new File(request.getSession().getServletContext().getRealPath("/") + "/upload/temp/");
		String baseFilePath = request.getSession().getServletContext().getRealPath("/") + "/upload";
		if (!tempFilePath.exists()) {
			tempFilePath.mkdirs();
		}
		request.setCharacterEncoding("utf-8"); // 设置编码
		// 获得磁盘文件条目工厂
		DiskFileItemFactory factory = new DiskFileItemFactory();
		// 如果没以下两行设置的话，上传大的 文件 会占用 很多内存，
		// 设置暂时存放的 存储室 , 这个存储室，可以和 最终存储文件 的目录不同
		factory.setRepository(tempFilePath);
		// 设置 缓存的大小，当上传文件的容量超过该缓存时，直接放到 暂时存储室
		factory.setSizeThreshold(1024 * 1024);
		// 高水平的API文件上传处理
		ServletFileUpload upload = new ServletFileUpload(factory);
		try {
			List<FileItem> list = upload.parseRequest(request);
			// 获取上传的文件
			FileItem item = getUploadFileItem(list);
			// 获取文件名
			String filename = getUploadFileName(item);
			// 保存后的文件名
			String saveName = generateFileName(3) + filename.substring(filename.lastIndexOf("."));
			String saveFilePath = baseFilePath + fileDir;
			File saveFileParentPath = new File(saveFilePath);
			if (!saveFileParentPath.exists()) {
				saveFileParentPath.mkdirs();
			}
			// 真正写到磁盘上
			item.write(new File(saveFilePath, saveName)); // 第三方提供的
			JSONObject resData = new JSONObject();
			resData.put("filePath", "/upload" + fileDir + "/" + saveName);
			result.put("returnCode", "0000");
			result.put("returnMsg", "上传成功");
			result.put("resData", resData);
			return result;
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			result.put("returnCode", "4004");
			result.put("returnMsg", "文件上传失败，请重试");
			return result;
		}
	}

	@ResponseBody
	@RequestMapping("/common/searchSubBank")
	public JSONObject searchSubBank(Model model, HttpServletRequest request) {
		JSONObject result = new JSONObject();
		try {
			JSONObject reqData = new JSONObject();
			String bankId = request.getParameter("bankId");
			String cityId = request.getParameter("cityId");
			String subBankName = request.getParameter("subBankName");
			if (subBankName == null || "".equals(subBankName)) {
				result.put("returnCode", "4004");
				result.put("returnMsg", "请输入搜索关键字");
				return result;
			}
			reqData.put("bankId", bankId);
			if (!"".equals(cityId) && null != cityId) {
				reqData.put("cityId", cityId);
			}
			reqData.put("subBankName", subBankName);
			reqData.put("bankLevel", 2);
			result = JSONObject.fromObject(HttpUtil.sendPostRequest(SysConfig.pospService + "/api/common/getBankList", CommonUtil.createSecurityRequstData(reqData)));
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			result.put("returnCode", "4004");
			result.put("returnMsg", "请求失败");
			return result;
		}
		return result;
	}

	private FileItem getUploadFileItem(List<FileItem> list) {
		for (FileItem fileItem : list) {
			if (!fileItem.isFormField()) {
				return fileItem;
			}
		}
		return null;
	}

	private String getUploadFileName(FileItem item) {
		// 获取路径名
		String value = item.getName();
		// 索引到最后一个反斜杠
		int start = value.lastIndexOf("/");
		// 截取 上传文件的 字符串名字，加1是 去掉反斜杠，
		String filename = value.substring(start + 1);

		return filename;
	}

	public String generateFileName(int randomLength) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String randomStr = "";
		for (int i = 0; i < randomLength; i++) {
			randomStr += (new Random()).nextInt(10);
		}
		return sdf.format(new Date()) + randomStr;
	}

	@RequestMapping("/common/getQrCode")
	public void getQrCode(HttpServletRequest request, HttpServletResponse response) {

		try {
			String fileName = request.getParameter("qrCode");
			String qrContent = SecurityUtil.base64Decode(request.getParameter("qrCode"));
			response.reset();
			OutputStream out = new BufferedOutputStream(response.getOutputStream());

			int length = 0;

			MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
			Map hints = new HashMap();
			hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
			hints.put(EncodeHintType.MARGIN, 1);
			BitMatrix bitMatrix = multiFormatWriter.encode(qrContent, BarcodeFormat.QR_CODE, 300, 300, hints);
			BufferedImage image = MatrixToImageWriter.toBufferedImage(bitMatrix);
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			ImageIO.write(image, "jpg", os);
			InputStream input = new ByteArrayInputStream(os.toByteArray());
			byte[] buffer = new byte[1024];
			while ((length = input.read(buffer)) != -1) {
				out.write(buffer, 0, length);
			}
			input.close();
			out.flush();
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (WriterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
