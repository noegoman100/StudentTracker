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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EditTaskActivity extends AppCompatActivity {
    public static final String LOG_TAG = "EditTaskAct";
    EditText taskTypeEditText;
    EditText taskTitleEditText;
    EditText taskDueDate;
    EditText taskInfoEditText;
    EditText alertTitleEditText;
    EditText alertDateEditText;
    CheckBox setAlertCheckBox;
    FullDatabase db;
    Button saveTaskButton;
    Button deleteTaskButton;
    int termId;
    int courseId;
    int taskId;
    Intent intentReceived;
    Task selectedTask = new Task();
    final SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm a");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);
        setTitle("Edit Task");
        //----Attach Views to Fields
        taskTypeEditText = findViewById(R.id.taskTypeEditText);
        saveTaskButton = findViewById(R.id.saveTaskButton);
        deleteTaskButton = findViewById(R.id.deleteTaskButton);
        taskTitleEditText = findViewById(R.id.taskTitileEditText);
        taskDueDate = findViewById(R.id.taskDueDate);
        taskInfoEditText = findViewById(R.id.taskInfoEditText);
        setAlertCheckBox = findViewById(R.id.setAlertCheckBox);
        alertTitleEditText = findViewById(R.id.alertTitleEditText);
        alertDateEditText = findViewById(R.id.alertDate);
        //----End Attach Views to Fields
        //---- Update Views


        db = FullDatabase.getInstance(getApplicationContext());

        intentReceived = getIntent();
        termId = intentReceived.getIntExtra("termId", -1);
        courseId = intentReceived.getIntExtra("courseId", -1);
        taskId = intentReceived.getIntExtra("taskId", -1);
        try {
            selectedTask = db.taskDao().getTask(courseId,taskId);
            if(selectedTask != null) {
                System.out.println("selectedTask Not Null");
                System.out.println("EditTask termId: " + termId);
                System.out.println("Edit Task courseId: " + courseId);
                System.out.println("Edit Task taskId: " + taskId);
                String taskType = selectedTask.getTask_type();
                System.out.println("Task Type: " + taskType);
                System.out.println("Task Name: " + selectedTask.getTask_name());
                System.out.println("Task CourseId: " + selectedTask.getCourse_id_fk());
                System.out.println("Task Info: " + selectedTask.getTask_info());
                taskTypeEditText.setText(selectedTask.getTask_type());
                taskTitleEditText.setText(selectedTask.getTask_name());
                taskInfoEditText.setText(selectedTask.getTask_info());
                setAlertCheckBox.setChecked((selectedTask.getTask_set_alert()==1)?true:false);
                alertTitleEditText.setText(selectedTask.getTask_alert_name());
                alertDateEditText.setText(formatter.format(selectedTask.getTask_alert_datetime()));
                taskDueDate.setText(formatter.format(selectedTask.getTask_due()));
            } else {System.out.println("Null Object");}
        } catch (Exception e) {System.out.println("selectedTask failed");}
        //---- End Update Views
        //------- Delete Task Button
            deleteTaskButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    db.taskDao().deleteTask(selectedTask);
                    finish();
                }
            });
        //------- End Delete Task Button
        //--------- Save Task Button
        saveTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(EditTaskActivity.LOG_TAG, "Save Task Button Pressed");
                selectedTask.setTask_type(taskTypeEditText.getText().toString());
                selectedTask.setTask_name(taskTitleEditText.getText().toString());
                selectedTask.setTask_info(taskInfoEditText.getText().toString());
                selectedTask.setTask_set_alert((setAlertCheckBox.isChecked())?1:0);  //Set to One if Checked. 0 if not checked.
                selectedTask.setTask_alert_name(alertTitleEditText.getText().toString());

                try {
                    selectedTask.setTask_alert_datetime(formatter.parse(alertDateEditText.getText().toString()));
                    selectedTask.setTask_due(formatter.parse(taskDueDate.getText().toString()));
                    Log.d(LOG_TAG, "updated alert date");
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if(setAlertCheckBox.isChecked()) {
                    setAlarm(selectedTask.getTask_alert_datetime());
                }
                db.taskDao().updateTask(selectedTask);

                finish();
            }
        });
        //--------- End Save Task Button


    }

    private void setAlarm(Date date) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getApplicationContext(), EditTaskActivity.class);
        intent.putExtra("termId", termId);
        intent.putExtra("courseId", courseId);
        intent.putExtra("taskId", taskId);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);
        alarmManager.set(AlarmManager.RTC, date.getTime(), pendingIntent);
        Log.d(LOG_TAG, "The alarm was set");

    }
}
