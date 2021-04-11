package kr.ac.kpu.s2018182039.kingdomrush;

import android.graphics.Canvas;
import android.view.MotionEvent;

public class MainMenuState {
    // Singleton
    private static MainMenuState instance;
    public static MainMenuState get() {
        if (instance == null) {
            instance = new MainMenuState();
        }
        return instance;
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

    }

    public boolean onTouchEvent(MotionEvent event) {
        return false;
    }
}
