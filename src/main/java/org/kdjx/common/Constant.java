package org.kdjx.common;


import com.benjaminwan.ocrlibrary.Point;

import java.io.File;

/**
 * 存放口袋觉醒字
 */

public class Constant {

    /**
     * 识别 进入游戏 的 文字
     */
    public static String START_GAME = "进入游戏";

    /**
     * 识别 点击选区 的 文字
     */
    public static String CLICK_REGION = "点击选区";

    /**
     * 识别 服务器名字 的 文字
     */
    public static String SERVER_NAME = "闪光地鼠";

    /**
     * 识别 竞技 的 文字
     */
    public static String ARENA = "竞技";

    /**
     * 识别 关卡 的 文字
     */
    public static String LEVEL = "关卡";

    /**
     * 返回图标的坐标
     */
    public static Point BACK_ICON = new Point(50, 50);

    /**
     * 进入邮件的坐标  x - 0.084375  y - 0.883
     */
    public static Point EMAIL_ICON = new Point(135, 795);

    /**
     * 进入设置的坐标  x - 0.134375  y - 0.883
     */
    public static Point SETTING_ICON = new Point(215, 795);


    /**
     * 项目路径
     */
    public static String PATH = System.getProperty("user.dir");

    /**
     * 存放截图路径
     */
    public static String PICTUREPATH = Constant.PATH + "\\picture\\";
}
