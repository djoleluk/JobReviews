package com.pistolshrimpstudio.jobreviews.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.pistolshrimpstudio.jobreviews.R;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class JobActivity extends AppCompatActivity {
    private static final String TAG = "JobActivity";
    private ImageView imageView;
    private String jobLogo;
    private String jobDescUrl;
    private String jobCompanyUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job);

        imageView = findViewById(R.id.logo_image_view);
        TextView title = findViewById(R.id.job_title);
        TextView type = findViewById(R.id.job_type);
        TextView location = findViewById(R.id.job_location);
        TextView company = findViewById(R.id.job_company);
        TextView postDate = findViewById(R.id.job_post_date);

        Intent intent = getIntent();
        String jobTitle = intent.getStringExtra("title");
        String jobType = intent.getStringExtra("type");
        String jobLocation = intent.getStringExtra("location");
        String jobCompany = intent.getStringExtra("company");
        String jobPostDate = intent.getStringExtra("post_date");
        jobLogo = intent.getStringExtra("logoUrl");
        jobDescUrl = intent.getStringExtra("jobDescUrl");
        jobCompanyUrl = intent.getStringExtra("jobCompanyUrl");

        title.setText(jobTitle);
        type.setText(jobType);
        location.setText(jobLocation);
        company.setText(jobCompany);
        postDate.setText(jobPostDate);

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(downloadAndShowLogo());
    }

    private Runnable downloadAndShowLogo() {
        return () -> {
            try {
                URL url = new URL(jobLogo);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                runOnUiThread(() -> imageView.setImageBitmap(bitmap));
            } catch (Exception e) {
                Log.i(TAG, e.toString());
                runOnUiThread(() -> imageView.setImageResource(R.drawable.it));
            }
        };
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public void openJobDescription(View view) {
        Uri uri = Uri.parse(jobDescUrl);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    public void openCompanyWebsite(View view) {
        Uri uri = Uri.parse(jobCompanyUrl);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }
}