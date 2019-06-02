package com.example.twitchflix;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import io.antmedia.android.liveVideoPlayer.LiveVideoPlayerActivity;

import android.os.Bundle;
import android.view.View;

import com.example.twitchflix.VideoOnDemand.Categories;

public class VideoStreaming extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_streaming);
    }


    public void VideoOnDemand(View view){
        Intent intent = new Intent(VideoStreaming.this, Categories.class);
        startActivity(intent);
    }

    public void VideoLive(View view){
        Intent intent = new Intent(VideoStreaming.this, LiveVideoPlayerActivity.class);
        startActivity(intent);
    }
}
