package com.il4mb.co2.util;

import android.view.View;

public class Co2Corners {
    int left;
    public int left_top;
    public int left_bottom;
    int top;
    int right;
    public int right_top;
    public int right_bottom;
    int bottom;
    public Co2Corners(int all) {
        setAll(all);
    }
    public Co2Corners(int left, int top, int right, int bottom) {
        setLeft(left);
        setTop(top);
        setRight(right);
        setBottom(bottom);
    }
    public Co2Corners(int left,
                      int left_top,
                      int left_bottom,
                      int top,
                      int right,
                      int right_top,
                      int right_bottom,
                      int bottom) {
        setLeft(left);
        setTop(top);
        setRight(right);
        setBottom(bottom);

        setLeft_top(left_top);
        setLeft_bottom(left_bottom);
        setRight_top(right_top);
        setRight_bottom(right_bottom);


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
        setLeft_top(left);
        setLeft_bottom(left);
    }
    public void setTop(int top) {
        this.top = top;
        setLeft_top(top);
        setRight_top(top);
    }
    public void setRight(int right) {
        this.right = right;
        setRight_bottom(right);
        setRight_top(right);
    }
    public void setBottom(int bottom) {
        this.bottom = bottom;
        setLeft_bottom(bottom);
        setRight_bottom(bottom);
    }
    public void setLeft_top(int left_top) {
        this.left_top = left_top;
    }
    public void setLeft_bottom(int left_bottom) {
        this.left_bottom = left_bottom;
    }
    public void setRight_top(int right_top) {
        this.right_top = right_top;
    }
    public void setRight_bottom(int right_bottom) {
        this.right_bottom = right_bottom;
    }
}