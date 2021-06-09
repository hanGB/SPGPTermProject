package kr.ac.kpu.s2018182039.kingdomrush.framework.bitmap;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;

public class AnimationGameBitmap extends GameBitmap {
    private static final int PIXEL_SIZE = 4;
    private Bitmap bitmap;
    private final int imageWidth;
    private final int imageHeight;
    private final int frameWidth;
    private final long createOn;
    private final float framePerSecond;
    private final int frameCount;
    private int frameIndex;

    public AnimationGameBitmap(int resId, float framePerSecond, int frameCount) {
        bitmap = GameBitmap.load(resId);
        imageWidth = bitmap.getWidth();
        imageHeight = bitmap.getHeight();

        if (frameCount == 0) {
            frameCount = imageWidth / imageHeight;
        }
        this.frameCount = frameCount;

        frameWidth = imageWidth / frameCount;
        createOn = System.currentTimeMillis();
        frameIndex = 0;
        this.framePerSecond = framePerSecond;
    }

    public void draw(Canvas canvas, float x, float y) {
        int elapsed = (int)(System.currentTimeMillis() - createOn);
        frameIndex = Math.round(elapsed * 0.001f * framePerSecond) % frameCount;

        int h = imageHeight;
        int fw = frameWidth;
        int hw = fw / 2 * PIXEL_SIZE;
        int hh = h / 2 * PIXEL_SIZE;

        Rect src = new Rect(fw * frameIndex, 0, fw * frameIndex + fw, h);
        RectF dst = new RectF(x - hw, y - hh, x + hw, y + hh);

        canvas.drawBitmap(bitmap, src, dst, null);
    }

    public int getWidth() {
        return this.frameWidth * PIXEL_SIZE;
    }
    public int getHeight() {
        return this.imageHeight * PIXEL_SIZE;
    }

    public void getBoundingRect(float x, float y, RectF rect) {
        int hw = getWidth() / 2;
        int hh = getHeight() / 2;

        float dl = x - hw;
        float dt = y - hh;
        float dr = x + hw;
        float db = y + hh;

        rect.set(dl, dt, dr, db);
    }
}
