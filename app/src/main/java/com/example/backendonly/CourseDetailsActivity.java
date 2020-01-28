package com.example.backendonly;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CourseDetailsActivity extends AppCompatActivity {
    TextView courseTitle;
    TextView courseStartDate;
    TextView courseEndDate;
    ListView taskListView;
    int termId;
    int courseId;
    FullDatabase db;
    Intent intent;
    //List<Task> taskList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);
        setTitle("Course Details Activity");
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
        db = FullDatabase.getInstance(getApplicationContext());
        intent = getIntent();
        termId = intent.getIntExtra("termId", -1);
        System.out.println("received termId: " + termId);
        courseId = intent.getIntExtra("courseId", -1);
        System.out.println("received courseId: " + courseId);
        courseTitle = findViewById(R.id.courseTitleEditText);
        courseStartDate = findViewById(R.id.courseStartDate);
        courseEndDate = findViewById(R.id.courseEndDate);
        taskListView = findViewById(R.id.taskListView);
        Course selectedCourse = db.courseDao().getCourse(termId, courseId);
        courseTitle.setText(selectedCourse.getCourse_name());
        try {
            System.out.println("Course Start: " + selectedCourse.getCourse_start());
            System.out.println("Course End: " + selectedCourse.getCourse_end());
            courseStartDate.setText(formatter.format(selectedCourse.getCourse_start()));
            courseEndDate.setText(formatter.format(selectedCourse.getCourse_end()));

        } catch (Exception e) {
            courseStartDate.setText("Could Not Set");
            courseEndDate.setText("Could Not Set");
        }
        updateTaskList();
        // -------------- FAB Add Stuff
        FloatingActionButton addTaskFAB = findViewById(R.id.addTaskFAB);
        addTaskFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("addTaskFAB clicked");
                int dbCount = db.taskDao().getTaskList(courseId).size() + 1;
                Task tempTask = new Task();
                tempTask.setTask_name("Task Added " + dbCount);
                tempTask.setTask_alert_datetime(Date.from(Instant.now()));
                tempTask.setTask_set_alert(0);
                tempTask.setCourse_id_fk(courseId);
                try {
                    System.out.println("Inside Try");
                    //db.taskDao().addTask(courseId, "New Task");
                    db.taskDao().insertTask(tempTask);

                } catch (Exception e) {System.out.println("Try inside addTaskFab failed");}

                updateTaskList();
            }
        });

// -------------- End FAB Add Stuff
    }

    private void updateTaskList() { //This updates the listView on this Course Details Activity
        //FullDatabase db = FullDatabase.getInstance(getApplicationContext());
        //intent = getIntent();
        //int selectedTerm = intent.getIntExtra("", 0);
        List<Task> allTasks = new ArrayList<>();
        try {
            allTasks = db.taskDao().getTaskList(courseId);
            System.out.println("Number of Rows in Course Query: " + allTasks.size());
        } catch (Exception e) {System.out.println("could not pull query");}




        String[] items = new String[allTasks.size()];
        if(!allTasks.isEmpty()){
            for (int i = 0; i < allTasks.size(); i++) {
                items[i] = allTasks.get(i).getTask_name();
                System.out.println("Inside updateList Loop: " + i);

            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        taskListView.setAdapter(adapter);

        adapter.notifyDataSetChanged();
    }
}
