package com.example.backendonly;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CoursementorDao {

    @Query("SELECT * FROM COURSEMENTOR_TABLE WHERE course_id_fk = :position ORDER BY course_id_fk")
    List<Coursementor> getCoursementorList(int position);

    @Insert
    void insertCoursementor(Coursementor coursementor);

    @Update
    void updateCoursementor(Coursementor coursementor);

    @Delete
    void deleteCoursementor(Coursementor coursementor);
}
