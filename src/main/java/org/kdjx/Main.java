package org.kdjx;


import com.benjaminwan.ocrlibrary.Point;
import com.benjaminwan.ocrlibrary.TextBlock;
import org.kdjx.common.Constant;
import org.kdjx.utils.ADBUtil;
import org.kdjx.utils.FileUtil;
import org.kdjx.utils.OCRUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;


public class Main {

    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws Exception {
        ADBUtil adbUtil = new ADBUtil();
        OCRUtil ocrUtil = new OCRUtil();

        // 检验功能是否成功
//        adbUtil.examination();

        // 脚本初始化
        adbUtil.init();

        // 先睡10秒 让游戏加载
        Thread.sleep(10000);
//
        TextBlock textBlock = null;
        // 进入游戏
        long startTime = System.currentTimeMillis();
        while (true) {
            log.info("等待进入主页...");

            String screenshot = adbUtil.screenshot();

            textBlock = ocrUtil.getTextBlock(Constant.startGame,screenshot);

            Thread.sleep(1500);

            if(textBlock != null){
                break;
            }

            long endTime = System.currentTimeMillis();
            if((endTime - startTime) > 20000){ // 设置 20秒的超时时间
                break;
            }
        }

        log.info(".......................");

        // 移动到 进入游戏 并点击
        ArrayList<Point> boxPoint = null;
        if (textBlock != null) {
            boxPoint = textBlock.getBoxPoint();
        }

        Point xy = boxPoint.get(boxPoint.size() / 2);
        Point point = new Point(xy.getX(),xy.getY());
        adbUtil.move(point);

        Thread.sleep(900);

        String number = adbUtil.screenshot();
        String openGame = adbUtil.screenshot();

//        System.out.println("boxPoint:\n" + boxPoint);


        // 删除缓存
//        FileUtil.closeAllFile();

//        // 还是 null 的话 就是启动失败
//        if(textBlock == null){
//            log.error("游戏启动失败.......");
//            return;
//        }
//
//        System.out.println(textBlock.getBoxPoint().getFirst());
//        adbUtil.clickScreen(textBlock.getBoxPoint().getFirst());


//        TextBlock res = null;
//
//        long startTime = System.currentTimeMillis();
//
//        adbUtil.screenshot();
//        while (true){
//            res = ocrUtil.getTextBlock(Constant.startGame);
//            long endTime = System.currentTimeMillis();
//
//            if(endTime - startTime > 12000){
//                break;
//            }
//
//            adbUtil.screenshot();
//            System.out.println(res);
//        }

    }
}