package com.example.projetbook.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projetbook.model.entity.Categorie;

import java.util.List;

public class CategorieAdapter extends RecyclerView.Adapter<CategorieAdapter.CategorieViewHolder> {
    private List<Categorie> categories;

    public CategorieAdapter(List<Categorie> categories) {
        this.categories = categories;
    }

    @NonNull
    @Override
    public CategorieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(android.R.layout.simple_list_item_2, parent, false);
        return new CategorieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategorieViewHolder holder, int position) {
        Categorie categorie = categories.get(position);
        holder.name.setText(categorie.nom);
        holder.image.setText(categorie.image != null ? categorie.image : "No image");
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public static class CategorieViewHolder extends RecyclerView.ViewHolder {
        TextView name, image;

        public CategorieViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(android.R.id.text1);
            image = itemView.findViewById(android.R.id.text2);
        }
    }
}

