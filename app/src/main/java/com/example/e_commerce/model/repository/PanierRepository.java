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

}
