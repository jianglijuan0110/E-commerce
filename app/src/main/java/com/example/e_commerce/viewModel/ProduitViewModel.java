package com.example.e_commerce.viewModel;

import android.net.Uri;

import androidx.lifecycle.ViewModel;

import com.example.e_commerce.model.model.Produit;
import com.example.e_commerce.model.repository.ProduitRepository;

public class ProduitViewModel extends ViewModel {
    private ProduitRepository produitRepository;

    public ProduitViewModel(){
        this.produitRepository = new ProduitRepository();
    }

    public void saveProduit(Produit produit, Uri imageUri){
        produitRepository.saveProduit(produit,imageUri);
    }
}