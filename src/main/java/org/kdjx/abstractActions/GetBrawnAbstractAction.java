package org.kdjx.abstractActions;

import org.kdjx.entity.GUI;
import org.kdjx.utils.GameUtil;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.concurrent.CompletableFuture;

public class GetBrawnAbstractAction extends AbstractAction {

    private GUI gui;

    public GetBrawnAbstractAction(GUI gui){
        this.gui = gui;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        CompletableFuture.runAsync(new Runnable() {
            @Override
            public void run() {
                GameUtil.getBrawn(gui);
            }
        });
    }
}