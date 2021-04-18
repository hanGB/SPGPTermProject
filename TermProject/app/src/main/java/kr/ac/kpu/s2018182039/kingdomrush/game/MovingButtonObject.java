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
    private boolean pressed;
    private boolean used;
    private float distanceX;
    private float distanceY;
    private float speed = 2.f;

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

    public void pressOn(float x, float y, boolean down) {
        if (down) {
            pressed = false;
            if(IsIn(x, y)) {
                pressed = true;
            }
        }
        else {
            pressed = false;
            if(IsIn(x, y)) {
                used = true;
            }
        }
    }

    private boolean IsIn(float x, float y) {
        int hw = bitmap.getWidth() / 2;
        int hh = bitmap.getHeight() / 2;
        if ( (this.x - hw) < x && x < (this.x + hw)) {
            if ( (this.y - hh) < y && y < (this.y + hh)) {
                return true;
            }
        }
        return false;
    }

    public boolean IsUsed() {
        return used;
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
