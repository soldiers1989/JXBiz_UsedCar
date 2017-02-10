package com.etong.android.jxappusedcar.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import com.etong.android.jxappusedcar.R;

public class ColorImageUtils {

    public static Drawable colorToImage(String color,Context context) {

        // 获取屏幕像素相关信息
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(dm);
        int mWidth = dm.widthPixels/11;

        Bitmap anotherBitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.uc_filter_color_another);

        Bitmap bitmap = Bitmap.createBitmap(mWidth, mWidth, Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        RectF outerRect = new RectF(0, 0, mWidth, mWidth);
        // 产生一个圆角矩形
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
//		Paint paint = new Paint();
        paint.setAntiAlias(true);// 消除锯齿
        if(!TextUtils.isEmpty(color)){
            paint.setColor(Color.parseColor(color));
            //画圆角矩形
            canvas.drawRoundRect(outerRect, mWidth/3, mWidth/3, paint);
        }else {
            //把图片画到矩形去
            canvas.drawBitmap(anotherBitmap, null, outerRect, paint);
        }


        Drawable drawable = new BitmapDrawable(bitmap);

        return drawable;
    }
}
