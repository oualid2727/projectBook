package com.example.projetbook.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projetbook.MyApplication;
import com.example.projetbook.R;
import com.example.projetbook.model.entity.Auteur;
import com.example.projetbook.model.entity.Categorie;
import com.example.projetbook.model.entity.Histoire;
import com.example.projetbook.model.entity.Pays;

import java.util.List;

public class HistoireAdapter extends RecyclerView.Adapter<HistoireAdapter.HistoireViewHolder> {
    private List<Histoire> histoireList;
    private Context context;
    private LifecycleOwner lifecycleOwner;
    public HistoireAdapter(Context context, List<Histoire> histoireList, LifecycleOwner lifecycleOwner) {
        this.context = context;
        this.histoireList = histoireList;
        this.lifecycleOwner = lifecycleOwner;
    }

    @NonNull
    @Override
    public HistoireViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_histoire, parent, false);
        return new HistoireViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoireViewHolder holder, int position) {
        Histoire histoire = histoireList.get(position);
        holder.titre.setText(histoire.titre);
        holder.date.setText(histoire.date);

        if (histoire.image != null) {
            holder.image.setImageURI(Uri.parse(histoire.image));
        }

        // Fetch and display the author's name
        MyApplication.getDatabase().auteurDao().getAuteurById(histoire.auteurId).observe(lifecycleOwner, new Observer<Auteur>() {
            @Override
            public void onChanged(Auteur auteur) {
                if (auteur != null) {
                    holder.auteur.setText("Author: " + auteur.nom);
                }
            }
        });

        // Fetch and display the country's name
        MyApplication.getDatabase().paysDao().getPaysById(histoire.paysId).observe(lifecycleOwner, new Observer<Pays>() {
            @Override
            public void onChanged(Pays pays) {
                if (pays != null) {
                    holder.pays.setText("Country: " + pays.nom);
                }
            }
        });

        // Fetch and display the category's name
        MyApplication.getDatabase().categorieDao().getCategorieById(histoire.categorieId).observe(lifecycleOwner, new Observer<Categorie>() {
            @Override
            public void onChanged(Categorie categorie) {
                if (categorie != null) {
                    holder.categorie.setText("Category: " + categorie.nom);
                }
            }
        });

        holder.itemView.setOnClickListener(v -> showHistoireDetailsDialog(histoire));
    }

    @Override
    public int getItemCount() {
        return histoireList.size();
    }

    private void showHistoireDetailsDialog(Histoire histoire) {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_histoire_details);

        ImageView imageView = dialog.findViewById(R.id.imageViewDialogImage);
        TextView textViewTitre = dialog.findViewById(R.id.textViewDialogTitre);
        TextView textViewContenu = dialog.findViewById(R.id.textViewDialogContenu);

        if (histoire.image != null) {
            imageView.setImageURI(Uri.parse(histoire.image));
        }
        textViewTitre.setText(histoire.titre);
        textViewContenu.setText(histoire.contenu);

        dialog.show();
    }

     public static class HistoireViewHolder extends RecyclerView.ViewHolder {
        TextView titre, date, auteur,pays, categorie;
        ImageView image;

        public HistoireViewHolder(@NonNull View itemView) {
            super(itemView);
            titre = itemView.findViewById(R.id.textViewTitre);
            date = itemView.findViewById(R.id.textViewDate);
            auteur = itemView.findViewById(R.id.textViewAuteur);
            image = itemView.findViewById(R.id.imageViewHistoire);
            pays = itemView.findViewById(R.id.textViewPays);
            categorie = itemView.findViewById(R.id.textViewCategorie);
        }
    }
}

