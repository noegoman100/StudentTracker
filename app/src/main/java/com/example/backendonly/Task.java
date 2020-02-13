package com.example.backendonly;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.Date;

import static androidx.room.ForeignKey.CASCADE;


@Entity(
        tableName = "task_table",
        foreignKeys = @ForeignKey(
                entity = Course.class,
                parentColumns = "course_id",
                childColumns = "course_id_fk",
                onDelete = CASCADE
        )
)
public class Task {
    @PrimaryKey(autoGenerate = true)
    private int task_id;
    @ColumnInfo(name = "course_id_fk")
    private int course_id_fk;
    @ColumnInfo (name = "task_type")
    private String task_type;
    @ColumnInfo(name = "task_name")
    private String task_name;
    @ColumnInfo(name = "task_due")
    private Date task_due;
    @ColumnInfo(name = "task_info")
    private String task_info;
//    @ColumnInfo(name = "task_set_alert")
//    private int task_set_alert;
    @ColumnInfo(name = "task_alert_name")
    private String task_alert_name;
    @ColumnInfo(name = "task_alert_datetime")
    private Date task_alert_datetime;

    public int getTask_id() {
        return task_id;
    }

    public void setTask_id(int task_id) {
        this.task_id = task_id;
    }

    public int getCourse_id_fk() {
        return course_id_fk;
    }

    public void setCourse_id_fk(int course_id_fk) {
        this.course_id_fk = course_id_fk;
    }

    public String getTask_type() {
        return task_type;
    }

    public void setTask_type(String task_type) {
        this.task_type = task_type;
    }

    public String getTask_name() {
        return task_name;
    }

    public void setTask_name(String task_name) {
        this.task_name = task_name;
    }

    public Date getTask_due() {
        return task_due;
    }

    public void setTask_due(Date task_due) {
        this.task_due = task_due;
    }

    public String getTask_info() {
        return task_info;
    }

    public void setTask_info(String task_info) {
        this.task_info = task_info;
    }

//    public int getTask_set_alert() {
//        return task_set_alert;
//    }
//
//    public void setTask_set_alert(int task_set_alert) {
//        this.task_set_alert = task_set_alert;
//    }

    public String getTask_alert_name() {
        return task_alert_name;
    }

    public void setTask_alert_name(String task_alert_name) {
        this.task_alert_name = task_alert_name;
    }

    public Date getTask_alert_datetime() {
        return task_alert_datetime;
    }

    public void setTask_alert_datetime(Date task_alert_datetime) {
        this.task_alert_datetime = task_alert_datetime;
    }
}
