package com.vinnstar.lab1_tryingit;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.vinnstar.lab1_tryingit.adapters.MovieAdapter;
import com.vinnstar.lab1_tryingit.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

public class MainActivity extends AppCompatActivity {

    public static final String NOW_PLAYING =  "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
    public static final String TAG = "MainActivity";

    public List<Movie> movies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView rvMovies = findViewById(R.id.rvMovies);
        movies = new ArrayList<>();
        //Create an adaptor
        final MovieAdapter movieAdapter = new MovieAdapter(this, movies);

        // Setup adpator on the recycler view
        rvMovies.setAdapter(movieAdapter);

        //Setup a layout manager on the recycler
        rvMovies.setLayoutManager(new LinearLayoutManager(this));


        AsyncHttpClient client = new AsyncHttpClient();
        client.get(NOW_PLAYING, new JsonHttpResponseHandler() {
           @ Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
               Log.d(TAG,"onSuccess");
               JSONObject jsonObject =  json.jsonObject;
               try {
                   JSONArray results = jsonObject.getJSONArray("results");
                   Log.i(TAG, "Result" + results.toString());
                   movies.addAll(Movie.fromJsonArray(results));
                   movieAdapter.notifyDataSetChanged();

                   Log.i(TAG, "Movies" + movies.size());
               } catch (JSONException e) {
                   Log.e(TAG, "hit JSON Exception",e);
                   e.printStackTrace();
               }
           }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.d(TAG,"onFailure");
            }
        });
    }
}
