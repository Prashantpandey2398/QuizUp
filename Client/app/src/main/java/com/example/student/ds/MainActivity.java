package com.example.student.ds;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    TextView text1;
    private static final String FORMAT = "%02d:%02d:%02d";
    boolean clicked=false;
    private static String TimeStamp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text1 = (TextView) findViewById(R.id.textView1);
        TimeStamp = new java.util.Date().toString();
        String t = TimeStamp.split(" ")[3];
        String[] t2 = t.split(":");
        long hour = Long.parseLong(t2[0]);
        long minute = Long.parseLong(t2[1]);
        long second = Long.parseLong(t2[2]);
        long total = (((60*hour)+minute)*60)+second;
        long total1 = 60*60*24;
        String t1 = "11:35:00";
        String[] t3 = t1.split(":");
        long hour1 = Long.parseLong(t3[0]);
        long minute1 = Long.parseLong(t3[1]);
        long second1 = Long.parseLong(t3[2]);
        long total2 = (((60*hour1)+minute1)*60)+second1;
        long time=10;
        if(total>total2){
            time=total1-(total-total2);
        }
        else{
            time=total2-total;
        }
        time-=5;
        time*=1000;
        Log.e("abcd",Long.toString(time));
        Log.e("abcd",Long.toString(total));
        Log.e("abcd",Long.toString(total1));
        Log.e("abcd",Long.toString(total2));
        runcountdown(time);

    }
    public void runcountdown(long time){
        text1 = (TextView) findViewById(R.id.textView1);
        Log.e("abcd",Long.toString(time));
        new CountDownTimer(time, 1000) { // adjust the milli seconds here

            public void onTick(long millisUntilFinished) {

                text1.setText("" + String.format(FORMAT,
                        TimeUnit.MILLISECONDS.toHours(millisUntilFinished),
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(
                                TimeUnit.MILLISECONDS.toHours(millisUntilFinished)),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(
                                TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
            }

            @Override
            public void onFinish() {
                if (clicked){

                }
                else{
                    Intent intent = new Intent(MainActivity.this,Quiz.class);
                    startActivity(intent);
                    finish();
                }
            }
        }.start();
    }
}
