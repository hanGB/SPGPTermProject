package kr.ac.kpu.s2018182039.kingdomrush.framework;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;

public class AnimationGameBitmapVertical {
    private final int PIXEL_SIZE;
    private Bitmap bitmap;
    private final int imageWidth;
    private final int imageHeight;
    private final int frameHeight;
    private final long createOn;
    private final float framePerSecond;
    private final int frameCount;
    private int frameIndex;

    public AnimationGameBitmapVertical(int resId, float framePerSecond, int frameCount, int pixel_size) {
        bitmap = kr.ac.kpu.s2018182039.kingdomrush.framework.GameBitmap.load(resId);
        imageWidth = bitmap.getWidth();
        imageHeight = bitmap.getHeight();

        if (frameCount == 0) {
            frameCount = imageWidth / imageHeight;
        }
        this.frameCount = frameCount;

        frameHeight = imageHeight / frameCount;
        createOn = System.currentTimeMillis();
        frameIndex = 0;
        this.framePerSecond = framePerSecond;

        PIXEL_SIZE = pixel_size;
    }

    public void draw(Canvas canvas, float x, float y) {
        int elapsed = (int)(System.currentTimeMillis() - createOn);
        frameIndex = Math.round(elapsed * 0.001f * framePerSecond) % frameCount;

        int w = imageWidth;
        int fh = frameHeight;
        int hw = w / 2 * PIXEL_SIZE;
        int hh = fh / 2 * PIXEL_SIZE;

        Rect src = new Rect(0, fh * frameIndex, w,fh * frameIndex + fh);
        RectF dst = new RectF(x - hw, y - hh, x + hw, y + hh);

        canvas.drawBitmap(bitmap, src, dst, null);
    }

    public void draw(Canvas canvas, float x, float y, int frameIndex) {
        int elapsed = (int)(System.currentTimeMillis() - createOn);
        int w = imageWidth;
        int fh = frameHeight;
        int hw = w / 2 * PIXEL_SIZE;
        int hh = fh / 2 * PIXEL_SIZE;

        Rect src = new Rect(0, fh * frameIndex, w,fh * frameIndex + fh);
        RectF dst = new RectF(x - hw, y - hh, x + hw, y + hh);

        canvas.drawBitmap(bitmap, src, dst, null);
    }

    public int getWidth() {
        return this.imageWidth * PIXEL_SIZE;
    }
    public int getHeight() {
        return this.frameHeight * PIXEL_SIZE;
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
