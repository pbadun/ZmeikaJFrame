package com.bb8qq.game.lib.obj;

/**
 * Игровой объект. Общее описание.
 */
public abstract class GameObject {

    public int x;
    public int y;

    /**
     * Схожи координаты?
     *
     * @param go
     * @return
     */
    public boolean belong(GameObject go) {
        return go.x == x && go.y == y;
    }

}
