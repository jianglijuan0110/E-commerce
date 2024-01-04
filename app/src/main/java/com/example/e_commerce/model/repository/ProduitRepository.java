package com.example.e_commerce.model.repository;

import android.net.Uri;
import android.util.Log;

import com.example.e_commerce.model.model.Produit;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class ProduitRepository {
    private static final String TAG = "ProduitRepository";
    private FirebaseFirestore db;
    private FirebaseStorage storage;

    private StorageReference ref;

    public ProduitRepository() {
        this.db = FirebaseFirestore.getInstance();
        this.storage = FirebaseStorage.getInstance();
        this.ref = storage.getReference();
    }

    public void saveProduit(Produit produit, Uri imageUri) {
        // Générer un ID unique pour le produit
        String produitId = db.collection("produits").document().getId();
        produit.setProduitId(produitId);

        // Enregistrer le produit dans Firestore
        db.collection("produits")
                .document(produitId)
                .set(produit)
                .addOnSuccessListener(aVoid -> {
                    Log.d(TAG, "Produit enregistré avec succès dans Firestore");

                    // Enregistrer l'image dans le stockage Firebase
                    uploadImage(produitId, imageUri);
                })
                .addOnFailureListener(e ->
                        Log.e(TAG, "Erreur lors de l'enregistrement du produit dans Firestore", e));
    }

    public void uploadImage(String produitId, Uri imageUri) {
        // Référence au stockage Firebase
        StorageReference storageRef = storage.getReference();
        StorageReference imageRef = storageRef.child("images/" + produitId + ".jpg");

        // Télécharger l'image dans le stockage Firebase
        imageRef.putFile(imageUri)
                .addOnSuccessListener(taskSnapshot -> {
                    // Récupérer l'URL de téléchargement de l'image
                    imageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                        // Définir l'URL de téléchargement dans l'objet Produit
                        String imageUrl = uri.toString();

                        // Utiliser la méthode getProduitById pour récupérer le produit
                        getProduitById(produitId,
                                produit -> {
                                    if (produit != null) {
                                        // Le produit a été trouvé, mettre à jour le Produit
                                        produit.setProduitImg(imageUrl);

                                        // Mettre à jour le produit dans Firestore avec l'URL de l'image
                                        updateProduit(produit);
                                        Log.d(TAG, "Image enregistrée avec succès dans le stockage Firebase et mise à jour du produit dans Firestore");
                                    } else {
                                        // Le produit n'a pas été trouvé
                                        Log.d(TAG, "Produit non trouvé pour l'ID : " + produitId);
                                    }
                                },
                                e -> Log.e(TAG, "Erreur lors de la récupération du produit pour l'ID : " + produitId, e)
                        );
                    });
                })
                .addOnFailureListener(e ->
                        Log.e(TAG, "Erreur lors de l'enregistrement de l'image dans le stockage Firebase", e));
    }

    private void updateProduit(Produit produit) {
        // Mettre à jour le produit dans Firestore avec l'URL de l'image
        db.collection("produits")
                .document(produit.getProduitId())
                .set(produit)
                .addOnSuccessListener(aVoid ->
                        Log.d(TAG, "Produit mis à jour avec l'URL de l'image dans Firestore"))
                .addOnFailureListener(e ->
                        Log.e(TAG, "Erreur lors de la mise à jour du produit dans Firestore", e));
    }

    private void getProduitById(String produitId, OnSuccessListener<Produit> successListener, OnFailureListener failureListener) {
        // Récupérer le produit à partir de Firestore
        db.collection("produits")
                .document(produitId)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        // Le document existe, mapper le document sur l'objet Produit
                        Produit produit = documentSnapshot.toObject(Produit.class);
                        successListener.onSuccess(produit);
                    } else {
                        // Le document n'existe pas
                        successListener.onSuccess(null);
                    }
                })
                .addOnFailureListener(failureListener);
    }

    public void getAllProduits(OnSuccessListener<List<Produit>> successListener, OnFailureListener failureListener) {
        db.collection("produits")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<Produit> produits = new ArrayList<>();
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        Produit produit = document.toObject(Produit.class);
                        produits.add(produit);
                    }
                    successListener.onSuccess(produits);
                })
                .addOnFailureListener(failureListener);
    }

}

