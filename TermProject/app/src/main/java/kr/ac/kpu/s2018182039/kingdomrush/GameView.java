package kr.ac.kpu.s2018182039.kingdomrush;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.Choreographer;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

public class GameView extends View {

    private long lastFrame;

    public static GameView view;

    public GameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        GameView.view = this;
        startUpdating();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        //super.onSizeChanged(w, h, oldw, oldh);
        MainMenuState state = MainMenuState.get();
        state.initResources();
    }

    private void startUpdating() { doGameFrame(); }

    private void doGameFrame() {
        MainMenuState state = MainMenuState.get();
        state.update();

        invalidate();

        Choreographer.getInstance().postFrameCallback(new Choreographer.FrameCallback() {
            @Override
            public void doFrame(long time) {
                if (lastFrame == 0){
                    lastFrame = time;
                }
                state.frameTime = (float) (time - lastFrame) / 1_000_000_000;
                doGameFrame();
                lastFrame = time;
            }
        });
    }

    @Override
    protected void onDraw(Canvas canvas) {
        MainMenuState state = MainMenuState.get();
        state.draw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        MainMenuState state = MainMenuState.get();
        return state.onTouchEvent(event);
    }
}
