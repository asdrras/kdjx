package org.kdjx.entity;

import javax.swing.*;
import java.awt.*;

public class GUI {

    private JFrame frame;

    public GUI(){
        this.frame = new JFrame("你好 gui");

        // 设置默认关闭操作
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 设置大小
        frame.setSize(300, 200);
        frame.setLayout(new FlowLayout());
        // 加载图标
        ImageIcon imageIcon = new ImageIcon("src/main/resources/miaomiao.jpeg");
        Image image = imageIcon.getImage();
        Image scaledImage = image.getScaledInstance(200, 200, Image.SCALE_SMOOTH); // 调整大小为 64x64 像素
        imageIcon = new ImageIcon(scaledImage);

        frame.setIconImage(imageIcon.getImage());
        // 创建自定义按钮
        JButton button = new JButton("Hello World");


        // 设置按钮样式
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(0x87ceeb)); // 天蓝色
        button.setBorder(BorderFactory.createRaisedBevelBorder());
        button.setBorder(null); // 去掉边框

        // 将按钮添加到 JFrame
        frame.add(button);
    }

    public void openWin(){
        // 显示窗口
        this.frame.setVisible(true);
    }

}
