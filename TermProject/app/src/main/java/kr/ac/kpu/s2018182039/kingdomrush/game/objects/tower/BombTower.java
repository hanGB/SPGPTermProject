package kr.ac.kpu.s2018182039.kingdomrush.game.objects.tower;

import kr.ac.kpu.s2018182039.kingdomrush.R;
import kr.ac.kpu.s2018182039.kingdomrush.game.objects.buller.BombBullet;
import kr.ac.kpu.s2018182039.kingdomrush.game.scenes.MainGameState;

public class BombTower extends TowerObject {
    private static final int BULLET_SPEED = 500;
    private static final float FIRE_INTERVAL = 2.0f;

    private float fireTime;

    BombTower(float x, float y) {
        super(x, y, R.mipmap.bomb_tower);
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
        float dy = -1.0f;
        BombBullet bullet = BombBullet.get(R.mipmap.bomb, this.x, this.y - 100, dx, dy, BULLET_SPEED);
        MainGameState state = MainGameState.get();
        state.add(MainGameState.Layer.bomb, bullet);
    }
}
