package com.example.backendonly;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class EditTaskActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);
        setTitle("Edit Task");
    }
}
