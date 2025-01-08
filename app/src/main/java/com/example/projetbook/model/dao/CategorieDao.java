package com.example.projetbook.model.dao;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

import com.example.projetbook.model.entity.Categorie;

import java.util.List;

@Dao
public interface CategorieDao {
    @Insert
    void insert(Categorie categorie);

    @Query("SELECT * FROM categories WHERE id = :id")
    LiveData<Categorie> getCategorieById(int id);
    @Update
    void update(Categorie categorie);

    @Delete
    void delete(Categorie categorie);

    @Query("SELECT * FROM categories")
    LiveData< List<Categorie>> getAllCategories();

    @Query("SELECT * FROM categories WHERE nom LIKE :nom")
    List<Categorie> searchCategorieByName(String nom);
}
