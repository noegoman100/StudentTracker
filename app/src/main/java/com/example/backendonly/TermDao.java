package com.example.backendonly;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TermDao {
    @Query("SELECT * FROM term_table")
    List<Term> getTermList();

//    @Query("SELECT * FROM course_table WHERE term_id = :selectedTerm")
//    List<Course> getCourseList(int selectedTerm);
    @Query("SELECT * FROM course_table WHERE term_id_fk = :selectedTerm")
    List<Course> getCourseList(int selectedTerm);


    @Insert
    void insertTerm(Term term);

    @Insert
    void insertAll(Term... term);

    @Update
    void updateTerm(Term term);

    @Delete
    void deleteTerm(Term term);

    @Query("DELETE FROM term_table")
    public void nukeTermTable();

}
