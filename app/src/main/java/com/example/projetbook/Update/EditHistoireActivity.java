package com.example.projetbook.Update;

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

public class EditHistoireActivity extends AppCompatActivity {
    private EditText editTextTitre, editTextContenu, editTextDate;
    private ImageView imageViewImage;
    private Spinner spinnerAuteur, spinnerPays, spinnerCategorie;
    private Button buttonSelectDate, buttonSelectImage, buttonSave;
    private Uri imageUri;
    private Histoire currentHistoire;
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
        setContentView(R.layout.activity_edit_histoire);

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

        int histoireId = getIntent().getIntExtra("histoire_id", -1);
        if (histoireId != -1) {
            MyApplication.getDatabase().histoireDao().getHistoireById(histoireId).observe(this, new Observer<Histoire>() {
                @Override
                public void onChanged(Histoire histoire) {
                    currentHistoire = histoire;
                    if (histoire != null) {
                        editTextTitre.setText(histoire.titre);
                        editTextContenu.setText(histoire.contenu);
                        editTextDate.setText(histoire.date);
                        if (histoire.image != null) {
                            imageUri = Uri.parse(histoire.image);
                            imageViewImage.setImageURI(imageUri);
                        }
                        loadSpinners(histoire.auteurId, histoire.paysId, histoire.categorieId);
                    }
                }
            });
        }

        buttonSave.setOnClickListener(v -> saveHistoire());
    }

    private void loadSpinners(int auteurId, int paysId, int categorieId) {
        MyApplication.getDatabase().auteurDao().getAllAuteurs().observe(this, new Observer<List<Auteur>>() {
            @Override
            public void onChanged(List<Auteur> auteurList) {
                auteurs = auteurList;
                ArrayAdapter<Auteur> adapter = new ArrayAdapter<>(EditHistoireActivity.this, android.R.layout.simple_spinner_item, auteurList);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerAuteur.setAdapter(adapter);
                setSpinnerSelection(spinnerAuteur, auteurId);
            }
        });

        MyApplication.getDatabase().paysDao().getAllPays().observe(this, new Observer<List<Pays>>() {
            @Override
            public void onChanged(List<Pays> paysList) {
                pays = paysList;
                ArrayAdapter<Pays> adapter = new ArrayAdapter<>(EditHistoireActivity.this, android.R.layout.simple_spinner_item, paysList);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerPays.setAdapter(adapter);
                setSpinnerSelection(spinnerPays, paysId);
            }
        });

        MyApplication.getDatabase().categorieDao().getAllCategories().observe(this, new Observer<List<Categorie>>() {
            @Override
            public void onChanged(List<Categorie> categorieList) {
                categories = categorieList;
                ArrayAdapter<Categorie> adapter = new ArrayAdapter<>(EditHistoireActivity.this, android.R.layout.simple_spinner_item, categorieList);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerCategorie.setAdapter(adapter);
                setSpinnerSelection(spinnerCategorie, categorieId);
            }
        });
    }

    private void setSpinnerSelection(Spinner spinner, int id) {
        for (int i = 0; i < spinner.getCount(); i++) {
            Object item = spinner.getItemAtPosition(i);
            if (item instanceof Auteur && ((Auteur) item).id == id) {
                spinner.setSelection(i);
                break;
            } else if (item instanceof Pays && ((Pays) item).id == id) {
                spinner.setSelection(i);
                break;
            } else if (item instanceof Categorie && ((Categorie) item).id == id) {
                spinner.setSelection(i);
                break;
            }
        }
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

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, year1, month1, dayOfMonth) -> {
            editTextDate.setText(year1 + "-" + (month1 + 1) + "-" + dayOfMonth);
        }, year, month, day);
        datePickerDialog.show();
    }

    private void saveHistoire() {
        if (currentHistoire != null) {
            currentHistoire.titre = editTextTitre.getText().toString();
            currentHistoire.contenu = editTextContenu.getText().toString();
            currentHistoire.date = editTextDate.getText().toString();
            currentHistoire.image = imageUri != null ? imageUri.toString() : currentHistoire.image;
            currentHistoire.auteurId = ((Auteur) spinnerAuteur.getSelectedItem()).id;
            currentHistoire.paysId = ((Pays) spinnerPays.getSelectedItem()).id;
            currentHistoire.categorieId = ((Categorie) spinnerCategorie.getSelectedItem()).id;

            MyApplication.getDatabase().histoireDao().update(currentHistoire);
            finish();
        }
    }
}
