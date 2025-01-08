package com.example.projetbook.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projetbook.model.entity.Pays;

import java.util.List;

public class PaysAdapter extends RecyclerView.Adapter<PaysAdapter.PaysViewHolder> {
    private List<Pays> paysList;

    public PaysAdapter(List<Pays> paysList) {
        this.paysList = paysList;
    }

    @NonNull
    @Override
    public PaysViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(android.R.layout.simple_list_item_1, parent, false);
        return new PaysViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PaysViewHolder holder, int position) {
        Pays pays = paysList.get(position);
        holder.name.setText(pays.nom);
    }

    @Override
    public int getItemCount() {
        return paysList.size();
    }

    public static class PaysViewHolder extends RecyclerView.ViewHolder {
        TextView name;

        public PaysViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(android.R.id.text1);
        }
    }
}
