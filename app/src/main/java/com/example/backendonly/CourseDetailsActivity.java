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
    SimpleDateFormat formatter;
    int termId;
    int courseId;
    int taskId;
    FullDatabase db;
    Intent intent;
    Course selectedCourse;
    List<Task> taskList;
    //List<Task> taskList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);
        //--------- Instantiate Views and Setup Activity

        formatter = new SimpleDateFormat(getString(R.string.date_pattern));
        db = FullDatabase.getInstance(getApplicationContext());
        intent = getIntent();
        termId = intent.getIntExtra("termId", -1);
        System.out.println("received termId: " + termId);
        courseId = intent.getIntExtra("courseId", -1);
        System.out.println("received courseId: " + courseId);
        //taskList = db.taskDao().getTaskList(courseId);
        //--------- END Instantiate Views and Setup Activity
        // -------Attach Views

        courseNotesButton = findViewById(R.id.courseNotesButton);
        courseMentorsButton = findViewById(R.id.courseMentorsButton);
        courseTitleTextView = findViewById(R.id.courseTitleTextView);
        courseStatusTextView = findViewById(R.id.courseStatusTextView);
        courseStartDate = findViewById(R.id.courseStartDate);
        courseEndDate = findViewById(R.id.courseEndDate);
        taskListView = findViewById(R.id.taskListView);
        // -------End Attach Views

        updateViews();

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
                intent.putExtra("termId", termId);
                intent.putExtra("courseId", courseId);
                startActivity(intent);
            }
        });
        //------- End Course Notes Button
    }

    private void updateTaskList() { //This updates the listView on this Course Details Activity
        taskList = new ArrayList<>();
        try {
            taskList = db.taskDao().getTaskList(courseId);
            System.out.println("Number of Rows in Course Query: " + taskList.size());
        } catch (Exception e) {System.out.println("could not pull query");}




        String[] items = new String[taskList.size()];
        if(!taskList.isEmpty()){
            for (int i = 0; i < taskList.size(); i++) {
                items[i] = taskList.get(i).getTask_name();
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
//        selectedCourse = db.courseDao().getCourse(termId, courseId);
//        if (selectedCourse == null) {finish();}
        updateTaskList();
        updateViews();

    }

    private void updateViews(){

        //----------Update Views
        selectedCourse = db.courseDao().getCourse(termId, courseId);
        setTitle(selectedCourse.getCourse_name() + " " + percentComplete() + " Done");

        if (selectedCourse != null) {
            Log.d(CourseDetailsActivity.LOG_TAG, "selectedCourse is Not null");
            courseTitleTextView.setText(selectedCourse.getCourse_name());
            courseStatusTextView.setText(selectedCourse.getCourse_status());
            courseStartDate.setText(formatter.format(selectedCourse.getCourse_start()));
            courseEndDate.setText(formatter.format(selectedCourse.getCourse_end()));

        } else {
            Log.d(CourseDetailsActivity.LOG_TAG, "selectedCourse is null");
            selectedCourse = new Course();
        }

        updateTaskList();
        //----------End Update Views
    }
    private String percentComplete() {
        //We need Start Date, End Date, and Today's Date
        //if (selectedCourse==null){selectedCourse = new Course();}
        Long start = selectedCourse.getCourse_start().getTime();
        Long end = selectedCourse.getCourse_end().getTime();
        Long now = Date.from(Instant.now()).getTime();
        double resultDouble = 0;
        if (!(end-start==0)){
            Long nowMinStart = now-start;
            Long endMinStart = end-start;
            double nowMinS = nowMinStart.intValue();
            double endMinS = endMinStart.intValue();
            double divideDouble = (nowMinS/endMinS);
            resultDouble = divideDouble * 100;
//            Log.d(LOG_TAG, "now-start double: " + nowMinS);
//            Log.d(LOG_TAG, "end-start double: " + endMinS);
//            Log.d(LOG_TAG, "divide double: " + divideDouble);
//            Log.d(LOG_TAG, "result double: " + resultDouble);
        } else {resultDouble = 1;}

        return String.format("%.2f", resultDouble) + "%";
    }
}
