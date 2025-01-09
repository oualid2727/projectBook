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

import com.example.projetbook.Adapter.HistoireAdapter;
import com.example.projetbook.Add.AddHistoireActivity;
import com.example.projetbook.MyApplication;
import com.example.projetbook.R;
import com.example.projetbook.model.entity.Histoire;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class HistoireFragment extends Fragment {
    private RecyclerView recyclerView;
    private HistoireAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_histoire, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        FloatingActionButton fabAdd = view.findViewById(R.id.fab_add);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AddHistoireActivity.class);
                startActivity(intent);
            }
        });

        // Observe LiveData for database changes
        MyApplication.getDatabase().histoireDao().getAllHistoires().observe(getViewLifecycleOwner(), new Observer<List<Histoire>>() {
            @Override
            public void onChanged(List<Histoire> histoires) {
                // Update the adapter when the data changes
                adapter = new HistoireAdapter(getContext(), histoires,getViewLifecycleOwner()); // Pass getContext() here
                recyclerView.setAdapter(adapter);
            }
        });

        return view;
    }
}

