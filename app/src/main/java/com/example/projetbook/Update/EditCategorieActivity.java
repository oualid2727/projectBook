package com.example.projetbook.Update;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import com.example.projetbook.MyApplication;
import com.example.projetbook.R;
import com.example.projetbook.model.entity.Categorie;

public class EditCategorieActivity extends AppCompatActivity {
    private EditText editTextNom;
    private ImageView imageViewImage;
    private Uri imageUri;
    private int categorieId;
    private Categorie currentCategorie;

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
        setContentView(R.layout.activity_edit_categorie);

        editTextNom = findViewById(R.id.editTextNom);
        imageViewImage = findViewById(R.id.imageViewImage);
        Button buttonSelectImage = findViewById(R.id.buttonSelectImage);
        Button buttonSave = findViewById(R.id.buttonSave);

        categorieId = getIntent().getIntExtra("categorie_id", -1);

        if (categorieId != -1) {
            MyApplication.getDatabase().categorieDao().getCategorieById(categorieId).observe(this, new Observer<Categorie>() {
                @Override
                public void onChanged(Categorie categorie) {
                    currentCategorie = categorie;
                    if (categorie != null) {
                        editTextNom.setText(categorie.nom);
                        if (categorie.image != null) {
                            imageUri = Uri.parse(categorie.image);
                            imageViewImage.setImageURI(imageUri);
                        }
                    }
                }
            });
        }

        buttonSelectImage.setOnClickListener(v -> openGallery());

        buttonSave.setOnClickListener(v -> saveCategorie());
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickImageLauncher.launch(intent);
    }

    private void saveCategorie() {
        if (currentCategorie != null) {
            currentCategorie.nom = editTextNom.getText().toString();
            currentCategorie.image = imageUri != null ? imageUri.toString() : currentCategorie.image;

            MyApplication.getDatabase().categorieDao().update(currentCategorie);
            finish(); // Close activity after saving
        }
    }
}
