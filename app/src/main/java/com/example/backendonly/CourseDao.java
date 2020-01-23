package com.example.backendonly;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CourseDao {
    @Query("SELECT * FROM course_table WHERE term_id_fk = :selectedTerm")
    List<Course> getCourseList(int selectedTerm);

    @Query("INSERT INTO course_table (term_id_fk, course_name)\n" +
            "VALUES(:position, \"Course Name\"); ")
    void addCourse(int position);

    @Insert
    void insertCourse(Course course);

    @Insert
    void insertAll(Course... course);

    @Update
    void updateTerm(Course course);

    @Delete
    void deleteTerm(Course course);

    @Query("DELETE FROM course_table")
    public void nukeCourseTable();

}
