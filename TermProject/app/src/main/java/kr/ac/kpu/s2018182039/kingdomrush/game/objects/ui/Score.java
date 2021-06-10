package kr.ac.kpu.s2018182039.kingdomrush.game.objects.ui;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;

import kr.ac.kpu.s2018182039.kingdomrush.R;
import kr.ac.kpu.s2018182039.kingdomrush.framework.bitmap.GameBitmap;
import kr.ac.kpu.s2018182039.kingdomrush.framework.iface.GameObject;
import kr.ac.kpu.s2018182039.kingdomrush.framework.view.GameView;

public class Score implements GameObject {
    private final Bitmap bitmap;
    private final int right;
    private final int top;
    private final float pixel_size;

    private Rect src = new Rect();
    private RectF dst = new RectF();

    public Score(int right, int top, float pixel_size) {
       bitmap = GameBitmap.load(R.mipmap.number_24x32);
       this.right = right;
       this.top = top;
       this.pixel_size = pixel_size;
   }

    private int score, displayScore;

    public void setScore(int score) {
        this.score = score;
        this.displayScore = score;
    }

    public void addScore(int amount) {
        this.score += amount;
    }

    @Override
    public void update() {
        if (displayScore < score){
            displayScore++;
        }
    }

    @Override
    public void draw(Canvas canvas) {
        int value = this.displayScore;
        int nw = bitmap.getWidth() / 10;
        int nh = bitmap.getHeight();
        int x = right;
        int dw = (int) (nw * pixel_size);
        int dh = (int) (nh * pixel_size);

        while (value > 0) {
            int digit = value % 10;
            src.set(digit * nw, 0, (digit + 1) * nw, nh);
            x -= dw;
            dst.set(x, top, x + dw, top + dh );
            canvas.drawBitmap(bitmap, src, dst, null);
            value /= 10;
        }
    }

}
