package com.ma.rab.timerapp;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    long val=0;
    boolean isStart=true;
    CountDownTimer timer;

    public void Start(View view){
        final TextView txt = findViewById(R.id.txtTime);
        final SeekBar slider = findViewById(R.id.slider);
        final Button btn = findViewById(R.id.btnStart);

        if (isStart)
        {
            btn.setText("Stop");
            slider.setEnabled(false);
            isStart = false;
            //timer.start();

            timer= new CountDownTimer(val,1000){
                public void onTick(long untilDone)
                {
                    long progress = untilDone/1000;
                    if (progress<10)
                    {
                        txt.setText("00:0"+String.valueOf(progress));
                    }
                    else
                        txt.setText("00:"+String.valueOf(progress));

                }

                public void onFinish()
                {
                    btn.setText("Start");
                    isStart = true;
                    slider.setEnabled(true);
                    slider.setProgress(30);
                    final MediaPlayer alarm = MediaPlayer.create(getApplicationContext(),R.raw.bell);
                    alarm.start();
                    new Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            alarm.stop();
                        }
                    }, 3500);
                }
            }.start();


        }
        else
        {
            btn.setText("Start");
            isStart = true;
            slider.setEnabled(true);
            timer.cancel();
            slider.setProgress(30);

        }



    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView txt = findViewById(R.id.txtTime);

        SeekBar slider = findViewById(R.id.slider);
        slider.setMax(60);

        slider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (progress<10)
                {
                    txt.setText("00:0"+String.valueOf(progress));
                }
                else
                    txt.setText("00:"+String.valueOf(progress));

                if (progress>=60){
                    txt.setText("01:00");
                }
                val = progress*1000;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


    }
}
