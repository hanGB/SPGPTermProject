package kr.ac.kpu.s2018182039.kingdomrush.game;

import android.graphics.Canvas;

import java.util.ArrayList;

import kr.ac.kpu.s2018182039.kingdomrush.R;
import kr.ac.kpu.s2018182039.kingdomrush.framework.GameObject;
import kr.ac.kpu.s2018182039.kingdomrush.framework.StaticGameBitmap;

public class ArcherTower extends TowerObject {
    private static final int BULLET_SPEED = 1500;
    private static final float FIRE_INTERVAL = 1.0f;

    private float fireTime;
    private float range = 200.0f;
    private boolean targetSetEnd = false;
    private EnemyObject targetEnemy;

    ArcherTower(float x, float y) {
        super(x, y, R.mipmap.archer_tower);
        fireTime = FIRE_INTERVAL;
    }

    @Override
    public void update() {
        MainGameState state = MainGameState.get();

        if (!targetSetEnd) {
            ArrayList<GameObject> enemies = state.getAllObjects(MainGameState.Layer.enemy);
            for (GameObject enemy : enemies) {
                EnemyObject enemyObject = (EnemyObject) enemy;
                float x = enemyObject.x;
                float y = enemyObject.y;

                if (this.x - range < x && x < this.x + range) {
                    if (this.y - range < y && y < this.y + range) {
                        targetEnemy = enemyObject;
                        targetSetEnd = true;
                    }
                }
            }
        }
        else {
            fireTime += state.frameTime;
            if (fireTime >= FIRE_INTERVAL) {
                fireBullet();
                fireTime -= FIRE_INTERVAL;
            }

            // 타겟이 범위 밖으로 이동 시 다른 타겟 설정
            if (this.x - range > targetEnemy.x || targetEnemy.x > this.x + range ||
            this.y - range > targetEnemy.y || targetEnemy.y > this.y + range) {
                targetSetEnd = false;
            }
            if (targetEnemy.hp <= 0){
                targetSetEnd = false;
            }
        }
    }


    private void fireBullet() {
        float dx = targetEnemy.x - this.x;
        float dy = targetEnemy.y - this.y;

        float dis = (float)Math.sqrt(dx * dx + dy * dy);

        Bullet bullet = Bullet.get(R.mipmap.arrow, this.x, this.y, dx / dis, dy / dis, BULLET_SPEED, 8);
        MainGameState state = MainGameState.get();
        state.add(MainGameState.Layer.bullet, bullet);
    }
}
