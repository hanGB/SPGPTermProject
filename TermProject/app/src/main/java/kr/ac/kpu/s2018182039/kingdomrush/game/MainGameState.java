package kr.ac.kpu.s2018182039.kingdomrush.game;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.MotionEvent;

import java.util.ArrayList;

import kr.ac.kpu.s2018182039.kingdomrush.R;
import kr.ac.kpu.s2018182039.kingdomrush.framework.GameObject;
import kr.ac.kpu.s2018182039.kingdomrush.ui.view.GameView;

public class MainGameState {

    // Singleton
    private static MainGameState instance;

    ArrayList<GameObject> objects = new ArrayList<>();

    public static MainGameState get() {
        if (instance == null) {
            instance = new MainGameState();
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
                = new StaticDrawObject(R.mipmap.test_map_background,
                w/2, h/2, 160, 175, 420, 320, 8);

        objects.add(background);
        objects.add(new EnemyGenerator());
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
        if (action == MotionEvent.ACTION_DOWN || action == MotionEvent.ACTION_MOVE) {
            return true;
        }
        else if (action == MotionEvent.ACTION_UP) {
            return true;
        }
        return false;
    }

    public void add(GameObject gameObject) {
        GameView.view.post(new Runnable() {
            @Override
            public void run() {
                objects.add(gameObject);
                //Log.d(TAG, "<R> object count = " + objects.size());
            }
        });
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
