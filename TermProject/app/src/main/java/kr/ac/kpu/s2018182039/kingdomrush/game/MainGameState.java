package kr.ac.kpu.s2018182039.kingdomrush.game;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.HashMap;

import kr.ac.kpu.s2018182039.kingdomrush.R;
import kr.ac.kpu.s2018182039.kingdomrush.framework.GameObject;
import kr.ac.kpu.s2018182039.kingdomrush.framework.Recyclable;
import kr.ac.kpu.s2018182039.kingdomrush.ui.view.GameView;
import kr.ac.kpu.s2018182039.kingdomrush.utils.CollisionHelper;

public class MainGameState {

    // Singleton
    private static MainGameState instance;

    ArrayList<ArrayList<GameObject>> layers;
    private static HashMap<Class, ArrayList<GameObject>> recycleBin = new HashMap<>();

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

    public static MainGameState get() {
        if (instance == null) {
            instance = new MainGameState();
        }
        return instance;
    }

    public float frameTime;
    private boolean initialized;

    public enum Layer {
        bg, tower, enemy, friendly, towerBuilder, bullet, controller, LAYER_COUNT
    }

    private void initLayers(int layerCount) {
        layers = new ArrayList<>();
        for (int i = 0; i < layerCount; ++i) {
            layers.add(new ArrayList<>());
        }
    }

    public boolean initResources() {
        if (initialized) {
            return false;
        }

        initLayers(Layer.LAYER_COUNT.ordinal());

        int w = GameView.view.getWidth();
        int h = GameView.view.getHeight();

        StaticDrawObject background
                = new StaticDrawObject(R.mipmap.test_map_background,
                w / 2, h / 2, 160, 175, 420, 320, 8);

        add(Layer.bg, background);
        add(Layer.controller, new EnemyGenerator());
        add(Layer.towerBuilder, new TowerBuilder(600, 600));
        add(Layer.towerBuilder, new TowerBuilder(1000, 300));
        add(Layer.towerBuilder, new TowerBuilder(1400, 600));

        initialized = true;
        return true;
    }

    public void update() {
        for (ArrayList<GameObject> objects : layers) {
            for (GameObject o : objects) {
                o.update();
            }
        }

        ArrayList<GameObject> enemies = layers.get(Layer.enemy.ordinal());
        ArrayList<GameObject> bullets = layers.get(Layer.bullet.ordinal());

        for (GameObject o1 : enemies) {
            EnemyObject enemy = (EnemyObject)o1;
            boolean collided = false;
            for (GameObject o2 : bullets){
                Bullet bullet = (Bullet) o2;
                if (CollisionHelper.collides(enemy, bullet)) {
                    if (enemy.giveDamage(bullet.damage)) {
                        remove(enemy, false);
                    }
                    remove(bullet, false);
                    collided = true;
                    break;
                }
            }
            if (collided) {
                break;
            }
        }
    }

    public void draw(Canvas canvas) {
        for (ArrayList<GameObject> objects : layers) {
            for (GameObject o : objects) {
                o.draw(canvas);
            }
        }
    }

    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        ArrayList<GameObject> towerBuilders = layers.get(Layer.towerBuilder.ordinal());
        ArrayList<GameObject> towers = layers.get(Layer.tower.ordinal());
        if (action == MotionEvent.ACTION_DOWN || action == MotionEvent.ACTION_MOVE) {
            for (GameObject o  : towerBuilders){
                TowerBuilder builder = (TowerBuilder)o;
                builder.pressOn(event.getX(), event.getY(), true);
            }
            for (GameObject o  : towers){
                TowerObject tower = (TowerObject)o;
                tower.pressOn(event.getX(), event.getY(), true);
            }

            return true;
        } else if (action == MotionEvent.ACTION_UP) {
            for (GameObject o  : towerBuilders){
                TowerBuilder builder = (TowerBuilder)o;
                builder.pressOn(event.getX(), event.getY(), false);
            }
            for (GameObject o  : towers){
                TowerObject tower = (TowerObject)o;
                tower.pressOn(event.getX(), event.getY(), false);
            }
            return true;
        }
        return false;
    }

    public void add(Layer layer, GameObject gameObject) {
        GameView.view.post(new Runnable() {
            @Override
            public void run() {
                ArrayList<GameObject> objects = layers.get(layer.ordinal());
                objects.add(gameObject);
                //Log.d(TAG, "<R> object count = " + objects.size());
            }
        });
    }

    public void remove(GameObject gameObject, boolean delayed) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                for (ArrayList<GameObject> objects : layers) {
                    boolean removed = objects.remove(gameObject);
                    if (removed) {
                        if (gameObject instanceof Recyclable) {
                            ((Recyclable) gameObject).recycle();
                            recycle(gameObject);
                        }
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

    public ArrayList<GameObject> getAllObjects(Layer layer) {
        ArrayList<GameObject> objects = layers.get(layer.ordinal());
        return objects;
    }
}
