package com.example.projetbook.Add;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.app.DatePickerDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.projetbook.MyApplication;
import com.example.projetbook.R;
import com.example.projetbook.model.entity.Auteur;

import java.util.Calendar;

public class AddAuteurActivity extends AppCompatActivity {
    private EditText editTextNom;
    private EditText editTextDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_auteur);

        editTextNom = findViewById(R.id.editTextNom);
        editTextDate = findViewById(R.id.editTextDate);
        Button buttonSelectDate = findViewById(R.id.buttonSelectDate);
        Button buttonSave = findViewById(R.id.buttonSave);

        buttonSelectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveAuteur();
            }
        });
    }

    private void showDatePicker() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
                editTextDate.setText(selectedYear + "-" + (selectedMonth + 1) + "-" + selectedDay);
            }
        }, year, month, day);
        datePickerDialog.show();
    }

    private void saveAuteur() {
        String nom = editTextNom.getText().toString();
        String date = editTextDate.getText().toString();

        Auteur auteur = new Auteur();
        auteur.nom = nom;
        auteur.date = date;

        MyApplication.getDatabase().auteurDao().insert(auteur);
        finish(); // Close activity after saving
    }
}
