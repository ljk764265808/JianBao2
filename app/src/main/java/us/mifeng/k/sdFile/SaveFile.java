package us.mifeng.k.sdFile;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by 黑夜之火 on 2016/12/9.
 */

public class SaveFile {
    public static boolean isScardOk(){
        boolean isStruts = false;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            isStruts = true;
        }
        return isStruts;
    }
    //对图片进行存储
    public static void saveBitmap(Bitmap bitmap){
        FileOutputStream b = null;
        File file = new File(Environment.getExternalStorageDirectory()+"/myImage/");
        file.mkdirs();//创建文件夹
        String fileName = Environment.getExternalStorageDirectory()+"/myImage/ww11.jpg";
        try {
            b = new FileOutputStream(fileName);
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,b);
            b.flush();
            b.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }
    }
    public static Bitmap getBitmap(){
        Bitmap bitmap = null;
        String fileName = Environment.getExternalStorageDirectory()+"/myImage/ww11.jpg";
        try {
            FileInputStream inputStream = new FileInputStream(new File(fileName));
            bitmap = BitmapFactory.decodeStream(inputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return bitmap;
    }
}
