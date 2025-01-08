package com.example.projetbook.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.lifecycle.Observer;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.projetbook.R;
import com.example.projetbook.Adapter.AuteurAdapter;
import com.example.projetbook.MyApplication;
import com.example.projetbook.model.entity.Auteur;

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

        // Observe LiveData for database changes
        MyApplication.getDatabase().auteurDao().getAllAuteurs().observe(getViewLifecycleOwner(), new Observer<List<Auteur>>() {
            @Override
            public void onChanged(List<Auteur> auteurList) {
                adapter = new AuteurAdapter(auteurList);
                recyclerView.setAdapter(adapter);
            }
        });

        return view;
    }
}
