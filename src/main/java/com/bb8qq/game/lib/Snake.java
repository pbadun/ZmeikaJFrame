package com.bb8qq.game.lib;

import com.bb8qq.game.lib.obj.SnakeObject;

import java.util.LinkedList;

/**
 * Змейка.
 */
public class Snake {
    private Direction tmDirection;
    public Direction direction;
    int widht;
    int heigt;

    private boolean isAdd;

    public LinkedList<SnakeObject> snake;

    public Snake(int widht, int heigt, int x, int y) {
        this.widht = widht;
        this.heigt = heigt;
        this.tmDirection = Direction._UP;
        this.direction = Direction._UP;
        this.snake = new LinkedList<>();

        this.isAdd = false;
        snake.add(new SnakeObject(x, y));
        snake.add(new SnakeObject(x, y + 1));
    }

    /**
     * Добавить элемент.
     */
    public void addEl() {
        isAdd = true;
    }

    /**
     * Координаты следующего шага.
     *
     * @return
     */
    public SnakeObject nextStep() {
        direction = tmDirection;
        SnakeObject o = new SnakeObject(snake.get(0));
        switch (direction) {
            case _UP:
                o.y--;
                break;
            case _DOWN:
                o.y++;
                break;
            case _LEFT:
                o.x--;
                break;
            case _RIGHT:
                o.x++;
                break;
        }
        if (o.y <= -1) {
            o.y = widht - 1;
        } else if (o.y >= widht) {
            o.y = 0;
        } else if (o.x <= -1) {
            o.x = heigt - 1;
        } else if (o.x >= heigt) {
            o.x = 0;
        }
        return o;
    }

    /**
     * Шаг змейки
     */
    public void step(float deltaTime, SnakeObject o) {
        snake.addFirst(o);
        if (!isAdd) {
            snake.removeLast();
        } else {
            isAdd = false;
        }
    }

    public void setDirection(Direction di) {
        this.tmDirection = di;
    }
}
