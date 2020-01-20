package com.example.backendonly;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class TermDetailsActivity extends AppCompatActivity {
    ListView courseListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_details);

        TextView positionExtraView = findViewById(R.id.positionExtraView);
        Intent intent = getIntent();
        int position = intent.getIntExtra("position", 0);
        System.out.println(position);
        positionExtraView.setText(Integer.toString(position));
    }

    private void updateList() { //This updates the listView on this mainActivity
        FullDatabase db = FullDatabase.getInstance(getApplicationContext());
        List<Term> allTerms = db.databaseDao().getTermList();
        System.out.println("Number of Rows in User Table: " + allTerms.size());



        String[] items = new String[allTerms.size()];
        if(!allTerms.isEmpty()){
            for (int i = 0; i < allTerms.size(); i++) {
                items[i] = allTerms.get(i).getTerm_name();
                System.out.println("Inside updateList Loop: " + i);

            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        courseListView.setAdapter(adapter);

        adapter.notifyDataSetChanged();
    }
}
