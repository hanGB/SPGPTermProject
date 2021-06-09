package kr.ac.kpu.s2018182039.kingdomrush.game.objects.ui;

import android.graphics.Canvas;

import kr.ac.kpu.s2018182039.kingdomrush.framework.iface.GameObject;
import kr.ac.kpu.s2018182039.kingdomrush.framework.bitmap.StaticGameBitmap;

public class StaticDrawObject implements GameObject {

    private float x, y;
    private final StaticGameBitmap bitmap;

    public StaticDrawObject(int resId, float x, float y, int left, int top, int right, int bottom, int pixel_size) {
        bitmap = new StaticGameBitmap(resId, left, top, right, bottom, pixel_size);
        this.x = x;
        this.y = y;
    }

    public void update() {

    }

    public void draw(Canvas canvas) {
        bitmap.draw(canvas, x, y);
    }
}
