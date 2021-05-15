package kr.ac.kpu.s2018182039.kingdomrush.game;

import android.graphics.Canvas;

import kr.ac.kpu.s2018182039.kingdomrush.R;
import kr.ac.kpu.s2018182039.kingdomrush.framework.AnimationGameBitmap;
import kr.ac.kpu.s2018182039.kingdomrush.framework.GameObject;

public class EnemyObject implements GameObject {
    private static final int MOVE = 0;
    private static final int ATTACK = 1;

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

    public EnemyObject(float x, float y) {
        moveBitmap = new AnimationGameBitmap(R.mipmap.enemy_move, 5, 5);
        attackBitmap = new AnimationGameBitmap(R.mipmap.enemy_attack, 3, 2);

        this.x = x;
        this.y = y;
        targetX = movePoints[0];
        targetY = movePoints[1];

        action = MOVE;
    }


    @Override
    public void update() {
        if (action == ATTACK)
            return;

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
