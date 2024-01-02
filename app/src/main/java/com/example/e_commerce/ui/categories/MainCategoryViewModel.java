package com.example.e_commerce.ui.categories;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.e_commerce.data.model.Produit;
import com.example.e_commerce.data.repository.ProduitRepository;

import java.util.List;

public class MainCategoryViewModel extends ViewModel {
    private ProduitRepository produitRepository;
    private MutableLiveData<List<Produit>> produitsLiveData;

    public MainCategoryViewModel() {
        this.produitsLiveData = new MutableLiveData<>();
        this.produitRepository = new ProduitRepository();
    }

    // Méthode pour récupérer tous les produits du Firestore
    public void getAllProduits() {
        produitRepository.getAllProduits(
                produits -> produitsLiveData.postValue(produits),
                e -> Log.e("CategoryViewModel", "Erreur lors de la récupération de tous les produits", e)
        );
    }

    // Exposer la MutableLiveData à l'interface utilisateur
    public MutableLiveData<List<Produit>> getProduitsLiveData() {
        return produitsLiveData;
    }

}