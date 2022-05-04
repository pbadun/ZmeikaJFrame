package com.bb8qq.game.lib.obj;

public class SnakeObject extends GameObject {

    public SnakeObject(SnakeObject s) {
        this.x = s.x;
        this.y = s.y;
    }

    public SnakeObject(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
