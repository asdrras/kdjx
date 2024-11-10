package org.kdjx.abstractActions;

import org.kdjx.entity.GUI;
import org.kdjx.utils.GameUtil;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.concurrent.CompletableFuture;

public class MailAbstractAction extends AbstractAction {

    private GUI gui;

    public MailAbstractAction(GUI gui){
        this.gui = gui;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        gui.writeLog("开始领取邮件");

        try {
            CompletableFuture.runAsync(new Runnable() {
                @Override
                public void run() {
                    GameUtil.getMail();
                }
            });
        }catch (Exception ex){
            gui.writeLog("领取邮件失败");
            throw new RuntimeException(ex);
        }
        gui.writeLog("领取邮件成功");
    }
}