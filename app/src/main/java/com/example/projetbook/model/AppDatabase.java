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
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Database(entities = {Histoire.class, Auteur.class, Pays.class, Categorie.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static final Executor executor = Executors.newSingleThreadExecutor();
    public abstract HistoireDao histoireDao();
    public abstract AuteurDao auteurDao();
    public abstract PaysDao paysDao();
    public abstract CategorieDao categorieDao();
}