package com.example.projetbook.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projetbook.model.entity.Histoire;

import java.util.List;

public class HistoireAdapter extends RecyclerView.Adapter<HistoireAdapter.HistoireViewHolder> {
    private List<Histoire> histoires;

    public HistoireAdapter(List<Histoire> histoires) {
        this.histoires = histoires;
    }

    @NonNull
    @Override
    public HistoireViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(android.R.layout.simple_list_item_2, parent, false);
        return new HistoireViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoireViewHolder holder, int position) {
        Histoire histoire = histoires.get(position);
        holder.title.setText(histoire.titre);
        holder.subtitle.setText(histoire.contenu);
    }

    @Override
    public int getItemCount() {
        return histoires.size();
    }

    public static class HistoireViewHolder extends RecyclerView.ViewHolder {
        TextView title, subtitle;

        public HistoireViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(android.R.id.text1);
            subtitle = itemView.findViewById(android.R.id.text2);
        }
    }
}

