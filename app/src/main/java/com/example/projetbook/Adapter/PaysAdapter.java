package com.example.projetbook.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ImageView;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projetbook.R;
import com.example.projetbook.model.entity.Pays;

import java.util.List;

public class PaysAdapter extends RecyclerView.Adapter<PaysAdapter.PaysViewHolder> {
    private List<Pays> paysList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onEditClick(Pays pays);
        void onDeleteClick(Pays pays);
    }
    public PaysAdapter(List<Pays> paysList,OnItemClickListener listener) {
        this.paysList = paysList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public PaysViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_pays, parent, false);
        return new PaysViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PaysViewHolder holder, int position) {
        Pays pays = paysList.get(position);
        holder.name.setText(pays.nom);
        if (pays.drapeau != null) {
            holder.flag.setImageURI(Uri.parse(pays.drapeau));
        }
        holder.edit.setOnClickListener(v -> listener.onEditClick(pays));
        holder.delete.setOnClickListener(v -> listener.onDeleteClick(pays));
    }

    @Override
    public int getItemCount() {
        return paysList.size();
    }

    public static class PaysViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        ImageView flag,edit, delete;

        public PaysViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.textViewName);
            flag = itemView.findViewById(R.id.imageViewFlag);
            edit = itemView.findViewById(R.id.imageViewEdit);
            delete = itemView.findViewById(R.id.imageViewDelete);
        }
    }
}
