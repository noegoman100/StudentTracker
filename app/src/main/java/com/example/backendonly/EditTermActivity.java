package com.example.backendonly;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;

public class EditTermActivity extends AppCompatActivity {
    Intent intent;
    int termId;
    FullDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_term);
        intent = getIntent();
        termId = intent.getIntExtra("termId", -1);
        setTitle("Edit Term for termId: " + termId);
        db = FullDatabase.getInstance(getApplicationContext());
        final EditText termNameEditText = findViewById(R.id.termNameEditText);
        final Term tempTerm = db.termDao().getTerm(termId);
        termNameEditText.setText(tempTerm.getTerm_name());
        Button saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tempTerm.setTerm_name(termNameEditText.getText().toString());
                db.termDao().updateTerm(tempTerm);
            }
        });
    }
}
