package com.sawyer.StudentTracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;
import java.util.List;

public class TermListActivity extends AppCompatActivity {

    public static String LOG_TAG = "TermListActivityLog";
    FullDatabase db;
    ListView listView;
    //String[] listItem;
    @Override
    protected void onResume() {
        super.onResume();
        updateList();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //getActionBar().setTitle("Term List"); //Crashes App
        setTitle("Term List");
        listView = findViewById(R.id.listView);
        //----Change BG Color
        View root = listView.getRootView();
        root.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_light));
        //----End Change BG Color
        //listItem = getResources().getStringArray(R.array.array_technology);
        db = FullDatabase.getInstance(getApplicationContext());
        //Context tempContext = getApplicationContext();


        // -------------- ListView Stuff

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("Position Clicked: " + position);
                Intent intent = new Intent(getApplicationContext(), TermDetailsActivity.class);
                int term_id;
                List<Term> termListActivity = db.termDao().getTermList();
                for (Term term: termListActivity) {
                    System.out.println(term.getTerm_id());
                }
                term_id = termListActivity.get(position).getTerm_id();
                intent.putExtra("termId", term_id);
                System.out.println("term_id at 0: " + term_id);
                startActivity(intent);
            }
        });
        updateList();

        // -------------- End ListView Stuff

        // -------------- FAB Stuff
        FloatingActionButton addTermFAB = findViewById(R.id.addTermFAB);
        addTermFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                Term tempTerm1 = new Term();
                tempTerm1.setTerm_name("Term Added");
                //todo replace these with API level 24 stuff
                tempTerm1.setTerm_start(calendar.getTime());
                calendar.add(Calendar.DAY_OF_YEAR, 1);
                tempTerm1.setTerm_end(calendar.getTime());

                db.termDao().insertTerm(tempTerm1);
                //ArrayAdapter<String> tempAdapter = listView.
                updateList();
                System.out.println("addTermFAB clicked");
            }
        });

        // -------------- End FAB Stuff

//        //-------- Create Nuke DB button programmatically
//        ConstraintLayout myLayout = findViewById(R.id.termListActivityConstraintLayout);
//        ConstraintSet set = new ConstraintSet();
//        Button nukeDBButton = new Button(getApplicationContext());
//        nukeDBButton.setText("Nuke DB Tables");
//        nukeDBButton.setId(R.id.nukeDBButton);
//
//        set.constrainHeight(nukeDBButton.getId(), ConstraintSet.WRAP_CONTENT);
//        set.constrainWidth(nukeDBButton.getId(), ConstraintSet.WRAP_CONTENT);
//        set.connect(nukeDBButton.getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM, 8);
//        set.connect(nukeDBButton.getId(), ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT, 8);
//
//        myLayout.addView(nukeDBButton);
//        setContentView(myLayout);
//        set.applyTo(myLayout);
//
//        nukeDBButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.d(LOG_TAG, "Nuke DB Button Pressed");
//                db.clearAllTables();
//                updateList();
//            }
//        });
//        //-------- End Create Nuke DB button programmatically


    } // END onCreate


    private void updateList() { //This updates the listView on this termListActivity
        //FullDatabase db = FullDatabase.getInstance(getApplicationContext());
        List<Term> allTerms = db.termDao().getTermList();
        System.out.println("Number of Rows in User Table: " + allTerms.size());



        String[] items = new String[allTerms.size()];
        if(!allTerms.isEmpty()){
            for (int i = 0; i < allTerms.size(); i++) {
                items[i] = allTerms.get(i).getTerm_name();
                System.out.println("Inside updateList Loop: " + i);

            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        listView.setAdapter(adapter);

        adapter.notifyDataSetChanged();
    }
}
