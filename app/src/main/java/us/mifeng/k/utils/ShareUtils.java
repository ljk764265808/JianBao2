package us.mifeng.k.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by k on 2016/11/30.
 */

public class ShareUtils {
    private static final String FILE_NAME = "my_share";

    /**
     * 保存数据的方法，需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
     */

    public static void setData(Context context , String key, Object object){
        String type = object.getClass().getSimpleName();
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        if("String".equals(type)){
            editor.putString(key, (String)object);
        }
        else if("Integer".equals(type)){
            editor.putInt(key, (Integer)object);
        }
        else if("Int".equals(type)){
            editor.putInt(key,(Integer)object);
        }
        else if("Boolean".equals(type)){
            editor.putBoolean(key, (Boolean)object);
        }

        editor.commit();
    }

    /**
     * 得到保存数据的方法，根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
     */
    public static Object getData(Context context , String key, Object defaultObject){
        String type = defaultObject.getClass().getSimpleName();
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);

        if("String".equals(type)){
            return sp.getString(key, (String)defaultObject);
        }
        else if("Integer".equals(type)){
            return sp.getInt(key, (Integer)defaultObject);
        }
        else if("Int".equals(type)){
            return sp.getInt(key, (Integer) defaultObject);
        }
        else if("Boolean".equals(type)){
            return sp.getBoolean(key, (Boolean)defaultObject);
        }

        return null;

    }
}
