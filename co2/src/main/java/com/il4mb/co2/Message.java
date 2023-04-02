package com.il4mb.co2;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;

import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.il4mb.co2.util.Co2Corners;
import com.il4mb.co2.util.Co2Drawable;
import com.il4mb.co2.util.Theme;

public final class Message {

    public String title, text;
    public int corners = 15;
    private Theme theme;

    private Context context;
    private LinearLayout vlayout;
    private FrameLayout vwrapper;
    private TextView vtitle, vtext;

    public Message(Context context) {

        this.context = context;

        vwrapper = new FrameLayout(context);
        vwrapper.setBackground(new ColorDrawable(Color.TRANSPARENT));
        vwrapper.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        vwrapper.setPadding(35, 0, 35, 0);

        vlayout = new LinearLayout(context);
        vlayout.setBackground(new Co2Drawable(Color.WHITE, new Co2Corners(corners)));
        vlayout.setOrientation(LinearLayout.VERTICAL);
        vlayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        vlayout.setPadding(25, 20, 25, 20);
        vlayout.setElevation(5f);

        vtitle = new TextView(context);
        vtitle.setTypeface(Typeface.DEFAULT_BOLD);
        vtitle.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        vtitle.setTextSize(18f);
        vtitle.setVisibility(View.GONE);

        vtext = new TextView(context);
        vtext.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        vtext.setTextSize(15f);
        vtext.setVisibility(View.GONE);

    }

    public void setTheme(Theme theme) {

        this.theme = theme;
    }

    public void messageDanger(String title, String text) {

        this.title = title;
        this.text = text;
        this.setTheme(Theme.DANGER());
        this.show();
    }

    public void messagePrimary(String title, String text) {

        this.title = title;
        this.text = text;
        this.setTheme(Theme.PRIMARY());
        this.show();
    }

    public void show() {

        if (this.theme == null) {
            this.theme = Theme.DEFAULT();
        }

        vtitle.setTextColor(this.theme.Title);
        vtext.setTextColor(this.theme.Text);

        Co2Drawable drawable = new Co2Drawable(Color.WHITE, new Co2Corners(corners));
        drawable.setTintList(ColorStateList.valueOf(this.theme.Background));
        vlayout.setBackground(drawable);

        if (this.title != null) {
            vtitle.setVisibility(View.VISIBLE);
            vtitle.setText(this.title);
            vlayout.addView(vtitle);
        }
        if (this.text != null) {
            vtext.setVisibility(View.VISIBLE);
            vtext.setText(this.text);
            vlayout.addView(vtext);
        }

        vwrapper.addView(vlayout);

        Toast toast = new Toast(context);
        toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL | Gravity.FILL_HORIZONTAL, 0, 25);

        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(vwrapper);
        toast.show();
    }


    public static class Builder {
        Message msg;

        public Builder(Context context) {
            msg = new Message(context);
        }

        public Builder setTitle(String title) {
            msg.title = title;
            return this;
        }

        public Builder setText(String text) {
            msg.text = text;
            return this;
        }

        public Builder setTheme(Theme theme) {
            msg.setTheme(theme);
            return this;
        }

        public void show() {
            msg.show();
        }
    }

}

