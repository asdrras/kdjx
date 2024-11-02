package org.kdjx.utils;

import com.benjaminwan.ocrlibrary.Point;
import org.kdjx.common.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.io.*;

/**
 * 通过 adb 操作 模拟器
 */

public class ADBUtil {

    private static final Logger log = LoggerFactory.getLogger(ADBUtil.class);

    // ADB 文件路径
    public static File adb = new File("F:\\MuMu Player 12\\shell\\adb.exe");

    // 要启动的应用的包名和活动名
    public static String packageName = "com.dyxt.kdjx";
    public static String activityName = "org.cocos2dx.lua.AppActivity";

    /**
     * 输入 adb 操作
     *
     * @param str
     * @return
     * @throws IOException
     */
    public String getProcess(String... str) throws IOException {
        ProcessBuilder builder = new ProcessBuilder(str);
        return getInputStream(builder.start());
    }

    /**
     * 获取 adb指令的 响应结果
     *
     * @param process
     * @return
     */
    private String getInputStream(Process process) throws IOException {
        InputStream inputStream = process.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder sb = new StringBuilder();


        String str;

        while ((str = br.readLine()) != null) {
            sb.append(str);
        }

        try {
            process.waitFor();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        try {
            process.waitFor();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


        int st = process.exitValue();
        if(st != 0){
            System.gc();
        }
        return sb.toString();
    }

    /**
     * 连接 adb
     *
     * @return
     * @throws IOException
     */
    public String getConnect() throws IOException {
        killADB();
        return getProcess(adb.getPath(), "connect", "127.0.0.1:16384");
    }

    /**
     * 杀死adb服务
     */
    public void killADB() throws IOException {
        getProcess(adb.getPath(), "kill-server");
    }

    /**
     * 获取 root权限
     */
    public String getRoot() throws IOException {
        return getProcess(adb.getPath(), "root");
    }

    /**
     * 启动游戏
     */
    public String getStartGame() throws IOException {
        return getProcess(adb.getPath(), "shell", "am", "start", "-n", packageName + "/" + activityName);
    }

    public String getStartGame(String newPackageName, String newActivityName) throws IOException {
        return getProcess(adb.getPath(), "shell", "am", "start", "-n", newPackageName + "/" + newActivityName);
    }

    /**
     * 截图
     *
     * @throws IOException
     */
    public void screenshot() throws IOException, InterruptedException {
        getProcess(adb.getPath(), "shell", "screencap", "-p", "/data/kdjx.png");
        getProcess(adb.getPath(), "pull", "/data/kdjx.png", Constant.PICTUREPATH.getPath() + "\\kdjx.png");
    }

    /**
     * 点击屏幕
     * @param point 坐标
     */
    public void clickScreen(Point point) throws IOException {
        getProcess(adb.getPath(), "shell", "input", "tap", String.valueOf(point.getX()), String.valueOf(point.getY()));
    }

    /**
     * 初始化
     */
    public void init() throws IOException {

        String connect = getConnect();
//        log.info("连接信息: {}", connect);
        if (!connect.equals("connected to 127.0.0.1:16384")) {
            log.info("连接失败: {}", connect);
            return;
        }
        log.info("连接成功！！！");

        String root = getRoot();
        log.info("获取root权限: {}", root);

        String startGame = getStartGame();
//        log.info("启动游戏: {}", startGame);

        if (!startGame.equals("Starting: Intent { cmp=com.dyxt.kdjx/org.cocos2dx.lua.AppActivity }")) {
            log.info("口袋觉醒，启动！\n失败: {}", startGame);
            return;
        }
        log.info("口袋觉醒成功启动了！！！");

    }
}
