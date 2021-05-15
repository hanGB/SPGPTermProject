package kr.ac.kpu.s2018182039.kingdomrush.game;

import android.graphics.Canvas;

import kr.ac.kpu.s2018182039.kingdomrush.R;
import kr.ac.kpu.s2018182039.kingdomrush.framework.AnimationGameBitmapVertical;
import kr.ac.kpu.s2018182039.kingdomrush.framework.GameObject;

public class SoldierTower implements GameObject {
    private static final int BULLET_SPEED = 1500;
    private static final float SPAWN_INTERVAL = 1.0f;

    private final AnimationGameBitmapVertical towerBitmap;

    private final float x;
    private final float y;

    private float gatheringPlaceX;
    private float gatheringPlaceY;

    private float spawnTime;
    private boolean isSpawning = false;
    private boolean isSpawnSoldier = false;

    SoldierTower(float x, float y) {
        this.x = x;
        this.y = y;

        gatheringPlaceX = x + 200;
        gatheringPlaceY = y + 200;
        spawnTime = SPAWN_INTERVAL;
        towerBitmap = new AnimationGameBitmapVertical(R.mipmap.soldier_tower, 0, 2, 4);
    }

    @Override
    public void update() {
        MainGameState state = MainGameState.get();

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

    }

    @Override
    public void draw(Canvas canvas) {
        if (!isSpawning) {
            towerBitmap.draw(canvas, x, y, 0);
        } else {
            if (spawnTime < SPAWN_INTERVAL / 2) {
                towerBitmap.draw(canvas, x, y, 0);
            } else {
                towerBitmap.draw(canvas, x, y, 1);
            }
        }
    }


    private void spawnSolider() {
        float dx = 1.0f;
        float dy = 1.0f;
        MainGameState state = MainGameState.get();
        SoldierObject solider = new SoldierObject(x+50, y +50, gatheringPlaceX, gatheringPlaceY);
        state.add(MainGameState.Layer.friendly, solider);
    }
}
