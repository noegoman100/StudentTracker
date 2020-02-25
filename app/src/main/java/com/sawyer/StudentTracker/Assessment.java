package com.sawyer.StudentTracker;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.Date;

import static androidx.room.ForeignKey.CASCADE;


@Entity(
        tableName = "assessment_table",
        foreignKeys = @ForeignKey(
                entity = Course.class,
                parentColumns = "course_id",
                childColumns = "course_id_fk",
                onDelete = CASCADE
        )
)
public class Assessment {
    @PrimaryKey(autoGenerate = true)
    private int assessment_id;
    @ColumnInfo(name = "course_id_fk")
    private int course_id_fk;
    @ColumnInfo (name = "assessment_type")
    private String assessment_type;
    @ColumnInfo(name = "assessment_name")
    private String assessment_name;
    @ColumnInfo(name = "assessment_due")
    private Date assessment_due;
    @ColumnInfo(name = "assessment_info")
    private String assessment_info;
    @ColumnInfo(name = "assessment_alert_name")
    private String assessment_alert_name;
    @ColumnInfo(name = "assessment_status")
    private String assessment_status;
    @ColumnInfo(name = "assessment_alert_date")
    private Date assessment_alert_date;

    public int getAssessment_id() {
        return assessment_id;
    }

    public void setAssessment_id(int assessment_id) {
        this.assessment_id = assessment_id;
    }

    public int getCourse_id_fk() {
        return course_id_fk;
    }

    public void setCourse_id_fk(int course_id_fk) {
        this.course_id_fk = course_id_fk;
    }

    public String getAssessment_type() {
        return assessment_type;
    }

    public void setAssessment_type(String assessment_type) {
        this.assessment_type = assessment_type;
    }

    public String getAssessment_name() {
        return assessment_name;
    }

    public void setAssessment_name(String assessment_name) {
        this.assessment_name = assessment_name;
    }

    public Date getAssessment_due() {
        return assessment_due;
    }

    public void setAssessment_due(Date assessment_due) {
        this.assessment_due = assessment_due;
    }

    public String getAssessment_info() {
        return assessment_info;
    }

    public void setAssessment_info(String assessment_info) {
        this.assessment_info = assessment_info;
    }

    public String getAssessment_alert_name() {
        return assessment_alert_name;
    }

    public void setAssessment_alert_name(String assessment_alert_name) {
        this.assessment_alert_name = assessment_alert_name;
    }

    public String getAssessment_status() {
        return assessment_status;
    }

    public void setAssessment_status(String assessment_status) {
        this.assessment_status = assessment_status;
    }

    public Date getAssessment_alert_date() {
        return assessment_alert_date;
    }

    public void setAssessment_alert_date(Date assessment_alert_date) {
        this.assessment_alert_date = assessment_alert_date;
    }
}
