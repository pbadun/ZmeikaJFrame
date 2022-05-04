package com.bb8qq.game;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Hello world!
 */
public class AppZmeika {
    private static boolean bQuit = false;
    private JFrame frame;
    private ScreenView main;


    public static void main(String[] args) {
        new AppZmeika();
    }

    public AppZmeika() {
        this.frame = new JFrame();
        this.main = new ScreenView(frame);
        frame.add(main);

        frame.setSize(512, 512);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                bQuit = true;
                System.exit(0);
            }
        });
        frame.setVisible(true);

        while (!bQuit) {
            try {
                Thread.sleep(100l);
            } catch (Exception e) {
            }
        }
    }

}
