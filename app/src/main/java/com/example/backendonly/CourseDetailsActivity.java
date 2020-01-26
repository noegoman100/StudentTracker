package com.example.backendonly;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

public class CourseDetailsActivity extends AppCompatActivity {
    EditText courseTitle;
    int termId;
    int courseId;
    FullDatabase db;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);
        setTitle("Course Details Activity");
        db = FullDatabase.getInstance(getApplicationContext());
        intent = getIntent();
        termId = intent.getIntExtra("termId", -1);
        System.out.println("received termId: " + termId);
        courseId = intent.getIntExtra("courseId", -1);
        System.out.println("received courseId: " + courseId);
        courseTitle = findViewById(R.id.courseTitleEditText);
        Course selectedCourse = db.courseDao().getCourse(termId, courseId);
        courseTitle.setText(selectedCourse.getCourse_name());
    }
}
