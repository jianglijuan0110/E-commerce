package com.example.e_commerce.model.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.e_commerce.model.model.Client;
import com.example.e_commerce.model.model.Commande;
import com.example.e_commerce.model.model.Panier;
import com.example.e_commerce.model.model.Produit;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.Transaction;

import java.util.ArrayList;
import java.util.List;

public class ClientRepository {
    private static final String TAG = "ClientRepository";
    private FirebaseAuth auth;
    private FirebaseFirestore db;
    private MutableLiveData<List<Panier>> panierProduitsLiveData = new MutableLiveData<>();

    public LiveData<List<Panier>> getPanierProduitsLiveData() {
        return panierProduitsLiveData;
    }

    public ClientRepository(){
        this.auth = FirebaseAuth.getInstance();
        this.db = FirebaseFirestore.getInstance();
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

    public void getClientInfo(ClientInfoCallback callback){
        DocumentReference clientDocRef = db.collection("users").document(auth.getUid());

        clientDocRef.get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        Client client = documentSnapshot.toObject(Client.class);
                        callback.onClientInfoLoaded(client);
                    } else {
                        callback.onClientInfoError("Aucune information client trouvée");
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Erreur lors de la récupération des informations client", e);
                    callback.onClientInfoError("Erreur lors de la récupération des informations client");
                });
    }

    public interface ClientInfoCallback {
        void onClientInfoLoaded(Client client);
        void onClientInfoError(String errorMessage);
    }


    public void updateProfile(String firstName, String lastName) {
        // Mettre à jour le profil dans la base de données Firestore
        DocumentReference clientDocRef = db.collection("users").document(auth.getUid());

        clientDocRef.update(
                        "nom", firstName,
                        "prenom", lastName
                )
                .addOnSuccessListener(aVoid -> Log.d(TAG, "Profil mis à jour avec succès !"))
                .addOnFailureListener(e -> Log.e(TAG, "Erreur lors de la mise à jour du profil !", e));
    }

    public void logOutClient() {
        auth.signOut();
    }

    public void getPanierProduits(PanierProduitsCallback callback) {
        CollectionReference panierCollectionRef = db.collection("users").document(auth.getUid()).collection("panier");

        panierCollectionRef.get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<Panier> panierProduits = new ArrayList<>();

                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                        Panier produit = documentSnapshot.toObject(Panier.class);
                        panierProduits.add(produit);
                    }
                    panierProduitsLiveData.setValue(panierProduits);
                    callback.onPanierProduitsLoaded(panierProduits);
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Erreur lors de la récupération des produits du panier", e);
                    callback.onPanierProduitsError("Erreur lors de la récupération des produits du panier");
                });
    }

    public interface PanierProduitsCallback {
        void onPanierProduitsLoaded(List<Panier> panierProduits);
        void onPanierProduitsError(String errorMessage);
    }

    public void calculateTotalPrice(TotalPriceCallback callback) {
        CollectionReference panierRef = db.collection("users")
                .document(auth.getUid())
                .collection("panier");

        panierRef.get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    double totalPrice = 0.0;

                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                        Panier panier = documentSnapshot.toObject(Panier.class);
                        totalPrice += panier.getQuantite() * panier.getProduit().getPrix();
                    }

                    // Appeler la fonction de rappel avec le prix total
                    callback.onTotalPriceCalculated(totalPrice);
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Erreur lors du calcul du prix total du panier", e);
                    callback.onTotalPriceError("Erreur lors du calcul du prix total du panier");
                });
    }

    public interface TotalPriceCallback {
        void onTotalPriceCalculated(double totalPrice);
        void onTotalPriceError(String errorMessage);
    }

    public void incrementQuantityInPanier(String idPanier) {
        DocumentReference produitDocRef = db.collection("users")
                .document(auth.getUid())
                .collection("panier")
                .document(idPanier);

        db.runTransaction((Transaction.Function<Void>) transaction -> {
                    DocumentSnapshot produitSnapshot = transaction.get(produitDocRef);

                    if (produitSnapshot.exists()) {
                        Panier panierProduit = produitSnapshot.toObject(Panier.class);

                        if (panierProduit != null) {
                            int newQuantity = panierProduit.getQuantite() + 1;
                            transaction.update(produitDocRef, "quantite", newQuantity);
                        }
                    }

                    return null;
                })
                .addOnSuccessListener(aVoid -> Log.d(TAG, "Quantité du produit incrémentée avec succès !"))
                .addOnFailureListener(e -> Log.e(TAG, "Erreur lors de l'incrémentation de la quantité du produit", e));
    }

    public void decrementQuantityInPanier(String idPanier) {
        DocumentReference produitDocRef = db.collection("users")
                .document(auth.getUid())
                .collection("panier")
                .document(idPanier);

        db.runTransaction((Transaction.Function<Void>) transaction -> {
                    DocumentSnapshot produitSnapshot = transaction.get(produitDocRef);

                    if (produitSnapshot.exists()) {
                        Panier panierProduit = produitSnapshot.toObject(Panier.class);

                        if (panierProduit != null) {
                            int currentQuantity = panierProduit.getQuantite();

                            if (currentQuantity > 1) {
                                int newQuantity = currentQuantity - 1;
                                transaction.update(produitDocRef, "quantite", newQuantity);
                            } else {
                                // Supprimer l'article si la quantité est égale à 1
                                transaction.delete(produitDocRef);
                            }
                        }
                    }

                    return null;
                })
                .addOnSuccessListener(aVoid -> Log.d(TAG, "Quantité du produit décrémentée avec succès !"))
                .addOnFailureListener(e -> Log.e(TAG, "Erreur lors de la décrémentation de la quantité du produit", e));
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
