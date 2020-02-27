package com.sawyer.StudentTracker;

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
import java.util.ArrayList;
import java.util.Calendar;
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
    SimpleDateFormat formatter;

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
        formatter = new SimpleDateFormat(getString(R.string.date_pattern));
        //-------------Attach Views
        courseListView = findViewById(R.id.courseListView);
        termStartTextView = findViewById(R.id.termStartTextView);
        termEndTextView = findViewById(R.id.termEndTextView);
        termTitleTextView = findViewById(R.id.termTitleTextView);

        //-------------End Attach Views

        updateViews();
        //------- List Click Listener
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
        //------- End List Click Listener

        updateCourseList();

// -------------- FAB Add Stuff
        FloatingActionButton addCourseFAB = findViewById(R.id.addAssessmentFAB);
        addCourseFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int dbCount = db.courseDao().getCourseList(termId).size() + 1;
                Course tempCourse = new Course();
                tempCourse.setCourse_name("Course Added " + dbCount);
                tempCourse.setCourse_start(calendar.getTime());
                calendar.add(Calendar.DAY_OF_YEAR, 1);
                tempCourse.setCourse_end(calendar.getTime());
                tempCourse.setCourse_status("Active Status");
                tempCourse.setTerm_id_fk(termId);
                db.courseDao().insertCourse(tempCourse);
                updateCourseList();
                System.out.println("addCourseFAB clicked");
            }
        });

// -------------- End FAB Add Stuff

// -------------- FAB Edit Term
        FloatingActionButton editTermFAB = findViewById(R.id.editCourseFAB);
        editTermFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("FAB Edit stuff pressed. ");
                Term tempTerm = db.termDao().getTerm(termId);
                System.out.println("Current Term Name: " + tempTerm.getTerm_name());
                Intent intent = new Intent(getApplicationContext(), EditTermActivity.class);
                intent.putExtra("termId", termId);

                startActivity(intent);
            }
        });

// -------------- End FAB Edit Term

    }

    private void updateCourseList() { //This updates the listView on this termListActivity
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
        selectedTerm = db.termDao().getTerm(termId);
        updateCourseList();
        updateViews();

    }

    private void updateViews() {
        //----- Update Views

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
            selectedTerm = new Term();
        }
        //----- End Update Views
        //-----Update the Title + Percent Complete
        String newTitle = "Term: " + selectedTerm.getTerm_name() + ": " + percentComplete();

        setTitle(newTitle);
        //-----Update the Title + Percent Complete


    }

    private String percentComplete() {
        //We need Start Date, End Date, and Today's Date
        Calendar calendar = Calendar.getInstance();
        Long start = selectedTerm.getTerm_start().getTime();
        Long end = selectedTerm.getTerm_end().getTime();
        Long now = calendar.getTime().getTime();
        double resultDouble = 0;
        if (now < start) {return "Pending";}
            else if (now >= end) {return "Complete";}
                else {
                    Log.d(LOG_TAG, "Now: " + now);
                    Log.d(LOG_TAG, "Start: " + start);
                    Log.d(LOG_TAG, "End: " + end);

                    Long nowMinStart = now-start; //positive value
                    Long endMinStart = end-start; //Positive Value Always
                    Log.d(LOG_TAG, "nowMinStart: " + nowMinStart);
                    Log.d(LOG_TAG, "endMinStart: " + endMinStart);
                    double nowMinS = nowMinStart.doubleValue();
                    double endMinS = endMinStart.doubleValue();
                    resultDouble = (nowMinS/endMinS) * 100;
                    Log.d(LOG_TAG, "nowMinS: " + nowMinS);
                    Log.d(LOG_TAG, "endMinS: " + endMinS);
                    Log.d(LOG_TAG, "resultDouble: " + resultDouble);

                    return String.format("%.1f", resultDouble) + "%";
                }

    }
}
