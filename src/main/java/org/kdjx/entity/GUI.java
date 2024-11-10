package org.kdjx.entity;

import org.kdjx.abstractActions.GetBrawnAbstractAction;
import org.kdjx.abstractActions.MailAbstractAction;
import org.kdjx.abstractActions.OneClickGiftAndReceiveOfBrawnAbstractAction;
import org.kdjx.abstractActions.StartScriptAbstractAction;
import org.kdjx.utils.ADBUtil;
import org.kdjx.utils.GameUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CompletableFuture;

public class GUI {

    private final JFrame jFrame;
//    private final JPanel jPanelMain = new JPanel();

    private final JPanel information = new JPanel();
    private final JTextField username = new JTextField(10);
    private final JTextField password = new JTextField(10);
    private final JButton startScript = new JButton("启动脚本");
    private final JButton checkEnvironment = new JButton("检查环境");
    private final JLabel loginStatus = new JLabel("否");
    private final JLabel openGameStatus = new JLabel("否");

    private boolean st = false;

    private final JPanel ability = new JPanel();
    private final JButton getGameIndexInfo = new JButton("获取游戏主界面的信息");
    private final JButton physicalStrength = new JButton("领取体力");
    private final JButton friend = new JButton("好友");
    private final JButton mail = new JButton("邮件");
    private final JButton goldCoin = new JButton("金币");

    private final JTextArea log = new JTextArea(); // 日志
    private final JScrollPane logScr = new JScrollPane(log); // 日志

    // 获取当前时间
    private final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public GUI() {
        this.jFrame = new JFrame("口袋觉醒脚本");

        // 设置一些组件的属性
        settingComponentProperties();

        // 初始化操作
        informationInit();
        abilityInit();
        logInit();

        // 设置一些事件
        addMonitorEvents();

        // 组装视图
        assembleView();

        writeLog("开启操作日志...................");
    }

    /**
     * 设置组件的属性
     */
    private void settingComponentProperties() {
        this.log.setEnabled(false);
        // 设置主面板的布局管理器为 BoxLayout，方向为垂直
        ability.setLayout(new BoxLayout(ability, BoxLayout.Y_AXIS));
    }

    /**
     * 把组件放到窗口中
     */
    private void assembleView() {
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.Y_AXIS));
        jPanel.add(this.information);
        jPanel.add(this.ability);

        this.jFrame.add(jPanel, BorderLayout.NORTH);
        this.jFrame.add(this.logScr);
    }

    /**
     * 组装 脚本功能
     */
    private void abilityInit() {

        JPanel jPanel = new JPanel();

        jPanel.add(physicalStrength);
        jPanel.add(friend);
        jPanel.add(mail);
        jPanel.add(goldCoin);
        jPanel.add(getGameIndexInfo);

        this.ability.add(new JLabel("脚本功能"));
        this.ability.add(jPanel);

//        this.jPanelMain.add(this.ability);
    }

    /**
     * 组装日志
     */
    private void logInit() {

    }

    /**
     * 组装 账号 密码 登录 信息初始化
     */
    private void informationInit() {
//
//        this.information.add(new JLabel("用户名:"));
//        this.information.add(username);
//
//        this.information.add(new JLabel("密码:"));
//        this.information.add(password);

        this.information.add(new JLabel("是否打开了游戏:"));
        this.information.add(openGameStatus);

        this.information.add(new JLabel("是否登录:"));
        this.information.add(loginStatus);

        this.information.add(startScript);
        this.information.add(checkEnvironment);
    }

    /**
     * 添加监听事件
     */
    private void addMonitorEvents() {
        GUI that = this;

        // 检查环境
        this.checkEnvironment.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                writeLog("开始检查环境........");

                CompletableFuture.runAsync(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            ADBUtil.getConnect();
                            writeLog("检查环境成功........");
                        } catch (Exception ex) {
                            showErrorMessageDialog("检查环境失败");
                            throw new RuntimeException(ex);
                        }
                    }
                });

            }
        });

        // 启动脚本
        this.startScript.addActionListener(new StartScriptAbstractAction(that));

        // 好友体力 一键领取 和 一键赠送
        this.friend.addActionListener(new OneClickGiftAndReceiveOfBrawnAbstractAction(that));

        // 领取福利里的体力
        this.physicalStrength.addActionListener(new GetBrawnAbstractAction(that));

        // 领取邮件
        this.mail.addActionListener(new MailAbstractAction(that));

        // 获取游戏主界面内容
        this.getGameIndexInfo.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {

                CompletableFuture.runAsync(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            GameUtil.getIndexGameInfo();
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        } catch (InterruptedException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                });
            }
        });
    }

    /**
     * 写入日志
     *
     * @param str
     */
    public void writeLog(String str) {
        String format = formatter.format(new Date()) + "    ";

        String text = this.log.getText();

        if (text.isEmpty()) {
            this.log.append(format + str);
        } else {
            this.log.append("\n");
            this.log.append(format + str);
        }
    }

    /**
     * 弹出 错误 提示框
     */
    public void showErrorMessageDialog(String msg) {
        JOptionPane.showMessageDialog(jFrame, msg, "错误提示", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * 打开窗口
     */
    public void openWindow() {
        // 让窗口的 x 可以关闭
        this.jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 设置图标
        ImageIcon imageIcon = new ImageIcon("src/main/resources/miaomiao.jpeg");
        this.jFrame.setIconImage(imageIcon.getImage());

        // 设置窗口打开的位置
        this.jFrame.setLocation(200, 200);
        // 设置大小
        this.jFrame.setSize(983, 571);
//        this.jFrame.pack();
        // 显示窗口
        this.jFrame.setVisible(true);
    }

}
