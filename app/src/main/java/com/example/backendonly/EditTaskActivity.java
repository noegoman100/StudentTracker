package com.example.backendonly;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditTaskActivity extends AppCompatActivity {
    public static final String LOG_TAG = "EditTaskAct";
    EditText taskTypeEditText;
    EditText taskTitleEditText;
    EditText taskDueDate;
    EditText taskInfoEditText;
    FullDatabase db;
    Button saveTaskButton;
    Button deleteTaskButton;
    int termId;
    int courseId;
    int taskId;
    Intent intentReceived;
    Task selectedTask = new Task();

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
        //----End Attach Views to Fields
        //---- Update Views
        //todo update View items with queried Task, modify task object and update in Save click listener. Add Delete function
        db = FullDatabase.getInstance(getApplicationContext());

        intentReceived = getIntent();
        termId = intentReceived.getIntExtra("termId", -1);
        courseId = intentReceived.getIntExtra("courseId", -1);
        taskId = intentReceived.getIntExtra("taskId", -1);
        try {
            selectedTask = db.taskDao().getTask(courseId,taskId);
            if(selectedTask != null) {
                System.out.println("Not Null");
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
            }
        });
        //--------- End Save Task Button


    }
}
