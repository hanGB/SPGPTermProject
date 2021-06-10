package kr.ac.kpu.s2018182039.kingdomrush.game.objects.ui;

import android.graphics.Canvas;
import android.graphics.Rect;

import kr.ac.kpu.s2018182039.kingdomrush.framework.bitmap.StaticGameBitmap;
import kr.ac.kpu.s2018182039.kingdomrush.framework.game.BaseGame;
import kr.ac.kpu.s2018182039.kingdomrush.framework.iface.GameObject;
import kr.ac.kpu.s2018182039.kingdomrush.game.scenes.menu.StageMenuScene;

public class ButtonObject implements GameObject {
    public float x, y;
    private final StaticGameBitmap bitmap;
    private final StaticGameBitmap bitmapPressed;
    private boolean pressed;

    public ButtonObject(int resId, int resIdPressed, float x, float y, Rect rect, Rect rectPressed, int pixel_size) {
        bitmap = new StaticGameBitmap(resId, rect.left, rect.top, rect.right, rect.bottom, pixel_size);
        bitmapPressed = new StaticGameBitmap(resIdPressed,
                rectPressed.left, rectPressed.top, rectPressed.right, rectPressed.bottom, pixel_size);
        this.x = x;
        this.y = y;
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
                processButton();
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

    public void update() {

    }

    public void processButton() {

    }

    public void draw(Canvas canvas) {
        if (!pressed) {
            bitmap.draw(canvas, x, y);
        } else {
            bitmapPressed.draw(canvas, x, y);
        }
    }
}