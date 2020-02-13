package com.example.backendonly;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;


public class EditCourseActivity extends AppCompatActivity {
    public static final String LOG_TAG = "EditCourseAct";
    Button deleteCourseButton;
    Button saveCourseButton;
    FullDatabase db;
    EditText courseTitleEditText;
    EditText courseStatusEditText;
    EditText courseStartDate;
    EditText courseEndDate;
    CheckBox setAlarmsCheckBox;
    int termId;
    int courseId;
    Intent intent;
    Course selectedCourse;
    SimpleDateFormat formatter;

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
        formatter = new SimpleDateFormat(getString(R.string.date_pattern));
        //------------ Attach Views
        deleteCourseButton = findViewById(R.id.deleteCourseButton);
        saveCourseButton = findViewById(R.id.saveCourseButton);
        courseTitleEditText = findViewById(R.id.courseTitleTextView);
        courseStatusEditText = findViewById(R.id.courseStatusTextView);
        courseStartDate = findViewById(R.id.courseStartDate);
        courseEndDate = findViewById(R.id.courseEndDate);
        setAlarmsCheckBox = findViewById(R.id.setAlarmsCheckBox);
        //------------ End Attach Views

        updateViews();

        //----------- Delete Course Button
        deleteCourseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.courseDao().deleteCourse(selectedCourse);
                //finish();
                Intent intent = new Intent(getApplicationContext(), TermDetailsActivity.class);
                intent.putExtra("termId", selectedCourse.getTerm_id_fk());
                startActivity(intent);
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
                try {
                    selectedCourse.setCourse_start(formatter.parse(courseStartDate.getText().toString()));
                    selectedCourse.setCourse_end(formatter.parse(courseEndDate.getText().toString()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                //------SetAlarms
                if(setAlarmsCheckBox.isChecked()) {
                    setAlarm(selectedCourse.getCourse_start(), "Start Date");
                    setAlarm(selectedCourse.getCourse_end(), "End Date");
                }
                //------End SetAlarms

                db.courseDao().updateCourse(selectedCourse);
                //---End Update
                finish();
            }
        });
        //-------- End Save Course Button

    }

    private void updateViews(){
        //-------Update Views
        selectedCourse = db.courseDao().getCourse(termId, courseId);
        if (selectedCourse != null) {
            courseTitleEditText.setText(selectedCourse.getCourse_name());
            courseStatusEditText.setText(selectedCourse.getCourse_status());
            courseStartDate.setText(formatter.format(selectedCourse.getCourse_start()));
            courseEndDate.setText(formatter.format(selectedCourse.getCourse_end()));
        } else {System.out.println("selectedCourse is null");}
        //-------End Update Views
    }

    private void setAlarm(Date dateProvided, String name) {
        if (dateProvided.compareTo(Date.from(Instant.now())) > 0) {
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(getApplicationContext(), CourseDetailsActivity.class);
            intent.putExtra("termId", termId);
            intent.putExtra("courseId", courseId);
            //intent.putExtra("taskId", taskId);
            PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);
            alarmManager.set(AlarmManager.RTC, dateProvided.getTime(), pendingIntent);
            Log.d(LOG_TAG, "The alarm was set");
        } else {
            Toast.makeText(getApplicationContext(),name + " is not in the Future.",Toast.LENGTH_SHORT).show();
        }
    }
}
