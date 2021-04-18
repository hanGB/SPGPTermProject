package kr.ac.kpu.s2018182039.kingdomrush.game;


import android.graphics.Canvas;
import android.view.MotionEvent;

import java.util.ArrayList;

import kr.ac.kpu.s2018182039.kingdomrush.R;
import kr.ac.kpu.s2018182039.kingdomrush.framework.GameObject;
import kr.ac.kpu.s2018182039.kingdomrush.ui.view.GameView;

public class StageSelectState {
    // Singleton
    private static StageSelectState instance;

    ArrayList<GameObject> objects = new ArrayList<>();
    private MovingBackgroundObject backgroundMap;

    public static StageSelectState get() {
        if (instance == null) {
            instance = new StageSelectState();
        }
        return instance;
    }

    public float frameTime;
    public boolean staring;
    private boolean initialized;

    public boolean initResources() {
        if (initialized) {
            return false;
        }
        staring = false;

        int w = GameView.view.getWidth();
        int h = GameView.view.getHeight();

        backgroundMap = new MovingBackgroundObject(R.mipmap.screen_map_background,
                w/2, h/2, 30, 80, 940, 740, 3);

        objects.add(backgroundMap);

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
            backgroundMap.pressDown(event.getX(), event.getY());
            return true;
        }
        else if (action == MotionEvent.ACTION_MOVE) {
            backgroundMap.pressMove(event.getX(), event.getY());
            return true;
        }
        else if (action == MotionEvent.ACTION_UP) {
            backgroundMap.pressUp();
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

    public void clear() {
        GameView.view.post(new Runnable() {
            @Override
            public void run() {
                objects.clear();
            }
        });
    }
}
