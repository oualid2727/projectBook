package com.example.projetbook.model;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.projetbook.model.dao.AuteurDao;
import com.example.projetbook.model.dao.CategorieDao;
import com.example.projetbook.model.dao.HistoireDao;
import com.example.projetbook.model.dao.PaysDao;
import com.example.projetbook.model.entity.Auteur;
import com.example.projetbook.model.entity.Categorie;
import com.example.projetbook.model.entity.Histoire;
import com.example.projetbook.model.entity.Pays;

@Database(entities = {Histoire.class, Auteur.class, Pays.class, Categorie.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract HistoireDao histoireDao();
    public abstract AuteurDao auteurDao();
    public abstract PaysDao paysDao();
    public abstract CategorieDao categorieDao();
}