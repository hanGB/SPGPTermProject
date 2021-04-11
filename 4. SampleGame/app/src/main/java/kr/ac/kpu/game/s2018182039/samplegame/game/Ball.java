package kr.ac.kpu.game.s2018182039.samplegame.game;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import kr.ac.kpu.game.s2018182039.samplegame.framework.GameObject;
import kr.ac.kpu.game.s2018182039.samplegame.ui.view.GameView;
import kr.ac.kpu.game.s2018182039.samplegame.R;

public class Ball implements GameObject {
    private float x, y;
    private float dx;
    private float dy;
    private static Bitmap bitmap;
    private static int imageWidth;
    private static int imageHeight;

    public Ball(float x, float y, float dx, float dy) {
        this.x = x;
        this.y = y;
        this.dx = dx;
        this.dy = dy;
        if (bitmap == null) {
            bitmap = BitmapFactory.decodeResource(GameView.view.getResources(), R.mipmap.soccer_ball_240);
            imageWidth = bitmap.getWidth();
            imageHeight = bitmap.getHeight();
        }
    }

    public void update() {
        MainGame game = MainGame.get();
        this.x +=  this.dx * game.frameTime;
        this.y +=  this.dy * game.frameTime;
        int w = GameView.view.getWidth();
        int h = GameView.view.getHeight();
        if (x < 0 || x > w - imageWidth) {
            dx *= -1;
        }
        if (y < 0 || y > h - imageHeight) {
            dy *= -1;
        }
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, this.x, this.y, null);
    }
}
