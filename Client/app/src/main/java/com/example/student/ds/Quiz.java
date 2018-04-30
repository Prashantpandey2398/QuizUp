package com.example.student.ds;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Quiz extends AppCompatActivity {
    TextView text1;
    private static final String FORMAT = "%02d:%02d:%02d";
    boolean clicked=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        final CardView c1 = (CardView) findViewById(R.id.c1);
        final CardView c2 = (CardView) findViewById(R.id.c2);
        final CardView c3 = (CardView) findViewById(R.id.c3);
        final String[] TimeStamp = new String[1];
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                trysocket(TimeStamp,"Start");
            }});
        thread.start();
        c1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                trysocket(TimeStamp,"1");
                c1.setCardBackgroundColor(255);
            }
        });
        c2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                trysocket(TimeStamp,"2");
                c2.setCardBackgroundColor(255);
            }
        });
        c3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                trysocket(TimeStamp,"3");
                c3.setCardBackgroundColor(255);
            }
        });
    }
    public void runcountdown(){
        text1 = (TextView) findViewById(R.id.textView1);

        new CountDownTimer(15000, 1000) { // adjust the milli seconds here

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
            }
        }.start();
    }

    public void trysocket(final String[] TimeStamp, final String answer){
        new Thread(new Runnable(){
            public void run(){
                try{
                    Log.e("abcd","Error");
                    Socket[] connection = new Socket[1];
                    try {
                        connection[0] = new Socket(InetAddress.getByName("10.7.7.217"), 1500);
                        Log.e("abcd",connection[0].getInetAddress().toString());
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    OutputStreamWriter osw = new OutputStreamWriter(new BufferedOutputStream(connection[0].getOutputStream()), "US-ASCII");
                    TimeStamp[0] = new Date().toString();
                    osw.write(answer + ("Calling the Socket Server on 10.7.7.217 port 1500 at " + TimeStamp[0] + '\r'));
                    osw.flush();
                    InputStream servermessage = null;
                    try {
                        servermessage = connection[0].getInputStream();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    final DataInputStream in = new DataInputStream(servermessage);
                    final String s = in.readUTF().toString();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            String s1=s;
                            String s2 [] = new String[6];
                            s2 = s1.split("\n");
                            if (s2[0].equals("You won")){
                                Intent i = new Intent(Quiz.this,Over.class);
                                startActivity(i);
                                finish();
                            }
                            else if (s2[0].equals("You lost")){
                                Intent i = new Intent(Quiz.this,MainActivity.class);
                                startActivity(i);
                                finish();
                            }
                            else{
                                if (s2[0].equals("Your answer is incorrect")){
                                    Toast.makeText(Quiz.this, "You are already eliminated", Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    ((TextView)Quiz.this.findViewById(R.id.q12)).setText(s2[1]);
                                    ((TextView)Quiz.this.findViewById(R.id.o1)).setText(s2[2]);
                                    ((TextView)Quiz.this.findViewById(R.id.o2)).setText(s2[3]);
                                    ((TextView)Quiz.this.findViewById(R.id.o3)).setText(s2[4]);
                                    ((TextView)Quiz.this.findViewById(R.id.noq)).setText("Ques - "+s2[5]);
                                    Toast.makeText(Quiz.this,s2[0],Toast.LENGTH_LONG).show();
                                }
                                runcountdown();
                            }
                        }
                    });
                }
                catch(IOException e){
                                            }
            }
        }).start();
    }
}
