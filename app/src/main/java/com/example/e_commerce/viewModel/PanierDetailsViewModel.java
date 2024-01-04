package com.example.e_commerce.viewModel;

import androidx.lifecycle.ViewModel;

import com.example.e_commerce.model.model.Produit;
import com.example.e_commerce.model.repository.ClientRepository;

public class PanierDetailsViewModel extends ViewModel {
    private ClientRepository clientRepository;
    public PanierDetailsViewModel(){
        this.clientRepository = new ClientRepository();
    }

    public void ajouterAuPanier(Produit produit, int quantite){
        clientRepository.addProduitToPanier(produit,quantite);
    }
}