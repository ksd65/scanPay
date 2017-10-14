package com.epay.scanpay.common.utils;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;


public final class MatrixToImageWriter {

  private static final int BLACK = 0xFF000000;
  private static final int WHITE = 0xFFFFFFFF;
  private static final int BLUE = 0x63B2FB;

  private MatrixToImageWriter() {}

  
  public static BufferedImage toBufferedImage(BitMatrix matrix) {
    int width = matrix.getWidth();
    int height = matrix.getHeight();
    BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        image.setRGB(x, y, matrix.get(x, y) ? BLACK : WHITE);
      }
    }
    return image;
  }

  
  public static void writeToFile(BitMatrix matrix, String format, File file)
      throws IOException {
    BufferedImage image = toBufferedImage(matrix);
    if (!ImageIO.write(image, format, file)) {
      throw new IOException("Could not write an image of format " + format + " to " + file);
    }
  }

  
  public static void writeToStream(BitMatrix matrix, String format, OutputStream stream)
      throws IOException {
    BufferedImage image = toBufferedImage(matrix);
    if (!ImageIO.write(image, format, stream)) {
      throw new IOException("Could not write an image of format " + format);
    }
  }
  
  
public static void main(String[] args) {
	try {
        
	     String content = "http://www.baidu.com";
	     String path = "E://uploadFile/wxFile/";
	     
	     MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
	     
	     Map hints = new HashMap();
	     hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
	     BitMatrix bitMatrix = multiFormatWriter.encode(content, BarcodeFormat.QR_CODE, 500, 500,hints);
	     File file1 = new File(path,"ceshi.jpg");
	     MatrixToImageWriter.writeToFile(bitMatrix, "jpg", file1);
	     
	 } catch (Exception e) {
	     e.printStackTrace();
	 }
}
  
  

}