package com.example.twitchflix.VideoOnDemand;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.twitchflix.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class SelectMovies extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
    private static String LOG_TAG = "CardViewActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_movies);

        setTitle("Movies");

        Intent intent = getIntent();
        String nameCategory = intent.getStringExtra("category");

        mRecyclerView = findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);

        new readUrl().execute("http://34.90.22.224:8080/resources",nameCategory);

    }

    public class readUrl extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {
            String result = "";

            try {
                URL url = new URL(params[0]);

                BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
                String line;

                while ((line = in.readLine()) != null) {
                    result+=line;
                }
                in.close();


            } catch (MalformedURLException e) {

                e.printStackTrace();
            } catch (IOException e) {

                e.printStackTrace();
            }
            result = params[1]+"--"+result;
            return result;
        }

        protected void onPreExecute() {

        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected void onPostExecute(String result) {

            String[] array = result.split("--");
            Log.i(LOG_TAG,result);
            String category = array[0];
            String data = array[1];


            try {

                mAdapter = new ViewAdapter(getDataSet(category, data));
                mRecyclerView.setAdapter(mAdapter);
                ((ViewAdapter) mAdapter).setOnItemClickListener(new ViewAdapter.MyClickListener() {
                    @Override
                    public void onItemClick(String url, View v) {
                        Log.i(LOG_TAG, " Clicked on Item " + url);
                        Intent intent = new Intent(SelectMovies.this, PlayMovie.class);
                        intent.putExtra("url", url);
                        startActivity(intent);
                    }
                });
                createButton(category,data);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }


    private ArrayList<MovieObject> getDataSet(String nameCategory, String data) throws JSONException {
        ArrayList results = new ArrayList<MovieObject>();
        JSONArray jsonArr = new JSONArray(data);
        int index = 0;

        for (int i = 0; i < jsonArr.length(); i++) {
            JSONObject obj = jsonArr.getJSONObject(i);
            final String url =  obj.getString("url");
            String year = obj.getString("year");
            String length = obj.getString("length");
            String title = obj.getString("name");

            JSONObject time = new JSONObject(length);
            String hour = time.getString("hours");
            String min = time.getString("minutes");
            String sec = time.getString("seconds");


            String category = obj.getString("tags");
            String[] tags = category.replace("[","").replace("]","").replace("\"","").split(",");
            for(int j=0; j<tags.length; j++){
                if (tags[j].equals(nameCategory)) {
                    String info = this.getResources().getString(R.string.year)+" " + year +"   "+ this.getResources().getString(R.string.duration)+": " + hour + "h " + min + "min " + sec + "s ";
                    MovieObject movie = new MovieObject(title,info,url);
                    results.add(index, movie);
                    index++;

                }
            }
        }

        return results;
    }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void createButton(String nameCategory, String data) throws JSONException {
      /*  JSONArray jsonArr = new JSONArray(data);
        String x = String.valueOf(jsonArr.length());

        // [{"url":"https://ia800302.us.archive.org/2/items/tl/tl_512kb.mp4","tags":["Animation","Comedy","LEGO"],"year":2003,"length":{"hours":0,"minutes":6,"seconds":30},"name":"The Letter","id":1},
        for (int i = 0; i < jsonArr.length(); i++) {
            JSONObject obj = jsonArr.getJSONObject(i);
            final String url =  obj.getString("url");
            String year = obj.getString("year");
            String length = obj.getString("length");
            String title = obj.getString("name");

            JSONObject time = new JSONObject(length);
            String hour = time.getString("hours");
            String min = time.getString("minutes");
            String sec = time.getString("seconds");


            String category = obj.getString("tags");
            String[] tags = category.replace("[","").replace("]","").replace("\"","").split(",");
            for(int j=0; j<tags.length; j++){

                if (tags[j].equals(nameCategory)) {
                    Button button = new Button(this);
                    String text = title + " \nYear: " + year + "    " + hour + ":" + min + ":" + sec;
                    button.setText(text);
                    button.setHeight(220);
                    button.setBackground(this.getResources().getDrawable(R.drawable.border_button));
                    button.setTextColor(getApplication().getResources().getColor(R.color.colorBackground));
                    button.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View view) {
                          //  Intent intent = new Intent(SelectVideos.this, PlayVideo.class);
                          //  intent.putExtra("url", url);
                          //  startActivity(intent);
                        }
                    });
                //    LinearLayout ll = findViewById(R.id.buttonLayout);
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                   //ll.addView(button, lp);
                }
            }
        }*/

    }



}
