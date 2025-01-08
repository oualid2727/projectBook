package com.example.projetbook.model.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

import com.example.projetbook.model.entity.Histoire;

import java.util.List;
@Dao
public interface HistoireDao {
    @Insert
    void insert(Histoire histoire);

    @Update
    void update(Histoire histoire);

    @Delete
    void delete(Histoire histoire);

    @Query("SELECT * FROM histoires")
    LiveData<List<Histoire>> getAllHistoires();

    @Query("SELECT * FROM histoires WHERE titre LIKE :titre")
    List<Histoire> searchByTitre(String titre);
}
