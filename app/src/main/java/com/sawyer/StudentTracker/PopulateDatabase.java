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
            insertCoursementors();
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
        start.add(Calendar.MONTH, -2);
        end.add(Calendar.MONTH, 1);
        tempTerm1.setTerm_name("My First Term");
        tempTerm1.setTerm_start(start.getTime());
        tempTerm1.setTerm_end(end.getTime());

        start = Calendar.getInstance();
        end = Calendar.getInstance();
        start.add(Calendar.MONTH, 2);
        end.add(Calendar.MONTH, 5);
        tempTerm2.setTerm_name("2nd Term");
        tempTerm2.setTerm_start(start.getTime());
        tempTerm2.setTerm_end(end.getTime());

        start = Calendar.getInstance();
        end = Calendar.getInstance();
        start.add(Calendar.MONTH, 6);
        end.add(Calendar.MONTH, 9);
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
        start.add(Calendar.MONTH, -2);
        end.add(Calendar.MONTH, -1);
        tempCourse1.setCourse_name("First Course in Term");
        tempCourse1.setCourse_start(start.getTime());
        tempCourse1.setCourse_end(end.getTime());
        tempCourse1.setCourse_notes("PrePopulate Notes - data data data Notes Notes Notes");
        tempCourse1.setCourse_status("Completed");
        tempCourse1.setTerm_id_fk(termList.get(0).getTerm_id());

        start = Calendar.getInstance();
        end = Calendar.getInstance();
        start.add(Calendar.MONTH, -1);
        //end.add(Calendar.WEEK_OF_MONTH, 1);
        tempCourse2.setCourse_name("Second Course in Term");
        tempCourse2.setCourse_start(start.getTime());
        tempCourse2.setCourse_end(end.getTime());
        tempCourse2.setCourse_notes("PrePopulate Notes - data data data Notes Notes Notes");
        tempCourse2.setCourse_status("Completed");
        tempCourse2.setTerm_id_fk(termList.get(0).getTerm_id());

        start = Calendar.getInstance();
        end = Calendar.getInstance();
        //start.add(Calendar.WEEK_OF_MONTH, 1);
        end.add(Calendar.MONTH, 1);
        tempCourse3.setCourse_name("Third Course in Term");
        tempCourse3.setCourse_start(start.getTime());
        tempCourse3.setCourse_end(end.getTime());
        tempCourse3.setCourse_notes("PrePopulate Notes - data data data Notes Notes Notes");
        tempCourse3.setCourse_status("In-Progress");
        tempCourse3.setTerm_id_fk(termList.get(0).getTerm_id());
        //</editor-fold>
        db.courseDao().insertCourse(tempCourse1);
        db.courseDao().insertCourse(tempCourse2);
        db.courseDao().insertCourse(tempCourse3);
        //<editor-fold desc="Term2 Courses">
        start = Calendar.getInstance();
        end = Calendar.getInstance();
        //start.add(Calendar.WEEK_OF_MONTH, 3); //offset
        //end.add(Calendar.WEEK_OF_MONTH, 3); //offset
        start.add(Calendar.MONTH, 2);
        end.add(Calendar.MONTH, 3);
        tempCourse1.setCourse_name("First Course in Term");
        tempCourse1.setCourse_start(start.getTime());
        tempCourse1.setCourse_end(end.getTime());
        tempCourse1.setCourse_notes("PrePopulate Notes - data data data Notes Notes Notes");
        tempCourse1.setCourse_status("Pending");
        tempCourse1.setTerm_id_fk(termList.get(1).getTerm_id());

        start = Calendar.getInstance();
        end = Calendar.getInstance();
        //start.add(Calendar.WEEK_OF_MONTH, 3); //offset
        //end.add(Calendar.WEEK_OF_MONTH, 3); //offset
        start.add(Calendar.MONTH, 3);
        end.add(Calendar.MONTH, 4);
        tempCourse2.setCourse_name("Second Course in Term");
        tempCourse2.setCourse_start(start.getTime());
        tempCourse2.setCourse_end(end.getTime());
        tempCourse2.setCourse_notes("PrePopulate Notes - data data data Notes Notes Notes");
        tempCourse2.setCourse_status("Pending");
        tempCourse2.setTerm_id_fk(termList.get(1).getTerm_id());

        start = Calendar.getInstance();
        end = Calendar.getInstance();
        //start.add(Calendar.WEEK_OF_MONTH, 3); //offset
        //end.add(Calendar.WEEK_OF_MONTH, 3); //offset
        start.add(Calendar.MONTH, 4);
        end.add(Calendar.MONTH, 5);
        tempCourse3.setCourse_name("Third Course in Term");
        tempCourse3.setCourse_start(start.getTime());
        tempCourse3.setCourse_end(end.getTime());
        tempCourse3.setCourse_notes("PrePopulate Notes - data data data Notes Notes Notes");
        tempCourse3.setCourse_status("Pending");
        tempCourse3.setTerm_id_fk(termList.get(1).getTerm_id());
        //</editor-fold>
        db.courseDao().insertCourse(tempCourse1);
        db.courseDao().insertCourse(tempCourse2);
        db.courseDao().insertCourse(tempCourse3);
        //<editor-fold desc="Term3 Courses">
        start = Calendar.getInstance();
        end = Calendar.getInstance();
        //start.add(Calendar.WEEK_OF_MONTH, 6); //offset
        //end.add(Calendar.WEEK_OF_MONTH, 6); //offset
        start.add(Calendar.MONTH, 6);
        end.add(Calendar.MONTH, 7);
        tempCourse1.setCourse_name("First Course in Term");
        tempCourse1.setCourse_start(start.getTime());
        tempCourse1.setCourse_end(end.getTime());
        tempCourse1.setCourse_notes("PrePopulate Notes - data data data Notes Notes Notes");
        tempCourse1.setCourse_status("Pending");
        tempCourse1.setTerm_id_fk(termList.get(2).getTerm_id());

        start = Calendar.getInstance();
        end = Calendar.getInstance();
        //start.add(Calendar.WEEK_OF_MONTH, 6); //offset
        //end.add(Calendar.WEEK_OF_MONTH, 6); //offset
        start.add(Calendar.MONTH, 7);
        end.add(Calendar.MONTH, 8);
        tempCourse2.setCourse_name("Second Course in Term");
        tempCourse2.setCourse_start(start.getTime());
        tempCourse2.setCourse_end(end.getTime());
        tempCourse2.setCourse_notes("PrePopulate Notes - data data data Notes Notes Notes");
        tempCourse2.setCourse_status("Pending");
        tempCourse2.setTerm_id_fk(termList.get(2).getTerm_id());

        start = Calendar.getInstance();
        end = Calendar.getInstance();
        //start.add(Calendar.WEEK_OF_MONTH, 6); //offset
        //end.add(Calendar.WEEK_OF_MONTH, 6); //offset
        start.add(Calendar.MONTH, 8);
        end.add(Calendar.MONTH, 9);
        tempCourse3.setCourse_name("Third Course in Term");
        tempCourse3.setCourse_start(start.getTime());
        tempCourse3.setCourse_end(end.getTime());
        tempCourse3.setCourse_notes("PrePopulate Notes - data data data Notes Notes Notes");
        tempCourse3.setCourse_status("Pending");
        tempCourse3.setTerm_id_fk(termList.get(2).getTerm_id());
        //</editor-fold>
        db.courseDao().insertCourse(tempCourse1);
        db.courseDao().insertCourse(tempCourse2);
        db.courseDao().insertCourse(tempCourse3);
    }

    private void insertAssessments() {
        List<Term> termList;
        List<Course> courseList;
        termList = db.termDao().getTermList();
        Calendar tempCalendar;

        //<editor-fold desc="Term1Course1">
        courseList = db.courseDao().getCourseList(termList.get(0).getTerm_id()); //Select term 0,1,2
        tempAssessment1.setCourse_id_fk(courseList.get(0).getCourse_id()); //Select Course 0,1,2
        tempAssessment1.setAssessment_name("Assessment 1");
        tempAssessment1.setAssessment_info("Info about this assignment");
        tempAssessment1.setAssessment_type("Performance");
        tempAssessment1.setAssessment_status("Pending");
        tempCalendar = Calendar.getInstance();
        //tempCalendar.add(Calendar.MONTH, 2) //Offset
        tempCalendar.add(Calendar.MONTH, -2);
        tempCalendar.add(Calendar.WEEK_OF_YEAR, 1);
        tempAssessment1.setAssessment_due(tempCalendar.getTime());
        tempAssessment1.setAssessment_alert_name("Alert For First Assessment");
        db.assessmentDao().insertAssessment(tempAssessment1);
        tempAssessment2.setCourse_id_fk(courseList.get(0).getCourse_id());//Select Course 0,1,2
        tempAssessment2.setAssessment_name("Assessment 2");
        tempAssessment2.setAssessment_info("Info about this assignment");
        tempAssessment2.setAssessment_type("Objective");
        tempAssessment2.setAssessment_status("Pending");
        tempCalendar = Calendar.getInstance();
        //tempCalendar.add(Calendar.MONTH, 2) //Offset
        tempCalendar.add(Calendar.MONTH, -2);
        tempCalendar.add(Calendar.WEEK_OF_YEAR, 2);
        tempAssessment2.setAssessment_due(tempCalendar.getTime());
        tempAssessment2.setAssessment_alert_name("Alert 2");
        db.assessmentDao().insertAssessment(tempAssessment2);
        tempAssessment3.setCourse_id_fk(courseList.get(0).getCourse_id());//Select Course 0,1,2
        tempAssessment3.setAssessment_name("Assessment 3");
        tempAssessment3.setAssessment_info("Info about this assignment");
        tempAssessment3.setAssessment_type("Performance");
        tempAssessment3.setAssessment_status("Pending");
        tempCalendar = Calendar.getInstance();
        //tempCalendar.add(Calendar.MONTH, 2) //Offset
        tempCalendar.add(Calendar.MONTH, -1);
        //tempCalendar.add(Calendar.WEEK_OF_YEAR, 2);
        tempAssessment3.setAssessment_due(tempCalendar.getTime());
        tempAssessment3.setAssessment_alert_name("Alert 3");
        db.assessmentDao().insertAssessment(tempAssessment3);
        //</editor-fold>
        //<editor-fold desc="Term1Course2">
        courseList = db.courseDao().getCourseList(termList.get(0).getTerm_id()); //Select term 0,1,2
        tempAssessment1.setCourse_id_fk(courseList.get(1).getCourse_id()); //Select Course 0,1,2
        tempAssessment1.setAssessment_name("Assessment 1");
        tempAssessment1.setAssessment_info("Info about this assignment");
        tempAssessment1.setAssessment_type("Performance");
        tempAssessment1.setAssessment_status("Pending");
        tempCalendar = Calendar.getInstance();
        tempCalendar.add(Calendar.MONTH, 1);//Offset
        tempCalendar.add(Calendar.MONTH, -2);
        tempCalendar.add(Calendar.WEEK_OF_YEAR, 1);
        tempAssessment1.setAssessment_due(tempCalendar.getTime());
        tempAssessment1.setAssessment_alert_name("Alert For First Assessment");
        db.assessmentDao().insertAssessment(tempAssessment1);
        tempAssessment2.setCourse_id_fk(courseList.get(1).getCourse_id());//Select Course 0,1,2
        tempAssessment2.setAssessment_name("Assessment 2");
        tempAssessment2.setAssessment_info("Info about this assignment");
        tempAssessment2.setAssessment_type("Objective");
        tempAssessment2.setAssessment_status("Pending");
        tempCalendar = Calendar.getInstance();
        tempCalendar.add(Calendar.MONTH, 1);//Offset
        tempCalendar.add(Calendar.MONTH, -2);
        tempCalendar.add(Calendar.WEEK_OF_YEAR, 2);
        tempAssessment2.setAssessment_due(tempCalendar.getTime());
        tempAssessment2.setAssessment_alert_name("Alert 2");
        db.assessmentDao().insertAssessment(tempAssessment2);
        tempAssessment3.setCourse_id_fk(courseList.get(1).getCourse_id());//Select Course 0,1,2
        tempAssessment3.setAssessment_name("Assessment 3");
        tempAssessment3.setAssessment_info("Info about this assignment");
        tempAssessment3.setAssessment_type("Performance");
        tempAssessment3.setAssessment_status("Pending");
        tempCalendar = Calendar.getInstance();
        tempCalendar.add(Calendar.MONTH, 1);//Offset
        tempCalendar.add(Calendar.MONTH, -1);
        //tempCalendar.add(Calendar.WEEK_OF_YEAR, 2);
        tempAssessment3.setAssessment_due(tempCalendar.getTime());
        tempAssessment3.setAssessment_alert_name("Alert 3");
        db.assessmentDao().insertAssessment(tempAssessment3);
        //</editor-fold>
        //<editor-fold desc="Term1Course3">
        courseList = db.courseDao().getCourseList(termList.get(0).getTerm_id()); //Select term 0,1,2
        tempAssessment1.setCourse_id_fk(courseList.get(2).getCourse_id()); //Select Course 0,1,2
        tempAssessment1.setAssessment_name("Assessment 1");
        tempAssessment1.setAssessment_info("Info about this assignment");
        tempAssessment1.setAssessment_type("Performance");
        tempAssessment1.setAssessment_status("Pending");
        tempCalendar = Calendar.getInstance();
        tempCalendar.add(Calendar.MONTH, 2);//Offset
        tempCalendar.add(Calendar.MONTH, -2);
        tempCalendar.add(Calendar.WEEK_OF_YEAR, 1);
        tempAssessment1.setAssessment_due(tempCalendar.getTime());
        tempAssessment1.setAssessment_alert_name("Alert For First Assessment");
        db.assessmentDao().insertAssessment(tempAssessment1);
        tempAssessment2.setCourse_id_fk(courseList.get(2).getCourse_id());//Select Course 0,1,2
        tempAssessment2.setAssessment_name("Assessment 2");
        tempAssessment2.setAssessment_info("Info about this assignment");
        tempAssessment2.setAssessment_type("Objective");
        tempAssessment2.setAssessment_status("Pending");
        tempCalendar = Calendar.getInstance();
        tempCalendar.add(Calendar.MONTH, 2);//Offset
        tempCalendar.add(Calendar.MONTH, -2);
        tempCalendar.add(Calendar.WEEK_OF_YEAR, 2);
        tempAssessment2.setAssessment_due(tempCalendar.getTime());
        tempAssessment2.setAssessment_alert_name("Alert 2");
        db.assessmentDao().insertAssessment(tempAssessment2);
        tempAssessment3.setCourse_id_fk(courseList.get(2).getCourse_id());//Select Course 0,1,2
        tempAssessment3.setAssessment_name("Assessment 3");
        tempAssessment3.setAssessment_info("Info about this assignment");
        tempAssessment3.setAssessment_type("Performance");
        tempAssessment3.setAssessment_status("Pending");
        tempCalendar = Calendar.getInstance();
        tempCalendar.add(Calendar.MONTH, 2);//Offset
        tempCalendar.add(Calendar.MONTH, -1);
        //tempCalendar.add(Calendar.WEEK_OF_YEAR, 2);
        tempAssessment3.setAssessment_due(tempCalendar.getTime());
        tempAssessment3.setAssessment_alert_name("Alert 3");
        db.assessmentDao().insertAssessment(tempAssessment3);
        //</editor-fold>

        //<editor-fold desc="Term2Course1">
        courseList = db.courseDao().getCourseList(termList.get(1).getTerm_id()); //Select term 0,1,2
        tempAssessment1.setCourse_id_fk(courseList.get(0).getCourse_id()); //Select Course 0,1,2
        tempAssessment1.setAssessment_name("Assessment 1");
        tempAssessment1.setAssessment_info("Info about this assignment");
        tempAssessment1.setAssessment_type("Performance");
        tempAssessment1.setAssessment_status("Pending");
        tempCalendar = Calendar.getInstance();
        tempCalendar.add(Calendar.MONTH, 4);//Offset
        tempCalendar.add(Calendar.MONTH, -2);
        tempCalendar.add(Calendar.WEEK_OF_YEAR, 1);
        tempAssessment1.setAssessment_due(tempCalendar.getTime());
        tempAssessment1.setAssessment_alert_name("Alert For First Assessment");
        db.assessmentDao().insertAssessment(tempAssessment1);
        tempAssessment2.setCourse_id_fk(courseList.get(0).getCourse_id());//Select Course 0,1,2
        tempAssessment2.setAssessment_name("Assessment 2");
        tempAssessment2.setAssessment_info("Info about this assignment");
        tempAssessment2.setAssessment_type("Objective");
        tempAssessment2.setAssessment_status("Pending");
        tempCalendar = Calendar.getInstance();
        tempCalendar.add(Calendar.MONTH, 4);//Offset
        tempCalendar.add(Calendar.MONTH, -2);
        tempCalendar.add(Calendar.WEEK_OF_YEAR, 2);
        tempAssessment2.setAssessment_due(tempCalendar.getTime());
        tempAssessment2.setAssessment_alert_name("Alert 2");
        db.assessmentDao().insertAssessment(tempAssessment2);
        tempAssessment3.setCourse_id_fk(courseList.get(0).getCourse_id());//Select Course 0,1,2
        tempAssessment3.setAssessment_name("Assessment 3");
        tempAssessment3.setAssessment_info("Info about this assignment");
        tempAssessment3.setAssessment_type("Performance");
        tempAssessment3.setAssessment_status("Pending");
        tempCalendar = Calendar.getInstance();
        tempCalendar.add(Calendar.MONTH, 4);//Offset
        tempCalendar.add(Calendar.MONTH, -1);
        //tempCalendar.add(Calendar.WEEK_OF_YEAR, 2);
        tempAssessment3.setAssessment_due(tempCalendar.getTime());
        tempAssessment3.setAssessment_alert_name("Alert 3");
        db.assessmentDao().insertAssessment(tempAssessment3);
        //</editor-fold>
        //<editor-fold desc="Term2Course2">
        courseList = db.courseDao().getCourseList(termList.get(1).getTerm_id()); //Select term 0,1,2
        tempAssessment1.setCourse_id_fk(courseList.get(1).getCourse_id()); //Select Course 0,1,2
        tempAssessment1.setAssessment_name("Assessment 1");
        tempAssessment1.setAssessment_info("Info about this assignment");
        tempAssessment1.setAssessment_type("Performance");
        tempAssessment1.setAssessment_status("Pending");
        tempCalendar = Calendar.getInstance();
        tempCalendar.add(Calendar.MONTH, 5);//Offset
        tempCalendar.add(Calendar.MONTH, -2);
        tempCalendar.add(Calendar.WEEK_OF_YEAR, 1);
        tempAssessment1.setAssessment_due(tempCalendar.getTime());
        tempAssessment1.setAssessment_alert_name("Alert For First Assessment");
        db.assessmentDao().insertAssessment(tempAssessment1);
        tempAssessment2.setCourse_id_fk(courseList.get(1).getCourse_id());//Select Course 0,1,2
        tempAssessment2.setAssessment_name("Assessment 2");
        tempAssessment2.setAssessment_info("Info about this assignment");
        tempAssessment2.setAssessment_type("Objective");
        tempAssessment2.setAssessment_status("Pending");
        tempCalendar = Calendar.getInstance();
        tempCalendar.add(Calendar.MONTH, 5);//Offset
        tempCalendar.add(Calendar.MONTH, -2);
        tempCalendar.add(Calendar.WEEK_OF_YEAR, 2);
        tempAssessment2.setAssessment_due(tempCalendar.getTime());
        tempAssessment2.setAssessment_alert_name("Alert 2");
        db.assessmentDao().insertAssessment(tempAssessment2);
        tempAssessment3.setCourse_id_fk(courseList.get(1).getCourse_id());//Select Course 0,1,2
        tempAssessment3.setAssessment_name("Assessment 3");
        tempAssessment3.setAssessment_info("Info about this assignment");
        tempAssessment3.setAssessment_type("Performance");
        tempAssessment3.setAssessment_status("Pending");
        tempCalendar = Calendar.getInstance();
        tempCalendar.add(Calendar.MONTH, 5);//Offset
        tempCalendar.add(Calendar.MONTH, -1);
        //tempCalendar.add(Calendar.WEEK_OF_YEAR, 2);
        tempAssessment3.setAssessment_due(tempCalendar.getTime());
        tempAssessment3.setAssessment_alert_name("Alert 3");
        db.assessmentDao().insertAssessment(tempAssessment3);
        //</editor-fold>
        //<editor-fold desc="Term2Course3">
        courseList = db.courseDao().getCourseList(termList.get(1).getTerm_id()); //Select term 0,1,2
        tempAssessment1.setCourse_id_fk(courseList.get(2).getCourse_id()); //Select Course 0,1,2
        tempAssessment1.setAssessment_name("Assessment 1");
        tempAssessment1.setAssessment_info("Info about this assignment");
        tempAssessment1.setAssessment_type("Performance");
        tempAssessment1.setAssessment_status("Pending");
        tempCalendar = Calendar.getInstance();
        tempCalendar.add(Calendar.MONTH, 6);//Offset
        tempCalendar.add(Calendar.MONTH, -2);
        tempCalendar.add(Calendar.WEEK_OF_YEAR, 1);
        tempAssessment1.setAssessment_due(tempCalendar.getTime());
        tempAssessment1.setAssessment_alert_name("Alert For First Assessment");
        db.assessmentDao().insertAssessment(tempAssessment1);
        tempAssessment2.setCourse_id_fk(courseList.get(2).getCourse_id());//Select Course 0,1,2
        tempAssessment2.setAssessment_name("Assessment 2");
        tempAssessment2.setAssessment_info("Info about this assignment");
        tempAssessment2.setAssessment_type("Objective");
        tempAssessment2.setAssessment_status("Pending");
        tempCalendar = Calendar.getInstance();
        tempCalendar.add(Calendar.MONTH, 6);//Offset
        tempCalendar.add(Calendar.MONTH, -2);
        tempCalendar.add(Calendar.WEEK_OF_YEAR, 2);
        tempAssessment2.setAssessment_due(tempCalendar.getTime());
        tempAssessment2.setAssessment_alert_name("Alert 2");
        db.assessmentDao().insertAssessment(tempAssessment2);
        tempAssessment3.setCourse_id_fk(courseList.get(2).getCourse_id());//Select Course 0,1,2
        tempAssessment3.setAssessment_name("Assessment 3");
        tempAssessment3.setAssessment_info("Info about this assignment");
        tempAssessment3.setAssessment_type("Performance");
        tempAssessment3.setAssessment_status("Pending");
        tempCalendar = Calendar.getInstance();
        tempCalendar.add(Calendar.MONTH, 6);//Offset
        tempCalendar.add(Calendar.MONTH, -1);
        //tempCalendar.add(Calendar.WEEK_OF_YEAR, 2);
        tempAssessment3.setAssessment_due(tempCalendar.getTime());
        tempAssessment3.setAssessment_alert_name("Alert 3");
        db.assessmentDao().insertAssessment(tempAssessment3);
        //</editor-fold>

        //<editor-fold desc="Term3Course1">
        courseList = db.courseDao().getCourseList(termList.get(2).getTerm_id()); //Select term 0,1,2
        tempAssessment1.setCourse_id_fk(courseList.get(0).getCourse_id()); //Select Course 0,1,2
        tempAssessment1.setAssessment_name("Assessment 1");
        tempAssessment1.setAssessment_info("Info about this assignment");
        tempAssessment1.setAssessment_type("Performance");
        tempAssessment1.setAssessment_status("Pending");
        tempCalendar = Calendar.getInstance();
        tempCalendar.add(Calendar.MONTH, 8);//Offset
        tempCalendar.add(Calendar.MONTH, -2);
        tempCalendar.add(Calendar.WEEK_OF_YEAR, 1);
        tempAssessment1.setAssessment_due(tempCalendar.getTime());
        tempAssessment1.setAssessment_alert_name("Alert For First Assessment");
        db.assessmentDao().insertAssessment(tempAssessment1);
        tempAssessment2.setCourse_id_fk(courseList.get(0).getCourse_id());//Select Course 0,1,2
        tempAssessment2.setAssessment_name("Assessment 2");
        tempAssessment2.setAssessment_info("Info about this assignment");
        tempAssessment2.setAssessment_type("Objective");
        tempAssessment2.setAssessment_status("Pending");
        tempCalendar = Calendar.getInstance();
        tempCalendar.add(Calendar.MONTH, 8);//Offset
        tempCalendar.add(Calendar.MONTH, -2);
        tempCalendar.add(Calendar.WEEK_OF_YEAR, 2);
        tempAssessment2.setAssessment_due(tempCalendar.getTime());
        tempAssessment2.setAssessment_alert_name("Alert 2");
        db.assessmentDao().insertAssessment(tempAssessment2);
        tempAssessment3.setCourse_id_fk(courseList.get(0).getCourse_id());//Select Course 0,1,2
        tempAssessment3.setAssessment_name("Assessment 3");
        tempAssessment3.setAssessment_info("Info about this assignment");
        tempAssessment3.setAssessment_type("Performance");
        tempAssessment3.setAssessment_status("Pending");
        tempCalendar = Calendar.getInstance();
        tempCalendar.add(Calendar.MONTH, 8);//Offset
        tempCalendar.add(Calendar.MONTH, -1);
        //tempCalendar.add(Calendar.WEEK_OF_YEAR, 2);
        tempAssessment3.setAssessment_due(tempCalendar.getTime());
        tempAssessment3.setAssessment_alert_name("Alert 3");
        db.assessmentDao().insertAssessment(tempAssessment3);
        //</editor-fold>
        //<editor-fold desc="Term3Course2">
        courseList = db.courseDao().getCourseList(termList.get(2).getTerm_id()); //Select term 0,1,2
        tempAssessment1.setCourse_id_fk(courseList.get(1).getCourse_id()); //Select Course 0,1,2
        tempAssessment1.setAssessment_name("Assessment 1");
        tempAssessment1.setAssessment_info("Info about this assignment");
        tempAssessment1.setAssessment_type("Performance");
        tempAssessment1.setAssessment_status("Pending");
        tempCalendar = Calendar.getInstance();
        tempCalendar.add(Calendar.MONTH, 9);//Offset
        tempCalendar.add(Calendar.MONTH, -2);
        tempCalendar.add(Calendar.WEEK_OF_YEAR, 1);
        tempAssessment1.setAssessment_due(tempCalendar.getTime());
        tempAssessment1.setAssessment_alert_name("Alert For First Assessment");
        db.assessmentDao().insertAssessment(tempAssessment1);
        tempAssessment2.setCourse_id_fk(courseList.get(1).getCourse_id());//Select Course 0,1,2
        tempAssessment2.setAssessment_name("Assessment 2");
        tempAssessment2.setAssessment_info("Info about this assignment");
        tempAssessment2.setAssessment_type("Objective");
        tempAssessment2.setAssessment_status("Pending");
        tempCalendar = Calendar.getInstance();
        tempCalendar.add(Calendar.MONTH, 9);//Offset
        tempCalendar.add(Calendar.MONTH, -2);
        tempCalendar.add(Calendar.WEEK_OF_YEAR, 2);
        tempAssessment2.setAssessment_due(tempCalendar.getTime());
        tempAssessment2.setAssessment_alert_name("Alert 2");
        db.assessmentDao().insertAssessment(tempAssessment2);
        tempAssessment3.setCourse_id_fk(courseList.get(1).getCourse_id());//Select Course 0,1,2
        tempAssessment3.setAssessment_name("Assessment 3");
        tempAssessment3.setAssessment_info("Info about this assignment");
        tempAssessment3.setAssessment_type("Performance");
        tempAssessment3.setAssessment_status("Pending");
        tempCalendar = Calendar.getInstance();
        tempCalendar.add(Calendar.MONTH, 9);//Offset
        tempCalendar.add(Calendar.MONTH, -1);
        //tempCalendar.add(Calendar.WEEK_OF_YEAR, 2);
        tempAssessment3.setAssessment_due(tempCalendar.getTime());
        tempAssessment3.setAssessment_alert_name("Alert 3");
        db.assessmentDao().insertAssessment(tempAssessment3);
        //</editor-fold>
        //<editor-fold desc="Term3Course3">
        courseList = db.courseDao().getCourseList(termList.get(2).getTerm_id()); //Select term 0,1,2
        tempAssessment1.setCourse_id_fk(courseList.get(2).getCourse_id()); //Select Course 0,1,2
        tempAssessment1.setAssessment_name("Assessment 1");
        tempAssessment1.setAssessment_info("Info about this assignment");
        tempAssessment1.setAssessment_type("Performance");
        tempAssessment1.setAssessment_status("Pending");
        tempCalendar = Calendar.getInstance();
        tempCalendar.add(Calendar.MONTH, 10);//Offset
        tempCalendar.add(Calendar.MONTH, -2);
        tempCalendar.add(Calendar.WEEK_OF_YEAR, 1);
        tempAssessment1.setAssessment_due(tempCalendar.getTime());
        tempAssessment1.setAssessment_alert_name("Alert For First Assessment");
        db.assessmentDao().insertAssessment(tempAssessment1);
        tempAssessment2.setCourse_id_fk(courseList.get(2).getCourse_id());//Select Course 0,1,2
        tempAssessment2.setAssessment_name("Assessment 2");
        tempAssessment2.setAssessment_info("Info about this assignment");
        tempAssessment2.setAssessment_type("Objective");
        tempAssessment2.setAssessment_status("Pending");
        tempCalendar = Calendar.getInstance();
        tempCalendar.add(Calendar.MONTH, 10);//Offset
        tempCalendar.add(Calendar.MONTH, -2);
        tempCalendar.add(Calendar.WEEK_OF_YEAR, 2);
        tempAssessment2.setAssessment_due(tempCalendar.getTime());
        tempAssessment2.setAssessment_alert_name("Alert 2");
        db.assessmentDao().insertAssessment(tempAssessment2);
        tempAssessment3.setCourse_id_fk(courseList.get(2).getCourse_id());//Select Course 0,1,2
        tempAssessment3.setAssessment_name("Assessment 3");
        tempAssessment3.setAssessment_info("Info about this assignment");
        tempAssessment3.setAssessment_type("Performance");
        tempAssessment3.setAssessment_status("Pending");
        tempCalendar = Calendar.getInstance();
        tempCalendar.add(Calendar.MONTH, 10);//Offset
        tempCalendar.add(Calendar.MONTH, -1);
        //tempCalendar.add(Calendar.WEEK_OF_YEAR, 2);
        tempAssessment3.setAssessment_due(tempCalendar.getTime());
        tempAssessment3.setAssessment_alert_name("Alert 3");
        db.assessmentDao().insertAssessment(tempAssessment3);
        //</editor-fold>
    }

    private void insertCoursementors() {
        List<Term> termList;
        List<Course> courseList;
        termList = db.termDao().getTermList();

        //<editor-fold desc="AddCoursementorsTerm1">
        courseList = db.courseDao().getCourseList(termList.get(0).getTerm_id());
        tempCourseMentor1.setCourse_id_fk(courseList.get(0).getCourse_id());
        tempCourseMentor1.setMentor_name("John Doe 1");
        tempCourseMentor1.setMentor_email("johndoe@nothere.com");
        tempCourseMentor1.setMentor_phone("555-555-5555");
        db.coursementorDao().insertCoursementor(tempCourseMentor1);
        tempCourseMentor2.setCourse_id_fk(courseList.get(0).getCourse_id());
        tempCourseMentor2.setMentor_name("John Doe 2");
        tempCourseMentor2.setMentor_email("johndoe2@nothere.com");
        tempCourseMentor2.setMentor_phone("555-555-5555");
        db.coursementorDao().insertCoursementor(tempCourseMentor2);
        tempCourseMentor1.setCourse_id_fk(courseList.get(1).getCourse_id());
        tempCourseMentor1.setMentor_name("John Doe 1");
        tempCourseMentor1.setMentor_email("johndoe@nothere.com");
        tempCourseMentor1.setMentor_phone("555-555-5555");
        db.coursementorDao().insertCoursementor(tempCourseMentor1);
        tempCourseMentor2.setCourse_id_fk(courseList.get(1).getCourse_id());
        tempCourseMentor2.setMentor_name("John Doe 2");
        tempCourseMentor2.setMentor_email("johndoe2@nothere.com");
        tempCourseMentor2.setMentor_phone("555-555-5555");
        db.coursementorDao().insertCoursementor(tempCourseMentor2);
        tempCourseMentor1.setCourse_id_fk(courseList.get(2).getCourse_id());
        tempCourseMentor1.setMentor_name("John Doe 1");
        tempCourseMentor1.setMentor_email("johndoe@nothere.com");
        tempCourseMentor1.setMentor_phone("555-555-5555");
        db.coursementorDao().insertCoursementor(tempCourseMentor1);
        tempCourseMentor2.setCourse_id_fk(courseList.get(2).getCourse_id());
        tempCourseMentor2.setMentor_name("John Doe 2");
        tempCourseMentor2.setMentor_email("johndoe2@nothere.com");
        tempCourseMentor2.setMentor_phone("555-555-5555");
        db.coursementorDao().insertCoursementor(tempCourseMentor2);
        //</editor-fold>
        //<editor-fold desc="AddCoursementorsTerm2">
        courseList = db.courseDao().getCourseList(termList.get(1).getTerm_id());
        tempCourseMentor1.setCourse_id_fk(courseList.get(0).getCourse_id());
        tempCourseMentor1.setMentor_name("John Doe 1");
        tempCourseMentor1.setMentor_email("johndoe@nothere.com");
        tempCourseMentor1.setMentor_phone("555-555-5555");
        db.coursementorDao().insertCoursementor(tempCourseMentor1);
        tempCourseMentor2.setCourse_id_fk(courseList.get(0).getCourse_id());
        tempCourseMentor2.setMentor_name("John Doe 2");
        tempCourseMentor2.setMentor_email("johndoe2@nothere.com");
        tempCourseMentor2.setMentor_phone("555-555-5555");
        db.coursementorDao().insertCoursementor(tempCourseMentor2);
        tempCourseMentor1.setCourse_id_fk(courseList.get(1).getCourse_id());
        tempCourseMentor1.setMentor_name("John Doe 1");
        tempCourseMentor1.setMentor_email("johndoe@nothere.com");
        tempCourseMentor1.setMentor_phone("555-555-5555");
        db.coursementorDao().insertCoursementor(tempCourseMentor1);
        tempCourseMentor2.setCourse_id_fk(courseList.get(1).getCourse_id());
        tempCourseMentor2.setMentor_name("John Doe 2");
        tempCourseMentor2.setMentor_email("johndoe2@nothere.com");
        tempCourseMentor2.setMentor_phone("555-555-5555");
        db.coursementorDao().insertCoursementor(tempCourseMentor2);
        tempCourseMentor1.setCourse_id_fk(courseList.get(2).getCourse_id());
        tempCourseMentor1.setMentor_name("John Doe 1");
        tempCourseMentor1.setMentor_email("johndoe@nothere.com");
        tempCourseMentor1.setMentor_phone("555-555-5555");
        db.coursementorDao().insertCoursementor(tempCourseMentor1);
        tempCourseMentor2.setCourse_id_fk(courseList.get(2).getCourse_id());
        tempCourseMentor2.setMentor_name("John Doe 2");
        tempCourseMentor2.setMentor_email("johndoe2@nothere.com");
        tempCourseMentor2.setMentor_phone("555-555-5555");
        db.coursementorDao().insertCoursementor(tempCourseMentor2);
        //</editor-fold>
        //<editor-fold desc="AddCoursementorsTerm3">
        courseList = db.courseDao().getCourseList(termList.get(2).getTerm_id());
        tempCourseMentor1.setCourse_id_fk(courseList.get(0).getCourse_id());
        tempCourseMentor1.setMentor_name("John Doe 1");
        tempCourseMentor1.setMentor_email("johndoe@nothere.com");
        tempCourseMentor1.setMentor_phone("555-555-5555");
        db.coursementorDao().insertCoursementor(tempCourseMentor1);
        tempCourseMentor2.setCourse_id_fk(courseList.get(0).getCourse_id());
        tempCourseMentor2.setMentor_name("John Doe 2");
        tempCourseMentor2.setMentor_email("johndoe2@nothere.com");
        tempCourseMentor2.setMentor_phone("555-555-5555");
        db.coursementorDao().insertCoursementor(tempCourseMentor2);
        tempCourseMentor1.setCourse_id_fk(courseList.get(1).getCourse_id());
        tempCourseMentor1.setMentor_name("John Doe 1");
        tempCourseMentor1.setMentor_email("johndoe@nothere.com");
        tempCourseMentor1.setMentor_phone("555-555-5555");
        db.coursementorDao().insertCoursementor(tempCourseMentor1);
        tempCourseMentor2.setCourse_id_fk(courseList.get(1).getCourse_id());
        tempCourseMentor2.setMentor_name("John Doe 2");
        tempCourseMentor2.setMentor_email("johndoe2@nothere.com");
        tempCourseMentor2.setMentor_phone("555-555-5555");
        db.coursementorDao().insertCoursementor(tempCourseMentor2);
        tempCourseMentor1.setCourse_id_fk(courseList.get(2).getCourse_id());
        tempCourseMentor1.setMentor_name("John Doe 1");
        tempCourseMentor1.setMentor_email("johndoe@nothere.com");
        tempCourseMentor1.setMentor_phone("555-555-5555");
        db.coursementorDao().insertCoursementor(tempCourseMentor1);
        tempCourseMentor2.setCourse_id_fk(courseList.get(2).getCourse_id());
        tempCourseMentor2.setMentor_name("John Doe 2");
        tempCourseMentor2.setMentor_email("johndoe2@nothere.com");
        tempCourseMentor2.setMentor_phone("555-555-5555");
        db.coursementorDao().insertCoursementor(tempCourseMentor2);
        //</editor-fold>

    }


}
