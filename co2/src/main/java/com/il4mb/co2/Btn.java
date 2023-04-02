package com.il4mb.co2;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.il4mb.co2.util.Corners;

public class Btn extends androidx.appcompat.widget.AppCompatButton {

    Corners rounded;
    int Tcolor = Color.WHITE;

    public Btn(@NonNull Context context) {

        super(context);
        rounded = new Corners(this);

    }

    public Btn(@NonNull Context context, @Nullable AttributeSet attrs) {

        super(context, attrs);
        rounded = new Corners(this);
        assert attrs != null;
        initAttrs(attrs);

    }

    public Btn(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {

        super(context, attrs, defStyleAttr);
        rounded = new Corners(this);
        assert attrs != null;
        initAttrs(attrs);

    }

    public void initAttrs(AttributeSet attrs) {

        this.setPadding(25, 10, 25, 10);

        for (int i =0; i < attrs.getAttributeCount(); i++ ) {

            if(attrs.getAttributeValue(i) == null) {
                return;
            }
            int value = 0;
            try {
                value = Integer.parseInt(attrs.getAttributeValue(i));
            } catch (Exception e) {
                e.printStackTrace();
            }
            finally {
                switch (attrs.getAttributeName(i)) {
                    case "rounded":
                        rounded.setAll(value);
                        break;
                    case "rounded_left":
                        rounded.setLeft(value);
                        break;
                    case "rounded_right":
                        rounded.setRight(value);
                        break;
                    case "rounded_left_top":
                        rounded.setLeft_top(value);
                        break;
                    case "rounded_left_bottom":
                        rounded.setLeft_bottom(value);
                        break;
                    case "rounded_right_top":
                        rounded.setRight_top(value);
                        break;
                    case "rounded_right_bottom":
                        rounded.setRight_bottom(value);
                        break;
                    case "rounded_top":
                        rounded.setTop(value);
                        break;
                    case "rounded_bottom":
                        rounded.setBottom(value);
                }
            }
        }
    }
    public void setCorners(Corners corners) {
        this.rounded = corners;
    }
    public Corners getCorners() {
        return rounded;
    }

    public void setBackgroundColor(ColorStateList colors) {
        this.setBackgroundTintList(colors);
    }

    public void textColor(int color) {
        this.Tcolor = color;
        setTextColor(color);
        invalidate();
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(Color.GRAY);
        drawable.setCornerRadii(new float[] {
                rounded.left_top, rounded.left_top,
                rounded.right_top, rounded.right_top,
                rounded.right_bottom, rounded.right_bottom,
                rounded.left_bottom, rounded.left_bottom
        });

        //setTextColor(Tcolor);

        this.setBackground(drawable);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            this.animate()
                    .scaleYBy(0.1f)
                    .scaleXBy(0.1f)
                    .setDuration(250)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            Btn.this.setScaleX(1);
                            Btn.this.setScaleY(1);
                        }
                    }).start();
        }
        return super.onTouchEvent(event);
    }
}