package kr.ac.kpu.s2018182039.kingdomrush.game.scenes.main;

import android.view.MotionEvent;

import java.util.ArrayList;

import kr.ac.kpu.s2018182039.kingdomrush.R;
import kr.ac.kpu.s2018182039.kingdomrush.framework.game.Scene;
import kr.ac.kpu.s2018182039.kingdomrush.framework.iface.GameObject;
import kr.ac.kpu.s2018182039.kingdomrush.framework.utils.CollisionHelper;
import kr.ac.kpu.s2018182039.kingdomrush.framework.utils.Sound;
import kr.ac.kpu.s2018182039.kingdomrush.framework.view.GameView;
import kr.ac.kpu.s2018182039.kingdomrush.game.control.EnemyGenerator;
import kr.ac.kpu.s2018182039.kingdomrush.game.control.EnemyMovementPointsData;
import kr.ac.kpu.s2018182039.kingdomrush.game.control.EnemyPathPoint;
import kr.ac.kpu.s2018182039.kingdomrush.game.control.TowerLocationData;
import kr.ac.kpu.s2018182039.kingdomrush.game.objects.bullet.BombBullet;
import kr.ac.kpu.s2018182039.kingdomrush.game.objects.bullet.Bullet;
import kr.ac.kpu.s2018182039.kingdomrush.game.objects.enemy.EnemyObject;
import kr.ac.kpu.s2018182039.kingdomrush.game.objects.friendly.SoldierObject;
import kr.ac.kpu.s2018182039.kingdomrush.game.objects.tower.TowerBuilder;
import kr.ac.kpu.s2018182039.kingdomrush.game.objects.tower.TowerObject;
import kr.ac.kpu.s2018182039.kingdomrush.game.objects.ui.BattleHUD;
import kr.ac.kpu.s2018182039.kingdomrush.game.objects.ui.MovingBackgroundObject;

public class MainScene extends Scene {
    private EnemyMovementPointsData pointsData = new EnemyMovementPointsData();
    private TowerLocationData towerLocationData = new TowerLocationData();
    public int stageId = 3;

    private int[] stageMapBitmaps = {
            R.mipmap.map_1,
            R.mipmap.map_2,
            R.mipmap.map_3,
    };

    private MovingBackgroundObject backgroundMap;
    private BattleHUD battleHUD;

    public enum Layer {
        bg,  tower, enemy, friendly, towerBuilder, bullet, bomb, point, point2, controller, ui, LAYER_COUNT
    }
    public static MainScene scene;
    public void add(Layer layer, GameObject obj) {
        add(layer.ordinal(), obj);
    }
    public ArrayList<GameObject> objectsAt(Layer layer) {
        return objectsAt(layer.ordinal());
    }

