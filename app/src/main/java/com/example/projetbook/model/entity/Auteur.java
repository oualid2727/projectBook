package com.example.projetbook.model.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "auteurs")
public class Auteur {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String nom;
    public String date;

    @Override
    public String toString() {
        return nom;
    }
}
