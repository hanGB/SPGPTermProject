package kr.ac.kpu.s2018182039.kingdomrush.game;

import android.graphics.Canvas;
import android.graphics.RectF;

import java.util.ArrayList;

import kr.ac.kpu.s2018182039.kingdomrush.R;
import kr.ac.kpu.s2018182039.kingdomrush.framework.AnimationGameBitmap;
import kr.ac.kpu.s2018182039.kingdomrush.framework.BoxCollidable;
import kr.ac.kpu.s2018182039.kingdomrush.framework.GameObject;

public class EnemyObject implements GameObject, BoxCollidable {
    private static final int MOVE = 0;
    private static final int ATTACK = 1;
    private static final float ATTACK_TIME = 1.0f;

    private RectF boundingRect = new RectF();

    private float[] movePoints = {
            200, 200,
            600, 200,
            800, 200,
            1000, 200,
            1200, 200,
            1400, 200,
            1600, 200,
            1600, 400,
            1600, 600,
            1600, 800,
            1400, 800,
            1200, 800,
            1000, 800,
            800, 800,
            600, 800,
            400, 800,
            200, 800,
            200, 600,
            200, 400,
    };
    private int pointCount = 19;
    private int nowPoint = 0;

    private final AnimationGameBitmap moveBitmap;
    private final AnimationGameBitmap attackBitmap;

    private float targetX;
    private float targetY;
    private float speed = 200;

    public float x;
    public float y;
    private int action;
    public int hp;

    private SoldierObject targetSolider;
    boolean targetSetEnd = false;
    private float range = 100.0f;
    private float attackRange = 50.0f;
    private int damage;

    private float attackTime = 0.0f;

    public EnemyObject(float x, float y) {
        moveBitmap = new AnimationGameBitmap(R.mipmap.enemy_move, 5, 5);
        attackBitmap = new AnimationGameBitmap(R.mipmap.enemy_attack, 3, 2);

        this.x = x;
        this.y = y;
        targetX = movePoints[0];
        targetY = movePoints[1];

        hp = 20;
        damage = 5;

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
                targetSolider.hp -= damage;
                attackTime -= ATTACK_TIME;
            }

            if (targetSolider.hp <= 0) {
                action = MOVE;
            }
            if (targetSolider.x - attackRange > this.x || this.x > targetSolider.x + attackRange ||
                    targetSolider.y - attackRange > this.y || this.y > targetSolider.y + attackRange) {
                action = MOVE;
            }

            return;
        }

        if (targetX - 10 < x && targetX + 10 > x){
            if (targetY - 10 < y && targetY + 10 > y) {
                nowPoint = (nowPoint + 1) % pointCount;
                targetX = movePoints[nowPoint * 2];
                targetY = movePoints[nowPoint * 2 + 1];
                return;
            }
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
            ArrayList<GameObject> players = state.getAllObjects(MainGameState.Layer.friendly);
            for (GameObject solider : players) {
                SoldierObject soldierObject = (SoldierObject) solider;
                float x = soldierObject.x;
                float y = soldierObject.y;

                if (x - range < this.x && this.x < x + range) {
                    if (y - range < this.y && this.y < y + range) {
                        targetSolider = soldierObject;
                        targetSetEnd = true;
                        targetX = soldierObject.x;
                        targetY =  soldierObject.y;
                    }
                }
            }
        }
        else {
            if (targetSolider.x - attackRange < this.x && this.x < targetSolider.x + attackRange) {
                if (targetSolider.y - attackRange < this.y && this.y < targetSolider.y + attackRange) {
                    action = ATTACK;
                    attackTime = 0.0f;
                }
            }

            // 타겟이 범위 밖으로 이동 시 다시 제자리로
            if (targetSolider.x - range > this.x || this.x > targetSolider.x + range ||
                    targetSolider.y - range > this.y || this.y > targetSolider.y + range) {
                targetSetEnd = false;
                targetX = movePoints[nowPoint * 2];
                targetY = movePoints[nowPoint * 2 + 1];
            }
            if (targetSolider.hp <= 0){
                targetSetEnd = false;
                targetX = movePoints[nowPoint * 2];
                targetY = movePoints[nowPoint * 2 + 1];
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

    public boolean giveDamage(int damage) {
        hp -= damage;
        if (hp <= 0) {
            return true;
        }
        return false;
    }

    @Override
    public RectF getBoundingRect() {
        moveBitmap.getBoundingRect(x, y, boundingRect);
        return boundingRect;
    }
}
