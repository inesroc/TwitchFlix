package com.example.twitchflix.VideoOnDemand;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.twitchflix.R;

public class Categories extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
    }
    public void onClick(View v) {
        Intent intent = new Intent(Categories.this, SelectMovies.class);
        switch(v.getId()) {
            case R.id.animation:
                intent.putExtra("category","Animation");
                break;
            case R.id.comedy:
                intent.putExtra("category","Comedy");
                break;
            case R.id.horror:
                intent.putExtra("category","Horror");
                break;
            case R.id.lego:
                intent.putExtra("category","LEGO");
                break;
            case R.id.scifi:
                intent.putExtra("category","Sci-fi");
                break;
        }
        startActivity(intent);
    }

}
