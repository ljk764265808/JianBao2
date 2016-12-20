package us.mifeng.k.action;

/**
 * Created by 黑夜之火 on 2016/12/10.
 */

public class Action {
    //打开相机的请求码
    public static  int REQUEST_CODE_OPEN_CAPTURE = 0x11;
    //打开相册的请求码
    public static int REQUEST_CODE_OPEN_ALDUM = 0x12;
    //在相册中选择多个图片的请求码
    public static int REQUEST_CODE_MULT_ALDUM = 0x13;
    //打开相册
    public static String ACTION_MULT_SELECTED = "luminous.Action.MultPicture";
    public static String ACTION_SIGLE_SELECTED = "luminous.Action.SinglePicture";

}
