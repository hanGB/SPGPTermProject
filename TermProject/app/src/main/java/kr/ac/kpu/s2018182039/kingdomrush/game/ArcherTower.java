package kr.ac.kpu.s2018182039.kingdomrush.game;

import android.graphics.Canvas;

import kr.ac.kpu.s2018182039.kingdomrush.R;
import kr.ac.kpu.s2018182039.kingdomrush.framework.GameObject;
import kr.ac.kpu.s2018182039.kingdomrush.framework.StaticGameBitmap;

public class ArcherTower implements GameObject {
    private static final int BULLET_SPEED = 1500;
    private static final float FIRE_INTERVAL = 1.0f;

    private final StaticGameBitmap towerBitmap = new StaticGameBitmap(R.mipmap.archer_tower, 4);

    private final float x;
    private final float y;

    private float fireTime;

    ArcherTower(float x, float y) {
        this.x = x;
        this.y = y;
        fireTime = FIRE_INTERVAL;
    }

    @Override
    public void update() {
        MainGameState state = MainGameState.get();

        fireTime += state.frameTime;
        if (fireTime >= FIRE_INTERVAL) {
            fireBullet();
            fireTime -= FIRE_INTERVAL;
        }

    }


    private void fireBullet() {
        float dx = 1.0f;
        float dy = 1.0f;
        Bullet bullet = Bullet.get(R.mipmap.arrow, this.x, this.y, dx, dy, BULLET_SPEED);
        MainGameState state = MainGameState.get();
        state.add(MainGameState.Layer.bullet, bullet);
    }

    @Override
    public void draw(Canvas canvas) {
        towerBitmap.draw(canvas, x, y);
    }
}
