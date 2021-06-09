package kr.ac.kpu.s2018182039.kingdomrush.game.scenes.main;

import java.util.ArrayList;

import kr.ac.kpu.s2018182039.kingdomrush.framework.game.BaseGame;
import kr.ac.kpu.s2018182039.kingdomrush.game.scenes.menu.TitleMenuScene;

public class MainGame extends BaseGame {
    private boolean initialized;

    public static MainGame get() {
        return (MainGame) instance;
    }


    @Override
    public boolean initResources() {
        if (initialized) {
            return false;
        }

        push(new TitleMenuScene());

        initialized = true;
        return true;

    }
}
