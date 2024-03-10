package abled.oscar.drawingboard;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.core.content.ContextCompat;

public class DrawBoard extends View {

    String TAG = "DrawBoard";

    Paint p1 = new Paint();
    Paint p2 = new Paint();
    Paint p3 = new Paint();
    Paint p4 = new Paint();
    Paint p5 = new Paint();
    Paint p6 = new Paint();
    Paint p7 = new Paint();
    Paint p8 = new Paint();
    Paint p9 = new Paint();
    Paint p10 = new Paint();

    int[] data_X = new int[30000];
    int[] data_Y = new int[30000];
    int[] data_Color = new int[30000];
    int[] data_Radius = new int[30000];

    static int radius = 15;
    static int dataNum = 0;
    int mx, my;
    static int whatColor = 0;

    public DrawBoard(Context context, AttributeSet attr) {
        super(context);

        p1.setColor(ContextCompat.getColor(context, R.color.black));
        p2.setColor(ContextCompat.getColor(context, R.color.white));
        p3.setColor(ContextCompat.getColor(context, R.color.red));
        p4.setColor(ContextCompat.getColor(context, R.color.orange));
        p5.setColor(ContextCompat.getColor(context, R.color.yellow));
        p6.setColor(ContextCompat.getColor(context, R.color.green));
        p7.setColor(ContextCompat.getColor(context, R.color.blue));
        p8.setColor(ContextCompat.getColor(context, R.color.purple));
        p9.setColor(ContextCompat.getColor(context, R.color.brown));
        p10.setColor(ContextCompat.getColor(context, R.color.apricot));

        data_X[0] = 0;
        data_Y[0] = 0;
        data_Color[0] = 0;

    }


    @Override
    public void onDraw(Canvas canvas) {

        for (int i=1; i <= dataNum; i++) {

            if (data_Color[i] == 0) {
                canvas.drawCircle(data_X[i], data_Y[i], data_Radius[i], p1);
            }

            if (data_Color[i] == 1) {
                canvas.drawCircle(data_X[i], data_Y[i], data_Radius[i], p2);
            }

            if (data_Color[i] == 2) {
                canvas.drawCircle(data_X[i], data_Y[i], data_Radius[i], p3);
            }

            if (data_Color[i] == 3) {
                canvas.drawCircle(data_X[i], data_Y[i], data_Radius[i], p4);
            }

            if (data_Color[i] == 4) {
                canvas.drawCircle(data_X[i], data_Y[i], data_Radius[i], p5);
            }

            if (data_Color[i] == 5) {
                canvas.drawCircle(data_X[i], data_Y[i], data_Radius[i], p6);
            }

            if (data_Color[i] == 6) {
                canvas.drawCircle(data_X[i], data_Y[i], data_Radius[i], p7);
            }

            if (data_Color[i] == 7) {
                canvas.drawCircle(data_X[i], data_Y[i], data_Radius[i], p8);
            }

            if (data_Color[i] == 8) {
                canvas.drawCircle(data_X[i], data_Y[i], data_Radius[i], p9);
            }

            if (data_Color[i] == 9) {
                canvas.drawCircle(data_X[i], data_Y[i], data_Radius[i], p10);
            }

        }

        invalidate();

    } // onDraw()


    /*
    사용자 터치에 의해 그려진 데이터 저장
     */
    public void saveData() {

        data_X[dataNum] = mx;
        data_Y[dataNum] = my;
        data_Color[dataNum] = whatColor;
        data_Radius[dataNum] = radius;

    } // saveData()


    /*
    터치 이벤트 발생 시 좌표 · 터치 입력 총 값 저장
     */
    public boolean onTouchEvent (MotionEvent event) {

        mx = (int) event.getX();
        my = (int) event.getY();

        dataNum +=1;
        saveData();
        Log.d(TAG, "onTouchEvent: " + dataNum + " / mx : " + mx + " / my : " + my + " / radius : " + radius + " / color : " + whatColor);

        return true;

    } // onTouchEvent()


}
