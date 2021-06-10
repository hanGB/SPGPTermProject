package kr.ac.kpu.s2018182039.kingdomrush.framework.game;

import android.view.MotionEvent;

import java.util.ArrayList;

import kr.ac.kpu.s2018182039.kingdomrush.framework.iface.GameObject;
import kr.ac.kpu.s2018182039.kingdomrush.framework.iface.Recyclable;
import kr.ac.kpu.s2018182039.kingdomrush.framework.view.GameView;

public class Scene {
    protected ArrayList<ArrayList<GameObject>> layers;
    public ArrayList<ArrayList<GameObject>> getLayers() { return layers; }
    public boolean isTransparent() {
        return transparent;
    }

    protected boolean transparent;

    protected void initLayers(int layerCount) {
        layers = new ArrayList<>();
        for (int i = 0; i < layerCount; i++) {
            layers.add(new ArrayList<>());
        }
    }
    public void add(int layerIndex, GameObject gameObject) {
        GameView.view.post(new Runnable() {
            @Override
            public void run() {
                ArrayList<GameObject> objects = layers.get(layerIndex);
                objects.add(gameObject);
            }
        });
    }

    public void remove(GameObject gameObject) {
        remove(gameObject, true);
    }
    public void remove(GameObject gameObject, boolean delayed) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                BaseGame game = BaseGame.get();
                for (ArrayList<GameObject> objects: layers) {
                    boolean removed = objects.remove(gameObject);
                    if (removed) {
                        if (gameObject instanceof Recyclable) {
                            ((Recyclable) gameObject).recycle();
                            game.recycle(gameObject);
                        }
                        //Log.d(TAG, "Removed: " + gameObject);
                        break;
                    }
                }
            }
        };
        if (delayed) {
            GameView.view.post(runnable);
        } else {
            runnable.run();
        }
    }

    public ArrayList<GameObject> getAllObjects(int layerOrdinal) {
        ArrayList<GameObject> objects = layers.get(layerOrdinal);
        return objects;
    }

    public ArrayList<GameObject> objectsAt(int layerIndex) {
        return layers.get(layerIndex);
    }
    public void start() {}
    public void end() {}
    public void pause() {}
    public void resume() {}
    public boolean onTouchEvent(MotionEvent e) { return false; }
    public boolean handleBackKey() { return false; }
    // 충돌 처리
    public void processCollision() {}
    // 배경이 이동했을때 위치 맞춤
    public void adjustLocation() {}
}
