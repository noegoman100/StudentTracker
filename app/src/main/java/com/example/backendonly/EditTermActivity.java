package com.example.backendonly;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Date;

public class EditTermActivity extends AppCompatActivity {
    Intent intent;
    int termId;
    int courseId;
    int taskId;
    FullDatabase db;
    Term tempTerm;
    SimpleDateFormat formatter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_term);
        intent = getIntent();
        termId = intent.getIntExtra("termId", -1);
        setTitle("Edit Term for termId: " + termId);
        db = FullDatabase.getInstance(getApplicationContext());
        tempTerm = db.termDao().getTerm(termId);
        formatter = new SimpleDateFormat("dd-MM-yyyy hh:mm");

     // ------- Views
        final EditText termNameEditText = findViewById(R.id.termNameEditText);
        termNameEditText.setText(tempTerm.getTerm_name());
        final EditText termStartDate = findViewById(R.id.termStartDate);
        final EditText termEndDate = findViewById(R.id.termEndDate);
        Date startDate = db.termDao().getTerm(termId).getTerm_start();
        System.out.println("startDateString: " + startDate);
        termStartDate.setText(formatter.format(startDate));
        Date endDate = db.termDao().getTerm(termId).getTerm_end();
        termEndDate.setText(formatter.format(endDate));
     // ------- End Views

     // ----------- Buttons
        Button saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tempTerm.setTerm_name(termNameEditText.getText().toString());
                try {
                    Date tempDate = formatter.parse(termStartDate.getText().toString());
                    tempTerm.setTerm_start(tempDate);
                    Date tempDateEnd = formatter.parse(termEndDate.getText().toString());
                    tempTerm.setTerm_end(tempDateEnd);
                } catch (Exception e) {System.out.println("update term dates failed.");}

                db.termDao().updateTerm(tempTerm);
                finish();
            }
        });
        Button deleteButton = findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.termDao().deleteTerm(tempTerm);
                finish();
            }
        });

     // ----------- End Buttons
    }
}
