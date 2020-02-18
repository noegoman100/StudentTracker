package com.sawyer.StudentTracker;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TaskDao {
    @Query("SELECT * FROM task_table WHERE course_id_fk = :courseId ORDER BY course_id_fk")
    List<Task> getTaskList(int courseId);

    @Query("SELECT * FROM task_table WHERE course_id_fk = :courseId AND task_id = :taskId")
    Task getTask(int courseId, int taskId);

//    @Query("INSERT INTO task_table (course_id_fk, task_name, task_set_alert) VALUES(:courseId, :taskName, 0)")
//    void addTask(int courseId, String taskName);

    @Insert
    void insertTask(Task task);

    @Insert
    void insertAll(Task... task);

    @Update
    void updateTask(Task task);

    @Delete
    void deleteTask(Task task);

    @Query("DELETE FROM task_table")
    public void nukeTaskTable();

}
