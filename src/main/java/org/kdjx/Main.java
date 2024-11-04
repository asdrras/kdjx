package org.kdjx;


import com.benjaminwan.ocrlibrary.Point;
import com.benjaminwan.ocrlibrary.TextBlock;
import org.kdjx.common.Constant;
import org.kdjx.utils.ADBUtil;
import org.kdjx.utils.FileUtil;
import org.kdjx.utils.GameUtil;
import org.kdjx.utils.OCRUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Queue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class Main {

    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws Exception {

        // 检验功能是否成功
//        adbUtil.examination();

        // 脚本初始化
        ADBUtil.init();

        // 先睡10秒 让游戏加载
        Thread.sleep(10000);

        log.info("等待进入主页...");

        // 第一个出现 进入游戏 文字
        boolean st = GameUtil.clickEntryGame();

        Thread.sleep(3300);

        GameUtil.clickLogin();

        log.info("成功进入 游戏主界面");

        Thread.sleep(9000);

        GameUtil.getIndexGameInfo();

        Thread.sleep(2000);

        ADBUtil.move(new Point(50,50));

        Thread.sleep(2000);

        ADBUtil.move(new Point(50,50));

        // 删除截图缓存
//        FileUtil.closeAllFile();
    }
}