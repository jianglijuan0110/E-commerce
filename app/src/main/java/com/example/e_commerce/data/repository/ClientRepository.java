package com.example.e_commerce.data.repository;

import android.util.Log;

import com.example.e_commerce.data.model.Adresse;
import com.example.e_commerce.data.model.Commande;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class ClientRepository {
    private static final String TAG = "ClientRepository";
    private FirebaseAuth auth;
    private FirebaseFirestore db;

    public ClientRepository(){
        this.auth = FirebaseAuth.getInstance();
        this.db = FirebaseFirestore.getInstance();
    }
    public void addAdress(Adresse adresse){
        db.collection("users")
                .document(auth.getUid())
                .collection("adresse")
                .document()
                .set(adresse)
                .addOnSuccessListener(task -> Log.d(TAG, "Adresse ajoutée avec succès à Firestore !"))
                .addOnFailureListener(e -> Log.e(TAG, "Erreur lors de l'ajout de l'adresse à Firestore", e));
    }

    public void passerCommande(Commande commande){
        // Ajouter la commande aux commandes de l'utilisateur
        db.collection("users")
                .document(auth.getUid())
                .collection("commandes")
                .document()
                .set(commande);

        // Ajouter la commande à la table 'commandes'
        db.collection("commandes")
                .document()
                .set(commande);

        // Supprimmer la commande du panier de l'utilisateur
        db.collection("users")
                .document(auth.getUid())
                .collection("panier")
                .get()
                .addOnSuccessListener(task ->{

                });
    }

    public void updateProfile(){

    }

}
