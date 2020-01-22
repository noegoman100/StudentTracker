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

import java.util.List;

public class TermDetailsActivity extends AppCompatActivity {
    ListView courseListView;
    Intent intent;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_details);
        courseListView = findViewById(R.id.courseListView);

        TextView positionExtraView = findViewById(R.id.positionExtraView);
        intent = getIntent();
        position = intent.getIntExtra("position", 0);
        System.out.println(position);
        positionExtraView.setText(Integer.toString(position));

        courseListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("Item Clicked: " + position);
            }
        });
        updateCourseList();

// -------------- FAB Stuff
        FloatingActionButton addCourseFAB = findViewById(R.id.addCourseFAB);
        addCourseFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("addCourseFAB clicked");
//
//                FullDatabase db = FullDatabase.getInstance(getApplicationContext());
//                int dbCount = db.termDao().getTermList().size() + 1;
                Course tempCourse = new Course();
                tempCourse.setCourse_name("Course Added");
                //db.databaseDao().insertCourse(tempCourse);   //May need to create a new DAO for this table.
                //ArrayAdapter<String> tempAdapter = listView.
                updateCourseList();

            }
        });

// -------------- End FAB Stuff

    }

    private void updateCourseList() { //This updates the listView on this mainActivity
        FullDatabase db = FullDatabase.getInstance(getApplicationContext());
        //intent = getIntent();
        //int selectedTerm = intent.getIntExtra("", 0);
        List<Course> allCourses = db.termDao().getCourseList(position);
        System.out.println("Number of Rows in Course Query: " + allCourses.size());



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
