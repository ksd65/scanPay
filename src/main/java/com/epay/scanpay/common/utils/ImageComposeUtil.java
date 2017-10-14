package com.epay.scanpay.common.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;

import javax.imageio.ImageIO;

public class ImageComposeUtil {
	
	
	/**
	 * 
	 * @param template 为模板图片位置 如：d:/33.jpg 
	 * @param qrCode 为二维码图片位置 如 d:/EE.jpg
	 * @param headImg 为头像   传如 null 不加头像
	 * @param userName 为用户名
	 * @param newImgPath 为 新图片的存放位置
	 * @param newImgName 为生成图片的名称
	 * @throws Exception
	 */
	
	public static void createUserImage(String template,String qrCode,String headImg,String userName,String newImgPath,String newImgName) throws Exception{
		File fileOne = new File(template);  //读取模板
		BufferedImage ImageOne = ImageIO.read(fileOne);			
		int width = ImageOne.getWidth();// 图片宽度  
		int height = ImageOne.getHeight();// 图片高度 	
		
		// 对模板图片做相同的处理	
		int[] ImageArrayOne = new int[width * height];
		ImageArrayOne = ImageOne.getRGB(0, 0, width, height, ImageArrayOne, 0, width);	
		
		//生成一个新的图片
		BufferedImage ImageNew = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	    ImageNew.setRGB(0, 0, width, height, ImageArrayOne, 0, width);  //在新图片里加载 模板	   
        
	    inputImgInImg(qrCode, 270, 270, ImageNew, width/2-135, 520);	    
	    
	    inputImgInImg(headImg, 130, 130, ImageNew,  width/2-65, 193);	 
		
	    File outFile = new File(newImgPath+"/"+newImgName); //新图片位置及名称写入 	  
	    	  				
		//文字处理
		String userNameNew ="您的好友";
		if(userName.length()>8){
			userNameNew = userName.substring(0, 8);
		}else{
			userNameNew = userName;
		}	
		int chinese = 0;
		for (int i = 0; i < userNameNew.length(); i++) {
			if(userNameNew.substring(i, i + 1).matches("[\\u4e00-\\u9fa5]+")){
				chinese = chinese+1;
			}
		}  	
		int english=userNameNew.length()-chinese;
		
		int wordlength=chinese*40+english*20+(userNameNew.length()-1)*3;
		Color nickNameColor=new Color(121, 19, 104);
		inputCharactersInImg(userNameNew, new Font("微软雅黑", Font.BOLD, 40), ImageNew,nickNameColor, 	((width-wordlength)/2), 320);   
	
        ImageIO.write(ImageNew, "jpg", outFile);
	}
	

	/**
	 * 	 * @description： 将文字写入指定的图片
	 */
	public static void inputCharactersInImg(String content,Font font,BufferedImage Image,Color color,int x ,int y )  throws Exception {
 		if(content!=null&&!"".equals(content)){
 			Graphics2D g = (Graphics2D)Image.getGraphics();  
 	 		g.setPaint(color);  //设置字体颜色
 	 		g.setFont(font);    //设置字体类型 大小
 	 		FontRenderContext context = g.getFontRenderContext();//44  
 	    	Rectangle2D bounds = font.getStringBounds(content, context);
 	 		double x1 = x;
 	 		double y1 = y;
 	 		double ascent1 = -bounds.getY();
 	 		double baseY1 = y1 + ascent1;
 	 		g.drawString(content, (int) x1, (int) baseY1);	
 		}		
	}
	

/**
 * 两个张图片合成
 * @param sourceImage
 * @param width
 * @param height
 * @param targetImage
 * @param x
 * @param y
 * @throws Exception
 */
	public static void inputImgInImg(String sourceImage,int width,int height,BufferedImage targetImage,int x ,int y)  throws Exception {
			File sourceImagefile = new File(sourceImage);  
			if(sourceImagefile.exists()){
				BufferedImage image = ImageIO.read(sourceImagefile);
				
				//等比缩放
				height=image.getHeight()*width/image.getWidth();
				
				BufferedImage resizeImage = resize(image, width,height);
		    	int[] ImageArraySmall = new int[width * height];
		    	ImageArraySmall = resizeImage.getRGB(0, 0, width, height, ImageArraySmall, 0,width);
		    	targetImage.setRGB(x,y, width,height, ImageArraySmall, 0, width);// 在新图片里加载 二维码
			}
	}
	
	/**
	 * 改变图片的尺寸
	 * 
	 * @param source
	 *            源文件
	 * @param targetW
	 *            目标长
	 * @param targetH
	 *            目标宽
	 */
	public static BufferedImage resize(BufferedImage source, int targetW, int targetH) throws Exception {
		int type = source.getType();
		BufferedImage target = null;
		double sx = (double) targetW / source.getWidth();
		double sy = (double) targetH / source.getHeight();
		// 这里想实现在targetW，targetH范围内实现等比缩放。如果不需要等比缩放
		// 则将下面的if else语句注释即可
		if (sx > sy) {
			sx = sy;
			targetW = (int) (sx * source.getWidth());
		} else {
			sy = sx;
			targetH = (int) (sy * source.getHeight());
		}
		if (type == BufferedImage.TYPE_CUSTOM) { 
			ColorModel cm = source.getColorModel();
			WritableRaster raster = cm.createCompatibleWritableRaster(targetW, targetH);
			boolean alphaPremultiplied = cm.isAlphaPremultiplied();
			target = new BufferedImage(cm, raster, alphaPremultiplied, null);
		} else {
			// 固定宽高，宽高一定要比原图片大
			// target = new BufferedImage(targetW, targetH, type);
			target = new BufferedImage(800, 800, type);
		}

		Graphics2D g = target.createGraphics();

		// 写入背景
		//g.drawImage(ImageIO.read(new File("ok/blank.png")), 0, 0, null);

		// smoother than exlax:
		g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g.drawRenderedImage(source, AffineTransform.getScaleInstance(sx, sy));
		g.dispose();
		return target;
	}
	
	public static void main(String[] args) {
		try {
			createUserImage("D:/upload/1/t.jpg", "D:/upload/1/qr.png", "D:/upload/1/header.png", "起来抓猫", "D:/upload/1/", "qilai.jpg");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
