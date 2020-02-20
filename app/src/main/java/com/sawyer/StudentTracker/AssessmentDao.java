package com.sawyer.StudentTracker;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface AssessmentDao {
    @Query("SELECT * FROM assessment_table WHERE course_id_fk = :courseId ORDER BY course_id_fk")
    List<Assessment> getAssessmentList(int courseId);

    @Query("SELECT * FROM assessment_table WHERE course_id_fk = :courseId AND assessment_id = :assessmentId")
    Assessment getAssessment(int courseId, int assessmentId);

//    @Query("INSERT INTO assessment_table (course_id_fk, assessment_name, assessment_set_alert) VALUES(:courseId, :assessmentName, 0)")
//    void addAssessment(int courseId, String assessmentName);

    @Insert
    void insertAssessment(Assessment assessment);

    @Insert
    void insertAll(Assessment... assessment);

    @Update
    void updateAssessment(Assessment assessment);

    @Delete
    void deleteAssessment(Assessment assessment);

    @Query("DELETE FROM assessment_table")
    public void nukeAssessmentTable();

}
