package kr.ac.kpu.s2018182039.kingdomrush.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import kr.ac.kpu.s2018182039.kingdomrush.R;
import kr.ac.kpu.s2018182039.kingdomrush.framework.GameObject;
import kr.ac.kpu.s2018182039.kingdomrush.framework.StaticGameBitmap;

public class TowerObject implements GameObject {
    private final StaticGameBitmap towerBitmap;

    protected final float x;
    protected final float y;

    TowerObject(float x, float y, int resId) {
        this.x = x;
        this.y = y;
        towerBitmap = new StaticGameBitmap(resId, 4);
    }

    @Override
    public void update() {

    }


    @Override
    public void draw(Canvas canvas) {
        towerBitmap.draw(canvas, x, y);
    }
}
