package kr.ac.kpu.s2018182039.kingdomrush.game.control;

import android.graphics.Canvas;
import android.graphics.Paint;

import kr.ac.kpu.s2018182039.kingdomrush.framework.bitmap.StaticGameBitmap;
import kr.ac.kpu.s2018182039.kingdomrush.framework.iface.GameObject;

public class EnemyPathPoint implements GameObject {

    public float x, y;
    private Paint paint = new Paint();

    public EnemyPathPoint(float x, float y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Canvas canvas) {
        //paint.setStyle(Paint.Style.FILL);
        //paint.setColor(0xFFFF0000);
        //canvas.drawCircle(x, y, 30, paint);
    }

    public void adjustLocationWithBackground(float x, float y) {
        this.x -= x;
        this.y -= y;
    }
}
