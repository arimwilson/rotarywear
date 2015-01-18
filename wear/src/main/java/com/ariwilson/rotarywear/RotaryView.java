package com.ariwilson.rotarywear;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class RotaryView extends SurfaceView implements SurfaceHolder.Callback {
    class RotaryThread extends Thread {
        public RotaryThread(Context ctx, SurfaceHolder holder) {
            ctx_ = ctx;
            holder_ = holder;
        }

        private Context ctx_;
        private SurfaceHolder holder_;
    }

    public RotaryView(Context ctx) {
        super(ctx);
        init(ctx);
    }

    public RotaryView(Context ctx, AttributeSet attrs) {
        super(ctx, attrs);
        init(ctx);
    }

    public RotaryView(Context ctx, AttributeSet attrs, int defStyle) {
        super(ctx, attrs, defStyle);
        init(ctx);
    }

    private void init(Context ctx) {
        rotary_thread_ = new RotaryThread(ctx, getHolder());
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
    }

    public void surfaceCreated(SurfaceHolder holder) {
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
    }

    public boolean onTouchEvent(MotionEvent event) {
        return true;
    }

    private RotaryThread rotary_thread_;
}
