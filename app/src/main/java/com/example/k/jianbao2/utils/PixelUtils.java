package com.example.k.jianbao2.utils;

import android.content.Context;

import java.text.DecimalFormat;

/**
 * Created by k on 2016/11/24.
 */

public class PixelUtils {
    /*
	 *dp转换为px
	 *
	 * */

    public  static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
    public static String formateDouble(double d){
        DecimalFormat df=new DecimalFormat(".##");
        return df.format(d);

    }
}
