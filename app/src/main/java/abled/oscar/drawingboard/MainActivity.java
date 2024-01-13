package abled.oscar.drawingboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.loader.content.CursorLoader;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.media.Image;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.normal.TedPermission;

import java.io.File;

import java.text.SimpleDateFormat;
import java.util.List;

import github.nisrulz.screenshott.ScreenShott;

public class MainActivity extends AppCompatActivity {

    String TAG = "메인액티비티";
    String FILENAME = "yyyy-MM-dd-HH-mm-ss-SSS";

    FrameLayout frameLayout;
    LinearLayout seekbarLayout, mainLayout, resultLayout;
    DrawerLayout drawerLayout;
    View drawerView;
    SeekBar seekBar;

    ImageButton screenshotBtn, resetBtn, radiusBtn, saveBtn, shareBtn;
    Button colorBtn, blackBtn, whiteBtn, redBtn, orangeBtn, yellowBtn, greenBtn, blueBtn, purpleBtn, brownBtn, apricotBtn;
    TextView seekbar_Text;
    ImageView resultImage, backBtn;

    File file;
    String filePath;
    Bitmap bitmap;
    String fileName;
    boolean saveStatus = false;
    AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setVariable();
        setView();

    } // onCreate()


    @Override
    public void onBackPressed() {

        if (resultLayout.getVisibility() == View.VISIBLE) {

            resultLayout.setVisibility(View.GONE);
            mainLayout.setVisibility(View.VISIBLE);

        } else {

            finish();

        }

    }


    public void setVariable() {

        frameLayout = findViewById(R.id.boardLayout);
        mainLayout = findViewById(R.id.mainLayout);
        resultLayout = findViewById(R.id.resultLayout);
        seekbarLayout = findViewById(R.id.seekbar_Layout);
        drawerLayout = findViewById(R.id.drawerLayout);
        drawerView = findViewById(R.id.color_drawer);
        seekBar = findViewById(R.id.radius_Seekbar);

        screenshotBtn = findViewById(R.id.screenshot_Btn);
        resetBtn = findViewById(R.id.reset_Btn);
        radiusBtn = findViewById(R.id.radius_Btn);
        colorBtn = findViewById(R.id.color_Btn);
        blackBtn = findViewById(R.id.black_Btn);
        whiteBtn = findViewById(R.id.white_Btn);
        redBtn = findViewById(R.id.red_Btn);
        orangeBtn = findViewById(R.id.orange_Btn);
        yellowBtn = findViewById(R.id.yellow_Btn);
        greenBtn = findViewById(R.id.green_Btn);
        blueBtn = findViewById(R.id.blue_Btn);
        purpleBtn = findViewById(R.id.purple_Btn);
        brownBtn = findViewById(R.id.brown_Btn);
        apricotBtn = findViewById(R.id.apricot_Btn);
        seekbar_Text = findViewById(R.id.seekbar_Text);
        resultImage = findViewById(R.id.resultImageView);
        saveBtn = findViewById(R.id.saveButton);
        shareBtn = findViewById(R.id.shareButton);
        backBtn = findViewById(R.id.resultBackButton);

        MobileAds.initialize(MainActivity.this);
        adView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

    } // setVariable()


    public void setView() {

        // 시크바
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                // 시크바 조작 감지 시
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
                seekbarLayout.setVisibility(View.GONE);
            }
        });


        // View 클릭 리스너
        View.OnClickListener Click = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getId() == R.id.color_Btn) {
                    drawerLayout.openDrawer(drawerView);
                } else if (view.getId() == R.id.radius_Btn) {
                    seekbarLayout.setVisibility(View.VISIBLE);
                } else if (view.getId() == R.id.saveButton) {
                    saveImage();
                } else if (view.getId() == R.id.shareButton) {
                    sharedImage();
                } else if (view.getId() == R.id.resultBackButton) {
                    resultLayout.setVisibility(View.GONE);
                    mainLayout.setVisibility(View.VISIBLE);
                } else {
                    if (view.getId() == R.id.screenshot_Btn) {
                        takeScreenShot();
                    } else if (view.getId() == R.id.reset_Btn) {
                        DrawBoard.dataNum = 0;
                    } else if (view.getId() == R.id.black_Btn) {
                        DrawBoard.whatColor = 0;
                        colorBtn.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(MainActivity.this, R.color.black)));
                    } else if (view.getId() == R.id.white_Btn) {
                        DrawBoard.whatColor = 1;
                        colorBtn.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(MainActivity.this, R.color.white)));
                    } else if (view.getId() == R.id.red_Btn) {
                        DrawBoard.whatColor = 2;
                        colorBtn.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(MainActivity.this, R.color.red)));
                    } else if (view.getId() == R.id.orange_Btn) {
                        DrawBoard.whatColor = 3;
                        colorBtn.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(MainActivity.this, R.color.orange)));
                    } else if (view.getId() == R.id.yellow_Btn) {
                        DrawBoard.whatColor = 4;
                        colorBtn.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(MainActivity.this, R.color.yellow)));
                    } else if (view.getId() == R.id.green_Btn) {
                        DrawBoard.whatColor = 5;
                        colorBtn.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(MainActivity.this, R.color.green)));
                    } else if (view.getId() == R.id.blue_Btn) {
                        DrawBoard.whatColor = 6;
                        colorBtn.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(MainActivity.this, R.color.blue)));
                    } else if (view.getId() == R.id.purple_Btn) {
                        DrawBoard.whatColor = 7;
                        colorBtn.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(MainActivity.this, R.color.purple)));
                    } else if (view.getId() == R.id.brown_Btn) {
                        DrawBoard.whatColor = 8;
                        colorBtn.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(MainActivity.this, R.color.brown)));
                    } else if (view.getId() == R.id.apricot_Btn) {
                        DrawBoard.whatColor = 9;
                        colorBtn.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(MainActivity.this, R.color.apricot)));
                    }

                    seekbarLayout.setVisibility(View.GONE);
                    drawerLayout.closeDrawer(drawerView);

                }

            }
        };

        screenshotBtn.setOnClickListener(Click);
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
        saveBtn.setOnClickListener(Click);
        shareBtn.setOnClickListener(Click);
        backBtn.setOnClickListener(Click);

    } // setView()


    public void takeScreenShot() {

        TedPermission.Builder tedPermission = TedPermission.create()
                .setPermissionListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted() {

                        SimpleDateFormat format = new SimpleDateFormat(FILENAME);
                        fileName = format.format(System.currentTimeMillis());

                        bitmap = ScreenShott.getInstance().takeScreenShotOfView(frameLayout);

                        Glide.with(MainActivity.this)
                                .load(bitmap)
                                .into(resultImage);

                        mainLayout.setVisibility(View.GONE);
                        resultLayout.setVisibility(View.VISIBLE);

                        saveStatus = false;

                    }

                    @Override
                    public void onPermissionDenied(List<String> deniedPermissions) {
                        Toast.makeText(MainActivity.this, "권한을 허용해 주셔야 사용 가능합니다.", Toast.LENGTH_SHORT).show();
                    }
                })
                .setDeniedMessage("[설정]으로 이동하셔서 권한을 허용해주세요.");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {

            tedPermission.setPermissions(Manifest.permission.READ_MEDIA_IMAGES).check();

        } else {

            tedPermission.setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE).check();

        }

    } // takeScreenShot()


    public void saveImage() {

        if (!saveStatus) {

            try {
                file = ScreenShott.getInstance().saveScreenshotToPicturesFolder(MainActivity.this, bitmap, fileName);
                filePath = file.getAbsolutePath();

                Toast.makeText(MainActivity.this, "갤러리에 저장되었습니다.", Toast.LENGTH_SHORT).show();

            } catch (Exception e) {
                Toast.makeText(MainActivity.this, "알 수 없는 오류로 저장이 실패했습니다.", Toast.LENGTH_SHORT).show();
                throw new RuntimeException(e);
            }

            saveStatus = true;

        }

    } // saveImage()


    public void sharedImage() {

        if (!saveStatus) {

            try {
                file = ScreenShott.getInstance().saveScreenshotToPicturesFolder(MainActivity.this, bitmap, fileName);
                filePath = file.getAbsolutePath();

            } catch (Exception e) {
                Toast.makeText(MainActivity.this, "알 수 없는 오류로 저장에 실패했습니다.", Toast.LENGTH_SHORT).show();
                throw new RuntimeException(e);
            }

            saveStatus = true;

        }

        Uri fileUri = FileProvider.getUriForFile(MainActivity.this, "abled.oscar.drawingboard.provider", file);

        Intent i = new Intent();
        i.setAction(Intent.ACTION_SEND);
        i.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        i.putExtra(Intent.EXTRA_STREAM, fileUri);
        i.setDataAndType(fileUri, "image/jpg");
        startActivity(Intent.createChooser(i, "공유하기"));

    } // sharedImage()



}