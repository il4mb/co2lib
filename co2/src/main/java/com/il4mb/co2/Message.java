package com.il4mb.co2;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;

import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public final class Message {

    public String title, text;
    private Theme theme;

    private Context context;
    private LinearLayout vlayout;
    private TextView vtitle, vtext;

    public Message(Context context) {

        this.context = context;

        ColorDrawable drawable = new ColorDrawable();
        drawable.setColor(Color.TRANSPARENT);
        vlayout = new LinearLayout(context);
        vlayout.setBackground(drawable);
        vlayout.setOrientation(LinearLayout.VERTICAL);
        vlayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        vlayout.setPadding(20, 15, 20, 15);

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
    public void setTheme(Message.Theme theme) {

        this.theme = theme;
    }

    public void messageDanger(String title, String text) {

        this.title = title;
        this.text = text;
        this.setTheme(Message.Theme.DANGER());
        this.show();
    }

    public void messagePrimary(String title, String text) {

        this.title = title;
        this.text = text;
        this.setTheme(Message.Theme.PRIMARY());
        this.show();
    }

    public void show() {

        if(this.theme == null) {
            this.theme = Theme.DEFAULT();
        }

        vtitle.setTextColor(this.theme.Title);
        vtext.setTextColor(this.theme.Text);

        ColorDrawable drawable = new ColorDrawable();
        drawable.setColor(this.theme.Background);
        vlayout.setBackground(drawable);

        if(this.title != null) {
            vtitle.setVisibility(View.VISIBLE);
            vtitle.setText(this.title);
            vlayout.addView(vtitle);
        }
        if(this.text != null) {
            vtext.setVisibility(View.VISIBLE);
            vtext.setText(this.text);
            vlayout.addView(vtext);
        }

        Toast toast = new Toast(context);
        toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL | Gravity.FILL_HORIZONTAL, 0, 25);

        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(vlayout);
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
    public static final class Theme {

        public int Title, Text, Background;
        public Theme(final int color) {

            int red = Color.red(color);
            int green = Color.green(color);
            int blue = Color.blue(color);

            Title = Color.argb(255, red, green, blue);
            Text = Color.argb(180, red, green, blue);

            // GENERATE BG COLOR
            int[] colors = {red, green, blue};
            List<Integer> list = Arrays.stream(colors).boxed().collect(Collectors.toList());
            int total = Arrays.stream(colors).sum();
            int max = Arrays.stream(colors).max().orElse(0);
            int maxI = list.indexOf(max);
            int average = (int) Arrays.stream(colors).average().orElse(255);

            if(total > 382) {
                // TEXT TERANG
                if(max < 150) {
                    for (int i = 0; i < colors.length; i++) {
                        colors[i] = 255 - colors[i];
                    }
                } else {
                    for (int i = 0; i < colors.length; i++) {

                        colors[i] = (int) (colors[i]/2.5);
                    }
                    //colors[maxI] = max;
                }
            }
            else {
                // TEXT GELAP
                if(max < 150) {
                    for (int i = 0; i < colors.length; i++) {
                        colors[i] = 255 - colors[i];
                    }
                } else {
                    for (int i = 0; i < colors.length; i++) {
                        if(max > 175) {
                            if(max > 250)
                                colors[i] = max - 15;
                            else
                                colors[i] = 255 - 15;
                        } else {
                            if (colors[i] < max) {
                                colors[i] = (255 - colors[i]);
                            }
                        }
                    }
                    if(max > 175)
                        colors[maxI] = max + (255 - max);
                }
            }

            this.Background = Color.rgb(colors[0], colors[1], colors[2]);
        }
        public static Theme DEFAULT(){
            return new Theme(Color.parseColor("#515151"));
        }
        public static Theme PRIMARY(){
            return new Theme(Color.parseColor("#0043DF"));
        }
        public static Theme WARNING(){
            return new Theme(Color.parseColor("#ffcb2e"));
        }
        public static Theme DANGER(){
            return new Theme(Color.parseColor("#ff2e51"));
        }
        public static Theme SUCCESS(){
            return new Theme(Color.parseColor("#2cbf47"));
        }
    }
}