package com.example.projetbook.model.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "histoires")
public class Histoire {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String titre;
    public String image;
    public String contenu;
    public String date;
    public int auteurId;
    public int paysId;
    public int categorieId;
}
