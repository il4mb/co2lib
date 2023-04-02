package com.il4mb.co2lib;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.il4mb.co2.Btn;
import com.il4mb.co2.Co2Dialog;

public class MainActivity extends AppCompatActivity {

    Btn btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        btn = findViewById(R.id.action_btn);

        btn.setOnClickListener( v -> {

            Co2Dialog dialog = Co2Dialog.newInstance();
            dialog.getLayout(layout -> {

                TextView view = new TextView(getApplicationContext());
                view.setText("lorem");
                view.setTextColor(Color.WHITE);
                layout.getBody().addView(view);
            });
            dialog.setOnCLickPositive(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getApplicationContext(), "OK", Toast.LENGTH_LONG).show();
                }
            });
            dialog.show(getSupportFragmentManager(), Co2Dialog.TAG);

           // System.out.println(dialog.getTitle());
            //dialog.show(getSupportFragmentManager(), "null");
            //dialog.show(getSupportFragmentManager(), "");

        });
    }
}