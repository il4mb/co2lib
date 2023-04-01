package com.il4mb.co2.util;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;

public class Co2Drawable extends GradientDrawable {

    int color;
    Co2Corners corners;
    public Co2Drawable(int color, Co2Corners corners) {
        this.color = color;
        this.corners = corners;
    }


    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        this.setColor(Color.RED);
        this.setCornerRadii(new float[] {
                corners.left_top, corners.left_top,
                corners.right_top, corners.right_top,
                corners.right_bottom, corners.right_bottom,
                corners.left_bottom, corners.left_bottom
        });

    }
}
