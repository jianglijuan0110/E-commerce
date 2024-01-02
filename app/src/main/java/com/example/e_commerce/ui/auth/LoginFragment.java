package com.example.e_commerce.ui.auth;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.e_commerce.R;
import com.google.firebase.auth.FirebaseUser;

public class LoginFragment extends Fragment {
    private AuthenticationViewModel authenticationViewModel;
    private EditText edPasswordLogin, edEmailLogin;
    private TextView dontHaveAccount;
    private Button loginButton;
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
                    navController.navigate(R.id.action_loginFragment_to_logoutFragment);
                }
            }
        });

        // Observer les changements dans le statut de connexion
        authenticationViewModel.getLoggedStatus().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean userLogged) {
                if (userLogged) {
                    // Utilisateur inconnu
                    Toast.makeText(requireContext(), "Email et ou mot de passe inconnu(s) !", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(requireContext(), "Vous êtes maintenant connecté !", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loginButton = view.findViewById(R.id.buttonLoginLogin);
        edEmailLogin = view.findViewById(R.id.edEmailLogin);
        edPasswordLogin = view.findViewById(R.id.edPasswordLogin);
        dontHaveAccount = view.findViewById(R.id.dontHaveAccount);

        // Initialisation de notre navController
        navController = Navigation.findNavController(view);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Récupérer les données depuis les champs d'entrée
                String email = edEmailLogin.getText().toString();
                String password = edPasswordLogin.getText().toString();

                if (TextUtils.isEmpty(email)){
                    edEmailLogin.setError("Email requis !");
                    return;
                }
                if (TextUtils.isEmpty(password)){
                    edPasswordLogin.setError("Mot de passe requis !");
                    return;
                }
                if (password.length() < 6){
                    edPasswordLogin.setError("Le mot de passe doit comporter au moins 6 charactères !");
                    return;
                }
                authenticationViewModel.login(email,password);
            }
        });

        dontHaveAccount.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_loginFragment_to_registerFragment);
            }
        });
    }
}