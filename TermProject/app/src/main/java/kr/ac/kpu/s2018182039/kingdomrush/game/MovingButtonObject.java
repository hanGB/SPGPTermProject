package kr.ac.kpu.s2018182039.kingdomrush.game;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

import kr.ac.kpu.s2018182039.kingdomrush.framework.GameObject;
import kr.ac.kpu.s2018182039.kingdomrush.framework.StaticGameBitmap;
import kr.ac.kpu.s2018182039.kingdomrush.ui.view.GameView;

public class MovingButtonObject implements GameObject {
    private float x, y;
    private float dx, dy;
    private final StaticGameBitmap bitmap;
    private final StaticGameBitmap bitmapPressed;
    public boolean pressed;
    private float distanceX;
    private float distanceY;
    private float speed = 3.f;

    public MovingButtonObject(int resId, int resIdPressed, float x, float y, float dx, float dy, Rect rect, Rect rectPressed) {
        bitmap = new StaticGameBitmap(resId, rect.left, rect.top, rect.right, rect.bottom);
        bitmapPressed = new StaticGameBitmap(resIdPressed, rectPressed.left, rectPressed.top, rectPressed.right, rectPressed.bottom);
        this.x = x;
        this.y = y;
        this.dx = dx;
        this.dy = dy;
        this.distanceX = dx - x;
        this.distanceY = dy - y;
        pressed = false;
    }

    public void pressOn(float x, float y) {

    }

    public void update() {
        MainMenuState state = MainMenuState.get();
        x += (distanceX * speed * state.frameTime);
        y += (distanceY * speed * state.frameTime);
        if ((distanceX > 0 && x > dx) || (distanceX < 0 && x < dx)) {
            x = dx;
        }
        if ((distanceY > 0 && y > dy) || (distanceY < 0 && y < dy)) {
            y = dy;
        }
    }

    public void draw(Canvas canvas) {
        if (!pressed) {
            bitmap.draw(canvas, x, y);
        } else {
            bitmapPressed.draw(canvas, x, y);
        }

    }
}
