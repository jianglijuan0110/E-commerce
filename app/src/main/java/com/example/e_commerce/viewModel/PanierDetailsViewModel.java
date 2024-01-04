package com.example.e_commerce.viewModel;

import androidx.lifecycle.ViewModel;

import com.example.e_commerce.model.model.Produit;
import com.example.e_commerce.model.repository.PanierRepository;

public class PanierDetailsViewModel extends ViewModel {
    private PanierRepository panierRepository;

    public PanierDetailsViewModel(){
        this.panierRepository = new PanierRepository();
    }

    public void ajouterAuPanier(Produit produit, int quantite){
        panierRepository.addProduitToPanier(produit,quantite);
    }
}