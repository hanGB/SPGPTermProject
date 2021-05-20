package kr.ac.kpu.s2018182039.kingdomrush.game;

import android.graphics.Canvas;

import java.util.ArrayList;

import kr.ac.kpu.s2018182039.kingdomrush.R;
import kr.ac.kpu.s2018182039.kingdomrush.framework.AnimationGameBitmap;
import kr.ac.kpu.s2018182039.kingdomrush.framework.AnimationGameBitmapVertical;
import kr.ac.kpu.s2018182039.kingdomrush.framework.GameObject;

public class SoldierObject implements GameObject {
    private static final int MOVE = 0;
    private static final int ATTACK = 1;
    private static final float ATTACK_TIME = 1.0f;

    private final AnimationGameBitmapVertical moveBitmap;
    private final AnimationGameBitmapVertical attackBitmap;

    private float targetX;
    private float targetY;
    private float speed = 150;

    public float x;
    public float y;
    private float locationX;
    private float locationY ;
    public int towerId;

    private int action;
    public int hp;
    private int damage;

    private boolean targetSetEnd = false;
    private float range = 200.0f;
    private float attackRange = 50.0f;
    private EnemyObject targetEnemy;

    private float attackTime = 0.0f;


    public SoldierObject(float x, float y, float locationX, float locationY, int towerId) {
        moveBitmap = new AnimationGameBitmapVertical(R.mipmap.soldier_move, 5, 2, 4);
        attackBitmap = new AnimationGameBitmapVertical(R.mipmap.soldier_attack, 2, 2, 4);

        this.locationX = locationX;
        this.locationY = locationY;
        targetX = locationX;
        targetY = locationY;

        this.x = x;
        this.y = y;
        hp = 25;
        damage = 5;

        this.towerId = towerId;

        action = MOVE;
    }


    @Override
    public void update() {
        if (hp <= 0){
            MainGameState state = MainGameState.get();
            state.remove(this, true);
        }

        if (action == ATTACK) {
            MainGameState state = MainGameState.get();
            attackTime += state.frameTime;

            if (attackTime >= ATTACK_TIME) {
                targetEnemy.giveDamage(damage);
                attackTime -= ATTACK_TIME;
            }


            if (targetEnemy.hp <= 0) {
                action = MOVE;
            }
            if (targetEnemy.x - attackRange > this.x || this.x > targetEnemy.x + attackRange ||
                    targetEnemy.y - attackRange > this.y || this.y > targetEnemy.y + attackRange) {
                action = MOVE;
            }

            return;

        }

        float delta_x = targetX - x;
        float delta_y = targetY - y;
        float angle = (float) Math.atan2(delta_y, delta_x);
        MainGameState state = MainGameState.get();
        float move_dist = speed * state.frameTime;
        float dx = (float) (move_dist * Math.cos(angle));
        float dy = (float) (move_dist * Math.sin(angle));

        x += dx;
        if ((dx > 0 && x > targetX) || (dx < 0 && x < targetX)) {
            x = targetX;
        }
        y += dy;
        if ((dy > 0 && y > targetY) || (dy < 0 && y < targetY)) {
            y = targetY;
        }


        if (!targetSetEnd) {
            ArrayList<GameObject> enemies = state.getAllObjects(MainGameState.Layer.enemy);
            for (GameObject enemy : enemies) {
                EnemyObject enemyObject = (EnemyObject) enemy;
                float x = enemyObject.x;
                float y = enemyObject.y;

                if (x - range < this.x && this.x < x + range) {
                    if (y - range < this.y && this.y < y + range) {
                        targetEnemy = enemyObject;
                        targetSetEnd = true;
                        targetX = enemyObject.x;
                        targetY =  enemyObject.y;
                    }
                }
            }
        }
        else {
            if (targetEnemy.x - attackRange < this.x && this.x < targetEnemy.x + attackRange) {
                if (targetEnemy.y - attackRange < this.y && this.y < targetEnemy.y + attackRange) {
                    action = ATTACK;
                    attackTime = 0.0f;
                }
            }

            // 타겟이 범위 밖으로 이동 시 다시 제자리로
            if (targetEnemy.x - range > this.x || this.x > targetEnemy.x + range ||
                    targetEnemy.y - range > this.y || this.y > targetEnemy.y + range) {
                targetSetEnd = false;
                targetX = locationX;
                targetY = locationY;
            }
            if (targetEnemy.hp <= 0){
                targetSetEnd = false;
                targetX = locationX;
                targetY = locationY;
            }
        }
    }

    @Override
    public void draw(Canvas canvas) {
        if (action == MOVE) {
            moveBitmap.draw(canvas, x, y);
        }
        else if (action == ATTACK) {
            attackBitmap.draw(canvas, x, y);
        }
    }
}
