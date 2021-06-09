package kr.ac.kpu.s2018182039.kingdomrush.game.objects.ui;

import android.graphics.Canvas;

import kr.ac.kpu.s2018182039.kingdomrush.framework.game.BaseGame;
import kr.ac.kpu.s2018182039.kingdomrush.framework.iface.GameObject;
import kr.ac.kpu.s2018182039.kingdomrush.framework.bitmap.StaticGameBitmap;

public class MovingBackgroundObject implements GameObject {
    private float x, y;
    private float tx, ty;
    private float speed;
    private final StaticGameBitmap bitmap;
    private boolean down;
    private float prevX, prevY;
    private float moveX, moveY;

    public MovingBackgroundObject(int resId, float x, float y, int left, int top, int right, int bottom, int pixel_size) {
        bitmap = new StaticGameBitmap(resId, left, top, right, bottom, pixel_size);
        this.x = x;
        this.y = y;
        this.tx = x;
        this.ty = y;
        this.speed = 800;
        this.down = false;
    }

    public void pressDown(float x, float y) {
        down = true;
        prevX = x;
        prevY = y;
    }

    public void pressUp() {
        down = false;
    }

    public void pressMove(float x, float y) {
        moveX = x;
        moveY = y;

        float tx = x + moveX - prevX;
        float ty = y + moveY - prevY;
    }

    public void update() {
        BaseGame game = BaseGame.get();
        if (down) {
            float dx = (moveX - prevX) * game.frameTime;
            float dy = (moveY - prevY) * game.frameTime;
            x += dx;
            if ((dx > 0 && x < tx) || (dx < 0 && x > tx)) {
                x = tx;
             }

            y += dy;
            if ((dy > 0 && y < ty) || (dy < 0 && y > ty)) {
                y = ty;
             }

        }
    }

    public void draw(Canvas canvas) {
        bitmap.draw(canvas, x, y);
    }
}
