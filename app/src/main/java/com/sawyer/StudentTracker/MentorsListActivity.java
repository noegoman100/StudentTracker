package com.sawyer.StudentTracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MentorsListActivity extends AppCompatActivity {
    public static final String LOG_TAG = "MentorListAct";
    int termId;
    int courseId;
    FullDatabase db;
    Intent intent;
    List<Coursementor> coursementorList;
    ListView mentorsListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentors_list);
        setTitle("Mentors List");
        intent = getIntent();
        termId = intent.getIntExtra("termId", -1);
        courseId = intent.getIntExtra("courseId", -1);
        updateMentorsList();
        //-----Attach Views
        FloatingActionButton addMentorsFAB = findViewById(R.id.addMentorFAB);
        mentorsListView = findViewById(R.id.mentorsListView);
        updateMentorsList();
        //-----End Attach Views
        //----- FAB Add Mentor
        addMentorsFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(LOG_TAG, "Add Mentors Button Pressed");
                Coursementor tempCoursementor = new Coursementor();
                //TODO implement add new Coursementor
                tempCoursementor.setMentor_name("New Course Mentor");
                tempCoursementor.setCourse_id_fk(courseId);
                tempCoursementor.setMentor_email("New Mentor Email");
                tempCoursementor.setMentor_phone("New Phone Number");
                Log.d(LOG_TAG, "About to insert new Coursementor");
                db.coursementorDao().insertCoursementor(tempCoursementor);
                updateMentorsList();

            }
        });
        //----- End FAB Add Mentor
        //-------Edit Coursementor Click
        mentorsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(LOG_TAG, "mentorsListView position clicked: " + position);
                Intent intent = new Intent(getApplicationContext(), EditCoursementorActivity.class);
                Coursementor selectedCoursementor = coursementorList.get(position);
                int coursementorId = selectedCoursementor.getCoursementor_id();
                Log.d(LOG_TAG, "coursementorId: " + coursementorId);
                Log.d(LOG_TAG, "courseId: " + courseId);
                Log.d(LOG_TAG, "termId: " + termId);


                intent.putExtra("termId", termId);
                intent.putExtra("courseId", courseId);
                intent.putExtra("coursementorId", coursementorId);

                startActivity(intent);
            }
        });
        //-------End Edit Coursementor Click
    }

    private void updateMentorsList() {
        db = FullDatabase.getInstance(getApplicationContext());
        Log.d(LOG_TAG, "Inside updateMentorsList - courseId: " + courseId);
        try {
            coursementorList = db.coursementorDao().getCoursementorList(courseId);
            if (!coursementorList.isEmpty()) {
                Log.d(LOG_TAG, "coursementorList is not empty");
            }
        } catch (Exception e) {
            Log.d(LOG_TAG, "failed to load coursementorList");
        }

        String[] items = new String[coursementorList.size()];
        if (!coursementorList.isEmpty()) {
            for (int i = 0; i < coursementorList.size(); i++) {
                items[i] = coursementorList.get(i).getMentor_name();
                Log.d(LOG_TAG, "Inside UpdateMentorlist loop: " + i);
            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        try {
            mentorsListView.setAdapter(adapter);
        } catch (Exception e) {
            e.printStackTrace();
        }

        adapter.notifyDataSetChanged();
    }


    @Override
    protected void onResume() {
        super.onResume();
        updateMentorsList();
    }

}
