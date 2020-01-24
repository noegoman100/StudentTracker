package com.example.backendonly;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "coursementor_table",
        foreignKeys = @ForeignKey(
                entity = Course.class,
                parentColumns = "course_id",
                childColumns = "course_id_fk"
        )
)
public class Coursementor {

    @PrimaryKey(autoGenerate = true)
    private int coursementor_id;

    @ColumnInfo(name = "course_id_fk")
    private int course_id_fk;

    @ColumnInfo(name = "mentor_name")
    private String mentor_name;

    @ColumnInfo(name = "mentor_phone")
    private String mentor_phone;

    @ColumnInfo(name = "mentor_email")
    private String mentor_email;

    public int getCourse_id_fk() {
        return course_id_fk;
    }

    public void setCourse_id_fk(int course_id_fk) {
        this.course_id_fk = course_id_fk;
    }

    public String getMentor_name() {
        return mentor_name;
    }

    public void setMentor_name(String mentor_name) {
        this.mentor_name = mentor_name;
    }

    public String getMentor_phone() {
        return mentor_phone;
    }

    public void setMentor_phone(String mentor_phone) {
        this.mentor_phone = mentor_phone;
    }

    public String getMentor_email() {
        return mentor_email;
    }

    public void setMentor_email(String mentor_email) {
        this.mentor_email = mentor_email;
    }

    public int getCoursementor_id() {
        return coursementor_id;
    }

    public void setCoursementor_id(int coursementor_id) {
        this.coursementor_id = coursementor_id;
    }
}
