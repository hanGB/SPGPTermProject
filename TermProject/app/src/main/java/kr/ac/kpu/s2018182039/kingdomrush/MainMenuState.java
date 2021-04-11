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
    private static int buttonImageWidth;
    private static int buttonImageHeight;

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
            buttonImageWidth = buttonImage.getWidth();
            buttonImageHeight = buttonImage.getHeight();
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
        // 배경
        canvas.drawBitmap(backGroundImage,
                new Rect(6, 6, backGroundImageWidth * 679 / 1000, backGroundImageHeight * 315 / 1000),
                new Rect(0, 0, canvas.getWidth(), canvas.getHeight()),
                null);

        // 시작 버튼
        canvas.drawBitmap(buttonImage,
                new Rect(5, 5,
                        buttonImageWidth * 326 / 1000, buttonImageHeight * 283 / 1000),
                new Rect(canvas.getWidth() / 2 - buttonImageWidth * 300 / 1000, canvas.getHeight() * 2 / 5,
                        canvas.getWidth() / 2 + buttonImageWidth * 400 / 1000,
                        canvas.getHeight() * 2 / 5 + canvas.getHeight() * 2 / 5),
                null);

        // 게임 로고
        canvas.drawBitmap(backGroundImage,
                new Rect(backGroundImageWidth * 445 / 1000, backGroundImageHeight * 316 / 1000,
                        backGroundImageWidth * 679 / 1000, backGroundImageHeight * 479 / 1000),
                new Rect(canvas.getWidth() / 2 - backGroundImageWidth * 234 / 1000, 0,
                        canvas.getWidth() / 2 - backGroundImageWidth * 234 / 1000 + canvas.getHeight() / 2 * 234 / 163,
                        canvas.getHeight() / 2),
                null);
    }

    public boolean onTouchEvent(MotionEvent event) {
        return false;
    }
}
