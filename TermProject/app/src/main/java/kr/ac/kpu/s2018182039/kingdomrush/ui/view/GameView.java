package kr.ac.kpu.s2018182039.kingdomrush.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.Choreographer;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import kr.ac.kpu.s2018182039.kingdomrush.framework.Sound;
import kr.ac.kpu.s2018182039.kingdomrush.game.MainGameState;
import kr.ac.kpu.s2018182039.kingdomrush.game.MainMenuState;
import kr.ac.kpu.s2018182039.kingdomrush.game.StageSelectState;

public class GameView extends View {

    private long lastFrame;
    public static GameView view;
    private int stateNumber;

    public GameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        GameView.view = this;
        Sound.init(context);
        stateNumber = 0;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        //super.onSizeChanged(w, h, oldw, oldh);
        MainMenuState state = MainMenuState.get();
        boolean justInitialized = state.initResources();

        if (justInitialized){
            requestCallback();
        }
    }

    private void update() {
        if (stateNumber == 0) {
            MainMenuState state = MainMenuState.get();
            state.update();
            if (state.staring) {
//                stateNumber = 1;

//                StageSelectState state1 = StageSelectState.get();
//                state1.initResources();
                stateNumber = 2;

                MainGameState state2 = MainGameState.get();
                state2.initResources();
            }
        }
        else if (stateNumber == 1) {
            StageSelectState state = StageSelectState.get();
            state.update();
        }
        else {
            MainGameState state = MainGameState.get();
            state.update();
        }
        invalidate();
    }

    private void requestCallback() {
        Choreographer.getInstance().postFrameCallback(new Choreographer.FrameCallback() {
            @Override
            public void doFrame(long time) {
                if (lastFrame == 0) {
                    lastFrame = time;
                }
                if (stateNumber == 0) {
                    MainMenuState state = MainMenuState.get();
                    state.frameTime = (float) (time - lastFrame) / 1_000_000_000;
                }
                else if (stateNumber == 1) {
                    StageSelectState state = StageSelectState.get();
                    state.frameTime = (float) (time - lastFrame) / 1_000_000_000;
                }
                else {
                    MainGameState state = MainGameState.get();
                    state.frameTime = (float) (time - lastFrame) / 1_000_000_000;
                }
                update();
                lastFrame = time;
                requestCallback();
            }
        });
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (stateNumber == 0) {
            MainMenuState state = MainMenuState.get();
            state.draw(canvas);
        }
        else if (stateNumber == 1) {
            StageSelectState state = StageSelectState.get();
            state.draw(canvas);
        }
        else {
            MainGameState state = MainGameState.get();
            state.draw(canvas);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (stateNumber == 0) {
            MainMenuState state = MainMenuState.get();
            return state.onTouchEvent(event);
        }
        else if (stateNumber == 1) {
            StageSelectState state = StageSelectState.get();
            return state.onTouchEvent(event);
        }
        else {
            MainMenuState state = MainMenuState.get();
            return state.onTouchEvent(event);
        }
    }
}
