package com.bb8qq.game;

import com.bb8qq.game.lib.obj.GameObject;
import com.bb8qq.game.lib.TexturesName;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;

/**
 * Отрисовка мира в буффер.
 */
public class RenderScreen {
    private HashMap<TexturesName, BufferedImage> textures;
    private BufferedImage textureImage;
    private GameWorld gameWorld;

    public RenderScreen(GameWorld gameWorld) {
        this.gameWorld = gameWorld;
        this.textures = new HashMap<>();
        readTexture();
    }

    private void readTexture() {
        try {
            textureImage = ImageIO.read(this.getClass().getResource("/snake.png"));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("No read texure");
        }
        textures.put(TexturesName.Z_UP, textureImage.getSubimage(8, 24, 8, 8));
        textures.put(TexturesName.Z_LEFT, textureImage.getSubimage(16, 24, 8, 8));
        textures.put(TexturesName.Z_DOWN, textureImage.getSubimage(24, 24, 8, 8));
        textures.put(TexturesName.Z_RIGHT, textureImage.getSubimage(32, 24, 8, 8));
        textures.put(TexturesName.Z_TELO, textureImage.getSubimage(40, 24, 8, 8));
        textures.put(TexturesName.E_A, textureImage.getSubimage(48, 24, 8, 8));
        textures.put(TexturesName.E_B, textureImage.getSubimage(56, 24, 8, 8));
    }

    public void render(BufferedImage bufferedImage) {
        Graphics2D g = bufferedImage.createGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight()); //заполнить прямоугольник
        for (GameObject go : gameWorld.eda) {
            drawPixel(TexturesName.E_A, g, go.x, go.y);
        }
        for (int i = 0; i < gameWorld.snake.snake.size(); i++) {
            GameObject go = gameWorld.snake.snake.get(i);
            if (i != 0) {
                drawPixel(TexturesName.Z_TELO, g, go.x, go.y);
                continue;
            }
            switch (gameWorld.snake.direction) {
                case _UP:
                    drawPixel(TexturesName.Z_UP, g, go.x, go.y);
                    break;
                case _DOWN:
                    drawPixel(TexturesName.Z_DOWN, g, go.x, go.y);
                    break;
                case _LEFT:
                    drawPixel(TexturesName.Z_LEFT, g, go.x, go.y);
                    break;
                case _RIGHT:
                    drawPixel(TexturesName.Z_RIGHT, g, go.x, go.y);
                    break;
            }
        }
    }

    private void drawPixel(TexturesName textur, Graphics2D g, int x, int y) {
        g.drawImage(textures.get(textur), x * 8, y * 8, x * 8 + 8, y * 8 + 8, 0, 0, 8, 8, null);
    }
}
