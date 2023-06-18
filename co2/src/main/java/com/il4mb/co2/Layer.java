package com.il4mb.co2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.il4mb.co2.util.OnSwipeTouchListener;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Layer extends DialogFragment {

    private static boolean readyToShow = true;
    public LinearLayout.LayoutParams layoutParams;
    public ConstraintLayout layout, wrapper;
    public LinearLayout container;
    public LinearLayout topElement;
    public TextView title;
    public Context context;

    public Layer() {}

    public Layer(Context context) {

        this.context = context;
        layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        wrapper = new ConstraintLayout(context);
        wrapper.setLayoutParams(layoutParams);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            wrapper.setBackgroundColor(Color.argb(0.5f, 0, 0, 0));
        }

        layout = new ConstraintLayout(context);
        layout.setLayoutParams(layoutParams);
        layout.setBackgroundColor(Color.WHITE);
        customView(layout, Color.WHITE, Color.WHITE);

        title = new TextView(context);
        title.setText("Judul Layer");
        title.setTextSize(22f);
        title.setTypeface(Typeface.DEFAULT_BOLD);
        title.setPadding(0, 0, 0, 25);

        topElement = new LinearLayout(context);
        topElement.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 250));

        ImageView imageView = new ImageView(context);
        imageView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 25, Gravity.CENTER));
        drawLineInImageView(imageView);

        topElement.addView(imageView);

        container = new LinearLayout(context);
        container.setLayoutParams(layoutParams);
        container.setPadding(25, 50, 25, 0);
        container.setOrientation(LinearLayout.VERTICAL);


        layout.addView(topElement);
        layout.addView(container);
        wrapper.addView(layout);
        container.addView(title);
    }

    public static void customView(View v, int backgroundColor, int borderColor) {
        GradientDrawable shape = new GradientDrawable();
        shape.setShape(GradientDrawable.RECTANGLE);
        shape.setCornerRadii(new float[]{10, 10, 10, 10, 0, 0, 0, 0});
        shape.setColor(backgroundColor);
        shape.setStroke(3, borderColor);
        v.setBackground(shape);
    }

    public LinearLayout getContainer() {
        return this.container;
    }

    public TextView getTitle() {
        return this.title;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return this.wrapper;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, androidx.appcompat.R.style.Theme_AppCompat_NoActionBar);
    }

    @Override
    public void onStart() {
        super.onStart();

        // safety check
        if (getDialog() == null) {
            return;
        }

        setStyle(DialogFragment.STYLE_NO_TITLE, androidx.appcompat.R.style.Theme_AppCompat_Light_NoActionBar);
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        getDialog().getWindow().setWindowAnimations(R.style.dialog_animation_fade);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getDialog().getWindow().setNavigationBarColor(Color.WHITE);


        DisplayMetrics displayMetrics = new DisplayMetrics();
        getDialog().getWindow().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;

        layout.setMaxHeight(height - 200);
        layout.setY(height - layout.getMaxHeight());

        Animation aniSlide = AnimationUtils.loadAnimation(context, R.anim.slide_up);
        layout.startAnimation(aniSlide);

        topElement.setOnTouchListener(new OnSwipeTouchListener(this.context) {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                // System.out.println(event.getRawY());
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                    case 2:
                        gestureToDown(event);
                        break;
                    default:
                        clearCurrentGesture(event);
                }
                return super.onTouch(v, event);
            }
        });
    }


    public boolean is_ready() {
        return readyToShow;
    }

    @Override
    public void show(@NonNull FragmentManager manager, @Nullable String tag) {
        super.show(manager, tag);

        if (!is_ready()) {
            this.dismiss();
        }

        readyToShow = false;
        final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                readyToShow = true;
            }
        }, 0, 500, TimeUnit.MILLISECONDS);
    }


    private void drawLineInImageView(ImageView imageView) {

        Bitmap bitmap = Bitmap.createBitmap(600, 65, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        Paint paint = new Paint();
        paint.setColor(Color.argb(100, 61, 158, 255));
        paint.setStrokeWidth(5f);

        canvas.drawColor(Color.TRANSPARENT);
        canvas.drawLine(100, 20, 500, 20, paint);
        canvas.drawLine(0, 50, 600, 50, paint);

        imageView.setImageDrawable(new BitmapDrawable(this.context.getResources(), bitmap));
    }

    private void clearCurrentGesture(MotionEvent event) {

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getDialog().getWindow().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;

        if(event.getRawY() -100 > (height/2.2)) {

            this.dismiss();
            return;
        }

        layout.setY(height - layout.getMaxHeight());
        this.layout.getBackground().setTint(Color.WHITE);
    }

    private void gestureToDown(MotionEvent event) {

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getDialog().getWindow().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        float rawY = event.getRawY() -100;

        layout.setY(rawY);
        if(rawY < 200) {
            clearCurrentGesture(event);
        }
        if(rawY > (height/2.2)) {

            this.layout.getBackground().setTint(Color.parseColor("#AAAAAA"));
        } else {
            this.layout.getBackground().setTint(Color.WHITE);
        }

    }

    public Context getSupportContext() {
        return this.context;
    }
}
