package com.example.e_commerce.ui.produit;

import android.net.Uri;

import androidx.lifecycle.ViewModel;

import com.example.e_commerce.data.model.Produit;
import com.example.e_commerce.data.repository.ProduitRepository;

public class ProduitViewModel extends ViewModel {
    private ProduitRepository produitRepository;

    public ProduitViewModel(){
        this.produitRepository = new ProduitRepository();
    }

    public void saveProduit(Produit produit, Uri imageUri){
        produitRepository.saveProduit(produit,imageUri);
    }
}