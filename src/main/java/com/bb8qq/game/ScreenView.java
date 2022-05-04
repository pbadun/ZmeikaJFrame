package com.bb8qq.game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class ScreenView extends JPanel implements ActionListener {

    private BufferedImage bufferImage;
    private Frame frame;

    private RenderScreen renderScreen;
    private final GameWorld gameWorld;
    public final int HEIGHT = 16;
    public final int WIDTH = 16;

    private Timer timer = new Timer(30, this);
    long startTime = System.nanoTime();

    public ScreenView(Frame frame) {
        this.frame = frame;
        this.bufferImage = new BufferedImage(WIDTH * 8, HEIGHT * 8, BufferedImage.TYPE_INT_ARGB);
        this.gameWorld = new GameWorld(WIDTH, HEIGHT);
        this.renderScreen = new RenderScreen(gameWorld);
        frame.addKeyListener(new KeyInputHandler());
        this.timer.start();
    }


    @Override
    public void paint(Graphics g) {
        try {
            renderScreen.render(bufferImage);
            g.drawImage(bufferImage, 0, 0, frame.getWidth(), frame.getHeight(), null);
            //---------------------------------------
            printTexts(g);
            //---------------------------------------
            g.dispose();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void printTexts(Graphics g) {
        if (gameWorld.gameStatus == gameWorld.GAME_WAIT) {
            g.setFont(new Font("default", Font.BOLD, 16));
            g.setColor(Color.WHITE);
            String s = "Press SPACE to start!";
            g.drawString(s, frame.getWidth() / 2 - g.getFontMetrics().stringWidth(s) / 2, frame.getHeight() / 2);
        } else if (gameWorld.gameStatus == gameWorld.GAME_START) {
            g.setFont(new Font("default", Font.BOLD, 16));
            g.setColor(Color.WHITE);
            String s = "Ball: " + gameWorld.ball;
            g.drawString(s, frame.getWidth() / 2 - g.getFontMetrics().stringWidth(s) / 2, 10);

        } else if (gameWorld.gameStatus == gameWorld.GAME_OWER) {
            g.setColor(Color.WHITE);
            g.setFont(new Font("default", Font.BOLD, 16));
            String s = "Press SPACE to restart!";
            g.drawString(s, frame.getWidth() / 2 - g.getFontMetrics().stringWidth(s) / 2, frame.getHeight() / 2 + 50);
            g.setFont(new Font("default", Font.BOLD, 48));
            s = "GAME OWER";
            g.drawString(s, frame.getWidth() / 2 - g.getFontMetrics().stringWidth(s) / 2, frame.getHeight() / 2);
        }
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        float deltaTime = (System.nanoTime() - startTime) / 1000000000.0f;
        startTime = System.nanoTime();
        gameWorld.update(deltaTime);
        repaint();
    }

    //------------------------------------------------------------------------------------------------------------------

    class KeyInputHandler extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            gameWorld.setKey(e, true);
        }

        @Override
        public void keyReleased(KeyEvent e) {
            gameWorld.setKey(e, false);
        }
    }
}
