package com.melun.jboss;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

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

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                Log.d("JSON",json);
            }
        });

        list.add(new getterHome("Test","Test","Test","Test","Test"));


    }

}
