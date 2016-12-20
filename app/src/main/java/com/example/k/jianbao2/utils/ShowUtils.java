package com.example.k.jianbao2.utils;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

/**
 * Created by k on 2016/11/24.
 */

public class ShowUtils {
    static String str=null;
    private static Toast toast;
    public static void show(Context context, String str){
        if (toast==null) {
            toast = Toast.makeText(context,str, Toast.LENGTH_LONG );
        }
        if(!TextUtils.isEmpty(str)){
            toast.setText(str);
        }
        toast.show();
    }
}
