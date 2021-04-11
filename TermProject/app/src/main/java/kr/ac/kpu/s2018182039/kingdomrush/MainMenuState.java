package kr.ac.kpu.s2018182039.kingdomrush;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.MotionEvent;

public class MainMenuState {
    // Singleton
    private static MainMenuState instance;
    private static Bitmap backGroundImage;
    private static int backGroundImageWidth;
    private static int backGroundImageHeight;
    private static Bitmap buttonImage;
    private static int buttonImageImageWidth;
    private static int buttonImageImageHeight;

    public static MainMenuState get() {
        if (instance == null) {
            instance = new MainMenuState();
        }
        return instance;
    }

    public MainMenuState() {
        if (backGroundImage == null) {
            backGroundImage = BitmapFactory.decodeResource(GameView.view.getResources(), R.mipmap.screen_slots);
            backGroundImageWidth = backGroundImage.getWidth();
            backGroundImageHeight = backGroundImage.getHeight();
        }
        if (buttonImage == null) {
            buttonImage = BitmapFactory.decodeResource(GameView.view.getResources(), R.mipmap.screens_common_ko);
            buttonImageImageWidth = buttonImage.getWidth();
            buttonImageImageHeight = buttonImage.getHeight();
        }
    }

    public float frameTime;
    private boolean initialized;

    public void initResources() {
        if (initialized) {
            return;
        }

        initialized = true;
    }

    public void update() {
        if (!initialized) return;
    }

    public void draw(Canvas canvas){

        canvas.drawBitmap(backGroundImage,
                new Rect(6, 6, backGroundImageWidth * 679 / 1000, backGroundImageHeight * 315 / 1000),
                new Rect(0, 0, canvas.getWidth(), canvas.getHeight()),
                null);
    }

    public boolean onTouchEvent(MotionEvent event) {
        return false;
    }
}
