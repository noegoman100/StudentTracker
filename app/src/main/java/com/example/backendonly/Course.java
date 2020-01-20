package com.example.backendonly;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;


@Entity(
        tableName = "course",
        foreignKeys = @ForeignKey(
                entity = Term.class,
                parentColumns = "id",
                childColumns = "term_id"
        ))
public class Course {
    @PrimaryKey(autoGenerate = true)
    private int course_id;
    @ColumnInfo(name = "term_id")
    private int term_id;
    @ColumnInfo(name = "course_name")
    private String course_name;
    @ColumnInfo(name = "course_start")
    private long course_start;
    @ColumnInfo(name = "course_end")
    private long course_end;
    @ColumnInfo(name = "course_status")
    private String course_status;
    @ColumnInfo(name = "course_notes")
    private String course_notes;

    public int getCourse_id() {
        return course_id;
    }

    public void setCourse_id(int course_id) {
        this.course_id = course_id;
    }

    public int getTerm_id() {
        return term_id;
    }

    public void setTerm_id(int term_id) {
        this.term_id = term_id;
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public long getCourse_start() {
        return course_start;
    }

    public void setCourse_start(long course_start) {
        this.course_start = course_start;
    }

    public long getCourse_end() {
        return course_end;
    }

    public void setCourse_end(long course_end) {
        this.course_end = course_end;
    }

    public String getCourse_status() {
        return course_status;
    }

    public void setCourse_status(String course_status) {
        this.course_status = course_status;
    }

    public String getCourse_notes() {
        return course_notes;
    }

    public void setCourse_notes(String course_notes) {
        this.course_notes = course_notes;
    }
}