package com.example.backendonly;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TermDetailsActivity extends AppCompatActivity {
    ListView courseListView;
    TextView termStartTextView;
    Intent intent;
    int termId;
    FullDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_details);
        courseListView = findViewById(R.id.courseListView);
        termStartTextView = findViewById(R.id.termStartTextView);
        db = FullDatabase.getInstance(getApplicationContext());
        TextView positionExtraView = findViewById(R.id.termTitleTextView);
        intent = getIntent();
        //getActionBar().setTitle("Term Details");
        setTitle("Term Details");
        termId = intent.getIntExtra("termId", -1);
        //System.out.println("Term Detail (with Courses) Received position: " + position);
        positionExtraView.setText("Received from Intent - TermId: " + termId);


        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
        try {
            Date currentDateTime = db.termDao().getTerm(termId).getTerm_start(); //returns milliseconds: 1579762062532 //.get(position -1) ??? Using correct index?
            //termStartTextView.setText(formatter.parse(currentDatTime.toString()).toString());
            //String millisecondDate = currentDateTime.toString();
            System.out.println("Millisecond Date: " + currentDateTime.toString());
            String temp = formatter.format(currentDateTime);
            System.out.println("Formatted Date: " + temp);
            termStartTextView.setText(temp);

        } catch (Exception e) {termStartTextView.setText("could not format");}

        courseListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("Course Clicked: " + position);
            }
        });
        updateCourseList();

// -------------- FAB Add Stuff
        FloatingActionButton addCourseFAB = findViewById(R.id.addCourseFAB);
        addCourseFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("addCourseFAB clicked");
//

                int dbCount = db.courseDao().getCourseList(termId).size() + 1;
                Course tempCourse = new Course();
                tempCourse.setCourse_name("Course Added " + dbCount);
                db.courseDao().addCourse(termId); //Crashes app on position=0
                //db.courseDao().insertCourse(tempCourse); //Crashes App
                //ArrayAdapter<String> tempAdapter = listView.
                updateCourseList();

            }
        });

// -------------- End FAB Add Stuff

// -------------- FAB Edit Stuff
        FloatingActionButton editTermFAB = findViewById(R.id.editTermFAB);
        editTermFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("FAB Edit stuff pressed. ");
                Term tempTerm = db.termDao().getTerm(termId);
                System.out.println("Current Term Name: " + tempTerm.getTerm_name());
                db.termDao().deleteTerm(tempTerm); //Seems to be causing problems. Not sure where, yet.
                //updateCourseList();
                finish();
            }
        });

// -------------- End FAB Edit Stuff

    }

    private void updateCourseList() { //This updates the listView on this mainActivity
        //FullDatabase db = FullDatabase.getInstance(getApplicationContext());
        //intent = getIntent();
        //int selectedTerm = intent.getIntExtra("", 0);
        List<Course> allCourses = new ArrayList<>();
        try {
            allCourses = db.courseDao().getCourseList(termId);
            System.out.println("Number of Rows in Course Query: " + allCourses.size());
        } catch (Exception e) {System.out.println("could not pull query");}




        String[] items = new String[allCourses.size()];
        if(!allCourses.isEmpty()){
            for (int i = 0; i < allCourses.size(); i++) {
                items[i] = allCourses.get(i).getCourse_name();
                System.out.println("Inside updateList Loop: " + i);

            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        courseListView.setAdapter(adapter);

        adapter.notifyDataSetChanged();
    }
}
