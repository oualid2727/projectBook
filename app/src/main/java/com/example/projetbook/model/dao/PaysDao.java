package com.example.projetbook.model.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

import com.example.projetbook.model.entity.Pays;

import java.util.List;

@Dao
public interface PaysDao {
    @Insert
    void insert(Pays pays);

    @Query("SELECT * FROM pays WHERE id = :id")
    LiveData<Pays> getPaysById(int id);

    @Update
    void update(Pays pays);

    @Delete
    void delete(Pays pays);

    @Query("SELECT * FROM pays")
    LiveData<List<Pays>> getAllPays();

    @Query("SELECT * FROM pays WHERE nom LIKE :nom")
    List<Pays> searchPaysByName(String nom);
}
