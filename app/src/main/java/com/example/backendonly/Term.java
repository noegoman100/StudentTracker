package com.example.backendonly;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "term")
public class Term {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "term_name")
    private String term_name;

//    private OffsetDateTime term_start;
//    private OffsetDateTime term_end;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTerm_name() {
        return term_name;
    }

    public void setTerm_name(String term_name) {
        this.term_name = term_name;
    }
}
