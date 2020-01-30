package com.example.backendonly;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MentorsListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentors_list);
        setTitle("Mentors List");
        FullDatabase db = FullDatabase.getInstance(getApplicationContext());
        FloatingActionButton addMentorsFAB = findViewById(R.id.addMentorFAB);
        addMentorsFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Coursementor tempCoursementor = new Coursementor();
                //todo I need the addExtras to query the selected course.
            }
        });
    }
}