    @Override
    public void start() {
        scene = this;
        super.start();
        int w = GameView.view.getWidth();
        int h = GameView.view.getHeight();

        initLayers(Layer.LAYER_COUNT.ordinal());

        backgroundMap
                = new MovingBackgroundObject(stageMapBitmaps[stageId - 1],
                w / 2, h / 2, 50, 50, 850, 850, 3);

        add(Layer.bg, backgroundMap);
        add(Layer.controller, new EnemyGenerator(stageId));

        for (int i = 0; i < towerLocationData.locationPoints[stageId - 1].length; i += 2) {
            add(Layer.towerBuilder,
                    new TowerBuilder(towerLocationData.locationPoints[stageId - 1][i], towerLocationData.locationPoints[stageId - 1][i + 1]));
        }

        for (int i = 0; i < pointsData.movePoints[stageId - 1].length; i += 2) {
            add(Layer.point, new EnemyPathPoint(pointsData.movePoints[stageId - 1][i], pointsData.movePoints[stageId - 1][i + 1]));
        }

        if (stageId > 1) {
            for (int i = 0; i < pointsData.movePoints[stageId - 1 + 2].length; i += 2) {
                add(Layer.point2, new EnemyPathPoint(pointsData.movePoints[stageId - 1 + 2][i], pointsData.movePoints[stageId - 1 + 2][i + 1]));
            }
        }

        battleHUD = new BattleHUD(w * 1/ 6, h * 1/ 6);
        add(Layer.ui, battleHUD);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        ArrayList<GameObject> towerBuilders = layers.get(Layer.towerBuilder.ordinal());
        ArrayList<GameObject> towers = layers.get(Layer.tower.ordinal());
        if (action == MotionEvent.ACTION_DOWN) {
            backgroundMap.pressMove(event.getX(), event.getY());

            for (GameObject o  : towerBuilders){
                TowerBuilder builder = (TowerBuilder)o;
                builder.pressOn(event.getX(), event.getY(), true);
            }
            for (GameObject o  : towers){
                TowerObject tower = (TowerObject)o;
                tower.pressOn(event.getX(), event.getY(), true);
            }

            return true;
        }
        else if (action == MotionEvent.ACTION_MOVE) {
            backgroundMap.pressMove(event.getX(), event.getY());
            return true;
        }
        else if (action == MotionEvent.ACTION_UP) {
            backgroundMap.pressUp();
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

    @Override
    public void processCollision() {
        ArrayList<GameObject> enemies = layers.get(Layer.enemy.ordinal());
        ArrayList<GameObject> bullets = layers.get(Layer.bullet.ordinal());

        for (GameObject o1 : enemies) {
            EnemyObject enemy = (EnemyObject)o1;
            boolean collided = false;
            for (GameObject o2 : bullets){
                Bullet bullet = (Bullet) o2;
                if (CollisionHelper.collides(enemy, bullet)) {
                    if (bullet.arrow) {
                        Sound.play(R.raw.sound_arrow_hit3);
                    }
                    if (enemy.giveDamage(bullet.damage)) {
                        Sound.play(R.raw.sound_enemy_orc_dead);
                        remove(enemy, false);
                        giveGold(15);
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

    @Override
    public void adjustLocation() {
        if ( backgroundMap.moveX == 0 && backgroundMap.moveY == 0) {
            return;
        }

        for (GameObject o : objectsAt(Layer.tower)) {
            TowerObject tower = (TowerObject)o;
            tower.adjustLocationWithBackground(backgroundMap.moveX, backgroundMap.moveY);
        }
        for (GameObject o : objectsAt(Layer.towerBuilder)) {
            TowerBuilder builder = (TowerBuilder)o;
            builder.adjustLocationWithBackground(backgroundMap.moveX, backgroundMap.moveY);
        }
        for (GameObject o : objectsAt(Layer.point)) {
            EnemyPathPoint point = (EnemyPathPoint)o;
            point.adjustLocationWithBackground(backgroundMap.moveX, backgroundMap.moveY);
        }
        for (GameObject o : objectsAt(Layer.point2)) {
            EnemyPathPoint point = (EnemyPathPoint)o;
            point.adjustLocationWithBackground(backgroundMap.moveX, backgroundMap.moveY);
        }
        for (GameObject o : objectsAt(Layer.enemy)) {
            EnemyObject enemy = (EnemyObject)o;
            enemy.adjustLocationWithBackground(backgroundMap.moveX, backgroundMap.moveY);
        }
        for (GameObject o : objectsAt(Layer.friendly)) {
            SoldierObject soldier = (SoldierObject)o;
            soldier.adjustLocationWithBackground(backgroundMap.moveX, backgroundMap.moveY);
        }
        for (GameObject o : objectsAt(Layer.bullet)) {
            Bullet bullet = (Bullet)o;
            bullet.adjustLocationWithBackground(backgroundMap.moveX, backgroundMap.moveY);
        }
        for (GameObject o : objectsAt(Layer.bomb)) {
            BombBullet bomb = (BombBullet)o;
            bomb.adjustLocationWithBackground(backgroundMap.moveX, backgroundMap.moveY);
        }


        backgroundMap.moveX = 0;
        backgroundMap.moveY = 0;
    }

    public void damageToLife(int value) {
        battleHUD.addHp(-value);
    }

    public void giveGold(int value) {
        battleHUD.addGold(value);
    }

    public void increaseWave(int value) {
        battleHUD.addWave(value);
    }

    public boolean isCanBuy(int value) {
        return battleHUD.isNotExpensive(value);
    }
}
