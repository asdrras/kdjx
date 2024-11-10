package org.kdjx.abstractActions;

import org.kdjx.entity.GUI;
import org.kdjx.utils.ADBUtil;
import org.kdjx.utils.GameUtil;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public class StartScriptAbstractAction extends AbstractAction {

    private GUI gui;

    public StartScriptAbstractAction(GUI gui){
        this.gui = gui;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        CompletableFuture.runAsync(new Runnable() {
            @Override
            public void run() {
                gui.writeLog("打开游戏中......");
                // 脚本初始化
                try {
                    ADBUtil.init();
                } catch (IOException ex) {
                    gui.showErrorMessageDialog("脚本初始化失败");
                    throw new RuntimeException(ex);
                }

                gui.writeLog("打开成功......");

                gui.writeLog("等待进入游戏界面");
                // 先睡10秒 让游戏加载
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }

                // 第一个出现 进入游戏 文字
                try {
                    boolean st = GameUtil.clickEntryGame();
                } catch (IOException | InterruptedException ex) {
                    gui.showErrorMessageDialog("未 出现进入游戏文字");
                    throw new RuntimeException(ex);
                }

                gui.writeLog("点击了进入游戏.....");

                try {
                    Thread.sleep(3300);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }

                gui.writeLog("检查是否已经登录.......");
                try {
                    GameUtil.clickLogin();
                } catch (IOException | InterruptedException ex) {
                    gui.showErrorMessageDialog("检查登录失败或进入游戏主界面失败");
                    throw new RuntimeException(ex);
                }

                gui.writeLog("成功进入游戏主页..........");

                try {
                    Thread.sleep(9000);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }

                gui.writeLog("正在获取游戏主界面内容");
                try {
                    GameUtil.getIndexGameInfo();
                } catch (IOException | InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
                gui.writeLog("获取成功");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }
}
