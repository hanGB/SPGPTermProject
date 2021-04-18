package kr.ac.kpu.s2018182039.kingdomrush.game;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.MotionEvent;

import java.util.ArrayList;

import kr.ac.kpu.s2018182039.kingdomrush.framework.GameObject;
import kr.ac.kpu.s2018182039.kingdomrush.ui.view.GameView;
import kr.ac.kpu.s2018182039.kingdomrush.R;

public class MainMenuState {
    // Singleton
    private static MainMenuState instance;

    ArrayList<GameObject> objects = new ArrayList<>();

    public static MainMenuState get() {
        if (instance == null) {
            instance = new MainMenuState();
        }
        return instance;
    }

    public float frameTime;
    private boolean initialized;

    public boolean initResources() {
        if (initialized) {
            return false;
        }
        int w = GameView.view.getWidth();
        int h = GameView.view.getHeight();

        StaticDrawObject background
                = new StaticDrawObject(R.mipmap.screen_slots, w/2, h/2, 6, 6, 1392, 646);
        objects.add(background);

        initialized = true;
        return true;
    }

    public void update() {
        for (GameObject o : objects) {
            o.update();
        }
    }

    public void draw(Canvas canvas){
        for (GameObject o: objects) {
            o.draw(canvas);
        }
    }

    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        if (action == MotionEvent.ACTION_DOWN) {
            return true;
        }
        return false;
    }

    public void add(GameObject gameObject) {
        objects.add(gameObject);
    }

    public void remove(GameObject gameObject) {
        GameView.view.post(new Runnable() {
            @Override
            public void run() {
                objects.remove(gameObject);
            }
        });
    }
}
