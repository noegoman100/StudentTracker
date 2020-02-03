package com.example.backendonly;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditCoursementorActivity extends AppCompatActivity {
    public static final String LOG_TAG = "EditCourAct";
    int termId;
    int courseId;
    int coursementorId;
    Intent intent;
    EditText mentorNameEditText;
    EditText mentorPhoneEditText;
    EditText mentorEmailEditText;
    Button saveButton;
    FullDatabase db;
    Coursementor selectedCoursementor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_coursementor);
        setTitle("Edit Mentor");
        db = FullDatabase.getInstance(getApplicationContext());
        Log.d(LOG_TAG, "About to get intent getExtras");
        intent = getIntent();
        termId = intent.getIntExtra("termId", -1);
        courseId = intent.getIntExtra("courseId", -1);
        coursementorId = intent.getIntExtra("coursementorId", -1);
        Log.d(LOG_TAG, "Made it here. 0203 1121");

        //--------Attach Views
        mentorEmailEditText = findViewById(R.id.mentorEmailEditText);
        mentorNameEditText = findViewById(R.id.mentorNameEditText);
        mentorPhoneEditText = findViewById(R.id.mentorPhoneEditText);
        saveButton =  findViewById(R.id.saveButton);
        //--------End Attach Views
        //--------Save Button
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo Complete Me.
                selectedCoursementor.setMentor_name(mentorNameEditText.getText().toString());
                selectedCoursementor.setMentor_email(mentorEmailEditText.getText().toString());
                selectedCoursementor.setMentor_phone(mentorPhoneEditText.getText().toString());
                db.coursementorDao().updateCoursementor(selectedCoursementor);

                Log.d(LOG_TAG, "saveButton clicked.");
                finish();
            }
        });
        //--------End Save Button

        updateViews();
    }

    private void updateViews() {
        selectedCoursementor = db.coursementorDao().getCoursementor(courseId, coursementorId);
        mentorNameEditText.setText(selectedCoursementor.getMentor_name());
        mentorEmailEditText.setText(selectedCoursementor.getMentor_email());
        mentorPhoneEditText.setText(selectedCoursementor.getMentor_phone());

        Log.d(LOG_TAG, "Made it through updateViews inside EditCoursementorActivity");
    }
}
