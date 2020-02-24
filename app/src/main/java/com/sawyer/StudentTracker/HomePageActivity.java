package com.sawyer.StudentTracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class HomePageActivity extends AppCompatActivity {
    public static String LOG_TAG = "HomePageAct";
    FullDatabase db;
    Button startButton;
    TextView coursesPendingTextView;
    TextView coursesCompletedTextView;
    TextView coursesDroppedTextView;
    TextView assessmentsPendingTextView;
    TextView assessmentsPassedTextView;
    TextView assessmentsFailedTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        db = FullDatabase.getInstance(getApplicationContext());
        //-----------Attach Views
        coursesPendingTextView = findViewById(R.id.coursesPendingTextView);
        coursesCompletedTextView = findViewById(R.id.coursesCompletedTextView);
        coursesDroppedTextView = findViewById(R.id.coursesDroppedTextView);
        assessmentsPendingTextView = findViewById(R.id.assessmentsPendingTextView);
        assessmentsPassedTextView = findViewById(R.id.assessmentsPassedTextView);
        assessmentsFailedTextView = findViewById(R.id.assessmentsFailedTextView);
        //-----------End Attach Views
        updateViews();
        //-----------Start Button Functionality
        startButton = findViewById(R.id.startButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TermListActivity.class);
                startActivity(intent);
            }
        });
        //-----------End Start Button Functionality
        //-------- Create Nuke DB button programmatically
        ConstraintLayout myLayout = findViewById(R.id.homePageConstraintLayout);
        ConstraintSet set = new ConstraintSet();
        Button nukeDBButton = new Button(getApplicationContext());
        nukeDBButton.setText("Nuke DB Tables");
        nukeDBButton.setId(R.id.nukeDBButton);

        set.constrainHeight(nukeDBButton.getId(), ConstraintSet.WRAP_CONTENT);
        set.constrainWidth(nukeDBButton.getId(), ConstraintSet.WRAP_CONTENT);
        set.connect(nukeDBButton.getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM, 8);
        set.connect(nukeDBButton.getId(), ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT, 8);

        myLayout.addView(nukeDBButton);
        setContentView(myLayout);
        set.applyTo(myLayout);

        nukeDBButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(LOG_TAG, "Nuke DB Button Pressed");
                db.clearAllTables();
            }
        });
        //-------- End Create Nuke DB button programmatically
        //-------- Create Populate DB button programmatically
        //ConstraintLayout myLayout = findViewById(R.id.homePageConstraintLayout);
        //ConstraintSet set = new ConstraintSet();
        Button populateDBbutton = new Button(getApplicationContext());
        populateDBbutton.setText("Populate Empty Database");
        populateDBbutton.setId(R.id.populateDBButton);

        set.constrainHeight(populateDBbutton.getId(), ConstraintSet.WRAP_CONTENT);
        set.constrainWidth(populateDBbutton.getId(), ConstraintSet.WRAP_CONTENT);
        set.connect(populateDBbutton.getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM, 8);
        set.connect(populateDBbutton.getId(), ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT, 8);

        myLayout.addView(populateDBbutton);
        setContentView(myLayout);
        set.applyTo(myLayout);

        populateDBbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(LOG_TAG, "populate DB Button Pressed");
                PopulateDatabase populateDatabase = new PopulateDatabase();
                populateDatabase.populate(getApplicationContext());
            }
        });
        //-------- End Create Populate DB button programmatically
    }

    private void updateViews(){
        if (db.termDao().getTermList() != null) { //Don't update anything until there is something to update.
            //--Count Pending/In-Progress Courses
            //get full list of courses by get term count and loop through all appending courseList, loop through and count matches for strings "Pending" and "In-Progress"
            List<Term> termList;
            List<Course> fullCourseList;
            List<Course> courseList;
            termList = db.termDao().getTermList();
            int termCount = termList.size();
            if(db.courseDao().getCourseList(1) != null) { //Don't update unless there is at least one course
                courseList = db.courseDao().getCourseList(1);
                if (termCount >= 1) {
                    for (int i = 1; i < termCount; i++) {
                        courseList.addAll(db.courseDao().getCourseList(i+1));
                    }
                }
                //todo now loop through and count matching terms. 
                Log.d(LOG_TAG, "courseList Count: " + courseList.size());
            }


            //--End Count Pending/In-Progress Courses
        }
    }

    @Override
    protected void onResume(){
        super.onResume();
        updateViews();
    }
}
