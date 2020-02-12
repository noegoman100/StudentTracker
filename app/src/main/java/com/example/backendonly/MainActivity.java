package com.example.backendonly;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Date;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.List;

public class MainActivity extends AppCompatActivity {

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
                List<Term> termList = db.termDao().getTermList();
                for (Term term: termList) {
                    System.out.println(term.getTerm_id());
                }
                term_id = termList.get(position).getTerm_id();
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
                System.out.println("addTermFAB clicked");

                //db = FullDatabase.getInstance(getApplicationContext());
                //int dbCount = db.termDao().getTermList().size() + 1;
                Term tempTerm1 = new Term();
                tempTerm1.setTerm_name("Term Added");
                tempTerm1.setTerm_start(Date.from(Instant.now()));
                tempTerm1.setTerm_end(Date.from(Instant.now()));
                db.termDao().insertTerm(tempTerm1);
                //ArrayAdapter<String> tempAdapter = listView.
                updateList();

            }
        });

// -------------- End FAB Stuff


    } // END onCreate


    private void updateList() { //This updates the listView on this mainActivity
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
