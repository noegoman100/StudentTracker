package com.example.backendonly;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditCourseActivity extends AppCompatActivity {
    Button deleteCourseButton;
    Button saveCourseButton;
    FullDatabase db;
    EditText courseTitleEditText;
    EditText courseStatusEditText;
    int termId;
    int courseId;
    Intent intent;
    Course selectedCourse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_course);
        setTitle("Edit Course");
        db = FullDatabase.getInstance(getApplicationContext());
        intent = getIntent();
        termId = intent.getIntExtra("termId", -1);
        System.out.println("Edit course activity received termId: " + termId);
        courseId = intent.getIntExtra("courseId", -1);
        System.out.println("Edit Course Activity received courseId: " + courseId);
        selectedCourse = db.courseDao().getCourse(termId, courseId);
        //------------ Attach Views
        deleteCourseButton = findViewById(R.id.deleteCourseButton);
        saveCourseButton = findViewById(R.id.saveCourseButton);
        courseTitleEditText = findViewById(R.id.courseTitleTextView);
        courseStatusEditText = findViewById(R.id.courseStatusTextView);
        //------------ End Attach Views
        //-------Update Views
        if (selectedCourse != null) {
            courseTitleEditText.setText(selectedCourse.getCourse_name());
            courseStatusEditText.setText(selectedCourse.getCourse_status());
        } else {System.out.println("selectedCourse is null");}
        //-------End Update Views

        //----------- Delete Course Button
        deleteCourseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.courseDao().deleteCourse(selectedCourse);
                finish();
            }
        });
        //----------- End Delete Course Button
    }
}
