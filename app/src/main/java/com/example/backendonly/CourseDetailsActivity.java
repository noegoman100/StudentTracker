package com.example.backendonly;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class CourseDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);
        setTitle("Course Details Activity");
    }
}
