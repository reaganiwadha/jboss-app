package com.melun.jboss;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class aboutRepo extends AppCompatActivity {

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
                            Log.d("WillItWorK?",repos.getJSONObject(0).getString("login"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });

    }
}
