package org.kdjx;

import com.benjaminwan.ocrlibrary.TextBlock;
import org.kdjx.utils.ADBUtil;
import org.kdjx.utils.OCRUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;


public class Main {


    private static final Logger log = LoggerFactory.getLogger(Main.class);
    public static ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

    public static void main(String[] args) throws Exception {
        ADBUtil adbUtil = new ADBUtil();
        OCRUtil ocrUtil = new OCRUtil();

        adbUtil.init();

        TextBlock textBlock = null;

        // 进入游戏
        long startTime = System.currentTimeMillis();
        while (true) {
            log.info("等待进入主页...");
            adbUtil.screenshot();
            textBlock = ocrUtil.getTextBlock("进入游戏");
            if(textBlock != null){
                break;
            }
            long endTime = System.currentTimeMillis();
            if((endTime - startTime) > 20000){ // 设置 12秒的超时时间
                break;
            }
        }

        // 还是 null 的话 就是启动失败
        if(textBlock == null){
            log.error("游戏启动失败.......");
            return;
        }

        System.out.println(textBlock.getBoxPoint().getFirst());
        adbUtil.clickScreen(textBlock.getBoxPoint().getFirst());


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