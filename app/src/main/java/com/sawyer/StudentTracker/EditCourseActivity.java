package com.sawyer.StudentTracker;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class EditCourseActivity extends AppCompatActivity {
    public static final String LOG_TAG = "EditCourseAct";
    Button deleteCourseButton;
    Button saveCourseButton;
    Button applyAlarmButton;
    FullDatabase db;
    EditText courseTitleEditText;
    EditText courseStatusEditText;
    EditText courseStartDate;
    EditText courseEndDate;
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
        applyAlarmButton = findViewById(R.id.applyCourseAlarmButton);
        //------------ End Attach Views

        updateViews();
        //------- Apply Alarm Button
        applyAlarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(LOG_TAG, "applyAlarmButton pressed");

                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.DAY_OF_YEAR, -1);
                Date alarmDate1 = calendar.getTime();
                Date alarmDate2 = calendar.getTime();
                try {
                    alarmDate1 = formatter.parse(courseStartDate.getText().toString());
                    alarmDate2 = formatter.parse(courseEndDate.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                    Log.d(LOG_TAG, "alarmDate not set properly");
                }
                setAlarm(alarmDate1);
                setAlarm(alarmDate2);

                saveData();
                finish();
            }
        });
        //------- End Apply Alarm Button
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
                saveData();
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

    private void setAlarm(Date dateProvided) {
        Calendar calendarNow = Calendar.getInstance();
        if (dateProvided.getTime() > calendarNow.getTime().getTime()) {
            Log.d(LOG_TAG, "Date Provided: " + formatter.format(dateProvided));
            Intent intent = new Intent(getApplicationContext(), ReminderBroadcast.class);
            intent.putExtra("message", "Alarm Set for: " + formatter.format(dateProvided));
            intent.putExtra("title", "Alert");
            PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, 0);
            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC, dateProvided.getTime(), pendingIntent);
            Toast.makeText(getApplicationContext(),"Alarm Set for: " + formatter.format(dateProvided),Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(),"Date: " + formatter.format(dateProvided) + " not in the future",Toast.LENGTH_SHORT).show();
        }
    }

    private void saveData(){
        //---Update selectedCourse with EditText data, then Update in Database
        selectedCourse.setCourse_status(courseStatusEditText.getText().toString());
        selectedCourse.setCourse_name(courseTitleEditText.getText().toString());
        try {
            selectedCourse.setCourse_start(formatter.parse(courseStartDate.getText().toString()));
            selectedCourse.setCourse_end(formatter.parse(courseEndDate.getText().toString()));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        db.courseDao().updateCourse(selectedCourse);
        //---End Update
    }
}
