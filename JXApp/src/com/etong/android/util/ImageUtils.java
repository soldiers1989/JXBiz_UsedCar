package com.etong.android.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.os.Environment;
import android.view.View;

public class ImageUtils {


	private static int backgroundColors[]={
		   Color.argb(255, 255, 255, 153),
		   Color.argb(255, 255, 204, 153),
		   Color.argb(255, 255, 153, 153),
		   Color.argb(255, 255, 102, 153),
		   Color.argb(255, 255, 51, 153),
		   Color.argb(255, 204, 255, 153),
		   Color.argb(255, 204, 102, 204),
		   Color.argb(255, 153, 255, 204),
		   Color.argb(255, 153, 102, 204),
		   Color.argb(255, 102, 102, 204),
		   Color.argb(255, 204, 51, 153),
		   Color.argb(255, 204, 153, 153),
		   Color.argb(255,255, 102, 102),
		   Color.argb(255, 255, 102, 204),
		   Color.argb(255, 204, 255, 204),
		   Color.argb(255, 204, 255, 255)
	};
	public static Bitmap nameToImage(String name){
		String temp= str2HexStr(name);
		String temp2=temp.substring(temp.length()-1);
		int n=Integer.parseInt(temp2,16)%backgroundColors.length;//16转10进制取模
		//画圆
		Bitmap bitmap = Bitmap.createBitmap(400, 400, Config.ARGB_8888); 
		Canvas canvas = new Canvas(bitmap); 
		Paint paint = new Paint(); 
		paint.setTextAlign(Align.LEFT);// 若设置为center，则文本左半部分显示不全 paint.setColor(Color.RED); 
		paint.setAntiAlias(true);// 消除锯齿 
		paint.setTextSize(20);
		
		paint.setColor(backgroundColors[n]);
		
		canvas.drawCircle(200, 200, 200, paint);
		
		
		
		//画字
		Canvas canvasText = new Canvas(bitmap); 
		Paint paintText = new Paint(); 
		paintText.setTextAlign(Align.CENTER);// 若设置为center，则文本左半部分显示不全 paint.setColor(Color.RED); 
		paintText.setAntiAlias(true);// 消除锯齿 
		paintText.setTextSize(200);
		paintText.setColor(Color.WHITE);
		paintText.setFakeBoldText(true);
		
		canvasText.drawText(name.substring(name.length()-1), 195, 270, paintText);
		canvasText.save(Canvas.ALL_SAVE_FLAG);
		canvas.save(Canvas.ALL_SAVE_FLAG);
		canvas.restore();
		canvasText.restore();
		
		return bitmap;
	}
	
	
	//字符串转16进制
	 public static String str2HexStr(String str) {    
	        char[] chars = "0123456789ABCDEF".toCharArray();    
	        StringBuilder sb = new StringBuilder("");  
	        byte[] bs = str.getBytes();    
	        int bit;    
	        for (int i = 0; i < bs.length; i++) {    
	            bit = (bs[i] & 0x0f0) >> 4;    
	            sb.append(chars[bit]);    
	            bit = bs[i] & 0x0f;    
	            sb.append(chars[bit]);    
	        }    
	        return sb.toString();    
	    }
}
