package org.kdjx;


import com.benjaminwan.ocrlibrary.Point;
import org.kdjx.entity.GUI;
import org.kdjx.utils.ADBUtil;
import org.kdjx.utils.GameUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;

public class Main {

    public static final GUI gui = new GUI();

    public static void main(String[] args) throws Exception {

        // 启动窗口
        SwingUtilities.invokeLater(gui::openWindow);


//        FileUtil.closeAllFile();
    }
}