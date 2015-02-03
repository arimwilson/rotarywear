package com.ariwilson.rotarywear;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;

public class RotaryView extends View {
    public RotaryView(Context ctx) {
        this(ctx, null);
    }

    public RotaryView(Context ctx, AttributeSet attrs) {
        this(ctx, attrs, 0);
    }

    public RotaryView(Context ctx, AttributeSet attrs, int defStyle) {
        super(ctx, attrs, defStyle);
        rotor_drawable_ = ctx.getResources().getDrawable(R.drawable.dialer);
    }

    private void fireDialListenerEvent(int number) {
      Log.i("RotaryWear", "fireDialListenerEvent " + Integer.toString(number));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int x = (getRight() - getLeft()) / 2;
        int y = (getBottom() - getTop()) / 2;

        canvas.save();
        rotor_drawable_.setBounds(0, 0, getWidth(), getHeight());
        if (rotor_angle_ != 0) {
          canvas.rotate(rotor_angle_, x, y);
        }
        rotor_drawable_.draw(canvas);
        canvas.restore();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        final float x0 = getWidth() / 2;
        final float y0 = getHeight() / 2;
        float x1 = event.getX();
        float y1 = event.getY();
        float x = x0 - x1;
        float y = y0 - y1;
        double r = Math.sqrt(x * x + y * y);
        double sinfi = y / r;
        double fi = Math.toDegrees(Math.asin(sinfi));

        if (x1 > x0 && y0 > y1) {
            fi = 180 - fi;
        } else if (x1 > x0 && y1 > y0) {
            fi = 180 - fi;
        } else if (x0 > x1 && y1 > y0) {
            fi += 360;
        }

        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                if (r > r1_ && r < r2_) {
                    rotor_angle_ += Math.abs(fi - last_fi_) + 0.25f;
                    rotor_angle_ %= 360;
                    last_fi_ = fi;
                    invalidate();
                    return true;
                }
            case MotionEvent.ACTION_DOWN:
                rotor_angle_ = 0;
                last_fi_ = fi;
                return true;
            case MotionEvent.ACTION_UP:
                final float angle = rotor_angle_ % 360;
                int number = Math.round(angle - 20) / 30;

                if (number > 0) {
                    if (number == 10) {
                        number = 0;
                    }
                    fireDialListenerEvent(number);
                }

                rotor_angle_ = 0;

                post(new Runnable() {
                    public void run() {
                        float fromDegrees = angle;
                        Animation anim = new RotateAnimation(
                                fromDegrees, 0, Animation.RELATIVE_TO_SELF, 0.5f,
                                Animation.RELATIVE_TO_SELF, 0.5f);
                        anim.setInterpolator(
                                AnimationUtils.loadInterpolator(
                                        getContext(), android.R.anim.decelerate_interpolator));
                        anim.setDuration((long) (angle * 5L));
                        startAnimation(anim);
                    }
                });
                return true;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }

    private final Drawable rotor_drawable_;
    private final int r1_ = 50;
    private final int r2_ = 160;

    private float rotor_angle_;
    private double last_fi_;
}
