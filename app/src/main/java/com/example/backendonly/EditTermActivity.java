package com.example.backendonly;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class EditTermActivity extends AppCompatActivity {
    Intent intent;
    int termId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_term);
        intent = getIntent();
        termId = intent.getIntExtra("termId", -1);
        setTitle("Edit Term for termId: " + termId);
    }
}
