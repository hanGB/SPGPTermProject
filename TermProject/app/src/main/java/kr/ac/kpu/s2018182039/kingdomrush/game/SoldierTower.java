package kr.ac.kpu.s2018182039.kingdomrush.game;

import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.ArrayList;

import kr.ac.kpu.s2018182039.kingdomrush.R;
import kr.ac.kpu.s2018182039.kingdomrush.framework.AnimationGameBitmapVertical;
import kr.ac.kpu.s2018182039.kingdomrush.framework.GameObject;

public class SoldierTower extends TowerObject {
    private static final int BULLET_SPEED = 1500;
    private static final float SPAWN_INTERVAL = 2.0f;
    private static final int MAX_SOLDIER = 3;

    private static int towerIdCount = 0;

    private final float x;
    private final float y;

    private float gatheringPlaceX;
    private float gatheringPlaceY;

    private float spawnTime;
    private boolean isSpawning = false;
    private boolean isSpawnSoldier = false;

    private int towerId;

    SoldierTower(float x, float y) {
        super(x, y);
        this.x = x;
        this.y = y;

        gatheringPlaceX = x + 200;
        gatheringPlaceY = y + 200;
        spawnTime = SPAWN_INTERVAL;

        towerId = towerIdCount;
        towerIdCount++;
    }

    @Override
    public void update() {
        MainGameState state = MainGameState.get();

        ArrayList<GameObject> objects = state.getAllObjects(MainGameState.Layer.friendly);

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
            isSpawning = false;
            return;
        }

        spawnTime += state.frameTime;
        if (spawnTime >= SPAWN_INTERVAL) {
            isSpawning = true;
            isSpawnSoldier = false;
            spawnTime -= SPAWN_INTERVAL;
        }
        if (isSpawning) {
            if (spawnTime > SPAWN_INTERVAL / 2) {
                if (!isSpawnSoldier) {
                    spawnSolider();
                    isSpawnSoldier = true;
                }
            }
        }


        if (!isSpawning) {
            frameIndex = 0;
        } else {
            if (spawnTime < SPAWN_INTERVAL / 2) {
                frameIndex = 0;
            } else {
                frameIndex = 1;
            }
        }
    }

    private void spawnSolider() {
        float dx = 1.0f;
        float dy = 1.0f;
        MainGameState state = MainGameState.get();
        SoldierObject solider = new SoldierObject(x+50, y +50, gatheringPlaceX, gatheringPlaceY, towerId);
        state.add(MainGameState.Layer.friendly, solider);
    }
}
