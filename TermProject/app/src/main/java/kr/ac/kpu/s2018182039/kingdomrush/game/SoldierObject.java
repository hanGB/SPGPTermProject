package kr.ac.kpu.s2018182039.kingdomrush.game;

import android.graphics.Canvas;

import kr.ac.kpu.s2018182039.kingdomrush.R;
import kr.ac.kpu.s2018182039.kingdomrush.framework.AnimationGameBitmap;
import kr.ac.kpu.s2018182039.kingdomrush.framework.AnimationGameBitmapVertical;
import kr.ac.kpu.s2018182039.kingdomrush.framework.GameObject;

public class SoldierObject implements GameObject {
    private static final int MOVE = 0;
    private static final int ATTACK = 1;

    private final AnimationGameBitmapVertical moveBitmap;
    private final AnimationGameBitmapVertical attackBitmap;

    private float targetX;
    private float targetY;
    private float speed = 50;

    private float x;
    private float y;
    private float locationX;
    private float locationY ;

    private int action;

    public SoldierObject(float x, float y, float locationX, float locationY) {
        moveBitmap = new AnimationGameBitmapVertical(R.mipmap.soldier_move, 5, 2, 4);
        attackBitmap = new AnimationGameBitmapVertical(R.mipmap.soldier_attack, 3, 2, 4);

        this.locationX = locationX;
        this.locationY = locationY;
        targetX = locationX;
        targetY = locationY;

        this.x = x;
        this.y = y;

        action = MOVE;
    }


    @Override
    public void update() {
        if (action == ATTACK)
            return;

        if (targetX - 10 < x && targetX + 10 > x){
            if (targetY - 10 < y && targetY + 10 > y) {
                //action = ATTACK;
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
