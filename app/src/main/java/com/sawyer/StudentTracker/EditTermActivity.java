package com.sawyer.StudentTracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class EditTermActivity extends AppCompatActivity {
    Intent intent;
    int termId;
    int courseId;
    int assessmentId;
    FullDatabase db;
    Term selectedTerm;
    SimpleDateFormat formatter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_term);
        intent = getIntent();
        termId = intent.getIntExtra("termId", -1);
        setTitle("Edit Term for termId: " + termId);
        db = FullDatabase.getInstance(getApplicationContext());
        selectedTerm = db.termDao().getTerm(termId);
        formatter = new SimpleDateFormat(getString(R.string.date_pattern));
     // ------- Views
        final EditText termNameEditText = findViewById(R.id.termNameEditText);
        termNameEditText.setText(selectedTerm.getTerm_name());
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
                selectedTerm.setTerm_name(termNameEditText.getText().toString());
                try {
                    Date tempDate = formatter.parse(termStartDate.getText().toString());
                    selectedTerm.setTerm_start(tempDate);
                    Date tempDateEnd = formatter.parse(termEndDate.getText().toString());
                    selectedTerm.setTerm_end(tempDateEnd);
                } catch (Exception e) {System.out.println("update term dates failed.");}

                db.termDao().updateTerm(selectedTerm);
                finish();
            }
        });
        Button deleteButton = findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!db.courseDao().getCourseList(termId).isEmpty()){
                    Toast.makeText(getApplicationContext(),"Courses Still Present",Toast.LENGTH_SHORT).show();
                    //todo send out a toast
                } else {
                    db.termDao().deleteTerm(selectedTerm);
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
            }
        });

     // ----------- End Buttons
    }
}
