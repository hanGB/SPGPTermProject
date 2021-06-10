package kr.ac.kpu.s2018182039.kingdomrush.game.objects.tower;

import java.util.ArrayList;

import kr.ac.kpu.s2018182039.kingdomrush.framework.game.BaseGame;
import kr.ac.kpu.s2018182039.kingdomrush.framework.iface.GameObject;
import kr.ac.kpu.s2018182039.kingdomrush.game.objects.friendly.SoldierObject;
import kr.ac.kpu.s2018182039.kingdomrush.game.scenes.main.MainScene;

public class SoldierTower extends TowerObject {
    private static final int BULLET_SPEED = 1500;
    private static final float SPAWN_INTERVAL = 5.0f;
    private static final int MAX_SOLDIER = 3;

    private static int towerIdCount = 0;

    private final float x;
    private final float y;

    private float gatheringPlaceX;
    private float gatheringPlaceY;

    private float spawnTime;

    private int towerId;

    SoldierTower(float x, float y) {
        super(x, y);
        this.x = x;
        this.y = y;

        gatheringPlaceX = x;
        gatheringPlaceY = y;
        spawnTime = SPAWN_INTERVAL;

        towerId = towerIdCount;
        towerIdCount++;
        damage = 5;
    }

    @Override
    public void update() {
        BaseGame game = BaseGame.get();

        ArrayList<GameObject> objects = game.getAllObjects(MainScene.Layer.friendly.ordinal());

        int count = 0;
        for (GameObject object : objects) {
            if (SoldierObject.class == object.getClass()) {
                SoldierObject soldier = (SoldierObject)object;

                if (soldier.towerId == this.towerId) {
                    count++;
                }
            }
        }
        if (count == MAX_SOLDIER) {
            frameIndex = 0;
            return;
        }

        spawnTime += game.frameTime;
        if (spawnTime >= SPAWN_INTERVAL) {
            spawnSolider();
            spawnTime -= SPAWN_INTERVAL;
        }

        if (spawnTime < SPAWN_INTERVAL * 9 / 10) {
            frameIndex = 0;
        } else {
            frameIndex = 1;
        }
    }

    private void spawnSolider() {
        float dx = 1.0f;
        float dy = 1.0f;
        BaseGame game = BaseGame.get();
        SoldierObject solider = new SoldierObject(x+50, y +50, gatheringPlaceX, gatheringPlaceY, towerId, damage);
        game.add(MainScene.Layer.friendly.ordinal(), solider);
    }
}
