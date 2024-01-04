package com.example.e_commerce.view.produit;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.e_commerce.R;
import com.example.e_commerce.model.model.Produit;
import com.example.e_commerce.viewModel.ProduitViewModel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ProduitFragment extends Fragment {
    private ActivityResultLauncher<Intent> pickImageLauncher;
    private ProduitViewModel produitViewModel;
    private EditText edName, edDescription, edPrice;
    private Button buttonImagesPicker, buttonSaveProduit;
    private ImageView selectedImage;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialiser le ViewModel en utilisant ViewModelProvider
        produitViewModel = new ViewModelProvider(this).get(ProduitViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_produit, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        buttonImagesPicker = view.findViewById(R.id.buttonImagesPicker);
        buttonSaveProduit = view.findViewById(R.id.buttonSaveProduit);

        edName = view.findViewById(R.id.edName);
        edDescription = view.findViewById(R.id.edDescription);
        edPrice = view.findViewById(R.id.edPrice);

        selectedImage = view.findViewById(R.id.selectedImage);

        // Initialiser le launcher pour récupérer l'URI de la galerie
        pickImageLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        Uri imageUri = result.getData().getData();
                        // Afficher l'image dans l'ImageView
                        selectedImage.setImageURI(imageUri);
                    }
                }
        );


        buttonImagesPicker.setOnClickListener(v -> ouvrirGalerie());

        buttonSaveProduit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Récupérer l'URI de l'ImageView ou l'image est stocké
                Uri imageUri = getImageUriFromImageView(selectedImage);

                if (imageUri != null) {
                    // Récupérer les données depuis les champs d'entrée
                    String nom = edName.getText().toString();
                    String description = edDescription.getText().toString();
                    Double prix = Double.parseDouble(edPrice.getText().toString());

                    // Créez votre objet Produit
                    Produit produit = new Produit(nom, prix, description);

                    // Appelez la méthode saveProduit du ViewModel avec l'objet Produit et l'Uri de l'image
                    produitViewModel.saveProduit(produit, imageUri);

                    Toast.makeText(requireContext(), "Produit enrégistré avec succès !", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    // Méthode pour ouvrir la galerie
    private void ouvrirGalerie() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickImageLauncher.launch(intent);
    }

    // Méthode pour récupérer l'Uri
    private Uri getImageUriFromImageView(ImageView imageView) {
        Drawable drawable = imageView.getDrawable();
        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            Bitmap bitmap = bitmapDrawable.getBitmap();
            return getImageUriFromBitmap(bitmap);
        }
        return null;
    }

    private Uri getImageUriFromBitmap(Bitmap bitmap) {
        try {
            // Créer un fichier temporaire pour stocker l'image
            File imageFile = new File(requireActivity().getCacheDir(), "temp_image.jpg");

            // Écrire le bitmap dans le fichier
            FileOutputStream outputStream = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            outputStream.flush();
            outputStream.close();

            // Renvoyer l'URI du fichier
            return Uri.fromFile(imageFile);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}