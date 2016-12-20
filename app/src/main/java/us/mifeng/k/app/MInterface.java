package us.mifeng.k.app;

/**
 * Created by k on 2016/11/24.
 */

public class MInterface {
    /**
     * 服务器的主机地址
     */
    public static String zhuji = "http://192.168.4.188/Goods";
    /**
     * 注册激活的接口
     */
    public static String zhuce = "/us/mifeng/k/app/common/reg.json";
    /**
     *登录验证的接口
     */
    public static String denglu = "/us/mifeng/k/app/common/login.json";
    /**
     *版本更新的接口
     */
    public static String update = "/us/mifeng/k/app/common/version.json";
    /**
     *已发布的商品列表查询
     */
    public static String yiFaBuLieBiao = "/us/mifeng/k/app/user/issue_list.json";
    /**
     * 已关注的商品列表查询
     */
    public static String yiGuanZhuLieBiao = "/us/mifeng/k/app/user/follow_list.json";
    /**
     * 个人信息查看
     */
    public static String geren = "/us/mifeng/k/app/user/info.json";
    /**
     * 上传头像
     */
    public static String touxiang = "us/mifeng/k/app/user/upload.json";
    /**
     * 邀请码
     */
    public static String yaoqingma = "/us/mifeng/k/app/user/invite.json";
    /**
     * 发布的商品
     */
    public static String fabu = "/us/mifeng/k/app/item/issue.json";
    /**
     * 商品列表查询
     */
    public static String shangpinliebiao = "/us/mifeng/k/app/item/issue.json";
    /**
     * 商品详细查询
     */
    public static String xiangqing = "/us/mifeng/k/app/item/detail.json";
    /**
     * 商品状态变更
     */
    public static String zhuangtai = "/us/mifeng/k/app/item/modify.json";
    /**
     * 关注商品
     */
    public static String guanzhu = "/us/mifeng/k/app/item/follow.json";

}
