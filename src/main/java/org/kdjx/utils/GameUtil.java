package org.kdjx.utils;


import com.benjaminwan.ocrlibrary.Point;
import com.benjaminwan.ocrlibrary.TextBlock;
import org.kdjx.Main;
import org.kdjx.common.Constant;
import org.kdjx.entity.GUI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

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
            Main.gui.writeLog("已经登录 正在进入游戏主页");
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
            res.put(textBlock.getText(), textBlock.getBoxPoint());
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
     * 好友赠送体力 领取体力
     */
    public static void oneClickGiftAndReceiveOfBrawn(GUI gui) throws InterruptedException {
        gui.writeLog("正在进行好友的一键领取和一键赠送");
        if (listHashMap != null) {
            ArrayList<Point> points = listHashMap.get("好友");
            try {
                ADBUtil.move(points.get(0));

                Thread.sleep(500);

                String f = ADBUtil.screenshot();
                ArrayList<TextBlock> textBlockList = OCRUtil.getTextBlockList(f);

                TextBlock receive = null;
                TextBlock gift = null;

                for (TextBlock textBlock : textBlockList) {
                    String text = textBlock.getText();

                    if (text.contains("一键领取")) {
                        receive = textBlock;
                    }

                    if (text.contains("一键赠送")) {
                        gift = textBlock;
                    }
                }

                ArrayList<Point> boxPoint1 = receive.getBoxPoint();
                ArrayList<Point> boxPoint2 = gift.getBoxPoint();
                Thread.sleep(1000);
                ADBUtil.move(boxPoint1.get(boxPoint1.size() / 2));

                Thread.sleep(1000);

                ADBUtil.move(boxPoint2.get(boxPoint2.size() / 2));

                // 返回到主界面
                Thread.sleep(800);
                ADBUtil.move(Constant.BACK_ICON);

            } catch (IOException e) {
                gui.writeLog("好友的一键领取和一键赠送失败");
                throw new RuntimeException(e);
            }
            gui.writeLog("成功完成好友的一键领取和一键赠送");
        } else {
            gui.writeLog("未开启游戏.......");
        }
    }

    /**
     * 领取体力
     */
    public static void getBrawn(GUI gui) {
        gui.writeLog("正在进行福利里的领取体力......");

        ArrayList<Point> points = listHashMap.get("福利");

        boolean st = false;

        try {
            ADBUtil.move(points.get(points.size() / 2));

            Thread.sleep(500);

            String screenshot = ADBUtil.screenshot();

            ArrayList<TextBlock> textBlockList = OCRUtil.getTextBlockList(screenshot);

            boolean status = false;

            for (TextBlock textBlock : textBlockList) {
                String t = textBlock.getText();

                boolean st1 = "食用".equals(t);
                ArrayList<Point> boxPoint = textBlock.getBoxPoint();
                if (st1) {
                    Thread.sleep(500);
                    status = true;
                    ADBUtil.move(boxPoint.get(boxPoint.size() / 2));
                }
            }

            st = true;

            Thread.sleep(900);
            ADBUtil.move(Constant.BACK_ICON);

            if (status) {
                Thread.sleep(900);
                ADBUtil.move(Constant.BACK_ICON);
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        } finally {

            try {

                if (!st) {
                    ADBUtil.move(Constant.BACK_ICON);
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }

    /**
     * 领取邮件
     */
    public static void getMail() {
        try {
            ADBUtil.move(Constant.EMAIL_ICON);
            String screenshot = ADBUtil.screenshot();

            ArrayList<TextBlock> textBlockList = OCRUtil.getTextBlockList(screenshot);
            TextBlock t1 = null;
            TextBlock t2 = null;
            TextBlock t3 = null;

            for (TextBlock textBlock : textBlockList) {
                String text = textBlock.getText();
                if ("系统".equals(text)) {
                    t1 = textBlock;
                }

                if ("全部领取".equals(text)) {
                    t2 = textBlock;
                }

                if ("删除已读".equals(text)) {
                    t3 = textBlock;
                }
            }

            ADBUtil.move(t1.getBoxPoint().get(0));
            Thread.sleep(500);
            ADBUtil.move(t2.getBoxPoint().get(0));
            Thread.sleep(500);
            ADBUtil.move(t3.getBoxPoint().get(0));
            Thread.sleep(500);

            ADBUtil.move(Constant.BACK_ICON);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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
