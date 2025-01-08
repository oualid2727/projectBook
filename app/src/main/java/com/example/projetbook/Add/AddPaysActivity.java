package com.example.projetbook.Add;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projetbook.MyApplication;
import com.example.projetbook.R;
import com.example.projetbook.model.entity.Pays;


public class AddPaysActivity extends AppCompatActivity {
    private EditText editTextNom, editTextDrapeau;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pays);

        editTextNom = findViewById(R.id.editTextNom);
        editTextDrapeau = findViewById(R.id.editTextDrapeau);
        Button buttonSave = findViewById(R.id.buttonSave);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savePays();
            }
        });
    }

    private void savePays() {
        String nom = editTextNom.getText().toString();
        String drapeau = editTextDrapeau.getText().toString();

        Pays pays = new Pays();
        pays.nom = nom;
        pays.drapeau = drapeau;

        MyApplication.getDatabase().paysDao().insert(pays);
        finish(); // Close activity after saving
    }
}
