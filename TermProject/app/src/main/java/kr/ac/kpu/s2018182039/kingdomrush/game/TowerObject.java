package kr.ac.kpu.s2018182039.kingdomrush.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import kr.ac.kpu.s2018182039.kingdomrush.R;
import kr.ac.kpu.s2018182039.kingdomrush.framework.GameObject;
import kr.ac.kpu.s2018182039.kingdomrush.framework.StaticGameBitmap;

public class TowerObject implements GameObject {

    private final int[] tower = {
            R.mipmap.archer_tower,
            R.mipmap.magic_tower,
    };

    private final int[] bullet = {
            R.mipmap.arrow,
            R.mipmap.ball,
    };
    private final StaticGameBitmap towerBitmap;
    private final StaticGameBitmap bulletBitmap;
    private final float x;
    private final float y;

    TowerObject(float x, float y, int type) {
        this.x = x;
        this.y = y;
        towerBitmap = new StaticGameBitmap(tower[type], 4);
        bulletBitmap = new StaticGameBitmap(bullet[type], 4);
    }

    @Override
    public void update() {

    }


    @Override
    public void draw(Canvas canvas) {
        towerBitmap.draw(canvas, x, y);
    }
}
