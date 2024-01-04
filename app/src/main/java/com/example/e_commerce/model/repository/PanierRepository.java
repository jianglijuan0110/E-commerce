package com.example.e_commerce.model.repository;

import android.util.Log;

import com.example.e_commerce.model.model.Panier;
import com.example.e_commerce.model.model.Produit;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class PanierRepository {
    private static final String TAG = "PanierRepository";
    private FirebaseAuth auth;
    private FirebaseFirestore db;

    public PanierRepository(){
        this.auth = FirebaseAuth.getInstance();
        this.db = FirebaseFirestore.getInstance();
    }

    public void addProduitToPanier(Produit produit, int quantite) {
        // Générer un ID unique pour le panier
        String idPanier = db.collection("users")
                .document(auth.getUid())
                .collection("panier")
                .document()
                .getId();

        Panier panier = new Panier(idPanier, quantite, produit);

        db.collection("users")
                .document(auth.getUid())
                .collection("panier")
                .whereEqualTo("produit.produitId", panier.getProduit().getProduitId())
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (queryDocumentSnapshots.getDocuments().isEmpty()) {
                        // Si le panier est vide, ajouter le produit
                        db.collection("users")
                                .document(auth.getUid())
                                .collection("panier")
                                .document(idPanier)
                                .set(panier)
                                .addOnSuccessListener(aVoid -> {
                                    Log.d(TAG, "Produit ajouté dans le panier !");
                                })
                                .addOnFailureListener(e -> Log.e(TAG, "Erreur lors de l'ajout du produit dans le panier", e));
                    } else {
                        // Si le produit est déjà présent dans le panier
                        Panier cart = queryDocumentSnapshots.getDocuments().get(0).toObject(Panier.class);
                        // Incrémenter la quantité
                        int nouvelleQuantite = cart.getQuantite() + quantite;
                        // Mettre à jour la quantité dans la base de données
                        db.collection("users")
                                .document(auth.getUid())
                                .collection("panier")
                                .document(queryDocumentSnapshots.getDocuments().get(0).getId())
                                .update("quantite", nouvelleQuantite)
                                .addOnSuccessListener(aVoid -> {
                                    Log.d(TAG, "Produit ajouté dans le panier !");
                                })
                                .addOnFailureListener(e -> Log.e(TAG, "Erreur lors de la mise à jour de la quantité dans le panier", e));
                    }
                })
                .addOnFailureListener(e -> Log.e(TAG, "Erreur lors de la vérification du produit dans le panier", e));
    }


}
