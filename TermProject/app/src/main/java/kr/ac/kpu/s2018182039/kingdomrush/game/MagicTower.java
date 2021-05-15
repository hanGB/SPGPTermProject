package kr.ac.kpu.s2018182039.kingdomrush.game;

import android.graphics.Canvas;

import kr.ac.kpu.s2018182039.kingdomrush.R;
import kr.ac.kpu.s2018182039.kingdomrush.framework.GameObject;
import kr.ac.kpu.s2018182039.kingdomrush.framework.StaticGameBitmap;

public class MagicTower extends TowerObject {
    private static final int BULLET_SPEED = 1500;
    private static final float FIRE_INTERVAL = 1.0f;

    private float fireTime;

    MagicTower(float x, float y) {
        super(x, y, R.mipmap.magic_tower);
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
        Bullet bullet = Bullet.get(R.mipmap.ball, this.x, this.y, dx, dy, BULLET_SPEED);
        MainGameState state = MainGameState.get();
        state.add(MainGameState.Layer.bullet, bullet);
    }
}
