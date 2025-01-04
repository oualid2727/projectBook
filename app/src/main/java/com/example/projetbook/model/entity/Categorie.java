package com.example.projetbook.model.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "categories")
public class Categorie {
    @PrimaryKey(autoGenerate = true)
    public int id;         // Unique ID for each category
    public String nom;     // Name of the category
    public String image;   // Path or URL for the category image
}