package com.example.e_commerce.ui.adress;

import androidx.lifecycle.ViewModel;

import com.example.e_commerce.data.model.Adresse;
import com.example.e_commerce.data.repository.ClientRepository;

public class AdressViewModel extends ViewModel {
    private ClientRepository clientRepository;

    public AdressViewModel(){
        this.clientRepository = new ClientRepository();
    }

    public void addAdress(Adresse adresse){
        clientRepository.addAdress(adresse);
    }
}