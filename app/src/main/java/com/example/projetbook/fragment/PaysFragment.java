package com.example.projetbook.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.lifecycle.Observer;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.projetbook.Add.AddPaysActivity;
import com.example.projetbook.MyApplication;
import com.example.projetbook.R;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projetbook.Adapter.PaysAdapter;
import com.example.projetbook.model.entity.Pays;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class PaysFragment extends Fragment {
    private RecyclerView recyclerView;
    private PaysAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pays, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        FloatingActionButton fabAdd = view.findViewById(R.id.fab_add);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AddPaysActivity.class);
                startActivity(intent);
            }
        });


        // Observe LiveData for database changes
        MyApplication.getDatabase().paysDao().getAllPays().observe(getViewLifecycleOwner(), new Observer<List<Pays>>() {
            @Override
            public void onChanged(List<Pays> paysList) {
                adapter = new PaysAdapter(paysList);
                recyclerView.setAdapter(adapter);
            }
        });

        return view;
    }
}