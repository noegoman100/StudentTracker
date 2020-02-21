package com.sawyer.StudentTracker;

import android.content.Context;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.List;

public class PopulateDatabase extends AppCompatActivity {
    public static String LOG_TAG = "PopData";
    Term tempTerm1 = new Term();
    Term tempTerm2 = new Term();
    Term tempTerm3 = new Term();
    Course tempCourse1 = new Course();
    Course tempCourse2 = new Course();
    Course tempCourse3 = new Course();
    Assessment tempAssessment1 = new Assessment();
    Assessment tempAssessment2 = new Assessment();
    Assessment tempAssessment3 = new Assessment();
    Coursementor tempCourseMentor1 =  new Coursementor();
    Coursementor tempCourseMentor2 =  new Coursementor();
    Coursementor tempCourseMentor3 =  new Coursementor();

    FullDatabase db;

    public void populate(Context context) {
        db = FullDatabase.getInstance(context);
        try {
            insertTerms();
            insertCourses();
            insertAssessments();
            insertCourseMentors();
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(LOG_TAG, "Populate DB Failed");
        }

    }

    private void insertTerms() {
        Calendar start;
        Calendar end;

        start = Calendar.getInstance();
        end = Calendar.getInstance();
        start.add(Calendar.WEEK_OF_MONTH, -1);
        end.add(Calendar.WEEK_OF_MONTH, 2);
        tempTerm1.setTerm_name("My First Term");
        tempTerm1.setTerm_start(start.getTime());
        tempTerm1.setTerm_end(end.getTime());

        start = Calendar.getInstance();
        end = Calendar.getInstance();
        start.add(Calendar.WEEK_OF_MONTH, 3);
        end.add(Calendar.WEEK_OF_MONTH, 6);
        tempTerm2.setTerm_name("2nd Term");
        tempTerm2.setTerm_start(start.getTime());
        tempTerm2.setTerm_end(end.getTime());

        start = Calendar.getInstance();
        end = Calendar.getInstance();
        start.add(Calendar.WEEK_OF_MONTH, 7);
        end.add(Calendar.WEEK_OF_MONTH, 10);
        tempTerm3.setTerm_name("The Third Term");
        tempTerm3.setTerm_start(start.getTime());
        tempTerm3.setTerm_end(end.getTime());

        db.termDao().insertAll(tempTerm1, tempTerm2, tempTerm3);


    }

