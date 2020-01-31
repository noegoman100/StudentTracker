package com.example.backendonly;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

public class EditCourseActivity extends AppCompatActivity {
    Button deleteCourseButton;
    Button saveCourseButton;
    FullDatabase db;
    EditText courseTitleEditText;
    EditText courseStatusEditText;
    EditText courseStartDate;
    EditText courseEndDate;
    int termId;
    int courseId;
    Intent intent;
    Course selectedCourse;
    SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm a");

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
        courseStartDate = findViewById(R.id.courseStartDate);
        courseEndDate = findViewById(R.id.courseEndDate);
        //------------ End Attach Views
        //-------Update Views
        if (selectedCourse != null) {
            courseTitleEditText.setText(selectedCourse.getCourse_name());
            courseStatusEditText.setText(selectedCourse.getCourse_status());
            //todo
            //courseStartDate.setText(formatter.format(selectedCourse.getCourse_start()));
            //courseEndDate.setText(formatter.format(selectedCourse.getCourse_end()));
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
        //-------- Save Course Button
        saveCourseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //---Update selectedCourse with EditText data, then Update in Database
                selectedCourse.setCourse_status(courseStatusEditText.getText().toString());
                selectedCourse.setCourse_name(courseTitleEditText.getText().toString());
                selectedCourse.setCourse_start(Date.from(Instant.now())); //todo get data from EditText
                selectedCourse.setCourse_end(Date.from(Instant.now())); //todo get data from EditText

                db.courseDao().updateCourse(selectedCourse);
                //---End Update
                finish();
            }
        });
        //-------- End Save Course Button

    }
}
