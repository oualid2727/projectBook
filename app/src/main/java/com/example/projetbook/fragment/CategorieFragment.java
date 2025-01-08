package com.example.projetbook.fragment;
import com.example.projetbook.Adapter.CategorieAdapter;
import com.example.projetbook.Add.AddCategorieActivity;
import com.example.projetbook.MyApplication;
import com.example.projetbook.R;
import com.example.projetbook.Update.EditCategorieActivity;
import com.example.projetbook.model.entity.Categorie;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import android.content.Intent;
import androidx.lifecycle.Observer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CategorieFragment extends Fragment {
    private RecyclerView recyclerView;
    private CategorieAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_categorie, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        FloatingActionButton fabAdd = view.findViewById(R.id.fab_add);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AddCategorieActivity.class);
                startActivity(intent);
            }
        });


        // Observe LiveData for database changes
        MyApplication.getDatabase().categorieDao().getAllCategories().observe(getViewLifecycleOwner(), new Observer<List<Categorie>>() {
            @Override
            public void onChanged(List<Categorie> categorieList) {
                adapter = new CategorieAdapter(getContext(), categorieList, new CategorieAdapter.OnItemClickListener() {
                    @Override
                    public void onEditClick(Categorie categorie) {
                        // Handle edit action
                        Intent intent = new Intent(getContext(), EditCategorieActivity.class);
                        intent.putExtra("categorie_id", categorie.id);
                        startActivity(intent);
                    }

                    @Override
                    public void onDeleteClick(Categorie categorie) {
                        // Handle delete action
                        MyApplication.getDatabase().categorieDao().delete(categorie);
                    }
                });
                recyclerView.setAdapter(adapter);
            }
        });

        return view;
    }
}
