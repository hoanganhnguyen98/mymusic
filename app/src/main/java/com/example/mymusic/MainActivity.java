package com.example.mymusic;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {
    MediaPlayer player;
    private ImageButton button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        player = MediaPlayer.create(this, R.raw.sakura);
        button = findViewById(R.id.imageButton11);
    }

    public void play(View v) {
        if(player.isPlaying()){
            player.pause();
            button.setImageResource(R.drawable.pause);
        } else {
            player.start();
            button.setImageResource(R.drawable.play);
        }
    }

    public void stop(View v) {
        if (player != null) {
            player.release();
            player = null;
        }
    }
}
