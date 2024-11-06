package org.kdjx.utils;


import com.benjaminwan.ocrlibrary.Point;
import com.benjaminwan.ocrlibrary.TextBlock;
import org.kdjx.common.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 用于 游戏 操作
 */

public class GameUtil {

    private static final Logger log = LoggerFactory.getLogger(GameUtil.class);
    private static HashMap<String, ArrayList<Point>> listHashMap;

    /**
     * 点击 进入游戏
     */
    public static boolean clickEntryGame() throws IOException, InterruptedException {
        String screenshot = ADBUtil.screenshot();
        Point point = OCRUtil.getPoint(screenshot, Constant.START_GAME);

        if (point != null) {

            ADBUtil.move(point);
            log.info("点击 进入游戏文字成功.......");
            return true;
        }

        log.error("当前界面没有 进入游戏 文字.....");
        return false;
    }

    /**
     * 是否登录
     */
    public static void clickLogin() throws IOException, InterruptedException {
        String screenshot = ADBUtil.screenshot();

        TextBlock textBlock = OCRUtil.getTextBlock(screenshot, Constant.CLICK_REGION);

        if (textBlock != null) { // 登录操作
            login();
        } else { // 未登录的 操作
            noLogin();
        }
    }

    /**
     * 已经 登录 的 操作
     */
    public static void login() throws IOException, InterruptedException {
        boolean st = clickEntryGame();

        if (st) {
            log.info("登录成功......");
        } else {
            log.error("登录失败......");
        }
        return;
    }

    /**
     * 未登录的操作
     */
    public static void noLogin() {

    }

    /**
     * 获取游戏主界面的 内容
     */
    public static void getIndexGameInfo() throws IOException, InterruptedException {
        String screenshot = ADBUtil.screenshot();
        ArrayList<TextBlock> textBlockList = OCRUtil.getTextBlockList(screenshot);

        HashMap<String, ArrayList<Point>> res = new HashMap<>();

        for (TextBlock textBlock : textBlockList) {
            System.out.println(textBlock.getText());
        }

        listHashMap = res;
    }

    /**
     * 验证是否在游戏主界面
     */
    public static boolean checkGameIndex() {
        return false;
    }

    /**
     * 获取 主界面的活动的坐标
     */
    public static Point getIndexGameXY(String key) {

        boolean isKey = listHashMap.containsKey(key);

        if (isKey) {
            ArrayList<Point> points = listHashMap.get(key);
            return points.get(points.size() / 2);
        }

        return null;
    }
}
