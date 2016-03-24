package com.example.m14x.demosound;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private MediaPlayer mplayer;
    private AudioManager audioManager;


    public void playAudio(View view){

        mplayer.start();
    }

    public void pauseAudio(View view){

        mplayer.pause();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mplayer = MediaPlayer.create(this, R.raw.switches);

        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE); //gettin the audio system services

        int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC); //Maximum volume
        int currVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC); //Current Volume


        SeekBar seekBar = (SeekBar) findViewById(R.id.seekBar);
        seekBar.setMax(maxVolume);
        seekBar.setProgress(currVolume);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.i("Volume Value", Integer.toString(progress));
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

         final SeekBar songControl = (SeekBar) findViewById(R.id.songControl);
        songControl.setMax(mplayer.getDuration());

        new Timer().scheduleAtFixedRate(new TimerTask() { //Asynctask
            @Override
            public void run() {
               //0 --> execute inmediately - 1000 --> very 1 second or according to your file
                songControl.setProgress(mplayer.getCurrentPosition());
            }
        },0,100);

        songControl.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.i("Song-Control Value", Integer.toString(progress));
                mplayer.seekTo(progress);
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
