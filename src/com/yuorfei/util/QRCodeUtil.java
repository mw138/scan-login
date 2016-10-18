package com.yuorfei.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import javax.imageio.ImageIO;
import sun.misc.BASE64Encoder;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;

/**
 * 二维码工具类，生成二维码需要依赖zxing包
 * @author hxy
 *
 */
public class QRCodeUtil {
	
	private static final int BLACK = 0xff000000;
    private static final int WHITE = 0xFFFFFFFF;
    
    /**
     * 生成二维码
     * @param matrix
     * @return
     */
    public static BufferedImage toBufferedImage(BitMatrix matrix){
		int width = matrix.getWidth();
        int height = matrix.getHeight();
        //创建一张bitmap图片，采用图片效果TYPE_INT_ARGB
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, matrix.get(x, y) == true ? BLACK : WHITE);
            }
        }
        return image;
	}
	
    /**
     * 将生成的二维码图片转化为base64编码，直接传给浏览器显示
     * 好处是可以传图片的同时将参数也一并传递过去
     * @param content
     * @param width
     * @param height
     * @return
     */
	public static String toBase64(String content,int width,int height){
		String qrCodeBASE64 = null;
    	try{
	    	BitMatrix bitMatrix = new MultiFormatWriter().encode(content,BarcodeFormat.QR_CODE , width, height);
	    	BufferedImage bufferedImage = QRCodeUtil.toBufferedImage(bitMatrix);
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			ImageIO.write(bufferedImage,"png",byteArrayOutputStream);
			byte[] data = byteArrayOutputStream.toByteArray();
			byteArrayOutputStream.flush();
			byteArrayOutputStream.close();
			BASE64Encoder base64 = new BASE64Encoder();
			qrCodeBASE64 = base64.encode(data);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	return qrCodeBASE64;
	}
	
	/**
	 * 生成二维码以流的形式存在
	 * @param matrix
	 * @param format
	 * @param out
	 * @throws IOException
	 */
	public static void writeToFile(BitMatrix matrix, String format, OutputStream out) throws IOException {
        BufferedImage image = toBufferedImage(matrix);
        ImageIO.write(image, format, out);
    }
	
	/**
	 * 生成的二维码以文件的形式存在
	 * @param matrix
	 * @param format
	 * @param file
	 * @throws IOException
	 */
	public static void writeToFile(BitMatrix matrix, String format, File file) throws IOException {
        BufferedImage image = toBufferedImage(matrix);
        ImageIO.write(image, format, file);
	}
	
	public static void main(String args[]){
		System.out.println( QRCodeUtil.toBase64("123", 100, 100) );
	}

}
