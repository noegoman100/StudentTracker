package com.example.backendonly;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CourseDao {
    @Query("SELECT * FROM course_table WHERE term_id_fk = :termId ORDER BY course_id")
    List<Course> getCourseList(int termId);

    @Query("SELECT * FROM course_table WHERE term_id_fk = :termId AND course_id = :courseId")
    Course getCourse(int termId, int courseId);

    @Query("INSERT INTO course_table (term_id_fk, course_name)\n" +
            "VALUES(:termId, \"Course Name\"); ")
    void addCourse(int termId);

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
