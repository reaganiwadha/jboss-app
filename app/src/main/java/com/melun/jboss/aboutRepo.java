package com.melun.jboss;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class aboutRepo extends AppCompatActivity {

    private RecyclerView recyclerView;
    private recyclerContributor adapter;
    List<Object> list = new ArrayList<>();

    @Override
    public void onStart(){
        super.onStart();
        recyclerView = findViewById(R.id.recyclerContributor);
        adapter = new recyclerContributor(getApplicationContext(), list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_repo);

        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("openrepo", Context.MODE_PRIVATE);
        String giturl = sharedPref.getString("link", "noUrl");

        final TextView name = findViewById(R.id.name);
        final TextView language = findViewById(R.id.language);
        final TextView description = findViewById(R.id.desc);
        final TextView stargazers = findViewById(R.id.stargazer);
        final TextView watchers = findViewById(R.id.watcher);
        final LinearLayout about = findViewById(R.id.aboutRepoTop);
        final ProgressBar pg = findViewById(R.id.pg);
        about.setVisibility(View.INVISIBLE);

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(giturl).get().build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String json = response.body().string();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject repos = new JSONObject(json);
                            name.setText(repos.getString("name"));
                            language.setText(repos.getString("language") == "null" ? "Unknown language" : repos.getString("language"));
                            description.setText(repos.getString("description") == "null" ? "No description" : repos.getString("description"));
                            stargazers.setText(repos.getString("stargazers_count"));
                            watchers.setText(repos.getString("watchers_count"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });

        Request requestContributors = new Request.Builder().url(giturl+"/contributors").get().build();

        client.newCall(requestContributors).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String json = response.body().string();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONArray repos = new JSONArray(json);
                            for(int y=0; y<repos.length(); y++) {
                                list.add(new getterContributor(repos.getJSONObject(y).getString("login")));
                            }
                            adapter.notifyDataSetChanged();
                            pg.setVisibility(View.GONE);
                            about.setVisibility(View.VISIBLE);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });

    }
}
