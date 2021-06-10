package kr.ac.kpu.s2018182039.kingdomrush.game.objects.tower;

import java.util.ArrayList;

import kr.ac.kpu.s2018182039.kingdomrush.R;
import kr.ac.kpu.s2018182039.kingdomrush.framework.game.BaseGame;
import kr.ac.kpu.s2018182039.kingdomrush.framework.iface.GameObject;
import kr.ac.kpu.s2018182039.kingdomrush.game.objects.buller.Bullet;
import kr.ac.kpu.s2018182039.kingdomrush.game.objects.enemy.EnemyObject;
import kr.ac.kpu.s2018182039.kingdomrush.game.scenes.main.MainScene;

public class ArcherTower extends TowerObject {
    private static final int BULLET_SPEED = 1500;
    private static final float FIRE_INTERVAL = 1.0f;

    private float fireTime;
    private float range = 500.0f;
    private boolean targetSetEnd = false;
    private EnemyObject targetEnemy;

    ArcherTower(float x, float y) {
        super(x, y, R.mipmap.archer_tower);
        fireTime = FIRE_INTERVAL;
    }

    @Override
    public void update() {
        BaseGame game = BaseGame.get();

        if (!targetSetEnd) {
            ArrayList<GameObject> enemies = game.getAllObjects(MainScene.Layer.enemy.ordinal());
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
            fireTime += game.frameTime;
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

        Bullet bullet = Bullet.get(R.mipmap.arrow, this.x, this.y, dx / dis, dy / dis, BULLET_SPEED, 7);
        BaseGame game = BaseGame.get();
        game.add(MainScene.Layer.bullet.ordinal(), bullet);
    }
}
