package org.kdjx.utils;

import com.benjaminwan.ocrlibrary.OcrResult;
import com.benjaminwan.ocrlibrary.Point;
import io.github.mymonstercat.Model;
import io.github.mymonstercat.ocr.InferenceEngine;
import io.github.mymonstercat.ocr.config.ParamConfig;
import org.kdjx.common.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.io.*;
import java.util.Arrays;
import java.util.UUID;

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
     * st : true 返回 String false 返回 Byte[]
     *
     * @param str
     * @return
     * @throws IOException
     */
    public String getProcess(String... str) throws IOException {
        ProcessBuilder builder = new ProcessBuilder(str);
        return getInputStreamByString(builder.start());
    }

    /**
     * 获取 adb指令的 响应结果
     *
     * @param process
     * @return
     */
    private String getInputStreamByString(Process process) throws IOException {
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
        if (st != 0) {
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
     * 查看连接的设备
     */
    public String getDevices() throws IOException {
        return getProcess(adb.getPath(),"devices");
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
     * 关闭游戏
     */
    public String closeGame() throws IOException {
        return getProcess(adb.getPath(), "shell", "am", "force-stop", packageName);
    }

    /**
     * 截图
     * @throws IOException
     */
    public String screenshot() throws IOException, InterruptedException {
//        getProcess(adb.getPath(), "shell", "screencap", "-p", "/data/kdjx.png");
//        getProcess(adb.getPath(), "pull", "/data/kdjx.png", Constant.PICTUREPATH.getPath() + "\\kdjx.png");
//        ProcessBuilder builder = new ProcessBuilder(adb.getPath(), "shell", "screencap", "-p");


        ProcessBuilder builder = new ProcessBuilder(adb.getPath(), "exec-out", "screencap", "-p");
        Process process = builder.start();

        // 获取命令的标准输出流
        InputStream inputStream = process.getInputStream();

        UUID uuid = UUID.randomUUID();
        String name = uuid.toString() + ".png";

        // 将输出流写入到内存中
        try (FileOutputStream fileOutputStream  = new FileOutputStream(Constant.PICTUREPATH + "\\"+ name)) {
            byte[] buffer = new byte[1024 * 1024 * 5];
            int length;

            while ((length = inputStream.read(buffer)) != -1){
                fileOutputStream.write(buffer,0,length);
            }

        }catch (IOException e){
            log.error("截图失败: {}",e.toString());
            throw e;
        }

        // 等待进程完成
        int exitCode = process.waitFor();

        // 根据退出码判断命令是否成功
        if (exitCode == 0) {
            log.info("操作成功");
        } else {
            log.error("截图失败");
        }

        return name;
    }

    /**
     * 点击屏幕
     *
     * @param point 坐标
     */
    public void move(Point point) throws IOException {
        getProcess(adb.getPath(), "shell", "input", "tap", String.valueOf(point.getX()), String.valueOf(point.getY()));
    }

    /**
     * 检查工作
     */
    public void examination(){
        // 检查连接功能
        try {
            getConnect();
        } catch (IOException e) {
            log.error("连接失败: {}",e.toString());
            throw new RuntimeException(e);
        }

        log.info("连接功能成功！！！");

        // 检查root权限
        try {
            getRoot();
        } catch (IOException e) {
            log.error("root权限功失败: {}",e.toString());
            throw new RuntimeException(e);
        }

        log.info("root权限功能成功！！！");

        // 检查截图功能
        try {
            screenshot();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 初始化
     */
    public void init() throws IOException {
        File file = new File(Constant.PICTUREPATH);

        if(!file.exists()){
            boolean newFile = file.createNewFile();
        }


        String connect = getConnect();
//        log.info("连接信息: {}", connect);
        if (!connect.equals("connected to 127.0.0.1:16384")) {
            log.error("连接失败: {}", connect);
            return;
        }
        log.info("连接成功！！！");
        // 连接信息
        String devices = getDevices();
        log.info("输出连接信息： {}",devices);

        String root = getRoot();
        log.info("获取root权限: {}", root);

        String closeGame = closeGame();
        log.info("把游戏先关闭 : {}", closeGame);

//        if (!"Success".equals(closeGame)) {
//            log.error("关闭失败 : {}", closeGame);
//            return;
//        }

        String startGame = getStartGame();
//        log.info("启动游戏: {}", startGame);

        if (!startGame.equals("Starting: Intent { cmp=com.dyxt.kdjx/org.cocos2dx.lua.AppActivity }")) {
            log.info("口袋觉醒，启动！\n失败: {}", startGame);
            return;
        }
        log.info("口袋觉醒成功启动了！！！");
    }
}
