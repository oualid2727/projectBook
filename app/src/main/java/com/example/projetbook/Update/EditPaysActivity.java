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
import com.example.projetbook.model.entity.Pays;

public class EditPaysActivity extends AppCompatActivity {
    private EditText editTextNom;
    private ImageView imageViewFlag;
    private Uri imageUri;
    private int paysId;
    private Pays currentPays;

    private final ActivityResultLauncher<Intent> pickImageLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    imageUri = result.getData().getData();
                    imageViewFlag.setImageURI(imageUri);
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_pays);

        editTextNom = findViewById(R.id.editTextNom);
        imageViewFlag = findViewById(R.id.imageViewFlag);
        Button buttonSelectImage = findViewById(R.id.buttonSelectImage);
        Button buttonSave = findViewById(R.id.buttonSave);

        paysId = getIntent().getIntExtra("pays_id", -1);

        if (paysId != -1) {
            MyApplication.getDatabase().paysDao().getPaysById(paysId).observe(this, new Observer<Pays>() {
                @Override
                public void onChanged(Pays pays) {
                    currentPays = pays;
                    if (pays != null) {
                        editTextNom.setText(pays.nom);
                        if (pays.drapeau != null) {
                            imageUri = Uri.parse(pays.drapeau);
                            imageViewFlag.setImageURI(imageUri);
                        }
                    }
                }
            });
        }

        buttonSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savePays();
            }
        });
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickImageLauncher.launch(intent);
    }

    private void savePays() {
        if (currentPays != null) {
            currentPays.nom = editTextNom.getText().toString();
            currentPays.drapeau = imageUri != null ? imageUri.toString() : currentPays.drapeau;

            MyApplication.getDatabase().paysDao().update(currentPays);
            finish(); // Close activity after saving
        }
    }
}
