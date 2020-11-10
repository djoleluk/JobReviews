package com.pistolshrimpstudio.jobreviews;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.pistolshrimpstudio.jobreviews.custom.JobListAdapter;
import com.pistolshrimpstudio.jobreviews.custom.JobListItem;
import com.pistolshrimpstudio.jobreviews.custom.JobPosition;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static final String JSON_JOBS_URL = "https://jobs.github.com/positions.json?utf8=%E2%9C%93&description=java&location=";
    private ArrayList<JobPosition> jobPositions;
    private ArrayList<JobListItem> items;
    private JobListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}