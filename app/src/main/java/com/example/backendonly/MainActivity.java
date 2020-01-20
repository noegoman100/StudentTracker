package com.example.backendonly;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    //TextView textView;
    //TextView longView;
    //TextView dateView;
    //TextView anotherLongView;
    ListView listView;
    String[] listItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);
        listItem = getResources().getStringArray(R.array.array_technology);


// ------------- Database Stuff
        BasicDatabase db = BasicDatabase.getInstance(getApplicationContext());

// -------------- End Database Stuff

// -------------- ListView Stuff

        //move me to a method.
        List<Term> allUsers = db.databaseDao().getTermList();
        System.out.println("Number of Rows in User Table: " + allUsers.size());


        updateList();
        String newText = Integer.toString(allUsers.size());

        Date date = new Date();
        Instant instant = date.toInstant();
        Long time = date.getTime();

        System.out.println("Time: " + time);
        System.out.println("Instant: " + instant.toString());

        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy hh:mm");



// -------------- End ListView Stuff

// -------------- FAB Stuff
        FloatingActionButton addTermFAB = findViewById(R.id.addTermFAB);
        addTermFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("addTermFAB clicked");

                BasicDatabase db = BasicDatabase.getInstance(getApplicationContext());
                int dbCount = db.databaseDao().getTermList().size() + 1;
                Term tempTerm1 = new Term();
                tempTerm1.setTerm_name("Term " + dbCount);
                db.databaseDao().insertTerm(tempTerm1);
                //ArrayAdapter<String> tempAdapter = listView.
                updateList();

            }
        });

// -------------- End FAB Stuff


    } // END onCreate


    private void updateList() { //This updates this listView on this mainActivity
        BasicDatabase db = BasicDatabase.getInstance(getApplicationContext());
        List<Term> allTerms = db.databaseDao().getTermList();
        System.out.println("Number of Rows in User Table: " + allTerms.size());



        String[] items = new String[allTerms.size()];
        if(!allTerms.isEmpty()){
            for (int i = 0; i < allTerms.size(); i++) {
                items[i] = allTerms.get(i).getTerm_name();
                System.out.println("Inside updateList Loop: " + i);

            }
        }
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("Item Clicked: " + position);
            }
        });
        adapter.notifyDataSetChanged();
    }
}
