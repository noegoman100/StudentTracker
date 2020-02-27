package com.sawyer.StudentTracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CourseNotesActivity extends AppCompatActivity {
    public static final String LOG_TAG = "CourseNotesAct";
    EditText notesEditText;
    EditText smsPhoneEditText;
    Button saveNotesButton;
    Button sendSMSButton;
    FullDatabase db;
    Course selectedCourse;
    Intent intent;
    int termId;
    int courseId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_notes);
        setTitle("Course Notes");
        db = FullDatabase.getInstance(getApplicationContext());
        intent = getIntent();
        termId = intent.getIntExtra("termId", -1);
        courseId = intent.getIntExtra("courseId", -1);
        selectedCourse = db.courseDao().getCourse(termId, courseId);

        //-----------Attach Views
        saveNotesButton = findViewById(R.id.saveNotesButton);
        sendSMSButton = findViewById(R.id.smsButton);
        notesEditText = findViewById(R.id.notesEditText);
        sendSMSButton = findViewById(R.id.smsButton);
        smsPhoneEditText = findViewById(R.id.smsPhoneEditText);
        //-----------End Attach Views

        updateViews();

        //-----------Save Button
        saveNotesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedCourse.setCourse_notes(notesEditText.getText().toString());
                db.courseDao().updateCourse(selectedCourse);
                Log.d(LOG_TAG, "Save button clicked");
                finish();
            }
        });
        //-----------End Save Button

        //--------SMS send Button
        sendSMSButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(LOG_TAG, "sms Button clicked with number: " + smsPhoneEditText.getText().toString());
                sendSmsBySIntent(smsPhoneEditText.getText().toString(), notesEditText.getText().toString());
                Log.d(LOG_TAG, "Completed SMS Button OnClickListener");
            }
        });
        Log.d(LOG_TAG, "SMS send button clicked");
        //--------End SMS send Button
    }

    private void updateViews() {
        notesEditText.setText(selectedCourse.getCourse_notes());
    }

    public void sendSmsBySIntent(String phoneNumber, String smsBody) {
        // add the phone number in the data
        Uri uri = Uri.parse("smsto:" + phoneNumber);

        Intent smsSIntent = new Intent(Intent.ACTION_SENDTO, uri);
        // add the message at the sms_body extra field
        smsSIntent.putExtra("sms_body", smsBody);
        try{
            startActivity(smsSIntent);
        } catch (Exception ex) {
            //Toast.makeText(CourseDetailsActivity.this, "Your sms has failed...", Toast.LENGTH_LONG).show();
            ex.printStackTrace();
        }
    }


}
