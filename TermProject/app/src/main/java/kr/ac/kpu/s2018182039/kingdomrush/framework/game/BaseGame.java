package kr.ac.kpu.s2018182039.kingdomrush.framework.game;

import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.HashMap;

import kr.ac.kpu.s2018182039.kingdomrush.framework.iface.GameObject;
import kr.ac.kpu.s2018182039.kingdomrush.framework.view.GameView;


public class BaseGame {
    private static final String TAG = BaseGame.class.getSimpleName();

    // singleton
    protected static BaseGame instance;

    public static BaseGame get() {
        return instance;
    }

    public float frameTime;

    protected BaseGame() {
        instance = this;
    }

    private static HashMap<Class, ArrayList<GameObject>> recycleBin = new HashMap<>();

    ArrayList<Scene> sceneStack = new ArrayList<>();
    public Scene getTopScene() {
        int lastIndex = sceneStack.size() - 1;
        if (lastIndex < 0) return null;
        return sceneStack.get(lastIndex);
    }
    public void start(Scene scene) {
        int lastIndex = sceneStack.size() - 1;
        if (lastIndex >= 0) {
            Scene top = sceneStack.remove(lastIndex);
            Log.d(TAG, "Ending(in start): " + top);
            top.end();
            sceneStack.set(lastIndex, scene);
        } else {
            sceneStack.add(scene);
        }
        Log.d(TAG, "Starting(in start): " + scene);
        scene.start();
    }
    public void push(Scene scene) {
        int lastIndex = sceneStack.size() - 1;
        if (lastIndex >= 0) {
            Scene top = sceneStack.get(lastIndex);
            Log.d(TAG, "Pausing: " + top);
            top.pause();
        }
        sceneStack.add(scene);
        Log.d(TAG, "Starting(in push): " + scene);
        scene.start();
    }
    public void popScene() {
        int lastIndex = sceneStack.size() - 1;
        if (lastIndex >= 0) {
            Scene top = sceneStack.remove(lastIndex);
            Log.d(TAG, "Ending(in pop): " + top);
            top.end();
        }
        lastIndex--;
        if (lastIndex >= 0) {
            Scene top = sceneStack.get(lastIndex);
            Log.d(TAG, "Resuming: " + top);
            top.resume();
        } else {
            Log.e(TAG, "should end app in popScene()");
            GameView.view.finishActivity();
        }
    }
    public void recycle(GameObject object) {
        Class clazz = object.getClass();
        ArrayList<GameObject> array = recycleBin.get(clazz);
        if (array == null) {
            array = new ArrayList<>();
            recycleBin.put(clazz, array);
        }
        array.add(object);
    }
    public GameObject get(Class clazz) {
        ArrayList<GameObject> array = recycleBin.get(clazz);
        if (array == null || array.isEmpty()) return null;
        return array.remove(0);
    }

    public boolean initResources() {
        // prints this is error
        return false;
    }

    public void update() {
        if (sceneStack.size() == 0) {
            return;
        }
        ArrayList<ArrayList<GameObject>> layers = getTopScene().getLayers();

        for (ArrayList<GameObject> objects: layers) {
            for (GameObject o : objects) {
                o.update();
            }
        }
        getTopScene().processCollision();
    }

    public void draw(Canvas canvas) {
        if (sceneStack.size() == 0) {
            return;
        }
        draw(canvas, sceneStack.size() - 1);
    }
    protected void draw(Canvas canvas, int index) {
        Scene scene = sceneStack.get(index);
        if (scene.isTransparent() && index > 0) {
            draw(canvas, index - 1);
        }
        ArrayList<ArrayList<GameObject>> layers = scene.getLayers();

        for (ArrayList<GameObject> objects: layers) {
            for (GameObject o : objects) {
                o.draw(canvas);
            }
        }
    }

    public boolean onTouchEvent(MotionEvent event) {
        return getTopScene().onTouchEvent(event);
    }

    public boolean handleBackKey() {
        Scene scene = getTopScene();
        if (scene.handleBackKey()) {
            return true;
        }
        popScene();
        return true;
    }

    public void remove(GameObject gameObject) {
        remove(gameObject, true);
    }
    public void remove(GameObject gameObject, boolean delayed) {
        Scene scene = getTopScene();
        scene.remove(gameObject, delayed);
    }

    public ArrayList<GameObject> getAllObjects(int layerOrdinal) {
        Scene scene = getTopScene();
        ArrayList<GameObject> objects = scene.getAllObjects(layerOrdinal);
        return objects;
    }

    public void add(int layerIndex, GameObject gameObject) {
        Scene scene = getTopScene();
        scene.add(layerIndex, gameObject);
    }
}
