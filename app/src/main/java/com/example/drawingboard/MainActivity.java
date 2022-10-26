package com.example.drawingboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout set_Linear = findViewById(R.id.set_Linear);
//        LinearLayout color_Linear = findViewById(R.id.color_Linear);

        ImageButton saveBtn = findViewById(R.id.save_Btn);
        ImageButton resetBtn = findViewById(R.id.reset_Btn);
        ImageButton increaseBtn = findViewById(R.id.increase_Btn);
        ImageButton decreaseBtn = findViewById(R.id.decrease_Btn);
        Button colorBtn = findViewById(R.id.color_Btn);
        Button blackBtn = findViewById(R.id.black_Btn);
        Button whiteBtn = findViewById(R.id.white_Btn);
        Button redBtn = findViewById(R.id.red_Btn);
        Button orangeBtn = findViewById(R.id.orange_Btn);
        Button yellowBtn = findViewById(R.id.yellow_Btn);
        Button greenBtn = findViewById(R.id.green_Btn);
        Button blueBtn = findViewById(R.id.blue_Btn);
        Button purpleBtn = findViewById(R.id.purple_Btn);
        Button brownBtn = findViewById(R.id.brown_Btn);
        Button apricotBtn = findViewById(R.id.apricot_Btn);

        DrawerLayout drawerLayout = findViewById(R.id.drawerLayout);
        View drawerView = findViewById(R.id.color_drawer);

        SeekBar seekBar = findViewById(R.id.radius_Seekbar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                // 시크바 조작 중
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // 시크바 조작 시작 시
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // 시크바 조작 끝났을 시
            }
        });

        View.OnClickListener Click = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getId() == R.id.color_Btn) {
//                    set_Linear.setVisibility(View.GONE);
//                    color_Linear.setVisibility(View.VISIBLE);

                    drawerLayout.openDrawer(drawerView);

                } else {
                    if (view.getId() == R.id.save_Btn) {


                    } else if (view.getId() == R.id.reset_Btn) {
                        DrawBoard.dataNum = 0;
                    } else if (view.getId() == R.id.increase_Btn) {
                        if (DrawBoard.radius < 29) {
                            DrawBoard.radius += 2;
                        }
                    } else if (view.getId() == R.id.decrease_Btn) {
                        if (DrawBoard.radius > 3) {
                            DrawBoard.radius -= 2;
                        }
                    } else if (view.getId() == R.id.black_Btn) {
                        DrawBoard.whatColor = 0;
                        colorBtn.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.black)));
                    } else if (view.getId() == R.id.white_Btn) {
                        DrawBoard.whatColor = 1;
                        colorBtn.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));
                    } else if (view.getId() == R.id.red_Btn) {
                        DrawBoard.whatColor = 2;
                        colorBtn.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.red)));
                    } else if (view.getId() == R.id.orange_Btn) {
                        DrawBoard.whatColor = 3;
                        colorBtn.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.orange)));
                    } else if (view.getId() == R.id.yellow_Btn) {
                        DrawBoard.whatColor = 4;
                        colorBtn.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.yellow)));
                    } else if (view.getId() == R.id.green_Btn) {
                        DrawBoard.whatColor = 5;
                        colorBtn.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
                    } else if (view.getId() == R.id.blue_Btn) {
                        DrawBoard.whatColor = 6;
                        colorBtn.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.blue)));
                    } else if (view.getId() == R.id.purple_Btn) {
                        DrawBoard.whatColor = 7;
                        colorBtn.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.purple)));
                    } else if (view.getId() == R.id.brown_Btn) {
                        DrawBoard.whatColor = 8;
                        colorBtn.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.brown)));
                    } else if (view.getId() == R.id.apricot_Btn) {
                        DrawBoard.whatColor = 9;
                        colorBtn.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.apricot)));
                    }

                    drawerLayout.closeDrawer(drawerView);
//                set_Linear.setVisibility(View.VISIBLE);
//                color_Linear.setVisibility(View.GONE);

                }

            }
        };

        saveBtn.setOnClickListener(Click);
        resetBtn.setOnClickListener(Click);
        increaseBtn.setOnClickListener(Click);
        decreaseBtn.setOnClickListener(Click);
        colorBtn.setOnClickListener(Click);
        blackBtn.setOnClickListener(Click);
        whiteBtn.setOnClickListener(Click);
        redBtn.setOnClickListener(Click);
        orangeBtn.setOnClickListener(Click);
        yellowBtn.setOnClickListener(Click);
        greenBtn.setOnClickListener(Click);
        blueBtn.setOnClickListener(Click);
        purpleBtn.setOnClickListener(Click);
        brownBtn.setOnClickListener(Click);
        apricotBtn.setOnClickListener(Click);

    }
}