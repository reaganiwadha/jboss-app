package com.melun.jboss;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;


import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class MainActivity extends AppCompatActivity {
    private String giturl = "https://api.github.com/orgs/JBossOutreach/repos";
    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;
    private ProgressBar pg;
    List<Object> list = new ArrayList<>();


    @Override
    public void onStart(){
        super.onStart();
        recyclerView = findViewById(R.id.recyclerview);
        adapter = new RecyclerViewAdapter(getApplicationContext(), list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(giturl).get().build();
        pg = findViewById(R.id.pg);

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                JSONArray repos = null;
                try{
                    repos = new JSONArray(json);
                    for(int y=0; y<repos.length(); y++){
                        list.add(new getterHome(
                                repos.getJSONObject(y).getString("name"),
                                repos.getJSONObject(y).getString("language"),
                                repos.getJSONObject(y).getString("description") == "null" ? "No description" : repos.getJSONObject(y).getString("description"),
                                repos.getJSONObject(y).getString("stargazers_count"),
                                repos.getJSONObject(y).getString("watchers_count")));
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter.notifyDataSetChanged();
                            pg.setVisibility(View.GONE);
                        }
                    });
                }
                catch(JSONException e){
                    e.printStackTrace();
                }
            }
        });





    }

}
