package com.example.drawingboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    String TAG = "메인";

    // 파일 이름을 현재 시간으로 지정
    private String dateName(long dateTaken){
        Date date = new Date(dateTaken);
        SimpleDateFormat dateFormat =
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(date);
    }

    public File ScreenShot(View view){
        view.setDrawingCacheEnabled(true); // 화면에 뿌릴 때 캐시를 사용

        Bitmap screenBitmap = view.getDrawingCache(); // 캐시를 비트맵으로 변환

        String filename = dateName(System.currentTimeMillis());
        Log.d(TAG, "filename: " + filename);

//        File file = new File(Environment.getExternalStorageDirectory()+"/DCIM/Screenshots", filename);
        File file = new File(Environment.getExternalStorageDirectory()+"/Pictures", filename);
        FileOutputStream os = null;
        try{
            Toast.makeText(getApplicationContext(),"저장 완료", Toast.LENGTH_SHORT).show();
            os = new FileOutputStream(file);
            screenBitmap.compress(Bitmap.CompressFormat.JPEG, 100, os); // 비트맵을 PNG파일로 변환
            os.close();
        }catch (IOException e){
            Toast.makeText(getApplicationContext(),"저장 실패", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
            return null;
        }

        view.setDrawingCacheEnabled(false);
        return file;
    }

    public void saveFile() {
        View view = getWindow().getDecorView();
//        View view = findViewById(R.id.drawBoard);

        File screenShot = ScreenShot(view);
        if (screenShot != null) {
            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(screenShot)));
            Log.d(TAG, "saveFile: 저장완료" + view);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout seekbar_Linear = findViewById(R.id.seekbar_Linear);

        ImageButton saveBtn = findViewById(R.id.save_Btn);
        ImageButton resetBtn = findViewById(R.id.reset_Btn);
        ImageButton radiusBtn = findViewById(R.id.radius_Btn);
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
        TextView seekbar_Text = findViewById(R.id.seekbar_Text);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                // 시크바 조작 중
                Log.d(TAG, "onStopTrackingTouch: " + seekBar.getProgress());
                DrawBoard.radius = seekBar.getProgress();
                seekbar_Text.setText(String.valueOf(DrawBoard.radius));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // 시크바 조작 시작 시
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // 시크바 조작 끝났을 시
//                Log.d(TAG, "onStopTrackingTouch: " + seekBar.getProgress());
//                DrawBoard.radius = seekBar.getProgress();
//                seekbar_Text.setText(String.valueOf(DrawBoard.radius));
            }
        });

        View.OnClickListener Click = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getId() == R.id.color_Btn) {
                    drawerLayout.openDrawer(drawerView);
                }  else if (view.getId() == R.id.radius_Btn) {
                    seekbar_Linear.setVisibility(View.VISIBLE);
                } else {
                    if (view.getId() == R.id.save_Btn) {


                        saveFile();


                    } else if (view.getId() == R.id.reset_Btn) {
                        DrawBoard.dataNum = 0;
                    }else if (view.getId() == R.id.black_Btn) {
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

                    seekbar_Linear.setVisibility(View.GONE);
                    drawerLayout.closeDrawer(drawerView);

                }

            }
        };

        saveBtn.setOnClickListener(Click);
        resetBtn.setOnClickListener(Click);
        radiusBtn.setOnClickListener(Click);
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