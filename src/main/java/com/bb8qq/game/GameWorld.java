package com.bb8qq.game;

import com.bb8qq.game.lib.*;
import com.bb8qq.game.lib.obj.EdaObject;
import com.bb8qq.game.lib.obj.GameObject;
import com.bb8qq.game.lib.obj.SnakeObject;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * Обработка игрового мира.
 */
public class GameWorld {
    public final int GAME_WAIT = 0;
    public final int GAME_START = 1;
    public final int GAME_OWER = 2;

    private Random random = new Random(System.currentTimeMillis());
    public final int HEIGHT;
    public final int WIDTH;

    public List<EdaObject> eda;
    public Snake snake;
    public int gameStatus = GAME_WAIT;
    private float time;
    public int ball;

    public GameWorld(int HEIGHT, int WIDTH) {
        this.HEIGHT = HEIGHT;
        this.WIDTH = WIDTH;
        this.eda = new ArrayList<>();
        this.time = 0;
        this.ball = 0;
        restart();
    }

    public void restart() {
        this.snake = new Snake(WIDTH, HEIGHT, WIDTH / 2, HEIGHT / 2);
        eda.clear();
        for (int i = 0; i < 20; i++) {
            eda.add(new EdaObject(random.nextInt(WIDTH), random.nextInt(HEIGHT)));
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    public void setKey(KeyEvent e, boolean st) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT && snake.direction != Direction._RIGHT) {
            snake.setDirection(Direction._LEFT);
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT && snake.direction != Direction._LEFT) {
            snake.setDirection(Direction._RIGHT);
        }
        if (e.getKeyCode() == KeyEvent.VK_UP && snake.direction != Direction._DOWN) {
            snake.setDirection(Direction._UP);
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN && snake.direction != Direction._UP) {
            snake.setDirection(Direction._DOWN);
        }
        if (e.getKeyCode() == KeyEvent.VK_SPACE && gameStatus == GAME_WAIT) {
            gameStatus = GAME_START;
        }
        if (e.getKeyCode() == KeyEvent.VK_SPACE && gameStatus == GAME_OWER) {
            restart();
            gameStatus = GAME_START;
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    public void update(float deltaTime) {
        if (deltaTime > 0.1f) {
            deltaTime = 0.1f;
        }
        time += deltaTime;
        if (time < 0.3f) {
            return;
        }
        time = 0;
        SnakeObject o = snake.nextStep();
        //----
        inFrontEda(o);
        inFrontSnake(o);
        //----
        if (gameStatus == GAME_START) {
            snake.step(deltaTime, o);
        }
    }
    //------------------------------------------------------------------------------------------------------------------

    /**
     * В переди еда?
     *
     * @param o
     */
    private void inFrontEda(SnakeObject o) {
        Iterator<EdaObject> e = eda.iterator();
        while (e.hasNext()) {
            EdaObject eo = e.next();
            if (eo.belong(o)) {
                snake.addEl();
                eda.remove(eo);
                ball++;
                break;
            }
        }
    }

    /**
     * Тело змейки?
     *
     * @param o
     */
    private void inFrontSnake(SnakeObject o) {
        Iterator<SnakeObject> e = snake.snake.iterator();
        while (e.hasNext()) {
            GameObject eo = e.next();
            if (eo.belong(o)) {
                gameStatus = GAME_OWER;
                break;
            }
        }
    }
}
