package com.il4mb.co2.util;

import android.graphics.Color;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Theme {
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

        if (total > 382) {
            // TEXT TERANG
            if (max < 150) {
                for (int i = 0; i < colors.length; i++) {
                    colors[i] = 255 - colors[i];
                }
            } else {
                for (int i = 0; i < colors.length; i++) {

                    colors[i] = (int) (colors[i] / 2.5);
                }
                //colors[maxI] = max;
            }
        } else {
            // TEXT GELAP
            if (max < 150) {
                for (int i = 0; i < colors.length; i++) {
                    colors[i] = 255 - colors[i];
                }
            } else {
                for (int i = 0; i < colors.length; i++) {
                    if (max > 175) {
                        if (max > 250)
                            colors[i] = max - 15;
                        else
                            colors[i] = 255 - 15;
                    } else {
                        if (colors[i] < max) {
                            colors[i] = (255 - colors[i]);
                        }
                    }
                }
                if (max > 175)
                    colors[maxI] = max + (255 - max);
            }
        }

        this.Background = Color.rgb(colors[0], colors[1], colors[2]);
    }

    public static Theme DEFAULT() {
        return new Theme(Color.parseColor("#515151"));
    }

    public static Theme PRIMARY() {
        return new Theme(Color.parseColor("#0043DF"));
    }

    public static Theme WARNING() {
        return new Theme(Color.parseColor("#ffcb2e"));
    }

    public static Theme DANGER() {
        return new Theme(Color.parseColor("#ff2e51"));
    }

    public static Theme SUCCESS() {
        return new Theme(Color.parseColor("#2cbf47"));
    }
}
