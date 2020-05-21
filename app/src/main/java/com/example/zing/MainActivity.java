package com.example.zing;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    Button playBtn;
    TextView songTitle;
    ImageButton backBtn;
    ImageButton nextBtn;
    SeekBar positionBar;
    TextView elapsedTimeLabel;
    TextView remainingTimeLabel;
    MediaPlayer mp;
    int totalTime;
    int position;
    List<Song> songs = new ArrayList<>();

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    protected  void initional() {
    Song ovannhien = new Song("Ô Vân Nhiên - Hồ Ca", R.raw.o_van_nhien);
    Song dynasty = new Song("Dynasty", R.raw.dynasty);
    Song sakura = new Song("Sakura", R.raw.sakura);
setPosition(0);
    songs.add(ovannhien);
    songs.add(dynasty);
    songs.add(sakura);
}


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        songTitle = (TextView)  findViewById(R.id.titleSong);
        playBtn = (Button) findViewById(R.id.playBtn);
        backBtn = (ImageButton) findViewById(R.id.backButton);
        nextBtn = (ImageButton) findViewById(R.id.nextButton);
        elapsedTimeLabel = (TextView) findViewById(R.id.elapsedTimeLabel);
        remainingTimeLabel = (TextView) findViewById(R.id.remainingTimeLabel);

        initional();

        // Media Player
        mp = MediaPlayer.create(this, songs.get(0).getFileId());
        songTitle.setText(songs.get(0).getSongName());
        mp.setLooping(true);
        mp.seekTo(0);
        mp.setVolume(0.5f, 0.5f);
        totalTime = mp.getDuration();

        // Position Bar
        positionBar = (SeekBar) findViewById(R.id.positionBar);
        positionBar.setMax(totalTime);
        positionBar.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        if (fromUser) {
                            mp.seekTo(progress);
                            positionBar.setProgress(progress);
                        }
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                }
        );


        // Thread (Update positionBar & timeLabel)
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (mp != null) {
                    try {
                        Message msg = new Message();
                        msg.what = mp.getCurrentPosition();
                        handler.sendMessage(msg);
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {}
                }
            }
        }).start();

    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int currentPosition = msg.what;
            // Update positionBar.
            positionBar.setProgress(currentPosition);

            // Update Labels.
            String elapsedTime = createTimeLabel(currentPosition);
            elapsedTimeLabel.setText(elapsedTime);

            String remainingTime = createTimeLabel(totalTime-currentPosition);
            remainingTimeLabel.setText("- " + remainingTime);
        }
    };

    public String createTimeLabel(int time) {
        String timeLabel = "";
        int min = time / 1000 / 60;
        int sec = time / 1000 % 60;

        timeLabel = min + ":";
        if (sec < 10) timeLabel += "0";
        timeLabel += sec;

        return timeLabel;
    }

    public void playBtnClick(View view) {

        if (!mp.isPlaying()) {
            // Stopping
            mp.start();
            playBtn.setBackgroundResource(R.drawable.stop);

        } else {
            // Playing
            mp.pause();
            playBtn.setBackgroundResource(R.drawable.play);
        }
    }

    void changeSongByPosition(int position){
        mp.pause();
        mp = MediaPlayer.create(this, songs.get(position).getFileId());
        mp.start();
        songTitle.setText(songs.get(position).getSongName());
        setPosition(position);
    }

    public void handleBack(View view){
        if( this.position == 0){
            changeSongByPosition(this.songs.size()-1);
        }else {
         changeSongByPosition(this.position-1);
        }
    }
    public void handleNext(View view){
        if( this.position == this.songs.size() - 1){
            changeSongByPosition(0);
        }else {
            changeSongByPosition(this.position+1);
        }
    }
}