package com.pistolshrimpstudio.jobreviews;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pistolshrimpstudio.jobreviews.activities.JobActivity;
import com.pistolshrimpstudio.jobreviews.custom.JobListAdapter;
import com.pistolshrimpstudio.jobreviews.custom.JobListItem;
import com.pistolshrimpstudio.jobreviews.custom.JobPosition;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static final String JSON_JOBS_URL = "https://jobs.github.com/positions.json?utf8=%E2%9C%93&description=java&location=";
    private ArrayList<JobPosition> jobPositions;
    private ArrayList<JobListItem> jobListItems;
    private JobListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);

        jobPositions = new ArrayList<>();
        jobListItems = new ArrayList<>();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new JobListAdapter(jobListItems);
        recyclerView.setAdapter(adapter);

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(downloadData());

        adapter.setOnItemTouchListener(new JobListAdapter.OnItemTouchListener() {
            @Override
            public void onItemTouch(View view, MotionEvent event, int position) {
                if(event.getAction() == MotionEvent.ACTION_UP) {
                    Intent intent = new Intent(view.getContext(), JobActivity.class);
                    intent.putExtra("title", jobPositions.get(position).getTitle());
                    intent.putExtra("type", jobPositions.get(position).getType());
                    intent.putExtra("url", jobPositions.get(position).getDescURL());
                    intent.putExtra("created_at", jobPositions.get(position).getPostDate());
                    intent.putExtra("company", jobPositions.get(position).getCompany());
                    intent.putExtra("location", jobPositions.get(position).getLocation());
                    intent.putExtra("company_url", jobPositions.get(position).getCompanyURL());
                    intent.putExtra("company_logo", jobPositions.get(position).getLogoUrl());
                    startActivity(intent);
                }
            }
        });
    }

    private Runnable downloadData() {
        return () -> {
            try {
                StringBuilder jsonData = new StringBuilder();
                URL url = new URL(JSON_JOBS_URL);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setReadTimeout(5000);
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String currentLine;
                while ((currentLine = bufferedReader.readLine()) != null) {
                    jsonData.append(currentLine);
                }
                bufferedReader.close();

                JSONArray jsonArray = new JSONArray(jsonData.toString());
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonPart = jsonArray.getJSONObject(i);
                    JobPosition jobPosition = new JobPosition();
                    jobPosition.setTitle(jsonPart.getString("title"));
                    jobPosition.setType(jsonPart.getString("type"));
                    jobPosition.setDescURL(jsonPart.getString("url"));
                    jobPosition.setPostDate(jsonPart.getString("created_at"));
                    jobPosition.setCompany(jsonPart.getString("company"));
                    jobPosition.setCompanyURL(jsonPart.getString("company_url"));
                    jobPosition.setLocation(jsonPart.getString("location"));
                    jobPosition.setLogoUrl(jsonPart.getString("company_logo"));
                    jobPositions.add(jobPosition);

                    JobListItem jobListItem = new JobListItem(jobPositions.get(i).getTitle(),
                           downloadLogo(jobPositions.get(i).getLogoUrl()));
                    jobListItems.add(jobListItem);

                    final int pos = i;
                    runOnUiThread(() -> adapter.notifyItemInserted(pos));
                }
            } catch (Exception e) {
                Log.i(TAG, e.toString());
            }
        };
    }

    private Bitmap downloadLogo(String logoUrl) {
        try {
            URL url = new URL(logoUrl);
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.connect();
            InputStream inputStream = httpURLConnection.getInputStream();
            return BitmapFactory.decodeStream(inputStream);
        } catch (IOException e) {
            return null;
        }
    }
}