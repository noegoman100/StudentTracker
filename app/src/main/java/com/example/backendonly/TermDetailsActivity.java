package com.example.backendonly;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TermDetailsActivity extends AppCompatActivity {
    static final String LOG_TAG = "TermDetAct";
    ListView courseListView;
    TextView termStartTextView;
    TextView termEndTextView;
    TextView termTitleTextView;
    Intent intent;
    int termId;
    FullDatabase db;
    Term selectedTerm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_details);
        db = FullDatabase.getInstance(getApplicationContext());
        intent = getIntent();
        setTitle("Term Details");
        termId = intent.getIntExtra("termId", -1);
        Log.d(TermDetailsActivity.LOG_TAG, "TermId passed In: " + termId);
        selectedTerm = db.termDao().getTerm(termId);

        //-------------Attach Views
        courseListView = findViewById(R.id.courseListView);
        termStartTextView = findViewById(R.id.termStartTextView);
        termEndTextView = findViewById(R.id.termEndTextView);
        termTitleTextView = findViewById(R.id.termTitleTextView);

        //-------------End Attach Views

        updateViews();

        courseListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("Course Clicked: " + position);
                Intent intent = new Intent(getApplicationContext(), CourseDetailsActivity.class);
                int courseId = db.courseDao().getCourseList(termId).get(position).getCourse_id();
                intent.putExtra("termId", termId);
                intent.putExtra("courseId", courseId);

                startActivity(intent);
            }
        });
        updateCourseList();

// -------------- FAB Add Stuff
        FloatingActionButton addCourseFAB = findViewById(R.id.addTaskFAB);
        addCourseFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("addCourseFAB clicked");
//

                int dbCount = db.courseDao().getCourseList(termId).size() + 1;
                Course tempCourse = new Course();
                tempCourse.setCourse_name("Course Added " + dbCount);
                tempCourse.setCourse_start(Date.from(Instant.now()));
                tempCourse.setCourse_end(Date.from(Instant.now()));
                tempCourse.setCourse_status("Active Status");
                tempCourse.setTerm_id_fk(termId);
                //db.courseDao().addCourse(tempCourse);
                db.courseDao().insertCourse(tempCourse);
                updateCourseList();

            }
        });

// -------------- End FAB Add Stuff

// -------------- FAB Edit Stuff
        FloatingActionButton editTermFAB = findViewById(R.id.editCourseFAB);
        editTermFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("FAB Edit stuff pressed. ");
                Term tempTerm = db.termDao().getTerm(termId);
                System.out.println("Current Term Name: " + tempTerm.getTerm_name());
                //db.termDao().deleteTerm(tempTerm);
                //updateCourseList();
                //finish();
                Intent intent = new Intent(getApplicationContext(), EditTermActivity.class);
                intent.putExtra("termId", termId);

                startActivity(intent);
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

    @Override
    protected void onResume() {
        super.onResume();
        updateCourseList();
        selectedTerm = db.termDao().getTerm(termId);
        updateViews();

    }

    private void updateViews() {
        //----- Update Views
        final SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
        if (selectedTerm != null) {
            Log.d(TermDetailsActivity.LOG_TAG, "selected Term is Not null");
            Date startDate = selectedTerm.getTerm_start(); //returns milliseconds: 1579762062532 //.get(position -1) ??? Using correct index?
            Date endDate = selectedTerm.getTerm_end();
            //termStartTextView.setText(formatter.parse(currentDatTime.toString()).toString());
            //String millisecondDate = currentDateTime.toString();
            System.out.println("Millisecond Date: " + startDate.toString());
            String temp = formatter.format(startDate);
            String tempEnd = formatter.format(endDate);
            System.out.println("Formatted Date: " + temp);
            termStartTextView.setText(temp);
            termEndTextView.setText(tempEnd);
            termTitleTextView.setText(selectedTerm.getTerm_name());
        } else {
            Log.d(TermDetailsActivity.LOG_TAG, "selected Term is Null");
        }
        //----- End Update Views

    }
}
