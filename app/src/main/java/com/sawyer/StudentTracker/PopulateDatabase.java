package com.sawyer.StudentTracker;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class PopulateDatabase extends AppCompatActivity {
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

    Calendar now = Calendar.getInstance();
    FullDatabase db = FullDatabase.getInstance(getApplicationContext());;

    public void populate() {
        insertTerms();
        insertCourses();
        insertAssessments();
        insertCourseMentors();
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
        tempTerm3.setTerm_name("2nd Term");
        tempTerm3.setTerm_start(start.getTime());
        tempTerm3.setTerm_end(end.getTime());

        db.termDao().insertAll(tempTerm1, tempTerm2, tempTerm3);


    }

    private void insertCourses() {
        //todo
    }

    private void insertAssessments() {
        //todo
    }

    private void insertCourseMentors() {
        //todo
    }


}
