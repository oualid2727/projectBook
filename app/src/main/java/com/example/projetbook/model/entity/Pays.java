package com.example.projetbook.model.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "pays")
public class Pays {
    @PrimaryKey(autoGenerate = true)
    public int id;         // Unique ID for each country
    public String nom;     // Name of the country
    public String drapeau; // Path or URL for the flag image

    @Override
    public String toString() {
        return nom;
    }

}