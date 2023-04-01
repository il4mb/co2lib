package com.il4mb.co2.util;

import android.view.View;

public class Corners {
    int left;
    public int left_top;
    public int left_bottom;
    int top;
    int right;
    public int right_top;
    public int right_bottom;
    int bottom;
    View view;
    public Corners(View view) {
        this.view = view;
        setAll(0);
    }
    public void setAll(int xr) {
        this.left = xr;
        this.top = xr;
        this.right = xr;
        this.bottom = xr;
        this.left_top=xr;
        this.left_bottom=xr;
        this.right_top=xr;
        this.right_bottom=xr;
    }
    public void setLeft(int left) {
        this.left = left;
        view.invalidate();
        setLeft_top(left);
        setLeft_bottom(left);
    }
    public void setTop(int top) {
        this.top = top;
        setLeft_top(top);
        setRight_top(top);
        view.invalidate();
    }
    public void setRight(int right) {
        this.right = right;
        setRight_bottom(right);
        setRight_top(right);
        view.invalidate();
    }
    public void setBottom(int bottom) {
        this.bottom = bottom;
        setLeft_bottom(bottom);
        setRight_bottom(bottom);
        view.invalidate();
    }
    public void setLeft_top(int left_top) {
        this.left_top = left_top;
        view.invalidate();
    }
    public void setLeft_bottom(int left_bottom) {
        this.left_bottom = left_bottom;
        view.invalidate();
    }
    public void setRight_top(int right_top) {
        this.right_top = right_top;
        view.invalidate();
    }
    public void setRight_bottom(int right_bottom) {
        this.right_bottom = right_bottom;
        view.invalidate();
    }
}