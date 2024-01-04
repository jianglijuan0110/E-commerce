package com.example.e_commerce.view.auth;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.e_commerce.R;
import com.example.e_commerce.model.model.Client;
import com.google.firebase.auth.FirebaseUser;

public class RegisterFragment extends Fragment {

    private AuthenticationViewModel authenticationViewModel;
    private EditText edFirstNameRegister, edLastNameRegister, edEmailRegister, edPasswordRegister;
    private TextView doYouHaveAccount;
    private Button registerButton;
    private RadioButton radioButton1, radioButton2;

    private NavController navController;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialiser le ViewModel en utilisant ViewModelProvider
        authenticationViewModel = new ViewModelProvider(this).get(AuthenticationViewModel.class);

        // Observer les changements dans les données utilisateur
        authenticationViewModel.getUserData().observe(this, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                // Mettre à jour l'interface utilisateur
                if (firebaseUser != null) {
                    // L'utilisateur est connecté
                    // Naviguer vers une autre activité ou effectuer d'autres actions
                    navController.navigate(R.id.action_registerFragment_to_loginFragment);
                }
            }
        });

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        registerButton = view.findViewById(R.id.buttonRegisterRegister);
        edFirstNameRegister = view.findViewById(R.id.edFirstNameRegister);
        edLastNameRegister = view.findViewById(R.id.edLastNameRegister);
        edEmailRegister = view.findViewById(R.id.edEmailRegister);
        edPasswordRegister = view.findViewById(R.id.edPasswordRegister);
        doYouHaveAccount = view.findViewById(R.id.doYouHaveAccount);

        radioButton1 = view.findViewById(R.id.radioButton1);
        radioButton2 = view.findViewById(R.id.radioButton2);

        // Initialisation de notre navController
        navController = Navigation.findNavController(view);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Récupérer les données depuis les champs d'entrée
                String nom = edFirstNameRegister.getText().toString();
                String prenom = edLastNameRegister.getText().toString();
                String email = edEmailRegister.getText().toString();
                String password = edPasswordRegister.getText().toString();

                if (TextUtils.isEmpty(nom)){
                    edFirstNameRegister.setError("Nom requis !");
                    return;
                }
                if (TextUtils.isEmpty(prenom)){
                    edLastNameRegister.setError("Prénom requis !");
                    return;
                }
                if (TextUtils.isEmpty(email)){
                    edEmailRegister.setError("Email requis !");
                    return;
                }
                if (TextUtils.isEmpty(password)){
                    edPasswordRegister.setError("Mot de passe requis !");
                    return;
                }
                if (password.length() < 6){
                    edPasswordRegister.setError("Le mot de passe doit comporter au moins 6 charactères !");
                    return;
                }

                // Vérifier quel CheckBox est sélectionné
                String role; // Utilisateur normal
                if (radioButton2.isChecked()) {
                    role = "commercant"; // Commerçant
                    Client client = new Client(nom, prenom, email, password, role);
                    registerClientAndShowToast(client, role);
                } else if (radioButton1.isChecked()) {
                    role = "client"; // Utilisateur normal
                    Client client = new Client(nom, prenom, email, password, role);
                    registerClientAndShowToast(client, role);
                } else {
                    Toast.makeText(requireContext(), "Vous devez sélectionner un des checkbox !", Toast.LENGTH_SHORT).show();
                }

            }
        });

        doYouHaveAccount.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_registerFragment_to_loginFragment);
            }
        });

    }

    private void registerClientAndShowToast(Client client, String role) {
        authenticationViewModel.register(client);
        // Afficher un message Toast en fonction du rôle
        String toastMessage = String.format("Compte %s créé avec succès ! Vous êtes maintenant connecté !", role);
        Toast.makeText(requireContext(), toastMessage, Toast.LENGTH_SHORT).show();
    }

}