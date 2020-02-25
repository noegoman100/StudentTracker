package com.sawyer.StudentTracker;

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
import java.util.Calendar;
import java.util.Date;

public class EditAssessmentActivity extends AppCompatActivity {
    public static final String LOG_TAG = "EditAssessmentAct";
    EditText assessmentTypeEditText;
    EditText assessmentTitleEditText;
    EditText assessmentDueDateEditText;
    EditText assessmentInfoEditText;
    EditText alertTitleEditText;
    EditText alertStartDateEditText;
    //EditText alertEndDateEditText;
    CheckBox setAlertCheckBox;
    FullDatabase db;
    Button saveAssessmentButton;
    Button deleteAssessmentButton;
    int termId;
    int courseId;
    int assessmentId;
    Intent intentReceived;
    Assessment selectedAssessment = new Assessment();
    SimpleDateFormat formatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_assessment);
        setTitle("Edit Assessment");
        formatter = new SimpleDateFormat(getString(R.string.date_pattern));
        //----Attach Views to Fields
        assessmentTypeEditText = findViewById(R.id.assessmentTypeEditText);
        saveAssessmentButton = findViewById(R.id.saveAssessmentButton);
        deleteAssessmentButton = findViewById(R.id.deleteAssessmentButton);
        assessmentTitleEditText = findViewById(R.id.assessmentTitileEditText);
        assessmentDueDateEditText = findViewById(R.id.assessmentDueDateEditText);
        assessmentInfoEditText = findViewById(R.id.assessmentInfoEditText);
        setAlertCheckBox = findViewById(R.id.setAlertCheckBox);
        alertTitleEditText = findViewById(R.id.alertTitleEditText);
        alertStartDateEditText = findViewById(R.id.alertStartDate);
        //alertEndDateEditText = findViewById(R.id.alertEndDate);
        //----End Attach Views to Fields
        updateViews();
        //------- Delete Assessment Button
            deleteAssessmentButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    db.assessmentDao().deleteAssessment(selectedAssessment);
                    finish();
                }
            });
        //------- End Delete Assessment Button
        //--------- Save Assessment Button
        saveAssessmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(EditAssessmentActivity.LOG_TAG, "Save Assessment Button Pressed");
                selectedAssessment.setAssessment_type(assessmentTypeEditText.getText().toString());
                selectedAssessment.setAssessment_name(assessmentTitleEditText.getText().toString());
                selectedAssessment.setAssessment_info(assessmentInfoEditText.getText().toString());
                //selectedAssessment.setAssessment_set_alert((setAlertCheckBox.isChecked())?1:0);  //Set to One if Checked. 0 if not checked.
                selectedAssessment.setAssessment_alert_name(alertTitleEditText.getText().toString());

                try {
                    selectedAssessment.setAssessment_alert_date(formatter.parse(alertStartDateEditText.getText().toString()));
                    selectedAssessment.setAssessment_due(formatter.parse(assessmentDueDateEditText.getText().toString()));
                    Log.d(LOG_TAG, "updated alert date");
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                //---------------Set the Alarm(s)
                if(setAlertCheckBox.isChecked()) {
                    setAlarm(selectedAssessment.getAssessment_alert_date());
                }
                //---------------End Set the Alarm(s)
                db.assessmentDao().updateAssessment(selectedAssessment);

                finish();
            }
        });
        //--------- End Save Assessment Button


    }

    private void setAlarm(Date dateProvided) {
        if (dateProvided.compareTo(Calendar.getInstance().getTime()) > 0) {
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(getApplicationContext(), EditAssessmentActivity.class);
            intent.putExtra("termId", termId);
            intent.putExtra("courseId", courseId);
            intent.putExtra("assessmentId", assessmentId);
            PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);
            alarmManager.set(AlarmManager.RTC, dateProvided.getTime(), pendingIntent);
            Log.d(LOG_TAG, "The alarm was set");
        } else {
            Toast.makeText(getApplicationContext(),"Alarm is not in the Future.",Toast.LENGTH_SHORT).show();
        }
    }

    private void updateViews() {
        //---- Update Views


        db = FullDatabase.getInstance(getApplicationContext());

        intentReceived = getIntent();
        termId = intentReceived.getIntExtra("termId", -1);
        courseId = intentReceived.getIntExtra("courseId", -1);
        assessmentId = intentReceived.getIntExtra("assessmentId", -1);
        try {
            selectedAssessment = db.assessmentDao().getAssessment(courseId,assessmentId);
            if(selectedAssessment != null) {
                System.out.println("selectedAssessment Not Null");
                System.out.println("EditAssessment termId: " + termId);
                System.out.println("Edit Assessment courseId: " + courseId);
                System.out.println("Edit Assessment assessmentId: " + assessmentId);
                String assessmentType = selectedAssessment.getAssessment_type();
                System.out.println("Assessment Type: " + assessmentType);
                System.out.println("Assessment Name: " + selectedAssessment.getAssessment_name());
                System.out.println("Assessment CourseId: " + selectedAssessment.getCourse_id_fk());
                System.out.println("Assessment Info: " + selectedAssessment.getAssessment_info());
                assessmentTypeEditText.setText(selectedAssessment.getAssessment_type());
                assessmentTitleEditText.setText(selectedAssessment.getAssessment_name());
                assessmentInfoEditText.setText(selectedAssessment.getAssessment_info());
                //setAlertCheckBox.setChecked((selectedAssessment.getAssessment_set_alert()==1)?true:false);
                alertTitleEditText.setText(selectedAssessment.getAssessment_alert_name());
                alertStartDateEditText.setText(formatter.format(selectedAssessment.getAssessment_alert_date()));
                assessmentDueDateEditText.setText(formatter.format(selectedAssessment.getAssessment_due()));
                Log.d(LOG_TAG, "assessmentDueDate: " + assessmentDueDateEditText.getText().toString());
            } else {System.out.println("Null Object");}
        } catch (Exception e) {System.out.println("selectedAssessment failed");}
        //---- End Update Views
    }
}
