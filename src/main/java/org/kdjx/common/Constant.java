package org.kdjx.common;

import java.io.File;

/**
 * 存放口袋觉醒字
 */

public class Constant {


    /**
     * 识别进入游戏
     */
    public static String startGame = "进入游戏";

    /**
     * 项目路径
     */
    public static String PATH = System.getProperty("user.dir");

    /**
     * 存放截图路径
     */
    public static File PICTUREPATH = new File(Constant.PATH + "\\src\\main\\resources\\picture\\kdjx.png");
}
