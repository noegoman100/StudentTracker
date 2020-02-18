package com.sawyer.StudentTracker;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CoursementorDao {

    @Query("SELECT * FROM COURSEMENTOR_TABLE WHERE course_id_fk = :courseId ORDER BY course_id_fk")
    List<Coursementor> getCoursementorList(int courseId);

    @Query("SELECT * FROM COURSEMENTOR_TABLE WHERE course_id_fk = :courseId AND coursementor_id = :coursementorId")
    Coursementor getCoursementor(int courseId, int coursementorId);

    @Insert
    void insertCoursementor(Coursementor coursementor);

    @Update
    void updateCoursementor(Coursementor coursementor);

    @Delete
    void deleteCoursementor(Coursementor coursementor);
}
