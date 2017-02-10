package com.etong.android.util;

import java.text.NumberFormat;

import com.etong.android.jxappdata.R;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;


public class SetTextColorBySymbolUtils {
	static NumberFormat mNumberFormat=NumberFormat.getPercentInstance();
	// 根据传过来的值的正负  来设置字的颜色
	public static void setColors(Context context,Double value,TextView view){
		if(null != value){			
			if(value>0){
				view.setTextColor(context.getResources().getColor(R.color.green_text_color));
			    view.setText("+"+mNumberFormat.format(value));
//				view.setText("+"+String.format("%.2f",value)+"%");
				
			}else if(value<0){
				view.setTextColor(context.getResources().getColor(R.color.red_text_color));
			    view.setText(mNumberFormat.format(value)+"");
//				view.setText(String.format("%.2f",value)+"%");
			}else{
				view.setText(0.00+"%");
				view.setTextColor(context.getResources().getColor(R.color.green_text_color));
			}
		}
	}
	
	
	//根据值的正负设置图片
	public static void setImage(Double value,TextView textView,ImageView imageView,String up,String down){
		if(null != value){	
		if(value>=0){
			imageView.setBackgroundResource(getImageIdByName(up));
			textView.setText(mNumberFormat.format(value)+"");
//			textView.setText(String.format("%.2f",value)+"%");
		}else {
			
			imageView.setBackgroundResource(getImageIdByName(down));
			textView.setText(String.valueOf(mNumberFormat.format(value)).substring(1));
//			textView.setText(String.format("%.2f",value).substring(1)+"%");
			
			}
		}
		
	}
	
	/**  
     * 根据图片名称获取R.java中对应的id  
     *   
     * @param name  
     * @return  
     */  
    public static int getImageIdByName (String name) {  
        int value = 0;  
        if (null != name) {  
            if (name.indexOf(".") != -1) {  
                name = name.substring(0, name.indexOf("."));  
            }  
            Class<com.etong.android.jxappdata.R.drawable> cls = R.drawable.class;
            try {    
                value = cls.getDeclaredField(name).getInt(null);    
            } catch (Exception e) {    
                  
            }             
        }  
        return value;  
    }  
}
