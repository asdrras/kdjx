package org.kdjx.abstractActions;

import org.kdjx.entity.GUI;
import org.kdjx.utils.GameUtil;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.concurrent.CompletableFuture;

/**
 * 好友体力 一键领取 和 一键赠送 监听器
 */

public class OneClickGiftAndReceiveOfBrawnAbstractAction extends AbstractAction {

    private GUI gui;

    public OneClickGiftAndReceiveOfBrawnAbstractAction(GUI gui){
        this.gui = gui;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        CompletableFuture.runAsync(new Runnable() {
            @Override
            public void run() {
                try {
                    GameUtil.oneClickGiftAndReceiveOfBrawn(gui);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }
}
