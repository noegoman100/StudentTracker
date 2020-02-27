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
                updateViews();
            }
        });
        //-------- End Create Nuke DB button programmatically
        //-------- Create Populate DB button programmatically

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
                updateViews();
            }
        });
        //-------- End Create Populate DB button programmatically
    }

    private void updateViews(){

        int coursesPending = 0;
        int coursesCompleted = 0;
        int coursesDropped = 0;
        int assessmentsPending = 0;
        int assessmentsPassed = 0;
        int assessmentsFailed = 0;
        try {
            List<Term> termList = db.termDao().getAllTerms();
            List<Course> courseList = db.courseDao().getAllCourses();
            List<Assessment> assessmentList = db.assessmentDao().getAllAssessments();

            try {
                for(int i = 0; i < courseList.size(); i++) {
                    if(courseList.get(i).getCourse_status().contains("Pending")) coursesPending++;
                    if(courseList.get(i).getCourse_status().contains("In-Progress")) coursesPending++;
                    if(courseList.get(i).getCourse_status().contains("Completed")) coursesCompleted++;
                    if(courseList.get(i).getCourse_status().contains("Dropped")) coursesDropped++;

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            for (int i = 0; i < assessmentList.size(); i++) {
                if(assessmentList.get(i).getAssessment_status().contains("Pending")) assessmentsPending++;
                if(assessmentList.get(i).getAssessment_status().contains("Passed")) assessmentsPassed++;
                if(assessmentList.get(i).getAssessment_status().contains("Failed")) assessmentsFailed++;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        coursesPendingTextView.setText(String.valueOf(coursesPending));
        coursesCompletedTextView.setText(String.valueOf(coursesCompleted));
        coursesDroppedTextView.setText(String.valueOf(coursesDropped));
        assessmentsPendingTextView.setText(String.valueOf(assessmentsPending));
        assessmentsFailedTextView.setText(String.valueOf(assessmentsFailed));
        assessmentsPassedTextView.setText(String.valueOf(assessmentsPassed));
    }

    @Override
    protected void onResume(){
        super.onResume();
        updateViews();
    }
}
