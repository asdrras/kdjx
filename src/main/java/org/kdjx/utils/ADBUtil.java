package org.kdjx.utils;


import com.benjaminwan.ocrlibrary.Point;
import org.kdjx.common.Constant;
import org.kdjx.entity.ADB;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.UUID;

/**
 * 通过 adb 操作 模拟器
 */

public class ADBUtil {

    private static final ADB adb = new ADB();

    // ADB.exe 文件路径
    private static final String adbPath = "F:\\MuMu Player 12\\shell\\adb.exe";

    // 要启动的应用的包名和活动名
    private static final String packageName = "com.dyxt.kdjx";
    private static final String activityName = "org.cocos2dx.lua.AppActivity";

    private static final Logger log = LoggerFactory.getLogger(ADBUtil.class);

    /**
     * 连接 adb
     *
     * @return
     * @throws IOException
     */
    public static String getConnect() throws IOException {
        killADB();
        Process process = adb.getProcess(adbPath, "connect", "127.0.0.1:16384");
        return getInputStreamByString(process);
    }

    /**
     * 查看连接的设备
     */
    public static String getDevices() throws IOException {
        Process process = adb.getProcess(adbPath, "devices");
        return getInputStreamByString(process);
    }

    /**
     * 杀死adb服务
     */
    public static void killADB() throws IOException {
        adb.getProcess(adbPath, "kill-server");
    }

    /**
     * 获取 root权限
     */
    public static String getRoot() throws IOException {
        Process process = adb.getProcess(adbPath, "root");
        return getInputStreamByString(process);
    }

    /**
     * 启动app
     * @param newPackageName 包名
     * @param newActivityName 主函数
     */
    public static String getStartGame(String newPackageName, String newActivityName) throws IOException {
        Process process = adb.getProcess(adbPath, "shell", "am", "start", "-n", newPackageName + "/" + newActivityName);
        return getInputStreamByString(process);
    }

    public static String getStartGame() throws IOException {
        return getStartGame(packageName, activityName);
    }

    /**
     * 关闭游戏
     */
    public static String closeGame(String newPackageName) throws IOException {
        Process process = adb.getProcess(adbPath, "shell", "am", "force-stop", packageName);
        return getInputStreamByString(process);
    }

    public static String closeGame() throws IOException {
        return closeGame(packageName);
    }

    /**
     * 截图
     * @throws IOException
     */
    public static String screenshot() throws IOException, InterruptedException {
        Process process = adb.getProcess(adbPath, "exec-out", "screencap", "-p");

        // 获取命令的标准输出流
        InputStream inputStream = process.getInputStream();

        UUID uuid = UUID.randomUUID();
        String name = uuid.toString() + ".png";

        // 将输出流写入到内存中
        try (FileOutputStream fileOutputStream = new FileOutputStream(Constant.PICTUREPATH + "\\" + name)) {
            byte[] buffer = new byte[1024 * 1024 * 5];
            int length;

            while ((length = inputStream.read(buffer)) != -1) {
                fileOutputStream.write(buffer, 0, length);
            }

        } catch (IOException e) {
            log.error("截图失败: {}", e.toString());
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
    public static void move(Point point) throws IOException {
        adb.getProcess(adbPath, "shell", "input", "tap", String.valueOf(point.getX()), String.valueOf(point.getY()));
    }

    /**
     * 检查 adb 检查功能 是否能运行成功 工作
     */
    public static void examination() {
        // 检查连接功能
        try {
            getConnect();
        } catch (IOException e) {
            log.error("连接失败: {}", e.toString());
            throw new RuntimeException(e);
        }

        log.info("连接功能成功！！！");

        // 检查root权限
        try {
            getRoot();
        } catch (IOException e) {
            log.error("root权限功失败: {}", e.toString());
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
    public static void init() throws IOException {
        File file = new File(Constant.PICTUREPATH);

        if (!file.exists()) {
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
        log.info("输出连接信息： {}", devices);

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

    /**
     * 获取 adb指令的 响应结果
     * @param process
     * @return
     */
    private static String getInputStreamByString(Process process) throws IOException {
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

        int st = process.exitValue();
        if (st != 0) {
            System.gc();
        }
        return sb.toString();
    }

}
