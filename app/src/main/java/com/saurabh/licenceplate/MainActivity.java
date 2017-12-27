package com.saurabh.licenceplate;

import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {


    JSONObject jsonObject = null;
    JSONArray jsonArray = null;
    private List<Model> modeldata = new ArrayList<>();


    RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager layoutManager;

    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swaptor);

        mAdapter = new AdapterRecycler(MainActivity.this, modeldata);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        recyclerView.setAdapter(mAdapter);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                new GetData().execute();
            }
        });

    }

    class GetData extends AsyncTask<Void,Void,Void>{

        Response response;
        @Override
        protected Void doInBackground(Void... voids) {
            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url("http://192.168.43.225/data.json")
                    .get()
                    .addHeader("cache-control", "no-cache")
                    .build();


            try {
                response = client.newCall(request).execute();
                jsonObject = new JSONObject( response.body().string());

                if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e){
                e.printStackTrace();
            }


            if (jsonObject != null) {
                try {
                    // Getting JSON Array node

                    modeldata.clear();
                    jsonArray = jsonObject.getJSONArray("person");

                    for (int i = 0; i < jsonArray .length(); i++) {

                        JSONObject c = jsonArray.getJSONObject(i);

                        Model item = new Model();

                        item.setName(c.getString("name"));
                        item.setNumber(c.getString("phone"));

                        modeldata.add(item);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Log.e("ServiceHandler", "Couldn't get any data from the url");
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if(swipeRefreshLayout.isRefreshing())
            {
                swipeRefreshLayout.setRefreshing(false);
            }
            mAdapter.notifyDataSetChanged();

        }
    }
}
