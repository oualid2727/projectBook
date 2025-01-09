package com.example.projetbook.Add;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import com.example.projetbook.MyApplication;
import com.example.projetbook.R;
import com.example.projetbook.model.entity.Auteur;
import com.example.projetbook.model.entity.Categorie;
import com.example.projetbook.model.entity.Histoire;
import com.example.projetbook.model.entity.Pays;

import java.util.Calendar;
import java.util.List;

public class AddHistoireActivity extends AppCompatActivity {
    private EditText editTextTitre, editTextContenu, editTextDate;
    private ImageView imageViewImage;
    private Spinner spinnerAuteur, spinnerPays, spinnerCategorie;
    private Button buttonSelectDate, buttonSelectImage, buttonSave;
    private Uri imageUri;
    private List<Auteur> auteurs;
    private List<Pays> pays;
    private List<Categorie> categories;

    private final ActivityResultLauncher<Intent> pickImageLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    imageUri = result.getData().getData();
                    imageViewImage.setImageURI(imageUri);
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_histoire);

        editTextTitre = findViewById(R.id.editTextTitre);
        editTextContenu = findViewById(R.id.editTextContenu);
        editTextDate = findViewById(R.id.editTextDate);
        imageViewImage = findViewById(R.id.imageViewImage);
        spinnerAuteur = findViewById(R.id.spinnerAuteur);
        spinnerPays = findViewById(R.id.spinnerPays);
        spinnerCategorie = findViewById(R.id.spinnerCategorie);
        buttonSelectDate = findViewById(R.id.buttonSelectDate);
        buttonSelectImage = findViewById(R.id.buttonSelectImage);
        buttonSave = findViewById(R.id.buttonSave);

        buttonSelectImage.setOnClickListener(v -> openGallery());
        buttonSelectDate.setOnClickListener(v -> showDatePickerDialog());

        loadSpinners();

        buttonSave.setOnClickListener(v -> saveHistoire());
    }

    private void loadSpinners() {
        // Load data for spinners from the database
        MyApplication.getDatabase().auteurDao().getAllAuteurs().observe(this, new Observer<List<Auteur>>() {
            @Override
            public void onChanged(List<Auteur> auteurList) {
                auteurs = auteurList;
                ArrayAdapter<Auteur> adapter = new ArrayAdapter<>(AddHistoireActivity.this, android.R.layout.simple_spinner_item, auteurList);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerAuteur.setAdapter(adapter);
            }
        });

        MyApplication.getDatabase().paysDao().getAllPays().observe(this, new Observer<List<Pays>>() {
            @Override
            public void onChanged(List<Pays> paysList) {
                pays = paysList;
                ArrayAdapter<Pays> adapter = new ArrayAdapter<>(AddHistoireActivity.this, android.R.layout.simple_spinner_item, paysList);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerPays.setAdapter(adapter);
            }
        });

        MyApplication.getDatabase().categorieDao().getAllCategories().observe(this, new Observer<List<Categorie>>() {
            @Override
            public void onChanged(List<Categorie> categorieList) {
                categories = categorieList;
                ArrayAdapter<Categorie> adapter = new ArrayAdapter<>(AddHistoireActivity.this, android.R.layout.simple_spinner_item, categorieList);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerCategorie.setAdapter(adapter);
            }
        });
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickImageLauncher.launch(intent);
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

    private void saveHistoire() {
        String titre = editTextTitre.getText().toString();
        String contenu = editTextContenu.getText().toString();
        String date = editTextDate.getText().toString();
        String image = imageUri != null ? imageUri.toString() : null;
        int auteurId = ((Auteur) spinnerAuteur.getSelectedItem()).id;
        int paysId = ((Pays) spinnerPays.getSelectedItem()).id;
        int categorieId = ((Categorie) spinnerCategorie.getSelectedItem()).id;

        Histoire histoire = new Histoire();
        histoire.titre = titre;
        histoire.contenu = contenu;
        histoire.date = date;
        histoire.image = image;
        histoire.auteurId = auteurId;
        histoire.paysId = paysId;
        histoire.categorieId = categorieId;

        MyApplication.getDatabase().histoireDao().insert(histoire);
        finish(); // Close activity after saving
    }
}
