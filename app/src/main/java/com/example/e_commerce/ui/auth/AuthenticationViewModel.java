package com.example.e_commerce.ui.auth;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.e_commerce.data.model.Client;
import com.example.e_commerce.data.repository.AuthenticationRepository;
import com.google.firebase.auth.FirebaseUser;

public class AuthenticationViewModel extends ViewModel {
    private AuthenticationRepository authenticationRepository;
    private MutableLiveData<FirebaseUser> userData;
    private MutableLiveData<Boolean> loggedStatus;

    public AuthenticationViewModel() {
        this.authenticationRepository = new AuthenticationRepository();
        this.userData = this.authenticationRepository.getFirebaseUserMutableLiveData();
        this.loggedStatus = this.authenticationRepository.getUserLoggedMutableLiveData();
    }

    public void register(Client client) {
        this.authenticationRepository.registerUser(client);
    }

    public void login(String email, String password) {
        this.authenticationRepository.loginUser(email, password);
    }

    public void logout() {
        this.authenticationRepository.logOutClient();
    }

    public MutableLiveData<FirebaseUser> getUserData() {
        return userData;
    }

    public MutableLiveData<Boolean> getLoggedStatus() {
        return loggedStatus;
    }

}