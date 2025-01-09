package com.example.projetbook.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projetbook.R;
import com.example.projetbook.model.entity.Auteur;

import java.util.List;

public class AuteurAdapter extends RecyclerView.Adapter<AuteurAdapter.AuteurViewHolder> {
    private List<Auteur> auteurList;
    private OnItemClickListener listener;


    public interface OnItemClickListener {
        void onEditClick(Auteur auteur);
        void onDeleteClick(Auteur auteur);
    }
    public AuteurAdapter(List<Auteur> auteurList, OnItemClickListener listener) {
        this.auteurList = auteurList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public AuteurViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_auteur, parent, false);
        return new AuteurViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AuteurViewHolder holder, int position) {
        Auteur auteur = auteurList.get(position);
        holder.name.setText(auteur.nom);
        holder.date.setText(auteur.date);

        holder.edit.setOnClickListener(v -> listener.onEditClick(auteur));
        holder.delete.setOnClickListener(v -> listener.onDeleteClick(auteur));
    }

    @Override
    public int getItemCount() {
        return auteurList.size();
    }

    public static class AuteurViewHolder extends RecyclerView.ViewHolder {
        TextView name, date;
        ImageView edit, delete;

        public AuteurViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.textViewNom);
            date = itemView.findViewById(R.id.textViewDate);
            edit = itemView.findViewById(R.id.imageViewEdit);
            delete = itemView.findViewById(R.id.imageViewDelete);
        }
    }
}
