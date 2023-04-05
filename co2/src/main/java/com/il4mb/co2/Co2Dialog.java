package com.il4mb.co2;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.il4mb.co2.util.Co2Corners;
import com.il4mb.co2.util.Co2Drawable;

public class Co2Dialog extends DialogFragment {

    public static String TAG = "Co2Dialog";
    private View.OnClickListener onCLickPositive, onClickNegative;

    public Co2Dialog() {}

    public static Co2Dialog newInstance() {

        return new Co2Dialog();
    }

    Ilayout ilayout;
    Layout layout;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        layout = new Layout(getContext());
        return layout;
    }

    boolean is_touch_in_layout = false;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        layout.setOnClickListener(v-> is_touch_in_layout = true);

        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getDialog().setCanceledOnTouchOutside(false);
        getDialog().getWindow().getDecorView().setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {


                if(! is_touch_in_layout) {

                    if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                        layout.animate()
                                .scaleYBy(0.05f)
                                .scaleXBy(0.05f)
                                .setDuration(100)
                                .setListener(new AnimatorListenerAdapter() {
                                    @Override
                                    public void onAnimationEnd(Animator animation) {
                                        layout.setScaleY(1);
                                        layout.setScaleX(1);
                                    }
                                });
                    }
                }
                is_touch_in_layout = false;
                return false;
            }
        });

        if(onClickNegative == null) {
            onClickNegative = v -> Co2Dialog.this.dismiss();
        }
        if(onCLickPositive == null) {
            onCLickPositive = v -> Co2Dialog.this.dismiss();
        }

        if(ilayout != null) {
            ilayout.onRender(layout);
        }

        layout.negativeBtn.setOnClickListener(onClickNegative);
        layout.positiveBtn.setOnClickListener(onCLickPositive);
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        onClickNegative = null;
        onCLickPositive = null;
    }

    public void getLayout(Ilayout ilayout) {
        this.ilayout = ilayout;
    }
    public void setOnCLickPositive(View.OnClickListener listener) {
        onCLickPositive = listener;
        if(layout != null) {
            layout.positiveBtn.setOnClickListener(onCLickPositive);
        }
    }
    public void setOnClickNegative(View.OnClickListener listener) {
        onClickNegative = listener;
        if(layout != null) {
            layout.negativeBtn.setOnClickListener(onClickNegative);
        }
    }

    public interface Ilayout {
        void onRender(Layout layout);
    }

    @Nullable
    @Override
    public Dialog getDialog() {
        return super.getDialog();
    }

    public static class Layout extends LinearLayout {
        public int corners = 12;
        private Btn positiveBtn, negativeBtn;
        LinearLayout layout, footer;
        ScrollView body;

        int bgColor;

        public Layout(Context context) {
            super(context);

            int nightModeFlags =
                    getContext().getResources().getConfiguration().uiMode &
                            Configuration.UI_MODE_NIGHT_MASK;
            switch (nightModeFlags) {

                case Configuration.UI_MODE_NIGHT_NO:
                    bgColor = Color.rgb(255, 255, 255);
                    break;

                default:
                    bgColor = Color.rgb(75, 75, 75);
            }

            ViewGroup.MarginLayoutParams layoutParams =
                    new ViewGroup.MarginLayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT);

            layoutParams.setMargins(15, 15, 15, 15);
            layout = new LinearLayout(context);
            layout.setOrientation(LinearLayout.VERTICAL);
            layout.setElevation(5);
            layout.setLayoutParams(layoutParams);
            layout.setMinimumWidth(600);
            layout.setMinimumHeight(200);

            layout.setBackground(new Co2Drawable(bgColor, new Co2Corners(corners)));

            body = new ScrollView(context);
            body.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0,1));
            body.setPadding(35, 35, 35, 5);

            footer = new LinearLayout(context);
            footer.setBackground(new Co2Drawable(Color.TRANSPARENT, new Co2Corners(0,0,corners,0,0,0,corners,0)));
            footer.setGravity(Gravity.RIGHT);

            layout.addView(body);
            layout.addView(footer);

            ViewGroup.MarginLayoutParams params = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(5, 15, 5, 15);

            ColorDrawable colorD = new ColorDrawable();
            colorD.setColor(Color.TRANSPARENT);

            negativeBtn = new Btn(context);
            negativeBtn.setText("Batal");
            negativeBtn.setPadding(25, 5, 25, 5);
            negativeBtn.rounded.setAll(5);
            negativeBtn.setTextColor(Color.parseColor("#ff4400"));
            negativeBtn.setBackgroundColor(ColorStateList.valueOf(Color.TRANSPARENT));
            negativeBtn.setLayoutParams(params);
            negativeBtn.setBackground(colorD);
            footer.addView(negativeBtn);

            params.setMargins(5, 15, 25, 15);
            positiveBtn = new Btn(context);
            positiveBtn.setText("Konfirmasi");
            positiveBtn.setPadding(25, 5, 25, 5);
            positiveBtn.rounded.setAll(5);
            positiveBtn.setBackgroundColor(ColorStateList.valueOf(Color.TRANSPARENT));
            positiveBtn.setTextColor(Color.parseColor("#47a0ff"));
            positiveBtn.setLayoutParams(params);
            positiveBtn.setBackground(colorD);
            footer.addView(positiveBtn);

            this.addView(layout);

        }
        public ScrollView getBody() {
            return body;
        }

        public LinearLayout getFooter() {
            return footer;
        }

        public Btn getPositiveBtn() {
            return positiveBtn;
        }

        public Btn getNegativeBtn() {
            return negativeBtn;
        }

    }
}
