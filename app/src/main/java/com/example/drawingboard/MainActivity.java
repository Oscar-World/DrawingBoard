package com.example.drawingboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
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
    View v1;
//    FrameLayout drawBoard;


    // 저장소 사용 권한
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    /**
     * 앱에 장치 저장소에 쓸 수 있는 권한이 있는지 확인합니다
     * 앱에 권한이 없는 경우 사용자에게 권한을 부여하라는 메시지가 표시됩니다
     */
    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // 권한이 없을때는 사용자에게 확인 메시지를 표시합니다
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }



    // 파일 이름을 현재 시간으로 지정
    private String dateName(long dateTaken){
        Date date = new Date(dateTaken);
        SimpleDateFormat dateFormat =
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(date);
    }



    public void takeScreenshot(View view) {

        if (view == null) {
            Toast.makeText(this,"null",Toast.LENGTH_SHORT).show();
        } else
        view.setDrawingCacheEnabled(true);

        String filename = "/DCIM/Screenshots/" + System.currentTimeMillis() + ".JPEG";

        try {
            File file = new File(Environment.getExternalStorageDirectory() + filename);
            if(!file.exists()){
//                file.mkdirs();
                Toast.makeText(this, "폴더가 생성되었습니다.", Toast.LENGTH_SHORT).show();
            }


            view.buildDrawingCache();
            Bitmap bitmap = view.getDrawingCache();

//            drawBoard.buildDrawingCache();
//            Bitmap bitmap = drawBoard.getDrawingCache();


//            v1.setDrawingCacheEnabled(true);
//            Bitmap bitmap = Bitmap.createBitmap(v1.getDrawingCache());
//            v1.setDrawingCacheEnabled(false);



            FileOutputStream outputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                    Uri.parse("file://" + Environment.getExternalStorageDirectory()
                            + "/DCIM/Screenshots/")));


            outputStream.flush();
            outputStream.close();

            openScreenshot(file);

            Toast.makeText(getApplicationContext(),filename + "저장 완료", Toast.LENGTH_SHORT).show();

            view.setDrawingCacheEnabled(false);

        } catch (Throwable e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),"저장 실패", Toast.LENGTH_SHORT).show();
        }


    }

    private void openScreenshot(File imageFile) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
//        Uri uri = Uri.fromFile(imageFile); >> FileUriExposedException 에러로 인해 파일프로바이더 사용해야함.
        Uri uri = FileProvider.getUriForFile(getApplicationContext(),"com.example.drawingboard.provider",imageFile);
        Log.d(TAG, "Uri 데이터 : " + uri);
        intent.setDataAndType(uri, "image/*");
        startActivity(intent);
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FrameLayout seekbar_Frame = findViewById(R.id.seekbar_Frame);
        LinearLayout set_Linear = findViewById(R.id.set_Linear);


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

        View v1 = getWindow().getDecorView().getRootView();

//        v1 = findViewById(R.id.drawBoard);
//        drawBoard = findViewById(R.id.boardLayout);

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
                seekbar_Frame.setVisibility(View.GONE);
            }
        });

        View.OnClickListener Click = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getId() == R.id.color_Btn) {
                    drawerLayout.openDrawer(drawerView);
                }  else if (view.getId() == R.id.radius_Btn) {
                    seekbar_Frame.setVisibility(View.VISIBLE);
                } else {
                    if (view.getId() == R.id.save_Btn) {

                        verifyStoragePermissions(MainActivity.this);

//                        saveFile();

                        takeScreenshot(v1);

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

                    seekbar_Frame.setVisibility(View.GONE);
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