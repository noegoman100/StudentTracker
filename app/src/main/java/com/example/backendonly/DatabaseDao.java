package com.example.backendonly;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface DatabaseDao {
    @Query("SELECT * FROM term")
    List<Term> getTermList();

//    @Query("SELECT * FROM course WHERE term_id = :selectedTerm")
//    List<Course> getCourseList(int selectedTerm);

    @Insert
    void insertTerm(Term term);

    @Insert
    void insertAll(Term... term);

    @Update
    void updateTerm(Term term);

    @Delete
    void deleteTerm(Term term);

    @Query("DELETE FROM term")
    public void nukeTermTable();
}
