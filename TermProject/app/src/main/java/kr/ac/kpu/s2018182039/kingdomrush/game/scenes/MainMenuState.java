package kr.ac.kpu.s2018182039.kingdomrush.game.scenes;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.MotionEvent;

import java.util.ArrayList;

import kr.ac.kpu.s2018182039.kingdomrush.framework.iface.GameObject;
import kr.ac.kpu.s2018182039.kingdomrush.framework.view.GameView;
import kr.ac.kpu.s2018182039.kingdomrush.R;
import kr.ac.kpu.s2018182039.kingdomrush.game.objects.ui.MovingButtonObject;
import kr.ac.kpu.s2018182039.kingdomrush.game.objects.ui.StaticDrawObject;

public class MainMenuState {
    // Singleton
    private static MainMenuState instance;

    ArrayList<GameObject> objects = new ArrayList<>();
    private MovingButtonObject startButton;

    public static MainMenuState get() {
        if (instance == null) {
            instance = new MainMenuState();
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

        StaticDrawObject background
                = new StaticDrawObject(R.mipmap.screen_slots, w/2, h/2, 6, 6, 1392, 646, 2);

        StaticDrawObject title
                = new StaticDrawObject(R.mipmap.screen_slots, w/2, h/3, 920, 650, 1387, 977, 2);

        Rect rect = new Rect(6, 6, 334,290);
        Rect rectPressed = new Rect(340, 6, 668,290);
        startButton = new MovingButtonObject(R.mipmap.screens_common_ko, R.mipmap.screens_common_ko,
                w/2, h * 5 / 10,w/2, h * 7 / 10, rect, rectPressed, 2);

        objects.add(background);
        objects.add(startButton);
        objects.add(title);

        initialized = true;
        return true;
    }

    public void update() {
        for (GameObject o : objects) {
            o.update();
        }
        if (startButton.IsUsed()) {
            clear();
            initialized = false;
            staring = true;
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
            startButton.pressOn(event.getX(), event.getY(), true);
            return true;
        }
        else if (action == MotionEvent.ACTION_UP) {
            startButton.pressOn(event.getX(), event.getY(), false);
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
