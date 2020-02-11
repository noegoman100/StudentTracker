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
//        selectedTerm = db.termDao().getTerm(termId);
//        if (selectedTerm == null) {finish();}
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
        String newTitle = "Term: " + selectedTerm.getTerm_name() + " - " + percentComplete() + " Done"; //todo .getTerm_name is crashing app

        setTitle(newTitle);
        //-----Update the Title + Percent Complete


    }

    private String percentComplete() {
        //We need Start Date, End Date, and Today's Date
//        if (selectedTerm==null){
//            Log.d(LOG_TAG, "selectedTerm is Null");
//            finish();}
        Long start = selectedTerm.getTerm_start().getTime();
        Long end = selectedTerm.getTerm_end().getTime();
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
