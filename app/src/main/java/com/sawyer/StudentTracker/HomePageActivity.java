package com.sawyer.StudentTracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class HomePageActivity extends AppCompatActivity {
    public static String LOG_TAG = "HomePageAct";
    FullDatabase db;
    Button startButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        db = FullDatabase.getInstance(getApplicationContext());
        //-----------Start Button Functionality
        startButton = findViewById(R.id.startButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TermListActivity.class);
                startActivity(intent);
            }
        });
        //-----------End Start Button Functionality
        //-------- Create Nuke DB button programmatically
        ConstraintLayout myLayout = findViewById(R.id.homePageConstraintLayout);
        ConstraintSet set = new ConstraintSet();
        Button nukeDBButton = new Button(getApplicationContext());
        nukeDBButton.setText("Nuke DB Tables");
        nukeDBButton.setId(R.id.nukeDBButton);

        set.constrainHeight(nukeDBButton.getId(), ConstraintSet.WRAP_CONTENT);
        set.constrainWidth(nukeDBButton.getId(), ConstraintSet.WRAP_CONTENT);
        set.connect(nukeDBButton.getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM, 8);
        set.connect(nukeDBButton.getId(), ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT, 8);

        myLayout.addView(nukeDBButton);
        setContentView(myLayout);
        set.applyTo(myLayout);

        nukeDBButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(LOG_TAG, "Nuke DB Button Pressed");
                db.clearAllTables();
            }
        });
        //-------- End Create Nuke DB button programmatically
        //-------- Create Populate DB button programmatically
        //ConstraintLayout myLayout = findViewById(R.id.homePageConstraintLayout);
        //ConstraintSet set = new ConstraintSet();
        Button populateDBbutton = new Button(getApplicationContext());
        populateDBbutton.setText("Populate Database");
        populateDBbutton.setId(R.id.populateDBButton);

        set.constrainHeight(populateDBbutton.getId(), ConstraintSet.WRAP_CONTENT);
        set.constrainWidth(populateDBbutton.getId(), ConstraintSet.WRAP_CONTENT);
        set.connect(populateDBbutton.getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM, 8);
        set.connect(populateDBbutton.getId(), ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT, 8);

        myLayout.addView(populateDBbutton);
        setContentView(myLayout);
        set.applyTo(myLayout);

        populateDBbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(LOG_TAG, "populate DB Button Pressed");
            }
        });
        //-------- End Create Populate DB button programmatically
    }
}
