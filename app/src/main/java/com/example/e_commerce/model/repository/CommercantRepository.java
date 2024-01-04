package com.example.e_commerce.model.repository;

import com.example.e_commerce.model.model.Commande;
import com.example.e_commerce.model.model.Produit;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

public class CommercantRepository {
    private DatabaseReference databaseReference;
    private Produit produit;
    private Commande commande;
    private FirebaseFirestore firestore;

    public CommercantRepository() {
        // Initialisez la référence à la base de données Firebase
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    /*public void createProduit(Produit produit) {
        // Générez une clé unique pour le produit
        String produitKey = databaseReference.child("produits").push().getKey();
        produit.setProduitId(produitKey);

        // Créez une référence au document du produit dans Firestore

        DocumentReference produitRef = firestore.collection("produits").document(produitKey);

        // Ajoutez le produit au document dans la collection "produits"
        produitRef.set(produit)
                .addOnSuccessListener(aVoid -> {
                    // Le produit a été ajouté avec succès
                    // Ajoutez ici votre logique en cas de succès
                })
                .addOnFailureListener(e -> {
                    // Gestion des erreurs lors de l'ajout du produit
                    // Ajoutez ici votre logique de gestion des erreurs
                });
    }


    public void updateProduit(Produit produit) {
        // Mettez à jour les détails du produit dans la base de données

        databaseReference.child("produits").child(produit.getProduitId()).setValue(produit);
    }
    public void deleteProduit(Produit produit) {
        // Supprimez le produit de la base de données
        databaseReference.child("produits").child(produit.getProduitId()).removeValue();
    }

    public void deleteCommande(Commande commande) {
        // Supprimez la commande de la base de données
        databaseReference.child("commandes").child(commande.getCommandeId()).removeValue();
    }*/
}
