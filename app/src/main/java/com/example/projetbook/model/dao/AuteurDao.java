package com.example.projetbook.model.dao;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

import com.example.projetbook.model.entity.Auteur;

import java.util.List;

@Dao
public interface AuteurDao {
    @Insert
    void insert(Auteur auteur);

    @Update
    void update(Auteur auteur);

    @Delete
    void delete(Auteur auteur);

    @Query("SELECT * FROM auteurs")
    LiveData< List<Auteur>> getAllAuteurs();

    @Query("SELECT * FROM auteurs WHERE nom LIKE :nom")
    List<Auteur> searchAuteurByName(String nom);
}

