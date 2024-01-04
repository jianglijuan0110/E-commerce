package com.example.e_commerce.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.e_commerce.model.model.Client;
import com.example.e_commerce.model.model.Panier;
import com.example.e_commerce.model.repository.ClientRepository;

import java.util.List;

public class UserDetailsViewModel extends ViewModel {
    private ClientRepository clientRepository;
    private MutableLiveData<Client> clientInfoLiveData = new MutableLiveData<>();
    private MutableLiveData<List<Panier>> panierProduitsLiveData = new MutableLiveData<>();

    private MutableLiveData<Double> totalPriceLiveData = new MutableLiveData<>();

    public UserDetailsViewModel(){
        this.clientRepository = new ClientRepository();
        loadClientInfo();
        loadPanierProduits();
    }

    public LiveData<Client> getClientInfo() {
        return clientInfoLiveData;
    }

    private void loadClientInfo() {
        clientRepository.getClientInfo(new ClientRepository.ClientInfoCallback() {
            @Override
            public void onClientInfoLoaded(Client clientInfo) {
                clientInfoLiveData.setValue(clientInfo);
            }

            @Override
            public void onClientInfoError(String errorMessage) {
                // Handle error if needed
            }
        });
    }

    private void loadPanierProduits() {
        clientRepository.getPanierProduits(new ClientRepository.PanierProduitsCallback() {
            @Override
            public void onPanierProduitsLoaded(List<Panier> panierProduits) {
                panierProduitsLiveData.setValue(panierProduits);
            }

            @Override
            public void onPanierProduitsError(String errorMessage) {
                // Handle error if needed
            }
        });
    }

    public void calculateTotalPrice() {
        clientRepository.calculateTotalPrice(new ClientRepository.TotalPriceCallback() {
            @Override
            public void onTotalPriceCalculated(double totalPrice) {
                // Mettre à jour le LiveData avec le prix total
                totalPriceLiveData.postValue(totalPrice);
            }

            @Override
            public void onTotalPriceError(String errorMessage) {
                // Mettre à jour le LiveData avec l'erreur
            }
        });
    }

    // Getter pour le LiveData du prix total
    public LiveData<Double> getTotalPriceLiveData() {
        return totalPriceLiveData;
    }

    public void updateProfile(String firstName, String lastName) {
        clientRepository.updateProfile(firstName,lastName);
    }

    public void logOut(){
        clientRepository.logOutClient();
    }

    public void incrementQuantityInPanier(String idPanier) {
        clientRepository.incrementQuantityInPanier(idPanier);
    }

    // Méthode pour décrémenter la quantité d'un produit dans le panier
    public void decrementQuantityInPanier(String idPanier) {
        clientRepository.decrementQuantityInPanier(idPanier);
    }

    public LiveData<List<Panier>> getPanierProduits() {
        return clientRepository.getPanierProduitsLiveData();
    }
}