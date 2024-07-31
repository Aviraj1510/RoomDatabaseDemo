package com.example.roomdatabasedemo;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@androidx.room.Dao
public interface Dao {

    @Insert
    void insert(DetailsModal modal);

    @Update
    void update(DetailsModal modal);

    @Delete
    void delete(DetailsModal modal);

    @Query("DELETE FROM form_table")
    void deleteAllForms();

    @Query("SELECT * FROM form_table ORDER BY Name ASC")
    LiveData<List<DetailsModal>> getAllDetail();

}
