package com.example.backendonly;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CourseDetailsActivity extends AppCompatActivity {
    static final String LOG_TAG = "CourseDetAct";
    TextView courseTitleTextView;
    TextView courseStartDate;
    TextView courseEndDate;
    TextView courseStatusTextView;
    ListView taskListView;
    Button courseNotesButton;
    Button courseMentorsButton;
    int termId;
    int courseId;
    int taskId;
    FullDatabase db;
    Intent intent;
    Course selectedCourse;
    //List<Task> taskList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);
        //--------- Instantiate Views and Setup Activity
        setTitle("Course Details Activity");
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
        db = FullDatabase.getInstance(getApplicationContext());
        intent = getIntent();
        termId = intent.getIntExtra("termId", -1);
        System.out.println("received termId: " + termId);
        courseId = intent.getIntExtra("courseId", -1);
        System.out.println("received courseId: " + courseId);
        final List<Task> taskList = db.taskDao().getTaskList(courseId);
        selectedCourse = db.courseDao().getCourse(termId, courseId);
        courseNotesButton = findViewById(R.id.courseNotesButton);
        courseMentorsButton = findViewById(R.id.courseMentorsButton);
        courseTitleTextView = findViewById(R.id.courseTitleTextView);
        courseStatusTextView = findViewById(R.id.courseStatusTextView);
        courseStartDate = findViewById(R.id.courseStartDate);
        courseEndDate = findViewById(R.id.courseEndDate);
        taskListView = findViewById(R.id.taskListView);
        //--------- END Instantiate Views and Setup Activity
        //----------Update Views
        if (selectedCourse != null) {
            Log.d(CourseDetailsActivity.LOG_TAG, "selectedCourse is Not null");
            courseTitleTextView.setText(selectedCourse.getCourse_name());
            courseStatusTextView.setText(selectedCourse.getCourse_status());

        } else {Log.d(CourseDetailsActivity.LOG_TAG, "selectedCourse is null");}

        updateTaskList();
        //----------End Update Views

        //--------- Task List View click function
        taskListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("Task clicked at position: " + position);
                Intent intent = new Intent(getApplicationContext(), EditTaskActivity.class);
                taskId = taskList.get(position).getTask_id();
                intent.putExtra("termId", termId);
                intent.putExtra("courseId", courseId);
                intent.putExtra("taskId", taskId);
                startActivity(intent);
            }
        });
        //--------- End Task List View click function



        // -------------- FAB Add Stuff
        FloatingActionButton addTaskFAB = findViewById(R.id.addTaskFAB);
        addTaskFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("addTaskFAB clicked");
                //int dbCount = db.taskDao().getTaskList(courseId).size() + 1;
                Task tempTask = new Task();
                tempTask.setTask_name("Task Added ");
                tempTask.setTask_type("Performance");
                tempTask.setTask_due(Date.from(Instant.now()));
                tempTask.setTask_info("Task info here");
                tempTask.setTask_alert_name("Temp Task Name");
                tempTask.setTask_alert_datetime(Date.from(Instant.now()));
                tempTask.setTask_set_alert(0);
                tempTask.setCourse_id_fk(courseId);
                try {
                    System.out.println("Inside Try - Add Task");
                    //db.taskDao().addTask(courseId, "New Task");
                    db.taskDao().insertTask(tempTask);

                } catch (Exception e) {System.out.println("Try inside addTaskFab failed");}

                updateTaskList();
            }
        });

        // -------------- End FAB Add Stuff
        // -------------- FAB Edit Course
        FloatingActionButton editCourseFAB = findViewById(R.id.editCourseFAB);
        editCourseFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("editCourseFAB clicked");
                Intent intent = new Intent(getApplicationContext(), EditCourseActivity.class);
                intent.putExtra("termId", termId);
                intent.putExtra("courseId", courseId);
                startActivity(intent);

            }
        });

        // -------------- End FAB Edit Course
        //------- Course Mentors Button
        courseMentorsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MentorsListActivity.class);
                intent.putExtra("termId", termId);
                intent.putExtra("courseId", courseId);
                startActivity(intent);
            }
        });
        //------- End Course Mentors Button
        //------- Course Notes Button
        courseNotesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CourseNotesActivity.class);
                //todo addExtras
                intent.putExtra("termId", termId);
                intent.putExtra("courseId", courseId);
                startActivity(intent);
            }
        });
        //------- End Course Notes Button
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

    @Override
    protected void onResume() {
        super.onResume();
        updateTaskList();

    }
}
