package com.example.drawingboard;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class DrawBoard extends View {

    String TAG = "터치 이벤트";

    Paint p1 = new Paint();
    Paint p2 = new Paint();
    Paint p3 = new Paint();
    Paint p4 = new Paint();
    Paint p5 = new Paint();

    int data_X[] = new int[30000];
    int data_Y[] = new int[30000];
    int data_Color[] = new int[30000];

    static int radius = 15;

    int dataNum = 0;
    int mx, my;
    static int whatColor = 0;

    public DrawBoard(Context context, AttributeSet attr) {
        super(context);

        p1.setColor(Color.BLACK);
        p2.setColor(Color.RED);
        p3.setColor(Color.BLUE);
        p4.setColor(Color.GREEN);
        p5.setColor(Color.YELLOW);

        data_X[0] = 0;
        data_Y[0] = 0;
        data_Color[0] = 0;

    }


    @Override
    public void onDraw(Canvas canvas) {

        for (int i=1; i <= dataNum; i++) {

            if (data_Color[i] == 0) {
                canvas.drawCircle(data_X[i], data_Y[i], radius, p1);
            }

            if (data_Color[i] == 1) {
                canvas.drawCircle(data_X[i], data_Y[i], radius, p2);
            }

            if (data_Color[i] == 2) {
                canvas.drawCircle(data_X[i], data_Y[i], radius, p3);
            }

            if (data_Color[i] == 3) {
                canvas.drawCircle(data_X[i], data_Y[i], radius, p4);
            }

            if (data_Color[i] == 4) {
                canvas.drawCircle(data_X[i], data_Y[i], radius, p5);
            }

        }

        invalidate();

    }

    public void saveData() {

        data_X[dataNum] = mx;
        data_Y[dataNum] = my;
        data_Color[dataNum] = whatColor;

    }

    public boolean onTouchEvent (MotionEvent event) {

        mx = (int) event.getX();
        my = (int) event.getY();

        dataNum +=1;
        saveData();
        Log.d(TAG, "onTouchEvent: " + dataNum);
        Log.d(TAG, "mx: " + mx);
        Log.d(TAG, "my: " + my);
        return true;

    }



}
