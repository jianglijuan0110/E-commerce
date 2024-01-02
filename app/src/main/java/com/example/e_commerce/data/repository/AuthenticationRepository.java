package com.example.e_commerce.data.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.e_commerce.data.model.Client;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AuthenticationRepository {
    private static final String TAG = "AuthRepository";
    private FirebaseAuth auth;
    private FirebaseFirestore db;

    private MutableLiveData<FirebaseUser> firebaseUserMutableLiveData;
    private MutableLiveData<Boolean> userLoggedMutableLiveData; //true : déconnecté
                                                                //false : connecté

    public AuthenticationRepository() {
        this.auth = FirebaseAuth.getInstance();
        this.db = FirebaseFirestore.getInstance();

        this.firebaseUserMutableLiveData = new MutableLiveData<>();
        this.userLoggedMutableLiveData = new MutableLiveData<>();

        if (auth.getCurrentUser() != null) {
            firebaseUserMutableLiveData.postValue(auth.getCurrentUser());
        }
    }

    public MutableLiveData<FirebaseUser> getFirebaseUserMutableLiveData() {
        return firebaseUserMutableLiveData;
    }

    public MutableLiveData<Boolean> getUserLoggedMutableLiveData() {
        return userLoggedMutableLiveData;
    }

    public void registerUser(Client client) {
        auth.createUserWithEmailAndPassword(client.getEmail(), client.getMotDePasse())
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser firebaseUser = auth.getCurrentUser();
                        if (firebaseUser != null) {
                            // Ajouter l'utilisateur à Firestore
                            String userId = firebaseUser.getUid();
                            addUserToFirestore(userId, client);
                            // Mettre à jour le MutableLiveData avec l'utilisateur actuel
                            firebaseUserMutableLiveData.postValue(firebaseUser);
                            // Mettre à jour le status avec l'utilisateur actuel
                            userLoggedMutableLiveData.postValue(false);
                        }
                    } else {
                        // L'enregistrement a échoué
                        Log.e(TAG, "Erreur lors de l'enrégistrement de l'utilisateur", task.getException());
                    }
                });
    }

    private void addUserToFirestore(String userId, Client client) {
        Map<String, Object> user = new HashMap<>();
        user.put("email", client.getEmail());
        user.put("nom", client.getNom());
        user.put("prenom", client.getPrenom());
        user.put("img", client.getImgPath());
        // Spécifier le role de l'utilisateur
        user.put("role", client.getRole());

        db.collection("users")
                .document(userId)
                .set(user)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Utilisateur ajouté avec succès à Firestore
                        Log.d(TAG, "Utilisateur ajouté avec succès à Firestore");
                    } else {
                        // Échec de l'enregistrement des données du client
                        Log.e(TAG, "Erreur lors de l'ajout de l'utilisateur à Firestore", task.getException());
                    }
                });
    }

    public void loginUser(String email, String password) {
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser firebaseUser = auth.getCurrentUser();
                        if (firebaseUser != null) {
                            // Vérifier si l'utilisateur existe dans Firestore
                            checkIfUserExists(firebaseUser.getUid());
                        }
                    } else {
                        Log.e(TAG, "Erreur lors de la connexion de l'utilisateur", task.getException());
                        // Mettre à jour le status avec l'utilisateur actuel
                        userLoggedMutableLiveData.postValue(true);
                    }
                });
    }

    private void checkIfUserExists(String userId) {
        db.collection("users")
                .document(userId)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            // L'utilisateur existe dans Firestore
                            // Mettre à jour le MutableLiveData avec l'utilisateur actuel
                            firebaseUserMutableLiveData.postValue(auth.getCurrentUser());
                            // Mettre à jour le status avec l'utilisateur actuel
                            userLoggedMutableLiveData.postValue(false);
                            Log.d(TAG, "Utilisateur connecté");
                        } else {
                            // L'utilisateur n'existe pas dans Firestore
                            Log.e(TAG, "Utilisateur non présent dans Firestore");
                            userLoggedMutableLiveData.postValue(true);
                        }
                    } else {
                        Log.e(TAG, "Erreur lors de la recherche de l'utilisateur dans Firestore", task.getException());
                        userLoggedMutableLiveData.postValue(true);
                    }
                });
    }

    public void logOutClient() {
        auth.signOut();
        // Mettre à jour le MutableLiveData avec la valeur true
        userLoggedMutableLiveData.postValue(true);
    }

}