    private void insertCourses() {
        Calendar start;
        Calendar end;
        List<Term> termList = db.termDao().getTermList();
        if (termList == null) return;

        //<editor-fold desc="Term1 Courses">
        start = Calendar.getInstance();
        end = Calendar.getInstance();
        start.add(Calendar.WEEK_OF_MONTH, -2);
        end.add(Calendar.MINUTE, 5);
        tempCourse1.setCourse_name("First Course in Term");
        tempCourse1.setCourse_start(start.getTime());
        tempCourse1.setCourse_end(end.getTime());
        tempCourse1.setCourse_notes("PrePopulate Notes - data data data Notes Notes Notes");
        tempCourse1.setCourse_status("Active");
        tempCourse1.setTerm_id_fk(termList.get(0).getTerm_id());

        start = Calendar.getInstance();
        end = Calendar.getInstance();
        start.add(Calendar.WEEK_OF_MONTH, -1);
        end.add(Calendar.WEEK_OF_MONTH, 1);
        tempCourse2.setCourse_name("Second Course in Term");
        tempCourse2.setCourse_start(start.getTime());
        tempCourse2.setCourse_end(end.getTime());
        tempCourse2.setCourse_notes("PrePopulate Notes - data data data Notes Notes Notes");
        tempCourse2.setCourse_status("Active");
        tempCourse2.setTerm_id_fk(termList.get(0).getTerm_id());

        start = Calendar.getInstance();
        end = Calendar.getInstance();
        start.add(Calendar.WEEK_OF_MONTH, 1);
        end.add(Calendar.WEEK_OF_MONTH, 2);
        tempCourse3.setCourse_name("Third Course in Term");
        tempCourse3.setCourse_start(start.getTime());
        tempCourse3.setCourse_end(end.getTime());
        tempCourse3.setCourse_notes("PrePopulate Notes - data data data Notes Notes Notes");
        tempCourse3.setCourse_status("Active");
        tempCourse3.setTerm_id_fk(termList.get(0).getTerm_id());
        //</editor-fold>
        db.courseDao().insertCourse(tempCourse1);
        db.courseDao().insertCourse(tempCourse2);
        db.courseDao().insertCourse(tempCourse3);
        //<editor-fold desc="Term2 Courses">
        start = Calendar.getInstance();
        end = Calendar.getInstance();
        start.add(Calendar.WEEK_OF_MONTH, 3); //offset
        end.add(Calendar.WEEK_OF_MONTH, 3); //offset
        start.add(Calendar.WEEK_OF_MONTH, -2);
        end.add(Calendar.MINUTE, 5);
        tempCourse1.setCourse_name("First Course in Term");
        tempCourse1.setCourse_start(start.getTime());
        tempCourse1.setCourse_end(end.getTime());
        tempCourse1.setCourse_notes("PrePopulate Notes - data data data Notes Notes Notes");
        tempCourse1.setCourse_status("Active");
        tempCourse1.setTerm_id_fk(termList.get(1).getTerm_id());

        start = Calendar.getInstance();
        end = Calendar.getInstance();
        start.add(Calendar.WEEK_OF_MONTH, 3); //offset
        end.add(Calendar.WEEK_OF_MONTH, 3); //offset
        start.add(Calendar.WEEK_OF_MONTH, -1);
        end.add(Calendar.WEEK_OF_MONTH, 1);
        tempCourse2.setCourse_name("Second Course in Term");
        tempCourse2.setCourse_start(start.getTime());
        tempCourse2.setCourse_end(end.getTime());
        tempCourse2.setCourse_notes("PrePopulate Notes - data data data Notes Notes Notes");
        tempCourse2.setCourse_status("Active");
        tempCourse2.setTerm_id_fk(termList.get(1).getTerm_id());

        start = Calendar.getInstance();
        end = Calendar.getInstance();
        start.add(Calendar.WEEK_OF_MONTH, 3); //offset
        end.add(Calendar.WEEK_OF_MONTH, 3); //offset
        start.add(Calendar.WEEK_OF_MONTH, 1);
        end.add(Calendar.WEEK_OF_MONTH, 2);
        tempCourse3.setCourse_name("Third Course in Term");
        tempCourse3.setCourse_start(start.getTime());
        tempCourse3.setCourse_end(end.getTime());
        tempCourse3.setCourse_notes("PrePopulate Notes - data data data Notes Notes Notes");
        tempCourse3.setCourse_status("Active");
        tempCourse3.setTerm_id_fk(termList.get(1).getTerm_id());
        //</editor-fold>
        db.courseDao().insertCourse(tempCourse1);
        db.courseDao().insertCourse(tempCourse2);
        db.courseDao().insertCourse(tempCourse3);
        //<editor-fold desc="Term3 Courses">
        start = Calendar.getInstance();
        end = Calendar.getInstance();
        start.add(Calendar.WEEK_OF_MONTH, 6); //offset
        end.add(Calendar.WEEK_OF_MONTH, 6); //offset
        start.add(Calendar.WEEK_OF_MONTH, -2);
        end.add(Calendar.MINUTE, 5);
        tempCourse1.setCourse_name("First Course in Term");
        tempCourse1.setCourse_start(start.getTime());
        tempCourse1.setCourse_end(end.getTime());
        tempCourse1.setCourse_notes("PrePopulate Notes - data data data Notes Notes Notes");
        tempCourse1.setCourse_status("Active");
        tempCourse1.setTerm_id_fk(termList.get(2).getTerm_id());

        start = Calendar.getInstance();
        end = Calendar.getInstance();
        start.add(Calendar.WEEK_OF_MONTH, 6); //offset
        end.add(Calendar.WEEK_OF_MONTH, 6); //offset
        start.add(Calendar.WEEK_OF_MONTH, -1);
        end.add(Calendar.WEEK_OF_MONTH, 1);
        tempCourse2.setCourse_name("Second Course in Term");
        tempCourse2.setCourse_start(start.getTime());
        tempCourse2.setCourse_end(end.getTime());
        tempCourse2.setCourse_notes("PrePopulate Notes - data data data Notes Notes Notes");
        tempCourse2.setCourse_status("Active");
        tempCourse2.setTerm_id_fk(termList.get(2).getTerm_id());

        start = Calendar.getInstance();
        end = Calendar.getInstance();
        start.add(Calendar.WEEK_OF_MONTH, 6); //offset
        end.add(Calendar.WEEK_OF_MONTH, 6); //offset
        start.add(Calendar.WEEK_OF_MONTH, 1);
        end.add(Calendar.WEEK_OF_MONTH, 2);
        tempCourse3.setCourse_name("Third Course in Term");
        tempCourse3.setCourse_start(start.getTime());
        tempCourse3.setCourse_end(end.getTime());
        tempCourse3.setCourse_notes("PrePopulate Notes - data data data Notes Notes Notes");
        tempCourse3.setCourse_status("Active");
        tempCourse3.setTerm_id_fk(termList.get(2).getTerm_id());
        //</editor-fold>
        db.courseDao().insertCourse(tempCourse1);
        db.courseDao().insertCourse(tempCourse2);
        db.courseDao().insertCourse(tempCourse3);

        //todo
    }

    private void insertAssessments() {
        //todo
    }

    private void insertCourseMentors() {
        //todo
    }


}
