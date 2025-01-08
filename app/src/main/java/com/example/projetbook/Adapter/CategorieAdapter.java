package com.example.projetbook.Adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projetbook.R;
import com.example.projetbook.model.entity.Categorie;

import java.util.List;

public class CategorieAdapter extends RecyclerView.Adapter<CategorieAdapter.CategorieViewHolder> {
    private List<Categorie> categorieList;
    private Context context;
    public interface OnItemClickListener {
        void onEditClick(Categorie categorie);
        void onDeleteClick(Categorie categorie);
    }

    private final OnItemClickListener listener;

    public CategorieAdapter(Context context, List<Categorie> categorieList, OnItemClickListener listener) {
        this.context = context;
        this.categorieList = categorieList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CategorieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_categorie, parent, false);
        return new CategorieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategorieViewHolder holder, int position) {
        Categorie categorie = categorieList.get(position);
        holder.name.setText(categorie.nom);
        if (categorie.image != null) {
            holder.image.setImageURI(Uri.parse(categorie.image));
        }else {
             // Set a default image if none provided
        }

        holder.edit.setOnClickListener(v -> listener.onEditClick(categorie));
        holder.delete.setOnClickListener(v -> listener.onDeleteClick(categorie));
    }

    @Override
    public int getItemCount() {
        return categorieList.size();
    }

    public static class CategorieViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        ImageView image, edit, delete;

        public CategorieViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.textViewName);
            image = itemView.findViewById(R.id.imageViewImage);
            edit = itemView.findViewById(R.id.imageViewEdit);
            delete = itemView.findViewById(R.id.imageViewDelete);
        }
    }
}
