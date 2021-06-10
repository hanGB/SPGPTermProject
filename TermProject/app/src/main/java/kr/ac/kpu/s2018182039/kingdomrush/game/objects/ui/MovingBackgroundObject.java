package kr.ac.kpu.s2018182039.kingdomrush.game.objects.ui;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;

import kr.ac.kpu.s2018182039.kingdomrush.framework.game.BaseGame;
import kr.ac.kpu.s2018182039.kingdomrush.framework.iface.GameObject;
import kr.ac.kpu.s2018182039.kingdomrush.framework.bitmap.StaticGameBitmap;
import kr.ac.kpu.s2018182039.kingdomrush.framework.view.GameView;

public class MovingBackgroundObject implements GameObject {
    private static final String TAG = MovingBackgroundObject.class.getSimpleName();
    private float x, y;
    private float tx, ty;
    private float speed;
    private final StaticGameBitmap bitmap;
    private boolean down;
    private float prevX, prevY;
    private float moveX, moveY;

    public MovingBackgroundObject(int resId, float x, float y, int left, int top, int right, int bottom, int pixel_size) {
        bitmap = new StaticGameBitmap(resId, left, top, right, bottom, pixel_size);
        this.x = x;
        this.y = y;
        this.tx = 0;
        this.ty = 0;
        this.speed = 800;
        this.down = false;
    }

    public void pressUp() {
        this.tx = 0;
        this.ty = 0;
        down = false;
    }

    public void pressMove(float x, float y) {
        if (!down) {
            down = true;
            prevX = x;
            prevY = y;
        }

        moveX = x;
        moveY = y;

        tx = moveX - prevX;
        ty = moveY - prevY;

        int vw = GameView.view.getWidth();
        int vh = GameView.view.getHeight();
        int bhw = bitmap.getWidth() / 2;
        int bhh = bitmap.getHeight() / 2;

        this.x -= tx;
        if (this.x > bhw || bhw < vw - this.x) {
            if (this.x > bhw ) {
                this.x = bhw;
            }
            else {
                this.x = vw - bhw;
            }
        }
        this.y -= ty;
        if (this.y > bhh || bhh < vh - this.y) {
            if (this.y > bhh ) {
                this.y = bhh;
            }
            else {
                this.y = vh - bhh;
            }
        }
        
        prevX = x;
        prevY = y;
    }

    public void update() {

    }

    public void draw(Canvas canvas) {
        bitmap.draw(canvas, x, y);
    }
}
