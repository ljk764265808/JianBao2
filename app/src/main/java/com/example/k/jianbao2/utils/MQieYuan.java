package com.example.k.jianbao2.utils;

/**
 * Created by k on 2016/11/28.
 */


import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;

/**
 * 这是一个图片切原工具类
 * 类名.方法就可以得到一个圆图了，就用这2行代码
 * Bitmap bit=BitmapFactory.decodeResource(getResources(), 想切的图片);
 * ImageView的id.setImageBitmap(QieYuan.getYuan(bit));
 * */

public class MQieYuan {
    public static Bitmap getYuan(Bitmap bitmap){
        Bitmap output= Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = bitmap.getWidth() / 2;
        final float roundPY = bitmap.getHeight() / 2;

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPY, paint);

        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;

    }
}
