package kr.ac.kpu.s2018182039.kingdomrush.game;

import android.graphics.Canvas;

import kr.ac.kpu.s2018182039.kingdomrush.R;
import kr.ac.kpu.s2018182039.kingdomrush.framework.AnimationGameBitmap;
import kr.ac.kpu.s2018182039.kingdomrush.framework.GameObject;

public class EnemyObject implements GameObject {
    private static final int MOVE_STATE = 0;
    private static final int ATTACK_STATE = 1;

    private AnimationGameBitmap moveBitmap;
    private AnimationGameBitmap attackBitmap;

    private float x;
    private float y;
    private int state;

    public EnemyObject(float x, float y) {
        moveBitmap = new AnimationGameBitmap(R.mipmap.move, 5, 5);
        attackBitmap = new AnimationGameBitmap(R.mipmap.attack, 4, 2);

        this.x = x;
        this.y = y;

        state = MOVE_STATE;
    }


    @Override
    public void update() {

    }

    @Override
    public void draw(Canvas canvas) {
        if (state == MOVE_STATE) {
            moveBitmap.draw(canvas, x, y);
        }
        else if (state == ATTACK_STATE) {
            attackBitmap.draw(canvas, x, y);
        }
    }
}
