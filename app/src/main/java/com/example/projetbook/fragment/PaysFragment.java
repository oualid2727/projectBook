package com.example.projetbook.fragment;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.contract.ActivityResultContracts;
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
import com.example.projetbook.Update.EditPaysActivity;
import com.example.projetbook.model.entity.Pays;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class PaysFragment extends Fragment {
    private RecyclerView recyclerView;
    private PaysAdapter adapter;

    // Declare the permission request launcher
    private final androidx.activity.result.ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), new ActivityResultCallback<Boolean>() {
                @Override
                public void onActivityResult(Boolean isGranted) {
                    if (isGranted) {
                        // Permission granted
                        // Proceed with the action that requires the permission
                    } else {
                        // Permission denied
                        // Show a message or handle accordingly
                    }
                }
            });


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pays, container, false);

        // Check if the permission is granted
        if (requireContext().checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // Request the permission
            requestPermissionLauncher.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE);
        }
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

        MyApplication.getDatabase().paysDao().getAllPays().observe(getViewLifecycleOwner(), new Observer<List<Pays>>() {
            @Override
            public void onChanged(List<Pays> paysList) {
                adapter = new PaysAdapter(paysList, new PaysAdapter.OnItemClickListener() {
                    @Override
                    public void onEditClick(Pays pays) {
                        // Handle edit action, open an activity to edit the Pays
                        Intent intent = new Intent(getContext(), EditPaysActivity.class);
                        intent.putExtra("pays_id", pays.id);
                        startActivity(intent);
                    }

                    @Override
                    public void onDeleteClick(Pays pays) {
                        // Handle delete action, remove the Pays from the database
                        MyApplication.getDatabase().paysDao().delete(pays);
                    }
                });
                recyclerView.setAdapter(adapter);
            }
        });

        return view;
    }
}