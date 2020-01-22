package com.example.backendonly;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "term_table")
public class Term {
    @PrimaryKey(autoGenerate = true)
    private int term_id;
    @ColumnInfo(name = "term_name")
    private String term_name;

//    private OffsetDateTime term_start;
//    private OffsetDateTime term_end;


    public int getTerm_id() {
        return term_id;
    }

    public void setTerm_id(int term_id) {
        this.term_id = term_id;
    }

    public String getTerm_name() {
        return term_name;
    }

    public void setTerm_name(String term_name) {
        this.term_name = term_name;
    }

}
