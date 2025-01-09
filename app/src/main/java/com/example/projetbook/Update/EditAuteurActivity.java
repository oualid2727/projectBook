package com.example.projetbook.Update;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import com.example.projetbook.MyApplication;
import com.example.projetbook.R;
import com.example.projetbook.model.entity.Auteur;
import java.util.Calendar;


public class EditAuteurActivity extends AppCompatActivity {
    private EditText editTextNom;
    private EditText editTextDate;
    private Button buttonSelectDate;
    private int auteurId;
    private Auteur currentAuteur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_auteur);
        buttonSelectDate = findViewById(R.id.buttonSelectDate);
        editTextNom = findViewById(R.id.editTextNom);
        editTextDate = findViewById(R.id.editTextDate);
        Button buttonSave = findViewById(R.id.buttonSave);


        buttonSelectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        auteurId = getIntent().getIntExtra("auteur_id", -1);

        if (auteurId != -1) {
            MyApplication.getDatabase().auteurDao().getAuteurById(auteurId).observe(this, new Observer<Auteur>() {
                @Override
                public void onChanged(Auteur auteur) {
                    currentAuteur = auteur;
                    if (auteur != null) {
                        editTextNom.setText(auteur.nom);
                        editTextDate.setText(auteur.date);
                    }
                }
            });
        }

        buttonSave.setOnClickListener(v -> saveAuteur());
    }

    private void showDatePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                editTextDate.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
            }
        }, year, month, day);
        datePickerDialog.show();
    }

    private void saveAuteur() {
        if (currentAuteur != null) {
            currentAuteur.nom = editTextNom.getText().toString();
            currentAuteur.date = editTextDate.getText().toString();

            MyApplication.getDatabase().auteurDao().update(currentAuteur);
            finish(); // Close activity after saving
        }
    }
}

