package com.example.projetbook.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projetbook.Adapter.AuteurAdapter;
import com.example.projetbook.Add.AddAuteurActivity;
import com.example.projetbook.MyApplication;
import com.example.projetbook.R;
import com.example.projetbook.Update.EditAuteurActivity;
import com.example.projetbook.model.entity.Auteur;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class AuteurFragment extends Fragment {
    private RecyclerView recyclerView;
    private AuteurAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_auteur, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        FloatingActionButton fabAdd = view.findViewById(R.id.fab_add);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AddAuteurActivity.class);
                startActivity(intent);
            }
        });

        MyApplication.getDatabase().auteurDao().getAllAuteurs().observe(getViewLifecycleOwner(), new Observer<List<Auteur>>() {
            @Override
            public void onChanged(List<Auteur> auteurList) {
                adapter = new AuteurAdapter(auteurList, new AuteurAdapter.OnItemClickListener() {
                    @Override
                    public void onEditClick(Auteur auteur) {
                        // Handle edit action
                        Intent intent = new Intent(getContext(), EditAuteurActivity.class);
                        intent.putExtra("auteur_id", auteur.id);
                        startActivity(intent);
                    }

                    @Override
                    public void onDeleteClick(Auteur auteur) {
                        // Handle delete action
                        MyApplication.getDatabase().auteurDao().delete(auteur);
                    }
                });
                recyclerView.setAdapter(adapter);
            }
        });

        return view;
    }
}
