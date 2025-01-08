package com.example.projetbook.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projetbook.model.entity.Auteur;

import java.util.List;

public class AuteurAdapter extends RecyclerView.Adapter<AuteurAdapter.AuteurViewHolder> {
    private List<Auteur> auteurs;

    public AuteurAdapter(List<Auteur> auteurs) {
        this.auteurs = auteurs;
    }

    @NonNull
    @Override
    public AuteurViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(android.R.layout.simple_list_item_2, parent, false);
        return new AuteurViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AuteurViewHolder holder, int position) {
        Auteur auteur = auteurs.get(position);
        holder.name.setText(auteur.nom);
        holder.date.setText(auteur.date);
    }

    @Override
    public int getItemCount() {
        return auteurs.size();
    }

    public static class AuteurViewHolder extends RecyclerView.ViewHolder {
        TextView name, date;

        public AuteurViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(android.R.id.text1);
            date = itemView.findViewById(android.R.id.text2);
        }
    }
}
